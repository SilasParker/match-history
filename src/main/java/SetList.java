package src.main.java;

import java.util.ArrayList;
import java.util.Calendar;
import com.google.gson.JsonArray;

//List that contains and manages all the Sets (essentially the Match History as an object)
public class SetList {

    private ArrayList<Set> allSets = new ArrayList<Set>();

    // Converts all this Character's information into a String
    // Returns: A String containing all this GameList's information neatly formatted
    public String toString() {
        String toPrint = "SetList: ";
        for (Set set : allSets) {
            toPrint += set.toString() + "\n";
        }
        return toPrint;
    }

    // Getter for all the Sets within this SetList
    // Returns: All the Sets in an ArrayList
    public ArrayList<Set> getAllSets() {
        return this.allSets;
    }

    // Adds a Set to this SetList and sorts it by Date
    // newSet: Set to add to the SetList
    public void addSet(Set newSet) {
        if (allSets.size() == 0) {
            allSets.add(0, newSet);
        } else if (newSet.getDate().isAfter(allSets.get(0).getDate())
                || newSet.getDate().equals(allSets.get(0).getDate())) {
            allSets.add(0, newSet);
        } else {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(newSet.getLocalDateAsDate());
            cal2.setTime(allSets.get(0).getLocalDateAsDate());
            if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                    && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
                boolean added = false;
                for (int i = 0; i <= allSets.size() - 1; i++) {
                    if (newSet.getDate().isAfter(allSets.get(i).getDate()) && !added) {
                        allSets.add(i, newSet);
                        added = true;
                    }
                }
                if (!added) {
                    allSets.add(allSets.size(), newSet);
                }
            } else {
                insertionSort(newSet, 0, allSets.size() - 1);
            }
        }
        // Sort explanation:
        // check if date is before the first position
        // if yes - insert it into first spot
        // if no - carry on
        // check if month is the same as first position
        // if yes - iterate through it
        // if not - quicksort it
    }

    // Sorts the Set by Date within this SetList by Date (using an adapted Insertion
    // Sort algorithm)
    // newSet: Set being sorted into the SetList
    // low: The lowest index that the Set could be inserted into
    // high: The highest index that the Set could be inserted into
    private void insertionSort(Set newSet, int low, int high) {
        int mid = (low + high) / 2;
        if (high <= low && allSets.size() > 1) {
            int insertionpoint = (newSet.getDate().isAfter(allSets.get(low).getDate())) ? low : (low + 1);
            allSets.add(insertionpoint, newSet);
        } else if (mid == allSets.size() - 1 && allSets.get(mid).getDate().isAfter(newSet.getDate())) {
            // If the current pointer is at the end of the array, insert it at the end
            allSets.add(allSets.size(), newSet);
        } else if (newSet.getDate().isAfter(allSets.get(mid).getDate())) { // If the newSet is in the left half
            insertionSort(newSet, low, mid - 1);
        } else if (newSet.getDate().isBefore(allSets.get(mid).getDate())) { // If the newSet is in the right half
            insertionSort(newSet, mid + 1, high);
        } else { // covers if the date is the same
            allSets.add(mid, newSet);
        }
    }

    // Applies the Filters to the current SetList
    // allFilters: FilterList containing all the filters to apply to this SetList
    // Returns: the filtered SetList
    public SetList applyFilters(FilterList allFilters) {
        SetList tempSetList = new SetList();
        for (int i = 0; i < allSets.size(); i++) {
            if (allFilters.isFiltered(allSets.get(i))) {
                tempSetList.addSet(allSets.get(i));
            }
        }
        return tempSetList;
    }

    // Converts this SetList into a JsonArray
    // Returns: this SetList as a JsonArray
    public JsonArray toJsonArray() {
        JsonArray jsonArray = new JsonArray();
        this.allSets.forEach((set) -> {
            jsonArray.add(set.toJsonObject());
        });
        return jsonArray;
    }

    // Ensures the SetList is sorted by Date to return
    // Returns: the SetList sorted by Date
    public SetList getSetListByDate() {
        SetList toReturn = new SetList();
        for (Set set : this.allSets) {
            toReturn.addSet(set);
        }
        return toReturn;
    }

    // Removes a Set from the SetList
    // setToRemove: the Set to remove
    public void removeSet(Set setToRemove) {
        allSets.remove(setToRemove);
    }

    // Retrieves the Set by index
    // index: the index to get the Set from the SetList from
    public Set getSet(int index) {
        return this.allSets.get(index);
    }

    // Retrieves this SetList's size
    // returns: the length of this SetList
    public int getLength() {
        return this.allSets.size();
    }

}
