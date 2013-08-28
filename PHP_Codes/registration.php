<?php
$con=mysqli_connect("localhost","root","","quiz");
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$sql = "CREATE TABLE IF NOT EXISTS registration 
(
SNo INT NOT NULL AUTO_INCREMENT,
Student_ID CHAR(20) NOT NULL,
PRIMARY KEY(SNo,Student_ID),
Name CHAR(30),
Password CHAR(5)
)";
if (mysqli_query($con,$sql))
  {
  }
else
  {
  echo "Error creating table: " . mysqli_error($con);
  }

$con=mysqli_connect("localhost","root","","quiz");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
$Student_ID = $_GET["RollNumber"];
$Student_ID = strtolower($Student_ID);
$Name = $_GET["Name"];
$Password = rand(10000,99999);
$result = mysqli_query($con, "SELECT COUNT(Student_ID) FROM quiz.registration WHERE Student_ID = $Student_ID");

while($row = mysqli_fetch_array($result))
  {
  $exists = $row['COUNT(Student_ID)'];
  }

if( $exists == '0')
{
  $sql = "INSERT INTO `registration`(`SNo`, `Student_ID`, `Name`, `Password`) VALUES (NULL, $Student_ID, $Name, $Password)";
  mysqli_query($con, $sql);
  echo "1";
  require_once 'swift/lib/swift_required.php';

	$transport = Swift_SmtpTransport::newInstance('smtp.gmail.com', 465, "ssl")
	->setUsername('incnab@gmail.com')
	->setPassword('iitmadras2013');

	$mailer = Swift_Mailer::newInstance($transport);

	/////// EDIT THIS INFO ///////
	$user = str_replace("'", "", $Name);
	$pass=$Password;
	$email=$Student_ID.'@ee.iitm.ac.in';
	//$email = 'vvnikhil@gmail.com';
	$email = str_replace("'", "", $email);
	//////////////////////////////
	$body="Hi ".$user." ,\n\nYou have successfully registered into the placement mock test database. Your password is \b".$pass."\b. Use this in the settings page in your moblie application. \n\nThis is a one time authentication process. Your mobile, on successful authentication, will remember the password for you. \n\nThis mail has been automatically generated. Please do not reply to it. For assistance, ping bc.elecengg@gmail.com.\n\nHave a pleasant day. \n\nCheers,\nPlacement Team"; 

	$message = Swift_Message::newInstance('Successful Registration')
	  ->setFrom(array('incnab@gmail.com' => 'Quiz Admin'))
	  ->setTo(array($email))
	  ->setBody($body);

	$result = $mailer->send($message);

}
else if ($exists != 0)
{
  echo "2";
}
else 
{
	echo "0";
}

mysqli_close($con);


?>