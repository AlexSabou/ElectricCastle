package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DayReport {
    private LocalDateTime date;
    private Long total;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ": \t" + total;
    }
}
