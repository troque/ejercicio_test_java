package unit.com.bitdubai.sub_app.crypto_customer_community;

import android.app.Activity;
import android.os.Bundle;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardConfiguration;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;

/**
 * Created by nelson on 21/09/15.
 */
public class TestActivity extends Activity implements WizardConfiguration {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showWizard(WizardTypes key, Object... args) {
        // DO NOTHING...
    }
}
