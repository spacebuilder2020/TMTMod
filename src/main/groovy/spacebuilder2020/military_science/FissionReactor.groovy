package spacebuilder2020.military_science

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.item.ItemBlock
import net.minecraft.util.math.BlockPos
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

    public static class FissionTile extends BaseReactor.ReactorTile
    {
        float heat = 0;
        @Override
        void update() {

            if (!worldObj.isRemote) {
                heat += 8
                BlockPos bp = pos.east()
                Block bs = worldObj.getBlockState(bp).block;
                println bs
                if (bs == Blocks.WATER || bs == Blocks.FLOWING_WATER) {
                    worldObj.setBlockState(bp, Blocks.AIR.getDefaultState())
                    heat -= 16
                    heat = heat > 0 ? heat : 0
                }
                if (heat > 300) {
                    worldObj.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState())
                    this.invalidate()
                }

                println heat
            }
            super.update()
        }
    }

}
