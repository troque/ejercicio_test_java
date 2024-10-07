package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListCryptoBrokersException;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerSearch</code>
 * expose all the methods to search a Crypto Broker.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public interface CryptoBrokerSearch {

    /**
     * Through the method <code>setAlias</code> we can set the alias of the broker to search.
     *
     * @param alias of the broker.
     */
    void setAlias(final String alias);

    /**
     * Through the method <code>getResult</code> we can get the results of the search,
     * Like we're not setting max and offset we will return all the crypto brokers that match
     * with the parameters set.
     *
     * @return a list of crypto brokers with their information.
     *
     * @throws CantListCryptoBrokersException  if something goes wrong.
     */
    List<CryptoBrokerData> getResult() throws CantListCryptoBrokersException;

    /**
     * Through the method <code>getResult</code> we can get the results of the search,
     * filtered by the parameters set.
     * We'll receive at most the quantity of @max set. If null by default the max will be 100.
     *
     * @param max   maximum quantity of results expected.
     *
     * @return a list of crypto brokers with their information.
     *
     * @throws CantListCryptoBrokersException  if something goes wrong.
     */
    List<CryptoBrokerData> getResult(final Integer max) throws CantListCryptoBrokersException;

    /**
     * Through the method <code>resetFilters</code> you can reset the filters set,
     */
    void resetFilters();
}
