<?php
$con=mysqli_connect("localhost","root","","quiz");
//$con=mysqli_connect("localhost","root");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

// Create database
/*$sql="CREATE DATABASE IF NOT EXISTS quiz";
if (mysqli_query($con,$sql))
  {
  //echo "Database quiz created successfully";
  }
else
  {
  echo "Error creating database: " . mysqli_error($con);
  }

mysql_select_db( quiz, $con);
*/

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
//$sql="CREATE TABLE scores(StartingTime int(20),Name CHAR(30),Student_Id INT)";
// Execute query
if (mysqli_query($con,$sql))
  {
  //echo "Table scores created successfully";
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
$StartTime = date("YmdHis");//echo $StartTime;
$Quiz_Name = $_GET["Quiz_Name"];//echo $Quiz_Name;
//$Admin_ID = $_GET["Admin_ID"];//echo $Admin_ID;
$Student_ID = $_GET["Student_ID"];//echo $Student_ID;
$Name = $_GET["Name"];//echo $Name;
$Deadline = $_GET["Deadline"];

//$sql = "INSERT INTO scores (StudentID, Name, /*StartingTime,*/ Quiz_Name, Admin_ID) 
//	VALUES ($Student_ID, $Name, /*$StartTime,*/ $Quiz_Name, $Admin_ID)";

$result = mysqli_query($con, "SELECT COUNT(Student_ID) FROM quiz.scores WHERE Student_ID = $Student_ID AND Quiz_Name = $Quiz_Name");

while($row = mysqli_fetch_array($result))
  {
  $exists = $row['COUNT(Student_ID)'];
  //echo "<br>";
  }
  
  $Deadline = str_replace("'", "", $Deadline);
  //echo $Deadline;
  $timestamp = strtotime($Deadline);
  $Deads = date('YmdHis', $timestamp);
  $Deads = $Deads+235959;
  /*echo "<br>";
  echo $Deads;
  echo "<br>";
  echo $StartTime;
  echo "<br>";*/
//echo $exists;
//$ret=mysqli_query($con,"INSERT INTO `quiz`.`scores` (`SNo`, `Student_ID`, `Name`, `StartingTime`, `Quiz_Name`, `Score`) VALUES (NULL,$Student_ID, $Name, $StartTime, $Quiz_Name, NULL)");
if( $exists == '0' && $Deads>$StartTime)
{
  //mysqli_query($con,"INSERT INTO `quiz`.`scores` (`SNo`, `Student_ID`, `Name`, `StartingTime`, `Quiz_Name`, `Score`) VALUES (NULL, $Student_ID, $Name, $StartTime, $Quiz_Name, NULL)");//NULL,NULL, NULL, NULL)");
  mysqli_query($con,"INSERT INTO `quiz`.`scores` (`SNo`, `Student_ID`, `Name`, `StartingTime`, `Quiz_Name`, `Score`) VALUES (NULL, $Student_ID, $Name, $StartTime, $Quiz_Name, NULL)");
  //echo "<br>";
  //echo "insert";
  echo "1";
}
else
{
  echo "0";
}
$data=mysqli_query($con,"SELECT * FROM quiz.scores");
//while($row = mysqli_fetch_array($data))
//  {
//  echo "<br>";
//  echo $row['Quiz_Name'] . " " . $row['SNo'];
//  }

mysqli_close($con);
  
?>