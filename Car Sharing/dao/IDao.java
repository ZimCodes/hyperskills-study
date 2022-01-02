package carsharing.dao;

import java.util.List;

public interface IDao<T>{
    List<T> getAll();
    T get(int id);
    void insert(T item);
    void close();
}