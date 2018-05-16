import java.io.Serializable;

public class Threshold extends Skill implements Serializable{
    private String statusmessage;
    private String[] stattypes = new String[] {"HP", "Strength", "Magic", "Skill", "Speed", "Luck", "Defense", "Resistance", "Hit", "Avoid", "Crit", "Physical damage taken", "Magical damage taken", "Damage taken", "Physical damage dealt", "Magical damage dealt", "Damage dealt"};
    private int[] buff = new int[17];
    double threshold;
    Threshold(String name, double threshold) {
        super(name);
        super.setThreshold(true);
        this.threshold = threshold;
    }
    public void setThresholdAmount(double d) {
        threshold = d;
        statusmessage += getName() + "'s activation threshold is now " + d + ".\n";
    }
    public double getThresholdAmount() { return threshold; }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return statusmessage;
    }
    public void setBuff(int stat, int b) { //sets what buff the stats get
        statusmessage += getName() + " now increases/decreases " + stattypes[stat] + " by " + b + ".\n";
        buff[stat] = b;
    }
    public int[] getBuff() { return buff; }
}
