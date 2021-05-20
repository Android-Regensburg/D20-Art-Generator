package de.ur.mi.android.demos.artgenerator;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;

import de.ur.mi.android.demos.artgenerator.generator.GeneratorRequest;
import de.ur.mi.android.demos.artgenerator.generator.GeneratorService;

public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_DEMO_DELAY_IN_MS = 3000;
    private GeneratorService generatorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGeneratorService();
        initUI();
    }

    private void initGeneratorService() {
        Intent intent = new Intent(this, GeneratorService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                generatorService = ((GeneratorService.LocalBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                generatorService = null;
            }
        }, Context.BIND_AUTO_CREATE);

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
        if (generatorService == null) {
            return;
        }
        GeneratorService.GeneratorListener listener = new GeneratorService.GeneratorListener() {
            @Override
            public void onResult(Bitmap image) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.setImageBitmap(image);
                    }
                });
            }
        };
        GeneratorRequest request = createImageRequest(listener, view);
        generatorService.generateImage(request);
    }

    private GeneratorRequest createImageRequest(GeneratorService.GeneratorListener listener, ImageView view) {
        view.setImageResource(R.drawable.waiting_image_container);
        GeneratorRequest.ImageGeneratorRequestBuilder builder = new GeneratorRequest.ImageGeneratorRequestBuilder();
        boolean useDelay = ((SwitchMaterial) findViewById(R.id.switch_demo_mode)).isChecked();
        boolean useColorMode = ((SwitchMaterial) findViewById(R.id.switch_color_mode)).isChecked();
        float pixelDensity = ((Slider) findViewById(R.id.slider_pixel_density)).getValue();
        int delay = useDelay ? DEFAULT_DEMO_DELAY_IN_MS : 0;
        return builder.setImageWidth(view.getWidth()).setImageHeight(view.getHeight()).setPixelDensity(pixelDensity).setColorMode(useColorMode).setResultDelay(delay).setListener(listener).build();
    }


}