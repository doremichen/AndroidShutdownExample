�p��beclipse�Ubuild��system�v����apk

�H�U���U�줶�Цp��beclipse�Ubuild��system�v����apk. �ì����|�J�쪺���D�C

��ڭ̭n�g�@��system permission��apk�ɡA�ݭn�bAndroidManifast.xml�[�J�U����r�G
    android:sharedUserId="android.uid.system"
    < uses-permission android:name="android.permission.REBOOT" / >
    < uses-permission android:name="android.permission.UPDATE_DEVICE_STATS" / >
    .....
�M�ӡA�[�J����A�|���@�Ǫ��p�A�y�z�p�U�G


1. eclipse build error:
�beclipse��perference���A���@��android�������]�w�C
�bAndroid\Lint Error Checking\ProtectedPermissions�w�]�Oerror����(�Ԧp�U��)�A�]�N�O����eclipse�ˬd��Y�ǳQ�w�q��system�v�����r��ɡA�|����user error�T���C
�o�˪�����ɭP��L�kbuild�o��apk.
�p�G< uses-permission android:name="android.permission.REBOOT" / > �N�Q�P�w�� error ����

�ѨM�覡�G
��preference���NProtectedPermissions�]��warning�Y�i�q�Leclipse�ˬd�C�]�ibuild�X��system�v����apk.


2. adb install:
���M#1�g�L�ק�eclipse�̭����]�w�i�H�׶}�ˬd�A���\build�Xapk�C
���Oadb install�|�]�v���I�sPackageManager�ˬdsignature�C���G�|�X�{�S��signatures.

adb logcat�p�U�G
E/PackageManager( 176): Package com.test.android.testreboot has no signatures that match those in shared user android.uid.system; ignoring!
adb install�^���p�U�G
Installation error: INSTALL_FAILED_SHARED_USER_INCOMPATIBLE


3. adb push:
�J�Madb install�����\�A�����adb push.
adb push���M���|�]�v���ˬdsignature�A���Oandroid����ɷ|�]�v���ˬdsignatures�A�ɭP��U�Cerror.
adb logcat�p�U�G
E/PackageManager( 176): Package com.test.android.testreboot has no signatures that match those in shared user android.uid.system; ignoring!

#2��#3���ѨM�覡�G
�ά۹�����platform key�hsign�L�Y�i�ѨM���D�C
Sign key�y�k�p�U�G
java -jar signapk.jar [pem certificate file of platform] [pk8 certificate file of platform] [origin.apk] [target.pak]
example:
java -jar signapk.jar platform.x509.pem platform.pk8 origin.apk target.pak


��L��T#1�G
�p�G�S���[�W
android:sharedUserId="android.uid.system"�A
�u���[
�O�i�Hbuild�Xapk�A�]�i�H��adb install�覡�w�ˡA���Oruntime�|�X�{�U�C���~�T���G

adb logcat�p�U�G
E/AndroidRuntime( 1131): java.lang.SecurityException: Neither user 10048 nor current process has android.permission.REBOOT.


��L��T#2�G
eclipse���v���ˬd���O�ˬd
    android:sharedUserId="android.uid.system"
�ӬO�ˬd
    < uses-permission android:name="android.permission.REBOOT" / >
    < uses-permission android:name="android.permission.UPDATE_DEVICE_STATS" / >
�ɭP�W�z���D���A�����٦���L���ءA�ثe�S�������̦��ԲӦC�X�ˬd�����ءC