package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import dataBase.DataBase ;
import logIn_RegisterSystem.LoginSystem ;
import logIn_RegisterSystem.RegisterSystem;

import static java.lang.System.exit;


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

        do {
            int cchoice = 0;
            String choice ;
            System.out.println("1. Log-in to chat.");
            System.out.println("2. Register in database.");
            System.out.println("3. Salon scheduling.");
            System.out.println("4. Exit.");
            System.out.println();

            System.out.println("Choose an option: ");
            Scanner myScanner = new Scanner(System.in) ;
            choice = myScanner.nextLine() ;

            try {
                cchoice = Integer.parseInt(choice) ;
            }catch(NumberFormatException e ) {
                System.out.println("Not a number!");
            }

            switch (cchoice) {
                case 1:
                    new LoginSystem().logInSystem();
                    break ;
                case 2:
                    new RegisterSystem().registerInDataBase() ;
                    break;
                case 3:
                    break;
                case 4:
                    exit(1) ;

            }

        }while(true) ;

    }

}