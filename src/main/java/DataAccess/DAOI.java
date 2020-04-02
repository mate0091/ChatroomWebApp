package DataAccess;

import java.sql.ResultSet;
import java.util.List;

public interface DAOI<T>
{
    List<T> findAll();
    T findById(int id);
    boolean insert(T obj);
    boolean update(T obj1, T obj2);
    boolean delete(T obj);
}
