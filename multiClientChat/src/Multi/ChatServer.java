package Multi;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChatServer {

    Map<Socket, DataOutputStream> map = new HashMap<>();

    public static void main(String[] args) throws Exception {

        new ChatServer().runShitShow();
    }

    public void runShitShow()throws Exception{
        ServerSocket serverSocket = new ServerSocket(1337);


        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("ServerSocket.accept");

            ClientHandler clientHandler = new ClientHandler(socket);
            Thread thread = new Thread(clientHandler);
            thread.start();

        }
    }

    private synchronized void broadcasstToClients(String message) {
        for (Map.Entry<Socket, DataOutputStream> entry : map.entrySet()) {
            try {
                entry.getValue().writeBytes(message);
                entry.getValue().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public class ClientHandler implements Runnable {
        Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (Scanner scanner = new Scanner(socket.getInputStream())) {
                while (true) {
                    String line = scanner.nextLine();

                   System.out.println(line); // blokerer
                    // Thread thread = new Thread();


                }
            } catch (Exception e) {

            }
        }
    }
}
