package pie.ilikepiefoo.compat.jade.builder;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.Accessor;

public class ServerDataProviderBuilder<T extends Accessor<?>> extends JadeProviderBuilder {
    private AppendServerDataCallback<T> callback;

    public ServerDataProviderBuilder(ResourceLocation uniqueIdentifier) {
        super(uniqueIdentifier);
        this.callback = ServerDataProviderBuilder::doNothing;
    }

    public static <T extends Accessor<?>> void doNothing(CompoundTag compoundTag, T accessor) {
        // Do nothing
    }

    public AppendServerDataCallback<T> getCallback() {
        return this.callback;
    }

    public ServerDataProviderBuilder<T> setCallback(AppendServerDataCallback<T> callback) {
        this.callback = callback;
        return this;
    }


    public interface AppendServerDataCallback<ACCESSOR extends Accessor<?>> {
        void appendServerData(CompoundTag compoundTag, ACCESSOR accessor);
    }
}
