package data.transform.sales.controller;


import data.transform.sales.entity.Sale;
import data.transform.sales.error.SalesException;
import data.transform.sales.service.SaleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ControllerAdvice
@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    SaleServiceImpl saleService;

    @CrossOrigin(origins = "*")
    @PostMapping("/import")
    public ResponseEntity<Object> ImportSales(@RequestParam("file") MultipartFile excelSalesFile) throws IOException, ParseException{
        saleService.createSalesData(excelSalesFile);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/import")
    public ResponseEntity<Object> ImportSales(@RequestParam("file") MultipartFile excelSalesFile, @RequestParam("editdate") String editDate) throws IOException,ParseException {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date eDate= dateFormatter.parse(editDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(eDate);
        cal.set(Calendar.HOUR_OF_DAY,00);
        cal.set(Calendar.MINUTE,00);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        eDate= cal.getTime();

        saleService.editSalesData(excelSalesFile,eDate);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/agentsales")
    public ResponseEntity<List<Sale>> agentSales(@RequestParam("firstname") String firstName,
                                                @RequestParam("lastname") String lastName,
                                                @RequestParam("fromdate") String fromDate,
                                                @RequestParam("todate") String toDate)throws ParseException,SalesException {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date fDate= dateFormatter.parse(fromDate);
        Date tDate= dateFormatter.parse(toDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(tDate);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        tDate= cal.getTime();

        if (!tDate.after(fDate)){
            throw new SalesException("To date cannot be greater than from date");
        }

        List<Sale> sales=saleService.agentSalesByDate(firstName,lastName,fDate,tDate);

        return new ResponseEntity<List<Sale>>(sales, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/daysales")
    public ResponseEntity<List<Sale>> daysales(@RequestParam("fromdate") String fromDate,
                                               @RequestParam("todate") String toDate)throws ParseException,SalesException {


        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date fDate= dateFormatter.parse(fromDate);
        Date tDate=dateFormatter.parse(toDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(tDate);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        tDate= cal.getTime();

        if (!tDate.after(fDate)){
            throw new SalesException("To date cannot be greater than from date");
        }
        List<Sale> sales= saleService.SalesByDate(fDate,tDate);
        if (sales.isEmpty()){
            return new ResponseEntity<List<Sale>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Sale>>(sales, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/category")
    public ResponseEntity<List<Sale>> daysales(@RequestParam("category") String category,
                                            @RequestParam("fromdate") String fromDate,
                                             @RequestParam("todate") String toDate)throws ParseException,SalesException {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date fDate= dateFormatter.parse(fromDate);
        Date tDate=dateFormatter.parse(toDate);
        if (tDate.after(fDate)){
            throw new SalesException("To date cannot be greater than from date");
        }
        List<Sale> sales= saleService.SalesByCategory(category,fDate,tDate);
        if (sales.isEmpty()){
            return new ResponseEntity<List<Sale>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Sale>>(sales, HttpStatus.OK);
    }

}
