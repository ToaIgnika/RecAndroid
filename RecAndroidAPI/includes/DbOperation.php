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
    function createReview($classID, $instructorID, $reviewText, $starRating){
        $reviewID = $this->gen_uuid();
        $timeStamp = time();

        $stmt = $this->con->prepare("INSERT INTO reviews (reviewID, instructorID, classID, reviewText, starRating, timeStamp) VALUES (?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("ssssds", $reviewID, $instructorID, $classID, $reviewText, $starRating, $timeStamp);
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

    function getClasses(){
        $stmt = $this->con->prepare("SELECT classID, className FROM classes GROUP BY className");
        $stmt->execute();
        $stmt->bind_result($classID, $className);

        $classes = array();

        while($stmt->fetch()){
            $class  = array();
            $class['classID'] = $classID;
            $class['className'] = $className;
            array_push($classes, $class);
        }

        return $classes;
    }

    function getInstructors(){
        $stmt = $this->con->prepare("SELECT instructorID, firstName, lastName FROM instructors");
        $stmt->execute();
        $stmt->bind_result($instructorID, $firstName, $lastName);

        $instructors = array();

        while($stmt->fetch()){
            $instructor  = array();
            $instructor['instructorID'] = $instructorID;
            $instructor['firstName'] = $firstName;
            $instructor['lastName'] = $lastName;
            array_push($instructors, $instructor);
        }

        return $instructors;
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

    function registerUser($uid, $eventid) {
        // set the balance plus one
        $stmt1 = $this->con->prepare("UPDATE externalusers SET balance = balance - 1 WHERE UID = '$uid'");
        $stmt2 = $this->con->prepare("INSERT INTO registeredevents (UID, eventID) VALUES (?, ?)");
        $stmt2->bind_param("ss", $uid, $eventid);
        $stmt3 = $this->con->prepare("UPDATE events SET usedslots = usedSlots + 1 WHERE eventID = '$eventid' ");
        if($stmt1->execute()  && $stmt2->execute() && $stmt3->execute()) {
            return true;
        } else {
            return false;
        }
    }

    function resetPin($newPin, $email) {
        $stmt = $this->con->prepare("UPDATE externalusers SET ePin = '$newPin' WHERE email = '$email'");
        $stmt2 = $this->con->prepare("UPDATE externalusers SET resetPin = '0' WHERE email = '$email'");

        if($stmt->execute() && $stmt2->execute())
            return true;
        return false;
    }

    function removeEvent($UID, $eventID) {
        // set the balance plus one
        $stmt1 = $this->con->prepare("UPDATE externalusers SET balance = balance + 1 WHERE UID = '$UID'");
        $stmt2 = $this->con->prepare("DELETE from registeredevents WHERE UID = '$UID' AND eventID ='$eventID' ");
        $stmt3 = $this->con->prepare("UPDATE events SET usedslots = usedSlots - 1 WHERE eventID = '$eventID' ");

        if($stmt1->execute()  && $stmt2->execute() && $stmt3->execute()) {
            return true;
        } else {
            return false;
        }
    }

    function gen_uuid() {
        return sprintf( '%04x%04x-%04x-%04x-%04x-%04x%04x%04x',
            // 32 bits for "time_low"
            mt_rand( 0, 0xffff ), mt_rand( 0, 0xffff ),

            // 16 bits for "time_mid"
            mt_rand( 0, 0xffff ),

            // 16 bits for "time_hi_and_version",
            // four most significant bits holds version number 4
            mt_rand( 0, 0x0fff ) | 0x4000,

            // 16 bits, 8 bits for "clk_seq_hi_res",
            // 8 bits for "clk_seq_low",
            // two most significant bits holds zero and one for variant DCE1.1
            mt_rand( 0, 0x3fff ) | 0x8000,

            // 48 bits for "node"
            mt_rand( 0, 0xffff ), mt_rand( 0, 0xffff ), mt_rand( 0, 0xffff )
        );
    }

}
