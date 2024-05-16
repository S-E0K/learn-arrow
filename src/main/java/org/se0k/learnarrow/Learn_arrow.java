package org.se0k.learnarrow;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Learn_arrow extends JavaPlugin {

    @Override
    public void onEnable() {

    Bukkit.getCommandMap().register("사격장", new CommandEvent("사격장"));


    }

    @Override
    public void onDisable() {

    }
}
