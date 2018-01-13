<?php
require_once "connection.php";
$user_name = $_POST['user_name'];
$sql = "SELECT * FROM user where user_name = '$user_name'";
$result = mysqli_query($conn,$sql);
$data = mysqli_fetch_array($result);
$profile = array();
array_push($profile,array('user_name'=>$data['user_name'],'full_name'=>$data['full_name'],'age'=>$data['age'],'birth'=>$data['birth'],'gender'=>$data['gender'],'email'=>$data['email'],'password'=>$data['password']));
echo json_encode(array('server_response'=>$profile));
?>