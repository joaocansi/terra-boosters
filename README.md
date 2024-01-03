<h1 align="center">
  <img alt="Terra-Purge" src="./.github/assets/logo.png" title="Terra-Boosters" />
</h1>

<p align="center">
  <b>OBS:</b> Plugin development isn't my main activity. If you need a well-suported plugin I highly recommend you doing your own or hiring someone to make.
</p>

<p align="center">
  <a href="#-project">💻 Project</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-requirements">✨ Requirements</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-configuration">⚙️ Configuration</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-how-to-use">🚀 How to use</a>&nbsp;&nbsp;&nbsp;
</p>

<p align="center">
   <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="MIT License" />
</p>


<p align="center">
   <img width="820px" src="./.github/assets/game.gif" alt="Gif of game working" />
</p>


## 💻 Project

A plugin designed to enhance servers by introducing boosters that multiply players' experience points. This plugin seamlessly integrates with both Minecraft XP and MCMMO skills, offering an enriched gaming experience for users.

## ✨ Requirements

- Minecraft 1.16+
- McMMO

## ⚙️ Configuration

This plugin does not require any previous configuration to run, but you still have the possibility to customize almost everything about it by editing the [config.yml](#-configuration) file.

```
# There are two types of messages you can use:
#
# If you need a chat message, you just need to insert your message normally.
# If you need a title message, you must insert {@} which will divide the message in two parts: title and subtitle.
#
# 'booster_view' is a bit different and just works as a chat message. Since it throws a booster list
# I decided to provide as a StringList. I also divided into header, footer and booster item for a better
# customization.
#
# OBS: Some messages may not work with a title since it's sent to a player.
messages:
  player_not_online: "&a&l[TerraBoosters] &cPlayer not found."
  player_inventory_full: "&a&l[TerraBoosters] &cPlayer {playerName} is full of items."
  command_in_game_only: "&a&l[TerraBoosters] &cThis is an exclusive command for in-game players."
  command_booster_give_syntax: "&a&l[TerraBoosters] &e/boosters give <player> <booster> <amount>"
  booster_not_found: "&a&l[TerraBoosters] &cBooster {boosterId} not found."
  booster_giving_success: "&a&l[TerraBoosters] &eYou gave {boosterAmount}x {boosterName} &eboosters to {receiverName}"
  booster_already_in_use: "&a&l[TerraBoosters]{@}&cThere's a booster with the same skill running."
  booster_consuming_error: "&a&l[TerraBoosters]{@}&cSomething wrong happended. Please contact an admin."
  booster_consuming_success: "&a&l[TerraBoosters]{@}&eBooster {boosterName} &estarted and will expire in {boosterExpiration}"
  booster_view:
    booster_list_header:
      - "&a&l[BOOSTERS]"
      - ""
    booster_list_footer:
    booster_list_item:
      - "{boosterName} &e- &fExpires in {boosterExpiration}"
    booster_list_empty:
      - "&cNo boosters consumed."

settings:
  # Configures booster expiration date format.
  date_format: "dd/MM/yyyy HH:mm"
  storage:
    type: SQL # If you need MySQL, change the type to MySQL and fill the fields right below.
    host: localhost
    port: 3306
    database: test
    username: test
    password: admin

# Translates every skill type to a proper name. For example, McMMO refers to mining skill as MINING, but it's proper
# name could be a way different like miner, mining and etc. You are also allowed to provide a specific color.
skill_translator:
  MINING: "&fMining"
  MINECRAFT_XP: "&fExperience"

boosters:
  0:
    name: "&d&lMINER"
    duration: 3600
    multiplication: 2
    skill: MINING
    item:
      id: EXPERIENCE_BOTTLE
      name: '&e&lBOOSTER'
      description:
        - '&7Type: &d&lMINER (MCMMO)'
        - ''
        - ' &8* &aWith this booster, you will'
        - ' &areceive 2.0x more than usual'
        - ''
        - ' &7Multiplier: &f2.0x'
        - ' &7Duration: &f60 min'
        - ''
      glow: true
  1:
    name: "&a&lXP"
    duration: 3600
    multiplication: 2
    skill: MINECRAFT_XP
    item:
      id: EXPERIENCE_BOTTLE
      name: '&e&lBOOSTER'
      description:
        - '&7Type: &a&lXP (MINECRAFT)'
        - ''
        - ' &8* &aWith this booster, you will'
        - ' &areceive 2.0x more than usual'
        - ''
        - ' &7Multiplier: &f2.0x'
        - ' &7Duration: &f60 min'
        - ''
      glow: true
```

## 🚀 How to use

If you're completely beginner and don't even know how to start a minecraft server, I highly recommend you to follow the steps at [Spigot](https://www.spigotmc.org/wiki/buildtools/) before downloading this plugin.

These are the following steps to use the plugin:
1. Download [TerraPurge](https://github.com/joaocansi/terra-purge/releases)
2. Move `.jar` file to your `plugins` folder
3. Reload the server.

To give the item which purge the plot's ground use the following:
`terrapurge <player_name> <amount>`
The permission is `terrapurge.admin`
