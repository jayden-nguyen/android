<?php 
require_once "connection.php";
$time = date("Y-m-d H:i:s");
$sql = "UPDATE exchange SET deal_time = '$time' Where deal_id > 0";
$result = mysqli_query($conn,$sql);
$sql1="SELECT * from exchange where deal_id = 1";
$result1 = mysqli_query($conn,$sql1);
$data = mysqli_fetch_array($result1);
echo $data ['deal_time'];

?>