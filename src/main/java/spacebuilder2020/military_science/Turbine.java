package spacebuilder2020.military_science;

import net.darkhax.tesla.lib.TeslaUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by spacebuilder2020 on 8/4/2016.
 */
public class Turbine extends BaseTileBlock
{
    public Turbine()
    {
        setTileClass(TurbineTile.class);
        setUnlocalizedName("turbine");
        setRegistryName("turbine");

        GameRegistry.register(this);
        GameRegistry.register(i = new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(TurbineTile.class, "turbine");
        setCreativeTab(Main.instance.sb2020tab);
        GameRegistry.addRecipe(new ItemStack(i),"crc","rcr","crc",'c', Blocks.COBBLESTONE, 'r', Items.REDSTONE);
    }

    @Override
    public boolean isOpaqueCube(IBlockState iBlockState)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState iBlockState)
    {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState iBlockState)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState p_getBoundingBox_1_, IBlockAccess p_getBoundingBox_2_, BlockPos p_getBoundingBox_3_)
    {
        return new AxisAlignedBB(0, 0, 0, 1, 0.25, 1);
    }

    public static class TurbineTile extends Tile
    {
        @Override
        public void setPos(BlockPos p_setPos_1_)
        {
            super.setPos(p_setPos_1_);
            down = getPos().down();
            up = getPos().up();
        }

        @Override
        public void update()
        {
            if (!worldObj.isRemote)
            {

                if (BaseTileBlock.getAirMap().contains(down))
                {
                    BaseTileBlock.getAirMap().remove(down);
                    TileEntity teup = worldObj.getTileEntity(up);
                    if (teup != null)
                        TeslaUtils.givePower(teup, EnumFacing.DOWN, 100, false);
                }

            }

            super.update();
        }

        public BlockPos getDown()
        {
            return down;
        }

        public void setDown(BlockPos down)
        {
            this.down = down;
        }

        public BlockPos getUp()
        {
            return up;
        }

        public void setUp(BlockPos up)
        {
            this.up = up;
        }

        private BlockPos down;
        private BlockPos up;
    }
}
