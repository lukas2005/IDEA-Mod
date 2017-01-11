package lukas2005.idea.guis.player;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class CustomInv implements IInventory {

	/** The name your custom inventory will display in the GUI, possibly just "Inventory" */
	private final String name = "Custom Inventory";
	
	/** The key used to store and retrieve the inventory from NBT */
	private final String tagName = "CustomInvTag";
	
	/** Inventory's size must be same as number of slots you add to the Container class */
	public static ArrayList<ItemStack> inventory = new ArrayList<ItemStack>();
	
	public CustomInv()
	{
		// don't need anything here!
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new TextComponentString(name);
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return inventory.size();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		// TODO Auto-generated method stub
		return inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null)
		{
			if (stack.stackSize > amount)
			{
				stack = stack.splitStack(amount);
				markDirty();
			}
			else
			{
				setInventorySlotContents(slot, null);
			}
		}
		return stack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = getStackInSlot(index);
		setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		
		inventory.set(index, stack);

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
				
			stack.stackSize = this.getInventoryStackLimit();
				
		}
		markDirty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty()	{
		for (int i = 0; i < getSizeInventory(); ++i)
		{
			if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
				inventory.set(i, null);
			}
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public void writeToNBT(NBTTagCompound compound)
	{
		NBTTagList items = new NBTTagList();

		for (int i = 0; i < getSizeInventory(); ++i)
		{
			if (getStackInSlot(i) != null)
			{
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte) i);
				getStackInSlot(i).writeToNBT(item);
				items.appendTag(item);
			}
		}
		
		// We're storing our items in a custom tag list using our 'tagName' from above
		// to prevent potential conflicts
		compound.setTag(tagName, items);
	}

	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList items = compound.getTagList(tagName, 0);

		for (int i = 0; i < items.tagCount(); ++i)
		{
			NBTTagCompound item = (NBTTagCompound) items.get(i);
			byte slot = item.getByte("Slot");

			if (slot >= 0 && slot < getSizeInventory()) {
				inventory.set(i,ItemStack.loadItemStackFromNBT(item));
			}
		}
	}
}
