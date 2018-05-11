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
        $stmt = $this->con->prepare("SELECT classID, className, classLocation, instructorID, reservedSlots, availableSlots, beginDate, endDate, beginHour, beginMin, endHour, endMin, dayOfWeek, classDescription, classImageURL FROM classes");
        $stmt->execute();
        $stmt->bind_result($classID, $className, $classLocation, $instructorID, $reservedSlots, $availableSlots, $beginDate, $endDate, $beginHour, $beginMin, $endHour, $endMin, $dayOfWeek, $classDescription, $classImageURL);

        $events = array();

        while($stmt->fetch()){
            $event  = array();
            $event['classID'] = $classID;
            $event['className'] = $className;
            $event['instructorID'] = $instructorID;
            $event['reservedSlots'] = $reservedSlots;
            $event['availableSlots'] = $availableSlots;
            $event['beginDate'] = $beginDate;
            $event['endDate'] = $endDate;
            $event['beginHour'] = $beginHour;
            $event['beginMin'] = $beginMin;
            $event['endHour'] = $endHour;
            $event['endMin'] = $endMin;
            $event['dayOfWeek'] = $dayOfWeek;
            $event['classDescription'] = $classDescription;
            $event['classImageURL'] = $classImageURL;

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


        $stmt = $this->con->prepare("SELECT email, ePin, balance FROM externalusers WHERE email ='$findEmail'");
        $stmt->execute();
        $stmt->bind_result($email, $ePin, $balance);

        //$users = array();

        $user = array();

        while($stmt->fetch()) {
            $user['email'] = $email;
            $user['ePin'] = $ePin;
            $user['balance'] = $balance;

            //array_push($users, $user);
        }

        return $user;

    }

    function getUserEvents($findEmail){
        $stmt = $this->con->prepare("SELECT classID, className, classLocation, instructorID, reservedSlots, availableSlots, beginDate, endDate, beginHour, beginMin, endHour, endMin, dayOfWeek, classDescription, classImageURL 
                                            FROM classes 
                                            WHERE classID IN (SELECT classID FROM registeredclasses WHERE registeredclasses.email='$findEmail')");
        /*WHERE email ='$findEmail'\") */
        $stmt->execute();
        $stmt->bind_result($classID, $className, $classLocation, $instructorID, $reservedSlots, $availableSlots, $beginDate, $endDate, $beginHour, $beginMin, $endHour, $endMin, $dayOfWeek, $classDescription, $classImageURL);


        $events = array();

        while($stmt->fetch()) {
            $event  = array();
            $event['classID'] = $classID;
            $event['className'] = $className;
            $event['instructorID'] = $instructorID;
            $event['reservedSlots'] = $reservedSlots;
            $event['availableSlots'] = $availableSlots;
            $event['beginDate'] = $beginDate;
            $event['endDate'] = $endDate;
            $event['beginHour'] = $beginHour;
            $event['beginMin'] = $beginMin;
            $event['endHour'] = $endHour;
            $event['endMin'] = $endMin;
            $event['dayOfWeek'] = $dayOfWeek;
            $event['classDescription'] = $classDescription;
            $event['classImageURL'] = $classImageURL;

            array_push($events, $event);
        }
        return $events;

    }

}