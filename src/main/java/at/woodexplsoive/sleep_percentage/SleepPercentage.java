package at.woodexplsoive.sleep_percentage;

import at.woodexplsoive.sleep_percentage.config.SleepConfig;
import at.woodexplsoive.sleep_percentage.logic.BedClickEvent;
import at.woodexplsoive.sleep_percentage.logic.SleepPercentageTickHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = SleepPercentage.MODID, name = SleepPercentage.NAME, version = SleepPercentage.VERSION, acceptableRemoteVersions = "*")
public class SleepPercentage {

    public static final String MODID = "sleep_percentage";
    public static final String NAME = "Sleep Percentage";
    public static final String VERSION = "1.0";

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @Instance(MODID)
    public static SleepPercentage instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        EventBus eventBus = MinecraftForge.EVENT_BUS;
        EventBus fmlEventBus = FMLCommonHandler.instance().bus();

        //Load Config file
        SleepConfig.load(event.getSuggestedConfigurationFile());

        // Register Events
        eventBus.register(new BedClickEvent());
        fmlEventBus.register(new SleepPercentageTickHandler());
    }
}
