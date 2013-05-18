package flow.entry;

import org.json.JSONObject;

public class User {

	public long userId;
	public String displayName;
	public int acceptRate;
	public int reputation;
	
	public static User parserJsonObject(JSONObject object) {

		if (object == null) {

			return null;

		}

		User owner = new User();

		owner.displayName = object.optString("display_name");
		owner.reputation = object.optInt("reputation");
		owner.userId = object.optLong("user_id");
		owner.acceptRate = object.optInt("accept_rate");
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		return owner;

	}

}
