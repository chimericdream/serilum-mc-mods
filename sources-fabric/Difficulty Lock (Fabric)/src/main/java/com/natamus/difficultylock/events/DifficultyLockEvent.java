/*
 * This is the latest source code of Difficulty Lock.
 * Minecraft version: 1.19.2, mod version: 1.8.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.difficultylock.events;

import com.natamus.difficultylock.config.ConfigHandler;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.WorldData;

public class DifficultyLockEvent {
	public static void onWorldLoad(ServerLevel world) {
		WorldData serverconfiguration = world.getServer().getWorldData();
		
		LevelData worldinfo = world.getLevelData();
		boolean islocked = worldinfo.isDifficultyLocked();
		if (islocked && !ConfigHandler.shouldChangeDifficultyWhenAlreadyLocked.getValue()) {
			return;
		}
		
		Difficulty currentdifficulty = worldinfo.getDifficulty();
		if (ConfigHandler.forcePeaceful.getValue()) {
			if (!currentdifficulty.equals(Difficulty.PEACEFUL)) {
				serverconfiguration.setDifficulty(Difficulty.PEACEFUL);
			}
		}
		else if (ConfigHandler.forceEasy.getValue()) {
			if (!currentdifficulty.equals(Difficulty.EASY)) {
				serverconfiguration.setDifficulty(Difficulty.EASY);
			}			
		}
		else if (ConfigHandler.forceNormal.getValue()) {
			if (!currentdifficulty.equals(Difficulty.NORMAL)) {
				serverconfiguration.setDifficulty(Difficulty.NORMAL);
			}			
		}
		else if (ConfigHandler.forceHard.getValue()) {
			if (!currentdifficulty.equals(Difficulty.HARD)) {
				serverconfiguration.setDifficulty(Difficulty.HARD);
			}			
		}
		
		if (ConfigHandler.shouldLockDifficulty.getValue()) {
			if (!islocked) {
				serverconfiguration.setDifficultyLocked(true);
			}
		}
	}
}
