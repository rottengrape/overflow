package flow.activity;

import flow.adapter.QuestionAdapter;
import flow.entry.Question;
import flow.utility.StackClient;
import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

public class QuestionActivity extends FragmentActivity implements
        OnGroupClickListener, LoaderCallbacks<Question> {

    private long questionId;
    private String apiSiteParameter;
    private ExpandableListView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        view = (ExpandableListView) findViewById(R.id.edlv_question);
        view.setOnGroupClickListener(this);
        questionId = getIntent().getLongExtra("questionId", 0l);
        apiSiteParameter = getIntent().getStringExtra("apiSiteParameter");
        getSupportLoaderManager().initLoader(0, null, this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(R.string.watch_question);

        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return true;

    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v,
                                int groupPosition, long id) {

        return true;

    }

    @Override
    public Loader<Question> onCreateLoader(int id, Bundle args) {

        AsyncTaskLoader<Question> fresh = new AsyncTaskLoader<Question>(this) {

            @Override
            public Question loadInBackground() {
                StackClient client = StackClient.getInstance();

                try {

                    Question[] questions = client.questionsAnswersComments(
                            apiSiteParameter, questionId);

                    if (questions != null && questions.length > 0) {

                        return questions[0];

                    }

                } catch (JSONException e) {

                }

                return null;

            }
        };

        fresh.forceLoad();

        return fresh;

    }

    @Override
    public void onLoadFinished(Loader<Question> loader, Question data) {

        if (data != null) {

            QuestionAdapter adapter = new QuestionAdapter(this, data);

            view.setAdapter(adapter);
            int count = adapter.getGroupCount();
            for (int i = 0; i < count; i++) {

                view.expandGroup(i);

            }

        }

    }

    @Override
    public void onLoaderReset(Loader<Question> loader) {


    }

}
