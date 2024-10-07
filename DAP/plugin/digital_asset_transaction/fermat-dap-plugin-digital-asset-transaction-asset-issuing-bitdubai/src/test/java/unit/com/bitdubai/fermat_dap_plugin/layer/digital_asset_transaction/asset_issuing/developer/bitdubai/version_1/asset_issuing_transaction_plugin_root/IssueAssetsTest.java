package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.asset_issuing_transaction_plugin_root;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.IntraActorCryptoTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.AssetIssuingDigitalAssetTransactionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.AssetIssuingTransactionManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetCryptoTransactionFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetIssuingVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.support.membermodification.MemberModifier;

import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 23/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class IssueAssetsTest {
    AssetIssuingDigitalAssetTransactionPluginRoot assetIssuingPluginRoot;
    AssetIssuingTransactionManager assetIssuingTransactionManager;
    DigitalAssetCryptoTransactionFactory digitalAssetCryptoTransactionFactory;

    UUID pluginId;

    @Mock
    ErrorManager errorManager;

    @Mock
    LogManager logManager;

    @Mock
    EventManager eventManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    FermatEventListener fermatEventListener1;
    @Mock
    FermatEventListener fermatEventListener2;
    @Mock
    FermatEventListener fermatEventListener3;
    @Mock
    FermatEventListener fermatEventListener4;
    @Mock
    FermatEventListener fermatEventListener5;

    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    AssetIssuerWalletManager assetIssuerWalletManager;

    @Mock
    ActorAssetIssuerManager actorAssetIssuerManager;

    @Mock
    CryptoVaultManager cryptoVaultManager;

    @Mock
    BitcoinWalletManager bitcoinWalletManager;

    @Mock
    AssetVaultManager assetVaultManager;

    @Mock
    CryptoAddressBookManager cryptoAddressBookManager;

    @Mock
    OutgoingIntraActorManager outgoingIntraActorManager;

    @Mock
    DeviceUserManager deviceUserManager;

    @Mock
    BitcoinNetworkManager bitcoinNetworkManager;

    @Mock
    AssetIssuingTransactionDao assetIssuingTransactionDao;

    String assetPublicKey;

    @Mock
    DigitalAssetContract digitalAssetContract;

    @Mock
    List<Resource> resources;

    @Mock
    IdentityAssetIssuer identityAssetIssuer;

    DigitalAsset digitalAsset;

    @Mock
    DigitalAssetIssuingVault digitalAssetIssuingVault;

    @Mock
    PluginTextFile pluginTextFile;

    BlockchainNetworkType blockchainNetworkType;

    @Mock
    BitcoinWalletBalance bitcoinWalletBalance;

    @Mock
    CryptoAddress genesisAddress;

    @Mock
    IntraActorCryptoTransactionManager intraActorCryptoTransactionManager;

    int assetsAmount;
    String walletPublicKey;
    String description;
    String name;
    String publicKey;
    String digitalAssetFileStoragePath;
    String digitalAssetFileName;

    @Mock
    DeviceUser deviceUser;

    String userPublicKey = "userPublicKey";

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();

        assetIssuingPluginRoot = new AssetIssuingDigitalAssetTransactionPluginRoot();
        assetIssuingPluginRoot.setId(pluginId);
        assetIssuingPluginRoot.setErrorManager(errorManager);
        assetIssuingPluginRoot.setLogManager(logManager);
        assetIssuingPluginRoot.setEventManager(eventManager);
        assetIssuingPluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
        assetIssuingPluginRoot.setPluginFileSystem(pluginFileSystem);
        assetIssuingPluginRoot.setAssetIssuerManager(assetIssuerWalletManager);
        assetIssuingPluginRoot.setActorAssetIssuerManager(actorAssetIssuerManager);
        assetIssuingPluginRoot.setCryptoVaultManager(cryptoVaultManager);
        assetIssuingPluginRoot.setBitcoinWalletManager(bitcoinWalletManager);
        assetIssuingPluginRoot.setAssetVaultManager(assetVaultManager);
        assetIssuingPluginRoot.setCryptoAddressBookManager(cryptoAddressBookManager);
        assetIssuingPluginRoot.setOutgoingIntraActorManager(outgoingIntraActorManager);
        assetIssuingPluginRoot.setDeviceUserManager(deviceUserManager);
        assetIssuingPluginRoot.setBitcoinNetworkManager(bitcoinNetworkManager);

        assetIssuingTransactionManager = new AssetIssuingTransactionManager(pluginId,
                cryptoVaultManager,
                bitcoinWalletManager,
                pluginDatabaseSystem,
                pluginFileSystem,
                errorManager,
                assetVaultManager,
                cryptoAddressBookManager,
                outgoingIntraActorManager);
        assetIssuingTransactionManager.setPluginId(pluginId);

        digitalAssetCryptoTransactionFactory = new DigitalAssetCryptoTransactionFactory(this.pluginId,
                this.cryptoVaultManager,
                this.bitcoinWalletManager,
                this.pluginDatabaseSystem,
                this.pluginFileSystem,
                this.assetVaultManager,
                this.cryptoAddressBookManager,
                this.outgoingIntraActorManager);
        digitalAssetCryptoTransactionFactory.setErrorManager(errorManager);
        digitalAssetCryptoTransactionFactory.setAssetIssuingTransactionDao(assetIssuingTransactionDao);
        digitalAssetCryptoTransactionFactory.setDigitalAssetIssuingVault(digitalAssetIssuingVault);

        assetsAmount = 1;
        walletPublicKey = "walletPublicKey";
        description = "description";
        name = "name";
        publicKey = "publicKey";
        digitalAssetFileStoragePath = "digital-asset-issuing/publicKey";
        digitalAssetFileName = "name.xml";

        digitalAsset = new DigitalAsset();
        digitalAsset.setIdentityAssetIssuer(identityAssetIssuer);
        digitalAsset.setPublicKey(publicKey);
        digitalAsset.setName(name);
        digitalAsset.setDescription(description);
        digitalAsset.setContract(digitalAssetContract);
        digitalAsset.setResources(resources);

        blockchainNetworkType = BlockchainNetworkType.REG_TEST;

        MemberModifier.field(AssetIssuingDigitalAssetTransactionPluginRoot.class, "assetIssuingTransactionManager").set(assetIssuingPluginRoot, assetIssuingTransactionManager);
        MemberModifier.field(AssetIssuingDigitalAssetTransactionPluginRoot.class, "digitalAssetIssuingVault").set(assetIssuingPluginRoot, digitalAssetIssuingVault);
        MemberModifier.field(AssetIssuingTransactionManager.class, "digitalAssetCryptoTransactionFactory").set(assetIssuingTransactionManager, digitalAssetCryptoTransactionFactory);
        MemberModifier.field(DigitalAssetCryptoTransactionFactory.class, "digitalAsset").set(digitalAssetCryptoTransactionFactory, digitalAsset);
        MemberModifier.field(DigitalAssetCryptoTransactionFactory.class, "bitcoinWalletBalance").set(digitalAssetCryptoTransactionFactory, bitcoinWalletBalance);

        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {
        when(pluginFileSystem.createTextFile(pluginId, digitalAssetFileStoragePath, digitalAssetFileName, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT)).thenReturn(pluginTextFile);
        doNothing().when(assetIssuingTransactionDao).persistDigitalAsset(
                digitalAsset.getPublicKey(),
                this.digitalAssetFileStoragePath,
                this.assetsAmount,
                this.blockchainNetworkType,
                this.walletPublicKey);
        when(assetVaultManager.getNewAssetVaultCryptoAddress(blockchainNetworkType)).thenReturn(genesisAddress);
        when(outgoingIntraActorManager.getTransactionManager()).thenReturn(intraActorCryptoTransactionManager);

        when(deviceUserManager.getLoggedInDeviceUser()).thenReturn(deviceUser);
        when(deviceUser.getPublicKey()).thenReturn(userPublicKey);
    }

    @Test
    public void test_OK() throws Exception {
        assetIssuingPluginRoot.issueAssets(digitalAsset, assetsAmount, walletPublicKey, blockchainNetworkType);
    }

    @Test
    public void test_Throws_Exception() throws Exception {
        catchException(assetIssuingPluginRoot).issueAssets(digitalAsset, 1, "publicKey", null);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantIssueDigitalAssetsException.class);
    }
}
