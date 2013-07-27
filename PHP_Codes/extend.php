<?php
	$con=mysqli_connect("students.iitm.ac.in","placementmt","PMockTest13-14","PlacementMockTest");
	$Quiz_Name = "'".$_POST["QuizName"]."'";
	$Deadline = $_POST["Deadline"];
	$timestamp = strtotime($Deadline);
	$Deads = date('YmdHis', $timestamp);
	$Deads = $Deads+235959;
	$StartTime = date("YmdHis");
	if($StartTime<$Deads)
	{
		if($Quiz_Name != "''")
		{
			mysqli_query($con,"UPDATE `PlacementMockTest`.`tests` SET Deadline=$Deads WHERE Quiz_Name=$Quiz_Name");
			echo $Quiz_Name." has been extended to ".$Deads;
		}
		else
		{
			echo "The field Quiz name cannot be empty.";
		}
	}
	else
	{
		echo "Please give a future date/ Use the Stop Test option.";
	}

?>