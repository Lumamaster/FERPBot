import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    Map charhash = Collections.synchronizedMap(new HashMap<String, Character>(200));
    Map itemhash = Collections.synchronizedMap(new HashMap<String, Item>(200));
    Map classhash = Collections.synchronizedMap(new HashMap<String, Class>(200));
    Map skillhash = Collections.synchronizedMap(new HashMap<String, Skill>(200));
    public void addCharacter(String s, Character c) { charhash.put(s, c); }
    public void addItem(String s, Item i) { itemhash.put(s, i); }
    public void addClass(String s, Class c) { classhash.put(s, c); }
    public void addSkill(String s, Skill sk) { skillhash.put(s, sk); }
    public boolean hasCharacter(String s) { return charhash.containsKey(s); }
    public boolean hasItem(String s) { return itemhash.containsKey(s); }
    public boolean hasClass(String s) { return classhash.containsKey(s); }
    public boolean hasSkill(String s) { return skillhash.containsKey(s); }
    public Character getCharacter(String s) { return (Character)charhash.get(s); }
    public Item getItem(String s) { return (Item)itemhash.get(s); }
    public Class getClass(String s) { return (Class)classhash.get(s); }
    public Skill getSkill(String s) { return (Skill)skillhash.get(s); }
    public void removeCharacter(String s) { charhash.remove(s); }
    public void removeItem(String s) { itemhash.remove(s); }
    public void removeClass(String s) { classhash.remove(s); }
    public void removeSkill(String s) { skillhash.remove(s); }
    public void saveData()
    {
        Collection<Character> tempchar = charhash.values();
        Collection<Item> tempitem = itemhash.values();
        Collection<Class> tempclass = classhash.values();
        Collection<Skill> tempskill = skillhash.values();
        Character[] chararray = (Character[])tempchar.toArray();
        Item[] itemarray = (Item[])tempitem.toArray();
        Class[] classarray = (Class[])tempclass.toArray();
        Skill[] skillarray = (Skill[])tempskill.toArray();
        int i;
        int j;
        try {
            for (i = 0; i < chararray.length; i++) {
                File f = new File(System.getProperty("user.dir") + "/characters/" + chararray[i].getName() + ".char");
                f.getParentFile().mkdirs();
                if (!f.exists()) {
                    f.createNewFile();
                }
                FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir") + "/characters/" + chararray[i].getName() + ".char");
                ObjectOutputStream oout = new ObjectOutputStream(fout);
                oout.writeObject(chararray[i]);
                oout.close();
                /*FileWriter fileWriter = new FileWriter(f);
                fileWriter.write(chararray[i].getName() + "\n");
                fileWriter.write(chararray[i].getAppearance() + "\n");
                fileWriter.write(chararray[i].getFamily() + "\n");
                fileWriter.write(chararray[i].getBackground() + "\n");
                fileWriter.write(chararray[i].getRace() + "\n");
                fileWriter.write(chararray[i].getOwner() + "\n");
                fileWriter.write(chararray[i].getStats()[0] + " " + chararray[i].getStats()[1] + " " + chararray[i].getStats()[2] + " " + chararray[i].getStats()[3] + " " + chararray[i].getStats()[4] + " " + chararray[i].getStats()[5] + " " + chararray[i].getStats()[6] + " " + chararray[i].getStats()[7] + " " + chararray[i].getStats()[8] + " " + chararray[i].getStats()[9] + " " + chararray[i].getStats()[10] + "\n");
                fileWriter.write(chararray[i].getGrowths()[0] + " " + chararray[i].getGrowths()[1] + " " + chararray[i].getGrowths()[2] + " " + chararray[i].getGrowths()[3] + " " + chararray[i].getGrowths()[4] + " " + chararray[i].getGrowths()[5] + " " + chararray[i].getGrowths()[6] + " " + chararray[i].getGrowths()[7] + "\n");
                fileWriter.write(chararray[i].getWeaponRanks()[0] + " " + chararray[i].getWeaponRanks()[1] + " " + chararray[i].getWeaponRanks()[2] + " " + chararray[i].getWeaponRanks()[3] + " " + chararray[i].getWeaponRanks()[4] + " " + chararray[i].getWeaponRanks()[5] + " " + chararray[i].getWeaponRanks()[6] + " " + chararray[i].getWeaponRanks()[7] + "\n");
                fileWriter.write(chararray[i].getExperience() + "\n");
                fileWriter.write(chararray[i].getLevel());
                fileWriter.write(chararray[i].getLevelCap() + "\n");
                fileWriter.write(chararray[i].getCurrentClass().getName());
                for (j = 0; j < chararray[i].getInventory().size(); j++) {
                    fileWriter.write(chararray[i].getInventory().get(j).getName() + ";" + chararray[i].getInventory().get(j).getCurrUses() + ":");
                }
                fileWriter.write("\n");
                for (j = 0; j < chararray[i].getSkilllist().getNumOfSkills(); j++) {
                    fileWriter.write(chararray[i].getSkilllist().getSkills().get(j).getName() + ";");
                }
                fileWriter.write("\n");
                int temp;
                if (chararray[i].isNPC()) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                fileWriter.write(temp + "\n");
                if (chararray[i].isBoss()) {
                    temp = 1;
                } else {
                    temp = 0;
                }
                fileWriter.write(temp + "\n");*/
            }
            for (i = 0; i < itemarray.length; i++) {
                File f = new File(System.getProperty("user.dir") + "/items/" + itemarray[i].getName());
                f.getParentFile().mkdirs();
                if (!f.exists()) {
                    f.createNewFile();
                }
                FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir") + "/items/" + itemarray[i].getName());
                ObjectOutputStream oout = new ObjectOutputStream(fout);
                oout.writeObject(itemarray[i]);
                oout.close();
            }
            for (i = 0; i < classarray.length; i++) {
                File f = new File(System.getProperty("user.dir") + "/classes/" + classarray[i].getName());
                f.getParentFile().mkdirs();
                if (!f.exists()) {
                    f.createNewFile();
                }
                FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir") + "/classes/" + classarray[i].getName());
                ObjectOutputStream oout = new ObjectOutputStream(fout);
                oout.writeObject(classarray[i]);
                oout.close();
            }
            for (i = 0; i < skillarray.length; i++) {
                File f = new File(System.getProperty("user.dir") + "/skills/" + skillarray[i].getName());
                f.getParentFile().mkdirs();
                if (!f.exists()) {
                    f.createNewFile();
                }
                FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir") + "/skills/" + skillarray[i].getName());
                ObjectOutputStream oout = new ObjectOutputStream(fout);
                oout.writeObject(skillarray[i]);
                oout.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public void loadData()
    {
        try {
            File[] charfiles = new File(System.getProperty("user.dir") + "/characters/").listFiles();
            for (File file : charfiles) {
                FileInputStream fin = new FileInputStream(System.getProperty("user.dir") + "/characters/" + file.getName());
                ObjectInputStream oin = new ObjectInputStream(fin);
                charhash.put(file.getName(), oin.readObject());
                oin.close();
            }
            File[] itemfiles = new File(System.getProperty("user.dir") + "/items/").listFiles();
            for (File file : itemfiles) {
                FileInputStream fin = new FileInputStream(System.getProperty("user.dir") + "/items/" + file.getName());
                ObjectInputStream oin = new ObjectInputStream(fin);
                itemhash.put(file.getName(), oin.readObject());
                oin.close();
            }
            File[] classfiles = new File(System.getProperty("user.dir") + "/classes/").listFiles();
            for (File file : classfiles) {
                FileInputStream fin = new FileInputStream(System.getProperty("user.dir") + "/classes/" + file.getName());
                ObjectInputStream oin = new ObjectInputStream(fin);
                classhash.put(file.getName(), oin.readObject());
                oin.close();
            }
            File[] skillfiles = new File(System.getProperty("user.dir") + "/skills/").listFiles();
            for (File file : skillfiles) {
                FileInputStream fin = new FileInputStream(System.getProperty("user.dir") + "/skills/" + file.getName());
                ObjectInputStream oin = new ObjectInputStream(fin);
                skillhash.put(file.getName(), oin.readObject());
                oin.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}
