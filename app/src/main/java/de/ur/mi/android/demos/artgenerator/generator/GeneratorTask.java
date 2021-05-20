package de.ur.mi.android.demos.artgenerator.generator;

import android.graphics.Bitmap;
import android.graphics.Color;

public class GeneratorTask implements Runnable {

    private static final int BACKGROUND_COLOR = Color.rgb(255, 255, 255);
    private static final int DEFAULT_FOREGROUND_COLOR = Color.rgb(0, 0, 0);

    private final ResultListener listener;
    private final GeneratorRequest request;

    public GeneratorTask(GeneratorRequest request, ResultListener listener) {
        this.request = request;
        this.listener = listener;
    }

    @Override
    public void run() {
        Bitmap bitmap = createBitmap();
        try {
            Thread.sleep(request.resultDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listener.onImageReady(bitmap);
    }

    private Bitmap createBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(request.imageWidth, request.imageHeight, Bitmap.Config.ARGB_8888);
        int foreGroundColor = request.useColorMode ? getRandomColor() : DEFAULT_FOREGROUND_COLOR;
        for (int x = 0; x < request.imageWidth; x++) {
            for (int y = 0; y < request.imageHeight; y++) {
                int color = getColorForPixel(foreGroundColor, request.pixelDensity);
                bitmap.setPixel(x, y, color);
            }
        }
        return bitmap;
    }

    private int getColorForPixel(int foregroundColor, float pixelDensity) {
        if (Math.random() + pixelDensity >= 1) {
            return foregroundColor;
        }
        return BACKGROUND_COLOR;
    }

    private int getRandomColor() {
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
        return Color.rgb(r, g, b);
    }

    public interface ResultListener {

        void onImageReady(Bitmap image);

    }

}
