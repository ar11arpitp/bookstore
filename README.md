# Online Bookstore
Online Bookstore is a secure Spring Boot project that enables users to search for books, add or remove them from their shopping cart, and proceed with the checkout process. The application calculates the total payable amount by applying relevant discounts. User-specific security measures are implemented to ensure that each user can only access and modify their own shopping cart.

## Technology Stack
Below tech Stack has been used to develop this project :
- Java 17
- Spring Boot 3.1.1
- Spring Security (Basic Auth)
- Docker
- Docker Compose
- Nginx (load balancer for multicontainerized applicaion)
- JOCOCO
- JUnit 5
- Swagger OpenAPI 2.1.0
- MySQL (Used for multicontainer application as it can be deployed separately)
- H2 (its used in local enviroment for the hassle free setup)
- Spring Data JPA

## Requirement
### Book Attributes
Each book has the following attributes:
- Name
- Description
- Author 
- Type/Classification 
- Price 
- ISBN

### Discounts
The promotional offers and discounts vary based on the type or classification of books. For instance, fiction books might be eligible for a 10% discount, whereas comic books are not eligible for any discount.
-------------------------------------------------------------------------------------------

## Manual
To execute the application on your local machine, please choose the "local" spring profile. However, I highly recommend utilizing Docker Compose, which is a tool for specifying and executing Docker applications to multiple containers. With Docker Compose, you can easily run the entire application stack by executing a single command, streamlining the deployment procedure.
- To start the application using Docker Compose, navigate to the project directory and run the following command: 
1. Select you user group to avoid the permission issues
2. or else Use below command
```bash
        docker-compose up --build 
```
- if you wish to run it with multiple containers, utilize the following command and replace the value of "n" with the desired number of containers you require.
```bash
        docker-compose up --build --scale app=n
```
- To stop the application gracefully, just use CTRL+C in the terminal Docker Compose.This should stop and remove the containers, networks, and volumes created by Docker Compose.
```bash
        docker-compose down --rmi all
```

## Swagger API Documentation
Once the application is up and running, you can use The following Swagger endpoints to test this application:

### Auth User-Admin Controller
This is a User management and authorization controller

#### Admin Registration

```bash
          POST /v1/authorize/registerAdmin
```
#### User Registration [Customer]
```bash
          POST /v1/authorize/registerUser
```

#### Get All Users [Admin Only]
```bash
          GET /v1/authorize/getAllUsers
```

#### Delete User [Admin Only]
```bash
          DELETE /v1/authorize/removeUserById/{userId}
```

### Book Store Controller

#### Add new Book [Admin Only]
```bash
          POST /v1/books/addBook
```

#### Get Book By ID [Login User]
```bash
          GET /v1/books/{id}
```
#### Add new List of Books [Admin Only]
```bash
          POST /v1/books/addBookDetails
```

#### Get All Books [Login User]
```bash
          GET /v1/books/searchBooks
```

### CartController [Login User]

#### Add book to the cart [Login User]
```bash
          POST /v1/cart/addToCart
```

#### remove from cart [Login User]
```bash
          DELETE /v1/cart/{bookId}
```

#### update cart [Login User]
```bash
          PUT /v1/cart/updateCart/{cartItemId}/{bookId}
```

### Checkout Controller [Login User]

#### checkout API [Login User]
```bash
          POST /v1/checkout
```

### ShoppingCart Controller [Login User]

#### Create a new shopping cart [Login User]
```bash
          GET /v1/createCart
```

#### get shopping cart for the current user API [Login User]
```bash
          GET /v1/getCartDetails
```

