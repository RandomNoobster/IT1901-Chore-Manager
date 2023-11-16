# Deliverable 3

## Application progress
**For deliverables 1 and 2, we implemented these user stories:**
- **"Lisa’s mid-mid-life crisis"**
- **Fading plants**
- **Jealousy, jealousy**

### Acceptance criteria for deliverable 3

Instead of creating a new client, we are going to implement more features.

**Complete:**
- ["Scoreboard"](../../UserStory.md)
- ["Popularity contest"](../../UserStory.md)
- ["Chopping down Christmas tree in july?"](../../UserStory.md)
- ["'Do(n't) put off until tomorrow what you can do today' - Benjamin Franklin"](../../UserStory.md)
- ["Spacetime"](../../UserStory.md)
- ["Detective"](../../UserStory.md)


**A user should be able to:**
- Receive the stated amount of points when having done a chore
	- The points will add up, and everyone in the group should be able to see the "scoreboard" (from **Scoreboard**)
- Create a group. Each group has its own calendar view, where the groups members can view their chores (from **Popularity contest**)
- The group should have a unique code, that can be passed around to roommates so that they can join the group when they first sign up (from **Popularity contest**)
- Delete chores (from **Chopping down christmas...**)
- Mark a chore as completed if completed (from **'Do(n't) put off until...**)
- If the chore is not completed within the time frame it is sat to, it should be pushed to the next day and marked as high priority. This could be achieved by changing the styling and marking it in red (from **'Do(n't) put off until...**)
- State how many weeks a chore should repeat in the chore-creation menu (from **Spacetime**)
- Create chores that can be done every day of the week (from **Spacetime**)
- View:
  - name of the chore
  - when the chore is overdue
  - the status of the chore
  - who the chore is assigned to 
  in a menu (from **Detective**)


### How far we got

We completed all the user stories, meaning we managed to finish the application. As we added features, we also realized that some quality of life (QOL) features were missing, such as "Go back" and "Log out" buttons as well as an easy way for members of a collective to spread the "collective code" around. These QOL features combined into the user story creatively named ["Quality of life"](../../UserStory.md).

We now have pages for:
 - Logging in
 - Creating users
 - Joining collectives
 - Creating collectives
 - The calendar
 - Creating chores
 - Viewing info about chores
 - The leaderboard

 |![List of pages](images/listOfPages.png)|
 |:--:|
 |Showcase of all pages|

## Description of the finished product

 |![State diagram](images/stateDiagram.png)|
 |:--:|
 |State diagram for the application|

The figure above shows a state diagram of how the different pages interact. You start at the "Logging in" page. From there you can either log in to an existing user or create a brand new one by clicking "Create user" and filling in the details on the "Creating users" page. 

After creating a user, or logging in to a user that has not yet been assigned to a collective, you will be taken to the "Joining collectives" page. From here you can either choose to join a collective by typing in a code given to you by someone who has created a collective already or create a brand new collective by clicking the "Create collective" button and filling in the name of the collective.

Finally, you will be taken to the main page, "the calendar" page. From here you can log out again, by clicking "Log out", go to the leaderboard for your collective by clicking the "Leaderboard" button, copy your collectives code by clicking the "Code: [collective code]" button, or you can create chores. Create day-chores by clicking "Add" on a specific day, or create week-chores by clicking "Add" next to the number of the week you want to add a week-chore to. If you have created chores already, you can click on the created chores to view more info about them.

[Click here](../../chore-manager/README.md) for a more in depth description of how all scenes interact.

## Diversions from the original plan

 Overall we followed the plan for minimum viable product described in the graphic [presented in the readme of deliverable 1](../release1/README.md) closely, but made some diversions. These diversions were mainly in the name of consistency.

 |![Plan versus finished product](images/planVsReal.png)|
 |:--:|
 |Plan versus finished product|

 |![Plan versus finished product colored](images/planVsRealColored.png)|
 |:--:|
 |Plan versus finished product: here planned views are colored the same as their implemented counterparts|


The "Joining collectives" page and the "Creating collectives" page were initially planned to be just one page. However, as we made the pages for joining and creating collectives, we felt that the idea of creating a user and logging into a user, are ideas that each map to creating a collective and joining a collective. Therefore, since joining and creating a user were on separate pages, creating a collective and joining a collective should also be, for consistency. 

Moving the leaderboard from under the calendar, to its own page, was another diversion from the original plan. This was done to make the calendar page seem less overcrowded with features. The "Viewing info about chores" page was intended to just be a popup where you could mark a chore as done, however, we realised that we needed a way to display extra information, and because of that this also ended up being its own page. **The reasons we were able to add these extra pages were threefold.**

Firstly, we originally planned that the creation of week-chores (chores that can be done over a week), and the creation of day-chores (chores that need to be done on a specific day), should have their own pages. This ended up not being necessary as the only difference between a week-chore and a day-chore, is that day-chores have the same start and end date, while week-chores end-date are 7 days after their start date. Therefore they both got the same creation page. Therefore we saved a bit of time on that. 

