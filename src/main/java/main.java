package src.main.java;

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
import src.main.java.filters.*;
class Main {

    


    public static void main(String[] args) throws ParseException {
        //Creating Match History based on 4qs#24
        Character peach = new Character("Peach",Paths.get("peach.png"));
        Character falcon = new Character("Captain Falcon",Paths.get("captainfalcon.png"));
        Character falco = new Character("Falco",Paths.get("falco.png"));
        Character sheik = new Character("Sheik",Paths.get("sheik.png"));
        Character marth = new Character("Marth",Paths.get("marth.png"));
        Map dreamland64 = new Map("Dreamland 64");
        Map yoshisStory = new Map("Yoshi's Story");
        Map battlefield = new Map("Battlefield");
        Map finalDestination = new Map("Final Destination");
        Map pokemonStadium = new Map("Pokemon Stadium");
        Map fountainOfDreams = new Map("Fountain of Dreams");
        Match gooseMatch1 = new Match(new Character[]{peach},new Character[]{marth},dreamland64,true);
        Match gooseMatch2 = new Match(new Character[]{peach},new Character[]{marth},yoshisStory,true);
        Match gooseMatch3 = new Match(new Character[]{peach},new Character[]{falcon},yoshisStory,true);
        Date date = parseDate("10-12-2020");
        Set goose = new Set(new Match[]{gooseMatch1,gooseMatch2,gooseMatch3},"Goose","","4Qs #24",date);
        Match mobyMatch3 = new Match(new Character[]{peach},new Character[]{falcon},dreamland64,true);
        Set moby = new Set(new Match[]{gooseMatch3,gooseMatch3,mobyMatch3},"Moby","","4Qs #24",date);
        Match slomMatch1 = new Match(new Character[]{peach},new Character[]{peach},battlefield,true);
        Match slomMatch2 = new Match(new Character[]{peach},new Character[]{peach},finalDestination,true);
        Match slomMatch3 = new Match(new Character[]{peach},new Character[]{peach},pokemonStadium,true);
        Set slom = new Set(new Match[]{slomMatch1,slomMatch2,slomMatch3},"Slom","","4Qs #24",date);
        Match raphMatch1 = new Match(new Character[]{peach},new Character[]{falco},fountainOfDreams,true);
        Match raphMatch2 = new Match(new Character[]{peach},new Character[]{falco},battlefield,false);
        Match raphMatch3 = new Match(new Character[]{peach},new Character[]{falco},finalDestination,true);
        Match raphMatch4 = new Match(new Character[]{peach},new Character[]{falco},yoshisStory,false);
        Match raphMatch5 = new Match(new Character[]{peach},new Character[]{falco},dreamland64,true);
        Set raph = new Set(new Match[]{raphMatch1,raphMatch2,raphMatch3,raphMatch4,raphMatch5},"23","","4Qs #24",date);
        Match danubiMatch1 = new Match(new Character[]{peach},new Character[]{sheik},battlefield,true);
        Match danubiMatch2 = new Match(new Character[]{peach},new Character[]{sheik},yoshisStory,true);
        Set danubi = new Set(new Match[]{danubiMatch1,danubiMatch2,danubiMatch1},"Danubi","","4Qs #24",date);
        SetList tSetList = new SetList();
        tSetList.addSet(goose);
        tSetList.addSet(moby);
        tSetList.addSet(slom);
        tSetList.addSet(raph);
        tSetList.addSet(danubi);

        Map[] allMaps = {dreamland64,yoshisStory,battlefield,finalDestination,pokemonStadium,fountainOfDreams};
        Character[] allChars = {peach,falcon,falco,sheik,marth};
        Game melee = new Game("Melee",1,false,allMaps,null,tSetList,allChars);
    
        PlayerCharacterFilter pcf = new PlayerCharacterFilter();
        OpponentCharacterFilter ocf = new OpponentCharacterFilter();
        MapFilter mf = new MapFilter();
        PlayerScoreFilter psf = new PlayerScoreFilter();
        OpponentScoreFilter osf = new OpponentScoreFilter();
        OpponentFilter of = new OpponentFilter();
        TeammateFilter tef = new TeammateFilter();
        TournamentFilter tof = new TournamentFilter();
        DateFilter df = new DateFilter();

        FilterList fl1 = new FilterList(new Filter[]{ocf},new Object[]{falcon}); 
        
        SetList filteredList = tSetList.applyFilters(fl1);
        
        Statistics stats = new Statistics(tSetList,melee);
        stats.fillCharacterStats();
        System.out.println(stats.toString());

        
        //have a look at the word doc
        //The stats thing needs to go through the match history and dynamically create a list of each characters stats

    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("DD-MM-yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}

