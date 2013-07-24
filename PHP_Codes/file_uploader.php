<?php
$allowedExts = array("nab");
$extension = end(explode(".", $_FILES["file"]["name"]));
if ( in_array($extension, $allowedExts) )
  {
  if ($_FILES["file"]["error"] > 0)
    {
    echo "Error: " . $_FILES["file"]["error"] . "<br>";
    }
  else
    {
	$target_path = getcwd()."/uploads/";
	$target_path = $target_path . basename( $_FILES['file']['name']); 

	if(move_uploaded_file($_FILES['file']['tmp_name'], $target_path)) {
		echo "The file ".  basename( $_FILES['file']['name']). 
		" has been uploaded";
	} else{
		echo "There was an error uploading the file, please try again!";
	}
	$con=mysqli_connect("localhost","root","","quiz");
	if (mysqli_connect_errno())
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}

	$sql = "CREATE TABLE IF NOT EXISTS Tests 
	(
		SNo INT NOT NULL AUTO_INCREMENT,
		Quiz_Name CHAR(20) NOT NULL,
		PRIMARY KEY(SNo,Quiz_Name),
		Deadline BIGINT,
		File_Name CHAR(20)
	)";
	if (mysqli_query($con,$sql))
	{
	}
	else
	{
		echo "Error creating table: " . mysqli_error($con);
	}

	echo $Quiz_Name = "'".$_POST["QuizName"]."'";
	echo $Deadline = $_POST["Deadline"];
	$timestamp = strtotime($Deadline);
	$Deads = date('YmdHis', $timestamp);
	$Deads = $Deads+235959;
	echo $File_Name = "'".$_FILES["file"]["name"]."'";
	
	$con=mysqli_connect("localhost","root","","quiz");
	mysqli_query($con,"INSERT INTO `quiz`.`tests` (`SNo`, `Quiz_Name`, `Deadline`, `File_Name`) VALUES (NULL, $Quiz_Name, $Deads, $File_Name)");
	$result = mysqli_query($con, "SELECT Quiz_Name,Deadline,File_Name FROM quiz.tests");
	echo "<table border='1'>
	<tr>
	<th>Quiz Name</th>
	<th>Deadline</th>
	<th>File Name</th>
	</tr>";

	while($row = mysqli_fetch_array($result))
	{
		echo "<tr>";
		echo "<td>" . $row['Quiz_Name'] . "</td>";
		echo "<td>" . $row['Deadline'] . "</td>";
		echo "<td>" . $row['File_Name'] . "</td>";
		echo "</tr>";
	}
	echo "</table>";

	// echo "Upload: " . $_FILES["file"]["name"] . "<br>";
    // echo "Type: " . $_FILES["file"]["type"] . "<br>";
    // echo "Size: " . ($_FILES["file"]["size"] / 1024) . " kB<br>";
    // echo "Stored in: " . $_FILES["file"]["tmp_name"];
    }
  }
else
  {
  echo "Invalid file";
  }
  //window.open('/uploads');
  echo '<script type="text/javascript" language="javascript"> 
window.open("/app/uploads"); 
</script>';
?>