package org.se0k.learnarrow.target;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.EventListener;

public class TargetEvent implements TargetManager, EventListener {


    @Override
    public void createTargetManager(Player player) {

        World world = player.getWorld();

        Material target = Material.TARGET;

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

        Location startLoc = new Location(world, x, y, z);

        for(int i = 0; i < xSize; i++) {
            for(int j = 0; j < zSize; j++) {
                for (int k = 0; k < ySize; k++) {
                    startLoc.set(x + i, y + k, z + j);
                    startLoc.getBlock().setType(target);


                }
                startLoc.setY(y);
            }
            startLoc.setZ(z);
        }







    }

    @Override
    public void removeTargetManager() {

    }
}
