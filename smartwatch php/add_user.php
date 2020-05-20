<?php
require "mysql_connection.php";

	$name = $_REQUEST['name'];
	$address = $_REQUEST['address'];
	$phone = $_REQUEST['phone'];
	$email = urldecode($_REQUEST['email']);
	$password = $_REQUEST['password'];
	$account_type = $_REQUEST['account_type'];

	$query = "INSERT INTO users (name, address, phone, email, password, account_type) 
				VALUES('$name', '$address', '$phone', '$email',  '$password', '$account_type');";
	
	if ($con->query($query) === TRUE) {
	    echo "success";
	} else {
		echo "Error: " . $query . "<br>" . $con->error;
	}
	mysqli_close($con);

?>