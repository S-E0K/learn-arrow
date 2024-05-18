package org.se0k.learnarrow.arrow;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.se0k.learnarrow.target.TargetEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArrowEvent implements ArrowManager, Listener {

    public static int targetCheck;
    public static int leftHand;
    public static int itemCheck;
    public static double startTime;
    double endTime;
    public static int shootCheck;

    private final ItemStack frontArrow = new ItemStack(Material.ARROW, 1);
    private final ItemStack upDownArrow = new ItemStack(Material.SPECTRAL_ARROW, 1);
    private final ItemStack sideArrow = new ItemStack(Material.TIPPED_ARROW, 1);
    private final ItemStack bow = new ItemStack(Material.BOW, 1);

    @Override
    public void giveArrow(Player player) {

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



    @EventHandler(priority = EventPriority.HIGH)
    public void shootCheck(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.getInventory().getItemInOffHand().isEmpty()) {
            event.setCancelled(true);
            return;
        }
        shootCheck += 1;

        if (shootCheck == 50 && targetCheck != 49) {
            endTime = System.currentTimeMillis();
            player.sendMessage("모든 과녁을 맞추지 못하셨습니다");

            player.sendMessage("타입 1: 0개, 타입 2: 0개, 타입 3: 0개 남음");

            itemRemove(player);

            player.sendMessage("남은 과녁의 개수: " + (49 - targetCheck));
            player.sendMessage("플레이 시간: " + String.valueOf((endTime - startTime) / 1000) + "초");

            TargetEvent targetEvent = new TargetEvent();
            targetEvent.removeTargetManager(player);

        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void shotArrowEvent(ProjectileHitEvent event) {
        Player player = (Player) event.getEntity().getShooter();
        if (player == null) return;

        Block block = event.getHitBlock();
        if (Objects.requireNonNull(block).getType() != Material.TARGET) return;
        if (event.getEntity().getWorld() != Bukkit.getWorld("shooter")) return;

        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();

        Location isTarget = block.getLocation();

        int playerX = (int) player.getLocation().getX();
        int playerY = (int) player.getLocation().getY();
        int playerZ = (int) player.getLocation().getZ();

        int xLoc = playerX - x;
        int yLoc = playerY - y;
        int zLoc = playerZ - z;

        int targetLoc = xLoc * xLoc + yLoc * yLoc + zLoc * zLoc;

        if (targetLoc < 400) {
            player.sendMessage("거리가 20칸 미만입니다");
            event.setCancelled(true);
            return;
        }

        switch (player.getInventory().getItemInOffHand().getType().name()) {
            case "ARROW" -> {
                if (isTarget.getBlock().getType() == Material.TARGET) {
                    block.setType(Material.AIR);
                    targetCheck += 1;
                }
            }
            case "SPECTRAL_ARROW" -> {
                isTarget.set(x - 1, y, z - 1);
                for(int i = 0; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (isTarget.getBlock().getType() == Material.TARGET) {
                            isTarget.getBlock().setType(Material.AIR);
                            targetCheck += 1;
                        }
                        isTarget.add(1, 0, 0);
                    }
                    isTarget.setX(x - 1);
                    isTarget.add(0, 0, 1);
                }
            }
            case  "TIPPED_ARROW" -> {
                isTarget.set(x, y - 1, z);
                for(int i = 0; i < 3; i++) {
                    if (isTarget.getBlock().getType() == Material.TARGET) {
                        isTarget.getBlock().setType(Material.AIR);
                        targetCheck += 1;
                    }
                    isTarget.add(0, 1, 0);
                }
            }
        }

        player.sendMessage(String.valueOf(targetCheck));

        targetCheck(player);

        event.getEntity().remove();
    }

    @Override
    public void targetCheck(Player player) {

        if (targetCheck == 49) {
            endTime = System.currentTimeMillis();
            player.sendMessage("모든 과녁을 맞추셨습니다");

            player.sendMessage("타입 1: " + itemCheck(player, "타입 1") + "개, 타입 2: " + itemCheck(player, "타입 2") + "개, 타입 3: " + itemCheck(player, "타입 3") + "개 남음");

            itemRemove(player);

            player.sendMessage("남은 과녁의 개수: " + (49 - targetCheck));
            player.sendMessage("플레이 시간: " + String.valueOf((endTime - startTime) / 1000) + "초");

            TargetEvent targetEvent = new TargetEvent();
            targetEvent.removeTargetManager(player);

        }
    }

    public void setArrowDisplay(ItemStack itemStack, String type, String lore, Player player, Integer amount) {
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

    public int itemCheck(Player player, String name) {
        itemCheck = 0;

        ItemStack[] inventory = player.getInventory().getContents();
        for(ItemStack item : inventory) {
            if (item == null) continue;

            String displayName = item.getItemMeta().getDisplayName();

            if (displayName.equals(name)) {
                itemCheck += item.getAmount();
            }
        }

        return itemCheck;
    }

    public void itemRemove(Player player) {
        player.getInventory().remove(Material.BOW);
        player.getInventory().remove(Material.ARROW);
        player.getInventory().remove(Material.SPECTRAL_ARROW);
        player.getInventory().remove(Material.TIPPED_ARROW);
        player.getInventory().getItemInOffHand().setAmount(0);
    }
}
