package org.mage.test.load;

import java.util.UUID;
import mage.interfaces.MageClient;
import mage.interfaces.callback.CallbackClient;
import mage.interfaces.callback.ClientCallback;
import mage.remote.Session;
import mage.utils.MageVersion;
import org.apache.log4j.Logger;

/**
 * For tests only
 *
 * @author noxx
 */
public class SimpleMageClient implements MageClient {

    private final UUID clientId;
    private static final MageVersion version = new MageVersion(1, 3, 0, MageVersion.MAGE_VERSION_INFO);

    private static final transient Logger log = Logger.getLogger(SimpleMageClient.class);

    private final CallbackClient callbackClient;

    public SimpleMageClient() {
        clientId = UUID.randomUUID();
        callbackClient = new LoadCallbackClient();
    }

    @Override
    public MageVersion getVersion() {
        return version;
    }

    @Override
    public void connected(String message) {
        // do nothing
    }

    @Override
    public void disconnected(boolean errorCall) {
        // do nothing
    }

    @Override
    public void showMessage(String message) {
        log.info(message);
    }

    @Override
    public void showError(String message) {
        log.error(message);
    }

    @Override
    public void processCallback(ClientCallback callback) {
        callbackClient.processCallback(callback);
    }

    public void setSession(Session session) {
        ((LoadCallbackClient)callbackClient).setSession(session);
    }

    public boolean isGameOver() {
        return ((LoadCallbackClient)callbackClient).isGameOver();
    }
}
