<?PHP
session_start();
if (!(isset($_SESSION['login']) && $_SESSION['login'] != '')) {
	header ("Location: login.php");
}

?>
<html>
<head>
<title>Title of the document</title>
</head>

</body>
</html>
<a href="upload_landing.php"> 
<button type="button" >Upload</button>  </a>
<br>
<br>
<a href="query_landing.php"> 
<button type="button" >Query</button>  </a>
<br>
<br>
<a href="extend_landing.php"> 
<button type="button" >Extend Deadline</button>  </a>
<br>
<br>
<a href="stop_landing.php"> 
<button type="button" >Stop Test</button>  </a>

</html>

