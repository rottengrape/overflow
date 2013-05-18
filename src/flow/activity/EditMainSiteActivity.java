package flow.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import flow.ui.EditMainSiteFragment;

public class EditMainSiteActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_container);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        EditMainSiteFragment fragment = EditMainSiteFragment.newInstance(this);
        transaction.add(R.id.ll_select, fragment);
        transaction.commit();


    }


}
