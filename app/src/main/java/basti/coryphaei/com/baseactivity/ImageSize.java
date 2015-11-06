package basti.coryphaei.com.baseactivity;

/**
 * 上传图片的尺寸
 * Created by Bowen on 2015-11-05.
 */
public class ImageSize {

    private int widthSize;
    private int heightSize;

    public ImageSize(int widthSize, int heightSize) {
        this.widthSize = widthSize;
        this.heightSize = heightSize;
    }

    public ImageSize() {
        widthSize = 250;
        heightSize = 250;
    }

    public void setWidthSize(int widthSize) {
        this.widthSize = widthSize;
    }

    public void setHeightSize(int heightSize) {
        this.heightSize = heightSize;
    }

    public int getWidthSize() {
        return widthSize;
    }

    public int getHeightSize() {
        return heightSize;
    }
}
