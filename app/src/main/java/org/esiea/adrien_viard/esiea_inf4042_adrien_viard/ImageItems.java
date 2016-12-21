package org.esiea.adrien_viard.esiea_inf4042_adrien_viard;

import android.net.Uri;

/**
 * Created by Clems on 18/12/2016.
 */

public class ImageItems {
    public String title;
    public String type;
    public String directors;
    public String nationality;
    public String actors;
    public String characters;
    public String producer;
    public String imageURL;
    public int id;

    public String getTitle(){
        return title;
    }

    public String getType(){
        return type;
    }

    public String getDirectors(){
        return directors;
    }

    public String getNationality(){
        return nationality;
    }

    public String getActors(){
        return actors;
    }

    public String getCharacters(){
        return characters;
    }

    public String getProducer(){
        return producer;
    }

    public String getImageURL(){
        return imageURL;
    }

    public Uri getImageURI() {
        return Uri.parse(imageURL);
    }

    public int getId(){
        return id;
    }
}
