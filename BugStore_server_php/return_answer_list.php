<?php
require_once "connection.php";
$ques_id = $_POST['ques_id'];
$sql = "SELECT * FROM answer_forum WHERE ques_id = '$ques_id'";
$result = mysqli_query($conn,$sql);
$answer = array();
while($data = mysqli_fetch_array($result)){
    array_push($answer,array('answer_id'=>$data['answer_id'],'answer_content'=>$data['answer_content'],'answer_producer'=>$data['answer_producer']));
}
echo json_encode(array('server_response'=>$answer));
?>