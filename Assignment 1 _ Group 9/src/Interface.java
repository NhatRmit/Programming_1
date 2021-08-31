import java.util.Scanner;

public class Interface {
    Scanner sc = new Scanner(System.in);
    Display d = new Display();
    Summary s = new Summary();
    String option;

    public Interface() {
    }

    public Interface(String option) {
        this.option = option;
    }


    public void userInterface() throws Exception {
        d.displayType();
        System.out.println("Do you want to restart (1) or exit (2) the the program?");
        option = sc.nextLine();

        if (option.equals("1")) {
            restartProgram();
        } else if (option.equals("2")) {
            System.exit(0);
        }
        else {
            System.out.println("Invalid Input. PLease try again !!! ");
            Thread.sleep(3000);
            userInterface();
        }
    }

    public void restartProgram() throws Exception {
        Interface Interface = new Interface();
        Interface.userInterface();
    }
}
