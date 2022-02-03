package ab.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ab.common.integration.corporea.CustomWrappedIInventory;
import ab.common.lib.utils.Pair;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import vazkii.botania.api.corporea.CorporeaHelper;
import vazkii.botania.api.corporea.CorporeaRequest;
import vazkii.botania.api.corporea.CorporeaRequestEvent;
import vazkii.botania.api.corporea.ICorporeaInterceptor;
import vazkii.botania.api.corporea.ICorporeaSpark;
import vazkii.botania.api.corporea.IWrappedInventory;

public class AdvancedCorporeaHelper {

	public static ItemStack getStackBySlot(IInventory inv, int slot, int itemCount, ICorporeaSpark spark,
			boolean doit) {
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		if (!CorporeaHelper.getSparkForInventory(inv).getConnections().contains(spark)
				|| inv.getStackInSlot(slot) == null)
			return null;
		Pair pair = new Pair<IInventory, Integer>(inv, slot);
		CorporeaRequestEvent event = new CorporeaRequestEvent(pair, itemCount, spark, false, doit);
		if (MinecraftForge.EVENT_BUS.post(event))
			return null;

		Map<ICorporeaInterceptor, ICorporeaSpark> interceptors = new HashMap<ICorporeaInterceptor, ICorporeaSpark>();
		IWrappedInventory invW = CustomWrappedIInventory.wrap(inv, spark);
		List<IInventory> inventories = CorporeaHelper.getInventoriesOnNetwork(spark);
		CorporeaRequest request = new CorporeaRequest(pair, false, itemCount);

		for (IInventory inv$ : inventories) {
			if (inv$ instanceof ICorporeaInterceptor) {
				ICorporeaSpark spark$ = CorporeaHelper.getSparkForInventory(inv$);
				ICorporeaInterceptor interceptor = (ICorporeaInterceptor) inv$;
				interceptor.interceptRequest(pair, itemCount, spark$, spark, stacks, inventories, doit);
				interceptors.put(interceptor, spark$);
			}
		}

		if (doit) {
			stacks.addAll(invW.extractItems(request));
		} else {
			stacks.addAll(invW.countItems(request));
		}

		for (ICorporeaInterceptor interceptor : interceptors.keySet())
			interceptor.interceptRequestLast(pair, itemCount, interceptors.get(interceptor), spark, stacks, inventories,
					doit);

		if (stacks.isEmpty())
			return null;
		else
			return stacks.get(0);
	}

	public static Object trasformateRequest(ICorporeaSpark spark, Object from, Class class$) {
		ItemStack stack = null;
		if (from instanceof ItemStack)
			stack = (ItemStack) from;
		if (from instanceof Pair) {
			Pair pair = (Pair) from;
			Object key = pair.getKey();
			Object value = pair.getValue();
			if (key instanceof IInventory && value instanceof Integer)
				stack = ((IInventory) from).getStackInSlot((Integer) value);
			if (key instanceof String && value instanceof ItemStack) {
				int count = ((ItemStack) value).stackSize;
				List<ItemStack> stacks = requestSameNamedItem((String) key, (ItemStack) value, spark, count, false);
				if(stacks.isEmpty()) {
					stacks = requestSameNamedItem((String) key, null, spark, count, false);
					if(stacks.isEmpty())
						return null;
				}
				stack = stacks.get(0);
			}
		}
		if(String.class.isAssignableFrom(class$))
			return stack.getDisplayName();
		if(IInventory.class.isAssignableFrom(class$))
			return getFirstInventoryWithStackByRequest(spark, stack);
		if(ItemStack.class.isAssignableFrom(class$))
			return stack;
		return null;
	}

