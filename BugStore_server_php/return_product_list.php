<?php
require_once "connection.php";
$sql = "SELECT * FROM product;";
$query = mysqli_query($conn,$sql);
$product = array();
while($data = mysqli_fetch_array($query)){
    array_push($product,array('product_name'=>$data['product_name'],'price'=>$data['price'],
        'producer_name'=>$data['producer_name'],'image_url'=>$data['image_url'],'product_type'=>$data['product_type']));
}
echo json_encode(array("server_response"=>$product));
?>