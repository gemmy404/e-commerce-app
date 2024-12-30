# E-Commerce Application

## Overview
E-Commerce Application is a backend system designed to facilitate seamless online shopping experiences. It supports user registration, authentication, and personalized features such as shopping carts, wishlists, and order management. The platform differentiates between **Admin** and **User** roles, with Admins having advanced privileges like managing products. Security is ensured using **JWT tokens**.

---

## Features

### **E-Commerce Functionalities**
- **Homepage Browsing:** Users can explore a variety of products.
- **Shopping Cart:** Add, view, and remove items in the cart.
- **Wishlist:** Save products for future consideration.
- **Order Review:**
    - View order details: `orderedAt`, `deliveredAt`, and `state` (Ordered/Delivered).

### **User Management**
- **User Registration:** Simple and user-friendly account creation.
- **Login:** Secure authentication using JWT tokens.
- **Forgotten Password:** Password recovery via OTP sent to the user’s email.

### **Role-Based Access**
1. **Admin:**
    - Manage products: Add, update, and delete products.
    - Access all user privileges.
2. **User:**
    - Browse and view products on the homepage.
    - Manage shopping cart and wishlist.
    - Place orders and view detailed order information.

---

## Technologies Used

- **Spring Boot 3 & Spring Security 6**
- **JWT Authentication & Authorization**
- **Spring Data JPA**
- **MySQL Database**
- **Liquibase**
- **MapStruct**
- **Lombok**
- **JSR-303 & Spring Validation**
- **OpenAPI & Swagger UI Documentation**
- **Tomcat Server**
- **IntelliJ IDEA**
