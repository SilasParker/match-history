import java.util.Date;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Main {

    private static SetList arr = new SetList();

    public static void main(String[] args) throws ParseException { 
        Character[] list1 = new Character[1];
        Character[] list2 = new Character[1];
        Character character = new Character("jeff",Paths.get("character"));
        Map map = new Map("fountain");
        list1[0] = character;
        list2[0] = character;
        
        Match match1 = new Match(list1,list2,map,true);
        Match match2 = new Match(list2,list1,map,false);
        Match[] matches = new Match[2];
        matches[0] = match1;
        matches[1] = match2;
        boolean[] winOrder = new boolean[2];
        winOrder[0] = true;
        winOrder[1] = false;
        Date date = new Date();
        Set set1 = new Set(matches,winOrder,"Boohbah","10QuidShoes","Long Live London",date);
        arr.addSet(set1);
        Character character2 = new Character("mememan",Paths.get("character"));
        int[] list3 = new int[1]; //CARRY ON HERE FOR TESTING
        int[] list4 = new int[1];
        list3[0] = 3;
        list4[0] = 4;
        Match match3 = new Match(list3,list4,5,false);
        Match match4 = new Match(list2,list1,6,true);
        Match[] matches2 = new Match[2];
        matches2[0] = match3;
        matches2[1] = match4;
        boolean[] winOrder2 = new boolean[2];
        winOrder2[0] = false;
        winOrder2[1] = true;
        Date date2 = new Date();
        Set set2 = new Set(matches2,winOrder2,"Under_Score","schmooblidon","Brighton Stock",date2);
        arr.addSet(set2);
        System.out.println("yo");
        //arr.toJSON();

        Game melee = new Game("lmzo****++++dddKKK¬!£$%^TYY",1,false,true,"/melee.png",arr);
        melee.toJSON();
        System.out.println("--------");
        melee.setListJsonToFile();
        GameList allgames = new GameList();
        //allgames.outputAllGames();
        

    }


}

