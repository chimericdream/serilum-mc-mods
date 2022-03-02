/*
 * This is the latest source code of Mooshroom Tweaks.
 * Minecraft version: 1.19.x, mod version: 1.6.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Mooshroom Tweaks ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.mooshroomtweaks.events;

import java.util.Set;

import com.natamus.collective_fabric.functions.EntityFunctions;
import com.natamus.mooshroomtweaks.config.ConfigHandler;
import com.natamus.mooshroomtweaks.util.Reference;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.level.Level;

public class MooshroomEvent {
	public static void onEntityJoin(Level world, Entity entity) {
		if (world.isClientSide()) {
			return;
		}
		
		if (entity instanceof MushroomCow == false) {
			return;
		}
		
		Set<String> tags = entity.getTags();
		if (tags.contains(Reference.MOD_ID + ".checked")) {
			return;
		}
		entity.addTag(Reference.MOD_ID + ".checked");
		
		double num = Math.random();
		if (num < ConfigHandler.becomeBrownChance.getValue()) {
			EntityFunctions.chargeEntity(entity);
		}	
	}
}
