package com.example.lg.myrecyclerview;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.lg.myrecyclerview.util.PermissionUtils;

import java.io.File;

import static com.example.lg.myrecyclerview.util.PermissionUtils.CODE_ACCESS_FINE_LOCATION;
import static com.example.lg.myrecyclerview.util.PermissionUtils.CODE_READ_EXTERNAL_STORAGE;
import static com.example.lg.myrecyclerview.util.PermissionUtils.CODE_READ_PHONE_STATE;
import static com.example.lg.myrecyclerview.util.PermissionUtils.REQUEST_DETAILS_SETTINGS;


public class SplashActivity extends AppCompatActivity {
    private boolean isGpsOK = false,isStorageOK = false;
    private boolean isPhoneOK = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);

        //渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.5f,1.0f);
        aa.setDuration(2000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationEnd(Animation arg0) {
                //初始化部分引擎内容
               new AppInstallAsyncTask().execute();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationStart(Animation animation) {}

        });

//
//        isGpsOK = checkPermissionLocation();
//        boolean isOpenGps = gPSIsOPen(this);
//        if (!isOpenGps) {
//            openGPS(this);
//        }
//        isStorageOK = checkPermissionStorage();
//        isPhoneOK = checkPermissionPhone();


        File file = this.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        /*if (file != null) {
            SDFileConfig.APP_FILES_PATH = this.getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath();
        } else {
            if (Environment
                    .getExternalStorageDirectory() != null) {
                SDFileConfig.APP_FILES_PATH = Environment
                        .getExternalStorageDirectory().toString();
            } else {
                SDFileConfig.APP_FILES_PATH = null;
            }
        }*/


       /* new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                redirectTo();
            }
        }, 2000);*/

    }


    private void initE() {
        File file = this.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        /*if (file != null) {
            SDFileConfig.APP_FILES_PATH = this.getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath();
        } else {
            if (Environment
                    .getExternalStorageDirectory() != null) {
                SDFileConfig.APP_FILES_PATH = Environment
                        .getExternalStorageDirectory().toString();
                ;
            } else {
                SDFileConfig.APP_FILES_PATH = null;
            }
        }*/

    }

    private boolean checkInstall() {
        boolean result = false;
        int nRet = InstallPak();
        if (nRet == 1) {// 安装包错误
            new AlertDialog.Builder(this)
                    .setTitle("错误提示")
                    .setMessage("")
                    .setPositiveButton("",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                            finish();
                                }
                            }).create().show();
        } else {
//            //获取屏幕尺寸
            DisplayMetrics metrics = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
           /* Config.windowWidth = metrics.widthPixels;
            Config.windowHeight = metrics.heightPixels;*/
            result = true;
        }
        return result;
    }

    /**
     * 安装应用
     *
     * @return
     */
    private int InstallPak() {
        //判断的版本号
//        final boolean isChangeVersion = VersionManager.getInstance().isNewData();
//        if (isChangeVersion) {
//            // 删除数据版本不正确的数据
//            VersionManager.getInstance().deleteOldDate();
//            // 所有版本升级操作完以后，写入当前版本的versionCode，以确不同版本数据的替换
//            VersionManager.getInstance().copyVersionCode();
//        }
        return 2;
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    private final boolean gPSIsOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 强制帮用户打开GPS
     *
     * @param context
     */
    public final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
    /**
     * 跳转到...
     */
    private void redirectTo(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        Log.i("跳转到","跳转到mainactivity");
    }


    /**
     * 检查位置权限
     */
    private boolean checkPermissionLocation() {
        boolean relust = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //进入到这里代表没有权限.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //已经禁止提示了
                    PermissionUtils.requestPermission(this,CODE_ACCESS_FINE_LOCATION,mPermissionGrant);
                } else {
                    relust = false;
                    PermissionUtils.requestMultiPermissions(this,mPermissionGrant);
                }
            } else {
                //MyLocationProvider.getInstance().initLocation(getApplicationContext());//启动定位
            }
        } else {
            //MyLocationProvider.getInstance().initLocation(getApplicationContext());//启动定位
        }
        return relust;
    }

    /**
     * 检查位置权限 不提示
     */
    private boolean checkPermissionLocationF() {
        boolean relust = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //进入到这里代表没有权限.
                    relust = false;
            }
        }
        return relust;
    }
    /**
     * 检查存储权限
     */
    private boolean checkPermissionStorage() {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //进入到这里代表没有权限.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //已经禁止提示了
                    result = false;
                    PermissionUtils.requestPermission(this,CODE_READ_EXTERNAL_STORAGE,mPermissionGrant);
                } else {
                    result = false;
                    //PermissionUtils.requestPermission(this,CODE_READ_EXTERNAL_STORAGE,mPermissionGrant);
                    PermissionUtils.requestMultiPermissions(this,mPermissionGrant);
                }
            } else {
              /*  SDFileConfig.SDKCARD_PATH = Environment
                        .getExternalStorageDirectory().toString();*/
            }
        }else {
            /*SDFileConfig.SDKCARD_PATH = Environment
                    .getExternalStorageDirectory().toString();*/
        }
        return result;
    }
    /**
     * 检查存储权限 不提示
     */
    private boolean checkPermissionStorageF() {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //进入到这里代表没有权限.
                result = false;
            }
        }
        return result;
    }

    /**
     * 手机状态
     */
    private boolean checkPermissionPhone() {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //进入到这里代表没有权限.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                    //已经禁止提示了
                    result = false;
                    //Toast.makeText(SplashActivity.this, "您已禁止手机状态权限，需要重新开启。", Toast.LENGTH_SHORT).show();
                    PermissionUtils.requestPermission(this,CODE_READ_PHONE_STATE,mPermissionGrant);
                } else {
                    result = false;
                    //PermissionUtils.requestMultiPermissions(this, mPermissionGrant);
                    PermissionUtils.requestMultiPermissions(this,mPermissionGrant);
                }
            }
        }
        return result;
    }
    /**
     * 手机状态
     */
    private boolean checkPermissionPhoneF() {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //进入到这里代表没有权限.
                result = false;
            }
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.i("requestMultiResult", "onRequestPermissionsResult 执行了"+requestCode);

        if (grantResults == null || grantResults.length <= 0) {
            return;
        }
        switch (requestCode) {
            case PermissionUtils.CODE_MULTI_PERMISSION:
                PermissionUtils.requestMultiResult(SplashActivity.this, permissions, grantResults, mPermissionGrant);
                break;
            case CODE_ACCESS_FINE_LOCATION:
                PermissionUtils.requestPermissionsResult(SplashActivity.this,CODE_ACCESS_FINE_LOCATION,permissions,grantResults,mPermissionGrant);
                break;
            case CODE_READ_PHONE_STATE:
                PermissionUtils.requestPermissionsResult(SplashActivity.this,CODE_READ_PHONE_STATE,permissions,grantResults,mPermissionGrant);
                break;
            case CODE_READ_EXTERNAL_STORAGE:
                PermissionUtils.requestPermissionsResult(SplashActivity.this,CODE_READ_EXTERNAL_STORAGE,permissions,grantResults,mPermissionGrant);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    break;
                case CODE_READ_PHONE_STATE:
                    isPhoneOK = checkPermissionPhoneF();
                    new AppInstallAsyncTask().execute();
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
                    break;
                case PermissionUtils.CODE_CAMERA:
                    break;
                case CODE_ACCESS_FINE_LOCATION:
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    //MyLocationProvider.getInstance().initLocation(getApplicationContext());//启动定位
                    isGpsOK = checkPermissionLocationF();
                    new AppInstallAsyncTask().execute();
                    break;
                case CODE_READ_EXTERNAL_STORAGE:
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    //SDFileConfig.SDKCARD_PATH = Environment.getExternalStorageDirectory().toString();
                    isStorageOK = checkPermissionStorageF();
                    new AppInstallAsyncTask().execute();
                    break;
                case PermissionUtils.CODE_MULTI_PERMISSION:
                    isGpsOK = checkPermissionLocationF();
                    isStorageOK = checkPermissionStorageF();
                    isPhoneOK = checkPermissionPhoneF();
                    new AppInstallAsyncTask().execute();
                    break;
                default:
                    break;
            }
        }
    };

    private class AppInstallAsyncTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            //还未获取到GPS暂时不登录；
            isGpsOK = checkPermissionLocationF();
            isStorageOK = checkPermissionStorageF();
            isPhoneOK = checkPermissionPhoneF();

            /*
            if (isGpsOK == false || isStorageOK == false|| isPhotoOK == false){
                return  false;
            }*/

           /* if (MyLocationProvider.getInstance().locationPoint == null) {
                MyLocationProvider.getInstance().locationPoint = Config.TIANANMEN;
            }*/
            boolean result = checkInstall();
            if (!result) {
                //安装不成功直接退出
                return false;
            }

            /*//设置登录状态
            boolean isLogin = ShareUtils.getBoolean(getApplicationContext(), UserManager.LOGIN, false);
            if (isLogin) {
                UserManager.getInstance().LOGINTYPE = UserManager.LoginStateEnum.YESLOGIN;
                UserManager.getInstance().setUserName(ShareUtils.getString(getApplicationContext(), UserManager.LOGIN_NAME, ""));
                UserManager.getInstance().setUserID(ShareUtils.getInt(getApplicationContext(), UserManager.LOGIN_USERID, 0));
            } else {
                UserManager.getInstance().LOGINTYPE = UserManager.LoginStateEnum.NOTLOGIN;

            }*/

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result && isStorageOK && isPhoneOK && isGpsOK) {

            }

            initE();
            redirectTo();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        Log.i("onActivityResult", "onActivityResult 执行了"+requestCode);


        if (requestCode == REQUEST_DETAILS_SETTINGS) {
            //初始化部分引擎内容
            new AppInstallAsyncTask().execute();
        }
    }
}
