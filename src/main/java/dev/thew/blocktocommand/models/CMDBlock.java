package dev.thew.blocktocommand.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class CMDBlock {

    private String id;
    private CMD cmdCommand;
    private Block block;

    public boolean isCMDBlock(@NonNull Block block) {
        return this.block.equals(block);
    }

    public void execute(@NonNull Player player){
        cmdCommand.execute(player);
    }

}
