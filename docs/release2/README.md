# Deliverable 2
## Progress
Checkstyle was implemented, and we used the Google coding conventions from [Google Java Style](https://google.github.io/styleguide/javaguide.html) as a template. We made some small changes. One change being that we doubled the indentation size from 2 to 4. The reason for this is that we are all more used to an indentation size of 4, and that we find the code to be easier to read with the larger indentation. We also increased the allowed abbreviation length from 0 to 4. This is to allow uppercase sequences like "JSON" and "UUID" in method and variable names. Again, this is because we think it improves readability and code clarity. We also changed the import order to match [RedHat's](https://marketplace.visualstudio.com/items?itemName=redhat.java) default setting. The regular expression that matches variable names also had to be changed in order to allow UPPERCASE_WITH_UNDERSCORES for constants. 

## Persistence

The [persistence module](/chore-manager/persistence/) contains the logic for reading and writing to a json file for storage. The file is saved in the home folder of the user. (`System.getProperty("user.home")`). The reason for writing to the home folder is that this ensures that the application does not need special permissions to write the data, as the user has appropriate access to their home directory. In addition storing files in a user's home directory makes the application more portable and independent of the specific system or environment.

Our save model consist of implicit saving, which saves after every action from the user, for example after creation of a chore. This is to ensure that the data is always up to date, and that the user does not have to worry about saving, making it most convenient for users.

We have chosen to use JSON.simple for handling JSON in this project.
The reason is because it's a lightweight and easy-to-use Java library. 
In addition it is a well known library, which means it is well documented and has a lot of resources online.

### Structure progression

We have three classes for handling persistence, [FileHandler](/chore-manager/persistence/src/main/java/persistence/FileHandling/FileHandler.java), [JSONConverter](/chore-manager/persistence/src/main/java/persistence/FileHandling/JSONConverter.java) and [Storage](/chore-manager/persistence/src/main/java/persistence/FileHandling/Storage.java). Each of these classes serve a different purpose.

#### FileHandler: 
This class handles the reading and writing directly to the file. This class uses InputStreams and OutputStreams to be able to read and write. It reads data from the file, then converts it to JSON and returns it. It can also take JSON data and convert it to a string, to then write it to the file.
This class has the lowest level of abstraction in the persistence module.

#### JSONConverter:
This class handles the conversion between the data classes and JSON.simple's JSONArray. It extends FileHandler, and the reason is to make it easier to handle JSON conversion and file reading from the same object with the same initialized variables, which reduces complexity. This class has a higher level of abstraction than FileHandler, but a lower level of abstraction than Storage.

#### Storage:
This class follows the singleton design pattern, which means only a single instance of the class can exist. On startup it reads and parses the JSON file, and stores all information in local variables. Because it stores the information, it does not need to read from the file again. All manipulation of data is done on the local variables, which is saved to the file when the save method is called. This proves to be quite time efficient.

This class has the highest level of abstraction in the persistence module, and is the main class used to handle persistence. This class delegates task down to the other classes with a lower level of abstraction. This class is therefore easy to use, and is the only class that the rest of the application needs to interact with. An example can be the save method, which is done by calling `Storage.getInstance().save()`, which then delegates the task to the other classes. The Storage class is full of easy-to-use methods, which enables us to not worry about the persistence layer when implementing the rest of the application. To ensure `Storage` behaves as expected, we have built a robust testing suite for it.

In all classes that handles JSONArrays, we had to add `@SuppressWarnings("unchecked")`. This is because the JSONArray class is not generic, and is instead a raw type. This means that the compiler cannot check that the JSONArray is of the correct type, and will instead give a warning. We have chosen to suppress this warning, as we are sure that the JSONArray is of the correct type, and there is no way to parameterize it.

#### Progression of persistence implementation
The above text explains the current implementation of persistence, but it has gone through many iterations before reaching this point.

The first iteration: This consisted of only the `FileHandler` class which read and parsed the data on every data fetch. This is quite inefficient and proved to be harder to use, as there were "low-level" methods that the rest of the application had to use.

The second iteration: We understood there was a lot of improvements that could be made, so we expanded with the classes `JSONConverter` and `Storage`. `JSONConverter` works the same as it does now, with it only purpose being converting between JSON and data classes. `Storage` however worked quite differently to what it does now. `Storage` used static variables to save and store data. This allowed us to keep the data consistent across the whole application, however this is not considered best practice, and also introduced a lot of complexity. In addition by using static variables, the tests conflicted with the main applications persistence, and we had to implement further complexity to ensure that the tests did not interfere with the main application. This was not a good solution, and we decided to change it.

Third iteration (Current): By researching, we found that a singleton would prove to be best for our use case. This follows the singleton pattern, which is a well known design pattern and considered a best practice. This means that only one instance of the class can exist, and consequently all data is consistent across the whole application.
This pattern massively reduced complexity, and allowed us to make the `Storage` class easy to use and understand.
