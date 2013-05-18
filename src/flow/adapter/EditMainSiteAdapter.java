package flow.adapter;

import java.util.HashSet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import flow.activity.R;
import flow.entry.Site;

public class EditMainSiteAdapter extends BaseAdapter {

    private Context context;
    private Site[] sites;
    private LayoutInflater inflater;
    private HashSet<Site> selectedSites;


    public EditMainSiteAdapter(Context context, Site[] sites) {

        this.sites = sites;
        this.context = context;
        inflater = LayoutInflater.from(context);
        selectedSites = new HashSet<Site>();

    }

    @Override
    public int getCount() {

        return sites.length;

    }

    @Override
    public Object getItem(int position) {

        return sites[position];

    }


    @Override
    public long getItemId(int position) {

        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.edit_item, null, false);

        }
        TextView view = (TextView) convertView;

        Site site = sites[position];

        if (site != null) {

            view.setText(site.name);

        }

        if (selectedSites.contains(sites[position])) {

            view.setTextColor(0xffff0000);


        } else {

            view.setTextColor(0xff00ff00);

        }

        return convertView;

    }

    public Site[] onUpdate() {

        Site[] fresh = new Site[selectedSites.size()];
        selectedSites.toArray(fresh);
        selectedSites.clear();

        notifyDataSetChanged();

        return fresh;

    }

    public void onCancel() {

        selectedSites.clear();

        notifyDataSetChanged();

    }

    public int selectedSize() {

        return selectedSites.size();


    }


    public void onclick(int position) {


        if (selectedSites.contains(sites[position])) {

            selectedSites.remove(sites[position]);


        } else {

            selectedSites.add(sites[position]);

        }

        notifyDataSetChanged();


    }

}
