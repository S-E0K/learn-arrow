package org.se0k.learnarrow;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.se0k.learnarrow.target.TargetEvent;

import java.util.Objects;

import static org.se0k.learnarrow.CommandEvent.*;
import static org.se0k.learnarrow.target.TargetEvent.blockLoc;

public final class Learn_arrow extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getCommandMap().register("사격장", new CommandEvent("사격장"));
        Bukkit.getCommandMap().register("fl", new ReloadConfirm("fl"));

    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (playerLoc.get(onlinePlayer.getUniqueId()) == null) return;
            onlinePlayer.teleport(playerLoc.get(onlinePlayer.getUniqueId()));
            onlinePlayer.sendMessage("이전 위치로 돌아가기");
            playerLoc.remove(onlinePlayer.getUniqueId());

            if (blockLoc.get(onlinePlayer.getUniqueId()) != null) {
                TargetEvent targetEvent = new TargetEvent();
                targetEvent.removeTargetManager(onlinePlayer);
            }

        });



    }
}
