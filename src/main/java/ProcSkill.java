import java.io.Serializable;

public class ProcSkill extends Skill implements Serializable{
    private String[] stattypes = new String[] {"HP", "Strength", "Magic", "Skill", "Speed", "Luck", "Defense", "Resistance", "Physical damage taken", "Magical damage taken", "Damage taken", "Physical damage dealt", "Magical damage dealt", "Damage dealt"};
    private double statmultiplier;
    private double damagemulti;
    private boolean enemystat;
    private boolean cancel;
    private boolean defensive;
    private boolean heal;
    private boolean damage;
    private int input;
    private int activationstat;
    private String statusmessage;
    ProcSkill(String name, double proc, double damage, int stat, int inputstat) { //constructor
        super(name);
        statmultiplier = proc;
        damagemulti = damage;
        if (stat >= 0 && stat < 8) {
            activationstat = stat;
        } else {
            activationstat = 3;
        }
        statusmessage = "";
        super.setProc(true);
        enemystat = false;
        cancel = false;
        heal = false;
        this.damage = true;
        input = inputstat;
    }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return temp;
    }
    public void setProcRate(double i) { //sets activation stat multiplier
        statusmessage += getName() + "'s activation stat multiplier has been set to " + i + ".\n";
        statmultiplier = i;
    }
    public double getProcRate() { return statmultiplier; }
    public void setDamageMultiplier(double i) { //sets how much it multiplies the input stat by
        statusmessage += getName() + "'s activation input multiplier has been set to " + i + ".\n";
        damagemulti = i;
    }
    public double getDamageMultiplier() { return damagemulti; }
    public void setActivationStat(int i) { //sets which stat this skill activates off of
        if (i >= 0 && i < 8) {
            statusmessage += getName() + "'s activation stat has been set to " + stattypes[i] + ".\n";
            activationstat = i;
        } else {
            statusmessage += "Invalid stat inputted, " + getName() + "'s activation stat has defaulted to Skill.\n";
            activationstat = 3;
        }
    }
    public int getActivationstat() { return activationstat; }
    public void useEnemyStats(boolean b) { //sets if skill uses enemy stats for calculations
        if (b) {
            statusmessage += getName() + " now uses enemy stats for its calculations.\n";
        } else {
            statusmessage += getName() + " now uses ally stats for its calculations.\n";
        }
        enemystat = b;
    }
    public boolean isUseEnemyStat() { return enemystat; }
    public void setCancel(boolean b) { //sets if skill prevents enemy counter attacks on activation
        if (b) {
            statusmessage += getName() + " now prevents enemy counter attacks on activation.\n";
        } else {
            statusmessage += getName() + " now no longer prevents enemy counter attacks on activation.\n";
        }
        cancel = b;
    }
    public boolean isCancel() { return cancel; }
    public void setOnAttacked(boolean b) { // sets if skill activates upon being attacked
        if (b) {
            statusmessage += getName() + " now activates on being attacked.\n";
        } else {
            statusmessage += getName() + " now activates when attacking.\n";
        }
        defensive = b;
    }
    public boolean isOnAttacked() { return defensive; }
    public void setInputStat(int i) {
        statusmessage += getName() + " now modifies " + stattypes[i] + ".\n";
        input = i;
    }
    public int getInputStat() { return input; }
    public void setHeal(boolean b) {
        if (b) {
            statusmessage += getName() + " now heals the user.\n";
        } else {
            statusmessage += getName() + " no longer heals the user.\n";
        }
        heal = b;
    }
    public boolean isHeal() { return heal; }
    public void setDamaging(boolean b) {
        if (b) {
            statusmessage += getName() + " now damages the target.\n";
        } else {
            statusmessage += getName() + " no longer damages the target.\n";
        }
        damage = b;
    }
    public boolean isDamaging() { return damage; }
    public boolean activate(int stat) { //tests to see if skill will activate in battle
        return Math.random() * 100 < stat * statmultiplier;
    }
}
