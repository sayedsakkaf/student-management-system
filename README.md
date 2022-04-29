# Student Management System (Back-end)
> By Sayed Sakkaf

Test coverage (src/main/java): 64%

* [Kanban board](https://sayedsakkaf.atlassian.net/jira/software/projects/SMS/boards/4)

## Introduction

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Applications used:

Below is a list of applications that I had used to complete my project and are also prerequisites for running the project on your system


* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Tools Suite](https://spring.io/tools) - Back-end Development
* [MySQL](https://dev.mysql.com/downloads/) - Database Management
* [Postman](https://www.postman.com/downloads/) - API Testing
* Java/ SpringBoot - Lanuage and method used for back-end development



### Installing:

To get this project up and running on your system, you will need to clone this repository down to your system. 
<img width="383" alt="Screenshot 2022-04-29 at 15 05 37" src="https://user-images.githubusercontent.com/100779457/165960445-7404c4b5-3a94-4d50-b969-b2f257b0761d.png">

This can be done by clicking on the code option on the Github repository, copying the link shown and using git clone to clone it down to your system. 



* Using an IDE (Spring Tools Suite was used for creation, Eclipse can also be used), open the folder where this repository was cloned. 
* From there click File, then click Open Project From File System, and in the interface that pops up, select the folder where the repository was cloned to. 
* The project should then open in your IDE.

<img width="449" alt="Screenshot 2022-04-29 at 14 57 04" src="https://user-images.githubusercontent.com/100779457/165959708-50828a11-9537-41f6-9ccf-0a23d5849108.png">

To start the application, click on the project in the boot dashboard and start the project using the start button. This will start an instance of the application on localhost:8080.

## Running the tests

To run the tests, right click on the project within the IDE file explorer and hover over Run As. From there you will be given an option to run as JUnit test. To see test coverage, install the [EclEmma Java Code Coverage](https://marketplace.eclipse.org/content/eclemma-java-code-coverage) plugin for your IDE. You will then be able to see a Coverage as option when right clicking on the project file. From there, select Run as JUnit test and you will be show the coverage percentage in the bottom part of the IDE. 

### Unit Tests 

Unlike integration testing, unit testing only testings individual units of source code. An example of unit testing the getCourse function in this project can be seen below: 

<img width="688" alt="Screenshot 2022-04-29 at 15 25 00" src="https://user-images.githubusercontent.com/100779457/165963889-67c671c3-3877-407c-9788-46b8a6743de4.png">



### Integration Tests 
An integration test is a type of testing where different software modules are integrated logically and tested as a group. Essentially testing the communication between the different parts of the project. An example of integration testing the getAllStudents function can be seen below:


<img width="737" alt="Screenshot 2022-04-29 at 15 07 54" src="https://user-images.githubusercontent.com/100779457/165963511-c1bf0bcf-47aa-40a3-afdb-8d02cf724b69.png">


## Deployment

A jar file is included which can be deployed from the command line. To start this, open a GitBash terminal and run the jar file using the following command:

<img width="535" alt="Screenshot 2022-04-29 at 15 31 05" src="https://user-images.githubusercontent.com/100779457/165965594-14b6f49d-0cd9-47b5-a865-689e68fc85a8.png">

This will start the SpringBoot API on port localhost:8080. 

<img width="1576" alt="Screenshot 2022-04-29 at 15 31 35" src="https://user-images.githubusercontent.com/100779457/165965616-6dc09d7f-3d9e-41fd-a9d5-6b302eab6d2b.png">

This can then be linked to a front-end which uses JavaScript to connect to localhost:8080 and implements the CRUD functionality from this project. 

## Authors

* **Sayed** - *Project Development* - [sayedsakkaf](https://github.com/sayedsakkaf)


## Acknowledgments

* Special thanks the the 22FebEnable3 cohort for peer support
* Special thanks to Morgan Walsh - Trainer

