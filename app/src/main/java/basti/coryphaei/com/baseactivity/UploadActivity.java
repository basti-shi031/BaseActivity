package basti.coryphaei.com.baseactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import java.io.File;

/**
 * Created by Bowen on 2015-11-05.
 */
public class UploadActivity extends BaseActivity implements UploadFileUtils.UpLoadFileListener {

    private Bitmap mBitmap;
    private static final String PHOTO_FILE_NAME = "IMG.jpg";
    private File mFile;
    private ImageSize imageSize;
    protected DialogUtils mUpLoadFileDialog;
    protected UpLoadFile mListener;


    public interface UpLoadFile{
        void loadFinished();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        init();
    }

    private void init() {
        imageSize = new ImageSize();
        mUpLoadFileDialog = new DialogUtils(this);
    }

    protected void setOnUploadListener(UpLoadFile listener){
        mListener = listener;
    }

    //拍照
    protected void snapShot(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        if (SystemUtils.hasSdCard()){
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
            startActivityForResult(intent, Code.PHOTO_CAMERA_REQUEST);
        }else {
            actionWithoutSDCard();
        }
    }

    //如果没有SD卡后续操作
    protected void actionWithoutSDCard() {
        Toast.makeText(getApplicationContext(), R.string.no_sdcard,Toast.LENGTH_SHORT).show();
    }

    //从系统相册中找图片
    protected void loadImg(ImageSize imageSize){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Code.PHOTO_GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Code.PHOTO_CAMERA_REQUEST:
                mFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                //裁剪
                crop(Uri.fromFile(mFile),imageSize);
                break;
            case Code.PHOTO_GALLERY_REQUEST:
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    crop(uri,imageSize);
                }
                    break;
            case Code.PHOTO_CROP_REQUEST:
                if(data!=null){
                    mBitmap = data.getParcelableExtra("data");
                    mFile = FileUtils.saveImageToGallery(this,mBitmap,FileUtils.ScannerType.RECEIVER);
                    if (mListener != null&&mFile != null) {
                        mListener.loadFinished();
                    }
                }
                break;
        }
    }

    //剪裁图片
    private void crop(Uri uri,ImageSize imageSize) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", imageSize.getWidthSize());
        intent.putExtra("aspectY", imageSize.getHeightSize());
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", imageSize.getWidthSize());
        intent.putExtra("outputY", imageSize.getHeightSize());
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, Code.PHOTO_CROP_REQUEST);
    }

    protected File getFile(){
        if (mFile == null){
            return null;
        }
        return mFile;
    }

    protected void uploadBitmap(RequestParams params,String url){
        UploadFileUtils.upLoadFile(this,params,url,this);
    }

    @Override
    public void onUpLoadSuccess(String result) {
        mUpLoadFileDialog.showProgressDialog(false);
        showToast(getResources().getString(R.string.upload_success));
    }

    @Override
    public void onUpLoadFailure(String result) {
        mUpLoadFileDialog.showProgressDialog(false);
        showToast(getResources().getString(R.string.upload_failure));
    }

    @Override
    public void onUpLoadProgress(int written, int total) {
        mUpLoadFileDialog.showProgressDialog(true, "已上传" + (written * 1.0f / total) + "%");
    }

    //设置上传图片的大小，默认为250*250
    protected void setImageSize(int width,int height){
        imageSize.setHeightSize(height);
        imageSize.setWidthSize(width);
    }

    //获得上传图片的大小
    protected ImageSize getImageSize(){
        return imageSize;
    }
}
