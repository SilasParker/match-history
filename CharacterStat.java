public class CharacterStat {
    private Character character;
    private Game gameRef; //reference to the game at hand
    private int[] mapWins, mapLosses, charWins, charLosses;
    private int gameWins, gameLosses, setWins, setLosses;

    public CharacterStat(Character character, Game game) {
        this.character = character;
        this.gameRef = game;
        this.mapWins = new int[gameRef.getMaps().length];
        this.gameWins = 0;
        this.gameLosses = 0;
        this.setWins = 0;
        this.setLosses = 0;
        fillIntArrayWithZero(mapWins);
        this.mapLosses = 
        this.charWins = this.mapWins;
        this.charLosses = this.mapWins;
    }

    private void fillIntArrayWithZero(int[] array) {
        for(int i = 0;i < array.length;i++) {
            array[i] = 0;
        }
    }

}
