package dev.komunre.thecoldroad;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class HeatSourceTile extends TileEntity implements ITickable {
    public float burnTime = 0;
    public boolean burning = false;
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setFloat("burnTime", burnTime);
        compound.setBoolean("burning", burning);
        return  compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        burnTime = compound.getFloat("burnTime");
        burning = compound.getBoolean("burning");
    }

    @Override
    public void update() {
        float burnStrength = burnTime / 10;
        if (burning && burnTime > 0) {
            for (EntityPlayer player : world.playerEntities) {
                double distance = pos.distanceSq(player.getPosition());
                if (distance < 12.4) {
                    float currHeat = player.getEntityData().getFloat("heat");
                    currHeat += (burnStrength * 100 + 200 + HeatManager.ColdSpeed) * TheColdRoad.DeltaTime;
                    player.getEntityData().setFloat("heat", currHeat);
                }
            }
        }
    }
}
