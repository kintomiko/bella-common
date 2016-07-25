package org.kin.common;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kindai on 9/07/16.
 */
public class MongoConnector{

    public static final String ID = "_id";

    MongoClient client;

    String dbname;

    DB db;

    public MongoConnector(String dbname, MongoClient client){
        this.dbname = dbname;
        this.client = client;
        db = client.getDB(dbname);
    }

    public List<DBObject> getObject(String collection, String id) {
        DBCollection table = db.getCollection(collection);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(ID, new ObjectId(id));

        DBCursor cursor = table.find(searchQuery);

        List<DBObject> rst = new ArrayList<>();
        while(cursor.hasNext()){
            rst.add(cursor.next());
        }
        return rst;
    }

    public WriteResult update(String collection, DBObject obj) {
        DBCollection table = db.getCollection(collection);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(ID, obj.get(ID));

        BasicDBObject update = new BasicDBObject();
        update.put("$set", obj);

        return table.update(searchQuery, update);
    }

    public DBObject delete(String collection, String id) {
        DBCollection table = db.getCollection(collection);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(ID, new ObjectId(id));
        return table.findAndRemove(searchQuery);
    }

    public WriteResult create(String collection, DBObject obj) {
        DBCollection table = db.getCollection(collection);

        BasicDBObject searchQuery = new BasicDBObject();
        return table.insert(obj);
    }

    public List<DBObject> getAll(String collection){
        DBCollection table = db.getCollection(collection);
        DBCursor cursor = table.find();

        List<DBObject> rst = new ArrayList<>();
        while(cursor.hasNext()){
            rst.add(cursor.next());
        }
        return rst;
    }
}
