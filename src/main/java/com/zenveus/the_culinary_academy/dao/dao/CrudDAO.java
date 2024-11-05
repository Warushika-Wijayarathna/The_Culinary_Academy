package com.zenveus.the_culinary_academy.dao.dao;

import com.zenveus.the_culinary_academy.dao.SuperDAO;

import java.util.List;

public interface CrudDAO<T> extends SuperDAO {
    boolean add(T entity) throws Exception;

    boolean delete(T entity) throws Exception;

    boolean update(T entity) throws Exception;

    Object search(T entity) throws Exception;

    List<T> getAll() throws Exception;
}
