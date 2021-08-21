import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Summary {
    int num_of_group, daysInput;
    String path = "java/src/covid-data.csv";
    String line = "";
    Scanner sc = new Scanner(System.in);
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> userList = new ArrayList<>();
    ArrayList<String> timerange = new ArrayList<>();
    ArrayList<String> cases = new ArrayList<>();
    ArrayList<String> deaths = new ArrayList<>();
    ArrayList<String> vaccinations = new ArrayList<>();
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

        System.out.println(list.get(0));

        for (String e : list) {
            for (int i = 0; i < user.range.length; i++) {
                if (isContain(e, user.range[i]) == true && isContain(e, user.area) == true) {
                    timerange.add(user.range[i]);
                    userList.add(e);
                }
            }
        }

        // convert list to array
        String[] userListConvert = new String[userList.size()];
        userListConvert = userList.toArray(userListConvert);

        String[] value;

        // get each index
        for (String s : userListConvert) {
            value = s.split(",");
            cases.add(value[4]);
            deaths.add(value[5]);
            vaccinations.add(value[6]);
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
                groupData();
        }
    }

    public void noGrouping(ArrayList<String> metric) {
        this.dataList = createDataList(userList.size());
            
                for (int i = 0; i < userList.size(); i++) {
                    for (int j = 1; j <= 1; j++) {
                        (this.dataList.get(i)).add(metric.get((j - 1) + i));
                    }
                }
                
        
        displayGrouping(this.dataList);
    }

    public void calnumofgroup(ArrayList<String> name, int num_of_group, int qty_days, int checkdays) {
        
    }

    public void numOfGroups(ArrayList<String> metric) {

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
                    (dataList.get(i)).add(metric.get((j - 1) + value + count));
                }
                count++;
                pos_datalist--;
            } else {
                for (int j = 1; j <= qty_days; j++) {
                    (this.dataList.get(i)).add(metric.get((j - 1) + value));
                }
            }
        }
            }
            displayGrouping(this.dataList);
        } else {
            System.out.println("invalid");
            numOfGroups(metric);
        }
    }

    public void numOfDays(ArrayList<String> metric) {
        this.num_of_group = userList.size() / this.daysInput; // N.O groups in case % == 0
        // Check
        if (userList.size() % this.daysInput == 0) {
            this.dataList = createDataList(this.num_of_group);
            int flag = 0;
            
                
                    for (int i = 0; i < this.dataList.size(); i++) {
                        for (int j = 0; j < this.daysInput; j++) {
                            (this.dataList.get(i)).add(metric.get(flag));
                            flag++;
                        }
                    }
            
            displayGrouping(this.dataList);
        } else {
            System.out.println("INVALID");
            numOfDays(metric);
        }
    }


    public int inputMetric(int num) {
        System.out.println("""
                Select the information you want to see:
                1. New Cases
                2. New Deaths
                3. People Vaccinated""");
        int opt = sc.nextInt();
        switch (opt) {
            case 1:
                switch (num) {
                    case 1:
                        noGrouping(cases);
                        chooseResultType(cases);
                        break;
                    case 2:
                        numOfGroups(cases);
                        chooseResultType(cases);
                        break;
                    case 3: 
                        numOfDays(cases);
                        chooseResultType(cases);
                        break;
                }
                break;
            case 2: 
                switch (num) {
                    case 1:
                        noGrouping(deaths);
                        chooseResultType(deaths);
                        break;
                    case 2:
                        numOfGroups(deaths);
                        chooseResultType(deaths);
                        break;
                    case 3: 
                        numOfDays(deaths);
                        chooseResultType(deaths);
                        break;
                }       
            case 3: 
                switch (num) {
                    case 1:
                        noGrouping(vaccinations);
                        chooseResultType(vaccinations);
                        break;
                    case 2:
                        numOfGroups(vaccinations);
                        chooseResultType(vaccinations);
                        break;
                    case 3: 
                        numOfDays(vaccinations);
                        chooseResultType(vaccinations);
                        break;
                }       
                 
            default:
                break;
        }
        return 1;
    }

    public ArrayList<Integer> newTotal(ArrayList<String> metric) {
        ArrayList<Integer> newtotal = new ArrayList<>();
        // go through each group after grouping
        for (int i = 0; i < this.dataList.size(); i++) {
            int sum = 0;
            if(metric != vaccinations) {
                for (String str : this.dataList.get(i)) {
                    int num = Integer.parseInt(str);
                    sum += num;
                }
            } else {
                for (int j = 0 ; j < this.dataList.get(i).size(); j++) {
                    int first_num = Integer.parseInt((this.dataList.get(i)).get(0));
                    int last_num = Integer.parseInt((this.dataList.get(i)).get(this.dataList.get(i).size()-1));
                    sum = last_num - first_num;
                }
            }
            newtotal.add(sum);
        }
        return newtotal;
    }

    public int upTo() {

        return -1;
    }

    public void chooseResultType(ArrayList<String> metric) {
        System.out.println("""
                Select the the result type:
                1. New Total
                2. Up To""");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                System.out.println(newTotal(metric));
                break;
            case 2:
                System.out.println(upTo());
                break;
            default:
                System.out.println("Invalid!");
                chooseResultType(metric);
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
}