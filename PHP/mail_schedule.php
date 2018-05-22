<?php
/**
 * Created by PhpStorm.
 * User: Toa
 * Date: 5/22/2018
 * Time: 12:15 AM
 */

use PHPMailer\PHPMailer\PHPMailer;

require 'PHPMailer/src/Exception.php';
require 'PHPMailer/src/PHPMailer.php';
include_once 'RecAndroidAPI/includes/Constants.php';

echo "was here";

if ($_POST["email"]) {
    process_mailqueue($_POST["email"]);
}




function process_mailqueue($mail) {
    try {
        $db = db();
        echo "was here";
        $current_time = time();
        $sql = "SELECT * FROM classes ORDER BY dayOfWeek, beginHour, beginMin";
        $result = $db->query($sql);
        $mail = new PHPMailer();
        $str = "Hello!\nPlease, see the BCIT Rec Center schedule below:\n";

        if ($result->num_rows > 0) {
            // output data of each row
            $mail = new PHPMailer();
            $mail->setFrom('bcit.rec.center@noreply.com', 'BCIT Rec Center');
            $mail->addAddress($mail);
            $wd = 1;
            while($row = $result->fetch_assoc()) {
                if ($wd == 1) {
                    $str .= "Monday:\n";
                } else if ($wd == 2) {
                    $str .= "Tuesday:\n";
                } else if ($wd == 3) {
                    $str .= "Wednesday:\n";
                } else if ($wd == 4) {
                    $str .= "Thursday:\n";
                } else {
                    $str .= "Friday:\n";
                }
                while ($wd == $row["dayOfWeek"]) {
                    $str .= $row["beginHour"] . ":" . $row["beginMin"] . "-" . $row["endHour"] . ":" . $row["endMin"] . ", ".$row["className"] . " - " . $row["classLocation"];
                }
                $wd++;
            }

            //Content
            $mail->Subject = "BCIT Rec Center Event Schedule";
            $mail->Body = $str;
            $mail->send();
        } else {
            return false;
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

