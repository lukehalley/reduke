
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

# Jump To:

[Demo Videos](https://github.com/lukehalley/reduke/blob/dev/README.md#demo-videos)

[Setup](https://github.com/lukehalley/reduke/blob/dev/README.md#setup-1)

[Features](https://github.com/lukehalley/reduke/blob/dev/README.md#features)

[Technolgies Used](https://github.com/lukehalley/reduke/blob/dev/README.md#technolgies-used)

[User Case Diagrams](https://github.com/lukehalley/reduke/blob/dev/README.md#user-case-diagrams)
 
[Screenshots](https://github.com/lukehalley/reduke/blob/dev/README.md#screenshots)

## [Setup](https://gitlab.com/stork-group/stork-web-service/wikis/Home/#setup)

## Demo Videos

**PLEASE NOTE:** There are two demo videos, one which shows the **Reduke App Functionality** and a second video which shows the **Espresso UI Tests**

Demo Links:

- [Reduke App Functionality](https://youtu.be/LO0r3PZQhF4)

- [Espresso UI Tests](https://youtu.be/ncm8QSMwZus)

## Setup

First clone the repo at the **master** branch down to your machine.

`git clone https://github.com/lukehalley/reduke.git`

Then [open the project in Android Studio](https://github.com/dogriffiths/HeadFirstAndroid/wiki/How-to-open-a-project-in-Android-Studio)

<p align="center">
  <img src="https://i.imgur.com/zUipbb3.png">
</p>

Connect your device or start an emulator and run the app! 😃

## Features
### Reduke V2 - Features added/improved/ to the second version of Reduke for assignment two.
**APIS/Libraries**
- Google Sign In
- Firebase Authentication
- Firebase Real Time Database
- Firebase Authentication
- RichLinkPreview
- Anko Commons
- UI Automator
- Espresso
- Play Services Auth
- JUnit
- Apache Commons Text
- CircleImageView
- Glide
- Picasso
**Reddit Feed Features**
  - Each user can either Upvote or Downvote a post (negative points allowed). When a post is votes on it tracks who placed the vote on the post which allows the app to keep of track of if the current use thas already voted.
  - The type of Post is used to in logic in the adapter to show/hide elements of the card to accomidate the type of post. This allows the app to use one design but display all the different types of posts in the feed while still maintaining a consitent design.
  - Post Title, Owner, Date Created, Votes, Subreddit, Comment Count can be seen on each card for each Post and are filled in with the data stored in the Firebase Realtime Database.
  - Changed editing of posts from a long press to a three button menu to improve UX.
**Added Rich Preview Links**
  - Used the [RichLinkPreview](https://github.com/PonnamKarthik/RichLinkPreview) by PonnamKarthik - library to show link previews in the cards for all link posts.
**Subreddits**
  - ExpandableListView in Nav Drawer with a list of Subreddits.
  - When a user clicks a subreddits the feed is filtered to only display posts which are within that subreddit.
**Register and Login**
  - Used the Glide CircleImageView library to display user image.
  - Added the option for the user to sign in user Google account using Google Sign In.
  - Used the users Google account email, username and profile picture to top of the navigation drawer.
  - Using a Reduke placeholder for users logged in using Firebase Auth.
**Persistence**
  - Persisting Users using Firebase Authentication.
  - Persisting Posts using Firebase Realtime Database including the creation, reading, editing and deletion of them.
**Your Posts**
  - Nav Drawer item to allow to user see their only their own posts.
**Image**
  - Allow user to take a picture from their phone camera.
  - Allow user to select a picture from their phones gallery.
**Design**
  - Switched FAB Button To The Left To Improve UX.
  - Redesigned Login Activity To Accomidate Google Sign In.
  - Added Break Link To Login Activity To Seperate Login to Register.
  - Refactored Post Card To Accomidate the 3 post types (previously on 1 design).
  - Improved general layout of cards to make them more uniform.
**Animations**
  - Tweaked when Animation for feed is played to improve UX.
**Testing**
  - Using Espresso UI with an Emulator to test the UI of reduke.
  - Using **EspressoIdlingResource** which allows testing of asynchronous functions in the Reduke app such as Login and Register.
  - Testing Splashscreen.
  - Testing login for Firebase authetication with random user credentials.
**Version Control**
  - Issues - 28 total Issues were created to be used to track task that needed to be done
  - Branches - a branch was created for each feature
  - Regular commits.
  - Dev branch used with merge requests to keep all code on Dev & Master working.
**CRUD Features**
  - Dynamic Edit/Update activity which shows/hides elements based on the type of type of the post the User is editing.
  - Link parsing, validates the entered URL for a URL post. Adds a HTTPS if the url doesnt include it.
**Post Model Fields**
  - link: the link associated to the link post.
  - image: the image associated with the image post.
  - votes: number of votes the post has decided by users upvoting and downvoting the post.
  - subreddit: subreddit the post belongs too.
  - timestamp: time the post was created.
**Anko Selector**
  - Used to allow the user to decide "What Type Of Post Do You Want To Create?" and select for the thre three post types (text, image or link) and bring them to a dynamic create post activity which shows/hides elements based on the type of type of the post the User is creating. 
### Reduke V1 - Features added/improved/ to the first version of Reduke for assignment two.
**Design**
  - Custom Splashscreen created by me.
  - Navigation drawer, Floating Action Button (FAB), Flat Icons.
  - Current Users Email & Username shown in sidebar.
  - Material Design - Flat and bright colours with consistent app wide colour scheme.
**Animations**
  - Posts fade in from the right when the posts are loaded or when they are sorted.
**Reddit Feed Features**
  - Scroll through all the posts created as a feed just like Reddit.
  - Upvote or Downvote a post.
  - Posts displayed in uniform cards.
**Persistence**
  - Persisting Users using Firebase.
  - Persisting Posts using JSON.
**Version Control/GIT**
  - Use of Issues to track task that needed to be done
  - Branches for each feature.
  - Regular commits.
  - Dev branch used with merge requests to keep all code on Dev & Master working.
**Register and Login**
  - Users stored and authenticated in the cloud using Firebase Authentication.
  - Password requires a double entry to ensure change is correct).
  - Empty field checking.
**CRUD Features**
  - Add a Post.
  - View/Read a Post.
  - Edit/Update a Post.
  - Delete Post.
  * User Model Fields
    - id: unique id of the user.
    - username: the username of the user.
    - email: the email of the user.
    - password: the password of the user.
**Post Model Fields (Some not used - will be in next assignment)**
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
**Anko Pop Up Windows**
  - Used in multiple areas such as logging out (are you sure you want to logout?) and when deleting a Post (are you sure you want to delete this Post?) etc...
**Using SharedPreferences**
  - Used for various logic and ui elements.

## Technolgies Used
- Google Sign In
- Firebase Authentication
- Firebase Real Time Database
- Firebase Authentication
- RichLinkPreview
- Anko Commons
- UI Automator
- Espresso
- Play Services Auth
- JUnit
- Apache Commons Text
- CircleImageView
- Glide
- Picasso

## User Case Diagrams

## Screenshots

<p align="center">
  <img src="https://i.imgur.com/6LL3cSp.jpg">
</p>
