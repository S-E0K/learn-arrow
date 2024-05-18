package org.se0k.learnarrow;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.se0k.learnarrow.arrow.ArrowEvent;
import org.se0k.learnarrow.target.TargetEvent;

import java.util.*;

import static org.se0k.learnarrow.target.TargetEvent.*;
import static org.se0k.learnarrow.arrow.ArrowEvent.*;

public class CommandEvent extends BukkitCommand {

    static final HashMap<UUID, Location> playerLoc = new HashMap<>();

    protected CommandEvent(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        UUID playerUUID = player.getUniqueId();

        if (args.length == 0) return false;

        switch (args[0]) {
            case "이동" -> {
                if (playerLoc.get(playerUUID) == null) playerLoc.put(playerUUID, player.getLocation());

                World world = targetWorld("shooter");
                Location location = new Location(world, 0.5, world.getHighestBlockYAt(0, 0) + 1, 0.5);
                player.teleport(location);
                player.sendMessage("사격장 이동");
            }
            case "돌아가기" -> {

                if (playerLoc.get(playerUUID) == null) {
                    player.sendMessage("사격장에 가지 않았습니다");
                    return false;
                }

                player.teleport(playerLoc.get(playerUUID));
                playerLoc.remove(playerUUID);
                player.sendMessage("이전 위치로 돌아가기");

            }
            case "생성" -> {
                startTime = System.currentTimeMillis();
                if (playerLoc.get(playerUUID) == null) {
                    player.sendMessage("사격장에 가지 않았습니다");
                    return false;
                }
                if (!(blockLoc.get(playerUUID) == null)) {
                    player.sendMessage("이미 만들었습니다");
                    return false;
                }

                ArrowEvent arrowEvent = new ArrowEvent();
                arrowEvent.giveArrow(player);

                targetCheck = 0;
                leftHand = 0;
                shootCheck = 0;

                TargetEvent targetEvent = new TargetEvent();
                targetEvent.createTargetManager(player);
            }
            case "지우기" -> {
                if (blockLoc.get(playerUUID) == null) {
                    player.sendMessage("과녁을 만들지 않았습니다");
                    return false;
                }

                TargetEvent targetEvent = new TargetEvent();
                targetEvent.removeTargetManager(player);

            }
//            case "아이템지급" -> {
//                World world = player.getWorld();
//                if (!world.getName().equals("shooter")) {
//                    player.sendMessage("사격장이 아닙니다");
//                    return false;
//                }
//                ArrowEvent arrowEvent = new ArrowEvent();
//                arrowEvent.giveArrow(player);
//            }
        }

        return false;
    }

    public World targetWorld(String name) {
        WorldCreator worldCreator = new WorldCreator(name);
        worldCreator.type(WorldType.FLAT);
        worldCreator.generateStructures(false);
        return worldCreator.createWorld();
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return Arrays.asList("이동", "돌아가기", "생성", "지우기");
        return null;
    }
}
