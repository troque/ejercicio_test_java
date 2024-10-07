package com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_sale.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_sale.exceptions.CantGetRequestListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_sale.exceptions.CantRequestCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_sale.exceptions.FailedToRejectTheRequestSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_sale.exceptions.RequestUnexpectedErrorException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 17/9/15.
 */

public interface RequestCustomerBrokerSaleManager {

    List<RequestCustomerBrokerSale> getRequestSaleSent(String walletPublicKey) throws CantGetRequestListException;

    List<RequestCustomerBrokerSale> getReceivedRequestSale(String walletPublicKey) throws CantGetRequestListException;

    public void sendRequestSale(String senderWalletPublicKey,
                                    String requestSenderPublicKey,
                                    String requestDestinationPublicKey,
                                    String requestDescription,
                                    CryptoAddress addressToSendThePayment,
                                    long cryptoAmount) throws RequestUnexpectedErrorException;

    public void rejectRequestSale(UUID requestId) throws FailedToRejectTheRequestSaleException;

    RequestCustomerBrokerSale createRequestCustomerBrokerSale(String senderWalletPublicKey,
                                                                      String requestSenderPublicKey,
                                                                      String requestDestinationPublicKey,
                                                                      String requestDescription,
                                                                      CryptoAddress addressToSendThePayment,
                                                                      long cryptoAmount) throws CantRequestCustomerBrokerSaleException;
}
