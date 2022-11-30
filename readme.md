# BasicAirData GPS Logger<br>[![Releases](http://img.shields.io/github/release/BasicAirData/GPSLogger.svg?label=%20release%20)](https://github.com/BasicAirData/GPSLogger/releases) [![GitHub license](https://img.shields.io/badge/license-GPL_3-blue.svg?label=%20license%20)](https://raw.githubusercontent.com/BasicAirData/GPSLogger/master/LICENSE) [![Crowdin](https://d322cqt584bo4o.cloudfront.net/gpslogger/localized.svg)](https://crowdin.com/project/gpslogger) 
A GPS logger for Android mobile devices.<br>
Offered by [BasicAirData](http://www.basicairdata.eu) - Open and free DIY air data instrumentation and telemetry 

![alt tag](https://github.com/BasicAirData/GPSLogger/blob/master/screenshots/Image_01.png)

## Description

BasicAirData GPS Logger is a simple App to record your position and your path.<br>
It's a basic and lightweight GPS tracker focused on accuracy, with an eye to power saving.<br>
This app is very accurate in determining your altitude: enable EGM96 automatic altitude correction on settings!<br>
You can record all your trips, view them in your preferred Viewer (it must be installed) directly from the in-app tracklist, and share them in KML, GPX, and TXT format in many ways.

The app is 100% Free and Open Source.

For further information about this app you can read [this article](http://www.basicairdata.eu/projects/android/android-gps-logger/).<br>
[Here](http://www.basicairdata.eu/projects/android/android-gps-logger/getting-started-guide-for-gps-logger/) you can find a Getting Started Guide.<br><br>
The application is freely downloadable from [Google Play(tm)](https://play.google.com/store/apps/details?id=eu.basicairdata.graziano.gpslogger) or directly here in this repository in /apk folder.<br>
You can install GPS Logger on your smartphone in one step, using the Google Store [QR-Code](https://github.com/BasicAirData/GPSLogger/blob/master/screenshots/qrcode%20-%20Google%20Store.png) or the Latest APK [QR-Code](https://github.com/BasicAirData/GPSLogger/blob/master/screenshots/qrcode.png);

## Translations

The app is translated in many languages thanks to the precious collaboration of some willing users around the world.<br>
Do you want to add a new language to the app?<br>
Do you want to help us in translations?<br>
Join Us on [Crowdin](https://crowdin.com/project/gpslogger) and help to translate and keep updated the app in your Language!

## Reference documents

[Code of conduct](CODE_OF_CONDUCT.md)

[Contributing Information](CONTRIBUTING.md)

[Repository License](LICENSE)

## Frequently Asked Questions

<i>The following answers are related to the latest version of GPS Logger.</i>

<b>Q</b> - <b>I've just installed the App, but it doesn't read the GPS Signal.</b><br>
<b>A</b> - Please reboot your Device, go in an open Area and try to repeat your test. It seems not relevant, but a system reboot is really the solution in most of these cases.

<b>Q</b> - <b>The Location is active, but the App sees "GPS disabled".</b><br>
<b>A</b> - Please go on Location Section of your Android Settings: the Phone could be set to use the "Battery saving" Locating Method. This Method uses Wi-Fi & Mobile Networks to estimate your Location, without turn on the GPS. In case please switch to "Phone only" or "High accuracy" Method to enable the GPS Sensor.

<b>Q</b> - <b>How can I view my recorded Tracks?</b><br>
<b>A</b> - You can view your Tracks by going on Tracklist Tab and clicking on it. An Actionbar will appear, that should contain an Eye Icon or the Icon of a KML/GPX Viewer. At least one KML/GPX viewer must be installed on your device; if not (in this case the Icon will not be visible), please install it. If You installed more than one Viewer, into GPS Logger's Settings you can choose which one to use. A good Viewer for Android is GPX Viewer, but there are lots of good Alternatives around.

<b>Q</b> - <b>The "View" Icon is not visible on Actionbar.</b><br>
<b>A</b> - The "View" Icon is visible, by selecting one single Track of the Tracklist, if you have at least one external Viewer installed on your Device. If You installed more than one Viewer, into GPS Logger's Settings you can choose which one to use. A good Viewer for Android is GPX Viewer, but there are lots of good Alternatives around.

<b>Q</b> - <b>The "Share" Icon is not visible on Actionbar.</b><br>
<b>A</b> - The "Share" Icon is visible, by selecting some Tracks of the Tracklist, if you have at least one Application installed on your Device with which to Share the Files. The Formats you will share are set on Exporting Section of GPS Logger's Settings.

<b>Q</b> - <b>My track is not shown (or partially shown) in Google Earth.</b><br>
<b>A</b> - GPS Logger might be set to show the Track in 3D, and the Track may be hidden under the Terrain. Please go in the Exportation Settings, switch the Altitude Mode to "Projected to ground" and try again.

<b>Q</b> - <b>The App sometimes stops recording when running in Background.</b><br>
<b>A</b> - The App may have been closed by the System during the Background Recording. To avoid it, as first Step, go on the Android Settings and turn off all Battery Monitoring and Optimizations for GPS Logger. On Android 9+ check also that the Application is NOT Background Restricted and verify that the Background Activity is allowed. Unfortunately any Device Brand implemented in a different Way the Settings to keep safe the Background Apps, so a small Research must be done. For example, for some brands you have to whitelist the background apps, whilst for some others you have to set the "high performances" power saving mode. Moreover some Anti-Viruses are very aggressive with long running Apps, and must be correctly set.

<b>Q</b> - <b>Why is GPS FIX Time different from the Time of my Android Device?</b><br>
<b>A</b> - Your Android Time could differ from GPS Time depending on Time Zone and on Daylight Saving. Starting from GPSLogger v2.2.10, You can go on App's Settings, section Interface, and verify that the GPS Time is Shown in Local Timezone instead of global GPS Time (UTC based). If not, switch on the Setting. Speaking of dates, it is important to point out that the app exports all the Timestamps in UTC, as required by KML and GPX standards. The local Time is used only for Track Names, for User Convenience. As a Note, the Time of the GPS should be slightly different from Android Time (some Seconds of Difference are common).


## Update 2022

Be seure to set this value in your strings.xm file:
<string name="provider_authorities" translatable="false">[your app id].fileprovider</string>

