package dev.thew.blocktocommand.service;

import dev.thew.blocktocommand.enums.CMDType;
import dev.thew.blocktocommand.models.CMD;
import dev.thew.blocktocommand.models.CMDBlock;
import dev.thew.blocktocommand.utils.StringUtils;
import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CMDService implements Listener {

    public CMDService(FileConfiguration config, Plugin plugin) {
        this.config = config;
        this.plugin = plugin;

        init();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private final FileConfiguration config;
    private final Plugin plugin;
    private List<CMDBlock> cmdBlocks;

    public void init() {
        cmdBlocks = new ArrayList<>();

        ConfigurationSection cmdSection = config.getConfigurationSection("blocks");
        assert cmdSection != null;

        for (String key : cmdSection.getKeys(false)){
            Block block = StringUtils.getBlockFromString(key);
            Validate.notNull(block, "Block \"" + key + "\" is null");

            ConfigurationSection commandSection = cmdSection.getConfigurationSection(key);
            assert commandSection != null;

            String command = commandSection.getString("command");
            Validate.notNull(command, "Command \"" + command + "\" is null");

            String stringCMDType = commandSection.getString("type");
            Validate.notNull(stringCMDType, "Type \"" + stringCMDType + "\" is null");
            CMDType cmdType = CMDType.valueOf(stringCMDType.toUpperCase());

            CMD cmd = new CMD(command, cmdType);
            CMDBlock cmdBlock = new CMDBlock(key, cmd, block);
            cmdBlocks.add(cmdBlock);
        }

    }

    public @Nullable CMDBlock getCMDBlock(String id){
        for (CMDBlock cmdBlock : cmdBlocks)
            if (cmdBlock.getId().equalsIgnoreCase(id)) return cmdBlock;

        return null;
    }

    public @Nullable CMDBlock getCMDBlock(Block block){
        for (CMDBlock cmdBlock : cmdBlocks)
            if (cmdBlock.isCMDBlock(block)) return cmdBlock;

        return null;
    }

    public boolean isCMDBlockExist(Block block){
        return getCMDBlock(block) != null;
    }

    private void addToConfig(CMDBlock cmdBlock){
        setConfig(cmdBlock, cmdBlock.getCmdCommand().getCommand(), cmdBlock.getCmdCommand().getType().name());
    }

    private void removeFromConfig(CMDBlock cmdBlock){
        setConfig(cmdBlock, null, null);
    }

    private void setConfig(CMDBlock cmdBlock, String command, String type){
        config.set(StringUtils.blockToString(cmdBlock.getBlock()) + ".command", command);
        config.set(StringUtils.blockToString(cmdBlock.getBlock()) + ".type", type);

        plugin.saveConfig();
        plugin.reloadConfig();
    }

    public void addCMDBlock(CMDBlock cmdBlock){
        cmdBlocks.add(cmdBlock);
        addToConfig(cmdBlock);
    }

    public void removeCMDBlock(CMDBlock cmdBlock){
        cmdBlocks.remove(cmdBlock);
        removeFromConfig(cmdBlock);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onClick(@NotNull PlayerInteractEvent event){
        Action action = event.getAction();
        if (!(action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.LEFT_CLICK_BLOCK))) return;

        Block block = event.getClickedBlock();
        CMDBlock CMDBlock = getCMDBlock(block);
        if (CMDBlock == null) return;

        Player player = event.getPlayer();
        CMDBlock.execute(player);
    }

}
