import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

public class Display {
    static Summary s = new Summary();

    public void tableWithLinesAndMaxWidth() throws FileNotFoundException, Exception {
        boolean leftJustifiedRows = true;
        int maxWidth = 30;
        s.summary();

        String[][] table = new String[s.uptoString.size()][2];

        for (int i = 0; i < table.length; i++) {
            table[i][0] = s.dataTimeString.get(i);
        }

        for (int i = 0; i < table.length; i++) {
            table[i][1] = s.uptoString.get(i);
        }

        // ArrayList<String[]> listTablePrep = new ArrayList<>(Arrays.asList(table));
        // ArrayList<String[]> listTablePrint = new ArrayList<>();
        // for (String[] row : listTablePrep) {
        //     boolean needExtraRow = false;
        //     int splitRow = 0;
        //     do {
        //         needExtraRow = false;
        //         String[] newRow = new String[row.length];
        //         for (int i = 0; i < row.length; i++) {
        //             if (row[i].length() < maxWidth) {
        //                 newRow[i] = splitRow == 0 ? row[i] : "";
        //             } else if ((row[i].length() > (splitRow * maxWidth))) {
        //                 int end = row[i].length() > ((splitRow * maxWidth) + maxWidth)
        //                         ? (splitRow * maxWidth) + maxWidth
        //                         : row[i].length();
        //                 newRow[i] = row[i].substring((splitRow * maxWidth), end);
        //                 needExtraRow = true;
        //             } else {
        //                 newRow[i] = "";
        //             }
        //         }
        //         listTablePrint.add(newRow);
        //         if (needExtraRow) {
        //             splitRow++;
        //         }
        //     } while (needExtraRow);
        // }

        // String[][] display = new String[listTablePrint.size()][listTablePrint.get(0).length];
        // for (int i = 0; i < display.length; i++) {
        //     display[i] = listTablePrint.get(i);
        // }

        // HashMap<Integer, Integer> columnLengths = new HashMap<>();
        // Arrays.stream(display).forEach(a -> Stream.iterate(0, (i -> i < a.length), (i -> ++i)).forEach(i -> {
        //     if (columnLengths.get(i) == null) {
        //         columnLengths.put(i, 0);
        //     }
        //     if (columnLengths.get(i) < a[i].length()) {
        //         columnLengths.put(i, a[i].length());
        //     }
        // }));

        // final StringBuilder formatString = new StringBuilder("");
        // String flag = leftJustifiedRows ? "-" : "";
        // columnLengths.entrySet().stream().forEach(e -> formatString.append("| %" + flag + e.getValue() + "s "));
        // formatString.append("|\n");

        // String line = columnLengths.entrySet().stream().reduce("", (ln, b) -> {
        //     String templn = "+-";
        //     templn = templn + Stream.iterate(0, (i -> i < b.getValue()), (i -> ++i)).reduce("", (ln1, b1) -> ln1 + "-",
        //             (a1, b1) -> a1 + b1);
        //     templn = templn + "-";
        //     return ln + templn;
        // }, (a, b) -> a + b);
        // line = line + "+\n";

        // System.out.print(line);
        // Arrays.stream(display).limit(1).forEach(a -> System.out.printf(formatString.toString(), a));

        // Stream.iterate(1, (i -> i < display.length), (i -> ++i))
        //         .forEach(a -> System.out.printf(formatString.toString(), display[a]));
        // System.out.print(line);
        
    }
}

