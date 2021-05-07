Complete the steps listed below to run the simulator:

Run neo4j: docker run --publish=7474:7474 --publish=7687:7687 neo4j:4.0.8 and go to http://localhost:7474/ or http://IP:7474/ if you are using docker toolbox where IP is the one assigned to you in docker toolbox. You will be prompted to enter a user name and password. Enter neo4j for both the username and password. You will then be prompted to change the password. Change the password to mts.
clock: Run the ClockServer
simulator: mvn spring-boot:run
traffic-lights: mvn spring-boot:run
home-simulator: mvn spring-boot:run
work-simulator: mvn spring-boot:run
web: mvn spring-boot:run
drivers-web: mvn spring-boot:run or drivers: mvn compile astra:deploy
management: mvn spring-boot:run
    
The simulator should now be up and running.