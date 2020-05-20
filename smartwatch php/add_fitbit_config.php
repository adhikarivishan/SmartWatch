<?php
require "mysql_connection.php";

	$fitbit_username = $_REQUEST['fitbit_username'];
	$fitbit_password = $_REQUEST['fitbit_password'];
	$user_id = $_REQUEST['user_id'];
	$query = "INSERT INTO fitbit_config (fitbit_username, fitbit_password, user_id)  
				VALUES('$fitbit_username', '$fitbit_password', $user_id);";
	
	if ($con->query($query) === TRUE) {
	    echo "success";
	} else {
		echo "Error: " . $query . "<br>" . $con->error;
	}
	mysqli_close($con);

?>