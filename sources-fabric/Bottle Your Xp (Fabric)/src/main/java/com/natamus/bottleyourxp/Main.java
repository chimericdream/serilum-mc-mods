/*
 * This is the latest source code of Bottle Your Xp.
 * Minecraft version: 1.17.x, mod version: 1.6.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Bottle Your Xp ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.bottleyourxp;

import com.natamus.bottleyourxp.config.ConfigHandler;
import com.natamus.bottleyourxp.events.ClickEvent;
import com.natamus.bottleyourxp.util.Reference;
import com.natamus.collective_fabric.check.RegisterMod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

public class Main implements ModInitializer {
	@Override
	public void onInitialize() { 
		ConfigHandler.setup();

		registerEvents();
		
		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}
	
	private void registerEvents() {
		UseItemCallback.EVENT.register((player, world, hand) -> {
			return ClickEvent.onClickBottle(player, world, hand);
		});
	}
}