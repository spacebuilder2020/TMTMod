package spacebuilder2020.military_science;

import net.minecraft.item.Item;
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
    }
}
