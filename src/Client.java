import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Scanner;

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
            if ( isInDataBase(username,userPassword) ) {
                this.userName = username;
                this.userPassword = userPassword;
            }else {
                System.out.println("Client is not registered in database!");
                System.out.println("You'll be registered!");
                if ( insertInDataBase(username, userPassword) ) {
                    System.out.println("Client registered succesfuly!");
                    System.exit(0);
                }
            }
        }catch (IOException e) {
            closeEverythings(socket, bufferedReader, bufferedWriter) ;
        }

    }

    public boolean insertInDataBase ( String uName, String userPassword ) {
        boolean isInserted = false ;

        final String URL = "jdbc:postgresql://idc.cluster-custom-cjcsijnttbb2.eu-central-1.rds.amazonaws.com:5432/AZadic";
        final String USERNAME = "ftuser" ;
        final String PASSWORD = "*******" ;
        try {
            Connection myConnection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            PreparedStatement preparedStatement = myConnection.prepareStatement("INSERT INTO users(username,userpassword) VALUES (?,?)") ;
            preparedStatement.setString(1,uName);
            preparedStatement.setString(2,userPassword);
            int updateValuesOfDB = preparedStatement.executeUpdate() ;
            System.out.println(updateValuesOfDB);
            preparedStatement.close();
            myConnection.close();
        }catch(SQLException e ) {
            System.out.println("You already have one in database!");
        }

        return isInserted ;
    }

    public boolean isInDataBase ( String uName, String uPass ) {
        boolean isInDataBase = false ;

        /*Connect to database... */
        final String URL = "jdbc:postgresql://idc.cluster-custom-cjcsijnttbb2.eu-central-1.rds.amazonaws.com:5432/AZadic";
        final String USERNAME = "ftuser" ;
        final String PASSWORD = "******" ;
        try {
            Connection myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            /* Create a query */
            Statement statement = myConn.createStatement();
            /* Execute the query */
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                String numeUser = resultSet.getString("username");
                String passUser = resultSet.getString("userpassword");
                if ( numeUser.contains(uName.trim()) && passUser.contains(uPass.trim())) {
                    isInDataBase = true ;
                }
            }

            resultSet.close();
            statement.close();
            myConn.close();
        }catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return isInDataBase ;
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