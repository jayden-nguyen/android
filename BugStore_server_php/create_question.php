<?php
require_once "connection.php";
$producer_name = $_POST['producer_name'];
$ques_title = $_POST['ques_title'];
$ques_content = $_POST['ques_content'];
$time = date("Y-m-d",time());

$sql = "INSERT INTO forum(producer_name,ques_title,ques_content,created_time) VALUES ('$producer_name','$ques_title','$ques_content','$time')";
$result = mysqli_query($conn,$sql);
if($result){
    echo "create question successfully";
}else{
    echo "create failed";
}
?>