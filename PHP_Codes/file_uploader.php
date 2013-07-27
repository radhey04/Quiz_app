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
		$sql = "CREATE TABLE IF NOT EXISTS Tests 
		(
			SNo INT NOT NULL AUTO_INCREMENT,
			Quiz_Name CHAR(20) NOT NULL,
			PRIMARY KEY(SNo,Quiz_Name),
			Deadline BIGINT,
			File_Name CHAR(20)
		)";
		$con=mysqli_connect("students.iitm.ac.in","placementmt","PMockTest13-14","PlacementMockTest");
		if (mysqli_connect_errno())
		{
			echo "Failed to connect to MySQL: " . mysqli_connect_error();
		}
		if (mysqli_query($con,$sql))
		{
		}
		else
		{
			echo "Error creating table: " . mysqli_error($con);
		}
		$Quiz_Name = "'".$_POST["QuizName"]."'";
		$Deadline = $_POST["Deadline"];
		$timestamp = strtotime($Deadline);
		$Deads = date('YmdHis', $timestamp);
		$Deads = $Deads+235959;
		$File_Name = "'".$_FILES["file"]["name"]."'";
		$StartTime = date("YmdHis");
		if($StartTime<$Deads && $Quiz_Name!="''")
		{
			$con=mysqli_connect("students.iitm.ac.in","placementmt","PMockTest13-14","PlacementMockTest");
			mysqli_query($con,"INSERT INTO `PlacementMockTest`.`tests` (`SNo`, `Quiz_Name`, `Deadline`, `File_Name`) VALUES (NULL, $Quiz_Name, $Deads, $File_Name)");
			echo "The file ".  basename( $_FILES['file']['name']). 
			" has been uploaded";
			echo '<script type="text/javascript" language="javascript"> window.open("/app/uploads"); </script>';
		}	
		else
		{
			echo "Cannot upload file without proper Deadline or Quiz Name.";
		}
		
		//echo "The file ".  basename( $_FILES['file']['name']). 
		//" has been uploaded";
	} else{
		echo "There was an error uploading the file, please try again!";
	}

	/*$result = mysqli_query($con, "SELECT Quiz_Name,Deadline,File_Name FROM quiz.tests");
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
	*/
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
?>