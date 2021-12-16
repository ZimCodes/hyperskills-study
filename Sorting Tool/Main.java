package sorting;

import java.util.*;

import sorting.datatype.*;

public class Main {
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
        Args argParse = new Args(args);
        ISort sortData = null;
        switch (argParse.dataType) {
            case "long":
                sortData = new LongSort(argParse.inFile,argParse.outFile);
                break;
            case "line":
                sortData = new LineSort(argParse.inFile,argParse.outFile);
                break;
            default:
                sortData = new WordSort(argParse.inFile,argParse.outFile);
                break;
        }
        if ("byCount".equals(argParse.sortType)) {
            sortData.countSort(scanner);
        }
        else if ("natural".equals(argParse.sortType)) {
            sortData.naturalSort(scanner);
        }
    }
}
