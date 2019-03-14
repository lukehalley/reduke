
<h1 align="center">
  <br>
  <a href="https://i.ibb.co/C93y7np/redukeiconblue.png"><img src="https://i.ibb.co/C93y7np/redukeiconblue.png" alt="Reduke Logo" width="200"></a>
  <br>
  Reduke
  <br>
</h1>

<h4 align="center">An android application built with Kotlin for my 4th Year, Semester 2 "Mobile App Dev 2" module for our continuous assesment.</a></h4>

## Luke Halley
### 20071820

## Repo

[Reduke Github Repo](https://github.com/lukehalley/reduke)

## Demo Videos

**PLEASE NOTE:** There are two demo videos, one which shows the **Reduke App Functionality** and a second video which shows the **Espresso UI Tests**

Demo Links:

- [Reduke App Functionality](https://youtu.be/LO0r3PZQhF4)

- [Espresso UI Tests](https://youtu.be/ncm8QSMwZus)

## Features
* **Design**
  - Custom Splashscreen created by me.
  - Navigation drawer, Floating Action Button (FAB), Flat Icons.
  - Current Users Email & Username shown in sidebar.
  - Material Design - Flat and bright colours with consistent app wide colour scheme.
* **Animations**
  - Posts fade in from the right when the posts are loaded or when they are sorted.
* **Espresso UI Tests**
  - Test all key functionalitys (CRUD, Upvote, Downvote, Login, Logout etc...)
  - Using **EspressoIdlingResource** which allows testing of asynchronous functions in the Reduke app such as Login and Register.
* **Reddit Feed Features**
  - Scroll through all the posts created as a feed just like Reddit.
  - Sort Posts by:
    - Top (Most votes)
    - Newest (Newest Posts)
    - Oldest (Oldest Posts)
    - Alphabetical (Ascending Order)
    - Alphabetical (Descending Order)
  - Upvote or Downvote a post.
  - Posts displayed in uniform cards.
  - Post Title, Owner, Date Created, Votes, Subreddit, Comment Count can be seen on each card for each Post.
* **Persistence**
  - Persisting Users using Firebase.
  - Persisting Posts using JSON.
* **Version Control**
  - Use of Issues to track task that needed to be done
  - Branches for each feature.
  - Regular commits.
  - Dev branch used with merge requests to keep all code on Dev & Master working.
* **Register and Login**
  - Users stored and authenticated in the cloud using Firebase Authentication.
  - Password requires a double entry to ensure change is correct).
  - Empty field checking.
* **CRUD Features**
  - Add a Post.
  - View/Read a Post.
  - Edit/Update a Post.
  - Delete Post.
  * User Model Fields
    - id: unique id of the user.
    - username: the username of the user.
    - email: the email of the user.
    - password: the password of the user.
* **Post Model Fields**
  - id: unique id of the post.
  - postOwner: user who created the post.
  - title: title of the post.
  - text: body text of the post.
  - tags: tags set by the user set when created - EG: Question, Answer...
  - votes: number of votes the post has decided by users upvoting and downvoting the post.
  - subreddit: subreddit the post belongs too.
  - timestamp: time the post was created.
  - upvotedBy: list of user emails who upvoted the post.
  - downvotedBy: list of user emails who downvoted the post.
  - Empty field checking when creating a Post.
* **Pop Up Windows**
  - Used in multiple areas such as logging out (are you sure you want to logout?) and when deleting a Post (are you sure you want to delete this Post?) etc...
* **Using SharedPreferences**
  - Used for various logic and ui elements.

## Screenshots

<p align="center">
  <img src="https://i.imgur.com/6LL3cSp.jpg">
</p>

## Setup

First clone the repo at the **dev** (development not finished) branch down to your machine.

`git clone https://github.com/lukehalley/reduke.git`

Then [open the project in Android Studio](https://github.com/dogriffiths/HeadFirstAndroid/wiki/How-to-open-a-project-in-Android-Studio)

<p align="center">
  <img src="https://i.imgur.com/zUipbb3.png">
</p>

Connect your device or start an emulator and run the app! 😃
