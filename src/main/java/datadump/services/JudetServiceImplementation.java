package datadump.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class JudetServiceImplementation implements JudetService {

    @Autowired
    private MongoTemplate mongoTemplate;

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

    @Override
    public List readCSV(String fileName) throws FileNotFoundException, IOException {
            List judete = new ArrayList<>();

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            String[] headerFields = null;
            if((line = br.readLine()) != null && !line.isEmpty()){
                headerFields = line.split(",");
            }
            int nrFields = headerFields.length;

            while ((line = br.readLine()) != null && !line.isEmpty() && headerFields != null) {
                HashMap<String, Object> judet = new HashMap<>();
                String[] values = line.split(",");
                for(int i=0; i<nrFields; i++){
                    judet.put(headerFields[i], values[i]);
                }
                judete.add(judet);
            }
            br.close();

            return judete;
    }

    @Override
    public void saveChanges(List judete) {
        for (Object item: judete) {
			Document judet = new Document((Map<String, Object>) item);
			Document query = new Document("judet", judet.getString("judet"));
            MongoCollection collection = null;

            if (!mongoTemplate.collectionExists("Judet")) {
                collection = mongoTemplate.createCollection("Judet");
            } else {
                collection = mongoTemplate.getCollection("Judet");
            }
			Document oldValue =  (Document) collection.find(query).first();
			if(oldValue != null){
				collection.replaceOne(oldValue ,judet);
			}else{
				collection.insertOne(judet);
			}
		}
    }
}
