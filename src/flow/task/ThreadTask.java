package flow.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import flow.adapter.ThreadAdapter;
import flow.entry.Question;
import flow.entry.Site;
import flow.utility.StackClient;
import org.json.JSONException;


public class ThreadTask extends AsyncTask
        <String
                , Void, Question[]> {

    private int page;
    private ListView lv;
    private Context context;


    public ThreadTask(ListView lv
            , Context context

    ) {
        this.lv = lv;
        page = 1;
        this.context = context;

    }


    @Override
    protected void onPostExecute(Question[] questions) {
        super.onPostExecute(questions);

        ThreadAdapter adapter = (ThreadAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new ThreadAdapter(context, questions);
            lv.setAdapter(adapter);

        } else {
            adapter.onSwapQuestions(questions);
        }


    }

    @Override
    protected Question[] doInBackground(
            String... params) {

        if (params == null || params.length == 0) {
            return null;
        }


        StackClient client = StackClient.getInstance();
        Question[] questions = null;
        try {
            client.sites();
            questions = client.questions(params[0], page);
        } catch (JSONException e) {

        }

        if(questions==null)     {
            Log.e("error","null questions")      ;
        }                        else {
            Log.e("error"," "+ questions.length)   ;
        }

        return questions;
    }
}
