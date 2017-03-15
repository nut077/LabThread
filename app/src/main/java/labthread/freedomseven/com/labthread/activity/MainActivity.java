package labthread.freedomseven.com.labthread.activity;

import android.databinding.DataBindingUtil;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import labthread.freedomseven.com.labthread.R;
import labthread.freedomseven.com.labthread.databinding.ActivityMainBinding;
import labthread.freedomseven.com.labthread.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ininitInstances();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance())
                    .commit();
        }
    }

    private void ininitInstances() {
        setSupportActionBar(binding.toolBar);
    }
}
