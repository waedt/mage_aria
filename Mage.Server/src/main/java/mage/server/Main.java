/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.server;

import mage.cards.repository.CardScanner;
import mage.game.match.MatchType;
import mage.game.tournament.TournamentType;
import mage.interfaces.MageServer;
import mage.remote.Connection;
import mage.server.draft.CubeFactory;
import mage.server.game.DeckValidatorFactory;
import mage.server.game.GameFactory;
import mage.server.game.PlayerFactory;
import mage.server.tournament.TournamentFactory;
import mage.server.util.ConfigSettings;
import mage.server.util.PluginClassLoader;
import mage.server.util.ServerMessagesUtil;
import mage.server.util.SystemUtil;
import mage.server.util.config.GamePlugin;
import mage.server.util.config.Plugin;
import mage.utils.MageVersion;
import org.apache.log4j.Logger;
import org.jboss.remoting.*;
import org.jboss.remoting.callback.InvokerCallbackHandler;
import org.jboss.remoting.callback.ServerInvokerCallbackHandler;
import org.jboss.remoting.transport.Connector;
import org.jboss.remoting.transport.socket.SocketWrapper;
import org.jboss.remoting.transporter.TransporterClient;
import org.jboss.remoting.transporter.TransporterServer;
import org.w3c.dom.Element;

