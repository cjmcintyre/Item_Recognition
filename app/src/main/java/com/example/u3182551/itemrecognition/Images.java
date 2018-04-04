package com.example.u3182551.itemrecognition;

/**
 * Created by corey on 3/04/2018.
 */

@SuppressWarnings("DefaultFileTemplate")
class Images {
    private long Id;
    private String Information;
    private String Title;
    public long getId(){return Id;}

    public String getDescription() {
        return Information;
    }

    public String getTitle() {
        return Title;
    }

    public void setId(long id){
        Id = id;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public void setTitle(String title) {
        Title = title;
    }

}
