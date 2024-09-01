package ab.common.item.equipment;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import ab.AdvancedBotany;
import ab.api.AdvancedBotanyAPI;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemMithrillMultiTool extends ItemTool {

    IIcon iconTool;
    IIcon iconOverlay;

    public ItemMithrillMultiTool() {
        super(0.0f, AdvancedBotanyAPI.mithrilToolMaterial, null);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setUnlocalizedName("mithrillMultiTool");
    }

    public boolean isItemTool(ItemStack par1ItemStack) {
        return true;
    }

    public boolean canHarvestBlock(Block block, ItemStack stack) {
        return (block != Blocks.bedrock);
    }

    public boolean func_150897_b(Block block) {
        return true;
    }

    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (this.isEnabled(stack)) return super.getDigSpeed(stack, block, meta) + 135.0f;
        else return 0.0f;
    }

    public float func_150893_a(ItemStack stack, Block block) {
        return 135.0f;
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        setEnabled(par1ItemStack, !isEnabled(par1ItemStack));
        if (!par2World.isRemote)
            par2World.playSoundAtEntity((Entity) par3EntityPlayer, "botania:terraPickMode", 0.5F, 0.4F);
        return par1ItemStack;
    }

    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.iconTool = ir.registerIcon("ab:item.mithrillMultiTool");
        this.iconOverlay = ir.registerIcon("ab:item.mithrillMultiTool.overlay");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 1 ? (isEnabled(stack) ? iconOverlay : iconTool) : iconTool;
    }

    boolean isEnabled(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, "enabled", false);
    }

    void setEnabled(ItemStack stack, boolean enabled) {
        ItemNBTHelper.setBoolean(stack, "enabled", enabled);
    }
}
