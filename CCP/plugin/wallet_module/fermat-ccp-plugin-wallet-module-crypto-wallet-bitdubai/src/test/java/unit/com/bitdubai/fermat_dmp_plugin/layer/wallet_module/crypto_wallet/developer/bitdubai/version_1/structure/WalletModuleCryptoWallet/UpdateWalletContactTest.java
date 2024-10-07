//package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;
//
//import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
//import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
//import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
//import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsRegistry;
//<<<<<<< HEAD:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/structure/WalletModuleCryptoWallet/UpdateWalletContactTest.java
//import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantUpdateWalletContactException;
//=======
//import com.bitdubai.fermat_ccp_api.layer.crypto_wallet.exceptions.CantUpdateWalletContactException;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2:CCP/plugin/wallet_module/fermat-ccp-plugin-wallet-module-crypto-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/wallet_module/crypto_wallet/developer/bitdubai/version_1/structure/WalletModuleCryptoWallet/UpdateWalletContactTest.java
//import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
//import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager;
//import ErrorManager;
//
//import junit.framework.TestCase;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.UUID;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.doThrow;
//
//@RunWith(MockitoJUnitRunner.class)
//public class UpdateWalletContactTest extends TestCase {
//
//    /**
//     * DealsWithErrors interface Mocked
//     */
//    @Mock
//    ErrorManager errorManager;
//
//    /**
//     * DealsWithCryptoAddressBook interface Mocked
//     */
//    @Mock
//    CryptoAddressBookManager cryptoAddressBookManager;
//
//    /**
//     * DealsWithWalletContacts interface Mocked
//     */
//    @Mock
//    WalletContactsManager walletContactsManager;
//
//
//    @Mock
//    WalletContactsRegistry walletContactsRegistry;
//
//    UUID contactId;
//    CryptoAddress receivedCryptoAddress;
//    String actorName;
//
//    CryptoWalletWalletModuleManager walletModuleCryptoWallet;
//
//    @Before
//    public void setUp() throws Exception {
//        doReturn(walletContactsRegistry).when(walletContactsManager).getWalletContactsRegistry();
//        contactId = UUID.randomUUID();
//        receivedCryptoAddress = new CryptoAddress("asdasdasd", CryptoCurrency.BITCOIN);
//        actorName = "Vigo Mortensen";
//        walletModuleCryptoWallet = new CryptoWalletWalletModuleManager();
//        walletModuleCryptoWallet.setErrorManager(errorManager);
//        walletModuleCryptoWallet.setCryptoAddressBookManager(cryptoAddressBookManager);
//        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
//        walletModuleCryptoWallet.initialize();
//    }
//
//    @Test
//    public void testUpdateWalletContact_Success() throws Exception {
//        walletModuleCryptoWallet.updateWalletContact(contactId, receivedCryptoAddress, actorName);
//    }
//
//    @Test(expected=CantUpdateWalletContactException.class)
//    public void testUpdateWalletContact_CantUpdateWalletContactException() throws Exception {
//        doThrow(new com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantUpdateWalletContactException())
//        .when(walletContactsRegistry).updateWalletContact(any(UUID.class), any(CryptoAddress.class), anyString());
//
//        walletModuleCryptoWallet.updateWalletContact(contactId, receivedCryptoAddress, actorName);
//    }
//}
