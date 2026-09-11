package at.woodexplsoive.sleep_percentage.logic;

import at.woodexplsoive.sleep_percentage.util.Text;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class BedClickEvent {

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!(event.entityPlayer instanceof EntityPlayerMP)) return;

        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;

        Block bed = event.world.getBlock(event.x, event.y, event.z);
        if (bed != Blocks.bed) return;

        EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;

        ChunkCoordinates bedSpawn = player.getBedLocation(player.dimension);
        boolean alreadySetHere = bedSpawn != null
                && bedSpawn.posX == event.x
                && bedSpawn.posY == event.y
                && bedSpawn.posZ == event.z;

        if (alreadySetHere) return;

        player.setSpawnChunk(new ChunkCoordinates(event.x, event.y, event.z),
                false,
                player.dimension
        );

        player.addChatMessage(Text.literal("Respawn point set"));
    }
}
