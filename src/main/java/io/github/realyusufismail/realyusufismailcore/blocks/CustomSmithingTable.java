package io.github.realyusufismail.realyusufismailcore.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SmithingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class CustomSmithingTable extends SmithingTableBlock {
    public static Component CONTAINER_TITLE = Component.nullToEmpty("container.upgrade");
    private static boolean isLegacy = false;

    public CustomSmithingTable(Properties pProperties) {
        super(pProperties);
    }

    public AbstractContainerMenu getNewSmithingMenu(int id, Inventory inventory, ContainerLevelAccess access) {
        return new SmithingMenu(id, inventory, access);
    }

    public AbstractContainerMenu getLegacySmithingMenu(int id, Inventory inventory, ContainerLevelAccess access) {
        return new OldSmithingMenu(id, inventory, access);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((id, inventory, player) -> {
            ContainerLevelAccess access = ContainerLevelAccess.create(level, pos);
            if (isLegacy) {
                return getLegacySmithingMenu(id, inventory, access);
            } else {
                return getNewSmithingMenu(id, inventory, access);
            }
        }, CONTAINER_TITLE);
    }

    @Override
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
            pPlayer.awardStat(Stats.INTERACT_WITH_SMITHING_TABLE);
            return InteractionResult.CONSUME;
        }
    }

    public void setLegacy(boolean isLegacy) {
        CustomSmithingTable.isLegacy = isLegacy;
    }

    public static boolean isLegacy() {
        return isLegacy;
    }
}
