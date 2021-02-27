import java.io.*;
import java.net.*;
import java.util.Date;

public class Server {
	public static void main (String[] args) throws Exception {
		Server myServer = new Server();
		myServer.run();
	}

	public void run () throws Exception {
		ServerSocket serverSocket = null;
		Socket mySS = null;
		int caseNum = 0;
		Process someProcess = null;
		Runtime someRuntime = null;
		BufferedReader stdInp = null;
		String cmd = "";
		String output = "";
		String line = "";
		Date date = new Date();
  	
		// Create a Server Socket on the specified port
		try {
			serverSocket = new ServerSocket(7315);
		} catch (IOException exception) {
			System.out.println("Could not create a socket on port 7315");
			System.exit(1);
		}	

		// Attempt to connect to the client
		try	{
			System.out.println("\nWaiting for connection from client\n");
			mySS = serverSocket.accept();
			System.out.println("Connected to client\n");
			System.out.println("Waiting on client request\n");
		} catch (IOException exception) {
			System.out.println("Could not accept the client request");
			System.exit(2);
		}

		PrintWriter SSPS = new PrintWriter(mySS.getOutputStream(), true);
		BufferedReader SS_BF = new BufferedReader(new InputStreamReader(mySS.getInputStream())); 
		

		// Run the process linked to the menu choice
		while (true) {
			String temp = SS_BF.readLine();
      // Convert the string to an integer
			caseNum = Integer.parseInt(temp);

			switch (caseNum) {
				case 1:
					System.out.println("Forking thread: date and time");
					someRuntime = Runtime.getRuntime();
					cmd = "date";

					// Runs the process, gets the ouptut, and prints it on the client side
					someProcess = someRuntime.exec(cmd);
					stdInp = new BufferedReader(new InputStreamReader(someProcess.getInputStream()));
					output = stdInp.readLine();
					SSPS.println(output);
					// Sets the output to blank so that all previous outputs will not be concatenated
					output = "";
					break;

				case 2:
					System.out.println("Forking thread: uptime");
				  // Creates a process for running processes
					someRuntime = Runtime.getRuntime();
					cmd = "uptime";

					// Runs the process, gets the ouptut, and prints it on the client side
					someProcess = someRuntime.exec(cmd);
					stdInp = new BufferedReader(new InputStreamReader(someProcess.getInputStream()));
					output = stdInp.readLine();
					SSPS.println(output);
					// Sets the output to blank so that all previous outputs will not be concatenated
					output = "";
					break;

				case 3:
					System.out.println("Forking thread: memory use");
				  // Creates a process for running processes
					someRuntime = Runtime.getRuntime();
					cmd = "free";

					// Runs the process, gets the ouptut, and prints it on the client side
					someProcess = someRuntime.exec(cmd);
					stdInp = new BufferedReader(new InputStreamReader(someProcess.getInputStream()));

					// Concatenates the lines to output
					while ((line = stdInp.readLine()) != null) 
						output = output + line;

					SSPS.println(output);
					// Sets the output to blank so that all previous outputs will not be concatenated
					output = "";
					break;

				case 4:
					System.out.println("Forking thread: netstat");
				  // Creates a process for running processes
					someRuntime = Runtime.getRuntime();
					cmd = "netstat";

					// Runs the process, gets the ouptut, and prints it on the client side
					someProcess = someRuntime.exec(cmd);
					stdInp = new BufferedReader(new InputStreamReader(someProcess.getInputStream()));

					// Concatenates the lines to output
					while ((line = stdInp.readLine()) != null) 
						output = output + line;

					SSPS.println(output);
					// Sets the output to blank so that all previous outputs will not be concatenated
					output = "";
					break;

				case 5:
					System.out.println("Forking thread: server current users");
				  // Creates a process for running processes
					someRuntime = Runtime.getRuntime();
					cmd = "who";

					// Runs the process, gets the ouptut, and prints it on the client side
					someProcess = someRuntime.exec(cmd);
					stdInp = new BufferedReader(new InputStreamReader(someProcess.getInputStream()));
					
					// Concatenates the lines to output
					while ((line = stdInp.readLine()) != null) 
						output = output + line;

					SSPS.println(output);
					// Sets the output to blank so that all previous outputs will not be concatenated
					output = "";
					break;
					
				case 6:
					System.out.println("Forking thread: server running processes\n");
					// Creates a process for running processes
					someRuntime = Runtime.getRuntime();
					cmd = "ps -e";

					// Runs the process, gets the ouptut, and prints it on the client side
					someProcess = someRuntime.exec(cmd);
					stdInp = new BufferedReader(new InputStreamReader(someProcess.getInputStream()));

					// Concatenates the lines to output
					while ((line = stdInp.readLine()) != null) 
						output = output + line;

					SSPS.println(output);
					// Sets the output to blank so that all previous outputs will not be concatenated
					output = "";
					break;

					case 7:
						System.out.println("Closing Connection.");
						SSPS.close();
						System.exit(3);
						break;
			}
		}
	}
}
