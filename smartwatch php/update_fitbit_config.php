<?php
require "mysql_connection.php";
	
$id = $_REQUEST['id'];
$fitbit_username = $_REQUEST['fitbit_username'];
$fitbit_password = $_REQUEST['fitbit_password'];
$user_id = $_REQUEST['user_id'];

$query = "UPDATE fitbit_config SET fitbit_username = '$fitbit_username', fitbit_password = '$fitbit_password', user_id = $user_id WHERE id = $id;";
	
if ($con->query($query) === TRUE) {
    echo "success";
} else {
	echo "Error: " . $query . "<br>" . $con->error;
}
mysqli_close($con);

?>