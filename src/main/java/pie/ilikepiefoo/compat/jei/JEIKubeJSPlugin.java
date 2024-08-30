package pie.ilikepiefoo.compat.jei;

import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import mezz.jei.api.gui.drawable.IDrawable;

public class JEIKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        if (type != ScriptType.CLIENT) {
            return;
        }
        if (!Platform.isModLoaded("jei")) {
            return;
        }
        typeWrappers.registerSimple(IDrawable.class, JEIDrawableWrapper::of);
    }
}
