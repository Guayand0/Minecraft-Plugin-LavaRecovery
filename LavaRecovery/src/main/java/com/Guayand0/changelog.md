# 1.0.0

- permisos
- comandos
- configuracion
- mensajes

# 1.0.1

- Fixed update-checker

- Added player.yml

- Added more config in config.yml:
```yml
config:
  # Maximum number of recovery times allowed per player
  max-recovery-times-player: 5 # Set -1 to disable

  # Chat prefix for plugin messages
  chat-prefix: "&4[&6&lLavaRecovery&4]&f"
```
- Modified config in config.yml:

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

- Added more messages in messages/en.yml:
```yml
config:
  lava-recovery-times: '%chatplugin% &aYou have collected lava from obsidian &e%lavatimes% &aout of &e%maxlavatimes% &atimes'

  max-lava-recovery-reached: '%chatplugin% &cYou have reached the max times you can collect lava from obsidian'
```

# 1.0.2

- Added more config in config.yml:
``` yml
config:
  # Maximum number of recovery times allowed per player
  max-recovery-times-player: 5 # Set -1 to disable
```

- Modified config in config.yml:

Before
``` yml
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

- Modified player in player.yml:

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
```****