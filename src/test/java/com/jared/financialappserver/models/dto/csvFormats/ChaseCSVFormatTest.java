package com.jared.financialappserver.models.dto.csvFormats;

import com.jared.financialappserver.apis.handlers.AccountHandler;
import com.jared.financialappserver.models.dto.TransactionDTO;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChaseCSVFormatTest {

    @Test
    void createTransaction() throws FileNotFoundException {
        URL path = this.getClass().getResource("/testCsvFiles/chaseExample1.csv");
        List<ChaseCSVFormat> beans =
                new CsvToBeanBuilder<ChaseCSVFormat>(new FileReader(path.getPath()) )
                .withType(ChaseCSVFormat.class).build().parse();

        List<TransactionDTO> dtos = new ArrayList<>();
        for(ChaseCSVFormat parsedCsv : beans){
            TransactionDTO dto = parsedCsv.createTransaction(null, "USD", new HashMap<>());
            dtos.add(dto);
        }

        assertEquals(25, dtos.size(), "Not all rows of CSV were parsed.");
        assertEquals(new BigDecimal("-800.73") , AccountHandler.getAccountBalance(dtos), "Transaction total does not match");
    }
}