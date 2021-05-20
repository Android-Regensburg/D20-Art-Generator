package de.ur.mi.android.demos.artgenerator.generator;

public class GeneratorRequest {

    public final int imageWidth;
    public final int imageHeight;
    public final float pixelDensity;
    public final boolean useColorMode;
    public final int resultDelay; // Delay in ms between image generation and notification call on listener
    public final GeneratorService.GeneratorListener listener;

    private GeneratorRequest(int imageWidth, int imageHeight, float pixelDensity, boolean useColorMode, int resultDelay, GeneratorService.GeneratorListener listener) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.pixelDensity = pixelDensity;
        this.useColorMode = useColorMode;
        this.resultDelay = resultDelay;
        this.listener = listener;
    }

    public static class ImageGeneratorRequestBuilder {

        private static final int DEFAULT_IMAGE_WIDTH = 500;
        private static final int DEFAULT_IMAGE_HEIGHT = 500;
        private static final float DEFAULT_PIXEL_DENSITY = 0.5f;
        private static final boolean DEFAULT_FOR_USING_COLOR_MODE = false;
        private static final int DEFAULT_RESULT_DELAY = 0;

        private int imageWidth = DEFAULT_IMAGE_WIDTH;
        private int imageHeight = DEFAULT_IMAGE_HEIGHT;
        private float pixelDensity = DEFAULT_PIXEL_DENSITY;
        private boolean useColorMode = DEFAULT_FOR_USING_COLOR_MODE;
        private int resultDelay = DEFAULT_RESULT_DELAY;
        private GeneratorService.GeneratorListener listener = null;

        public GeneratorRequest build() {
            return new GeneratorRequest(imageWidth, imageHeight, pixelDensity, useColorMode, resultDelay, listener);
        }

        public ImageGeneratorRequestBuilder setImageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
            return this;
        }

        public ImageGeneratorRequestBuilder setImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }

        public ImageGeneratorRequestBuilder setPixelDensity(float pixelDensity) {
            this.pixelDensity = pixelDensity;
            return this;
        }

        public ImageGeneratorRequestBuilder setResultDelay(int resultDelay) {
            this.resultDelay = resultDelay;
            return this;
        }

        public ImageGeneratorRequestBuilder setListener(GeneratorService.GeneratorListener listener) {
            this.listener = listener;
            return this;
        }

        public ImageGeneratorRequestBuilder setColorMode(boolean enabled) {
            this.useColorMode = enabled;
            return this;
        }

    }

}
