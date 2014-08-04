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
				$mon = $tue = $wed = $thur = $fri = $sat = $sun = $reqKit = FALSE;
				$name = $_POST["name"];
				$time_strt = $_POST["time_strt"];
				$time_end = $_POST["time_end"];
				$desc = $_POST["desc"];
				$mon = $_POST["mon"] == 'Monday';
				$tue = $_POST["tue"] == 'Tuesday';
				$wed = $_POST["wed"] == 'Wednesday';
				$thur = $_POST["thur"] == 'Thursday';
				$fri = $_POST["fri"] == 'Friday';
				$sat = $_POST["sat"] == 'Saturday';
				$sun = $_POST["sun"] == 'Sunday';
				$reqKit = $_POST["reqKit"];
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
						while ($out = socket_read($socket, 2048)) {
							if ($out = "start") {
								socket_write($socket, $data, strlen($data));
								socket_write($socket, $name . "\n");
								if ($time_strt != "")
								socket_write($socket, "time_strt:" . $time_strt . "\n");
								if ($time_end != "")
								socket_write($socket, "time_end:" . $time_end . "\n");
								if ($desc != "")
								socket_write($socket, "desc:" . $desc . "\n");
								socket_write($socket, "days:" . $mon . ":" . $tue . ":" . $wed . ":" . $thur . ":" . $fri . ":" . $sat . ":" . $sun . "\n");
								socket_write($socket, "reqKit:" . $reqKit . "\n");
								socket_write($socket, "save" . "\n");
								socket_write($socket, "exit");
								socket_write($socket, "getinfo");
								echo socket_read($socket, 2048);
								socket_close($socket);
							}
						}
					}
				}				
			}
		?>
		<h1>Add Activity</h1>
		<form method="POST" name="frm1" action="activity.php">
			<p>
			Activity Name: <input type="text" name="name"><br>
			Start Time: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="time_strt">(Format as "hh:mm")<br>
			End Time: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="time_end">(Format as "hh:mm")<br>
			Description: &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="desc"><br>
			</p>
			<p>
			Days Running: <br>
						<input type="checkbox" name="mon" value="Monday">Monday &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="tue" value="Tuesday">Tuesday<br>
						<input type="checkbox" name="wed" value="Wednesday">Wednesday <input type="checkbox" name="thur" value="Thursday">Thursday<br>
						<input type="checkbox" name="fri" value="Friday">Friday &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="sat" value="Saturday">Saturday<br>
						<input type="checkbox" name="sun" value="Sunday">Sunday<br>
			</p>
			<p>
			<input type="checkbox" name="reqkit" value="reqkit">Kit Required<br>
			</p>
			<input type="submit" value="Submit">
		</form>
		
	</body>
</html>