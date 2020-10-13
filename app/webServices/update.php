<?php
include ("db_connect.php");

$response=array();


if( isset($_GET["id"]) && isset($_GET["nom"]) && isset($_GET["age"]) )
	
	{
		
		$id=$_GET["id"];
		$nom=$_GET["nom"];
		$age=$_GET["age"];
		
		$req=mysqli_query($cnx, " update user set name='$nom' , age='$age' where id='$id' ");
		
		if($req)
		{
			$response["success"]=1;
			$response["message"]="updated successfully";
			
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