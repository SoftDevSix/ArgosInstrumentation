package edu.usb.argosinstrumentation;

import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {

    private Utils() {}

    public static String getPackageName(String className) {
        String[] strings = className.split("/");
        if (strings.length == 1) {
            return strings[0];
        }

        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < strings.length - 1; index++) {
            builder.append(strings[index]);
            builder.append("/");
        }
        return builder.toString();
    }

    public static String getSourceFileName(String className) {
        String[] strings = className.split("/");
        if (strings.length == 1) {
            return strings[0];
        }

        return String.format("%s.java", strings[strings.length - 1]);
    }

    public static void writeToPath(String pathName, byte[] file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(pathName)) {
            outputStream.write(file);
        }
    }
}
