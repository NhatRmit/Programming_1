import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Data {
    // Data Fields
    String area, startMonth, startDay, startYear, endMonth, endDay, endYear;
    int numOfDays;
    Scanner sc = new Scanner(System.in);
    LocalDate startDate;
    LocalDate endDate;
    String[] range;
    Scanner s = new Scanner(System.in);
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
        sc.nextLine();
        
        switch (this.opt) {
            case 1:
                System.out.println("Input your country: ");
                this.area = sc.nextLine();
                break;
            case 2:
                System.out.println("Input your continent: ");
                this.area = sc.nextLine();
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
                getDaysFrom();
                break;
            case 3:
                getDaysTo();
                break;
        }
    }

    public void inputStart() { 
        System.out.println("Start month: ");
        this.startMonth = s.nextLine();   
        System.out.println("Start date: ");
        this.startDay = s.nextLine(); 
        System.out.println("Start year: ");
        this.startYear = s.nextLine();
        this.startDate = dateInput(startMonth + "/" + startDay + "/" + startYear);
    }

    public void inputEnd() { 
        System.out.println("End month: ");
        this.endMonth = s.nextLine();
        System.out.println("End date: ");
        this.endDay = s.nextLine();
        System.out.println("End year: ");
        this.endYear = s.nextLine();
        this.endDate = dateInput(endMonth + "/" + endDay + "/" + endYear);
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
        this.numOfDays = s.nextInt();
        this.endDate = startDate.plusDays(this.numOfDays);

        arrayOfTime();
        return this.arrayOfTime();
    }

    public String[] getDaysTo() {
        inputEnd();

        System.out.println("Enter your days will stay: ");
        this.numOfDays = s.nextInt();
        if (Integer.parseInt(endDay) >= this.numOfDays) {
            this.startDate = this.endDate.minusDays(this.numOfDays);
        } else {
            getDaysTo();
        }

        arrayOfTime();
        return this.arrayOfTime();
    }
}
