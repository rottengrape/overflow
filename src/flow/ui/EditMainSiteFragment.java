
package flow.ui;

import flow.activity.R;
import flow.adapter.EditMainSiteAdapter;
import flow.entry.Site;
import flow.persistence.LocalCache;
import flow.utility.StackClient;
import org.json.JSONException;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class EditMainSiteFragment extends ListFragment implements
        LoaderCallbacks<Site[]>, OnClickListener {

    private Context context;
    private LinearLayout bar;
    private Button update;
    private Button cancel;


    public static EditMainSiteFragment newInstance(Context context) {

        EditMainSiteFragment fragment = new EditMainSiteFragment(context);

        return fragment;

    }

    public EditMainSiteFragment(Context context) {

        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_site_chooser, null, false);

        bar = (LinearLayout) view.findViewById(R.id.bar);
        update = (Button) view.findViewById(R.id.b_update);
        cancel = (Button) view.findViewById(R.id.b_cancel);


        bar.setOnClickListener(this);
        update.setOnClickListener(this);
        cancel.setOnClickListener(this);


        return view;


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        EditMainSiteAdapter adapter = (EditMainSiteAdapter) getListAdapter();

        adapter.onclick(position);

        if (adapter.selectedSize() == 0 && bar.getVisibility() == View.VISIBLE) {

            bar.setVisibility(View.GONE);


        } else if (adapter.selectedSize() == 1 && bar.getVisibility() == View.GONE) {

            bar.setVisibility(View.VISIBLE);


        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


        getLoaderManager().initLoader(0, null, this);


    }

    @Override
    public Loader<Site[]> onCreateLoader(int id, Bundle args) {

        AsyncTaskLoader<Site[]> fresh = new AsyncTaskLoader<Site[]>(
                getActivity()) {

            @Override
            public Site[] loadInBackground() {

                StackClient client = StackClient.getInstance();
                Site[] sites = null;

                try {

                    sites = client.sites();

                } catch (JSONException e) {

                }

                return sites;

            }

        };

        fresh.forceLoad();

        return fresh;

    }

    @Override
    public void onLoadFinished(Loader<Site[]> loader, Site[] data) {

        if (data != null) {

            EditMainSiteAdapter adapter = new EditMainSiteAdapter(context, data);

            setListAdapter(adapter);

        }

    }

    @Override
    public void onLoaderReset(Loader<Site[]> loader) {

        Log.e("logcat", "#onLoaderReset");


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.b_update:

            {

                bar.setVisibility(View.GONE);

                EditMainSiteAdapter adapter = (EditMainSiteAdapter) getListAdapter();
                final Site[] fresh = adapter.onUpdate();

                Runnable runnable = new Runnable() {

                    @Override
                    public void run() {

                        LocalCache cache = LocalCache.getInstance(context);
                        cache.updateSites(fresh);


                    }
                };

                Thread thread = new Thread(runnable);
                thread.start();
            }
            break;

            case R.id.b_cancel: {
                EditMainSiteAdapter adapter = (EditMainSiteAdapter) getListAdapter();
                adapter.onCancel();

                bar.setVisibility(View.GONE);


            }
            break;

            default:

                break;


        }


    }

}
