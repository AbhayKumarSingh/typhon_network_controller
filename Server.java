import java.io.*;
import java.net.*;

public class Server{
	ObjectOutputStream output;
	ObjectInputStream input;
    int option;
    String message;
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
                //String message = "SERVER>>> Connection successful";
                //output.writeObject(message);
                //output.flush();


                try{
                    message = (String) input.readObject();
                    option = Integer.parseInt(message);
                }
                catch( ClassNotFoundException cnfex) {
                    System.out.println("Unknown object type received");
                }

                switch(option){
                    case 1:
                        System.out.println("First executed.");
                        message = "1done";
                        output.writeObject(message);
                        output.flush();
                        break;

                    case 2:
                        System.out.println("Second executed.");
                        message = "2done";
                        output.writeObject(message);
                        output.flush();
                        break;

                    default:
                        System.out.println("Code should not reach default.");
                        break;
                        
                }
                //Close connection.
                //System.out.println("User terminated connection");
                output.close();
                input.close();
                connection.close();
                System.out.println("Closing this connection.");
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
