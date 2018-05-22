<?php
/**
 * Created by PhpStorm.
 * User: Toa
 * Date: 5/18/2018
 * Time: 6:50 PM
 */

use PHPMailer\PHPMailer\PHPMailer;

require 'PHPMailer/src/Exception.php';
require 'PHPMailer/src/PHPMailer.php';
include_once 'RecAndroidAPI/includes/Constants.php';

echo "was here";
process_mailqueue();

function process_mailqueue() {
    try {
        $db = db();
        echo "was here";
        $current_time = time();
        $sql = "SELECT eventID, message FROM mailqueue WHERE timestamp < '$current_time' AND active = '1' ";
        $result = $db->query($sql);
        $mail = new PHPMailer();
        if ($result->num_rows > 0) {
            // output data of each row
            while($row = $result->fetch_assoc()) {
                $mail = new PHPMailer();
                $mail->setFrom('bcit.rec.center@noreply.com', 'BCIT Rec Center');
                $mail->addAddress('eugene.my88@gmail.com', 'hello');               // Name is optional

                echo "id: " . $row["eventID"]."<br>";
                $eID = $row["eventID"];
                $sql2 = "SELECT email FROM mailqueue m 
                    LEFT JOIN events e ON m.eventID = e.eventID
                    LEFT JOIN registeredevents r ON e.eventID = r.eventID
                    LEFT JOIN externalusers eu ON r.UID = eu.UID
                    WHERE m.eventid = '$eID'";
                $result2 = $db->query($sql2);
                if ($result2->num_rows > 0) {
                    while ($row2 = $result2->fetch_assoc()) {
                        echo "email: " . $row2["email"]."<br>";
                        $mail->addBCC($row2["email"]);
                    }
                }
                // loaded
                $sql3 = "SELECT email FROM mailqueue m 
                    LEFT JOIN events e ON m.eventID = e.eventID
                    LEFT JOIN registeredevents r ON e.eventID = r.eventID
                    LEFT JOIN emailreserved eu ON e.classID = eu.classID
                    WHERE m.eventid = '$eID'";
                $result3 = $db->query($sql3);
                if ($result3->num_rows > 0) {
                    while ($row3 = $result3->fetch_assoc()) {
                        if (!empty($row["email"])) {
                            echo "email: " . $row3["email"]."<br>";
                            $mail->addBCC($row3["email"]);
                        }
                    }
                }

                //Content
                $mail->Subject = $row["message"]    ;
                $mail->Body = $row["message"];
                $mail->send();
                echo 'Message has been sent';
                $sql = "UPDATE mailqueue SET active='0' WHERE eventid='$eID'";
                if ($db->query($sql) === TRUE) {
                    echo "Record updated successfully";
                }
            }
        } else {
            echo "0 results";
        }
        $db->close();
    } catch (\Exception $e) {
        echo 'Message could not be sent. Mailer Error: ', $mail->ErrorInfo;
    }
}

function db () {
    $con = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
    if ($con->connect_error) {
        die("Connection failed: " . $con->connect_error);
    }
    return $con;
}

