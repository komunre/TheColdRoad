package dev.komunre.thecoldroad;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class HeatFurnace extends Block {

    public HeatFurnace() {
        super(Material.ROCK);
        setUnlocalizedName("heat_furnace");
        setRegistryName("heat_furnace");
        setTickRandomly(true);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemStack = playerIn.getActiveItemStack();
        float burnTime = 5; /*itemStack.getItem().getItemBurnTime(itemStack)*/;
        //f (burnTime <= 0) return false;
        HeatSourceTile heatSource = (HeatSourceTile) worldIn.getTileEntity(pos);
        if (heatSource == null) return  false;
        TheColdRoad.logger.debug("adding " + String.valueOf(burnTime) + " time");
        itemStack.setCount(itemStack.getCount() - 1);
        heatSource.burnTime += burnTime;
        heatSource.burning = true;
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new HeatSourceTile();
    }
}
