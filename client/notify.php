<!DOCTYPE HTML>
<html>
	<head>
		<title>Notify</title>
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
		<h1>Notify</h1>
		<form method="POST" name="frm1" action="notify.php">
			<table>
			<tr><td>Activity Name: </td><td><input type="text" name="name"></td></tr>
			<tr><td>Message: </td><td><textarea rows="4" cols="50" name="notification"></textarea></td></tr>
			</table>
			<input type="submit" value="Submit">
		</form>
		
	</body>
</html>