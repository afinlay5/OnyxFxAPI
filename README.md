# OnyxFXAPI
Gradle source code repository for OnyxFxAPI, a RESTful API written in Java with Spring Boot.
This project doubled as an demonstration of the Java10 HTTP APIS for my blog post, http://bit.ly/XXXXXXXXX.
If invalid input is entered the API will return values of "-1" for each endpoint.
Built and tested on SUSE Linux. Hosted @ https://onyxfx-api.herokuapp.com/nbaBasicStatBean .

<p align="center"> <img width="633" height="221" src="https://raw.githubusercontent.com/afinlay5/OnyxFxAPI/master/onyx.png"> </p>
<!-- ![alt text](https://raw.githubusercontent.com/afinlay5/OnyxFX/master/blog.png) -->

# Platform 
- Any platform supporting a Java Virtual Machine/Apache Tomcat.

# Requirements
- Gradle 4.7 
- Java 10+ 

# Endpoints
- GET /nbaBasicStatBean

# Request Parameters
- firstName: First name of the NBA® Player.
- surname: 	 Last name of the NBA® Player.
- season: 	 Year the player played.

# Gradle Tasks
- ./gradlew build - Build Executable Jar.
- ./gradlew bootRun - Run Spring Boot Application.

# Known Problems
- None (05/21/2018).
- <strike>Several. There is plenty of work to be done on this (05/21/2018).</strike>

# Execution Screenshot

Example of "GET /nbaBasicStatBean?firstName="Michael"&surname="Jordan"&season=1988 "
![alt text](https://raw.githubusercontent.com/afinlay5/OnyxFxAPI/master/gradle_run1.png)

Example of "GET /nbaBasicStatBean "
![alt text](https://raw.githubusercontent.com/afinlay5/OnyxFxAPI/master/gradle_run2.png)
