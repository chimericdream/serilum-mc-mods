/*
 * This is the latest source code of Collective.
 * Minecraft version: 1.17.x, mod version: 1.61.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Collective ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.collective_fabric.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class CollectiveExplosionEvents {
	private CollectiveExplosionEvents() { }
	 
    public static final Event<CollectiveExplosionEvents.Explosion_Detonate> EXPLOSION_DETONATE = EventFactory.createArrayBacked(CollectiveExplosionEvents.Explosion_Detonate.class, callbacks -> (world, sourceEntity, explosion) -> {
        for (CollectiveExplosionEvents.Explosion_Detonate callback : callbacks) {
        	callback.onDetonate(world, sourceEntity, explosion);
        }
    });
    
	@FunctionalInterface
	public interface Explosion_Detonate {
		 void onDetonate(Level world, Entity sourceEntity, Explosion explosion);
	}
}