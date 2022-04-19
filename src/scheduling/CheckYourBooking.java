package scheduling;

import dataBase.DataBase;

import java.util.Scanner;

public class CheckYourBooking {

    public CheckYourBooking() {
    }


    public void checkYourBooking ( ) {

        Scanner readNume = new Scanner(System.in) ;
        System.out.println("Introduce your name: ");
        String setName = readNume.nextLine() ;

        Scanner readSurname = new Scanner(System.in) ;
        System.out.println("Introduce your surname: ");
        String setSurname = readSurname.nextLine() ;

        DataBase dataBase = new DataBase() ;
        dataBase.displayCheckBooking(setName,setSurname);

    }

}
