package at.woodexplsoive.sleep_percentage.logic;

import at.woodexplsoive.sleep_percentage.config.SleepConfig;
import at.woodexplsoive.sleep_percentage.util.Text;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;

import java.util.List;

public class SleepPercentageTickHandler {
    private static int oldSleeping = 0;

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (!(event.phase == TickEvent.Phase.END
                && event.side == Side.SERVER
                && event.world instanceof WorldServer
                && !event.world.isDaytime()
                && event.world.getTotalWorldTime() % SleepConfig.checkIntervall == 0L)) return;

        World world = event.world;
        List players = world.playerEntities;
        int total = players.size();
        if (total == 0) return;

        int sleeping = 0;
        int neededPlayers = SleepConfig.sleepPercentage * total  / 100;

        for (Object o : players) {
            EntityPlayer player = (EntityPlayer) o;
            if (player.isPlayerFullyAsleep()) {
                sleeping++;
            }

            if (oldSleeping != sleeping && neededPlayers > 1) {
                player.addChatComponentMessage(Text.literal(String.format("Players sleeping: %d/%d", total, neededPlayers)));
            }
        }
        oldSleeping = sleeping;
        if (sleeping == 0) return;

        if (sleeping < neededPlayers) return;

        WorldInfo info = world.getWorldInfo();
        if (world.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
            long time = info.getWorldTime() + 24000L;
            info.setWorldTime(time - time % 24000L);
        }

        for (Object o : players) {
            EntityPlayer player = (EntityPlayer) o;
            if (player.isPlayerSleeping()) {
                player.wakeUpPlayer(false, false, true);
            }
        }

        world.provider.resetRainAndThunder();
    }
}
