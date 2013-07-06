import java.io.*;
import java.net.*;

public class Server{
	ObjectOutputStream output;
	ObjectInputStream input;

	public static void main(String args[]){
		Server app = new Server();
		app.runServer();
	}
	
	public void runServer(){
		ServerSocket server;
		Socket connection;
		
		try {
			//Create a ServerSocket.
			server = new ServerSocket( 5000, 100);

			while(true) {
				//Wait for a connection.
				connection = server.accept();
				System.out.println("Connection received from" + connection.getInetAddress().getHostName());

				//Get input and output streams.
				output = new ObjectOutputStream( connection.getOutputStream());
				output.flush();
				input = new ObjectInputStream( connection.getInputStream());
				System.out.println("Got I/O streams");

				//Process connection.
				String message = "SERVER>>> Connection successful";
				output.writeObject(message);
				output.flush();

				do {
					try{
						message = (String) input.readObject();
						System.out.println( message);
					}
					catch( ClassNotFoundException cnfex) {
						System.out.println("Unknown object type received");
					}
				}while( !message.equals("CLIENT>>> TERMINATE"));
				
				//Close connection.
				System.out.println("User terminated connection");
				output.close();
				input.close();
				connection.close();
			}
		}
		catch( EOFException eof ) {
			System.out.println("Client terminated connection");
		}
		catch( IOException io) {
			io.printStackTrace();
		}
	}
}			
