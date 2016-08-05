package spacebuilder2020.military_science

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.registry.GameRegistry

import javax.annotation.Nullable

/**
 * Created by spacebuilder2020 on 8/4/2016.
 */
class FissionReactor extends BaseTileBlock {

    public FissionReactor()
    {
        tileClass = FissionTile
        Item i;
        setUnlocalizedName("fission_reactor");
        setRegistryName("fission_reactor");
        setCreativeTab(Main.instance.sb2020tab);
        GameRegistry.register(this);
        GameRegistry.register(i = new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(FissionTile.class, "fission_reactor");
        Blocks.BIRCH_FENCE
        ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation("military_science:fission_reactor", "inventory"));
    }

    @Override
    public boolean isOpaqueCube(IBlockState iBlockState) {
        return false;
    }
    @Override
    public boolean isFullCube(IBlockState iBlockState) {
        return false;
    }
    @Override
    public EnumBlockRenderType getRenderType(IBlockState iBlockState) {
        return EnumBlockRenderType.MODEL;
    }
    @Override
    boolean onBlockActivated(World p_onBlockActivated_1_, BlockPos p_onBlockActivated_2_, IBlockState p_onBlockActivated_3_, EntityPlayer p_onBlockActivated_4_, EnumHand p_onBlockActivated_5_,
                             @Nullable ItemStack p_onBlockActivated_6_, EnumFacing p_onBlockActivated_7_, float p_onBlockActivated_8_, float p_onBlockActivated_9_, float p_onBlockActivated_10_) {
        if (!p_onBlockActivated_1_.isRemote && p_onBlockActivated_5_ == EnumHand.MAIN_HAND) {
            TileEntity te;
            if ((te = p_onBlockActivated_1_.getTileEntity(p_onBlockActivated_2_)) instanceof FissionTile) {
                if (!((te as FissionTile).running))
                    (te as FissionTile).running = true;
                else
                    println((te as FissionTile).heat)
            }
        }
        return super.onBlockActivated(p_onBlockActivated_1_, p_onBlockActivated_2_, p_onBlockActivated_3_, p_onBlockActivated_4_, p_onBlockActivated_5_, p_onBlockActivated_6_, p_onBlockActivated_7_, p_onBlockActivated_8_, p_onBlockActivated_9_, p_onBlockActivated_10_)
    }

    public static class FissionTile extends BaseTileBlock.Tile
    {
        BlockPos[] adj = new BlockPos[6];
        @Override
        void setPos(BlockPos p_setPos_1_) {
            super.setPos(p_setPos_1_)
            adj[0] = pos.east()
            adj[1] = pos.west()
            adj[2] = pos.north()
            adj[3] = pos.south()
            adj[4] = pos.up()
            adj[5] = pos.down()

        }
        int heat = 0;
        boolean running = false;
        int tick = 0
        @Override
        void update() {

            if (!worldObj.isRemote) {
                if (running) {
                    heat += 3
                }
                ArrayList<BlockPos> waterBlocks = new ArrayList<>()
                for (bp in adj) {
                    Block bs = worldObj.getBlockState(bp).block;
                    if ((bs == Blocks.WATER || bs == Blocks.FLOWING_WATER)) {
                        waterBlocks.add(bp)
                        if (!heatMap.containsKey(bp)) {
                            heatMap.put(bp, 0)
                            airMap.remove(bp)
                        }
                    }
                }
                for (bp in waterBlocks)
                {
                    if (heat > 0) {
                        heatMap[bp]++
                        heat --
                        if (heatMap[bp] > 15) {
                            worldObj.setBlockState(bp, Blocks.AIR.getDefaultState())
                            airMap.add(bp)
                            heatMap.remove(bp)
                        }
                    }

                }
                if (heat > 300) {
                    worldObj.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState())
                    this.invalidate()
                }
            }
            super.update()
        }
    }

}
