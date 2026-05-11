package net.gnomecraft.obtainableend.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@NullMarked
public class ObtainableEndBlockLootTableProvider extends FabricBlockLootTableProvider {
    protected ObtainableEndBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture);
    }

    @Override
    public void generate() {
        // Always drop one End Stone (the gating factor to crafting End Portal Frame pieces).
        // Drop an Ender Eye if there was one in the End Portal Frame piece.
        add(Blocks.END_PORTAL_FRAME, LootTable.lootTable()
                .pools(List.of(
                        LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.END_STONE))
                                .build(),
                        LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.ENDER_EYE)
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.END_PORTAL_FRAME)
                                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                                        .hasProperty(EndPortalFrameBlock.HAS_EYE, true)
                                                )
                                        )
                                )
                                .build()
                        )
                )
        );
    }
}
