package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.providers.BtceProvider;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.BtceProvider;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.HTTPJson;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.Date;

/**
 * Created by francisco on 17/09/15.
 */
public class GetMarketPriceTest {

    BtceProvider btceProvider = new BtceProvider();
    private CryptoCurrency cryptoCurrencyTest;
    private FiatCurrency fiatCurrencyTest;
    private long time;

    @Test
    public void TestGetMarketPriceTest_successful() throws Exception{
        cryptoCurrencyTest= CryptoCurrency.getByCode("BTC");
        fiatCurrencyTest= FiatCurrency.getByCode("USD");
        double values=btceProvider.getMarketPrice(cryptoCurrencyTest,fiatCurrencyTest,time);
        System.out.println(values);
        Assertions.assertThat(values).isNotNull();
    }

}
