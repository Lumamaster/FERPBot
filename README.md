# FERPBot
A Discord bot meant to simulate Fire Emblem style combat with the capability to create custom skills, weapons, classes, characters, and so much more.

The bot is currently in beta testing. Features are missing, and commands may not work properly.

This bot was made in IntelliJ IDEA Community Edition, and uses Discord4J as part of its functionality.

Further documentation will be shown below as bot development continues.

TODO LIST: Implement statuses and status staves, implement team commands, implmement permissions and player only control.

Use the jar file in the root directory if you want to host this bot under your own bot account. It will be updated to the latest build as time goes on. Just put your bot account token in a txt file named "token.txt" in the directory with the jar file, and run the jar file via command prompt.

Below are a list of commands and their functions.

## Commands
###### CHARACTER COMMANDS
 * !createchar "name" 
   - Creates a new character.
 * !deletechar "name"
   - Deletes an existing character if it exists.
 * !charinfo "name" 
   - Prints information about a character.
 * !changecharname "name" "newname"
   - Changes a character's name.
 * !setchardescription "name" "desc" 
   - Changes a character's description.
 * !setcharclass "name" "class" 
   - Changes a character's class. Weapon ranks, etc. will have to be edited manually.)
 * !setcharrace "name" "race"
   - Sets a character's race.
 * !setcharweaponrank "name" weapon rank 
   - Sets a character's rank in the given weapon.
 * !setcharlevel "name" level 
   - Sets the character's current level.
 * !setcharexp "name" experience 
   - Sets the character's current experience.
 * !addexp "name" experience 
   - Gives the character experience, and levels them up if they go over 100.
 * !levelchar "name" 
   - Levels up a character.
 * !sethp "name" hp 
   - Sets a character's maximum HP.
 * !setstr "name" strength 
   - Sets a character's strength stat.
 * !setmag "name" magic 
   - Sets a character's magic stat.
 * !setskl "name" skill 
   - Sets a character's skill stat.
 * !setspd "name" speed 
   - Sets a character's speed stat.
 * !setlck "name" luck 
   - Sets a character's luck stat.
 * !setdef "name" defense 
   - Sets a character's defense stat.
 * !setres "name" resistance 
   - Sets a character's resistance stat.
 * !setcurrhp "name" hp 
   - Sets a character's current HP.
 * !setcharhitbonus "name" bonus 
   - Sets a character's bonus to hit.
 * !setcharavoidbonus "name" bonus 
   - Sets a character's bonus to avoid.
 * !setcharcritbonus "name" bonus 
   - Sets a character's bonus to crit.
 * !sethpgrow "name" growth 
   - Sets a character's HP growth.
 * !setstrgrow "name" growth 
   - Sets a character's Strength growth.
 * !setmaggrow "name" growth 
   - Sets a character's Magic growth.
 * !setsklgrow "name" growth
   - Sets a character's Skill growth.
 * !setspdgrow "name" growth 
   - Sets a character's Speed growth.
 * !setlckgrow "name" growth 
   - Sets a character's Luck growth.
 * !setdefgrow "name" growth
   - Sets a character's Defense growth.
 * !setresgrow "name" growth 
   - Sets a character's Resistance growth.
 * !setlevelcap "name" cap 
   - Sets a character's maximum level. (Default: 20)
 * !promote "character" "class" 
   - Promotes a character to the given class, if it is a promotion path for them.
 * !showinventory "character" 
   - Prints out the character's inventory.
 * !heal "character" HP 
   - Heals the character for the specified amount.
 * !setteam "character" team 
   - Sets a character's team.
 * !removecharitem "character" "item" 
   - Removes an item from the character's inventory.
 * !equipweapon "character" "weapon" 
   - Equips the weapon onto the character if it is in their inventory.
 * !unequipweapon "character" 
   - Unequips the character's current weapon, if he/she has one.
 * !equipstaff "character" "staff" 
   - Equips a staff to the character.

