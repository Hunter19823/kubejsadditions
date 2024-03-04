package pie.ilikepiefoo.wrappergen.util;

public class MethodHotSwapHandler<HANDLER> implements MethodHandler<HANDLER> {
    private HANDLER superHandler;
    private HANDLER customHandler;

    public MethodHotSwapHandler() {

    }

    public MethodHotSwapHandler(HANDLER superHandler) {
        this.superHandler = superHandler;
    }

    public MethodHotSwapHandler(HANDLER superHandler, HANDLER customHandler) {
        this.superHandler = superHandler;
        this.customHandler = customHandler;
    }

    public MethodHotSwapHandler<HANDLER> setCustomHandler(HANDLER customHandler) {
        this.customHandler = customHandler;
        return this;
    }

    public HANDLER getHandler() {
        if (superHandler == null && customHandler == null) {
            throw new IllegalStateException("Not implemented.");
        }
        return customHandler != null
                ? customHandler
                : superHandler;
    }

    public HANDLER getHandler(HANDLER superHandler) {
        return (this.customHandler == null) ? superHandler : this.customHandler;
    }

    public HANDLER getSuperHandler() {
        return superHandler;
    }

    public MethodHotSwapHandler<HANDLER> setSuperHandler(HANDLER superHandler) {
        this.superHandler = superHandler;
        return this;
    }

}
