<?php
	$con=mysqli_connect("localhost","root","","quiz");
	$StartTime = date("YmdHis");
	$result=mysqli_query($con,"SELECT * FROM `quiz`.`tests` WHERE Deadline>$StartTime");
	while($row = mysqli_fetch_array($result))
	{
		$entry = $row['File_Name'];
		echo "$entry\n";
	}
	/*if ($handle = opendir('./uploads')) {
        while (false !== ($entry = readdir($handle))) {
            if ($entry != "." && $entry != "..") {
                echo "$entry\n";
            }
        }
        closedir($handle);
   }*/
?>