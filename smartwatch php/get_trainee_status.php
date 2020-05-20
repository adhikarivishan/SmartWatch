<?php
require "mysql_connection.php";

$trainee_id = $_REQUEST['trainee_id'];
$date = $_REQUEST['date'];
$query = "SELECT u.name, AVG(fd.foot_steps) as foot_steps, AVG(fd.distance) as distance_traveled, AVG(fd.calories) as calories_out, AVG(fd.heart_rate) as heart_rate, AVG(fd.floors) as floors FROM users u JOIN fitbit_data fd ON u.user_id = fd.user_id WHERE fd.user_id = $trainee_id AND fd.date = '$date';";
$result=$con->query($query);
if ($result->num_rows < 1) {
	echo "No data";
}else{
	$finalValue = array();
	while ($row = $result->fetch_assoc()) {
		$trainee_name = $row["name"];
		$foot_steps = $row["foot_steps"];
		$distance_traveled = $row["distance_traveled"];
		$calories_out = $row["calories_out"];
		$heart_rate = $row["heart_rate"];
		$floors = $row["floors"];
		array_push($finalValue, array('trainee_name' => $trainee_name, 'foot_steps' => round($foot_steps), 'distance_traveled' => round($distance_traveled, 2), 'calories_out' => round($calories_out), 'heart_rate' => round($heart_rate), 'floors' => round($floors)));
	}
	echo json_encode($finalValue);
}
mysqli_close($con);

?>
