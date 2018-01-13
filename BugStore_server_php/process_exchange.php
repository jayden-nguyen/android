<?php
require_once "connection.php";
$product_name = $_POST['product_name'];
$product_price = $_POST['product_price'];
$producer_name = $_POST['producer_name'];
$user_name = $_POST['user_name'];
$date = date("Y-m-d H:i:s");

// Calculate Infinity per game and total income
$income_game = ((float)$product_price)*0.2;
$sql1 = "SELECT * FROM exchange WHERE deal_id = (SELECT max(deal_id) FROM exchange)" ;
$query1 = mysqli_query($conn,$sql1);
echo $income_game;
$data = mysqli_fetch_array($query1);
$income_total = $data['infinity_total']+$income_game;
echo $income_total;
//Insert to exchange table
$sql2 = "INSERT INTO exchange (product_name,price,user_name,producer_name,deal_time,infinity_game,infinity_total) VALUES ('$product_name','$product_price','$user_name','$producer_name','$date','$income_game','$income_total')";
$query2 = mysqli_query($conn,$sql2);
if($query2){
    echo json_encode(array("server_response"=>"update success"));
}else{
    echo json_encode(array("server_response"=>"update failed"));
}
// Access user table to minus money of user
$sql3 = "SELECT * FROM user WHERE user_name ='$user_name'";
$query3 = mysqli_query($conn,$sql3);
$data1 = mysqli_fetch_array($query3);
$account_after_buy = $data1['credit_account'] - (float)$product_price;
$sql4 = "UPDATE user SET credit_account = '$account_after_buy' WHERE user_name = '$user_name'";
$query4 = mysqli_query($conn,$sql4);
//Access user table to plus money for producer
$sql5 = "SELECT * FROM user WHERE user_name = '$producer_name'";
$query5 = mysqli_query($conn,$sql5);
$data2 = mysqli_fetch_array($query5);
$account_after_sale =$data2['credit_account']+((float)$product_price)*0.8;
$sql6 = "UPDATE user SET credit_account = '$account_after_sale' WHERE user_name = '$producer_name'";
$query6 = mysqli_query($conn,$sql6);

?>