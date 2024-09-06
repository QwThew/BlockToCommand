package dev.thew.blocktocommand.enums;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum CMDType {

    CONSOLE {
        @Override
        public void execute(String command, Player player) {
            String newcommand = command.replace("%player%", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), newcommand);
        }
    },
    PLAYER {
        @Override
        public void execute(String command, Player player) {
            player.performCommand(command);
        }
    };

    public abstract void execute(String command, Player player);

}
