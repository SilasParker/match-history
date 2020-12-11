public class FilterList {
    private Filter[] allFilters;
    private Object[] filterData;

    public FilterList(Filter[] allFilters, Object[] filterData) {
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
            } else if (!allFilters[i].apply(set, filterData[i])) {
                return false;
            }
        }
        return true;
    }

}
