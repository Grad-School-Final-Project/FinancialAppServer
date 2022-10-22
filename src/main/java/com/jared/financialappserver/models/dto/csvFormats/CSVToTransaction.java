package com.jared.financialappserver.models.dto.csvFormats;

import com.jared.financialappserver.models.dto.AccountDTO;
import com.jared.financialappserver.models.dto.BudgetDTO;
import com.jared.financialappserver.models.dto.CategoryDTO;
import com.jared.financialappserver.models.dto.TransactionDTO;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CSVToTransaction {

    public TransactionDTO createTransaction(AccountDTO account,
                                            String currency,
                                            Map<String, CategoryDTO> categoryMap);

    public static List<TransactionDTO> createTransactionsFromCSV(String csv,
                                                                AccountDTO account,
                                                                String currency,
                                                                Map<String, CategoryDTO> categoryMap)
    {
        CSVToTransaction csvToTransaction = new ChaseCSVFormat();
        List<CSVToTransaction> beans =
                new CsvToBeanBuilder<CSVToTransaction>(new StringReader(csv))
                        .withType(csvToTransaction.getClass()).build().parse();

        List<TransactionDTO> dtos = new ArrayList<>();
        for(CSVToTransaction parsedCsv : beans){
            TransactionDTO dto = parsedCsv.createTransaction(account, currency, categoryMap);
            dto.setUser(account.getUser());
            dtos.add(dto);
        }

        return dtos;
    }
}
