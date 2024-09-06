package dev.thew.blocktocommand.command.cmd.list;

import dev.thew.blocktocommand.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class CMDList  implements SubCommand {

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return;

        

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }


}
