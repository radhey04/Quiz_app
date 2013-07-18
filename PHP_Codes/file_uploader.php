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
    
	echo "Upload: " . $_FILES["file"]["name"] . "<br>";
    echo "Type: " . $_FILES["file"]["type"] . "<br>";
    echo "Size: " . ($_FILES["file"]["size"] / 1024) . " kB<br>";
    echo "Stored in: " . $_FILES["file"]["tmp_name"];
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