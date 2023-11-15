# Chore Manager

[<img src="https://eclipse.dev/che/docs/_/img/icon-eclipse-che.svg" width="15" /> Open in Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2316/gr2316?new)
## How to use Eclipse Che
First ensure you have logged into [Eclipse Che Dashboard](https://che.stud.ntnu.no/dashboard/#/workspaces) with your NTNU account.
Then make sure you have a linked access token between GitLab and Eclipse Che. If you do not, you can watch this [video](https://ntnu.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?pid=1bcdc898-00f3-4a03-9e44-b08f00fd818d) from George. 

Now you can open Eclipse Che with the link above. Then you must follow the steps in "How to run" to get the application running. Remember to install the recommended extensions. To view the UI, you must open the endpoint `6080-tcp-desktop-ui` located in the bottom left corner of VSCode. Do that by copying the URL by clicking the copy button and pasting it in a new tab.
![eclipse-che-endpoint image](/img/eclipse-che-endpoint.png)

# About the project
## Project structure
The project is located in [/chore-manager/](/chore-manager/). The project follows the Model-View-Controller (MVC) principle, where the model is located in the [core module](/chore-manager/core/) and the view and controller are located in the [ui module](/chore-manager/ui/). There is also a [persistence module](/chore-manager/persistence) that handles reading and writing to a json file for storage. Documentation and user stories are located in [docs](/docs/).

## Modules
- **Core:** The [core module](/chore-manager/core/) contains the model of the application. It contains class logic for [chores](/chore-manager/core/src/main/java/core/data/Chore.java), [users](/chore-manager/core/src/main/java/core/data/Person.java), [days](/chore-manager/core/src/main/java/core/data/Day.java) and [weeks](/chore-manager/core/src/main/java/core/data/Week.java).

- **Persistence:** The [persistence module](/chore-manager/persistence/) contains the logic for reading and writing to a json file for storage. The file is saved in the home folder of the user. (`System.getProperty("user.home")`). \
For Windows users, it is often: `C:\Users\YourName\chore-manager-data.json`\
For Mac users, it is often:
`/Users/YourName/chore-manager-data.json` \
The reason for writing to the home folder is that this ensures that the application does not need special permissions to write data, as the user has appropriate access to their home directory. In addition storing files in a user's home directory makes the application more portable and independent of the specific system or environment.

- **UI:** The [ui module](/chore-manager/ui/) contains the [view](/chore-manager/ui/src/main/java/ui/viewClasses/) and [controller](/chore-manager/ui/src/main/java/ui/AppController.java) of the application. The fxml and css files for the views are located in [/chore-manager/ui/src/main/resources/ui/](/chore-manager/ui/src/main/resources/ui/).

- **JacocoAggregateReporter:** The [jacocoAggregateReporter module](/chore-manager/jacocoAggregateReporter/) has the required pom.xml configuration for generating an aggregate Jacoco report for the project. The report is generated in [/chore-manager/jacocoAggregateReporter/target/site/](/chore-manager/jacocoAggregateReporter/target/site/).

## Project description
A description of the project can be found in [/chore-manager/README.md](/chore-manager/README.md).

## Dependencies
- Java 17.0.8-tem
- Maven 3.9.4

## How to run
First install the necessary dependencies in [chore-manager](/chore-manager/):

```shell
cd chore-manager/
mvn install -DskipTests
```

Then you will need to start the REST API in [chore-manager/springboot/restserver](/chore-manager/springboot/restserver/):
Navigate to `chore-manager/springboot/restserver`:

```shell
cd springboot/restserver
mvn spring-boot:run
```

Then you need to open a new terminal window, while the API is running.
Then navigate to [chore-manager/ui](/chore-manager/ui/) in your new terminal, and run the following command:
```shell
cd chore-manager/ui/
mvn javafx:run
```

You will be presented with a login page. Please create a user in the application by clicking the "Create user" button. Then you can log in with the user you just created.

## How to view the javadocs
First change directory to [chore-manager](/chore-manager/):

```shell
cd chore-manager/
```

Then run the maven javadoc command:

```shell
mvn javadoc:aggregate
```

Then open the [/chore-manager/target/site/apidocs/index.html](/chore-manager/target/site/apidocs/index.html) file.


## How to test
First change directory to [chore-manager](/chore-manager/):

```shell
cd chore-manager/
```

Then run the maven test command:

```shell
mvn test
```

A Jacoco report will then be generated in [chore-manager/jacocoAggregateReporter/target/site](/chore-manager/jacocoAggregateReporter/target/site/).

A Checkstyle report will be generated during the first maven phase, `validate`, meaning it will be generated if you run `mvn test`. If you want to generate a Spotbugs report as well, you can run the following command:

```shell
mvn verify
```

The `mvn verify` command will generate a Checkstyle report, Jacoco report and a Spotbugs report. The Checkstyle reports are located in [persistence](/chore-manager/persistence/target/checkstyle-result.xml), [core](/chore-manager/core/target/checkstyle-result.xml) and [ui](/chore-manager/ui/target/checkstyle-result.xml). The Spotbugs reports are located in [persistence](/chore-manager/persistence/target/spotbugs.html), [core](/chore-manager/core/target/spotbugs.html) and [ui](/chore-manager/ui/target/spotbugs.html).



## How to make shippable

```shell
cd chore-manager/
```

Then run this command:

```shell
mvn javafx:jlink -f ./ui/pom.xml
```

This will generate a jlink image of the project located in [ui](/chore-manager/ui/target/chore-manager). Inside the chore-manager folder [ui](/chore-manager/ui/target/chore-manager) there will be generated four folders; bin, conf, legal lib, including a text file called release. While the project could be run from the chore-manager.exe script located in [ui](/chore-manager/ui/target/chore-manager/bin), this isn't very user-friendly and that's where jpackage comes into the picture.


```shell
mvn jpackage:jpackage -f ./ui/pom.xml
```

Depending on the operating system you are running this command will create a bundle Java applications along with a Java runtime into native platform-specific packages and installers. Again depending on your OS, you will then proceed normally like downloading any other application. This executable will be placed in [ui](/chore-manager/ui/target/dist).

For the application to work properly you must also make sure to run the API in the background. Like any other application, our application too needs a backend server. Instructions to run the API can be found above. 