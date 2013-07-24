<?php
$con=mysqli_connect("localhost","root","","quiz");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
//mysql_select_db("quiz"); 
$Quiz_Name = $_GET["Quiz_Name"];
$Score = $_GET["Score"];
$Time_Limit = $_GET["TimeLimit"];
$currTime = date("YmdHis");
$Student_ID = $_GET["Student_ID"];//echo "<br>"; echo $Student_ID;
$result = mysqli_query($con,"SELECT * FROM scores
WHERE Student_ID = $Student_ID");
if($Quiz_Name=="DefaultTest")
{
	echo "1";
}
else if(mysqli_num_rows($result)=='0') 
  {
  echo "Trying to upload score without authenticating";
  }
else
{
while($row = mysqli_fetch_array($result))
  {
  $StartTime = $row['StartingTime'];
  //echo $StartTime;
  //echo "<br>";
  }
//echo $Score;
//echo "<br>";
//echo $Time_Limit;
//echo "<br>";
//echo ($currTime - $StartTime);

if( $currTime - $StartTime < $Time_Limit)
  {
  mysqli_query($con,"UPDATE scores SET Score=$Score
  WHERE Student_ID = $Student_ID AND Quiz_Name = $Quiz_Name ");
  echo "1";
  }
else
  {
  echo "0";
  }
}
mysqli_close($con);

?>