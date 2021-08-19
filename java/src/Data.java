import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Data {
    // Data Fields
    String area;
    Scanner sc = new Scanner(System.in);
    LocalDate startDate;
    LocalDate endDate;
    String[] range;

    
    // Constructors
    public Data() {
    }

    public Data(String area,LocalDate startDate, LocalDate endDate , String[] range) {
        this.area = area;
        this.startDate = startDate;
        this.endDate = endDate;
        this.range = range;
    }

    // Methods
    public String inputArea() {
        System.out.println("Select your country or continent: ");
        String option = sc.nextLine();
        if (option.equals("country")) {
            System.out.println("Input your country: ");
            this.area = sc.nextLine();
            return this.area;
        } else if (option.equals("continent")) {
            System.out.println("Input your continent: ");
            this.area = sc.nextLine();
            return this.area;
        } else {
            System.out.println("Invalid option");
            inputArea();
        }
        return null;
    }

    public static LocalDate dateInput(String userInput) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate date = LocalDate.parse(userInput, dateFormat);
        return date;
    }

    public void timeRangeType() {
        int option;
        System.out.println("""
                Choose your time range:
                1. Pair
                2. Number of days or weeks from a particular date
                3. Number of days or weeks to a particular date""");
        option = sc.nextInt();
        switch (option) {
            case 1:
                Method1();
                break;
            case 2:
                Method2();
                break;
            case 3:
                Method3();
                break;
        }
    }

    public String[] Method1() {
        String startMonth, startDay, startYear, endMonth, endDay, endYear;
        Scanner s = new Scanner(System.in);
        System.out.println("Start month: ");
        startMonth = s.nextLine();        
        System.out.println("Start date: ");
        startDay = s.nextLine();
        System.out.println("Start year: ");
        startYear = s.nextLine();

        System.out.println("End month: ");
        endMonth = s.nextLine();        
        System.out.println("End date: ");
        endDay = s.nextLine();
        System.out.println("End year: ");
        endYear = s.nextLine();

        this.startDate = dateInput(startMonth+"/"+startDay+"/"+startYear);
        this.endDate = dateInput(endMonth+"/"+endDay+"/"+endYear);

        List<LocalDate> listOfDates = this.startDate.datesUntil(this.endDate.plusDays(1)).collect(Collectors.toList());
        this.range = new String[listOfDates.size()];
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/yyyy");
    
        for (int i = 0; i < this.range.length; i++) {
            this.range[i] = (listOfDates.get(i)).format(format);
        }

        return this.range;
    }

    public void Method2() {
        int year, month, day, days_of_month;
        Scanner s = new Scanner(System.in);
        System.out.println("Start year: ");
        year = s.nextInt();
        System.out.println("Start month: ");
        month = s.nextInt();
        System.out.println("Start date: ");
        day = s.nextInt();

        LocalDate startDate = LocalDate.of(year, month, day);
        LocalDate endDate = LocalDate.of(year, month, day);

        System.out.println("Enter your days will stay: ");
        days_of_month = s.nextInt();
        endDate = startDate.plusDays(days_of_month);
        System.out.println("Your start date is: " + startDate + " and your end date is: " + endDate);
    }

    public void Method3() {
        int year, month, day, days_of_month;
        Scanner s = new Scanner(System.in);
        System.out.println("End year: ");
        year = s.nextInt();
        System.out.println("End month: ");
        month = s.nextInt();
        System.out.println("End date: ");
        day = s.nextInt();

        LocalDate startDate = LocalDate.of(year, month, day);
        LocalDate endDate = LocalDate.of(year, month, day);

        System.out.println("Enter your days will stay: ");
        days_of_month = s.nextInt();
        startDate = endDate.minusDays(days_of_month);
        System.out.println("Your start date is: " + startDate + " and your end date is: " + endDate);
    }
}
