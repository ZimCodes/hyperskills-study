package platform.compare;

import java.util.Comparator;
import platform.model.Record;

public class DateComparator implements Comparator<Record> {

    @Override
    public int compare(Record o1, Record o2) {
        return o1.getDate().compareTo(o2.getDate());
    }

    @Override
    public Comparator<Record> reversed() {
        return Comparator.super.reversed();
    }
}