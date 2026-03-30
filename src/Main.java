import users.*;
import java.util.Scanner;
public class Main {
    public static void main(String [] args){
        Scanner keyboard = new Scanner(System.in);
        int firstPage;

        while(true){
            System.out.println("Welcome Handong Global University");
            System.out.println("Press button what you want(1) login  2) sign up 3)quit)");
            firstPage = keyboard.nextInt();

            if(firstPage == 1){ // log in


            }else if( firstPage == 2){ // sign up

            }else{

                break;
            }
        }

        System.exit(0);

    }
}
