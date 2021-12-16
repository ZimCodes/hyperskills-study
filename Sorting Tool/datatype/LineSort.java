package sorting.datatype;

import java.io.*;
import java.util.*;

import sorting.datatype.util.DataString;

public class LineSort extends Sort {

    public LineSort(String inFile, String outFile) {
        super(inFile, outFile);
    }

    @Override
    public void countSort(Scanner scan) {
        scan = readFile(scan);
        int total = 0;
        Map<String, DataString> map = new HashMap<>();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (map.containsKey(line)) {
                map.get(line).incrementOccurrence();
            } else {
                map.put(line, new DataString(line));
            }
            total += 1;
        }
        List<DataString> lines = new ArrayList<>(map.values());
        Collections.sort(lines);
        if (this.outFile != null) {
            try (FileWriter fwrite = new FileWriter(this.outFile);
                 BufferedWriter bwrite = new BufferedWriter(fwrite);
                 PrintWriter writer = new PrintWriter(bwrite)) {
                writer.println("Total lines: " + total + ".");
                for (DataString data : lines) {
                    writer.printf("%s: %d time(s), %d%%%n", data.getData(),
                            data.getOccurrence(),
                            data.getPercent(total));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Total lines: " + total + ".");
            for (DataString data : lines) {
                System.out.printf("%s: %d time(s), %d%%%n", data.getData(), data.getOccurrence(),
                        data.getPercent(total));
            }
        }
        scan.close();
    }

    @Override
    public void naturalSort(Scanner scan) {
        scan = readFile(scan);
        int total = 0;
        List<String> list = new ArrayList<>();
        while (scan.hasNextLine()) {
            list.add(scan.nextLine());
            total += 1;
        }
        Collections.sort(list);
        if (this.outFile != null) {
            try (FileWriter fwrite = new FileWriter(this.outFile);
                 BufferedWriter bwrite = new BufferedWriter(fwrite);
                 PrintWriter writer = new PrintWriter(bwrite)) {
                writer.println("Total lines: " + total);
                writer.println("Sorted data:");
                list.forEach(writer::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Total lines: " + total);
            System.out.println("Sorted data:");
            list.forEach(System.out::println);
        }
        scan.close();
    }
}