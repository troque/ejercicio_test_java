package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.CryptoCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.FiatCurrencyNotSupportedException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetHistoricalExchangeRateException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetMarketPriceException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.HistoricalExchangeRateNotFoundException;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndexProvider</code>
 * represent the basic functionality of a Crypto Index Provider.<p/>
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface CryptoIndexProvider {

    double getMarketPrice(CryptoCurrency cryptoCurrency,
                          FiatCurrency fiatCurrency,
                          long time) throws FiatCurrencyNotSupportedException,
            CryptoCurrencyNotSupportedException,
            CantGetMarketPriceException;

   double getHistoricalExchangeRate(CryptoCurrency cryptoCurrency,
                                              FiatCurrency fiatCurrency,
                                              long time)
              throws FiatCurrencyNotSupportedException,
              CryptoCurrencyNotSupportedException,
              CantGetHistoricalExchangeRateException,
              HistoricalExchangeRateNotFoundException;
}
