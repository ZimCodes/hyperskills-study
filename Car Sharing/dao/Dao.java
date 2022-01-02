package carsharing.dao;

import carsharing.MyDatabase;

public abstract class Dao<T> implements IDao<T> {
    protected final MyDatabase db;
    protected final String tableName;

    public Dao(MyDatabase db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    @Override
    public void close() {
        this.db.close();
    }
}