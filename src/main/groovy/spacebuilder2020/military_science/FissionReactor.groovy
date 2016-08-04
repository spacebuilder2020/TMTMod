package spacebuilder2020.military_science

import net.minecraft.item.ItemBlock
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * Created by spacebuilder2020 on 8/4/2016.
 */
class FissionReactor  extends BaseReactor {

    public FissionReactor()
    {
        reactorTile = FissionTile
        setUnlocalizedName("fission_reactor");
        setRegistryName("fission_reactor");
        setCreativeTab(Main.instance.sb2020tab);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(FissionTile.class, "fission_reactor");
    }

    public static class FissionTile extends BaseReactor.ReactorTile
    {
        @Override
        void update() {


            Object.update()
        }
    }

}
