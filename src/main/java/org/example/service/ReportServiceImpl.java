package org.example.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.model.Operation;
import org.example.model.Account;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Override
    public List<Operation> getOperationsInPeriod(Account account, Date fromDate, Date toDate) {
        return null;
    }

    @Override
    public void exportReportToCsv(List<Operation> operations, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("OperationType,Amount,Category,Date\n");

            for (Operation operation : operations) {
                writer.append(String.format("%s,%.2f,%s,%s\n",
                        operation.getOperationType(),
                        operation.getAmount(),
                        operation.getCategory().toString(),
                        operation.getDate().toString()));
            }

            logger.info("CSV файл успешно создан!");
        } catch (IOException e) {
            logger.error("Ошибка при создании CSV файла: {}", e.getMessage());
        }
    }

}
