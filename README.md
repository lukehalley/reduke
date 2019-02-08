
<h1 align="center">
  <br>
  <a href="https://preview.ibb.co/mDA9Sf/appNoTxt.png"><img src="https://preview.ibb.co/mDA9Sf/appNoTxt.png" alt="Markdownify" width="200"></a>
  <br>
  Hillfort
  <br>
</h1>

<h4 align="center">An android application built with Kotlin for my 4th Year, Semester 1 "Mobile App Dev" module for our first assignment.</a></h4>

## Demo

<p align="center">
  <img src="https://media.giphy.com/media/63LNxgT7pn0Ma9NRHh/giphy.gif">
</p>

## Screenshots

<p align="center">
  <img src="http://i.imgur.com/UGPa7oW.jpg">
</p>

<p align="center">
  <img src="https://image.ibb.co/maSjSf/2.jpg">
</p>

<p align="center">
  <img src="https://image.ibb.co/h3LtL0/3.jpg">
</p>

## Setup

First clone the repo at the master branch down to your machine.

`git clone https://github.com/lukehalley/hillfort.git`

Then [open the project in Android Studio](https://github.com/dogriffiths/HeadFirstAndroid/wiki/How-to-open-a-project-in-Android-Studio)

<p align="center">
  <img src="https://i.imgur.com/LiOBGk0.png">
</p>

navigate to the file located:

`Hillfort/src/main/AndroidManifest.xml`

and find the following lines:

`<meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="YOUR-API-KEY-HERE" />`

replace `YOUR-API-KEY-HERE` with you Google Maps API.

If you do not know how to get a Google Maps API [here is a tutorial by Google](https://developers.google.com/maps/documentation/android-sdk/signup)

Finally connect your device or start an emulator and run the app! 😃

## Key Features

* Splashscreen
* Persitance for both Hilloforts and Users using JSON
* Register and Login
  - Using a "User" module with the details of user strored locally on the device with a JSON file.
  - Password requires a double entry to ensure change is correct)
  - Empty field checking
* Material Design
  - Flat and bright colours
* Modern UI
  - Navigation drawer, Floating Action Button (FAB), Flat Icons
* CRUD Features
  - Add a Hillfort
  - View/Read a Hillfort
  - Edit/Update a Hillfort
  - Delete Hillfort
* Multiple Data Fields
  - Title
  - Description
  - Additional Notes
  - Visted (Switch/Boolean)
  - Images (Up to 4 - can be loaded from devices gallery or taken by camera)
  - Location with readable address (Google Maps with Geocoder)
  - Empty field checking
* User Settings
  - Edit Email
  - Edit Password (with double entry to ensure change is correct)
  - Empty field checking
* Pop Up Windows
  - Used in multiple areas such as logging out (are you sure you want to logout?) and when deleting a hillfort (are you sure you want to delete this hillfort?) etc...
