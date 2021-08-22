import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Summary {
    int num_of_group, daysInput;
    String path = "java/src/covid-data.csv";
    String line = "";
    String userMetric = "";
    Scanner sc = new Scanner(System.in);
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> userList = new ArrayList<>();
    ArrayList<String> subList = new ArrayList<>();
    ArrayList<String> metric = new ArrayList<>();
    ArrayList<Integer> newtotal = new ArrayList<>();
    ArrayList<Integer> upto = new ArrayList<>();
    List<List<String>> dataList = new ArrayList<>();

    String[] userData;
    Data user = new Data();

    public void summary() throws Exception, FileNotFoundException {
        user.inputArea();
        user.timeRangeType();
        fetchData();
        groupData();
    }

    public void fetchData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            list.add(line);
        }

        for (String e : list) {
            if (isContain(e, user.area) == true) {
                subList.add(e);
            }
        }

        for (String e : subList) {
            for (int i = 0; i < user.range.length; i++) {
                if (isContain(e, user.range[i]) == true) {
                    userList.add(e);
                }
            }
        }
    }

    public void convertToArray(ArrayList<String> userList2) {
        // convert list to array
        String[] userListConvert = new String[userList2.size()];
        userListConvert = userList2.toArray(userListConvert);

        String[] value;

        // get each index
        for (String s : userListConvert) {
            value = s.split(",");
            switch (this.userMetric) {
                case "cases":
                    this.metric.add(value[4]);
                    break;
                case "deaths":
                    this.metric.add(value[5]);
                    break;
                case "vaccinations":
                    this.metric.add(value[6]);
                    break;
                default:
                    annInvalid();
                    break;
            }
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
                inputMetric(opt);
                break;
            case 2:
                System.out.println("Please choose number of group that you want: ");
                this.num_of_group = sc.nextInt();
                inputMetric(opt);
                break;
            case 3:
                System.out.println("Enter your number of days: ");
                this.daysInput = sc.nextInt();
                inputMetric(opt);
                break;
            default:
                annInvalid();
                groupData();
        }
    }

    public void noGrouping() {
        this.dataList = createDataList(userList.size());
        for (int i = 0; i < userList.size(); i++) {
            for (int j = 1; j <= 1; j++) {
                (this.dataList.get(i)).add(this.metric.get((j - 1) + i));
            }
        }
        displayGrouping(this.dataList);
    }

    public void numOfGroups() {

        int qty_days = userList.size() / this.num_of_group;
        int checkdays = userList.size() % this.num_of_group;

        if ((this.num_of_group >= 2 || this.num_of_group < 80) && (this.num_of_group <= userList.size())) {
            if (checkdays <= userList.size()) {
                this.dataList = createDataList(this.num_of_group);
                int count = 0;
                int pos_datalist = checkdays;
                for (int i = 0; i < this.num_of_group; i++) {
                    int value = i * qty_days;
                    if (i == this.num_of_group - pos_datalist) {
                        for (int j = 1; j <= qty_days + 1; j++) {
                            (dataList.get(i)).add(this.metric.get((j - 1) + value + count));
                        }
                        count++;
                        pos_datalist--;
                    } else {
                        for (int j = 1; j <= qty_days; j++) {
                            (this.dataList.get(i)).add(this.metric.get((j - 1) + value));
                        }
                    }
                }
            }
            displayGrouping(this.dataList);
        } else {
            annInvalid();
            numOfGroups();
        }
    }

    public void numOfDays() {
        this.num_of_group = userList.size() / this.daysInput; // N.O groups in case % == 0
        // Check
        if (userList.size() % this.daysInput == 0) {
            this.dataList = createDataList(this.num_of_group);
            int flag = 0;

            for (int i = 0; i < this.dataList.size(); i++) {
                for (int j = 0; j < this.daysInput; j++) {
                    (this.dataList.get(i)).add(this.metric.get(flag));
                    flag++;
                }
            }
            displayGrouping(this.dataList);
        } else {
            annInvalid();
            numOfDays();
        }
    }

    public void runMetric(int num) {
        convertToArray(this.userList);
        switch (num) {
            case 1:
                noGrouping();
                chooseResultType();
                break;
            case 2:
                numOfGroups();
                chooseResultType();
                break;
            case 3:
                numOfDays();
                chooseResultType();
                break;
            default:
                annInvalid();
                runMetric(num);
        }
    }

    public void inputMetric(int num) {
        System.out.println("""
                Select the information you want to see:
                1. New Cases
                2. New Deaths
                3. People Vaccinated""");
        int opt = sc.nextInt();
        switch (opt) {
            case 1:
                this.userMetric = "cases";
                runMetric(num);
                break;
            case 2:
                this.userMetric = "deaths";
                runMetric(num);
                break;
            case 3:
                this.userMetric = "vaccinations";
                runMetric(num);
                break;
            default:
                annInvalid();
                inputMetric(num);
                break;
        }

    }

    public ArrayList<Integer> newTotal() {
        // go through each group after grouping
        for (int i = 0; i < this.dataList.size(); i++) {
            int sum = 0;
            if (this.userMetric != "vaccinations") {
                for (String str : this.dataList.get(i)) {
                    int num = Integer.parseInt(str);
                    sum += num;
                }
            } else {
                for (int j = 0; j < this.dataList.get(i).size(); j++) {
                    int first_num = Integer.parseInt((this.dataList.get(i)).get(0));
                    int last_num = Integer.parseInt((this.dataList.get(i)).get(this.dataList.get(i).size() - 1));
                    sum = last_num - first_num;
                }
            }
            this.newtotal.add(sum);
        }
        return this.newtotal;
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
                System.out.println(newTotal());
                break;
            case 2:
                System.out.println(upTo());
                break;
            default:
                annInvalid();
                chooseResultType();
        }
    }

    public List<List<String>> createDataList(int num_of_group) {
        this.dataList = new ArrayList<>();
        for (int i = 1; i <= num_of_group; i++) {
            List<String> tempList = new ArrayList<>();
            this.dataList.add(tempList);
        }
        return this.dataList;
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

    public void annInvalid() {
        System.out.println("Your input is INVALID. Please, input again!");
    }
}
