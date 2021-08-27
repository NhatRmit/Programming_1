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
    String[] range;
    String newString;
    int opt;

    // Constructors
    public Data() {
    }

    public Data(String area, LocalDate startDate, LocalDate endDate, String[] range) {
        this.area = area;
        this.startDate = startDate;
        this.endDate = endDate;
        this.range = range;
    }

    // Methods
    public void inputArea() {
        System.out.println("""
                Select your country or continent:
                1. Country
                2. Continent""");

        this.opt = sc.nextInt();
        sc.nextLine();  // This line is to flush the cache

        switch (this.opt) {
            case 1:
                System.out.println("Input your country: ");
                this.area = sc.nextLine().toLowerCase(Locale.ROOT);
                break;
            case 2:
                System.out.println("Input your continent: ");
                this.area = sc.nextLine().toLowerCase(Locale.ROOT);
                break;
            default:
                System.out.println("Invalid!");
                inputArea();
        }
    }


    public static LocalDate dateInput(String userInput) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate date = LocalDate.parse(userInput, dateFormat);
        return date;
    }

    public void timeRangeType() {
        int sub_opt;
        System.out.println("""
                Choose your time range:
                1. Pair
                2. Number of days or weeks from a particular date
                3. Number of days or weeks to a particular date""");

        this.opt = sc.nextInt();
        switch (this.opt) {
            case 1:
                getPair();
                break;
            case 2:
                System.out.println("Choose days (1) or weeks (2): ");
                sub_opt = sc.nextInt();
                switch (sub_opt) {
                    case 1:
                        getDaysFrom();
                        break;
                    case 2:
                        getWeekFrom();
                        break;
                }
                break;
            case 3:
                System.out.println("Choose days (1) or weeks (2): ");
                sub_opt = sc.nextInt();
                switch (sub_opt) {
                    case 1:
                        getDaysTo();
                        break;
                    case 2:
                        getWeekTo();
                        break;
                }
                break;
        }
    }

    public String[] getWeekFrom() {
        inputStart();
        System.out.println("Input number of week: ");
        this.numOfWeeks = sc.nextInt();

        //Convert to days
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

        //Convert to days
        this.numOfDays = this.numOfWeeks * 7;
        this.startDate = this.endDate.minusDays(this.numOfDays);
        arrayOfTime();
        return this.arrayOfTime();
    }

    public void inputStart() {
        System.out.println("Input start day (M-D-YYYY): ");
        sc.nextLine();
        dayInput();
        this.startDate = dateInput(this.newString);
    }

    public void inputEnd() {
        System.out.println("Input end day (M-D-YYYY): ");
        dayInput();
        this.endDate = dateInput(this.newString);
    }

    public String[] arrayOfTime() {
        List<LocalDate> listOfDates = this.startDate.datesUntil(this.endDate.plusDays(1)).collect(Collectors.toList());
        this.range = new String[listOfDates.size()];
        DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/yyyy");

        for (int i = 0; i < this.range.length; i++) {
            this.range[i] = (listOfDates.get(i)).format(format);
        }
        return this.range;
    }

    public String[] getPair() {
        inputStart();
        inputEnd();
        arrayOfTime();
        return this.arrayOfTime();
    }

    public String[] getDaysFrom() {
        inputStart();
        System.out.println("Enter your days will stay: ");
        this.numOfDays = sc.nextInt();
        this.endDate = startDate.plusDays(this.numOfDays);
        arrayOfTime();
        return this.arrayOfTime();
    }

    public String[] getDaysTo() {
        sc.nextLine();
        inputEnd();
        System.out.println("Enter your days will stay: ");
        this.numOfDays = sc.nextInt();
        //Count
        this.startDate = this.endDate.minusDays(this.numOfDays);
        arrayOfTime();
        return this.arrayOfTime();
    }

    public void dayInput(){
        String s = sc.nextLine();
        if (s.contains("/")){
            this.newString = s;
        }
        else {
            String s1[] = s.split("-");
            System.out.println(Arrays.toString(s1));
            for (int i =0; i < s1.length; i++){
                this.newString = String.join("/", s1[0], s1[1], s1[2]);
            }
        }
    }
}