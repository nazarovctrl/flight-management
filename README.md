# flight-management


## Requirements

- Java 21
- Spring Boot 3.3.1

## How to run

<details close>
  <summary>
    <h3>
      Docker    
    </h3>
  </summary>

To run the application, ensure that Docker is installed on your machine.
Then, execute the commands in the specified order.

1. **Create flight-management folder in your machine**

2. **Create and copy the .env file, and get docker-compose.yml from GitHub link below and put them into the flight-management folder**

   .env file content

    ```.env
    DB_URL=<your-databse-url>
    DB_USERNAME=<your-databse-username>
    DB_PASSWORD=<your-databse-password>
    SECURITY_TOKEN_ACCESS_SECRET_KEY=<repalce-with-generated-secret-koy-for-access-token>
    SECURITY_TOKEN_ACCESS_TIME=<access-token-valid-time-in-millieseconds>
    SECURITY_TOKEN_REFRESH_SECRET_KEY=<repalce-with-generated-secret-koy-for-refresh-token>
    SECURITY_TOKEN_REFRESH_TIME=<refresht-token-valid-time-in-millieseconds>
    ```
   docker-compose.yml link

   https://github.com/nazarovctrl/flight-management/blob/master/docker-compose.yml

3. **Pull the Docker Image**

    ```sh
   docker pull nazarovv2/flight-management:latest
    ```

4. **Start the Application**

    ```sh
   docker-compose up -d flight-management-app
   ```
5. **Link for the application**

   http://localhost/swagger-ui/index.html#/

</details>

<details close>
  <summary>
    <h3>
      Jar    
    </h3>
  </summary>

1. **Clone the repository:**

    ```sh
    git clone https://github.com/nazarovctrl/flight-management.git
    cd flight-management
    ```
2. **Paste the .env file into flight-management folder**

   .env file content

    ```.env
    DB_URL=<your-databse-url>
    DB_USERNAME=<your-databse-username>
    DB_PASSWORD=<your-databse-password>
    SECURITY_TOKEN_ACCESS_SECRET_KEY=<repalce-with-generated-secret-koy-for-access-token>
    SECURITY_TOKEN_ACCESS_TIME=<access-token-valid-time-in-millieseconds>
    SECURITY_TOKEN_REFRESH_SECRET_KEY=<repalce-with-generated-secret-koy-for-refresh-token>
    SECURITY_TOKEN_REFRESH_TIME=<refresht-token-valid-time-in-millieseconds>
    ```
3. **Build the project:**

   Use Maven to build the project.

    ```sh
    mvn clean install
    ```

4. **Run the application:**

   To run the application, make sure you have Java 21 installed

    ```sh
    java -jar target/flight-management-0.0.1.jar
    ```
5. **Link for the application**

   http://localhost/swagger-ui/index.html#/

</details>
