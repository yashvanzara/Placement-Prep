package in.learncodeonline.placementprep;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    CardView cardViewDataStructure;
    ImageView ivLogo;
    TextView tvTagLine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupReferences();
    }
    private void setupReferences(){
        //References and Listeners
        //TODO: Add Butterknife later
        cardViewDataStructure = findViewById(R.id.cardDataStructures);
        ivLogo = findViewById(R.id.ivLogoMain);
        tvTagLine = findViewById(R.id.tvTagLineMain);
        cardViewDataStructure.setOnClickListener(this);
        ivLogo.setOnClickListener(this);
        tvTagLine.setOnClickListener(this);
    }
    private void launchWebsite(){
        //Launch client website
        String url = getResources().getString(R.string.learn_code_online_website);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == ivLogo.getId() || v.getId() == tvTagLine.getId()){
            launchWebsite();
        }else if(v.getId() == cardViewDataStructure.getId()){
            //Open question activity for data structure topic, add more topics in if else ladder if needed
            //Pass url and title to dynamically load the next screen
            Intent i = new Intent(MainActivity.this, QuestionActivity.class);
            i.putExtra("title", getResources().getString(R.string.data_structures));
            i.putExtra("url", Utils.DS_URL);
            startActivity(i);
        }
    }
}
