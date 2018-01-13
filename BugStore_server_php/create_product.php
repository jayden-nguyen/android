<?php
require_once "connection.php";
$product_name = $_POST['product_name'];
$product_type = $_POST['product_type'];
$product_price = $_POST['product_price'];
$product_producer = $_POST['product_producer'];
$product_image_name = $_POST['product_image_name'];
$product_image = $_POST['product_image'];

$upload_path = "Images/$product_image_name.jpg";
$image_url = "http://192.168.1.135/Bugstore_app/".$upload_path;

$sql = "INSERT INTO product (product_name,producer_name,product_type,price,image_name,image_url) VALUES ('$product_name','$product_producer','$product_type','$product_price','$product_image_name','$image_url')";
$result = mysqli_query($conn,$sql);
if($result){
    file_put_contents($upload_path, base64_decode($product_image));
    echo "create product success";
}else{
    echo json_encode(array("server_response"=>"something went wrong"));
}
?>