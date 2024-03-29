package fr.chaffotm.gamebook.elementary.model.io;

import java.util.List;

public class IOCharacter {

    private String name;

    private List<IOSkill> skills;

    private String money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IOSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<IOSkill> skills) {
        this.skills = skills;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
