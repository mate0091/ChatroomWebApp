package DataAccess;

import java.sql.ResultSet;
import java.util.List;

public interface DAOI<T>
{
    List<T> findAll();
    T findById(int id);
    int insert(T obj);
    boolean update(int id, T obj2);
    boolean delete(int id);
}
