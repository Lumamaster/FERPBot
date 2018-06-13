import java.io.Serializable;

public class WeaponBreaker extends PassiveSkill implements Serializable{
    private int weapon;
    private String[] types = new String[]{"Hit", "Avoid", "Crit", "Damage dealt", "Damage Multiplier", "Damage taken"};
    private double[] advantage = new double[6];
    private String statusmessage;
    private String[] weapontypes = new String[] {"Sword", "Lance", "Axe", "Anima", "Light", "Dark", "Bow"};
    WeaponBreaker(String name, int w) { //constructor
        super(name);
        super.setBreaker(true);
        weapon = w;
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
    public void setAdvantage(int stat, double a) { //sets what bonus the skill gives
        statusmessage += getName() + " now increases/decreases " + types[stat] + " by " + a + " against " + weapontypes[weapon] + ".\n";
        advantage[stat] = a;
    }
    public double[] getAdvantage() {
        return advantage;
    }
    public void setWeapon(int i) { //sets which weapon type skill grants advantage against
        statusmessage += getName() + " is now a " + weapontypes[i] + " breaker skill.\n";
        weapon = i;
    }
    public int getEffectiveWeapon() { return weapon; }
}