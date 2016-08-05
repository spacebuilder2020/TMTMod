package spacebuilder2020.military_science

import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * Created by spacebuilder2020 on 8/4/2016.
 */
class Turbine extends BaseTileBlock {
    public Turbine()
    {
        tileClass = TurbineTile
        Item i;
        setUnlocalizedName("turbine");
        setRegistryName("turbine");
        setCreativeTab(Main.instance.sb2020tab);
        GameRegistry.register(this);
        GameRegistry.register(i = new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(Turbine.TurbineTile.class, "turbine");

        ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation("military_science:turbine", "inventory"));
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
    AxisAlignedBB getBoundingBox(IBlockState p_getBoundingBox_1_, IBlockAccess p_getBoundingBox_2_, BlockPos p_getBoundingBox_3_) {
        return new AxisAlignedBB(0,0,0,1,0.25,1)
    }

    public static class TurbineTile extends BaseTileBlock.Tile {
        @Override
        void update() {
            if (!worldObj.isRemote) {
                BlockPos down = pos.down()
                if (airMap.contains(down)) {
                    airMap.remove(down)
                    println "Caught Steam!"
                }
            }
            super.update()
        }
    }
}
