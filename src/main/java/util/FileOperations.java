package util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileOperations {
    public static String readFile(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream in = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(System.lineSeparator());
        }

        return sb.toString();
    }

    public static File convertToFile(MultipartFile multipartFile) throws IOException {

        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

    public static String parseFile(File file) {
        String str = null;
        try {
            str = FileOperations.readFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
