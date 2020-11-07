package quek.undergarden.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import quek.undergarden.UGMod;
import quek.undergarden.world.gen.structure.*;

import java.util.HashMap;
import java.util.Map;

public class UGStructures {

    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, UGMod.MODID);

    public static final RegistryObject<Structure<NoFeatureConfig>> forgotten_ruin = STRUCTURES.register("forgotten_ruin", () -> new ForgottenRuinsStructure(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Structure<NoFeatureConfig>> forgotten_castle = STRUCTURES.register("forgotten_castle", () -> new ForgottenCastleStructure(NoFeatureConfig.field_236558_a_));

    public static void registerStructures() {
        setupStructure(forgotten_ruin.get(), new StructureSeparationSettings(4, 2, 276320041), true);
        setupStructure(forgotten_castle.get(), new StructureSeparationSettings(16, 8, 276320042), true);
    }

    public static void registerConfiguredStructures() {
        Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(UGMod.MODID, "forgotten_ruin"), forgotten_ruin.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
        Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(UGMod.MODID, "forgotten_castle"), forgotten_castle.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    }

    public static void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator && serverWorld.getDimensionKey().equals(World.OVERWORLD)) {
                return;
            }
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());

            tempMap.put(UGStructures.forgotten_ruin.get(), DimensionStructuresSettings.field_236191_b_.get(UGStructures.forgotten_ruin.get()));
            tempMap.put(UGStructures.forgotten_castle.get(), DimensionStructuresSettings.field_236191_b_.get(UGStructures.forgotten_castle.get()));

            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
        }
    }

    private static <F extends Structure<?>> void setupStructure(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        if(transformSurroundingLand) {
            Structure.field_236384_t_ =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.field_236384_t_)
                            .add(structure)
                            .build();
        }

        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(structure, structureSeparationSettings)
                        .build();
    }
}
