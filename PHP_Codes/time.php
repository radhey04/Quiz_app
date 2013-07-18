<?php
$str = '21-JUN-2013';
echo $str;
// previous to PHP 5.1.0 you would compare with -1, instead of false
if (($timestamp = strtotime($str)) === false) {
    echo "The string ($str) is bogus";
} else {
    echo "$str == " . date('YmdHis', $timestamp);
}
echo "<br>";
echo $timestamp;
?>