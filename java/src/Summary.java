import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Summary {
    // Attribute
    int num_of_group, daysInput;
    String path = "src/covid-data.csv";
    String line = "";
    String userMetricChoice = "";
    String resultType = "";
    String opt;
    Scanner sc = new Scanner(System.in);
    // Data Fetched and Filtered by Area
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> listByArea = new ArrayList<>();

    // Data Filtered by Area and Time from Beginning to Start Date
    ArrayList<String> listFromBeginToStartDate = new ArrayList<>();
    ArrayList<String> listByAreaMetricBeforeStartDate = new ArrayList<>();
    ArrayList<String> listTimeRangeBeforeStartDate = new ArrayList<>();

    // Data of Metric and Time Range User Input
    ArrayList<String> listUserRangeTime = new ArrayList<>();
    ArrayList<String> listUserMetric = new ArrayList<>();
    ArrayList<String> listUserTimeRange = new ArrayList<>();

    // List of group of metric data and time data
    List<List<String>> dataList = new ArrayList<>();
    List<List<String>> dataTime = new ArrayList<>();

    // List result of newtotal type and upto
    ArrayList<Integer> newtotal = new ArrayList<>();
    ArrayList<Integer> upto = new ArrayList<>();

    // List convert result to string to display (Result and Time)
    ArrayList<String> resultString = new ArrayList<>();
    ArrayList<String> dataTimeString = new ArrayList<>();

    // Create a User
    Data user = new Data();

    // Constructor
    public Summary() {
    }

    // Summary main function
    public void sumData() throws Exception, FileNotFoundException {
        // User input data (area and time range)
        user.inputArea();
        user.timeRangeType();

        // fetch data and put in list respectively
        fetchData();

        // ask users how to process their data to get result
        groupData();

        // convert result from int to string to display
        processMetricAndTime();
    }

    // Fetch data from CSV and get data by filter funciton
    public void fetchData() throws IOException {
        // fetch data from csv
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            this.list.add(line.toLowerCase(Locale.ROOT));
        }

        // filter data by area user inputs
        if (user.chooseAreaInput.equals("1")) {
            for (String e : this.list) {
                if (isContained(e, user.area)) {
                    this.listByArea.add(e);
                }
            }
        } else {
            for (String e : this.list) {
                if (isContained(e, user.area) && ((isContained(e, "owid_asi")) ||

                        (isContained(e, "owid_eur")) ||

                        (isContained(e, "owid_afr")) ||

                        (isContained(e, "owid_oce")) ||

                        (isContained(e, "owid_nam")) ||

                        (isContained(e, "owid_eun")) ||

                        (isContained(e, "owid_sam")) ||

                        (isContained(e, "owid_wrl"))
                )) {
                    this.listByArea.add(e);
                }
            }
        }

        // filter data from beginning of the area to the start date user inputs
        for (String e : this.listByArea) {
            if (isContained(e, user.timerange[0]) != true) {
                this.listFromBeginToStartDate.add(e);
            } else {
                break;
            }
        }

        // get data by area and time range that user inputs
        for (String e : this.listByArea) {
            for (int i = 0; i < user.timerange.length; i++) {
                if (isContained(e, user.timerange[i]) == true) {
                    this.listUserRangeTime.add(e);
                }
            }
        }
        br.close();
    }

    public void noGrouping() {
        this.dataList = createDataList(this.dataList, listUserRangeTime.size());
        this.dataTime = createDataList(this.dataTime, listUserRangeTime.size());
        this.num_of_group = listUserRangeTime.size();
        for (int i = 0; i < listUserRangeTime.size(); i++) {
            for (int j = 1; j <= 1; j++) {
                (this.dataList.get(i)).add(this.listUserMetric.get((j - 1) + i));
                (this.dataTime.get(i)).add(this.listUserTimeRange.get((j - 1) + i));
            }
        }
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
                        (this.dataTime.get(i)).add(this.listUserTimeRange.get((j - 1) + value + count));
                    }
                    count++;
                    pos_datalist--;
                } else {
                    for (int j = 1; j <= qty_days; j++) {
                        (this.dataList.get(i)).add(this.listUserMetric.get((j - 1) + value));
                        (this.dataTime.get(i)).add(this.listUserTimeRange.get((j - 1) + value + count));
                    }
                }
            }
        }

    }

    public void numOfDays() {
        // N.O groups in case % == 0
        this.num_of_group = listUserRangeTime.size() / this.daysInput;
        // Check
        this.dataList = createDataList(this.dataList, this.num_of_group);
        this.dataTime = createDataList(this.dataTime, this.num_of_group);

        int flag = 0;
        for (int i = 0; i < this.dataList.size(); i++) {
            for (int j = 0; j < this.daysInput; j++) {
                (this.dataList.get(i)).add(this.listUserMetric.get(flag));
                (this.dataTime.get(i)).add(this.listUserTimeRange.get(flag));
                flag++;
            }
        }
    }

    // Let User Choose how to group their data
    public void groupData() {

        System.out.println("""
                How would you group your data:
                1. No Grouping
                2. Number of Groups
                3. Number of Days in a group""");
        this.opt = sc.nextLine();
        boolean check = true;
        switch (opt) {
            case "1":
                // Nogrouping Option
                inputMetric(opt);
                break;
            case "2":
                // Number of groups Option
                System.out.println("Please choose number of group that you want: ");
                this.num_of_group = sc.nextInt();
                // Input again if their input is invalid
                while (check) {
                    if ((this.num_of_group >= 2 && this.num_of_group < 80)
                            && (this.num_of_group <= this.listUserRangeTime.size())) {
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
            case "3":
                // Number of days in a group
                System.out.println("Enter your number of days: ");
                this.daysInput = sc.nextInt();
                // Input again if their input is invalid
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
                // Input again if their input is invalid
                annInvalid();
                groupData();
        }
    }

    // Let Users choose their metric
    public void inputMetric(String num) {
        System.out.println("""
                Select the information you want to see:
                1. New Cases
                2. New Deaths
                3. People Vaccinated""");
        String opt = sc.nextLine();
        switch (opt) {
            case "1":
                // Metric is new covid cases
                this.userMetricChoice = "cases";
                runMetric(num);
                break;
            case "2":
                // Metric is people deaths
                this.userMetricChoice = "deaths";
                runMetric(num);
                break;
            case "3":
                // Metric is people who got vaccinated
                this.userMetricChoice = "vaccinations";
                runMetric(num);
                break;
            default:
                // Input again because their input is invalid
                annInvalid();
                inputMetric(num);
                break;
        }
    }

    // runMetric function will proccess to get exact metric data and then group
    // users data as their choices and
    // choose result type
    public void runMetric(String num) {
        // Get Metric Data from beginning of the area to start date in order to
        // calculate the upTo function
        getMetricDataAndTimeRange(this.listFromBeginToStartDate, this.listByAreaMetricBeforeStartDate,
                this.listTimeRangeBeforeStartDate);
        // Get Metric Data and Time Range Data as user choice
        getMetricDataAndTimeRange(this.listUserRangeTime, this.listUserMetric, this.listUserTimeRange);
        switch (num) {
            case "1":
                // Run no grouping and run function to choose result type to calculate
                noGrouping();
                chooseResultType();
                break;
            case "2":
                // Run number of groups grouping and run function to choose result type to
                // calculate
                numOfGroups();
                chooseResultType();
                break;
            case "3":
                // Run number of days in a group grouping and run function to choose result type
                // to calculate
                numOfDays();
                chooseResultType();
                break;
            default:
                // Input value is invalid, input again announcement
                annInvalid();
                runMetric(num);
        }
    }

    // User Chooses result type function
    public void chooseResultType() {
        System.out.println("""
                Select the the result type:
                1. New Total
                2. Up To""");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                // Run function newTotal and get the resultType String to use later in display
                this.resultType = "newtotal";
                newTotal();
                break;
            case 2:
                // Run function upTo and get the resultType String to use later in display
                this.resultType = "upto";
                upTo();
                break;
            default:
                // Input is invalid, announce and ask users to input again
                annInvalid();
                chooseResultType();
        }
    }

    public ArrayList<Integer> newTotal() {
        ArrayList<Integer> groupMax = new ArrayList<>();
        ArrayList<Integer> maxBeforeStartDate = new ArrayList<>();
        // Go through each group in a dataList to calculate data
        if (this.userMetricChoice != "vaccinations") {
            for (int i = 0; i < this.dataList.size(); i++) {
                int sum = 0;

                // Only calculate new covid cases and deaths
                for (String str : this.dataList.get(i)) {
                    if (str == "") {
                        str = "0";
                    }
                    int num = Integer.parseInt(str); // Convert to Int in order to sum up
                    sum += num;
                }

                this.newtotal.add(sum);
            }
        } else {

            ArrayList<Integer> maxEachGroup = new ArrayList<>();
            for (String str : this.listByAreaMetricBeforeStartDate) {
                if (str == "") {
                    str = "0";
                }
                int num = Integer.parseInt(str);
                maxBeforeStartDate.add(num);
            }
            int maxfirst = Collections.max(maxBeforeStartDate);

            for (int i = 0; i < this.dataList.size(); i++) {
                int maxBigger = 0;
                for (String str : this.dataList.get(i)) {
                    if (str == "") {
                        str = "0";
                    }
                    int num = Integer.parseInt(str); // Convert to Int in order to sum up
                    groupMax.add(num);
                }
                maxBigger = Collections.max(groupMax);
                maxEachGroup.add(maxBigger);
            }
            for (int i = 0; i < maxEachGroup.size(); i++) {
                int sum = 0;
                if (i == 0) {
                    sum = maxEachGroup.get(i) - maxfirst;
                } else {
                    sum = maxEachGroup.get(i) - maxEachGroup.get(i - 1);
                }
                this.newtotal.add(sum);
            }
        }
        return this.newtotal;
    }

    public ArrayList<Integer> upTo() {
        int sumBeforeRange = 0;
        int sumPerGroup = 0;
        ArrayList<Integer> listMetricInt = new ArrayList<>();

        // Calculate all the value each group then plus with the sumBefore Range to get
        // upTo of each group in list
        for (int i = 0; i < this.dataList.size(); i++) {
            // Only calculate new covid cases and deaths
            if (this.userMetricChoice != "vaccinations") {
                // Calculate all the value from beginning upto start date for covid new cases
                // and deaths
                for (String str : this.listByAreaMetricBeforeStartDate) {
                    if (str == "") {
                        str = "0";
                    }
                    int num = Integer.parseInt(str);
                    sumBeforeRange += num;
                }
                for (String str : this.dataList.get(i)) {
                    if (str == "") {
                        str = "0";
                    }
                    int num = Integer.parseInt(str);
                    sumPerGroup += num;
                }
                this.upto.add(sumBeforeRange + sumPerGroup);
            } else {
                // Only calculate people got vaccinations
                // Calculate all the value from beginning upto start date for vaccinations
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

    public void getMetricDataAndTimeRange(ArrayList<String> list, ArrayList<String> metric,
                                          ArrayList<String> rangetime) {
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

    // Function supports to create DataList (List contains groups)
    public List<List<String>> createDataList(List<List<String>> list, int num_of_group) {
        list = new ArrayList<>();
        for (int i = 1; i <= num_of_group; i++) {
            List<String> eachGroupList = new ArrayList<>();
            list.add(eachGroupList);
        }
        return list;
    }

    public boolean isContained(String s, String str) {
        String check = "\\b" + str + "\\b";
        Pattern pattern = Pattern.compile(check);
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

    // 3 functions below works together to convert result and time to string to
    // display
    public void convertResultToString() {
        if (this.resultType == "newtotal") {
            for (int num : this.newtotal) {
                String s = String.valueOf(num);
                this.resultString.add(s);
            }
        } else {
            for (int num : this.upto) {
                String s = String.valueOf(num);
                this.resultString.add(s);
            }
        }
    }

    public void convertGroupTimeToArray() {
        String groupFirstDate = "";
        String groupLastDate = "";
        if (this.opt.equals("1")) {
            for (int i = 0; i < this.dataTime.size(); i++) {
                groupFirstDate = this.dataTime.get(i).get(0);
                this.dataTimeString.add(groupFirstDate);
            }
        } else {
            if (this.num_of_group == listUserRangeTime.size()) {
                for (int i = 0; i < this.dataTime.size(); i++) {
                    groupFirstDate = this.dataTime.get(i).get(0);
                    this.dataTimeString.add(groupFirstDate);
                }
            }
            for (int i = 0; i < this.dataTime.size(); i++) {
                groupFirstDate = this.dataTime.get(i).get(0);
                groupLastDate = this.dataTime.get(i).get(this.dataTime.get(i).size() - 1);
                this.dataTimeString.add(groupFirstDate + " " + groupLastDate);
            }
        }

    }

    // Convert both time and result to string
    public void processMetricAndTime() {
        convertResultToString();
        convertGroupTimeToArray();
    }

    // Announcement invalid function
    public void annInvalid() {
        System.out.println("Your input is INVALID. Please, input again!");
    }
}
