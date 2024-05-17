package org.se0k.learnarrow.arrow;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ArrowEvent implements ArrowManager{
    @Override
    public void giveArrow(Player player) {
        ItemStack bow = new ItemStack(Material.BOW, 1);
        ItemStack frontArrow = new ItemStack(Material.ARROW, 1);
        ItemStack upDownArrow = new ItemStack(Material.SPECTRAL_ARROW, 1);
        ItemStack sideArrow = new ItemStack(Material.SPECTRAL_ARROW, 1);

        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.setDisplayName("사격용 활");
        bowMeta.setUnbreakable(true);

        List<String> bowLore = new ArrayList<>();
        bowLore.add("내구도가 무한이다");
        bowMeta.setLore(bowLore);
        bow.setItemMeta(bowMeta);

        player.getInventory().addItem(bow);

        setArrowDisplay(frontArrow, "타입 1", "명중한 블록을 제거", player, 30);
        setArrowDisplay(upDownArrow, "타입 2", "명중한 블록과 위 아래 블록 제거", player, 10);
        setArrowDisplay(sideArrow, "타입 3", "명중한 블록과 양 옆 블록 제거", player, 10);

        bowLore.clear();
    }

    @Override
    public void blockDestroy(Player player) {

    }

    void setArrowDisplay(ItemStack itemStack, String type, String lore, Player player, Integer amount) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(type);

        List<String> loreList = new ArrayList<>();
        loreList.add(lore);
        meta.setLore(loreList);
        itemStack.setAmount(amount);
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);

        loreList.clear();
    }
}
