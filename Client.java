import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	public static void main (String[] args) throws Exception {
		Client myCli = new Client();
		myCli.run(args);
	}

  public void run (String[] args) throws Exception {
		Socket clientSocket = null;
		PrintWriter myPS = null;
		BufferedReader myBR = null;
		String s;
		Scanner input = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);

		// If the user does not specify a server, exit
		if (args.length == 0) {
			System.out.println("\nNo server name given. EXITING PROGRAM.\n");
			System.exit(1);
		}

		// Create a client socket on the server specified by the user
		try {
			clientSocket = new Socket(args[0], 7315);
			myPS = new PrintWriter(clientSocket.getOutputStream(), true);
			myBR = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}	catch (IOException exception) {
			System.out.println("\nInput/Output Exception. EXITING PROGRAM.\n");
			System.exit(2);
		}

    // Print the menu until the user specifies to exit
		while (true) {
			int check = 0;
			long startTime = 0;
			long endTime = 0;
			double averageTime = 0;
			double averageForAllThreads = 0;
			long totalTime = 0;
			int numThreads;

		  System.out.println("\n1. Host current Date and Time");
			System.out.println("2. Host uptime");
			System.out.println("3. Host memory use");
			System.out.println("4. Host Netstat");
			System.out.println("5. Host current users");
			System.out.println("6. Host running processes");
			System.out.println("7. Exit Program");
			System.out.print("Please enter a number from  the options above for "
			+ "information you would like to recieve from the server: ");
		
			s = input.nextLine();

			System.out.println();
			System.out.print("Please enter the number of threads: ");

			numThreads = input2.nextInt();

		
			// If the user enters a non integer catch the exception
			// Invalid Command will be displayed below
			try {
				check = Integer.parseInt(s); 		
			}	catch(Exception someException) {
				// Do nothing just catch exception
			}

			// If the user enters 7 exit the program
			if ( s.equals("7") ) {
				myPS.println(s);
				break;
			// If the user enters an integer not on the menu 
			// display an error and reprint menu
			} else if (check > 7 || check < 1) {
				System.out.println("Invalid Command. Please Try Again.");
				continue;
			// Print the information returned from the server
			} else {
        timeStack myTimeStack = new timeStack();

				for(int i = 1; i < numThreads + 1; i++) {
				
					// Create a new thread
					myThread clientThread = new myThread(check, myPS, myBR, myTimeStack);
					clientThread.start();
					
					// Waits for all threads
					try	{
						clientThread.join();
					} catch(InterruptedException e) {
						System.out.println(e);
					}

					if ( (i % 5 == 0) || (i == 1) ) {
						averageTime = myTimeStack.getAverage();
						System.out.println("Average time at " + i + " threads (in milliseconds): " + averageTime);
					}
				}
			}
		}
	  myPS.close();
	  System.exit(3);
  }
}

class myThread extends Thread {
	int userChoice;
	PrintWriter output;
	BufferedReader input;
	long totalTime;
	timeStack myTimeStack;
	
	public myThread (int userChoice, PrintWriter output, BufferedReader input, timeStack myTimeStack) {
		this.userChoice = userChoice;
		this.output = output;
		this.input = input;
		this.myTimeStack = myTimeStack;
	}
	
	public void run() {
		long totalTime;
		long startTime;
		long endTime;
		
		startTime = System.currentTimeMillis();
		
		output.println(userChoice);
		
		try {
			String serverInput = "From server: " + input.readLine();
			endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;
			myTimeStack.push(totalTime);
			System.out.println(serverInput);
		} catch(IOException e) {
			System.out.println(e);
		}
	}
}


class timeStack {
	long[] arrayTimes = new long[50];
	int numTimes;

	public timeStack() {
		numTimes = 0;
	}

	public synchronized void push (long time) {
		arrayTimes[numTimes] = time;
		numTimes++;
	}
	
	
	public double getAverage () {
		double averageTime = 0;
		
		for (int count = 0; count < numTimes; count++)
			averageTime += (double)arrayTimes[count];
	
		averageTime = averageTime / (double)arrayTimes.length;
		
		return averageTime;	
	
	}	
}




