package com.bitdubai.fermat_dap_api.layer.dap_transaction.common;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransactionRecord;

import java.util.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 06/10/15.
 * Based on com/bitdubai/fermat_dap_plugin/layer/wallet/asset/issuer/developer/bitdubai/version_1/structure/AssetIssuerWalletTransactionRecordWrapper.java
 * Implemented by franklin on 01/10/15.
 */
public class AssetIssuerWalletTransactionRecordWrapper implements AssetIssuerWalletTransactionRecord {
    private final DigitalAsset digitalAsset;
    private final String digitalAssetPublicKey;
    private final String name;
    private final String description;
    private final CryptoAddress addressFrom;
    private final CryptoAddress addressTo;
    private final String actorFromPublicKey;
    private final String actorToPublicKey;
    private final Actors actorFromType;
    private final Actors actorToType;
    private final long amount;
    private final long timeStamp;
    private final String memo;
    private final String digitalAssetMetadataHash;
    private final String transactionId;

    AssetIssuerWalletTransactionRecordWrapper(DigitalAsset digitalAsset,
                                              String digitalAssetPublicKey,
                                              String name,
                                              String description,
                                              CryptoAddress addressFrom,
                                              CryptoAddress addressTo,
                                              String actorFromPublicKey,
                                              String actorToPublicKey,
                                              Actors actorFromType,
                                              Actors actorToType,
                                              long amount,
                                              long timeStamp,
                                              String memo,
                                              String digitalAssetMetadataHash,
                                              String transactionId){
        this.digitalAsset = digitalAsset;
        this.digitalAssetPublicKey = digitalAssetPublicKey;
        this.name = name;
        this.description = description;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey =actorToPublicKey;
        this.actorFromType = actorFromType;
        this.actorToType = actorToType;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.memo = memo;
        this.digitalAssetMetadataHash = digitalAssetMetadataHash;
        this.transactionId = transactionId;
    }

    public AssetIssuerWalletTransactionRecordWrapper(DigitalAssetMetadata digitalAssetMetadata,
                                              CryptoTransaction cryptoGenesisTransaction,
                                              String actorFromPublicKey,
                                              String actorToPublicKey){
        this.digitalAsset = digitalAssetMetadata.getDigitalAsset();
        this.digitalAssetPublicKey = this.digitalAsset.getPublicKey();
        this.name = this.digitalAsset.getName();
        this.description = this.digitalAsset.getDescription();
        this.addressFrom = cryptoGenesisTransaction.getAddressFrom();
        this.addressTo = cryptoGenesisTransaction.getAddressTo();
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey =actorToPublicKey;
        this.actorFromType = Actors.INTRA_USER;
        this.actorToType = Actors.DAP_ASSET_ISSUER;
        this.amount = cryptoGenesisTransaction.getCryptoAmount();
        this.digitalAssetMetadataHash = digitalAssetMetadata.getDigitalAssetHash();
        this.transactionId = cryptoGenesisTransaction.getTransactionHash();
        Date date= new Date();
        this.timeStamp = date.getTime();
        this.memo = "Digital Asset delivered at"+this.timeStamp;
    }

    @Override
    public DigitalAsset getDigitalAsset() {
        return digitalAsset;
    }

    @Override
    public String getDigitalAssetPublicKey() {
        return digitalAssetPublicKey;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    @Override
    public String getActorFromPublicKey() {
        return actorFromPublicKey;
    }

    @Override
    public Actors getActorFromType() {
        return actorFromType;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    @Override
    public String getActorToPublicKey() {
        return actorToPublicKey;
    }

    @Override
    public Actors getActorToType() {
        return actorToType;
    }

    @Override
    public String getIdTransaction() {
        return transactionId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getTimestamp() {
        return timeStamp;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public String getDigitalAssetMetadataHash() {
        return digitalAssetMetadataHash;
    }
}
