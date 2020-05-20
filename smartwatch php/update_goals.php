<?php
require "mysql_connection.php";
	
$foot_steps = $_REQUEST['steps'];
$distance_traveled = $_REQUEST['distance'];
$calories_out = $_REQUEST['calories'];
$floors = $_REQUEST['floors'];
$user_id = $_REQUEST['user_id'];


$query = "UPDATE goals SET foot_steps = $foot_steps,  distance_traveled = $distance_traveled, calories_out = $calories_out, floors = $floors WHERE user_id = $user_id;";
	
if ($con->query($query) === TRUE) {
    echo "success";
} else {
	echo "Error: " . $query . "<br>" . $con->error;
}
mysqli_close($con);

?>