package spacebuilder2020.military_science

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

/**
 * Created by spacebuilder2020 on 8/7/2016.
 */
class ClientProxy extends CommonProxy {
    @Override
    void preInit(FMLPreInitializationEvent event) {
        Main.instance.clientSide = true
        super.preInit(event)


        ModelLoader.setCustomModelResourceLocation(Main.instance.uraniumFuelRod, 0, new ModelResourceLocation("military_science:uranium_fuel_rod", "inventory"));
        ModelLoader.setCustomModelResourceLocation(((BaseTileBlock)Main.instance.fissionReactor).i, 0, new ModelResourceLocation("military_science:fission_reactor", "inventory"));
        ModelLoader.setCustomModelResourceLocation(((BaseTileBlock)Main.instance.turbine).i, 0, new ModelResourceLocation("military_science:turbine", "inventory"));
    }
}
