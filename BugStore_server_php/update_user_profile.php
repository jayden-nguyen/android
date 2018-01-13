<?php
require_once "connection.php";
$old_user_name = $_POST['old_user_name'];
$user_name = $_POST['user_name'];
$full_name = $_POST['full_name'];
$age = $_POST['age'];
$email = $_POST['email'];
$birth = $_POST['birth'];
$password = $_POST['password'];
$gender = $_POST['gender'];

$sql = "UPDATE user SET user_name = '$user_name',full_name = '$full_name', age = '$age', email = '$email',birth = '$birth',
password = '$password',gender = '$gender' WHERE user_name = '$old_user_name'";
$result = mysqli_query($conn,$sql);
if($result){
    echo "update success";
}
else{
    echo "update failed";
}
?>