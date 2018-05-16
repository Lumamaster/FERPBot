import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;

/**List of commands that users can input:
 * CHARACTER COMMANDS
 * !createchar name
 * !deletechar name
 * !charinfo name
 * !changecharname name
 * !setchardescription name "description"
 * !setcharclass name class
 * !setcharrace name race
 * !setcharweaponrank name rank
 * !setcharlevel name level
 * !setcharexp name experience
 * !addexp name experience
 * !levelchar name
 * !sethp name hp
 * !setstr name strength
 * !setmag name magic
 * !setskl name skill
 * !setspd name speed
 * !setlck name luck
 * !setdef name defense
 * !setres name resistance
 * !setcurrhp name hp
 * !setcharhitbonus name bonus
 * !setcharavoidbonus name bonus
 * !setcharcritbonus name bonus
 * !sethpgrow name growth
 * !setstrgrow name growth
 * !setmaggrow name growth
 * !setsklgrow name growth
 * !setspdgrow name growth
 * !setlckgrow name growth
 * !setdefgrow name growth
 * !setresgrow name growth
 * !setlevelcap name cap
 * !promote char class
 * !showinventory char
 * !charskilllist char
 * !heal char HP
 * !setteam char team
 * !removecharitem char itemname
 * !setcharweaponrank char weapontype rank
 * !equipweapon char weapon
 * !unequipweapon char
 * !equipstaff char staff
 * !promotechar char class
 *
 * CLASS COMMANDS
 * !createclass name
 * !addclasspromopath name class
 * !removeclasspromopath name class
 * !setclassrankbonus name bonus
 * !setclassskillbonus name skill
 * !removeclassskillbonus name bonus
 * !setclassstatbonus name bonus
 * !classinfo
 *
 * SKILL COMMANDS
 * !createskill name type
 * !deleteskill name
 * !setskillname skill "name"
 * !setskilldesc skill "description"
 * !listskills
 * !skillinfo skill
 * !setslayrace skill race
 * !setslaybonus skill stat bonus
 * !setbreakerweapon skill weapon
 * !setbreakerbonus skill stat bonus
 * !setmiraclerate skill rate
 * !setmiraclestat skill stat
 * !setdefensivebuff skill stat buff
 * !setoffensivebuff skill stat buff
 * !setthresholdthresh skill threshold
 * !setthresholdbuff skill buff
 * !setskillprocrate skill rate
 * !setprocskillstatmulti skill statmulti
 * !setprocskillactistat skill stat
 * !setprocskillusenemystat skill yes/no
 * !setprocskillcancel skill yes/no
 * !setprocskillwhenattacked skill yes/no
 * !setprocskillinputstat skill stat
 * !setprocskillisdamaging skill yes/no
 * !setprocskillishealing skill yes/no
 * !setpassiveskillbuff skill stat buff
 * !setvantage skill yes/no
 * !setvantagethreshold skill threshold
 *
 * ITEM COMMANDS
 * !createitem name type
 * !setitemdescription item "desc"
 * !makeindestructible item
 * !useitem char item
 * !breakitem char item
 * !iteminfo item
 *
 * WEAPON COMMANDS
 * !setphysadvantage weapon weapontype
 * !setphysweakness weapon weapontype
 * !setmagadvantage weapon weapontype
 * !setmagdisadvantage weapon weapontype
 * !setrequiredweaponrank weapon rank
 * !setweaponeffectiveness weapon weapontype yes/no
 * !setarmorslaying weapon yes/no
 * !setantifliers weapon yes/no
 * !setantimounted weapon yes/no
 * !settargetsres weapon yes/no
 * !setusesmagicstat weapon yes/no
 * !setweaponhitrate weapon hitrate
 * !setweaponmight weapon might
 * !setweaponcritrate weapon critrate
 * !setweaponcounterable weapon yes/no
 * !setminweaponrange weapon range
 * !setmaxweaponrange weapon range
 * !setbraveweapon weapon yes/no
 * !setweaponcandouble weapon yes/no
 * !setweaponignoredefense weapon yes/no
 * !setlifesteal weapon yes/no
 *
 * STAFF COMMANDS
 * !setstaffrank staff rank
 * !sethealingstaff staff yes/no
 * !setstaffmagmulti staff multiplier
 *
 * RECIPE COMMANDS
 * !definerecipe name "items"
 * !removerecipeitem name item
 * !addrecipeitem name item
 * !recipeinfo name
 * !listrecipes
 *
 * BATTLE COMMANDS
 * !battle initiator defender
 * !battlecommand initiator defender command
 * !battleforecast char1 char2
 * !battlestaff initiator target
 *
 * STATUS COMMANDS
 * !createstatus name
 * !setdefaultduration name duration
 * !setstatusdebuff stat debuff
 * !cyclestatus team
 * !cyclecharstatus char
 * !addstatus char status
 *
 * MISC COMMANDS
 * !toast "All toasters toast toast."
 * !neal "find it in game."
 * !royalty "BECOME AS GODS"
 * !frown ">:["
 */

