package spacebuilder2020.military_science

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTool
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * Created by spacebuilder2020 on 8/6/2016.
 */
class UraniumFuelRod extends Item{
    public UraniumFuelRod()
    {
        setMaxDamage(3000)
        setUnlocalizedName("uranium_fuel_rod");
        setRegistryName("uranium_fuel_rod");
        setCreativeTab(Main.instance.sb2020tab)
        GameRegistry.register(this);
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("military_science:uranium_fuel_rod", "inventory"));
    }
}
