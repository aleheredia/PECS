package br.com.herediadesign.pecs.enumerations;

/**
 * Created by aheredia on 4/22/2015.
 */
public enum Category {
    BASIC("Básico"),
    INTERMEDIATE("Intermediário"),
    ADVANCED("Avançado"),
    PRO("Profissional");

    private String cat;

    Category(String cat){
        this.cat = cat;
    }

    public String getValue(){
        return this.cat;
    }
}
