import java.util.Date;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;

class Main {

    private static SetList arr = new SetList();

    public static void main(String[] args) throws ParseException {
        Character[] list1 = new Character[1];
        Character[] list2 = new Character[1];
        Character character = new Character("jeff", Paths.get("character"));
        Map map = new Map("fountain");
        list1[0] = character;
        list2[0] = character;

        Match match1 = new Match(list1, list2, map, true);
        Match match2 = new Match(list2, list1, map, false);
        Match[] matches = new Match[2];
        matches[0] = match1;
        matches[1] = match2;
        boolean[] winOrder = new boolean[2];
        winOrder[0] = true;
        winOrder[1] = false;
        Date date = new Date();
        Set set1 = new Set(matches, winOrder, "Boohbah", "10QuidShoes", "Long Live London", date);
        arr.addSet(set1);
        Character character2 = new Character("mememan", Paths.get("character"));
        Character[] list3 = new Character[1];
        Character[] list4 = new Character[1];
        list3[0] = character2;
        list4[0] = character;
        Match match3 = new Match(list3, list4, map, false);
        Match match4 = new Match(list2, list1, map, true);
        Match[] matches2 = new Match[2];
        matches2[0] = match3;
        matches2[1] = match4;
        boolean[] winOrder2 = new boolean[2];
        winOrder2[0] = false;
        winOrder2[1] = true;
        Date date2 = new Date();
        Set set2 = new Set(matches2, winOrder2, "Under_Score", "schmooblidon", "Brighton Stock", date2);
        arr.addSet(set2);
        System.out.println("yo");
        // arr.toJSON();
        Map[] maps = new Map[1];
        maps[0] = map;
        Path path = FileSystems.getDefault().getPath("games");
        Game melee = new Game("mk4", 1, false, maps, path, new SetList(), list3);
        melee.getSetList().addSet(set1);
        melee.getSetList().addSet(set2);
        melee.toJSON();
        System.out.println("--------");
        // melee.setListJsonToFile();

        try {
            String mk3SetList = new String(Files.readAllBytes(Paths.get("setLists", "mk3.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        melee.importSetList("setLists/mk3.json", true);
        melee.setListJsonToFile();

        // GameList allgames = new GameList();
        // allgames.outputAllGames();

    }

}
