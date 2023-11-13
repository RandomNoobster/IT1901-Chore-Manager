# Deliverable 3

## Application progress
**For deliverable 1 and 2, we implemented these user stories:**
- **"Lisa’s mid-mid-life crisis"**
- **Fading plants**
- **Jealousy, jealousy**

### Acceptance criteria for deliverable 3

**Complete:**
- ["Scoreboard"](../../UserStory.md)
- ["Popularity contest"](../../UserStory.md)
- ["Chopping down Christmas tree in july?"](../../UserStory.md)
- ["'Do(n't) put off until tomorrow what you can do today' - Benjamin Franklin"](../../UserStory.md)

**A user should be able to:**
- Receive stated amount of points when having done a chore
	- The points will add up, and everyone in the group should be able to see the "scoreboard" (from **Scoreboard**)
- Create a group. Each group has its own calendar view, where the groups members can view their tasks (from **Popularity contest**)
- The group should have a unique code, that can be passed around to roommates so that they can join the group when they first sign up. (from **Popularity contest**)
- Delete nonrecurring and recurring tasks (from **Chopping down christmas...**)
- Mark a chore as completed if completed (from **'Do(n't) put off until...**)
- If the chore is not completed within the time frame it is sat to, it should be pushed to the nest day and marked as high priority. This could be achieved by changing the styling and marking it in red (from **'Do(n't) put off until...**)

### How far we got

We completed all the user stories, meaning we managed to finish the application. As we added features, we also realised that some quality of life (QOL) features were missing, such as "Go back" and "Log out" buttons as well as an easy way for members of a collective to spread the "collective code" around. These QOL features combined into the user story creatively named ["Quality of life"](../../UserStory.md).

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
 |State diagram|

The figure above shows a state diagram of how the different pages interact. You start at the "Logging in" page. From there you can either log in to an existing user, or create a brand new one by clicking "Create user" and filling in the details on the "Creating users" page. 

After creating a user, or logging in to a user that has not yet been assigned to a collective, you will be taken to the "Joining collectives" page. From here you can either choose to join a collective by typing in a code given to you by someone who has created a collective already, or create a brand new collective by clicking the "Create collective" button and filling in the name of the collective.

Finally you will be taken to the main page, "the calendar" page. From here you can log out again, by clicking "Log out", go to the leaderboard for your collective by clicking the "Leaderboard" button, copy your collectives code by clicking the "Code: [collective code]" button, or you can create chores. Create day-chores by clicking "Add" on a specific day, or create week-chores by clicking "Add" next to the number of the week you want to add a week-chore to. If you have created chores already, you can click on the created chores to view more info about them.

## Diversion from the original plan

 Overall we followed the plan for minimum viable product described in the graphic [presented in the readme of deliverable 1](../release1/README.md) closely, but made some diversions.

 |![Plan versus finished product](images/planVsReal.png)|
 |:--:|
 |Plan versus finished product|


Originally we planned that the creation of week-chores (chores that can be done over the course of a week), and the creation of day-chores (chores that need to be done on a specific day), each should have their own pages. This ended up not being nesessary as the only differences between a week-chore and a day-chore, is that day-chores have the same start and end date, while week-chores end-date are 7 days after their start date. Therefore they both got the same creation page. 

The "Joining collectives" page and the "Creating collectives" page were initially planned to be just one page. However, joining and creating collectives are ideas that can map to logging in and creating a user

We ended up splitting them to be more consistent with the logging into, 
 such as the "Creating users" and "Creating chores"
consistent with the "Logging in" and "Creating users" pages

Moving the leaderboard from under the calendar, to its own page, was another diversion from the original plan. This was done to make the calendar page seem less overcrowded with features. 

 |![Plan versus finished product colored](images/planVsRealColored.png)|
 |:--:|
 |The planned views are colored the same as their implemented counterparts|


 

## Test coverage
According to JaCoCo, our test coverage was at about 70% after the second deliverable. We have worked on improving this and get as close as possible to 100%. Some code branches are hard to get coverage for, since they are only executed in case of an error. We have tried to test these cases as well, but it is not always possible to get 100% coverage.

When using JaCoCo, we learned that just looking at test coverage can be misleading. JaCoCo marks code as "covered" if it is run during the test phase. However, this does not mean that the code is explicitly tested. It might just be run as periphery code during another test. We have tried to avoid this by being aware of it and writing tests that explicitly test all the code we want to test.