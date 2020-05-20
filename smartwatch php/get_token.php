<?php

	$username = $_REQUEST['username'];
	$password = $_REQUEST['password'];
	$cURLConnection = curl_init('https://api.fitbit.com/oauth2/token?grant_type=password&username='.$username.'&password='.$password.'&client_id=228TQF');
	curl_setopt($cURLConnection, CURLOPT_POSTFIELDS, '');
	curl_setopt($cURLConnection, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($cURLConnection, CURLOPT_HTTPHEADER, array(
    	 'Content-Type: application/x-www-form-urlencoded'
	)
	);

	$apiResponse = curl_exec($cURLConnection);
	// $apiResponse - available data from the API request
	// echo gettype(json_decode(json_encode($apiResponse), true));
	echo json_decode(json_encode($apiResponse), true);

	curl_close($cURLConnection);

?>