import java.io.*;
import java.net.*;
import java.util.*;
public class FileServer {

private static ServerSocket serverSocket;
private static Socket clientSocket = null;

public static void main(String[] args) throws IOException {

try {

        serverSocket = new ServerSocket(13850);// creating a new serversocket
        System.out.println("Server started.");
} catch (Exception e)// catches errors and display them to the user for eg in case the port is busy we may specify a different port
{
        System.err.println("Port already in use.");
        System.exit(1);
}

    while (true)
{
try {
clientSocket = serverSocket.accept();// establishing the connection
System.out.println("Accepted connection : " + clientSocket);

Thread t = new Thread(new CLIENTConnection(clientSocket));
/*creating thread for clientconnection.java file and sending socket as an object thus everytime a connection is established a new thread/process is generated through which the file is sent and recieved*/
t.start();// executing the thread. here the new process or thread is created and ready to be implemented

} catch (Exception e) // catches error if any and displays it in the terminal/command prompt
{
System.err.println("Error in connection attempt.");
}
}
}
}

