<?php
require "mysql_connection.php";

$trainer_id = $_REQUEST['trainer_id'];
$query = "SELECT u.name, u.address, u.phone, u.email, u.password, u.account_type, t.trainee_id FROM trainee t JOIN users u ON t.trainee_id =u.user_id  WHERE t.trainer_id = $trainer_id;";
$result=$con->query($query);
if ($result->num_rows < 1) {
	echo "No User";
}else{
	$finalValue = array();
	while ($row = $result->fetch_assoc()) {
		$name = $row["name"];
		$address = $row["address"];
		$phone = $row["phone"];
		$email = $row["email"];
		$trainee_id = "".$row["trainee_id"];
		array_push($finalValue, array('name' => $name, 'address' => $address, 'phone' => $phone, 'email' => $email, 'trainee_id' => $trainee_id));
	}
	echo json_encode($finalValue);
}
mysqli_close($con);

?>
