package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_cbp_api.all_definition.enums.IdentityPublished;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;

/**
 * Created by jorge on 14-10-2015.
 */
public class CryptoBrokerIdentityInformationImpl implements CryptoBrokerIdentityInformation {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 3307;
    private static final int HASH_PRIME_NUMBER_ADD = 4153;

    private final String alias;
    private final String publicKey;
    private final byte[] profileImage;
    private final boolean published;

    public CryptoBrokerIdentityInformationImpl(final String alias, final String publicKey, final byte[] profileImage, final boolean published){
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.published = published;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public byte[] getProfileImage() {
        return this.profileImage;
    }

    @Override
    public boolean isPublished(){ return this.published; }

    public boolean equals(Object o){
        if(!(o instanceof CryptoBrokerIdentityInformation))
            return false;
        CryptoBrokerIdentityInformation compare = (CryptoBrokerIdentityInformation) o;
        return alias.equals(compare.getAlias()) && this.publicKey.equals(compare.getPublicKey());
    }

    @Override
    public int hashCode(){
        int c = 0;
        c += alias.hashCode();
        c += publicKey.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}
