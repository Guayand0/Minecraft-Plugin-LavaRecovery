# 1.0.0

- Initial Permissions
```
lavarecovery.admin             Admin permission
lavarecovery.updatechecker     Allow receive message if there is an update
lavarecovery.use               Allow to recover lava from obsidian
lavarecovery.cooldown.exempt   Allow not wait to recover lava from obsidian
```

- Initial Commands
```
Aliases: lavarecovery, lr

/lavarecovery help             Show plugin command list (admin)
/lavarecovery reload           Reload plugin config (admin)
/lavarecovery version          Show plugin version (admin)
/lavarecovery author           Show plugin author (admin)
/lavarecovery permissions      Show plugin permission list (admin)
/lavarecovery plugin           Show plugin download link (admin)
```

- Initial Variables
```
%plugin%                       Show plugin name
%chatplugin%                   Show chat plugin prefix
%version%                      Show plugin version
%latestversion%                Show plugin last version
%link%                         Show plugin download link
%author%                       Show plugin author
%timereaming%                  Show reaming time to recover lava from obsidian
```

- Initial config.yml
``` yml
# ############################################################################################################# #
# +-----------------------------------------------------------------------------------------------------------+ #
# |                                                                                                           | #
# |     ██╗      █████╗ ██╗   ██╗ █████╗ ██████╗ ███████╗ █████╗  █████╗ ██╗   ██╗███████╗██████╗ ██╗   ██╗   | #
# |     ██║     ██╔══██╗██║   ██║██╔══██╗██╔══██╗██╔════╝██╔══██╗██╔══██╗██║   ██║██╔════╝██╔══██╗╚██╗ ██╔╝   | #
# |     ██║     ███████║╚██╗ ██╔╝███████║██████╔╝█████╗  ██║  ╚═╝██║  ██║╚██╗ ██╔╝█████╗  ██████╔╝ ╚████╔╝    | #
# |     ██║     ██╔══██║ ╚████╔╝ ██╔══██║██╔══██╗██╔══╝  ██║  ██╗██║  ██║ ╚████╔╝ ██╔══╝  ██╔══██╗  ╚██╔╝     | #
# |     ███████╗██║  ██║  ╚██╔╝  ██║  ██║██║  ██║███████╗╚█████╔╝╚█████╔╝  ╚██╔╝  ███████╗██║  ██║   ██║      | #
# |     ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚════╝  ╚════╝    ╚═╝   ╚══════╝╚═╝  ╚═╝   ╚═╝      | #
# |                                                                                                           | #
# |                    Youtube:    www.youtube.com/@davigamer1614                                             | #
# |                    Github:     https://github.com/Guayand0/Minecraft-Plugin-LavaRecovery                  | #
# |                                                                                                           | #
# +-----------------------------------------------------------------------------------------------------------+ #
# ############################################################################################################# #
config:
  # You can add a new language adding (language.yml) to lang folder
  # If you want to have support for more languages by default, send
  # the .yml file with the translations and the language via GitHub
  # https://github.com/Guayand0/Minecraft-Plugin-LavaRecovery/issues
  # Available default languages: en, es
  # English - en.yml      Spanish - es.yml
  message-language: en
  # Enable/Disable the update checker
  update-checker: true
  # Enable/Disable the turn obsidian into lava option
  obsidian-to-lava: true
  # Cooldown setting to turn obsidian into lava
  cooldown:
    enable: true
    time: 60 # Time in seconds
  # List of allowed worlds in which the plugin works
  allowed-worlds:
    - world
    - skyblock
  # Chat prefix for plugin messages
  chat-prefix: "&4[&6&lLavaRecovery&4]&f"
```

