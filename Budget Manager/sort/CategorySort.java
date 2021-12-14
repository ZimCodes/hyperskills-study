package budget.sort;

import budget.Compute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategorySort implements Sort {
    @Override
    public void sort(Map<String, List<String>> map) {
        Map<String, Double> checkMap = new HashMap<>();
        this.initMap(checkMap);
        System.out.println("Types:");
        if (map.isEmpty()) {
            for (String cat : checkMap.keySet()) {
                System.out.println(cat + " - $0");
            }
            System.out.println("Total sum: $0\n");
        } else {
            double grandTotal = 0;
            for (Map.Entry<String, List<String>> keySet : map.entrySet()) {
                List<String> items = keySet.getValue();
                double total = 0;
                for (String item : items) {
                    total += Compute.computeTotal(item);
                }
                grandTotal += total;
                checkMap.put(keySet.getKey(), total);
            }
            List<String> catList = new ArrayList<>();
            for (Map.Entry<String, Double> check : checkMap.entrySet()) {
                catList.add(String.format("%s - $%.2f", check.getKey(), check.getValue()));
            }
            Compute.bubbleSortDesc(catList);
            for (String cat : catList) {
                System.out.println(cat);
            }
            System.out.printf("Total sum: $%.2f%n%n", grandTotal);
        }
    }

    private void initMap(Map<String, Double> map) {
        map.put("Food", 0.0);
        map.put("Entertainment", 0.0);
        map.put("Clothes", 0.0);
        map.put("Other", 0.0);
    }
}