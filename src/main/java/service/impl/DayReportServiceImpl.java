package service.impl;

import model.DayReport;
import repository.ReportRepository;
import repository.impl.ReportRepositoryImpl;
import service.ReportService;

import java.util.List;

public class DayReportServiceImpl implements ReportService<DayReport> {
    private static final Integer DEFAULT_BEFORE_DAYS = 3;
    private ReportRepository reportRepository;

    public DayReportServiceImpl() {
        this.reportRepository = new ReportRepositoryImpl();
    }

    public DayReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<DayReport> getReport() {
        return this.reportRepository.getDaysReport(DEFAULT_BEFORE_DAYS);
    }
}
