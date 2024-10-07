package com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.createwallet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.APIException;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.HttpClient;
/**
 * This class reflects the functionality documented
 * at https://blockchain.info/api/create_walleti. It allows users to create new wallets
 * as long as their API code has the 'generate wallet' permission.
 *
 */

//code:25c34cee-1129-4e53-bf49-726530b0cc58
public class CreateWallet
{
    /**
     * Creates a new Blockchain.info wallet.
     * @param password Password for the new wallet. At least 10 characters.
     * @param apiCode API code with create wallets permission
     * @return An instance of the CreateWalletResponse class
     * @throws IOException
     * @throws APIException If the server returns an error
     */
    public static CreateWalletResponse create(String password, String apiCode, String privatekey)
            throws IOException, APIException
    {
        return create(password, apiCode, privatekey, null, null);
    }

    /**
     * Creates a new Blockchain.info wallet. It can be created containing a pre-generated
     * private key or will otherwise generate a new private key.
     * @param password Password for the new wallet. At least 10 characters.
     * @param apiCode API code with create wallets permission
     * @param privateKey Private key to add to the wallet (optional, nullable)
     * @param label Label for the first address in the wallet (optional, nullable)
     * @param email Email to associate with the new wallet (optional, nullable)
     * @return An instance of the CreateWalletResponse class
     * @throws IOException
     * @throws APIException If the server returns an error
     */
    public static CreateWalletResponse create(String password, String apiCode,
                                              String privateKey, String label, String email) throws IOException, APIException
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("testnet", "true");
        params.put("password", password);
        params.put("api_code", apiCode);
        if (privateKey != null)
            params.put("priv", privateKey);
        if (label != null)
            params.put("label", label);
        if (email != null)
            params.put("email", email);

        String response = HttpClient.post("api/v2/create_wallet", params);
        JsonObject jsonObj = new JsonParser()
                .parse(response)
                .getAsJsonObject();

        return new CreateWalletResponse(
                jsonObj.get("guid").getAsString(),
                jsonObj.get("address").getAsString(),
                jsonObj.get("link").getAsString());
    }
}