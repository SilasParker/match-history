public class FilterList {
    private Filter[] allFilters;

    public FilterList(Filter[] allFilters) {
        this.allFilters = allFilters;
    }

    public boolean isFiltered(Match match) {
        //apply all filters until either it's true or all filters run out. in the latter case, add match to filteredmatchlist
    }
}
