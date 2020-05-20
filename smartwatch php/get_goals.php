<?php
require "mysql_connection.php";

$user_id = $_REQUEST['user_id'];
$query = "SELECT * FROM goals WHERE user_id = $user_id;";
$result=$con->query($query);
if ($result->num_rows < 1) {
	echo "No data";
}else{
	$finalValue = array();
	while ($row = $result->fetch_assoc()) {
		$id = "".$row["id"];
		$foot_steps = $row["foot_steps"];
		$distance_traveled = $row["distance_traveled"];
		$calories_out = $row["calories_out"];
		$floors = $row["floors"];
		array_push($finalValue, array('id' => $id, 'foot_steps' => $foot_steps, 'distance_traveled' => $distance_traveled, 'calories_out' => $calories_out, 'floors' => $floors));
	}
	echo json_encode($finalValue);
}
mysqli_close($con);

?>