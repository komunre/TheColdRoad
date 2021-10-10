package dev.komunre.thecoldroad;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class HeatManager {
    @SubscribeEvent
    public static void RenderOverlay(RenderGameOverlayEvent renderEvent) {
        ScaledResolution resolution = renderEvent.getResolution();
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayer player = minecraft.player;
        if (!player.getEntityData().hasKey("heat")) {
            minecraft.fontRenderer.drawString("No heat!", 0, 0, 1);
            return;
        }
        minecraft.fontRenderer.drawString(String.valueOf(player.getEntityData().getFloat("heat")), 0, 0, 1);

        //GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        /*GL11.glColor3b((byte)255, (byte)100, (byte)0);
        GL11.glVertex2d(0, 0);
        GL11.glVertex2d(0, 30);
        GL11.glVertex2d(player.getEntityData().getFloat("heat"), 30);
        GL11.glVertex2d(player.getEntityData().getFloat("heat"), 0);
        GL11.glColor3f(1, 1, 1);
        GL11.glEnd();*/
    }

    @SubscribeEvent
    public static void ResetHeat(PlayerEvent.PlayerRespawnEvent event) {
        event.player.getEntityData().setFloat("heat", 100);
    }

    @SubscribeEvent
    public static void LivingUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase ent = event.getEntityLiving();
        if (!ent.getEntityData().hasKey("heat")) {
            ent.getEntityData().setFloat("heat", 100);
        }
        float currHeat = ent.getEntityData().getFloat("heat");
        currHeat -= 600f * TheColdRoad.DeltaTime;
        ent.getEntityData().setFloat("heat", currHeat);

        if (currHeat <= 0) {
            ent.setHealth(0);
        }
    }
}
