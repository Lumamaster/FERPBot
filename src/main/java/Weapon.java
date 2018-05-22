import java.io.Serializable;

public class Weapon extends Item implements Serializable{
    private int might;
    private int hitrate;
    private int critrate;
    private int critmodifier;
    private int weaponrank;
    private boolean counterable;
    private boolean brave;
    private boolean ignoredefense;
    private boolean lifesteal;
    private boolean targetsmagic;
    private boolean usesmagic;
    private boolean candouble;
    private boolean antiflier;
    private boolean antiarmor;
    private boolean antimounted;
    private int minrange;
    private int maxrange;
    private int weapontype;
    private int physadvantage;
    private int physweak;
    private int magadvantage;
    private int magweak;
    private String statusmessage;
    private int[] statbonus = new int[11];
    private boolean[] weaponeffective = new boolean[7];
    String[] weapontypes = new String[] {"None","Sword", "Lance", "Axe", "Anima", "Light", "Dark", "Bow"};
    private String[] ranktypes = new String[] {"E","D","C","B","A","S"};
    private String[] stattypes = new String[] {"HP", "Strength", "Magic", "Skill", "Speed", "Luck", "Defense", "Resistance", "Hit", "Avoid", "Crit"};
    Weapon(String name, int type) { //constructor
        super(name);
        statusmessage = "New " + weapontypes[type] + " named " + name + " created!\n";
        weaponrank = 0;
        might = 0;
        hitrate = 0;
        critrate = 0;
        counterable = true;
        critmodifier = 3;
        brave = false;
        ignoredefense = false;
        candouble = true;
        antiflier = false;
        antiarmor = false;
        antimounted = false;
        int i;
        for (i = 0; i < 11; i++) {
            statbonus[i] = 0;
        }
        for (i = 0; i < 7; i++) {
            weaponeffective[i] = false;
        }
        weapontype = 0;
        minrange = 1;
        maxrange = 1;
        targetsmagic = false;
        usesmagic = false;
        magadvantage = 0;
        magweak = 0;
        physadvantage = 0;
        switch (type) {
            case 1:
                weapontype = type;
                physadvantage = 3;
                physweak = 2;
                break;
            case 2:
                weapontype = type;
                physadvantage = 1;
                physweak = 3;
                break;
            case 3:
                weapontype = type;
                physadvantage = 2;
                physweak = 1;
                break;
            case 4:
                weapontype = type;
                maxrange = 2;
                targetsmagic = true;
                usesmagic = true;
                magadvantage = 5;
                magweak = 6;
                break;
            case 5:
                weapontype = type;
                maxrange = 2;
                targetsmagic = true;
                usesmagic = true;
                magadvantage = 6;
                magweak = 4;
                break;
            case 6:
                weapontype = type;
                maxrange = 2;
                targetsmagic = true;
                usesmagic = true;
                magadvantage = 4;
                magweak = 5;
                break;
            case 7:
                weapontype = type;
                minrange = 2;
                maxrange = 2;
                antiflier = true;
                break;
        }
        lifesteal = false;
        this.setWeapon(true);
    }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return temp;
    }
    public void setWeaponRank(int i) { //changes specified weapon rank
        statusmessage += this.getName() + "'s required rank set to " + ranktypes[i] + ".\n";
        weaponrank = i;
    }
    int getWeaponRank() { return weaponrank; }
    public void setWeaponEffectiveness(int weapon, boolean e) {
        if (e) {
            statusmessage += getName() + " is now effective against " + weapontypes[weapon + 1] + ".\n";
        } else {
            statusmessage += getName() + " is no longer effective against " + weapontypes[weapon + 1] + ".\n";
        }
        weaponeffective[weapon + 1] = e;
    }
    boolean isEffectiveAgainst(int weapon) { return weaponeffective[weapon + 1]; }
    public void setAntiArmor(boolean b) { //sets if weapon is armorslaying
        if (b) {
            statusmessage += getName() + " is now an anti armor weapon.\n";
        } else {
            statusmessage += getName() + " is no longer an anti armor weapon.\n";
        }
        antiarmor = b;
    }
    boolean isAntiArmor() { return antiarmor; }
    public void setAntiFlier(boolean b) { //sets if weapon is anti fliers
        if (b) {
            statusmessage += getName() + " is now an anti flier weapon.\n";
        } else {
            statusmessage += getName() + " is no longer an anti flier weapon.\n";
        }
        antiflier = b;
    }
    boolean isAntiFlier() { return antiflier; }
    public void setAntiMounted(boolean b) { //sets if weapon is anti mounted
        if (b) {
            statusmessage += getName() + " is now an anti mounted weapon.\n";
        } else {
            statusmessage += getName() + " is no longer an anti mounted weapon.\n";
        }
        antimounted = b;
    }
    boolean isAntimounted() { return antimounted; }
    public void setPhysicalAdvantage(int s) { //sets advantageous weapon
        statusmessage += this.getName() + "is now advantageous against " + weapontypes[s] + ".\n";
        physadvantage = s;
    }
    public int getPhysicalAdvantage()
    {
        return physadvantage;
    }
    public void setPhysicalWeakness(int s) { //sets disadvantageous weapon
        statusmessage += this.getName() + "is now disadvantageous against " + weapontypes[s] + ".\n";
        physweak = s;
    }
    public int getPhysicalWeakness()
    {
        return physweak;
    }
    public void setMagicalAdvantage(int s) { //sets advantageous magic
        statusmessage += this.getName() + "is now advantageous against " + weapontypes[s] + ".\n";
        magadvantage = s;
    }
    public int getMagicalAdvantage()
    {
        return magadvantage;
    }
    public void setMagicalWeakness(int s) { //sets diaadvantageous magic
        statusmessage += this.getName() + "is now disadvantageous against " + weapontypes[s] + ".\n";
        magweak = s;
    }
    public int getMagicalWeakness()
    {
        return magweak;
    }
    public void setWeaponType(int s) { //changes type of weapon
        statusmessage += this.getName() + "is now classified as " + weapontypes[s+1] + ".\n";
        weapontype = s+1;
    }
    int getWeapontype()
    {
        return weapontype-1;
    }
    public void setMagicTarget(boolean b) { //determines if weapon hits resistance or not
        if (b) {
            statusmessage += this.getName() + "is now a magical weapon.\n";
        } else {
            statusmessage += this.getName() + "is now a physical weapon.\n";
        }
        targetsmagic = b;
    }
    boolean isMagicTarget() { return targetsmagic; }
    public void setHitRate(int i) { //changes weapon hitrate
        statusmessage += this.getName() + "'s hitrate is now " + i + ".\n";
        hitrate = i;
    }
    public void usesMagic(boolean b) {
        if (b) {
            statusmessage += this.getName() + "now targets resistance.\n";
        } else {
            statusmessage += this.getName() + "now targets defense.\n";
        }
        usesmagic = b;
    }
    boolean isUsesMagic() { return usesmagic; }
    int getHitRate()
    {
        return hitrate;
    }
    public void setCritRate(int i) { //changes weapon crit rate
        statusmessage += this.getName() + "'s critrate is now " + i + ".\n";
        critrate = i;
    }
    int getCritRate()
    {
        return critrate;
    }
    public void setCritModifier(int i) { //changes weapon crit multiplier
        statusmessage += this.getName() + "'s crit modifier is now " + i + ".\n";
        critmodifier = i;
    }
    int getCritModifier()
    {
        return critmodifier;
    }
    public void setMight(int i) { //sets weapon might
        statusmessage += this.getName() + "'s might is now " + i + ".\n";
        might = i;
    }
    int getMight()
    {
        return might;
    }
    public void setCounterable(boolean c) { //sets if weapon can be countered
        if (c) {
            statusmessage += this.getName() + "'s is now counterable.\n";
        } else {
            statusmessage += this.getName() + "'s is now uncounterable.\n";
        }
        counterable = c;
    }
    boolean isCounterable()
    {
        return counterable;
    }
    public void setMinRange(int i) { //sets weapon minimum range
        statusmessage += this.getName() + "'s minimum range is now " + i + ".\n";
        minrange = i;
    }
    public int getMinRange()
    {
        return minrange;
    }
    public void setMaxRange(int i) { //sets weapon maximum range
        statusmessage += this.getName() + "'s maximum range is now " + i + ".\n";
        maxrange = i;
    }
    public int getMaxRange()
    {
        return maxrange;
    }
    public void setBrave(boolean b) { //sets if weapon has brave effect
        if (b) {
            statusmessage += this.getName() + "'s is now a brave weapon.\n";
        } else {
            statusmessage += this.getName() + "'s is no longer a brave weapon.\n";
        }
        brave = b;
    }
    boolean isBrave()
    {
        return brave;
    }
    public void setDouble(boolean b) { //sets if weapon can double or not
        if (b) {
            statusmessage += getName() + " can now make follow up attacks.\n";
        } else {
            statusmessage += getName() + " can no longer make follow up attacks.\n";
        }
        candouble = b;
    }
    boolean canDouble() { return candouble; }
    public void setStatBonus(int stat, int bonus) { //sets weapon stat bonuses
        statusmessage += this.getName() + " now grants a bonus of " + bonus + " to " + stattypes[stat] + ".\n";
        statbonus[stat] = bonus;
    }
    int[] getStatBonuses()
    {
        return statbonus;
    }
    public void setIgnoreDefense(boolean b) { //sets luna effect
        if (b) {
            statusmessage += this.getName() + " now ignores defense/resistance.\n";
        } else {
            statusmessage += this.getName() + " no longer ignores defense/resistance.\n";
        }
        ignoredefense = b;
    }
    boolean isIgnoreDefense()
    {
        return ignoredefense;
    }
    public void setLifeSteal(boolean b) { //sets nosferatu effect
        if (b) {
            statusmessage += this.getName() + " now heals for half the damage dealt.\n";
        } else {
            statusmessage += this.getName() + " no longer heals for half the damage dealt.\n";
        }
        lifesteal = b;
    }
    public boolean isLifeSteal()
    {
        return lifesteal;
    }
    int hasAdvantage(int s) { //calculates weapon advantage against given weapon
        if (physadvantage == s) {
            return 2;
        }
        if (physweak == s) {
            return 0;
        }
        if (magadvantage == s) {
            return 2;
        }
        if (magweak == s) {
            return 0;
        }
        return 1;
    }
}