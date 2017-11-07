package com.sun.mybatis.dao;

import java.util.List;

/**
 * Created by zhikunsun on 2017/11/7.
 */
public interface GenericCrudDAO<E> {
    void insert(E entity);

    void batchInsert(List<E> list);

    int update(E entity);

    E queryById(long id);

    List<E> query(Object query);

    int count(Object query);
}
