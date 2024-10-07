package com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.wallet_manager.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_cbp_api.all_definition.util.Version;
import com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.wallet_manager.exception.CantGetCryptoWalletListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.wallet_manager.exception.CantListWalletsException;
import com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.wallet_manager.exception.CantStartUninstallWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.wallet_manager.exception.CantStartUninstallWalletLanguageException;
import com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.wallet_manager.exception.CantStartUninstallWalletSkinException;
import com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.wallet_manager.exception.CantStartWalletInstallationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.wallet_manager.exception.CantStartWalletLanguageInstallationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.wallet_manager.exception.CantStartWalletSkinInstallationException;

import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 16/09/15.
 */

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.wallet_manage.interfaces.CryptoDesktopWalletModuleManager</code>
 * provides the methods for the Crypto Broker Wallets Installed sub app.
 */
public interface CryptoDesktopWalletModuleManager {

    /**
     * The method <code>getAllWalletInstalled</code> returns the list of all crypto wallet installed
     *
     * @return the list of crypto wallet installed
     * @throws CantGetCryptoWalletListException
     */
    public List<CryptoDesktopWalletInformation> getAllWalletInstalled(int max, int offset) throws CantGetCryptoWalletListException;


    /**
     * This method returns the list of installed wallets in the device
     *
     * @return A list with the installed wallets information
     */
    public List<CryptoDesktopInstalledWallet> getInstalledWallets() throws CantListWalletsException;

    /**
     * This method initialize the installation presses of the wallet by giving the control to the
     * wallet manager.
     *
     * @param walletCategory the category of the wallet to install
     * @param skinId the skin to install with the wallet
     * @param languageId the language to install in the wallet
     * @param walletCatalogueId the wallet identifier in the catalogue
     * @param version the version of the wallet to install
     * @throws CantStartWalletInstallationException
     */
    public void installWallet(WalletCategory walletCategory,
                              UUID skinId,
                              UUID languageId,
                              UUID walletCatalogueId,
                              Version version) throws CantStartWalletInstallationException;



    /**
     * This method initialize the installation presses of a skin by giving the control to the
     * wallet manager.
     *
     * @param walletCatalogueId the wallet identifier where to install the skin in.
     * @param languageId the language identifier
     * @throws CantStartWalletLanguageInstallationException
     */
    public void installLanguage(UUID walletCatalogueId, UUID languageId) throws CantStartWalletLanguageInstallationException;

    /**
     * This method initialize the installation presses of a skin by giving the control to the
     * wallet manager.
     *
     * @param walletCatalogueId the wallet identifier where to install the skin in.
     * @param skinId the skin identifier
     * @throws CantStartWalletSkinInstallationException
     */
    public void installSkin(UUID walletCatalogueId, UUID skinId) throws CantStartWalletSkinInstallationException;


    /**
     * This method initialize the uninstall presses of a skin by giving the control to the
     * wallet manager.
     *
     * @param walletCatalogueId the wallet identifier where to uninstall the skin from.
     * @param languageId the language identifier
     * @throws CantStartUninstallWalletLanguageException
     */
    public void uninstallLanguage(UUID walletCatalogueId, UUID languageId) throws CantStartUninstallWalletLanguageException;

    /**
     * This method initialize the uninstall presses of a skin by giving the control to the
     * wallet manager.
     *
     * @param walletCatalogueId the wallet identifier where to uninstall the skin from.
     * @param skinId the skin identifier
     * @throws CantStartUninstallWalletSkinException
     */
    public void uninstallSkin(UUID walletCatalogueId, UUID skinId) throws CantStartUninstallWalletSkinException;

    /**
     * This method initialize the uninstall presses of the wallet by giving the control to the
     * wallet manager.
     *
     * @param walletCatalogueId the wallet id in the catalogue
     * @throws CantStartUninstallWalletException
     */
    public void uninstallWallet(UUID walletCatalogueId) throws CantStartUninstallWalletException;
}
