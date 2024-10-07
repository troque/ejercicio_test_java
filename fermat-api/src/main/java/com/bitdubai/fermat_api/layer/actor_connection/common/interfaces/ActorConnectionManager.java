package com.bitdubai.fermat_api.layer.actor_connection.common.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.abstract_classes.ActorIdentity;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantBuildActorIdentityException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnectionManager</code>
 * represents all the basic functionality of an actor connection plug-in.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/11/2015.
 */
public interface ActorConnectionManager {

    /**
     * Through the method <code>buildActorIdentity</code> we can construct a ActorIdentity Instance.
     * Here we'll make all the necessary validations in the information to send/expose, whatever.
     *
     * @param publicKey    the public key of the actor identity.
     * @param actorType    the actor type of the actor identity.
     *
     * @return an instance of ActorIdentity with the information of the Actor Identity.
     *
     * @throws CantBuildActorIdentityException if something goes wrong.
     */
    ActorIdentity buildActorIdentity(final String publicKey,
                                     final Actors actorType) throws CantBuildActorIdentityException;

    /**
     * Through the method <code>getSearch</code> we can get a new instance of Actor Connection Search.
     * This Actor Connection search provides all the necessary functionality to make an Actor Connection Search.
     *
     * @return a ActorConnectionSearch instance.
     */
    ActorConnectionSearch getSearch(final ActorIdentity actorIdentitySearching);

    /**
     * Through the method <code>requestConnection</code> we can request an actor for a connection.
     * When we're connected with an actor, we're enabled to interact with him.
     *
     * @param actorIdentityRequestingConnection    the actor identity who requests the connection with another actor.
     * @param actorConnectionPublicKey             the public key of the actor which is connected.
     * @param actorConnectionActorType             the actor type of the actor which is connected.
     * @param actorConnectionAlias                 the alias of the actor which is connected.
     * @param actorConnectionImage                 the image of the actor which is connected.
     *
     * @throws CantRequestActorConnectionException if something goes wrong.
     */
    void requestConnection(final ActorIdentity actorIdentityRequestingConnection,
                           final String        actorConnectionPublicKey         ,
                           final Actors        actorConnectionActorType         ,
                           final String        actorConnectionAlias             ,
                           final byte[]        actorConnectionImage             ) throws CantRequestActorConnectionException;

    /**
     * Through the method <code>disconnect</code> we can disconnect from an actor.
     * If we don't want to interact anymore with the other actor, you can disconnect from him.
     *
     * @param connectionId   id of the actor connection to be disconnected.
     *
     * @throws CantDisconnectFromActorException   if something goes wrong.
     * @throws ActorConnectionNotFoundException   if we can't find an actor connection with this connection id.
     */
    void disconnect(final UUID connectionId) throws CantDisconnectFromActorException,
                                                    ActorConnectionNotFoundException;

    /**
     * Through the method <code>denyConnection</code> we can deny an actor connection.
     * The actor identity can deny a connection request if he doesn't trust in the counterpart.
     *
     * @param connectionId   id of the actor connection to be denied.
     *
     * @throws CantDenyActorConnectionRequestException   if something goes wrong.
     * @throws ActorConnectionNotFoundException          if we can't find an actor connection with this connection id.
     */
    void denyConnection(final UUID connectionId) throws CantDenyActorConnectionRequestException,
                                                        ActorConnectionNotFoundException       ;

    /**
     * Through the method <code>cancelConnection</code> we can cancel a connection request sent.
     * The actor could cancel a connection request previously sent.
     * We've to check the state of the connection here.
     *
     * @param connectionId   id of the actor connection request to be canceled.
     *
     * @throws CantCancelActorConnectionRequestException   if something goes wrong.
     * @throws ActorConnectionNotFoundException            if we can't find an actor connection with this connection id.
     */
    void cancelConnection(final UUID connectionId) throws CantCancelActorConnectionRequestException,
                                                          ActorConnectionNotFoundException         ;

    /**
     * Through the method <code>acceptConnection</code> we can accept a received connection request.
     *
     * @param connectionId   id of the actor connection to be accepted.
     *
     * @throws CantAcceptActorConnectionRequestException   if something goes wrong.
     * @throws ActorConnectionNotFoundException            if we can't find an actor connection with this connection id.
     */
    void acceptConnection(final UUID connectionId) throws CantAcceptActorConnectionRequestException,
                                                          ActorConnectionNotFoundException         ;

}
