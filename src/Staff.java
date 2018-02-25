public class Staff extends Item {
    private int baseheal;
    private int magicmulti;
    private boolean healing;
    private int[] statmod = new int[10];
    private boolean percentage;
    private String statusmessage;
    private int weaponrank;
    private String[] ranktypes = new String[] {"E","D","C","B","A","S"};
    private String[] stattypes = new String[] {"HP", "Strength", "Magic", "Skill", "Speed", "Luck", "Defense", "Resistance", "Hit", "Avoid", "Crit"};

    public Staff(String name) { //constructor
        super(name);
        baseheal = 0;
        magicmulti = 1;
        healing = true;
        percentage = false;
        weaponrank = 0;
        int i;
        for (i = 0; i < 10; i++) {
            statmod[i] = 0;
        }
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
    public int getWeaponRank()
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
    public void setPercentage(boolean b) { //sets if status given applies health based damage
        if (b) {
            statusmessage += getName() + " now does percentage based health damage.\n";
        } else {
            statusmessage += getName() + " no longer does percentage based health damage.\n";
        }
        percentage = b;
    }
    public boolean isPercentage()
    {
        return percentage;
    }
    public void setStatModifier(int stat, int mod) { //sets the stat modifier on status given
        statusmessage += getName() + " now buffs/debuffs " + stattypes[stat] + " by " + mod + ".\n";
        statmod[stat] = mod;
    }
    public int[] getStatModifiers()
    {
        return statmod;
    }
}
