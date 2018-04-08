import com.koloboke.collect.impl.hash.Hash;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.obj.Role;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.HashMap;
import java.util.List;

/**List of commands that users can input:
 * CHARACTER COMMANDS
 * !createchar name
 * !deletechar name
 * !charinfo name
 * !changecharname name
 * !setchardescription name "desc"
 * !setcharfamily name "family"
 * !setcharbackground name "background"
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
 * !setclassskillbonus name bonus
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
    HashMap<String,Character> charmap;
    HashMap<String,Skill> skillmap;
    HashMap<String,Item> itemmap;
    public FERPBot(IDiscordClient discordClient) {
        super(discordClient);
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
                String withoutstart = message.getContent().substring(1,message.getContent().length());
                String[] m = withoutstart.split(" (?=(([^'\"]*['\"]){2})*[^'\"]*$)");
                int argnum = m.length;
                int i;
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

                        }
                    }
                    if (m[0].equals("deletechar"))// name
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !deletechar \"name\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("charinfo"))// name
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !charinfo \"name\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("changecharname"))// name name
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !changecharname \"before\" \"after\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setchardescription"))// name "desc"
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setchardescription \"name\" \"description\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setcharfamily name"))// "family"
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcharfamily \"char\" \"family\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setcharbackground"))// name "background"
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcharbackground \"char\" \"background\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setcharclass"))// name class
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcharclass \"name\" \"class\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setcharrace"))// name race
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcharrace \"name\" \"race\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setcharweaponrank"))// name rank
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !setcharweaponrank \"name\" \"weapon\" rank", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setcharlevel"))// name level
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcharlevel \"name\" level", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setcharexp"))// name experience
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcharexp \"name\" exp", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("addexp"))// name experience
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !addexp \"name\" exp", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("levelchar"))// name
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !levelchar \"name\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("sethp"))// name hp
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !sethp \"name\" hp", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setstr"))// name strength
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setstr \"name\" str", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setmag"))// name magic
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setmag \"name\" mag", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setskl"))// name skill
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setskl \"name\" skill", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setspd"))// name speed
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setspd \"name\" speed", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setlck"))// name luck
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setlck \"name\" luck", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setdef"))// name defense
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setdef \"name\" defense", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setres"))// name resistance
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setres \"name\" resistance", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setcurrhp"))// name hp
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcurr \"name\" hp", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setcharhitbonus"))// name bonus
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcharhitbonus \"name\" hitbonus", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setcharavoidbonus"))// name bonus
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcharavoidbonus \"name\" avoidbonus", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setcharcritbonus"))// name bonus
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setcharcritbonus \"name\" critbonus", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("sethpgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !sethpgrow \"name\" growth", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setstrgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setstrgrow \"name\" growth", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setmaggrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setmaggrow \"name\" growth", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setsklgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setsklgrow \"name\" growth", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setspdgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setspdgrow \"name\" growth", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setlckgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setlckgrow \"name\" growth", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setdefgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setdefgrow \"name\" growth", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setresgrow"))// name growth
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setresgrow \"name\" growth", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setlevelcap"))// name cap
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setlevelcap \"name\" levelcap", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("showinventory"))// char
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !showinventory \"name\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("charskilllist"))// char
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !charskilllist \"name\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("heal"))// char HP
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !heal \"name\" hp", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setteam"))// char team
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !setteam \"name\" teamnumber", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("removecharitem"))// char itemname
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !removecharitem \"name\" \"item\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("setcharweaponrank"))// char weapontype rank
                    {
                        if (m.length != 4) {
                            printmessage("Invalid argument amount. Usage: !setcharweaponrank \"name\" \"weapon\" rank", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("equipweapon"))// char weapon
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !equipweapon \"name\" \"weapon\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("unequipweapon"))// char
                    {
                        if (m.length != 2) {
                            printmessage("Invalid argument amount. Usage: !unequipweapon \"name\"", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("equipstaff"))// char staff
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !equipstaff \"name\" staff", channel);
                        } else {

                        }
                    }
                    if (m[0].equals("promotechar"))// char class
                    {
                        if (m.length != 3) {
                            printmessage("Invalid argument amount. Usage: !promotechar \"name\" \"class\"", channel);
                        } else {

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
    public void printmessage(String m, IChannel c)
    {
        new MessageBuilder(this.client).withChannel(c).withContent(m).build();
    }
}