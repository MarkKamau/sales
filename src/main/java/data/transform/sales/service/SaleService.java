package data.transform.sales.service;

import data.transform.sales.entity.Sale;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface SaleService
{
    public void createSalesData(MultipartFile excelSalesFile) throws IOException, ParseException;
    public void editSalesData(MultipartFile excelSalesFile, Date salesRecordDate) throws IOException,ParseException;
    public List<Sale> agentSalesByDate(String firstName, String lastName, Date fromDate, Date toDate) throws ParseException;
    public List<Sale> SalesByDate(Date fromDate, Date toDate) throws ParseException;
    public List<Sale> SalesByCategory(String category, Date fromDate, Date toDate) throws ParseException;
}
