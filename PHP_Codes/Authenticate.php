<?php
$con=mysqli_connect("localhost","root","","quiz");
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$sql = "CREATE TABLE IF NOT EXISTS scores 
(
SNo INT NOT NULL AUTO_INCREMENT,
Student_ID CHAR(20) NOT NULL,
PRIMARY KEY(SNo,Student_ID),
Name CHAR(30),
StartingTime BIGINT,
Quiz_Name CHAR(40),
Score INT
)";
if (mysqli_query($con,$sql))
  {
  }
else
  {
  echo "Error creating table: " . mysqli_error($con);
  }

$con=mysqli_connect("localhost","root","","quiz");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
$StartTime = date("YmdHis");
$Quiz_Name = $_GET["Quiz_Name"];
$Student_ID = $_GET["Student_ID"];
$Name = $_GET["Name"];
$Deadline = $_GET["Deadline"];
$result = mysqli_query($con, "SELECT COUNT(Student_ID) FROM quiz.scores WHERE Student_ID = $Student_ID AND Quiz_Name = $Quiz_Name");

while($row = mysqli_fetch_array($result))
  {
  $exists = $row['COUNT(Student_ID)'];
  }
  $Deadline = str_replace("'", "", $Deadline);
  $timestamp = strtotime($Deadline);
  $Deads = date('YmdHis', $timestamp);
  $Deads = $Deads+235959;
if($Quiz_Name == "'DefaultTest'")
{
	echo "1";
}
else if( $exists == '0' && $Deads>$StartTime)
{
  $sql = "INSERT INTO `scores`(`SNo`, `Student_ID`, `Name`, `StartingTime`, `Quiz_Name`, `Score`) VALUES (NULL, $Student_ID, $Name, $StartTime, $Quiz_Name, NULL)";
  mysqli_query($con, $sql);
  echo "1";
}
else if ($exists != 0)
{
  echo "2";
}
else if ($Deads<$StartTime)
{
	echo "3";
}
else 
{
	echo "0";
}

mysqli_close($con);
  
?>