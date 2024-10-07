/*
 * @#WebSocketNodeChannelServerEndpoint.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels;

import org.jboss.logging.Logger;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketNodeChannelServerEndpoint</code>
 * represent the the communication chanel between nodes<p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@ServerEndpoint("/fermat/node-channel")
public class WebSocketNodeChannelServerEndpoint {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(WebSocketNodeChannelServerEndpoint.class.getName());


    @OnOpen
    public void onConnect(Session session) throws IOException {
        LOG.info("New connection stablished: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {

        LOG.info("Closed connection: " + session.getId() + " message = " + message + ")");

        for (Session s : session.getOpenSessions()) {
            s.getAsyncRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(CloseReason closeReason, Session session) {

        LOG.info("Closed connection: " + session.getId() + "(" + closeReason.getReasonPhrase() + ")");

    }

}
