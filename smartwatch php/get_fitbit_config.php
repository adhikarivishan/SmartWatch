<?php
require "mysql_connection.php";

$user_id = $_REQUEST['user_id'];
$query = "SELECT * FROM fitbit_config WHERE user_id = $user_id;";
$result=$con->query($query);
if ($result->num_rows < 1) {
	echo "No data";
}else{
	$finalValue = array();
	while ($row = $result->fetch_assoc()) {
		$id = "".$row["id"];
		$fitbit_username = $row["fitbit_username"];
		$fitbit_password = $row["fitbit_password"];
		array_push($finalValue, array('id' => $id, 'fitbit_username' => $fitbit_username, 'fitbit_password' => $fitbit_password));
	}
	echo json_encode($finalValue);
}
mysqli_close($con);

?>