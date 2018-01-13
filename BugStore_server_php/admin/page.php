<?php
require_once "connection.php";
$limit = 10;
if(isset($_GET['page'])){
    $page = $_GET['page'];
}else{
    $page = 1;
}
//calculate total record
$count_sql = "SELECT COUNT(user_id) FROM user";
$count_result = mysqli_query($conn,$count_sql);
$data = mysqli_fetch_array($count_result);
$total_records = $data[0];
//calculate total page
$total_pages = ceil($total_records/$limit);
//calculate start position
$start_position = ($page - 1) * $limit;
//return data
if(isset($_GET['username_search'])){
    $username_search = $_GET['username_search'];
    $data_sql = "SELECT * FROM user WHERE user_name LIKE '$username_search%' ORDER BY user_id LIMIT $start_position,$limit";
}else{
    $data_sql = "SELECT * FROM user ORDER BY user_id LIMIT $start_position,$limit";
}
$data_result = mysqli_query($conn,$data_sql);
$output=array();
while($row=mysqli_fetch_array($data_result)){
    array_push($output,array('user_id'=>$row['user_id'],'user_name'=>$row['user_name'],'full_name'=>$row['full_name'],'age'=>$row['age'],
        'birth'=>$row['birth'],'email'=>$row['email'],'gender'=>$row['gender'],'total_pages'=>$total_pages));
}
echo json_encode($output);

?>