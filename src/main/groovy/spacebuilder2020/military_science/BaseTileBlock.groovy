package spacebuilder2020.military_science

import net.minecraft.block.ITileEntityProvider
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ITickable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * Created by spacebuilder2020 on 8/4/2016.
 */
class BaseTileBlock extends BaseBlock implements ITileEntityProvider {
    Class<TileEntity> tileClass = null
    @Override
    TileEntity createNewTileEntity(World worldIn, int meta) {
        return tileClass?.newInstance()
    }

    static HashMap<BlockPos,Integer> heatMap = new HashMap<>()
    static HashSet<BlockPos> airMap = new HashMap<>()

    public static class Tile extends TileEntity implements ITickable
    {
        @Override
        void update() {

        }
    }
}
