package spacebuilder2020.military_science

import net.darkhax.tesla.capability.TeslaCapabilities
import net.darkhax.tesla.lib.TeslaUtils
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

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

        GameRegistry.register(this);
        GameRegistry.register(i = new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(Turbine.TurbineTile.class, "turbine");
        setCreativeTab(Main.instance.sb2020tab)


    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean isOpaqueCube(IBlockState iBlockState) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean isFullCube(IBlockState iBlockState) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public EnumBlockRenderType getRenderType(IBlockState iBlockState) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    AxisAlignedBB getBoundingBox(IBlockState p_getBoundingBox_1_, IBlockAccess p_getBoundingBox_2_, BlockPos p_getBoundingBox_3_) {
        return new AxisAlignedBB(0,0,0,1,0.25,1)
    }

    public static class TurbineTile extends BaseTileBlock.Tile {
        BlockPos down;
        BlockPos up;
        @Override
        void setPos(BlockPos p_setPos_1_) {
            super.setPos(p_setPos_1_)
            down = pos.down()
            up = pos.up()
        }
        @Override
        void update() {
            if (!worldObj.isRemote) {

                if (airMap.contains(down)) {
                    airMap.remove(down)
                    net.minecraft.tileentity.TileEntity teup = worldObj.getTileEntity(up)
                    if (teup)
                        TeslaUtils.givePower(teup, EnumFacing.DOWN, 100, false)
                }

            }
            super.update()
        }
    }
}
