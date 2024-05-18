package org.se0k.learnarrow.arrow;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

public interface ArrowManager {
    void giveArrow(Player player);
    void targetCheck(Player player); //활 무한 하고 왼손 화살 하나씩 지우기
    void shotArrowEvent(ProjectileHitEvent event);
    void shootCheck(EntityShootBowEvent event);
    int itemCheck(Player player, String string);
    void itemRemove(Player player);
    void setArrowDisplay(ItemStack itemStack, String type, String lore, Player player, Integer amount);
}
