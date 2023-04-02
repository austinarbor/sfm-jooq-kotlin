package dev.aga.sfm.model;

public class MyPojo {
    private int id;
    private String name;

    public MyPojo() {
        this(0, null);
    }

    public MyPojo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
