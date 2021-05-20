package de.ur.mi.android.demos.artgenerator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }


    private void initUI() {
        setContentView(R.layout.activity_main);
        initClickListenerForImageContainers();
    }

    private void initClickListenerForImageContainers() {
        GridLayout parent = findViewById(R.id.image_container);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ImageView) {
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        generateImageFor((ImageView) childView);
                    }
                });
            }
        }
    }

    private void generateImageFor(ImageView view) {
        Log.d("Image-Generator", "in: generateImageFor");
    }


}