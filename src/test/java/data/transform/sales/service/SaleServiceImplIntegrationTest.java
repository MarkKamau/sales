package data.transform.sales.service;

import data.transform.sales.entity.Sale;
import data.transform.sales.repository.SaleRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
public class SaleServiceImplIntegrationTest {

        @TestConfiguration
        static class SaleServiceImplTestContextConfiguration {
            @Bean
            public SaleService saleService() {
                return new SaleServiceImpl();
            }
        }

        @Autowired
        private SaleService saleService;

        @MockBean
        private SaleRepository saleRepository;

        private List<Sale> sales;


        @Before
        public void setup(){
            //Given
            Sale sale=new Sale();
            sale.setFirstName("Mark");
            sale.setLastName("Kamau");
            sale.setEmail("Mark.Kamau@gmail.com");
            sale.setGender("Male");
            sale.setIpAddress("191.156.46.171");
            sale.setLatitude("43.88719");
            sale.setLongitude("81.621986");
            sale.setCardNumber("5100130363338560");
            sale.setCardType("mastercard");
            sale.setAmount("4000");
            sale.setCategory("Games");
            sale.setTransactionDate(new Date());

            sales=new ArrayList<>();
            sales.add(sale);
        }

        @Test()
        public void agentSalesByDate() throws ParseException
        {
           //Given
            String firstName ="Mark";
            String lastName= "Kamau";

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            Date fromDate=  dateFormatter.parse(dateFormatter.format(new Date()));
            Date toDate=  dateFormatter.parse(dateFormatter.format(new Date()));


            Mockito.when(saleRepository.findAgentSalesByDate(firstName,
                    lastName,fromDate, toDate)).
                    thenReturn(sales);

            //When
            List<Sale> foundSales=saleService.agentSalesByDate(firstName,lastName,fromDate,toDate);


            //Then
            Assert.assertEquals(1,foundSales.size());
        }

       @Test()
        public void salesByDate() throws ParseException
        {
            //Given
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            Date fromDate=  dateFormatter.parse(dateFormatter.format(new Date()));
            Date toDate=  dateFormatter.parse(dateFormatter.format(new Date()));

            Calendar cal = Calendar.getInstance();
            cal.setTime(toDate);
            cal.set(Calendar.HOUR_OF_DAY,23);
            cal.set(Calendar.MINUTE,59);
            cal.set(Calendar.SECOND,0);
            cal.set(Calendar.MILLISECOND,0);
            toDate= cal.getTime();

            Mockito.when(saleRepository.findSalesByDate(fromDate, toDate)).
                    thenReturn(sales);

            //When
            List<Sale> foundSales=saleService.SalesByDate(fromDate,toDate);

            //Then
            Assert.assertEquals(1,foundSales.size());
        }

         @Test
        public void SalesByCategory() throws ParseException
        {
            //Given
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            Date fromDate=  dateFormatter.parse(dateFormatter.format(new Date()));
            Date toDate=  dateFormatter.parse(dateFormatter.format(new Date()));

            Calendar cal = Calendar.getInstance();
            cal.setTime(toDate);
            cal.set(Calendar.HOUR_OF_DAY,23);
            cal.set(Calendar.MINUTE,59);
            cal.set(Calendar.SECOND,0);
            cal.set(Calendar.MILLISECOND,0);
            toDate= cal.getTime();

            String category="Games";

            Mockito.when(saleRepository.findSalesByCategory(category,fromDate, toDate)).
                    thenReturn(sales);

            //When
            List<Sale> foundSales=saleService.SalesByCategory(category,fromDate, toDate);

            //Then
            Assert.assertEquals(1,foundSales.size());
        }

    /*@Test
    public void createSalesData() throws IOException,ParseException
    {
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sales-data.csv");
        final MockMultipartFile salesFile = new MockMultipartFile("file",inputStream);//("test.png", "test.png", "image/png", inputStream);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date fromDate=  dateFormatter.parse(dateFormatter.format(new Date()));
        Date toDate=  dateFormatter.parse(dateFormatter.format(new Date()));

        Calendar cal = Calendar.getInstance();
        cal.setTime(toDate);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        toDate= cal.getTime();

        Mockito.when(saleRepository.findAll()).thenReturn(sales);
        Mockito.when(saleRepository.findSalesByDate(fromDate, toDate)).thenReturn(sales);

        //When
        this.sales=saleService.createSalesData(salesFile);

        List<Sale> foundSales=saleService.SalesByDate(fromDate,toDate);

        //Then
        Assert.assertEquals(1000,foundSales.size());




            *//*
            MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "sales-data.csv", "multipart/form-data", );
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
            Assert.assertEquals(200, result.getResponse().getStatus());
            Assert.assertNotNull(result.getResponse().getContentAsString());
            Assert.assertEquals("excel.xlsx", result.getResponse().getContentAsString());*//*
    }*/

    }

