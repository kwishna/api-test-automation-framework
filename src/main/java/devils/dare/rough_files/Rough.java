package devils.dare.rough_files;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class Rough {

    public static void unZip(String zipFilePath, String unZipFolderPath) {

        try {
            ZipFile zipFile = new ZipFile(zipFilePath);
            Enumeration<?> enu = zipFile.entries();
            while (enu.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enu.nextElement();

                String name = zipEntry.getName();
                long size = zipEntry.getSize();
                long compressedSize = zipEntry.getCompressedSize();
                System.out.printf("File name: %-20s | File size: %6d | compressed size: %6d\n", name, size, compressedSize);

                File file = new File(unZipFolderPath + File.separator + name);
                if (name.endsWith("/")) {
                    file.mkdirs();
                    continue;
                }

                File parent = file.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }

                InputStream is = zipFile.getInputStream(zipEntry);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = is.read(bytes)) >= 0) {
                    fos.write(bytes, 0, length);
                }
                is.close();
                fos.close();

            }
            zipFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readExcelFile(String excelFilePath, String strQuery) {
        try {
            Fillo fillo = new Fillo();
            Connection connection = fillo.getConnection(excelFilePath);
//            String strQuery = "Select * from Sheet1";
            Recordset recordset = connection.executeQuery(strQuery);

            while (recordset.next()) {
                System.err.println(recordset.getFieldNames());
                System.err.println(recordset.getField("FirstName"));
            }

            recordset.close();
            connection.close();
        } catch (FilloException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        String dir = System.getProperty("user.dir") + "/data";
//        final Path path = Path.of(dir);
//
//        Collection<File> listStr1 = FileUtils.listFiles(path.toFile(), new String[]{"json", "xml"}, false);
//        Collection<File> listStr2 = FileUtils.listFiles(path.toFile(), new String[]{"json", "xml", "zip"}, false);
//
//        List<File> newZipFiles = listStr2.stream().filter(_l -> !listStr1.contains(_l)).toList();
//
//        String newfileName = newZipFiles.get(0).getPath();
//        String unZipFolderName = new File(newfileName).getName().split("\\.")[0];
//        unZip(newfileName, dir + File.separator + unZipFolderName);
//        readExcelFile(dir + File.separator + unZipFolderName + File.separator + "name.xlsx", "Select * from Sheet1");
//        System.out.println(new File("F:\\ABD\\GitHub\\RestAssuredUdemyLearn\\data\\name\\name.xlsx").getName().split("\\.")[0]);

        System.out.println(UUID.randomUUID());

    }
}