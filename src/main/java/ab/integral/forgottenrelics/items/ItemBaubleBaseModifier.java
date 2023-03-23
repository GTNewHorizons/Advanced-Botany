package com.integral.forgottenrelics.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * A direct copy of ItemBaubleModifier from Botania.
 * 
 * @author Integral
 */

public abstract class ItemBaubleBaseModifier extends ItemBaubleBase {

    Multimap<String, AttributeModifier> attributes = HashMultimap.create();

    public ItemBaubleBaseModifier(String name) {
        super(name);
    }

    @Override
    public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
        attributes.clear();
        fillModifiers(attributes, stack);
        player.getAttributeMap().applyAttributeModifiers(attributes);
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        attributes.clear();
        fillModifiers(attributes, stack);
        player.getAttributeMap().removeAttributeModifiers(attributes);
    }

    abstract void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack);

}
