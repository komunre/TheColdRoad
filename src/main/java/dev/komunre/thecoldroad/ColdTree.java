package dev.komunre.thecoldroad;

import jdk.nashorn.internal.ir.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class ColdTree extends WorldGenAbstractTree {
    public ColdTree() {
        super(true);
    }

    @Override
    public void generateSaplings(World worldIn, Random random, BlockPos pos) {
        return;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < 7; i++) {
            worldIn.setBlockState(position.add(new Vec3i(0, i, 0)), Blocks.LOG.getDefaultState());
        }
        return true;
    }
}
