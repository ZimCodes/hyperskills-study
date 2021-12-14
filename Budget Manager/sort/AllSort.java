package budget.sort;

import budget.Compute;

import java.util.*;

public class AllSort implements Sort {

    @Override
    public void sort(Map<String, List<String>> map) {
        if (map.isEmpty()) {
            System.out.println("The purchase list is empty!\n");
        } else{
            System.out.println("All:");
            double total = 0;
            List<String> allList = new ArrayList<>();
            for (Map.Entry<String, List<String>> keySet: map.entrySet()) {
                List<String> values = keySet.getValue();
                for(String item: values){
                    allList.add(item);
                    total += Compute.computeTotal(item);
                }
            }
            Compute.bubbleSortDesc(allList);
            for(String item: allList){
                System.out.println(item);
            }

            System.out.printf("Total: $%.2f%n%n",total);
        }
    }
}