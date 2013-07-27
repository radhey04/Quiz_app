<?php
	$con=mysqli_connect("students.iitm.ac.in","placementmt","PMockTest13-14","PlacementMockTest");
	$Quiz_Name = "'".$_POST["QuizName"]."'";
	$StartTime = date("YmdHis");
	$StartTime = $StartTime - 100000;
	if($Quiz_Name != "''")
	{
	mysqli_query($con,"UPDATE `PlacementMockTest`.`tests` SET Deadline=$StartTime WHERE Quiz_Name=$Quiz_Name");
	echo $Quiz_Name." has been stopped";
	}
	else
	{
		echo "The field Quiz name cannot be empty.";
	}
?>