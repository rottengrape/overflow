package flow.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import flow.activity.EditMainSiteActivity;
import flow.activity.R;
import flow.adapter.MainSiteAdapter;
import flow.entry.Site;
import flow.persistence.LocalCache;

public class MainSiteFragment extends ListFragment implements
        LoaderCallbacks<Site[]>, OnClickListener {

    private Context context;

    private final int addCode = 0;
    private final int deleteCode = 1;
    private final int viewCode = 2;


    public static MainSiteFragment newInstance(Context context
    ) {

        MainSiteFragment fragment = new MainSiteFragment(context
        );

        fragment.setHasOptionsMenu(true);

        return fragment;

    }

    public MainSiteFragment(Context context
    ) {

        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.site, null, false);

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.add(Menu.NONE, addCode, Menu.NONE, R.string.add);
        menu.add(deleteCode, deleteCode, Menu.NONE, R.string.delete_mode);
        menu.add(viewCode, viewCode, Menu.NONE, R.string.view_mode);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        MainSiteAdapter adapter = (MainSiteAdapter) getListAdapter();

        if (adapter == null) {

            menu.setGroupVisible(deleteCode, false);
            menu.setGroupVisible(viewCode, false);

        } else if (adapter.deleteMode) {

            menu.setGroupVisible(deleteCode, false);
            menu.setGroupVisible(viewCode, true);


        } else {

            menu.setGroupVisible(deleteCode, true);
            menu.setGroupVisible(viewCode, false);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case addCode:

                Intent intent = new Intent(context, EditMainSiteActivity.class);
                startActivityForResult(intent, 0);

                break;

            case deleteCode: {

                MainSiteAdapter adapter = (MainSiteAdapter) getListAdapter();

                if (adapter != null && !adapter.deleteMode) {

                    adapter.deleteMode = true;
                }
            }

            break;

            case viewCode: {

                MainSiteAdapter adapter = (MainSiteAdapter) getListAdapter();


                if (adapter != null && adapter.deleteMode) {

                    adapter.deleteMode = false;
                    adapter.onCancel();
                }

            }

            break;

            default:

                break;

        }

        return true;

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Loader<Site> loader = getLoaderManager().getLoader(0);

        if (loader != null) {

            loader.forceLoad();


        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<Site[]> onCreateLoader(int id, Bundle args) {

        final Bundle bundle = args;

        AsyncTaskLoader<Site[]> fresh = new AsyncTaskLoader<Site[]>(
                getActivity()) {

            @Override
            public Site[] loadInBackground() {

                LocalCache cache = LocalCache.getInstance(context);

                if (bundle != null) {

                    Site[] deleteSites = (Site[]) bundle.get("deleteSites");
                    cache.deleteSites(deleteSites);

                }

                Site[] sites = cache.selectSites();


                return sites;

            }

        };

        fresh.forceLoad();

        return fresh;

    }

    @Override
    public void onLoadFinished(Loader<Site[]> loader, Site[] data) {

        Log.e("MainSiteFragment", "#onLoadFinished");

        if (data != null) {

            MainSiteAdapter adapter = (MainSiteAdapter) getListAdapter();

            if (adapter != null) {
                adapter.onSwapSites(data);


            } else {

                adapter = new MainSiteAdapter(context, data);
                setListAdapter(adapter);

            }


        } else {

            setListAdapter(null);

        }

    }

    @Override
    public void onLoaderReset(Loader<Site[]> loader) {

        Log.e("MainSiteFragment", "#onLoaderReset");


    }

    @Override
    public void onClick(View v) {

    }

}
