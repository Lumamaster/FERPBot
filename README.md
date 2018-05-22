# FERPBot
A Discord bot meant to simulate Fire Emblem style combat with the capability to create custom skills, weapons, classes, characters, and so much more.

THIS BOT IS CURRENTLY WORK IN PROGRESS.

This bot was made in IntelliJ IDEA Community Edition, and uses Discord4J as part of its functionality.

Further documentation will be shown below as bot development continues.

## Commands
###### CHARACTER COMMANDS
 * !createchar name 
   - Creates a new character.
 * !deletechar name 
   - Deletes an existing character if it exists.
 * !charinfo name 
   - Prints information about a character.
 * !changecharname name newname 
   - Changes a character's name.
 * !setchardescription name "desc" 
   - Changes a character's description.
 * !setcharclass name class 
   - Changes a character's class. Weapon ranks, etc. will have to be edited manually.)
 * !setcharrace name race 
   - Sets a character's race.
 * !setcharweaponrank name weapon rank 
   - Sets a character's rank in the given weapon.
 * !setcharlevel name level 
   - Sets the character's current level.
 * !setcharexp name experience 
   - Sets the character's current experience.
 * !addexp name experience 
   - Gives the character experience, and levels them up if they go over 100.
 * !levelchar name 
   - Levels up a character.
 * !sethp name hp 
   - Sets a character's maximum HP.
 * !setstr name strength 
   - Sets a character's strength stat.
 * !setmag name magic 
   - Sets a character's magic stat.
 * !setskl name skill 
   - Sets a character's skill stat.
 * !setspd name speed 
   - Sets a character's speed stat.
 * !setlck name luck 
   - Sets a character's luck stat.
 * !setdef name defense 
   - Sets a character's defense stat.
 * !setres name resistance 
   - Sets a character's resistance stat.
 * !setcurrhp name hp 
   - Sets a character's current HP.
 * !setcharhitbonus name bonus 
   - Sets a character's bonus to hit.
 * !setcharavoidbonus name bonus 
   - Sets a character's bonus to avoid.
 * !setcharcritbonus name bonus 
   - Sets a character's bonus to crit.
 * !sethpgrow name growth 
   - Sets a character's HP growth.
 * !setstrgrow name growth 
   - Sets a character's Strength growth.
 * !setmaggrow name growth 
   - Sets a character's Magic growth.
 * !setsklgrow name growth
   - Sets a character's Skill growth.
 * !setspdgrow name growth 
   - Sets a character's Speed growth.
 * !setlckgrow name growth 
   - Sets a character's Luck growth.
 * !setdefgrow name growth
   - Sets a character's Defense growth.
 * !setresgrow name growth 
   - Sets a character's Resistance growth.
 * !setlevelcap name cap 
   - Sets a character's maximum level. (Default: 20)
 * !promote char class 
   - Promotes a character to the given class, if it is a promotion path for them.
 * !showinventory char 
   - Prints out the character's inventory.
 * !heal char HP 
   - Heals the character for the specified amount.
 * !setteam char team 
   - Sets a character's team.
 * !removecharitem char itemname 
   - Removes an item from the character's inventory.
 * !equipweapon char weapon 
   - Equips the weapon onto the character if it is in their inventory.
 * !unequipweapon char 
   - Unequips the character's current weapon, if he/she has one.
 * !equipstaff char staff 
   - Equips a staff to the character.

###### CLASS COMMANDS
 * !createclass name 
   - Creates a new class.
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
