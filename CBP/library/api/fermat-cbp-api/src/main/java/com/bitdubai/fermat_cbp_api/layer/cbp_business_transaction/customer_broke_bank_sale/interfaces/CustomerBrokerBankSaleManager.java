package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_sale.interfaces;


import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_sale.exceptions.CantCreateCustomerBrokerBankSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_sale.exceptions.CantGetCustomerBrokerBankSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_sale.exceptions.CantUpdateStatusCustomerBrokerBankSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_sale.interfaces.CustomerBrokerBankSale;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface CustomerBrokerBankSaleManager {

    List<CustomerBrokerBankSale> getAllCustomerBrokerBankSaleFromCurrentDeviceUser() throws CantGetCustomerBrokerBankSaleException;

    CustomerBrokerBankSale createCustomerBrokerBankSale(
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
    ) throws CantCreateCustomerBrokerBankSaleException;

    void updateStatusCustomerBrokerBankSale(final UUID transactionId,final BusinessTransactionStatus transactionStatus) throws CantUpdateStatusCustomerBrokerBankSaleException;

}
