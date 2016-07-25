package org.kin.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.kin.common.model.Entity;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kindai on 9/07/16.
 */
public class MongoDAO<T extends Entity> implements DAO<T>{

    @Autowired
    MongoConnector mongoConnector;

    static Gson gson=new GsonBuilder().create();

    protected Class<T> entityClass;

    @Override
    public T getObject(String id) {
        List<DBObject> objects = mongoConnector.getObject(this.getTClass().getName(), id);
        if(objects.size()<1)
            return null;
        return gson.fromJson(gson.toJson(objects.get(0)), getTClass());
    }

    @Override
    public List getObjects(String id) {
        List<DBObject> objects = mongoConnector.getObject(this.getTClass().getName(), id);
        List<T> rst = new ArrayList<T>();
        for(DBObject object:objects){
            rst.add(gson.fromJson(gson.toJson(object), getTClass()));
        }
        return rst;
    }

    @Override
    public T update(T obj) {
        DBObject updateObj = (DBObject) JSON.parse(gson.toJson(obj));
        updateObj.put("_id", obj.get_id());
        mongoConnector.update(getTClass().getName(), updateObj);
        return obj;
    }

    @Override
    public T delete(Serializable id) {
        return null;
    }

    @Override
    public T create(T obj) {
        DBObject updateJson = (DBObject) JSON.parse(gson.toJson(obj));
        WriteResult result = mongoConnector.create(getTClass().getName(), updateJson);
        return gson.fromJson(gson.toJson(updateJson), getTClass());
    }

    @Override
    public List<T> getAll() {
        List<DBObject> objects = mongoConnector.getAll(getTClass().getName());
        List<T> rst = new ArrayList<T>();
        for(DBObject object:objects){
            rst.add(gson.fromJson(gson.toJson(object), getTClass()));
        }
        return rst;
    }

    private Class<T> getTClass()
    {
        return entityClass;
    }
}
