# Superior Goal
The superior goal of the application is to make living alone and with other people easier. The application aims to deliver an easy way to keep track of different chores that should be done in and around the house. Through and easy to navigate and beautiful user interface the app should let users add and delete chores, as well as specify wether a chore should be recurring or not. Each user should also have a clear overview of who should do what chores at which time.

# Superior User Story Statement:
"As a Chore Manager app user I want to be able to schedule chores that should be done in and around the house. I want to have a full overview of what chores is done by who and at what time. This should ensure that everyone do what the are supposed to do and the house should be more or less in order at all times."  

# Substories
## Lisa’s mid-mid-life crisis

Lisa is an unstructured person living with others. She needs a tool to help remind her which day she has planned to do a chore.

### Requirements:
 **Users should be able to**
- Add a chore to a specific date
- See upcoming and past chores
  

## Fading plants

*This task requires “Lisa’s mid-mid-life crisis” to be implemented first.*

After a couple of weeks, the variety of tasks start to overwhelm Lisa. Was she supposed to water the plants on Monday, or carry out the trash? To solve this issue, Lisa requests a way to differ between the different tasks. For example, instead of just having a generic reminder on Monday, she wants a reminder that reminds her to specifically water the plants.

### Requirements:
**Users should be able to**
- When a chore is created, a new scene/menu should pop up where you can choose 
	- Name
	- When the task should be done
  

## “Do(n't) put off until tomorrow what you can do today” ― Benjamin Franklin

*This task requires “Lisa’s mid-mid-life crisis” to be implemented first.*

Even with an application to structure her busy life, she manages to fail at doing tasks some days. Trash is left at the door, dishes left dirty, resulting in angry roommates. Consequently, she wants unfinished tasks to be pushed to the next day. Preferably it should be visually apparent that these tasks should have been done the day before, to add that extra stress element.

### Requirements:
**Users should be able to**
- Mark a chore as completed if completed.
- If the chore is not completed within the time frame it is sat to, it should be pushed to the nest day and marked as high priority. This could be achieved by changing the styling and marking it in red.


## Jealousy, jealousy

As Lisa starts to gain more structure in her life, her roomies look at her with jealousy. They also want to join in on this fantastic application. As a result, Lisa needs a way to give her roomies access to the application so that they also add their chores too. Because of this, each chore needs a label that states who the task is assigned for, so that, for example, Lisa doesn't start breaking into the roomies' dorms to water their plants.

### Requirements:
**Users should be able to**
- Create an account by choosing a username and password. This should be done when opening the application the first time.
- Be able to log into their account when the application is opened.
- Be able to log out of their account
- Assign chores to themselves, as well to other users. This should be done in the chore-creation menu. If the user does not specify a person, it should be set to everyone by default
- Add a label to each chore in the view that states who the task is assigned to


## Popularity contest

*This task requires “Jealousy, jealousy” to be implemented first.*

More and more people around the campus are starting to notice how clean Lisa and her roomies' apartment is. Lisa gathers a crowd at campus, and tells the spectators about the application, and as such, the onlookers request a way to add their collectives to the system as well.


### Requirements:
**Users should be able to**
- Create a group. Each group has its own calendar view, where the groups members can view their tasks.
- The group should have a unique code, that can be passed around to roommates so that they can join the group when they first sign up

## Chopping down Christmas tree in july?
To remind her to chop down a tree before Christmas, Lisa added a chore for that exact occasion, but she accidentally made it recurring. Now its july, and every monday she wakes up in horror to a chore named "Chop chop time" in her calendar. Therefore she wants to be able to delete both recurring, and non-recurring tasks to stop this madness from lasting any longer.

### Requirements:
**Users should be able to**
- Delete nonrecurring and recurring tasks


## Scoreboard
"I do more chores a day than u do a week!" Lisa yelled at her roommate, who stood there, unimpressed by Lisa's unsubstantiated claim. To satisfy her need for proof, Lisa requires an additional feature to be added to the application: a way to keep score over how much work each person puts into their chores. Since taking out the trash is easier than cleaning the whole house, different tasks need to be valued differently. It should also be a reminder to the single individual wether they are doing "too little" or "too much".

### Requirements:
**Users should be able to**
- Specify how many points a chore is worth in the chore-creation-menu
- Receive said amount of points when having done a chore
	- The points will add up, and everyone in the group should be able to see the "scoreboard".
