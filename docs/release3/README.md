# Deliverable 3

## Application progress

## Test coverage
According to JaCoCo, our test coverage was at about 70% after the second deliverable. We have worked on improving this and get as close as possible to 100%. Some code branches are hard to get coverage for, since they are only executed in case of an error. We have tried to test these cases as well, but it is not always possible to get 100% coverage.

When using JaCoCo, we learned that just looking at test coverage can be misleading. JaCoCo marks code as "covered" if it is run during the test phase. However, this does not mean that the code is explicitly tested. It might just be run as periphery code during another test. We have tried to avoid this by being aware of it and writing tests that explicitly test all the code we want to test.

## Environments / Isolation
We have introduced environments in release 3, which are used to isolate the configuration of the application. In total, we have two `.env`-files, one for each environment, which are [`.env.development`](/chore-manager/.env.development) and [`.env.test`](/chore-manager/.env.test). By introducing environments there is no way for the test environment to access the information used in the development environment, and vice versa. This solved a major concern regarding accidentally overwriting our main text file with our test data, and we needed to be extremely careful to overwrite all file paths in tests. With environments, testing has no knowledge of the existence of the development environment, and therefore cannot overwrite it. Also by doing this, we automated the process of setting the file path, as the file path is defined in the `.env`-files.  

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
In this release we needed to create a REST API. We have tried to follow best practices and industry standard while building it. We have endpoints for each of the CRUD (Create, Read, Update, Delete) operations, where we respectively use the HTTP Methods POST, GET, PUT, DELETE.  
As a result of the REST API, no classes in the UI-package uses `Storage` or `State` methods/data directly, but instead gets all information through `DataAccess`. 

Note:
At the time of writing this documentation, we noticed the REST API is not stateless. This means the API stores information about the authorized user, which should instead be provided on each request. Our `StateController` stores the currently logged in user, which should instead be stored in the frontend. Although our API would conform better with the REST standard if it was stateless, we have decided to leave it as is. This is because it would require a lot of development time and code changes in order to make our REST API stateless, and other issues are more pressing. In addition, it is not a requirement for this course to have a stateless REST API, so we kept the stateful REST API which uses the state as authorization.
It would not be difficult to make our API stateless, but it would take a lot of time, here is a list of changes which would need to be made:
- Move `State` to UI, and store the currently logged in user there.
- Remove all methods in `State` which manipulates data and move them into `Storage`.
- Remove `StateController` and move most methods into `StorageController`.
- Send the username and password on each request.
- Create a method in backend which checks if the username and password finds a match, then use this information to decide if the user is authorized to perform this request or not.
- Rewrite all API tests and some UI tests.
- Now the API would be stateless, as it would not store any information about the client.

#### Format
We have two different controllers and subsequentially two different context paths: `storage/` and `state/`, which correspond to the `Storage` and `State` classes. This makes it easier to understand the endpoints, and makes it easier to find the endpoints you are looking for. This means if you want to do something about that is relevant to the currently logged in collective, you would use the `state/` endpoints. An example is `POST: chores/{uuid}` which creates a new chore in the currently logged in collective. As this is relevant to the current collective, we do not use `storage/`. However creating a new person, does not depend on the currently logged in collective (as we have not logged into any collective yet), and therefore we use `storage/` as the endpoint.

Our endpoints does not use verbs, like `getPerson`, `addPerson` or `movePerson`, but instead uses nouns, like `GET persons/{uuid}`, `POST persons/{uuid}` and `PUT persons/{uuid}`. This is because the HTTP methods already specify the action, and therefore we do not need to specify it in the endpoint.

The format for a request should be as follows:
```
(HTTP-METHOD) baseurl/context-path/path?query-parameters
(YOUR-HEADERS)
```

Here is an example of an API request, which gets your user information:
```
GET /storage%2Fpersons%2FLasse?password=39a7067148acfa9987f856e9e996e6ac
Host: http://localhost:8080
Accept: application/json
Content-Type: application/x-www-form-urlencoded
```

The final URI looks like this:
```
http://localhost:8080/storage%2Fpersons%2FLasse?password=39a7067148acfa9987f856e9e996e6ac
```

Here is the example of the response from the API request above (formatted from a `HttpResponse`-object):
```json
{
    "status":200,
    "Content-Type": "application/json",
    "body": {"collectiveJoinCode":"465187","password":"39a7067148acfa9987f856e9e996e6ac","chores":[],"displayName":"Lasse","username":"Lasse"},
}
```

Some endpoints also require a body to be sent in the request. For example `POST storage/persons/{username}` which adds a user defined in the body.

### Fetching

One thing to be careful about when building an API is to avoid over-fetching. We could in practice, have a method which gets all collectives (which contains all information about persons and chores), then cache it, then never need to perform another GET request (until data is invalidated with a POST, PUT or DELETE request), but this is considered bad practice. If we were to do this, we would fetch information that we would never need, like information about other collectives which you are not a part of. Another massive issue with a solution like that, would be that we would return data that the user should not have access to, like other users' passwords. We instead made sure to make endpoints which returns only the information that is needed, and the user should not get access to any information that is not relevant to them. This is done by having multiple smaller endpoints, instead of one large which returns everything. To give an example `getAllCollectives`-endpoint got turned into `getCollective`-, `getPerson`- and `getChores`-endpoints.

### Caching

Caching is essential in reducing server load and latency. By caching frequently accessed data, we can reduce the time the server has to spend on processing a request. In our API we have used Spring Boot's inbuilt caching implementation, which is applied with the `@Cacheable`-annotation. In addition we can invalidate data with `@CacheEvict`, when we change the corresponding data. Because we are using Spring Boot's implementation in the back-end, it is the server which caches the data, and not the client (server-side caching). 
In practice, our application would benefit better from client-side caching, as most of our endpoints have a relatively low time complexity, and it is the request itself which takes the most time. As server-side caching was supported by Spring Boot, we decided to use it, and as we already had a caching implementation in the back-end, we thought development time was better spent elsewhere than implementing client-side caching.

### Sensitive information

Our API hides sensitive information from the user, unless it is information about the authorized user itself (e.g. when logging in). This is done by creating a restricted class which does not have sensitive attributes (See [`RestrictedPerson`](/chore-manager/core/src/main/java/core/data/RestrictedPerson.java) and [`RestrictedCollective`](/chore-manager/core/src/main/java/core/data/RestrictedPerson.java)). An example is when getting a collective, we return a `RestrictedCollective`-object, which do not have any attributes for persons in the collective, and therefore does not expose sensitive information about them. The same applies for `RestrictedPerson`, which does not have any attributes for password, which means you only get information about the username and display name.  

In addition, all validation and checking is done in the back-end, to not expose any sensitive information to the user. 
For-example when checking trying to log in, we don't call a method which returns all users, then check if the logging information match any of those users. Instead we have a method in the back-end, which takes in the username and password, and checks if it matches any of the users, then returns only that user.

## Refactor of `Password` class

