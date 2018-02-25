public class Item {
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
    public void setStaff(boolean b) {
        statusmessage += name + " is marked as a staff.\n";
        isstaff = b;
    }
    public boolean isStaff() { return isstaff; }
    public void setWeapon(boolean b) {
        statusmessage += name + " is marked as a weapon.\n";
        isweapon = b;
    }
    public boolean isWeapon() { return isweapon; }
    public void setBuff(boolean b) {
        statusmessage += name + " is marked as a consumable.\n";
        isbuff = b;
    }
    public boolean isBuff() { return isbuff; }
    public String getName() { return name; }
    public void setDescription(String s) {
        statusmessage += name + "'s description has been changed.\n";
        description = s;
    }
    public String getDescription() { return description; }
    public void setMaxUses(int s) {
        statusmessage += name + "'s max durability has been set to " + s + ".\n";
        maxuses = s;
    }
    public int getMaxUses() { return maxuses; }
    public void setCurrUses(int s) {
        statusmessage += name + "'s durability has been set to " + s + ".\n";
        uses = s;
    }
    public int getCurrUses() { return uses; }
    public void setIndestructible(boolean b) {
        if (b) {
            statusmessage += name + " is now indestructible.\n";
        } else {
            statusmessage += name + " is no longer indestructible.\n";
        }
        indestructible = b;
    }
    public boolean isIndestructible() { return indestructible; }
    public int use() {
        uses--;
        return uses;
    }
    public boolean isBroken() {
        if (uses <= 0) {
            statusmessage += name + " broke!\n";
            return true;
        }
        return false;
    }
}
