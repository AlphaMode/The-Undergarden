package quek.undergarden.item.armor;

import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.Level;
import quek.undergarden.registry.UGItemGroups;

import net.minecraft.world.item.Item.Properties;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class MasticatedChestplateItem extends ArmorItem {

    public MasticatedChestplateItem(ArmorMaterial materialIn) {
        super(materialIn, EquipmentSlot.CHEST, new Properties()
                .rarity(Rarity.EPIC)
                .tab(UGItemGroups.GROUP)
        );
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> list) {
        if(allowdedIn(group)) {
            ItemStack stack = new ItemStack(this);
            stack.enchant(Enchantments.THORNS, 5);
            list.add(stack);
        }

    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        stack.serializeNBT();
        stack.enchant(Enchantments.THORNS, 5);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot slot, String layer) {
        return "undergarden:textures/armor/masticated_layer_1.png";
    }
}