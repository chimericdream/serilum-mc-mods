/*
 * This is the latest source code of The Vanilla Experience.
 * Minecraft version: 1.17.1, mod version: 1.4.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.thevanillaexperience.mods.tntbreaksbedrock.events;

import java.util.ArrayList;
import java.util.List;

import com.natamus.thevanillaexperience.mods.tntbreaksbedrock.util.TNTBreaksBedrockUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class TNTBreaksBedrockBoomEvent {
	@SubscribeEvent
	public void onExplosion(ExplosionEvent.Detonate e) {
		Level world = e.getWorld();
		if (world.isClientSide) {
			return;
		}
		
		Explosion explosion = e.getExplosion();
		
		List<BlockPos> affected = explosion.getToBlow();
		if (affected.size() == 0) {
			return;
		}
		
		if (explosion.getExploder() instanceof PrimedTnt == false) {
			return;
		}
		
		List<BlockPos> bedrocks = new ArrayList<BlockPos>();
		for (BlockPos pos : affected) {
			for (BlockPos bedpos : TNTBreaksBedrockUtil.getBedrocks(world, pos.immutable())) {
				if (!bedrocks.contains(bedpos.immutable())) {
					bedrocks.add(bedpos.immutable());
				}
			}
		}
		
		BlockState air = Blocks.AIR.defaultBlockState();
		for (BlockPos bedrock : bedrocks) {
			world.setBlockAndUpdate(bedrock, air);
		}
	}
}
