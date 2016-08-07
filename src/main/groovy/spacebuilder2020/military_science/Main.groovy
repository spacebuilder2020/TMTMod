package spacebuilder2020.military_science;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Items
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "military_science", name = "Military Science")
public class Main
{

    @SidedProxy (clientSide = "spacebuilder2020.military_science,ClientProxy",serverSide = "spacebuilder2020.military_science.ServerProxy")
    public static CommonProxy cp;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        cp.preInit(event)

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @Mod.Instance
    public static Main instance = new Main()
    public CreativeTabs sb2020tab = null
    public Block fissionReactor = null
    public Block turbine = null
    public Item uraniumFuelRod = null
    public boolean clientSide = false
}
