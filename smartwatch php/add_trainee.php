<?php
require "mysql_connection.php";

	$trainer_id = $_REQUEST['trainer_id'];
	$trainee_id = $_REQUEST['trainee_id'];
	$query = "INSERT INTO trainee (trainer_id, trainee_id)  
				VALUES($trainer_id, $trainee_id);";
	
	if ($con->query($query) === TRUE) {
	    echo "success";
	} else {
		echo "Error: " . $query . "<br>" . $con->error;
	}
	mysqli_close($con);

?>