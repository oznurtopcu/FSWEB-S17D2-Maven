package model;

public class Developer {
    private long id;
    private String name;
    private double salary;
    private Experience experience;

    public Developer(long id, String name, double salary, Experience experience) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.experience = experience;
    }
}
