package sorting.datatype.util;

public abstract class Data<T> implements Comparable<Data<T>> {
    protected int occurrence;
    protected final T data;
    public Data(T data) {
        this.data = data;
        this.occurrence = 1;
    }
    public int getOccurrence(){
        return this.occurrence;
    }
    public void incrementOccurrence(){
        this.occurrence++;
    }
    public T getData(){
        return this.data;
    }
    public int getPercent(int total) {
        return (this.occurrence * 100) / total;
    }

    @Override
    public int compareTo(Data other){
        return Integer.valueOf(this.occurrence).compareTo(other.occurrence);
    }
}