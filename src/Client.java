import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket ;
    private BufferedReader bufferedReader ;
    private BufferedWriter bufferedWriter ;
    private String userName ;

    public Client (Socket socket, String username ) {

        try{
            this.socket = socket ;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            this.userName = username ;
        }catch (IOException e) {
            closeEverythings(socket, bufferedReader, bufferedWriter) ;
        }

    }

    public void sendMessages( ){

        try {
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in) ;
            while ( socket.isConnected()) {
                String messageToSend = scanner.nextLine() ;
                bufferedWriter.write(userName + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
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
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client ( socket, username ) ;
        client.listenForMessages();
        client.sendMessages();

    }

}
