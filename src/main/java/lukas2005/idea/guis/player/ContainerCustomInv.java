package lukas2005.idea.guis.player;

import javax.swing.Icon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCustomInv extends Container
{
	/** Avoid magic numbers! This will greatly reduce the chance of you making errors in 'transferStackInSlot' method */
	private static final int ARMOR_START = 0, ARMOR_END = ARMOR_START+3,
			INV_START = ARMOR_END+1, INV_END = INV_START+26, HOTBAR_START = INV_END+1,
			HOTBAR_END = HOTBAR_START+8;

	public ContainerCustomInv(EntityPlayer player, InventoryPlayer inventoryPlayer, CustomInv inventoryCustom)
	{
		int i;

		// Add CUSTOM slots - we'll just add two for now, both of the same type.
		// Make a new Slot class for each different item type you want to add
		this.addSlotToContainer(new Slot(inventoryCustom, 0, 80, 8));
		this.addSlotToContainer(new Slot(inventoryCustom, 1, 80, 26));

		// Add ARMOR slots; note you need to make a public version of SlotArmor
		// just copy and paste the vanilla code into a new class and change what you need
		for (i = 0; i < 4; ++i)
		{
			this.addSlotToContainer(new SlotArmor(player, inventoryPlayer, inventoryPlayer.getSizeInventory() - 1 - i, 8, 8 + i * 18, i));
		}

		// Add vanilla PLAYER INVENTORY - just copied/pasted from vanilla classes
		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		// Add ACTION BAR - just copied/pasted from vanilla classes
		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	/**
	 * This should always return true, since custom inventory can be accessed from anywhere
	 */
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 * Basically the same as every other container I make, since I define the same constant indices for all of them 
	 */
	public ItemStack transferStackInSlot(EntityPlayer player, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// Either armor slot or custom item slot was clicked
			if (par2 < INV_START)
			{
				// try to place in player inventory / action bar
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			// Item is in inventory / hotbar, try to place either in custom or armor slots
			else
			{
				// if item is armor
				if (itemstack1.getItem() instanceof ItemArmor){
					
					int type = ((ItemArmor) itemstack1.getItem()).armorType.getIndex();
					if (!this.mergeItemStack(itemstack1, ARMOR_START + type, ARMOR_START + type + 1, false))
					{
						return null;
					}
				}
				// item in player's inventory, but not in action bar
				else if (par2 >= INV_START && par2 < HOTBAR_START)
				{
					// place in action bar
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_START + 1, false))
					{
						return null;
					}
				}
				// item in action bar - place in player inventory
				else if (par2 >= HOTBAR_START && par2 < HOTBAR_END + 1)
				{
					if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false))
					{
						return null;
					}
				}
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}
}

// Armor Slot:
class SlotArmor extends Slot
{
	/** The armor type that can be placed on that slot, it uses the same values of armorType field on ItemArmor. */
	EntityEquipmentSlot armorType;

	/** The parent class of this slot, ContainerPlayer, SlotArmor is a Anon inner class. */
	final EntityPlayer player;

	public SlotArmor(EntityPlayer player, IInventory inventory, int slotIndex, int x, int y, int armorType)
	{
		super(inventory, slotIndex, x, y);
		this.player = player;
		
		switch (armorType) {
		
		case(0):
			this.armorType = EntityEquipmentSlot.HEAD;
		
		case(1):
			this.armorType = EntityEquipmentSlot.CHEST;
		
		case(2):
			this.armorType = EntityEquipmentSlot.LEGS;
		
		case(3):
			this.armorType = EntityEquipmentSlot.FEET;
		
		default:
			this.armorType = EntityEquipmentSlot.HEAD;
		
		}
	}

	/**
	 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
	 * of armor slots)
	 */
	public int getSlotStackLimit()
	{
		return 1;
	}

	/**
	 * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
	 */
	public boolean isItemValid(ItemStack itemstack)
	{
		Item item = (itemstack == null ? null : itemstack.getItem());
		return item != null && item.isValidArmor(itemstack, armorType, player);
	}
}
