<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<script src="https://www.gstatic.com/firebasejs/4.10.1/firebase.js"></script>
<script>
    // Initialize Firebase
    // TODO: Replace with your project's customized code snippet
    var config = {
        apiKey: "AIzaSyCo0agYeZy9SIsUenF7rOuY9TyMLONlnKc",
        authDomain: "semms-a2748.firebaseapp.com",
        databaseURL: "https://semms-a2748.firebaseio.com",
        storageBucket: "semms-a2748.appspot.com"
    };

    var email = 'simclogger@gmail.com';
    var password = '@sscp@ssw0rd';


    firebase.initializeApp(config);


//   firebase.auth().createUserWithEmailAndPassword(email, password).catch(function(error) {
//  // Handle Errors here.
//  alert('success');
//  var errorCode = error.code;
//  var errorMessage = error.message;
//  // ...
//});

    firebase.auth().signInWithEmailAndPassword(email, password).catch(function (error) {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;
        // ...
    });


    // Get a reference to the database service
    var database = firebase.database();

//    writeLocationData('new2', 1520937610000, 1);

//  removeLocationData('newInsertedss');

//  alert(readLocationData().val);
//    readAllLocationData();


    setInterval(function () {
        readAndProcessAllLocationData();
    }, 30000);


//    readAndProcessAllLocationData();

    function writeLocationData(userId, time, accuracy) {
        firebase.database().ref('locations/' + userId).set({
            time: time,
            accuracy: accuracy
        });
    }

    function removeLocationData(userId) {
        firebase.database().ref('locations/' + userId).remove();
    }


//reading
    function readLocationData(userId) {

        return firebase.database().ref('/locations/' + userId).once('value').then(function (snapshot) {
            var username = (snapshot.val() && snapshot.val().username) || 'Anonymous';
            alert(username);
            return username;
            // ...
        });

    }


//var mostViewedPosts = firebase.database().ref('/locations/');
//var fLen = mostViewedPosts.length;
//alert(fLen);
//for (i = 0; i < fLen; i++) {
//    text += "<li>" + mostViewedPosts[i] + "</li>";
//}


    function readAndProcessAllLocationData() {



        var leadsRef = database.ref('locations');
        leadsRef.on('value', function (snapshot) {
            snapshot.forEach(function (childSnapshot) {
                var keyID = childSnapshot.ref.getKey();
                var parentKeyID = childSnapshot.ref.parent.getKey();
//                alert("Key ID : " + keyID);


                var unix_timestamp = childSnapshot.val().time;
                var accuracy = childSnapshot.val().accuracy;
//                alert(unix_timestamp);
//                alert(accuracy);




//                alert(timeConverter(unix_timestamp));
//                var current_UnixTime = Math.round(+new Date());
//                alert(timeConverter(current_UnixTime));

                Date1 = (unix_timestamp / 1000);
                Date2 = Math.round(+new Date() / 1000);
                var wholeSecdiff = Date2 - Date1;
                var wholeMindiff = Math.round(wholeSecdiff / 60);

//                alert("Whole Difference In Minute : " + wholeMindiff);

                if (wholeMindiff > 4)
                {
//                    alert('deleting');
                    removeLocationData(keyID);
                }

            });
        });
    }




    function readAllLocationData() {

        var leadsRef = database.ref('locations');
        leadsRef.on('value', function (snapshot) {
            snapshot.forEach(function (childSnapshot) {
                var keyID = childSnapshot.ref.getKey();
                var parentKeyID = childSnapshot.ref.parent.getKey();
                alert("Key ID : " + keyID);


                var unix_timestamp = childSnapshot.val().time;
                var accuracy = childSnapshot.val().accuracy;
//                alert(unix_timestamp);
//                alert(accuracy);
                timDif(unix_timestamp / 1000);

                alert(timeConverter(unix_timestamp));
                var current_UnixTime = Math.round(+new Date());
                alert(timeConverter(current_UnixTime));


                if (accuracy === 1)
                {
                    alert('deleting');
                    removeLocationData(childSnapshot.ref.getKey())
                }

            });
        });
    }

    function getParent(snapshot) {
        // You can get the reference (A Firebase object) from a snapshot
        // using .ref().
        var ref = snapshot.ref();
        // Now simply find the parent and return the name.
        return snapshot.parent().name();
    }


    function timeConverter(UNIX_timestamp) {
        var a = new Date(UNIX_timestamp);
        var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        var year = a.getFullYear();
        var month = months[a.getMonth()];
        var date = a.getDate();
        var hour = a.getHours();
        var min = a.getMinutes();
        var sec = a.getSeconds();
        var time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min + ':' + sec;
        return time;
    }


    function timDif(unixTime)
    {

        Date1 = unixTime;

        Date2 = Math.round(+new Date() / 1000);

        var secdiff = Date2 - Date1;
        var mindiff = Math.floor(secdiff / 60);
        secdiff = secdiff % 60;
        var hourdiff = Math.floor(mindiff / 60);
        mindiff = mindiff % 60;
        var daydiff = Math.floor(hourdiff / 24);
        hourdiff = hourdiff % 24;

        alert("Difference In Seconds : " + secdiff);
        alert("Difference In Minute : " + mindiff);
        alert("Difference In Hours : " + hourdiff);
        alert("Difference In Day : " + daydiff);

        var wholeSecdiff = Date2 - Date1;
        var wholeMindiff = Math.round(wholeSecdiff / 60);

        alert("Whole Difference In Seconds : " + wholeSecdiff);
        alert("Whole Difference In Minute : " + wholeMindiff);
// don't bother with: secdiff = secdiff % 60;

    }

</script>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Firebase Maintenance Service Is Running!</h1>
        <h2>Please don't close this window!</h2>
    </body>
</html>
