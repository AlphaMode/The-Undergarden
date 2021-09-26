package quek.undergarden.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import quek.undergarden.entity.projectile.BlisterbombEntity;
import quek.undergarden.registry.UGItemGroups;
import quek.undergarden.registry.UGSoundEvents;

import net.minecraft.world.item.Item.Properties;

public class BlisterbombItem extends Item {

    public BlisterbombItem() {
        super(new Properties()
                .stacksTo(8)
                .tab(UGItemGroups.GROUP)
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), UGSoundEvents.BLISTERBOMB_THROW.get(), SoundSource.NEUTRAL, 0.5F, 1F);
        playerIn.getCooldowns().addCooldown(this, 50);
        if (!worldIn.isClientSide) {
            BlisterbombEntity blisterbomb = new BlisterbombEntity(worldIn, playerIn);
            blisterbomb.setItem(itemstack);
            blisterbomb.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.5F, 1.0F);
            worldIn.addFreshEntity(blisterbomb);
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.success(itemstack);
    }

}