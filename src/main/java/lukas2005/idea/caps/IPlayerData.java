package lukas2005.idea.caps;

import lukas2005.idea.network.ModNetwork;
import lukas2005.idea.network.packets.PlayerDataMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface IPlayerData {
		
		public int addStrength(int count);
		public int subStrength(int count);
		
		public int setStrength(int count);
		public int getStrength();

		public static class Storage implements Capability.IStorage<IPlayerData> {

			  @Override
			  public NBTBase writeNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side) {
				  NBTTagCompound nbt = new NBTTagCompound();
				  
				  nbt.setInteger("Strength", instance.getStrength());
				  
				  return nbt;
			  }

				@Override
				public void readNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side, NBTBase nbt) {

					instance.setStrength(((NBTTagCompound)nbt).getInteger("Strength"));
					
				}
				
		}
		
		public static class DefaultImpl implements IPlayerData {

			private int Strength = 0;
			
			private EntityPlayer p;
			
			DefaultImpl(EntityPlayer p) {
				
				this.p = p;
				
			}
			
			@Override
			public int addStrength(int count) {

				return setStrength(getStrength() + count);
			}

			@Override
			public int subStrength(int count) {

				return setStrength(getStrength() - count);
			}

			@Override
			public int setStrength(int count) {
				
				if (!p.getEntityWorld().isRemote) {
					
					ModNetwork.INSTANCE.sendTo(new PlayerDataMessage(count), (EntityPlayerMP) p);	
					
				}
				
				return this.Strength = count;
				
			}

			@Override
			public int getStrength() {

				return this.Strength;
				
			}  
		}
		
		public static class Provider implements ICapabilitySerializable<NBTTagCompound> {
			
			private IPlayerData instance;
			
			public Provider(EntityPlayer p) {
				
				instance = new DefaultImpl(p);
				
			}
			
			 @Override

			 public boolean hasCapability(Capability<?> capability, EnumFacing facing)

			 {

			 return capability == ModCaps.PLAYER_DATA_CAP;

			 }



			 @Override

			 public <T> T getCapability(Capability<T> capability, EnumFacing facing)

			 {

			 return capability == ModCaps.PLAYER_DATA_CAP ? ModCaps.PLAYER_DATA_CAP.<T> cast(this.instance) : null;

			 }

			@Override
			public NBTTagCompound serializeNBT() {
				return (NBTTagCompound) ModCaps.PLAYER_DATA_CAP.getStorage().writeNBT(ModCaps.PLAYER_DATA_CAP, this.instance, null);
			}

			@Override
			public void deserializeNBT(NBTTagCompound nbt) {
				 ModCaps.PLAYER_DATA_CAP.getStorage().readNBT(ModCaps.PLAYER_DATA_CAP, this.instance, null, nbt);
			}
			
			
			
		}
		
}