/*
 * This is the latest source code of The Vanilla Experience.
 * Minecraft version: 1.17.1, mod version: 1.4.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.thevanillaexperience.mods.conduitspreventdrowned.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConduitsPreventDrownedConfigHandler {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(BUILDER);
	public static final ForgeConfigSpec spec = BUILDER.build();

	public static class General {
		public final ForgeConfigSpec.ConfigValue<Integer> preventDrownedInRange;

		public General(ForgeConfigSpec.Builder builder) {
			builder.push("General");
			preventDrownedInRange = builder
					.comment("The euclidian distance range around the drowned where a check for a player with the conduit effect is done. A value of 400 prevents the spawning of all drowned around.")
					.defineInRange("preventDrownedInRange", 400, 0, 400);
			
			builder.pop();
		}
	}
}