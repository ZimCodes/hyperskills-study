package sorting.datatype;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sort implements ISort {
    protected final String inFile;
    protected final String outFile;

    public Sort(String inFile, String outFile) {
        this.inFile = inFile;
        this.outFile = outFile;
    }

    @Override
    public void countSort(Scanner scan) {
    }

    protected Scanner readFile(Scanner scan) {
        if (this.inFile != null) {
            scan.close();
            try {
                return new Scanner(new File(this.inFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return scan;
    }



    @Override
    public void naturalSort(Scanner scan) {

    }
}