package util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    public static File modifyFile(String content) throws IOException {

        String path = "D:\\WORK\\Hackathon2021\\decryptionApplication\\src\\main\\resources\\file.txt";
        File file = new File(path);

        try{
            FileOutputStream fos=new FileOutputStream(file.getName(), true);
            byte[] bytes = content.getBytes();
            fos.write(bytes);
            fos.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }


        return file;
    }

    public static void convertToZip(String licenseFilePath, String licenseKeyPath) {

        String zipFile = "D:\\WORK\\Hackathon2021\\decryptionApplication\\src\\main\\resources\\archive.zip";

        String[] srcFiles = { licenseFilePath, licenseKeyPath};

        try {

            // create byte buffer
            byte[] buffer = new byte[1024];

            FileOutputStream fos = new FileOutputStream(zipFile);

            ZipOutputStream zos = new ZipOutputStream(fos);

            for (int i=0; i < srcFiles.length; i++) {

                File srcFile = new File(srcFiles[i]);

                FileInputStream fis = new FileInputStream(srcFile);

                // begin writing a new ZIP entry, positions the stream to the start of the entry data
                zos.putNextEntry(new ZipEntry(srcFile.getName()));

                int length;

                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry();

                // close the InputStream
                fis.close();

            }

            // close the ZipOutputStream
            zos.close();

        }
        catch (IOException ioe) {
            System.out.println("Error creating zip file: " + ioe);
        }

    }

}
