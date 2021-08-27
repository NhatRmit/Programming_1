import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

public class Display {
    static Summary s = new Summary();

    public void tableWithLinesAndMaxWidth() throws FileNotFoundException, Exception {
        boolean rowJustifyLeft = true;
        int tableMaxWidth = 80;
        s.summary();

        String[][] tabular = new String[(s.resultString.size())+1][2];
        tabular[0][0] = "Range";
        tabular[0][1] = "Value";

        for (int i = 1; i < tabular.length; i++) {
            tabular[i][0] = s.dataTimeString.get(i-1);
        }

        for (int i = 1; i < tabular.length; i++) {
            tabular[i][1] = s.resultString.get(i-1);
        }

        ArrayList<String[]> table = new ArrayList<>(Arrays.asList(tabular));
        ArrayList<String[]> tableFinale = new ArrayList<>();
        for (String[] row : table) {
            boolean extraRow = false;
            int split = 0;
            do {
                extraRow = false;
                String[] addRow = new String[row.length];
                for (int i = 0; i < row.length; i++) {
                    if (row[i].length() < tableMaxWidth) {
                        addRow[i] = split == 0 ? row[i] : "";
                    } else if ((row[i].length() > (split * tableMaxWidth))) {
                        int end = row[i].length() > ((split * tableMaxWidth) + tableMaxWidth)
                                ? (split * tableMaxWidth) + tableMaxWidth
                                : row[i].length();
                                addRow[i] = row[i].substring((split * tableMaxWidth), end);
                        extraRow = true;
                    } else {
                        addRow[i] = "";
                    }
                }
                tableFinale.add(addRow);
                if (extraRow) {
                    split++;
                }
            } while (extraRow);
        }

        String[][] getTable = new String[tableFinale.size()][tableFinale.get(0).length];
        for (int i = 0; i < getTable.length; i++) {
            getTable[i] = tableFinale.get(i);
        }

        HashMap<Integer, Integer> colLength = new HashMap<>();
        Arrays.stream(getTable)
            .forEach(thisTable -> Stream.iterate(0, (i -> i < thisTable.length), (i -> ++i))
            .forEach(i -> {
                    if (colLength.get(i) == null) {
                        colLength.put(i, 0);
                    }
                    if (colLength.get(i) < thisTable[i].length()) {
                        colLength.put(i, thisTable[i].length());
                    }}
            ));

        final StringBuilder getStringFormatter = new StringBuilder("");

        String flag = rowJustifyLeft ? "-" : "";

        colLength.entrySet().stream().forEach(e -> getStringFormatter.append("| %" + flag + e.getValue() + "s "));
        
        getStringFormatter.append("|\n");

        String getHoriz = colLength.entrySet().stream()
        .reduce("", (line, b) -> {
            String lineMaterial = "--";
            lineMaterial = lineMaterial + Stream.iterate(0, (i -> i < b.getValue()), (i -> ++i)).reduce("", (line1, b1) -> line1 + "-", (a1, b1) -> a1 + b1);
            lineMaterial = lineMaterial + "-";
            return line + lineMaterial;
        }, (a, b) -> a + b);

        getHoriz = getHoriz + "\n";
        
        String horizLine = getHoriz;

        System.out.print(horizLine);
        
        Arrays.stream(getTable).limit(1).forEach(temp -> System.out.printf(getStringFormatter.toString(), temp));

        System.out.print(horizLine);

        Stream.iterate(1, (i -> i < getTable.length), (i -> ++i))
                .forEach(temp -> {
                    System.out.printf(getStringFormatter.toString(), getTable[temp]);
                    System.out.print(horizLine);
                });
        
    }
}


