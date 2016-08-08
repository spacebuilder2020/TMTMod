package spacebuilder2020.military_science;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by spacebuilder2020 on 8/6/2016.
 */
public class UraniumFuelRod extends Item
{
    public UraniumFuelRod()
    {
        setMaxDamage(3000);
        setUnlocalizedName("uranium_fuel_rod");
        setRegistryName("uranium_fuel_rod");
        GameRegistry.register(this);
        setCreativeTab(Main.instance.sb2020tab);

        GameRegistry.addRecipe(new ItemStack(this),"gsg","grg","gsg",'g', Blocks.GLASS_PANE, 's', Blocks.STONE_SLAB,'r',Blocks.REDSTONE_BLOCK);
    }
}
