<?php
require_once "connection.php";
$user_name = $_POST['user_name'];
$sql = "SELECT * FROM user WHERE user_name = '$user_name'";
$result = mysqli_query($conn,$sql);
$data =mysqli_fetch_array($result);
echo json_encode(array("server_response"=> $data['credit_account']));
?>