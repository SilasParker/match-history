package src.main.java.filters;

import java.time.LocalDate;

import src.main.java.Set;

//Inhereted Filter class to filter by Date
public class DateFilter extends Filter {

    // Determines whether a set should by filtered or not
    // set: The Set to potentially filter
    // date: The date that is compared to the Set's date
    // Returns: Whether the Set should be filtered or not
    @Override
    public boolean apply(Set set, LocalDate date) {
        if (set.getDate().isEqual(date)) {
            return true;
        }
        return false;
    }
}