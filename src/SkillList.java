import java.lang.reflect.Array;
import java.util.ArrayList;

public class SkillList {
    private ArrayList<Skill> allskills;
    private ArrayList<PassiveSkill> passives;
    private ArrayList<ProcSkill> procs;
    private ArrayList<RaceSlayer> raceslayers;
    private ArrayList<WeaponBreaker> breakers;
    private ArrayList<Miracle> miracles;
    private ArrayList<DefensiveSkill> defensives;
    private ArrayList<InitiationSkill> offensives;
    private ArrayList<Threshold> thresholds;
    SkillList()
    {

    }
    public boolean addSkill(Skill s) { //adds skill to skill list
        if (!hasSkill(s)) { // checks if skill doesn't exist
            allskills.add(s);
            if (s.isProc()) {
                procs.add((ProcSkill) s);
            } else if (s.isBreaker()) {
                breakers.add((WeaponBreaker) s);
            } else if (s.isDefensive()) {
                defensives.add((DefensiveSkill) s);
            } else if (s.isOffensive()) {
                offensives.add((InitiationSkill) s);
            } else if (s.isSlayer()) {
                raceslayers.add((RaceSlayer) s);
            } else if (s.isMiracle()) {
                miracles.add((Miracle) s);
            } else if (s.isPassive()) {
                passives.add((PassiveSkill) s);
            } else if (s.isThreshhold()) {
                thresholds.add((Threshold) s);
            }
            return true;
        }
        return false;
    }
    public boolean hasSkill(Skill s) { // checks if skill exists in skill list
        return allskills.contains(s);
    }
    public int getNumOfSkills() { return allskills.size(); } //returns number of skills
    public boolean removeSkill(Skill s) //checks if skill is in skill list, then removes from list
    {
        if (allskills.remove(s)) {
            if (s.isProc()) {
                procs.remove(s);
            }
            if (s.isBreaker()) {
                breakers.remove(s);
            }
            if (s.isDefensive()) {
                defensives.remove(s);
            }
            if (s.isOffensive()) {
                offensives.remove(s);
            }
            if (s.isSlayer()) {
                raceslayers.remove(s);
            }
            if (s.isMiracle()) {
                miracles.remove(s);
            }
            if (s.isPassive()) {
                passives.remove(s);
            }
            return true;
        }
        return false;
    }
    //helper methods
    public int getNumOfPassives() { return passives.size(); }
    public ArrayList<DefensiveSkill> getDefensives() { return defensives; }
    public ArrayList<InitiationSkill> getOffensives() { return offensives; }
    public ArrayList<Miracle> getMiracles() { return miracles; }
    public ArrayList<PassiveSkill> getPassives() { return passives; }
    public ArrayList<ProcSkill> getProcs() { return procs; }
    public ArrayList<RaceSlayer> getSlayers() { return raceslayers; }
    public ArrayList<Skill> getSkills() { return allskills; }
    public ArrayList<WeaponBreaker> getBreakers() { return breakers; }
    public ArrayList<Threshold> getThresholds() { return thresholds; }
}
