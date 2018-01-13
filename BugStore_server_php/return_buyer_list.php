<?php
require_once "connection.php";
$user_name = $_POST['user_name'];
$sql = "SELECT * FROM exchange WHERE user_name = '$user_name'";
$result = mysqli_query($conn,$sql);
$buyer_list = array();
while($data = mysqli_fetch_array($result)){
    array_push($buyer_list,array('product_name'=>$data['product_name'],'price'=>$data['price'],'producer_name'=>$data['producer_name'],'deal_time'=>$data['deal_time']));
}
echo json_encode(array("server_response"=>$buyer_list));
?>