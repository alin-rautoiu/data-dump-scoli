package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JudetService {
    public static List<Map<String, Object>> getAll(MongoCollection collection){
        List<Map<String, Object>> values = new LinkedList<>();

        MongoCursor cursor = collection.find().projection(Projections.excludeId()).cursor();
        try {
            while (cursor.hasNext()) {
                values.add((Map<String, Object>) cursor.next());
            }
        }
        finally {
            cursor.close();
        }

        return values;
    }
}
