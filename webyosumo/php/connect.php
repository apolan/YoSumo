
<?php 

  //--------------------------------------------------------------------------
  // Example php script for fetching data from mysql database
  //--------------------------------------------------------------------------
  $host = "localhost";
  $user = "root";
  $pass = "";

  $databaseName = "yosumo";
  $tableName = "denuncia";

  //--------------------------------------------------------------------------
  // 1) Connect to mysql database
  //--------------------------------------------------------------------------
  $db=mysqli_connect('localhost','root','','yosumo');

  //--------------------------------------------------------------------------
  // 2) Query database for data
  //--------------------------------------------------------------------------
          //query
  $query = "SELECT * FROM $tableName";
//  $result = mysqli_query($db, $query) or die('Error querying database.');
//  $array = mysqli_fetch_assoc($result);                          //fetch result    

//$row = mysqli_fetch_assoc($result);
//$result = print_r($row);




  //--------------------------------------------------------------------------
  // 3) echo result as json 
  //--------------------------------------------------------------------------
 // echo json_encode($all);
  //echo json_encode(mysqli_fetch_row($result));
  $all = array();

if ($result=mysqli_query($db,$query)){
   while ($row=mysqli_fetch_row($result)){
    $all[] = $row;
   }
   mysqli_free_result($result);
}

mysqli_close($db);
echo json_encode($all);

?>