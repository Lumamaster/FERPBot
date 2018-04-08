public class DefensiveSkill extends Skill {
    private String[] stattypes = new String[] {"HP", "Strength", "Magic", "Skill", "Speed", "Luck", "Defense", "Resistance", "Hit", "Avoid", "Crit", "Physical damage taken", "Magical damage taken", "Damage taken", "Physical damage dealt", "Magical damage dealt", "Damage dealt"};
    private int[] buff = new int[17];
    private String statusmessage;
    DefensiveSkill(String name) {
        super(name);
        super.setDefensive(true);
        statusmessage = "";
        int i;
        for (i = 0; i < 17; i++) {
            buff[i] = 0;
        }
    }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return temp;
    }
    public void setBuff(int stat, int b) {
        statusmessage += getName() + " now increases/decreases " + stattypes[stat] + " by " + b + " when defending.\n";
        buff[stat] = b;
    }
    public int[] getBuff() { return buff; }
}
