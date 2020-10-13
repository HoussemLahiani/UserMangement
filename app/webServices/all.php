<?php

include("db_connect.php");

$response=array();

$req=mysqli_query($cnx, " select * from user ");

if(mysqli_num_rows($req) >  0)
{
	
	$tmp=array();
	$response["users"]=array();
	
	while($cur=mysqli_fetch_array($req))
	{
		$tmp["id"]=$cur["id"];
		$tmp["nom"]= $cur["name"];
		$tmp["age"]=$cur["age"];
		
		array_push($response["users"],$tmp);
	}
	$response["success"]=1;
	echo json_encode($response);
		
}
else
{
	$response["success"]=0;
	$response["message"]="no data found";
	echo json_encode($response);
	
}

?>