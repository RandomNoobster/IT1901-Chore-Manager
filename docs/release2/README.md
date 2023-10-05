# Deliverable 2

## Progress

Checkstyle was implemented, and we used the Google coding conventions from [Google Java Style](https://google.github.io/styleguide/javaguide.html) as a template. We made some small changes. One change being that we doubled the indentation size from 2 to 4. The reason for this is that we are all more used to an indentation size of 4, and that we find the code to be easier to read with the larger indentation. We also increased the allowed abbreviation length from 0 to 4. This is to allow uppercase sequences like "JSON" and "UUID" in method and variable names. Again, this is because we think it improves readability and code clarity. We also changed the import order to match [RedHat's](https://marketplace.visualstudio.com/items?itemName=redhat.java) default setting. The regular expression that matches variable names also had to be changed in order to allow UPPERCASE_WITH_UNDERSCORES for constants.

Javadoc comments was added to most of the code. This will make it easier for everyone to understand what each class and method does. PlantUML diagrams were added to the javadocs through the [UMLDoclet](https://github.com/talsma-ict/umldoclet) plugin. The diagrams are generated from the javadoc comments in the code. See [this file](/README.md) for how to view the javadocs and the diagrams.

TODO: Persistence was put into it's own module.

TODO: Updates to the UI.

TODO: New unit tests.
