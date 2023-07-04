# order-management


a.	How to build the application?
1) Define the Entities: Product, Stock, Order, ProductOrder, Customer
2) Database Design: define the attributes/columns for each entity with their datatypes and the re;ationships between them
3) Implement API Endpoints: Implement the necessary CRUD (Create, Read, Update, Delete) operations for each entity
4) Implement exception handling: Bad Request, Resource not found


b.	How to create and run the docker image?
1) create Dockerfile: FROM httpd:2.4
                      COPY ./public-html/ /usr/local/apache2/htdocs/
2) docker build -t my-apache2 .
3) docker run -dit --name my-running-app -p 8080:80 my-apache2
