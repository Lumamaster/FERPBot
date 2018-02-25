public class MultiHitSkill extends ProcSkill {
    private int attackcount;
    private String statusmessage;
    private boolean stack;
    MultiHitSkill(String name, int ac, double proc, double damage, int stat) {
        super(name,proc,damage,stat, 0);
        super.setAttackMulti(true);
        attackcount = ac;
        statusmessage = "";
    }
    public String getStatus() {
        String temp = statusmessage;
        statusmessage = "";
        return temp;
    }
    public void setAttackNumber(int i) {
        statusmessage += getName() + "'s attack multiplier is now " + i + ".\n";
        attackcount = i;
    }
    public int getAttackCount() { return attackcount; }
    public void setStack(boolean b) {
        if (b) {
            statusmessage += getName() + " now stacks with other multihit skills.\n";
        } else {
            statusmessage += getName() + " no longer stacks with other multihit skills.\n";
        }
        stack = b;
    }
    public boolean getStack() { return stack; }
}
