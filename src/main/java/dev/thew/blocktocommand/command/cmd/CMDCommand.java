package dev.thew.blocktocommand.command.cmd;

import dev.thew.blocktocommand.command.LongCommandExecutor;
import dev.thew.blocktocommand.command.cmd.list.CMDRemove;
import dev.thew.blocktocommand.command.cmd.list.CMDSet;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CMDCommand extends LongCommandExecutor {

    public CMDCommand() {
        add(new CMDSet(), new String[]{"set"}, new Permission("blockcmd.edit.set"));
        add(new CMDRemove(), new String[]{"remove"}, new Permission("blockcmd.edit.remove"));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) return false;
        final LongCommandExecutor.SubCommandWrapper wrapper = getWrapperFromLabel(args[0]);
        if (wrapper == null) return false;

        if (!sender.hasPermission(wrapper.permission()) && !sender.hasPermission("blockcmd.edit.admin")) return true;

        wrapper.command().execute(sender, args);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return getAliases();

        final SubCommandWrapper wrapper = getWrapperFromLabel(args[0]);
        if (wrapper == null) return null;

        return wrapper.command().onTabComplete(sender, args);
    }
}
