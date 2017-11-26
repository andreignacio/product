# product

Run the tests
Execute de commnad in the root of project:
	- mvn test

Run the application
Execute de commnad in the root of project:
        - mvn spring-boot:run


Services:

{server}/product/all
	Return json body with products, without your images and parent

{server}/product/all/hierarchy
	Return json body with products, your images and parent

{server}/product/parent/{id}
	Return json body with products, by parent

{server}/product/{id}
	Return json body with the product by your id

{server}/product Verb POST
	Save a new Product
	Contratc - 
		{
    "id":  ,
    "name": "",
    "description": "" 
}		
	The fields name and description are required
	Return json body with the new product

{server}/product Verb PUT
	Updete a Product
	Contratc - 
		{
    "id":  ,
    "name": "",
    "description": "" 
}
		The fields id, name and description are required
	Return json body with the updated product

{server}/product Verb DELETE
	Delete a Product
	Contratc - 
		{
    "id":  ,
    "name": "",
    "description": "" 
}
		The field id are required

{server}/image/product/{id}
	Return json body with the images by prodcut	


{server}/product Verb POST
	Save a new Image
	Contratc - 
		 {
    "id": "" ,
    "type": "",
    "product": {
        "id": 1
    }
}
	The fields type and product are required
	Return json body with the new Image


{server}/product Verb PUT
	Update a Image
	Contratc - 
		 {
    "id": "" ,
    "type": "JPG"
}
	The fields id and type are required
	Return json body with the updated product

{server}/product Verb DELETE
	Delete a Image
	Contratc - 
		{
    "id": "" ,
    "type": ""
}
	The field id are required







