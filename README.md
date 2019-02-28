
<h1 align="center">
  <br>
  <a href="https://i.ibb.co/C93y7np/redukeiconblue.png"><img src="https://i.ibb.co/C93y7np/redukeiconblue.png" alt="Reduke Logo" width="200"></a>
  <br>
  Reduke
  <br>
</h1>

<h4 align="center">An android application built with Kotlin for my 4th Year, Semester 2 "Mobile App Dev 2" module for our continuous assesment.</a></h4>

## Demo



## Screenshots

<p align="center">
  <img src="https://lh3.googleusercontent.com/ypfYjsWXhkA_iboEqDVH155mCfQ8gcXP5qMJWtdWUWnRwkuBf1tIZUjdL0tBIMxoTt7z_zmTr7gnzTAdYUTuQrUdQXpQRkRCAqNLnCMw5l4b-2QU3SIs5AcAWqfHsDwUfRJ8VQAKSMJm-QI5RV5sk_gkVBA9x1Py-0nxEFLaglrNJVNAzbCTYYvTo7kveOMFCx7O87bZnd2dWlFoKC1eMD1zCH5jGwBYCSbN26toFoClV5iNtQuQfy5mnoIoB_0ZZokZuB_RD_kSSJi7CF1vobrb_wJag-5okXCveQTiHN5ESy-zTcK-VsXtbHUY3EIXoP6DXuM7QtJX3huEqVOzXG7U1RMpAIC3XyyFJc1m4RvavgXYv_OvvALhmP_FJO9pXZ51s249fekmTmjrGtoMNyZS97yMF3kwhkk8NwYHAhxV7N7zWl1ePN7z94eAmzJdgidWvZk60tdtKpAzEyQXvxApBfA1n3HkvDRc4VGN61_Rl-8-hBJga8UBczRY3Depx03VDfhSvxy4ZlYo2HUXMzzej-gFlZrz8rEu1Pr-3cyi9gQCf50zYhCxYWHQG-nF8UZ-ZOhQBfgL_ETpKSWcYRpBjVhc4yw-CEcjQA8kW5kvA_b49kPfZwrWwvD2Hs6I8eJvorB10G2g-ToXe8BjO9sy6tK8P6H3=w924-h1640-no">
</p>

## Setup

First clone the repo at the master branch down to your machine.

`git clone https://github.com/lukehalley/reduke.git`

Then [open the project in Android Studio](https://github.com/dogriffiths/HeadFirstAndroid/wiki/How-to-open-a-project-in-Android-Studio)

<p align="center">
  <img src="https://lh3.googleusercontent.com/vqXWSJiIdlsARvsxzszBKqxI8_Ey979guegQUAda_gG-zGAGnjPwBV5rkzoBK5JEpqhO7sVs7mTrhMEfDILk_5pchhrhOoNJHKmmRYmtd3E0uydd8KOFA0EwqT_fZOrAWC1dE9BWotG55KP41ERNY3geb8cktDTjJQRroetPZGlnO1wSxRvCQHvHFT7fx3CgAReDkLZc7ztJKVlPKOC5MWj2bt3bdy3RdRYMeJ4w9hR_EmcEOUndeUNEzU--AbzGcTA3vry4c5MKv5nxXRNnleDoYfVExE0I9IogHI3KwraMVpiiEf8etHzFhxuv6eAorbqUe-OljEIi7yRjBD9WpwyBsxwQRSCvcoVeAF5bPmLOf5aXsV9AUEci_yRTXdt1sd7BpFvs4KMnW_JdZjt3O7KtqxPe35tD2eWBivzXlE97pSjvg4zBfsMNoRsPwpPUc8RdrfJiJZVl1zF9SW_GbWswTzs3zLboi5lxFSdz_xz7TMWvyINRu_cKVdvpX4nVBrxyYpdHiTcbDtvbwWFXbqK9c6xEOYmr5R-NS56aDjtsIycqkZvV9q7lyNiYEnC5HaJNJb4Qa1AYbmevyoyZfx9tvwRx8mWs1aoVuR4vtTLbl3s_V4sj09W2ymnaegb8FowDcX_T7pPy9cxaavh-RZIihw0ZHHbc=w2692-h1640-no">
</p>

Connect your device or start an emulator and run the app! 😃

## Key Features

* Splashscreen
* Material Design
  - Flat and bright colours with consistent app wide colour scheme.
* Modern UI Elements
  - Navigation drawer, Floating Action Button (FAB), Flat Icons.
  - Current Users Email & Username shown in sidebar
* Animations
  - Posts fade in from the right when the posts are loaded or when they sorted.
* Reddit Feed Features
  - Scroll through all the posts created.
  - Sort Posts by:
    - Top (Most votes)
    - Newest (Newest Posts)
    - Oldest (Oldest Posts)
    - Alphabetical (Ascending Order)
    - Alphabetical (Descending Order)
  - Upvote or Downvote a post.
  - Posts displayed in uniform cards.
  - Post Title, Owner, Date Created, Votes, Subreddit, Comment Count can be seen on each card for each Post.
* Persistence
  - Persisting Users using Firebase
  - Persisting Posts using JSON
* Register and Login
  - Users stored and authenticated in the cloud using Firebase Authentication.
  - Password requires a double entry to ensure change is correct)
  - Empty field checking
* CRUD Features
  - Add a Post
  - View/Read a Post
  - Edit/Update a Post
  - Delete Post
  * User Model Fields
    - id: unique id of the user
    - username: the username of the user.
    - email: the email of the user.
    - password: the password of the user.
* Post Model Fields
  - id: unique id of the post
  - postOwner: user who created the post
  - title: title of the post
  - text: body text of the post.
  - tags: tags set by the user set when created - EG: Question, Answer...
  - votes: number of votes the post has decided by users upvoting and downvoting the post.
  - subreddit: subreddit the post belongs too.
  - timestamp: time the post was created.
  - upvotedBy: list of user emails who upvoted the post.
  - downvotedBy: list of user emails who downvoted the post.
  - Empty field checking when creating a Post.
* Pop Up Windows
  - Used in multiple areas such as logging out (are you sure you want to logout?) and when deleting a Post (are you sure you want to delete this Post?) etc...
* Using SharedPreferences
  - Used for various logic and ui elements
