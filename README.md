Quiz (An Android App for Mock Test-taking)
==========================================

This Android application for conducting mock tests on Android smartphones was 
built by a team of 3 students from the Dept. of Electrical Engineering, IIT 
Madras. The primary objective of the app is to enable students to take their 
placement mock tests at the ease of their Android smartphones, thereby solving 
the problem of slow rendering of questions, when the tests are hosted on 
external websites. The app has been designed to supports devices of all sizes 
operating on Android OS 2.3 (Gingerbread) and above.

**Overview of the Application**:
The app can be used to create MCQ questions with multiple options correct and 
the question can have one image attachment (For example: The circuits). These 
questions as a bundle can be exported as a database, which then can be 
uploaded onto an official website. The app can then access this website and 
download the database. The user can then take the timed test. The app meanwhile 
will authenticate the user on to the server and register the start of the test 
and end of the test and the score. The app has two parts, the Admin app, and 
the User app.

**Admin app**:
The Admin app, (the master app) will be only with the Branch Councillors and 
Placement coordinators, who need to create the questions. They can create 
questions on this app, export the data base and then check the test. It is 
hosted in the *master* branch of the repository.

A video demonstrating the Admin app: http://youtu.be/UlSCEk_oq00

**User app**:
The User app is basically a scaled down version of the Admin app. It will not 
have the capabilities of creating questions and exporting. This app will 
authenticate users on to the website, download the question database and begin 
the quiz for the user. Once the user is done with the quiz, the app will 
automatically log the marks in to the server. It is hosted in the *User* 
branch of the repository.

**Current Status**:
With a great deal of help from the EE Branch Councillor, Chaitra, we were 
given space on the students server (students.iitm.ac.in) to test the 
application, and we have successfully tested the app and shown it her. We are 
now ready to share the application with the students, and the best way of 
doing it would be through the Google Play Store. We will be uploading it on Play
Store soon.

**Future**:
Depending on the demand and interest, we intend to make the application SCORM
compliant helping us expand the usability of the application.

