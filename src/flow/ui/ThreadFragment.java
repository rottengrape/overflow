package flow.ui;

import flow.activity.PreferActivity;
import flow.activity.QuestionActivity;
import flow.activity.R;
import flow.adapter.ThreadAdapter;
import flow.entry.Question;
import flow.utility.StackClient;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ThreadFragment extends ListFragment implements
        LoaderCallbacks<Question[]>, OnClickListener {
    private int page;

    private String apiSiteParameter;

    private Context context;
    private RelativeLayout bar;
    private Button previousPage;
    private Button nextPage;

    public static ThreadFragment newInstance(Context context, int page) {

        ThreadFragment fragment = new ThreadFragment(context, page);

        fragment.setHasOptionsMenu(true);

        return fragment;


    }

    private ThreadFragment(Context context, int page) {

        this.page = page;
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.thread, null, false);


        bar = (RelativeLayout) inflater.inflate(R.layout.bar, null, false);
        previousPage = (Button) bar.findViewById(R.id.b_previous_page);
        nextPage = (Button) bar.findViewById(R.id.b_next_page);

        bar.setOnClickListener(this);
        previousPage.setOnClickListener(this);
        nextPage.setOnClickListener(this);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        getListView().addHeaderView(bar);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.add(R.string.preference);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(context, PreferActivity.class);
        startActivity(intent);

        return true;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

                                                                     getLoaderManager().initLoader(0,null,this)             ;


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra("questionId", id);
        intent.putExtra("apiSiteParameter", apiSiteParameter);

        startActivity(intent);

    }

    @Override
    public Loader<Question[]> onCreateLoader(int id, Bundle args) {

        final Bundle bundle = args;


        AsyncTaskLoader<Question[]> fresh = new AsyncTaskLoader<Question[]>(
                getActivity()) {

            @Override
            public Question[] loadInBackground() {

                StackClient client = StackClient.getInstance();

                Question[] questions = null;

                try {

                    if (bundle != null) {

                        apiSiteParameter = bundle.getString("apiSiteParameter");

                    }

                    if (apiSiteParameter == null || apiSiteParameter.equals("")) {

                        apiSiteParameter = "stackoverflow.com";

                    }

                    questions = client.questions(apiSiteParameter, page);

                } catch (JSONException e) {

                }

                return questions;

            }
        };

        fresh.forceLoad();

        return fresh;

    }

    @Override
    public void onLoadFinished(Loader<Question[]> loader, Question[] data) {


        if (data != null) {


            ThreadAdapter adapter = (ThreadAdapter) getListAdapter();

            if (adapter != null) {
                adapter.onSwapQuestions(data);
            } else {

                adapter = new ThreadAdapter(context, data);
                setListAdapter(adapter);

            }

        } else {

            setListAdapter(null);

        }

    }

    @Override
    public void onLoaderReset(Loader<Question[]> loader) {


    }

    @Override
    public void onClick(View v) {

        Loader<Question[]> loader = getLoaderManager().getLoader(0);

        switch (v.getId()) {

            case R.id.b_previous_page:

                if (page > 0) {
                    page--;

                }

                if (page == 0) {

                    previousPage.setVisibility(View.GONE);


                }

                if (loader != null) {

                    loader.forceLoad();

                }

                break;

            case R.id.b_next_page:

                page++;

                if (page > 0 && previousPage.getVisibility() == View.GONE) {

                    previousPage.setVisibility(View.VISIBLE);

                }

                if (loader != null) {

                    loader.forceLoad();

                }

                break;

            default:

                break;


        }

    }

}
