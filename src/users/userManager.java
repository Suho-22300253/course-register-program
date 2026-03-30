package users;
import java.util.Scanner;
public class userManager {
    Scanner keyboard = new Scanner(System.in);
    void logIn(Student[] students){
        System.out.print("ID : ");
        String id = keyboard.next();
        System.out.print("Password : ");
        String password = keyboard.next();
    }

    void logIn(Professor[] professors){
        System.out.print("ID : ");
        String id = keyboard.next();
        System.out.print("Password : ");
        String password = keyboard.next();
    }

}
