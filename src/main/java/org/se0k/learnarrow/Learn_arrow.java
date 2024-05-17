package org.se0k.learnarrow;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static org.se0k.learnarrow.CommandEvent.lastLoc;

public final class Learn_arrow extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getCommandMap().register("사격장", new CommandEvent("사격장"));
        Bukkit.getCommandMap().register("fl", new ReloadConfirm("fl"));

    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            onlinePlayer.teleport(lastLoc.get(lastLoc.size() - 1));
            onlinePlayer.sendMessage("이전 위치로 돌아가기");
            lastLoc.remove(lastLoc.size() - 1);
        });
    }
}
