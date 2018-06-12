package in.learncodeonline.placementprep;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.learncodeonline.placementprep.adapters.SwipeStackAdapter;
import in.learncodeonline.placementprep.models.Question;
import link.fls.swipestack.SwipeStack;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuestionActivity extends AppCompatActivity implements SwipeStack.SwipeStackListener {

    ArrayList<Question> questionList;
    Activity context;
    SwipeStack swipeStack;
    SwipeStackAdapter adapter;
    TextView tvNoMoreQuestions;
    ImageView ivCardLogoEmpty;
    TextView tvCardTagLineEmpty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        String title = getIntent().getExtras().getString("title");
        String url = getIntent().getExtras().getString("url");
        getSupportActionBar().setTitle(title);
        setupReferences();
        loadData(url);
    }
    private void setupReferences(){
        //Refereces and Listeners
        context = this;
        tvNoMoreQuestions = findViewById(R.id.tvNoMoreQuestions);
        ivCardLogoEmpty = findViewById(R.id.ivCardLogoEmpty);
        tvCardTagLineEmpty = findViewById(R.id.tvCardTagLineEmpty);
        swipeStack = findViewById(R.id.swipeStack);
        swipeStack.setListener(this);
        ivCardLogoEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchWebsite();
            }
        });
        tvCardTagLineEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchWebsite();
            }
        });
    }
    private void loadData(String url){
        //TODO: Switch to Retrofit if need to deal with a lot of APIs
        OkHttpHandler okHttpHandler= new OkHttpHandler();
        okHttpHandler.execute(url);
    }

    @Override
    public void onViewSwipedToLeft(int position) {

    }

    @Override
    public void onViewSwipedToRight(int position) {

    }

    @Override
    public void onStackEmpty() {
        //Cards went empty, all questions now completed
        Toast.makeText(context, R.string.no_more_questions, Toast.LENGTH_SHORT).show();
        tvNoMoreQuestions.setVisibility(View.VISIBLE);
        ivCardLogoEmpty.setVisibility(View.VISIBLE);
        tvCardTagLineEmpty.setVisibility(View.VISIBLE);
    }

    public class OkHttpHandler extends AsyncTask<String, String, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String...params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                if(Utils.isConnectedToInternet(getApplicationContext())){
                    //Internet is connected, Safe to make the call
                    Response response = client.newCall(request).execute();
                    return response.body().string();
                }else{
                    Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //Data received, Parse and fillup the adapter
            super.onPostExecute(s);
            try {
                JSONObject reader = new JSONObject(s);
                JSONArray questions = reader.getJSONArray("questions");
                questionList = new ArrayList<>();
                for(int i=0; i<questions.length(); ++i){
                    JSONObject question = questions.getJSONObject(i);
                    String desc = question.getString("question");
                    String solution = question.getString("Answer");
                    Question q = new Question(desc, solution);
                    questionList.add(q);
                    adapter = new SwipeStackAdapter(context, questionList);
                    swipeStack.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    if(questionList.size()==0){
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (NullPointerException npe){
                Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
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
