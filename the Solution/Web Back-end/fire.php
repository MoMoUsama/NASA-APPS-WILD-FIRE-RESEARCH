<?php
  
  #echo "hello";
  
   $host="sql10.freesqldatabase.com";
   $username="sql10650569";
   $password="JQDVEuL5BQ";
   $db="sql10650569";
    #phpinfo();
    #$conn=new PDO ("mysql:host=$host;dbname=$db;" , $username , $password );
    #$conn = pg_connect("host=$host dbname=$db user=$username password=$password");
   

// Create connection
     $conn = new mysqli($host, $username, $password, $db);
   
    if( $conn->connect_error )
	{
	   #echo "error in connection";
	}else
		#echo "good";

  

  $device_id = 1 ;
  $reading = 20.1;
  $time = date("M,d,Y h:i:s A");
  
  $device_id = $_GET['id'];
  $sensor_name = $_GET['sensor_name'];
  $reading =  $_GET['reading'];
  
  #echo $device_id; 
  #echo " ";
  #echo $reading;
  #echo " ";
  #echo $time;
  
  #echo "      ";
  #echo "insert into sensor (Device_ID, Sensor_Reading , Reading_Time ) VALUES ( $device_id , $reading , '$time' )";
             #INSERT INTO sensor_reading (sensor_id, reading)VALUES ( (SELECT id FROM sensor WHERE id = 3 AND device_id = $device_id), 200 );
  $sql_qu = "call insertintosensor((SELECT id FROM sensor WHERE sensor_name='$sensor_name' AND device_id = $device_id ), $reading );";
  echo "call insertintosensor((SELECT id FROM sensor WHERE sensor_name='$sensor_name' AND device_id = $device_id ), $reading );";
                                                                         
  if ($conn->query($sql_qu) === TRUE)
  { 
     echo 1;
  }else
  {
	  echo 0;
  }
  $conn->close();
  #$sql=$conn->prepare("insert into sensor (Device_ID, Sensor_Reading , Reading_Time ) VALUES ( $device_id , $reading , '$time' )" ) ; 
  #$sql=$conn->prepare("select * from sensor");
  #$sql->execute();
  
  #foreach($sql as $result)
  #{
  #  echo $result['device_id'];
  #	echo "   ";
  #}
  
 
 

?> 