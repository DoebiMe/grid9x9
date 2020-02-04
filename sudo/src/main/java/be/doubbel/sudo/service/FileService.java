package be.doubbel.sudo.service;

import javax.management.Notification;
import java.io.InputStream;
import java.util.Scanner;

public class FileService {
    public Integer[][] readSudo(String sudoName) {
        try {
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
