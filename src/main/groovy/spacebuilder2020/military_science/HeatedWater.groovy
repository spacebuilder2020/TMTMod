package spacebuilder2020.military_science

import net.minecraft.block.properties.IProperty
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.item.ItemBlock
import net.minecraft.util.IStringSerializable
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * Created by spacebuilder2020 on 8/4/2016.
 */
class HeatedWater extends BaseFluid{
    public HeatedWater()
    {
        super(FluidRegistry.getFluid("water"))
        setUnlocalizedName("heated_water");
        setRegistryName("heated_water");
        setCreativeTab(Main.instance.sb2020tab);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
    }
}
