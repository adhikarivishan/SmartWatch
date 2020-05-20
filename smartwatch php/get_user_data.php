<?php
require "mysql_connection.php";


	$username=$_REQUEST['username'];
	$password=$_REQUEST['password'];
	$query = "SELECT * FROM users WHERE email='$username' AND password='$password';";
	$result=$con->query($query);
	if ($result->num_rows < 1) {
		echo "No User";
	}else{
		$row = $result->fetch_assoc();
		$id = "".$row["id"];
		$name = $row["name"];
		$address = $row["address"];
		$phone = "".$row["phone"];
		$email = $row["email"];
		$password = $row["password"];
		$account_type = $row["account_type"];

		$finalValue = array();
		array_push($finalValue, array('id' => $id, 'name' => $name, 'address' => $address, 'phone' => $phone, 'email' => $email, 'password' => $password, 'account_type' => $account_type));
		echo json_encode($finalValue);
	}
	mysqli_close($con);

?>