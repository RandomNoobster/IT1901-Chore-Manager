# Superior Goal for the Application
The superior goal of the application is to make living alone and with other people easier. The application aims to deliver an easy way to keep track of different chores that should be done in and around the house. Through an easy to navigate user interface, the app should let users add and delete chores. Each user should also have a clear overview of who should do what chores at which time.

# Superior User Story:
Peter is a 21 year old in his second year of his Master of Science (MSc) studies in Trondheim at Norwegian University of Science and Technology (NTNU). 

He lives in a collective along with other roomates. They struggle to communicate who should do what chores, and when they should be done. Therefore he requests an application that can help him coordinate with his roomates. He requests: 

> "As a Chore Manager app user I want to be able to schedule chores that should be done in and around the house. I want   to have a full overview of what chores is done by who and at what time. This should ensure that everyone do what the are supposed to do and the house should be more or less in order at all times."  


# Substories

## About user-story character
Lisa is 19 years old, and recently moved to Trondheim for her studies at NTNU. She lives with a couple of people in a collective. The following user stories will mainly follow Lisa and her roomates. 


## Lisa’s mid-mid-life crisis

Lisa is an unstructured person. She needs a tool to help remind her which day she has planned to do a chore.

### Requirements:
 **Users should be able to**
- Add a chore to a specific date
- See upcoming and past chores
  

## Fading plants

*This task requires “Lisa’s mid-mid-life crisis” to be implemented first.*

After a couple of weeks, the variety of chores start to overwhelm Lisa. Was she supposed to water the plants on Monday, or carry out the trash? To solve this issue, Lisa requests a way to differ between the different chores. For example, instead of just having a generic reminder on Monday, she wants a reminder that reminds her to specifically water the plants.

### Requirements:
**Users should be able to**
- Choose 
	- Name
	- When the chore should be done
	- Color of the chore
  in a chore-creation menu when creating a chore
  

## “Do(n't) put off until tomorrow what you can do today” ― Benjamin Franklin

*This task requires “Lisa’s mid-mid-life crisis” to be implemented first.*

Even with an application to structure her busy life, she manages to fail at doing chores some days. Trash is left at the door, dishes left dirty, resulting in angry roommates. Consequently, she wants unfinished chores to be pushed to the next day. Preferably it should be visually apparent that these chores should have been done the day before, to add that extra stress element.

### Requirements:
**Users should be able to**
- Mark a chore as completed if completed.
- If the chore is not completed within the time frame it is sat to, it should be pushed to the next day and marked as high priority. This could be achieved by changing the styling and marking it red.


## Jealousy, jealousy

As Lisa starts to gain more structure in her life, her roomates look at her with jealousy. They also want to join in on this fantastic application. As a result, Lisa needs a way to give her roomates access to the application so that they also add their chores too. Because of this, each chore needs a label that states who the chore is assigned to, so that, for example, Lisa doesn't start breaking into the roomates' dorms to water their plants.

### Requirements:
**Users should be able to**
- Create an account by choosing a username and password. This should be done when opening the application the first time.
- Be able to log into their account when the application is opened.
- Be able to log out of their account.
- Assign chores to themselves, as well to other users. This should be done in the chore-creation menu.
- Add a label to each chore in the view that states who the chore is assigned to.


## Popularity contest

*This task requires “Jealousy, jealousy” to be implemented first.*

More and more people around the campus are starting to notice how clean Lisa and her roomates' apartment is. Lisa gathers a crowd at campus, and tells the spectators about the application, and as such, the onlookers request a way to add their collectives to the system as well.


### Requirements:
**Users should be able to**
- Create a group. Each group has its own calendar view, where the groups members can view their chores.
- The group should have a unique code, that can be passed around to roommates so that they can join the group when they first sign up.

## Chopping down Christmas tree in july?
To remind her to chop down a tree before Christmas, Lisa added a chore for that exact occasion, but she accidentally made it repeat many times. Now its july, and every monday she wakes up in horror to a chore named "Chop chop time" in her calendar. Therefore she wants to be able to delete chores to stop this madness from lasting any longer.

### Requirements:
**Users should be able to**
- Delete chores.


## Scoreboard
"I do more chores a day than u do a week!" Lisa yelled at her roommate, who stood there, unimpressed by Lisa's unsubstantiated claim. To satisfy her need for proof, Lisa requires an additional feature to be added to the application: a way to keep score over how much work each person puts into their chores. Since taking out the trash is easier than cleaning the whole house, different chores need to be valued differently. It should also be a reminder to the single individual wether they are doing "too little" or "too much".

### Requirements:
**Users should be able to**
- Specify how many points a chore is worth in the chore-creation-menu
- Receive said amount of points when having done a chore
	- The points will add up, and everyone in the group should be able to see the "scoreboard".


## Spacetime
Every week Lisa makes a chores for taking out the trash on friday morning, as the garbage truck comes later the same day to pick it up. Therefore she wants to be able to make chores repeat once every week for a certain amount of weeks.

Some chores, unlike the abovementioned take-out-trash chores, are not day specific. The bathroom has to be cleaned at least once a week, but it does not matter what day that week it is done. Because of this, she wants to be able to create chores that can be done every day of the week.

### Requirements:
**Users should be able to**
- State how many weeks a chore should repeat in the chore-creation menu.
- Create chores that can be done every day of the week. 


## Quality of life
Sometimes when Lisa is trying to log in, she presses the "Create user" button. She wants to go back.

Other times she wants to log out from the application so that her roomie, that doesnt have a PC, can log into their account and mark some chores as done. 

However, none of this is possible unless she restarts the application.

She also wants an easier way to pass around the code to her collective to her roomates.

Its safe to say that some quality of life features are needed to make it easier to use the application. 

### Requirements:
**Users should be able to**
- "Go back" on pages where this makes sense *(page -> page button takes you to)*
	- Create user page -> Login page
	- Create collective page -> Join collective page
	- Chore creation page -> Calendar page
	- Leaderboard page -> Calendar page
	- Chore information page -> Calendar page
- Log out from the calendar page
- Copy the code to their collective from the calendar page

## Detective
*This task requires “'Do(n't) put off until tomorrow what you can do today' ― Benjamin Franklin” to be implemented first.*

In the calendar view the name of a chore and who the chore is assigned to is displayed. As well as this, a red border around a chore symbolises that a chore is overdue, and a green border symbolises that a chore has been done. 

A new person has joined Lisas collective. They do not understand what the different colored borders around chores symbolise. They want an alternative, more clear way to convay the same information that is convayed in the calendar view.

### Requirements:
**Users should be able to**
- View:
  - name of the chore
  - when the chore is overdue
  - the status of the chore
  - who the chore is asssigned to
  - how many points the chore is worth
  in a menu