###### CLASS COMMANDS
 * !createclass "name" 
   - Creates a new class.
 * !addclasspromopath "name" "class"
   - Adds a promotion path to the specified class.
 * !removeclasspromopath "name" "class"
   - Removes a promotion path from the specified class.
 * !setclassrankbonus "name" weapon bonus
   - Sets a weapon rank bonus for the given class.
 * !addclassskillbonus "name" "skill"
   - Adds a skill that is granted by the class.
 * !removeclassskillbonus "name" "skill"
   - Removes a skill that is granted by the class.
 * !setclassstatbonus "name" stat bonus
   - Sets a stat bonus conferred by the class.
 * !setclassattribute "name" attribute true/false
   - Sets various attributes about the class, such as if it is mounted, armored, etc. or not.
 * !setclassbonusrankfixed "name" true/false
   - Sets if the bonus weapon ranks gained upon promotion are added to the character, or just set to that level if below.
 * !classinfo "name"
   - Prints out information about the specified class.

###### SKILL COMMANDS
 * !createpassiveskill "name"
   - Creates a new passive skill.
 * !createprocskill "name" procrate damagemultiplier activationstat inputstat
   - Creates a new proc skill.
 * !createmultihitskill "name" hitcount procrate damagemulti inputstat
   - Creates a new multi hit skill.
 * !createinitiationskill "name" buff stat
   - Creates a new initiation skill.
 * !createdefensiveskill "name" buff stat
   - Creates a new defensive skill.
 * !createbreakerskill "name" weapon stat advantage
   - Creates a new weapon breaker skill.
 * !createslayerskill "name" "race" stat advantage
   - Creates a new anti race skill.
 * !createmiracleskill "name" stat multiplier
   - Creates a new miracle-type skill.
 * !deleteskill "name"
   - Deletes the specified skill if it exists.
 * !setskillname "skill" "name"
   - Sets the name of the skill.
 * !setskilldesc "skill" "description"
   - Sets the description of the skill.
 * !listskills
   - Lists all skills currently created.
 * !skillinfo "skill"
   - Prints out information about a specified skill.
 * !setslayrace "skill" "race"
   - Sets which race a slayer-type skill is effective against.
 * !setslaybonus "skill" stat bonus
   - Sets the bonus that a slayer-type skill grants.
 * !setbreakerweapon "skill" weapon
   - Sets which weapon a breaker-type skill is effective against.
 * !setbreakerbonus "skill" stat bonus
   - Sets the bonus that a breaker-type skill grants.
 * !setmiraclerate "skill" rate
   - Sets the multiplier that is used by the skill to determine activation percentage.
 * !setmiraclestat "skill" stat
   - Sets the stat that a miracle-type skill uses for calculations.
 * !setdefensivebuff "skill" stat buff
   - Sets the buff granted by a defensive-type skill.
 * !setoffensivebuff "skill" stat buff
   - Sets the buff granted by an initiation-type skill.
 * !setthresholdthresh "skill" threshold
   - Sets the threshold upon which a threshold-type skill activates.
 * !setthresholdbuff "skill" buff
   - Sets the buff granted by a threshold-type skill.
 * !setskillprocrate "skill" rate
   - Sets the multiplier that is used by the skill to determine activation percentage.
 * !setprocskillstatmulti "skill" statmulti
   - Sets how much the stat used by the skill is multiplied to determine bonus damage.
 * !setprocskillactistat "skill" stat
   - Sets which stat the skill uses for activation calculations.
 * !setprocskillusenemystat "skill" yes/no
   - Sets if the skill uses enemy stats for calculations.
 * !setprocskillcancel "skill" yes/no
   - Sets if the skill prevents enemy counterattacks on activation.
 * !setprocskillwhenattacked "skill" yes/no
   - Sets if the skill only activates when being attacked.
 * !setprocskillinputstat "skill" stat
   - Sets which stat the skill uses for determining damage bonus.
 * !setprocskillisdamaging "skill" yes/no
   - Sets if the skill is meant to deal damage or not.
 * !setprocskillishealing "skill" yes/no
   - Sets if the skill is meant to heal the user or not.
 * !setpassiveskillbuff "skill" stat buff
   - Sets the stat bonus granted by a passive skill.
 * !setvantage "skill" yes/no
   - Sets if a passive skill acts like a vantage-type skill (allows user to attack first in combat regardless of initiator).
 * !setvantagethreshold "skill" threshold
   - Sets the HP% threshold for a vantage skill to activate.

