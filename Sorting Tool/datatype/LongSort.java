package sorting.datatype;

import sorting.datatype.util.DataLong;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class LongSort extends Sort {


    public LongSort(String inFile, String outFile) {
        super(inFile, outFile);
    }

    @Override
    public void countSort(Scanner scan) {
        scan = readFile(scan);
        int total = 0;
        Map<Long, DataLong> map = new HashMap<>();
        while (scan.hasNext()) {
            String phrase = scan.next();
            try {
                long number = Long.parseLong(phrase);
                if (map.containsKey(number)) {
                    map.get(number).incrementOccurrence();
                } else {
                    map.put(number, new DataLong(number));
                }
                total += 1;
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a long. It will be skipped.", phrase);
            }
        }
        List<DataLong> numbers = new ArrayList<>(map.values());
        Collections.sort(numbers);
        if (this.outFile != null) {
            try (FileWriter fwrite = new FileWriter(this.outFile);
                 BufferedWriter bwrite = new BufferedWriter(fwrite);
                 PrintWriter writer = new PrintWriter(bwrite)) {
                writer.println("Total numbers: " + total + ".");
                for (DataLong data : numbers) {
                    writer.printf("%s: %d time(s), %d%%%n", data.getData().toString(),
                            data.getOccurrence(),
                            data.getPercent(total));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Total numbers: " + total + ".");
            for (DataLong data : numbers) {
                System.out.printf("%s: %d time(s), %d%%%n", data.getData().toString(), data.getOccurrence(),
                        data.getPercent(total));
            }
        }
        scan.close();

    }

    @Override
    public void naturalSort(Scanner scan) {
        scan = readFile(scan);
        int total = 0;
        List<Long> list = new ArrayList<>();
        while (scan.hasNext()) {
            String phrase = scan.next();
            try {
                list.add(Long.parseLong(phrase));
                total += 1;
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a long. It will be skipped.", phrase);
            }
        }
        Collections.sort(list);
        if (this.outFile != null) {
            try (FileWriter fwrite = new FileWriter(this.outFile);
                 BufferedWriter bwrite = new BufferedWriter(fwrite);
                 PrintWriter writer = new PrintWriter(bwrite)) {
                writer.println("Total numbers: " + total + ".");
                writer.print("Sorted data: ");
                list.forEach(num -> writer.printf("%d ", num));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Total numbers: " + total + ".");
            System.out.print("Sorted data: ");
            list.forEach(num -> System.out.printf("%d ", num));
        }
        scan.close();
    }
}