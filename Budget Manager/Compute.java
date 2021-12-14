package budget;

import java.util.List;

public class Compute {
    public static double computeTotal(String item) {
        int index = item.lastIndexOf('$');
        String price = item.substring(index + 1);
        return Double.parseDouble(price);
    }

    public static void bubbleSortDesc(List<String> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = 1; j < items.size() - i; j++) {
                String firstItem = items.get(j - 1);
                String secondItem = items.get(j);
                double firstPrice = computeTotal(firstItem);
                double secondPrice = computeTotal(secondItem);
                if (firstPrice < secondPrice) {
                    items.set(j - 1, secondItem);
                    items.set(j, firstItem);
                }
            }
        }
    }
}