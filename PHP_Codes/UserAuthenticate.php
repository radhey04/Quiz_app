<?php
$con=mysqli_connect("localhost","root","","quiz");
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$Student_ID = $_GET["RollNumber"];
$Name = $_GET["Name"];
$Password = $_GET["Password"];
$Password = str_replace("'", "", $Password);
$result = mysqli_query($con, "SELECT Password FROM quiz.registration WHERE Student_ID = $Student_ID");

while($row = mysqli_fetch_array($result))
  {
  $exists = $row['Password'];
  }
//echo $exists;
if( $exists == $Password)
{
  echo "1";
}
else if ($exists != $Password)
{
  echo "2";
}
else 
{
	echo "0";
}

mysqli_close($con);


?>