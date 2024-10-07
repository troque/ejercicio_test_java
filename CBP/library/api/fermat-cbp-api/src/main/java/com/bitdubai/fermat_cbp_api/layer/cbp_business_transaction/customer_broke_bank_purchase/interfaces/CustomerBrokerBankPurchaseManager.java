package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_purchase.interfaces;


import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_purchase.exceptions.CantCreateCustomerBrokerBankPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_purchase.exceptions.CantGetCustomerBrokeBankPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_purchase.exceptions.CantUpdateStatusCustomerBrokeBankPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_purchase.interfaces.CustomerBrokerBankPurchase;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface CustomerBrokerBankPurchaseManager {

    List<CustomerBrokerBankPurchase> getAllCustomerBrokerBankPurchaseFromCurrentDeviceUser() throws CantGetCustomerBrokeBankPurchaseException;

    CustomerBrokerBankPurchase createCustomerBrokerBankPurchase(
             final UUID contractId
            ,final String publicKeyBroker
            ,final String publicKeyCustomer
            ,final UUID paymentTransactionId
            ,final CurrencyType paymentCurrency
            ,final CurrencyType merchandiseCurrency
            ,final float merchandiseAmount
            ,final UUID executionTransactionId
            ,final BankCurrencyType bankCurrencyType
            ,final BankOperationType bankOperationType
    ) throws CantCreateCustomerBrokerBankPurchaseException;

    void updateStatusCustomerBrokerBankPurchase(final UUID transactionId,final BusinessTransactionStatus transactionStatus) throws CantUpdateStatusCustomerBrokeBankPurchaseException;

}
