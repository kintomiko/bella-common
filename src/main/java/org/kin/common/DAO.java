package org.kin.common;


import org.kin.common.model.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kindai on 9/07/16.
 */
public interface DAO<T extends Entity> {
    T getObject(String id);
    List<T> getObjects(String id);
    T update(T obj);
    T delete(Serializable id);
    T create(T obj);
    List<T> getAll();
}
