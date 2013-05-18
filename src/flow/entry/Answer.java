package flow.entry;

import org.json.JSONArray;
import org.json.JSONObject;

public class Answer {

	public long answerId;
	public long lastActivityDate;
	public int score;
	public boolean isAccepted;
	public User owner;
	public Comment[] comments;
	public String body;

	public static Answer[] parseJsonArray(JSONArray array) {
		
		if (array == null) {
			
			return null;
			
		}

		Answer[] answers = null;
		int count = array.length();
		
		if (count == 0) {
			
			return null;
			
		}
		
		
		answers = new Answer[count];

		for (int i = 0; i < count; i++) {

			JSONObject jsonAnswer = array.optJSONObject(i);
			answers[i] = parserJsonObject(jsonAnswer);

		}

		return answers;

	}

	public static Answer parserJsonObject(JSONObject object) {

        if (object == null) {
			
			return null;
			
		}

		Answer a = new Answer();
		a.answerId = object.optLong("answer_id");
		a.isAccepted = object.optBoolean("is_accepted");
		a.lastActivityDate = object.optLong("last_activity_date");
		a.score = object.optInt("score");
		a.body = object.optString("body");
		a.owner = User.parserJsonObject(object.optJSONObject("owner"));
		a.comments = Comment.parseJsonArray(object.optJSONArray("comments"));

		
		

		return a;

	}

}
