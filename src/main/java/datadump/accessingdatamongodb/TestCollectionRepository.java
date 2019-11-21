package datadump.accessingdatamongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestCollectionRepository extends MongoRepository<TestCollection, String> {

    public TestCollection findTestCollectionByData(String data);

}
