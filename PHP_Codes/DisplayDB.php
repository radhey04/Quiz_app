<html>
<body>

<h1>Scores</h1>

<p>database</p>

</body>
</html>

<?php
$connection = mysqli_connect("localhost", "root", "", "quiz"); //The Blank string is the password
//mysql_select_db('hrmwaitrose');
$QuizName = $_GET["QuizName"];
$query = "SELECT Student_ID, Name, Quiz_Name, Score FROM score where Quiz_Name = ($QuizName)"; //You don't need a ; like you do in SQL
$result = mysqli_query($connection,$query);

echo "<table>"; // start a table tag in the HTML
echo "<tr><td>Student ID</td><td>Name</td><td>Quiz Name</td><td>Score</td>";
while($row = mysqli_fetch_array($result))
  { //Creates a loop to loop through results
	echo "<tr><td>" . $row['Student_ID'] . "</td><td>" . $row['Name'] . "</td><td>" . $row['Quiz_Name'] . "</td><td>" . $row['Score'] . "</td></tr>";  //$row['index'] the index here is a field name
  }

echo "</table>"; //Close the table in HTML

mysqli_close($connection); //Make sure to close out the database connection
?>