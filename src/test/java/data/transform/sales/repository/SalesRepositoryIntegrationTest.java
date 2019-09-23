package data.transform.sales.repository;


import data.transform.sales.entity.Sale;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SalesRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SaleRepository saleRepository;

    //assertThathttps://www.baeldung.com/spring-boot-testing

    @Test
    public  void saveSales(){
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

        saleRepository.save(sale);

        //When
        List<Sale> foundSales=saleRepository.findAll();//.findAgentSalesByDate(sale.getFirstName(),
                                    //sale.getLastName(),sale.getTransactionDate(), sale.getTransactionDate());


        //Then
        Assert.assertEquals(1,foundSales.size());

    }
}
