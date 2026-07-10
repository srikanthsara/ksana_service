-- ==========================================
-- Create Databases
-- ==========================================

CREATE DATABASE auth_db;
CREATE DATABASE cart_db;
CREATE DATABASE order_db;
CREATE DATABASE payment_db;
CREATE DATABASE search_db;

-- ==========================================
-- Create Users
-- ==========================================

CREATE USER auth_user WITH PASSWORD 'auth_password';
CREATE USER cart_user WITH PASSWORD 'cart_password';
CREATE USER order_user WITH PASSWORD 'order_password';
CREATE USER payment_user WITH PASSWORD 'payment_password';
CREATE USER search_user WITH PASSWORD 'search_password';

-- ==========================================
-- Database Privileges
-- ==========================================

GRANT ALL PRIVILEGES ON DATABASE auth_db TO auth_user;
GRANT ALL PRIVILEGES ON DATABASE cart_db TO cart_user;
GRANT ALL PRIVILEGES ON DATABASE order_db TO order_user;
GRANT ALL PRIVILEGES ON DATABASE payment_db TO payment_user;
GRANT ALL PRIVILEGES ON DATABASE search_db TO search_user;

-- ==================================================
--After creating the databases, PostgreSQL still leaves the public schema owned by postgres.
--Flyway needs CREATE permission on the schema.
-- ==================================================

ALTER DATABASE auth_db OWNER TO auth_user;

\connect auth_db

GRANT ALL ON SCHEMA public TO auth_user;
ALTER SCHEMA public OWNER TO auth_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO auth_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO auth_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON TABLES TO auth_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON SEQUENCES TO auth_user;


-- ==================================================
-- cart_db permission
-- ==================================================


ALTER DATABASE cart_db OWNER TO cart_user;

\connect cart_db

GRANT ALL ON SCHEMA public TO cart_user;
ALTER SCHEMA public OWNER TO cart_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO cart_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO cart_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON TABLES TO cart_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON SEQUENCES TO cart_user;


-- ==================================================
-- order_db permission
-- ==================================================
ALTER DATABASE order_db OWNER TO order_user;

\connect order_db

GRANT ALL ON SCHEMA public TO order_user;
ALTER SCHEMA public OWNER TO order_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO order_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO order_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON TABLES TO order_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON SEQUENCES TO order_user;

-- ==================================================
-- payment_db permission
-- ==================================================
ALTER DATABASE payment_db OWNER TO payment_user;

\connect payment_db

GRANT ALL ON SCHEMA public TO payment_user;
ALTER SCHEMA public OWNER TO payment_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO payment_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO payment_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON TABLES TO payment_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON SEQUENCES TO payment_user;

-- ==================================================
-- search_db permission
-- ==================================================
ALTER DATABASE search_db OWNER TO search_user;

\connect search_db

GRANT ALL ON SCHEMA public TO search_user;
ALTER SCHEMA public OWNER TO search_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO search_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO search_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON TABLES TO search_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON SEQUENCES TO search_user;