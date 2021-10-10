package dev.komunre.thecoldroad;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColdBiomes extends BiomeProvider {
    public Biome coldBiome;

    public ColdBiomes() {
        coldBiome = new ColdBiome();
    }
    @Override
    public Biome getBiome(BlockPos pos) {
        return coldBiome;
    }

    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height) {
        return new Biome[] {coldBiome};
    }

    @Override
    public Biome[] getBiomes(@Nullable Biome[] oldBiomeList, int x, int z, int width, int depth) {
        return new Biome[] {coldBiome};
    }

    @Override
    public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
        return new Biome[] {coldBiome};
    }

    @Nullable
    @Override
    public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
        return new BlockPos(x, 63, z);
    }

    @Override
    public List<Biome> getBiomesToSpawnIn() {
        ArrayList<Biome> biomes = new ArrayList<Biome>();
        biomes.add(coldBiome);
        return biomes;
    }

    @Override
    public Biome getBiome(BlockPos pos, Biome defaultBiome) {
        return coldBiome;
    }


}
