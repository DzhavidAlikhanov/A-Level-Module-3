package org.example.service;

import org.example.library.Inject;
import org.example.library.Service;
import org.example.model.Operation;
import org.example.model.Report;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Inject
    private OperationService operationService;

    @Override
    public Report generateFinancialReport(User user, LocalDate fromDate, LocalDate toDate) {
        List<Operation> operations = operationService.findOperationsByUserAndPeriod(user, fromDate, toDate);
        return new Report(user, fromDate, toDate, operations);
    }

    @Override
    public void exportReport(Report report, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("ID, Account ID, Name, Category, Amount, Created At");
            for (Operation operation : report.getOperations()) {
                writer.println(
                        operation.getId() + ", " +
                                operation.getAccount().getId() + ", " +
                                operation.getName() + ", " +
                                operation.getCategory() + ", " +
                                operation.getAmount() + ", " +
                                operation.getCreatedAt());
            }

            logger.info("Report exported successfully to: {}", filePath);
        } catch (IOException e) {
            logger.error("Error exporting report to CSV", e);

        }
    }
}
