package datadump.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface JudetService {
      List<Map<String, Object>> getAll(MongoCollection collection);
      List readCSV(String fileName) throws FileNotFoundException, IOException;
      void saveChanges(List judete);
}
