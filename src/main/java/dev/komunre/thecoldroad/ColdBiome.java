package dev.komunre.thecoldroad;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class ColdBiome extends Biome {

    public ColdBiome() {
        super(new BiomeProperties("cold").setBaseHeight(63).setSnowEnabled().setTemperature(-15f));

        topBlock = Blocks.SNOW.getDefaultState();
        fillerBlock = Blocks.DIRT.getDefaultState();
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return new ColdTree();
    }

    @Override
    public boolean getEnableSnow() {
        return true;
    }


}
