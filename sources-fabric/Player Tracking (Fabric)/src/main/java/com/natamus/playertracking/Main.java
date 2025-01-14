/*
 * This is the latest source code of Player Tracking.
 * Minecraft version: 1.19.2, mod version: 2.2.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.playertracking;

import com.natamus.collective_fabric.check.RegisterMod;
import com.natamus.playertracking.cmds.CommandTrack;
import com.natamus.playertracking.util.Reference;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Main implements ModInitializer {
	public static Main instance;
	
	@Override
	public void onInitialize() { 
		instance = this;
		
		registerEvents();
		
		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}
	
	private void registerEvents() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			CommandTrack.register(dispatcher);
		});
	}
}
