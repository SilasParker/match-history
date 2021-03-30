package src.main.java.filters;

import java.time.LocalDate;
import java.util.Date;
import src.main.java.*;

public abstract class Filter {
    public boolean apply(Set set, String string) {
        return false;
    }

    public boolean apply(Set set, int integer) {
        return false;
    }

    public boolean apply(Set set, LocalDate date) {
        return false;
    }

    public boolean apply(Set set, Object object) {
        return false;
    }
}