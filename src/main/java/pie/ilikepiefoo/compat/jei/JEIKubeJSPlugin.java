package pie.ilikepiefoo.compat.jei;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import mezz.jei.api.gui.drawable.IDrawable;
import net.neoforged.fml.ModList;

public class JEIKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        if (type != ScriptType.CLIENT) {
            return;
        }
        if (!ModList.get().isLoaded("jei")) {
            return;
        }
        typeWrappers.registerSimple(IDrawable.class, JEIDrawableWrapper::of);
    }
}
