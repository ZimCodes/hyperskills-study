package carsharing.util;

public class View {

    public static void menuOptions(String[] opts, String... more) {
        String[] combinedOpts = more.length == 0 ? opts : mergeArrays(opts, more);
        int id = 1;
        for (String opt : combinedOpts) {
            if ("exit".equalsIgnoreCase(opt) || "back".equalsIgnoreCase(opt)) {
                View.println("0. " + opt);
            } else {
                View.println(id + ". " + opt);
            }
            id++;
        }
    }

    private static String[] mergeArrays(String[] arr1, String[] arr2) {
        String[] mergeArr = new String[arr1.length + arr2.length];
        int index = 0;
        for (String str : arr1) {
            mergeArr[index] = str;
            index++;
        }
        for (String str : arr2) {
            mergeArr[index] = str;
            index++;
        }
        return mergeArr;
    }

    public static void println(String str) {
        System.out.println(str);
    }

    public static void print(String str) {
        System.out.print(str);
    }

    public static void line() {
        println("");
    }
}