
<h1 align="center">
  <br>
  <a href="https://i.ibb.co/C93y7np/redukeiconblue.png"><img src="https://i.ibb.co/C93y7np/redukeiconblue.png" alt="Reduke Logo" width="200"></a>
  <br>
  Reduke
  <br>
</h1>

<h4 align="center">An android application built with Kotlin for my 4th Year, Semester 2 "Mobile App Dev 2" module for our continuous assesment.</a></h4>

## Repo

[Reduke Github Repo](https://github.com/lukehalley/reduke)

## Demo

**PLEASE NOTE:** There are two demo videos, one which shows the **Reduke App Functionality** and a second video which shows the **Espresso UI Tests**

- [Reduke App Functionality](https://youtu.be/LO0r3PZQhF4)

- [Espresso UI Tests](https://youtu.be/ncm8QSMwZus)

## Screenshots

<p align="center">
  <img src="https://lh3.googleusercontent.com/_0mnH3U0Z7-XHENUl9iQms1cob98f6atZ2LkoHEe_eqQWyqs99nFNqrXSt7Bhwq1rZMCErYuYQEVy5rhkn2PFgRAjQTRnlMxgfCIZ1uEnwK_FILo25XBh_lmuFRPKFGPtx5o52oW5mhKcjEKBCqizme_8xvDS0t41zml5DQcLFMWAKb02k_vqVarH5HSTLBRYdPGQ1xo2_C2RGQPTTz65qBZdbkDGvVAJOBSWH9Ol2K0BXrvcWS1xjJ5ZzlW1L5eVJKvPrD-RzTeKe28taQ3qqf_bpekcwPpJiFCrG50qHimfzVRmPkP3goIGAE8oibUKBYEHPt4CuUHi5pIu_bsHSGQxACRln78b5p6_S53YC_85p4GKYk8SBZna1Y64U_8zmHpc_vq3M_5Xhe-Iab2Z9zdj2a03g1CEJV13YpNw6Rc7Nc5Z1AfObMrtJVx5fZmQJB80Ddeopdj0kHwXwme5sUZNmXliOi8fBl4sM-S8m-tiP-bTE9gXkTw9zQM9WW4-5WijpPk3gcdYLxBgpGHhBsSmjQVQM-o4EN3_HslTtHQa-vzd5nH42dHnuQL5k2ZX84lkhMryg7yJWRADbSXwSPJmQ0aNmM2h5kyi86QfPAyHvQRZHtgdgZho57_p73GM-QT9TZdVLkNY5M0R2a0R5k3F8VgMN5M=w978-h1738-no">
</p>

## Setup

First clone the repo at the master branch down to your machine.

`git clone https://github.com/lukehalley/reduke.git`

Then [open the project in Android Studio](https://github.com/dogriffiths/HeadFirstAndroid/wiki/How-to-open-a-project-in-Android-Studio)

<p align="center">
  <img src="https://lh3.googleusercontent.com/tl_GS5PHma9mZy216QqzIieXNpsxTJWZcIydTyuHyU1RuU5PYlf8J29PKNYb1EGE8YmsBp_mk7vT2pG2Ft_o-vwVGKnwAnJCRP_rblzJ5NbQBz2X9QSMkkCJqrB-7MKY2uco3zgwd6O8sd896R65JlMbnF4SyTVTpdt0UBCUr2PwUE5rFbqIAHxBCiwrPNZKVqnDO8je8cIGbvNsDEgoQbJBQKPx_XweyI4Zj2qWsG7Gil9E6NTMlxpVG_8cgjxCFsydGudEQyHtz_kHtjw6c2eMxAmWKGldMK4HnojXnvzF1V41ATjEUECggLZeqlgNsJTHfjQl0vanOhPdUl7sHRZnh87Z_ywz630kRmjkUXJ0OeWA0UNk3lEMCHXkl7KooMUjGbwu9r1EmRomVYNtuCKyy4VMpZfipiwf8T_3Nma8J48ZoiqB6jHesaw2NQXq0h4vvSDKm8AKF57POINPalybIyC2jWHi3gXrSNd6YoACgn_r8vLdjbv7WXaGoB78Ie5eLP_05D-svOZESRVWyx1EVEdm3ZHhSOUj9w_Wc5Ff8knGY7HOQ2Zi7FXsZi9kLVuBjXEFO2EP5f-P0IlkIPxEUSyeFnKGHqu3-8-8jB3_qPV5bVcyOny8IOeBOoIsJcWLoPbI8-BmxmkPEhedzD9Tvr9MSgGR=w2854-h1738-no">
</p>

Connect your device or start an emulator and run the app! 😃

## Features
* **Design**
  - Custom Splashscreen created by me.
  - Navigation drawer, Floating Action Button (FAB), Flat Icons.
  - Current Users Email & Username shown in sidebar.
  - Material Design - Flat and bright colours with consistent app wide colour scheme.
* **Animations**
  - Posts fade in from the right when the posts are loaded or when they sorted.
* **Espresso UI Tests**
  - Test all key functionalitys (CRUD, Upvote, Downvote, Login, Logout etc...)
  - Using EspressoIdling which allows testing of asynchronous functions in the Reduke app such as Login and Register.
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
