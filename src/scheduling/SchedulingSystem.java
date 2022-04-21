package scheduling;

import dataBase.DataBase;

import java.util.Scanner;
import java.time.*;

public class SchedulingSystem {


    public SchedulingSystem() {}

    public void setSchedulingDateForUser ( )  {

        Scanner getName = new Scanner(System.in);
        System.out.println("Introduce your name: ");
        String name = getName.nextLine() ;


        Scanner getSurname = new Scanner(System.in) ;
        System.out.println("Introduce your surname: ");
        String surname = getSurname.nextLine() ;


        Scanner myScanner = new Scanner(System.in) ;
        System.out.println("Introduce your day(number) when you want to go to saloon: ");
        int userDay = myScanner.nextInt();


        Scanner secScanner = new Scanner(System.in) ;
        System.out.println("Introduce month(number/ ex: 01(January)): ");
        int userMonth = secScanner.nextInt() ;


        Scanner thirdScanner = new Scanner(System.in);
        System.out.println("Introduce year(Ex: 2012): ");
        int userYear = thirdScanner.nextInt();


        Scanner hourScanner = new Scanner(System.in) ;
        System.out.println("Introduce hour(Ex: 13): ");
        int userHour = hourScanner.nextInt();


        Scanner minScanner = new Scanner(System.in);
        System.out.println("Introduce minutes(Ex:49): ");
        int userMin = minScanner.nextInt();


        LocalDate setDate = LocalDate.of(userYear,userMonth,userDay);
        LocalDateTime localDateTime = setDate.atTime(userHour,userMin,0);
        //todo
        // nu lasa inainte sa faci un search in DB si sa faci compararea
        boolean isEqual_ = new DataBase().searchInScheduleDateAndHour(setDate,localDateTime) ;
        if ( isEqual_ ) {

            boolean dataBase = new DataBase().insertScheduleDateAndHour(name, surname, setDate, localDateTime);

            if (dataBase) {
                System.out.println("Added!");
            }
        }else {
            System.out.println("You must change the date or time");
        }

    }

}