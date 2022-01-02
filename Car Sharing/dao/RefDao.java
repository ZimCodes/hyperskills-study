package carsharing.dao;

import carsharing.MyDatabase;

import java.util.List;

public abstract class RefDao<T, U> extends Dao<T> {
    protected final String refTableName;

    public RefDao(MyDatabase db, String tableName, String refTableName) {
        super(db, tableName);
        this.refTableName = refTableName;
    }

    public abstract List<U> getRefs(int refID);
}