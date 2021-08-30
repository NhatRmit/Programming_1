import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Data {
    // Data Fields
    String area;
    int numOfDays;
    int numOfWeeks;
    Scanner sc = new Scanner(System.in);
    LocalDate startDate;
    LocalDate endDate;
    String[] timerange;
    String newString;
    String s1[];
    String chooseAreaInput;

    // Constructors
    public Data() {
    }

    public Data(String area, LocalDate startDate, LocalDate endDate, String[] timerange) {
        this.area = area;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timerange = timerange;
    }

    // Methods
    public void inputArea() {
        System.out.println("""
                Select your country or continent:
                1. Country
                2. Continent""");

        this.chooseAreaInput = sc.nextLine();

        switch (this.chooseAreaInput) {
            case "1":
                System.out.println("Input your country: ");
                this.area = sc.nextLine().toLowerCase(Locale.ROOT);
                break;
            case "2":
                System.out.println("Input your continent: ");
                this.area = sc.nextLine().toLowerCase(Locale.ROOT);
                break;
            default:
                System.out.println("Invalid!");
                inputArea();
        }
    }

    // Convert String to LocalDate type
    public LocalDate dateInput(String userInput) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate date = LocalDate.parse(userInput, dateFormat);
        return date;
    }

    public void timeRangeType() {
        String opt, sub_opt;

        System.out.println("""
                Choose your time range:
                1. Pair
                2. Number of days or weeks from a particular date
                3. Number of days or weeks to a particular date""");
        opt = sc.nextLine();
        switch (opt) {
            case "1":
                getPair();
                break;
            case "2":
                System.out.println("Choose days (1) or weeks (2): ");
                sub_opt = sc.nextLine();
                switch (sub_opt) {
                    case "1":
                        getDaysFrom();
                        break;
                    case "2":
                        getWeekFrom();
                        break;
                    default:
                        System.out.println("Your input is INVALID. Please try again");
                        timeRangeType();
                }
                break;
            case "3":
                System.out.println("Choose days (1) or weeks (2): ");
                sub_opt = sc.nextLine();
                switch (sub_opt) {
                    case "1":
                        getDaysTo();
                        break;
                    case "2":
                        getWeekTo();
                        break;
                    default:
                        System.out.println("Your input is INVALID. Please try again");
                        timeRangeType();
                }
                break;
            default:
                System.out.println("Your input is INVALID. Please try again");
                timeRangeType();
        }
    }

    public String[] getWeekFrom() {
        inputStart();
        System.out.println("Input number of week: ");
        this.numOfWeeks = sc.nextInt();

        // Convert weeks to days
        this.numOfDays = this.numOfWeeks * 7;
        this.endDate = startDate.plusDays(this.numOfDays);
        arrayOfTime();
        return this.arrayOfTime();
    }

    public String[] getWeekTo() {
        sc.nextLine();
        inputEnd();
        System.out.println("Input number of week: ");
        this.numOfWeeks = sc.nextInt();

        // Convert to days
        this.numOfDays = this.numOfWeeks * 7;
        this.startDate = this.endDate.minusDays(this.numOfDays);
        arrayOfTime();
        return this.arrayOfTime();
    }

    public void inputStart() {
        System.out.println("Input start day (M-D-YYYY): ");
        dayInput();
        this.startDate = dateInput(this.newString);
    }

    public void inputEnd() {
        System.out.println("Input end day (M-D-YYYY): ");
        dayInput();
        this.endDate = dateInput(this.newString);
    }

    // Return an Array of Start Date to End Date of user's input
    public String[] arrayOfTime() {
        List<LocalDate> listOfDates = this.startDate.datesUntil(this.endDate.plusDays(1)).collect(Collectors.toList());
        this.timerange = new String[listOfDates.size()];
        DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/yyyy");

        for (int i = 0; i < this.timerange.length; i++) {
            this.timerange[i] = (listOfDates.get(i)).format(format);
        }
        return this.timerange;
    }

    public String[] getPair() {
        inputStart();
        inputEnd();
        arrayOfTime();
        return this.arrayOfTime();
    }

    public String[] getDaysFrom() {
        inputStart();
        System.out.println("Enter the number of days from your start date (Start Date inclusive): ");
        this.numOfDays = sc.nextInt();

        //Count
        this.endDate = startDate.plusDays(this.numOfDays);
        arrayOfTime();
        return this.arrayOfTime();
    }

    public String[] getDaysTo() {
        sc.nextLine();
        inputEnd();
        System.out.println("Enter the number of days before your end date (End Date inclusive): ");
        this.numOfDays = sc.nextInt();

        // Count
        this.startDate = this.endDate.minusDays(this.numOfDays);
        arrayOfTime();
        return this.arrayOfTime();
    }

    public void dayInput() {
        String s = sc.nextLine();

        //
        if (s.contains("/")) {
            this.s1 = s.split("/");
            int month = Integer.parseInt(this.s1[0]);
            int day = Integer.parseInt(this.s1[1]);
            int year = Integer.parseInt(this.s1[2]);

            if (month < 1 || month > 12) {
                System.out.println("Your month input is invalid");
                dayInput();
            }

            if (month == 2) {
                if (day == 29 && year == 2020) {
                } else {
                    System.out.println("""
                            Your input is invalid.
                            There is no 2/29 in 2021.
                            Only year 2020 and 2021 is valid to input.
                            Please input again.
                            """);
                    dayInput();
                }
            }

            if (day < 1 || day > 31) {
                System.out.println("Your day input is invalid");
                dayInput();
            }

            if (year != 2020 && year != 2021) {
                System.out.println("Your year input is invalid");
                dayInput();
            }


            for (int i = 0; i < s1.length; i++) {
                this.newString = s;
            }
        } else {
            this.s1 = s.split("-");
            int month = Integer.parseInt(this.s1[0]);
            int day = Integer.parseInt(this.s1[1]);
            int year = Integer.parseInt(this.s1[2]);

            if (month < 1 || month > 12) {
                System.out.println("Your month input is invalid");
                dayInput();
            }

            if (month == 2) {
                if (day == 29) {
                    if (year == 2020) {
                    } else {
                        System.out.println("""
                                Your input is invalid.
                                There is no 2/29 in 2021.
                                Only year 2020 and 2021 is valid to input.
                                Please input again.
                                """);
                        dayInput();
                    }
                }
            }

            if (day < 1 || day > 31) {
                System.out.println("Your day input is invalid");
                dayInput();
            }

            if (year != 2020 && year != 2021) {
                System.out.println("Your year input is invalid");
                dayInput();
            }
            for (int i = 0; i < s1.length; i++) {
                this.newString = String.join("/", s1[0], s1[1], s1[2]);
            }
        }
    }
}
