<?php
require "mysql_connection.php";

$query = "SELECT * FROM users;";
$result=$con->query($query);
if ($result->num_rows < 1) {
	echo "No User";
}else{
	$finalValue = array();
	while ($row = $result->fetch_assoc()) {
		$user_id = "".$row["user_id"];
		$name = $row["name"];
		$address = $row["address"];
		$phone = $row["phone"];
		$email = $row["email"];
		$account_type = $row["account_type"];
		array_push($finalValue, array('user_id' => $user_id, 'name' => $name, 'address' => $address, 'phone' => $phone, 'email' => $email, 'account_type' => $account_type));
	}
	echo json_encode($finalValue);
}
mysqli_close($con);

?>
