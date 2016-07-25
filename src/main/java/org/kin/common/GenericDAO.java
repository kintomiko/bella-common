package org.kin.common;


import org.kin.common.model.Entity;

/**
 * Created by kindai on 9/07/16.
 */
final class GenericDAO<T extends Entity> extends MongoDAO<T> {

    public GenericDAO(Class<T> entityClass){
        this.entityClass = entityClass;
    }

}
