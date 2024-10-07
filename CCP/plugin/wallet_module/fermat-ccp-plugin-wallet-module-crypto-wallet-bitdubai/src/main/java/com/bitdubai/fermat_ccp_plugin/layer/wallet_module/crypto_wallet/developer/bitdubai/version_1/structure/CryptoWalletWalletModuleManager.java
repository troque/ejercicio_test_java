package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantCreateExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSetPhotoException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSignExtraUserMessageException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.ExtraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.IntraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionSummary;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.CryptoAddressDealers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentType;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantApproveCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGenerateCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGetCryptoPaymentRegistryException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantRejectCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CryptoPaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPayment;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantApproveRequestPaymentException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListPaymentRequestDateOrderException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListReceivePaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListSentPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantRefuseRequestPaymentException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.PaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.RequestPaymentInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantStoreMemoException;

import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActor;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;

import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsSearch;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllIntraUserConnectionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantFindWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetActorTransactionHistoryException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSaveTransactionDescriptionException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.TransactionNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.ActorTransactionSummary;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.OutgoingExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.exceptions.CantGetTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.exceptions.CantSendFundsException;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentRegistry;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantEnrichIntraUserException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantEnrichTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantGetActorException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantCreateOrRegisterActorException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantInitializeCryptoWalletManagerException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantRequestOrRegisterCryptoAddressException;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager</code>
 * haves all methods for the contacts activity of a bitcoin wallet
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/06/15.
 * Modified and reviewed by nattyco & furszy.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoWalletWalletModuleManager implements CryptoWallet {

    private final BitcoinWalletManager           bitcoinWalletManager          ;
    private final CryptoAddressBookManager       cryptoAddressBookManager      ;
    private final CryptoAddressesManager         cryptoAddressesNSManager      ;
    private final CryptoPaymentManager           cryptoPaymentManager          ;
    private final CryptoVaultManager             cryptoVaultManager            ;
    private final ErrorManager                   errorManager                  ;
    private final ExtraUserManager               extraUserManager              ;
    private final IntraWalletUserActorManager    intraUserManager              ;
    private final IntraWalletUserIdentityManager intraWalletUserIdentityManager;
    private final OutgoingExtraUserManager       outgoingExtraUserManager      ;
    private final OutgoingIntraActorManager      outgoingIntraActorManager     ;
    private final WalletContactsManager          walletContactsManager         ;

    public CryptoWalletWalletModuleManager(final BitcoinWalletManager           bitcoinWalletManager          ,
                                           final CryptoAddressBookManager       cryptoAddressBookManager      ,
                                           final CryptoAddressesManager         cryptoAddressesNSManager      ,
                                           final CryptoPaymentManager           cryptoPaymentManager          ,
                                           final CryptoVaultManager             cryptoVaultManager            ,
                                           final ErrorManager                   errorManager                  ,
                                           final ExtraUserManager               extraUserManager              ,
                                           final IntraWalletUserActorManager    intraUserManager              ,
                                           final IntraWalletUserIdentityManager intraWalletUserIdentityManager,
                                           final OutgoingExtraUserManager       outgoingExtraUserManager      ,
                                           final OutgoingIntraActorManager      outgoingIntraActorManager     ,
                                           final WalletContactsManager          walletContactsManager         ) {

        this.bitcoinWalletManager           = bitcoinWalletManager          ;
        this.cryptoAddressBookManager       = cryptoAddressBookManager      ;
        this.cryptoAddressesNSManager       = cryptoAddressesNSManager      ;
        this.cryptoPaymentManager           = cryptoPaymentManager          ;
        this.cryptoVaultManager             = cryptoVaultManager            ;
        this.errorManager                   = errorManager                  ;
        this.extraUserManager               = extraUserManager              ;
        this.intraUserManager               = intraUserManager              ;
        this.intraWalletUserIdentityManager = intraWalletUserIdentityManager;
        this.outgoingExtraUserManager       = outgoingExtraUserManager      ;
        this.outgoingIntraActorManager      = outgoingIntraActorManager     ;
        this.walletContactsManager          = walletContactsManager         ;
    }

    private CryptoPaymentRegistry  cryptoPaymentRegistry ;
    private WalletContactsRegistry walletContactsRegistry;


    public final void initialize() throws CantInitializeCryptoWalletManagerException {

        try {

            cryptoPaymentRegistry  = cryptoPaymentManager .getCryptoPaymentRegistry();

            walletContactsRegistry = walletContactsManager.getWalletContactsRegistry();

        } catch (final CantGetWalletContactRegistryException e) {

            throw new CantInitializeCryptoWalletManagerException(e, "", "Error trying to get wallet manager registry.");
        } catch(final CantGetCryptoPaymentRegistryException e) {

            throw new CantInitializeCryptoWalletManagerException(e, "", "Error get crypto Payment Registry object");
        }  catch (final Exception e){

            throw new CantInitializeCryptoWalletManagerException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoWalletWalletContact> listWalletContacts(String walletPublicKey,String intraUserLoggedInPublicKey) throws CantGetAllWalletContactsException {
        try {


            List<CryptoWalletWalletContact> finalRecordList = new ArrayList<>();
            finalRecordList.clear();
            WalletContactsSearch walletContactsSearch = walletContactsRegistry.searchWalletContact(walletPublicKey);
            for(WalletContactRecord r : walletContactsSearch.getResult()){

                byte[] image = getImageByActorType(r.getActorType(), r.getActorPublicKey(),intraUserLoggedInPublicKey);

                finalRecordList.add(new CryptoWalletWalletModuleWalletContact(r, image));
            }
            return  finalRecordList;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e);
        }  catch (Exception e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<CryptoWalletWalletContact> listAllActorContactsAndConnections(String walletPublicKey,String intraUserLoggedInPublicKey) throws CantGetAllWalletContactsException {
        try {
            Map<String, CryptoWalletWalletContact> contactMap = new HashMap<>();

            //get wallet contacts
            WalletContactsSearch walletContactsSearch = walletContactsRegistry.searchWalletContact(walletPublicKey);


            for(WalletContactRecord r : walletContactsSearch.getResult()){
                // System.out.println("wallet contact: "+r);
                byte[] image = getImageByActorType(r.getActorType(), r.getActorPublicKey(),intraUserLoggedInPublicKey);
                contactMap.put(r.getActorPublicKey(), new CryptoWalletWalletModuleWalletContact(r, image));
            }

            // get intra user connections
            List<IntraWalletUserActor> intraUserList = intraUserManager.getConnectedIntraWalletUsers(intraUserLoggedInPublicKey);

            for(IntraWalletUserActor intraUser : intraUserList) {
                // System.out.println("intra user: " + intraUser);
                if (!contactMap.containsKey(intraUser.getPublicKey()))
                {
                    contactMap.put(intraUser.getPublicKey(), new CryptoWalletWalletModuleWalletContact( new CryptoWalletWalletModuleIntraUserActor(
                            intraUser.getName(),
                            false,
                            intraUser.getProfileImage(),
                            intraUser.getPublicKey()),
                            walletPublicKey));

                }

            }
            return new ArrayList<>(contactMap.values());


        } catch (CantGetAllWalletContactsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e);
        }  catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    private byte[] getImageByActorType(final Actors actorType     ,
                                       final String actorPublicKey, final String intraUserLoggedInPublicKey) throws CantGetAllWalletContactsException,
            ExtraUserNotFoundException       ,
            CantGetExtraUserException        {

        try {
            Actor actor;
            switch (actorType) {
                case EXTRA_USER:
                    actor = extraUserManager.getActorByPublicKey(actorPublicKey);
                    return actor.getPhoto();
                case INTRA_USER:
                    try {
                        actor = intraUserManager.getActorByPublicKey(intraUserLoggedInPublicKey, actorPublicKey);
                        return actor.getPhoto();

                    } catch (CantGetIntraUserException | IntraUserNotFoundException e) {
                        throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e);
                    }

                default:
                    throw new CantGetAllWalletContactsException("UNEXPECTED ACTOR TYPE", null, "", "incomplete switch");
            }
        } catch (Exception e) {
            return new byte[0];
        }
    }


    @Override
    public List<CryptoWalletWalletContact> listWalletContactsScrolling(String  walletPublicKey,
                                                                       String intraUserLoggedInPublicKey,
                                                                       Integer max,
                                                                       Integer offset) throws CantGetAllWalletContactsException {
        try {
            Actor actor;
            List<CryptoWalletWalletContact> finalRecordList = new ArrayList<>();
            finalRecordList.clear();
            WalletContactsSearch walletContactsSearch = walletContactsRegistry.searchWalletContact(walletPublicKey);
            for(WalletContactRecord r : walletContactsSearch.getResult(max, offset)){

                byte[] image = getImageByActorType(r.getActorType(), r.getActorPublicKey(),intraUserLoggedInPublicKey);

                finalRecordList.add(new CryptoWalletWalletModuleWalletContact(r, image));
            }
            return  finalRecordList;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e);
        }
        catch (Exception e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<CryptoWalletIntraUserActor> listAllIntraUserConnections(String  intraUserIdentityPublicKey,
                                                                        String  walletPublicKey,
                                                                        Integer max,
                                                                        Integer offset) throws CantGetAllIntraUserConnectionsException {
        try {
            List<CryptoWalletIntraUserActor> intraUserActorList = new ArrayList<>();

            List<IntraWalletUserActor> intraUserList = intraUserManager.getAllIntraWalletUsers(intraUserIdentityPublicKey, max, offset);

            for(IntraWalletUserActor intraUser : intraUserList)
                intraUserActorList.add(new CryptoWalletWalletModuleIntraUserActor(
                        intraUser.getName(),
                        false,
                        intraUser.getProfileImage(),
                        intraUser.getPublicKey()));

            return intraUserActorList;
        } catch (CantGetIntraWalletUsersException e) {
            throw new CantGetAllIntraUserConnectionsException(CantGetAllIntraUserConnectionsException.DEFAULT_MESSAGE, e, "", "Problem trying yo get actors from Intra-User Actor plugin.");
        } catch (Exception e) {
            throw new CantGetAllIntraUserConnectionsException(CantGetAllIntraUserConnectionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    private CryptoWalletIntraUserActor enrichIntraUser(IntraWalletUserActor intraWalletUser,
                                                       String walletPublicKey) throws CantEnrichIntraUserException {
        try {
            walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(intraWalletUser.getPublicKey(), walletPublicKey);

            return new CryptoWalletWalletModuleIntraUserActor(
                    intraWalletUser.getName(),
                    true,
                    intraWalletUser.getProfileImage(),
                    intraWalletUser.getPublicKey()
            );
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
            return new CryptoWalletWalletModuleIntraUserActor(
                    intraWalletUser.getName(),
                    false,
                    intraWalletUser.getProfileImage(),
                    intraWalletUser.getPublicKey()
            );
        } catch (CantGetWalletContactException e) {
            throw new CantEnrichIntraUserException(CantEnrichIntraUserException.DEFAULT_MESSAGE, e, "", "There was a problem trying to enrich the intra user record.");
        }
    }

    @Override
    public CryptoWalletWalletContact convertConnectionToContact( String        actorAlias,
                                                                 Actors        actorConnectedType,
                                                                 String        actorConnectedPublicKey,
                                                                 byte[]        actorPhoto,
                                                                 Actors        actorWalletType ,
                                                                 String        identityWalletPublicKey,
                                                                 String        walletPublicKey,
                                                                 CryptoCurrency walletCryptoCurrency,
                                                                 BlockchainNetworkType blockchainNetworkType) throws CantCreateWalletContactException, ContactNameAlreadyExistsException{
        try{

            try {
                WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByAliasAndWalletPublicKey(actorAlias, walletPublicKey);

                //get to Crypto Address NS the intra user actor address
                cryptoAddressesNSManager.sendAddressExchangeRequest(walletPublicKey,
                        walletCryptoCurrency,
                        actorWalletType,
                        actorConnectedType,
                        identityWalletPublicKey,
                        actorConnectedPublicKey,
                        CryptoAddressDealers.CRYPTO_WALLET,
                        blockchainNetworkType);

                return new CryptoWalletWalletModuleWalletContact(walletContactRecord);

            } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {

                WalletContactRecord walletContactRecord = walletContactsRegistry.createWalletContact(
                        actorConnectedPublicKey,
                        actorAlias,
                        "",
                        "",
                        actorConnectedType,
                        walletPublicKey
                );

                //get to Crypto Address NS the intra user actor address
                cryptoAddressesNSManager.sendAddressExchangeRequest(walletPublicKey,
                        walletCryptoCurrency,
                        actorWalletType,
                        actorConnectedType,
                        identityWalletPublicKey,
                        actorConnectedPublicKey,
                        CryptoAddressDealers.CRYPTO_WALLET,
                        blockchainNetworkType);




                return new CryptoWalletWalletModuleWalletContact(walletContactRecord, actorPhoto);
            }

        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creation a wallet contact.", null);
        } catch (Exception e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }



    @Override
    public CryptoWalletWalletContact createWalletContactWithPhoto(CryptoAddress receivedCryptoAddress,
                                                                  String        actorAlias,
                                                                  String        actorFirstName,
                                                                  String        actorLastName,
                                                                  Actors        actorType,
                                                                  String        walletPublicKey,
                                                                  byte[]        photo) throws CantCreateWalletContactException, ContactNameAlreadyExistsException {
        try{
            try {
                walletContactsRegistry.getWalletContactByAliasAndWalletPublicKey(actorAlias, walletPublicKey);
                throw new ContactNameAlreadyExistsException(ContactNameAlreadyExistsException.DEFAULT_MESSAGE, null, null, null);

            } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
                String actorPublicKey = createActor(actorAlias, actorType, photo);
                List<CryptoAddress> cryptoAddresses = new ArrayList<>();
                cryptoAddresses.add(receivedCryptoAddress);
                WalletContactRecord walletContactRecord = walletContactsRegistry.createWalletContact(
                        actorPublicKey,
                        actorAlias,
                        actorFirstName,
                        actorLastName,
                        actorType,
                        cryptoAddresses,
                        walletPublicKey
                );
                return new CryptoWalletWalletModuleWalletContact(walletContactRecord, photo);
            }

        } catch (ContactNameAlreadyExistsException e) {
            throw e;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e);
        } catch (CantCreateOrRegisterActorException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creating or registering actor.", null);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creation a wallet contact.", null);
        } catch (Exception e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public CryptoWalletWalletContact createWalletContact(CryptoAddress receivedCryptoAddress,
                                                         String        actorAlias,
                                                         String        actorFirstName,
                                                         String        actorLastName,
                                                         Actors        actorType,
                                                         String        walletPublicKey) throws CantCreateWalletContactException, ContactNameAlreadyExistsException {
        try{
            try {
                walletContactsRegistry.getWalletContactByAliasAndWalletPublicKey(actorAlias, walletPublicKey);
                throw new ContactNameAlreadyExistsException(ContactNameAlreadyExistsException.DEFAULT_MESSAGE, null, null, null);

            } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {

                String actorPublicKey = createActor(actorAlias, actorType);
                List<CryptoAddress> cryptoAddresses = new ArrayList<>();
                cryptoAddresses.add(receivedCryptoAddress);
                WalletContactRecord walletContactRecord = walletContactsRegistry.createWalletContact(
                        actorPublicKey,
                        actorAlias,
                        actorFirstName,
                        actorLastName,
                        actorType,
                        cryptoAddresses,
                        walletPublicKey
                );
                return new CryptoWalletWalletModuleWalletContact(walletContactRecord);
            }

        } catch (ContactNameAlreadyExistsException e) {
            throw e;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e);
        } catch (CantCreateOrRegisterActorException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creating or registering actor.", null);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creation a wallet contact.", null);
        } catch (Exception e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void updateContactPhoto(String actorPublicKey, Actors actor, byte[] photo) throws CantUpdateWalletContactException {
        try {
            switch (actor) {
                case EXTRA_USER:
                    this.extraUserManager.setPhoto(actorPublicKey, photo);
                    break;
                case INTRA_USER:
                    this.intraUserManager.setPhoto(actorPublicKey, photo);
                    break;
                default:
                    throw new CantUpdateWalletContactException("Actor not expected", null, "The actor type is:" + actor.getCode(), "Incomplete switch");
            }
        } catch (ExtraUserNotFoundException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " i cannot find the actor ");
        } catch (CantSetPhotoException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " error trying to get the actor photo");
        }
        catch (com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantSetPhotoException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " error trying to get the actor photo");
        }
        catch (IntraUserNotFoundException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " i cannot find the actor");
        }
    }

    @Override
    public CryptoWalletWalletContact findWalletContactById(UUID contactId,String intraUserLoggedInPublicKey) throws CantFindWalletContactException, WalletContactNotFoundException {
        try {
            WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByContactId(contactId);


            byte[] image = getImageByActorType(walletContactRecord.getActorType(), walletContactRecord.getActorPublicKey(), intraUserLoggedInPublicKey);


            return new CryptoWalletWalletModuleWalletContact(walletContactRecord, image);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantFindWalletContactException(CantFindWalletContactException.DEFAULT_MESSAGE, e);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
            throw new WalletContactNotFoundException(WalletContactNotFoundException.DEFAULT_MESSAGE, e);
        } catch (Exception e) {
            throw new CantFindWalletContactException(CantFindWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public CryptoWalletWalletContact addIntraUserActorLikeContact(String intraUserPublicKey,
                                                                  String alias,
                                                                  String firstName,
                                                                  String lastName,
                                                                  String walletPublicKey) throws CantCreateWalletContactException {
        try {
            WalletContactRecord walletContactRecord = walletContactsRegistry.createWalletContact(
                    intraUserPublicKey,
                    alias,
                    firstName,
                    lastName,
                    Actors.INTRA_USER,
                    null,
                    walletPublicKey
            );

            return new CryptoWalletWalletModuleWalletContact(walletContactRecord);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateWalletContactException();
        } catch (Exception e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public CryptoAddress requestAddressToNewExtraUser(String deliveredByActorPublicKey,
                                                      Actors deliveredByActorType,
                                                      String deliveredToActorName,
                                                      Platforms platform,
                                                      VaultType vaultType,
                                                      String vaultIdentifier,
                                                      String walletPublicKey,
                                                      ReferenceWallet walletType) throws CantRequestCryptoAddressException {
        // TODO implement this method
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public CryptoAddress requestAddressToKnownUser(String deliveredByActorPublicKey,
                                                   Actors deliveredByActorType,
                                                   String deliveredToActorPublicKey,
                                                   Actors deliveredToActorType,
                                                   Platforms platform,
                                                   VaultType vaultType,
                                                   String vaultIdentifier,
                                                   String walletPublicKey,
                                                   ReferenceWallet walletType) throws CantRequestCryptoAddressException {
        try {
            CryptoAddress deliveredCryptoAddress;
            deliveredCryptoAddress = requestCryptoAddressByReferenceWallet(walletType);
            cryptoAddressBookManager.registerCryptoAddress(
                    deliveredCryptoAddress,
                    deliveredByActorPublicKey,
                    deliveredByActorType,
                    deliveredToActorPublicKey,
                    deliveredToActorType,
                    platform,
                    vaultType,
                    vaultIdentifier,
                    walletPublicKey,
                    walletType
            );
            System.out.println("im a delivered address: " + deliveredCryptoAddress.getAddress());
            return deliveredCryptoAddress;
        } catch (CantRequestOrRegisterCryptoAddressException e) {
            throw new CantRequestCryptoAddressException(CantRequestCryptoAddressException.DEFAULT_MESSAGE, e);
        } catch (CantRegisterCryptoAddressBookRecordException e) {
            throw new CantRequestCryptoAddressException(CantRequestCryptoAddressException.DEFAULT_MESSAGE, e, "", "Check if all the params are sended.");
        } catch(Exception exception){
            throw new CantRequestCryptoAddressException(CantRequestCryptoAddressException.DEFAULT_MESSAGE, FermatException.wrapException(exception));
        }
    }

    @Override
    public void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName) throws CantUpdateWalletContactException {
        try {
            List<CryptoAddress> cryptoAddresses = new ArrayList<>();
            cryptoAddresses.add(receivedCryptoAddress);
            walletContactsRegistry.updateWalletContact(
                    contactId,
                    actorName,
                    null,
                    null,
                    cryptoAddresses
            );
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantUpdateWalletContactException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, e);
        }  catch (Exception e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException {
        try {
            walletContactsRegistry.deleteWalletContact(contactId);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantDeleteWalletContactException e) {
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, e);
        } catch (Exception e) {
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public long getBalance(BalanceType balanceType,
                           String walletPublicKey) throws CantGetBalanceException {
        try {
            BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            return bitcoinWalletWallet.getBalance(balanceType).getBalance();
        } catch (CantLoadWalletException e) {
            throw new CantGetBalanceException(CantGetBalanceException.DEFAULT_MESSAGE, e, "", "Cant Load Wallet.");
        }  catch (CantCalculateBalanceException e) {
            throw new CantGetBalanceException(CantGetBalanceException.DEFAULT_MESSAGE, e, "", "Cant Calculate Balance of the wallet.");
        } catch(Exception e){
            throw new CantGetBalanceException(CantGetBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override

    public List<CryptoWalletTransaction> getTransactions(String intraUserLoggedInPublicKey,
                                                         BalanceType balanceType, final TransactionType transactionType,
                                                         String walletPublicKey,
                                                         int max,
                                                         int offset) throws CantListTransactionsException {
        List<CryptoWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
        try {
            if(intraUserLoggedInPublicKey!=null) {
                BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
                List<BitcoinWalletTransaction> bitcoinWalletTransactionList = bitcoinWalletWallet.listTransactions(balanceType, transactionType, max, offset);

                for (BitcoinWalletTransaction bwt : bitcoinWalletTransactionList) {
                    cryptoWalletTransactionList.add(enrichTransaction(bwt, walletPublicKey, intraUserLoggedInPublicKey));
                }
            }
            return cryptoWalletTransactionList;
        } catch(Exception e){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<CryptoWalletTransaction> listTransactionsByActor(BalanceType balanceType,
                                                                 String walletPublicKey,
                                                                 String actorPublicKey,
                                                                 String intraUserLoggedInPublicKey,
                                                                 int max,
                                                                 int offset) throws CantListTransactionsException {
        try {
            BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            List<CryptoWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
            List<BitcoinWalletTransaction> bitcoinWalletTransactionList = bitcoinWalletWallet.listTransactionsByActor(actorPublicKey, balanceType, max, offset);

            for (BitcoinWalletTransaction bwt : bitcoinWalletTransactionList) {
                cryptoWalletTransactionList.add(enrichTransaction(bwt,walletPublicKey,intraUserLoggedInPublicKey));
            }

            return cryptoWalletTransactionList;
        } catch (CantLoadWalletException | com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantListTransactionsException e) {
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public ActorTransactionSummary getActorTransactionHistory(BalanceType balanceType,
                                                              String walletPublicKey,
                                                              String actorPublicKey) throws CantGetActorTransactionHistoryException {
        try {
            BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            return constructActorTransactionSummary(bitcoinWalletWallet.getActorTransactionSummary(actorPublicKey, balanceType));
        } catch (CantLoadWalletException | CantGetActorTransactionSummaryException e) {
            throw new CantGetActorTransactionHistoryException(CantGetActorTransactionHistoryException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantGetActorTransactionHistoryException(CantGetActorTransactionHistoryException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }


    @Override
    public List<CryptoWalletTransaction> listLastActorTransactionsByTransactionType(BalanceType balanceType,
                                                                                    final TransactionType transactionType,
                                                                                    String walletPublicKey,
                                                                                    String intraUserLoggedInPublicKey,
                                                                                    int max,
                                                                                    int offset) throws CantListTransactionsException {


        List<CryptoWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
        try {
            if(intraUserLoggedInPublicKey!=null){
                BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
                List<BitcoinWalletTransaction> bitcoinWalletTransactionList = bitcoinWalletWallet.listLastActorTransactionsByTransactionType(
                        balanceType,
                      transactionType,
                       max,
                        offset
                );

                for (BitcoinWalletTransaction bwt : bitcoinWalletTransactionList) {
                    cryptoWalletTransactionList.add(enrichTransaction(bwt, walletPublicKey, intraUserLoggedInPublicKey));
                }
            }
            return cryptoWalletTransactionList;
        } catch(Exception e){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void setTransactionDescription(String walletPublicKey,
                                          UUID   transactionID,
                                          String description) throws CantSaveTransactionDescriptionException, TransactionNotFoundException {

        try {
            BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            bitcoinWalletWallet.setTransactionDescription(transactionID, description);
        } catch (CantLoadWalletException | CantStoreMemoException e) {
            throw new CantSaveTransactionDescriptionException(CantSaveTransactionDescriptionException.DEFAULT_MESSAGE, e);
        } catch (CantFindTransactionException e) {
            throw new TransactionNotFoundException(TransactionNotFoundException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantSaveTransactionDescriptionException(CantSaveTransactionDescriptionException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    private ActorTransactionSummary constructActorTransactionSummary(BitcoinWalletTransactionSummary transactionSummary) {
        return new CryptoWalletWalletModuleActorTransactionSummary(
                transactionSummary.getSentTransactionsNumber(),
                transactionSummary.getReceivedTransactionsNumber(),
                transactionSummary.getSentAmount(),
                transactionSummary.getReceivedAmount()
        );
    }

    @Override
    public void send(long cryptoAmount, CryptoAddress destinationAddress, String notes, String walletPublicKey, String deliveredByActorPublicKey, Actors deliveredByActorType, String deliveredToActorPublicKey, Actors deliveredToActorType,ReferenceWallet referenceWallet) throws CantSendCryptoException, InsufficientFundsException {
        try {

            switch (deliveredToActorType) {
                case EXTRA_USER:
                    System.out.println("Sending throw outgoing Extra User ...");
                    outgoingExtraUserManager.getTransactionManager().send(walletPublicKey, destinationAddress, cryptoAmount, notes, deliveredByActorPublicKey, deliveredByActorType, deliveredToActorPublicKey, deliveredToActorType);

                    break;
                case INTRA_USER:
                    System.out.println("Sending throw outgoing Intra Actor ...");
                    outgoingIntraActorManager.getTransactionManager().sendCrypto(walletPublicKey, destinationAddress, cryptoAmount, notes, deliveredByActorPublicKey,  deliveredToActorPublicKey,deliveredByActorType, deliveredToActorType,referenceWallet);

                    break;
            }

        }
        catch (com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.exceptions.InsufficientFundsException e) {
            throw new InsufficientFundsException(InsufficientFundsException.DEFAULT_MESSAGE, e);
        }
        catch(OutgoingIntraActorCantSendFundsExceptions| OutgoingIntraActorInsufficientFundsException ex){
            throw new CantSendCryptoException(CantSendCryptoException.DEFAULT_MESSAGE, ex);

        }
        catch (CantSendFundsException | CantGetTransactionManagerException e) {
            throw new CantSendCryptoException(CantSendCryptoException.DEFAULT_MESSAGE, e);
        }
        catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantSendCryptoException(CantSendCryptoException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<PaymentRequest> listSentPaymentRequest(String walletPublicKey,int max,int offset) throws CantListSentPaymentRequestException {
        try {
            List<PaymentRequest> lst =  new ArrayList<>();
            CryptoWalletWalletContact cryptoWalletWalletContact = null;

            //find received payment request
            for (CryptoPayment paymentRecord :  cryptoPaymentRegistry.listCryptoPaymentRequestsByType(walletPublicKey, CryptoPaymentType.SENT, max, offset)) {

                WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(paymentRecord.getActorPublicKey(),walletPublicKey);
                if (walletContactRecord != null)
                    cryptoWalletWalletContact = new CryptoWalletWalletModuleWalletContact(walletContactRecord);

                CryptoWalletWalletModulePaymentRequest cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest(
                        paymentRecord.getRequestId(),
                        convertTime(paymentRecord.getStartTimeStamp()),
                        paymentRecord.getDescription(),
                        paymentRecord.getAmount(),
                        cryptoWalletWalletContact,
                        PaymentRequest.SEND_PAYMENT,
                        paymentRecord.getState().name());
                lst.add(cryptoWalletPaymentRequest);
            }


            //TODO: Harcoder
            if(lst.size() == 0){
                CryptoWalletWalletModulePaymentRequest cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest(UUID.randomUUID(),"1 hour ago","Starbucks coffe",500000,null,PaymentRequest.SEND_PAYMENT,"accepted");
                lst.add(cryptoWalletPaymentRequest);
                cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest(UUID.randomUUID(),"2 hour ago","Hamburguer from MC donald",100000,null,PaymentRequest.SEND_PAYMENT,"accepted");

                lst.add(cryptoWalletPaymentRequest);
            }


            return lst;
        } catch (Exception e) {
            throw new CantListSentPaymentRequestException(CantListSentPaymentRequestException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }

    }

    @Override
    public List<PaymentRequest> listReceivedPaymentRequest(String walletPublicKey,int max,int offset) throws CantListReceivePaymentRequestException {

        try {
            List<PaymentRequest> lst =  new ArrayList<>();

            CryptoWalletWalletContact cryptoWalletWalletContact = null;

            //find received payment request
            for (CryptoPayment paymentRecord :  cryptoPaymentRegistry.listCryptoPaymentRequestsByType(
                    walletPublicKey,
                    CryptoPaymentType.RECEIVED,
                    max,
                    offset
            )) {

                try
                {
                    WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(
                            paymentRecord.getActorPublicKey(),
                            walletPublicKey
                    );

                    if (walletContactRecord != null)
                        cryptoWalletWalletContact = new CryptoWalletWalletModuleWalletContact(walletContactRecord);

                }
                catch(com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e)
                {
                    //not found contact, set contact null
                }
                CryptoWalletWalletModulePaymentRequest cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest(
                        paymentRecord.getRequestId(),
                        convertTime(paymentRecord.getStartTimeStamp()),
                        paymentRecord.getDescription(),
                        paymentRecord.getAmount(),
                        cryptoWalletWalletContact,
                        PaymentRequest.RECEIVE_PAYMENT,
                        paymentRecord.getState().name()
                );

                lst.add(cryptoWalletPaymentRequest);
            }

            //TODO: Harcoder
            if(lst.size() == 0) {
                CryptoWalletWalletModulePaymentRequest cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest(UUID.randomUUID(),"1 hour ago", "Starbucks coffe", 500000, null, PaymentRequest.RECEIVE_PAYMENT, "accepted");
                lst.add(cryptoWalletPaymentRequest);
                cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest(UUID.randomUUID(),"2 hour ago", "Hamburguer from MC donald", 100000, null, PaymentRequest.RECEIVE_PAYMENT, "accepted");

                lst.add(cryptoWalletPaymentRequest);
            }

            return lst;
        } catch (Exception e) {
            throw new CantListReceivePaymentRequestException(CantListSentPaymentRequestException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }


    }


    /**
     * Throw the method <code>refuseRequest</code> you can refuse a request.
     *
     * @param requestId
     * @throws CantRejectCryptoPaymentRequestException
     * @throws CryptoPaymentRequestNotFoundException
     */
    public void refuseRequest(UUID requestId) throws CantRefuseRequestPaymentException,PaymentRequestNotFoundException
    {
        try {
            cryptoPaymentRegistry.refuseRequest(requestId);
        }
        catch(CantRejectCryptoPaymentRequestException e)
        {
            throw new CantRefuseRequestPaymentException(CantRefuseRequestPaymentException.DEFAULT_MESSAGE,e);
        }
        catch(CryptoPaymentRequestNotFoundException e)
        {
            throw new PaymentRequestNotFoundException(PaymentRequestNotFoundException.DEFAULT_MESSAGE,e);
        }
        catch(Exception e)
        {
            throw new CantRefuseRequestPaymentException(CantRefuseRequestPaymentException.DEFAULT_MESSAGE,FermatException.wrapException(e));
        }
    }


    /**
     * Throw the method <code>approveRequest</code> you can approve a request and send the specified crypto.
     * @param requestId
     * @throws CantApproveCryptoPaymentRequestException
     * @throws CryptoPaymentRequestNotFoundException
     * @throws com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.InsufficientFundsException
     */

    public void approveRequest(UUID requestId) throws CantApproveRequestPaymentException,
                                                     PaymentRequestNotFoundException,
                                                     RequestPaymentInsufficientFundsException
    {
        try {
            cryptoPaymentRegistry.approveRequest(requestId);


        }
        catch(CantApproveCryptoPaymentRequestException e)
        {
            throw new CantApproveRequestPaymentException(CantApproveRequestPaymentException.DEFAULT_MESSAGE,e);
        }
        catch(CryptoPaymentRequestNotFoundException e)
        {
            throw new PaymentRequestNotFoundException(PaymentRequestNotFoundException.DEFAULT_MESSAGE,e);
        }
        catch(com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.InsufficientFundsException e)
        {
            throw new RequestPaymentInsufficientFundsException(RequestPaymentInsufficientFundsException.DEFAULT_MESSAGE,e);
        }
        catch(Exception e)
        {
            throw new CantApproveRequestPaymentException(CantApproveRequestPaymentException.DEFAULT_MESSAGE,FermatException.wrapException(e));
        }
    }




    @Override
    public List<PaymentRequest> listPaymentRequestDateOrder(String walletPublicKey,int max,int offset) throws CantListPaymentRequestDateOrderException {
        try {
            //Request order by date desc

            List<PaymentRequest> lst =  new ArrayList<>();

            CryptoWalletWalletContact cryptoWalletWalletContact = null;

//            for (CryptoPayment paymentRecord :  cryptoPaymentRegistry.listCryptoPaymentRequests(walletPublicKey, max,offset)) {
//
//                WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(paymentRecord.getActorPublicKey(),walletPublicKey);
//                if (walletContactRecord != null)
//                    cryptoWalletWalletContact = new CryptoWalletWalletModuleWalletContact(walletContactRecord);
//
//                CryptoWalletWalletModulePaymentRequest cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest(convertTime(paymentRecord.getStartTimeStamp()),paymentRecord.getDescription(),paymentRecord.getAmount(),cryptoWalletWalletContact,PaymentRequest.SEND_PAYMENT,paymentRecord.getState().name());
//                lst.add(cryptoWalletPaymentRequest);
//            }
//            //TODO: Harcoder
//            CryptoWalletWalletModulePaymentRequest cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("1 hour ago","Starbucks coffe",500000,null,PaymentRequest.SEND_PAYMENT,"accepted");
//            lst.add(cryptoWalletPaymentRequest);
//            cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("1 hour ago","Hamburguer from MC donald",100000,null,PaymentRequest.SEND_PAYMENT,"accepted");
//            lst.add(cryptoWalletPaymentRequest);
//
//            cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("1 hour ago","Starbucks coffe",500000,null,PaymentRequest.RECEIVE_PAYMENT,"accepted");
//            lst.add(cryptoWalletPaymentRequest);
//            cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("2 hour ago","Hamburguer from MC donald",100000,null,PaymentRequest.RECEIVE_PAYMENT,"accepted");
//            lst.add(cryptoWalletPaymentRequest);


            return lst;
        } catch (Exception e) {
            throw new CantListPaymentRequestDateOrderException(CantListSentPaymentRequestException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public boolean isValidAddress(CryptoAddress cryptoAddress) {
        return cryptoVaultManager.isValidAddress(cryptoAddress);
    }

    @Override
    public List<CryptoWalletIntraUserIdentity> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListCryptoWalletIntraUserIdentityException {

        try {

            List<CryptoWalletIntraUserIdentity> cryptoWalletIntraUserIdentityList = new  ArrayList<CryptoWalletIntraUserIdentity>();

            for (IntraWalletUserIdentity intraWalletUser : this.intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser()) {

                CryptoWalletIntraUserIdentity cryptoWalletIntraUserIdentity = new CryptoWalletWalletIntraUserIdentity(intraWalletUser.getPublicKey(),intraWalletUser.getAlias(),intraWalletUser.getProfileImage());

                cryptoWalletIntraUserIdentityList.add(cryptoWalletIntraUserIdentity);
            }

            //TODO Harcoder
            cryptoWalletIntraUserIdentityList.add(new CryptoWalletWalletIntraUserIdentity("afd0647a-87de-4c56-9bc9-be736e0c5059", "mati", new byte[0]));


            return cryptoWalletIntraUserIdentityList;

        }
        catch(CantListIntraWalletUsersException e)
        {
            throw new CantListCryptoWalletIntraUserIdentityException(CantListIntraWalletUsersException.DEFAULT_MESSAGE,e,"","");
        }
        catch(Exception e)
        {
            throw new CantListCryptoWalletIntraUserIdentityException(CantListIntraWalletUsersException.DEFAULT_MESSAGE,e,"","unknown error");
        }

    }

    @Override
    public List<IntraWalletUserIdentity> getActiveIdentities() {

        try{

            return intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private String createActor(String actorName, Actors actorType, byte[] photo) throws CantCreateOrRegisterActorException {
        switch (actorType){
            case EXTRA_USER:
                try {
                    Actor actor = extraUserManager.createActor(actorName, photo);
                    return actor.getActorPublicKey();
                } catch (CantCreateExtraUserException e) {
                    throw new CantCreateOrRegisterActorException(CantCreateOrRegisterActorException.DEFAULT_MESSAGE, e, "", "Check if all the params are sended.");
                }
            case INTRA_USER:
                // THERE'S NO NEED TO CREATE A NEW INTRA ACTOR. PLEASE DONT ADD ANYTHING HERE

            default:
                throw new CantCreateOrRegisterActorException(CantCreateOrRegisterActorException.DEFAULT_MESSAGE, null, "", "ActorType is not Compatible.");
        }
    }


    private String createActor(String actorName, Actors actorType) throws CantCreateOrRegisterActorException {
        switch (actorType){
            case EXTRA_USER:
                try {
                    Actor actor = extraUserManager.createActor(actorName);
                    return actor.getActorPublicKey();
                } catch (CantCreateExtraUserException e) {
                    throw new CantCreateOrRegisterActorException(CantCreateOrRegisterActorException.DEFAULT_MESSAGE, e, "", "Check if all the params are sended.");
                }
            default:
                throw new CantCreateOrRegisterActorException(CantCreateOrRegisterActorException.DEFAULT_MESSAGE, null, "", "ActorType is not Compatible.");
        }
    }

    private CryptoAddress requestCryptoAddressByReferenceWallet(ReferenceWallet referenceWallet) throws CantRequestOrRegisterCryptoAddressException {
        switch (referenceWallet){
            case BASIC_WALLET_BITCOIN_WALLET:
                return cryptoVaultManager.getAddress();
            default:
                throw new CantRequestOrRegisterCryptoAddressException(CantRequestOrRegisterCryptoAddressException.DEFAULT_MESSAGE, null, "", "ReferenceWallet is not Compatible.");
        }
    }

    private CryptoWalletTransaction enrichTransaction(BitcoinWalletTransaction bitcoinWalletTransaction, String walletPublicKey, String intraUserLoggedInPublicKey) throws CantEnrichTransactionException {
        try {
            Actor involvedActor = null;
            UUID contactId = null;
            switch (bitcoinWalletTransaction.getTransactionType()) {
                case CREDIT:
                    try {
                        involvedActor = getActorByActorPublicKeyAndType(bitcoinWalletTransaction.getActorToPublicKey(), bitcoinWalletTransaction.getActorToType(),intraUserLoggedInPublicKey);
                        WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(bitcoinWalletTransaction.getActorToPublicKey(),walletPublicKey);
                        if (walletContactRecord != null)
                            contactId = walletContactRecord.getContactId();

                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
                        throw new CantEnrichTransactionException(CantEnrichTransactionException.DEFAULT_MESSAGE, e, "Cant get Contact Information", "");
                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
                        contactId = null;
                    }
                    break;
                case DEBIT:
                    try {
                        involvedActor = getActorByActorPublicKeyAndType(bitcoinWalletTransaction.getActorToPublicKey(), bitcoinWalletTransaction.getActorToType(),intraUserLoggedInPublicKey);
                        WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(bitcoinWalletTransaction.getActorFromPublicKey(), walletPublicKey);
                        if (walletContactRecord != null)
                            contactId = walletContactRecord.getContactId();

                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
                        throw new CantEnrichTransactionException(CantEnrichTransactionException.DEFAULT_MESSAGE, e, "Cant get Contact Information", "");
                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
                        contactId = null;
                    }
                    break;
            }
            return new CryptoWalletWalletModuleTransaction(bitcoinWalletTransaction, contactId, involvedActor);
        } catch (Exception e) {
            throw new CantEnrichTransactionException(CantEnrichTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }

    private Actor getActorByActorPublicKeyAndType(String actorPublicKey, Actors actorType, String intraUserLoggedInPublicKey) throws CantGetActorException {
        Actor actor;
        switch (actorType) {
            case EXTRA_USER:
                try {
                    actor = extraUserManager.getActorByPublicKey(actorPublicKey);
                    return actor;
                } catch (CantGetExtraUserException | ExtraUserNotFoundException e) {
                    throw new CantGetActorException(CantGetActorException.DEFAULT_MESSAGE, e, null, "Cant get Extra User on DataBase");
                }
            case INTRA_USER:
                try {
                    //find actor connected with logget identity
                    actor = intraUserManager.getActorByPublicKey(intraUserLoggedInPublicKey,actorPublicKey);
                    return actor;
                } catch (CantGetIntraUserException| IntraUserNotFoundException e) {
                    throw new CantGetActorException(CantGetActorException.DEFAULT_MESSAGE, e, null, "Cant get Intra User on DataBase");
                }

            default:
                throw new CantGetActorException(CantGetActorException.DEFAULT_MESSAGE, null, null, null);
        }
    }

    @Override
    public final void sendCryptoPaymentRequest(final String                walletPublicKey  ,
                                               final String                identityPublicKey,
                                               final Actors                identityType     ,
                                               final String                actorPublicKey   ,
                                               final Actors                actorType        ,
                                               final CryptoAddress         cryptoAddress    ,
                                               final String                description      ,
                                               final long                  amount           ,
                                               final BlockchainNetworkType networkType      ) throws CantSendCryptoPaymentRequestException {

        try {

            cryptoPaymentRegistry.generateCryptoPaymentRequest(
                    walletPublicKey,
                    identityPublicKey,
                    identityType,
                    actorPublicKey,
                    actorType,
                    cryptoAddress,
                    description,
                    amount,
                    networkType
            );
        } catch (CantGenerateCryptoPaymentRequestException e) {

            throw new CantSendCryptoPaymentRequestException(e, "", "Error found in crypto payment request plugin.");
        } catch (Exception e) {

            throw new CantSendCryptoPaymentRequestException(e, "", "Unhandled error.");
        }

    }

    private  String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(date);
    }
}
