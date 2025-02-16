# Invoice Generator

A full-stack web application to create, manage, and generate invoices. This project uses React for the frontend, Spring Boot for the backend, and PostgreSQL as the database.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
  - [Backend Setup](#backend-setup)
  - [Frontend Setup](#frontend-setup)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

## Overview

The Invoice Generator is designed to provide a seamless way to create and manage invoices with an intuitive interface and robust backend services. It ensures data persistence and scalability using a modern tech stack.

## Features

- **Invoice Management:** Create, view, update, and delete invoices.
- **Client & Product Management:** Manage client and product details.
- **PDF Export:** Generate and export invoices in PDF format.
- **User Authentication:** Secure access with user authentication and authorization.
- **Responsive Design:** Optimized for both desktop and mobile viewing.

## Tech Stack

- **Frontend:** React, with optional libraries like Redux and Material-UI/Bootstrap.
- **Backend:** Spring Boot, Spring Security, Spring Data JPA.
- **Database:** PostgreSQL.
- **Build Tools:** Maven/Gradle for backend, npm/yarn for frontend.

## Prerequisites

- **Java:** JDK 11 or higher.
- **Node.js:** Version 12 or above.
- **PostgreSQL:** Version 10 or above.
- **Build Tools:** Maven or Gradle (for Spring Boot), npm or yarn (for React).

## Installation

### Backend Setup

1. **Clone the repository:**
    ```bash
    git clone git@github.com:Sai-Deepak-1/invoice-gen.git
    cd invoice-generator/backend
    ```

2. **Configure PostgreSQL:**
   - Create a PostgreSQL database (e.g., `invoice_db`).
   - Update `src/main/resources/application.properties` with your database credentials:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/invoice_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    ```

3. **Run the backend server:**
    - If using Maven:
      ```bash
      ./mvnw spring-boot:run
      ```
    - If using Gradle:
      ```bash
      ./gradlew bootRun
      ```

### Frontend Setup

1. **Navigate to the frontend directory:**
    ```bash
    cd ../frontend
    ```

2. **Install dependencies:**
    ```bash
    npm install
    ```
    or, if you prefer yarn:
    ```bash
    yarn install
    ```

3. **Start the development server:**
    ```bash
    npm start
    ```
    or with yarn:
    ```bash
    yarn start
    ```

## Usage

With both the backend and frontend running, open your browser and navigate to [http://localhost:3000](http://localhost:3000) to access the application. You can now create, manage, and generate invoices through the web interface.

## API Endpoints

The backend exposes RESTful endpoints. Below are some examples:

- **Invoices**
  - `GET /api/invoices` - Retrieve all invoices.
  - `POST /api/invoices` - Create a new invoice.
  - `GET /api/invoices/{id}` - Retrieve invoice details by ID.
  - `PUT /api/invoices/{id}` - Update an existing invoice.
  - `DELETE /api/invoices/{id}` - Delete an invoice.

- **Authentication**
  - `POST /api/auth/login` - User login.
  - `POST /api/auth/register` - User registration.

*Note: Ensure that you secure these endpoints using proper authentication middleware as needed.*

## Contributing

Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new feature branch.
3. Commit your changes.
4. Push your branch and create a pull request.

For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the [MIT License](LICENSE).

