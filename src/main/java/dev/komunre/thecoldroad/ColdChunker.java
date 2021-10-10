package dev.komunre.thecoldroad;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorDebug;
import net.minecraft.world.gen.ChunkGeneratorFlat;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.Sys;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColdChunker implements IChunkGenerator {
    private Random random;
    private World world;
    private long seed;
    private ArrayList<Item> possibleLoot = new ArrayList<Item>();
    public ColdChunker(World world, long seed) {
        random = new Random();
        random.setSeed(seed);
        this.world = world;
        this.seed = seed;
        possibleLoot.add(Items.APPLE);
        possibleLoot.add(Items.BREAD);
        possibleLoot.add(Items.IRON_AXE);
    }

    private void buildSnow(int x, int z, ChunkPrimer primer) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int h = 1; h < 54; h++) {
                    primer.setBlockState(i, h, j, Blocks.STONE.getDefaultState());
                }
                primer.setBlockState(i, 54, j, Blocks.DIRT.getDefaultState());
                for (int h = 55; h < 63; h++) {
                    //TheColdRoad.logger.info(i + ":" + h + ":" + j);
                    primer.setBlockState(i, h, j, Blocks.SNOW.getDefaultState());
                }
                primer.setBlockState(i, 0, j, Blocks.BEDROCK.getDefaultState());
            }
        }
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        ChunkPrimer chunkprimer = new ChunkPrimer();
        buildSnow(x, z, chunkprimer);

        Chunk chunk = new Chunk(world, chunkprimer, x, z);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {
        ForgeEventFactory.onChunkPopulate(true, this, world, random, x, z, false);
        random.setSeed(seed + System.nanoTime());
        if (random.nextInt(100) > 90) return;
        int x1 = (x * 16 + random.nextInt(16) + 8);
        int y = 63;
        int z1 = (z * 16 + random.nextInt(16) + 8);
        new ColdTree().generate(world, random, new BlockPos(x1, y, z1));

        // Generate ore
        for (int i = 0; i < random.nextInt(4); i++) {
            int x2 = x * 16 + random.nextInt(16);
            int y1 = random.nextInt(54) + 1;
            int z2 = z * 16 + random.nextInt(16);
            world.setBlockState(new BlockPos(x2, y1, z2), Blocks.IRON_BLOCK.getDefaultState());
        }

        ForgeEventFactory.onChunkPopulate(false, this, world, random, x, z, false);
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        random.setSeed(seed + x * 1028L + z * 856L);
        if (random.nextInt(100) > 20) return false;
        chunkIn.setBlockState(new BlockPos(x, 63, z), Blocks.CHEST.getDefaultState());
        TileEntityChest chest = (TileEntityChest) chunkIn.getWorld().getTileEntity(new BlockPos(x * 16, 63, z * 16));
        if (chest == null) return false;
        IItemHandler cap = chest.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        if (cap != null) {
            for (int i = 0; i < random.nextInt(3); i++) {
                cap.insertItem(i, new ItemStack(possibleLoot.get(random.nextInt(possibleLoot.size())), random.nextInt(4) + 1), false);
            }
        }
        return true;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return null;
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {
        /*random.setSeed(seed + x * 153L + z * 912L);
        if (random.nextInt(100) > 30) {
            return;
        }*/

    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }


}
