<?PHP
session_start();
if (!(isset($_SESSION['login']) && $_SESSION['login'] != '')) {
	header ("Location: login.php");
}

?>
<?php $con=mysqli_connect("localhost","root","","quiz");
$Quiz_Name=$_GET["QuizName"];
echo $Quiz_Name."<br>";
$QuizName = "'".$Quiz_Name."'";
echo $QuizName;
$query = "SELECT Student_ID,Name,Score FROM quiz.scores WHERE Quiz_Name = $QuizName ORDER BY DESC";
$result = mysqli_query($con, "SELECT Student_ID,Name,Score FROM quiz.scores WHERE Quiz_Name = $QuizName ORDER BY Score DESC");
echo $query;
echo "<table border='1'>
<tr>
<th>Student ID</th>
<th>Name</th>
<th>Score</th>
</tr>";

while($row = mysqli_fetch_array($result))
  {
  echo "<tr>";
  echo "<td>" . $row['Student_ID'] . "</td>";
  echo "<td>" . $row['Name'] . "</td>";
  echo "<td>" . $row['Score'] . "</td>";
  echo "</tr>";
  }
echo "</table>";


mysqli_close($con);

?>