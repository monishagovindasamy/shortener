# Prerequisites:

1) Java 11

2) Gradle

3) Port 8080 should not be used by any other application while running this application

Note: For creating database tables, liquibase framework is used along with H2 in-memory database.


# To Run the Application:

1) Go to shortener folder from terminal

2) Run command 'gradle bootRun' to start the application

3) Use curl -H "Content-Type: application/json" -X POST -d '{"url":"https://www.google.se/"}' http://localhost:8080/shortUrl/ to create the shortUrl.

4) In order to redirect to long url, access the short url received from the above request in browser.


# To Test the application via Junit:

1) Go to shortener folder from terminal

2) Run 'gradle test'

3) It will show the test results in terminal


# To Run the application using Docker:

1) Go to shortener folder from terminal

2) Run docker-compose up

