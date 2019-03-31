package io.github.cottonmc.um.block.entity;

import io.github.cottonmc.energy.impl.SimpleEnergyComponent;
import io.github.cottonmc.um.block.UMBlocks;
import io.github.cottonmc.um.component.SimpleItemComponent;
import io.github.cottonmc.um.component.wrapper.SidedItemView;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.ContainerLock;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.nbt.CompoundTag;

public class RollerEntity extends BlockEntity {

	public static final int SLOT_INGREDIENT = 0;
	public static final int SLOT_WORK = 1;
	public static final int SLOT_RESULT = 2;
	public static final int SLOT_RESULT_EXTRA = 3;

	private ContainerLock lock; //TODO: Awaiting design direction, to be decided alongside Locky

	/** The machine's internal item storage. */
	private SimpleItemComponent items = new SimpleItemComponent(4);
	/** The machine's internal energy buffer. */
	SimpleEnergyComponent energy = new SimpleEnergyComponent(32);
	/** The WorldTickTime when the machine started the current operation. */
	long operationStart = 0L;
	/** The duration of the current operation. */
	long operationLength = 0L;

	/** A Recipe indicating the operation currently in progress. */
	//TODO: Fix up with a real recipe class
	//SimpleProcessingRecipe operation;

	public RollerEntity() {
		super(UMBlocks.ROLLER_ENTITY);
		items.listen(this::markDirty);
		energy.listen(this::markDirty);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		CompoundTag result = super.toTag(tag);

		result.put("Items", items.toTag());

		result.put("Energy", energy.toTag());
		result.putLong("OperationStart", operationStart);
		result.putInt("OperationLength", (int)operationLength);

		return result;
	}

	@Override
	public void fromTag(CompoundTag tag) {
		super.fromTag(tag);

		items.fromTag(tag.getTag("Items"));
		energy.fromTag(tag.getTag("Energy"));

		operationStart = tag.getLong("OperationStart");
		operationLength = tag.getInt("OperationLength");

	}

	@Override
	public void markDirty() {
		super.markDirty();
		if (!world.getBlockTickScheduler().isScheduled(pos, UMBlocks.ROLLER)) {
			//Check if we need to start pulsing again
			if (needsPulse()) world.getBlockTickScheduler().schedule(pos, UMBlocks.ROLLER, 1);
		}
	}

	public void pulse() {
		/*
		if (world==null) return;
		if (operation != world.getRecipeManager().get(UMRecipes.ROLLER, getInventory(), world).orElse(null)) {
			operation = null;
			operationLength = 0;
		}
		if (world.getTime()>=operationStart+operationLength) {

			if (operation==null && !items.get(SLOT_WORK).isEmpty()) {
				operation = world.getRecipeManager().get(UMRecipes.ROLLER, getInventory(), world).orElse(null);
				if (operation != null) {
					operationStart = world.getTime();
					operationLength = operation.getDuration();
				}

			} else {
				items.getInvStack(SLOT_INGREDIENT).subtractAmount(1); // probably put an amount in the Recipe
				items.insert(SLOT_RESULT, operation.getOutput(), ActionType.PERFORM);
			}


			if (needsPulse()) {
				world.getBlockTickScheduler().schedule(pos, UMBlocks.HAMMER_MILL, (int)operationLength);
			}
		}
		*/
	}

	public boolean needsPulse() {
		if (energy.getCurrentEnergy()<=0) return false; //We can't do anything while powered down
		if (items.isEmpty()) return false;              //We can't do anything while totally empty

		return true;
	}

	//@Override
	public SidedInventory getInventory() {
		return new SidedItemView(items);
	}

}