package dev.thew.blocktocommand.command.cmd.list;

import dev.thew.blocktocommand.BlockToCommand;
import dev.thew.blocktocommand.command.SubCommand;
import dev.thew.blocktocommand.enums.CMDType;
import dev.thew.blocktocommand.models.CMD;
import dev.thew.blocktocommand.models.CMDBlock;
import dev.thew.blocktocommand.service.CMDService;
import dev.thew.blocktocommand.utils.StringUtils;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class CMDSet implements SubCommand {

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return;
        if (args.length < 2) return;

        CMDType cmdType = getCmdType(args[0]);
        if (cmdType == null) return;

        String command = getCommand(args, 1);
        if (command.isEmpty()) return;

        CMD cmd = getCMD(command, cmdType);
        Block block = player.getTargetBlock(null,10);
        String id = StringUtils.blockToString(block);


        CMDService cmdService = BlockToCommand.getCmdService();
        if (cmdService == null) return;
        if (cmdService.isCMDBlockExist(block)) return;

        CMDBlock cmdBlock = new CMDBlock(id, cmd, block);
        cmdService.addCMDBlock(cmdBlock);

        player.sendMessage("    Блок успешно добавлен!");
        player.sendMessage("    Тип: " + cmdType.name());
        player.sendMessage("    Команда: " + cmd.getCommand());
        player.sendMessage("    Айди: " + StringUtils.blockToString(cmdBlock.getBlock()));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return List.of("ERROR", "ERROR");
        if (args.length == 0) return List.of("CONSOLE", "PLAYER");

        return Collections.emptyList();
    }

    public CMDType getCmdType(String string) {
        String cmdTypeString = string.toUpperCase();
        return CMDType.valueOf(cmdTypeString);
    }

    public CMD getCMD(String command, CMDType type) {
        return new CMD(command, type);
    }

    public String getCommand(String[] args, int multiple){
        StringBuilder commandBuilder = new StringBuilder();
        for (int i = multiple; i < args.length; i++)
            commandBuilder.append(args[i]).append(" ");

        return commandBuilder.toString();
    }
}
