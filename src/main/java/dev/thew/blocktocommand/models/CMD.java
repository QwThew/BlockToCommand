package dev.thew.blocktocommand.models;

import dev.thew.blocktocommand.enums.CMDType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class CMD {

    private String command;
    private CMDType type;

    public void execute(@NonNull Player player) {
        type.execute(command, player);
    }

}
