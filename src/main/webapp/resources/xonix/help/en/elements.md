<meta charset="UTF-8">

## Symbol breakdown
| Sprite | Code | Description |
| -------- | -------- | -------- |
|<img src="/codenjoy-contest/resources/xonix/sprite/sea.png" style="height:auto;" /> | `SEA('.')` | Море, где живут морские враги. Море нужно делать сушей. | 
|<img src="/codenjoy-contest/resources/xonix/sprite/land.png" style="height:auto;" /> | `LAND('X')` | Ничейная суша, по которой можно передвигаться героям и наземным врагам. | 
|<img src="/codenjoy-contest/resources/xonix/sprite/hero.png" style="height:auto;" /> | `HERO('O')` | Твой герой. | 
|<img src="/codenjoy-contest/resources/xonix/sprite/hero_land.png" style="height:auto;" /> | `HERO_LAND('#')` | Захваченная тобой суша. | 
|<img src="/codenjoy-contest/resources/xonix/sprite/hero_trace.png" style="height:auto;" /> | `HERO_TRACE('o')` | След, который оставляет герой двигаясь по морю или по сушам противника. Уязвим для морских врагов. После выхода героя на сушу, море (и/или суша другого противника), очерченное следом, превращается в сушу. | 
|<img src="/codenjoy-contest/resources/xonix/sprite/hostile.png" style="height:auto;" /> | `HOSTILE('A')` | Герой противника. | 
|<img src="/codenjoy-contest/resources/xonix/sprite/hostile_land.png" style="height:auto;" /> | `HOSTILE_LAND('@')` | Захваченные противниками суша. | 
|<img src="/codenjoy-contest/resources/xonix/sprite/hostile_trace.png" style="height:auto;" /> | `HOSTILE_TRACE('a')` | След, оставляемые противником во время захвата суши. | 
|<img src="/codenjoy-contest/resources/xonix/sprite/marine_enemy.png" style="height:auto;" /> | `MARINE_ENEMY('M')` | Морской враг. | 
|<img src="/codenjoy-contest/resources/xonix/sprite/land_enemy.png" style="height:auto;" /> | `LAND_ENEMY('L')` | Сухопутный враг. | 
