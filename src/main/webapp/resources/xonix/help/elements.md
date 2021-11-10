<meta charset="UTF-8">

## Symbol breakdown
| Sprite | Code | Description |
| -------- | -------- | -------- |
|<img src="https://github.com/codenjoyme/codenjoy-xonix/raw/master/src/main/webapp/resources/xonix/sprite/sea.png" style="width:40px;" /> | `SEA('.')` | Море, которое нужно осушать. Тут плавают морские враги. | 
|<img src="https://github.com/codenjoyme/codenjoy-xonix/raw/master/src/main/webapp/resources/xonix/sprite/land.png" style="width:40px;" /> | `LAND('X')` | Ничейная суша. Тут бегают сухопутные враги. | 
|<img src="https://github.com/codenjoyme/codenjoy-xonix/raw/master/src/main/webapp/resources/xonix/sprite/hero.png" style="width:40px;" /> | `HERO('O')` | Твой герой. | 
|<img src="https://github.com/codenjoyme/codenjoy-xonix/raw/master/src/main/webapp/resources/xonix/sprite/hero_land.png" style="width:40px;" /> | `HERO_LAND('#')` | Захваченная тобой суша. | 
|<img src="https://github.com/codenjoyme/codenjoy-xonix/raw/master/src/main/webapp/resources/xonix/sprite/hero_trace.png" style="width:40px;" /> | `HERO_TRACE('o')` | След, который оставляет герой двигаясь по морю или по сушам противника. Уязвим для морских врагов. После выхода героя на сушу, море (и/или суша другого противника), очерченное следом, превращается в сушу. | 
|<img src="https://github.com/codenjoyme/codenjoy-xonix/raw/master/src/main/webapp/resources/xonix/sprite/hostile.png" style="width:40px;" /> | `HOSTILE('A')` | Герой противника. | 
|<img src="https://github.com/codenjoyme/codenjoy-xonix/raw/master/src/main/webapp/resources/xonix/sprite/hostile_land.png" style="width:40px;" /> | `HOSTILE_LAND('@')` | Захваченные противниками суша. | 
|<img src="https://github.com/codenjoyme/codenjoy-xonix/raw/master/src/main/webapp/resources/xonix/sprite/hostile_trace.png" style="width:40px;" /> | `HOSTILE_TRACE('a')` | След, оставляемые противником во время захвата суши. | 
|<img src="https://github.com/codenjoyme/codenjoy-xonix/raw/master/src/main/webapp/resources/xonix/sprite/marine_enemy.png" style="width:40px;" /> | `MARINE_ENEMY('M')` | Морской враг. | 
|<img src="https://github.com/codenjoyme/codenjoy-xonix/raw/master/src/main/webapp/resources/xonix/sprite/land_enemy.png" style="width:40px;" /> | `LAND_ENEMY('L')` | Сухопутный враг. | 
