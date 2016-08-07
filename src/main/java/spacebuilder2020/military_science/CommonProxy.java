package spacebuilder2020.military_science;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by spacebuilder2020 on 8/7/2016.
 */
public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        Main.instance.sb2020tab = new CreativeTabs("Spacebuilder2020")
        {
            @Override
            public Item getTabIconItem()
            {
                return Items.CAULDRON;
            }

        };
        Main.instance.fissionReactor = new FissionReactor();
        Main.instance.turbine = new Turbine();
        Main.instance.uraniumFuelRod = new UraniumFuelRod();
    }

}
