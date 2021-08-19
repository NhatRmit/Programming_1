package ofDays;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class numberOfDaysGrouping {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        String path = "src/covid-data.csv";
        String l = "";
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> timerange = new ArrayList<>();
        ArrayList<String> area = new ArrayList<>();


        String[] country = {"Vietnam"};
        LocalDate startDate = dateInput("12/1/2020");
        LocalDate endDate = dateInput("12/22/2020");

        List<LocalDate> listOfDates = startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());
        String[] time = new String[listOfDates.size()];
        DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/yyyy");

        for (int i = 0; i < time.length; i++) {
            time[i] = (listOfDates.get(i)).format(format);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));  // Read data from file CSV and add the data into list
            while ((l = br.readLine()) != null) {
                list.add(l);
            }

            System.out.println(list.get(0));    // Print the headers

            for (String e : list) {
                for (int i = 0; i < time.length; i++) {
                    for (int j = 0; j < country.length; j++) {
                        if (isContain(e, time[i]) && isContain(e, country[j])) {    // isContain is to check if TIME and COUNTRY is available in the data
                            data.add(e);
                            timerange.add(time[i]);
                            area.add(country[j]);
                        }
                    }
                }
            }
            System.out.println(timerange.size());
            System.out.println("Enter your day-period: ");
            int daysInput = sc.nextInt();
            int numberOfGroups = timerange.size() / daysInput;    // N.O groups in case % == 0
            //Check
            if (timerange.size() % daysInput == 0){
                List<List<String>> dataList = new ArrayList<>();
                for (int i = 1; i <= numberOfGroups; i++) {
                    List<String> tempList = new ArrayList<>();
                    dataList.add(tempList);
                }
                int flag =0;
                for (int i = 0 ; i < dataList.size(); i++){
                    for (int j = 0; j < daysInput; j++){
                        (dataList.get(i)).add(timerange.get(flag));
                        flag++;
                    }
                }
                for (List<String> s : dataList) {
                    System.out.println(s);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LocalDate dateInput(String userInput) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
        return LocalDate.parse(userInput, dateFormat);
    }

    public static boolean isContain(String source, String subItem) {
        String pattern = "\\b" + subItem + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        return m.find();
    }
}
