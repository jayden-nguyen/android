<?php
require_once "connection.php";
$sql = "UPDATE user SET status = $select_value WHERE ";
$result = mysqli_query($conn,$sql);
if($result){
    echo "OK";
}else{
    echo "failed";
}
?>