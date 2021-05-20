package de.ur.mi.android.demos.artgenerator.generator;


import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GeneratorService extends Service {

    private static final int THREAD_POOL_SIZE = 3;

    private ExecutorService executorPool;

    @Override
    public void onCreate() {
        super.onCreate();
        executorPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder(this);
    }

    public void generateImage(GeneratorRequest request) {
        GeneratorTask task = new GeneratorTask(request, new GeneratorTask.ResultListener() {
            @Override
            public void onImageReady(Bitmap image) {
                request.listener.onResult(image);
            }
        });
        executorPool.submit(task);
    }

    public interface GeneratorListener {

        void onResult(Bitmap image);
    }

    public static class LocalBinder extends Binder {

        private final GeneratorService service;

        public LocalBinder(GeneratorService service) {
            this.service = service;
        }

        public GeneratorService getService() {
            return service;
        }

    }

}
