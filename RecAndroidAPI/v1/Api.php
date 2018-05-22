<?php
/**
 * Created by PhpStorm.
 * User: Cragzeek
 * Date: 2018-05-03
 * Time: 8:01 PM
 */


//getting the dboperation class
require_once '../includes/DbOperation.php';

//function validating all the paramters are available
//we will pass the required parameters to this function
function isTheseParametersAvailable($params){
    //assuming all parameters are available
    $available = true;
    $missingparams = "";

    foreach($params as $param){
        if(!isset($_POST[$param]) || strlen($_POST[$param])<=0){
            $available = false;
            $missingparams = $missingparams . ", " . $param;
        }
    }

    //if parameters are missing
    if(!$available){
        $response = array();
        $response['error'] = true;
        $response['message'] = 'Parameters ' . substr($missingparams, 1, strlen($missingparams)) . ' missing';

        //displaying error
        echo json_encode($response);

        //stopping further execution
        die();
    }
}

//an array to display response
$response = array();

//if it is an v1 call
//that means a get parameter named v1 call is set in the URL
//and with this parameter we are concluding that it is an v1 call
if(isset($_GET['apicall'])){

    switch($_GET['apicall']) {

        //the CREATE operation
        //if the v1 call value is 'createreview'
        //we will create a record in the database
        case 'createreview':

            //creating a new dboperation object
            $db = new DbOperation();

            //creating a new record in the database
            $result = $db->createReview(
                $_POST['classID'],
                $_POST['instructorID'],
                $_POST['reviewText'],
                $_POST['starRating']
            );


            //if the record is created adding success to response
            if ($result) {
                //record is created means there is no error
                $response['error'] = false;

                //in message we have a success message
                $response['message'] = 'Review added successfully';

            } else {

                //if record is not added that means there is an error
                $response['error'] = true;

                //and we have the error message
                $response['message'] = 'Some error occurred please try again';
            }

            break;

        //the READ operation
        //if the call is getreviews
        case 'getreviews':
            $db = new DbOperation();
            $response['error'] = false;
            $response['message'] = 'Request successfully completed';
            $response['reviews'] = $db->getReviews();
            break;

        //the READ operation
        //if the call is getevents
        case 'getevents':
            $db = new DbOperation();
            $response['error'] = false;
            $response['message'] = 'Request successfully completed';
            $response['events'] = $db->getEvents();
            break;


        case 'getuser':
            $db = new DbOperation();
            $response['error'] = false;
            $response['message'] = 'Request sucessfully completed';
            $response['user'] = $db->getUser($_POST['email'], $_POST['ePin']);
            break;

        case "getuserevents":
            $db = new DbOperation();
            $response['error'] = false;
            $response['message'] = 'Request sucessfully completed';
            $response['events'] = $db->getUserEvents($_POST['UID']);
            break;

        case "resetpin":
            $db = new DbOperation();
            $response['error'] = false;
            $response['message'] = 'New pin has been set';
            $db->resetPin(
                $_POST['newPin'],
                $_POST['email']
            );
            break;

        case "removeevent":
            $db = new DbOperation();
            $response['error'] = false;
            $response['message'] = 'Event has been removed';
            $db->removeEvent(
                $_POST['UID'],
                $_POST['eventID']
            );
            break;

        case "getclasses":
            $db = new DbOperation();
            $response['error'] = false;
            $response['message'] = 'Classes have been retrieved';
            $response['classes'] = $db->getClasses();
            break;

        case "getinstructors":
            $db = new DbOperation();
            $response['error'] = false;
            $response['message'] = 'Instructors have been retrieved';
            $response['instructors'] = $db->getInstructors();
            break;

        case "regclass":
            $db = new DbOperation();
            $response['error'] = false;
            $response['message'] = 'User is registered!';
            $response['success'] = $db->registerUser(
                $_POST['uid'],
                $_POST['classid']
            );
            break;

        case "mailuserevents":
            $db = new DbOperation();
            $response['error'] = false;
            $response['message'] = 'm';
            $response['mail'] = $db->mailUserSchedule($_POST['UID']);
            break;
    }

}else{
    //if it is not v1 call
    //pushing appropriate values to response array
    $response['error'] = true;
    $response['message'] = 'Invalid API Call';
}

//displaying the response in json structure
echo json_encode($response);
