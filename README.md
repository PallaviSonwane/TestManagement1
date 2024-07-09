# TestManagement

This project is an Exam Management System developed using Spring Boot and Hibernate, designed to manage categories, subcategories, and exam questions.

## Features

- **Category Management:**
  - Add, view all, view by ID, update, and delete categories.
  
- **Subcategory Management:**
  - Add, view all, view by ID, update, and delete subcategories.
  
- **Exam Management:**
  - Upload questions from Excel.
  - Add, view all, view by ID, update, and delete exam questions.


## Technologies Used

- Java
- Spring Boot
- Apache POI
- Spring Data JPA
- Lombok
- SLF4J (for logging)

## Getting Started

To run this project locally, follow these steps:

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/pallaviSonwane/testmanagement-api.git
   ```
   ```bash
   cd testmanagement-api
   ```

   
1. **Set up the database:**
   
   Ensure you have PostgreSQL installed locally or configure your database settings in application.properties.

2. **Build and run the application:**
    ```bash
   ./mvnw spring-boot:run

3. **Access the API endpoints:**

  Once the application is running, you can access the API using tools like Postman.


## API Endpoints

 ### Category Management

1. **Add Category:**

    ```bash
    POST /api/category
    ```
    Request Body:
    ```bash
    {
    "categoryName": "Java",
    "categoryDescription": "Core Java category"
    }
    ```
    
2. **View All Categories:**

   ```bash
   GET /api/category

3. **View Category By ID:**

   ```bash
   GET /api/category/4

4. **Delete Category By ID:**

   ```bash
   DELETE /api/category/1

5. **Update Category By ID:**

   ```bash
   PUT /api/category/4


### Subcategory Management

1. **Add Subcategory**

   ```bash
   POST /api/subCategory
   ```
   Request Body:
   ```bash
   {
    "subCategoryName": "Exception Handling",
    "subCategoryDis": "Exception Handling from Java"
    }
   ```
  

2. **View All Subcategory**

   ```bash
   GET /api/subCategory
   ```

3. **View Subcategory By Id**

  ```bash
  GET /api/subCategory/16
  ```

4. **Delete Subcategory By ID:**

  ```bash
  DELETE /api/subCategory/1
  ```

5. **Update Subcategory By ID:**

  ```bash
  PUT /api/subCategory/21
  ```

### Exam Management

1. **Upload Excel Data**

   ```bash
   POST /api/exam/upload
   ```

   ```bash
   {
    "file": "<Excel File>"
   }
   ```

2. **Add Question**

   ```bash
   POST /api/exam
   ```
   
   Request Body:
   ```bash
    {
      "subCategory": {
        "subCategoryID": 16,
        "category": {
          "categoryId": 2,
          "categoryName": "Java",
          "categoryDescription": "Core Java category"
        },
        "subCategoryName": "Physics",
        "subCategoryDis": "Branch of science concerned with the nature and properties of matter and energy"
      },
      "question": "When does Exceptions in Java arises in code sequence?",
      "option1": "Run Time",
      "option2": "Compilation Time",
      "option3": "Can Occur Any Time",
      "option4": " None of the mentioned",
      "ans": "Exceptions in Java are run-time errors.",
      "positiveMark": "1",
      "negativeMark": "0.25"
  }


3. **View All Questions**

   ```bash
   GET /api/exam
   

4. **View Question By ID:**

   ```bash
   GET /api/exam/5
   ```

5. **Delete Question By ID:**

    ```bash
    DELETE /api/exam/4
    ```

6. **Update Question By ID:**

    ```bash
    PUT /api/exam/6
    ```


### Swagger UI

To interact with the API endpoints directly from the documentation, you can use the Swagger UI.

  http://localhost:8080/swagger-ui/index.html


## Logging

SLF4J is used for logging. Logs are available to track requests, errors, and other important information.
