package flow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import flow.activity.R;
import flow.entry.Answer;
import flow.entry.Comment;
import flow.entry.Question;
import flow.entry.User;

public class QuestionAdapter extends BaseExpandableListAdapter {


    private Context context;
    private Question question;

    public QuestionAdapter(Context context, Question question) {

        this.context = context;
        this.question = question;

    }

    @Override
    public int getGroupCount() {

        if (question == null) {

            return 0;

        }


        if (question.answers == null) {

            return 1;

        }

        return question.answers.length + 1;


    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (groupPosition == 0) {

            if (question.comments == null) {

                return 0;
            }

            return question.comments.length;

        }


        if (question.answers[groupPosition - 1].comments != null) {

            return question.answers[groupPosition - 1].comments.length;
        }


        return 0;


    }

    public Question getQuestion() {

        return question;

    }


    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.post, null, false);

        }

        TextView post = (TextView) convertView.findViewById(R.id.tv_post);
        TextView owner = (TextView) convertView.findViewById(R.id.tv_owner);

        User u = null;

        if (groupPosition == 0) {

            post.setText(question.body);
            u = question.owner;


        } else {

            Answer a = question.answers[groupPosition - 1];
            u = a.owner;

            post.setText(a.body);

        }

        if (u != null) {

            StringBuilder builder = new StringBuilder();
            builder.append(u.displayName).append(" reputation").append(u.reputation).append(" accept").append(u.acceptRate);

            owner.setText(builder);

        }


        return convertView;


    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.post_comment, null, false);

        }

        TextView comment = (TextView) convertView.findViewById(R.id.tv_comment);
        TextView owner = (TextView) convertView.findViewById(R.id.tv_owner);

        User u = null;


        if (groupPosition == 0) {

            Comment c = question.comments[childPosition];
            comment.setText(c.body);

            u = c.owner;


        } else {

            Answer a = question.answers[groupPosition - 1];
            Comment c = a.comments[childPosition];
            comment.setText(c.body);

            u = c.owner;

        }


        if (u != null) {

            StringBuilder builder = new StringBuilder();
            builder.append(u.displayName).append(" reputation").append(u.reputation).append(" accept").append(u.acceptRate);

            owner.setText(builder);

        }


        return convertView;


    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
