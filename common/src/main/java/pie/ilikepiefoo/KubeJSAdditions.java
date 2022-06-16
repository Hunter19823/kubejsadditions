package pie.ilikepiefoo;

import me.shedaniel.architectury.registry.CreativeTabs;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.registry.Registries;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class KubeJSAdditions {
    public static final String MOD_ID = "kubejsadditions";
    // We can use this if we don't want to use DeferredRegister
    public static final LazyLoadedValue<Registries> REGISTRIES = new LazyLoadedValue<>(() -> Registries.get(MOD_ID));
    // Registering a new creative tab
    public static final CreativeModeTab KUBEJS_ADDITIONS_TAB = CreativeTabs.create(new ResourceLocation(MOD_ID, "kubejs_additions_tab"), () ->
            new ItemStack(KubeJSAdditions.KUBEJS_ADDITIONS_ITEM.get()));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_REGISTRY);
    public static final RegistrySupplier<Item> KUBEJS_ADDITIONS_ITEM = ITEMS.register("kubejs_additions_item", () ->
            new Item(new Item.Properties().tab(KubeJSAdditions.KUBEJS_ADDITIONS_TAB)));

    public static void init() {
        ITEMS.register();

        System.out.println(ExampleExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}