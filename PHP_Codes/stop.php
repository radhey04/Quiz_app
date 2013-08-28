<?PHP
session_start();
if (!(isset($_SESSION['login']) && $_SESSION['login'] != '')) {
	header ("Location: login.php");
}

?>
<?php
	$con=mysqli_connect("localhost","root","","quiz");
	$Quiz_Name = "'".$_POST["QuizName"]."'";
	$StartTime = date("YmdHis");
	$StartTime = $StartTime - 100000;
	if($Quiz_Name != "''")
	{
	mysqli_query($con,"UPDATE `quiz`.`tests` SET Deadline=$StartTime WHERE Quiz_Name=$Quiz_Name");
	echo $Quiz_Name." has been stopped";
	}
	else
	{
		echo "The field Quiz name cannot be empty.";
	}
?>