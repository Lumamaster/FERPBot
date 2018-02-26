# FERPBot
A Discord bot meant to simulate Fire Emblem style combat with the capability to create custom skills, weapons, classes, characters, and so much more.

THIS BOT IS CURRENTLY WORK IN PROGRESS, AND IS CURRENTLY NONFUNCTIONAL.

This bot was made in IntelliJ IDEA Community Edition, and uses Discord4J as part of its functionality.

Further documentation will be shown below as bot development continues.

## Commands
###### CHARACTER COMMANDS
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

###### CLASS COMMANDS
 * !createclass name
 * !addclasspromopath name class
 * !removeclasspromopath name class
 * !setclassrankbonus name bonus
 * !setclassskillbonus name bonus
 * !removeclassskillbonus name bonus
 * !setclassstatbonus name bonus
 * !classinfo

###### SKILL COMMANDS
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

###### ITEM COMMANDS
 * !createitem name type
 * !setitemdescription item "desc"
 * !makeindestructible item
 * !useitem char item
 * !breakitem char item
 * !iteminfo item

###### WEAPON COMMANDS
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

###### STAFF COMMANDS
 * !setstaffrank staff rank
 * !sethealingstaff staff yes/no
 * !setstaffmagmulti staff multiplier

###### RECIPE COMMANDS
 * !definerecipe name "items"
 * !removerecipeitem name item
 * !addrecipeitem name item
 * !recipeinfo name
 * !listrecipes

###### BATTLE COMMANDS
 * !battle initiator defender
 * !battlecommand initiator defender command
 * !battleforecast char1 char2
 * !battlestaff initiator target

###### STATUS COMMANDS
 * !createstatus name
 * !setdefaultduration name duration
 * !setstatusdebuff stat debuff
 * !cyclestatus team
 * !cyclecharstatus char
 * !addstatus char status
