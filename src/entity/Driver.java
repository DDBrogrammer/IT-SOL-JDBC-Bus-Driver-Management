package entity;

import java.io.Serializable;

public class Driver extends Person implements Serializable {
    private int id;
    private String skillLevel;
    private static final long serialVersionUID = 1L;

    public Driver(String name, String address, String phone, int id, String skillLevel) {
        super(name, address, phone);
        this.id = id;
        this.skillLevel = skillLevel;
    }
    public Driver(String name, String address, String phone, String skillLevel) {
        super(name, address, phone);
        this.skillLevel = skillLevel;
    }
    public Driver() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", skillLevel=" + skillLevel +
                "} " + super.toString();
    }
}
