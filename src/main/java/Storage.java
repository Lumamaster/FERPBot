import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    private Map charhash = Collections.synchronizedMap(new HashMap<String, Character>(200));
    private Map itemhash = Collections.synchronizedMap(new HashMap<String, Item>(200));
    private Map classhash = Collections.synchronizedMap(new HashMap<String, Class>(200));
    private Map skillhash = Collections.synchronizedMap(new HashMap<String, Skill>(200));
    void addCharacter(String s, Character c) { charhash.put(s, c); }
    void addItem(String s, Item i) { itemhash.put(s, i); }
    void addClass(String s, Class c) { classhash.put(s, c); }
    void addSkill(String s, Skill sk) { skillhash.put(s, sk); }
    boolean hasCharacter(String s) { return charhash.containsKey(s); }
    boolean hasItem(String s) { return itemhash.containsKey(s); }
    boolean hasClass(String s) { return classhash.containsKey(s); }
    boolean hasSkill(String s) { return skillhash.containsKey(s); }
    Character getCharacter(String s) { return (Character)charhash.get(s); }
    Item getItem(String s) { return (Item)itemhash.get(s); }
    Class getClass(String s) { return (Class)classhash.get(s); }
    Skill getSkill(String s) { return (Skill)skillhash.get(s); }
    void removeCharacter(String s) { charhash.remove(s); }
    public void removeItem(String s) { itemhash.remove(s); }
    public void removeClass(String s) { classhash.remove(s); }
    void removeSkill(String s) { skillhash.remove(s); }
    Character[] getcharlist() {
        Collection<Character> tempchar = charhash.values();
        Character[] chararray = tempchar.toArray(new Character[0]);
        return chararray;
    }
    Class[] getclasslist() {
        Collection<Character> tempclass = classhash.values();
        Class[] classarray = tempclass.toArray(new Class[0]);
        return classarray;
    }
    Skill[] getskilllist() {
        Collection<Character> tempskill = skillhash.values();
        Skill[] skillarray = tempskill.toArray(new Skill[0]);
        return skillarray;
    }
    public Item[] getitemlist() {
        Collection<Character> tempitem = itemhash.values();
        Item[] itemarray = tempitem.toArray(new Item[0]);
        return itemarray;
    }
    void saveData()
    {
        Collection<Character> tempchar = charhash.values();
        Collection<Item> tempitem = itemhash.values();
        Collection<Class> tempclass = classhash.values();
        Collection<Skill> tempskill = skillhash.values();
        Character[] chararray = tempchar.toArray(new Character[0]);
        Item[] itemarray = tempitem.toArray(new Item[0]);
        Class[] classarray = tempclass.toArray(new Class[0]);
        Skill[] skillarray = tempskill.toArray(new Skill[0]);
        int i;
        try {
            for (i = 0; i < chararray.length; i++) {
                File f = new File(System.getProperty("user.dir") + "/characters/" + chararray[i].getName());
                f.getParentFile().mkdirs();
                if (!f.exists()) {
                    f.createNewFile();
                }
                FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir") + "/characters/" + chararray[i].getName());
                ObjectOutputStream oout = new ObjectOutputStream(fout);
                oout.writeObject(chararray[i]);
                oout.close();
                /*FileWriter fileWriter = new FileWriter(f);
                fileWriter.write(chararray[i].getName() + "\n");
                fileWriter.write(chararray[i].getAppearance() + "\n");
                fileWriter.write(chararray[i].getFamily() + "\n");
                fileWriter.write(chararray[i].getDescription() + "\n");
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
    void loadData()
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
        } catch (NullPointerException e) {
            File chars = new File(System.getProperty("user.dir") + "/characters/" + "dummy.char");
            chars.mkdirs();
            File items = new File(System.getProperty("user.dir") + "/items/" + "dummy.item");
            items.mkdirs();
            File classes = new File(System.getProperty("user.dir") + "/classes/" + "dummy.class");
            classes.mkdirs();
            File skills = new File(System.getProperty("user.dir") + "/skills/" + "dummy.skill");
            skills.mkdirs();
        }
        Collection<Character> tempchar = charhash.values();
        Collection<Item> tempitem = itemhash.values();
        Collection<Class> tempclass = classhash.values();
        Collection<Skill> tempskill = skillhash.values();
        Character[] chararray = tempchar.toArray(new Character[0]);
        Item[] itemarray = tempitem.toArray(new Item[0]);
        Class[] classarray = tempclass.toArray(new Class[0]);
        Skill[] skillarray = tempskill.toArray(new Skill[0]);
        int i;
        System.out.println("Characters loaded: " + charhash.size());
        for (i = 0; i < chararray.length; i++) {
            System.out.println(chararray[i].getName());
        }
        System.out.println("Classes loaded: " + classhash.size());
        for (i = 0; i < classarray.length; i++) {
            System.out.println(classarray[i].getName());
        }
        System.out.println("Items loaded: " + itemhash.size());
        for (i = 0; i < itemarray.length; i++) {
            System.out.println(itemarray[i].getName());
        }
        System.out.println("Skills loaded: " + skillhash.size());
        for (i = 0; i < skillarray.length; i++) {
            System.out.println(skillarray[i].getName());
        }
    }
}
