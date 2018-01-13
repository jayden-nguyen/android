<?php
require_once "connection.php";
$sql = "SELECT * FROM forum";
$result = mysqli_query($conn,$sql);
$question = array();
while($data = mysqli_fetch_array($result)){
    array_push($question,array('ques_title'=>$data['ques_title'],'ques_content'=>$data['ques_content'],'created_time'=>$data['created_time'],
        'producer_name'=>$data['producer_name'],'ques_id'=>$data['ques_id']));
}
echo json_encode(array('server_response'=>$question));
?>