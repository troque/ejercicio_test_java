package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantSingMessageException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;

/**
 * Created by franklin on 26/09/15.
 */
public class AssetIssuerIdentity implements IdentityAssetIssuer{
    String alias;
    String publicKey;
    String mensage;
    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public byte[] getProfileImage() {
        return new byte[0];
    }

    @Override
    public void setNewProfileImage(byte[] newProfileImage) {

    }

    @Override
    public String createMessageSignature(String mensage) {
        return this.mensage;
    }

    public void setAlias(String alias){
        this.alias = alias;
    }

    public void setPublicKey(String publicKey){
        this.publicKey = publicKey;
    }
}
