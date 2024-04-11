package pie.ilikepiefoo.compat.jade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo.KubeJSAdditions;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin(value = KubeJSAdditions.MOD_ID)
public class AdditionalJadePlugin implements IWailaPlugin {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void register(IWailaCommonRegistration registration) {
        LOG.info("Jade Plugin Registering Common Data");
        var event = new WailaCommonRegistrationEventJS(registration);
        JadeEvents.ON_COMMON_REGISTRATION.post(event);
        event.register();
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        LOG.info("Jade Plugin Registering Client Data");
        var event = new WailaClientRegistrationEventJS(registration);
        JadeEvents.ON_CLIENT_REGISTRATION.post(event);
        event.register();
    }
}
