<!DOCTYPE HTML>
<html>
	<head>
		<title>Add Activity</title>
		<style>
			body {
				font-family: arial;
			}
		</style>
		<script src= "http://code.jquery.com/jquery-2.1.1.js"></script>
		<script type="text/javascript">
			function sendData() {
				//collect data from form
				var name = document.forms["frm1"]["name"].value;
				var time_strt = document.forms["frm1"]["time_strt"].value;
				var time_end = document.forms["frm1"]["time_end"].value;
				var desc = document.forms["frm1"]["desc"].value;
				var days = [];
					days[0] = document.forms["frm1"]["mon"].checked;
					days[1] = document.forms["frm1"]["tue"].checked;
					days[2] = document.forms["frm1"]["wed"].checked;
					days[3] = document.forms["frm1"]["thur"].checked;
					days[4] = document.forms["frm1"]["fri"].checked;
					days[5] = document.forms["frm1"]["sat"].checked;
					days[6] = document.forms["frm1"]["sun"].checked;
				var kit = document.forms["frm1"]["reqkit"].checked;
			}
		</script>
		
	</head>
	<body>
		<?php
			if ($_SERVER["REQUEST_METHOD"] == "POST") {
				$name = $time_strt = $time_end = $desc = "";
				$mon = $tue = $wed = $thur = $fri = $sat = $sun = $reqKit = "false";
				$name = $_POST["name"];
				$notification = $_POST["notification"];
				$host = "127.0.0.1"; 
				$port = 3000;
				$data = "activity\n";
				

				if ( ($socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP)) === FALSE ) {
					echo "socket_create() failed: reason: " . socket_strerror(socket_last_error());
				} else {
					echo "Attempting to connect to '$host' on port '$port'...<br>";
					if ( ($result = socket_connect($socket, $host, $port)) === FALSE ) {
						echo "socket_connect() failed. Reason: ($result) " . socket_strerror(socket_last_error($socket));
					} else {
						$iterator = 0;
						while ($out = socket_read($socket, 2048)) {
							if ($out = "go") {
								if ($iterator == 0)
									socket_write($socket, $data, strlen($data));
								if ($iterator == 1)
									socket_write($socket, $name . "\n");
								if ($iterator == 2 && $notification != "")
									socket_write($socket, "notify:" . $notification . "\n");
								if ($iterator == 3)
									socket_write($socket, "exit" . "\n");
								$iterator ++;
							}
						}
					}
				}				
			}
		?>
		<h1>Add Activity</h1>
		<form method="POST" name="frm1" action="notify.php">
			<table>
			<tr><td>Activity Name: </td><td><input type="text" name="name"></td></tr>
			<tr><td>Message: </td><td><input type="text" name="notification"></td></tr>
			</table>
			<input type="submit" value="Submit">
		</form>
		
	</body>
</html>