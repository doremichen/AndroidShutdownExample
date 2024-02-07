How to build under the eclipse system permissions apk

Here is how to introduce you in the eclipse build system permissions APK and related problems encountered.

When we want to write a apk system permission, you need to add the following text in AndroidManifast.xml:
    android:sharedUserId="android.uid.system"
    < uses-permission android:name="android.permission.REBOOT" / >
    < uses-permission android:name="android.permission.UPDATE_DEVICE_STATS" / >
    .....
However, after joining, there will be some conditions, described as follows:

1. eclipse build error:
In eclipse perference, there are some android settings.
In Android \ Lint Error Checking \ ProtectedPermissions the default is the error level (as shown below), which means that when eclipse checks to some of the strings defined as the system permissions, it will prompt user error message.
This mechanism led to that can not build this apk.
Such as: <uses-permission android: name = "android.permission.REBOOT" /> is judged as error level

Solution:
ProtectedPermissions will be set to the warning to eclipse check. You can also build the system permissions apk.

2. adb install:
Although #1 is modified eclipse inside the settings can avoid checking, successfully build an apk.
However, adb install calls PackageManager to check the signature for permission. The result will appear without signatures.

adb logcat as follows:
E/PackageManager( 176): Package com.test.android.testreboot has no signatures that match those in shared user android.uid.system; ignoring!
adb install response is as follows:
Installation error: INSTALL_FAILED_SHARED_USER_INCOMPATIBLE

3. adb push:
Since adb install unsuccessful, then use adb push.
adb push although not because of the authority to check the signature, but the android execution signatures due to permission checks, resulting in the following error.
adb logcat as follows:
E/PackageManager( 176): Package com.test.android.testreboot has no signatures that match those in shared user android.uid.system; ignoring!

#2 #3 with the solution:
Use the corresponding platform key to sign over to solve the problem.
Sign key syntax is as follows:
java -jar signapk.jar [pem certificate file of platform] [pk8 certificate file of platform] [origin.apk] [target.pak]
example:
java -jar signapk.jar platform.x509.pem platform.pk8 origin.apk target.pak

Additional Information #1:
If not added
android:sharedUserId="android.uid.system"¡A
It is possible to build an apk or install it using adb install, but the runtime will get the following error message:
adb logcat as follows:
E/AndroidRuntime( 1131): java.lang.SecurityException: Neither user 10048 nor current process has android.permission.REBOOT.

   