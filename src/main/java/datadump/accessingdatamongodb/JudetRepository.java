package datadump.accessingdatamongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JudetRepository extends MongoRepository<Judet, String> {
    public JudetRepository findJudetByNume(String nume);
}
