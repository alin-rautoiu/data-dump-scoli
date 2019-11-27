package datadump.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaCreateDirectory {
    public static String createDirectory(String dirName) throws IOException {

        String fileName = "/" + dirName;

        Path path = Paths.get(fileName);

        if (!Files.exists(path)) {
            Files.createDirectory(path);
            System.out.println("Directory created!");

        } else {
            System.out.println("Directory already exists");
        }
        System.out.println(fileName);
        return fileName;
    }
}
