<?php
require_once "connection.php";
?>
<?php
if(isset($_GET['id_delete'])){
    $id_delete = $_GET['id_delete'];
    echo $id_delete;
    $delete_sql = "DELETE FROM user WHERE user_id ='$id_delete'";
    $delete_result = mysqli_query($conn,$delete_sql);
}?>
<?php
$sql = "SELECT COUNT(user_id) FROM user";
$result = mysqli_query($conn,$sql);
$data = mysqli_fetch_array($result);
$user_total = $data[0];
$sql1 = "SELECT COUNT(product_id) FROM product";
$result1 = mysqli_query($conn,$sql1);
$data1 = mysqli_fetch_array($result1);
$product_total = $data1[0];
$sql2 = "SELECT COUNT(deal_id) FROM exchange";
$result2 = mysqli_query($conn,$sql2);
$data2 = mysqli_fetch_array($result2);
$exchange_total = $data2[0];
$sql3 = "SELECT * FROM exchange WHERE deal_id = (SELECT max(deal_id) FROM exchange)";
$result3 = mysqli_query($conn,$sql3);
$data3 = mysqli_fetch_array($result3);
$total_account = $data3['infinity_total'];
?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        body {
            font-family: "Lato", sans-serif;
        }
        .sidenav {
            height: 100%;
            width: 0;
            position: fixed;
            z-index: 1;
            top: 0;
            left: 0;
            background-color: 	#005b96;
            overflow-x: hidden;
            transition: 0.5s;
            padding-top: 60px;
        }

        .sidenav a {
            padding: 8px 8px 8px 32px;
            text-decoration: none;
            font-size: 25px;
            color: #818181;
            display: block;
            transition: 0.3s;
        }

        .sidenav a:hover {
            color: #f1f1f1;
        }

        .sidenav .closebtn {
            position: absolute;
            top: 0;
            right: 25px;
            font-size: 36px;
            margin-left: 50px;
        }

        #main {
            transition: margin-left .5s;
            padding: 16px;
        }

        @media screen and (max-height: 450px) {
            .sidenav {padding-top: 15px;}
            .sidenav a {font-size: 18px;}
        }
        table{
            text-align: center;
        }
        thead{
            font-weight: bolder;
        }
    </style>
</head>
<body>

<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
    <a href="home.php">User</a>
    <a href="product.php">Product</a>
    <a href="exchange.php">Exchange</a>

</div>

<div id="main">
    <div class = "row" style="height:200px">
        <div class = "col-lg-2">
            <h2>INFINITY</h2>
            <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; </span>
        </div>


        <div class = "col-lg-1">
        </div>
        <div class = "col-lg-1" style="height:150px;width:150px;background-color: #005b96;margin:10px">
            <h2 style="color: #f1f1f1;text-align: center">User</h2><br>
            <h1 style="color: #f1f1f1;text-align: center;margin-top:-10px"><?php echo $user_total?></h1>
        </div>
        <div class = "col-lg-1" style="height:150px;width:150px;background-color: #005b96;margin:10px">
            <h2 style="color: #f1f1f1;text-align: center">Product</h2><br>
            <h1 style="color: #f1f1f1;text-align: center;margin-top:-10px"><?php echo $product_total?></h1>
        </div>
        <div class = "col-lg-1" style="height:150px;width:150px;background-color: #005b96;margin:10px">
            <h2 style="color: #f1f1f1;text-align: center">Deal</h2><br>
            <h1 style="color: #f1f1f1;text-align: center;margin-top:-10px"><?php echo $exchange_total?></h1>
        </div>
        <div class = "col-lg-1" style="height:150px;width:150px;background-color: #005b96;margin:10px">
            <h2 style="color: #f1f1f1;text-align: center">Revenue</h2><br>
            <h1 style="color: #f1f1f1;text-align: center;margin-top:-10px"><?php echo $total_account."$"?></h1>
        </div>
    </div>
    <form action="edit_user.php?id_edit=<?php echo $_GET['id_edit'];?>" method="post">
        <div class="form-group">
            <label for="username">User Name</label>
            <input type = "text" id = "username" placeholder="user name" class="form-control" name="user_name"/>
        </div>
        <div class="form-group">
            <label for="fullname">Full Name</label>
            <input type = "text" id = "fullname" placeholder="full name"class="form-control" name="full_name"/>
        </div>
        <div class="form-group">
            <label for="username">Email</label>
            <input type = "email" id = "email" placeholder="email" class="form-control" name ="email"/>
        </div>
        <div class="form-group">
            <label for="gender">Gender</label>
            <input type = "text" id = "gender" placeholder="gender" class="form-control" name ="gender"/>
        </div>
        <div class="form-group">
            <label for="age">Age</label>
            <input type = "text" id = "age" placeholder="age" class="form-control" name="age"/>
        </div>
        <button type="submit" class="btn btn-default" name="btn-submit">Submit</button>
    </form>
</div>
</body>
</html>
<script>
    function openNav() {
        document.getElementById("mySidenav").style.width = "250px";
        document.getElementById("main").style.marginLeft = "250px";
    }

    function closeNav() {
        document.getElementById("mySidenav").style.width = "0";
        document.getElementById("main").style.marginLeft= "0";
    }

</script>