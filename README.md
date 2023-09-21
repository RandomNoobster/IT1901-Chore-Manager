For a more detailed overview of how you should develop and what convensions this project uses, please see [DEVELOPER_GUIDE.md](/DEVELOPER_GUIDE.md).

To run project: 

First install the necessary dependencies in [chore-manager](/chore-manager/):
```
cd chore-manager/
mvn install -DskipTests 
```

EITHER: \
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

To test project:
First change directory to [chore-manager/](/chore-manager/):
```
cd chore-manager/
```
Then run the maven test command:
```
mvn test
```
A jacoco report will then be generated in [chore-manager/jacocoAggregateReporter/target/site](/chore-manager/jacocoAggregateReporter/target/site/)