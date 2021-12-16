package sorting.datatype.util;


public class DataString extends Data<String> {
    public DataString(String data) {
        super(data);
    }

    @Override
    public int compareTo(Data other) {
        int result = Integer.compare(this.occurrence, other.occurrence);
        if (result == 0) {
            DataString otherObj = (DataString) other;
            result = data.compareTo(otherObj.data);
        }
        return result;
    }
}