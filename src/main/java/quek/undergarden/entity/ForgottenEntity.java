package quek.undergarden.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import quek.undergarden.entity.rotspawn.RotspawnEntity;
import quek.undergarden.registry.UGItems;
import quek.undergarden.registry.UGSoundEvents;

import javax.annotation.Nullable;

public class ForgottenEntity extends AbstractSkeleton {

    public ForgottenEntity(EntityType<? extends AbstractSkeleton> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, RotspawnEntity.class, true));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return UGSoundEvents.FORGOTTEN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return UGSoundEvents.FORGOTTEN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UGSoundEvents.FORGOTTEN_DEATH.get();
    }

    @Override
    protected SoundEvent getStepSound() {
        return UGSoundEvents.FORGOTTEN_STEP.get();
    }

    @Override
    protected void populateDefaultEquipmentEnchantments(DifficultyInstance difficulty) {
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        for (EquipmentSlot armorSlots : EquipmentSlot.values()) {
            if (armorSlots.getType() == EquipmentSlot.Type.ARMOR) {
                ItemStack armorStack = this.getItemBySlot(armorSlots);
                if (armorStack.isEmpty()) {
                    Item item = getEquipmentForSlot(armorSlots);
                    if (item != null && this.random.nextBoolean()) {
                        this.setItemSlot(armorSlots, new ItemStack(item));
                    }
                }
            }
        }
        if(this.random.nextBoolean()) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(UGItems.CLOGGRUM_SWORD.get()));
        }
        else {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(UGItems.CLOGGRUM_AXE.get()));
        }
    }

    @Nullable
    public static Item getEquipmentForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> UGItems.FORGOTTEN_HELMET.get();
            case CHEST -> UGItems.FORGOTTEN_CHESTPLATE.get();
            case LEGS -> UGItems.FORGOTTEN_LEGGINGS.get();
            default -> null;
        };
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return 1.9F;
    }
}