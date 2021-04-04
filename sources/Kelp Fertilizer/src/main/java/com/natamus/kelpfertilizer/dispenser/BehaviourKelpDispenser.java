/*
 * This is the latest source code of Kelp Fertilizer.
 * Minecraft version: 1.16.5, mod version: 1.4.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Kelp Fertilizer ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.kelpfertilizer.dispenser;

import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BehaviourKelpDispenser implements IDispenseItemBehavior {
	protected final Item kelp;

	public BehaviourKelpDispenser(Item itemIn){
		kelp = itemIn;
	}

	@Override
	public ItemStack dispense(IBlockSource source, ItemStack itemstack) {
		World world = source.getWorld();
		if (world.isRemote) {
			return itemstack;
		}
		
		BlockPos pos = source.getBlockPos();
		BlockState state = source.getBlockState();
		Direction facing = state.get(DispenserBlock.FACING);
		BlockPos facepos = pos.offset(facing);
		
		if (BoneMealItem.applyBonemeal(itemstack, world, facepos, null)) {
			world.playEvent(2005, facepos, 0);
		}
		
		return itemstack;
	}
}