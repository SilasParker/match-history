package src.main.java.filters;

import java.time.LocalDate;
import java.util.Date;

import src.main.java.Set;

public class DateFilter extends Filter {
    
    @Override
    public boolean apply(Set set, LocalDate date) {
        if (set.getDate().isEqual(date)) {
            return true;
        }
        return false;
    }
}