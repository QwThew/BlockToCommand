package dev.thew.blocktocommand;

import dev.thew.blocktocommand.command.cmd.CMDCommand;
import dev.thew.blocktocommand.service.CMDService;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public final class BlockToCommand extends JavaPlugin {

    @Getter
    private static BlockToCommand instance;
    @Getter
    private static CMDService cmdService;

    @Override
    public void onEnable() {
        instance = this;

        if (!new File(getDataFolder(), "config.yml").exists()) saveDefaultConfig();
        FileConfiguration config = getConfig();

        cmdService = new CMDService(config, instance);

        CMDCommand cmdCommand = new CMDCommand();
        hookCommand("blockcmd", cmdCommand, cmdCommand);

        getLogger().log(Level.INFO, "BlockToCommand successfully enabled!");
    }

    private void hookCommand(String command, TabCompleter completer, CommandExecutor executor) {
        PluginCommand pluginCommand = getCommand(command);
        assert pluginCommand != null;
        pluginCommand.setExecutor(executor);
        pluginCommand.setTabCompleter(completer);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
