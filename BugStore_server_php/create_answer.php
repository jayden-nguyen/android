<?php
require_once "connection.php";
$answer_content = $_POST['answer_content'];
$answer_producer = $_POST['answer_producer'];
$ques_id = $_POST['ques_id'];
$sql = "INSERT INTO answer_forum(ques_id,answer_content,answer_producer) VALUES ('$ques_id','$answer_content','$answer_producer')";
$result = mysqli_query($conn,$sql);
if($result){
    echo "create success";
}else{
    echo "create failed";
}
?>