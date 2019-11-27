package datadump.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.IOException;

@ConfigurationProperties("services")
public class StorageProperties {
    private String location = JavaCreateDirectory.createDirectory("upload-dir");

    public StorageProperties() throws IOException {
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
