package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AppropriationStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.CantLoadAssetAppropriationTransactionListException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces.AssetAppropriationTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.AssetUserWalletTransactionRecordWrapper;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.AssetAppropriationDigitalAssetTransactionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.exceptions.CantLoadAssetAppropriationEventListException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.database.AssetAppropriationDAO;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.functional.AssetAppropriationVault;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class AssetAppropriationMonitorAgent implements Agent {

    //VARIABLE DECLARATION
    private ServiceStatus status;

    {
        this.status = ServiceStatus.CREATED;
    }

    private final ErrorManager errorManager;
    private final LogManager logManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final AssetAppropriationVault assetVault;
    private final UUID pluginId;
    private final AssetVaultManager assetVaultManager;
    private final AssetUserWalletManager assetUserWalletManager;
    private final BitcoinNetworkManager bitcoinNetworkManager;
    private final CryptoAddressBookManager cryptoAddressBookManager;
    private final CryptoVaultManager cryptoVaultManager;
    private final IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    private AppropriationAgent appropriationAgent;
    //VARIABLES ACCESSED BY AGENT INNER CLASS.
    //NEEDS TO BE VOLATILE SINCE THEY'RE BEING USED ON ANOTHER THREAD.
    //I NEED THREAD TO NOTICE ASAP.
    private volatile CountDownLatch latch;

    //CONSTRUCTORS


    public AssetAppropriationMonitorAgent(AssetAppropriationVault assetVault,
                                          PluginDatabaseSystem pluginDatabaseSystem,
                                          LogManager logManager,
                                          ErrorManager errorManager,
                                          UUID pluginId,
                                          AssetVaultManager assetVaultManager,
                                          AssetUserWalletManager assetUserWalletManager,
                                          BitcoinNetworkManager bitcoinNetworkManager,
                                          CryptoAddressBookManager cryptoAddressBookManager,
                                          CryptoVaultManager cryptoVaultManager,
                                          IntraWalletUserIdentityManager intraWalletUserIdentityManager) throws CantSetObjectException {
        this.assetVault = assetVault;
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.logManager = Validate.verifySetter(logManager, "logManager is null");
        this.errorManager = Validate.verifySetter(errorManager, "errorManager is null");
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
        this.assetVaultManager = Validate.verifySetter(assetVaultManager, "assetVaultManager is null");
        this.assetUserWalletManager = Validate.verifySetter(assetUserWalletManager, "assetUserWalletManager is null");
        this.bitcoinNetworkManager = Validate.verifySetter(bitcoinNetworkManager, "bitcoinNetworkManager is null");
        this.cryptoAddressBookManager = Validate.verifySetter(cryptoAddressBookManager, "cryptoAddressBookManager is null");
        this.cryptoVaultManager = Validate.verifySetter(cryptoVaultManager, "cryptoVaultManager is null");
        this.intraWalletUserIdentityManager = Validate.verifySetter(intraWalletUserIdentityManager, "intraWalletUserIdentityManager is null");

    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            logManager.log(AssetAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: starting...", null, null);
            latch = new CountDownLatch(1);

            appropriationAgent = new AppropriationAgent();
            Thread eventThread = new Thread(appropriationAgent);
            eventThread.start();
        } catch (Exception e) {
            throw new CantStartAgentException();
        }
        this.status = ServiceStatus.STARTED;
        logManager.log(AssetAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: successfully started...", null, null);
    }

    @Override
    public void stop() {
        logManager.log(AssetAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: stopping...", null, null);
        appropriationAgent.stopAgent();
        try {
            latch.await(); //WAIT UNTIL THE LAST RUN FINISH
        } catch (InterruptedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
        }
        appropriationAgent = null; //RELEASE RESOURCES.
        logManager.log(AssetAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: successfully stopped...", null, null);
        this.status = ServiceStatus.STOPPED;
    }

    public boolean isMonitorAgentActive() {
        return status == ServiceStatus.STARTED;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
    private class AppropriationAgent implements Runnable {

        private volatile boolean agentRunning;
        private static final int WAIT_TIME = 5 * 1000; //SECONDS

        public AppropriationAgent() {
            startAgent();
        }

        @Override
        public void run() {
            while (agentRunning) {
                try {
                    doTheMainTask();
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    /*If this happen there's a chance that the information remains
                    in a corrupt state. That probably would be fixed in a next run.
                    */
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                } catch (Exception e) {
                    e.printStackTrace();
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }

            latch.countDown();
        }

        private void doTheMainTask() {
            try (AssetAppropriationDAO dao = new AssetAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault)) {

                eventMonitoring(dao);

                statusMonitoring(dao);

            } catch (InvalidParameterException | CantRegisterDebitException | CantLoadWalletException | CantGetCryptoTransactionException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            } catch (CantLoadAssetAppropriationEventListException | CantGetTransactionsException | CantLoadAssetAppropriationTransactionListException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            } catch (RecordsNotFoundException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private void eventMonitoring(AssetAppropriationDAO dao) throws Exception {
            for (String eventId : dao.getPendingActorAssetUserEvents()) {
                switch (dao.getEventTypeById(eventId)) {
                    //TODO CHANGE THESE EVENTS TO THE NEW ONES.
                    case INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER:
                        for (AssetAppropriationTransactionRecord record : dao.getTransactionsForStatus(AppropriationStatus.BITCOINS_SENT)) {
                            if (record.genesisTransaction().equals("-")) {
                                //If for some reason it wasn't updated then the bitcoins wasn't sent.
                                dao.updateTransactionStatusCryptoAddressObtained(record.transactionRecordId());
                                continue;
                            }
                            for (CryptoTransaction cryptoTransaction : bitcoinNetworkManager.getCryptoTransaction(record.genesisTransaction())) {
                                if (cryptoTransaction.getCryptoStatus() == CryptoStatus.ON_CRYPTO_NETWORK) {
                                    AssetUserWallet userWallet = assetUserWalletManager.loadAssetUserWallet(record.userWalletPublicKey());
                                    AssetUserWalletBalance balance = userWallet.getBookBalance(BalanceType.AVAILABLE);
                                    AssetUserWalletTransactionRecordWrapper walletRecord = new AssetUserWalletTransactionRecordWrapper(record.digitalAsset(),
                                            cryptoTransaction,
                                            record.digitalAsset().getPublicKey(),
                                            Actors.DAP_ASSET_USER,
                                            record.addressTo().getAddress(),
                                            Actors.EXTRA_USER);
                                    balance.debit(walletRecord, BalanceType.AVAILABLE);
                                    dao.updateTransactionStatusAssetDebited(record.transactionRecordId());
                                }
                            }
                        }
                        dao.updateEventStatus(EventStatus.NOTIFIED, eventId);
                        break;

                    case INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER:
                        for (AssetAppropriationTransactionRecord record : dao.getTransactionsForStatus(AppropriationStatus.ASSET_DEBITED)) {
                            for (CryptoTransaction cryptoTransaction : bitcoinNetworkManager.getCryptoTransaction(record.genesisTransaction())) {
                                if (cryptoTransaction.getCryptoStatus() == CryptoStatus.ON_BLOCKCHAIN) {
                                    AssetUserWallet userWallet = assetUserWalletManager.loadAssetUserWallet(record.userWalletPublicKey());
                                    AssetUserWalletBalance balance = userWallet.getBookBalance(BalanceType.BOOK);
                                    AssetUserWalletTransactionRecordWrapper walletRecord = new AssetUserWalletTransactionRecordWrapper(record.digitalAsset(),
                                            cryptoTransaction,
                                            record.digitalAsset().getPublicKey(),
                                            Actors.DAP_ASSET_USER,
                                            record.addressTo().getAddress(),
                                            Actors.EXTRA_USER);
                                    balance.debit(walletRecord, BalanceType.BOOK);
                                    dao.completeAppropriationSuccessful(record.transactionRecordId());
                                }
                            }
                        }
                        dao.updateEventStatus(EventStatus.NOTIFIED, eventId);
                        break;

                    default:
                        //THIS CAN'T HAPPEN. But if it happen, this event is not for me and I don't care...
                        dao.updateEventStatus(EventStatus.NOTIFIED, eventId);
                        break;
                }
            }
        }

        private void statusMonitoring(AssetAppropriationDAO dao) throws Exception {
            AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation(dao.getUnsendedTransactions().size() + " unsended transactions were found.");
            for (AssetAppropriationTransactionRecord record : dao.getUnsendedTransactions()) {
                switch (record.status()) {
                    case APPROPRIATION_STARTED:
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("getting crypto address and saving it..." + record.transactionRecordId());
                        CryptoAddress cryptoAddress = cryptoVaultManager.getAddress();
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Address: " + cryptoAddress.getAddress());
                        dao.updateCryptoAddress(cryptoAddress, record.transactionRecordId());
                        dao.updateTransactionStatusCryptoAddressObtained(record.transactionRecordId());
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("statuses updated");
                        break;
                    case CRYPTOADDRESS_OBTAINED:
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("registering crypto address in crypto book. : " + record.transactionRecordId());
                        IntraWalletUserIdentity assetIdentity = intraWalletUserIdentityManager.createNewIntraWalletUser("Asset Appropriation: " + record.digitalAsset().getName(), null);
                        cryptoAddressBookManager.registerCryptoAddress(record.addressTo(),
                                assetIdentity.getPublicKey(),
                                Actors.INTRA_USER,
                                record.btcWalletPublicKey(),
                                Actors.EXTRA_USER,
                                Platforms.CRYPTO_CURRENCY_PLATFORM,
                                VaultType.CRYPTO_CURRENCY_VAULT,
                                CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                                record.userWalletPublicKey(),
                                ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);
                        dao.updateTransactionStatusCryptoAddressRegistered(record.transactionRecordId());
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Transaction Registered on crypto book.");
                        break;
                    case CRYPTOADDRESS_REGISTERED:
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Sending asset bitcoins : " + record.transactionRecordId());
                        if (record.addressTo() == null) {
                            AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("This transaction failed to have a crypto address... Returning to previous state");
                            dao.updateTransactionStatusAppropriationStarted(record.transactionRecordId());
                        } else {
                            String genesisTransaction = assetVaultManager.sendAssetBitcoins(record.digitalAsset().getGenesisAddress().getAddress(), record.addressTo(), record.digitalAsset().getGenesisAmount());
                            dao.updateGenesisTransaction(genesisTransaction, record.transactionRecordId());
                            dao.updateTransactionStatusBitcoinsSent(record.transactionRecordId());
                            AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Bitcoins sent!");
                        }
                        break;
                    default:
                        //This should never happen.
                        break;
                }
            }
        }


        public boolean isAgentRunning() {
            return agentRunning;
        }

        public void stopAgent() {
            agentRunning = false;
        }

        public void startAgent() {
            agentRunning = true;
        }
    }
}
