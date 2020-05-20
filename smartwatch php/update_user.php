<?php
require "mysql_connection.php";
	
$user_id = $_REQUEST['user_id'];
$name = $_REQUEST['name'];
$address = $_REQUEST['address'];
$phone = $_REQUEST['phone'];
$email = urldecode($_REQUEST['email']);


$query = "UPDATE users SET name = '$name',  address = '$address', phone = '$phone', email = '$email' WHERE user_id = $user_id;";
	
if ($con->query($query) === TRUE) {
    echo "success";
} else {
	echo "Error: " . $query . "<br>" . $con->error;
}
mysqli_close($con);

?>