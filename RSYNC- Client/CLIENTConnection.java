import java.util.*;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.String;



public class CLIENTConnection implements Runnable {

private Socket clientSocket;
private BufferedReader in = null;

public CLIENTConnection(Socket client) {
this.clientSocket = client;
}

@Override
public void run() {
try {
in = new BufferedReader(new InputStreamReader(
clientSocket.getInputStream()));

receiveFile();

in.close();

}


catch (IOException ex) {
Logger.getLogger(CLIENTConnection.class.getName()).log(Level.SEVERE, null, ex);
}
}

public void receiveFile() {
try {
int bytesRead;
DataInputStream clientData = new DataInputStream(clientSocket.getInputStream());

String fileName = clientData.readUTF();
OutputStream output = new FileOutputStream(fileName);
long size = clientData.readLong();
byte[] buffer = new byte[1024];
while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
output.write(buffer, 0, bytesRead);
size -= bytesRead;
}

output.close();
clientData.close();

System.out.println("File "+fileName+" received from client.");
} catch (IOException ex) {
System.err.println("Client error. Connection closed.");
}
}


}
