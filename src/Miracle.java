public class Miracle extends Skill {
    private double luckmulti;
    private String[] stattypes = new String[] {"HP", "Strength", "Magic", "Skill", "Speed", "Luck", "Defense", "Resistance"};
    private int stattype;
    private String statusmessage;
    Miracle(String name, int p) {
        super(name);
        super.setMiracle(true);
        luckmulti = p;
        statusmessage = "";
        stattype = 5;
    }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return temp;
    }
    public void setPercentage(int i) {
        statusmessage += getName() + "'s luck multiplier percentage has been changed to " + i + ".\n";
        luckmulti = i;
    }
    public double getLuckMultiplier() { return luckmulti; }
    public void setActivationStat(int i) {
        if (i >= 0 && i < 8) {
            statusmessage += getName() + "'s activation stat has been set to " + stattypes[i] + ".\n";
            stattype = i;
        } else {
            statusmessage += "Invalid stat inputted, " + getName() + "'s activation stat has defaulted to Luck.\n";
            stattype = 3;
        }
    }
    public int getActivationstat() { return stattype; }
    public boolean testMiracle(int luck) {
        return Math.random() * 100 < luckmulti * luck;
    }
}
