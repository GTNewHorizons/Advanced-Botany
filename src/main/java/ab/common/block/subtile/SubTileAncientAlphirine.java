package ab.common.block.subtile;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import ab.api.AdvancedBotanyAPI;
import ab.api.recipe.RecipeAncientAlphirine;
import ab.common.entity.EntityAlphirinePortal;
import ab.common.lib.register.RecipeListAB;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.item.ItemLexicon;

public class SubTileAncientAlphirine extends SubTileFunctional {

    protected static int manaRequare = 4500;

    public void onUpdate() {
        super.onUpdate();
        float posX = this.supertile.xCoord + 0.5f;
        float posY = this.supertile.yCoord;
        float posZ = this.supertile.zCoord + 0.5f;
        World world = this.supertile.getWorldObj();
        List<EntityItem> items = world.getEntitiesWithinAABB(
                EntityItem.class,
                AxisAlignedBB.getBoundingBox(posX, posY, posZ, (posX + 1), (posY + 1), (posZ + 1)).expand(1, 0, 1));
        if (this.ticksExisted % 10 == 0 && this.mana >= manaRequare) {
            label666: for (EntityItem item : items) {
                if (item.isDead || !(item.getEntityItem().stackSize >= 1)) continue;
                for (RecipeAncientAlphirine recipe : AdvancedBotanyAPI.alphirineRecipes) {
                    if (recipe.matches(item.getEntityItem())) {
                        if (world.isRemote) {
                            spawnParticle(world, item);
                        } else {
                            if (item.getEntityItem().stackSize > 1) item.getEntityItem().stackSize -= 1;
                            else item.setDead();
                            if (world.rand.nextInt(111) <= recipe.getChance()) {
                                spawnPortal(world, recipe.getOutput().copy(), posX, posY, posZ);
                                this.mana -= manaRequare;
                            } else {
                                this.mana -= manaRequare / 10;
                            }
                            this.sync();
                        }
                        break label666;
                    }
                }
                if (item.getEntityItem().getItem() instanceof ItemLexicon) {
                    if (!((ItemLexicon) item.getEntityItem().getItem())
                            .isKnowledgeUnlocked(item.getEntityItem(), RecipeListAB.forgotten)) {
                        if (world.isRemote) spawnParticle(world, item);
                        else {
                            if (item.getEntityItem().stackSize > 1) item.getEntityItem().stackSize -= 1;
                            else item.setDead();
                            ItemStack lexicon = item.getEntityItem().copy();
                            ((ItemLexicon) lexicon.getItem()).unlockKnowledge(lexicon, RecipeListAB.forgotten);
                            this.mana -= manaRequare;
                            spawnPortal(world, lexicon, posX, posY, posZ);
                            this.sync();
                        }
                        break label666;
                    }
                }
            }
        }
    }

    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(toChunkCoordinates(), 1);
    }

    private void spawnPortal(World world, ItemStack stack, float posX, float posY, float posZ) {
        EntityAlphirinePortal portal = new EntityAlphirinePortal(world);
        float itemX = (float) (posX + (Math.random() * 2.0f - 1.0f));
        float itemY = (float) (posY + 1.2f + (Math.random() - 0.5f));
        float itemZ = (float) (posZ + (Math.random() * 2.0f - 1.0f));
        portal.setPosition(itemX, itemY, itemZ);
        portal.setStack(stack);
        world.spawnEntityInWorld(portal);
    }

    private void spawnParticle(World world, EntityItem item) {
        ItemStack stack = item.getEntityItem();
        if (world.isRemote) for (int i = 0; i < 10; i++) {
            float m = 0.2F;
            float mx = (float) (Math.random() - 0.5D) * m;
            float my = (float) (Math.random() - 0.5D) * m;
            float mz = (float) (Math.random() - 0.5D) * m;
            if (stack.getItem() instanceof net.minecraft.item.ItemBlock) {
                world.spawnParticle(
                        "blockcrack_" + Item.getIdFromItem(stack.getItem()) + "_" + stack.getItemDamage(),
                        item.posX,
                        item.posY,
                        item.posZ,
                        mx,
                        my,
                        mz);
            } else {
                world.spawnParticle(
                        "iconcrack_" + Item.getIdFromItem(stack.getItem()) + "_" + stack.getItemDamage(),
                        item.posX,
                        item.posY,
                        item.posZ,
                        mx,
                        my,
                        mz);
            }
        }
    }

    public int getMaxMana() {
        return 180000;
    }

    public int getColor() {
        return 0xd0bf58;
    }

    public LexiconEntry getEntry() {
        return RecipeListAB.ancientAlphirine;
    }
}
