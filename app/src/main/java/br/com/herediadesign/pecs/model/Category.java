package br.com.herediadesign.pecs.model;

import java.io.Serializable;

/**
 * Created by aheredia on 4/24/2015.
 */
public class Category implements Serializable {

    private int id;
    private String label;
    private String color;

    public Category(){}

    public Category(int id, String label, String color){
        this.id = id;
        this.label = label;
        this.color = color;
    }

    public Category(String label, String color){
        this.label = label;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
