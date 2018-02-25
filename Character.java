import java.util.ArrayList;

public class Character {
    private String statusmessage;
    private String name;
    private String appearance;
    private String family;
    private String background;
    private String race;
    private ArrayList<Item> inventory = new ArrayList<>();
    private SkillList skilllist = new SkillList();
    private String[] weapontypes = new String[] {"Sword", "Lance", "Axe", "Anima", "Light", "Dark", "Bow", "Staff"};
    private String[] ranktypes = new String[] {"E","D","C","B","A","S"};
    private String[] stattypes = new String[] {"HP", "Strength", "Magic", "Skill", "Speed", "Luck", "Defense", "Resistance", "Hit", "Avoid", "Crit"};
    private Weapon equippedweapon;
    private boolean hasEquippedWeapon;
    private Staff equippedstaff;
    private int[] weaponrank = new int[8];
    private int[] growths = new int[8];
    private int[] stats = new int[11];
    private int currHP;
    private int exp;
    private int level;
    private int levelcap;
    private Class charclass;
    private int team;

    public Character() { //constructor
        appearance = "";
        family = "";
        background = "";
        race = "";
        int i;
        for (i = 0; i < 8; i++) {
            growths[i] = 0;
        }
        for (i = 0; i < 11; i++) {
            stats[i] = 0;
        }
        for (i = 0; i < 8; i++) {
            weaponrank[i] = 0;
        }
        currHP = 0;
        level = 1;
        levelcap = 20;
        statusmessage = "";
        hasEquippedWeapon = false;
    }
    public Character(String name) {
        this();
        statusmessage += "New character named " + name + " created!\n";
        this.name = name;
    }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return temp;
    }
    public SkillList getSkills() {
        return skilllist;
    }
    public void addSkill(Skill s) { //adds skill to skill list if not already in
        if (skilllist.addSkill(s))
        {
            statusmessage += s.getName() + " has been added to " + name + "'s skills.\n";
        }
        else
        {
            statusmessage += s.getName() + " is already in " + name + "'s skill list.\n";
        }
    }
    public void removeSkill(Skill s) { //checks if skill is in skill list, then removes if it is
        if (skilllist.removeSkill(s))
        {
            statusmessage += s.getName() + " has been removed from " + name + "'s skills.\n";
        }
        else
        {
            statusmessage += s.getName() + " was not in " + name + "'s skill list.\n";
        }
    }
    public void changeName(String n) { //changes name of character
        statusmessage += name + " is now named " + n + ".\n";
        name = n;
    }
    public String getName() { return name; }
    public void setAppearance(String a) { //changes appearance of character
        statusmessage += name + "'s appearance has been changed.\n";
        appearance = a;
    }
    public String getAppearance() { return appearance; }
    public void setFamily(String s) { //changes family of character
        statusmessage += name + "'s family has been changed.\n";
        family = s;
    }
    public String getFamily() { return family; }
    public void setBackground(String s) { //changes background of character
        statusmessage += name + "'s background has been changed.\n";
        background = s;
    }
    public String getBackground() { return background; }
    public void setRace(String s) { //changes character race
        statusmessage += name + "'s race has been changed.\n";
        race = s;
    }
    public String getRace() { return race; } //helpers
    public ArrayList<Item> getInventory() { return inventory; }
    public SkillList getSkilllist() { return skilllist; }
    public Weapon getEquippedWeapon() { return equippedweapon; }
    public Staff getEquippedStaff() { return equippedstaff; }
    public int[] getGrowths() { return growths; }
    public int[] getStats() { return stats; }
    public int getCurrHP() { return currHP; }
    public void setMaxHP(int value) {
        stats[0] = value;
        statusmessage += name + "'s maximum HP has been set to " + value + ".\n";
    }
    public void setCurrHP(int value) {
        currHP = value;
        statusmessage += name + "'s current HP has been set to " + value + ".\n";
    }
    public void setStrength(int value) {
        stats[1] = value;
        statusmessage += name + "'s strength has been set to " + value + ".\n";
    }
    public void setMagic(int value) {
        stats[2] = value;
        statusmessage += name + "'s magic has been set to " + value + ".\n";
    }
    public void setSkill(int value) {
        stats[3] = value;
        statusmessage += name + "'s skill has been set to " + value + ".\n";
    }
    public void setSpeed(int value) {
        stats[4] = value;
        statusmessage += name + "'s speed has been set to " + value + ".\n";
    }
    public void setLuck(int value) {
        stats[5] = value;
        statusmessage += name + "'s luck has been set to " + value + ".\n";
    }
    public void setDefense(int value) {
        stats[6] = value;
        statusmessage += name + "'s defense has been set to " + value + ".\n";
    }
    public void setResistance(int value) {
        stats[7] = value;
        statusmessage += name + "'s resistance has been set to " + value + ".\n";
    }
    public void setLevel(int i) { //sets character level
        statusmessage += name + "'s level has been set to " + i + "\n";
        level = i;
    }
    public int getLevel() { return level; }
    public void setLevelCap(int i) { //sets level cap
        statusmessage += name + "'s level cap has been set to " + i + "\n";
        levelcap = i;
    }
    public int getLevelCap() { return levelcap; }
    public void setClass(Class c) { //changes character class
        statusmessage += name + "'s class has been set to " + c.getName() + "\n";
        charclass = c;
    }
    public Class getCurrentClass() { return charclass; }
    public void setTeam(int i) { //changes character team
        statusmessage += name + "'s team has been set to " + i + "\n";
        team = i;
    }
    public int getTeam() { return team; }
    public void addItem(Item i) { //adds an item to inventory
        statusmessage += i.getName() + " has been added to " + name + "'s inventory.\n";
        inventory.add(i);
    }
    public boolean hasItem(Item i) { //checks if character has item in inventory
        return inventory.contains(i);
    }
    public boolean removeItem(Item i) { //removes item from inventory if it exists
        if (inventory.remove(i)) {
            statusmessage += i.getName() + " has been removed from  " + name + "'s inventory.\n";
            return true;
        }
        statusmessage += i.getName() + " was not in  " + name + "'s inventory.\n";
        return false;
    }
    public void setWeaponRank(int weapon, int rank) { //changes character weapon rank
        statusmessage += name + "'s " + weapontypes[weapon] + " rank has been set to " + ranktypes[rank] + ".\n";
        weaponrank[weapon] = rank;
    }
    public int[] getWeaponRanks() { return weaponrank; }
    public boolean equipWeapon(Weapon w) { //equips a weapon in character's inventory if it exists
        if (inventory.contains(w)) {
            if (weaponrank[w.getWeapontype()] >= w.getWeaponRank()) {
                equippedweapon = w;
                statusmessage += name + " has equipped " + w.getName() + ".\n";
                hasEquippedWeapon = true;
                return true;
            }
            else
            {
                statusmessage += name + "'s " + weapontypes[w.getWeapontype()] + " rank is insufficient to equip " + w.getName() + ". (has: " + weaponrank[w.getWeapontype()] + ", required: " + w.getWeaponRank() + ").\n";
                return false;
            }
        }
        return false;
    }
    public void unequipWeapon() { //unequips weapon if weapon is equipped
        if (hasEquippedWeapon) {
            statusmessage += name + " has unequipped " + equippedweapon.getName() + ".\n";
            hasEquippedWeapon = false;
        } else {
            statusmessage += name + " does not have any currently equipped weapon.\n";
        }
    }
    public boolean isEquippingWeapon() { return hasEquippedWeapon; }
    public boolean equipStaff(Staff s) { //equips a staff if staff is in character's weapon
        if (inventory.contains(s)) {
            if (weaponrank[7] >= s.getWeaponRank()) {
                equippedstaff = s;
                statusmessage += name + " has equipped " + s.getName() + ".\n";
                return true;
            } else {
                statusmessage += name + "'s staff rank is insufficient to equip " + s.getName() + ". (has: " + weaponrank[7] + ", required: " + s.getWeaponRank() + ").\n";
                return false;
            }
        }
        statusmessage += name + " does not have " + s.getName() + " in their inventory.\n";
        return false;
    }
    public boolean promote(Class c) { //promotes a character to the specified class if it is a promotion path
        statusmessage = "Promotion!\n";
        int i;
        if (charclass.getPromotionPaths().contains(c)) {
            statusmessage += name + " has promoted to " + c.getName() + ".\n";
            charclass = c;
            for (i = 0; i < c.getBonusSkills().size(); i++) {
                if (!skilllist.hasSkill(charclass.getBonusSkills().get(i))) {
                    statusmessage += name + " has learned " + charclass.getBonusSkills().get(i).getName() + "!\n";
                    skilllist.addSkill(charclass.getBonusSkills().get(i));
                }
            }
            for (i = 0; i < 7; i++) {
                if (charclass.isPromoWeaponRankBonusSet()) {
                    if (charclass.getPromoWeaponRankBonus()[i] > weaponrank[i]) {
                        weaponrank[i] = charclass.getPromoWeaponRankBonus()[i];
                        statusmessage += weapontypes[i] + " rank has increased to " + ranktypes[charclass.getPromoWeaponRankBonus()[i]] + ".\n";
                    }
                } else {
                    if (weaponrank[i] < 6) {
                        if (weaponrank[i] == 6) { }
                        else if (weaponrank[i] + charclass.getPromoWeaponRankBonus()[i] > 6)
                        {
                            weaponrank[i] = 6;
                            statusmessage += weapontypes[i] + " rank has increased to S.\n";
                        }
                        else {
                            weaponrank[i] += charclass.getPromoWeaponRankBonus()[i];
                            statusmessage += weapontypes[i] + " rank has increased to " + ranktypes[weaponrank[i]] + ".\n";
                        }
                    }
                }
            }
            for (i = 0; i < 11; i++) {
                stats[i] += charclass.getClassBonuses()[i];
            }
            for (i = 0; i < 8; i++) {
                if (charclass.getPromotionGains()[i] != 0) {
                    statusmessage += stattypes[i] + " increased by " + charclass.getPromotionGains()[i] + "! (" + stats[i] + "->" + (charclass.getPromotionGains()[i] + stats[i]) + ").\n";
                }
                stats[i] += charclass.getPromotionGains()[i];
            }
            level = 1;
            return true;
        }
        statusmessage = c.getName() + " is not a possible promotion for " + name + ".\n";
        return false;
    }
    public void levelup() { //levels up the character
        statusmessage += name + " leveled up from " + level + " to " + (level+1) + "!\n";
        int i;
        int temp;
        int gtemp;
        for (i = 0; i < 8; i++) {
            int total = 0;
            gtemp = growths[i];
            do {
                temp = (int) (Math.random() * 100);
                if (temp < growths[i]) {
                    total++;
                }
                gtemp -= 100;
            } while (gtemp > 100);
            if (total > 0) {
                statusmessage += stattypes[i] + " increased by " + total + "! {" + stats[i] + "->" + (stats[i] + total) + ").\n";
            }
            stats[i] += total;
        }
    }
    public void gainExperience(int experience) { //adds experience to the character and levels it up if it reaches 100
        if (experience > 0) {
            statusmessage += name + " gained " + experience + " experience!\n";
            int temp = experience;
            while (exp + temp >= 100 && level < levelcap) {
                temp -= (100 - exp);
                exp = 0;
                levelup();
            }
            exp += temp;
        } else {
            statusmessage += "Invalid experience amount.";
        }
    }
    public int getExperience() { return exp; }
    public boolean damage(int damage) {
        if (damage > stats[1]) {
            stats[1] = 0;
            return true;
        } else {
            stats[1] -= damage;
            return false;
        }
    }
    public void heal(int healing) {
        int h;
        int temp = stats[1];
        if (healing + stats[1] > stats[0]) {
            h = stats[0] - stats[1];
            stats[1] = stats[0];
        } else {
            h = healing;
            stats[1] += healing;
        }
        statusmessage += name + " healed " + h + " HP! (" + temp + "->" + stats[1] + ")\n";
    }
}