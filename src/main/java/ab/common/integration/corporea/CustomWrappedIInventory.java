package ab.common.integration.corporea;

import java.util.ArrayList;
import java.util.List;

import ab.common.lib.utils.Pair;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.corporea.CorporeaHelper;
import vazkii.botania.api.corporea.CorporeaRequest;
import vazkii.botania.api.corporea.ICorporeaSpark;
import vazkii.botania.api.corporea.IWrappedInventory;
import vazkii.botania.common.integration.corporea.WrappedInventoryBase;

public class CustomWrappedIInventory extends WrappedInventoryBase {

	private IInventory inv;

	private CustomWrappedIInventory(IInventory inv, ICorporeaSpark spark) {
		this.inv = inv;
		this.spark = spark;
	}

	@Override
	public IInventory getWrappedObject() {
		return inv;
	}

	@Override
	public List<ItemStack> countItems(CorporeaRequest request) {
		return iterateOverSlots(request, false);
	}

	@Override
	public List<ItemStack> extractItems(CorporeaRequest request) {
		return iterateOverSlots(request, true);
	}

	private List<ItemStack> iterateOverSlots(CorporeaRequest request, boolean doit) {
		List<ItemStack> stacks = new ArrayList<ItemStack>();

		boolean removedAny = false;
		boolean isRequestNamed = request.matcher instanceof Pair && ((Pair) request.matcher).getKey() instanceof String;
		boolean isRequestSloted = request.matcher instanceof Pair
				&& ((Pair) request.matcher).getKey() instanceof IInventory
				&& ((Pair) request.matcher).getValue() instanceof Integer;
		String name = null;
		ItemStack stack = null;

		if (isRequestNamed) {
			name = (String) ((Pair) request.matcher).getKey();
			stack = (ItemStack) ((Pair) request.matcher).getValue();
		}

		if (isRequestSloted) {
			IInventory inv = (IInventory) ((Pair) request.matcher).getKey();
			int slot = (Integer) ((Pair) request.matcher).getValue();
			
			if (CorporeaHelper.isValidSlot(inv, slot)) {
				ItemStack stackAt = inv.getStackInSlot(slot);
				if (this.inv != ((Pair) request.matcher).getKey())
					return stacks;

				int rem = Math.min(stackAt.stackSize, request.count == -1 ? stackAt.stackSize : request.count);
				if (rem > 0) {
					ItemStack copy = stackAt.copy();
					if (rem < copy.stackSize)
						copy.stackSize = rem;
					stacks.add(copy);
				}

				request.foundItems += stackAt.stackSize;
				request.extractedItems += rem;

				if (doit && rem > 0) {
					inv.decrStackSize(slot, rem);
					removedAny = true;
					if (spark != null)
						spark.onItemExtracted(stackAt);
				}
				if (request.count != -1)
					request.count -= rem;
			}
		}

		for (int i = inv.getSizeInventory() - 1; i >= 0; i--) {
			if (!CorporeaHelper.isValidSlot(inv, i))
				continue;

			ItemStack stackAt = inv.getStackInSlot(i);
			// WARNING: this code is very similar in all implementations of
			// IWrappedInventory - keep it synch
			if ((request.matcher == null && stackAt != null)
					|| isMatchingItemStack(request.matcher, request.checkNBT, stackAt)
					|| (isRequestNamed && isMatchingNames(name, stackAt, stack, request.checkNBT))) {
				int rem = Math.min(stackAt.stackSize, request.count == -1 ? stackAt.stackSize : request.count);

				if (rem > 0) {
					ItemStack copy = stackAt.copy();
					if (rem < copy.stackSize)
						copy.stackSize = rem;
					stacks.add(copy);
				}

				request.foundItems += stackAt.stackSize;
				request.extractedItems += rem;

				if (doit && rem > 0) {
					inv.decrStackSize(i, rem);
					removedAny = true;
					if (spark != null)
						spark.onItemExtracted(stackAt);
				}
				if (request.count != -1)
					request.count -= rem;
			}
		}

		if (removedAny) {
			inv.markDirty();
		}

		return stacks;
	}

	protected boolean isMatchingNames(String name, ItemStack stackAt, ItemStack stack, boolean checkNBT) {
		return isSameNamed(name, stackAt) && (stack == null || !isMatchingItemStack(stack, checkNBT, stackAt));
	}

	protected boolean isSameNamed(String name, ItemStack stack) {
		if (stack != null)
			System.out.println(stack.getDisplayName());
		return stack != null && name.equals(stack.getDisplayName());
	}

	public static IWrappedInventory wrap(IInventory inv, ICorporeaSpark spark) {
		return new CustomWrappedIInventory(inv, spark);
	}

}
