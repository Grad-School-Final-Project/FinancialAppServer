# FinancialAppServer
The server side code for the financial web app being created for my final project
test


# Setup Instructions
The entire server side of the application can be launched via docker compose. You just have to configure the .env file
before running the docker compose file. 

## Prerequisites 
- Must be running on a machine with docker and docker compose already installed. 
- Must have cloned this repository to your machine
- Must have internet access to pull the images from DockerHub

## Docker Compose Instructions
- cd into .docker
- copy the .env.example file to .env
- You may change values in the .env file to your liking but the defaults will get you up and running
- Run docker compose up -d --> this will pull the necessary images then start the containers
- Sections below will assume you made no changes to the .env file
- 
## Navigate to Server Landing Page
- Once all the docker containers are up and running navigate to localhost:8080
- You should see the landing page for the server. If you do not something is wrong. 

## Keycloak Configuration
- This section assumes you did not change any of the values in the .env file
- Once the keycloak container is up and running navigate to: localhost:3456
- Login to the admin console using admin and password as the username and password
- Follow the instructions in the keycloak-configuration.pdf to get setup.

