import java.util.Date;

public class FilterList {
    private Filter[] allFilters;
    private Object[] filterData;

    public FilterList(Filter[] allFilters, Object[] filterData) {
        System.out.println("FilterList initialised");
        this.allFilters = allFilters;
        this.filterData = filterData;
    }

    public FilterList(Filter[] allFilters,String[] filterData) {
        this.allFilters = allFilters;
        this.filterData = filterData;
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
                if(!allFilters[i].apply(set,strFilterData)) {
                    return false;
                }
            } else if(filterData[i] instanceof Date) {
                Date dateFilterData = ((Date) filterData[i]);
                if(!allFilters[i].apply(set,dateFilterData)) {
                    return false;
                }
            }
            
        }
        return true;
    }


}
