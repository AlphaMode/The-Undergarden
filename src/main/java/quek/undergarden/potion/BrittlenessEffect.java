package quek.undergarden.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import quek.undergarden.registry.UGEffects;

@Mod.EventBusSubscriber
public class BrittlenessEffect extends MobEffect {

    public BrittlenessEffect() {
        super(MobEffectCategory.HARMFUL, 9843250);
    }

    @SubscribeEvent
    public static void applyBrittleness(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        DamageSource source = event.getSource();
        float damage = event.getAmount();

        if (entity.hasEffect(UGEffects.BRITTLENESS.get()) && source != DamageSource.OUT_OF_WORLD) {
            int amplifier = (entity.getEffect(UGEffects.BRITTLENESS.get()).getAmplifier() + 1) + (entity.getArmorValue() / 2) * 2;

            event.setAmount(damage + amplifier);
        }
    }
}
