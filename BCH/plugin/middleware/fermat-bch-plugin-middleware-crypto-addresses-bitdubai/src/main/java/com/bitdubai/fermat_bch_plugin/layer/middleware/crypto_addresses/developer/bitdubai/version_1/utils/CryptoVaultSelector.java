package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatVaultEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantIdentifyVaultException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;

/**
 * The class <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.CryptoVaultSelector</code>
 * is intended to contain all the necessary business logic to decide which vault to use.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class CryptoVaultSelector {

    private final CryptoVaultManager cryptoVaultManager;

    public CryptoVaultSelector(final CryptoVaultManager cryptoVaultManager) {

        this.cryptoVaultManager = cryptoVaultManager;
    }

    public final CryptoVaultManager getVault(final VaultType      vaultType     ,
                                             final CryptoCurrency cryptoCurrency) throws CantIdentifyVaultException {

        try {
            switch (vaultType) {

                case ASSET_VAULT          : return getAssetVault(cryptoCurrency);
                case CRYPTO_CURRENCY_VAULT: return getCryptoCurrencyVault(cryptoCurrency);

                default:
                    throw new CantIdentifyVaultException(
                            "Unexpected vaultType: " + vaultType.toString() + " - " + vaultType.getCode()
                    );
            }
        } catch(InvalidParameterException e) {

            throw new CantIdentifyVaultException(e, "VaultType: "+vaultType.toString()+ " - CryptoCurrency: "+ cryptoCurrency, "Vault not supported or incorrect data given.");
        }
    }

    public final CryptoVaultManager getVault(final FermatVaultEnum fermatVaultEnum) throws CantIdentifyVaultException {

        return getVault(fermatVaultEnum.getVaultType(), fermatVaultEnum.getCryptoCurrency());
    }

    public final CryptoVaultManager getCryptoCurrencyVault(final CryptoCurrency cryptoCurrency) throws InvalidParameterException {

        switch (CryptoCurrencyVault.getByCryptoCurrency(cryptoCurrency)) {

            case BITCOIN_VAULT: return cryptoVaultManager;

            default:
                throw new InvalidParameterException(
                        "Unexpected cryptoCurrency: " + cryptoCurrency.toString() + " - " + cryptoCurrency.getCode()
                );
        }
    }

    public final CryptoVaultManager getAssetVault(final CryptoCurrency cryptoCurrency) throws InvalidParameterException {

        switch (CryptoCurrencyVault.getByCryptoCurrency(cryptoCurrency)) {

            case BITCOIN_VAULT: return cryptoVaultManager;

            default:
                throw new InvalidParameterException(
                        "Unexpected cryptoCurrency: " + cryptoCurrency.toString() + " - " + cryptoCurrency.getCode()
                );
        }
    }

    public final FermatVaultEnum getVaultEnum(final VaultType      vaultType     ,
                                              final CryptoCurrency cryptoCurrency) throws InvalidParameterException {

        switch (vaultType) {

            case CRYPTO_CURRENCY_VAULT: return CryptoCurrencyVault.getByCryptoCurrency(cryptoCurrency);
            case ASSET_VAULT:           return CryptoCurrencyVault.getByCryptoCurrency(cryptoCurrency);

            default: throw new InvalidParameterException("VaultType: "+vaultType.toString()+ " - CryptoCurrency: "+ cryptoCurrency, "Vault not recognized.");
        }

    }
}
