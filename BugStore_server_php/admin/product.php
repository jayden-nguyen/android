<?php
require_once "connection.php";
?>
<?php
if(isset($_GET['id_delete'])){
    $id_delete = $_GET['id_delete'];
    echo $id_delete;
    $delete_sql = "DELETE FROM product WHERE product_id ='$id_delete'";
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
    <div class = "row" style="height:30px">
        <div class = "col-lg-9">
            <h2>
                Product Table
            </h2>

        </div>
        <input type="text" name="username" id="user-text" class="col-lg-2">
        <button id="search" class="col-lg-1 btn-danger">Search</button>
    </div>

    <table class="table table-bordered table-condensed danger">
        <thead >
        <tr class="danger">
            <td >No</td>
            <td >Product Name</td>
            <td>Product Type</td>
            <td >Producer Name</td>
            <td >Price</td>
            <td>Action</td>
        </tr>
        </thead>
        <tbody id="table-body"></tbody>
    </table>
    <div class = "row">
        <div class="col-lg-6">
        </div>
        <div class="col-lg-3">
            <a class="btn-page" id ="first">|<<</a>
            <a class="btn-page" id ="previous"><< </a>
            <a class="btn-page" id ="next">>></a>
            <a class="btn-page" id ="last">>>|</a>
        </div>
        <div class="col-lg-3">
            <span id="current-page"></span>
            <span>/</span>
            <span id="total-page"></span>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-11"></div>
        <button class = "col-lg-1 btn-info"  ><a href="add_user.php" style="color:#f1f1f1">Add</a></button>
    </div>
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
    var productname_search;
    $(document).ready(function () {
        var page = 1;
        getData(page);

        $(".btn-page").on("click",function () {
            event.preventDefault();
            var total_page = Number($("#total-page").text());
            if(this.id=="first"){
                page = 1;
            }else if(this.id=="previous"){
                if(page==1){
                    page = 1;
                }else{
                    page -= 1;
                }
            }else if(this.id == "next"){
                console.log("page"+page);
                console.log("total_page"+total_page);
                if(page == total_page){
                    page = total_page;
                    console.log("OK");
                }else{
                    page += 1;
                }
            }else if(this.id == "last"){
                page = total_page;
            }
            getData(page);
        });
        $("#search").on("click",function () {
            productname_search =  $("#user-text").val();
            console.log("product search"+productname_search);
            getData(page,productname_search)
        });
        $(".status").change(function () {
            console.log(this.id);
            var index = Number(this.id);
            console.log("index is"+ index);

        });
        function getData(page,productname_search) {
            $("#table-body").empty();
            $.ajax({
                url:"page_product.php",
                data:{page:page,productname_search:productname_search},
                dataType:"json",
                contentType:"application/json",
                success:function (output) {
                    console.log(output);
                    $("#current-page").text(page);
                    for(var i = 0;i<output.length;i++){
                        $("#total-page").text(output[i].total_pages);
                        $("#table-body").append(
                            '<tr>'
                            +'<td>'+ output[i].product_id+'</td>'
                            +'<td>'+ output[i].product_name+'</td>'
                            +'<td>'+output[i].product_type+'</td>'
                            +'<td>' + output[i].producer_name +'</td>'
                            +'<td>' + output[i].price +'</td>'
                            +'<td>'+ '<a href="edit_product.php">edit  </a>'
                            +'<a href="product.php?id_delete='+output[i].product_id+'"> delete</a>'+'</td>'
                        )

                    }

                }
            });
        }
    });
</script>