<!DOCTYPE HTML>
<html>
	<head>
		<title>Add Activity</title>
		<style>
			body {
				font-family: arial;
			}
		</style>
	</head>
	<body>
		<?php
			if ($_SERVER["REQUEST_METHOD"] == "POST") {
				$name = $time_strt = $time_end = $desc = "";
				$mon = $tue = $wed = $thur = $fri = $sat = $sun = $reqKit = "false";
				$name = $_POST["name"];
				$time_strt = $_POST["time_strt"];
				$time_end = $_POST["time_end"];
				$desc = $_POST["desc"];
				$mon = $_POST["mon"];
				$tue = $_POST["tue"];
				$wed = $_POST["wed"];
				$thur = $_POST["thur"];
				$fri = $_POST["fri"];
				$sat = $_POST["sat"];
				$sun = $_POST["sun"];
				$reqKit = $_POST["reqkit"];
				$host = "127.0.0.1"; 
				$port = 3000;
				$data = "activity\n";
				
				if ($mon != 'true') {
					$mon = 'false';
				}
				if ($tue != 'true') {
					$tue = 'false';
				}
				if ($wed != 'true') {
					$wed = 'false';
				}
				if ($thur != 'true') {
					$thur = 'false';
				}
				if ($fri != 'true') {
					$fri = 'false';
				}
				if ($sat != 'true') {
					$sat = 'false';
				}
				if ($sun != 'true') {
					$sun = 'false';
				}
				if ($reqKit != 'true') {
					$reqKit = 'false';
				}

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
								if ($time_strt != "" && $time_end != "") {
									if ($iterator == 2)
										socket_write($socket, "time:" . $time_strt . ":" . $time_end . "\n");
								}
								if ($desc != "") {
									if ($iterator == 4)
										socket_write($socket, "desc:" . $desc . "\n");
								}
								if ($iterator == 5)
									socket_write($socket, "days:" . $mon . ":" . $tue . ":" . $wed . ":" . $thur . ":" . $fri . ":" . $sat . ":" . $sun . "\n");
								if ($iterator == 6)
									socket_write($socket, "reqkit:" . $reqKit . "\n");
								if ($iterator == 7)
									socket_write($socket, "save" . "\n");
								if ($iterator == 8) {
									socket_write($socket, "exit");
									//socket_write($socket, "getinfo");
									//echo socket_read($socket, 2048);
									socket_close($socket);
								}
								$iterator ++;
							}
						}
					}
				}				
			}
		?>
		<h1>Add Activity</h1>
		<form method="POST" name="frm1" action="activity.php">
			<table>
			<tr><td>Activity Name: </td><td><input type="text" name="name"></td></tr>
			<tr><td>Start Time: </td><td><input type="text" name="time_strt">(Format as "hh:mm")</td></tr>
			<tr><td>End Time: </td><td><input type="text" name="time_end">(Format as "hh:mm")</td></tr>
			<tr><td>Description: </td><td><input type="text" name="desc"></td></tr>
			</table>
			<p>
			Days Running: <br>
						<input type="checkbox" name="mon" value="true">Monday &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="tue" value="true">Tuesday<br>
						<input type="checkbox" name="wed" value="true">Wednesday <input type="checkbox" name="thur" value="true">Thursday<br>
						<input type="checkbox" name="fri" value="true">Friday &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="sat" value="true">Saturday<br>
						<input type="checkbox" name="sun" value="true">Sunday<br>
			</p>
			<p>
			<input type="checkbox" name="reqkit" value="true">Kit Required<br>
			</p>
			<input type="submit" value="Submit">
		</form>
		
	</body>
</html>