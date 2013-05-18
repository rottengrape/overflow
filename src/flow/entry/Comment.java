package flow.entry;

import org.json.JSONArray;
import org.json.JSONObject;


public class Comment {
	
	public String body;
	public long commentId;
	public long creationDate;
	public User owner;
	public User replyToUser;
	public int score;
	
	public static Comment[] parseJsonArray(JSONArray array) {

		if (array == null) {
			
			return null;
			
		}
		
		
		int count = array.length();

		if (count == 0) {
			
			return null;
			
		}


		Comment[] comments = new Comment[count];


		for (int i = 0; i < count; i++) {

			JSONObject object = array.optJSONObject(i);
			
			Comment c = new Comment();
			comments[i] = c;
			c.body = object.optString("body");
			c.commentId = object.optLong("comment_id");
			c.creationDate = object.optLong("creation_date");
			c.score = object.optInt("score");
			c.owner = User.parserJsonObject(object.optJSONObject("owner"));
			c.replyToUser = User.parserJsonObject(object.optJSONObject("reply_to_user"));


		}
		
		
		
		return comments;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