import javax.management.MBeanServer;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import org.jboss.remoting.transport.bisocket.BisocketServerInvoker;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);
    private static final MageVersion version = new MageVersion(1, 3, 0, MageVersion.MAGE_VERSION_INFO);

    private static final String testModeArg = "-testMode=";
    private static final String fastDBModeArg = "-fastDbMode=";
    private static final String adminPasswordArg = "-adminPassword=";
    private static final String pluginFolder = "plugins";


    public static PluginClassLoader classLoader = new PluginClassLoader();
    public static TransporterServer server;
    protected static boolean testMode;
    protected static boolean fastDbMode;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        logger.info("Starting MAGE server version " + version);
        logger.info("Logging level: " + logger.getEffectiveLevel());

        String adminPassword = "";
        for (String arg: args) {
            if (arg.startsWith(testModeArg)) {
                testMode = Boolean.valueOf(arg.replace(testModeArg, ""));
            }
            else if (arg.startsWith(adminPasswordArg)) {
                adminPassword = arg.replace(adminPasswordArg, "");
                adminPassword = SystemUtil.sanitize(adminPassword);
            }
            else if (arg.startsWith(fastDBModeArg)) {
                fastDbMode = Boolean.valueOf(arg.replace(fastDBModeArg, ""));
            }
        }
        
        logger.info("Loading cards...");
        if (fastDbMode) {
            CardScanner.scanned = true;
        } else {
            CardScanner.scan();
        }
        logger.info("Done.");

        deleteSavedGames();
        ConfigSettings config = ConfigSettings.getInstance();
        for (GamePlugin plugin: config.getGameTypes()) {
            GameFactory.getInstance().addGameType(plugin.getName(), loadGameType(plugin), loadPlugin(plugin));
        }
        for (GamePlugin plugin: config.getTournamentTypes()) {
            TournamentFactory.getInstance().addTournamentType(plugin.getName(), loadTournamentType(plugin), loadPlugin(plugin));
        }
        for (Plugin plugin: config.getPlayerTypes()) {
            PlayerFactory.getInstance().addPlayerType(plugin.getName(), loadPlugin(plugin));
        }
        for (Plugin plugin: config.getDraftCubes()) {
            CubeFactory.getInstance().addDraftCube(plugin.getName(), loadPlugin(plugin));
        }
        for (Plugin plugin: config.getDeckTypes()) {
            DeckValidatorFactory.getInstance().addDeckType(plugin.getName(), loadPlugin(plugin));
        }

        logger.info("Config - max seconds idle: " + config.getMaxSecondsIdle());
        logger.info("Config - max game threads: " + config.getMaxGameThreads());
        logger.info("Config - max AI opponents: " + config.getMaxAiOpponents());
        logger.info("Config - min user name l.: " + config.getMinUserNameLength());
        logger.info("Config - max user name l.: " + config.getMaxUserNameLength());
        logger.info("Config - save game active: " + (config.isSaveGameActivated() ? "True":"false"));
        
        logger.info("Config - backlog size    : " + config.getBacklogSize());
        logger.info("Config - lease period    : " + config.getLeasePeriod());
        logger.info("Config - max pool size   : " + config.getMaxPoolSize());
        logger.info("Config - num accp.threads: " + config.getNumAcceptThreads());
        logger.info("Config - second.bind port: " + config.getSecondaryBindPort());
        
        Connection connection = new Connection("&maxPoolSize=" + config.getMaxPoolSize());
        connection.setHost(config.getServerAddress());
        connection.setPort(config.getPort());
        try {
            // Parameter: serializationtype => jboss
            InvokerLocator serverLocator = new InvokerLocator(connection.getURI());                
            if (!isAlreadyRunning(serverLocator)) {
                server = new MageTransporterServer(serverLocator, new MageServerImpl(adminPassword, testMode), MageServer.class.getName(), new MageServerInvocationHandler());
                server.start();
                logger.info("Started MAGE server - listening on " + connection.toString());
                
                if (testMode) {
                    logger.info("MAGE server running in test mode");
                }
                initStatistics();
            }
            else {
                logger.fatal("Unable to start MAGE server - another server is already started");
            }
        } catch (IOException ex) {
            logger.fatal("Failed to start server - " + connection.toString(), ex);
        } catch (Exception ex) {
            logger.fatal("Failed to start server - " + connection.toString(), ex);
        }

    }

    static void initStatistics() {
        ServerMessagesUtil.getInstance().setStartDate(System.currentTimeMillis());
    }

    static boolean isAlreadyRunning(InvokerLocator serverLocator) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put(SocketWrapper.WRITE_TIMEOUT, "2000");
        metadata.put("generalizeSocketException", "true");
        try {
            MageServer testServer = (MageServer) TransporterClient.createTransporterClient(serverLocator.getLocatorURI(), MageServer.class, metadata);
            if (testServer != null) {
                testServer.getServerState();
                return true;
            }
        } catch (Throwable t) {
            // assume server is not running
        }
        return false;
    }

    static class ClientConnectionListener implements ConnectionListener {
        @Override
        public void handleConnectionException(Throwable throwable, Client client) {
            Session session = SessionManager.getInstance().getSession(client.getSessionId());
            if (session != null) {
                StringBuilder sessionInfo = new StringBuilder();
                User user = UserManager.getInstance().getUser(session.getUserId());
                if (user != null) {
                    sessionInfo.append(user.getName());
                } else {
                    sessionInfo.append("[user missing] ");
                }
                sessionInfo.append(" at ").append(session.getHost()).append(" sessionId: ").append(session.getId());
                if (throwable instanceof ClientDisconnectedException) {
                    // Seems like the random diconnects from public server land here and should not be handled as explicit disconnects
                    // So it should be possible to reconnect to server and continue games if DisconnectReason is set to LostConnection
                    //SessionManager.getInstance().disconnect(client.getSessionId(), DisconnectReason.Disconnected);                    
                    SessionManager.getInstance().disconnect(client.getSessionId(), DisconnectReason.LostConnection);
                    logger.info("CLIENT DISCONNECTED - " + sessionInfo, throwable);
                    if (logger.isDebugEnabled()) {
                        throwable.printStackTrace();
                    }                    
                }
                else {                    
                    SessionManager.getInstance().disconnect(client.getSessionId(), DisconnectReason.LostConnection);                    
                    logger.info("CONNECTION LOST - " + sessionInfo, throwable);
                    if (logger.isDebugEnabled()) {
                        if (throwable == null) {
                            logger.debug("Lease expired");
                        } else {
                            throwable.printStackTrace();
                        }
                    }                    
                }
            }
        }
    }

    static class MageTransporterServer extends TransporterServer {

        protected Connector connector;

        public MageTransporterServer(InvokerLocator locator, Object target, String subsystem, MageServerInvocationHandler callback) throws Exception {
            super(locator, target, subsystem);
            connector.addInvocationHandler("callback", callback);
            connector.setLeasePeriod(ConfigSettings.getInstance().getLeasePeriod());
            connector.addConnectionListener(new ClientConnectionListener());
        }

        public Connector getConnector() throws Exception {
            return connector;
        }

        @Override
        protected Connector getConnector(InvokerLocator locator, Map config, Element xmlConfig) throws Exception {
            Connector c = super.getConnector(locator, config, xmlConfig);
            this.connector = c;
            return c;
        }
    }

    static class MageServerInvocationHandler implements ServerInvocationHandler {

        @Override
        public void setMBeanServer(MBeanServer server) {
            
        }

        @Override
        public void setInvoker(ServerInvoker invoker) {            
            ((BisocketServerInvoker) invoker).setSecondaryBindPort(ConfigSettings.getInstance().getSecondaryBindPort());
            ((BisocketServerInvoker) invoker).setBacklog(ConfigSettings.getInstance().getBacklogSize());                        
            ((BisocketServerInvoker) invoker).setNumAcceptThreads(ConfigSettings.getInstance().getNumAcceptThreads());            
        }

        @Override
        public Object invoke(final InvocationRequest invocation) throws Throwable {
            String sessionId = invocation.getSessionId();
            Map map = invocation.getRequestPayload();
            String host;
            if (map != null) {
                InetAddress clientAddress = (InetAddress) invocation.getRequestPayload().get(Remoting.CLIENT_ADDRESS);
                host = clientAddress.getHostAddress();
            } else {
                host = "localhost";
            }
            SessionManager.getInstance().getSession(sessionId).setHost(host);
            return null;
        }

        @Override
        public void addListener(InvokerCallbackHandler callbackHandler) {
            ServerInvokerCallbackHandler handler = (ServerInvokerCallbackHandler) callbackHandler;
            try {
                String sessionId = handler.getClientSessionId();
                SessionManager.getInstance().createSession(sessionId, callbackHandler);
            } catch (Throwable ex) {
                logger.fatal("", ex);
            }
        }

        @Override
        public void removeListener(InvokerCallbackHandler callbackHandler) {
            ServerInvokerCallbackHandler handler = (ServerInvokerCallbackHandler) callbackHandler;
            String sessionId = handler.getClientSessionId();
            SessionManager.getInstance().disconnect(sessionId, DisconnectReason.Disconnected);
        }

    }

    private static Class<?> loadPlugin(Plugin plugin) {
        try {
            classLoader.addURL(new File(pluginFolder + "/" + plugin.getJar()).toURI().toURL());
            logger.debug("Loading plugin: " + plugin.getClassName());
            return Class.forName(plugin.getClassName(), true, classLoader);
        } catch (ClassNotFoundException ex) {
            logger.warn(new StringBuilder("Plugin not Found: ").append(plugin.getClassName()).append(" - ").append(plugin.getJar()).append(" - check plugin folder"), ex);
        } catch (MalformedURLException ex) {
            logger.fatal("Error loading plugin " + plugin.getJar(), ex);
        }
        return null;
    }

    private static MatchType loadGameType(GamePlugin plugin) {
        try {
            classLoader.addURL(new File(pluginFolder + "/" + plugin.getJar()).toURI().toURL());
            logger.debug("Loading game type: " + plugin.getClassName());
            return (MatchType) Class.forName(plugin.getTypeName(), true, classLoader).newInstance();
        } catch (ClassNotFoundException ex) {
            logger.warn("Game type not found:" + plugin.getJar() + " - check plugin folder", ex);
        } catch (Exception ex) {
            logger.fatal("Error loading game type " + plugin.getJar(), ex);
        }
        return null;
    }

    private static TournamentType loadTournamentType(GamePlugin plugin) {
        try {
            classLoader.addURL(new File(pluginFolder + "/" + plugin.getJar()).toURI().toURL());
            logger.debug("Loading tournament type: " + plugin.getClassName());
            return (TournamentType) Class.forName(plugin.getTypeName(), true, classLoader).newInstance();
        } catch (ClassNotFoundException ex) {
            logger.warn("Tournament type not found:" + plugin.getName() + " / "+ plugin.getJar() + " - check plugin folder", ex);
        } catch (Exception ex) {
            logger.fatal("Error loading game type " + plugin.getJar(), ex);
        }
        return null;
    }

    private static void deleteSavedGames() {
        File directory = new File("saved/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File[] files = directory.listFiles(
            new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".game");
                }
            }
        );
        for (File file : files)
        {
            file.delete();
        }
    }

    public static MageVersion getVersion() {
        return version;
    }

    public static boolean isTestMode() {
        return testMode;
    }
}
