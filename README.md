# About the project
## Project structure
The project is located in [/chore-manager/](/chore-manager/). The project follows the Model-View-Controller (MVC) principle, where the model is located in the [core module](/chore-manager/core/) and the view and controller are located in the [ui module](/chore-manager/ui/). There is also a [persistence module](/chore-manager/persistence) that handles reading and writing to a json file for storage. Documentation and user stories are located in [docs](/docs/). 

## Modules
- **Core:** The [core module](/chore-manager/core/) contains the model of the application. It contains class logic for [chores](/chore-manager/core/src/main/java/core/Data/Chore.java), [users](/chore-manager/core/src/main/java/core/Data/Person.java), [days](/chore-manager/core/src/main/java/core/Data/Day.java) and [weeks](/chore-manager/core/src/main/java/core/Data/Week.java).

- **Persistence:** The [persistence module](/chore-manager/persistence/) contains the logic for reading and writing to a json file for storage. The file is saved in the home folder of the user. (`System.getProperty("user.home")`). \
For Windows users, it is often: `C:\Users\YourName\chore-manager-data.json`\
For Mac users, it is often:
`/Users/YourName/chore-manager-data.json` \
The reason for writing to the home folder is that this ensures that the application does not need special permissions to write data, as the user has appropriate access to their home directory. In addition storing files in a user's home directory makes the application more portable and independent of the specific system or environment.


- **UI:** The [ui module](/chore-manager/ui/) contains the [view](/chore-manager/ui/src/main/java/ui/ViewClasses/) and [controller](/chore-manager/ui/src/main/java/ui/AppController.java) of the application. The fxml and css files for the views are located in [/chore-manager/ui/src/main/resources/ui/](/chore-manager/ui/src/main/resources/ui/).

- **JacocoAggregateReporter:** The [jacocoAggregateReporter module](/chore-manager/jacocoAggregateReporter/) has the required pom.xml configuration for generating an aggregate jacoco report for the project. The report is generated in [/chore-manager/jacocoAggregateReporter/target/site/](/chore-manager/jacocoAggregateReporter/target/site/).

## Project description
A description of the project can be found in [/chore-manager/README.md](/chore-manager/README.md).

## Dependencies
- Java 17.0.8-tem
- Maven 3.9.4

## How to run

First install the necessary dependencies in [chore-manager](/chore-manager/):
```
cd chore-manager/
mvn install 
```
Then run the javafx run command in [chore-manager/ui](/chore-manager/ui/):
```
cd chore-manager/ui/
mvn javafx:run
```

## How to test
First change directory to [chore-manager](/chore-manager/):
```
cd chore-manager/
```
Then run the maven test command:
```
mvn test
```
A jacoco report will then be generated in [chore-manager/jacocoAggregateReporter/target/site](/chore-manager/jacocoAggregateReporter/target/site/).