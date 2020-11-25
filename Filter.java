abstract class Filter {
    public boolean apply(Set set, Object object) {return false;}
    public boolean apply(Set set, int[] integer) {return false;}
}
//Create filter interface and all types of filters

class PlayerCharacterFilter extends Filter {

    @Override
    public boolean apply(Set set, int[] character) {
        Match[] matches = set.getMatches();
        for(int i = 0;i < matches.length;i++) {
            for(int j = 0;j < matches[i].getPlayerCharacters().length;j++) {
                if(matches[i].getPlayerCharacters()[j] == character[j]) {
                    if(matches[i]) //Make it so that it checks for an exact match of 1-3 characters rather than just matching 1
                }
            }
        }
        return false;
    }
}

class OpponentCharacterFilter extends Filter {
    @Override
    public boolean apply(Set, int character) {

    }
}