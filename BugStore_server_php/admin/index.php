<?php
require_once "connection.php";
echo "heello";
if(isset($_POST["btn-submit"])){
    $user_name = $_POST['user_name'];
    $password = $_POST['password'];
    $sql = "SELECT * FROM user WHERE user_name='$user_name' and password = '$password'";
    $result = mysqli_query($conn,$sql);
    $num_row = mysqli_num_rows($result);
    if($num_row==0){
        echo '<h1>'."this account didn't exist".'</h1>';
    }else{
        $data= mysqli_fetch_array($result);
        $permission = $data['permission'];
        if($permission==1){
            header("Location:home.php");
        }else{
            echo '<h1>'."you don't have permission for this site".'</h1>';
        }
    }
}
?>
<html lang="en">
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        .login{
            height:40%;
            width:50%;
            margin-top:10%;
            margin-left:25%;
            background-color: #005b96;
            font-family: "Lato", sans-serif;
            color:#f1f1f1;
        }
    </style>
</head>
<body>

<div class = "login container">
    <div class="row">
        <div class = "col-lg-5"></div>
        <h2 class="col-lg-2">LOGIN</h2>
    </div>
    <form action="index.php" method="post">
        <div class="form-group">
            <label for="username">USER NAME</label>
            <input type = "text" id = "username" placeholder="user name" class="form-control" name="user_name" style="margin-left:10px;margin-right: 10px"/>
        </div>
        <div class="form-group">
            <label for="fullname">PASSWORD</label>
            <input type = "password" id = "password" placeholder="full name"class="form-control" name="password" style="margin:10px"/>
        </div>

        <button type="submit" class="btn btn-default" name="btn-submit" style="margin-left: 45%">Submit</button>
    </form>
</div>

</body>
</html>