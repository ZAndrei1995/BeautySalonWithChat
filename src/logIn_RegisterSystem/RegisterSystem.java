package logIn_RegisterSystem;

import dataBase.DataBase;

import java.util.Scanner;

public class RegisterSystem {

    public void registerInDataBase ( ) {
        String uName;
        String uPassword;
        String uEmail;
        String uOriginalName;
        String uOriginalSurname ;

        System.out.println("Welcome, register yourself into the app!");

        Scanner userScanner = new Scanner(System.in) ;
        System.out.println("Enter username: ");
        uName = userScanner.nextLine() ;

        Scanner passScanner = new Scanner(System.in) ;
        System.out.println("Enter a password: ");
        uPassword = passScanner.nextLine() ;

        Scanner emailScanner = new Scanner(System.in) ;
        System.out.println("Enter your email: ");
        uEmail = emailScanner.nextLine() ;

        Scanner uOriginalN = new Scanner(System.in) ;
        System.out.println("Introduce your name: ");
        uOriginalName = uOriginalN.nextLine() ;

        Scanner uOriginalS = new Scanner(System.in) ;
        System.out.println("Introduce your surname: ");
        uOriginalSurname = uOriginalS.nextLine();

        if ( new DataBase().insertInDataBase(uName, uPassword, uEmail, uOriginalName, uOriginalSurname) ) {
            System.out.println("Added succesfuly");
        }

    }

}