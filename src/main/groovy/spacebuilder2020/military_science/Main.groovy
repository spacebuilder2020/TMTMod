package spacebuilder2020.military_science;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "spacebuilder2020.military_science", name = "Military Science")
public class Main
{
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        sb2020tab = new CreativeTabs("Spacebuilder2020"){

            @Override
            Item getTabIconItem() {
                return Items.CAULDRON
            }
        }
        fissionReactor = new FissionReactor();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @Mod.Instance
    public static Main instance = new Main();
    public CreativeTabs sb2020tab = null;
    public Block fissionReactor = null;
}
