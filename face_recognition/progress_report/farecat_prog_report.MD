__FACIAL RECOGNITION__

- INTRODUCTION

The facial recognition implementation had a couple of major aims. The first was to avoid any possibility of impersonation or cheating by students. The second was to avoid any accidental errors in marking students’ attendance. A third final aim was to make the solution available easily in terms of today’s technology, which based on our survey, we decided would be an Android app. This not only makes the solution more relevant but also makes it very handy and mobile in its use. The core functionality has been implemented thanks to the Kairos facial recognition REST API.

- DONE

In terms of code components three things were done. The first was taking a picture of the student, the second was calling the relevant service of the Kairos API using the image taken and the final one was parsing the response and displaying the success or failure of the action to the user. In simpler terms, enrollment and recognition(for marking attendance) of a student were implemented. The end user has an option to enter a name and select the enroll option to register himself. Or choose the recognize button to automatically detect himself in the picture taken. As stated earlier, both these actions display whether the action was successful or not to the user. An SQLite database has been created for recording attendance locally on the phone.

- DOING

Currently, work is in progress for recording attendance in the database. This is in continuation of the last point from the previous section. Whenever a user successfully uses the recognize option, his/her attendance will be saved to the local database. The next step will be the final part of the next user functionality, where the end user will be able to select a date or date range and view the attendance of all students for that time period. The last possible functionality for the future is the feature of class segregation, creating separate classes (for different courses) and enrolling and recognizing students specific to a class. And of course, viewing attendance for each of those classes separately. Login will be the last functionality, if at all, because of it being a common feature in all apps, but even more importantly because of the reason mentioned in the next section.

- ROADBLOCKS

A major roadblock, or should we say a learning curve, was Android development. Since we weren’t familiar with any sort of mobile app development, a fair amount of time after the January phase was spent in learning to code in and familiarize ourselves with the Android framework and environment. After this was testing and checking the feasibility of a facial recognition API which was a make or break criteria for this implementation. Thankfully the Kairos API worked out perfectly for our needs. A future roadblock seems to be in the registration of users, and this is purely in terms of the possibility of  the release of this app for commercial purposes. Registration on the Kairos website provides an app_id and an app_key which can be configured and used, but only for personal use. Commerical use requires a commercial account which involves a fee for the use of the API. Though for the requirements of this course, this wouldn’t be an issue.
