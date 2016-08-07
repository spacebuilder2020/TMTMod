package spacebuilder2020.military_science;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

/**
 * Created by spacebuilder2020 on 8/4/2016.
 */
public class BaseFluid extends BlockFluidFinite
{
    public BaseFluid(Fluid fluid)
    {
        super(fluid, Material.WATER);
    }
}
