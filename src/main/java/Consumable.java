import java.io.Serializable;

public class Consumable extends Item implements Serializable{
    private int[] statmod = new int[11];
    private String statusmessage;
    private String[] stattypes = new String[] {"HP", "Strength", "Magic", "Skill", "Speed", "Luck", "Defense", "Resistance", "Hit", "Avoid", "Crit"};
    Consumable(String name) {
        super(name);
        statusmessage += "New consumable named " + name + " created!\n";
        this.setBuff(true);
        int i;
        for (i = 0; i < 11; i++) {
            statmod[i] = 0;
        }
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
    public int[] getStatModifiers()
    {
        return statmod;
    }
}
