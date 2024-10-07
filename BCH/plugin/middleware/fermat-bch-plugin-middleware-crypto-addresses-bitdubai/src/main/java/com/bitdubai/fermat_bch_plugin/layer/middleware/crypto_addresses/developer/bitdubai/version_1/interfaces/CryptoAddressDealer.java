package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatVaultEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CallToGetByCodeOnNONEException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantGenerateAndRegisterCryptoAddressException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantGenerateCryptoAddressException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantGetDefaultWalletException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressesNewException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantIdentifyVaultException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantIdentifyWalletManagerException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantRegisterCryptoAddressBookException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.utils.CryptoVaultSelector;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.utils.WalletManagerSelector;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantGetInstalledWalletException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.DefaultWalletNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

/**
 * The interface <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.interfaces.CryptoAddressDealer</code>
 * represents all the components in the crypto address plugin that generate and register addresses.
 * <p>
 *
 * This components must contain all the necessary logic to generate the addresses, register them and deliver to the correct plugin.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 */
public abstract class CryptoAddressDealer {

    private final CryptoAddressBookManager cryptoAddressBookManager;
    private final CryptoVaultSelector      cryptoVaultSelector     ;
    private final WalletManagerSelector    walletManagerSelector   ;

    public CryptoAddressDealer(final CryptoAddressBookManager cryptoAddressBookManager,
                               final CryptoVaultSelector      cryptoVaultSelector     ,
                               final WalletManagerSelector    walletManagerSelector   ) {

        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.cryptoVaultSelector      = cryptoVaultSelector     ;
        this.walletManagerSelector    = walletManagerSelector   ;
    }

    protected final CryptoAddress getAddress(final VaultType      vaultType     ,
                                             final CryptoCurrency cryptoCurrency) throws CantGenerateCryptoAddressException {

        try {

            return cryptoVaultSelector.getVault(vaultType, cryptoCurrency).getAddress();

        } catch (CantIdentifyVaultException e) {

            throw new CantGenerateCryptoAddressException(e, "", "Problem identifying vault.");
        }
    }

    protected final InstalledWallet getDefaultWallet(final Actors                actorType     ,
                                                     final CryptoCurrency        cryptoCurrency,
                                                     final BlockchainNetworkType networkType   ,
                                                     final Platforms             platform      ) throws CantGetDefaultWalletException ,
                                                                                                        DefaultWalletNotFoundException {

        try {

            return walletManagerSelector.getWalletManager(platform).getDefaultWallet(
                    cryptoCurrency,
                    actorType,
                    networkType
            );

        } catch (CantIdentifyWalletManagerException e) {

            throw new CantGetDefaultWalletException(e, "", "Problem identifying wallet manager.");
        } catch (com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.DefaultWalletNotFoundException e) {

            throw new DefaultWalletNotFoundException(e, "", "Default wallet not found.");
        } catch (CantGetInstalledWalletException e) {

            throw new CantGetDefaultWalletException(e, "", "There was a problem trying to get the default wallet.");
        }
    }

    protected final void registerCryptoAddress(final CryptoAddress        cryptoAddress           ,
                                               final CryptoAddressRequest request                 ,
                                               final InstalledWallet      installedWallet         ,
                                               final VaultType            vaultType               ) throws CantRegisterCryptoAddressBookException {

        try {

            FermatVaultEnum fermatVaultEnum = cryptoVaultSelector.getVaultEnum(vaultType, request.getCryptoCurrency());

            ReferenceWallet referenceWallet = ReferenceWallet.getByCategoryAndIdentifier(installedWallet.getWalletCategory(), installedWallet.getWalletPlatformIdentifier());

            cryptoAddressBookManager.registerCryptoAddress(
                    cryptoAddress,
                    request.getIdentityPublicKeyResponding(),
                    request.getIdentityTypeResponding(),
                    request.getIdentityPublicKeyRequesting(),
                    request.getIdentityTypeRequesting(),
                    installedWallet.getPlatform(),
                    vaultType,
                    fermatVaultEnum.getCode(),
                    installedWallet.getWalletPublicKey(),
                    referenceWallet
            );
        } catch(InvalidParameterException      |
                CallToGetByCodeOnNONEException e) {

            throw new CantRegisterCryptoAddressBookException(e, "walletCategory: "+installedWallet.getWalletCategory()+" - walletPlatformIdentifier: "+installedWallet.getWalletPlatformIdentifier(), "Error trying to get the reference wallet type.");
        } catch(CantRegisterCryptoAddressBookRecordException e) {

            throw new CantRegisterCryptoAddressBookException(e, "", "Error trying to register the crypto address.");
        }
    }

    protected final CryptoAddress generateAndRegisterCryptoAddress(final Platforms            platform ,
                                                                   final VaultType            vaultType,
                                                                   final CryptoAddressRequest request  ) throws CantGenerateAndRegisterCryptoAddressException,
                                                                                                                DefaultWalletNotFoundException               {

        try {

            InstalledWallet wallet = this.getDefaultWallet(request.getIdentityTypeResponding(), request.getCryptoCurrency(), request.getBlockchainNetworkType(), platform);

            final CryptoAddress cryptoAddress = this.getAddress(vaultType, request.getCryptoCurrency());

            this.registerCryptoAddress(
                    cryptoAddress,
                    request,
                    wallet,
                    vaultType
            );

            return cryptoAddress;

        } catch (CantGetDefaultWalletException e) {

            throw new CantGenerateAndRegisterCryptoAddressException(e, "", "There was a problem trying to get a wallet.");
        } catch (CantRegisterCryptoAddressBookException e) {

            throw new CantGenerateAndRegisterCryptoAddressException(e, "", "There was a problem trying to register the crypto address.");
        } catch (CantGenerateCryptoAddressException e) {

            throw new CantGenerateAndRegisterCryptoAddressException(e, "", "There was a problem trying to generate the crypto address.");
        }
    }

    /**
     * Throw the method <code>handleCryptoAddressesNew</code> you can manage the different actions of a crypto address new.
     *
     * @throws CantHandleCryptoAddressesNewException if something goes wrong.
     */
    public abstract void handleCryptoAddressesNew(final CryptoAddressRequest cryptoAddressRequest) throws CantHandleCryptoAddressesNewException;

}
