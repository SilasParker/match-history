package src.main.java;

import java.time.LocalDate;

import src.main.java.filters.Filter;

//List that holds all the Filters that are being applied to a Match History, along with the filter data
public class FilterList {

    private Filter[] allFilters;
    private Object[] filterData;

    // Constructor to initialise the Filter List
    // allFilters: array of filters
    // filterData: array of filter data that is consecutive with the allFilters
    // array
    public FilterList(Filter[] allFilters, Object[] filterData) {
        this.allFilters = allFilters;
        this.filterData = filterData;
    }

    public FilterList(Filter[] allFilters, String[] filterData) {
        this.allFilters = allFilters;
        this.filterData = filterData;
    }

    // Applies all present filters against a set to see if it is filtered or not
    // set: The set being checked against
    // Returns: Whether the Set is filtered or not
    public boolean isFiltered(Set set) {
        for (int i = 0; i < allFilters.length; i++) {
            if (filterData[i] instanceof Integer) {
                int intFilterData = ((Integer) filterData[i]).intValue();
                if (!allFilters[i].apply(set, intFilterData)) {
                    return false;
                }
            } else if (filterData[i] instanceof String) {
                String strFilterData = ((String) filterData[i]);
                if (!allFilters[i].apply(set, strFilterData)) {
                    return false;
                }
            } else if (filterData[i] instanceof LocalDate) {
                LocalDate dateFilterData = ((LocalDate) filterData[i]);
                if (!allFilters[i].apply(set, dateFilterData)) {
                    return false;
                }
            } else if (filterData[i] instanceof Character) {
                Character charFilterData = ((Character) filterData[i]);
                if (!allFilters[i].apply(set, charFilterData)) {
                    return false;
                }
            } else if (filterData[i] instanceof Map) {
                Map mapFilterData = ((Map) filterData[i]);
                if (!allFilters[i].apply(set, mapFilterData)) {
                    return false;
                }
            }

        }
        return true;
    }

    // Clears this FilterList of all Filters and data
    public void clearFilter() {
        this.allFilters = new Filter[] {};
        this.filterData = new Object[] {};
    }

}
