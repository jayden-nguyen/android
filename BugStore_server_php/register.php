<?php
require_once "connection.php";
$user_name = $_POST['user_name'];
$email = $_POST['email'];
$password = $_POST['password'];

$sql = "INSERT INTO user(user_name,email,password) VALUES ('$user_name','$email','$password')";
$result = mysqli_query($conn,$sql);
if($result){
    echo json_encode(array("server_response"=>"Create account success"));
}else{
    echo json_encode(array("server_response"=>"Create failed"));
}
?>