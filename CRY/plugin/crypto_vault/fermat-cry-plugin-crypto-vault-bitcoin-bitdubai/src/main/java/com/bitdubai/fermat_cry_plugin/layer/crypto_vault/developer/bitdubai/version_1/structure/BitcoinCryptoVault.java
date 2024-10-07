package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantConnectToBitcoinNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantCreateCryptoWalletException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVault;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.AbstractPeerEventListener;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.FilteredBlock;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionBroadcast;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptOpCodes;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.store.UnreadableWalletException;
import org.bitcoinj.wallet.DeterministicSeed;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by rodrigo on 09/06/15.
 */
public class BitcoinCryptoVault implements
        BitcoinManager,
        CryptoVault,
        TransactionProtocolManager {

    /**
     * BitcoinCryptoVault member variables
     */
    NetworkParameters networkParameters;
    File vaultFile;
    String vaultFileName;
    VaultEventListeners vaultEventListeners;
    PeerGroup peerGroup;


    /**
     * CryptoVault interface member variable
     */
    String userPublicKey;
    Wallet vault;

    /**
     * DealsWithCryptonetwork interface member variable
     */
    BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;


    /**
     * DealsWithEvents interface member variable
     */
    EventManager eventManager;

    /**
     * DealsWithErros interface member variable
     */
    ErrorManager errorManager;

    /**
     * DealsWithLoggers interface member variable
     */
    LogManager logManager;
    /**
     * DealsWithPluginIdentity interface member variable
     */
    UUID pluginId;


    Database database;

    /**
     * DealsWithPlugInFileSystem interface member variable
     */
    PluginFileSystem pluginFileSystem;

    public BitcoinCryptoVault(EventManager eventManager, ErrorManager errorManager, LogManager logManager, UUID pluginId, PluginFileSystem pluginFileSystem, Database database) {
        this.eventManager = eventManager;
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;
        this.database = database;
    }

    /**
     * CryptoVault interface implementations
     * @param userPublicKey
     */
    @Override
    public void setUserPublicKey(String userPublicKey) {
        this.userPublicKey = userPublicKey;
    }

    /**
     * CryptoVault interface implementations
     * @return
     */
    @Override
    public String getUserPublicKey() {
        return this.userPublicKey;
    }

    /**
     * CryptoVault interface implementations
     * @return
     */
    @Override
    public Object getWallet() {
        return vault;
    }

    public void setDatabase(Database database){
        this.database = database;
    }


    /**
     * Constructor
     * @param userPublicKey the Id of the user of the platform.
     */
    public BitcoinCryptoVault (String userPublicKey, EventManager eventManager, ErrorManager errorManager, LogManager logManager, UUID pluginId, PluginFileSystem pluginFileSystem, Database database) throws CantCreateCryptoWalletException {

        this.eventManager = eventManager;
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;
        this.database = database;

        try{
            this.userPublicKey = userPublicKey;
            this.networkParameters = BitcoinNetworkConfiguration.getNetworkConfiguration();

            this.vaultFileName = userPublicKey.toString() + ".vault";
            //todo this needs to be fixed. I need to find a better way to get the file
            this.vaultFile = new File("/data/data/com.bitdubai.fermat/files", vaultFileName);
        }catch(Exception exception){
            throw new CantCreateCryptoWalletException(CantCreateCryptoWalletException.DEFAULT_MESSAGE,exception,null,"Unchecked exception, chech the cause");
        }
    }

    public  void loadOrCreateVault() throws CantCreateCryptoWalletException {
        /**
         * Last Update: 23/07/2015 for: fmarcano
         */
        try {
            if (vaultFile.exists())
                loadExistingVaultFromFile();
            else
                createNewVault();

            configureVault();
        }catch(CantCreateCryptoWalletException exception){
            throw exception;
        }catch(Exception exception){
            throw new CantCreateCryptoWalletException(CantCreateCryptoWalletException.DEFAULT_MESSAGE,exception,null,"Unchecked exception, chech the cause");
        }
    }

    /**
     * creates a new vault.
     * @throws CantCreateCryptoWalletException
     */
    private void createNewVault() throws CantCreateCryptoWalletException {
        vault = new Wallet(networkParameters);
        try {
            PluginTextFile vaultFile = pluginFileSystem.createTextFile(pluginId, userPublicKey.toString(), vaultFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            vaultFile.persistToMedia();

            logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Vault created into file " + vaultFileName, null, null);
            /**
             * If I couldn't create it I can't go on
             */
        } catch (CantCreateFileException cantCreateFileException) {
            throw new CantCreateCryptoWalletException("There was an error trying to create a new Vault." ,cantCreateFileException, "Vault filename: " + vaultFileName, "Not enought space on disk?");
        } catch (CantPersistFileException e) {
            throw new CantCreateCryptoWalletException("There was an error trying to save the Vault into a file." ,e, "Vault filename: " + vaultFileName, "Not enought space on disk?");
        }
    }

    /**
     * Loads an existing Vault from file
     * @throws CantCreateCryptoWalletException
     */
    private void loadExistingVaultFromFile() throws CantCreateCryptoWalletException {
        try {
            vault = Wallet.loadFromFile(vaultFile);

            logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Vault loaded from file " + vaultFile.getAbsoluteFile().toString(), "CryptoVault current balance: " + vault.getBalance().getValue(), "CryptoVault estimated current balance: " + vault.getBalance(Wallet.BalanceType.ESTIMATED).toFriendlyString());

            /**
             * If I couldn't load it I can't go on.
             */
        } catch (UnreadableWalletException unreadableWalletException) {
            throw new CantCreateCryptoWalletException("Vault file not accesible.", unreadableWalletException, "Vault filename: " + vaultFileName, "Corrupted file.");
        }

    }

    /**
     * Will load the Vault from a provided seed. The user will need to provide the mNemonic code,
     * which is a series of prefedined words and the creation time. The vault will need to provide the methods
     * to retrieve this information at soma point.
     * @param mNemonicCode
     * @param CreationTimeInSeconds
     */
    public void loadExistingVaultFromSeed(String mNemonicCode, long CreationTimeInSeconds) throws CantCreateCryptoWalletException {
        try {
            DeterministicSeed seed = new DeterministicSeed(mNemonicCode, null, null, CreationTimeInSeconds);
            vault = Wallet.fromSeed(networkParameters, seed);
            configureVault();
        } catch (UnreadableWalletException e) {
            /**
             * I cannot load the existing vault from the provide seed. I cannot handle this
             */
            throw new CantCreateCryptoWalletException("Error trying to load the Vault from a seed.", e, null, "Unreadeble seed.");
        }

    }

    /**
     * I'm connecting the vault to the bitcoin Agent.
     * @throws CantStartAgentException
     */
    public void connectVault() throws CantConnectToBitcoinNetwork {
        try {
            //bitcoinCryptoNetworkManager.setVault(this);
            //bitcoinCryptoNetworkManager.connectToBitcoinNetwork();

            BlockStore blockStore = new MemoryBlockStore(this.networkParameters);
            BlockChain blockChain = new BlockChain(this.networkParameters,vault, blockStore);
            peerGroup = new PeerGroup(this.networkParameters,blockChain);
            peerGroup.addWallet(vault);


            if (networkParameters == RegTestParams.get()) {
                InetSocketAddress inetSocketAddress1 = new InetSocketAddress(REGTEST_SERVER_1_ADDRESS, REGTEST_SERVER_1_PORT);
                PeerAddress peerAddress1 = new PeerAddress(inetSocketAddress1);
                peerGroup.addAddress(peerAddress1);

                InetSocketAddress inetSocketAddress2 = new InetSocketAddress(REGTEST_SERVER_2_ADDRESS, REGTEST_SERVER_2_PORT);
                PeerAddress peerAddress2 = new PeerAddress(inetSocketAddress2);
                peerGroup.addAddress(peerAddress2);
            } else
            /**
             * If it is not RegTest, then I will get the Peers by DNSDiscovery
             */ {
                peerGroup.addPeerDiscovery(new DnsDiscovery(this.networkParameters));
            }

            peerGroup.start();
            peerGroup.startBlockChainDownload(new AbstractPeerEventListener(){
                @Override
                public void onPeerConnected(Peer peer, int peerCount) {
                    System.out.println("Connected to Peer from Crypto Vault: " + peer.toString());
                }

                @Override
                public void onBlocksDownloaded(Peer peer, Block block, FilteredBlock filteredBlock, int blocksLeft) {
                    //System.out.println("Block downloaded. Block left: " + blocksLeft);
                }

                @Override
                public void onTransaction(Peer peer, Transaction t) {
                    System.out.println("Transaction from Peer: " + t.toString());
                }
            });

        }catch(Exception exception){
            throw new CantConnectToBitcoinNetwork(CantConnectToBitcoinNetwork.DEFAULT_MESSAGE,exception,null,"Unchecked exception, chech the cause");
        }
    }

    public void disconnectVault() throws CantConnectToBitcoinNetwork { //todo raise correct exception
        try {
            bitcoinCryptoNetworkManager.setVault(this);
            bitcoinCryptoNetworkManager.disconnectFromBitcoinNetwork();
        }catch(Exception exception){
        throw new CantConnectToBitcoinNetwork(CantConnectToBitcoinNetwork.DEFAULT_MESSAGE,exception,null,"Unchecked exception, chech the cause");
    }
    }

    /**
     * configures internal vault parameters and creates the database that will hold
     * the transactions status.
     * @throws CantCreateCryptoWalletException
     */
    private void configureVault() throws CantCreateCryptoWalletException {
        try{
            vault.autosaveToFile(vaultFile, 500, TimeUnit.MILLISECONDS, null);
            vaultEventListeners = new VaultEventListeners(database, errorManager, eventManager, logManager);
            vault.addEventListener(vaultEventListeners);
        }catch(Exception exception){
            throw new CantCreateCryptoWalletException(CantCreateCryptoWalletException.DEFAULT_MESSAGE,exception,null,"Unchecked exception, chech the cause");
        }

    }


    /**
     * returns a valid CryptoAddrres from this vault
     * @return
     */
    public CryptoAddress getAddress(){
        try {
            CryptoAddress address = new CryptoAddress();
            address.setCryptoCurrency(CryptoCurrency.BITCOIN);
            address.setAddress(vault.freshReceiveAddress().toString());
            return address;
        }
        catch (Exception exception){
            throw exception;
        }
    }

    /**
     * Sends bitcoins to the specified address
     *
     * @param fermatTxId  the internal txID set for the transfer protocol
     * @param addressTo   the address to
     * @param amount      the amount of satoshis
     * @param op_Return   the op_return to be included in the output.
     * @return the transaction hash created to send the crypto.
     *
     * @throws InsufficientCryptoFundsException if i don't have enough crypto to send
     */

    public String sendBitcoins(UUID          fermatTxId,
                               CryptoAddress addressTo ,
                               long          amount    ,
                               String op_Return) throws InsufficientCryptoFundsException      ,
            InvalidSendToAddressException         ,
            CouldNotSendMoneyException            ,
            CryptoTransactionAlreadySentException {

        try {

            logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Sending bitcoins...", "Address to:" + addressTo.getAddress() + "TxId: " + fermatTxId, null);

            CryptoVaultDatabaseActions db = new CryptoVaultDatabaseActions(database, eventManager);

            // check if the transaction was already sent, this might be an error. we're not going to send it again.
            if (!db.isNewFermatTransaction(fermatTxId)) {
                System.out.println("Crypto Vault reSending previously sent transaction...");
            }

            // generate the address in the BitcoinJ format
            Address address = new Address(this.networkParameters, addressTo.getAddress());


            // I create the transaction that will be used to send the bitcoins.
            Wallet.SendRequest request = Wallet.SendRequest.to(address, Coin.valueOf(amount));



            // after we persist the new Transaction, we'll persist it as a Fermat transaction.
            db.persistnewFermatTransaction(fermatTxId.toString());


            // I'm adding a fixed fee for now.
            request.ensureMinRequiredFee = true;
            request.fee = Coin.valueOf(15000);
            /**
             * If OP_return was specified then I will add an output to the transaction
             */
            if (op_Return != null)
                request.tx.addOutput(Coin.ZERO, new ScriptBuilder().op(ScriptOpCodes.OP_RETURN).data(op_Return.getBytes()).build());


            /**
             * complete the transaction and commit it.
             */
            Transaction transaction = request.tx;
            System.out.println("Timelocked " + transaction.isTimeLocked());
            System.out.println("locktime: " + transaction.getLockTime());
            vault.completeTx(request);
            vault.commitTx(request.tx);
            /**
             * I get the transaction hash and persists this transaction in the database.
             */
            db.persistNewTransaction(fermatTxId.toString(), request.tx.getHashAsString());
            vault.saveToFile(vaultFile);

            final TransactionBroadcast broadcast = peerGroup.broadcastTransaction(request.tx);
            broadcast.setProgressCallback(new TransactionBroadcast.ProgressCallback() {
                @Override
                public void onBroadcastProgress(double progress) {
                    System.out.println("****CryptoVault: progress broadcast " + progress);
                }
            });

            broadcast.broadcast().get(2, TimeUnit.MINUTES);
            broadcast.future().get(2, TimeUnit.MINUTES);

            logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoVault information: bitcoin sent!!!", "Address to: " + addressTo.getAddress(), "Amount: " + amount);



            //returns the created transaction id
            return request.tx.getHashAsString();

        } catch (InsufficientMoneyException insufficientMoneyException) {

            throw new InsufficientCryptoFundsException("Not enought money in Vault to complete the transaction", insufficientMoneyException, "AddressTo:" + addressTo.getAddress() + ", Satoshis: " + amount, "Transaction confidence level too low to spend money. Wait for at least another block generation.");
        } catch (AddressFormatException addressFormatException) {

            throw new InvalidSendToAddressException(addressFormatException, "Address: " + addressTo.getAddress(), "Incorrect generation by scanner or user entry");
        } catch (CantExecuteQueryException cantExecuteQueryException) {

            throw new CouldNotSendMoneyException("I coudln't persist the internal transaction Id.", cantExecuteQueryException, "Transaction ID: " + fermatTxId.toString(), "An error in the Database plugin..");
        }  catch (InterruptedException | ExecutionException | TimeoutException | IOException e) {
            throw new CouldNotSendMoneyException(CouldNotSendMoneyException.DEFAULT_MESSAGE, e, "Time out broadcasting the transaction to peers.", "No peers found that accepted this transaction because low fee");
        }   catch(Exception exception){
            throw new CouldNotSendMoneyException("Fatal error sending bitcoins.", exception, "Address to:" + addressTo.getAddress() + ", transaction Id:" + fermatTxId.toString(), "Unkwnown.");
        }
    }


    /**
     * Validates if this transaction is to send money to ourselves.
     * This is not allowed.
     * @param transaction
     * @return
     */
    private boolean isSendingMoneyToMyself(Transaction transaction) {
        int size = transaction.getOutputs().size();
        boolean[] confirmations = new boolean[size];
        int i=0;
        for (TransactionOutput output : transaction.getOutputs()){
            /**
             * will save the confirmations for every address in the output.
             * If I get all trues, then I'm sending money to myself.
             */
            confirmations[i] = output.isMine(vault);
            i++;
        }

        /**
         * I will loop the array, If I get a false, then I return false.
         */
        for (int x=0; x<size; x++){
            if (!confirmations[x])
                return false;
        }

        /**
         * All address returned true, so I'm returning true.
         */
        return true;
    }

    /**
     * Validates if the address sent is valid in the current network or not.
     * @param addressTo
     * @return
     */
    public boolean isValidAddress(CryptoAddress addressTo){
        try {
            Address address = new Address(networkParameters, addressTo.getAddress());
            return true;
        } catch (AddressFormatException e) {
            return false;
        }
    }


    /**
     * TransactionProtocolManager interface implementation
     */
    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {
        /**
         * will marked the transaction as notified
         */
        try{
            CryptoVaultDatabaseActions db = new CryptoVaultDatabaseActions(database, eventManager);
            db.updateTransactionProtocolStatus(transactionID, ProtocolStatus.RECEPTION_NOTIFIED);
        } catch (Exception e){
            throw new CantConfirmTransactionException("There was an error trying to confirm reception of a transaction", e, "TransactionId: " + transactionID.toString(), "Database plugin error.");
        }
    }

    @Override
    public List<com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        /**
         * will return all the pending transactions
         */
        try{
            List<com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction> txs = new ArrayList<>();
            CryptoVaultDatabaseActions db = new CryptoVaultDatabaseActions(database, eventManager);
            /**
             * Im getting the transaction headers which is a map with transactionID and Transaction Hash. I will use this information to access the vault.
             */
            HashMap<String, String> transactionHeaders = db.getPendingTransactionsHeadersByTransactionType(CryptoTransactionType.INCOMING);
            for (Map.Entry<String, String> entry : transactionHeaders.entrySet()){
                String txId = entry.getKey();
                String txHash = entry.getValue();

                /**
                 * I get the address from the vault.
                 */
                String[] addresses = getAddressFromTransaction(txHash);
                CryptoAddress addressFrom = new CryptoAddress(addresses[0], CryptoCurrency.BITCOIN);
                CryptoAddress addressTo = new CryptoAddress(addresses[1], CryptoCurrency.BITCOIN);
                long amount = getAmountFromVault(txHash);


                /**
                 * For issue #620, I will get the transaction status from database, instead from the vault.
                 * I need to sent the transaction status of the snapshot of the transaction, not the current crypto status
                 */
                CryptoStatus cryptoStatus = db.getCryptoStatus(txId);

                CryptoTransaction cryptoTransaction = new CryptoTransaction(
                        txHash,
                        addressFrom,
                        addressTo,
                        CryptoCurrency.BITCOIN,
                        amount,
                        cryptoStatus
                );

                com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction tx = new com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction(
                        UUID.fromString(txId),
                        cryptoTransaction,
                        Action.APPLY,
                        getTransactionTimestampFromVault(txHash)
                );
                txs.add(tx);

                /**
                 * I update the status of the transaction protocol to SENDING_NOTIFIED
                 */
                db.updateTransactionProtocolStatus(UUID.fromString(txId), ProtocolStatus.SENDING_NOTIFIED);
            }


            /**
             * once the database is updated, I return the transaction
             */

            return txs;
        } catch (Exception e){
            throw new CantDeliverPendingTransactionsException("I couldn't deliver pending transactions",e,null,null);
        }
    }

    /**
     * gets the amount of satoshis of the transaction
     * @param txHash
     * @return satoshis
     */
    private long getAmountFromVault(String txHash) {
        /**
         * I calculate the ammount by SUM all the outputs amounts.
         */
        Sha256Hash hash = new Sha256Hash(txHash);
        Transaction tx = vault.getTransaction(hash);
        Coin values = tx.getValue(vault);
        return values.getValue();
    }

    /**
     * gets the address To of the transaction
     * @param txHash
     * @return the string of the address
     */
    private String[] getAddressFromTransaction(String txHash) {
        String[] addresses = new String[2];

        Sha256Hash hash = new Sha256Hash(txHash);
        Transaction tx = vault.getTransaction(hash);

        /**
         * I need to determine if this address is outgoing on incoming to determine what is addressTo
         * and addressFrom.
         */
        boolean isOutgoing;
        if (tx.getValueSentFromMe(vault).getValue() != 0)
            isOutgoing = true;
        else
            isOutgoing = false;

        /**
         * if is a transaction I generated to send to some one
         */
        if (isOutgoing){
            /**
             * I will search on all outputs for an address that is mine
             */
            for (TransactionOutput output : tx.getOutputs()) {
                if (output.isMine(vault)){
                    /**
                     * this is address From
                     */
                    addresses[0] = output.getScriptPubKey().getToAddress(this.networkParameters).toString();
                } else {
                    /**
                     * This is address To
                     */
                try{
                    addresses[1] = output.getScriptPubKey().getToAddress(networkParameters).toString();
                } catch (Exception e){

                }
                    addresses[1] = "";
                }

            }
        } else
        /**
         * if it is an incoming transaction
         */
        {
            /**
             * I will search on all outputs for an address that is mine
             */
            for (TransactionOutput output : tx.getOutputs()) {
                if (output.isMine(vault)){
                    /**
                     * this is address To
                     */
                    addresses[1] = output.getScriptPubKey().getToAddress(this.networkParameters).toString();
                } else {
                    /**
                     * This is address From
                     */
                    addresses[0] = output.getScriptPubKey().getToAddress(networkParameters).toString();
                }

            }
        }

        return addresses;
    }



    /**
     * Get the timestamp of the transaction
     * @param txHash
     * @return
     */
    private long getTransactionTimestampFromVault(String txHash){
        Sha256Hash hash = new Sha256Hash(txHash);
        Transaction tx = vault.getTransaction(hash);

        /*
        long timestamp =tx.getLockTime();
        if (timestamp == 0)

         // If the transaction doesn't have a locktime, I will return the current timestamp

            return System.currentTimeMillis();
        else
        // I get the current timestamp

        return tx.getLockTime() * 1000;
        */

        // Changed be Ezequiel Postan <ezequiel.postan@gmail.com>
        // August 20th 2015
        return tx.getUpdateTime().getTime();
    }

    // modified by lnacosta
    public CryptoStatus getCryptoStatus(final String txHash) throws CantExecuteQueryException                     ,
                                                                    UnexpectedResultReturnedFromDatabaseException {

        try {

            CryptoVaultDatabaseActions db = new CryptoVaultDatabaseActions(database, eventManager);
            return db.getLastCryptoStatus(txHash);

        } catch(CantLoadTableToMemoryException exception){

            throw new CantExecuteQueryException(exception, null, "Check the cause");
        } catch(Exception exception){

            throw new CantExecuteQueryException(FermatException.wrapException(exception));
        }
    }
}
