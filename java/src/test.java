import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        List<List<Integer>> dataList = new ArrayList<List<Integer>>();
        
        for(int i = 1; i <= 4; i++)
        {
            List<Integer> tempList = new ArrayList<Integer>();
            dataList.add(tempList);
        }

        for(int i = 1; i <= 4; i++)
        {
            int value = 5+i;
            dataList.get(i - 1).add(value);
        }
    }
}