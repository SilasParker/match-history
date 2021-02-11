package src.main.java.filters;

import java.util.Date;

import src.main.java.Set;

public class DateFilter extends Filter {
    @Override
    public boolean apply(Set set, Date date) {
        if (set.getDate() == date) {
            return true;
        }
        return false;
    }
}