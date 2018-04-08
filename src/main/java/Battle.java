import java.util.ArrayList;

public class Battle {
    private String[] weapontypes = new String[]{"None", "Sword", "Lance", "Axe", "Anima", "Light", "Dark", "Bow"};
    private String[] stattypes = new String[]{"HP", "Strength", "Magic", "Skill", "Speed", "Luck", "Defense", "Resistance", "Hit", "Avoid", "Crit", "Physical damage", "Magical damage", "Damage taken"};

    public Battle() {

    }

    public String commenceBattle(Character initiator, Character defender) {
        String result = "";
        int[] istattemp = initiator.getStats();
        int[] dstattemp = defender.getStats();
        int ihit = CalculateHit(initiator, true); //calculate initial values
        int dhit = CalculateHit(defender, false);
        int istartinghp = initiator.getCurrHP();
        int dstartinghp = defender.getCurrHP();
        int idamage = CalculateDamage(initiator, true);
        int ddamage = CalculateDamage(defender, false);
        int iavoid = CalculateAvoid(initiator, true);
        int davoid = CalculateAvoid(defender, false);
        int icrit = CalculateCrit(initiator, true);
        int dcrit = CalculateCrit(defender, false);
        int idefense = CalculateDefenses(initiator, defender.getEquippedWeapon().isMagicTarget(), true);
        int ddefense = CalculateDefenses(defender, initiator.getEquippedWeapon().isMagicTarget(), false);
        boolean canretaliate = initiator.getEquippedWeapon().isCounterable();
        if (initiator.getEquippedWeapon().hasAdvantage(defender.getEquippedWeapon().getWeapontype()) == 2) { //calculate WTA
            ihit += 10;
            idamage += 2;
            dhit -= 10;
            ddamage -= 2;
        } else if (initiator.getEquippedWeapon().hasAdvantage(defender.getEquippedWeapon().getWeapontype()) == 0) {
            ihit -= 10;
            idamage -= 2;
            dhit += 10;
            ddamage += 2;
        }
        int i;
        if (isEffective(initiator, defender)) { //check for effectiveness
            idamage += initiator.getEquippedWeapon().getMight() * 2;
        }
        if (isEffective(defender, initiator)) {
            ddamage += defender.getEquippedWeapon().getMight() * 2;
        }
        for (i = 0; i < initiator.getSkilllist().getOffensives().size(); i++) { //check initiation skills and apply buffs
            istattemp[1] += initiator.getSkilllist().getOffensives().get(i).getBuff()[1];
            istattemp[2] += initiator.getSkilllist().getOffensives().get(i).getBuff()[2];
            istattemp[3] += initiator.getSkilllist().getOffensives().get(i).getBuff()[3];
            istattemp[4] += initiator.getSkilllist().getOffensives().get(i).getBuff()[4];
            istattemp[5] += initiator.getSkilllist().getOffensives().get(i).getBuff()[5];
            istattemp[6] += initiator.getSkilllist().getOffensives().get(i).getBuff()[6];
            istattemp[7] += initiator.getSkilllist().getOffensives().get(i).getBuff()[7];
            ihit += initiator.getSkilllist().getOffensives().get(i).getBuff()[8];
            iavoid += initiator.getSkilllist().getOffensives().get(i).getBuff()[9];
            icrit += initiator.getSkilllist().getOffensives().get(i).getBuff()[10];
            if (defender.getEquippedWeapon().isMagicTarget()) {
                ddamage -= initiator.getSkilllist().getOffensives().get(i).getBuff()[12];
            } else {
                ddamage -= initiator.getSkilllist().getOffensives().get(i).getBuff()[11];
            }
            ddamage -= initiator.getSkilllist().getOffensives().get(i).getBuff()[13];
            if (initiator.getEquippedWeapon().isMagicTarget()) {
                idamage += initiator.getSkilllist().getOffensives().get(i).getBuff()[15];
            } else {
                idamage += initiator.getSkilllist().getOffensives().get(i).getBuff()[14];
            }
            idamage += initiator.getSkilllist().getOffensives().get(i).getBuff()[16];
        }
        for (i = 0; i < initiator.getSkilllist().getThresholds().size(); i++) {
            if ((initiator.getSkilllist().getThresholds().get(i)).getThresholdAmount() * istattemp[0] > istattemp[1]) {
                istattemp[1] += initiator.getSkilllist().getThresholds().get(i).getBuff()[1];
                istattemp[2] += initiator.getSkilllist().getThresholds().get(i).getBuff()[2];
                istattemp[3] += initiator.getSkilllist().getThresholds().get(i).getBuff()[3];
                istattemp[4] += initiator.getSkilllist().getThresholds().get(i).getBuff()[4];
                istattemp[5] += initiator.getSkilllist().getThresholds().get(i).getBuff()[5];
                istattemp[6] += initiator.getSkilllist().getThresholds().get(i).getBuff()[6];
                istattemp[7] += initiator.getSkilllist().getThresholds().get(i).getBuff()[7];
                ihit += initiator.getSkilllist().getThresholds().get(i).getBuff()[8];
                iavoid += initiator.getSkilllist().getThresholds().get(i).getBuff()[9];
                icrit += initiator.getSkilllist().getThresholds().get(i).getBuff()[10];
                if (defender.getEquippedWeapon().isMagicTarget()) {
                    ddamage -= initiator.getSkilllist().getThresholds().get(i).getBuff()[12];
                } else {
                    ddamage -= initiator.getSkilllist().getThresholds().get(i).getBuff()[11];
                }
                ddamage -= initiator.getSkilllist().getThresholds().get(i).getBuff()[13];
                if (initiator.getEquippedWeapon().isMagicTarget()) {
                    idamage += initiator.getSkilllist().getOffensives().get(i).getBuff()[15];
                } else {
                    idamage += initiator.getSkilllist().getOffensives().get(i).getBuff()[14];
                }
                idamage += initiator.getSkilllist().getOffensives().get(i).getBuff()[16];
            }
        }
        for (i = 0; i < defender.getSkilllist().getDefensives().size(); i++) { //check defensive skills and apply buffs
            dstattemp[1] += defender.getSkilllist().getDefensives().get(i).getBuff()[1];
            dstattemp[2] += defender.getSkilllist().getDefensives().get(i).getBuff()[2];
            dstattemp[3] += defender.getSkilllist().getDefensives().get(i).getBuff()[3];
            dstattemp[4] += defender.getSkilllist().getDefensives().get(i).getBuff()[4];
            dstattemp[5] += defender.getSkilllist().getDefensives().get(i).getBuff()[5];
            dstattemp[6] += defender.getSkilllist().getDefensives().get(i).getBuff()[6];
            dstattemp[7] += defender.getSkilllist().getDefensives().get(i).getBuff()[7];
            dhit += defender.getSkilllist().getDefensives().get(i).getBuff()[8];
            davoid += defender.getSkilllist().getDefensives().get(i).getBuff()[9];
            dcrit += defender.getSkilllist().getDefensives().get(i).getBuff()[10];
            if (initiator.getEquippedWeapon().isMagicTarget()) {
                idamage -= defender.getSkilllist().getDefensives().get(i).getBuff()[12];
            } else {
                idamage -= defender.getSkilllist().getDefensives().get(i).getBuff()[11];
            }
            idamage -= defender.getSkilllist().getDefensives().get(i).getBuff()[13];
            if (defender.getEquippedWeapon().isMagicTarget()) {
                ddamage += defender.getSkilllist().getOffensives().get(i).getBuff()[15];
            } else {
                ddamage += defender.getSkilllist().getOffensives().get(i).getBuff()[14];
            }
            ddamage += defender.getSkilllist().getOffensives().get(i).getBuff()[16];
        }
        for (i = 0; i < defender.getSkilllist().getThresholds().size(); i++) {
            if ((defender.getSkilllist().getThresholds().get(i)).getThresholdAmount() * dstattemp[0] > dstattemp[1]) {
                dstattemp[1] += defender.getSkilllist().getThresholds().get(i).getBuff()[1];
                dstattemp[2] += defender.getSkilllist().getThresholds().get(i).getBuff()[2];
                dstattemp[3] += defender.getSkilllist().getThresholds().get(i).getBuff()[3];
                dstattemp[4] += defender.getSkilllist().getThresholds().get(i).getBuff()[4];
                dstattemp[5] += defender.getSkilllist().getThresholds().get(i).getBuff()[5];
                dstattemp[6] += defender.getSkilllist().getThresholds().get(i).getBuff()[6];
                dstattemp[7] += defender.getSkilllist().getThresholds().get(i).getBuff()[7];
                dhit += defender.getSkilllist().getThresholds().get(i).getBuff()[8];
                davoid += defender.getSkilllist().getThresholds().get(i).getBuff()[9];
                dcrit += defender.getSkilllist().getThresholds().get(i).getBuff()[10];
                if (initiator.getEquippedWeapon().isMagicTarget()) {
                    idamage -= defender.getSkilllist().getThresholds().get(i).getBuff()[12];
                } else {
                    idamage -= defender.getSkilllist().getThresholds().get(i).getBuff()[11];
                }
                idamage -= defender.getSkilllist().getThresholds().get(i).getBuff()[13];
                if (defender.getEquippedWeapon().isMagicTarget()) {
                    ddamage += defender.getSkilllist().getOffensives().get(i).getBuff()[15];
                } else {
                    ddamage += defender.getSkilllist().getOffensives().get(i).getBuff()[14];
                }
                ddamage += defender.getSkilllist().getOffensives().get(i).getBuff()[16];
            }
        }
        for (i = 0; i < initiator.getSkilllist().getBreakers().size(); i++) { //check breaker skills and apply buffs
            if (initiator.getSkilllist().getBreakers().get(i).getEffectiveWeapon() == defender.getEquippedWeapon().getWeapontype()) {
                ihit += initiator.getSkilllist().getBreakers().get(i).getBuff()[0];
                iavoid += initiator.getSkilllist().getBreakers().get(i).getBuff()[1];
                icrit += initiator.getSkilllist().getBreakers().get(i).getBuff()[2];
                idamage += initiator.getSkilllist().getBreakers().get(i).getBuff()[3];
                idamage += initiator.getEquippedWeapon().getMight() * initiator.getSkilllist().getBreakers().get(i).getBuff()[4];
                ddamage -= initiator.getSkilllist().getBreakers().get(i).getBuff()[5];
            }
        }
        for (i = 0; i < defender.getSkilllist().getBreakers().size(); i++) {
            if (defender.getSkilllist().getBreakers().get(i).getEffectiveWeapon() == initiator.getEquippedWeapon().getWeapontype()) {
                dhit += defender.getSkilllist().getBreakers().get(i).getBuff()[0];
                davoid += defender.getSkilllist().getBreakers().get(i).getBuff()[1];
                dcrit += defender.getSkilllist().getBreakers().get(i).getBuff()[2];
                ddamage += defender.getSkilllist().getBreakers().get(i).getBuff()[3];
                ddamage += defender.getEquippedWeapon().getMight() * defender.getSkilllist().getBreakers().get(i).getBuff()[4];
                idamage -= defender.getSkilllist().getBreakers().get(i).getBuff()[5];
            }
        }
        for (i = 0; i < initiator.getSkilllist().getSlayers().size(); i++) { //check anti race skills and apply buffs
            if (initiator.getSkilllist().getSlayers().get(i).getSlay().equals(defender.getRace())) {
                ihit += initiator.getSkilllist().getSlayers().get(i).getStatAdvantage()[0];
                iavoid += initiator.getSkilllist().getSlayers().get(i).getStatAdvantage()[1];
                icrit += initiator.getSkilllist().getSlayers().get(i).getStatAdvantage()[2];
                idamage += initiator.getSkilllist().getSlayers().get(i).getStatAdvantage()[3];
                idamage += initiator.getEquippedWeapon().getMight() * initiator.getSkilllist().getSlayers().get(i).getStatAdvantage()[4];
                ddamage -= initiator.getSkilllist().getSlayers().get(i).getStatAdvantage()[5];
            }
        }
        for (i = 0; i < defender.getSkilllist().getSlayers().size(); i++) {
            if (defender.getSkilllist().getSlayers().get(i).getSlay().equals(initiator.getRace())) {
                dhit += defender.getSkilllist().getSlayers().get(i).getStatAdvantage()[0];
                davoid += defender.getSkilllist().getSlayers().get(i).getStatAdvantage()[1];
                dcrit += defender.getSkilllist().getSlayers().get(i).getStatAdvantage()[2];
                ddamage += defender.getSkilllist().getSlayers().get(i).getStatAdvantage()[3];
                ddamage += defender.getEquippedWeapon().getMight() * defender.getSkilllist().getSlayers().get(i).getStatAdvantage()[4];
                idamage -= defender.getSkilllist().getSlayers().get(i).getStatAdvantage()[5];
            }
        }
        ArrayList<ProcSkill> initprocs = initiator.getSkilllist().getProcs();
        ArrayList<ProcSkill> defprocs = defender.getSkilllist().getProcs();
        int initattack = 1; //sets up initial variables
        int inithitcount = 1;
        double iattackmod = 1;
        int defattack = 1;
        int defhitcount = 1;
        double defattackmod = 1;
        boolean ibrave = false;
        boolean dbrave = false;
        int iheal = 0;
        int dheal = 0;
        boolean ialive = true;
        boolean dalive = true;
        boolean dvantage = false;
        if (!defender.isEquippingWeapon()) { //tests if defender has equipped weapon
            defattack = 0;
        } else if (defender.getEquippedWeapon().isBrave()) { //tests for brave effects
            dbrave = true;
        }
        ibrave = initiator.getEquippedWeapon().isBrave();
        int temp;
        int j;
        int damage;
        if (canDouble(initiator, defender) == 1) {
            inithitcount = 2;
        } else if (canDouble(initiator, defender) == 2) {
            defhitcount = 2;
        }
        for (i = 0; i < defender.getSkilllist().getPassives().size(); i++) {
            if (defender.getSkilllist().getPassives().get(i).isVantage()) {
                if (defender.getSkilllist().getPassives().get(i).getVantageThreshold() * defender.getStats()[0] > defender.getCurrHP()) {
                    dvantage = true;
                    result += defender.getName() + " activates " + defender.getSkilllist().getPassives().get(i).getName() + " and gets to attack first!\n";
                    break;
                }
            }
        }
        if (dvantage && canretaliate) {
            result += defender.getName() + " attacks " + initiator.getName() + " with " + defender.getEquippedWeapon().getName() + ".\n";
            for (i = 0; i < defender.getSkilllist().getProcs().size(); i++) { //tests for multiattack skills
                if (defender.getSkilllist().getProcs().get(i).isAttackMulti()) {
                    if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                        defattack *= ((MultiHitSkill) defender.getSkilllist().getProcs().get(i)).getAttackCount();
                        defattackmod = (defender.getSkilllist().getProcs().get(i)).getDamageMultiplier();
                        result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                        if (!((MultiHitSkill) defender.getSkilllist().getProcs().get(i)).getStack()) {
                            break;
                        }
                    }
                }
            }
            for (i = 0; i < defattack; i++) {
                damage = (int) (ddamage * defattackmod);
                dheal = 0;
                temp = (int) (Math.random() * 100);
                if (temp < dhit - iavoid) {
                    for (j = 0; j < defender.getSkilllist().getProcs().size(); j++) {
                        if (!defender.getSkilllist().getProcs().get(i).isAttackMulti()) {
                            if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                if (!defender.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                    if (!defender.getSkilllist().getProcs().get(i).isUseEnemyStat()) {
                                        if (defender.getSkilllist().getProcs().get(i).isDamaging()) {
                                            damage += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                        }
                                        if (defender.getSkilllist().getProcs().get(i).isHeal()) {
                                            dheal += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                        }
                                    } else {
                                        if (defender.getSkilllist().getProcs().get(i).isDamaging()) {
                                            damage += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                        }
                                        if (defender.getSkilllist().getProcs().get(i).isHeal()) {
                                            dheal += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                        }
                                    }
                                    if (defender.getSkilllist().getProcs().get(i).isCancel()) {
                                        inithitcount = 0;
                                    }
                                    result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                    break;
                                }
                            }
                        }
                    }
                    for (j = 0; j < initiator.getSkilllist().getProcs().size(); j++) {
                        if (initiator.getSkilllist().getProcs().get(i).isOnAttacked()) {
                            if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 8) {
                                    if (!defender.getEquippedWeapon().isUsesMagic()) {
                                        damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                        result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                } else if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 9) {
                                    if (defender.getEquippedWeapon().isUsesMagic()) {
                                        damage /= initiator.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                        result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                } else if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 10) {
                                    damage /= initiator.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                    result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                    break;
                                }
                            }
                        }
                    }
                    if (!defender.getEquippedWeapon().isIgnoreDefense()) {
                        damage -= idefense;
                        if (damage < 0) {
                            damage = 0;
                        }
                    }
                    if (Math.random() * 100 < dcrit) {
                        damage *= defender.getEquippedWeapon().getCritModifier();
                        result += "***CRITICAL!***\n";
                    }
                    result += defender.getName() + " hit for " + damage + " damage!\n";
                    for (j = 0; j < initiator.getSkilllist().getMiracles().size(); j++) {
                        if (damage > initiator.getCurrHP() && initiator.getSkilllist().getMiracles().get(j).testMiracle(istattemp[initiator.getSkilllist().getMiracles().get(j).getActivationstat()])) {
                            damage = initiator.getCurrHP() - 1;
                            result += initiator.getName() + " activated " + initiator.getSkilllist().getMiracles().get(j).getName() + " and survived with 1 HP!\n";
                            break;
                        }
                    }
                    if (initiator.damage(damage)) {
                        ialive = false;
                    }
                    if (dheal > 0) {
                        defender.heal(dheal);
                        result += defender.getStatus();
                    }
                    if (ialive = false) {
                        result += initiator.getName() + " was defeated!\n";
                        break;
                    }
                } else {
                    result += defender.getName() + " missed!\n";
                }
            }
            if (dbrave && ialive) {
                result += defender.getName() + " attacks again consecutively!\n";
                for (i = 0; i < defender.getSkilllist().getProcs().size(); i++) { //tests for multiattack skills
                    if (defender.getSkilllist().getProcs().get(i).isAttackMulti()) {
                        if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                            defattack *= ((MultiHitSkill) defender.getSkilllist().getProcs().get(i)).getAttackCount();
                            defattackmod = (defender.getSkilllist().getProcs().get(i)).getDamageMultiplier();
                            result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                            if (!((MultiHitSkill) defender.getSkilllist().getProcs().get(i)).getStack()) {
                                break;
                            }
                        }
                    }
                }
                for (i = 0; i < defattack; i++) {
                    damage = (int) (ddamage * defattackmod);
                    iheal = 0;
                    temp = (int) (Math.random() * 100);
                    if (temp < dhit - iavoid) {
                        for (j = 0; j < defender.getSkilllist().getProcs().size(); j++) {
                            if (!defender.getSkilllist().getProcs().get(i).isAttackMulti()) {
                                if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                    if (!defender.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                        if (!defender.getSkilllist().getProcs().get(i).isUseEnemyStat()) {
                                            if (defender.getSkilllist().getProcs().get(i).isDamaging()) {
                                                damage += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                            if (defender.getSkilllist().getProcs().get(i).isHeal()) {
                                                dheal += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                        } else {
                                            if (defender.getSkilllist().getProcs().get(i).isDamaging()) {
                                                damage += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                            if (defender.getSkilllist().getProcs().get(i).isHeal()) {
                                                dheal += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                        }
                                        if (defender.getSkilllist().getProcs().get(i).isCancel()) {
                                            inithitcount = 0;
                                        }
                                        result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                }
                            }
                        }
                        for (j = 0; j < initiator.getSkilllist().getProcs().size(); j++) {
                            if (initiator.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                    if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 8) {
                                        if (!defender.getEquippedWeapon().isUsesMagic()) {
                                            damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                            result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    } else if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 9) {
                                        if (defender.getEquippedWeapon().isUsesMagic()) {
                                            damage /= initiator.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                            result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    } else if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 10) {
                                        damage /= initiator.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                        result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                }
                            }
                        }
                        if (!defender.getEquippedWeapon().isIgnoreDefense()) {
                            damage -= idefense;
                            if (damage < 0) {
                                damage = 0;
                            }
                        }
                        if (Math.random() * 100 < dcrit) {
                            damage *= defender.getEquippedWeapon().getCritModifier();
                            result += "***CRITICAL!***\n";
                        }
                        result += defender.getName() + " hit for " + damage + " damage!\n";
                        for (j = 0; j < initiator.getSkilllist().getMiracles().size(); j++) {
                            if (damage > initiator.getCurrHP() && initiator.getSkilllist().getMiracles().get(j).testMiracle(istattemp[initiator.getSkilllist().getMiracles().get(j).getActivationstat()])) {
                                damage = initiator.getCurrHP() - 1;
                                result += initiator.getName() + " activated " + initiator.getSkilllist().getMiracles().get(j).getName() + " and survived with 1 HP!\n";
                                break;
                            }
                        }
                        if (initiator.damage(damage)) {
                            ialive = false;
                        }
                        if (dheal > 0) {
                            defender.heal(dheal);
                            result += defender.getStatus();
                        }
                        if (ialive = false) {
                            result += initiator.getName() + " was defeated!\n";
                            break;
                        }
                    } else {
                        result += defender.getName() + " missed!\n";
                    }
                }
            }
        }

        if (ialive && inithitcount > 0) {
            result += initiator.getName() + " attacks " + defender.getName() + " with " + initiator.getEquippedWeapon().getName() + ".\n";
            for (i = 0; i < initiator.getSkilllist().getProcs().size(); i++) { //tests for multiattack skills
                if (initiator.getSkilllist().getProcs().get(i).isAttackMulti()) {
                    if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                        initattack *= ((MultiHitSkill) initiator.getSkilllist().getProcs().get(i)).getAttackCount();
                        iattackmod = (initiator.getSkilllist().getProcs().get(i)).getDamageMultiplier();
                        result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                        if (!((MultiHitSkill) initiator.getSkilllist().getProcs().get(i)).getStack()) {
                            break;
                        }
                    }
                }
            }
            for (i = 0; i < initattack; i++) {
                damage = idamage *= iattackmod;
                iheal = 0;
                temp = (int) (Math.random() * 100);
                if (temp < ihit - davoid) {
                    for (j = 0; j < initiator.getSkilllist().getProcs().size(); j++) {
                        if (!initiator.getSkilllist().getProcs().get(i).isAttackMulti()) {
                            if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                if (!initiator.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                    if (!initiator.getSkilllist().getProcs().get(i).isUseEnemyStat()) {
                                        if (initiator.getSkilllist().getProcs().get(i).isDamaging()) {
                                            damage += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                        }
                                        if (initiator.getSkilllist().getProcs().get(i).isHeal()) {
                                            iheal += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                        }
                                    } else {
                                        if (initiator.getSkilllist().getProcs().get(i).isDamaging()) {
                                            damage += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                        }
                                        if (initiator.getSkilllist().getProcs().get(i).isHeal()) {
                                            iheal += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                        }
                                    }
                                    if (initiator.getSkilllist().getProcs().get(i).isCancel()) {
                                        defhitcount = 0;
                                    }
                                    result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                    break;
                                }
                            }
                        }
                    }
                    for (j = 0; j < defender.getSkilllist().getProcs().size(); j++) {
                        if (defender.getSkilllist().getProcs().get(i).isOnAttacked()) {
                            if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                if (defender.getSkilllist().getProcs().get(i).getInputStat() == 8) {
                                    if (!initiator.getEquippedWeapon().isUsesMagic()) {
                                        damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                        result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                } else if (defender.getSkilllist().getProcs().get(i).getInputStat() == 9) {
                                    if (initiator.getEquippedWeapon().isUsesMagic()) {
                                        damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                        result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                } else if (defender.getSkilllist().getProcs().get(i).getInputStat() == 10) {
                                    damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                    result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                    break;
                                }
                            }
                        }
                    }
                    if (!initiator.getEquippedWeapon().isIgnoreDefense()) {
                        damage -= ddefense;
                        if (damage < 0) {
                            damage = 0;
                        }
                    }
                    if (Math.random() * 100 < icrit) {
                        damage *= initiator.getEquippedWeapon().getCritModifier();
                        result += "***CRITICAL!***\n";
                    }
                    result += initiator.getName() + " hit for " + damage + " damage!\n";
                    for (j = 0; j < defender.getSkilllist().getMiracles().size(); j++) {
                        if (damage > defender.getCurrHP() && defender.getSkilllist().getMiracles().get(j).testMiracle(dstattemp[defender.getSkilllist().getMiracles().get(j).getActivationstat()])) {
                            damage = defender.getCurrHP() - 1;
                            result += defender.getName() + " activated " + defender.getSkilllist().getMiracles().get(j).getName() + " and survived with 1 HP!\n";
                            break;
                        }
                    }
                    if (defender.damage(damage)) {
                        dalive = false;
                    }
                    if (iheal > 0) {
                        initiator.heal(iheal);
                        result += initiator.getStatus();
                    }
                    if (dalive = false) {
                        result += defender.getName() + " was defeated!\n";
                        break;
                    }
                } else {
                    result += initiator.getName() + " missed!\n";
                }
            }
            if (ibrave && dalive) {
                result += initiator.getName() + " attacks again consecutively!";
                for (i = 0; i < initiator.getSkilllist().getProcs().size(); i++) { //tests for multiattack skills
                    if (initiator.getSkilllist().getProcs().get(i).isAttackMulti()) {
                        if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                            initattack *= ((MultiHitSkill) initiator.getSkilllist().getProcs().get(i)).getAttackCount();
                            iattackmod = (initiator.getSkilllist().getProcs().get(i)).getDamageMultiplier();
                            result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                            if (!((MultiHitSkill) initiator.getSkilllist().getProcs().get(i)).getStack()) {
                                break;
                            }
                        }
                    }
                }
                for (i = 0; i < initattack; i++) {
                    damage = idamage *= iattackmod;
                    iheal = 0;
                    temp = (int) (Math.random() * 100);
                    if (temp < ihit - davoid) {
                        for (j = 0; j < initiator.getSkilllist().getProcs().size(); j++) {
                            if (!initiator.getSkilllist().getProcs().get(i).isAttackMulti()) {
                                if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                    if (!initiator.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                        if (!initiator.getSkilllist().getProcs().get(i).isUseEnemyStat()) {
                                            if (initiator.getSkilllist().getProcs().get(i).isDamaging()) {
                                                damage += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                            if (initiator.getSkilllist().getProcs().get(i).isHeal()) {
                                                iheal += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                        } else {
                                            if (initiator.getSkilllist().getProcs().get(i).isDamaging()) {
                                                damage += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                            if (initiator.getSkilllist().getProcs().get(i).isHeal()) {
                                                iheal += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                        }
                                        if (initiator.getSkilllist().getProcs().get(i).isCancel()) {
                                            defhitcount = 0;
                                        }
                                        result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                }
                            }
                        }
                        for (j = 0; j < defender.getSkilllist().getProcs().size(); j++) {
                            if (defender.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                    if (defender.getSkilllist().getProcs().get(i).getInputStat() == 8) {
                                        if (!initiator.getEquippedWeapon().isUsesMagic()) {
                                            damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                            result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    } else if (defender.getSkilllist().getProcs().get(i).getInputStat() == 9) {
                                        if (initiator.getEquippedWeapon().isUsesMagic()) {
                                            damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                            result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    } else if (defender.getSkilllist().getProcs().get(i).getInputStat() == 10) {
                                        damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                        result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                }
                            }
                        }
                        if (!initiator.getEquippedWeapon().isIgnoreDefense()) {
                            damage -= ddefense;
                            if (damage < 0) {
                                damage = 0;
                            }
                        }
                        if (Math.random() * 100 < icrit) {
                            damage *= initiator.getEquippedWeapon().getCritModifier();
                            result += "***CRITICAL!***\n";
                        }
                        result += initiator.getName() + " hit for " + damage + " damage!\n";
                        for (j = 0; j < defender.getSkilllist().getMiracles().size(); j++) {
                            if (damage > defender.getCurrHP() && defender.getSkilllist().getMiracles().get(j).testMiracle(dstattemp[defender.getSkilllist().getMiracles().get(j).getActivationstat()])) {
                                damage = defender.getCurrHP() - 1;
                                result += defender.getName() + " activated " + defender.getSkilllist().getMiracles().get(j).getName() + " and survived with 1 HP!\n";
                                break;
                            }
                        }
                        if (defender.damage(damage)) {
                            dalive = false;
                        }
                        if (iheal > 0) {
                            initiator.heal(iheal);
                            result += initiator.getStatus();
                        }
                        if (dalive = false) {
                            result += defender.getName() + " was defeated!\n";
                            break;
                        }
                    } else {
                        result += initiator.getName() + " missed!\n";
                    }
                }
            }
            inithitcount--;
        }
        if ((inithitcount > 0 && defhitcount < 1) && canretaliate) {
            if (ialive && dalive) {
                for (i = 0; i < initiator.getSkilllist().getProcs().size(); i++) { //tests for multiattack skills
                    if (initiator.getSkilllist().getProcs().get(i).isAttackMulti()) {
                        if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                            initattack *= ((MultiHitSkill) initiator.getSkilllist().getProcs().get(i)).getAttackCount();
                            iattackmod = (initiator.getSkilllist().getProcs().get(i)).getDamageMultiplier();
                            result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                            if (!((MultiHitSkill) initiator.getSkilllist().getProcs().get(i)).getStack()) {
                                break;
                            }
                        }
                    }
                }
                for (i = 0; i < initattack; i++) {
                    damage = idamage *= iattackmod;
                    iheal = 0;
                    temp = (int) (Math.random() * 100);
                    if (temp < ihit - davoid) {
                        for (j = 0; j < initiator.getSkilllist().getProcs().size(); j++) {
                            if (!initiator.getSkilllist().getProcs().get(i).isAttackMulti()) {
                                if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                    if (!initiator.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                        if (!initiator.getSkilllist().getProcs().get(i).isUseEnemyStat()) {
                                            if (initiator.getSkilllist().getProcs().get(i).isDamaging()) {
                                                damage += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                            if (initiator.getSkilllist().getProcs().get(i).isHeal()) {
                                                iheal += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                        } else {
                                            if (initiator.getSkilllist().getProcs().get(i).isDamaging()) {
                                                damage += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                            if (initiator.getSkilllist().getProcs().get(i).isHeal()) {
                                                iheal += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                        }
                                        if (initiator.getSkilllist().getProcs().get(i).isCancel()) {
                                            defhitcount = 0;
                                        }
                                        result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                }
                            }
                        }
                        for (j = 0; j < defender.getSkilllist().getProcs().size(); j++) {
                            if (defender.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                    if (defender.getSkilllist().getProcs().get(i).getInputStat() == 8) {
                                        if (!initiator.getEquippedWeapon().isUsesMagic()) {
                                            damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                            result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    } else if (defender.getSkilllist().getProcs().get(i).getInputStat() == 9) {
                                        if (initiator.getEquippedWeapon().isUsesMagic()) {
                                            damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                            result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    } else if (defender.getSkilllist().getProcs().get(i).getInputStat() == 10) {
                                        damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                        result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                }
                            }
                        }
                        if (!initiator.getEquippedWeapon().isIgnoreDefense()) {
                            damage -= ddefense;
                            if (damage < 0) {
                                damage = 0;
                            }
                        }
                        if (Math.random() * 100 < icrit) {
                            damage *= initiator.getEquippedWeapon().getCritModifier();
                            result += "***CRITICAL!***\n";
                        }
                        result += initiator.getName() + " hit for " + damage + " damage!\n";
                        for (j = 0; j < defender.getSkilllist().getMiracles().size(); j++) {
                            if (damage > defender.getCurrHP() && defender.getSkilllist().getMiracles().get(j).testMiracle(dstattemp[defender.getSkilllist().getMiracles().get(j).getActivationstat()])) {
                                damage = defender.getCurrHP() - 1;
                                result += defender.getName() + " activated " + defender.getSkilllist().getMiracles().get(j).getName() + " and survived with 1 HP!\n";
                                break;
                            }
                        }
                        if (defender.damage(damage)) {
                            dalive = false;
                        }
                        if (iheal > 0) {
                            initiator.heal(iheal);
                            result += initiator.getStatus();
                        }
                        if (dalive = false) {
                            result += defender.getName() + " was defeated!\n";
                            break;
                        }
                    } else {
                        result += initiator.getName() + " missed!\n";
                    }
                }
                if (ibrave && dalive) {
                    result += initiator.getName() + " attacks again consecutively!\n";
                    for (i = 0; i < initiator.getSkilllist().getProcs().size(); i++) { //tests for multiattack skills
                        if (initiator.getSkilllist().getProcs().get(i).isAttackMulti()) {
                            if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                initattack *= ((MultiHitSkill) initiator.getSkilllist().getProcs().get(i)).getAttackCount();
                                iattackmod = (initiator.getSkilllist().getProcs().get(i)).getDamageMultiplier();
                                result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                if (!((MultiHitSkill) initiator.getSkilllist().getProcs().get(i)).getStack()) {
                                    break;
                                }
                            }
                        }
                    }
                    for (i = 0; i < initattack; i++) {
                        damage = idamage *= iattackmod;
                        iheal = 0;
                        temp = (int) (Math.random() * 100);
                        if (temp < ihit - davoid) {
                            for (j = 0; j < initiator.getSkilllist().getProcs().size(); j++) {
                                if (!initiator.getSkilllist().getProcs().get(i).isAttackMulti()) {
                                    if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                        if (!initiator.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                            if (!initiator.getSkilllist().getProcs().get(i).isUseEnemyStat()) {
                                                if (initiator.getSkilllist().getProcs().get(i).isDamaging()) {
                                                    damage += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                                }
                                                if (initiator.getSkilllist().getProcs().get(i).isHeal()) {
                                                    iheal += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                                }
                                            } else {
                                                if (initiator.getSkilllist().getProcs().get(i).isDamaging()) {
                                                    damage += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                                }
                                                if (initiator.getSkilllist().getProcs().get(i).isHeal()) {
                                                    iheal += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                                }
                                            }
                                            if (initiator.getSkilllist().getProcs().get(i).isCancel()) {
                                                defhitcount = 0;
                                            }
                                            result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    }
                                }
                            }
                            for (j = 0; j < defender.getSkilllist().getProcs().size(); j++) {
                                if (defender.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                    if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                        if (defender.getSkilllist().getProcs().get(i).getInputStat() == 8) {
                                            if (!initiator.getEquippedWeapon().isUsesMagic()) {
                                                damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                                result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                break;
                                            }
                                        } else if (defender.getSkilllist().getProcs().get(i).getInputStat() == 9) {
                                            if (initiator.getEquippedWeapon().isUsesMagic()) {
                                                damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                                result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                break;
                                            }
                                        } else if (defender.getSkilllist().getProcs().get(i).getInputStat() == 10) {
                                            damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                            result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!initiator.getEquippedWeapon().isIgnoreDefense()) {
                                damage -= ddefense;
                                if (damage < 0) {
                                    damage = 0;
                                }
                            }
                            if (Math.random() * 100 < icrit) {
                                damage *= initiator.getEquippedWeapon().getCritModifier();
                                result += "***CRITICAL!***\n";
                            }
                            result += initiator.getName() + " hit for " + damage + " damage!\n";
                            for (j = 0; j < defender.getSkilllist().getMiracles().size(); j++) {
                                if (damage > defender.getCurrHP() && defender.getSkilllist().getMiracles().get(j).testMiracle(dstattemp[defender.getSkilllist().getMiracles().get(j).getActivationstat()])) {
                                    damage = defender.getCurrHP() - 1;
                                    result += defender.getName() + " activated " + defender.getSkilllist().getMiracles().get(j).getName() + " and survived with 1 HP!\n";
                                    break;
                                }
                            }
                            if (defender.damage(damage)) {
                                dalive = false;
                            }
                            if (iheal > 0) {
                                initiator.heal(iheal);
                                result += initiator.getStatus();
                            }
                            if (dalive = false) {
                                result += defender.getName() + " was defeated!\n";
                                break;
                            }
                        } else {
                            result += initiator.getName() + " missed!\n";
                        }
                    }
                }
            }
        } else if (defhitcount > 0 && dalive && ialive && canretaliate) {
            result += defender.getName() + " attacks " + initiator.getName() + " with " + defender.getEquippedWeapon().getName() + ".\n";
            for (i = 0; i < defender.getSkilllist().getProcs().size(); i++) { //tests for multiattack skills
                if (defender.getSkilllist().getProcs().get(i).isAttackMulti()) {
                    if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                        defattack *= ((MultiHitSkill) defender.getSkilllist().getProcs().get(i)).getAttackCount();
                        defattackmod = (defender.getSkilllist().getProcs().get(i)).getDamageMultiplier();
                        result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                        if (!((MultiHitSkill) defender.getSkilllist().getProcs().get(i)).getStack()) {
                            break;
                        }
                    }
                }
            }
            for (i = 0; i < defattack; i++) {
                damage = (int) (ddamage * defattackmod);
                dheal = 0;
                temp = (int) (Math.random() * 100);
                if (temp < dhit - iavoid) {
                    for (j = 0; j < defender.getSkilllist().getProcs().size(); j++) {
                        if (!defender.getSkilllist().getProcs().get(i).isAttackMulti()) {
                            if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                if (!defender.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                    if (!defender.getSkilllist().getProcs().get(i).isUseEnemyStat()) {
                                        if (defender.getSkilllist().getProcs().get(i).isDamaging()) {
                                            damage += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                        }
                                        if (defender.getSkilllist().getProcs().get(i).isHeal()) {
                                            dheal += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                        }
                                    } else {
                                        if (defender.getSkilllist().getProcs().get(i).isDamaging()) {
                                            damage += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                        }
                                        if (defender.getSkilllist().getProcs().get(i).isHeal()) {
                                            dheal += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                        }
                                    }
                                    if (defender.getSkilllist().getProcs().get(i).isCancel()) {
                                        inithitcount = 0;
                                    }
                                    result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                    break;
                                }
                            }
                        }
                    }
                    for (j = 0; j < initiator.getSkilllist().getProcs().size(); j++) {
                        if (initiator.getSkilllist().getProcs().get(i).isOnAttacked()) {
                            if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 8) {
                                    if (!defender.getEquippedWeapon().isUsesMagic()) {
                                        damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                        result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                } else if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 9) {
                                    if (defender.getEquippedWeapon().isUsesMagic()) {
                                        damage /= initiator.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                        result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                } else if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 10) {
                                    damage /= initiator.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                    result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                    break;
                                }
                            }
                        }
                    }
                    if (!defender.getEquippedWeapon().isIgnoreDefense()) {
                        damage -= idefense;
                        if (damage < 0) {
                            damage = 0;
                        }
                    }
                    if (Math.random() * 100 < dcrit) {
                        damage *= defender.getEquippedWeapon().getCritModifier();
                        result += "***CRITICAL!***\n";
                    }
                    result += defender.getName() + " hit for " + damage + " damage!\n";
                    for (j = 0; j < initiator.getSkilllist().getMiracles().size(); j++) {
                        if (damage > initiator.getCurrHP() && initiator.getSkilllist().getMiracles().get(j).testMiracle(istattemp[initiator.getSkilllist().getMiracles().get(j).getActivationstat()])) {
                            damage = initiator.getCurrHP() - 1;
                            result += initiator.getName() + " activated " + initiator.getSkilllist().getMiracles().get(j).getName() + " and survived with 1 HP!\n";
                            break;
                        }
                    }
                    if (initiator.damage(damage)) {
                        ialive = false;
                    }
                    if (dheal > 0) {
                        defender.heal(dheal);
                        result += defender.getStatus();
                    }
                    if (ialive = false) {
                        result += initiator.getName() + " was defeated!\n";
                        break;
                    }
                } else {
                    result += defender.getName() + " missed!\n";
                }
            }
            if (dbrave && ialive) {
                result += defender.getName() + " attacks again consecutively!\n";
                for (i = 0; i < defender.getSkilllist().getProcs().size(); i++) { //tests for multiattack skills
                    if (defender.getSkilllist().getProcs().get(i).isAttackMulti()) {
                        if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                            defattack *= ((MultiHitSkill) defender.getSkilllist().getProcs().get(i)).getAttackCount();
                            defattackmod = (defender.getSkilllist().getProcs().get(i)).getDamageMultiplier();
                            result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                            if (!((MultiHitSkill) defender.getSkilllist().getProcs().get(i)).getStack()) {
                                break;
                            }
                        }
                    }
                }
                for (i = 0; i < defattack; i++) {
                    damage = (int) (ddamage * defattackmod);
                    dheal = 0;
                    temp = (int) (Math.random() * 100);
                    if (temp < dhit - iavoid) {
                        for (j = 0; j < defender.getSkilllist().getProcs().size(); j++) {
                            if (!defender.getSkilllist().getProcs().get(i).isAttackMulti()) {
                                if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                    if (!defender.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                        if (!defender.getSkilllist().getProcs().get(i).isUseEnemyStat()) {
                                            if (defender.getSkilllist().getProcs().get(i).isDamaging()) {
                                                damage += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                            if (defender.getSkilllist().getProcs().get(i).isHeal()) {
                                                dheal += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                        } else {
                                            if (defender.getSkilllist().getProcs().get(i).isDamaging()) {
                                                damage += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                            if (defender.getSkilllist().getProcs().get(i).isHeal()) {
                                                dheal += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                            }
                                        }
                                        if (defender.getSkilllist().getProcs().get(i).isCancel()) {
                                            inithitcount = 0;
                                        }
                                        result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                }
                            }
                        }
                        for (j = 0; j < initiator.getSkilllist().getProcs().size(); j++) {
                            if (initiator.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                    if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 8) {
                                        if (!defender.getEquippedWeapon().isUsesMagic()) {
                                            damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                            result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    } else if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 9) {
                                        if (defender.getEquippedWeapon().isUsesMagic()) {
                                            damage /= initiator.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                            result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    } else if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 10) {
                                        damage /= initiator.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                        result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                        break;
                                    }
                                }
                            }
                        }
                        if (!defender.getEquippedWeapon().isIgnoreDefense()) {
                            damage -= idefense;
                            if (damage < 0) {
                                damage = 0;
                            }
                        }
                        if (Math.random() * 100 < dcrit) {
                            damage *= defender.getEquippedWeapon().getCritModifier();
                            result += "***CRITICAL!***\n";
                        }
                        result += defender.getName() + " hit for " + damage + " damage!\n";
                        for (j = 0; j < initiator.getSkilllist().getMiracles().size(); j++) {
                            if (damage > initiator.getCurrHP() && initiator.getSkilllist().getMiracles().get(j).testMiracle(istattemp[initiator.getSkilllist().getMiracles().get(j).getActivationstat()])) {
                                damage = initiator.getCurrHP() - 1;
                                result += initiator.getName() + " activated " + initiator.getSkilllist().getMiracles().get(j).getName() + " and survived with 1 HP!\n";
                                break;
                            }
                        }
                        if (initiator.damage(damage)) {
                            ialive = false;
                        }
                        if (dheal > 0) {
                            defender.heal(dheal);
                            result += defender.getStatus();
                        }
                        if (ialive = false) {
                            result += initiator.getName() + " was defeated!\n";
                            break;
                        }
                    } else {
                        result += defender.getName() + " missed!\n";
                    }
                }
            }
            if (defhitcount > 0 && inithitcount < 1 && canretaliate && ialive && dalive) {
                    result += defender.getName() + " attacks " + initiator.getName() + " with " + defender.getEquippedWeapon().getName() + ".\n";
                    for (i = 0; i < defender.getSkilllist().getProcs().size(); i++) { //tests for multiattack skills
                        if (defender.getSkilllist().getProcs().get(i).isAttackMulti()) {
                            if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                defattack *= ((MultiHitSkill) defender.getSkilllist().getProcs().get(i)).getAttackCount();
                                defattackmod = (defender.getSkilllist().getProcs().get(i)).getDamageMultiplier();
                                result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                if (!((MultiHitSkill) defender.getSkilllist().getProcs().get(i)).getStack()) {
                                    break;
                                }
                            }
                        }
                    }
                    for (i = 0; i < defattack; i++) {
                        damage = (int) (ddamage * defattackmod);
                        dheal = 0;
                        temp = (int) (Math.random() * 100);
                        if (temp < dhit - iavoid) {
                            for (j = 0; j < defender.getSkilllist().getProcs().size(); j++) {
                                if (!defender.getSkilllist().getProcs().get(i).isAttackMulti()) {
                                    if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                        if (!defender.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                            if (!defender.getSkilllist().getProcs().get(i).isUseEnemyStat()) {
                                                if (defender.getSkilllist().getProcs().get(i).isDamaging()) {
                                                    damage += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                                }
                                                if (defender.getSkilllist().getProcs().get(i).isHeal()) {
                                                    dheal += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                                }
                                            } else {
                                                if (defender.getSkilllist().getProcs().get(i).isDamaging()) {
                                                    damage += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                                }
                                                if (defender.getSkilllist().getProcs().get(i).isHeal()) {
                                                    dheal += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                                }
                                            }
                                            if (defender.getSkilllist().getProcs().get(i).isCancel()) {
                                                inithitcount = 0;
                                            }
                                            result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    }
                                }
                            }
                            for (j = 0; j < initiator.getSkilllist().getProcs().size(); j++) {
                                if (initiator.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                    if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                        if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 8) {
                                            if (!defender.getEquippedWeapon().isUsesMagic()) {
                                                damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                                result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                break;
                                            }
                                        } else if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 9) {
                                            if (defender.getEquippedWeapon().isUsesMagic()) {
                                                damage /= initiator.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                                result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                break;
                                            }
                                        } else if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 10) {
                                            damage /= initiator.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                            result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!defender.getEquippedWeapon().isIgnoreDefense()) {
                                damage -= idefense;
                                if (damage < 0) {
                                    damage = 0;
                                }
                            }
                            if (Math.random() * 100 < dcrit) {
                                damage *= defender.getEquippedWeapon().getCritModifier();
                                result += "***CRITICAL!***\n";
                            }
                            result += defender.getName() + " hit for " + damage + " damage!\n";
                            for (j = 0; j < initiator.getSkilllist().getMiracles().size(); j++) {
                                if (damage > initiator.getCurrHP() && initiator.getSkilllist().getMiracles().get(j).testMiracle(istattemp[initiator.getSkilllist().getMiracles().get(j).getActivationstat()])) {
                                    damage = initiator.getCurrHP() - 1;
                                    result += initiator.getName() + " activated " + initiator.getSkilllist().getMiracles().get(j).getName() + " and survived with 1 HP!\n";
                                    break;
                                }
                            }
                            if (initiator.damage(damage)) {
                                ialive = false;
                            }
                            if (dheal > 0) {
                                defender.heal(iheal);
                                result += defender.getStatus();
                            }
                            if (ialive = false) {
                                result += initiator.getName() + " was defeated!\n";
                                break;
                            }
                        } else {
                            result += defender.getName() + " missed!\n";
                        }
                    }
                    if (dbrave && ialive) {
                        result += defender.getName() + " attacks again consecutively!\n";
                        for (i = 0; i < defender.getSkilllist().getProcs().size(); i++) { //tests for multiattack skills
                            if (defender.getSkilllist().getProcs().get(i).isAttackMulti()) {
                                if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                    defattack *= ((MultiHitSkill) defender.getSkilllist().getProcs().get(i)).getAttackCount();
                                    defattackmod = (defender.getSkilllist().getProcs().get(i)).getDamageMultiplier();
                                    result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                    if (!((MultiHitSkill) defender.getSkilllist().getProcs().get(i)).getStack()) {
                                        break;
                                    }
                                }
                            }
                        }
                        for (i = 0; i < defattack; i++) {
                            damage = (int) (ddamage * defattackmod);
                            dheal = 0;
                            temp = (int) (Math.random() * 100);
                            if (temp < dhit - iavoid) {
                                for (j = 0; j < defender.getSkilllist().getProcs().size(); j++) {
                                    if (!defender.getSkilllist().getProcs().get(i).isAttackMulti()) {
                                        if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                            if (!defender.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                                if (!defender.getSkilllist().getProcs().get(i).isUseEnemyStat()) {
                                                    if (defender.getSkilllist().getProcs().get(i).isDamaging()) {
                                                        damage += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                                    }
                                                    if (defender.getSkilllist().getProcs().get(i).isHeal()) {
                                                        dheal += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                                    }
                                                } else {
                                                    if (defender.getSkilllist().getProcs().get(i).isDamaging()) {
                                                        damage += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                                    }
                                                    if (defender.getSkilllist().getProcs().get(i).isHeal()) {
                                                        dheal += defender.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[defender.getSkilllist().getProcs().get(i).getInputStat()];
                                                    }
                                                }
                                                if (defender.getSkilllist().getProcs().get(i).isCancel()) {
                                                    inithitcount = 0;
                                                }
                                                result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                break;
                                            }
                                        }
                                    }
                                }
                                for (j = 0; j < initiator.getSkilllist().getProcs().size(); j++) {
                                    if (initiator.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                        if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                            if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 8) {
                                                if (!defender.getEquippedWeapon().isUsesMagic()) {
                                                    damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                                    result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                    break;
                                                }
                                            } else if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 9) {
                                                if (defender.getEquippedWeapon().isUsesMagic()) {
                                                    damage /= initiator.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                                    result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                    break;
                                                }
                                            } else if (initiator.getSkilllist().getProcs().get(i).getInputStat() == 10) {
                                                damage /= initiator.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                                result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (!defender.getEquippedWeapon().isIgnoreDefense()) {
                                    damage -= idefense;
                                    if (damage < 0) {
                                        damage = 0;
                                    }
                                }
                                if (Math.random() * 100 < dcrit) {
                                    damage *= defender.getEquippedWeapon().getCritModifier();
                                    result += "***CRITICAL!***\n";
                                }
                                result += defender.getName() + " hit for " + damage + " damage!\n";
                                for (j = 0; j < initiator.getSkilllist().getMiracles().size(); j++) {
                                    if (damage > initiator.getCurrHP() && initiator.getSkilllist().getMiracles().get(j).testMiracle(istattemp[initiator.getSkilllist().getMiracles().get(j).getActivationstat()])) {
                                        damage = initiator.getCurrHP() - 1;
                                        result += initiator.getName() + " activated " + initiator.getSkilllist().getMiracles().get(j).getName() + " and survived with 1 HP!\n";
                                        break;
                                    }
                                }
                                if (initiator.damage(damage)) {
                                    ialive = false;
                                }
                                if (dheal > 0) {
                                    defender.heal(iheal);
                                    result += defender.getStatus();
                                }
                                if (ialive = false) {
                                    result += initiator.getName() + " was defeated!\n";
                                    break;
                                }
                            } else {
                                result += defender.getName() + " missed!\n";
                            }
                        }
                    }
            } else if (inithitcount > 1 && ialive) {
                if (dalive && ialive) {
                    for (i = 0; i < initiator.getSkilllist().getProcs().size(); i++) { //tests for multiattack skills
                        if (initiator.getSkilllist().getProcs().get(i).isAttackMulti()) {
                            if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                initattack *= ((MultiHitSkill) initiator.getSkilllist().getProcs().get(i)).getAttackCount();
                                iattackmod = (initiator.getSkilllist().getProcs().get(i)).getDamageMultiplier();
                                result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                if (!((MultiHitSkill) initiator.getSkilllist().getProcs().get(i)).getStack()) {
                                    break;
                                }
                            }
                        }
                    }
                    for (i = 0; i < initattack; i++) {
                        damage = idamage *= iattackmod;
                        iheal = 0;
                        temp = (int) (Math.random() * 100);
                        if (temp < ihit - davoid) {
                            for (j = 0; j < initiator.getSkilllist().getProcs().size(); j++) {
                                if (!initiator.getSkilllist().getProcs().get(i).isAttackMulti()) {
                                    if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                        if (!initiator.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                            if (!initiator.getSkilllist().getProcs().get(i).isUseEnemyStat()) {
                                                if (initiator.getSkilllist().getProcs().get(i).isDamaging()) {
                                                    damage += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                                }
                                                if (initiator.getSkilllist().getProcs().get(i).isHeal()) {
                                                    iheal += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                                }
                                            } else {
                                                if (initiator.getSkilllist().getProcs().get(i).isDamaging()) {
                                                    damage += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                                }
                                                if (initiator.getSkilllist().getProcs().get(i).isHeal()) {
                                                    iheal += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                                }
                                            }
                                            if (initiator.getSkilllist().getProcs().get(i).isCancel()) {
                                                defhitcount = 0;
                                            }
                                            result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    }
                                }
                            }
                            for (j = 0; j < defender.getSkilllist().getProcs().size(); j++) {
                                if (defender.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                    if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                        if (defender.getSkilllist().getProcs().get(i).getInputStat() == 8) {
                                            if (!initiator.getEquippedWeapon().isUsesMagic()) {
                                                damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                                result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                break;
                                            }
                                        } else if (defender.getSkilllist().getProcs().get(i).getInputStat() == 9) {
                                            if (initiator.getEquippedWeapon().isUsesMagic()) {
                                                damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                                result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                break;
                                            }
                                        } else if (defender.getSkilllist().getProcs().get(i).getInputStat() == 10) {
                                            damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                            result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!initiator.getEquippedWeapon().isIgnoreDefense()) {
                                damage -= ddefense;
                                if (damage < 0) {
                                    damage = 0;
                                }
                            }
                            if (Math.random() * 100 < icrit) {
                                damage *= initiator.getEquippedWeapon().getCritModifier();
                                result += "***CRITICAL!***\n";
                            }
                            result += initiator.getName() + " hit for " + damage + " damage!\n";
                            for (j = 0; j < defender.getSkilllist().getMiracles().size(); j++) {
                                if (damage > defender.getCurrHP() && defender.getSkilllist().getMiracles().get(j).testMiracle(dstattemp[defender.getSkilllist().getMiracles().get(j).getActivationstat()])) {
                                    damage = defender.getCurrHP() - 1;
                                    result += defender.getName() + " activated " + defender.getSkilllist().getMiracles().get(j).getName() + " and survived with 1 HP!\n";
                                    break;
                                }
                            }
                            if (defender.damage(damage)) {
                                dalive = false;
                            }
                            if (iheal > 0) {
                                initiator.heal(iheal);
                                result += initiator.getStatus();
                            }
                            if (dalive = false) {
                                result += defender.getName() + " was defeated!\n";
                                break;
                            }
                        } else {
                            result += initiator.getName() + " missed!\n";
                        }
                    }
                    if (ibrave && dalive) {
                        result += initiator.getName() + " attacks again consecutively!\n";
                        for (i = 0; i < initiator.getSkilllist().getProcs().size(); i++) { //tests for multiattack skills
                            if (initiator.getSkilllist().getProcs().get(i).isAttackMulti()) {
                                if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                    initattack *= ((MultiHitSkill) initiator.getSkilllist().getProcs().get(i)).getAttackCount();
                                    iattackmod = (initiator.getSkilllist().getProcs().get(i)).getDamageMultiplier();
                                    result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                    if (!((MultiHitSkill) initiator.getSkilllist().getProcs().get(i)).getStack()) {
                                        break;
                                    }
                                }
                            }
                        }
                        for (i = 0; i < initattack; i++) {
                            damage = idamage *= iattackmod;
                            iheal = 0;
                            temp = (int) (Math.random() * 100);
                            if (temp < ihit - davoid) {
                                for (j = 0; j < initiator.getSkilllist().getProcs().size(); j++) {
                                    if (!initiator.getSkilllist().getProcs().get(i).isAttackMulti()) {
                                        if (initiator.getSkilllist().getProcs().get(i).activate(istattemp[initiator.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                            if (!initiator.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                                if (!initiator.getSkilllist().getProcs().get(i).isUseEnemyStat()) {
                                                    if (initiator.getSkilllist().getProcs().get(i).isDamaging()) {
                                                        damage += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                                    }
                                                    if (initiator.getSkilllist().getProcs().get(i).isHeal()) {
                                                        iheal += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * istattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                                    }
                                                } else {
                                                    if (initiator.getSkilllist().getProcs().get(i).isDamaging()) {
                                                        damage += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                                    }
                                                    if (initiator.getSkilllist().getProcs().get(i).isHeal()) {
                                                        iheal += initiator.getSkilllist().getProcs().get(i).getDamageMultiplier() * dstattemp[initiator.getSkilllist().getProcs().get(i).getInputStat()];
                                                    }
                                                }
                                                if (initiator.getSkilllist().getProcs().get(i).isCancel()) {
                                                    defhitcount = 0;
                                                }
                                                result += initiator.getName() + " activates " + initiator.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                break;
                                            }
                                        }
                                    }
                                }
                                for (j = 0; j < defender.getSkilllist().getProcs().size(); j++) {
                                    if (defender.getSkilllist().getProcs().get(i).isOnAttacked()) {
                                        if (defender.getSkilllist().getProcs().get(i).activate(dstattemp[defender.getSkilllist().getProcs().get(i).getActivationstat()])) {
                                            if (defender.getSkilllist().getProcs().get(i).getInputStat() == 8) {
                                                if (!initiator.getEquippedWeapon().isUsesMagic()) {
                                                    damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                                    result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                    break;
                                                }
                                            } else if (defender.getSkilllist().getProcs().get(i).getInputStat() == 9) {
                                                if (initiator.getEquippedWeapon().isUsesMagic()) {
                                                    damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                                    result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                    break;
                                                }
                                            } else if (defender.getSkilllist().getProcs().get(i).getInputStat() == 10) {
                                                damage /= defender.getSkilllist().getProcs().get(i).getDamageMultiplier();
                                                result += defender.getName() + " activates " + defender.getSkilllist().getProcs().get(i).getName() + "!\n";
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (!initiator.getEquippedWeapon().isIgnoreDefense()) {
                                    damage -= ddefense;
                                    if (damage < 0) {
                                        damage = 0;
                                    }
                                }
                                if (Math.random() * 100 < icrit) {
                                    damage *= initiator.getEquippedWeapon().getCritModifier();
                                    result += "***CRITICAL!***\n";
                                }
                                result += initiator.getName() + " hit for " + damage + " damage!\n";
                                for (j = 0; j < defender.getSkilllist().getMiracles().size(); j++) {
                                    if (damage > defender.getCurrHP() && defender.getSkilllist().getMiracles().get(j).testMiracle(dstattemp[defender.getSkilllist().getMiracles().get(j).getActivationstat()])) {
                                        damage = defender.getCurrHP() - 1;
                                        result += defender.getName() + " activated " + defender.getSkilllist().getMiracles().get(j).getName() + " and survived with 1 HP!\n";
                                        break;
                                    }
                                }
                                if (defender.damage(damage)) {
                                    dalive = false;
                                }
                                if (iheal > 0) {
                                    initiator.heal(iheal);
                                    result += initiator.getStatus();
                                }
                                if (dalive = false) {
                                    result += defender.getName() + " was defeated!\n";
                                    break;
                                }
                            } else {
                                result += initiator.getName() + " missed!\n";
                            }
                        }
                    }
                }
            }
        }
        if (ialive) {
            if (dalive) {
                if (!initiator.isNPC()) {
                    initiator.gainExperience(calculateExperience(initiator, defender, false, dstartinghp - defender.getCurrHP()));
                    result += initiator.getStatus();
                }
            } else {
                if (!initiator.isNPC()) {
                    initiator.gainExperience(calculateExperience(initiator, defender, true, dstartinghp - defender.getCurrHP()));
                    result += initiator.getStatus();
                }
            }
        }
        if (dalive) {
            if (ialive) {
                if (!defender.isNPC()) {
                    defender.gainExperience(calculateExperience(defender, initiator, false, istartinghp - initiator.getCurrHP()));
                    result += defender.getStatus();
                }
            } else {
                if (!defender.isNPC()) {
                    defender.gainExperience(calculateExperience(defender, initiator, true, istartinghp - initiator.getCurrHP()));
                    result += defender.getStatus();
                }
            }
        }
        return result;
    }

    public String battleForecast(Character initiator, Character defender) { // returns a string containing the battle forecast
        String result = ""; //result string
        int[] istattemp = initiator.getStats();
        int[] dstattemp = defender.getStats();
        int ihit = CalculateHit(initiator, true); //calculate initial values
        int dhit = CalculateHit(defender, false);
        int idamage = CalculateDamage(initiator, true);
        int ddamage = CalculateDamage(defender, false);
        int iavoid = CalculateAvoid(initiator, true);
        int davoid = CalculateAvoid(defender, false);
        int icrit = CalculateCrit(initiator, true);
        int dcrit = CalculateCrit(defender, false);
        int idefense = CalculateDefenses(initiator, defender.getEquippedWeapon().isMagicTarget(), true);
        int ddefense = CalculateDefenses(defender, initiator.getEquippedWeapon().isMagicTarget(), false);
        boolean canretaliate = initiator.getEquippedWeapon().isCounterable();
        if (initiator.getEquippedWeapon().hasAdvantage(defender.getEquippedWeapon().getWeapontype()) == 2) { //calculate WTA
            ihit += 10;
            idamage += 2;
            dhit -= 10;
            ddamage -= 2;
        } else if (initiator.getEquippedWeapon().hasAdvantage(defender.getEquippedWeapon().getWeapontype()) == 0) {
            ihit -= 10;
            idamage -= 2;
            dhit += 10;
            ddamage += 2;
        }
        int i;
        if (isEffective(initiator, defender)) { //check for effectiveness
            idamage += initiator.getEquippedWeapon().getMight() * 2;
        }
        if (isEffective(defender, initiator)) {
            ddamage += defender.getEquippedWeapon().getMight() * 2;
        }
        for (i = 0; i < initiator.getSkilllist().getOffensives().size(); i++) { //check initiation skills and apply buffs
            istattemp[1] += initiator.getSkilllist().getOffensives().get(i).getBuff()[1];
            istattemp[2] += initiator.getSkilllist().getOffensives().get(i).getBuff()[2];
            istattemp[3] += initiator.getSkilllist().getOffensives().get(i).getBuff()[3];
            istattemp[4] += initiator.getSkilllist().getOffensives().get(i).getBuff()[4];
            istattemp[5] += initiator.getSkilllist().getOffensives().get(i).getBuff()[5];
            istattemp[6] += initiator.getSkilllist().getOffensives().get(i).getBuff()[6];
            istattemp[7] += initiator.getSkilllist().getOffensives().get(i).getBuff()[7];
            ihit += initiator.getSkilllist().getOffensives().get(i).getBuff()[8];
            iavoid += initiator.getSkilllist().getOffensives().get(i).getBuff()[9];
            icrit += initiator.getSkilllist().getOffensives().get(i).getBuff()[10];
            if (defender.getEquippedWeapon().isMagicTarget()) {
                ddamage -= initiator.getSkilllist().getOffensives().get(i).getBuff()[12];
            } else {
                ddamage -= initiator.getSkilllist().getOffensives().get(i).getBuff()[11];
            }
            ddamage -= initiator.getSkilllist().getOffensives().get(i).getBuff()[13];
            if (initiator.getEquippedWeapon().isMagicTarget()) {
                idamage += initiator.getSkilllist().getOffensives().get(i).getBuff()[15];
            } else {
                idamage += initiator.getSkilllist().getOffensives().get(i).getBuff()[14];
            }
            idamage += initiator.getSkilllist().getOffensives().get(i).getBuff()[16];
        }
        for (i = 0; i < initiator.getSkilllist().getThresholds().size(); i++) {
            if ((initiator.getSkilllist().getThresholds().get(i)).getThresholdAmount() * istattemp[0] > istattemp[1]) {
                istattemp[1] += initiator.getSkilllist().getThresholds().get(i).getBuff()[1];
                istattemp[2] += initiator.getSkilllist().getThresholds().get(i).getBuff()[2];
                istattemp[3] += initiator.getSkilllist().getThresholds().get(i).getBuff()[3];
                istattemp[4] += initiator.getSkilllist().getThresholds().get(i).getBuff()[4];
                istattemp[5] += initiator.getSkilllist().getThresholds().get(i).getBuff()[5];
                istattemp[6] += initiator.getSkilllist().getThresholds().get(i).getBuff()[6];
                istattemp[7] += initiator.getSkilllist().getThresholds().get(i).getBuff()[7];
                ihit += initiator.getSkilllist().getThresholds().get(i).getBuff()[8];
                iavoid += initiator.getSkilllist().getThresholds().get(i).getBuff()[9];
                icrit += initiator.getSkilllist().getThresholds().get(i).getBuff()[10];
                if (defender.getEquippedWeapon().isMagicTarget()) {
                    ddamage -= initiator.getSkilllist().getThresholds().get(i).getBuff()[12];
                } else {
                    ddamage -= initiator.getSkilllist().getThresholds().get(i).getBuff()[11];
                }
                ddamage -= initiator.getSkilllist().getThresholds().get(i).getBuff()[13];
                if (initiator.getEquippedWeapon().isMagicTarget()) {
                    idamage += initiator.getSkilllist().getOffensives().get(i).getBuff()[15];
                } else {
                    idamage += initiator.getSkilllist().getOffensives().get(i).getBuff()[14];
                }
                idamage += initiator.getSkilllist().getOffensives().get(i).getBuff()[16];
            }
        }
        for (i = 0; i < defender.getSkilllist().getDefensives().size(); i++) { //check defensive skills and apply buffs
            dstattemp[1] += defender.getSkilllist().getDefensives().get(i).getBuff()[1];
            dstattemp[2] += defender.getSkilllist().getDefensives().get(i).getBuff()[2];
            dstattemp[3] += defender.getSkilllist().getDefensives().get(i).getBuff()[3];
            dstattemp[4] += defender.getSkilllist().getDefensives().get(i).getBuff()[4];
            dstattemp[5] += defender.getSkilllist().getDefensives().get(i).getBuff()[5];
            dstattemp[6] += defender.getSkilllist().getDefensives().get(i).getBuff()[6];
            dstattemp[7] += defender.getSkilllist().getDefensives().get(i).getBuff()[7];
            dhit += defender.getSkilllist().getDefensives().get(i).getBuff()[8];
            davoid += defender.getSkilllist().getDefensives().get(i).getBuff()[9];
            dcrit += defender.getSkilllist().getDefensives().get(i).getBuff()[10];
            if (initiator.getEquippedWeapon().isMagicTarget()) {
                idamage -= defender.getSkilllist().getDefensives().get(i).getBuff()[12];
            } else {
                idamage -= defender.getSkilllist().getDefensives().get(i).getBuff()[11];
            }
            idamage -= defender.getSkilllist().getDefensives().get(i).getBuff()[13];
            if (defender.getEquippedWeapon().isMagicTarget()) {
                ddamage += defender.getSkilllist().getOffensives().get(i).getBuff()[15];
            } else {
                ddamage += defender.getSkilllist().getOffensives().get(i).getBuff()[14];
            }
            ddamage += defender.getSkilllist().getOffensives().get(i).getBuff()[16];
        }
        for (i = 0; i < defender.getSkilllist().getThresholds().size(); i++) {
            if ((defender.getSkilllist().getThresholds().get(i)).getThresholdAmount() * dstattemp[0] > dstattemp[1]) {
                dstattemp[1] += defender.getSkilllist().getThresholds().get(i).getBuff()[1];
                dstattemp[2] += defender.getSkilllist().getThresholds().get(i).getBuff()[2];
                dstattemp[3] += defender.getSkilllist().getThresholds().get(i).getBuff()[3];
                dstattemp[4] += defender.getSkilllist().getThresholds().get(i).getBuff()[4];
                dstattemp[5] += defender.getSkilllist().getThresholds().get(i).getBuff()[5];
                dstattemp[6] += defender.getSkilllist().getThresholds().get(i).getBuff()[6];
                dstattemp[7] += defender.getSkilllist().getThresholds().get(i).getBuff()[7];
                dhit += defender.getSkilllist().getThresholds().get(i).getBuff()[8];
                davoid += defender.getSkilllist().getThresholds().get(i).getBuff()[9];
                dcrit += defender.getSkilllist().getThresholds().get(i).getBuff()[10];
                if (initiator.getEquippedWeapon().isMagicTarget()) {
                    idamage -= defender.getSkilllist().getThresholds().get(i).getBuff()[12];
                } else {
                    idamage -= defender.getSkilllist().getThresholds().get(i).getBuff()[11];
                }
                idamage -= defender.getSkilllist().getThresholds().get(i).getBuff()[13];
                if (defender.getEquippedWeapon().isMagicTarget()) {
                    ddamage += defender.getSkilllist().getOffensives().get(i).getBuff()[15];
                } else {
                    ddamage += defender.getSkilllist().getOffensives().get(i).getBuff()[14];
                }
                ddamage += defender.getSkilllist().getOffensives().get(i).getBuff()[16];
            }
        }
        for (i = 0; i < initiator.getSkilllist().getBreakers().size(); i++) { //check breaker skills and apply buffs
            if (initiator.getSkilllist().getBreakers().get(i).getEffectiveWeapon() == defender.getEquippedWeapon().getWeapontype()) {
                ihit += initiator.getSkilllist().getBreakers().get(i).getBuff()[0];
                iavoid += initiator.getSkilllist().getBreakers().get(i).getBuff()[1];
                icrit += initiator.getSkilllist().getBreakers().get(i).getBuff()[2];
                idamage += initiator.getSkilllist().getBreakers().get(i).getBuff()[3];
                idamage += initiator.getEquippedWeapon().getMight() * initiator.getSkilllist().getBreakers().get(i).getBuff()[4];
                ddamage -= initiator.getSkilllist().getBreakers().get(i).getBuff()[5];
            }
        }
        for (i = 0; i < defender.getSkilllist().getBreakers().size(); i++) {
            if (defender.getSkilllist().getBreakers().get(i).getEffectiveWeapon() == initiator.getEquippedWeapon().getWeapontype()) {
                dhit += defender.getSkilllist().getBreakers().get(i).getBuff()[0];
                davoid += defender.getSkilllist().getBreakers().get(i).getBuff()[1];
                dcrit += defender.getSkilllist().getBreakers().get(i).getBuff()[2];
                ddamage += defender.getSkilllist().getBreakers().get(i).getBuff()[3];
                ddamage += defender.getEquippedWeapon().getMight() * defender.getSkilllist().getBreakers().get(i).getBuff()[4];
                idamage -= defender.getSkilllist().getBreakers().get(i).getBuff()[5];
            }
        }
        for (i = 0; i < initiator.getSkilllist().getSlayers().size(); i++) { //check anti race skills and apply buffs
            if (initiator.getSkilllist().getSlayers().get(i).getSlay().equals(defender.getRace())) {
                ihit += initiator.getSkilllist().getSlayers().get(i).getStatAdvantage()[0];
                iavoid += initiator.getSkilllist().getSlayers().get(i).getStatAdvantage()[1];
                icrit += initiator.getSkilllist().getSlayers().get(i).getStatAdvantage()[2];
                idamage += initiator.getSkilllist().getSlayers().get(i).getStatAdvantage()[3];
                idamage += initiator.getEquippedWeapon().getMight() * initiator.getSkilllist().getSlayers().get(i).getStatAdvantage()[4];
                ddamage -= initiator.getSkilllist().getSlayers().get(i).getStatAdvantage()[5];
            }
        }
        for (i = 0; i < defender.getSkilllist().getSlayers().size(); i++) {
            if (defender.getSkilllist().getSlayers().get(i).getSlay().equals(initiator.getRace())) {
                dhit += defender.getSkilllist().getSlayers().get(i).getStatAdvantage()[0];
                davoid += defender.getSkilllist().getSlayers().get(i).getStatAdvantage()[1];
                dcrit += defender.getSkilllist().getSlayers().get(i).getStatAdvantage()[2];
                ddamage += defender.getSkilllist().getSlayers().get(i).getStatAdvantage()[3];
                ddamage += defender.getEquippedWeapon().getMight() * defender.getSkilllist().getSlayers().get(i).getStatAdvantage()[4];
                idamage -= defender.getSkilllist().getSlayers().get(i).getStatAdvantage()[5];
            }
        }
        result += "**BATTLE FORECAST:**\n\n";
        result += "Initiator: " + initiator.getName() + "\n";
        result += "Health: " + initiator.getStats()[1] + "/" + initiator.getStats()[0] + ".\n";
        result += "Equipped Weapon: " + initiator.getEquippedWeapon().getName();
        if (canDouble(initiator, defender) == 1) {
            if (initiator.getEquippedWeapon().isBrave()) {
                result += " x4";
            } else {
                result += " x2";
            }
        } else if (initiator.getEquippedWeapon().isBrave()) {
            result += " x2";
        }
        if (initiator.getEquippedWeapon().hasAdvantage(defender.getEquippedWeapon().getWeapontype()) == 2) {
            result += " (Advantageous)";
        } else if (initiator.getEquippedWeapon().hasAdvantage(defender.getEquippedWeapon().getWeapontype()) == 0) {
            result += " (Disadvantageous)";
        }
        if (isEffective(initiator, defender)) {
            result += " (Effective)";
        }
        result += "\n";
        if (initiator.getEquippedWeapon().isIgnoreDefense()) {
            result += "Attack: " + idamage + "\n";
        } else {
            result += "Attack: " + (idamage - ddefense) + "\n";
        }
        if (ihit - davoid > 100) {
            result += "Hit: 100%\n";
        } else {
            result += "Hit: " + (ihit - davoid) + "%\n";
        }
        if (icrit - dstattemp[5] > 100) {
            result += "Crit: 100%\n";
        } else {
            result += "Crit:" + (icrit - dstattemp[5]) + "%\n";
        }
        result += "\n***VS***\n";
        result += "Defender: " + defender.getName() + "\n";
        result += "Health: " + defender.getStats()[1] + "/" + defender.getStats()[0] + ".\n";
        result += "Equipped Weapon: " + defender.getEquippedWeapon().getName();
        if (canretaliate) {
            if (canDouble(initiator, defender) == 2) {
                if (defender.getEquippedWeapon().isBrave()) {
                    result += " x4";
                } else {
                    result += " x2";
                }
            } else if (defender.getEquippedWeapon().isBrave()) {
                result += " x2";
            }
        }
        if (defender.getEquippedWeapon().hasAdvantage(initiator.getEquippedWeapon().getWeapontype()) == 2) {
            result += " (Advantageous)";
        } else if (defender.getEquippedWeapon().hasAdvantage(initiator.getEquippedWeapon().getWeapontype()) == 0) {
            result += " (Disadvantageous)";
        }
        if (canretaliate) {
            if (isEffective(defender, initiator)) {
                result += " (Effective)";
            }
        }
        result += "\n";
        if (canretaliate) {
            result += "Attack: " + (ddamage - idefense) + "\n";
            if (ihit - davoid > 100) {
                result += "Hit: 100%\n";
            } else {
                result += "Hit: " + (dhit - iavoid) + "%\n";
            }
            if (icrit - dstattemp[5] > 100) {
                result += "Crit: 100%\n";
            } else {
                result += "Crit:" + (dcrit - istattemp[5]) + "%\n";
            }
        } else {
            result += "Attack: --\nHit: --\nCrit: --\n";
        }
        return result;
    }

    public int CalculateHit(Character c, boolean init) { //calculates hitrate of character before skills
        double hit = 0;
        hit += c.getStats()[3] * 1.5;
        hit += c.getStats()[5] * 0.5;
        hit += c.getCurrentClass().getClassBonuses()[8];
        hit += c.getWeaponRanks()[c.getEquippedWeapon().getWeapontype()];
        int i;
        if (c.getSkilllist().getPassives().size() > 0) {
            for (i = 0; i < c.getSkilllist().getPassives().size(); i++) {
                hit += c.getSkilllist().getPassives().get(i).getBuff()[8];
            }
        }
        hit += c.getEquippedWeapon().getHitRate();
        hit += c.getEquippedWeapon().getStatBonuses()[8];
        if (init) {
            for (i = 0; i < c.getSkilllist().getOffensives().size(); i++) {
                hit += c.getSkilllist().getOffensives().get(i).getBuff()[3] * 1.5;
                hit += c.getSkilllist().getOffensives().get(i).getBuff()[5] * 0.5;
            }
        } else {
            for (i = 0; i < c.getSkilllist().getDefensives().size(); i++) {
                hit += c.getSkilllist().getDefensives().get(i).getBuff()[3] * 1.5;
                hit += c.getSkilllist().getDefensives().get(i).getBuff()[5] * 0.5;
            }
        }
        return (int) hit;
    }

    public int CalculateAvoid(Character c, boolean init) { //calculates avoid of character before skills
        double avoid = 0;
        avoid += c.getStats()[4] * 1.5;
        avoid += c.getStats()[5] * 0.5;
        avoid += c.getCurrentClass().getClassBonuses()[9];
        int i;
        if (c.getSkilllist().getPassives().size() > 0) {
            for (i = 0; i < c.getSkilllist().getPassives().size(); i++) {
                avoid += c.getSkilllist().getPassives().get(i).getBuff()[9];
            }
        }
        avoid += c.getEquippedWeapon().getStatBonuses()[9];
        if (init) {
            for (i = 0; i < c.getSkilllist().getOffensives().size(); i++) {
                avoid += c.getSkilllist().getOffensives().get(i).getBuff()[4] * 1.5;
                avoid += c.getSkilllist().getOffensives().get(i).getBuff()[5] * 0.5;
            }
        } else {
            for (i = 0; i < c.getSkilllist().getDefensives().size(); i++) {
                avoid += c.getSkilllist().getDefensives().get(i).getBuff()[4] * 1.5;
                avoid += c.getSkilllist().getDefensives().get(i).getBuff()[5] * 0.5;
            }
        }
        return (int) avoid;
    }

    public int CalculateCrit(Character c, boolean init) { //calculates crit chance of character before skills
        double crit = 0;
        crit += c.getStats()[3] * 0.5;
        crit += c.getCurrentClass().getClassBonuses()[10];
        int i;
        if (c.getSkilllist().getPassives().size() > 0) {
            for (i = 0; i < c.getSkilllist().getPassives().size(); i++) {
                crit += c.getSkilllist().getPassives().get(i).getBuff()[10];
            }
        }
        crit += c.getEquippedWeapon().getCritRate();
        crit += c.getEquippedWeapon().getStatBonuses()[10];
        if (init) {
            for (i = 0; i < c.getSkilllist().getOffensives().size(); i++) {
                crit += c.getSkilllist().getOffensives().get(i).getBuff()[3] * 0.5;
            }
        } else {
            for (i = 0; i < c.getSkilllist().getDefensives().size(); i++) {
                crit += c.getSkilllist().getDefensives().get(i).getBuff()[3] * 0.5;
            }
        }
        return (int) crit;
    }

    public int CalculateDamage(Character i, boolean init) { //calculates estimated raw damage character will deal before defenses
        int damage = 0;
        int j;
        if (i.isEquippingWeapon()) {
            damage += i.getEquippedWeapon().getMight();
            if (i.getEquippedWeapon().isUsesMagic()) {
                damage += i.getStats()[2];
                damage += i.getEquippedWeapon().getStatBonuses()[2];
                damage += i.getCurrentClass().getClassBonuses()[2];
                for (j = 0; j < i.getSkilllist().getPassives().size(); j++) {
                    damage += i.getSkilllist().getPassives().get(j).getBuff()[15];
                    damage += i.getSkilllist().getPassives().get(j).getBuff()[16];
                }
                if (init) {
                    for (j = 0; j < i.getSkilllist().getOffensives().size(); j++) {
                        damage += i.getSkilllist().getOffensives().get(j).getBuff()[3];
                    }
                } else {
                    for (j = 0; j < i.getSkilllist().getDefensives().size(); j++) {
                        damage += i.getSkilllist().getOffensives().get(j).getBuff()[3];
                    }
                }
            } else {
                damage += i.getStats()[1];
                damage += i.getEquippedWeapon().getStatBonuses()[1];
                damage += i.getCurrentClass().getClassBonuses()[1];
                for (j = 0; j < i.getSkilllist().getPassives().size(); j++) {
                    damage += i.getSkilllist().getPassives().get(j).getBuff()[14];
                    damage += i.getSkilllist().getPassives().get(j).getBuff()[16];
                }
                if (init) {
                    for (j = 0; j < i.getSkilllist().getOffensives().size(); j++) {
                        damage += i.getSkilllist().getOffensives().get(j).getBuff()[2];
                    }
                } else {
                    for (j = 0; j < i.getSkilllist().getDefensives().size(); j++) {
                        damage += i.getSkilllist().getOffensives().get(j).getBuff()[2];
                    }
                }
            }
        }
        return damage;
    }

    public int CalculateDefenses(Character i, boolean magic, boolean init) { //calculates defense character has against a damage type
        int defense = 0;
        int j;
        if (magic) {
            defense += i.getEquippedWeapon().getStatBonuses()[7];
            defense += i.getCurrentClass().getClassBonuses()[7];
            defense += i.getStats()[7];
            for (j = 0; j < i.getSkilllist().getPassives().size(); j++) {
                defense += i.getSkilllist().getPassives().get(j).getBuff()[12];
                defense += i.getSkilllist().getPassives().get(j).getBuff()[13];
            }
            if (init) {
                for (j = 0; j < i.getSkilllist().getOffensives().size(); j++) {
                    defense += i.getSkilllist().getOffensives().get(j).getBuff()[7];
                }
            } else {
                for (j = 0; j < i.getSkilllist().getDefensives().size(); j++) {
                    defense += i.getSkilllist().getDefensives().get(j).getBuff()[7];
                }
            }
        } else {
            defense += i.getEquippedWeapon().getStatBonuses()[6];
            defense += i.getCurrentClass().getClassBonuses()[6];
            defense += i.getStats()[6];
            for (j = 0; j < i.getSkilllist().getPassives().size(); j++) {
                defense += i.getSkilllist().getPassives().get(j).getBuff()[11];
                defense += i.getSkilllist().getPassives().get(j).getBuff()[13];
            }
            if (init) {
                for (j = 0; j < i.getSkilllist().getOffensives().size(); j++) {
                    defense += i.getSkilllist().getOffensives().get(j).getBuff()[6];
                }
            } else {
                for (j = 0; j < i.getSkilllist().getDefensives().size(); j++) {
                    defense += i.getSkilllist().getDefensives().get(j).getBuff()[6];
                }
            }
        }
        return defense;
    }

    public int canDouble(Character i, Character d) { //calculates if character can double enemy
        int doub = 0;
        if (i.getEquippedWeapon().canDouble()) {
            int ispeed = i.getStats()[4] + i.getEquippedWeapon().getStatBonuses()[4] + i.getCurrentClass().getClassBonuses()[4];
            int dspeed = d.getStats()[4] + d.getEquippedWeapon().getStatBonuses()[4] + d.getCurrentClass().getClassBonuses()[4];
            int j;
            for (j = 0; j < i.getSkilllist().getPassives().size(); j++) {
                ispeed += i.getSkilllist().getPassives().get(j).getBuff()[4];
            }
            for (j = 0; j < i.getSkilllist().getOffensives().size(); j++) {
                ispeed += i.getSkilllist().getOffensives().get(j).getBuff()[4];
            }
            for (j = 0; j < d.getSkilllist().getPassives().size(); j++) {
                dspeed += d.getSkilllist().getPassives().get(j).getBuff()[4];
            }
            for (j = 0; j < d.getSkilllist().getDefensives().size(); j++) {
                dspeed += d.getSkilllist().getDefensives().get(j).getBuff()[4];
            }
            if (ispeed - dspeed >= 5) {
                doub = 1;
            } else if (dspeed - ispeed >= 5) {
                doub = 2;
            }
        }
        return doub;
    }

    public boolean isEffective(Character i, Character d) {
        boolean eff = false;
        if (i.getEquippedWeapon().isAntiArmor() && d.getCurrentClass().isArmored()) {
            eff = true;
        }
        if (i.getEquippedWeapon().isAntimounted() && d.getCurrentClass().isMounted()) {
            eff = true;
        }
        if (i.getEquippedWeapon().isAntiFlier() && d.getCurrentClass().isFlying()) {
            eff = true;
        }
        if (i.getEquippedWeapon().isEffectiveAgainst(d.getEquippedWeapon().getWeapontype())) {
            eff = true;
        }
        return eff;
    }

    public int calculateExperience(Character i, Character d, boolean killed, int damage) {
        int e = 0;
        if (damage < 1) { return 1; }
        int damageexp = (31 + (d.getLevel() + (d.getCurrentClass().getTier() * 20 - 20) - (i.getLevel() + (i.getCurrentClass().getTier() * 20 - 20))));
        int defeatexp = d.getLevel() - i.getLevel();
        e += damageexp;
        if (killed) {
            e += defeatexp + 20;
            if (d.isBoss()) {
                e += 40;
            }
        }
        return e;
    }
}