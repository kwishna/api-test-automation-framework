package devils.dare.commons.data_provider;

//
//import org.testng.annotations.DataProvider;
//
public class TestNGDataProvider {
//    @DataProvider(name = "AllData")
//    public String[][] AllDataProvider() {
//        String fName = System.getProperty("User.dir") + "//TestData//TestData.xlsx";
//
//        XLSXReader xlsxReader = new XLSXReader(fName);
//
//        int ttlRowCnt = xlsxReader.getRowCount("Sheet1");
//        int ttlColCnt = xlsxReader.getColumnCount("Sheet1");
//
//        String[][] userData = new String[ttlRowCnt - 1][ttlColCnt];
//
//        for (int rowNo = 1; rowNo < ttlRowCnt; rowNo++) {
//            for (int colNo = 0; colNo < ttlColCnt; colNo++) {
//                userData[rowNo - 1][colNo] = xlsxReader.getCellData("Sheet1", colNo, rowNo);
//            }
//
//        }
//        return userData;
//    }
}
