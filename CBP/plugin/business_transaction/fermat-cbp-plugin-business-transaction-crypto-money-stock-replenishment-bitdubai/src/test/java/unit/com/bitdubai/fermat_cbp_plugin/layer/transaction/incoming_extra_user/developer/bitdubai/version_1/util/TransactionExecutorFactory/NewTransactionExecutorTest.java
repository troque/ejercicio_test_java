//package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util.TransactionExecutorFactory;
//
//import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
//<<<<<<< HEAD
//import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantLoadWalletException;
//=======
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
//import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
//import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.TransactionExecutor;
//import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.executors.BitcoinBasicWalletTransactionExecutor;
//import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util.TransactionExecutorFactory;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import static org.mockito.Mockito.*;
//import static com.googlecode.catchexception.CatchException.*;
///**
// * Created by jorgegonzalez on 2015.07.08..
// */
//@RunWith(MockitoJUnitRunner.class)
//public class NewTransactionExecutorTest {
//
//    @Mock
//    private ActorAddressBookManager mockActorAddressBookManager;
//    @Mock
//    private BitcoinWalletManager mockBitcoinWalletManager;
//    @Mock
//    private BitcoinWalletWallet mockBitcoinWallet;
//
//    private TransactionExecutorFactory testExecutorFactory;
//    private TransactionExecutor testExecutor;
//
//
//    @Test
//    public void newTransactionExecutor_PlatformWalletTypeNotSupported_TransactionExecutorCreated() throws Exception{
//        when(mockBitcoinWalletManager.loadWallet(anyString())).thenReturn(mockBitcoinWallet);
//
//        testExecutorFactory = new TransactionExecutorFactory(mockBitcoinWalletManager, mockActorAddressBookManager);
//        testExecutor = testExecutorFactory.newTransactionExecutor(ReferenceWallet.COMPOSITE_WALLET_MULTI_ACCOUNT, "replace_by_wallet_public_key");
//        assertThat(testExecutor).isNull();
//    }
//
//    @Test
//    public void newTransactionExecutor_WalletRecognizedByManager_TransactionExecutorCreated() throws Exception{
//        when(mockBitcoinWalletManager.loadWallet(anyString())).thenReturn(mockBitcoinWallet);
//
//        testExecutorFactory = new TransactionExecutorFactory(mockBitcoinWalletManager, mockActorAddressBookManager);
//        testExecutor = testExecutorFactory.newTransactionExecutor(ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET, "replace_by_wallet_public_key");
//        assertThat(testExecutor)
//                .isNotNull()
//                .isInstanceOf(BitcoinBasicWalletTransactionExecutor.class);
//    }
//
//    @Test
//    public void newTransactionExecutor_WalletNotRecognizedByManager_ThrowsCantLoadWalletException() throws Exception{
//        when(mockBitcoinWalletManager.loadWallet(anyString())).thenThrow(new CantLoadWalletException("MOCK", null, null, null));
//
//        testExecutorFactory = new TransactionExecutorFactory(mockBitcoinWalletManager, mockActorAddressBookManager);
//        catchException(testExecutorFactory).newTransactionExecutor(ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET, "replace_by_wallet_public_key");
//
//        assertThat(caughtException())
//                .isNotNull()
//                .isInstanceOf(CantLoadWalletException.class);
//    }
//
//}
