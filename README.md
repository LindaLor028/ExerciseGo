# ExerciseGo

After a semester long of Mobile Software Development instructed by Péter Ekler, I am proud to announce my final project..

                                                Exercise Go!
                                                
This app was developed in Android Studio and models what a basic Maps project would be (except that we specialize in exercise parks).

The app connects with Google's Firebase Authentication and Firestore to ensure that information regarding all exercise parks are available to 
the user. Additionally, we have a review system implemented so users can read up on how others have enjoyed the park. 

As this app was developed in Budapest, Hungary, it only contains information regarding exercise parks in Budapest*.

*Please note this app is in its early stages- there is a limited number of exercise parks presented to the user. 

Thank you for checkin' out my app, and we hope you enjoy exercise parks as much as we do! 

<img src="Thanks.png" alt="alt text" width="400">





#### Development Notes ####

For individuals interested in developing a similar app, please read all information below. 

FIREBASE: This app relies on a Cloud Firestore that I have linked with my Gmail account. I recommend that you create your own Cloud Firestore if you'd like to store cloud information. 

GOOGLE MAPS: This app relies on a Google Maps API Key that I have linked with my Gmail account. Unfortunately, the API key occassionally fails to read and ends up crashing the entire code (often sending an error on line 53 of MapsActivity). If this happens, just comment out all code relating to the Map in these files MapsActivity, activity_maps.xml. This will make app development speedier- I unfortunately wasted a lot of time trying to find out why my app wasn't working when it was beyond my control. 

IMAGES: All images are created by Linda Lor (me). Icons were created using Google Slides. You are free to use these images as needed- just credit my work!

CODE: A vast majority of the code is written by Linda Lor (me), but there are chunks of code that are also provided by others. It is noted in each individual file, but I would like to credit them here as well for their knowledge. 
*Péter Ekler : LoginActivity, Adapter Classes 
*Arjun Shrestha : Back Button code in ReviewActivity and DetailsActivity
*JavaPaper.com (Joe) : Current Location code in MapsActivity
*Gaurang Goda : DetailsActivity, Decimal formatting code

#### Future Improvements ####

I hope this app can inspire others to create something similar and even better! Here are some future improvements to consider if you'd like to extend this app : 
*Making Google Maps implementation a lot more intentional with markers and onMapClick Listeners. 
*A Favorite Parks activity that lists user's favorite parks
*Using Google Fit's API and making the app even more "exercise" friendly 
*Adding images to user reviews 
*Calculating the average of all reviews 




