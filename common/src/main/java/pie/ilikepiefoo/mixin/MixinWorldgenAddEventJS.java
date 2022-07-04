package pie.ilikepiefoo.mixin;

import dev.latvian.kubejs.util.ListJS;
import dev.latvian.kubejs.util.UtilsJS;
import dev.latvian.kubejs.world.gen.AddOreProperties;
import dev.latvian.kubejs.world.gen.AnyRuleTest;
import dev.latvian.kubejs.world.gen.InvertRuleTest;
import dev.latvian.kubejs.world.gen.WorldgenAddEventJS;
import dev.latvian.kubejs.world.gen.WorldgenEntryList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.util.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Consumer;

@Mixin(value = WorldgenAddEventJS.class, remap = false)
public abstract class MixinWorldgenAddEventJS {
	@Shadow
	protected abstract boolean verifyBiomes(WorldgenEntryList biomes);

	@Shadow
	protected abstract void addFeature(GenerationStep.Decoration decoration,
									   ConfiguredFeature<?, ?> configuredFeature);

	/**
	 * @author ILIKEPIEFOO2
	 * @reason Simple Order of Operations issue causing square Oregen to break.
	 */
	@Overwrite
	public void addOre(Consumer<AddOreProperties> p) {
		AddOreProperties properties = new AddOreProperties();
		p.accept(properties);

		if (properties._block == Blocks.AIR.defaultBlockState()) {
			return;
		}

		if (!verifyBiomes(properties.biomes)) {
			return;
		}

		AnyRuleTest ruleTest = new AnyRuleTest();

		for (Object o : ListJS.orSelf(properties.spawnsIn.values)) {
			String s = String.valueOf(o);
			boolean invert = false;

			while (s.startsWith("!")) {
				s = s.substring(1);
				invert = !invert;
			}

			if (s.startsWith("#")) {
				RuleTest tagTest = new TagMatchTest(SerializationTags.getInstance().getBlocks().getTag(new ResourceLocation(s.substring(1))));
				ruleTest.list.add(invert
						? new InvertRuleTest(tagTest)
						: tagTest);
			} else {
				BlockState bs = UtilsJS.parseBlockState(s);
				RuleTest tagTest = s.indexOf('[') != -1
						? new BlockStateMatchTest(bs)
						: new BlockMatchTest(bs.getBlock());
				ruleTest.list.add(invert
						? new InvertRuleTest(tagTest)
						: tagTest);
			}
		}

		RuleTest ruleTest1 = ruleTest.list.isEmpty()
				? OreConfiguration.Predicates.NATURAL_STONE
				: ruleTest;

		ConfiguredFeature<OreConfiguration,?> oreConfig = (properties.noSurface
				? Feature.NO_SURFACE_ORE
				: Feature.ORE).configured(new OreConfiguration(properties.spawnsIn.blacklist
				? new InvertRuleTest(ruleTest1)
				: ruleTest1, properties._block,
				properties.clusterMaxSize));

		oreConfig = UtilsJS.cast(
				oreConfig.decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(properties.minHeight, 0, properties.maxHeight))));


		if (properties.squared) {
			oreConfig = UtilsJS.cast(oreConfig.squared());
		}


		oreConfig = UtilsJS.cast(oreConfig.count(UniformInt.of(properties.clusterMinCount, properties.clusterMaxCount - properties.clusterMinCount)));


		if (properties.chance > 0) {
			oreConfig = UtilsJS.cast(oreConfig.chance(properties.chance));
		}


		addFeature(properties._worldgenLayer, oreConfig);
	}
}
