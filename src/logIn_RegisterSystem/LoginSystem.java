package logIn_RegisterSystem;

import client.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class LoginSystem {

    public void logInSystem ( ) throws IOException {
        Scanner scanner = new Scanner(System.in) ;
        System.out.println("Enter your username for the group chat: ");
        String username = scanner.nextLine();

        Scanner scannerr = new Scanner(System.in) ;
        System.out.println("Password: ");
        String userpass = scannerr.nextLine();

        Socket socket = new Socket("localhost", 1234);
        Client client = new Client ( socket, username, userpass) ;
        client.listenForMessages();
        client.sendMessages();
    }

}
