package flow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import flow.activity.R;
import flow.entry.Question;

public class ThreadAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Question[] questions;

    public ThreadAdapter(Context context, Question[] q) {

        questions = q;
        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {

        if(questions == null)  {
            return 0;
        }

        return questions.length;

    }

    @Override
    public Object getItem(int position) {

        return questions[position];


    }

    @Override
    public long getItemId(int position) {


        return questions[position].questionId;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {

            convertView = inflater.inflate(R.layout.thread_item, null, false);

        }
        ThreadViewHolder tag = (ThreadViewHolder) convertView.getTag();
        if (tag == null) {
            tag = new ThreadViewHolder();
            tag.title = (TextView) convertView.findViewById(R.id.tv_title);
            tag.owner = (TextView) convertView.findViewById(R.id.tv_owner);
            tag.answerViewScore = (TextView) convertView.findViewById(R.id.tv_answer_view_score);
            convertView.setTag(tag);

        }

        Question q = (Question) getItem(position);

        StringBuilder fresh = new StringBuilder();
        fresh.append(q.answerCount).append("\n").append(q.viewCount).append("\n").append(q.score);
        tag.title.setText(q.title);
        tag.answerViewScore.setText(fresh.toString());
        tag.owner.setText(q.owner.displayName);

        return convertView;


    }


    public void onSwapQuestions(Question[] questions) {

        this.questions = questions;
        notifyDataSetChanged();


    }


    private class ThreadViewHolder {

        public TextView answerViewScore;
        public TextView title;
        public TextView owner;


    }


}
