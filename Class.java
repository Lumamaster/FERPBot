import java.util.ArrayList;

public class Class {
    private String name;
    private int[] gains = new int[8];
    private int[] classbonus = new int[11];
    private int[] bonusweaponrank = new int[7];
    private boolean bonusset;
    private boolean mounted;
    private boolean flying;
    private boolean armored;
    private int tier;
    private String statusmessage;
    private ArrayList<Skill> bonusskills;
    private ArrayList<Class> promotionpaths;
    private String[] weapontypes = new String[] {"Sword", "Lance", "Axe", "Bow", "Anima", "Light", "Dark"};
    private String[] ranktypes = new String[] {"E","D","C","B","A","S"};
    private String[] stattypes = new String[] {"HP", "Strength", "Magic", "Skill", "Speed", "Luck", "Defense", "Resistance", "Hit", "Avoid", "Crit"};

    public Class() { //constructor
        int i;
        for (i = 0; i < 8; i++) {
            gains[i] = 0;
        }
        for (i = 0; i < 11; i++) {
            classbonus[i] = 0;
        }
        for (i = 0; i < 7; i++) {
            bonusweaponrank[i] = 0;
        }
        bonusset = false;
        mounted = false;
        flying = false;
        armored = false;
    }
    public Class(String name) {
        this();
        statusmessage += "New class named " + name + " created!\n";
        this.name = name;
        tier = 1;
    }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return temp;
    }
    public void setName(String s) { //sets class name
        statusmessage += "Class " + name + " is now named " + s + ".\n";
        name = s;
    }
    public String getName()
    {
        return name;
    }
    public void setBonusWeaponRankToBeFixed(boolean b) { //sets if on promotion, weapon ranks will be raised to a point rather than added
        if (b) {
            statusmessage += name + "'s weapon rank bonus on promotion will now raise ranks to its level.\n";
        } else {
            statusmessage += name + "'s weapon rank bonus on promotion will now increase ranks based on gains.\n";
        }
        bonusset = b;
    }
    public void setMounted(boolean b) {
        if (b) {
            statusmessage += name + " is now a mounted class.\n";
        } else {
            statusmessage += name + " is no longer a mounted class.\n";
        }
        mounted = b;
    }
    public boolean isMounted() { return mounted; }
    public void setFlying(boolean b) {
        if (b) {
            statusmessage += name + " is now a flying class.\n";
        } else {
            statusmessage += name + " is no longer a flying class.\n";
        }
        flying = b;
    }
    public boolean isFlying() { return flying; }
    public void setArmored(boolean b) {
        if (b) {
            statusmessage += name + " is now an armored class.\n";
        } else {
            statusmessage += name + " is no longer an armored class.\n";
        }
        armored = b;
    }
    public boolean isArmored() { return armored; }
    public boolean isPromoWeaponRankBonusSet()
    {
        return bonusset;
    }
    public void setPromoWeaponRankBonus(int weapon, int rank) { //sets weapon rank bonus on promotion
        statusmessage += name + "'s " + weapontypes[weapon] + " rank bonus is now " + ranktypes[rank] + ".\n";
        bonusweaponrank[weapon] = rank;
    }
    public int[] getPromoWeaponRankBonus()
    {
        return bonusweaponrank;
    }
    public void setPromotionGain(int stat, int gain) { //sets
        statusmessage += name + "'s " + stattypes[stat] + " promotion gain is now " + gain + ".\n";
        gains[stat] = gain;
    }
    public int[] getPromotionGains()
    {
        return gains;
    }
    public void setClassBonus(int stat, int gain) {
        statusmessage += name + "'s " + stattypes[stat] + " class bonus is now " + gain + ".\n";
        classbonus[stat] = gain;
    }
    public int[] getClassBonuses()
    {
        return classbonus;
    }
    public void setTier(int i) {
        statusmessage += name + "'s tier is now " + i + ".\n";
        tier = i;
    }
    public int getTier()
    {
        return tier;
    }
    public void addSkill(Skill s) {
        statusmessage += s.getName() + " has been added as a skill gained on promotion for " + name + ".\n";
        bonusskills.add(s);
    }
    public boolean removeSkill(Skill s) {
        if (bonusskills.remove(s)) {
            statusmessage += s.getName() + " has been removed as a skill gained on promotion for " + name + ".\n";
            return true;
        }
        statusmessage += s.getName() + " was not a skill gained on promotion for " + name + ".\n";
        return false;
    }
    public void addPromotionPath(Class c) {
        statusmessage += c.getName() + " has been added as a promotion path for " + name + ".\n";
        promotionpaths.add(c);
    }
    public ArrayList<Skill> getBonusSkills()
    {
        return bonusskills;
    }
    public ArrayList<Class> getPromotionPaths()
    {
        return promotionpaths;
    }
    public boolean removePromotionPath(Class c) {
        if (promotionpaths.remove(c)) {
            statusmessage += c.getName() + " has been removed as a promotion path for " + name + ".\n";
            return true;
        }
        statusmessage += c.getName() + " was not a promotion path for " + name + ".\n";
        return false;
    }
}
