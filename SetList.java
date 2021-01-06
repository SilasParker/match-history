import java.util.ArrayList;
import java.util.Calendar;
import com.google.gson.JsonArray;

public class SetList {
    private ArrayList<Set> allSets = new ArrayList<Set>();

    public String toString() {
        String toPrint = "SetList: ";
        for (Set set : allSets) {
            System.out.println(allSets.size());
            toPrint += set.toString() + "\n";
        }
        return toPrint;
    }

    public int getLength() {
        return this.allSets.size();
    }

    public Set getSet(int index) {
        return this.allSets.get(index);
    }

    public void addSet(Set newSet) {
        if (allSets.size() == 0) {
            allSets.add(0, newSet);
        } else if (newSet.getDate().after(allSets.get(0).getDate()) || newSet.getDate().equals(allSets.get(0).getDate())) {
            allSets.add(0, newSet);
        } else {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(newSet.getDate());
            cal2.setTime(allSets.get(0).getDate());
            if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                    && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
                for (int i = 0; i <= allSets.size() - 1; i++) {
                    System.out.println(newSet.getDate().after(allSets.get(i).getDate()));
                    System.out.println(newSet.getDate().before(allSets.get(i).getDate()));
                    System.out.println(newSet.getDate().equals(allSets.get(i).getDate()));
                    if (newSet.getDate().after(allSets.get(i).getDate())) {
                        allSets.add(i, newSet);
                    }
                }
            } else {
                insertionSort(newSet, 0, allSets.size() - 1);
            }
        }
        // check if date is before the first position
        // if yes - insert it into first spot
        // if no - carry on
        // check is month is the same as first position
        // if yes - iterate through it
        // if not - quicksort it

    }

    public void removeSet(Set setToRemove) {
        allSets.remove(setToRemove);
    }

    private void insertionSort(Set newSet, int low, int high) {
        int mid = (low + high) / 2;
        if (high <= low && allSets.size() > 1) {
            int insertionpoint = (newSet.getDate().after(allSets.get(low).getDate())) ? low : (low + 1);
            allSets.add(insertionpoint, newSet);
        } else if (mid == allSets.size() - 1 && allSets.get(mid).getDate().after(newSet.getDate())) {
            // If the current pointer is at the end of the array, insert it at the end
            allSets.add(allSets.size(), newSet);
        } else if (newSet.getDate().after(allSets.get(mid).getDate())) { // If the newSet is in the left half
            insertionSort(newSet, low, mid - 1);
        } else if (newSet.getDate().before(allSets.get(mid).getDate())) { // If the newSet is in the right half
            insertionSort(newSet, mid + 1, high);
        } else { // covers if the date is the same
            allSets.add(mid, newSet);
        }
    }

    public SetList applyFilters(FilterList allFilters) {
        SetList tempSetList = new SetList();
        for (int i = 0; i < allSets.size(); i++) {
            System.out.println("Checking set "+(i+1));
            if (allFilters.isFiltered(allSets.get(i))) {
                System.out.println("Adding set");
                tempSetList.addSet(allSets.get(i));
            }
        }
        return tempSetList;
    }

    public JsonArray toJsonArray() {
        JsonArray jsonArray = new JsonArray();
        this.allSets.forEach((set) -> {
            jsonArray.add(set.toJsonObject());
        });
        return jsonArray;
    }

    public ArrayList<Set> getAllSets() {
        return this.allSets;
    }

}
