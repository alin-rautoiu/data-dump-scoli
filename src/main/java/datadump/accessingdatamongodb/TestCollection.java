package datadump.accessingdatamongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "testCollection")
public class TestCollection {
    @Id
    public String id;

    private String data;
    private String data2;

    public TestCollection(){}

    public TestCollection(String data){
        this.setData(data);
        data2 = data;
    }

    @Override
    public String toString() {
        return String.format("TestCollection[id=%s, data='%s']", id, getData());
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
