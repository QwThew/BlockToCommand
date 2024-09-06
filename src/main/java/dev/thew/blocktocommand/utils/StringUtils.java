package dev.thew.blocktocommand.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;


public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("StringUtils class");
    }

    public static Block getBlockFromString(String string) {
        String[] args = string.split(";", 4);
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        String world = args[3];

        World realWorld = Bukkit.getWorld(world);
        if (realWorld == null) return null;
        Location location = new Location(realWorld, x, y, z);
        return location.getBlock();
    }

    public static String blockToString(Block block) {
        String world = block.getWorld().getName();
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        return x + ";" + y + ";" + z + ";" + world;
    }

}
