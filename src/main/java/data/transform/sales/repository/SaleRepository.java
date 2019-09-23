package data.transform.sales.repository;


import data.transform.sales.entity.Sale;
import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public  interface SaleRepository  extends  JpaRepository<Sale, Integer>{

    @Query("select t from Sale t where t.firstName=:firstname and t.lastName=:lastname and  t.transactionDate BETWEEN :fromdate and :todate")
    List<Sale> findAgentSalesByDate(@Param("firstname") String firstName, @Param("lastname") String lastName,@Param("fromdate") Date fromDate,@Param("todate") Date todate);

/*
    @Query("select t from Sale t where t.firstName=:firstname and t.lastName=:lastname and  t.transactionDate>= :fromdate and t.transactionDate<=:todate")
    List<Sale> findAgentSalesByDate(@Param("firstname") String firstName, @Param("lastname") String lastName,@Param("fromdate") Date fromDate,@Param("todate") Date todate);
*/


    @Query("select t from Sale t where t.transactionDate BETWEEN :fromdate and :todate")
    List<Sale> findSalesByDate(@Param("fromdate") Date fromDate, @Param("todate") Date todate);

    @Query("select t from Sale t where t.category=:category and t.transactionDate BETWEEN :fromdate and :todate")
    List<Sale> findSalesByCategory(@Param("category") String category, @Param("fromdate") Date fromDate, @Param("todate") Date todate);

    @Transactional
    @Modifying
    @Query("Delete from Sale t where t.transactionDate=:editdate")
    void deleteSalesByDate(@Param("editdate") Date editDate);
}


