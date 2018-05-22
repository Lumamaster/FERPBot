import java.io.Serializable;

public class Consumable extends Item implements Serializable{
    private int[] statmod = new int[11];
    private String statusmessage;
    private boolean heals;
    private int healamount;
    private String[] stattypes = new String[] {"HP", "Strength", "Magic", "Skill", "Speed", "Luck", "Defense", "Resistance", "Hit", "Avoid", "Crit", "Current HP"};
    Consumable(String name) {
        super(name);
        statusmessage += "New consumable named " + name + " created!\n";
        this.setBuff(true);
        int i;
        for (i = 0; i < 12; i++) {
            statmod[i] = 0;
        }
        heals = false;
        healamount = 0;
    }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return temp;
    }
    public void setStatmod(int stat, int mod) {
        statusmessage += getName() + " now increases/decreases " + stattypes[stat] + " by " + mod + ".\n";
        statmod[stat] = mod;
    }
    int[] getStatModifiers()
    {
        return statmod;
    }
    public void setHeals(boolean b) {
        heals = b;
        if (b) {
            statusmessage += getName() + " now heals the user.\n";
        } else {
            statusmessage += getName() + " no longer heals the user.\n";
        }
    }
    public boolean isHealing() { return heals; }
    public void setHealAmount(int i) {
        healamount = i;
        statusmessage += getName() + " now heals for " + i + " HP.\n";
    }
    public int getHealAmount() { return healamount; }
}
