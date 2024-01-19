package org.example.service;

import java.util.Date;
import java.util.List;
import org.example.model.Operation;
import org.example.model.Account;

public interface ReportService {
    List<Operation> getOperationsInPeriod(Account account, Date fromDate, Date toDate);

    void exportReportToCsv(List<Operation> operations, String filePath);

}
