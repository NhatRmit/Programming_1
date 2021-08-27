import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Summary {
    int num_of_group, daysInput;

    String path = "java/src/covid-data.csv";
    String line = "";
    String userMetricChoice = "";
    Scanner sc = new Scanner(System.in);

    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> listByArea = new ArrayList<>();

    ArrayList<String> listFromBeginToStartDate = new ArrayList<>();
    ArrayList<String> listByAreaMetricBeforeStartDate = new ArrayList<>();
    ArrayList<String> listTimeRangeBeforeStartDate = new ArrayList<>();

    ArrayList<String> listUserRangeTime = new ArrayList<>();
    ArrayList<String> listUserMetric = new ArrayList<>();
    ArrayList<String> listTimeRange = new ArrayList<>();

    List<List<String>> dataList = new ArrayList<>();
    List<List<String>> dataTime = new ArrayList<>();

    ArrayList<Integer> newtotal = new ArrayList<>();
    ArrayList<Integer> upto = new ArrayList<>();

    ArrayList<String> uptoString = new ArrayList<>();
    ArrayList<String> dataTimeString = new ArrayList<>();

    String[] uptoConvert = new String[uptoString.size()];
    String[] dataTimeConvert = new String[dataTimeString.size()];

    Data user = new Data();

    public void summary() throws Exception, FileNotFoundException {
        user.inputArea();
        user.timeRangeType();
        fetchData();
        groupData();

        for (int num : this.upto) {
            String s = String.valueOf(num);
            this.uptoString.add(s);
        }

        uptoConvert = uptoString.toArray(uptoConvert);
        dataTimeConvert = dataTimeString.toArray(dataTimeConvert);

        String groupRange = "";
        for (int i = 0; i < this.dataTime.size(); i++) {
            String temp = "";
            for (int j = 0; j < this.dataTime.get(i).size(); j++) {
                groupRange = this.dataTime.get(i).get(j);
                temp += groupRange + " - ";

            }
            dataTimeString.add(temp);
        }
    }

    public void fetchData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            list.add(line.toLowerCase(Locale.ROOT));
        }

        for (String e : list) {
            if (isContain(e, user.area) == true) {
                listByArea.add(e);
            }
        }
        for (String e : listByArea) {
            if (isContain(e, user.range[0]) != true) {
                listFromBeginToStartDate.add(e);
            } else {
                break;
            }
        }
        for (String e : listByArea) {
            for (int i = 0; i < user.range.length; i++) {
                if (isContain(e, user.range[i]) == true) {
                    listUserRangeTime.add(e);
                }
            }
        }
        br.close();
    }

    public void noGrouping() {
        this.dataList = createDataList(this.dataList, listUserRangeTime.size());
        this.dataTime = createDataList(this.dataTime, listUserRangeTime.size());
        for (int i = 0; i < listUserRangeTime.size(); i++) {
            for (int j = 1; j <= 1; j++) {
                (this.dataList.get(i)).add(this.listUserMetric.get((j - 1) + i));
                (this.dataTime.get(i)).add(this.listTimeRange.get((j - 1) + i));
            }
        }
        displayGrouping(this.dataList);
    }

    public void numOfGroups() {
        int qty_days = listUserRangeTime.size() / this.num_of_group;
        int checkdays = listUserRangeTime.size() % this.num_of_group;
        if (checkdays <= listUserRangeTime.size()) {
            this.dataList = createDataList(this.dataList, this.num_of_group);
            this.dataTime = createDataList(this.dataTime, this.num_of_group);
            int count = 0;
            int pos_datalist = checkdays;
            for (int i = 0; i < this.num_of_group; i++) {
                int value = i * qty_days;
                if (i == this.num_of_group - pos_datalist) {
                    for (int j = 1; j <= qty_days + 1; j++) {
                        (this.dataList.get(i)).add(this.listUserMetric.get((j - 1) + value + count));
                        (this.dataTime.get(i)).add(this.listTimeRange.get((j - 1) + value + count));
                    }
                    count++;
                    pos_datalist--;
                } else {
                    for (int j = 1; j <= qty_days; j++) {
                        (this.dataList.get(i)).add(this.listUserMetric.get((j - 1) + value));
                        (this.dataTime.get(i)).add(this.listTimeRange.get((j - 1) + value + count));
                    }
                }
            }
        }
        displayGrouping(this.dataList);
    }

    public void numOfDays() {
        this.num_of_group = listUserRangeTime.size() / this.daysInput; // N.O groups in case % == 0
        // Check
        this.dataList = createDataList(this.dataList, this.num_of_group);
        this.dataTime = createDataList(this.dataTime, this.num_of_group);

        int flag = 0;
        for (int i = 0; i < this.dataList.size(); i++) {
            for (int j = 0; j < this.daysInput; j++) {
                (this.dataList.get(i)).add(this.listUserMetric.get(flag));
                (this.dataTime.get(i)).add(this.listTimeRange.get(flag));
                flag++;
            }
        }
        displayGrouping(this.dataList);
    }

    public void groupData() {
        int opt;
        System.out.println("""
                How would you group your data:
                1. No Grouping
                2. Number of Groups
                3. Number of Days in a group""");
        opt = sc.nextInt();
        boolean check = true;
        switch (opt) {
            case 1:
                inputMetric(opt);
                break;
            case 2:
                System.out.println("Please choose number of group that you want: ");
                this.num_of_group = sc.nextInt();
                while (check) {
                    if ((this.num_of_group >= 2 && this.num_of_group < 80)
                            && (this.num_of_group <= listUserRangeTime.size())) {
                        check = false;
                    } else {
                        annInvalid();
                        System.out.println("Please choose number of group that you want: ");
                        this.num_of_group = sc.nextInt();
                        check = true;
                    }
                }
                inputMetric(opt);
                break;
            case 3:
                System.out.println("Enter your number of days: ");
                this.daysInput = sc.nextInt();
                while (check) {
                    if (checkDivisible(this.daysInput) != true) {
                        annInvalid();
                        System.out.println("Enter your number of days: ");
                        this.daysInput = sc.nextInt();
                    } else {
                        check = false;
                    }
                }
                inputMetric(opt);
                break;
            default:
                annInvalid();
                groupData();
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
                this.userMetricChoice = "cases";
                runMetric(num);
                break;
            case 2:
                this.userMetricChoice = "deaths";
                runMetric(num);
                break;
            case 3:
                this.userMetricChoice = "vaccinations";
                runMetric(num);
                break;
            default:
                annInvalid();
                inputMetric(num);
                break;
        }
    }

    public void runMetric(int num) {
        convertToArray(this.listFromBeginToStartDate, this.listByAreaMetricBeforeStartDate,
                this.listTimeRangeBeforeStartDate);
        convertToArray(this.listUserRangeTime, this.listUserMetric, this.listTimeRange);
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

    public ArrayList<Integer> newTotal() {
        // go through each group after grouping
                ArrayList<String> max = new ArrayList<>();
                ArrayList<String> converted_max = new ArrayList<>();
                int result = 0;
                for (int i = 0; i < this.dataList.size(); i++) {
                    int sum = 0;
                    if (this.userMetricChoice != "vaccinations") {
                        for (String str : this.dataList.get(i)) {
                            if (str == "") {
                                str = "0";
                            }
                            int num = Integer.parseInt(str); // Convert to Int in order to sum up
                            sum += num;
                        }
                    } else {
                        for (String s : dataList.get(i)) { //groups
                            max.add(s);
                        }
                        converted_max.add(Collections.max(max));
                    }
                    System.out.println(converted_max);
                    
                    this.newtotal.add(sum);
                }

                return this.newtotal;
    }

    public ArrayList<Integer> upTo() {
        int sumBeforeRange = 0;
        int sumPerGroup = 0;
        ArrayList<Integer> listMetricInt = new ArrayList<>();
        for (String str : this.listByAreaMetricBeforeStartDate) {
            if (this.userMetricChoice != "vaccinations") {
                if (str == "") {
                    str = "0";
                }
                int num = Integer.parseInt(str);
                sumBeforeRange += num;
            }
        }

        for (int i = 0; i < this.dataList.size(); i++) {
            if (this.userMetricChoice != "vaccinations") {
                for (String str : this.dataList.get(i)) {
                    if (str == "") {
                        str = "0";
                    }
                    int num = Integer.parseInt(str);
                    sumPerGroup += num;
                }
                this.upto.add(sumBeforeRange + sumPerGroup);
            } else {
                for (String str : this.listByAreaMetricBeforeStartDate) {
                    if (str == "") {
                        str = "0";
                    }
                    int num = Integer.parseInt(str);
                    listMetricInt.add(num);
                }
                for (String str : this.dataList.get(i)) {
                    if (str == "") {
                        str = "0";
                    }
                    int num = Integer.parseInt(str);
                    listMetricInt.add(num);
                }
                this.upto.add(Collections.max(listMetricInt));
            }
        }
        return this.upto;
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

    public void convertToArray(ArrayList<String> list, ArrayList<String> metric, ArrayList<String> rangetime) {
        // convert list to array
        String[] value;
        String[] userListConvert = new String[list.size()];
        userListConvert = list.toArray(userListConvert);

        // get each index
        for (String s : userListConvert) {
            value = s.split(",");
            switch (this.userMetricChoice) {
                case "cases":
                    metric.add(value[4]);
                    rangetime.add(value[3]);
                    break;
                case "deaths":
                    metric.add(value[5]);
                    rangetime.add(value[3]);
                    break;
                case "vaccinations":
                    metric.add(value[6]);
                    rangetime.add(value[3]);
                    break;
            }
        }
    }

    public List<List<String>> createDataList(List<List<String>> list, int num_of_group) {
        list = new ArrayList<>();
        for (int i = 1; i <= num_of_group; i++) {
            List<String> tempList = new ArrayList<>();
            list.add(tempList);
        }
        return list;
    }

    public void displayGrouping(List<List<String>> dataList) {
        for (List<String> s : dataList) {
            System.out.println(s);
        }
    }

    public boolean isContain(String s, String item) {
        String p = "\\b" + item + "\\b";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }

    public boolean checkDivisible(int num) {
        boolean result = true;
        if (listUserRangeTime.size() % num != 0) {
            result = false;
            return result;
        }
        return result;
    }

    public void annInvalid() {
        System.out.println("Your input is INVALID. Please, input again!");
    }
}