- Initial messages/en.yml
``` yml
# ############################################################################################################# #
# +-----------------------------------------------------------------------------------------------------------+ #
# |                                                                                                           | #
# |     ██╗      █████╗ ██╗   ██╗ █████╗ ██████╗ ███████╗ █████╗  █████╗ ██╗   ██╗███████╗██████╗ ██╗   ██╗   | #
# |     ██║     ██╔══██╗██║   ██║██╔══██╗██╔══██╗██╔════╝██╔══██╗██╔══██╗██║   ██║██╔════╝██╔══██╗╚██╗ ██╔╝   | #
# |     ██║     ███████║╚██╗ ██╔╝███████║██████╔╝█████╗  ██║  ╚═╝██║  ██║╚██╗ ██╔╝█████╗  ██████╔╝ ╚████╔╝    | #
# |     ██║     ██╔══██║ ╚████╔╝ ██╔══██║██╔══██╗██╔══╝  ██║  ██╗██║  ██║ ╚████╔╝ ██╔══╝  ██╔══██╗  ╚██╔╝     | #
# |     ███████╗██║  ██║  ╚██╔╝  ██║  ██║██║  ██║███████╗╚█████╔╝╚█████╔╝  ╚██╔╝  ███████╗██║  ██║   ██║      | #
# |     ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚════╝  ╚════╝    ╚═╝   ╚══════╝╚═╝  ╚═╝   ╚═╝      | #
# |                                                                                                           | #
# |                    Youtube:    www.youtube.com/@davigamer1614                                             | #
# |                    Github:     https://github.com/Guayand0/Minecraft-Plugin-LavaRecovery                  | #
# |                                                                                                           | #
# +-----------------------------------------------------------------------------------------------------------+ #
# ############################################################################################################# #
messages:
  help:
    - '&b<-----------------&aCOMMANDS LAVARECOVERY&b----------------->'
    - '&e/lavarecovery help &7- Show this message'
    - '&e/lavarecovery reload &7- Reload plugin config'
    - '&e/lavarecovery version &7- Show plugin version'
    - '&e/lavarecovery author &7- Show plugin author'
    - '&e/lavarecovery permissions &7- Show plugin permission list'
    - '&e/lavarecovery plugin &7- Show plugin download link'
    - '&7Aliases: &elavarecovery&7, &elr'
  reload: '%plugin% &aThe plugin has been reloaded.'
  version: '%plugin% &aPlugin version: &b%version%'
  author: '%plugin% &aPlugin author: &b%author%'
  permissions:
    - '&b<-----------------&aPERMISSIONS LAVARECOVERY&b----------------->'
    - '&elavarecovery.admin &7- All permissions'
    - '&elavarecovery.updatechecker &7- Allow receive message if there is an update'
    - '&elavarecovery.use &7- Allow get lava from obsidian'
    - '&elavarecovery.cooldown.exempt &7- Avoid wait to get lava from obsidian'
  command-no-argument: '%plugin% &7Use &e/lavarecovery help &7to see the command list.'
  no-perm: '%plugin% &cYou don`t have permission to use that command.'
  console-error: '%plugin% &cYou can`t use that command in console.'
config:
  update-checker:
  - '%plugin% &bThere is a new version available. &f(&e%latestversion%&f).'
  - '%plugin% &bDownload it here: &f%link%'
  - '%plugin% &bSome updates may require you to change some things manually.'
  - '%plugin% &bRead changelog: &f%link%/updates'
  cooldown-message: '%chatplugin% &cYou must wait &e%timereaming% &cseconds to turn obsidian into lava'
```

# 1.0.1

- Fixed update-checker

- Added player.yml

- Added more config in config.yml
```yml
config:
  # Maximum number of recovery times allowed per player
  max-recovery-times-player: 5 # Set -1 to disable

  # Chat prefix for plugin messages
  chat-prefix: "&4[&6&lLavaRecovery&4]&f"
```

- Modified config in config.yml

Before
``` yml
config:
  # Enable/Disable the turn obsidian into lava option
  obsidian-to-lava: true
```

After
``` yml
config:
  # Enable/Disable the lava recovery from obsidian
  lava-recovery: true
```

- Added more messages in messages/en.yml
```yml
config:
  lava-recovery-times: '%chatplugin% &aYou have collected lava from obsidian &e%lavatimes% &aout of &e%maxlavatimes% &atimes'

  max-lava-recovery-reached: '%chatplugin% &cYou have reached the max times you can collect lava from obsidian'
```

- Added more variables
```
%lavatimes%                    Show the amount of times a player recover lava from obsidian in each world
%maxlavatimes%                 Show the max times a player can recover lava from obsidian in each world
```

# 1.0.2

- Added more config in config.yml
``` yml
config:
  # Maximum number of recovery times allowed per player
  max-recovery-times-player: 5 # Set -1 to disable
```

- Modified config in config.yml

Before
``` yml
config:
  # Cooldown setting to turn obsidian into lava
  cooldown:
    enable: true
    time: 60 # Time in seconds
```

After
``` yml
config:
  # Amount of time to recover lava from obsidian
  lava-recovery-cooldown: 60 # Time in seconds, Set -1 to disable
```

- Modified player in player.yml

Before
``` yml
player:
  <UUID>:
    <USERNAME>:
      recovery-times: 0
```

After
``` yml
player:
  <UUID>:
    <USERNAME>:
      allowed-worlds-name:
        <world1>:
          recovery-times: 0
        <world2>:
          recovery-times: 0
```