Secondly, between deliverables 1 and 2, we created most "page-types" needed for the rest of the application. As visible in the overview of all pages, most pages follow a similar structure. Because of this, when we created new pages, we could use the already implemented pages as starting points. We could copy one of the old fxml-files, edit where needed, and pass it off as a new page. This saved us a lot of time. 

 |![Plan versus finished product colored](images/similarities.png)|
 |:--:|
 |Pages follow a clear structure|

Finally, the framework for going between pages made between deliverables 1 and 2 also saved us a lot of time. Instead of each controller having to define its own method for going from one page to another, which we had done up to that point, we instead followed the "Don't Repeat Yourself" (DRY)principle. We defined a static method in the App class in the UI that does this. We could then call this method from anywhere in the UI, and easily switch between scenes. This saved us a lot of time for this delivery, as we added a lot of buttons that take you from one page to another, such as the "Go back" buttons.

```java
public static void switchScene(String fxmlName) {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlName + ".fxml"));
        Parent parent = fxmlLoader.load();
        scene.setRoot(parent);
    } catch (IOException e) {
        System.out.println(e);
    }
}
```

Another diversion was a switch from weekly, recurring chores, to the ability to state how many weeks a chore should repeat. Weekly tasks were tasks that were going to reoccur every week, until it was deleted by a user. We instead gave the user the ability to choose how many weeks a chore should repeat, as deletion should be reserved for when the user has done something wrong, like spelling a chore wrong or assigning the wrong person, and not be something a user is forced to in able to use the application. 

 |![Repeating input](images/repeating.png)|
 |:--:|
 |The user can decide how many weeks a chore should repeat|
 

## Test coverage
According to JaCoCo, our line coverage was at 77% and our branch coverage was at 55% after the second deliverable. We have worked on improving our coverage as much as possible. However, some code branches are hard to get coverage for, since they are only executed in case of an error. We have tried to test these cases as well, but it is not always possible to get 100% coverage. There is also some code that makes no sense to test, i.e. the `main`-method in [`App`](/chore-manager/ui/src/main/java/ui/App.java) or the `main`-method in [`AppApplication`](/chore-manager/springboot/restserver/src/main/java/springboot/restserver/AppApplication.java). 

|![jaCoCo coverage](images/jaCoCo_coverage_deliverable2.png)|![jaCoCo coverage](images/jaCoCo_coverage_deliverable3.png)|
|:--:|:--:|
|Our test coverage after deliverable 2 as reported by JaCoCo|Our final test coverage as reported by JaCoCo| 

|![jaCoCo coverage](images/jaCoCo_ui_deliverable2.png)|![jaCoCo coverage](images/jaCoCo_ui_deliverable3.png)|
|:--:|:--:|
|Our test coverage in [ui](../../chore-manager/ui/src/main/java/ui/) after deliverable 2 as reported by JaCoCo|Our final test coverage in [ui](../../chore-manager/ui/src/main/java/ui/) as reported by JaCoCo| 

Even though our line coverage only increased from 77% to 90%, the amount of code (both lines and branches) in the project has increased by a lot. The total amount of lines and branches has more than doubled since deliverable 2. The amount of lines we test increased from 1849 -> 5875 (217% increase) and the amount of branches we test increased from 66 -> 271 (310% increase). We also test the UI a lot more thoroughly now. Previously our tests did not have very good branch coverage (40%), but now we have reached 72%. This reflects our renewed focus on ensuring that the application has a proper test suite, as well as a more test-driven approach to development.

When using JaCoCo, we learned that just looking at test coverage can be misleading. JaCoCo marks code as "covered" if it is run during the test phase. However, this does not mean that the code is explicitly tested. It might just be run as periphery code during another test. We have tried to avoid this issue by (1) being aware of it and (2) writing tests to explicitly test all the code we want to test. 

In accordance to the DRY (Don't repeat yourself) principle, we have used a `BaseTestClass` which all our other tests extend. This decision was made in order to avoid having a lot of boilerplate code in all of our test classes. Amongst other things, this includes methods like `setTestEnvironment` that sets the test environment, and methods such as `deleteFile` or `clearItems` that resets the state of the application between tests. This base class was especially useful for ui tests, since these tests also include boilerplate for handling the view as well as creating test persons and test collectives.

An issue we ran into when extending from the [base class in the ui module](../../chore-manager/ui/src/test/java/ui/BaseTestClass.java), was when we wanted to override the `setup` method. Since `setup` was used in static methods (decorated with `@BeforeAll`) it also had to be static - and we cannot override static methods. A possible solution to this was by adding `@TestInstance(Lifecycle.PER_CLASS)` to the base class. This meant that `@BeforeAll`-methods did not have to be static and could be overridden. We did not do this, the main reason being that the state of the test class would persist between tests. Our tests have been made with the assumption that the state resets between tests, meaning we would see unexpected behavior if we were to suddenly change this. The solution we went for was instead to change the extending class so it did not have to be override the `setup`-method.