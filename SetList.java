import java.util.ArrayList;
import java.util.Collections;

public class SetList {
    private ArrayList<Set> allSets = new ArrayList<Set>();

    private void addSet(Set newSet) {
        int i = 0;
        while(i < allSets.size()) //Okay so from here you need to iterate through the array until the set date is more recent than the comparison and insert it there
        //this is efficient because the user is more likely to add a more recent game than an old one. check month first?
        
    }

    private void removeSet(Set setToRemove) {
        allSets.remove(setToRemove);
    }

    private void sortByDate() {
        
    }

    
}
