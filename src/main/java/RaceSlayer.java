import java.io.Serializable;

public class RaceSlayer extends Skill implements Serializable{
    private String[] types = new String[]{"Hit", "Avoid", "Crit", "Damage dealt", "Damage Multiplier", "Damage taken"};
    private String raceslay;
    private double[] advantage = new double[6];
    private String statusmessage;
    RaceSlayer(String name, String race) { //constructor
        super(name);
        super.setSlayer(true);
        raceslay = race;
        statusmessage = "";
        int i;
        for (i = 0; i < 6; i++) {
            advantage[i] = 0;
        }
    }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return temp;
    }
    public void setSlay(String s) { //sets which race this skill is effective against
        statusmessage += getName() + " is now a " + s + " slaying skill.\n";
        raceslay = s;
    }
    public String getSlay() { return raceslay; }
    public void setStatAdvantage(int stat, double a) { //sets stat advantage against race this skill grants effectiveness against
        statusmessage += getName() + " now increases/modifies " + types[stat] + " by " + a + " against " + raceslay + "(s).\n";
        advantage[stat] = a;
    }
    public double[] getStatAdvantage() { return advantage; }
}
