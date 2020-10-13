<?php

include ("db_connect.php");

$response=array();

if( isset($_GET["nom"]) && isset($_GET["age"]) )
{
	$nom=$_GET["nom"];
	$age = $_GET["age"];
	
	$req=mysqli_query($cnx, " insert into user(name,age) values('$nom','$age') ");
	
	
	if($req)
	{
	$response["success"]=1;
	$response["message"]="inserted !";
	
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