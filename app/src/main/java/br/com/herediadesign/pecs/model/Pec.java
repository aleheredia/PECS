package br.com.herediadesign.pecs.model;

import java.io.Serializable;

/**
 * Created by aheredia on 4/22/2015.
 */
public class Pec implements Serializable {
    private int id;
    private String path;
    private String label;
    private int categoryId;

    public Pec(){}

    public Pec(int id,String path, String label, int catId){
        this.id = id;
        this.path = path;
        this.label = label;
        this.categoryId = catId;
    }

    public Pec(String path, String label, int catId){
        this.path = path;
        this.label = label;
        this.categoryId = catId;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
