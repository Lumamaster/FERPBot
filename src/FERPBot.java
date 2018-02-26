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
 * !neal "people die when they are killed."
 * !royalty "BECOME AS GODS"
 */

public class FERPBot extends BaseBot implements IListener<MessageEvent> {
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
            if (message.getContent().startsWith("#")) //test if valid prefix
            {

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