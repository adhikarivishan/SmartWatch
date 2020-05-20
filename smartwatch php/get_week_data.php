<?php

$cURLConnection = curl_init();
$user_id= $_REQUEST['user_id'];//'85GCKB';
$access_token = $_REQUEST['access_token'];
$activity = $_REQUEST['activity'];
curl_setopt($cURLConnection, CURLOPT_URL, 'https://api.fitbit.com/1/user/'.$user_id.'/activities/'.$activity.'/date/today/1w.json');
//curl_setopt($cURLConnection, CURLOPT_URL, 'https://api.fitbit.com/1/user/'.$user_id.'/activities/heart/date/today/1d.json');
curl_setopt($cURLConnection, CURLOPT_RETURNTRANSFER, true);
curl_setopt($cURLConnection, CURLOPT_HTTPHEADER, array(
		'Authorization: Bearer '.$access_token
));

$apiResponse = curl_exec($cURLConnection);
	
echo json_decode(json_encode($apiResponse), true);

curl_close($cURLConnection);


?>