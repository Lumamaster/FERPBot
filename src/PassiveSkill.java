public class PassiveSkill extends Skill {
    private String[] stattypes = new String[] {"HP", "Strength", "Magic", "Skill", "Speed", "Luck", "Defense", "Resistance", "Hit", "Avoid", "Crit", "Physical damage taken", "Magical damage taken", "Damage taken", "Physical damage dealt", "Magical damage dealt", "Damage dealt"};
    private int[] buff = new int[17];
    private boolean vantage;
    private double vantagethreshold;
    private String statusmessage;
    PassiveSkill(String name) { //constructor
        super(name);
        super.setPassive(true);
        statusmessage = "";
        int i;
        for (i = 0; i < 17; i++) {
            buff[i] = 0;
        }
        vantage = false;
        vantagethreshold = 0;
    }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return temp;
    }
    public void setBuff(int stat, int b) { //sets what buff the stats get
        statusmessage += getName() + " now increases/decreases " + stattypes[stat] + " by " + b + ".\n";
        buff[stat] = b;
    }
    public int[] getBuff() { return buff; }
    public void setVantage(boolean b) {
        if (b) {
            statusmessage += getName() + " is now a vantage-type skill.\n";
        } else {
            statusmessage += getName() + " is no longer a vantage-type skill.\n";
        }
        vantage = b;
    }
    public boolean isVantage() { return vantage; }
    public void setVantageThreshold(double d) {
        vantagethreshold = d;
        statusmessage += getName() + "'s vantage activation threshold is now " + d + ".\n";
    }
    public double getVantageThreshold() { return vantagethreshold; }
}
