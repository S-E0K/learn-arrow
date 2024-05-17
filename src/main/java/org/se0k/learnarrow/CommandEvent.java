package org.se0k.learnarrow;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.se0k.learnarrow.target.TargetEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandEvent extends BukkitCommand {

    static final List<Location> lastLoc = new ArrayList<>();

    protected CommandEvent(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) return false;

        switch (args[0]) {
            case "이동" -> {
                lastLoc.add(player.getLocation());
                World world = targetWorld("shooter");
                Location location = new Location(world, 0.5, world.getHighestBlockYAt(0, 0) + 1, 0.5);
                player.teleport(location);
                player.sendMessage("사격장 이동");
            }
            case "돌아가기" -> {
                if (lastLoc.isEmpty()) {
                    player.sendMessage("사격장에 가지 않았습니다");
                    return false;
                }
                player.teleport(lastLoc.get(lastLoc.size() - 1));
                player.sendMessage("이전 위치로 돌아가기");
                lastLoc.remove(lastLoc.size() - 1);
            }
            case "생성" -> {
                if (lastLoc.isEmpty()) {
                    player.sendMessage("사격장에 가지 않았습니다");
                    return false;
                }
                TargetEvent targetEvent = new TargetEvent();

                targetEvent.createTargetManager(player);



            }
//            case "지우기" -> {
//
//            }
//            case "아이템" -> {
//
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
        if (args.length == 1) return Arrays.asList("이동", "돌아가기", "생성");
        return null;
    }
}
