package datadump;

import com.mongodb.client.MongoCollection;
import datadump.accessingdatamongodb.JudetRepository;
import datadump.accessingdatamongodb.TestCollection;
import datadump.accessingdatamongodb.TestCollectionRepository;
import datadump.common.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import datadump.services.StorageService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static com.mongodb.client.model.Filters.exists;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class DatadumpApplication implements CommandLineRunner {

	@Autowired
	private TestCollectionRepository repository;

	@Autowired
	private JudetRepository repositoryJudet;

	@Autowired
	private MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DatadumpApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(StorageService storageService) {
//		return (args) -> {
//			storageService.deleteAll();
//			storageService.init();
//		};
//	}

	public static List readCSV() throws FileNotFoundException, IOException {

		List judete = new ArrayList<>();

		BufferedReader br = new BufferedReader(new FileReader("D:\\Educ\\exemplu_harta.csv"));
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
	public void run(String... args) throws  Exception{

		System.out.println(repository.findTestCollectionByData("b"));
//		System.out.println(repository.findById("5dd0652fdc9af440fdb3bc2c"));
//		System.out.println(repository.findAll(Example.of(new TestCollection("a"), ExampleMatcher.matching().withMatcher("data", startsWith().ignoreCase()))).get(0).toString());
		if (!mongoTemplate.collectionExists(TestCollection.class)) {
			mongoTemplate.createCollection(TestCollection.class);
		}

		MongoCollection collection = null;

		if (!mongoTemplate.collectionExists("Judet")) {
			collection = mongoTemplate.createCollection("Judet");
		} else {
			collection = mongoTemplate.getCollection("Judet");
		}

//		List judete = readCSV();
//		for (Object item: judete) {
//			Document judet = new Document((Map<String, Object>) item);
//			Document query = new Document("judet", judet.getString("judet"));
//			//BSONObject q = new BasicBSONObject();
//			//q.put("judet", ((Map<String, Object>) item).get("judet"));
//
//			/*List results = new ArrayList<>();
//			collection.find(query).into(results);*/
//			Document oldValue =  (Document) collection.find(query).first();
//			if(oldValue != null){
//				collection.replaceOne(oldValue ,judet);
//			}else{
//				collection.insertOne(judet);
//			}
//		}

//		System.out.println(mongoTemplate.findAll(TestCollection.class));
	}

}
