# cartservice
cartservice

#### Swagger url:http://localhost:8763/cartservice/swagger-ui/index.html
##### FINAL RESULT


AddCart:


{
	  
	  "customerId": "CUST100",
	  "customerName": "Srikanth",
	  "productId": "GROC1002",
	  "productName": "India Gate Basmati Rice 25KG",
	  "category": "Grocery",
	  "brand": "India Gate",
	  "quantity": 2,
	  "unitPrice": 2450
	  
}


FIRST REQUEST

{
  
	"productId": "RICE100",
	"quantity": 2
  
}

creates:

Product	   Qty

Rice	   		 2



SECOND REQUEST SAME PRODUCT

{
  
	"productId": "RICE100",
	"quantity": 3
  
}

updates:

Product	 Qty

Rice	 		5

NOT duplicate rows.