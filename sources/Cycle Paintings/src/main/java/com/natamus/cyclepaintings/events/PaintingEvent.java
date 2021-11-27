/*
 * This is the latest source code of Cycle Paintings.
 * Minecraft version: 1.17.1, mod version: 2.1.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Cycle Paintings ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.cyclepaintings.events;

import java.util.Collections;
import java.util.List;

import com.natamus.cyclepaintings.util.Util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class PaintingEvent {
	@SubscribeEvent
	public void onClick(PlayerInteractEvent.EntityInteract e) {
		Level world = e.getWorld();
		if (world.isClientSide) {
			return;
		}
		
		ItemStack hand = e.getItemStack();
		if (!hand.getItem().equals(Items.PAINTING)) {
			return;
		}
		
		Entity target = e.getTarget();
		if (target instanceof Painting == false) {
			return;
		}
		
		Player player = e.getPlayer();
		Painting painting = (Painting)target;
		Motive art = painting.motive;
		
		Motive newart = null;
		
		List<Motive> similarart = Util.getSimilarArt(art);
		if (player.isCrouching()) {
			Collections.reverse(similarart);
		}
		
		if (similarart.get(similarart.size()-1).equals(art)) {
			newart = similarart.get(0);
		}
		else {
			Boolean choosenext = false;
			for (Motive sa : similarart) {
				if (choosenext) {
					newart = sa;
					break;
				}
				if (sa.equals(art)) {
					choosenext = true;
				}
			}
		}
		
		if (newart == null) {
			return;
		}
		
		BlockPos ppos = painting.getPos();
		Painting newpainting = new Painting(world, ppos, painting.getMotionDirection());
		
		newpainting.motive = newart;
		newpainting.setPos(ppos.getX(), ppos.getY(), ppos.getZ());
		
		painting.remove(RemovalReason.DISCARDED);
		world.addFreshEntity(newpainting);
	}
}
