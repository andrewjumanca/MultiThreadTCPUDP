import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 3116;

        try {
            // Create a UDP socket
            DatagramSocket clientSocket = new DatagramSocket();

            // Create a scanner to read user input
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Read a message from the user
                System.out.print("Enter a message (or 'quit' to exit): ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("quit")) {
                    break; // Exit the loop if the user enters "quit"
                }

                // Prepare the message to send
                byte[] sendData = message.getBytes();

                // Create a datagram packet with the server's address and port
                InetAddress serverIPAddress = InetAddress.getByName(serverAddress);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, serverPort);

                // Send the packet to the server
                clientSocket.send(sendPacket);

                // Receive the response from the server
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                // Process the response
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Response from server: " + response);
            }

            // Close the scanner and the socket
            scanner.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
