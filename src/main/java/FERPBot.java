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

public class FERPBot extends BaseBot implements IListener<MessageEvent> {
    private Storage store = new Storage();

    FERPBot(IDiscordClient discordClient) {
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
                                StringBuilder infomessage = new StringBuilder("***Name:*** ");
                                infomessage.append(temp.getName());
                                infomessage.append("\n***Owner:*** ");
                                infomessage.append(temp.getOwner());
                                infomessage.append("\n***Description:*** ");
                                infomessage.append(temp.getDescription());
                                infomessage.append("\n***Race:*** ");
                                infomessage.append(temp.getRace());
                                infomessage.append("\n***Class:*** ");
                                infomessage.append(temp.getCurrentClass().getName());
                                infomessage.append("\n***Skills:*** ");
                                int j;
                                for (j = 0; j < temp.getSkilllist().getSkills().size(); j++) {
                                    infomessage.append(temp.getSkilllist().getSkills().get(j));
                                    if (j < temp.getSkilllist().getSkills().size() - 1) {
                                        infomessage.append(", ");
                                    }
                                }
                                infomessage.append("\n***Inventory:*** ");
                                for (j = 0; j < temp.getInventory().size(); j++) {
                                    infomessage.append(temp.getInventory().get(j).getName());
                                    if (j < temp.getInventory().size() - 1) {
                                        infomessage.append(", ");
                                    }
                                }
                                infomessage.append("\n***EXP:*** ");
                                if (temp.getLevel() == temp.getLevelCap()) {
                                    infomessage.append("--");
                                } else {
                                    infomessage.append(temp.getExperience()).append("/100");
                                }
                                infomessage.append("\n***Stats:*** ");
                                infomessage.append("\nHP: ").append(temp.getCurrHP()).append("/").append(temp.getStats()[0]);
                                infomessage.append("\nSTR: ").append(temp.getStats()[1]);
                                infomessage.append("\nMAG: ").append(temp.getStats()[2]);
                                infomessage.append("\nSKL: ").append(temp.getStats()[3]);
                                infomessage.append("\nSPD: ").append(temp.getStats()[4]);
                                infomessage.append("\nLCK: ").append(temp.getStats()[5]);
                                infomessage.append("\nDEF: ").append(temp.getStats()[6]);
                                infomessage.append("\nRES: ").append(temp.getStats()[7]);
                                infomessage.append("\nHIT: ").append(temp.getStats()[8]);
                                infomessage.append("\nAVO: ").append(temp.getStats()[9]);
                                infomessage.append("\nCRT: ").append(temp.getStats()[10]);
                                printmessage(infomessage.toString(), channel);
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
                                        switch (m[2]) {
                                            case "Sword":
                                                store.getCharacter(m[1]).setWeaponRank(0, rank);
                                                store.saveData();
                                                printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                                break;
                                            case "Lance":
                                                store.getCharacter(m[1]).setWeaponRank(1, rank);
                                                store.saveData();
                                                printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                                break;
                                            case "Axe":
                                                store.getCharacter(m[1]).setWeaponRank(2, rank);
                                                store.saveData();
                                                printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                                break;
                                            case "Anima":
                                                store.getCharacter(m[1]).setWeaponRank(3, rank);
                                                store.saveData();
                                                printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                                break;
                                            case "Light":
                                                store.getCharacter(m[1]).setWeaponRank(4, rank);
                                                store.saveData();
                                                printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                                break;
                                            case "Dark":
                                                store.getCharacter(m[1]).setWeaponRank(5, rank);
                                                store.saveData();
                                                printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                                break;
                                            case "Bow":
                                                store.getCharacter(m[1]).setWeaponRank(6, rank);
                                                store.saveData();
                                                printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                                break;
                                            case "Staff":
                                                store.getCharacter(m[1]).setWeaponRank(7, rank);
                                                store.saveData();
                                                printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                                break;
                                            default:
                                                printmessage("Invalid weapon type inputted. The bot currently only supports: Sword, Lance, Axe, Bow, Anima, Light, Dark, Staff.", channel);
                                                break;
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
                                StringBuilder temp = new StringBuilder(store.getCharacter(m[1]).getName() + "'s inventory:\n");
                                int a;
                                for (a = 0; a < store.getCharacter(m[1]).getInventory().size(); a++) {
                                    temp.append(store.getCharacter(m[1]).getInventory().get(a).getName()).append(" (").append(store.getCharacter(m[1]).getInventory().get(a).getCurrUses()).append("/").append(store.getCharacter(m[1]).getInventory().get(a).getMaxUses()).append(")\n");
                                }
                                printmessage(temp.toString(), channel);
                            } else {
                                printmessage("Character does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("addcharskill")) // char skill
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !addcharskill \"name\" \"skill\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                if (store.hasSkill(m[2])) {
                                    store.getCharacter(m[1]).addSkill(store.getSkill(m[2]));
                                    printmessage(store.getCharacter(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Skill does not exist.", channel);
                                }
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
                            printmessage("Invalid argument amount. Usage: !equipstaff \"name\" \"staff\"", channel);
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
                            StringBuilder t = new StringBuilder("List of characters: \n");
                            for (a = 0; a < temp.length; a++) {
                                t.append(temp[a].getName()).append("\n");
                            }
                            printmessage(t.toString(), channel);
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
                    if (m[0].equals("setclassdescription")) // name "description"
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setclassdescription \"name\" \"description\"", channel);
                        } else {
                            if (store.hasClass(m[1])) {
                                store.getClass(m[1]).setDescription(m[2]);
                                printmessage(store.getClass(m[1]).getStatus(), channel);
                            } else {
                                printmessage("Class does not exist.", channel);
                            }
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
                                    if (store.getClass(m[1]).getPromotionPaths().get(a).getName().equals(m[2])) {
                                        printmessage("Class already has this promotion path.", channel);
                                        return;
                                    }
                                }
                                if (store.hasClass(m[2])) {
                                    store.getClass(m[1]).addPromotionPath(store.getClass(m[2]));
                                    printmessage(store.getClass(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("The class you are trying to add does not exist.", channel);
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
                            printmessage("Invalid argument amount. Usage: !setclassrankbonus \"name\" weapon bonus", channel);
                        } else {
                            if (store.hasClass(m[1])) {
                                String regex = "\\d+";
                                if (m[3].matches(regex)) {
                                    int bonus = Integer.parseInt(m[3]);
                                    if (bonus > 0 && bonus < 7) {
                                        switch (m[2]) {
                                            case "Sword":
                                                store.getClass(m[1]).setPromoWeaponRankBonus(0, bonus);
                                                store.saveData();
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                break;
                                            case "Lance":
                                                store.getClass(m[1]).setPromoWeaponRankBonus(1, bonus);
                                                store.saveData();
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                break;
                                            case "Axe":
                                                store.getClass(m[1]).setPromoWeaponRankBonus(2, bonus);
                                                store.saveData();
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                break;
                                            case "Anima":
                                                store.getClass(m[1]).setPromoWeaponRankBonus(4, bonus);
                                                store.saveData();
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                break;
                                            case "Light":
                                                store.getClass(m[1]).setPromoWeaponRankBonus(5, bonus);
                                                store.saveData();
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                break;
                                            case "Dark":
                                                store.getClass(m[1]).setPromoWeaponRankBonus(6, bonus);
                                                store.saveData();
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                break;
                                            case "Bow":
                                                store.getClass(m[1]).setPromoWeaponRankBonus(3, bonus);
                                                store.saveData();
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                break;
                                            case "Staff":
                                                store.getClass(m[1]).setPromoWeaponRankBonus(7, bonus);
                                                store.saveData();
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                break;
                                            default:
                                                printmessage("Invalid weapon type inputted. The bot currently only supports: \"Sword, Lance, Axe, Bow, Anima, Light, Dark, Staff\".", channel);
                                                break;
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
                            if (store.hasClass(m[1])) {
                                int a;
                                for (a = 0; a < store.getClass(m[1]).getBonusSkills().size(); a++) {
                                    if (store.getClass(m[1]).getBonusSkills().get(a).getName().equals(m[2])) {
                                        printmessage("Class already has this skill.", channel);
                                        return;
                                    }
                                }
                                if (store.hasSkill(m[2])) {
                                    store.getClass(m[1]).addSkill(store.getSkill(m[2]));
                                    printmessage(store.getClass(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("The skill you are trying to add does not exist.", channel);
                                }
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
                            if (store.hasClass(m[1])) {
                                int a;
                                for (a = 0; a < store.getClass(m[1]).getBonusSkills().size(); a++) {
                                    if (store.getClass(m[1]).getBonusSkills().get(a).getName().equals(m[2])) {
                                        store.getClass(m[1]).removeSkill(store.getClass(m[1]).getBonusSkills().get(a));
                                        printmessage(store.getClass(m[1]).getStatus(), channel);
                                        return;
                                    }
                                }
                                printmessage("Class does not have this skill.", channel);
                            } else {
                                printmessage("Class does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setclassstatbonus")) // name stat bonus
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !setclassstatbonus \"name\" stat bonus", channel);
                        } else {
                            if (store.hasClass(m[1])) {
                                String regex = "\\d+";
                                if (m[2].matches(regex)) {
                                    if (m[3].matches(regex)) {
                                        int bonus = Integer.parseInt(m[3]);
                                        int stat = Integer.parseInt(m[2]);
                                        store.getClass(m[1]).setClassBonus(stat, bonus);
                                        printmessage(store.getClass(m[1]).getStatus(), channel);
                                    } else {
                                        printmessage("Invalid stat bonus input. Ensure it is a number.", channel);
                                    }
                                } else {
                                    printmessage("Invalid stat. Ensure it is a number.", channel);
                                }
                            } else {
                                printmessage("Class does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setclasstier")) // name tier
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setclasstier \"name\" tier", channel);
                        } else {
                            if (store.hasClass(m[1])) {
                                String regex = "\\d+";
                                if (m[2].matches(regex)) {
                                    int tier = Integer.parseInt(m[2]);
                                    store.getClass(m[1]).setTier(tier);
                                    printmessage(store.getClass(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Invalid tier. Ensure it is a number.", channel);
                                }
                            } else {
                                printmessage("Class does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setclasspromogain")) // name stat bonus
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !setclasspromogain \"name\" stat bonus", channel);
                        } else {
                            if (store.hasClass(m[1])) {
                                String regex = "\\d+";
                                if (m[2].matches(regex)) {
                                    if (m[3].matches(regex)) {
                                        int bonus = Integer.parseInt(m[3]);
                                        int stat = Integer.parseInt(m[2]);
                                        store.getClass(m[1]).setPromotionGain(stat, bonus);
                                        printmessage(store.getClass(m[1]).getStatus(), channel);
                                    } else {
                                        printmessage("Invalid stat bonus input. Ensure it is a number.", channel);
                                    }
                                } else {
                                    printmessage("Invalid stat. Ensure it is a number.", channel);
                                }
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
                            if (store.hasClass(m[1])) {
                                StringBuilder mess = new StringBuilder();
                                mess.append("***Class Name: *** ").append(store.getClass(m[1]).getName()).append("\n");
                                mess.append("***Tier: *** ").append(store.getClass(m[1]).getTier()).append("\n");
                                mess.append("***Description: *** ").append(store.getClass(m[1]).getDescription()).append("\n");
                                mess.append("***Weapon Rank Bonuses: ***\n");
                                if (store.getClass(m[1]).getPromoWeaponRankBonus()[0] > 0) {
                                    if (store.getClass(m[1]).getPromoWeaponRankBonus()[0] == 1) {
                                        mess.append("Sword: E\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[0] == 2) {
                                        mess.append("Sword: D\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[0] == 3) {
                                        mess.append("Sword: C\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[0] == 4) {
                                        mess.append("Sword: B\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[0] == 5) {
                                        mess.append("Sword: A\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[0] == 6) {
                                        mess.append("Sword: S\n");
                                    }
                                }
                                if (store.getClass(m[1]).getPromoWeaponRankBonus()[1] > 0) {
                                    if (store.getClass(m[1]).getPromoWeaponRankBonus()[1] == 1) {
                                        mess.append("Lance: E\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[1] == 2) {
                                        mess.append("Lance: D\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[1] == 3) {
                                        mess.append("Lance: C\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[1] == 4) {
                                        mess.append("Lance: B\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[1] == 5) {
                                        mess.append("Lance: A\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[1] == 6) {
                                        mess.append("Lance: S\n");
                                    }
                                }
                                if (store.getClass(m[1]).getPromoWeaponRankBonus()[2] > 0) {
                                    if (store.getClass(m[1]).getPromoWeaponRankBonus()[2] == 1) {
                                        mess.append("Axe: E\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[2] == 2) {
                                        mess.append("Axe: D\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[2] == 3) {
                                        mess.append("Axe: C\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[2] == 4) {
                                        mess.append("Axe: B\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[2] == 5) {
                                        mess.append("Axe: A\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[2] == 6) {
                                        mess.append("Axe: S\n");
                                    }
                                }
                                if (store.getClass(m[1]).getPromoWeaponRankBonus()[3] > 0) {
                                    if (store.getClass(m[1]).getPromoWeaponRankBonus()[3] == 1) {
                                        mess.append("Bow: E\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[3] == 2) {
                                        mess.append("Bow: D\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[3] == 3) {
                                        mess.append("Bow: C\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[3] == 4) {
                                        mess.append("Bow: B\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[3] == 5) {
                                        mess.append("Bow: A\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[3] == 6) {
                                        mess.append("Bow: S\n");
                                    }
                                }
                                if (store.getClass(m[1]).getPromoWeaponRankBonus()[4] > 0) {
                                    if (store.getClass(m[1]).getPromoWeaponRankBonus()[4] == 1) {
                                        mess.append("Anima: E\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[4] == 2) {
                                        mess.append("Anima: D\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[4] == 3) {
                                        mess.append("Anima: C\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[4] == 4) {
                                        mess.append("Anima: B\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[4] == 5) {
                                        mess.append("Anima: A\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[4] == 6) {
                                        mess.append("Anima: S\n");
                                    }
                                }
                                if (store.getClass(m[1]).getPromoWeaponRankBonus()[5] > 0) {
                                    if (store.getClass(m[1]).getPromoWeaponRankBonus()[5] == 1) {
                                        mess.append("Light: E\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[5] == 2) {
                                        mess.append("Light: D\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[5] == 3) {
                                        mess.append("Light: C\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[5] == 4) {
                                        mess.append("Light: B\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[5] == 5) {
                                        mess.append("Light: A\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[5] == 6) {
                                        mess.append("Light: S\n");
                                    }
                                }
                                if (store.getClass(m[1]).getPromoWeaponRankBonus()[6] > 0) {
                                    if (store.getClass(m[1]).getPromoWeaponRankBonus()[6] == 1) {
                                        mess.append("Dark: E\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[6] == 2) {
                                        mess.append("Dark: D\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[6] == 3) {
                                        mess.append("Dark: C\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[6] == 4) {
                                        mess.append("Dark: B\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[6] == 5) {
                                        mess.append("Dark: A\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[6] == 6) {
                                        mess.append("Dark: S\n");
                                    }
                                }
                                if (store.getClass(m[1]).getPromoWeaponRankBonus()[7] > 0) {
                                    if (store.getClass(m[1]).getPromoWeaponRankBonus()[7] == 1) {
                                        mess.append("Staff: E\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[7] == 2) {
                                        mess.append("Staff: D\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[7] == 3) {
                                        mess.append("Staff: C\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[7] == 4) {
                                        mess.append("Staff: B\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[7] == 5) {
                                        mess.append("Staff: A\n");
                                    } else if (store.getClass(m[1]).getPromoWeaponRankBonus()[7] == 6) {
                                        mess.append("Staff: S\n");
                                    }
                                }
                                int a;
                                mess.append("***Bonus Skills: ***\n");
                                for (a = 0; a < store.getClass(m[1]).getBonusSkills().size(); a++) {
                                    mess.append(store.getClass(m[1]).getBonusSkills().get(a).getName()).append(", ");
                                }
                                mess.append("\n");
                                mess.append("***Promotion Bonuses: ***\n");
                                if (store.getClass(m[1]).getPromotionGains()[0] > 0) {
                                    mess.append("HP: ").append(store.getClass(m[1]).getPromotionGains()[0]).append("\n");
                                }
                                if (store.getClass(m[1]).getPromotionGains()[1] > 0) {
                                    mess.append("STR: ").append(store.getClass(m[1]).getPromotionGains()[1]).append("\n");
                                }
                                if (store.getClass(m[1]).getPromotionGains()[2] > 0) {
                                    mess.append("MAG: ").append(store.getClass(m[1]).getPromotionGains()[2]).append("\n");
                                }
                                if (store.getClass(m[1]).getPromotionGains()[3] > 0) {
                                    mess.append("SKL: ").append(store.getClass(m[1]).getPromotionGains()[3]).append("\n");
                                }
                                if (store.getClass(m[1]).getPromotionGains()[4] > 0) {
                                    mess.append("SPD: ").append(store.getClass(m[1]).getPromotionGains()[4]).append("\n");
                                }
                                if (store.getClass(m[1]).getPromotionGains()[5] > 0) {
                                    mess.append("LUCK: ").append(store.getClass(m[1]).getPromotionGains()[5]).append("\n");
                                }
                                if (store.getClass(m[1]).getPromotionGains()[6] > 0) {
                                    mess.append("DEF: ").append(store.getClass(m[1]).getPromotionGains()[6]).append("\n");
                                }
                                if (store.getClass(m[1]).getPromotionGains()[7] > 0) {
                                    mess.append("RES: ").append(store.getClass(m[1]).getPromotionGains()[7]).append("\n");
                                }
                                printmessage(mess.toString(), channel);
                            } else {
                                printmessage("Class does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setclassattribute")) // name attribute true/false
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !setclassattribute \"name\" attribute true/false", channel);
                        } else {
                            if (store.hasClass(m[1])) {
                                switch (m[2]) {
                                    case "mounted":
                                        switch (m[3]) {
                                            case "true":
                                                store.getClass(m[1]).setMounted(true);
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                store.saveData();
                                                break;
                                            case "false":
                                                store.getClass(m[1]).setMounted(false);
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                store.saveData();
                                                break;
                                            default:
                                                printmessage("Please use true or false.", channel);
                                                break;
                                        }
                                        break;
                                    case "flier":
                                        switch (m[3]) {
                                            case "true":
                                                store.getClass(m[1]).setFlying(true);
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                store.saveData();
                                                break;
                                            case "false":
                                                store.getClass(m[1]).setFlying(false);
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                store.saveData();
                                                break;
                                            default:
                                                printmessage("Please use true or false.", channel);
                                                break;
                                        }
                                        break;
                                    case "armored":
                                        switch (m[3]) {
                                            case "true":
                                                store.getClass(m[1]).setArmored(true);
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                store.saveData();
                                                break;
                                            case "false":
                                                store.getClass(m[1]).setArmored(false);
                                                printmessage(store.getClass(m[1]).getStatus(), channel);
                                                store.saveData();
                                                break;
                                            default:
                                                printmessage("Please use true or false.", channel);
                                                break;
                                        }
                                        break;
                                    default:
                                        printmessage("Unknown attribute. The bot currently only supports: Flier, Armored, Mounted", channel);
                                        break;
                                }
                            } else {
                                printmessage("Class does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setclassbonusrankfixed")) // name true/false
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setclassbonusrankfixed \"name\" true/false", channel);
                        } else {
                            if (store.hasClass(m[1])) {
                                switch (m[2]) {
                                    case "true":
                                        store.getClass(m[1]).setBonusWeaponRankToBeFixed(true);
                                        store.saveData();
                                        printmessage(store.getClass(m[1]).getStatus(), channel);
                                        break;
                                    case "false":
                                        store.getClass(m[1]).setBonusWeaponRankToBeFixed(false);
                                        store.saveData();
                                        printmessage(store.getClass(m[1]).getStatus(), channel);
                                        break;
                                    default:
                                        printmessage("Please use true or false.", channel);
                                        break;
                                }
                            } else {
                                printmessage("Class does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("createpassiveskill")) // name
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !createpassiveskill \"name\"", channel);
                        } else {
                            PassiveSkill temp = new PassiveSkill(m[1]);
                            store.addSkill(m[1], temp);
                            store.saveData();
                            printmessage(temp.getStatus(), channel);
                        }
                    }
                    if (m[0].equals("createprocskill")) // name type
                    {
                        if (m.length != 6) {
                            printmessage("Invalid argument amount. Usage: !createskill \"name\" procrate damagemultiplier activationstat inputstat", channel);
                        } else {
                            double procrate;
                            double dammulti;
                            int activationstat;
                            int inputstat;
                            String regex;
                            regex = "^\\d*\\.?\\d*$";
                            if (m[2].matches(regex) && m[3].matches(regex)) {
                                procrate = Double.parseDouble(m[2]);
                                dammulti = Double.parseDouble(m[3]);
                                switch (m[4]) {
                                    case "hp":
                                        activationstat = 0;
                                        break;
                                    case "strength":
                                        activationstat = 1;
                                        break;
                                    case "magic":
                                        activationstat = 2;
                                        break;
                                    case "skill":
                                        activationstat = 3;
                                        break;
                                    case "speed":
                                        activationstat = 4;
                                        break;
                                    case "luck":
                                        activationstat = 5;
                                        break;
                                    case "defense":
                                        activationstat = 6;
                                        break;
                                    case "resistance":
                                        activationstat = 7;
                                        break;
                                    default:
                                        printmessage("Invalid activation stat. Ensure it is one of these: hp, strength, magic, skill, speed, luck, defense, resistance", channel);
                                        return;
                                }
                                switch (m[5]) {
                                    case "hp":
                                        inputstat = 0;
                                        break;
                                    case "strength":
                                        inputstat = 1;
                                        break;
                                    case "magic":
                                        inputstat = 2;
                                        break;
                                    case "skill":
                                        inputstat = 3;
                                        break;
                                    case "speed":
                                        inputstat = 4;
                                        break;
                                    case "luck":
                                        inputstat = 5;
                                        break;
                                    case "defense":
                                        inputstat = 6;
                                        break;
                                    case "resistance":
                                        inputstat = 7;
                                        break;
                                    default:
                                        printmessage("Invalid input stat. Ensure it is one of these: hp, strength, magic, skill, speed, luck, defense, resistance", channel);
                                        return;
                                }
                                ProcSkill temp = new ProcSkill(m[1], procrate, dammulti, activationstat, inputstat);
                                store.addSkill(m[1], temp);
                                store.saveData();
                                printmessage(temp.getStatus(), channel);
                            } else {
                                printmessage("Invalid procrate or damage multiplier. Ensure they are numbers.", channel);
                            }
                        }
                    }
                    if (m[0].equals("createmultihitskill")) // name type
                    {
                        if (m.length != 6) {
                            printmessage("Invalid argument amount. Usage: !createmultihitskill \"name\" hitcount procrate damagemulti inputstat", channel);
                        } else {
                            int hitcount;
                            double procrate;
                            double damagemulti;
                            int inputstat;
                            String regex = "\\d+";
                            String doubleregex;
                            doubleregex = "^\\d*\\.?\\d*$";
                            if (m[3].matches(regex)) {
                                hitcount = Integer.parseInt(m[3]);
                            } else {
                                printmessage("Invalid hitcount. Ensure it is a number.", channel);
                                return;
                            }
                            if (m[4].matches(doubleregex) && m[5].matches(doubleregex)) {
                                procrate = Double.parseDouble(m[4]);
                                damagemulti = Double.parseDouble(m[5]);
                            } else {
                                printmessage("Invalid damage multiplier or proc rate. Ensure it is a number or decimal.", channel);
                                return;
                            }
                            switch (m[6]) {
                                case "hp":
                                    inputstat = 0;
                                    break;
                                case "strength":
                                    inputstat = 1;
                                    break;
                                case "magic":
                                    inputstat = 2;
                                    break;
                                case "skill":
                                    inputstat = 3;
                                    break;
                                case "speed":
                                    inputstat = 4;
                                    break;
                                case "luck":
                                    inputstat = 5;
                                    break;
                                case "defense":
                                    inputstat = 6;
                                    break;
                                case "resistance":
                                    inputstat = 7;
                                    break;
                                default:
                                    printmessage("Invalid input stat. Ensure it is one of these: hp, strength, magic, skill, speed, luck, defense, resistance", channel);
                                    return;
                            }
                            MultiHitSkill temp = new MultiHitSkill(m[1], hitcount, procrate, damagemulti, inputstat);
                            store.addSkill(m[1], temp);
                            store.saveData();
                            printmessage(temp.getStatus(), channel);
                        }
                    }
                    if (m[0].equals("createinitiationskill")) // name type
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !createinitiationskill \"name\" buff stat", channel);
                        } else {
                            int inputstat;
                            int buff = 0;
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                buff = Integer.parseInt(m[2]);
                            } else {
                                printmessage("Invalid buff amount. Ensure it is a number.", channel);
                            }
                            switch (m[3]) {
                                case "hp":
                                    inputstat = 0;
                                    break;
                                case "strength":
                                    inputstat = 1;
                                    break;
                                case "magic":
                                    inputstat = 2;
                                    break;
                                case "skill":
                                    inputstat = 3;
                                    break;
                                case "speed":
                                    inputstat = 4;
                                    break;
                                case "luck":
                                    inputstat = 5;
                                    break;
                                case "defense":
                                    inputstat = 6;
                                    break;
                                case "resistance":
                                    inputstat = 7;
                                    break;
                                case "hit":
                                    inputstat = 8;
                                    break;
                                case "avoid":
                                    inputstat = 9;
                                    break;
                                case "crit":
                                    inputstat = 10;
                                    break;
                                case "physdamtake":
                                    inputstat = 11;
                                    break;
                                case "magdamtake":
                                    inputstat = 12;
                                    break;
                                case "damtake":
                                    inputstat = 13;
                                    break;
                                case "physdamdeal":
                                    inputstat = 14;
                                    break;
                                case "magdamdeal":
                                    inputstat = 15;
                                    break;
                                case "damdeal":
                                    inputstat = 16;
                                    break;
                                default:
                                    printmessage("Invalid input stat. Ensure it is one of these: hp, strength, magic, skill, speed, luck, defense, resistance, hit, avoid, crit, physdamtake, magdamtake, damtake, physdamdeal, magdamdeal, damdeal", channel);
                                    return;
                            }
                            InitiationSkill temp = new InitiationSkill(m[1]);
                            temp.setBuff(inputstat, buff);
                            store.addSkill(m[1], temp);
                            store.saveData();
                            printmessage(temp.getStatus(), channel);
                        }
                    }
                    if (m[0].equals("createdefensiveskill")) // name type
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !createinitiationskill \"name\" buff stat", channel);
                        } else {
                            int inputstat;
                            int buff = 0;
                            String regex = "\\d+";
                            if (m[2].matches(regex)) {
                                buff = Integer.parseInt(m[2]);
                            } else {
                                printmessage("Invalid buff amount. Ensure it is a number.", channel);
                            }
                            switch (m[3]) {
                                case "hp":
                                    inputstat = 0;
                                    break;
                                case "strength":
                                    inputstat = 1;
                                    break;
                                case "magic":
                                    inputstat = 2;
                                    break;
                                case "skill":
                                    inputstat = 3;
                                    break;
                                case "speed":
                                    inputstat = 4;
                                    break;
                                case "luck":
                                    inputstat = 5;
                                    break;
                                case "defense":
                                    inputstat = 6;
                                    break;
                                case "resistance":
                                    inputstat = 7;
                                    break;
                                case "hit":
                                    inputstat = 8;
                                    break;
                                case "avoid":
                                    inputstat = 9;
                                    break;
                                case "crit":
                                    inputstat = 10;
                                    break;
                                case "physdamtake":
                                    inputstat = 11;
                                    break;
                                case "magdamtake":
                                    inputstat = 12;
                                    break;
                                case "damtake":
                                    inputstat = 13;
                                    break;
                                case "physdamdeal":
                                    inputstat = 14;
                                    break;
                                case "magdamdeal":
                                    inputstat = 15;
                                    break;
                                case "damdeal":
                                    inputstat = 16;
                                    break;
                                default:
                                    printmessage("Invalid input stat. Ensure it is one of these: hp, strength, magic, skill, speed, luck, defense, resistance, hit, avoid, crit, physdamtake, magdamtake, damtake, physdamdeal, magdamdeal, damdeal", channel);
                                    return;
                            }
                            DefensiveSkill temp = new DefensiveSkill(m[1]);
                            temp.setBuff(inputstat, buff);
                            store.addSkill(m[1], temp);
                            store.saveData();
                            printmessage(temp.getStatus(), channel);
                        }
                    }
                    if (m[0].equals("createbreakerskill")) // name type
                    {
                        if (m.length != 5) {
                            printmessage("Invalid argument amount. Usage: !createbreakerskill \"name\" weapon stat advantage", channel);
                        } else {
                            int stat;
                            double advantage;
                            int weapon;
                            String regex;
                            regex = "^\\d*\\.?\\d*$";
                            if (m[4].matches(regex)) {
                                advantage = Double.parseDouble(m[4]);
                            } else {
                                printmessage("Invalid advantage number. Ensure it is a number.", channel);
                                return;
                            }
                            switch (m[2]) {
                                case "Sword":
                                    weapon = 0;
                                    break;
                                case "Lance":
                                    weapon = 1;
                                    break;
                                case "Axe":
                                    weapon = 2;
                                    break;
                                case "Anima":
                                    weapon = 3;
                                    break;
                                case "Light":
                                    weapon = 4;
                                    break;
                                case "Dark":
                                    weapon = 5;
                                    break;
                                case "Bow":
                                    weapon = 6;
                                    break;
                                default:
                                    printmessage("Invalid weapon type inputted. The bot currently only supports: \"Sword, Lance, Axe, Bow, Anima, Light, Dark\".", channel);
                                    return;
                            }
                            switch (m[3]) {
                                case "hit":
                                    stat = 0;
                                    break;
                                case "avoid":
                                    stat = 1;
                                    break;
                                case "crit":
                                    stat = 2;
                                    break;
                                case "damdeal":
                                    stat = 3;
                                    break;
                                case "damtake":
                                    stat = 4;
                                    break;
                                case "dammulti":
                                    stat = 5;
                                    break;
                                default:
                                    printmessage("Invalid stat inputted. The bot currently only supports: \"hit, avoid, crit, damdeal, damtake, dammulti\"", channel);
                                    return;
                            }
                            WeaponBreaker temp = new WeaponBreaker(m[1], weapon);
                            temp.setAdvantage(stat, advantage);
                            store.addSkill(m[1], temp);
                            store.saveData();
                            printmessage(temp.getStatus(), channel);
                        }
                    }
                    if (m[0].equals("createslayerskill")) // name type
                    {
                        if (m.length != 5) {
                            printmessage("Invalid argument amount. Usage: !createslayerskill \"name\" race stat advantage", channel);
                        } else {
                            double advantage;
                            int stat;
                            String regex = "^\\d*\\.?\\d*$";
                            if (m[4].matches(regex)) {
                                advantage = Double.parseDouble(m[4]);
                            } else {
                                printmessage("Invalid advantage number. Ensure it is a number.", channel);
                                return;
                            }
                            switch (m[3]) {
                                case "hit":
                                    stat = 0;
                                    break;
                                case "avoid":
                                    stat = 1;
                                    break;
                                case "crit":
                                    stat = 2;
                                    break;
                                case "damdeal":
                                    stat = 3;
                                    break;
                                case "damtake":
                                    stat = 4;
                                    break;
                                case "dammulti":
                                    stat = 5;
                                    break;
                                default:
                                    printmessage("Invalid stat inputted. The bot currently only supports: \"hit, avoid, crit, damdeal, damtake, dammulti\"", channel);
                                    return;
                            }
                            RaceSlayer temp = new RaceSlayer(m[1], m[2]);
                            temp.setStatAdvantage(stat, advantage);
                            store.addSkill(m[1], temp);
                            store.saveData();
                            printmessage(temp.getStatus(), channel);

                        }
                    }
                    if (m[0].equals("createmiracleskill")) // name type
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !createskill \"name\" stat multiplier", channel);
                        } else {
                            double multiplier;
                            int inputstat;
                            String regex = "^\\d*\\.?\\d*$";
                            if (m[3].matches(regex)) {
                                multiplier = Double.parseDouble(m[3]);
                            } else {
                                printmessage("Invalid stat multiplier. Ensure it is a number.", channel);
                                return;
                            }
                            switch (m[2]) {
                                case "hp":
                                    inputstat = 0;
                                    break;
                                case "strength":
                                    inputstat = 1;
                                    break;
                                case "magic":
                                    inputstat = 2;
                                    break;
                                case "skill":
                                    inputstat = 3;
                                    break;
                                case "speed":
                                    inputstat = 4;
                                    break;
                                case "luck":
                                    inputstat = 5;
                                    break;
                                case "defense":
                                    inputstat = 6;
                                    break;
                                case "resistance":
                                    inputstat = 7;
                                    break;
                                default:
                                    printmessage("Invalid stat inputted. Ensure it is one of the following: hp, strength, magic, skill, speed, luck, defense, resistance.", channel);
                                    return;
                            }
                            Miracle temp = new Miracle(m[1], multiplier);
                            temp.setActivationStat(inputstat);
                            store.addSkill(m[1], temp);
                            store.saveData();
                            printmessage(temp.getStatus(), channel);
                        }
                    }
                    if (m[0].equals("deleteskill")) // name
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !deleteskill \"name\"", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                int a;
                                Character[] temp = store.getcharlist();
                                Class[] tempclass = store.getclasslist();
                                for (a = 0; a < temp.length; a++) {
                                    if (temp[a].getSkilllist().hasSkill(store.getSkill(m[1]))) {
                                        store.getCharacter(temp[a].getName()).removeSkill(store.getSkill(m[1]));
                                    }
                                }
                                for (a = 0; a < tempclass.length; a++) {
                                    if (tempclass[a].getBonusSkills().contains(store.getSkill(m[1]))) {
                                        store.getClass(tempclass[a].getName()).removeSkill(store.getSkill(m[1]));
                                    }
                                }
                                store.removeSkill(m[1]);
                                printmessage("Skill removed.", channel);
                                store.saveData();
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setskilldesc")) // skill "description"
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setskilldesc \"name\" \"description\"", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                store.getSkill(m[1]).setDescription(m[2]);
                                store.saveData();
                                printmessage(store.getSkill(m[1]).getStatus(), channel);
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("listskills"))
                    {
                        if (m.length != 1) {
                            printmessage("Invalid argument amount. Usage: !listskills", channel);
                        } else {
                            Skill[] temp = store.getskilllist();
                            StringBuilder list = new StringBuilder("**List of Skills:**\n");
                            int a;
                            for (a = 0; a < temp.length; a++) {
                                list.append(temp[a].getName());
                                if (temp[a].isAttackMulti()) {
                                    list.append(" (Attack Multiplier)");
                                } else if (temp[a].isBreaker()) {
                                    list.append(" (Breaker)");
                                } else if (temp[a].isDefensive()) {
                                    list.append(" (Defensive)");
                                } else if (temp[a].isMiracle()) {
                                    list.append(" (Miracle)");
                                } else if (temp[a].isOffensive()) {
                                    list.append(" (Initiation)");
                                } else if (temp[a].isPassive()) {
                                    list.append(" (Passive)");
                                } else if (temp[a].isProc()) {
                                    list.append(" (Proc)");
                                } else if (temp[a].isSlayer()) {
                                    list.append(" (Slayer)");
                                } else if (temp[a].isThreshhold()) {
                                    list.append(" (Threshold Activated)");
                                }
                                list.append("\n");
                            }
                            printmessage(list.toString(), channel);
                        }
                    }
                    if (m[0].equals("skillinfo")) // skill
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !skillinfo \"name\"", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                String temp = "";
                                temp += "***" + store.getSkill(m[1]).getName() + "***" + "\n";
                                temp += "***Description: ***" + store.getSkill(m[1]).getDescription();
                                if (store.getSkill(m[1]).isAttackMulti()) {
                                    temp += "**Type:** Attack Multiplier\n";
                                    MultiHitSkill tempskill = (MultiHitSkill) store.getSkill(m[1]);
                                    temp += "**Hit Count: ** " + tempskill.getAttackCount() + "\n";
                                    temp += "**Activation Stat:** ";
                                    switch (tempskill.getActivationstat()) {
                                        case 0:
                                            temp += "HP";
                                            break;
                                        case 1:
                                            temp += "Strength";
                                            break;
                                        case 2:
                                            temp += "Magic";
                                            break;
                                        case 3:
                                            temp += "Skill";
                                            break;
                                        case 4:
                                            temp += "Speed";
                                            break;
                                        case 5:
                                            temp += "Luck";
                                            break;
                                        case 6:
                                            temp += "Defense";
                                            break;
                                        case 7:
                                            temp += "Resistance";
                                            break;
                                    }
                                    temp += " x " + tempskill.getProcRate() + "%\n";
                                    temp += "**Damage Multiplier:** " + tempskill.getDamageMultiplier() + "\n";
                                } else if (store.getSkill(m[1]).isBreaker()) {
                                    temp += "**Type:** Breaker\n";
                                    WeaponBreaker tempskill = (WeaponBreaker) store.getSkill(m[1]);
                                    switch (tempskill.getEffectiveWeapon()) {
                                        case 0:
                                            temp += "**Effective against: **Sword\n";
                                            break;
                                        case 1:
                                            temp += "**Effective against: **Lance\n";
                                            break;
                                        case 2:
                                            temp += "**Effective against: **Axe\n";
                                            break;
                                        case 3:
                                            temp += "**Effective against: **Anima\n";
                                            break;
                                        case 4:
                                            temp += "**Effective against: **Light\n";
                                            break;
                                        case 5:
                                            temp += "**Effective against: **Dark\n";
                                            break;
                                        case 6:
                                            temp += "**Effective against: **Bow\n";
                                            break;
                                    }
                                    if (tempskill.getAdvantage()[0] > 0) {
                                        temp += "**Hit Bonus:** " + tempskill.getAdvantage()[0] + "\n";
                                    }
                                    if (tempskill.getAdvantage()[1] > 0) {
                                        temp += "**Avoid Bonus:** " + tempskill.getAdvantage()[1] + "\n";
                                    }
                                    if (tempskill.getAdvantage()[2] > 0) {
                                        temp += "**Crit Bonus:** " + tempskill.getAdvantage()[2] + "\n";
                                    }
                                    if (tempskill.getAdvantage()[3] > 0) {
                                        temp += "**Bonus to Damage Dealt:** " + tempskill.getAdvantage()[3] + "\n";
                                    }
                                    if (tempskill.getAdvantage()[4] > 0) {
                                        temp += "**Bonus to Damage Dealt (multiplicative):** " + tempskill.getAdvantage()[4] + "\n";
                                    }
                                    if (tempskill.getAdvantage()[5] > 0) {
                                        temp += "**Damage Reduction:** " + tempskill.getAdvantage()[5] + "\n";
                                    }
                                } else if (store.getSkill(m[1]).isDefensive()) {
                                    temp += "**Type:** Defensive\n";
                                    DefensiveSkill tempskill = (DefensiveSkill) store.getSkill(m[1]);
                                    if (tempskill.getBuff()[0] > 0) {
                                        temp += "**Bonus to HP:** " + tempskill.getBuff()[0] + "\n";
                                    }
                                    if (tempskill.getBuff()[1] > 0) {
                                        temp += "**Bonus to Strength:** " + tempskill.getBuff()[1] + "\n";
                                    }
                                    if (tempskill.getBuff()[2] > 0) {
                                        temp += "**Bonus to Magic:** " + tempskill.getBuff()[2] + "\n";
                                    }
                                    if (tempskill.getBuff()[3] > 0) {
                                        temp += "**Bonus to Skill:** " + tempskill.getBuff()[3] + "\n";
                                    }
                                    if (tempskill.getBuff()[4] > 0) {
                                        temp += "**Bonus to Speed:** " + tempskill.getBuff()[4] + "\n";
                                    }
                                    if (tempskill.getBuff()[5] > 0) {
                                        temp += "**Bonus to Luck:** " + tempskill.getBuff()[5] + "\n";
                                    }
                                    if (tempskill.getBuff()[6] > 0) {
                                        temp += "**Bonus to Defense:** " + tempskill.getBuff()[6] + "\n";
                                    }
                                    if (tempskill.getBuff()[7] > 0) {
                                        temp += "**Bonus to Resistance:** " + tempskill.getBuff()[7] + "\n";
                                    }
                                    if (tempskill.getBuff()[8] > 0) {
                                        temp += "**Bonus to Hit:** " + tempskill.getBuff()[8] + "\n";
                                    }
                                    if (tempskill.getBuff()[9] > 0) {
                                        temp += "**Bonus to Avoid:** " + tempskill.getBuff()[9] + "\n";
                                    }
                                    if (tempskill.getBuff()[10] > 0) {
                                        temp += "**Bonus to Crit:** " + tempskill.getBuff()[10] + "\n";
                                    }
                                    if (tempskill.getBuff()[11] > 0) {
                                        temp += "**Bonus to Physical Damage Taken:** " + tempskill.getBuff()[11] + "\n";
                                    }
                                    if (tempskill.getBuff()[12] > 0) {
                                        temp += "**Bonus to Magical Damage Taken:** " + tempskill.getBuff()[12] + "\n";
                                    }
                                    if (tempskill.getBuff()[13] > 0) {
                                        temp += "**Bonus to Damage Taken:** " + tempskill.getBuff()[13] + "\n";
                                    }
                                    if (tempskill.getBuff()[14] > 0) {
                                        temp += "**Bonus to Physical Damage Dealt:** " + tempskill.getBuff()[14] + "\n";
                                    }
                                    if (tempskill.getBuff()[15] > 0) {
                                        temp += "**Bonus to Magical Damage Dealt:** " + tempskill.getBuff()[15] + "\n";
                                    }
                                    if (tempskill.getBuff()[16] > 0) {
                                        temp += "**Bonus to Damage Dealt:** " + tempskill.getBuff()[16] + "\n";
                                    }
                                } else if (store.getSkill(m[1]).isMiracle()) {
                                    temp += "**Type:** Miracle\n";
                                    Miracle tempskill = (Miracle) store.getSkill(m[1]);
                                    switch (tempskill.getActivationstat()) {
                                        case 0:
                                            temp += "**Activation Chance:** HP";
                                            break;
                                        case 1:
                                            temp += "**Activation Chance:** Strength";
                                            break;
                                        case 2:
                                            temp += "**Activation Chance:** Magic";
                                            break;
                                        case 3:
                                            temp += "**Activation Chance:** Skill";
                                            break;
                                        case 4:
                                            temp += "**Activation Chance:** Speed";
                                            break;
                                        case 5:
                                            temp += "**Activation Chance:** Luck";
                                            break;
                                        case 6:
                                            temp += "**Activation Chance:** Defense";
                                            break;
                                        case 7:
                                            temp += "**Activation Chance:** Resistance";
                                            break;
                                    }
                                    temp += " x " + tempskill.getLuckMultiplier() + "%\n";
                                } else if (store.getSkill(m[1]).isOffensive()) {
                                    temp += "**Type:** Initiation\n";
                                    InitiationSkill tempskill = (InitiationSkill) store.getSkill(m[1]);
                                    if (tempskill.getBuff()[0] > 0) {
                                        temp += "**Bonus to HP:** " + tempskill.getBuff()[0] + "\n";
                                    }
                                    if (tempskill.getBuff()[1] > 0) {
                                        temp += "**Bonus to Strength:** " + tempskill.getBuff()[1] + "\n";
                                    }
                                    if (tempskill.getBuff()[2] > 0) {
                                        temp += "**Bonus to Magic:** " + tempskill.getBuff()[2] + "\n";
                                    }
                                    if (tempskill.getBuff()[3] > 0) {
                                        temp += "**Bonus to Skill:** " + tempskill.getBuff()[3] + "\n";
                                    }
                                    if (tempskill.getBuff()[4] > 0) {
                                        temp += "**Bonus to Speed:** " + tempskill.getBuff()[4] + "\n";
                                    }
                                    if (tempskill.getBuff()[5] > 0) {
                                        temp += "**Bonus to Luck:** " + tempskill.getBuff()[5] + "\n";
                                    }
                                    if (tempskill.getBuff()[6] > 0) {
                                        temp += "**Bonus to Defense:** " + tempskill.getBuff()[6] + "\n";
                                    }
                                    if (tempskill.getBuff()[7] > 0) {
                                        temp += "**Bonus to Resistance:** " + tempskill.getBuff()[7] + "\n";
                                    }
                                    if (tempskill.getBuff()[8] > 0) {
                                        temp += "**Bonus to Hit:** " + tempskill.getBuff()[8] + "\n";
                                    }
                                    if (tempskill.getBuff()[9] > 0) {
                                        temp += "**Bonus to Avoid:** " + tempskill.getBuff()[9] + "\n";
                                    }
                                    if (tempskill.getBuff()[10] > 0) {
                                        temp += "**Bonus to Crit:** " + tempskill.getBuff()[10] + "\n";
                                    }
                                    if (tempskill.getBuff()[11] > 0) {
                                        temp += "**Bonus to Physical Damage Taken:** " + tempskill.getBuff()[11] + "\n";
                                    }
                                    if (tempskill.getBuff()[12] > 0) {
                                        temp += "**Bonus to Magical Damage Taken:** " + tempskill.getBuff()[12] + "\n";
                                    }
                                    if (tempskill.getBuff()[13] > 0) {
                                        temp += "**Bonus to Damage Taken:** " + tempskill.getBuff()[13] + "\n";
                                    }
                                    if (tempskill.getBuff()[14] > 0) {
                                        temp += "**Bonus to Physical Damage Dealt:** " + tempskill.getBuff()[14] + "\n";
                                    }
                                    if (tempskill.getBuff()[15] > 0) {
                                        temp += "**Bonus to Magical Damage Dealt:** " + tempskill.getBuff()[15] + "\n";
                                    }
                                    if (tempskill.getBuff()[16] > 0) {
                                        temp += "**Bonus to Damage Dealt:** " + tempskill.getBuff()[16] + "\n";
                                    }
                                } else if (store.getSkill(m[1]).isPassive()) {
                                    temp += "**Type:** Passive\n";
                                    PassiveSkill tempskill = (PassiveSkill) store.getSkill(m[1]);
                                    if (tempskill.isVantage()) {
                                        temp += "**Vantage-Type:** Yes\n";
                                    }
                                    if (tempskill.getBuff()[0] > 0) {
                                        temp += "**Bonus to HP:** " + tempskill.getBuff()[0] + "\n";
                                    }
                                    if (tempskill.getBuff()[1] > 0) {
                                        temp += "**Bonus to Strength:** " + tempskill.getBuff()[1] + "\n";
                                    }
                                    if (tempskill.getBuff()[2] > 0) {
                                        temp += "**Bonus to Magic:** " + tempskill.getBuff()[2] + "\n";
                                    }
                                    if (tempskill.getBuff()[3] > 0) {
                                        temp += "**Bonus to Skill:** " + tempskill.getBuff()[3] + "\n";
                                    }
                                    if (tempskill.getBuff()[4] > 0) {
                                        temp += "**Bonus to Speed:** " + tempskill.getBuff()[4] + "\n";
                                    }
                                    if (tempskill.getBuff()[5] > 0) {
                                        temp += "**Bonus to Luck:** " + tempskill.getBuff()[5] + "\n";
                                    }
                                    if (tempskill.getBuff()[6] > 0) {
                                        temp += "**Bonus to Defense:** " + tempskill.getBuff()[6] + "\n";
                                    }
                                    if (tempskill.getBuff()[7] > 0) {
                                        temp += "**Bonus to Resistance:** " + tempskill.getBuff()[7] + "\n";
                                    }
                                    if (tempskill.getBuff()[8] > 0) {
                                        temp += "**Bonus to Hit:** " + tempskill.getBuff()[8] + "\n";
                                    }
                                    if (tempskill.getBuff()[9] > 0) {
                                        temp += "**Bonus to Avoid:** " + tempskill.getBuff()[9] + "\n";
                                    }
                                    if (tempskill.getBuff()[10] > 0) {
                                        temp += "**Bonus to Crit:** " + tempskill.getBuff()[10] + "\n";
                                    }
                                    if (tempskill.getBuff()[11] > 0) {
                                        temp += "**Bonus to Physical Damage Taken:** " + tempskill.getBuff()[11] + "\n";
                                    }
                                    if (tempskill.getBuff()[12] > 0) {
                                        temp += "**Bonus to Magical Damage Taken:** " + tempskill.getBuff()[12] + "\n";
                                    }
                                    if (tempskill.getBuff()[13] > 0) {
                                        temp += "**Bonus to Damage Taken:** " + tempskill.getBuff()[13] + "\n";
                                    }
                                    if (tempskill.getBuff()[14] > 0) {
                                        temp += "**Bonus to Physical Damage Dealt:** " + tempskill.getBuff()[14] + "\n";
                                    }
                                    if (tempskill.getBuff()[15] > 0) {
                                        temp += "**Bonus to Magical Damage Dealt:** " + tempskill.getBuff()[15] + "\n";
                                    }
                                    if (tempskill.getBuff()[16] > 0) {
                                        temp += "**Bonus to Damage Dealt:** " + tempskill.getBuff()[16] + "\n";
                                    }
                                } else if (store.getSkill(m[1]).isProc()) {
                                    temp += "**Type:** Proc\n";
                                    ProcSkill tempskill = (ProcSkill) store.getSkill(m[1]);
                                    if (tempskill.isCancel()) {
                                        temp += "**Cancels enemy counterattacks.\n";
                                    }
                                    if (tempskill.isDamaging()) {
                                        temp += "**Deals bonus damage.**\n";
                                    } else {
                                        temp += "**Heals the user.**\n";
                                    }
                                    if (tempskill.isOnAttacked()) {
                                        temp += "**Activates when attacked.**\n";
                                    }
                                    if (tempskill.isUseEnemyStat()) {
                                        temp += "**Uses enemy stats for calculations.**\n";
                                    }
                                    switch (tempskill.getActivationstat()) {
                                        case 0:
                                            temp += "**Activation Chance:** HP";
                                            break;
                                        case 1:
                                            temp += "**Activation Chance:** Strength";
                                            break;
                                        case 2:
                                            temp += "**Activation Chance:** Magic";
                                            break;
                                        case 3:
                                            temp += "**Activation Chance:** Skill";
                                            break;
                                        case 4:
                                            temp += "**Activation Chance:** Speed";
                                            break;
                                        case 5:
                                            temp += "**Activation Chance:** Luck";
                                            break;
                                        case 6:
                                            temp += "**Activation Chance:** Defense";
                                            break;
                                        case 7:
                                            temp += "**Activation Chance:** Resistance";
                                            break;
                                    }
                                    temp += " x " + tempskill.getProcRate() + "%\n";
                                    temp += "**Damage Multiplier: **" + tempskill.getDamageMultiplier() + "\n";
                                } else if (store.getSkill(m[1]).isSlayer()) {
                                    temp += "**Type:** Slayer\n";
                                    RaceSlayer tempskill = (RaceSlayer) store.getSkill(m[1]);
                                    temp += "**Effective Against:** " + tempskill.getSlay() + "\n";
                                    if (tempskill.getStatAdvantage()[0] > 0) {
                                        temp += "**Hit Bonus:** " + tempskill.getStatAdvantage()[0] + "\n";
                                    }
                                    if (tempskill.getStatAdvantage()[1] > 0) {
                                        temp += "**Avoid Bonus:** " + tempskill.getStatAdvantage()[1] + "\n";
                                    }
                                    if (tempskill.getStatAdvantage()[2] > 0) {
                                        temp += "**Crit Bonus:** " + tempskill.getStatAdvantage()[2] + "\n";
                                    }
                                    if (tempskill.getStatAdvantage()[3] > 0) {
                                        temp += "**Bonus to Damage Dealt:** " + tempskill.getStatAdvantage()[3] + "\n";
                                    }
                                    if (tempskill.getStatAdvantage()[4] > 0) {
                                        temp += "**Bonus to Damage Dealt (multiplicative):** " + tempskill.getStatAdvantage()[4] + "\n";
                                    }
                                    if (tempskill.getStatAdvantage()[5] > 0) {
                                        temp += "**Damage Reduction:** " + tempskill.getStatAdvantage()[5] + "\n";
                                    }
                                } else if (store.getSkill(m[1]).isThreshhold()) {
                                    temp += "**Type:** Threshold-Activated\n";
                                    Threshold tempskill = (Threshold) store.getSkill(m[1]);
                                    temp += "**Threshold Amount: **" + tempskill.getThresholdAmount();
                                    if (tempskill.getBuff()[0] > 0) {
                                        temp += "**Bonus to HP:** " + tempskill.getBuff()[0] + "\n";
                                    }
                                    if (tempskill.getBuff()[1] > 0) {
                                        temp += "**Bonus to Strength:** " + tempskill.getBuff()[1] + "\n";
                                    }
                                    if (tempskill.getBuff()[2] > 0) {
                                        temp += "**Bonus to Magic:** " + tempskill.getBuff()[2] + "\n";
                                    }
                                    if (tempskill.getBuff()[3] > 0) {
                                        temp += "**Bonus to Skill:** " + tempskill.getBuff()[3] + "\n";
                                    }
                                    if (tempskill.getBuff()[4] > 0) {
                                        temp += "**Bonus to Speed:** " + tempskill.getBuff()[4] + "\n";
                                    }
                                    if (tempskill.getBuff()[5] > 0) {
                                        temp += "**Bonus to Luck:** " + tempskill.getBuff()[5] + "\n";
                                    }
                                    if (tempskill.getBuff()[6] > 0) {
                                        temp += "**Bonus to Defense:** " + tempskill.getBuff()[6] + "\n";
                                    }
                                    if (tempskill.getBuff()[7] > 0) {
                                        temp += "**Bonus to Resistance:** " + tempskill.getBuff()[7] + "\n";
                                    }
                                    if (tempskill.getBuff()[8] > 0) {
                                        temp += "**Bonus to Hit:** " + tempskill.getBuff()[8] + "\n";
                                    }
                                    if (tempskill.getBuff()[9] > 0) {
                                        temp += "**Bonus to Avoid:** " + tempskill.getBuff()[9] + "\n";
                                    }
                                    if (tempskill.getBuff()[10] > 0) {
                                        temp += "**Bonus to Crit:** " + tempskill.getBuff()[10] + "\n";
                                    }
                                    if (tempskill.getBuff()[11] > 0) {
                                        temp += "**Bonus to Physical Damage Taken:** " + tempskill.getBuff()[11] + "\n";
                                    }
                                    if (tempskill.getBuff()[12] > 0) {
                                        temp += "**Bonus to Magical Damage Taken:** " + tempskill.getBuff()[12] + "\n";
                                    }
                                    if (tempskill.getBuff()[13] > 0) {
                                        temp += "**Bonus to Damage Taken:** " + tempskill.getBuff()[13] + "\n";
                                    }
                                    if (tempskill.getBuff()[14] > 0) {
                                        temp += "**Bonus to Physical Damage Dealt:** " + tempskill.getBuff()[14] + "\n";
                                    }
                                    if (tempskill.getBuff()[15] > 0) {
                                        temp += "**Bonus to Magical Damage Dealt:** " + tempskill.getBuff()[15] + "\n";
                                    }
                                    if (tempskill.getBuff()[16] > 0) {
                                        temp += "**Bonus to Damage Dealt:** " + tempskill.getBuff()[16] + "\n";
                                    }
                                }
                                printmessage(temp, channel);
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setslayrace")) // skill race
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setslayrace \"name\" \"race\"", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isSlayer()) {
                                    ((RaceSlayer) store.getSkill(m[1])).setSlay(m[2]);
                                    store.saveData();
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Skill is not a slayer-type skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setslaybonus")) // skill stat bonus
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !setslaybonus \"name\" stat buff", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isSlayer()) {
                                    int inputstat;
                                    double advantage;
                                    String regex = "^\\d*\\.?\\d*$";
                                    if (m[3].matches(regex)) {
                                        advantage = Double.parseDouble(m[3]);
                                    } else {
                                        printmessage("Invalid buff amount. Ensure it is a number.", channel);
                                        return;
                                    }
                                    switch (m[2]) {
                                        case "hit":
                                            inputstat = 0;
                                            break;
                                        case "avoid":
                                            inputstat = 1;
                                            break;
                                        case "crit":
                                            inputstat = 2;
                                            break;
                                        case "damtake":
                                            inputstat = 5;
                                            break;
                                        case "damdeal":
                                            inputstat = 4;
                                            break;
                                        case "dammulti":
                                            inputstat = 4;
                                            break;
                                        default:
                                            printmessage("Invalid input stat. Ensure it is one of these: hit, avoid, crit, damtake, damdeal, dammulti", channel);
                                            return;
                                    }
                                    ((RaceSlayer) store.getSkill(m[1])).setStatAdvantage(inputstat, advantage);
                                    store.saveData();
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                } else {
                                    printmessage("Skill is not a slayer-type skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setbreakerweapon")) // skill weapon
                    {
                        if (store.hasSkill(m[1])) {
                            if (store.getSkill(m[1]).isBreaker()) {
                                int weapon;
                                switch (m[2]) {
                                    case "Sword":
                                        weapon = 0;
                                        break;
                                    case "Lance":
                                        weapon = 1;
                                        break;
                                    case "Axe":
                                        weapon = 2;
                                        break;
                                    case "Anima":
                                        weapon = 3;
                                        break;
                                    case "Light":
                                        weapon = 4;
                                        break;
                                    case "Dark":
                                        weapon = 5;
                                        break;
                                    case "Bow":
                                        weapon = 6;
                                        break;
                                    default:
                                        printmessage("Invalid weapon type inputted. The bot currently only supports: \"Sword, Lance, Axe, Bow, Anima, Light, Dark\".", channel);
                                        return;
                                }
                                ((WeaponBreaker)store.getSkill(m[1])).setWeapon(weapon);
                                printmessage(store.getSkill(m[1]).getStatus(), channel);
                            } else {
                                printmessage("Skill is not a breaker-type skill.", channel);
                            }
                        } else {
                            printmessage("Skill does not exist.", channel);
                        }
                    }
                    if (m[0].equals("setbreakerbonus")) // skill stat bonus
                    {
                        if (store.hasSkill(m[1])) {
                            if (store.getSkill(m[1]).isBreaker()) {
                                int inputstat;
                                double advantage = 0;
                                String regex = "^\\d*\\.?\\d*$";
                                if (m[3].matches(regex)) {
                                    advantage = Double.parseDouble(m[3]);
                                } else {
                                    printmessage("Invalid buff amount. Ensure it is a number.", channel);
                                }
                                switch (m[2]) {
                                    case "hit":
                                        inputstat = 0;
                                        break;
                                    case "avoid":
                                        inputstat = 1;
                                        break;
                                    case "crit":
                                        inputstat = 2;
                                        break;
                                    case "damtake":
                                        inputstat = 5;
                                        break;
                                    case "damdeal":
                                        inputstat = 4;
                                        break;
                                    case "dammulti":
                                        inputstat = 4;
                                        break;
                                    default:
                                        printmessage("Invalid input stat. Ensure it is one of these: hit, avoid, crit, damtake, damdeal, dammulti", channel);
                                        return;
                                }
                                ((WeaponBreaker)store.getSkill(m[1])).setAdvantage(inputstat,advantage);
                                printmessage(store.getSkill(m[1]).getStatus(), channel);
                                store.saveData();
                            } else {
                                printmessage("Skill is not a breaker-type skill.", channel);
                            }
                        } else {
                            printmessage("Skill does not exist.", channel);
                        }
                    }
                    if (m[0].equals("setmiraclerate")) // skill rate
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setmiraclerate \"name\" rate", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isMiracle()) {
                                    double rate;
                                    String regex = "^\\d*\\.?\\d*$";
                                    if (m[2].matches(regex)) {
                                        rate = Double.parseDouble(m[2]);
                                    } else {
                                        printmessage("Invalid activation rate. Ensure it is a number.", channel);
                                        return;
                                    }
                                    ((Miracle)store.getSkill(m[1])).setPercentage(rate);
                                    store.saveData();
                                    printmessage(store.getSkill(m[1]).getStatus(),channel);
                                } else {
                                    printmessage("Skill is not a miracle-type skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setmiraclestat")) // skill stat
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setmiraclestat \"name\" stat", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isMiracle()) {
                                    int inputstat;
                                    switch (m[2]) {
                                        case "hp":
                                            inputstat = 0;
                                            break;
                                        case "strength":
                                            inputstat = 1;
                                            break;
                                        case "magic":
                                            inputstat = 2;
                                            break;
                                        case "skill":
                                            inputstat = 3;
                                            break;
                                        case "speed":
                                            inputstat = 4;
                                            break;
                                        case "luck":
                                            inputstat = 5;
                                            break;
                                        case "defense":
                                            inputstat = 6;
                                            break;
                                        case "resistance":
                                            inputstat = 7;
                                            break;
                                        default:
                                            printmessage("Invalid stat inputted. Ensure it is a number.", channel);
                                            return;
                                    }
                                    ((Miracle)store.getSkill(m[1])).setActivationStat(inputstat);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a miracle-type skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setdefensivebuff")) // skill stat buff
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setdefensivebuff \"name\" stat buff", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isDefensive()) {
                                    int buff;
                                    int inputstat;
                                    String regex = "\\d+";
                                    if (m[3].matches(regex)) {
                                        buff = Integer.parseInt(m[3]);
                                    } else {
                                        printmessage("Invalid buff. Ensure it is a number.", channel);
                                        return;
                                    }
                                    switch (m[2]) {
                                        case "hp":
                                            inputstat = 0;
                                            break;
                                        case "strength":
                                            inputstat = 1;
                                            break;
                                        case "magic":
                                            inputstat = 2;
                                            break;
                                        case "skill":
                                            inputstat = 3;
                                            break;
                                        case "speed":
                                            inputstat = 4;
                                            break;
                                        case "luck":
                                            inputstat = 5;
                                            break;
                                        case "defense":
                                            inputstat = 6;
                                            break;
                                        case "resistance":
                                            inputstat = 7;
                                            break;
                                        case "hit":
                                            inputstat = 8;
                                            break;
                                        case "avoid":
                                            inputstat = 9;
                                            break;
                                        case "crit":
                                            inputstat = 10;
                                            break;
                                        case "physdamtake":
                                            inputstat = 11;
                                            break;
                                        case "magdamtake":
                                            inputstat = 12;
                                            break;
                                        case "damtake":
                                            inputstat = 13;
                                            break;
                                        case "physdamdeal":
                                            inputstat = 14;
                                            break;
                                        case "magdamdeal":
                                            inputstat = 15;
                                            break;
                                        case "damdeal":
                                            inputstat = 16;
                                            break;
                                        default:
                                            printmessage("Invalid input stat. Ensure it is one of these: hp, strength, magic, skill, speed, luck, defense, resistance, hit, avoid, crit, physdamtake, magdamtake, damtake, physdamdeal, magdamdeal, damdeal", channel);
                                            return;
                                    }
                                    ((DefensiveSkill)store.getSkill(m[1])).setBuff(inputstat, buff);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not defensive-type skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setoffensivebuff")) // skill stat buff
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setoffensivebuff \"name\" stat buff", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isOffensive()) {
                                    int buff;
                                    int inputstat;
                                    String regex = "\\d+";
                                    if (m[3].matches(regex)) {
                                        buff = Integer.parseInt(m[3]);
                                    } else {
                                        printmessage("Invalid buff. Ensure it is a number.", channel);
                                        return;
                                    }
                                    switch (m[2]) {
                                        case "hp":
                                            inputstat = 0;
                                            break;
                                        case "strength":
                                            inputstat = 1;
                                            break;
                                        case "magic":
                                            inputstat = 2;
                                            break;
                                        case "skill":
                                            inputstat = 3;
                                            break;
                                        case "speed":
                                            inputstat = 4;
                                            break;
                                        case "luck":
                                            inputstat = 5;
                                            break;
                                        case "defense":
                                            inputstat = 6;
                                            break;
                                        case "resistance":
                                            inputstat = 7;
                                            break;
                                        case "hit":
                                            inputstat = 8;
                                            break;
                                        case "avoid":
                                            inputstat = 9;
                                            break;
                                        case "crit":
                                            inputstat = 10;
                                            break;
                                        case "physdamtake":
                                            inputstat = 11;
                                            break;
                                        case "magdamtake":
                                            inputstat = 12;
                                            break;
                                        case "damtake":
                                            inputstat = 13;
                                            break;
                                        case "physdamdeal":
                                            inputstat = 14;
                                            break;
                                        case "magdamdeal":
                                            inputstat = 15;
                                            break;
                                        case "damdeal":
                                            inputstat = 16;
                                            break;
                                        default:
                                            printmessage("Invalid input stat. Ensure it is one of these: hp, strength, magic, skill, speed, luck, defense, resistance, hit, avoid, crit, physdamtake, magdamtake, damtake, physdamdeal, magdamdeal, damdeal", channel);
                                            return;
                                    }
                                    ((InitiationSkill)store.getSkill(m[1])).setBuff(inputstat, buff);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not defensive-type skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setthresholdthresh")) // skill threshold
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setthresholdthresh \"name\" threshold", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isThreshhold()) {
                                    double thresh;
                                    String regex = "^\\d*\\.?\\d*$";
                                    if (m[2].matches(regex)) {
                                        thresh = Double.parseDouble(m[2]);
                                    } else {
                                        printmessage("Invalid threshold. Ensure it is a decimal between 0 and 1 (e.g. 0.75).", channel);
                                        return;
                                    }
                                    ((Threshold)store.getSkill(m[1])).setThresholdAmount(thresh);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a threshold-type skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setthresholdbuff")) // skill stat buff
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !setthresholdbuff \"name\" stat buff", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isThreshhold()) {
                                    int buff;
                                    int inputstat;
                                    String regex = "\\d+";
                                    if (m[3].matches(regex)) {
                                        buff = Integer.parseInt(m[3]);
                                    } else {
                                        printmessage("Invalid buff. Ensure it is a number.", channel);
                                        return;
                                    }
                                    switch (m[2]) {
                                        case "hp":
                                            inputstat = 0;
                                            break;
                                        case "strength":
                                            inputstat = 1;
                                            break;
                                        case "magic":
                                            inputstat = 2;
                                            break;
                                        case "skill":
                                            inputstat = 3;
                                            break;
                                        case "speed":
                                            inputstat = 4;
                                            break;
                                        case "luck":
                                            inputstat = 5;
                                            break;
                                        case "defense":
                                            inputstat = 6;
                                            break;
                                        case "resistance":
                                            inputstat = 7;
                                            break;
                                        case "hit":
                                            inputstat = 8;
                                            break;
                                        case "avoid":
                                            inputstat = 9;
                                            break;
                                        case "crit":
                                            inputstat = 10;
                                            break;
                                        case "physdamtake":
                                            inputstat = 11;
                                            break;
                                        case "magdamtake":
                                            inputstat = 12;
                                            break;
                                        case "damtake":
                                            inputstat = 13;
                                            break;
                                        case "physdamdeal":
                                            inputstat = 14;
                                            break;
                                        case "magdamdeal":
                                            inputstat = 15;
                                            break;
                                        case "damdeal":
                                            inputstat = 16;
                                            break;
                                        default:
                                            printmessage("Invalid input stat. Ensure it is one of these: hp, strength, magic, skill, speed, luck, defense, resistance, hit, avoid, crit, physdamtake, magdamtake, damtake, physdamdeal, magdamdeal, damdeal", channel);
                                            return;
                                    }
                                    ((Threshold)store.getSkill(m[1])).setBuff(inputstat, buff);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a threshold-type skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setskillprocrate")) // skill rate
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setskillprocrate \"name\" procrate", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isProc()) {
                                    double proc;
                                    String regex = "^\\d*\\.?\\d*$";
                                    if (m[2].matches(regex)) {
                                        proc = Double.parseDouble(m[2]);
                                    } else {
                                        printmessage("Invalid proc rate. Ensure it is a decimal between 0 and 1 (e.g. 0.75).", channel);
                                        return;
                                    }
                                    ((ProcSkill)store.getSkill(m[1])).setProcRate(proc);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a proc skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setprocskillstatmulti")) // skill statmulti
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setprocskillstatmulti \"name\" statmulti", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isProc()) {
                                    double multi;
                                    String regex = "^\\d*\\.?\\d*$";
                                    if (m[2].matches(regex)) {
                                        multi = Double.parseDouble(m[2]);
                                    } else {
                                        printmessage("Invalid stat multiplier. Ensure it is a decimal between 0 and 1 (e.g. 0.75).", channel);
                                        return;
                                    }
                                    ((ProcSkill)store.getSkill(m[1])).setDamageMultiplier(multi);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a proc skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setprocskillactistat")) // skill stat
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setprocskillactistat \"name\" stat", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isProc()) {
                                    int inputstat;
                                    switch (m[2]) {
                                        case "hp":
                                            inputstat = 0;
                                            break;
                                        case "strength":
                                            inputstat = 1;
                                            break;
                                        case "magic":
                                            inputstat = 2;
                                            break;
                                        case "skill":
                                            inputstat = 3;
                                            break;
                                        case "speed":
                                            inputstat = 4;
                                            break;
                                        case "luck":
                                            inputstat = 5;
                                            break;
                                        case "defense":
                                            inputstat = 6;
                                            break;
                                        case "resistance":
                                            inputstat = 7;
                                            break;
                                        default:
                                            printmessage("Invalid input stat. Ensure it is one of these: hp, strength, magic, skill, speed, luck, defense, resistance", channel);
                                            return;
                                    }
                                    ((ProcSkill)store.getSkill(m[1])).setActivationStat(inputstat);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a proc skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setprocskillusenemystat")) // skill true/false
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setprocstkilluseenemystat \"name\" true/false", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isProc()) {
                                    boolean usestat;
                                    switch (m[2]) {
                                        case "true":
                                            usestat = true;
                                            break;
                                        case "false":
                                            usestat = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((ProcSkill)store.getSkill(m[1])).useEnemyStats(usestat);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a proc skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setprocskillcancel")) // skill true/false
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setprocskillcancel \"name\" true/false", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isProc()) {
                                    boolean cancel;
                                    switch (m[2]) {
                                        case "true":
                                            cancel = true;
                                            break;
                                        case "false":
                                            cancel = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((ProcSkill)store.getSkill(m[1])).setCancel(cancel);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a proc skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setprocskillwhenattacked")) // skill true/false
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setprocskillwhenattacked \"name\" true/false", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isProc()) {
                                    boolean defensive;
                                    switch (m[2]) {
                                        case "true":
                                            defensive = true;
                                            break;
                                        case "false":
                                            defensive = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((ProcSkill)store.getSkill(m[1])).setOnAttacked(defensive);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a proc skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setprocskillinputstat")) // skill stat
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setprocskillinputstat \"name\" stat", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isProc()) {
                                    int inputstat;
                                    switch (m[2]) {
                                        case "hp":
                                            inputstat = 0;
                                            break;
                                        case "strength":
                                            inputstat = 1;
                                            break;
                                        case "magic":
                                            inputstat = 2;
                                            break;
                                        case "skill":
                                            inputstat = 3;
                                            break;
                                        case "speed":
                                            inputstat = 4;
                                            break;
                                        case "luck":
                                            inputstat = 5;
                                            break;
                                        case "defense":
                                            inputstat = 6;
                                            break;
                                        case "resistance":
                                            inputstat = 7;
                                            break;
                                        default:
                                            printmessage("Invalid input stat. Ensure it is one of these: hp, strength, magic, skill, speed, luck, defense, resistance", channel);
                                            return;
                                    }
                                    ((ProcSkill)store.getSkill(m[1])).setInputStat(inputstat);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a proc skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setprocskillisdamaging")) // skill true/false
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setprocskillisdamaging \"name\" true/false", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isProc()) {
                                    boolean damage;
                                    switch (m[2]) {
                                        case "true":
                                            damage = true;
                                            break;
                                        case "false":
                                            damage = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((ProcSkill)store.getSkill(m[1])).setDamaging(damage);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a proc skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setprocskillishealing")) // skill true/false
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setprocskillishealing \"name\" true/false", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isProc()) {
                                    boolean heal;
                                    switch (m[2]) {
                                        case "true":
                                            heal = true;
                                            break;
                                        case "false":
                                            heal = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((ProcSkill)store.getSkill(m[1])).setHeal(heal);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a proc skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setpassiveskillbuff")) // skill stat buff
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setpassiveskillbuff \"name\" stat buff", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isPassive()) {
                                    int buff;
                                    int inputstat;
                                    String regex = "\\d+";
                                    if (m[3].matches(regex)) {
                                        buff = Integer.parseInt(m[3]);
                                    } else {
                                        printmessage("Invalid buff. Ensure it is a number.", channel);
                                        return;
                                    }
                                    switch (m[2]) {
                                        case "hp":
                                            inputstat = 0;
                                            break;
                                        case "strength":
                                            inputstat = 1;
                                            break;
                                        case "magic":
                                            inputstat = 2;
                                            break;
                                        case "skill":
                                            inputstat = 3;
                                            break;
                                        case "speed":
                                            inputstat = 4;
                                            break;
                                        case "luck":
                                            inputstat = 5;
                                            break;
                                        case "defense":
                                            inputstat = 6;
                                            break;
                                        case "resistance":
                                            inputstat = 7;
                                            break;
                                        case "hit":
                                            inputstat = 8;
                                            break;
                                        case "avoid":
                                            inputstat = 9;
                                            break;
                                        case "crit":
                                            inputstat = 10;
                                            break;
                                        case "physdamtake":
                                            inputstat = 11;
                                            break;
                                        case "magdamtake":
                                            inputstat = 12;
                                            break;
                                        case "damtake":
                                            inputstat = 13;
                                            break;
                                        case "physdamdeal":
                                            inputstat = 14;
                                            break;
                                        case "magdamdeal":
                                            inputstat = 15;
                                            break;
                                        case "damdeal":
                                            inputstat = 16;
                                            break;
                                        default:
                                            printmessage("Invalid input stat. Ensure it is one of these: hp, strength, magic, skill, speed, luck, defense, resistance, hit, avoid, crit, physdamtake, magdamtake, damtake, physdamdeal, magdamdeal, damdeal", channel);
                                            return;
                                    }
                                    ((PassiveSkill)store.getSkill(m[1])).setBuff(inputstat, buff);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a passive skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setvantage")) // skill true/false
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setvantage \"name\" true/false", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isPassive()) {
                                    boolean vantage;
                                    switch (m[2]) {
                                        case "true":
                                            vantage = true;
                                            break;
                                        case "false":
                                            vantage = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((PassiveSkill)store.getSkill(m[1])).setVantage(vantage);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a passive skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setvantagethreshold")) // skill threshold
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setvantagethreshold \"name\" threshold", channel);
                        } else {
                            if (store.hasSkill(m[1])) {
                                if (store.getSkill(m[1]).isPassive()) {
                                    double thresh;
                                    String regex = "^\\d*\\.?\\d*$";
                                    if (m[2].matches(regex)) {
                                        thresh = Double.parseDouble(m[2]);
                                    } else {
                                        printmessage("Invalid stat multiplier. Ensure it is a decimal between 0 and 1 (e.g. 0.75).", channel);
                                        return;
                                    }
                                    ((PassiveSkill)store.getSkill(m[1])).setVantageThreshold(thresh);
                                    printmessage(store.getSkill(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Skill is not a passive skill.", channel);
                                }
                            } else {
                                printmessage("Skill does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("createitem")) // name type uses
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !createitem \"name\" type uses", channel);
                        } else {
                            String regex = "\\d+";
                            int uses;
                            if (m[3].matches(regex)) {
                                uses = Integer.parseInt(m[3]);
                            } else {
                                printmessage("Invalid number of uses. Ensure it is a number.", channel);
                                return;
                            }
                            switch (m[2]) {
                                case "item": {
                                    Item temp = new Item(m[1]);
                                    temp.setCurrUses(uses);
                                    temp.setMaxUses(uses);
                                    printmessage(temp.getStatus(), channel);
                                    store.addItem(m[1], temp);
                                    store.saveData();
                                    break;
                                }
                                case "weapon": {
                                    Weapon temp = new Weapon(m[1], 0);
                                    temp.setCurrUses(uses);
                                    temp.setMaxUses(uses);
                                    printmessage(temp.getStatus(), channel);
                                    store.addItem(m[1], temp);
                                    store.saveData();
                                    break;
                                }
                                case "staff": {
                                    Staff temp = new Staff(m[1]);
                                    temp.setCurrUses(uses);
                                    temp.setMaxUses(uses);
                                    printmessage(temp.getStatus(), channel);
                                    store.addItem(m[1], temp);
                                    store.saveData();
                                    break;
                                }
                                case "consumable": {
                                    Consumable temp = new Consumable(m[1]);
                                    temp.setCurrUses(uses);
                                    temp.setMaxUses(uses);
                                    printmessage(temp.getStatus(), channel);
                                    store.addItem(m[1], temp);
                                    store.saveData();
                                    break;
                                }
                            }
                        }
                    }
                    if (m[0].equals("setitemdescription")) // item "desc"
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !createitem \"item\" \"description\"", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                store.getItem(m[1]).setDescription(m[2]);
                                printmessage(store.getItem(m[1]).getStatus(), channel);
                                store.saveData();
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("makeindestructible")) // item true/false
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !makeindestructible \"item\" true/false", channel);
                        } else {
                            boolean indes;
                            switch (m[2]) {
                                case "true":
                                    indes = true;
                                    break;
                                case "false":
                                    indes = false;
                                    break;
                                default:
                                    printmessage("Please use true/false.", channel);
                                    return;
                            }
                            if (store.hasItem(m[1])) {
                                store.getItem(m[1]).setIndestructible(indes);
                                printmessage(store.getItem(m[1]).getStatus(), channel);
                                store.saveData();
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("useitem")) // char item
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !useitem \"char\" \"item\"", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                store.getItem(m[1]).use();
                                if (store.getItem(m[1]).getCurrUses() == 0) {
                                    store.getItem(m[1]).isBroken();
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("breakitem")) // char item
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !breakitem \"char\" \"item\"", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                store.getItem(m[1]).setCurrUses(0);
                                store.getItem(m[1]).isBroken();
                                printmessage(store.getItem(m[1]).getStatus(), channel);
                                store.saveData();
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("iteminfo")) // item
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !iteminfo \"item\"", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                String temp = "***Item Info:***\n";
                                temp += "**Name: **" + store.getItem(m[1]).getName() + "\n";
                                temp += "**Description: **\n" + store.getItem(m[1]).getDescription() + "\n";
                                if (store.getItem(m[1]).isIndestructible()) {
                                    temp += "**Uses: ** --/--\n";
                                } else {
                                    temp += "**Uses: **" + store.getItem(m[1]).getCurrUses() + "/" + store.getItem(m[1]).getMaxUses() + "\n";
                                }
                                if (store.getItem(m[1]).isWeapon()) {
                                    temp += "**Weapon Type: **";
                                    switch (((Weapon) store.getItem(m[1])).getWeapontype()) {
                                        case 0:
                                            temp += "None\n";
                                            break;
                                        case 1:
                                            temp += "Sword\n";
                                            break;
                                        case 2:
                                            temp += "Lance\n";
                                            break;
                                        case 3:
                                            temp += "Axe\n";
                                            break;
                                        case 4:
                                            temp += "Anima\n";
                                            break;
                                        case 5:
                                            temp += "Light\n";
                                            break;
                                        case 6:
                                            temp += "Dark\n";
                                            break;
                                        case 7:
                                            temp += "Bow\n";
                                            break;
                                    }
                                    String rank = "E";
                                    switch (((Weapon) store.getItem(m[1])).getWeaponRank()) {
                                        case 0:
                                            rank = "E";
                                            break;
                                        case 1:
                                            rank = "D";
                                            break;
                                        case 2:
                                            rank = "C";
                                            break;
                                        case 3:
                                            rank = "B";
                                            break;
                                        case 4:
                                            rank = "A";
                                            break;
                                        case 5:
                                            rank = "S";
                                            break;
                                    }
                                    temp += "**Weapon Rank: **" + rank + "\n";
                                    temp += "**Hit: **" + ((Weapon)store.getItem(m[1])).getHitRate() + "\n";
                                    temp += "**Might: **" + ((Weapon)store.getItem(m[1])).getMight() + "\n";
                                    temp += "**Crit: **" + ((Weapon)store.getItem(m[1])).getCritRate() + "\n";
                                    temp += "**Crit Damage: **" + ((Weapon)store.getItem(m[1])).getCritModifier() + "x\n";
                                    temp += "**Range: **" + ((Weapon)store.getItem(m[1])).getMinRange() + "-" + ((Weapon)store.getItem(m[1])).getMaxRange() + "\n";
                                    temp += "**Advantageous Against: **\n";
                                    if (((Weapon)store.getItem(m[1])).getPhysicalAdvantage() != 0) {
                                        temp += ((Weapon)store.getItem(m[1])).weapontypes[((Weapon)store.getItem(m[1])).getPhysicalAdvantage()] + "\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).getMagicalAdvantage() != 0) {
                                        temp += ((Weapon)store.getItem(m[1])).weapontypes[((Weapon)store.getItem(m[1])).getMagicalAdvantage()] + "\n";
                                    }
                                    temp += "**Disadvantageous Against: **\n";
                                    if (((Weapon)store.getItem(m[1])).getPhysicalWeakness() != 0) {
                                        temp += ((Weapon)store.getItem(m[1])).weapontypes[((Weapon)store.getItem(m[1])).getPhysicalWeakness()] + "\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).getMagicalWeakness() != 0) {
                                        temp += ((Weapon)store.getItem(m[1])).weapontypes[((Weapon)store.getItem(m[1])).getMagicalWeakness()] + "\n";
                                    }
                                    temp += "**Effective Against: **\n";
                                    if (((Weapon)store.getItem(m[1])).isEffectiveAgainst(0)) {
                                        temp += "Swords\n";
                                    } else if (((Weapon)store.getItem(m[1])).isEffectiveAgainst(1)) {
                                        temp += "Axes\n";
                                    } else if (((Weapon)store.getItem(m[1])).isEffectiveAgainst(2)) {
                                        temp += "Lances\n";
                                    } else if (((Weapon)store.getItem(m[1])).isEffectiveAgainst(3)) {
                                        temp += "Anima\n";
                                    } else if (((Weapon)store.getItem(m[1])).isEffectiveAgainst(4)) {
                                        temp += "Light\n";
                                    } else if (((Weapon)store.getItem(m[1])).isEffectiveAgainst(5)) {
                                        temp += "Dark\n";
                                    } else if (((Weapon)store.getItem(m[1])).isEffectiveAgainst(6)) {
                                        temp += "Bows\n";
                                    }
                                    temp += "**Weapon Attributes: **\n";
                                    if (!((Weapon)store.getItem(m[1])).canDouble()) {
                                        temp += "Cannot double.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).isBrave()) {
                                        temp += "Brave.\n";
                                    }
                                    if (!((Weapon)store.getItem(m[1])).isCounterable()) {
                                        temp += "Uncounterable.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).isAntiArmor()) {
                                        temp += "Armorslaying.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).isAntiFlier()) {
                                        temp += "Anti-Fliers.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).isAntimounted()) {
                                        temp += "Anti-mounted.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).isUsesMagic()) {
                                        temp += "Magic weapon.\n";
                                    } else {
                                        temp += "Physical weapon.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).isMagicTarget()) {
                                        temp += "Targets resistance.\n";
                                    } else {
                                        temp += "Targets defense.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).isLifeSteal()) {
                                        temp += "Restores HP equal to damage dealt.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).isIgnoreDefense()) {
                                        temp += "Ignores defenses.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).getStatBonuses()[0] > 1) {
                                        temp += "Boosts HP by " + ((Weapon)store.getItem(m[1])).getStatBonuses()[0] + " while equipped.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).getStatBonuses()[1] > 1) {
                                        temp += "Boosts Strength by " + ((Weapon)store.getItem(m[1])).getStatBonuses()[1] + " while equipped.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).getStatBonuses()[2] > 1) {
                                        temp += "Boosts Magic by " + ((Weapon)store.getItem(m[1])).getStatBonuses()[2] + " while equipped.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).getStatBonuses()[3] > 1) {
                                        temp += "Boosts Skill by " + ((Weapon)store.getItem(m[1])).getStatBonuses()[3] + " while equipped.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).getStatBonuses()[4] > 1) {
                                        temp += "Boosts Speed by " + ((Weapon)store.getItem(m[1])).getStatBonuses()[4] + " while equipped.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).getStatBonuses()[5] > 1) {
                                        temp += "Boosts Luck by " + ((Weapon)store.getItem(m[1])).getStatBonuses()[5] + " while equipped.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).getStatBonuses()[6] > 1) {
                                        temp += "Boosts Defense by " + ((Weapon)store.getItem(m[1])).getStatBonuses()[6] + " while equipped.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).getStatBonuses()[7] > 1) {
                                        temp += "Boosts Resistance by " + ((Weapon)store.getItem(m[1])).getStatBonuses()[7] + " while equipped.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).getStatBonuses()[8] > 1) {
                                        temp += "Boosts Hit by " + ((Weapon)store.getItem(m[1])).getStatBonuses()[8] + " while equipped.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).getStatBonuses()[9] > 1) {
                                        temp += "Boosts Avoid by " + ((Weapon)store.getItem(m[1])).getStatBonuses()[9] + " while equipped.\n";
                                    }
                                    if (((Weapon)store.getItem(m[1])).getStatBonuses()[10] > 1) {
                                        temp += "Boosts Crit by " + ((Weapon)store.getItem(m[1])).getStatBonuses()[10] + " while equipped.\n";
                                    }
                                } else if (store.getItem(m[1]).isStaff()) {
                                    String rank = "E";
                                    switch (((Staff) store.getItem(m[1])).getWeaponRank()) {
                                        case 0:
                                            rank = "E";
                                            break;
                                        case 1:
                                            rank = "D";
                                            break;
                                        case 2:
                                            rank = "C";
                                            break;
                                        case 3:
                                            rank = "B";
                                            break;
                                        case 4:
                                            rank = "A";
                                            break;
                                        case 5:
                                            rank = "S";
                                            break;
                                    }
                                    temp += "**Weapon Rank: **" + rank + "\n";
                                    temp += "**Staff Type: **";
                                    if (((Staff) store.getItem(m[1])).isHealing()) {
                                        temp += "Healing\n";
                                        temp += "**Base Healing Amount:** " + ((Staff) store.getItem(m[1])).getBaseHeal() + "\n";
                                        temp += "**Bonus Heal Per Magic:** " + ((Staff) store.getItem(m[1])).getMagicMultiplier() + "\n";
                                    } else {
                                        temp += "Status\n";
                                    }

                                } else if (store.getItem(m[1]).isBuff()) {
                                    if (((Consumable)store.getItem(m[1])).isHealing()) {
                                        temp += "Heals the user for " + ((Consumable)store.getItem(m[1])).getHealAmount() + " HP.\n";
                                    }
                                    if (((Consumable)store.getItem(m[1])).getStatModifiers()[0] > 0) {
                                        temp += "Boosts max HP by: " + ((Consumable)store.getItem(m[1])).getStatModifiers()[0];
                                    }
                                    if (((Consumable)store.getItem(m[1])).getStatModifiers()[1] > 0) {
                                        temp += "Boosts Strength by: " + ((Consumable)store.getItem(m[1])).getStatModifiers()[0];
                                    }
                                    if (((Consumable)store.getItem(m[1])).getStatModifiers()[2] > 0) {
                                        temp += "Boosts Magic by: " + ((Consumable)store.getItem(m[1])).getStatModifiers()[0];
                                    }
                                    if (((Consumable)store.getItem(m[1])).getStatModifiers()[3] > 0) {
                                        temp += "Boosts Skill by: " + ((Consumable)store.getItem(m[1])).getStatModifiers()[0];
                                    }
                                    if (((Consumable)store.getItem(m[1])).getStatModifiers()[4] > 0) {
                                        temp += "Boosts Speed by: " + ((Consumable)store.getItem(m[1])).getStatModifiers()[0];
                                    }
                                    if (((Consumable)store.getItem(m[1])).getStatModifiers()[5] > 0) {
                                        temp += "Boosts Luck by: " + ((Consumable)store.getItem(m[1])).getStatModifiers()[0];
                                    }
                                    if (((Consumable)store.getItem(m[1])).getStatModifiers()[6] > 0) {
                                        temp += "Boosts Defense by: " + ((Consumable)store.getItem(m[1])).getStatModifiers()[0];
                                    }
                                    if (((Consumable)store.getItem(m[1])).getStatModifiers()[7] > 0) {
                                        temp += "Boosts Resistance by: " + ((Consumable)store.getItem(m[1])).getStatModifiers()[0];
                                    }
                                    if (((Consumable)store.getItem(m[1])).getStatModifiers()[8] > 0) {
                                        temp += "Boosts Hit by: " + ((Consumable)store.getItem(m[1])).getStatModifiers()[0];
                                    }
                                    if (((Consumable)store.getItem(m[1])).getStatModifiers()[9] > 0) {
                                        temp += "Boosts Avoid by: " + ((Consumable)store.getItem(m[1])).getStatModifiers()[0];
                                    }
                                    if (((Consumable)store.getItem(m[1])).getStatModifiers()[10] > 0) {
                                        temp += "Boosts Crit by: " + ((Consumable)store.getItem(m[1])).getStatModifiers()[0];
                                    }
                                }
                                printmessage(temp, channel);
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setphysadvantage")) // weapon weapontype
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setphysadvantage \"weapon\" weapontype", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    int weapon;
                                    switch (m[2]) {
                                        case "Sword":
                                            weapon = 0;
                                            break;
                                        case "Lance":
                                            weapon = 1;
                                            break;
                                        case "Axe":
                                            weapon = 2;
                                            break;
                                        case "Bow":
                                            weapon = 6;
                                            break;
                                        default:
                                            printmessage("Invalid weapon type inputted. The bot currently only supports: \"Sword, Lance, Axe, Bow\".", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setPhysicalAdvantage(weapon);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setphysweakness")) // weapon weapontype
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setphysweakness \"weapon\" weapontype", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    int weapon;
                                    switch (m[2]) {
                                        case "Sword":
                                            weapon = 0;
                                            break;
                                        case "Lance":
                                            weapon = 1;
                                            break;
                                        case "Axe":
                                            weapon = 2;
                                            break;
                                        case "Bow":
                                            weapon = 6;
                                            break;
                                        default:
                                            printmessage("Invalid weapon type inputted. The bot currently only supports: \"Sword, Lance, Axe, Bow\".", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setPhysicalWeakness(weapon);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setmagadvantage")) // weapon weapontype
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setmagadvantage \"weapon\" weapontype", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    int weapon;
                                    switch (m[2]) {
                                        case "Anima":
                                            weapon = 3;
                                            break;
                                        case "Light":
                                            weapon = 4;
                                            break;
                                        case "Dark":
                                            weapon = 5;
                                            break;
                                        default:
                                            printmessage("Invalid weapon type inputted. The bot currently only supports: \"Anima, Light, Dark\".", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setMagicalAdvantage(weapon);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setmagdisadvantage")) // weapon weapontype
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setmagdisadvantage \"weapon\" weapontype", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    int weapon;
                                    switch (m[2]) {
                                        case "Anima":
                                            weapon = 3;
                                            break;
                                        case "Light":
                                            weapon = 4;
                                            break;
                                        case "Dark":
                                            weapon = 5;
                                            break;
                                        default:
                                            printmessage("Invalid weapon type inputted. The bot currently only supports: \"Anima, Light, Dark\".", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setMagicalWeakness(weapon);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setrequiredweaponrank")) // weapon rank
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setrequiredweaponrank \"weapon\" weapontype", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    int rank;
                                    String regex = "\\d+";
                                    if (m[2].matches(regex)) {
                                        rank = Integer.parseInt(m[3]);
                                    } else {
                                        printmessage("Invalid weapon rank. Ensure it is a number from 0-5 (0 = E, 1 = D, 2 = C, 3 = B, 4 = A, 5 = S).", channel);
                                        return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setWeaponRank(rank);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setweaponeffectiveness")) // weapon weapontype yes/no
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !setweaponeffectiveness \"weapon\" weapontype true/false", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    int weapon;
                                    boolean effective;
                                    switch (m[2]) {
                                        case "Sword":
                                            weapon = 0;
                                            break;
                                        case "Lance":
                                            weapon = 1;
                                            break;
                                        case "Axe":
                                            weapon = 2;
                                            break;
                                        case "Anima":
                                            weapon = 3;
                                            break;
                                        case "Light":
                                            weapon = 4;
                                            break;
                                        case "Dark":
                                            weapon = 5;
                                            break;
                                        case "Bow":
                                            weapon = 6;
                                            break;
                                        default:
                                            printmessage("Invalid weapon type inputted. The bot currently only supports: \"Sword, Lance, Axe, Bow, Anima, Light, Dark\".", channel);
                                            return;
                                    }
                                    switch (m[3]) {
                                        case "true":
                                            effective = true;
                                            break;
                                        case "false":
                                            effective = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setWeaponEffectiveness(weapon, effective);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setarmorslaying")) // weapon yes/no
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setarmorslaying \"weapon\" true/false", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    boolean slaying;
                                    switch (m[2]) {
                                        case "true":
                                            slaying = true;
                                            break;
                                        case "false":
                                            slaying = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setAntiArmor(slaying);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setantifliers")) // weapon yes/no
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setantifliers \"weapon\" true/false", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    boolean slaying;
                                    switch (m[2]) {
                                        case "true":
                                            slaying = true;
                                            break;
                                        case "false":
                                            slaying = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setAntiFlier(slaying);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setantimounted")) // weapon yes/no
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setantimounted \"weapon\" true/false", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    boolean slaying;
                                    switch (m[2]) {
                                        case "true":
                                            slaying = true;
                                            break;
                                        case "false":
                                            slaying = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setAntiMounted(slaying);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("settargetsres")) // weapon yes/no
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !settargetres \"weapon\" true/false", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    boolean targres;
                                    switch (m[2]) {
                                        case "true":
                                            targres = true;
                                            break;
                                        case "false":
                                            targres = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setMagicTarget(targres);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setusesmagicstat")) // weapon yes/no
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setusesmagicstat \"weapon\" true/false", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    boolean magic;
                                    switch (m[2]) {
                                        case "true":
                                            magic = true;
                                            break;
                                        case "false":
                                            magic = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).usesMagic(magic);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setweaponhitrate")) // weapon hitrate
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setweaponhitrate \"weapon\" hitrate", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    int hit;
                                    String regex = "\\d+";
                                    if (m[2].matches(regex)) {
                                        hit = Integer.parseInt(m[2]);
                                    } else {
                                        printmessage("Invalid hit rate. Ensure it is a number.", channel);
                                        return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setHitRate(hit);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setweaponmight")) // weapon might
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setweaponmight \"weapon\" might", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    int might;
                                    String regex = "\\d+";
                                    if (m[2].matches(regex)) {
                                        might = Integer.parseInt(m[2]);
                                    } else {
                                        printmessage("Invalid might. Ensure it is a number.", channel);
                                        return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setMight(might);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setweaponcritrate")) // weapon critrate
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setweaponcritrate \"weapon\" critrate", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    int crit;
                                    String regex = "\\d+";
                                    if (m[2].matches(regex)) {
                                        crit = Integer.parseInt(m[2]);
                                    } else {
                                        printmessage("Invalid crit rate. Ensure it is a number.", channel);
                                        return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setCritRate(crit);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setweaponcounterable")) // weapon yes/no
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setweaponcounterable \"weapon\" yes/no", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    boolean counter;
                                    switch (m[2]) {
                                        case "true":
                                            counter = true;
                                            break;
                                        case "false":
                                            counter = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setCounterable(counter);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setminweaponrange")) // weapon range
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setminweaponrange \"weapon\" range", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    int range;
                                    String regex = "\\d+";
                                    if (m[2].matches(regex)) {
                                        range = Integer.parseInt(m[2]);
                                    } else {
                                        printmessage("Invalid range. Ensure it is a number.", channel);
                                        return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setMinRange(range);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    if (m[0].equals("setmaxweaponrange")) // weapon range
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setmaxweaponrange \"weapon\" range", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    int range;
                                    String regex = "\\d+";
                                    if (m[2].matches(regex)) {
                                        range = Integer.parseInt(m[2]);
                                    } else {
                                        printmessage("Invalid range. Ensure it is a number.", channel);
                                        return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setMaxRange(range);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setbraveweapon")) // weapon yes/no
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setbraveweapon \"weapon\" yes/no", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    boolean brave;
                                    switch (m[2]) {
                                        case "true":
                                            brave = true;
                                            break;
                                        case "false":
                                            brave = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setBrave(brave);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setweaponcandouble")) // weapon yes/no
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setweaponcandouble \"weapon\" true/false", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    boolean d;
                                    switch (m[2]) {
                                        case "true":
                                            d = true;
                                            break;
                                        case "false":
                                            d = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setDouble(d);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setweaponignoredefense")) // weapon yes/no
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setweaponignoredefense \"weapon\" true/false", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    boolean luna;
                                    switch (m[2]) {
                                        case "true":
                                            luna = true;
                                            break;
                                        case "false":
                                            luna = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setIgnoreDefense(luna);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setlifesteal")) // weapon yes/no
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setlifesteal \"weapon\" true/false", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isWeapon()) {
                                    boolean ls;
                                    switch (m[2]) {
                                        case "true":
                                            ls = true;
                                            break;
                                        case "false":
                                            ls = false;
                                            break;
                                        default:
                                            printmessage("Please use true/false.", channel);
                                            return;
                                    }
                                    ((Weapon)store.getItem(m[1])).setLifeSteal(ls);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a weapon.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setstaffrank")) // staff rank
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setstaffrank \"staff\" rank", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isStaff()) {

                                } else {
                                    printmessage("Item is not a staff.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("sethealingstaff")) // staff yes/no
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !sethealingstaff \"staff\" true/false", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isStaff()) {
                                    int rank;
                                    String regex = "\\d+";
                                    if (m[2].matches(regex)) {
                                        rank = Integer.parseInt(m[3]);
                                    } else {
                                        printmessage("Invalid weapon rank. Ensure it is a number from 0-5 (0 = E, 1 = D, 2 = C, 3 = B, 4 = A, 5 = S).", channel);
                                        return;
                                    }
                                    ((Staff)store.getItem(m[1])).setWeaponRank(rank);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a staff.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("setstaffmagmulti")) // staff multiplier
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setstaffmagmulti \"staff\" multiplier", channel);
                        } else {
                            if (store.hasItem(m[1])) {
                                if (store.getItem(m[1]).isStaff()) {
                                    int multi;
                                    String regex = "\\d+";
                                    if (m[2].matches(regex)) {
                                        multi = Integer.parseInt(m[3]);
                                    } else {
                                        printmessage("Invalid multiplier. Ensure it is a number.", channel);
                                        return;
                                    }
                                    ((Staff)store.getItem(m[1])).setMagicMultiplier(multi);
                                    printmessage(store.getItem(m[1]).getStatus(), channel);
                                    store.saveData();
                                } else {
                                    printmessage("Item is not a staff.", channel);
                                }
                            } else {
                                printmessage("Item does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("battle")) // initiator defender
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !battle \"initiator\" \"defender\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                if (store.hasCharacter(m[2])) {
                                    Battle temp = new Battle();
                                    String mess = temp.commenceBattle(store.getCharacter(m[1]),store.getCharacter(m[2]));
                                    printmessage(mess, channel);
                                    store.saveData();
                                } else {
                                    printmessage("Defender does not exist.", channel);
                                }
                            } else {
                                printmessage("Initiator does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("battleforecast")) // char1 char2
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !battleforecast \"initiator\" \"defender\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                if (store.hasCharacter(m[2])) {
                                    Battle temp = new Battle();
                                    String mess = temp.battleForecast(store.getCharacter(m[1]),store.getCharacter(m[2]));
                                    printmessage(mess, channel);
                                } else {
                                    printmessage("Defender does not exist.", channel);
                                }
                            } else {
                                printmessage("Initiator does not exist.", channel);
                            }
                        }
                    }
                    if (m[0].equals("battlestaff")) // initiator target
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !battlestaff \"caster\" \"target\"", channel);
                        } else {
                            if (store.hasCharacter(m[1])) {
                                if (store.hasCharacter(m[2])) {
                                    if (store.getCharacter(m[1]).getEquippedStaff() != null) {
                                        if (store.getCharacter(m[1]).getEquippedStaff().isHealing()) {
                                            store.getCharacter(m[2]).heal(store.getCharacter(m[1]).getEquippedStaff().getBaseHeal() + store.getCharacter(m[1]).getEquippedStaff().getMagicMultiplier() * store.getCharacter(m[1]).getStats()[2]);
                                            store.getCharacter(m[1]).getEquippedStaff().use();
                                            String mess = store.getCharacter(m[2]).getStatus();
                                            mess += store.getCharacter(m[1]).getEquippedStaff().getStatus();
                                            printmessage(mess, channel);
                                            store.saveData();
                                        } else {
                                            printmessage("Status staves have not been implemented.", channel);
                                        }
                                    } else {
                                        printmessage("Initiator does not have an equipped staff.", channel);
                                    }
                                } else {
                                    printmessage("Target does not exist.", channel);
                                }
                            } else {
                                printmessage("Initiator does not exist.", channel);
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

    private void printmessage(String m, IChannel c) {
        new MessageBuilder(this.client).withChannel(c).withContent(m).build();
    }
}