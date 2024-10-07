package unit.com.bitdubai.reference_wallet.crypto_broker_wallet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardConfiguration;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by nelson on 21/09/15.
 */
public class TestActivity extends Activity implements WizardConfiguration {
    public static final int LAYOUT_ID = android.R.id.content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LinearLayout view = new LinearLayout(this);
        view.setId(LAYOUT_ID);
        setContentView(view);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showWizard(WizardTypes key, Object... args) {
        // DO NOTHING...
    }
}
