package in.learncodeonline.placementprep.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.learncodeonline.placementprep.R;
import in.learncodeonline.placementprep.models.Question;

public class SwipeStackAdapter extends BaseAdapter {

    private List<Question> questions;
    Activity context;
    public SwipeStackAdapter(Activity activity, List<Question> questions) {
        this.questions = questions;
        context = activity;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Question getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = context.getLayoutInflater().inflate(R.layout.flash_card, parent, false);
        //References
        final TextView tvQuestion = convertView.findViewById(R.id.tvQuestion);
        final Button btnToggle = convertView.findViewById(R.id.btnToggle);
        final TextView tvAnswer = convertView.findViewById(R.id.tvAnswer);
        final TextView tvQuestionHeader = convertView.findViewById(R.id.tvQuestionHeader);
        TextView tvCardStatus = convertView.findViewById(R.id.tvCardStatus);

        //Set Data
        tvCardStatus.setText((position+1) + " of " + questions.size());
        TextView tvCardTagLine = convertView.findViewById(R.id.tvCardTagLine);
        ImageView ivCardLogo = convertView.findViewById(R.id.ivCardLogo);
        tvQuestion.setText(questions.get(position).getDescription());
        String ans = context.getResources().getString(R.string.answer);;
        tvAnswer.setText(ans + ": " + questions.get(position).getSolution());

        //Listeners
        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAnswer.setVisibility(View.VISIBLE);
                tvQuestionHeader.setText(R.string.answer);
                btnToggle.setVisibility(View.GONE);
            }
        });
        tvCardTagLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchWebsite();
            }
        });
        ivCardLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchWebsite();
            }
        });
        return convertView;
    }
    private void launchWebsite(){
        //Launch client website
        String url = context.getResources().getString(R.string.learn_code_online_website);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }
}