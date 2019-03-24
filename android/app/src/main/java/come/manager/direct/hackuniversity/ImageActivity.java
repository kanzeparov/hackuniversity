package come.manager.direct.hackuniversity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ImageView imageView = findViewById(R.id.info_image);

        if(getIntent().getBooleanExtra("status",false)) {
            imageView.setImageResource(R.drawable.with);
        }
    }
}
