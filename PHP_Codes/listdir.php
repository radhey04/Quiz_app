<?php
	$con=mysqli_connect("students.iitm.ac.in","placementmt","PMockTest13-14","PlacementMockTest");
	$StartTime = date("YmdHis");
	$result=mysqli_query($con,"SELECT * FROM `PlacementMockTest`.`tests` WHERE Deadline>$StartTime ORDER BY File_Name ASC");
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