package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces.BrokerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces.CryptoBrokerActorManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerActorDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure.BrokerIdentityWalletRelationshipInformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 19/11/15.
 */
public class CryptoBrokerActorDao implements CryptoBrokerActorManager {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;

    /*
        Builders
    */

        public CryptoBrokerActorDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
            this.pluginId = pluginId;
        }

    /*
        Public methods
     */

        public void initializeDatabase() throws CantInitializeCryptoBrokerActorDatabaseException{
            try {
                database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCryptoBrokerActorDatabaseException(cantOpenDatabaseException.getMessage());
            } catch (DatabaseNotFoundException e) {
                CryptoBrokerActorDatabaseFactory BrokerActorDatabaseFactory = new CryptoBrokerActorDatabaseFactory(pluginDatabaseSystem);
                try {
                    database = BrokerActorDatabaseFactory.createDatabase(pluginId, pluginId.toString());
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    throw new CantInitializeCryptoBrokerActorDatabaseException(cantCreateDatabaseException.getMessage());
                }
            }
        }

        @Override
        public BrokerIdentityWalletRelationship createNewBrokerIdentityWalletRelationship(ActorIdentity identity, UUID wallet) throws CantCreateNewBrokerIdentityWalletRelationshipException {

            try {
                DatabaseTable RelationshipTable = this.database.getTable(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_TABLE_NAME);
                DatabaseTableRecord recordToInsert   = RelationshipTable.getEmptyRecord();
                UUID relationshipId = UUID.randomUUID();
                loadRecordAsNew(
                        recordToInsert,
                        relationshipId,
                        identity.getPublicKey(),
                        wallet
                );
                RelationshipTable.insertRecord(recordToInsert);
                return constructCryptoBrokerActorRelationshipFromRecord(recordToInsert);
            } catch (CantInsertRecordException e) {
                throw new CantCreateNewBrokerIdentityWalletRelationshipException(CantCreateNewBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantCreateNewBrokerIdentityWalletRelationshipException(CantCreateNewBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantCreateNewBrokerIdentityWalletRelationshipException(CantCreateNewBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        @Override
        public Collection<BrokerIdentityWalletRelationship> getAllBrokerIdentityWalletRelationship() throws CantGetListBrokerIdentityWalletRelationshipException {
            try {
                DatabaseTable RelationshipTable = this.database.getTable(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_TABLE_NAME);
                RelationshipTable.loadToMemory();
                List<DatabaseTableRecord> records = RelationshipTable.getRecords();
                RelationshipTable.clearAllFilters();
                Collection<BrokerIdentityWalletRelationship> resultados = new ArrayList<BrokerIdentityWalletRelationship>();
                for (DatabaseTableRecord record : records) {
                    resultados.add(constructCryptoBrokerActorRelationshipFromRecord(record));
                }
                return resultados;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        @Override
        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByIdentity(ActorIdentity identity) throws CantGetListBrokerIdentityWalletRelationshipException {
            try {
                DatabaseTable RelationshipTable = this.database.getTable(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_TABLE_NAME);
                RelationshipTable.setStringFilter(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_BROKER_PUBLIC_KEY_COLUMN_NAME, identity.getPublicKey(), DatabaseFilterType.EQUAL);
                RelationshipTable.loadToMemory();
                List<DatabaseTableRecord> records = RelationshipTable.getRecords();
                RelationshipTable.clearAllFilters();
                for (DatabaseTableRecord record : records) {
                    return  constructCryptoBrokerActorRelationshipFromRecord(record);
                }
                return null;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        @Override
        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByWallet(UUID wallet) throws CantGetListBrokerIdentityWalletRelationshipException {
            try {
                DatabaseTable RelationshipTable = this.database.getTable(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_TABLE_NAME);
                RelationshipTable.setUUIDFilter(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_BROKER_PUBLIC_KEY_COLUMN_NAME, wallet, DatabaseFilterType.EQUAL);
                RelationshipTable.loadToMemory();
                List<DatabaseTableRecord> records = RelationshipTable.getRecords();
                RelationshipTable.clearAllFilters();
                for (DatabaseTableRecord record : records) {
                    return  constructCryptoBrokerActorRelationshipFromRecord(record);
                }
                return null;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            }
        }

    /*
        Private methods
     */

        private void loadRecordAsNew(
                DatabaseTableRecord databaseTableRecord,
                UUID   relationshipId,
                String publicKeyBroker,
                UUID   walletId
        ) {
            databaseTableRecord.setUUIDValue(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME, relationshipId);
            databaseTableRecord.setStringValue(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
            databaseTableRecord.setUUIDValue(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_WALLET_COLUMN_NAME, walletId);
        }

        private BrokerIdentityWalletRelationship newCryptoBrokerActorRelationship(
                UUID   relationshipId,
                String publicKeyBroker,
                UUID   walletId
        ){
            return new BrokerIdentityWalletRelationshipInformation(relationshipId, publicKeyBroker, walletId);
        }

        private BrokerIdentityWalletRelationship constructCryptoBrokerActorRelationshipFromRecord(DatabaseTableRecord record) throws InvalidParameterException, CantGetListClauseException {
            UUID    relationshipId       = record.getUUIDValue(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME);
            String  publicKeyBroker     = record.getStringValue(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_BROKER_PUBLIC_KEY_COLUMN_NAME);
            UUID    walletId            = record.getUUIDValue(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_WALLET_COLUMN_NAME);
            return newCryptoBrokerActorRelationship(relationshipId, publicKeyBroker, walletId);
        }

}
