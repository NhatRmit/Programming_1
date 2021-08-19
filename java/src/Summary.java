import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Summary {
    public void sum() throws Exception {
        String path = "src/covid-data.csv";
        String line = "";
        Scanner sc = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> timerange = new ArrayList<>();
        ArrayList<String> area = new ArrayList<>();
        Data user = new Data();
        user.inputArea();
        user.timeRangeType();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                list.add(line);
            }

            System.out.println(list.get(0));

            for (String e : list) {
                for (int i = 0; i < user.range.length; i++) {
                    if (isContain(e,  user.range[i]) == true && isContain(e, user.area) == true) {
                        data.add(e);
                        timerange.add( user.range[i]);
                        area.add(user.area);
                    }
                }
            }

            System.out.println("Please choose number of group that you want: ");
            int num_of_group = sc.nextInt();
            int qty_days = timerange.size() / num_of_group;
            int checkdays = timerange.size() % num_of_group;

            System.out.println("total days " + timerange.size());
            System.out.println("quantity of days " + qty_days);
            System.out.println("check days " + checkdays);
            System.out.println("num of group " + num_of_group);

            if((num_of_group >= 2 || num_of_group < 80) && (num_of_group <= timerange.size())) {
                if (checkdays <= timerange.size()) {
                    List<List<String>> dataList = new ArrayList<List<String>>();
                    for (int i = 1; i <= num_of_group; i++) {
                        List<String> tempList = new ArrayList<String>();
                        dataList.add(tempList);
                    }
                    int count = 0;
                    int pos_datalist=checkdays;
                    for (int i = 0; i < num_of_group; i++) {
                        int value = i * qty_days;
                        if(i == num_of_group - pos_datalist) {
                            for (int j = 1; j <= qty_days+1; j++) {      
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
                    for (List<String> s : dataList) {
                        System.out.println(s);
                    }
                }

            } else {
                System.out.println("invalid");
            }
            
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public boolean isContain(String source, String subItem) {
        String pattern = "\\b" + subItem + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        return m.find();
    }
}