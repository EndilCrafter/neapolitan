package com.teamabnormals.neapolitan.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.neapolitan.core.Neapolitan;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class NeapolitanEntityTypeTags {
	public static final TagKey<EntityType<?>> UNAFFECTED_BY_SLIPPING = entityTypeTag("unaffected_by_slipping");
	public static final TagKey<EntityType<?>> UNAFFECTED_BY_HARMONY = entityTypeTag("unaffected_by_harmony");
	public static final TagKey<EntityType<?>> SCARES_CHIMPANZEES = entityTypeTag("scares_chimpanzees");
	public static final TagKey<EntityType<?>> CHIMPANZEE_DART_TARGETS = entityTypeTag("chimpanzee_dart_targets");
	public static final TagKey<EntityType<?>> EXPLOSION_HEALS_IN_STRAWBERRY = entityTypeTag("explosion_heals_in_strawberry");
	public static final TagKey<EntityType<?>> MUDDY_PROJECTILES = TagUtil.entityTypeTag("environmental", "muddy_projectiles");

	private static TagKey<EntityType<?>> entityTypeTag(String name) {
		return TagUtil.entityTypeTag(Neapolitan.MOD_ID, name);
	}
}
