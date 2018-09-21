package com.example.dell.rxjavademo.androidversionchange;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.dell.rxjavademo.R;

import java.io.File;
import java.util.List;

/**
 * android 7.0 系统变化
 * 参考： https://mp.weixin.qq.com/s/05EIPgg_4LjrRQxPjqG-gg
 *
 */

public class FileProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_provider);
        initData();
    }

    private void initData() {
        /**创建一个文件*/
        /**文件路径，文件名*/
        File file = new File("","");
        /**7.0 之后 此时得到该文件的URI，可以打印一下看其格式 */
        Uri uriForFile = FileProvider.getUriForFile(this, "com.bool.car.boolcarlease.fileprovider", file);
        Log.e("FileProviderActivity",uriForFile.toString());
        /**7.0之前*/
        Uri uri = Uri.fromFile(file);
        Log.e("FileProviderActivity",uri.toString());
        /**当我们生成一个 content:// 的 Uri 对象之后，其实还无法对其直接使用，
         * 还需要对这个 Uri 接收的 App 赋予对应的权限才可以。
         *  ①
         *  通过 Context 的 grantUriPermission() 方法授权
                  Context 提供了两个方法
                      grantUriPermission(String toPackage, Uri uri, int modeFlags)
                      revokeUriPermission(Uri uri, int modeFlags);
                  可以看到 grantUriPermission() 方法需要传递一个包名，就是你给哪个应用授权，
                  但是很多时候，比如分享，我们并不知道最终用户会选择哪个 app，所以我们可以这样：
            或者 ②
                配合 Intent.addFlags() 授权
                   既然这是一个 Intent 的 Flag，Intent 也提供了另外一种比较方便的授权方式，
                  那就是使用 Intent.setFlags() 或者 Intent.addFlag 的方式
                 使用这种形式的授权，权限截止于该 App 所处的堆栈被销毁。
                 也就是说，一旦授权，知道该 App 被完全退出，这段时间内，
                 该 App 享有对此 Uri 指向的文件的对应权限，我们无法主动收回该权限了。
         * */
        List<ResolveInfo> resolveInfos =
                this.getPackageManager().queryIntentActivities(getIntent(),
                                       PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resolveInfos){
            String packageName = resolveInfo.activityInfo.packageName;
            /**Flags 是？*/
            this.grantUriPermission(packageName,uri, 0);
        }
       /**根据 Intent 查询出所有符合的应用，都给他们授权，然后在不需要的时候通过 revokeUriPermission 移除权限。*/
    }
}
