# Deliverable 3

## Application progress

## Test coverage
According to JaCoCo, our test coverage was at about 70% after the second deliverable. We have worked on improving this and get as close as possible to 100%. Some code branches are hard to get coverage for, since they are only executed in case of an error. We have tried to test these cases as well, but it is not always possible to get 100% coverage.

When using JaCoCo, we learned that just looking at test coverage can be misleading. JaCoCo marks code as "covered" if it is run during the test phase. However, this does not mean that the code is explicitly tested. It might just be run as periphery code during another test. We have tried to avoid this by being aware of it and writing tests that explicitly test all the code we want to test.

## Environments
We have introduced environments in release 3, which are used to isolate the configuration of the application. In total, we have two `.env`-files, one for each environment, which are [`.env.development`](/chore-manager/.env.development) and [`.env.test`](/chore-manager/.env.test). By introducing environments there is no way for the test environment to access the information used in the development environment, and vice versa. This solved a major concern regarding accidentally overwriting our main text file, with our test data, and we needed to be extremely careful to overwrite all file paths in tests. With environments, testing has no knowledge of the existence of the development environment, and therefore cannot overwrite it. Also by doing this, we automated the process of setting the file path, as the file path is defined in the `.env`-files.  

Although it is not recommended to push .env-files to GitLab, and instead share it confidentially within the team. We needed to push it to master, so that the app has the necessary information without needing to contact us when grading. In addition, our .env files do not contain any sensitive information.

