package com.jared.financialappserver.models.dto.csvFormats;

import com.jared.financialappserver.models.dao.CategoryDAO;
import com.jared.financialappserver.models.dto.AccountDTO;
import com.jared.financialappserver.models.dto.CategoryDTO;
import com.jared.financialappserver.models.dto.TransactionDTO;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

public class ChaseCSVFormat implements CSVToTransaction{

    @CsvDate(value = "MM/dd/yyyy")
    @CsvBindByName(column = "Transaction Date")
    private LocalDate transactionDate;

    @CsvDate(value = "MM/dd/yyyy")
    @CsvBindByName(column = "Post Date")
    private LocalDate postDate;

    @CsvBindByName(column = "Description")
    private String description;

    @CsvBindByName(column = "Category")
    private String category;

    @CsvBindByName(column = "Type")
    private String type;

    @CsvBindByName(column = "Amount")
    private BigDecimal amount;

    @CsvBindByName(column = "Memo")
    private String memo;

    @Override
    public TransactionDTO createTransaction(
            AccountDTO accountDTO,
            String currency,
            Map<String, CategoryDTO> categoryMap) {
        CategoryDTO categoryToSet = CategoryDTO.getUncategorizedCategory();
        if(categoryMap.get(category) != null){
            categoryToSet =categoryMap.get(category);
        }

        return TransactionDTO.builder()
                .amount(amount)
                .associatedAccount(accountDTO)
                .notes(memo)
                .currency(currency)
                .date(Date.valueOf(transactionDate))
                .description(description)
                .category(categoryToSet)
                .build();
    }
}
