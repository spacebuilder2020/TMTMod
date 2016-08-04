package spacebuilder2020.TMT_MOD

import net.minecraft.block.ITileEntityProvider
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ITickable
import net.minecraft.world.World

/**
 * Created by spacebuilder2020 on 8/4/2016.
 */
class BaseReactor extends BaseBlock implements ITileEntityProvider {
    @Override
    TileEntity createNewTileEntity(World worldIn, int meta) {
        return null
    }



    class ReactorTile extends TileEntity implements ITickable
    {
        @Override
        void update() {

        }
    }
}
