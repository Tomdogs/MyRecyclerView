package com.example.lg.myrecyclerview;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class PermissionActivity extends AppCompatActivity {

    private final int REQUSET_CODE_LOCATION = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        //checkPermissionLocation();

        insertDummyContactWrapper();
    }


    /**
     * 检查位置权限，并申请权限
     */
    private void checkPermissionLocation() {
        boolean relust = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
                //如果用户在过去拒绝了权限请求，并在权限请求系统对话框中选择了 Don’t ask again 选项，此方法将返回 false
                //如果设备规范禁止应用具有该权限，此方法也会返回 false。
               if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //进入到这里代表没有权限.
                    //已经禁止提示了
                    Toast toast = Toast.makeText(PermissionActivity.this, "您已禁止位置权限，需要重新开启。否则程序不能正常启动", Toast.LENGTH_SHORT);
                    toast.show();
                    Log.i("permission1","禁止位置权限");
                } else {
                    Log.i("permission2","设置位置权限");
                    ActivityCompat.requestPermissions(PermissionActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUSET_CODE_LOCATION);
                }
                
                Log.i("permission3","shouldShowRequestPermissionRationale: "+ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION));

               /* if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(PermissionActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUSET_CODE_LOCATION);
                    return;
                }
                ActivityCompat.requestPermissions(PermissionActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUSET_CODE_LOCATION);
                return;*/
            } else {
                Log.i("permission4","有定位权限");
            }
        } else {
            Log.i("permission5","SDK小于23");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUSET_CODE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.i("permission6","授权成功");
                }else {
                    Log.i("permission7","授权失败");
                }
            case REQUEST_CODE_ASK_PERMISSIONS:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.i("permission8","授权成功");
                }else {
                    Log.i("permission9","授权失败");
                }
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private void insertDummyContactWrapper() {
        int hasWriteContactsPermission = ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_CONTACTS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_CONTACTS)) {
                showMessageOKCancel("You need to allow access to Contacts",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(PermissionActivity.this,new String[] {Manifest.permission.WRITE_CONTACTS},REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(PermissionActivity.this,new String[] {Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(PermissionActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
