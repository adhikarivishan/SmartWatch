<?php
require "mysql_connection.php";


	$email = $_REQUEST['email'];
	$password = $_REQUEST['password'];
	$query = "SELECT u.user_id, u.name, u.address, u.phone, u.email, u.password, u.account_type, f.id, f.fitbit_username, f.fitbit_password FROM users u LEFT JOIN fitbit_config f ON u.user_id = f.user_id WHERE email='$email' AND password='$password';";
	$result=$con->query($query);
	if ($result->num_rows < 1) {
		echo "No User";
	}else{
		$row = $result->fetch_assoc();
		$user_id = "".$row["user_id"];
		$name = $row["name"];
		$address = $row["address"];
		$phone = "".$row["phone"];
		$email = $row["email"];
		$password = $row["password"];
		$account_type = $row["account_type"];
		$fitbit_id = "".$row["id"];
		$fitbit_username = $row["fitbit_username"];
		$fitbit_password = $row["fitbit_password"];


		$finalValue = array();
		array_push($finalValue, array('user_id' => $user_id, 'name' => $name, 'address' => $address, 'phone' => $phone, 'email' => $email, 'password' => $password, 'account_type' => $account_type, 'fitbit_id' => $fitbit_id, 'fitbit_username' => $fitbit_username, 'fitbit_password' => $fitbit_password));
		echo json_encode($finalValue);
	}
	mysqli_close($con);

?>