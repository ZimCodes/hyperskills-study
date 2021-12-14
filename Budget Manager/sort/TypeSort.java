package budget.sort;

import budget.Compute;

import java.util.List;
import java.util.Map;

public class TypeSort implements Sort {
    private final String category;

    public TypeSort(String category) {
        this.category = category;
    }

    @Override
    public void sort(Map<String, List<String>> map) {
        if (map.isEmpty() || !map.containsKey(this.category) || map.get(this.category).isEmpty()) {
            System.out.println("The purchase list is empty!\n");
        } else {
            System.out.println(this.category + ":");
            List<String> items = map.get(this.category);
            double total = 0;
            Compute.bubbleSortDesc(items);
            for (String item : items) {
                System.out.println(item);
                total += Compute.computeTotal(item);
            }
            System.out.printf("Total sum: $%.2f%n%n", total);
        }
    }
}