package dev.thew.blocktocommand.command.cmd.list;

import dev.thew.blocktocommand.BlockToCommand;
import dev.thew.blocktocommand.command.SubCommand;
import dev.thew.blocktocommand.models.CMDBlock;
import dev.thew.blocktocommand.service.CMDService;
import dev.thew.blocktocommand.utils.StringUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CMDList  implements SubCommand {

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return;

        TextComponent message = new TextComponent("Список блоков с командой: \n");

        for (CMDBlock cmdBlock : BlockToCommand.getCmdService().getCmdBlocks()) {
            message.addExtra(" - §6Команда:§f "
                    + cmdBlock.getCmdCommand().getCommand()
                    + " §6Тип: §f"
                    + cmdBlock.getCmdCommand().getType()
                    + " §6Айди: §7"
                    + StringUtils.blockToString(cmdBlock.getBlock())
                    + " "
            );

            TextComponent openButton = new TextComponent(ChatColor.GOLD + "{+} ");
            openButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/blockcmd tp " + cmdBlock.getId()));
            openButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§aТелепортироваться")));

            message.addExtra(openButton);

            TextComponent removeButton = new TextComponent(ChatColor.GOLD + "{-}\n");
            removeButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/blockcmd remove " + cmdBlock.getId()));
            removeButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cУдалить")));

            message.addExtra(removeButton);
        }
        player.spigot().sendMessage(message);

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 1) return getIds(BlockToCommand.getCmdService().getCmdBlocks());
        return Collections.emptyList();
    }

    private List<String> getIds(List<CMDBlock> cmdBlocks){
        List<String> ids = new ArrayList<>();

        for (CMDBlock cmdBlock : cmdBlocks)
            ids.add(cmdBlock.getId());

        return ids;
    }


}
