package flow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.*;
import flow.task.ThreadTask;
import flow.ui.MainSiteFragment;
import flow.utility.ParamHolder;

public class ThreadActivity extends FragmentActivity
        implements AdapterView.OnItemClickListener,View.OnClickListener {


    private ListView thread;
    private LinearLayout sidebar;
    private ImageButton favo;
    private String apiSiteParameter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

                      obtainView();

        obtainListener();
                            apiSiteParameter = ParamHolder.getHost();

        ThreadTask task = new ThreadTask(thread
                ,this)    ;
        task.execute(apiSiteParameter)                              ;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.ll_sidebar, MainSiteFragment.newInstance(this))          ;
        transaction.commit();



    }

    private void obtainView(){
        thread = (ListView)       findViewById(R.id.lv_thread)        ;
        favo = (ImageButton)                         findViewById(R.id.b_favo)        ;
        sidebar = (LinearLayout)findViewById(R.id.ll_sidebar);



    }



    private void obtainListener()   {
        thread.setOnItemClickListener(this);
        favo.setOnClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        Intent intent = new Intent(this,QuestionActivity.class)   ;
        intent.putExtra("questionId",id)        ;




              intent.putExtra("apiSiteParameter",apiSiteParameter)    ;
        startActivity(intent);

    }


    @Override
    public void onClick(View v) {

        if(sidebar.getVisibility()==View.GONE)        {


                    sidebar.setVisibility(View.VISIBLE);
            favo.setImageResource(R.drawable.back);
        }                 else {
                           sidebar.setVisibility(View.GONE);
            favo.setImageResource(R.drawable.favo_site);


        }




    }
}
