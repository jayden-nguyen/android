<?php
require_once "connection.php";
$user_name = $_POST['user_name'];
$sql = "SELECT * FROM exchange WHERE producer_name = '$user_name'";
$result = mysqli_query($conn,$sql);
$saler_list = array();
while($data = mysqli_fetch_array($result)){
    array_push($saler_list,array('product_name'=>$data['product_name'],'price'=>$data['price'],'user_name'=>$data['user_name'],'deal_time'=>$data['deal_time']));
}
echo json_encode(array("server_response"=>$saler_list));
?>