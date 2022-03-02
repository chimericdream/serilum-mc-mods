/*
 * This is the latest source code of GUI Followers.
 * Minecraft version: 1.19.x, mod version: 1.7.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of GUI Followers ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.guifollowers.events;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.natamus.collective_fabric.functions.WorldFunctions;
import com.natamus.guifollowers.config.ConfigHandler;
import com.natamus.guifollowers.util.Variables;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.phys.Vec3;

public class GUIEvent {
	private static final Minecraft mc = Minecraft.getInstance();

	public static void renderOverlay(PoseStack posestack, float tickDelta){
		Font fontRender = mc.font;
		Window scaled = mc.getWindow();
		posestack.pushPose();
		
		if (Variables.activefollowers.size() > 0) {
			int width = scaled.getGuiScaledWidth();
			
			String displaystring = ConfigHandler.followerListHeaderFormat.getValue();
			
			int stringWidth = fontRender.width(displaystring);
			
			Color colour = new Color(ConfigHandler.RGB_R.getValue(), ConfigHandler.RGB_G.getValue(), ConfigHandler.RGB_B.getValue(), 255);
			
			int xcoord = 0;
			int xoffset = 5;
			if (ConfigHandler.followerListPositionIsLeft.getValue()) {
				xcoord = 5;
			}
			else if (ConfigHandler.followerListPositionIsCenter.getValue()) {
				xcoord = (width/2) - (stringWidth/2);
			}
			else {
				xcoord = width - stringWidth - 5;
			}
			
			boolean drawnfirst = false;
			int heightoffset = ConfigHandler.followerListHeightOffset.getValue();
			
			LocalPlayer player = mc.player;
			String playerdimension = WorldFunctions.getWorldDimensionName(player.getCommandSenderWorld());
			
			List<Entity> toremove = new ArrayList<Entity>();
			Iterator<Entity> it = new ArrayList<Entity>(Variables.activefollowers).iterator();
			while (it.hasNext()) {
				Entity follower = it.next();
				String followerdimension = WorldFunctions.getWorldDimensionName(follower.getCommandSenderWorld());
				if (!playerdimension.equals(followerdimension)) {
					toremove.add(follower);
					continue;
				}
				
				if (!follower.isAlive() || follower instanceof TamableAnimal == false) {
					toremove.add(follower);
					continue;
				}
				
				TamableAnimal te = (TamableAnimal)follower;
				if (te.isInSittingPose()) {
					toremove.add(follower);
					continue;
				}
				
				String follower_string = follower.getName().getString();
				if (ConfigHandler.showFollowerHealth.getValue()) {
					LivingEntity le = (LivingEntity)follower;
					float currenthealth = le.getHealth();
					float maxhealth = le.getMaxHealth();
					
					int percenthealth = (int)((100/maxhealth)*currenthealth);
					if (percenthealth <= 0) {
						toremove.add(follower);
						continue;
					}
					
					String healthformat = ConfigHandler.followerHealthFormat.getValue();
					follower_string = follower_string + healthformat.replaceAll("<health>", percenthealth + "");
				}
				
				if (ConfigHandler.showFollowerDistance.getValue()) {
					Vec3 pvec = player.position();
					Vec3 fvec = follower.position();
					
					double distance = pvec.distanceTo(fvec);
					String distanceformat = ConfigHandler.followerDistanceFormat.getValue();
					follower_string = follower_string + distanceformat.replaceAll("<distance>", String.format("%.2f", distance));
				}
				
				int follower_stringWidth = fontRender.width(follower_string);
				
				if (ConfigHandler.followerListPositionIsCenter.getValue()) {
					xcoord = (width/2) - (follower_stringWidth/2) - xoffset;
				}
				else if (!ConfigHandler.followerListPositionIsLeft.getValue()) {
					xcoord = width - follower_stringWidth - 5 - xoffset;
				}
				
				if (!drawnfirst) {
					fontRender.draw(posestack, displaystring, xcoord, heightoffset, colour.getRGB());
					drawnfirst = true;
				}
				
				heightoffset += 10;
				fontRender.draw(posestack, follower_string, xcoord + xoffset, heightoffset, colour.getRGB());
			}
			
			if (toremove.size() > 0) {
				for (Entity etr : toremove) {
					Variables.activefollowers.remove(etr);
				}
			}
		}
		
		posestack.popPose();
	}
}