如何在eclipse下build有system權限的apk

以下為各位介紹如何在eclipse下build有system權限的apk. 並相關會遇到的問題。

當我們要寫一支system permission的apk時，需要在AndroidManifast.xml加入下面文字：
    android:sharedUserId="android.uid.system"
    < uses-permission android:name="android.permission.REBOOT" / >
    < uses-permission android:name="android.permission.UPDATE_DEVICE_STATS" / >
    .....
然而，加入之後，會有一些狀況，描述如下：


1. eclipse build error:
在eclipse的perference中，有一些android的相關設定。
在Android\Lint Error Checking\ProtectedPermissions預設是error等級(詳如下圖)，也就是說當eclipse檢查到某些被定義成system權限的字串時，會提示user error訊息。
這樣的機制導致於無法build這個apk.
如：< uses-permission android:name="android.permission.REBOOT" / > 就被判定為 error 等級

解決方式：
到preference中將ProtectedPermissions設成warning即可通過eclipse檢查。也可build出具system權限的apk.


2. adb install:
雖然#1經過修改eclipse裡面的設定可以避開檢查，成功build出apk。
但是adb install會因權限呼叫PackageManager檢查signature。結果會出現沒有signatures.

adb logcat如下：
E/PackageManager( 176): Package com.test.android.testreboot has no signatures that match those in shared user android.uid.system; ignoring!
adb install回應如下：
Installation error: INSTALL_FAILED_SHARED_USER_INCOMPATIBLE


3. adb push:
既然adb install不成功，那改用adb push.
adb push雖然不會因權限檢查signature，但是android執行時會因權限檢查signatures，導致於下列error.
adb logcat如下：
E/PackageManager( 176): Package com.test.android.testreboot has no signatures that match those in shared user android.uid.system; ignoring!

#2跟#3的解決方式：
用相對應的platform key去sign過即可解決問題。
Sign key語法如下：
java -jar signapk.jar [pem certificate file of platform] [pk8 certificate file of platform] [origin.apk] [target.pak]
example:
java -jar signapk.jar platform.x509.pem platform.pk8 origin.apk target.pak


其他資訊#1：
如果沒有加上
android:sharedUserId="android.uid.system"，
是可以build出apk，也可以用adb install方式安裝，但是runtime會出現下列錯誤訊息：

adb logcat如下：
E/AndroidRuntime( 1131): java.lang.SecurityException: Neither user 10048 nor current process has android.permission.REBOOT.
