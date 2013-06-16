<?php
$con=mysqli_connect("localhost","root","","quiz");
//$con=mysqli_connect("localhost","root");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

// Create database
//$sql="CREATE DATABASE quiz";
//if (mysqli_query($con,$sql))
//  {
//  echo "Database my_db created successfully";
//  }
//else
//  {
//  echo "Error creating database: " . mysqli_error($con);
//  }



$sql = "CREATE TABLE IF NOT EXISTS scores 
(
SNo INT NOT NULL AUTO_INCREMENT,
Student_ID INT NOT NULL,
PRIMARY KEY(SNo,Student_ID),
Name CHAR(30),
StartingTime BIGINT,
Quiz_ID INT,
Admin_ID INT,
Score INT
)";
//$sql="CREATE TABLE scores(StartingTime int(20),Name CHAR(30),Student_Id INT)";
// Execute query
if (mysqli_query($con,$sql))
  {
  echo "Table scores created successfully";
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
$Quiz_ID = $_GET["Quiz_ID"];//echo $Quiz_ID;
$Admin_ID = $_GET["Admin_ID"];//echo $Admin_ID;
$Student_ID = $_GET["Student_ID"];//echo $Student_ID;
$Name = $_GET["Name"];//echo $Name;

//$sql = "INSERT INTO scores (StudentID, Name, /*StartingTime,*/ Quiz_ID, Admin_ID) 
//	VALUES ($Student_ID, $Name, /*$StartTime,*/ $Quiz_ID, $Admin_ID)";

$result = mysqli_query($con, "SELECT COUNT(Student_ID) FROM quiz.scores WHERE Student_ID = $Student_ID");

while($row = mysqli_fetch_array($result))
  {
  $exists = $row['COUNT(Student_ID)'];
  //echo "<br>";
  }

//$ret=mysqli_query($con,"INSERT INTO `quiz`.`scores` (`SNo`, `Student_ID`, `Name`, `StartingTime`, `Quiz_ID`, `Admin_ID`, `Score`) VALUES (NULL,$Student_ID, $Name, $StartTime, $Quiz_ID, $Admin_ID, NULL)");
if( $exists == '0')
{
mysqli_query($con,"INSERT INTO `quiz`.`scores` (`SNo`, `Student_ID`, `Name`, `StartingTime`, `Quiz_ID`, `Admin_ID`, `Score`) VALUES (NULL,$Student_ID, $Name,$StartTime, $Quiz_ID, $Admin_ID, NULL)");//NULL,NULL, NULL, NULL)");
  echo "<br>";
  echo "<br>";
}
//$data=mysqli_query($con,"SELECT * FROM quiz.scores");
//while($row = mysqli_fetch_array($data))
//  {
//  echo $row['Quiz_ID'] . " " . $row['SNo'];
//  echo "<br>";
//  }

mysqli_close($con);
  
?>