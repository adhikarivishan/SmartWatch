<?php
require "mysql_connection.php";
$new_password = $_POST["new_password"];
$user_id = $_POST["user_id"];
$query = "UPDATE users SET password = '$new_password' WHERE user_id = $user_id;";
if ($con->query($query) === TRUE) {
    echo "Password changed successfully";
} else {
	echo "Error: " . $query . "<br>" . $con->error;
}
mysqli_close($con);

?>