	public static Pair<IInventory, Integer> getFirstInventoryWithStackByRequest(ICorporeaSpark spark, ItemStack stack) {
		List<IInventory> inventories = CorporeaHelper.getInventoriesOnNetwork(spark);
		Pair<IInventory, Integer> pair = new Pair<IInventory, Integer>();
		for (IInventory inv : inventories) {
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack stack$ = inv.getStackInSlot(i);
				if (stack$ == null)
					continue;
				if (stack.getItem().equals(stack$.getItem()) && stack$.getItemDamage() == stack.getItemDamage()
						&& stack.getItem().equals(stack$.getItem())
						&& stack.getDisplayName().equals(stack$.getDisplayName())) {
					pair.set(inv, i);
				}
			}
		}
		return pair;
	}

	public static List<ItemStack> requestRandomItem(ICorporeaSpark spark, int itemCount, boolean doit) {
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		CorporeaRequestEvent event = new CorporeaRequestEvent(null, itemCount, spark, false, doit);
		if (MinecraftForge.EVENT_BUS.post(event))
			return stacks;

		Map<ICorporeaInterceptor, ICorporeaSpark> interceptors = new HashMap<ICorporeaInterceptor, ICorporeaSpark>();
		List<IInventory> inventories = CorporeaHelper.getInventoriesOnNetwork(spark);
		List<IWrappedInventory> inventoriesW = wrapInventory(inventories);
		CorporeaRequest request = new CorporeaRequest(null, false, itemCount);

		for (IWrappedInventory inv : inventoriesW) {
			ICorporeaSpark invSpark = inv.getSpark();

			Object originalInventory = inv.getWrappedObject();
			if (originalInventory instanceof ICorporeaInterceptor) {
				ICorporeaInterceptor interceptor = (ICorporeaInterceptor) originalInventory;
				interceptor.interceptRequest(null, itemCount, invSpark, spark, stacks, inventories, doit);
				interceptors.put(interceptor, invSpark);
			}

			if (doit) {
				stacks.addAll(inv.extractItems(request));
			} else {
				stacks.addAll(inv.countItems(request));
			}
		}

		for (ICorporeaInterceptor interceptor : interceptors.keySet())
			interceptor.interceptRequestLast(null, itemCount, interceptors.get(interceptor), spark, stacks, inventories,
					doit);

		return stacks;
	}

	public static int sumStackSizes(List<ItemStack> stacks) {
		int sum = 0;
		for (ItemStack stack : stacks)
			sum += stack.stackSize;
		return sum;
	}

	public static List<IWrappedInventory> wrapInventory(List<IInventory> inventories) {
		ArrayList<IWrappedInventory> arrayList = new ArrayList<IWrappedInventory>();
		for (IInventory inv : inventories) {
			ICorporeaSpark spark = CorporeaHelper.getSparkForInventory(inv);
			IWrappedInventory wrapped = CustomWrappedIInventory.wrap(inv, spark);
			arrayList.add(wrapped);
		}
		return arrayList;
	}

	public static List<ItemStack> requestSameNamedItem(String name, ItemStack matcher, ICorporeaSpark spark, int count,
			boolean doit) {
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		Pair pair = new Pair<String, ItemStack>(name, matcher);
		CorporeaRequestEvent event = new CorporeaRequestEvent(pair, count, spark, false, doit);
		if (MinecraftForge.EVENT_BUS.post(event))
			return stacks;

		Map<ICorporeaInterceptor, ICorporeaSpark> interceptors = new HashMap<ICorporeaInterceptor, ICorporeaSpark>();
		List<IInventory> inventories = CorporeaHelper.getInventoriesOnNetwork(spark);
		List<IWrappedInventory> inventoriesW = wrapInventory(inventories);
		CorporeaRequest request = new CorporeaRequest(pair, false, count);

		for (IWrappedInventory inv : inventoriesW) {
			ICorporeaSpark invSpark = inv.getSpark();

			Object originalInventory = inv.getWrappedObject();
			if (originalInventory instanceof ICorporeaInterceptor) {
				ICorporeaInterceptor interceptor = (ICorporeaInterceptor) originalInventory;
				interceptor.interceptRequest(pair, count, invSpark, spark, stacks, inventories, doit);
				interceptors.put(interceptor, invSpark);
			}

			if (doit) {
				stacks.addAll(inv.extractItems(request));
			} else {
				stacks.addAll(inv.countItems(request));
			}
		}

		for (ICorporeaInterceptor interceptor : interceptors.keySet())
			interceptor.interceptRequestLast(matcher, count, interceptors.get(interceptor), spark, stacks, inventories,
					doit);

		return stacks;
	}
}
