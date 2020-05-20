<?php
require "mysql_connection.php";

	$foot_steps = $_REQUEST['steps'];
	$distance_traveled = $_REQUEST['distance'];
	$calories_out = $_REQUEST['calories'];
	$floors = $_REQUEST['floors'];
	$user_id = $_REQUEST['user_id'];
	
	$query = "INSERT INTO goals (foot_steps, distance_traveled, calories_out, floors, user_id) 
				VALUES($foot_steps, $distance_traveled, $calories_out, $floors, $user_id);";
	
	if ($con->query($query) === TRUE) {
	    echo "success";
	} else {
		echo "Error: " . $query . "<br>" . $con->error;
	}
	mysqli_close($con);

?>