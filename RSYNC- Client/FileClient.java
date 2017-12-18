import java.io.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileClient {

private static Socket sock;// defining a socket
private static String fileName;// defining a file
private static BufferedReader stdin;// creating a buffered reader object to take and read the input from the users
private static PrintStream os;// creating an object of printstream to display text or output in the terminal/command prompt

public static void main(String[] args) throws IOException {
try {
InetAddress addr=InetAddress.getByName("fe80::20c:29ff:fe93:1724");
sock = new Socket(addr, 13850);// creating a socket specifying the port and the ip address of the server to the client
stdin = new BufferedReader(new InputStreamReader(System.in));
} catch (Exception e) {
System.err.println("Cannot connect to the server, try again later."+e);
        System.exit(1);
}

os = new PrintStream(sock.getOutputStream());

try {

sendFile();

} catch (Exception e) {
System.err.println("not valid input");
}


sock.close();
}



public static void sendFile() {
try {
System.err.print("Enter file name: ");
fileName = stdin.readLine();//reads the file entered by the user 


File myFile = new File(fileName);
byte[] mybytearray = new byte[(int) myFile.length()];//determining the lenght of the file

FileInputStream fis = new FileInputStream(myFile);
BufferedInputStream bis = new BufferedInputStream(fis);//creating an input stream to read the contents of the file
//bis.read(mybytearray, 0, mybytearray.length);

DataInputStream dis = new DataInputStream(bis);
dis.readFully(mybytearray, 0, mybytearray.length);

OutputStream os = sock.getOutputStream();

//Sending file name and file size to the server
DataOutputStream dos = new DataOutputStream(os);//creating an output stream to send the file to the server
dos.writeUTF(myFile.getName());
dos.writeLong(mybytearray.length);
dos.write(mybytearray, 0, mybytearray.length);//writing the contents of the file to the server
dos.flush();
System.out.println("File "+fileName+" sent to Server.");
} catch (Exception e) {                           //reports error if any
System.err.println("File does not exist!"); 
}
}


}