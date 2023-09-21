# About the project
## Project structure
The project is located in [/chore-manager/](/chore-manager/). The project follows the Model-View-Controller (MVC) principle, where the model is located in the [core module](/chore-manager/core/) and the view and controller are located in the [ui module](/chore-manager/ui/). Documentation and user stories are located in [docs](/docs/).

## Project description
A description of the project can be found in [/chore-manager/README.md](/chore-manager/README.md).

## Dependencies
- Java 17.0.8-tem
- Maven 3.9.4

## How to run
To run project: 

First install the necessary dependencies in [chore-manager](/chore-manager/):
```
cd chore-manager/
mvn install -DskipTests 
```

EITHER: 
Run this command in the root folder:
```
mvn javafx:run -f chore-manager/ui/pom.xml
```

OR:

- Change directory to [chore-manager/ui](/chore-manager/ui/)
- Run javafx run command
```
cd chore-manager/ui/
mvn javafx:run
```

## To test project:
First change directory to [chore-manager](/chore-manager/):
```
cd chore-manager/
```
Then run the maven test command:
```
mvn test
```
A jacoco report will then be generated in [chore-manager/jacocoAggregateReporter/target/site](/chore-manager/jacocoAggregateReporter/target/site/)