package datadump.services;

import datadump.common.StorageException;
import datadump.common.StorageFileNotFoundException;
import datadump.common.StorageProperties;
import datadump.web.MainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;
    private static final Logger logger = Logger.getLogger(MainController.class.getName());

    @Autowired
    public FileSystemStorageService(StorageProperties properties){
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try{
            Files.createDirectories(rootLocation);
            logger.log(Level.ALL, rootLocation.toString());
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
            e.printStackTrace();
            throw new StorageException("Could not initialize storage", e);
        }
    }

    public void store(MultipartFile file){
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(file.isEmpty()){
                throw new StorageException("Failed to store empty file");
            }
            if(filename.contains("..")){
                throw new StorageException("Cannot store file with relative path outside current directory" + filename);
            }
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream,this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try{
            return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation)).map(this.rootLocation::relativize);
        } catch (IOException e) {
           throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String fileName) {
        try {
            Path file = load(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + fileName);
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
            throw new StorageFileNotFoundException("Could not read file" + fileName);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public File lastFileModified(String dir) {
            File fl = new File(dir);
            System.out.println(dir);
            File choice = null;
            if (fl.listFiles().length>0) {
                File[] files = fl.listFiles(new FileFilter() {
                    public boolean accept(File file) {
                        return file.isFile();
                    }
                });
                long lastMod = Long.MIN_VALUE;

                for (File file : files) {
                    if (file.lastModified() > lastMod) {
                        choice = file;
                        lastMod = file.lastModified();
                    }
                }
            }
            return choice;
    }

    @Override
    public void deleteOldFiles(String dir) {
            File fl = new File(dir);
            if (fl.listFiles().length>0) {
                File[] files = fl.listFiles(new FileFilter() {
                    public boolean accept(File file) {
                        return file.isFile();
                    }
                });

                for (File file : files) {
                    file.delete();
                }
            }

    }

    @Override
    public String getCurrentDir() {
        Path currentPath = Paths.get("");
        String projectPath = currentPath.toAbsolutePath().toString();
        String dir = "/tmp";
        //String dir = projectPath;

        return dir;
    }
}
