<?php
require_once "connection.php";
$limit = 10;
if(isset($_GET['page'])){
    $page = $_GET['page'];
}else{
    $page = 1;
}
//calculate total record
$count_sql = "SELECT COUNT(product_id) FROM product";
$count_result = mysqli_query($conn,$count_sql);
$data = mysqli_fetch_array($count_result);
$total_records = $data[0];
//calculate total page
$total_pages = ceil($total_records/$limit);
//calculate start position
$start_position = ($page - 1) * $limit;
//return data
if(isset($_GET['productname_search'])){
    $productname_search = $_GET['productname_search'];
    $data_sql = "SELECT * FROM product WHERE product_name LIKE '$productname_search%' ORDER BY product_id LIMIT $start_position,$limit";
}else{
    $data_sql = "SELECT * FROM product ORDER BY product_id LIMIT $start_position,$limit";
}
$data_result = mysqli_query($conn,$data_sql);
$output=array();
while($row=mysqli_fetch_array($data_result)){
    array_push($output,array('product_id'=>$row['product_id'],'product_name'=>$row['product_name'],'product_type'=>$row['product_type'],'price'=>$row['price'],
       'producer_name'=>$row['producer_name'],'total_pages'=>$total_pages));
}
echo json_encode($output);

?>