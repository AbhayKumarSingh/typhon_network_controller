import java.io.*;
import java.net.*;

public class Client{
	ObjectOutputStream output;
	ObjectInputStream input;
	String message = "";

	public static void main(String argsp[]){
		Client app = new Client();
		app.runClient();
	}
	
	public void runClient(){
		Socket client;

		try {
			System.out.println("Attempting connection");
			client = new Socket( InetAddress.getByName("127.0.0.1"), 5000);
			System.out.println("Connected to: " + client.getInetAddress().getHostName());
			//Get input and output streams.
			output = new ObjectOutputStream( client.getOutputStream());
			output.flush();
			input = new ObjectInputStream( client.getInputStream());
			System.out.println("Got I/O streams");

			//Process connection.
			//String message = "SERVER>>> Connection successful";
			//output.writeObject(message);
			//output.flush();

			do {
				try{
					message = (String) input.readObject();
					System.out.println( message);
				}
				catch( ClassNotFoundException cnfex) {
					System.out.println("Unknown object type received");
				}
			}while( !message.equals("SERVER>>> TERMINATE"));
				
			//Close connection.
			System.out.println("Closing connection.");
			output.close();
			input.close();
			client.close();
		}
	
		catch( EOFException eof ) {
			System.out.println("Client terminated connection");
		}
		catch( IOException io) {
			io.printStackTrace();
		}
	}
}
