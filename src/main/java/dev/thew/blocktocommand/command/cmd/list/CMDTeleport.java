package dev.thew.blocktocommand.command.cmd.list;

import dev.thew.blocktocommand.BlockToCommand;
import dev.thew.blocktocommand.command.SubCommand;
import dev.thew.blocktocommand.enums.CMDType;
import dev.thew.blocktocommand.models.CMD;
import dev.thew.blocktocommand.models.CMDBlock;
import dev.thew.blocktocommand.service.CMDService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CMDTeleport  implements SubCommand {

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return;
        if (args.length > 1) return;

        String id = args[0];
        CMDService cmdService = BlockToCommand.getCmdService();
        if (cmdService == null) return;

        CMDBlock cmdBlock = cmdService.getCMDBlock(id);
        if (cmdBlock == null) return;

        player.teleport(cmdBlock.getBlock().getLocation());
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 0) return getIds(BlockToCommand.getCmdService().getCmdBlocks());
        return Collections.emptyList();
    }

    private List<String> getIds(List<CMDBlock> cmdBlocks){
        List<String> ids = new ArrayList<>();

        for (CMDBlock cmdBlock : cmdBlocks)
            ids.add(cmdBlock.getId());

        return ids;
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
