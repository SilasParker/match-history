

import java.io.FileWriter;

import com.google.gson.Gson;

public class Game {
    private String name;
    private int characterNumPerSide;
    private boolean teammate;
    private boolean maps;
    private String image;
    private transient SetList setList;
    private Gson gson = new Gson();

    public Game(String name, int characterNumPerSide, boolean teammate, boolean maps, String image, SetList setList) {
        this.name = name;
        this.characterNumPerSide = characterNumPerSide;
        this.teammate = teammate;
        this.maps = maps;
        this.image = image;
        this.setList = setList;
    }

    public String getName() {
        return this.name;
    }

    public int getCharactersPerSide() {
        return this.characterNumPerSide;
    }

    public boolean isTeammate() {
        return this.teammate;
    }

    public boolean isMap() {
        return this.maps;
    }

    public String getImage() {
        return this.image;
    }

    public SetList getSetList() {
        return this.setList;
    }

    public String toJSON() {
        System.out.println(this.gson.toJson(this));
        return this.gson.toJson(this);
    }

    public void setListJsonToFile() {
        gson.toJson(this.setList,new FileWriter("./setLits/"+this.name+".json")); //I guess carry on here
    }

    public void importSetList(String json, boolean replace) {
        SetList tempSetList = gson.fromJson(json, SetList.class);
        if(replace) {
            this.setList = tempSetList;
        } else {
            tempSetList.getAllSets().forEach((set) -> this.setList.addSet(set));
        }
        
    }
}
