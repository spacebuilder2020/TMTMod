package spacebuilder2020.military_science

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

/**
 * Created by spacebuilder2020 on 8/7/2016.
 */
class CommonProxy {

    public void preInit(FMLPreInitializationEvent event)
    {
        Main.instance.sb2020tab = new CreativeTabs("Spacebuilder2020"){

            @Override
            Item getTabIconItem() {
                return Items.CAULDRON
            }
        }
        Main.instance.fissionReactor = new spacebuilder2020.military_science.FissionReactor()
        Main.instance.turbine = new spacebuilder2020.military_science.Turbine()
        Main.instance.uraniumFuelRod = new spacebuilder2020.military_science.UraniumFuelRod()
    }
}
