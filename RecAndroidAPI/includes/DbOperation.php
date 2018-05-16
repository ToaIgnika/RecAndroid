<?php
/**
 * Created by PhpStorm.
 * User: Cragzeek
 * Date: 2018-05-02
 * Time: 10:57 AM
 */

class DbOperation
{
    //Database connection link
    private $con;

    //Class constructor
    function __construct()
    {
        //Getting the DbConnect.php file
        require_once dirname(__FILE__) . '/DbConnect.php';

        //Creating a DbConnect object to connect to the database
        $db = new DbConnect();

        //Initializing our connection link of this class
        //by calling the method connect of DbConnect class
        $this->con = $db->connect();
    }

    /*
    * The create operation
    * When this method is called a new record is created in the database
    */
    function createReview($courseName, $instructorName, $reviewText, $starRating){
        $stmt = $this->con->prepare("INSERT INTO reviews (courseName, instructorName, reviewText, starRating) VALUES (?, ?, ?, ?)");
        $stmt->bind_param("sssd", $courseName, $instructorName, $reviewText, $starRating);
        if($stmt->execute())
            return true;
        return false;
    }

    /*
    * The read operation
    * When this method is called it is returning all the existing record of the database
    */
    function getReviews(){
        $stmt = $this->con->prepare("SELECT courseName, instructorName, reviewText, starRating FROM reviews");
        $stmt->execute();
        $stmt->bind_result($courseName, $instructorName, $reviewText, $starRating);

        $reviews = array();

        while($stmt->fetch()){
            $review  = array();
            $review['courseName'] = $courseName;
            $review['instructorName'] = $instructorName;
            $review['reviewText'] = $reviewText;
            $review['starRating'] = $starRating;

            array_push($reviews, $review);
        }

        return $reviews;
    }

    /*
    * The read operation
    * When this method is called it is returning all the existing record of the database
    */
    function getEvents(){
        $stmt = $this->con->prepare("SELECT eventID, eventDay, usedSlots, maxSlots, active, className, classLocation,
            c.instructorID, beginHour, beginMin, endHour, endMin, dayOfWeek, classDescription,
            classImageURL, firstname, lastname, photoURL, bio, categoryName, hexColor FROM events e LEFT JOIN classes c ON c.classID=e.classID LEFT JOIN instructors i ON i.instructorID = c.instructorID LEFT JOIN classcategories cc ON c.categoryID = cc.categoryID ORDER BY eventDay, beginHour, beginMin");
        $stmt->execute();
        $stmt->bind_result($eventID, $eventDay, $usedSlots, $maxSlots, $active, $className, $classLocation,
            $instructorID, $beginHour, $beginMin, $endHour, $endMin, $dayOfWeek, $classDescription,
            $classImageURL, $firstname, $lastname, $photoURL, $bio, $categoryName, $hexColor);

        $events = array();

        while($stmt->fetch()){
            $event  = array();
            $event['eventID'] = $eventID;
            $event['eventDay'] = $eventDay;
            $event['usedSlots'] = $usedSlots;
            $event['maxSlots'] = $maxSlots;
            $event['active'] = $active;
            $event['className'] = $className;
            $event['classLocation'] = $classLocation;
            $event['instructorID'] = $instructorID;
            $event['beginHour'] = $beginHour;
            $event['beginMin'] = $beginMin;
            $event['endHour'] = $endHour;
            $event['endMin'] = $endMin;
            $event['endMin'] = $endMin;
            $event['dayOfWeek'] = $dayOfWeek;
            $event['classDescription'] = $classDescription;
            $event['classImageURL'] = $classImageURL;
            $event['firstname'] = $firstname;
            $event['lastname'] = $lastname;
            $event['photoURL'] = $photoURL;
            $event['bio'] = $bio;
            $event['categoryName'] = $categoryName;
            $event['hexColor'] = $hexColor;

            array_push($events, $event);
        }

        return $events;
    }

    /*
     * Find user
     */

    function getUser($findEmail, $findePin) {

        /*
         * $type     = 'testing';
            $type     = mysql_real_escape_string($type);
         */

        //$email = mysqli_real_escape_string($email);
       // $ePin = mysqli_real_escape_string($ePin);


        $stmt = $this->con->prepare("SELECT UID, ePin, balance, resetPin FROM externalusers WHERE email ='$findEmail'");
        $stmt->execute();
        $stmt->bind_result($UID, $ePin, $balance, $resetPin);

        //$users = array();

        $user = array();

        while($stmt->fetch()) {
            $user['UID'] = $UID;
            $user['ePin'] = $ePin;
            $user['balance'] = $balance;
            $user['resetPin'] = $resetPin;
            //array_push($users, $user);
        }

        return $user;

    }

    function getUserEvents($findUID){
        $stmt = $this->con->prepare("SELECT eventID, eventDay, usedSlots, maxSlots, active, className, classLocation,
            c.instructorID, beginHour, beginMin, endHour, endMin, dayOfWeek, classDescription,
            classImageURL, firstname, lastname, photoURL, bio, categoryName, hexColor 
				FROM events e
				LEFT JOIN classes c ON c.classID=e.classID 
				LEFT JOIN instructors i ON i.instructorID = c.instructorID LEFT JOIN classcategories cc ON c.categoryID = cc.categoryID  
				WHERE eventID IN (SELECT eventID FROM registeredevents WHERE UID = '$findUID')");

        $stmt->execute();

        $stmt->bind_result($eventID, $eventDay, $usedSlots, $maxSlots, $active, $className, $classLocation,
            $instructorID, $beginHour, $beginMin, $endHour, $endMin, $dayOfWeek, $classDescription,
            $classImageURL, $firstname, $lastname, $photoURL, $bio, $categoryName, $hexColor);

        $events = array();

        while($stmt->fetch()){
            $event  = array();
            $event['eventID'] = $eventID;
            $event['eventDay'] = $eventDay;
            $event['usedSlots'] = $usedSlots;
            $event['maxSlots'] = $maxSlots;
            $event['active'] = $active;
            $event['className'] = $className;
            $event['classLocation'] = $classLocation;
            $event['instructorID'] = $instructorID;
            $event['beginHour'] = $beginHour;
            $event['beginMin'] = $beginMin;
            $event['endHour'] = $endHour;
            $event['endMin'] = $endMin;
            $event['endMin'] = $endMin;
            $event['dayOfWeek'] = $dayOfWeek;
            $event['classDescription'] = $classDescription;
            $event['classImageURL'] = $classImageURL;
            $event['firstname'] = $firstname;
            $event['lastname'] = $lastname;
            $event['photoURL'] = $photoURL;
            $event['bio'] = $bio;
            $event['categoryName'] = $categoryName;
            $event['hexColor'] = $hexColor;

            array_push($events, $event);
        }

        return $events;

    }

    function resetPin($newPin, $email) {
        $stmt = $this->con->prepare("UPDATE externalusers SET ePin = '$newPin' WHERE email = '$email'");
        $stmt2 = $this->con->prepare("UPDATE externalusers SET resetPin = '0' WHERE email = '$email'");

        if($stmt->execute() && $stmt2->execute())
            return true;
        return false;
    }

}
