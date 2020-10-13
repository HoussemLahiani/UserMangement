<?php

include ("db_connect.php");

$response=array();

if(isset($_GET["id"]) )   
{
	$id=$_GET["id"];
	
	$req=mysqli_query($cnx," select * from user where id='$id'  ");
	
	if(mysqli_num_rows($req) > 0)
	{
		$tmp=array();
		
		$response["user"]=array();
		$cur=mysqli_fetch_array($req);
		
		$tmp["id"]=$cur["id"];
		$tmp["nom"]=$cur["name"];
		$tmp["age"]=$cur["age"];
		
		array_push($response["user"],$tmp);
		$response["success"]=1;
	    echo json_encode($response);
	} 
	else
	{
		$response["success"]=0;
		$response["message"]="no data found";
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