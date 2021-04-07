package src.main.java;

import java.time.LocalDate;
import java.util.Date;

import src.main.java.filters.Filter;

public class FilterList {
    private Filter[] allFilters;
    private Object[] filterData;

    public FilterList(Filter[] allFilters, Object[] filterData) {
        this.allFilters = allFilters;
        this.filterData = filterData;
    }

    public FilterList(Filter[] allFilters, String[] filterData) {
        this.allFilters = allFilters;
        this.filterData = filterData;
    }

    public void clearFilter() {
        this.allFilters = new Filter[]{};
        this.filterData = new Object[]{};
    }



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

}
