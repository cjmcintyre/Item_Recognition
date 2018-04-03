package com.example.u3182551.itemrecognition;

/**
 * Created by corey on 3/04/2018.
 */

public class Images {
    private long Id; private String Name;


    public long getId(){return Id;}
    public String getName(){return Name;}

    public long setId(long id){
        Id = id;
        return Id;
    }
    public String setName(String name){
        Name = name;
        return name;
    }
}