public class FERPBot extends BaseBot implements IListener<MessageEvent> {
    Storage store = new Storage();

    public FERPBot(IDiscordClient discordClient) {
        super(discordClient);
        store.loadData();
        EventDispatcher dispatcher = discordClient.getDispatcher(); // Gets the client's event dispatcher
        dispatcher.registerListener(this); // Registers this bot as an event listener
    }

    /**
     * Called when the client receives a message.
     */
    @Override
    public void handle(MessageEvent event) {
        IMessage message = event.getMessage(); // Gets the message from the event object NOTE: This is not the content of the message, but the object itself
        IChannel channel = message.getChannel(); // Gets the channel in which this message was sent.
        IUser author = message.getAuthor();
        IGuild userroles = message.getGuild();
        List<IRole> rolelist = author.getRolesForGuild(userroles);
        try {
            if (message.getContent().startsWith("!")) //test if valid prefix
            {
                String withoutstart = message.getContent().substring(1, message.getContent().length());
                String[] m = withoutstart.split(" (?=(([^'\"]*['\"]){2})*[^'\"]*$)");
                int argnum = m.length;
                int i;
                for (i = 0; i < m.length; i++) {
                    if (m[i].charAt(0) == '"') {
                        m[i] = m[i].substring(1, m[i].length() - 1);
                    }
                }
                for (i = 0; i < m.length; i++) {
                    System.out.println(m[i]);
                }
                if (!m[0].isEmpty()) {
                    if (m[0].equals("toast")) {
                        if (m.length > 1) {
                            printmessage("Invalid argument amount. Usage: !toast", channel);
                        } else {
                            printmessage("All toasters toast toast.", channel);
                        }
                    }
                    if (m[0].equals("neal")) {
                        if (m.length > 1) {
                            printmessage("Invalid argument amount. Usage: !neal", channel);
                        } else {
                            printmessage("find it in game", channel);
                        }
                    }
                    if (m[0].equals("royalty")) {
                        if (m.length > 1) {
                            printmessage("Invalid argument amount. Usage: !royalty", channel);
                        } else {
                            printmessage("BECOME AS GODS", channel);
                        }
                    }
                    if (m[0].equals("frown")) {
                        if (m.length > 1) {
                            printmessage("Invalid argument amount. Usage: !frown", channel);
                        } else {
                            printmessage(">:[", channel);
                        }
                    }
                    if (m[0].equals("createchar"))// name
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !createchar \"name\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                printmessage("Character already exists.", channel);
                            } else {
                                store.addCharacter(m[1], new Character(m[1]));
                                store.getCharacter(m[1]).setOwner(author.getName());
                                store.saveData();
                                printmessage(store.getCharacter(m[1]).getStatus(), channel);
                            }
                        }
                    }
                    if (m[0].equals("deletechar"))// name
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !deletechar \"name\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                store.removeCharacter(m[1]);
                                store.saveData();
                                printmessage("Character removed.", channel);
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("charinfo"))// name
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !charinfo \"name\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                Character temp = store.getCharacter(m[1]);
                                String infomessage = "***Name:*** ";
                                infomessage += temp.getName();
                                infomessage += "\n***Owner:*** ";
                                infomessage += temp.getOwner();
                                infomessage += "\n***Description:*** ";
                                infomessage += temp.getDescription();
                                infomessage += "\n***Race:*** ";
                                infomessage += temp.getRace();
                                infomessage += "\n***Class:*** ";
                                infomessage += temp.getCurrentClass().getName();
                                infomessage += "\n***Skills:*** ";
                                int j;
                                for (j = 0; j < temp.getSkilllist().getSkills().size(); j++) {
                                    infomessage += temp.getSkilllist().getSkills().get(j);
                                    if (j < temp.getSkilllist().getSkills().size() - 1) {
                                        infomessage += ", ";
                                    }
                                }
                                infomessage += "\n***Inventory:*** ";
                                for (j = 0; j < temp.getInventory().size(); j++) {
                                    infomessage += temp.getInventory().get(j).getName();
                                    if (j < temp.getInventory().size() - 1) {
                                        infomessage += ", ";
                                    }
                                }
                                infomessage += "\n***EXP:*** ";
                                if (temp.getLevel() == temp.getLevelCap()) {
                                    infomessage += "--";
                                } else {
                                    infomessage += temp.getExperience() + "/100";
                                }
                                infomessage += "\n***Stats:*** ";
                                infomessage += "\nHP: " + temp.getCurrHP() + "/" + temp.getStats()[0];
                                infomessage += "\nSTR: " + temp.getStats()[1];
                                infomessage += "\nMAG: " + temp.getStats()[2];
                                infomessage += "\nSKL: " + temp.getStats()[3];
                                infomessage += "\nSPD: " + temp.getStats()[4];
                                infomessage += "\nLCK: " + temp.getStats()[5];
                                infomessage += "\nDEF: " + temp.getStats()[6];
                                infomessage += "\nRES: " + temp.getStats()[7];
                                infomessage += "\nHIT: " + temp.getStats()[8];
                                infomessage += "\nAVO: " + temp.getStats()[9];
                                infomessage += "\nCRT: " + temp.getStats()[10];
                                printmessage(infomessage, channel);
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("changecharname"))// name name
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !changecharname \"before\" \"after\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                store.getCharacter(m[1]).changeName(m[2]);
                                store.addCharacter(m[2], store.getCharacter(m[1]));
                                store.removeCharacter(m[1]);
                                store.addCharacter(m[1], store.getCharacter(m[1]));
                                store.saveData();
                                printmessage(store.getCharacter(m[2]).getStatus(), channel);
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setchardescription"))// name "desc"
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setchardescription \"name\" \"description\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                store.getCharacter(m[1]).setDescription(m[2]);
                                store.saveData();
                                printmessage(store.getCharacter(m[1]).getStatus(), channel);
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setcharclass"))// name class
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcharclass \"name\" \"class\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                if (store.hasClass(m[2])) {
                                    store.getCharacter(m[1]).setClass(store.getClass(m[2]));
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Class does not exist.", channel);
                                }
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setcharrace"))// name race
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcharrace \"name\" \"race\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                if (store.getCharacter(m[1]).getRace().equals(m[2])) {
                                    printmessage("Character race is already " + m[2] + ".", channel);
                                } else {
                                    store.getCharacter(m[1]).setRace(m[2]);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                }
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setcharweaponrank"))// name rank
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !setcharweaponrank \"name\" \"weapon\" rank", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[3].matches(regex)) {
                                int rank = Integer.parseInt(m[3]);
                                if (rank < 0 || rank > 6) {
                                    if (store.hasCharacter(m[1])) {
                                        if (m[2].equals("Sword")) {
                                            store.getCharacter(m[1]).setWeaponRank(0, rank);
                                            store.saveData();
                                            printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Lance")) {
                                            store.getCharacter(m[1]).setWeaponRank(1, rank);
                                            store.saveData();
                                            printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Axe")) {
                                            store.getCharacter(m[1]).setWeaponRank(2, rank);
                                            store.saveData();
                                            printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Anima")) {
                                            store.getCharacter(m[1]).setWeaponRank(3, rank);
                                            store.saveData();
                                            printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Light")) {
                                            store.getCharacter(m[1]).setWeaponRank(4, rank);
                                            store.saveData();
                                            printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Dark")) {
                                            store.getCharacter(m[1]).setWeaponRank(5, rank);
                                            store.saveData();
                                            printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Bow")) {
                                            store.getCharacter(m[1]).setWeaponRank(6, rank);
                                            store.saveData();
                                            printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Staff")) {
                                            store.getCharacter(m[1]).setWeaponRank(7, rank);
                                            store.saveData();
                                            printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                        } else {
                                            printmessage("Invalid weapon type inputted. The bot currently only supports: \"Sword, Lance, Axe, Bow, Anima, Light, Dark, Staff\".", channel);
                                        }
                                    } else {
                                        printmessage("Character does not exist.", channel);
                                    }
                                }
                            } else {
                                printmessage("Invalid rank inputted. Ensure it is one of these: (0 - None, 1 - E, 2 - D, 3 - C, 4 - B, 5 - A, 6 - S).", channel);
                            }
                        }
                    }
                    if (m[0].equals("setcharlevel"))// name level
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcharlevel \"name\" level", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int level = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setLevel(level);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid level input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setcharexp"))// name experience
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcharexp \"name\" exp", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int exp = Integer.parseInt(m[2]);
                                if (exp < 100 && exp > -1) {
                                    if (store.hasCharacter(m[1])) {
                                        int currexp = store.getCharacter(m[1]).getExperience();
                                        store.getCharacter(m[1]).gainExperience(currexp - exp);
                                        store.saveData();
                                        printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                    } else {
                                        printmessage("Character does not exist.", channel);
                                    }
                                } else {
                                    printmessage("Invalid EXP input. Ensure it is between 0 and 99 inclusive.", channel);
                                }
                            } else {
                                printmessage("Invalid EXP input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("addexp"))// name experience
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !addexp \"name\" exp", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int exp = Integer.parseInt(m[2]);
                                if (exp < 100 && exp > -1) {
                                    if (store.hasCharacter(m[1])) {
                                        store.getCharacter(m[1]).gainExperience(exp);
                                        store.saveData();
                                        printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                    } else {
                                        printmessage("Character does not exist.", channel);
                                    }
                                } else {
                                    printmessage("Invalid EXP input. Ensure it is between 0 and 99 inclusive.", channel);
                                }
                            } else {
                                printmessage("Invalid EXP input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("levelchar"))// name
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !levelchar \"name\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                store.getCharacter(m[1]).levelup();
                                store.saveData();
                                printmessage(store.getCharacter(m[1]).getStatus(), channel);
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("sethp"))// name hp
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !sethp \"name\" hp", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int hp = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setMaxHP(hp);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid HP input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setstr"))// name strength
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setstr \"name\" str", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int str = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setStrength(str);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid STR input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setmag"))// name magic
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setmag \"name\" mag", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int mag = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setMagic(mag);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid MAG input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setskl"))// name skill
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setskl \"name\" skill", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int skill = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setSkill(skill);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid SKL input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setspd"))// name speed
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setspd \"name\" speed", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int speed = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setSpeed(speed);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid SPD input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setlck"))// name luck
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setlck \"name\" luck", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int luck = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setLuck(luck);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid LCK input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setdef"))// name defense
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setdef \"name\" defense", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int def = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setDefense(def);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid DEF input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setres"))// name resistance
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setres \"name\" resistance", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int res = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setResistance(res);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid RES input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setcurrhp"))// name hp
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcurr \"name\" hp", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int hp = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setCurrHP(hp);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid HP input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("sethpgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !sethpgrow \"name\" growth", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int hp = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setHPGrowth(hp);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setstrgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setstrgrow \"name\" growth", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int str = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setSTRGrowth(str);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setmaggrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setmaggrow \"name\" growth", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int mag = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setMAGGrowth(mag);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setsklgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setsklgrow \"name\" growth", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int skl = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setSKLGrowth(skl);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setspdgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setspdgrow \"name\" growth", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int spd = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setSPDGrowth(spd);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setlckgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setlckgrow \"name\" growth", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int luck = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setLUCKGrowth(luck);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setdefgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setdefgrow \"name\" growth", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int def = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setDEFGrowth(def);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setresgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setresgrow \"name\" growth", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int res = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setRESGrowth(res);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setlevelcap"))// name cap
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setlevelcap \"name\" levelcap", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int level = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setLevelCap(level);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid input. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("showinventory"))// char
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !showinventory \"name\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                String temp = store.getCharacter(m[1]).getName() + "'s inventory:\n";
                                int a;
                                for (a = 0; a < store.getCharacter(m[1]).getInventory().size(); a++) {
                                    temp += store.getCharacter(m[1]).getInventory().get(a).getName() + " (" + store.getCharacter(m[1]).getInventory().get(a).getCurrUses() + "/" + store.getCharacter(m[1]).getInventory().get(a).getMaxUses() + ")\n";
                                }
                                printmessage(temp, channel);
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("charskilllist"))// char
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !charskilllist \"name\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                String temp = store.getCharacter(m[1]).getName() + "'s skill list:\n";
                                int a;
                                for (a = 0; a < store.getCharacter(m[1]).getInventory().size(); a++) {
                                    temp += store.getCharacter(m[1]).getSkilllist().getSkills().get(a).getName() + "\n";
                                }
                                printmessage(temp, channel);
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("heal"))// char HP
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !heal \"name\" hp", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                store.getCharacter(m[1]).heal(store.getCharacter(m[1]).getStats()[0] - store.getCharacter(m[1]).getCurrHP());
                                printmessage(store.getCharacter(m[1]).getStatus(), channel);
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setteam"))// char team
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setteam \"name\" teamnumber", channel);
                        } else {
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                int team = Integer.parseInt(m[2]);
                                if (store.hasCharacter(m[1])) {
                                    store.getCharacter(m[1]).setTeam(team);
                                    store.saveData();
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Character does not exist.", channel);
                                }
                            } else {
                                printmessage("Invalid team. Ensure it is a number.", channel);
                            }
                        }
                    }
                    if (m[0].equals("removecharitem"))// char itemname
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !removecharitem \"name\" \"item\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                int a;
                                for (a = 0; a < store.getCharacter(m[1]).getInventory().size(); a++) {
                                    if (store.getCharacter(m[1]).getInventory().get(a).getName().equals(m[2])) {
                                        store.getCharacter(m[1]).getInventory().remove(a);
                                        printmessage("Item removed.", channel);
                                        store.saveData();
                                        return;
                                    }
                                }
                                printmessage("Item not found.", channel);
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("equipweapon"))// char weapon
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !equipweapon \"name\" \"weapon\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                int a;
                                for (a = 0; a < store.getCharacter(m[1]).getInventory().size(); a++) {
                                    if (store.getCharacter(m[1]).getInventory().get(a).getName().equals(m[2])) {
                                        if (store.getCharacter(m[1]).getInventory().get(a).isWeapon()) {
                                            store.getCharacter(m[1]).equipWeapon((Weapon) store.getCharacter(m[1]).getInventory().get(a));
                                            printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                            store.saveData();
                                            return;
                                        }
                                    }
                                }
                                printmessage("Weapon not found.", channel);
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("unequipweapon"))// char
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !unequipweapon \"name\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                int a;
                                for (a = 0; a < store.getCharacter(m[1]).getInventory().size(); a++) {
                                    if (store.getCharacter(m[1]).isEquippingWeapon()) {
                                        store.getCharacter(m[1]).unequipWeapon();
                                        printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                    } else {
                                        printmessage("No weapon equipped.", channel);
                                    }
                                }
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("equipstaff"))// char staff
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !equipstaff \"name\" staff", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                int a;
                                for (a = 0; a < store.getCharacter(m[1]).getInventory().size(); a++) {
                                    if (store.getCharacter(m[1]).getInventory().get(a).getName().equals(m[2])) {
                                        if (store.getCharacter(m[1]).getInventory().get(a).isStaff()) {
                                            store.getCharacter(m[1]).equipStaff((Staff) store.getCharacter(m[1]).getInventory().get(a));
                                            printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                            store.saveData();
                                            return;
                                        }
                                    }
                                }
                                printmessage("Staff not found.", channel);
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("promotechar"))// char class
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !promotechar \"name\" \"class\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                if (store.getCharacter(m[1]).getCurrentClass().getPromotionPaths().size() > 0) {
                                    int a;
                                    for (a = 0; a < store.getCharacter(m[1]).getCurrentClass().getPromotionPaths().size(); a++) {
                                        if (store.getCharacter(m[1]).getCurrentClass().getPromotionPaths().get(a).getName().equals(m[2])) {
                                            store.getCharacter(m[1]).promote(store.getCharacter(m[1]).getCurrentClass().getPromotionPaths().get(a));
                                            printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                            return;
                                        }
                                        printmessage("Promotion path not found.", channel);
                                    }
                                } else {
                                    printmessage("Character has no promotion paths.", channel);
                                }
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("listchars")) {
                        if (m.length != 1) {
                            printmessage("Invalid argument amount. Usage: !listchars", channel);
                        } else {
                            Character[] temp = store.getcharlist();
                            int a;
                            String t = "List of characters: \n";
                            for (a = 0; a < temp.length; a++) {
                                t += temp[a].getName() + "\n";
                            }
                            printmessage(t, channel);
                        }
                    }
                    if (m[0].equals("createclass")) //name
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !createclass \"name\"", channel);
                        } else {
                            Class temp = new Class(m[1]);
                            store.addClass(m[1], temp);
                            store.saveData();
                            printmessage("Class " + m[1] + " created.", channel);
                        }
                    }
                    if (m[0].equals("addclasspromopath")) // name class
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !addclasspromopath \"name\" \"class\"", channel);
                        } else {
                            if (store.hasClass(m[1])) {
                                int a;
                                for (a = 0; a < store.getClass(m[1]).getPromotionPaths().size(); a++) {
                                    if (store.getClass(m[1]).getPromotionPaths().get(a).getName().equals(m[3])) {
                                        printmessage("Class already has this promotion path.", channel);
                                        return;
                                    }
                                }
                                if (store.hasClass(m[2])) {
                                    store.getClass(m[1]).addPromotionPath(store.getClass(m[2]));
                                    printmessage(store.getClass(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Class you are trying to add does not exist.", channel);
                                }
                            } else {
                                printmessage("Class does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("removeclasspromopath")) // name class
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !removeclasspromopath \"name\" \"class\"", channel);
                        } else {
                            if (store.hasClass(m[1])) {
                                int a;
                                for (a = 0; a < store.getClass(m[1]).getPromotionPaths().size(); a++) {
                                    if (store.getClass(m[1]).getPromotionPaths().get(a).getName().equals(m[2])) {
                                        store.getClass(m[1]).removePromotionPath(store.getClass(m[1]).getPromotionPaths().get(a));
                                        printmessage(store.getClass(m[1]).getStatus(), channel);
                                        store.saveData();
                                        return;
                                    }
                                }
                                printmessage("Promotion path does not exist in the specified class.", channel);
                            } else {
                                printmessage("Class does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setclassrankbonus")) // name weapon bonus
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !setclassrankbonus \"name\" \"weapon\" \"bonus\"", channel);
                        } else {
                            if (store.hasClass(m[1])) {
                                String regex = "\\d+";
                                if (m[3].matches(regex)) {
                                    int bonus = Integer.parseInt(m[3]);
                                    if (bonus > 0 && bonus < 7) {
                                        if (m[2].equals("Sword")) {
                                            store.getClass(m[1]).setPromoWeaponRankBonus(0, bonus);
                                            store.saveData();
                                            printmessage(store.getClass(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Lance")) {
                                            store.getClass(m[1]).setPromoWeaponRankBonus(1, bonus);
                                            store.saveData();
                                            printmessage(store.getClass(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Axe")) {
                                            store.getClass(m[1]).setPromoWeaponRankBonus(2, bonus);
                                            store.saveData();
                                            printmessage(store.getClass(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Anima")) {
                                            store.getClass(m[1]).setPromoWeaponRankBonus(4, bonus);
                                            store.saveData();
                                            printmessage(store.getClass(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Light")) {
                                            store.getClass(m[1]).setPromoWeaponRankBonus(5, bonus);
                                            store.saveData();
                                            printmessage(store.getClass(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Dark")) {
                                            store.getClass(m[1]).setPromoWeaponRankBonus(6, bonus);
                                            store.saveData();
                                            printmessage(store.getClass(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Bow")) {
                                            store.getClass(m[1]).setPromoWeaponRankBonus(3, bonus);
                                            store.saveData();
                                            printmessage(store.getClass(m[1]).getStatus(), channel);
                                        } else if (m[2].equals("Staff")) {
                                            store.getClass(m[1]).setPromoWeaponRankBonus(7, bonus);
                                            store.saveData();
                                            printmessage(store.getClass(m[1]).getStatus(), channel);
                                        } else {
                                            printmessage("Invalid weapon type inputted. The bot currently only supports: \"Sword, Lance, Axe, Bow, Anima, Light, Dark, Staff\".", channel);
                                        }
                                    } else {
                                        printmessage("Rank bonus out of bounds, ensure it is between 1 and 6 inclusive.", channel);
                                    }
                                } else {
                                    printmessage("Invalid bonus. Ensure it is a number.", channel);
                                }
                            } else {
                                printmessage("Class does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setclassskillbonus")) // name skill
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setclassskillbonus \"name\" \"skill\"", channel);
                        } else {
                            if (store.hasClass(m[2])) {

                            } else {
                                printmessage("Class does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("removeclassskillbonus")) // name bonus
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !removeclassskillbonus \"name\" \"skill\"", channel);
                        } else {
                            if (store.hasClass(m[2])) {

                            } else {
                                printmessage("Class does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setclassstatbonus")) // name bonus
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setclassstatbonus \"name\" \"bonus\"", channel);
                        } else {
                            if (store.hasClass(m[2])) {

                            } else {
                                printmessage("Class does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("classinfo")) // name
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !classinfo \"name\"", channel);
                        } else {
                            if (store.hasClass(m[2])) {

                            } else {
                                printmessage("Class does not exist.", channel);
                            }
                        }
                    }
                }
            }
        } catch (RateLimitException e) { // RateLimitException thrown. The bot is sending messages too quickly!
            System.err.print("Sending messages too quickly!");
            e.printStackTrace();
        } catch (DiscordException e) { // DiscordException thrown. Many possibilities. Use getErrorMessage() to see what went wrong.
            System.err.print(e.getErrorMessage()); // Print the error message sent by Discord
            e.printStackTrace();
        } catch (MissingPermissionsException e) { // MissingPermissionsException thrown. The bot doesn't have permission to send the message!
            System.err.print("Missing permissions for channel!");
            e.printStackTrace();
        }
    }

    public void printmessage(String m, IChannel c) {
        new MessageBuilder(this.client).withChannel(c).withContent(m).build();
    }
}