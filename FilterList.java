public class FilterList {
    private Filter[] allFilters;
    private Object[] filterData;

    public FilterList(Filter[] allFilters, Object[] filterData) {
        this.allFilters = allFilters;
        this.filterData = filterData;
    }

    public boolean isFiltered(Set set) {
        for(int i = 0;i < allFilters.length; i++) {
            if(allFilters[i].apply(set,filterData[i])) {
                return true; 
            }
        }
        return false;
        
    }
}
