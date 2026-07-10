@echo off
setlocal EnableDelayedExpansion


echo ==========================================
echo Cleaning Previous Deployment
echo ==========================================

kubectl delete namespace ksana --ignore-not-found=true
kubectl delete pods --all -n ksana

:WAIT_NAMESPACE
kubectl get namespace ksana >nul 2>&1
if %ERRORLEVEL%==0 (
    timeout /t 5 >nul
    goto WAIT_NAMESPACE
)

docker container prune -f
docker image prune -f
docker volume prune -f
docker network prune -f
docker system prune -a --volumes -f

REM Always run from the directory containing this script
cd /d "%~dp0"


echo ====================================================
echo Building All Spring Boot Services
echo ====================================================

for /d %%d in (*) do (

    if exist "%%d\pom.xml" (

        echo.
        echo ==========================================
        echo Building %%d
        echo ==========================================

        pushd "%%d"

        call mvn clean install

        if errorlevel 1 (
            echo.
            echo ***************************************
            echo BUILD FAILED : %%d
            echo ***************************************
            popd
            pause
            exit /b 1
        )

        popd
    )
)

echo.
echo ====================================================
echo Maven Build Completed Successfully
echo ====================================================

echo.
echo ====================================================
echo Cleaning Old Docker Containers
echo ====================================================

if exist docker-compose.yml (
    docker compose down -v
)


docker container prune -f

echo.
echo ====================================================
echo Removing Old Project Images
echo ====================================================

for %%i in (
ksana_service-configserver
ksana_service-discoveryservice
ksana_service-apigateway
ksana_service-authservice
ksana_service-cartservice
ksana_service-grocerysearchservice
ksana_service-orderservice
ksana_service-paymentservice
ksana_service-ksana-app
) do (

    docker image inspect %%i:latest >nul 2>&1

    if !errorlevel! EQU 0 (
        echo Removing %%i
        docker image rm -f %%i:latest
    ) else (
        echo %%i does not exist. Skipping.
    )
)

echo.
echo ====================================================
echo Building Docker Images
echo ====================================================


docker compose build --parallel --no-cache

if errorlevel 1 (
    goto :FAILED
)


docker build -t ksana_service-ksana-app:latest ./ksana-app

docker images

docker ps -a



echo.
echo ====================================================
echo Docker Images Built Successfully
echo ====================================================



echo.
echo ====================================================
echo Apply k8s Start
echo ====================================================


if not exist k8s (
    echo k8s folder not found.
    pause
    exit /b 1
)

cd k8s

kubectl apply -f namespace.yaml
kubectl apply -f common/
kubectl apply -f postgres/
kubectl create configmap postgres-initdb  --from-file=init.sql=./postgres/init.sql -n ksana --dry-run=client -o yaml > ./postgres/initdb-configmap.yaml

kubectl apply -f ./postgres/initdb-configmap.yaml
kubectl apply -f zookeeper/
kubectl apply -f kafka/


kubectl rollout status statefulset/postgres -n ksana
echo ### kubectl rollout status statefulset/zookeeper -n ksana
kubectl rollout status statefulset/kafka -n ksana


kubectl apply -f configserver/
echo ### kubectl rollout restart deployment configserver -n ksana
kubectl rollout status deployment/configserver -n ksana

kubectl apply -f discoveryservice/
echo ### kubectl rollout restart deployment discoveryservice -n ksana
kubectl rollout status deployment/discoveryservice -n ksana

kubectl apply -f authservice/
echo ### kubectl rollout restart deployment authservice -n ksana
kubectl rollout status deployment/authservice -n ksana --timeout=300s || goto :FAILED

kubectl apply -f grocerysearchservice/
echo ### kubectl rollout restart deployment grocerysearchservice -n ksana
kubectl rollout status deployment/grocerysearchservice -n ksana --timeout=300s || goto :FAILED

kubectl apply -f cartservice/
echo ### kubectl rollout restart deployment cartservice -n ksana
kubectl rollout status deployment/cartservice -n ksana --timeout=300s || goto :FAILED

kubectl apply -f orderservice/
echo ### kubectl rollout restart deployment orderservice -n ksana
kubectl rollout status deployment/orderservice -n ksana --timeout=300s || goto :FAILED

kubectl apply -f paymentservice/
echo ### kubectl rollout restart deployment paymentservice -n ksana
kubectl rollout status deployment/paymentservice -n ksana --timeout=300s || goto :FAILED

kubectl apply -f apigateway/
echo ### kubectl rollout restart deployment apigateway -n ksana
kubectl rollout status deployment/apigateway -n ksana --timeout=300s || goto :FAILED


kubectl apply -f ksana-app/
echo ### kubectl rollout restart deployment ksana-app -n ksana
kubectl rollout status deployment/ksana-app  -n ksana --timeout=300s || goto :FAILED

kubectl get deployments -n ksana
kubectl get pods -n ksana
kubectl get all -n ksana


echo.
echo ====================================================
echo K8S deployment Done Successfully
echo ====================================================

pause
exit /b

:FAILED

echo.
echo ***********************************************
echo Docker Build Failed
echo ***********************************************


pause
exit /b 1