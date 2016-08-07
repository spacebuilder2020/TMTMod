package spacebuilder2020.military_science;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by spacebuilder2020 on 8/4/2016.
 */
public class BaseTileBlock extends BaseBlock implements ITileEntityProvider
{
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        try
        {
            return tileClass.newInstance();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Class<? extends TileEntity> getTileClass()
    {
        return tileClass;
    }

    public void setTileClass(Class<? extends TileEntity> tileClass)
    {
        this.tileClass = tileClass;
    }

    public static HashMap<BlockPos, Integer> getHeatMap()
    {
        return heatMap;
    }

    public static void setHeatMap(HashMap<BlockPos, Integer> heatMap)
    {
        BaseTileBlock.heatMap = heatMap;
    }

    public static HashSet<BlockPos> getAirMap()
    {
        return airMap;
    }

    public static void setAirMap(HashSet<BlockPos> airMap)
    {
        BaseTileBlock.airMap = airMap;
    }

    private Class<? extends TileEntity> tileClass = null;
    public Item i;
    private static HashMap<BlockPos, Integer> heatMap = new HashMap<BlockPos, Integer>();
    private static HashSet<BlockPos> airMap = new HashSet<BlockPos>();

    public static class Tile extends TileEntity implements ITickable
    {
        @Override
        public void update()
        {

        }

    }
}
