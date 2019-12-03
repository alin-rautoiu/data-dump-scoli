package datadump.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaCreateDirectory {
    public static String createDirectory(String dirName) throws IOException {

        String fileName = "/tmp/" + dirName;

        Path path = Paths.get(fileName);

        if (!Files.exists(path)) {
            try{
                Files.createDirectory(path);
            } catch (Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        System.out.println(fileName);

        return fileName;
    }
}
