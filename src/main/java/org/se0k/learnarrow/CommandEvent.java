package org.se0k.learnarrow;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandEvent extends BukkitCommand {

    protected CommandEvent(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) return false;

        switch (args[0]) {
            case "이동" -> {

            }
            case "생성" -> {

            }
            case "지우기" -> {

            }
            case "아이템" -> {

            }
        }

        return false;
    }
}
