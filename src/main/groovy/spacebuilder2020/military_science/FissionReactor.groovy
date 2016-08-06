package spacebuilder2020.military_science

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.inventory.IInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
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
                p_onBlockActivated_4_.addChatComponentMessage(new TextComponentString((te as FissionTile).heat.toString()))
            }
        }
        return super.onBlockActivated(p_onBlockActivated_1_, p_onBlockActivated_2_, p_onBlockActivated_3_, p_onBlockActivated_4_, p_onBlockActivated_5_, p_onBlockActivated_6_, p_onBlockActivated_7_, p_onBlockActivated_8_, p_onBlockActivated_9_, p_onBlockActivated_10_)
    }

    public static class FissionTile extends BaseTileBlock.Tile implements IInventory
    {
        ItemStack[] fuel = new ItemStack[1]
        @Override
        void readFromNBT(NBTTagCompound p_readFromNBT_1_) {
            super.readFromNBT(p_readFromNBT_1_)
            fuel[0] = ItemStack.loadItemStackFromNBT(p_readFromNBT_1_.getCompoundTag("fuel"))
            heat = p_readFromNBT_1_.getInteger("heat")
        }

        @Override
        NBTTagCompound writeToNBT(NBTTagCompound p_writeToNBT_1_) {
            p_writeToNBT_1_ = super.writeToNBT(p_writeToNBT_1_)
            fuel[0].writeToNBT(p_writeToNBT_1_.getCompoundTag("fuel"))
            p_writeToNBT_1_.setInteger("heat",heat)
            return p_writeToNBT_1_
        }
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
                if (fuel[0] && fuel[0].item.equals(Main.instance.uraniumFuelRod) && fuel[0].itemDamage < (fuel[0].item.getMaxDamage() -2)) {
                    heat += 3
                    fuel[0].itemDamage++

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

        @Override
        int getSizeInventory() {
            return 1
        }

        @Override
        ItemStack getStackInSlot(int i) {
            if (i == 0)
                return fuel[i]
            else
                return null
        }

        @Override
        ItemStack decrStackSize(int i, int i1) {
            def fi = fuel[i]
            fuel[i] = null
            return fi;
        }

        @Override
        ItemStack removeStackFromSlot(int i) {
            if (i == 0)
            {
                def out = fuel[i]
                fuel[i] = null;
                return out
            }
            else
                return null
        }

        @Override
        void setInventorySlotContents(int i, @Nullable ItemStack itemStack) {
            if (i == 0) {
                fuel[i] = itemStack
                if( itemStack?.stackSize > getInventoryStackLimit()) {
                    itemStack.stackSize = getInventoryStackLimit()
                }
            }
        }

        @Override
        int getInventoryStackLimit() {
            return 1
        }

        @Override
        boolean isUseableByPlayer(EntityPlayer entityPlayer) {
            if (this.worldObj.getTileEntity(this.pos) != this) return false;
            final double X_CENTRE_OFFSET = 0.5;
            final double Y_CENTRE_OFFSET = 0.5;
            final double Z_CENTRE_OFFSET = 0.5;
            final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
            return entityPlayer.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
        }

        @Override
        void openInventory(EntityPlayer entityPlayer) {

        }

        @Override
        void closeInventory(EntityPlayer entityPlayer) {

        }

        @Override
        boolean isItemValidForSlot(int i, ItemStack itemStack) {
            if (i == 0)
                return true
            return false
        }

        @Override
        int getField(int i) {
            return 0
        }

        @Override
        void setField(int i, int i1) {

        }

        @Override
        int getFieldCount() {
            return 0
        }

        @Override
        void clear() {
            fuel[0] = null
        }

        @Override
        String getName() {
            return "container.fusion_reactor.name"
        }

        @Override
        boolean hasCustomName() {
            return false
        }
    }

}
