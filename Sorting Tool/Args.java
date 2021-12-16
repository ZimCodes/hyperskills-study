package sorting;

class Args {
    private final String[] args;
    public String dataType;
    public String sortType;
    public String inFile;
    public String outFile;

    public Args(String[] args) {
        this.args = args;
        this.parseArgs();
    }

    private void parseArgs() {
        int index = 0;
        while (index < args.length) {
            switch (args[index]) {
                case "-dataType":
                    if (isNotArg(index)) {
                        System.out.println("No data type defined!");
                    } else {
                        index += 1;
                        this.dataType = args[index];
                    }
                    break;
                case "-sortingType":
                    if (isNotArg(index)) {
                        System.out.println("No sorting type defined!");
                    } else {
                        index += 1;
                        this.sortType = args[index];
                    }
                    break;
                case "-inputFile":
                    if (isNotArg(index)) {
                        System.out.println("No input file defined!");
                    } else {
                        index += 1;
                        this.inFile = args[index];
                    }
                    break;
                case "-outputFile":
                    if (isNotArg(index)) {
                        System.out.println("No output file defined!");
                    } else {
                        index += 1;
                        this.outFile = args[index];
                    }
                    break;
                default:
                    System.out.println(args[index] + " is not a valid parameter. It will be " +
                            "skipped.");
                    break;
            }
            index += 1;
        }
        checkDataType();
        checkSortType();
    }

    private boolean isNotArg(int index) {
        int cIndex = index + 1;
        return cIndex >= args.length || args[cIndex].startsWith("-");
    }

    private void checkDataType() {
        if (this.dataType == null) {
            this.dataType = "word";
        }
    }

    private void checkSortType() {
        if (this.sortType == null) {
            this.sortType = "natural";
        }
    }
}