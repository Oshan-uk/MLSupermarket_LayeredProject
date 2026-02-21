package lk.ijse.mlsupermarket.dao;

import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {

    boolean save(T entity) throws Exception;

    boolean update(T entity) throws Exception;

    boolean delete(String id) throws Exception;

    String generateId() throws Exception;

    T search(String id) throws Exception;

    ArrayList<T> getAll() throws Exception;
}