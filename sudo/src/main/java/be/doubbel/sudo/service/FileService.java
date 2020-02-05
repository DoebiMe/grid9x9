package be.doubbel.sudo.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileService {
    public List<String> getResourceFolderFiles () throws IOException {
        List<String> resultList = new ArrayList<>();

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] resources = resolver.getResources("META-INF/resources/sudos/*.txt");

        for (Resource file : resources) {
            resultList.add(file.getFilename());
            System.out.println(file.getFilename());
        }
        return resultList;
    }


    public Integer[][] readSudo(String sudoName) {
        try {
            getResourceFolderFiles();
            System.out.println("Loading " + sudoName);
            InputStream inputStream =
                    this.getClass()
                            .getClassLoader()
                            .getResourceAsStream("META-INF/resources/sudos/" + sudoName);
            Scanner scanner = new Scanner(inputStream);
            StringBuffer stringBuffer = new StringBuffer();
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (Exception ex) {

        }
        return null;
    }



}
