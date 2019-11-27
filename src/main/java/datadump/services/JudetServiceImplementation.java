package datadump.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class JudetServiceImplementation implements JudetService {

    @Override
    public List<Map<String, Object>> getAll(MongoCollection collection) {
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
