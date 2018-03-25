package service.impl;

import model.UserReport;
import repository.ReportRepository;
import repository.SaleRepository;
import repository.impl.ReportRepositoryImpl;
import repository.impl.SaleRepositoryImpl;
import service.ReportService;

import java.util.List;

public class UserReportServiceImpl implements ReportService<UserReport> {
    private ReportRepository reportRepository;

    public UserReportServiceImpl() {
        reportRepository = new ReportRepositoryImpl();
    }

    public UserReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<UserReport> getReport() {
        return this.reportRepository.getUsersReport();
    }
}
