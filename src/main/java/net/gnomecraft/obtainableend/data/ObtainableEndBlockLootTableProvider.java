package net.gnomecraft.obtainableend.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.StatePredicate;

import java.util.List;

public class ObtainableEndBlockLootTableProvider extends FabricBlockLootTableProvider {
  protected ObtainableEndBlockLootTableProvider(FabricDataGenerator dataGenerator) {
    super(dataGenerator);
  }

  @Override
  protected void generateBlockLootTables() {
    // Always drop one End Stone (the gating factor to crafting End Portal Frame
    // pieces).
    // Drop an Ender Eye if there was one in the End Portal Frame piece.
    addDrop(Blocks.END_PORTAL_FRAME, LootTable.builder()
        .pools(List.of(
            LootPool.builder()
                .with(ItemEntry.builder(Items.END_STONE))
                .build(),
            LootPool.builder()
                .with(ItemEntry.builder(Items.ENDER_EYE)
                    .conditionally(BlockStatePropertyLootCondition
                        .builder(Blocks.END_PORTAL_FRAME)
                        .properties(StatePredicate.Builder
                            .create()
                            .exactMatch(EndPortalFrameBlock.EYE,
                                true))))
                .build())));
  }
}
