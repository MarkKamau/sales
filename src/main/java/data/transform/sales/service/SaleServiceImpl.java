package data.transform.sales.service;

import data.transform.sales.entity.Sale;
import data.transform.sales.repository.SaleRepository;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.supercsv.io.CsvMapReader;
import org.supercsv.prefs.CsvPreference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;
    //private Date salesRecordDate;

    public void createSalesData(MultipartFile excelSalesFile) throws IOException, ParseException
    {
 /*       SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date salesRecordDate=dateFormatter.parse(dateFormatter.format(new Date()));
*/
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date salesRecordDate=cal.getTime();


        List<Sale> sales=extractCSVFile(excelSalesFile, salesRecordDate);
        saleRepository.deleteSalesByDate(salesRecordDate);
        saleRepository.saveAll(sales);
    }

    public void editSalesData(MultipartFile excelSalesFile, Date salesRecordDate) throws IOException,ParseException
    {
        //this.salesRecordDate=salesRecordDate;
        List<Sale> sales=extractCSVFile(excelSalesFile,salesRecordDate);
        saleRepository.saveAll(sales);
    }

    /*private List<Sale> extractExcelFile(MultipartFile excelSalesFile,Date salesRecordDate) throws IOException, ParseException
    {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        salesRecordDate= dateFormatter.parse(dateFormatter.format(salesRecordDate));
        List<Sale> sales = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(excelSalesFile.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);

        for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
            Sale sale = new Sale();
            Row row = worksheet.getRow(i);
            //sale.setId((int) row.getCell(0).getNumericCellValue());
            sale.setFirstName((String) row.getCell(1).getStringCellValue());
            sale.setLastName((String) row.getCell(2).getStringCellValue());
            sale.setEmail((String) row.getCell(3).getStringCellValue());
            sale.setGender((String) row.getCell(4).getStringCellValue());
            sale.setIpAddress((String) row.getCell(5).getStringCellValue());
            sale.setLatitude((String) row.getCell(6).getStringCellValue());
            sale.setLongitude((String) row.getCell(7).getStringCellValue());
            sale.setCardNumber((String) row.getCell(8).getStringCellValue());
            sale.setCardType((String) row.getCell(9).getStringCellValue());
            sale.setAmount((String) row.getCell(10).getStringCellValue());
            sale.setCategory((String) row.getCell(11).getStringCellValue());
            sale.setTransactionDate(salesRecordDate);
            sales.add(sale);
        }
        return sales;
    }*/

    private List<Sale> extractCSVFile(MultipartFile excelSalesFile, Date salesRecordDate) throws IOException,ParseException
    {
/*        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        salesRecordDate= dateFormatter.parse(dateFormatter.format(salesRecordDate));*/
        List<Sale> sales = new ArrayList<>();
        Map<String, String> readMap = null;
        Map csvRow;
        CsvMapReader csvMapReader = new CsvMapReader(new InputStreamReader(excelSalesFile.getInputStream()), CsvPreference.STANDARD_PREFERENCE);
        final String[] header = csvMapReader.getHeader(true);
        while ((csvRow = csvMapReader.read(header)) != null) {
            Sale sale = new Sale();
            //sale.setId((int) row.getCell(0).getNumericCellValue());
            sale.setFirstName((String) csvRow.get(header[1]));
            sale.setLastName((String) csvRow.get(header[2]));
            sale.setEmail((String) csvRow.get(header[3]));
            sale.setGender((String) csvRow.get(header[4]));
            sale.setIpAddress((String) csvRow.get(header[5]));
            sale.setLatitude((String) csvRow.get(header[6]));
            sale.setLongitude((String) csvRow.get(header[7]));
            sale.setCardNumber((String) csvRow.get(header[8]));
            sale.setCardType((String) csvRow.get(header[9]));
            sale.setAmount((String)csvRow.get(header[10]));
            sale.setCategory((String) csvRow.get(header[11]));
            sale.setTransactionDate(salesRecordDate);
            sales.add(sale);
        }
        return sales;
    }

    public List<Sale> agentSalesByDate(String firstName, String lastName, Date fromDate, Date toDate) {
            List<Sale> sales = saleRepository.findAgentSalesByDate(firstName, lastName, fromDate, toDate);
            return sales;
    }

    public List<Sale> SalesByDate(Date fromDate, Date toDate)
    {
        List<Sale> sales=saleRepository.findSalesByDate(fromDate,toDate);
        return sales;
    }
    public List<Sale> SalesByCategory(String category, Date fromDate, Date toDate)
    {
        List<Sale> sales=saleRepository.findSalesByCategory(category,fromDate,toDate);
        return sales;
    }
}