###### ITEM COMMANDS
 * !createitem "name" type uses
   - Creates a new item. (item, weapon, staff, consumable)
 * !setitemdescription "item" "desc"
   - Sets the item's description.
 * !makeindestructible "item" true/false
   - Sets if an item is indestructible.
 * !useitem "character" "item"
   - Decrements an item's uses by 1.
 * !breakitem "character" "item"
   - Breaks an item in a character's inventory.
 * !iteminfo "item"
   - Gives information on an item.

###### WEAPON COMMANDS
 * !setphysadvantage "weapon" weapontype
   - Sets which physical weapon a weapon is advantageous against.
 * !setphysweakness "weapon" weapontype
   - Sets which physical weapon a weapon is disadvantageous against.
 * !setmagadvantage "weapon" weapontype
   - Sets which magical weapon a weapon is advantageous against.
 * !setmagdisadvantage "weapon" weapontype
   - Sets which magical weapon a weapon is disadvantageous against.
 * !setrequiredweaponrank "weapon" rank
   - Sets the required weapon rank for a weapon to be usable.
 * !setweaponeffectiveness "weapon" weapontype true/false
   - Sets if a weapon is effective against a weapon type.
 * !setarmorslaying "weapon" true/false
   - Sets if a weapon is effective against armored units.
 * !setantifliers "weapon" true/false
   - Sets if a weapon is effective against flying units.
 * !setantimounted "weapon" true/false
   - Sets if a weapon is effective against mounted units
 * !settargetsres "weapon" true/false
   - Sets if a weapon targets the foe's Resistance or not.
 * !setusesmagicstat "weapon" true/false
   - Sets if a weapon uses the user's Magic stat for damage calculation or not.
 * !setweaponhitrate "weapon" hitrate
   - Sets a weapon's base hit rate.
 * !setweaponmight "weapon" might
   - Sets a weapon's base might.
 * !setweaponcritrate "weapon" critrate
   - Sets a weapon's base crit rate.
 * !setweaponcounterable "weapon" true/false
   - Sets if a weapon is counterable.
 * !setminweaponrange "weapon" range
   - Sets the weapon's minimum range.
 * !setmaxweaponrange "weapon" range
   - Sets the weapon's maximum range.
 * !setbraveweapon "weapon" yes/no
   - Sets if a weapon makes immediate consecutive attacks or not.
 * !setweaponcandouble "weapon" yes/no
   - Sets if a weapon is able to make follow up attacks.
 * !setweaponignoredefense "weapon" yes/no
   - Sets if a weapon ignores the target's defense/resistance.
 * !setlifesteal "weapon" yes/no
   - Sets if a weapon heals the user equal to damage dealt.

###### STAFF COMMANDS
 * !setstaffrank "staff" rank
   - Sets the required rank to use the specified staff.
 * !sethealingstaff "staff" yes/no
   - Sets if a staff is a healing staff or status staff.
 * !setstaffmagmulti "staff" multiplier
   - Sets the multipiler used on the user's magic stat when calculating heal amount.
   
###### BATTLE COMMANDS
 * !battle "initiator" "defender"
   - Commences a battle between the two characters.
 * !battleforecast "initiator" "defender"
   - Shows a battle prediction between the two characters.
 * !battlestaff "initiator" "target"
   - Uses the initiator's currently equipped staff on the target character.
