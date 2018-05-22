import java.io.Serializable;

public class Staff extends Item implements Serializable{
    private int baseheal;
    private int magicmulti;
    private boolean healing;
    private String statusmessage;
    private int weaponrank;
    private String[] ranktypes = new String[] {"E","D","C","B","A","S"};
    private String[] stattypes = new String[] {"HP", "Strength", "Magic", "Skill", "Speed", "Luck", "Defense", "Resistance", "Hit", "Avoid", "Crit"};

    Staff(String name) { //constructor
        super(name);
        baseheal = 0;
        magicmulti = 1;
        healing = true;
        weaponrank = 0;
        this.setStaff(true);
    }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return temp;
    }
    public void setWeaponRank(int i) { //sets required staff rank
        statusmessage += this.getName() + "'s required staff rank set to " + ranktypes[i] + ".\n";
        weaponrank = i;
    }
    int getWeaponRank()
    {
        return weaponrank;
    }
    public void setHealing(boolean b) { //sets if staff is healing or status
        if (b) {
            statusmessage += getName() + " is now a healing staff.\n";
        } else {
            statusmessage += getName() + " is now a status staff.\n";
        }
        healing = b;
    }
    public boolean isHealing()
    {
        return healing;
    }
    public void setBaseHeal(int i) { //sets base healing amount
        statusmessage += getName() + "'s base healing is now " + i + ".\n";
        baseheal = i;
    }
    public int getBaseHeal()
    {
        return baseheal;
    }
    public void setMagicMultiplier(int i) { //sets bonus from magic
        statusmessage += getName() + "'s now adds magic stat x " + i + " to total healing.\n";
        magicmulti = i;
    }
    public int getMagicMultiplier()
    {
        return magicmulti;
    }
}
