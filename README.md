Build a web-based system that supports the specified functionality:
- on the basis of the entities of the domain, create classes describing them
- classes and methods should have names that reflect their functionality, and be competently structured by package
- the code must conform to the Java Code Convention
- information about the domain should be stored in the database, for access use The JDBC API using a connection pool, standard or self-developed. As a DBMS, it is recommended to use MySQL or Derby;
- the application should support the work with Cyrillic (to be multilingual), including when storing information in the database;
- The application architecture must match the Model-View-Controller;
- when implementing business logic algorithms, use the GoF templates: Factory Method, Command, Builder, Strategy, State, Observer etc.
- using servlets and JSP, implement the functions proposed in the statement Specific task;
- use the JSTL library in JSP pages;
- when developing business logic, use sessions and filters;
- use Log4j;
- the code must contain comments.

System Development team. 

The customer represents the Technical Task (TOR),  which contains the list of Works with the indication of qualifications is listed and the number of specialists required. 
The manager reviews the TOR and prepares the Project, assigning to it unoccupied Developers of the required qualification, after which the project cost is calculated and the Customer is invoiced.
The developer has the opportunity to set the number of hours spent working on the project.


--------------------------------------------
Description of the realized functional:
DB MySQL are used. For access used The JDBC API with standard connection pool.
The system can upload files (for order's specification) and validate parameters from GET and POST http-query (used JS validation and java validation): text fields, checkboxes, selectes, files.
AJAX are used to search projects.
For checking the right for all actions ACLFilter are used.
For sending email used smtp


There are 4 roles in the system.
* Customer has the opportunity to create an order, view order and have a look on all list of orders.
* Manager has the opportunity to have a look on all list of new orders, view order and create a project for order if there were not busy developers for period defined in the order, set project's price. Also he can to view list of projects and view all information about project. He can find a project use the search field.
* Developer has the opportunity to have a look on all project where he takes a part. And also he can to set the number of hours spent working on the project.
* Admin has the opportunity to have a look all employees.


Main technologies: Java 8, tomcat9, maven, mysql, MVC, javascript, ajax, jsp, Taglib, bootstrap, smtp
