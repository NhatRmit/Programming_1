import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Summary {
    String path = "java/src/covid-data.csv";
    String line = "";
    String metric;
    Scanner sc = new Scanner(System.in);
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> userList = new ArrayList<>();
    ArrayList<String> timerange = new ArrayList<>();
    ArrayList<String> cases = new ArrayList<>();

    String[] userData;
    Data user = new Data();

    public void summary() throws Exception, FileNotFoundException {
        user.inputArea();
        user.timeRangeType();
        fetchData();
        groupData();
        inputMetric();
        chooseResultType();
    }

    public void fetchData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            list.add(line);
        }

        System.out.println(list.get(0));

        for (String e : list) {
            for (int i = 0; i < user.range.length; i++) {
                if (isContain(e, user.range[i]) == true && isContain(e, user.area) == true) {
                    timerange.add(user.range[i]);
                    userList.add(e);
                }
            }
        }

        String[] userListConvert = new String[userList.size()];
        userListConvert = userList.toArray(userListConvert);

        String[] value;

        for(String s : userListConvert) {
            value = s.split(",");
        }

        for(String s : cases) {
            System.out.println(s);
        }
    }

    public void groupData() {
        int opt;
        System.out.println("""
                How would you group your data:
                1. No Grouping
                2. Number of Groups
                3. Number of Days in a group""");
        opt = sc.nextInt();
        switch (opt) {
            case 1:
                noGrouping();
                break;
            case 2:
                numOfGroups();
                break;
            case 3:
                numOfDays();
                break;
            default:
                groupData();
        }
    }

    public void noGrouping() {
        List<List<String>> dataList = createDataList(timerange.size());
        for (int i = 0; i < timerange.size(); i++) {

            for (int j = 1; j <= 1; j++) {
                (dataList.get(i)).add(timerange.get((j - 1) + i));
            }
        }
        displayGrouping(dataList);
    }

    public void numOfGroups() {
        System.out.println("Please choose number of group that you want: ");
        int num_of_group = sc.nextInt();
        int qty_days = timerange.size() / num_of_group;
        int checkdays = timerange.size() % num_of_group;

        if ((num_of_group >= 2 || num_of_group < 80) && (num_of_group <= timerange.size())) {
            if (checkdays <= timerange.size()) {
                List<List<String>> dataList = createDataList(num_of_group);
                int count = 0;
                int pos_datalist = checkdays;
                for (int i = 0; i < num_of_group; i++) {
                    int value = i * qty_days;
                    if (i == num_of_group - pos_datalist) {
                        for (int j = 1; j <= qty_days + 1; j++) {
                            (dataList.get(i)).add(timerange.get((j - 1) + value + count));
                        }
                        count++;
                        pos_datalist--;
                    } else {
                        for (int j = 1; j <= qty_days; j++) {
                            (dataList.get(i)).add(timerange.get((j - 1) + value));
                        }
                    }
                }
                displayGrouping(dataList);
            }
        } else {
            System.out.println("invalid");
            numOfGroups();
        }
    }

    public void numOfDays() {
        System.out.println("Enter your number of days: ");
        int daysInput = sc.nextInt();
        int num_of_group = timerange.size() / daysInput; // N.O groups in case % == 0
        // Check
        if (timerange.size() % daysInput == 0) {
            List<List<String>> dataList = createDataList(num_of_group);
            int flag = 0;
            for (int i = 0; i < dataList.size(); i++) {
                for (int j = 0; j < daysInput; j++) {
                    (dataList.get(i)).add(timerange.get(flag));
                    flag++;
                }
            }
            displayGrouping(dataList);
        } else {
            System.out.println("Invalid");
            numOfDays();
        }
    }

    public void inputMetric() {
        System.out.println("""
                Select the information you want to see:
                1. New Cases
                2. New Deaths
                3. People Vaccinated""");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                System.out.println("Input your country: ");
                this.metric = sc.nextLine();
                break;
            case 2:
                System.out.println("Input your continent: ");
                this.metric = sc.nextLine();
                break;
            case 3:
                System.out.println("Input your continent: ");
                this.metric = sc.nextLine();
                break;
            default:
                System.out.println("Invalid!");
                inputMetric();
        }
    }

    public int newTotal() {

        return -1;
    }

    public int upTo() {

        return -1;
    }

    public void chooseResultType() {
        System.out.println("""
                Select the the result type:
                1. New Total
                2. Up To""");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                newTotal();
                break;
            case 2:
                upTo();
                break;
            default:
                System.out.println("Invalid!");
                chooseResultType();
        }
    }

    public List<List<String>> createDataList(int num_of_group) {
        List<List<String>> dataList = new ArrayList<>();
        for (int i = 1; i <= num_of_group; i++) {
            List<String> tempList = new ArrayList<>();
            dataList.add(tempList);
        }
        return dataList;
    }

    public void displayGrouping(List<List<String>> dataList) {
        for (List<String> s : dataList) {
            System.out.println(s);
        }
    }

    public boolean isContain(String source, String subItem) {
        String pattern = "\\b" + subItem + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        return m.find();
    }
}