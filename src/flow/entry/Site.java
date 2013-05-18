package flow.entry;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Site implements Parcelable {
	
	public String siteType;
	public String name;
	public String apiSiteParameter;
	
	public static Site[] parseJson(String json) throws JSONException {
		
		if (json == null) {
			
			return null;
			
		}
		
		
		JSONObject object = new JSONObject(json);
		
		JSONArray array = object.optJSONArray("items");
		
		if (array == null) {
			
			return null;
			
		}
		
		int count = array.length();
		
		Site[] sites = new Site[count];
		
		for (int i = 0; i < count; i++) {
			
			JSONObject jsonSite = array.optJSONObject(i);
			
			
			if (jsonSite == null) {
				
				sites[i] = null;
				
			} else {



				Site site = new Site();
				sites[i] = site;
				site.siteType = jsonSite.optString("site_type");
				site.name = jsonSite.optString("name");
				site.apiSiteParameter = jsonSite.optString("api_site_parameter");

			}
			
		}
		
		return sites;
		
		
		
		
		
	}

	@Override
	public int describeContents() {
		
		return 0;
		
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(siteType);
		dest.writeString(name);
		dest.writeString(apiSiteParameter);
		
		
	}
	
	public static final Creator<Site> CREATOR = new Creator<Site>() {

		@Override
		public Site createFromParcel(Parcel source) {
			
			Site site = new Site();
			site.siteType = source.readString();
			site.name = source.readString();
			site.apiSiteParameter = source.readString();
			
			return site;
			
		}

		@Override
		public Site[] newArray(int size) {
			
			return new Site[size];
			
			
		}
		
		
		
		
		
		
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
