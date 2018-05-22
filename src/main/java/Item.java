import java.io.Serializable;

public class Item implements Serializable{
    private String name;
    private String description;
    private int uses;
    private int maxuses;
    private boolean indestructible;
    private boolean isstaff;
    private boolean isweapon;
    private boolean isbuff;
    private String statusmessage;
    public Item() {
        statusmessage = "";
        description = "";
        uses = 1;
        maxuses = 1;
        name = "";
        isstaff = false;
        isweapon = false;
        isbuff = false;
    }
    public Item(String name) {
        this();
        statusmessage += "New item named " + name + " created!\n";
        this.name = name;
    }
    public void setName(String s) {
        statusmessage += name + " is now named " + s + ".\n";
        name = s;
    }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return temp;
    }
    void setStaff(boolean b) {
        statusmessage += name + " is marked as a staff.\n";
        isstaff = b;
    }
    boolean isStaff() { return isstaff; }
    void setWeapon(boolean b) {
        statusmessage += name + " is marked as a weapon.\n";
        isweapon = b;
    }
    boolean isWeapon() { return isweapon; }
    void setBuff(boolean b) {
        statusmessage += name + " is marked as a consumable.\n";
        isbuff = b;
    }
    boolean isBuff() { return isbuff; }
    public String getName() { return name; }
    void setDescription(String s) {
        statusmessage += name + "'s description has been changed.\n";
        description = s;
    }
    String getDescription() { return description; }
    void setMaxUses(int s) {
        statusmessage += name + "'s max durability has been set to " + s + ".\n";
        maxuses = s;
    }
    int getMaxUses() { return maxuses; }
    void setCurrUses(int s) {
        statusmessage += name + "'s durability has been set to " + s + ".\n";
        uses = s;
    }
    int getCurrUses() { return uses; }
    void setIndestructible(boolean b) {
        if (b) {
            statusmessage += name + " is now indestructible.\n";
        } else {
            statusmessage += name + " is no longer indestructible.\n";
        }
        indestructible = b;
    }
    boolean isIndestructible() { return indestructible; }
    int use() {
        uses--;
        return uses;
    }
    boolean isBroken() {
        if (uses <= 0) {
            statusmessage += name + " broke!\n";
            return true;
        }
        return false;
    }
}
