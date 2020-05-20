<?php
require "mysql_connection.php";

$trainee_id = $_REQUEST['trainee_id'];
$query = "SELECT u.name, u.address, u.phone, u.email FROM users u JOIN trainee t ON u.user_id = t.trainer_id WHERE t.trainee_id = $trainee_id;";
$result=$con->query($query);
if ($result->num_rows < 1) {
	echo "No User";
}else{
	$finalValue = array();
	while ($row = $result->fetch_assoc()) {
		$trainer_name = $row["name"];
		$trainer_address = $row["address"];
		$trainer_phone = $row["phone"];
		$trainer_email = $row["email"];
		array_push($finalValue, array('trainer_name' => $trainer_name, 'trainer_address' => $trainer_address, 'trainer_phone' => $trainer_phone, 'trainer_email' => $trainer_email));
	}
	echo json_encode($finalValue);
}
mysqli_close($con);

?>
