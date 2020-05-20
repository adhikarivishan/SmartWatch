<?php
require "mysql_connection.php";

$trainee_id = $_REQUEST['trainee_id'];
$query = "SELECT name, address, phone, email FROM users WHERE user_id = $trainee_id;";
$result=$con->query($query);
if ($result->num_rows < 1) {
	echo "No User";
}else{
	$finalValue = array();
	while ($row = $result->fetch_assoc()) {
		$trainee_name = $row["name"];
		$trainee_address = $row["address"];
		$trainee_phone = $row["phone"];
		$trainee_email = $row["email"];
		array_push($finalValue, array('trainee_name' => $trainee_name, 'trainee_address' => $trainee_address, 'trainee_phone' => $trainee_phone, 'trainee_email' => $trainee_email));
	}
	echo json_encode($finalValue);
}
mysqli_close($con);

?>
