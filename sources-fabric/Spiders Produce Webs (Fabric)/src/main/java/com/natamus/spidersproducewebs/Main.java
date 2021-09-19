/*
 * This is the latest source code of Spiders Produce Webs.
 * Minecraft version: 1.17.x, mod version: 1.5.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Spiders Produce Webs ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.spidersproducewebs;

import com.natamus.collective_fabric.check.RegisterMod;
import com.natamus.collective_fabric.fabric.callbacks.CollectivePlayerEvents;
import com.natamus.spidersproducewebs.config.ConfigHandler;
import com.natamus.spidersproducewebs.events.SpiderEvent;
import com.natamus.spidersproducewebs.util.Reference;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class Main implements ModInitializer {
	@Override
	public void onInitialize() { 
		ConfigHandler.setup();

		registerEvents();
		
		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}
	
	private void registerEvents() {
		CollectivePlayerEvents.PLAYER_TICK.register((ServerLevel world, ServerPlayer player) -> {
			SpiderEvent.onPlayerTick(world, player);
		});
	}
}
