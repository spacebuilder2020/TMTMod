package spacebuilder2020.military_science

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fluids.BlockFluidBase
import net.minecraftforge.fluids.BlockFluidFinite
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * Created by spacebuilder2020 on 8/4/2016.
 */
class FissionReactor  extends BaseReactor {

    public FissionReactor()
    {
        reactorTile = FissionTile
        setUnlocalizedName("fission_reactor");
        setRegistryName("fission_reactor");
        setCreativeTab(Main.instance.sb2020tab);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(FissionTile.class, "fission_reactor");
    }

    @Override
    void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        super.onBlockClicked(worldIn, pos, playerIn)
    }

    public static class FissionTile extends BaseReactor.ReactorTile
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
        @Override
        void update() {

            if (!worldObj.isRemote) {
                ArrayList<BlockPos> waterBlocks = new ArrayList<>()
                for (bp in adj) {
                    Block bs = worldObj.getBlockState(bp).block;
                    if ((bs == Blocks.WATER || bs == Blocks.FLOWING_WATER)) {
                        waterBlocks.add(bp)
                    }
                }
                for (bp in waterBlocks)
                {
                    if (heat > 0) {
                        int lvl = worldObj.getBlockState(bp).getValue(BlockFluidBase.LEVEL)
                        lvl++;
                        heat--;
                        if (lvl < 16)
                            worldObj.setBlockState(bp, worldObj.getBlockState(bp).withProperty(BlockFluidBase.LEVEL, lvl))
                        else
                            worldObj.setBlockState(bp, Blocks.AIR.getDefaultState())
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
