<?php 
$con=mysqli_connect("students.iitm.ac.in","placementmt","PMockTest13-14","PlacementMockTest");
$Quiz_Name=$_GET["QuizName"];
echo $Quiz_Name."<br>";
$QuizName = "'".$Quiz_Name."'";
echo $QuizName;
$query = "SELECT Student_ID,Name,Score FROM PlacementMockTest.scores WHERE Quiz_Name = $QuizName ORDER BY DESC";
$result = mysqli_query($con, "SELECT Student_ID,Name,Score FROM PlacementMockTest.scores WHERE Quiz_Name = $QuizName ORDER BY Score DESC");
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