## JSON in Java
In release 2, we used `JSON.simple`, which gave us a warning: ![Name of automatic module is unstable](json-simple-warning.png)
Maven did not find the module if we specified an alternate path, therefore we decided to change our JSON library all together. We found [`JSON in Java`](https://mvnrepository.com/artifact/org.json/json) to be a more popular library, while the implementation was relativly similar to `JSON.simple`. By changing to this library, we got rid of the warning, with minimal code changes, this library also provided greater JSON support, which made building the REST API easier. 
We chose `JSON.simple`/`JSON in Java` because we thought that would cover all our use cases, at that time we did not know Spring Boot used Jackson to serialize/deserialize. Because of this, we could not use Spring Boot's automated JSON serialization/deserialization, and instead had to return plain Strings. In retrospect it would be better to utilize Jackson as our JSON library, which was better supported by Spring Boot.

Another change related to JSON, is that each class now has a static encode and decode method. This is used to convert the class to and from JSON, and is used when parsing the file. These methods handle exceptions appropriately. To assist us in converting `Strings` to `JSONObject` and `JSONArray`, we created a new class [`JSONValidator`](/chore-manager/core/src/main/java/core/json/JSONValidator.java).

## Changes in file classes
Previously we only had a `Storage` class, which contained all information about the application. Now we introduced a new class `State`, which holds information about the current state of the application. By that, I mean which person is logged in and the corresponding collective. By introducing this class we better uphold the Single Responsibility Principle. In addition, this made it easier to hide information about other collectives and users, which is important for security reasons. since we do not want to expose information about other users and collectives outside the collective the logged-in user in registered to. 

Since we have extended the functionality of the application, the JSON representation has also changed. Previously we had a list of `Persons`, but now we have a list of `Collectives`. Each `Collective` has a list of `Persons`, which works the same as release 2. There have been some changes in the attributes of each class, which would be to many too list, but some notable ones is: 
- Added collective
- Chores now a UUID
- Persons now have a hashed password
- Persons now have a unique username instead of UUID

The JSON representation of the data is as follows:
```json
[
    {
        "name": "The Almighty Collective",
        "joinCode": "465187",
        "persons": {
            "Christian": {
                "collectiveJoinCode": "465187",
                "password": "39a7067148acfa9987f856e9e996e6ac",
                "chores": [
                    {
                        "timeFrom": "2023-11-13",
                        "creator": "Christian",
                        "timeTo": "2023-11-13",
                        "color": "#FFFFFF",
                        "isWeekly": false,
                        "choreName": "Chore Test",
                        "checked": false,
                        "uuid": "ad1fc218-5a7b-4720-9e53-14c0558a42da",
                        "assignedTo": "Christian",
                        "points": 10,
                        "daysIncompleted": 0
                    }
                ],
                "displayName": "Christian",
                "username": "Christian"
            },
            "Kristoffer": {
                "collectiveJoinCode": "465187",
                "password": "8c137d328b81d693c3e95b24bebd31f4",
                "chores": [],
                "displayName": "Kristoffer",
                "username": "Kristoffer"
            },
            "Lasse": {
                "collectiveJoinCode": "465187",
                "password": "b7afbba14a9e6b51e7a8b6054f9a114c",
                "chores": [],
                "displayName": "Lasse",
                "username": "Lasse"
            },
            "Sebastian": {
                "collectiveJoinCode": "465187",
                "password": "42f749ade7f9e195bf475f37a44cafcb",
                "chores": [],
                "displayName": "Sebastian",
                "username": "Sebastian"
            }
        }
    },
    {
        "name": "Limbo Collective",
        "joinCode": "000000",
        "persons": {}
    },
    {
        "name": "New Collective",
        "joinCode": "793978",
        "persons": {
            "TestUser": {
                "collectiveJoinCode": "793978",
                "password": "b251e8bdeed0c7137bb9d72ece6e1568",
                "chores": [
                    {
                        "timeFrom": "2023-11-14",
                        "creator": "TestUser",
                        "timeTo": "2023-11-14",
                        "color": "#334DB3",
                        "isWeekly": false,
                        "choreName": "Ta ut av oppvaskmaskinen",
                        "checked": false,
                        "uuid": "7902f9f0-a4b2-4115-9a65-b6b04ec95f76",
                        "assignedTo": "TestUser",
                        "points": 60,
                        "daysIncompleted": 0
                    },
                    {
                        "timeFrom": "2023-11-16",
                        "creator": "TestUser",
                        "timeTo": "2023-11-16",
                        "color": "#FFFFFF",
                        "isWeekly": false,
                        "choreName": "Ta ut søpla",
                        "checked": false,
                        "uuid": "50dd48b6-133c-46ad-bbd8-bda8de938c78",
                        "assignedTo": "TestUser",
                        "points": 10,
                        "daysIncompleted": 0
                    }
                ],
                "displayName": "TestUser",
                "username": "TestUser"
            }
        }
    }
]
```

## CI/CD pipelines
In order to conform with best practices, we decided to protect the `master` branch. This means we remove the ability to direct push to the `master` branch, and instead have to go through merge requests. Following this change, we introduced continuous integration and continuous delivery (CI/CD) pipelines. The pipeline is only triggered on merge requests, and it will run certain maven commands. The pipeline will run the following commands:
- `mvn install -DskipTests` - Installs the necessary dependencies.
- `mvn test` - Runs all tests (in core and persistence).

If any of these commands fail, the pipeline will fail, and the merge request will not be allowed to merge. This ensures that the `master` branch is always in a working state, and that no unforeseen bugs are introduced.
`mvn test` does not only run the tests, but also Spotbugs and Checkstyle, and if any of them throw errors, the pipeline will fail. However, the pipeline does not run tests in the UI package as the pipeline fails to run the JavaFX application and render the GUI. Spotbugs and Checkstyle is still checking all packages. To conclude, the pipeline has successfully identified bugs and test-fails in our code and has helped us produce a more stable application and keep master stable.


## REST API
In this release we needed to create a REST API

All UI-views does not use `Storage` or `State` methods/data directly, but instead gets all information through `DataAccess`. 

We have endpoints for each of the CRUD (Create, Read, Update, Delete) operations, where we respectively use HTTP Methods POST, GET, PUT, DELETE. 

### Fetching

Not overfetch


### Sensitive information

Hide information about other users and collectives, we do not want to expose sensitive information
RestricedPerson and RestricedCollective

### Caching


### Other Best practices

#### Using nouns instead of verbs
Our endpoints does not use verbs, like `getChores` or `createChore`, but instead uses nouns, like `chores` and `chores/{uuid}`. This is because the HTTP methods already specify the action, and therefore we do not need to specify it in the endpoint.

#### Logical nesting in endpoints
We have logical nesting in our endpoints, which can be seen with the use of `storage/` and `state/`, which corresponding to the `Storage` and `State` classes. This makes it easier to understand the endpoints, and makes it easier to find the endpoints you are looking for. This means if you want to do something about that is relevant to the currently logged in collective, you would use the `state/` endpoints. An example is `POST: chores/{uuid}` which creates a new chore in the currently logged in collective. As this is relevant to the current collective, we do not use `storage/`. However creating a new person, does not depend on the currently logged in collective (as we have not logged into any collective yet), and therefore we use `storage/` as the endpoint.









## Refactor of `Password` class

We never store the actual password in clear text, we instead store a hash of the password.