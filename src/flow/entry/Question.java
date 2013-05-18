package flow.entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Question {

	public long questionId;
	public long lastActivityDate;
	public int score;
	public int answerCount;
	public long acceptedAnswerId;
	public String title;
	public String[] tags;
	public int viewCount;
	public User owner;
	public boolean isAnswered;
	public Answer[] answers;
	public String body;
	public int bountyAmount;
	public long bountyCloseDate;
	public Comment[] comments;

	public static Question[] parseJson(String json) throws JSONException {
		
		if (json == null) {
			
			return null;
			
		}
		

		Question[] questions = null;

		JSONObject object = new JSONObject(json);
		JSONArray array = object.optJSONArray("items");
		
		if (array == null) {
			
			return null;
			
		}
		
		int count = array.length();
		
		questions = new Question[count];
		
		for (int i = 0; i < count; i++) {

			JSONObject jsonQuestion = array.optJSONObject(i);

			questions[i] = parseJsonObject(jsonQuestion);

		}

		return questions;

	}

	public static Question parseJsonObject(JSONObject object) {
		
		if (object == null) {
			
			return null;
			
		}



		Question q = new Question();

		q.acceptedAnswerId = object.optLong("accepted_answer_id");
		q.answerCount = object.optInt("answer_count");
		q.isAnswered = object.optBoolean("is_answered");
		q.lastActivityDate = object.optLong("last_activity_date");
		q.owner = User.parserJsonObject(object.optJSONObject("owner"));
		q.questionId = object.optLong("question_id");
		q.score = object.optInt("score");
		q.title = object.optString("title");
		q.viewCount = object.optInt("view_count");
		q.body = object.optString("body");
		q.bountyAmount = object.optInt("bounty_amount");
		q.bountyCloseDate = object.optLong("bounty_close_date");

		q.answers = Answer.parseJsonArray(object.optJSONArray("answers"));
		q.comments = Comment.parseJsonArray(object.optJSONArray("comments"));
		
		
		
		
		
		


		
		
		
		
		
		

		return q;

	}

}
