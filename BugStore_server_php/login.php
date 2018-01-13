<?php
require_once 'connection.php';
    $user_name = $_POST['user_name'];
    $password = $_POST['password'];

    $sql = "SELECT * FROM user WHERE user_name = '$user_name' and password = '$password'";
    $result = mysqli_query($conn,$sql);
    if(mysqli_num_rows($result)==0){
        echo json_encode(array("server_response"=>"This account hasn't existed"));
    }else{
        echo json_encode(array("server_response"=>"Login success"));
    }
?>