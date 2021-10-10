package dev.komunre.thecoldroad;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.Random;

public class ColdWorld extends WorldType {
    public ColdWorld() {
        super("Cold World");
    }

    @Override
    public BiomeProvider getBiomeProvider(World world) {
        return new ColdBiomes();
    }

    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
        TheColdRoad.logger.info("Generating world. Options: " + generatorOptions);
        return new ColdChunker(world, new Random().nextLong()); // replace with actual seeed?
    }

    @Override
    public boolean isCustomizable() {
        return false;
    }


}
