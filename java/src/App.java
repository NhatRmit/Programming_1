import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        String path = "src/covid-data.csv";
        String l = "";
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> timerange = new ArrayList<>();
        ArrayList<String> area = new ArrayList<>();

        String[] country = { "Brunei" };
        String[] time = { "12/1/2020", "12/2/2020", "12/3/2020", "12/4/2020", "12/5/2020", "12/6/2020", "12/7/2020",
                "12/8/2020", "12/9/2020", "12/10/2020", "12/11/2020", "12/12/2020", "12/13/2020", "12/14/2020",
                "12/15/2020", "12/16/2020", "12/17/2020", "12/18/2020", "12/19/2020", "12/20/2020", "12/21/2020",
                "12/22/2020", "12/23/2020", "12/24/2020", "12/25/2020", "12/26/2020", "12/27/2020", "12/28/2020",
                "12/29/2020", "12/30/2020"  };

        try {
            int count = 0;
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((l = br.readLine()) != null) {
                list.add(l);
            }

            System.out.println(list.get(0));

            for (String e : list) {
                for (int i = 0; i < time.length; i++) {
                    for (int j = 0; j < country.length; j++) {
                        if (isContain(e, time[i]) == true && isContain(e, country[j]) == true) {
                            data.add(e);
                            timerange.add(time[i]);
                            area.add(country[j]);
                            count++;
                        }
                    }
                }
            }

            int num_of_group = 10;
            int qty_days = timerange.size() / num_of_group;
            int checkdays = timerange.size() % num_of_group;

            System.out.println("total days " + timerange.size());
            System.out.println("quantity of days " + qty_days);
            System.out.println("check days " + checkdays);

            if (checkdays == 0) {
                List<List<String>> dataList = new ArrayList<List<String>>();
                for (int i = 1; i <= num_of_group; i++) {
                    List<String> tempList = new ArrayList<String>();
                    dataList.add(tempList);
                }

                for (int i = 0; i < num_of_group; i++) {
                    int value = i * qty_days;
                    for (int j = 1; j <= qty_days; j++) {
                        (dataList.get(i)).add(timerange.get((j - 1) + value));
                    }

                }

                for (List<String> s : dataList) {
                    System.out.println(s);
                }
            }

            if (checkdays == 1) {
                List<List<String>> dataList = new ArrayList<List<String>>();
                for (int i = 1; i <= num_of_group; i++) {
                    List<String> tempList = new ArrayList<String>();
                    dataList.add(tempList);
                }

                for (int i = 0; i < num_of_group; i++) {
                    int value = i * qty_days;
                    for (int j = 1; j <= qty_days; j++) {
                        (dataList.get(i)).add(timerange.get((j - 1) + value));
                    }
                }
                (dataList.get(num_of_group - 1)).add(timerange.get(timerange.size() - 1));

                for (List<String> s : dataList) {
                    System.out.println(s);
                }
            }

            if (checkdays == 2) {
                List<List<String>> dataList = new ArrayList<List<String>>();
                for (int i = 1; i <= num_of_group; i++) {
                    List<String> tempList = new ArrayList<String>();
                    dataList.add(tempList);
                }

                for (int i = 0; i < num_of_group; i++) {
                    int value = i * qty_days;
                    for (int j = 1; j <= qty_days + i; j++) {
                        (dataList.get(i)).add(timerange.get((j - 1) + value));
                    }

                }
                (dataList.get(num_of_group - 1)).remove(0);

                for (List<String> s : dataList) {
                    System.out.println(s);
                }
            }

            int days = 4;

            if (timerange.size() % days == 0) {

            } else {
                System.out.println("nhap lai ditme");
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean isContain(String source, String subItem) {
        String pattern = "\\b" + subItem + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        return m.find();
    }
}

class Data {
    // Data Fields
    String country, continent;
    String[] timeRange = new String[2];
    Scanner sc = new Scanner(System.in);

    // Constructors
    public Data() {
    }

    public Data(String country, String continent, String[] timeRange) {
        this.country = country;
        this.continent = continent;
        this.timeRange = timeRange;
    }

    // Methods
    public void inputArea() {
        System.out.println("Select your country or continent: ");
        String option = sc.nextLine();
        if (option.equals("country")) {
            System.out.println("Input your country: ");
            country = sc.nextLine();
        } else if (option.equals("continent")) {
            System.out.println("Input your continent: ");
            continent = sc.nextLine();
        } else {
            System.out.println("Invalid option");
            inputArea();
        }
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

    public static void Method1() {
        int year, month, day, year1, month1, day1;
        Scanner s = new Scanner(System.in);
        System.out.println("Start year: ");
        year = s.nextInt();
        System.out.println("Start month: ");
        month = s.nextInt();
        System.out.println("Start date: ");
        day = s.nextInt();

        System.out.println("End year: ");
        year1 = s.nextInt();
        System.out.println("End month: ");
        month1 = s.nextInt();
        System.out.println("End date: ");
        day1 = s.nextInt();

        LocalDate startDate = LocalDate.of(year, month, day);
        LocalDate endDate = LocalDate.of(year1, month1, day1);
        if (startDate.isBefore(endDate) == true) {
            System.out.println("Your start date is: " + startDate + " and your end date is: " + endDate);
        } else {
            System.out.println("Nhap sai cmnr !");
        }

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