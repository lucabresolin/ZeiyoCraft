package fr.zeiyo.zeiyocraft.item;


import fr.zeiyo.zeiyocraft.ZeiyoMain;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ZItemDrug extends Item
{

    private final int healAmount;
    private final float saturationModifier;

    public ZItemDrug(String unlocalizedName, int amount, float saturation)
    {
    	this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(ZeiyoMain.MODID + ":" + unlocalizedName);
        this.healAmount = amount;
        this.saturationModifier = saturation;
        this.setCreativeTab(CreativeTabs.FOOD);
        this.setMaxStackSize(16);
    }

    public ItemStack onItemUseEnd(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        stack.func_190918_g(1);

        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            entityplayer.getFoodStats().addStats(healAmount, saturationModifier);
            worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            this.onFoodEaten(stack, worldIn, entityplayer);
            entityplayer.addStat(StatList.getObjectUseStats(this));
        }

        return stack;
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        {
          /*  Random rand = new Random();
            int effectNumber = rand.nextInt(5)*5;
            System.out.println("on y va  " + effectNumber);
            switch(effectNumber)
            {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;

                default:
                    break;
            }*/
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 400, 0, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 400, 0, false, false));
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 24;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.NONE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn)
    {
        ItemStack itemstack = worldIn.getHeldItem(playerIn);
        worldIn.setActiveHand(playerIn);
        return new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        this.onItemUseEnd(stack, worldIn, entityLiving);
        return new ItemStack(ZeiyoItems.pipe);
    }
    
}