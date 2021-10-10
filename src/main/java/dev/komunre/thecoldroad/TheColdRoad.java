package dev.komunre.thecoldroad;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.repackage.com.nothome.delta.Delta;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.HashMap;

@Mod(modid = TheColdRoad.MODID, name = TheColdRoad.NAME, version = TheColdRoad.VERSION)
public class TheColdRoad
{
    public static final String MODID = "thecoldroad";
    public static final String NAME = "The Cold Road";
    public static final String VERSION = "0.1";

    public WorldType coldWorld;

    public static Logger logger;

    public static double DeltaTime = 0;
    private static long lastTick = 0;


    @SubscribeEvent
    public static void RenderOverlay(RenderGameOverlayEvent renderEvent) {
        long time = System.nanoTime();
        DeltaTime = (time - lastTick) / 1000000000.0f;
        lastTick = time;
    }



    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        ConfigManager.init();
        int freezeSpeed = ConfigManager.getInt("cold", "cold_speed");
        if (freezeSpeed == 0) {
            ConfigManager.createConfig();
            freezeSpeed = ConfigManager.getInt("cold", "cold_speed");
        }
        HeatManager.ColdSpeed = freezeSpeed;

        lastTick = System.currentTimeMillis();
        GameRegistry.registerTileEntity(HeatSourceTile.class, new ResourceLocation("heat_source"));
        HeatFurnace heatFurnace = new HeatFurnace();
        ItemBlock heatFurnaceItem = RegisterBlock(heatFurnace);
        coldWorld = new ColdWorld();
        GameRegistry.addShapedRecipe(heatFurnace.getRegistryName(), null, new ItemStack(heatFurnaceItem, 1), new Object[] {
                "RRR",
                "R R",
                "RWR", 'R', ItemBlock.getByNameOrId("cobblestone"), 'W', ItemBlock.getByNameOrId("planks")});
    }

    private ItemBlock RegisterBlock(Block target) {
        ForgeRegistries.BLOCKS.register(target);
        ItemBlock itemBlock = new ItemBlock(target);
        itemBlock.setRegistryName(target.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlock);
        return itemBlock;
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("Initializing The Cold Road...");
        MinecraftForge.EVENT_BUS.register(this.getClass());
        MinecraftForge.EVENT_BUS.register(HeatManager.class);
        logger.info("The Cold Road initialized!");
    }
}
