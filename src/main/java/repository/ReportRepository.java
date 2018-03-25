package repository;

import model.DayReport;
import model.UserReport;

import java.util.List;

public interface ReportRepository {
    public List<UserReport> getUsersReport();
    public List<DayReport> getDaysReport(Integer beforeDays);
}
