package sorting.datatype.util;

public class DataLong extends Data<Long>{
    public DataLong(Long data) {
        super(data);
    }
    @Override
    public int compareTo(Data other){
        int result = Integer.compare(this.occurrence, other.occurrence);
        if (result == 0){
            DataLong otherObj = (DataLong) other;
            result = data.compareTo(otherObj.data);
        }
        return result;
    }

}