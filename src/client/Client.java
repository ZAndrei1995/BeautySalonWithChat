package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import dataBase.DataBase ;


public class Client {

    private Socket socket ;
    private BufferedReader bufferedReader ;
    private BufferedWriter bufferedWriter ;
    private String userName ;
    private String userPassword ;

    public Client (Socket socket, String username, String userPassword) {

        try{
            this.socket = socket ;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            if ( new DataBase().isInDataBase(username,userPassword) ) {
                this.userName = username;
                this.userPassword = userPassword;
            }else {
                System.out.println("Client.Client is not registered in database!");
                System.out.println("You'll be registered!");
                if ( new DataBase().insertInDataBase(username, userPassword) ) {
                    System.out.println("Client.Client registered succesfuly!");
                    System.exit(0);
                }
            }
        }catch (IOException e) {
            closeEverythings(socket, bufferedReader, bufferedWriter) ;
        }

    }

    public void sendMessages( ){

        try {
            if ( userName!= null ) {
                bufferedWriter.write(userName);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                Scanner scanner = new Scanner(System.in);
                while (socket.isConnected()) {
                    String messageToSend = scanner.nextLine();
                    bufferedWriter.write(userName + ": " + messageToSend);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
        }catch(IOException e ) {
            closeEverythings(socket, bufferedReader, bufferedWriter) ;
        }

    }

    public void listenForMessages() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat ;
                while ( socket.isConnected()) {
                    try {
                        msgFromGroupChat = bufferedReader.readLine() ;
                        System.out.println(msgFromGroupChat);
                    }catch(IOException e) {
                        closeEverythings(socket, bufferedReader, bufferedWriter) ;
                    }
                }
            }
        }).start();

    }

    public void closeEverythings (Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter ) {
        try{
            if ( bufferedReader != null ) {
                bufferedReader.close();
            }
            if ( bufferedWriter != null ) {
                bufferedWriter.close();
            }
            if ( socket != null ) {
                socket.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

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