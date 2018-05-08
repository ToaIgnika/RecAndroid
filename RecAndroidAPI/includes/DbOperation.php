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
        $stmt = $this->con->prepare("SELECT timeSlot, eventName, eventDescription FROM events");
        $stmt->execute();
        $stmt->bind_result($timeSlot, $eventName, $eventDescription);

        $events = array();

        while($stmt->fetch()){
            $event  = array();
            $event['timeSlot'] = $timeSlot;
            $event['eventName'] = $eventName;
            $event['eventDescription'] = $eventDescription;
            array_push($events, $event);
        }
        return $events;
    }

    function getAllEvents() {
        $stmt = $this->con->prepare("SELECT * FROM events");
        $stmt->bind_param('timeSlot',$id);
        $stmt->execute();
        $res = $stmt->get_result();
        $events = array();

        while($r =$res->fetch_assoc()){
            $events[] = $r; //  = array();
        }
        return $events;
    }
}