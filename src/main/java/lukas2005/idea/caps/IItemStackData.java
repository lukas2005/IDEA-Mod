package lukas2005.idea.caps;

import lukas2005.idea.network.ModNetwork;
import lukas2005.idea.network.packets.ItemStackDataMessage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public interface IItemStackData {
		
		public Double setWeight(Double w);
		public Double getWeight();
		
		public Double subWeight(Double w);
		public Double addWeight(Double w);
		
		public static class Storage implements Capability.IStorage<IItemStackData> {

			  @Override
			  public NBTBase writeNBT(Capability<IItemStackData> capability, IItemStackData instance, EnumFacing side) {
				  NBTTagCompound nbt = new NBTTagCompound();
				  
				  nbt.setDouble("Weight", instance.getWeight());
				  
				  return nbt;
			  }

				@Override
				public void readNBT(Capability<IItemStackData> capability, IItemStackData instance, EnumFacing side, NBTBase nbt) {

					instance.setWeight(((NBTTagCompound)nbt).getDouble("Weight"));
					
				}
				
		}
		
		public static class DefaultImpl implements IItemStackData {

			private Double Weight = 1.0;
			
			DefaultImpl(Double d) {
				
				setWeight(d);
			}
			
			@Override
			public Double addWeight(Double count) {

				return setWeight(getWeight() + count);
			}

			@Override
			public Double subWeight(Double count) {

				return setWeight(getWeight() - count);
			}

			@Override
			public Double setWeight(Double count) {
				
				if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
					
					ModNetwork.INSTANCE.sendToAll(new ItemStackDataMessage(count));	
					
				}
				
				this.Weight = count;
				
				return this.Weight;
				
			}

			@Override
			public Double getWeight() {

				return this.Weight;
				
			}  
		}
		
		public static class Provider implements ICapabilitySerializable<NBTTagCompound> {
			
			private IItemStackData instance;
			
			public Provider(Double d) {
				
				instance = new DefaultImpl(d);
				
			}
			
			@Override

			public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
				
				return capability == ModCaps.ITEMSTACK_DATA_CAP;

			}



			@SuppressWarnings("unchecked")
			@Override
			public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

				return (T) this.instance;

			}
			@Override
			public NBTTagCompound serializeNBT() {
				return (NBTTagCompound)  ModCaps.ITEMSTACK_DATA_CAP.getStorage().writeNBT(ModCaps.ITEMSTACK_DATA_CAP, instance, null);
			}

			@Override
			public void deserializeNBT(NBTTagCompound nbt) {
				ModCaps.ITEMSTACK_DATA_CAP.getStorage().readNBT(ModCaps.ITEMSTACK_DATA_CAP, this.instance, null, nbt);
			}
			
		}
		
}
