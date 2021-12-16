package sorting.datatype;

import sorting.datatype.util.DataString;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class WordSort extends Sort {

    public WordSort(String inFile, String outFile) {
        super(inFile, outFile);
    }

    @Override
    public void countSort(Scanner scan) {
        scan = readFile(scan);
        int total = 0;
        Map<String, DataString> map = new HashMap<>();
        while (scan.hasNext()) {
            String word = scan.next();
            if (map.containsKey(word)) {
                map.get(word).incrementOccurrence();
            }else{
                map.put(word, new DataString(word));
            }
            total += 1;
        }
        List<DataString> words = new ArrayList<>(map.values());
        Collections.sort(words);
        if (this.outFile != null){
            try (FileWriter fwrite = new FileWriter(this.outFile);
                 BufferedWriter bwrite = new BufferedWriter(fwrite);
                 PrintWriter writer = new PrintWriter(bwrite)) {
                writer.println("Total words: " + total + ".");
                for (DataString data : words) {
                    writer.printf("%s: %d time(s), %d%%%n", data.getData(),
                            data.getOccurrence(),
                            data.getPercent(total));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Total words: " + total + ".");
            for (DataString data : words) {
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
        while (scan.hasNext()) {
            list.add(scan.next());
            total += 1;
        }
        Collections.sort(list);
        if (this.outFile != null){
            try (FileWriter fwrite = new FileWriter(this.outFile);
                 BufferedWriter bwrite = new BufferedWriter(fwrite);
                 PrintWriter writer = new PrintWriter(bwrite)) {
                writer.println("Total words: " + total + ".");
                writer.print("Sorted data: ");
                list.forEach(word -> writer.printf("%s ", word));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Total words: " + total + ".");
            System.out.print("Sorted data: ");
            list.forEach(word -> System.out.printf("%s ", word));
        }
        scan.close();
    }
}