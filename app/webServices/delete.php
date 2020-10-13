<?php
include("db_connect.php");

$response=array();

if(isset($_GET["id"]) )
{
	$id=$_GET["id"];
	
	$req=mysqli_query($cnx, " delete from user where id='$id' ");
	
	if($req)
	{
		$response["success"]=1;
		$response["message"]="successful delete";
		
		
		echo json_encode($response);
		}
		else
		{
		$response["success"]=0;
		$response["message"]="request error";
		
		echo json_encode($response);	
		}
	
}
else
{
	
	$response["success"]=0;
		$response["message"]="required field is missing";
		
		echo json_encode($response);	
	
}

?>