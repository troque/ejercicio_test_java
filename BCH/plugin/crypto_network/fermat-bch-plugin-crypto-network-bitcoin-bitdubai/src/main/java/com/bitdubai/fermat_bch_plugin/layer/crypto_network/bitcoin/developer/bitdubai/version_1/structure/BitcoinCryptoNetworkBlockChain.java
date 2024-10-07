package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.BlockchainException;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.store.SPVBlockStore;

import java.io.File;
import java.io.Serializable;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptonetwork.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkBlockChain</code>
 * holds the downloaded blockchain by saving the block headers.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 09/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
class BitcoinCryptoNetworkBlockChain implements Serializable{

    /**
     * Classes variables
     */
    private BlockChain blockChain;
    private BlockStore blockStore;
    private NetworkParameters networkParameters;
    private final String BLOCKCHAIN_FILENAME = "/data/data/com.bitdubai.fermat/files/bitcoin_Blockchain_";


    /**
     * Constructor
     */
    public BitcoinCryptoNetworkBlockChain(NetworkParameters networkParameters) throws BlockchainException {
        this.networkParameters= networkParameters;

        /**
         * initialize the objects
         */
        try {
            initialize(false);
        } catch (BlockStoreException e) {
            throw new BlockchainException(BlockchainException.DEFAULT_MESSAGE, e, "Could not create blockchain to store block headers.", null);
        }
    }

    /**
     * Gets the blockChain object
     * @return
     */
    public BlockChain getBlockChain() {
        return blockChain;
    }

    /**
     * Initializes the blochchain and blockstore objects.
     * @param withError since I'm using this recursively, I will use this parameter to avoid a loop.
     * @throws BlockStoreException if something went wrong and I can't create the blockchain
     */
    private void initialize(boolean withError) throws BlockStoreException {
        /**
         * I will define the SPV blockstore were I will save the blockchain.
         * I will be saving the file under the network type I'm being created for.
         */
        String fileName = BLOCKCHAIN_FILENAME + BitcoinNetworkSelector.getBlockchainNetworkType(networkParameters).getCode();
        File blockChainFile = new File(fileName);
        try {
            blockStore = new SPVBlockStore(networkParameters, blockChainFile);
        } catch (Exception e) {
            /**
             * If there is an error saving it to file, I will save it to memory
             */
            blockStore = new MemoryBlockStore(this.networkParameters);
            System.out.println("*** Crypto Network Warning, error creating file to store blockchain, will save it to memory.");
            System.out.println("*** Crypto Network: " + e.toString());
        }

        /**
         * I initialize the blockchain object
         */
        try{
            blockChain = new BlockChain(this.networkParameters, blockStore);
        } catch (Exception e){
            if (withError)
                throw new BlockStoreException(e);
            /**
             * In case we have an issue like a corrupted blockstore, will delete the blockchain file
             */
            blockChainFile.delete();
            initialize(true);
        }

    }
}
