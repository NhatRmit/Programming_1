import java.util.Scanner;

public class Interface {
    Scanner sc = new Scanner(System.in);
    Display d = new Display();
    String option;

    public Interface() {
    }

    public Interface(String option) {
        this.option = option;
    }

    public void displayType() throws Exception {
        String opt;
        System.out.println("""
                Choose the way to display your results:
                1. Display Table
                2. Display Chart
                """);
        opt = sc.nextLine();
        switch (opt) {
            case "1":
                d.displayTabular();
                break;
            case "2":
                d.displayChart();
                break;
            default:
                System.out.println("Your input is INVALID. Please try again");
                displayType();
                break;
        }

    }
    public void userInterface() throws Exception {
        displayType();
        System.out.println("Do you want to restart (1) or exit (2) the the program?");
        option = sc.nextLine();

        if (option.equals("1")) {
            displayType();
        } else if (option.equals("2")) {
            System.exit(0);
        }
        else {
            System.out.println("Invalid Input. PLease try again !!! ");
            Thread.sleep(3000);
            userInterface();
        }
    }
}
