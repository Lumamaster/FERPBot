import java.io.Serializable;

public class Skill implements Serializable{
    private String name;
    private String description;
    private boolean passive;
    private boolean threshhold;
    private boolean attackmultiplier;
    private boolean proc;
    private boolean breaker;
    private boolean offensive;
    private boolean defensive;
    private boolean miracle;
    private boolean raceslayer;
    private String statusmessage;
    Skill() { //constructor
        description = "";
        passive = false;
        threshhold = false;
        attackmultiplier = false;
        proc = false;
        breaker = false;
        offensive = false;
        defensive = false;
        miracle = false;
        raceslayer = false;
    }
    Skill(String name) {
        this();
        statusmessage = "New skill named " + name + " created!\n";
        this.name = name;
    }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return temp;
    }
    //settings for determining what type of skill it is
    public void setProc(boolean b) {
        if (b) {
            statusmessage = name + " is now a battle proc skill.\n";
        } else {
            statusmessage = name + " is no longer a battle proc skill.\n";
        }
        proc = b;
    }
    public boolean isProc() { return proc; }
    public void setThreshold(boolean b) {
        if (b) {
            statusmessage = name + " is now a health threshold skill.\n";
        } else {
            statusmessage = name + " is no longer a health threshold skill.\n";
        }
        threshhold = b;
    }
    public boolean isThreshhold() { return threshhold; }
    public void setAttackMulti(boolean b) {
        if (b) {
            statusmessage = name + " is now an attack multiplier skill.\n";
        } else {
            statusmessage = name + " is no longer an attack multiplier skill.\n";
        }
        attackmultiplier = b;
    }
    public boolean isAttackMulti() { return attackmultiplier; }
    public void setBreaker(boolean b) {
        if (b) {
            statusmessage = name + " is now a weapon breaker skill.\n";
        } else {
            statusmessage = name + " is no longer a weapon breaker skill.\n";
        }
        breaker = b;
    }
    public boolean isBreaker() { return breaker; }
    public void setOffensive(boolean b) {
        if (b) {
            statusmessage = name + " is now an initiation skill.\n";
        } else {
            statusmessage = name + " is no longer an initiation skill.\n";
        }
        offensive = b;
    }
    public boolean isOffensive() { return offensive; }
    public void setDefensive(boolean b) {
        if (b) {
            statusmessage = name + " is now a defensive skill.\n";
        } else {
            statusmessage = name + " is no longer a defensive skill.\n";
        }
        defensive = b;
    }
    public boolean isDefensive() { return defensive; }
    public void setSlayer(boolean b) {
        if (b) {
            statusmessage = name + " is now a racial slayer skill.\n";
        } else {
            statusmessage = name + " is no longer a racial slayer skill.\n";
        }
        raceslayer = b;
    }
    public boolean isSlayer() { return raceslayer; }
    public void setMiracle(boolean b) {
        if (b) {
            statusmessage = name + " is now a miracle skill.\n";
        } else {
            statusmessage = name + " is no longer a miracle skill.\n";
        }
        miracle = b;
    }
    public boolean isMiracle() { return miracle; }
    public void setName(String s) {
        statusmessage = "Skill " + name + " is now named " + s + ".\n";
        name = s;
    }
    public String getName() { return name; }
    public void setDescription(String s) { //sets skill description
        statusmessage = name + "'s description has been changed.\n";
        description = s;
    }
    public String getDescription() { return description; }
    public void setPassive(boolean b) {
        if (b) {
            statusmessage = name + " is now an active skill.\n";
        } else {
            statusmessage = name + " is now a passive skill.\n";
        }
        passive = b;
    }
    public boolean isPassive() { return passive; }
}
