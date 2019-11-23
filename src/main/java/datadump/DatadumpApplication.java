package datadump;

import com.mongodb.client.MongoCollection;
import datadump.accessingdatamongodb.Judet;
import datadump.accessingdatamongodb.JudetRepository;
import datadump.accessingdatamongodb.TestCollection;
import datadump.accessingdatamongodb.TestCollectionRepository;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static com.mongodb.client.model.Filters.exists;

@SpringBootApplication
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
//		repository.save(new TestCollection("a"));
//		repository.save(new TestCollection("b"));
//		repository.save(new TestCollection("c"));

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


		/*List judete = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader("exemplu_harta.csv"));
		String line = br.readLine(); // Reading header, Ignoring
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split(",");
			String nume = fields[0];
			int unitati_inv = Integer.parseInt(fields[1]);
			int cu_pers_juridica = Integer.parseInt(fields[2]);
			int arondate = Integer.parseInt(fields[3]);
			int particulare = Integer.parseInt(fields[4]);
			int publice = Integer.parseInt(fields[5]);
			Judet judet = new Judet(nume, unitati_inv, cu_pers_juridica, arondate, particulare, publice);
			judete.add(judet);
		}
		br.close();

		return judete;*/

		//repositoryJudet.save(new Judet("ALBA", 516, 144, 372, 16, 500));
		//repositoryJudet.save(new Judet("ALBA", 516, 144, 372, 16, 500));
//		System.out.println(mongoTemplate.findAll(TestCollection.class));
	}

}
