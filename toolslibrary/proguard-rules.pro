# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\C\IT\android_studio\some\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Retrolambda
-dontwarn java.lang.invoke.*

# 模块中的内部类 & RxEvent
-keep class chestnut.media.Player$MediaInfo{ *; }
-keep class chestnut.utils.AppUtils$AppInfo{ *; }
-keep class chestnut.utils.ClipboardUtils$Type{ *; }
-keep class chestnut.utils.ConstUtils$MemoryUnit{ *; }
-keep class chestnut.utils.ConstUtils$TimeUnit{ *; }
-keep class chestnut.utils.ImageUtils$BitmapPiece{ *; }
-keep class chestnut.utils.LogUtils$Config{ *; }
-keep class chestnut.utils.ScreenUtils$ScreenListener{ *; }
-keep class chestnut.utils.SDCardUtils$SDCardInfo{ *; }
-keep class chestnut.utils.ShellUtils$CommandResult{ *; }
-keep class chestnut.utils.ThreadPoolUtils$Type{ *; }
-keep class chestnut.rx.RxEvent{ *; }

