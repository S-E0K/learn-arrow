package org.se0k.learnarrow.target;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.EventListener;
import java.util.HashMap;
import java.util.UUID;

public class TargetEvent implements TargetManager, EventListener {

    public static final HashMap<UUID, Location> blockLoc = new HashMap<>();

    public static final HashMap<UUID, BlockFace> blockFace = new HashMap<>();

    @Override
    public void createTargetManager(Player player) {

        UUID playerUUID = player.getUniqueId();

        World world = Bukkit.getWorld("shooter");

        int x = (int) player.getLocation().getX();
        int y = (int) player.getLocation().getY();
        int z = (int) player.getLocation().getZ();

        int xSize = 1;
        int zSize = 1;
        int ySize = 7;

        switch (player.getFacing()) {
            case EAST -> {
                x += 20;

                zSize = 7;
            }
            case WEST -> {
                x -= 20;

                zSize = 7;
            }
            case SOUTH -> {
                z += 20;

                xSize = 7;
            }
            case NORTH -> {
                z -= 20;

                xSize = 7;
            }
        }
        blockFace.put(playerUUID, player.getFacing());
        Location startLoc = new Location(world, x, y, z);
        blockLoc.put(playerUUID, startLoc);

        for(int i = 0; i < xSize; i++) {
            for(int j = 0; j < zSize; j++) {
                for (int k = 0; k < ySize; k++) {
                    startLoc.set(x + i, y + k, z + j);
                    startLoc.getBlock().setType(Material.TARGET);


                }
                startLoc.setY(y);
            }
            startLoc.setZ(z);
        }
    }

    @Override
    public void removeTargetManager(Player player) {

        UUID playerUUID = player.getUniqueId();

        BlockFace faceCheck = blockFace.get(playerUUID);
        Location startLoc = blockLoc.get(playerUUID);

        int x = (int) startLoc.getX();
        int y = (int) startLoc.getY();
        int z = (int) startLoc.getZ();

        int xSize = 1;
        int zSize = 1;
        int ySize = 7;

        switch (faceCheck) {
            case EAST, WEST -> {
                zSize = 7;
            }
            case SOUTH, NORTH -> {
                xSize = 7;
            }
        }

        for(int i = 0; i < xSize; i++) {
            for(int j = 0; j < zSize; j++) {
                for (int k = 0; k < ySize; k++) {
                    startLoc.set(x - i, y + k, z + j);
                    startLoc.getBlock().setType(Material.AIR);


                }
                startLoc.setY(y);
            }
            startLoc.setZ(z);
        }
        blockFace.remove(playerUUID);
        blockLoc.remove(playerUUID);
    }






}
