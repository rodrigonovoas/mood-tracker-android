
![Screenshot_20230628_220247](https://github.com/rodrigonovoas/mood-tracker-android/assets/49367885/2bddff24-add1-42ae-8e86-4d50b2aab8aa)
![Screenshot_20230628_220336](https://github.com/rodrigonovoas/mood-tracker-android/assets/49367885/774a4a68-f5ef-4312-97ba-f9a26a93cad5)
![Screenshot_20230628_220348](https://github.com/rodrigonovoas/mood-tracker-android/assets/49367885/905fbb93-2bf5-4624-a511-ea5575e04052)


# Mood Tracker

A simple way to track your mood everyday via Android.


# Main Features

- Kotlin
- MVVM architecture
- Repository pattern
- Livedata
- Coroutines
- Room database
- Shared Preferences
- Unit Testing
- Dialogs
- Bottom Sheets
- SOLID principles

# Project overview

A simple project to go over some of the most useful Jetpack libraries: Room, Shared Preferences, Livedata...

In this project we create an app to "track" our mood over time.

For that, we use a Dialog component to give the user the possibility to choose between three different moods, and we also give them the option to link a comment together with the selected mood.

All of this is stored in a Room database which saves important data such as selected mood, the linked comment and the creation date.

This mood will can be stored once per day, and for that, we make use of Shared Preferences to keep track of the date of the last mood creation date. If this date is the same as the current one, the selection dialog won't appear. If not, the dialog will appear to let the user choose his day's mood.

All this data will be printed in a Activity in a graphical way, so the user can see his mood over the time. For that, I create a graphic chart with a Recyclerview filled with data bars. This data bars are collected from the database and printed in the recyclerview via an Adapter, as a custom row.

# MVVM architecture and Repository pattern

![image](https://github.com/rodrigonovoas/mood-tracker-android/assets/49367885/dce11078-7486-439b-9b6e-8ceb477df329)

MVVM is currently the most used architecture in android development. 

Thanks to its properties, we can create applications with a good separation of responsabilities between the app logic and the UI, an easier project to test, and an easy way to handle asynchronous data, among other things.

With the repository pattern strategy we separate the data from the logic and we bring a source to manage data among the project, which can be easily injected, tested, and brings us the possibility to change the source of the data without making big impacts in our project.


# Coroutines and LiveData

Using coroutines is a simple way to run asynchronous functions in our project. 

Thanks to its properties we can run functions in different threads, and with that, we prevent the UI to be blocked and let the user use the App while executing functions (for example: getting data from the database) in the background thread.

This functions and their data can be tracked with LiveData variables, which bring us the possibility to apply the Observable Pattern and, for exaple, track the data from the background, allowing us to use it in the UI in a organic way.

# Room

![image](https://github.com/rodrigonovoas/mood-tracker-android/assets/49367885/c90cb1ba-8c8b-4b8b-a223-9f7f1ed8dbb7)


Room allows us to create a database via SQLite in a simpler way thanks to its abstraction, which brings us the possibility to apply persistance in our application.




