package spacebuilder2020.military_science;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import scala.Int;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Created by spacebuilder2020 on 8/4/2016.
 */
public class FissionReactor extends BaseTileBlock
{
    public FissionReactor()
    {
        setTileClass(FissionTile.class);
        setUnlocalizedName("fission_reactor");
        setRegistryName("fission_reactor");
        GameRegistry.register(this);
        GameRegistry.register(i = new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(FissionTile.class, "fission_reactor");
        setCreativeTab(Main.instance.sb2020tab);

        GameRegistry.addRecipe(new ItemStack(i),"oso","sds","oso",'o',Blocks.OBSIDIAN, 's', Blocks.STONE,'d',Blocks.DIAMOND_BLOCK);
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
    public boolean onBlockActivated(World p_onBlockActivated_1_, BlockPos p_onBlockActivated_2_, IBlockState p_onBlockActivated_3_, EntityPlayer p_onBlockActivated_4_, EnumHand p_onBlockActivated_5_, @Nullable ItemStack p_onBlockActivated_6_, EnumFacing p_onBlockActivated_7_,
            float p_onBlockActivated_8_, float p_onBlockActivated_9_, float p_onBlockActivated_10_)
    {
        if (!p_onBlockActivated_1_.isRemote && p_onBlockActivated_5_.equals(EnumHand.MAIN_HAND))
        {
            TileEntity te;
            if ((te = p_onBlockActivated_1_.getTileEntity(p_onBlockActivated_2_)) instanceof FissionTile)
            {
                p_onBlockActivated_4_.addChatComponentMessage(new TextComponentString(Integer.toString(((FissionTile) te).getHeat())));
            }

        }

        return super.onBlockActivated(p_onBlockActivated_1_, p_onBlockActivated_2_, p_onBlockActivated_3_, p_onBlockActivated_4_, p_onBlockActivated_5_,
                p_onBlockActivated_6_, p_onBlockActivated_7_, p_onBlockActivated_8_, p_onBlockActivated_9_, p_onBlockActivated_10_);
    }

    public static class FissionTile extends Tile implements IInventory
    {
        @Override
        public void readFromNBT(NBTTagCompound p_readFromNBT_1_)
        {
            super.readFromNBT(p_readFromNBT_1_);
            fuel[0] = ItemStack.loadItemStackFromNBT(p_readFromNBT_1_.getCompoundTag("fuel"));
            heat = p_readFromNBT_1_.getInteger("heat");
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound p_writeToNBT_1_)
        {
            p_writeToNBT_1_ = super.writeToNBT(p_writeToNBT_1_);
            if (fuel[0] != null)
                fuel[0].writeToNBT(p_writeToNBT_1_.getCompoundTag("fuel"));
            p_writeToNBT_1_.setInteger("heat", heat);
            return p_writeToNBT_1_;
        }

        @Override
        public void setPos(BlockPos p_setPos_1_)
        {
            super.setPos(p_setPos_1_);
            adj[0] = getPos().east();
            adj[1] = getPos().west();
            adj[2] = getPos().north();
            adj[3] = getPos().south();
            adj[4] = getPos().up();
            adj[5] = getPos().down();

        }

        @Override
        public void update()
        {

            if (!worldObj.isRemote)
            {
                if (fuel[0] != null && fuel[0].getItem().equals(Main.instance.uraniumFuelRod) && fuel[0].getItemDamage() < (fuel[0].getItem().getMaxDamage() - 2))
                {
                    heat += 3;
                    fuel[0].setItemDamage(fuel[0].getItemDamage()+1);

                }

                ArrayList<BlockPos> waterBlocks = new ArrayList<BlockPos>();
                for (BlockPos bp : adj)
                {
                    Block bs = worldObj.getBlockState(bp).getBlock();
                    if ((bs.equals(Blocks.WATER) || bs.equals(Blocks.FLOWING_WATER)))
                    {
                        waterBlocks.add(bp);
                        if (!BaseTileBlock.getHeatMap().containsKey(bp))
                        {
                            BaseTileBlock.getHeatMap().put(bp, 0);
                            BaseTileBlock.getAirMap().remove(bp);
                        }

                    }

                }

                for (BlockPos bp : waterBlocks)
                {
                    if (heat > 0)
                    {
                        BaseTileBlock.getHeatMap().put(bp,BaseTileBlock.getHeatMap().get(bp)+1);
                        heat = heat-1;
                        if (BaseTileBlock.getHeatMap().get(bp) > 15)
                        {
                            worldObj.setBlockState(bp, Blocks.AIR.getDefaultState());
                            BaseTileBlock.getAirMap().add(bp);
                            BaseTileBlock.getHeatMap().remove(bp);
                        }

                    }

                }

                if (heat > 300)
                {
                    worldObj.setBlockState(getPos(), Blocks.OBSIDIAN.getDefaultState());
                    this.invalidate();
                }

            }

            super.update();
        }

        @Override
        public int getSizeInventory()
        {
            return 1;
        }

        @Override
        public ItemStack getStackInSlot(int i)
        {
            if (i == 0)
                return ((ItemStack) (fuel[i]));
            else
                return null;
        }

        @Override
        public ItemStack decrStackSize(int i, int i1)
        {
            ItemStack fi = fuel[i];
            fuel[i] = null;
            return ((ItemStack) (fi));
        }

        @Override
        public ItemStack removeStackFromSlot(int i)
        {
            if (i == 0)
            {
                ItemStack out = fuel[i];
                fuel[i] = null;
                return ((ItemStack) (out));
            }
            else
                return null;
        }

        @Override
        public void setInventorySlotContents(int i, @Nullable ItemStack itemStack)
        {
            if (i == 0)
            {
                fuel[i] = itemStack;
                if ((itemStack == null ? null : itemStack.stackSize) > getInventoryStackLimit())
                {
                    itemStack.stackSize = getInventoryStackLimit();
                }

            }

        }

        @Override
        public int getInventoryStackLimit()
        {
            return 1;
        }

        @Override
        public boolean isUseableByPlayer(EntityPlayer entityPlayer)
        {
            if (!this.worldObj.getTileEntity(this.getPos()).equals(this))
                return false;
            final double X_CENTRE_OFFSET = 0.5;
            final double Y_CENTRE_OFFSET = 0.5;
            final double Z_CENTRE_OFFSET = 0.5;
            final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
            return entityPlayer.getDistanceSq(getPos().getX() + X_CENTRE_OFFSET, getPos().getY() + Y_CENTRE_OFFSET, getPos().getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
        }

        @Override
        public void openInventory(EntityPlayer entityPlayer)
        {

        }

        @Override
        public void closeInventory(EntityPlayer entityPlayer)
        {

        }

        @Override
        public boolean isItemValidForSlot(int i, ItemStack itemStack)
        {
            if (i == 0)
                return true;
            return false;
        }

        @Override
        public int getField(int i)
        {
            return 0;
        }

        @Override
        public void setField(int i, int i1)
        {

        }

        @Override
        public int getFieldCount()
        {
            return 0;
        }

        @Override
        public void clear()
        {
            fuel[0] = null;
        }

        @Override
        public String getName()
        {
            return "container.fusion_reactor.name";
        }

        @Override
        public boolean hasCustomName()
        {
            return false;
        }

        public ItemStack[] getFuel()
        {
            return fuel;
        }

        public void setFuel(ItemStack[] fuel)
        {
            this.fuel = fuel;
        }

        public BlockPos[] getAdj()
        {
            return adj;
        }

        public void setAdj(BlockPos[] adj)
        {
            this.adj = adj;
        }

        public int getHeat()
        {
            return heat;
        }

        public void setHeat(int heat)
        {
            this.heat = heat;
        }

        private ItemStack[] fuel = new ItemStack[1];
        private BlockPos[] adj = new BlockPos[6];
        private int heat = 0;
    }
}
