<!DOCTYPE HTML>
<html>
	<head>
		<title>Pupil</title>
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
				$first = $_POST["first"];
				$last = $_POST["last"];
				$form = $_POST["form"];
				$email = $_POST["email"];
				$sms = $_POST["sms"];
				$act = $_POST["act"];
				
				$host = "127.0.0.1"; 
				$port = 3000;
				$data = "pupil\n";
				

				if ( ($socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP)) === FALSE ) {
					echo "socket_create() failed: reason: " . socket_strerror(socket_last_error());
				} else {
					echo "Attempting to connect to '$host' on port '$port'...<br>";
					if ( ($result = socket_connect($socket, $host, $port)) === FALSE ) {
						echo "socket_connect() failed. Reason: ($result) " . socket_strerror(socket_last_error($socket));
					} else {
						$iterator = 0;
						while ($out = socket_read($socket, 2048)) {
							echo $out;
							if ($out = "go") {
								if ($iterator == 0)
									socket_write($socket, $data, strlen($data));
								if ($iterator == 1 && $first != "" && $last != "")
									socket_write($socket, $first . " " . $last . "\n");
								if ($iterator == 2 && $first != "")
									socket_write($socket, "firstname:" . $first . "\n");
								if ($last != "" && $iterator == 3)
									socket_write($socket, "lastname:" . $last . "\n");
								if ($form != "" && $iterator == 4)
									socket_write($socket, "form:" . $form . "\n");
								if ($iterator == 5 && email != "")
									socket_write($socket, "email:" . $email . "\n");
								if ($iterator == 6 && $sms != "")
									socket_write($socket, "sms:" . $sms . "\n");
								if ($iterator == 7 && $act != "")
									socket_write($socket, "activity:add:" . $act . "\n");
								if ($iterator == 8)
									socket_write($socket, "save" . "\n");
								if ($iterator == 9) {
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
		<h1>Pupil</h1>
		<form method="POST" name="frm1" action="pupil.php">
			<table>
			<tr><td>First Name: </td><td><input type="text" name="first"></td></tr>
			<tr><td>Last Name: </td><td><input type="text" name="last"></td></tr>
			<tr><td>Form: </td><td><input type="text" name="form"></td></tr>
			<tr><td>Email: </td><td><input type="text" name="email"></td></tr>
			<tr><td>SMS: </td><td><input type="text" name="sms"></td></tr>
			<tr><td>Add Activity: </td><td><input type="text" name="act"></td></tr>
			</table>
			<input type="submit" value="Submit">
		</form>
		
	</body>
</html>