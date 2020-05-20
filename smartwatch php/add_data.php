<?php
require "mysql_connection.php";

	$user_id = $_REQUEST['user_id'];
	$date = $_REQUEST['date'];
	$steps = $_REQUEST['steps'];
	$distance = $_REQUEST['distance'];
	$calories = $_REQUEST['calories'];
	$heart_rate = $_REQUEST['heart_rate'];
	$floors = $_REQUEST['floors'];

	$query = "INSERT INTO fitbit_data (user_id, date, foot_steps, distance, calories, heart_rate, floors) 
				VALUES($user_id, '$date', $steps, $distance,  $calories, $heart_rate, $floors);";
	
	if ($con->query($query) === TRUE) {
	    echo "success";
	} else {
		echo "Error: " . $query . "<br>" . $con->error;
	}
	mysqli_close($con);

?>