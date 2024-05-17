package org.se0k.learnarrow.arrow;

import org.bukkit.entity.Player;

public interface ArrowManager {
    void giveArrow(Player player);
    void blockDestroy(Player player); //활 무한 하고 왼손 화살 하나씩 지우기

}
