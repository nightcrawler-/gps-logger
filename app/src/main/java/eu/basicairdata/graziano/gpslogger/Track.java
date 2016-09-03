/*
 * Track - Java Class for Android
 * Created by G.Capelli (BasicAirData) on 1/5/2016
 * v.2.0.0
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package eu.basicairdata.graziano.gpslogger;

import android.location.Location;

import java.text.SimpleDateFormat;

public class Track {

    // Constants
    public static final int NOT_AVAILABLE = -100000;
    public static final double MIN_ALTITUDE_STEP = 8.0;
    public static final float STANDARD_ACCURACY = 10.0f;
    public static final float SECURITY_COEFF = 1.7f;

    public static final int UM_METRIC_MS = 0;
    public static final int UM_METRIC_KMH = 1;
    public static final int UM_IMPERIAL_FPS = 8;
    public static final int UM_IMPERIAL_MPH = 9;

    public static final float M_TO_FT = 3.280839895f;
    public static final float MS_TO_MPH = 2.2369363f;
    public static final float KM_TO_MI = 0.621371192237f;

    public static final int TRACK_TYPE_STEADY   = 0;
    public static final int TRACK_TYPE_WALK     = 1;
    public static final int TRACK_TYPE_MOUNTAIN = 2;
    public static final int TRACK_TYPE_RUN      = 3;
    public static final int TRACK_TYPE_BICYCLE  = 4;
    public static final int TRACK_TYPE_CAR      = 5;
    public static final int TRACK_TYPE_FLIGHT   = 6;
    public static final int TRACK_TYPE_ND       = NOT_AVAILABLE;


    private long   id;                                              // Saved in DB
    private String Name                         = "";               // Saved in DB

    private double  Start_Latitude              = NOT_AVAILABLE;    // Saved in DB
    private double  Start_Longitude             = NOT_AVAILABLE;    // Saved in DB
    private double  Start_Altitude              = NOT_AVAILABLE;    // Saved in DB
    private double  Start_EGMAltitudeCorrection = NOT_AVAILABLE;
    private float   Start_Accuracy              = STANDARD_ACCURACY;// Saved in DB
    private float   Start_Speed                 = NOT_AVAILABLE;    // Saved in DB
    private long    Start_Time                  = NOT_AVAILABLE;    // Saved in DB

    private long    LastFix_Time                = NOT_AVAILABLE;    // Saved in DB

    private double  End_Latitude                = NOT_AVAILABLE;    // Saved in DB
    private double  End_Longitude               = NOT_AVAILABLE;    // Saved in DB
    private double  End_Altitude                = NOT_AVAILABLE;    // Saved in DB
    private double  End_EGMAltitudeCorrection   = NOT_AVAILABLE;
    private float   End_Accuracy                = STANDARD_ACCURACY;// Saved in DB
    private float   End_Speed                   = NOT_AVAILABLE;    // Saved in DB
    private long    End_Time                    = NOT_AVAILABLE;    // Saved in DB

    private double  LastStepDistance_Latitude   = NOT_AVAILABLE;    // Saved in DB
    private double  LastStepDistance_Longitude  = NOT_AVAILABLE;    // Saved in DB
    private float   LastStepDistance_Accuracy   = STANDARD_ACCURACY;// Saved in DB

    private double  LastStepAltitude_Altitude   = NOT_AVAILABLE;    // Saved in DB
    private float   LastStepAltitude_Accuracy   = STANDARD_ACCURACY;// Saved in DB

    private double  Min_Latitude                = NOT_AVAILABLE;    // Saved in DB
    private double  Min_Longitude               = NOT_AVAILABLE;    // Saved in DB

    private double  Max_Latitude                = NOT_AVAILABLE;    // Saved in DB
    private double  Max_Longitude               = NOT_AVAILABLE;    // Saved in DB

    private long    Duration                    = NOT_AVAILABLE;    // Saved in DB
    private long    Duration_Moving             = NOT_AVAILABLE;    // Saved in DB

    private float   Distance                    = NOT_AVAILABLE;    // Saved in DB
    private float   DistanceInProgress          = NOT_AVAILABLE;    // Saved in DB
    private long    DistanceLastAltitude        = NOT_AVAILABLE;    // Saved in DB

    private double  Altitude_Up                 = NOT_AVAILABLE;    // Saved in DB
    private double  Altitude_Down               = NOT_AVAILABLE;    // Saved in DB
    private double  Altitude_InProgress         = NOT_AVAILABLE;    // Saved in DB

    private float   SpeedMax                    = NOT_AVAILABLE;    // Saved in DB
    private float   SpeedAverage                = NOT_AVAILABLE;    // Saved in DB
    private float   SpeedAverageMoving          = NOT_AVAILABLE;    // Saved in DB

    private long    NumberOfLocations           = 0;                // Saved in DB
    private long    NumberOfPlacemarks          = 0;                // Saved in DB

    private int ValidMap                        = 1;                // Saved in DB
    // 1 = Map extents valid, OK generation of Thumb
    // 0 = Do not generate thumb (track crosses antimeridian)

    private int Type = TRACK_TYPE_ND;                               // Saved in DB

    // The value of the progressbar in card view
    private int Progress = 0;

    public void add(LocationExtended location) {
        if (NumberOfLocations == 0) {
            // Init "Start" variables
            Start_Latitude = location.getLocation().getLatitude();
            Start_Longitude = location.getLocation().getLongitude();
            if (location.getLocation().hasAltitude()) {
                Start_Altitude = location.getLocation().getAltitude();
            } else {
                Start_Altitude = NOT_AVAILABLE;
            }
            Start_EGMAltitudeCorrection = location.getAltitudeEGM96Correction();
            Start_Speed = location.getLocation().hasSpeed() ? location.getLocation().getSpeed() : NOT_AVAILABLE;
            Start_Accuracy = location.getLocation().hasAccuracy() ? location.getLocation().getAccuracy() : STANDARD_ACCURACY;
            Start_Time = location.getLocation().getTime();

            LastStepDistance_Latitude = Start_Latitude;
            LastStepDistance_Longitude = Start_Longitude;
            LastStepDistance_Accuracy = Start_Accuracy;

            Max_Latitude = Start_Latitude;
            Max_Longitude = Start_Longitude;
            Min_Latitude = Start_Latitude;
            Min_Longitude = Start_Longitude;

            if (Name.equals("")) {
                SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd-HHmmss");
                Name = df2.format(Start_Time);
            }

            LastFix_Time = Start_Time;
            End_Time = Start_Time;

            Duration_Moving = 0;
            Duration = 0;
            Distance = 0;
        }

        LastFix_Time = End_Time;

        End_Latitude = location.getLocation().getLatitude();
        End_Longitude = location.getLocation().getLongitude();
        if (location.getLocation().hasAltitude()) {
            End_Altitude = location.getLocation().getAltitude();
        } else {
            End_Altitude = NOT_AVAILABLE;
        }
        End_EGMAltitudeCorrection = location.getAltitudeEGM96Correction();

        End_Speed = location.getLocation().hasSpeed() ? location.getLocation().getSpeed() : NOT_AVAILABLE;
        End_Accuracy = location.getLocation().hasAccuracy() ? location.getLocation().getAccuracy() : STANDARD_ACCURACY;
        End_Time = location.getLocation().getTime();

        if (End_EGMAltitudeCorrection == NOT_AVAILABLE) getEnd_EGMAltitudeCorrection();
        if (Start_EGMAltitudeCorrection == NOT_AVAILABLE) getStart_EGMAltitudeCorrection();

        // ------------------------------------------------------------- Coords for thumb and stats

        if (ValidMap != 0) {
            if (End_Latitude > Max_Latitude) Max_Latitude = End_Latitude;
            if (End_Longitude > Max_Longitude) Max_Longitude = End_Longitude;
            if (End_Latitude < Min_Latitude) Min_Latitude = End_Latitude;
            if (End_Longitude < Min_Longitude) Min_Longitude = End_Longitude;

            if (Math.abs(LastStepDistance_Longitude - End_Longitude) > 90) ValidMap = 0;
            //  YOU PASS FROM -180 TO +180, OR REVERSE. iN THE PACIFIC OCEAN.
            //  in that case the app doesn't generate the thumb map.
        }

        // ---------------------------------------------------------------------------------- Times

        Duration = End_Time - Start_Time;
        if (End_Speed > 0) Duration_Moving += End_Time - LastFix_Time;

        // --------------------------- Spaces (Distances) increment if distance > sum of accuracies

        // -- Temp locations for "DistanceTo"
        Location LastStepDistanceLoc = new Location("TEMP");
        LastStepDistanceLoc.setLatitude(LastStepDistance_Latitude);
        LastStepDistanceLoc.setLongitude(LastStepDistance_Longitude);

        Location EndLoc = new Location("TEMP");
        EndLoc.setLatitude(End_Latitude);
        EndLoc.setLongitude(End_Longitude);
        // -----------------------------------

        DistanceInProgress = LastStepDistanceLoc.distanceTo(EndLoc);
        float DeltaDistancePlusAccuracy = DistanceInProgress + End_Accuracy;

        if (DeltaDistancePlusAccuracy < DistanceInProgress + End_Accuracy) {
            LastStepDistance_Accuracy = DeltaDistancePlusAccuracy;
            //Log.w("myApp", "[#] Track.java - LastStepDistance_Accuracy updated to " + LastStepDistance_Accuracy );
        }

        if (DistanceInProgress > End_Accuracy + LastStepDistance_Accuracy) {
            Distance += DistanceInProgress;
            if (DistanceLastAltitude != NOT_AVAILABLE) DistanceLastAltitude += DistanceInProgress;
            DistanceInProgress = 0;

            LastStepDistance_Latitude = End_Latitude;
            LastStepDistance_Longitude = End_Longitude;
            LastStepDistance_Accuracy = End_Accuracy;
        }

        // Found a first fix with altitude!!
        if ((End_Altitude != NOT_AVAILABLE) && (DistanceLastAltitude == NOT_AVAILABLE)) {
            DistanceLastAltitude = 0;
            Altitude_Up = 0;
            Altitude_Down = 0;
            if (Start_Altitude == NOT_AVAILABLE) Start_Altitude = End_Altitude;
            LastStepAltitude_Altitude = End_Altitude;
            LastStepAltitude_Accuracy = End_Accuracy;
        }

        if ((LastStepAltitude_Altitude != NOT_AVAILABLE) && (End_Altitude != NOT_AVAILABLE)) {
            Altitude_InProgress = End_Altitude - LastStepAltitude_Altitude;
            // Improve last step accuracy in case of new data elements:
            float DeltaAltitudePlusAccuracy = (float) Math.abs(Altitude_InProgress) + End_Accuracy;
            if (DeltaAltitudePlusAccuracy <= LastStepAltitude_Accuracy) {
                LastStepAltitude_Accuracy = DeltaAltitudePlusAccuracy;
                DistanceLastAltitude = 0;
                //Log.w("myApp", "[#] Track.java - LastStepAltitude_Accuracy updated to " + LastStepAltitude_Accuracy );
            }
            // Evaluate the altitude step convalidation:
            if ((Math.abs(Altitude_InProgress) > MIN_ALTITUDE_STEP)
                    && ((float) Math.abs(Altitude_InProgress) > (SECURITY_COEFF * (LastStepAltitude_Accuracy + End_Accuracy)))) {
                // Altitude step:
                // increment distance only if the inclination is relevant (assume deltah=20m in max 5000m)
                if (DistanceLastAltitude < 5000) {
                    float hypotenuse = (float) Math.sqrt((double) (DistanceLastAltitude * DistanceLastAltitude) + (Altitude_InProgress * Altitude_InProgress));
                    Distance = Distance + hypotenuse - DistanceLastAltitude;
                    //Log.w("myApp", "[#] Track.java - Distance += " + (hypotenuse - DistanceLastAltitude));
                }
                //Reset variables
                LastStepAltitude_Altitude = End_Altitude;
                LastStepAltitude_Accuracy = End_Accuracy;
                DistanceLastAltitude = 0;

                if (Altitude_InProgress > 0) Altitude_Up += Altitude_InProgress;    // Increment the correct value of Altitude UP/DOWN
                else Altitude_Down -= Altitude_InProgress;
                Altitude_InProgress = 0;
            }

        }

        // --------------------------------------------------------------------------------- Speeds

        if ((End_Speed != NOT_AVAILABLE) && (End_Speed > SpeedMax)) SpeedMax = End_Speed;
        if (Duration > 0) SpeedAverage = Distance / ((float) Duration / 1000);
        if (Duration_Moving > 0) SpeedAverageMoving = Distance / ((float) (Duration_Moving / 1000));
        NumberOfLocations++;
    }

    // Empty constructor
    public Track(){
    }

    // constructor
    public Track(String Name){
        this.Name = Name;
    }

    public void FromDB(long id, String Name, String From, String To,
                       double Start_Latitude,double Start_Longitude, double Start_Altitude,
                       float Start_Accuracy, float Start_Speed, long Start_Time, long LastFix_Time,
                       double End_Latitude, double End_Longitude, double End_Altitude,
                       float End_Accuracy, float End_Speed, long End_Time,
                       double LastStepDistance_Latitude, double LastStepDistance_Longitude, float LastStepDistance_Accuracy,
                       double LastStepAltitude_Altitude, float LastStepAltitude_Accuracy,
                       double Min_Latitude, double Min_Longitude,
                       double Max_Latitude, double Max_Longitude,
                       long Duration, long Duration_Moving, float Distance, float DistanceInProgress,
                       long DistanceLastAltitude, double Altitude_Up, double Altitude_Down,
                       double Altitude_InProgress, float SpeedMax, float   SpeedAverage,
                       float SpeedAverageMoving, long NumberOfLocations, long NumberOfPlacemarks,
                       int ValidMap, int Type) {
        this.id = id;
        this.Name = Name;

        this.Start_Latitude = Start_Latitude;
        this.Start_Longitude = Start_Longitude;
        this.Start_Altitude = Start_Altitude;
        this.Start_Accuracy = Start_Accuracy;
        this.Start_Speed = Start_Speed;
        this.Start_Time = Start_Time;

        this.LastFix_Time = LastFix_Time;

        this.End_Latitude = End_Latitude;
        this.End_Longitude = End_Longitude;
        this.End_Altitude = End_Altitude;
        this.End_Accuracy = End_Accuracy;
        this.End_Speed = End_Speed;
        this.End_Time = End_Time;

        this.LastStepDistance_Latitude = LastStepDistance_Latitude;
        this.LastStepDistance_Longitude = LastStepDistance_Longitude;
        this.LastStepDistance_Accuracy = LastStepDistance_Accuracy;

        this.LastStepAltitude_Altitude = LastStepAltitude_Altitude;
        this.LastStepAltitude_Accuracy = LastStepAltitude_Accuracy;

        this.Min_Latitude = Min_Latitude;
        this.Min_Longitude = Min_Longitude;

        this.Max_Latitude = Max_Latitude;
        this.Max_Longitude = Max_Longitude;

        this.Duration = Duration;
        this.Duration_Moving = Duration_Moving;

        this.Distance = Distance;
        this.DistanceInProgress = DistanceInProgress;
        this.DistanceLastAltitude = DistanceLastAltitude;

        this.Altitude_Up = Altitude_Up;
        this.Altitude_Down = Altitude_Down;
        this.Altitude_InProgress = Altitude_InProgress;

        this.SpeedMax = SpeedMax;
        this.SpeedAverage = SpeedAverage;
        this.SpeedAverageMoving = SpeedAverageMoving;

        this.NumberOfLocations = NumberOfLocations;
        this.NumberOfPlacemarks = NumberOfPlacemarks;

        this.ValidMap = ValidMap;
        this.Type = Type;

        EGM96 egm96 = EGM96.getInstance();
        if (egm96 != null) {
            if (egm96.isEGMGridLoaded()) {
                if (Start_Latitude != NOT_AVAILABLE) Start_EGMAltitudeCorrection = egm96.getEGMCorrection(Start_Latitude, Start_Longitude);
                if (End_Latitude != NOT_AVAILABLE) End_EGMAltitudeCorrection = egm96.getEGMCorrection(End_Latitude, End_Longitude);
            }
        }
    }


    // ------------------------------------------------------------------------ Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getStart_Latitude() {
        return Start_Latitude;
    }

    public double getStart_Longitude() {
        return Start_Longitude;
    }

    public double getStart_Altitude() {
        return Start_Altitude;
    }

    public double getStart_EGMAltitudeCorrection() {

        if (Start_EGMAltitudeCorrection == NOT_AVAILABLE) {
            EGM96 egm96 = EGM96.getInstance();
            if (egm96 != null) {
                if (egm96.isEGMGridLoaded()) {
                    if (Start_Latitude != NOT_AVAILABLE)
                        Start_EGMAltitudeCorrection = egm96.getEGMCorrection(Start_Latitude, Start_Longitude);
                }
            }
        }
        return Start_EGMAltitudeCorrection;
    }

    public float getStart_Accuracy() {
        return Start_Accuracy;
    }

    public float getStart_Speed() {
        return Start_Speed;
    }

    public long getStart_Time() {
        return Start_Time;
    }

    public long getLastFix_Time() {
        return LastFix_Time;
    }

    public double getEnd_Latitude() {
        return End_Latitude;
    }

    public double getEnd_Longitude() {
        return End_Longitude;
    }

    public double getEnd_Altitude() {
        return End_Altitude;
    }

    public double getEnd_EGMAltitudeCorrection() {
        if (End_EGMAltitudeCorrection == NOT_AVAILABLE) {
            EGM96 egm96 = EGM96.getInstance();
            if (egm96 != null) {
                if (egm96.isEGMGridLoaded()) {
                    if (End_Latitude != NOT_AVAILABLE)
                        End_EGMAltitudeCorrection = egm96.getEGMCorrection(End_Latitude, End_Longitude);
                }
            }
        }
        return End_EGMAltitudeCorrection;
    }

    public float getEnd_Accuracy() {
        return End_Accuracy;
    }

    public float getEnd_Speed() {
        return End_Speed;
    }

    public long getEnd_Time() {
        return End_Time;
    }

    public double getLastStepDistance_Latitude() {
        return LastStepDistance_Latitude;
    }

    public double getLastStepDistance_Longitude() {
        return LastStepDistance_Longitude;
    }

    public float getLastStepDistance_Accuracy() {
        return LastStepDistance_Accuracy;
    }

    public double getLastStepAltitude_Altitude() {
        return LastStepAltitude_Altitude;
    }

    public float getLastStepAltitude_Accuracy() {
        return LastStepAltitude_Accuracy;
    }

    public double getMin_Latitude() {
        return Min_Latitude;
    }

    public double getMin_Longitude() {
        return Min_Longitude;
    }

    public double getMax_Latitude() {
        return Max_Latitude;
    }

    public double getMax_Longitude() {
        return Max_Longitude;
    }

    public long getDuration() {
        return Duration;
    }

    public long getDuration_Moving() {
        return Duration_Moving;
    }

    public float getDistance() {
        return Distance;
    }

    public float getDistanceInProgress() {
        return DistanceInProgress;
    }

    public long getDistanceLastAltitude() {
        return DistanceLastAltitude;
    }

    public double getAltitude_Up() {
        return Altitude_Up;
    }

    public double getAltitude_Down() {
        return Altitude_Down;
    }

    public double getAltitude_InProgress() {
        return Altitude_InProgress;
    }

    public float getSpeedMax() {
        return SpeedMax;
    }

    public float getSpeedAverage() {
        return SpeedAverage;
    }

    public float getSpeedAverageMoving() {
        return SpeedAverageMoving;
    }

    public long getNumberOfLocations() {
        return NumberOfLocations;
    }

    public long getNumberOfPlacemarks() {
        return NumberOfPlacemarks;
    }

    public int getValidMap() {
        return ValidMap;
    }

    public int getType() {
        return Type;
    }

    public int getProgress() {
        return Progress;
    }

    public void setProgress(int progress) {
        Progress = progress;
    }

    // --------------------------------------------------------------------------------------------

    public long addPlacemark(LocationExtended location) {
        this.NumberOfPlacemarks++ ;

        if (Name.equals("")) {
            SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd-HHmmss");
            Name = df2.format(location.getLocation().getTime());
        }

        return NumberOfPlacemarks;
    }

    public float getEstimatedDistance(){ return Distance + DistanceInProgress; }


    public double getEstimatedAltitudeUp(boolean EGMCorrection){
        // Retrieve EGM Corrections if available
        if ((Start_EGMAltitudeCorrection == NOT_AVAILABLE) || (End_EGMAltitudeCorrection == NOT_AVAILABLE)) {
            EGM96 egm96 = EGM96.getInstance();
            if (egm96 != null) {
                if (egm96.isEGMGridLoaded()) {
                    if (Start_Latitude != NOT_AVAILABLE) Start_EGMAltitudeCorrection = egm96.getEGMCorrection(Start_Latitude, Start_Longitude);
                    if (End_Latitude != NOT_AVAILABLE) End_EGMAltitudeCorrection = egm96.getEGMCorrection(End_Latitude, End_Longitude);
                }
            }
        }
        double egmcorr = 0;
        if ((EGMCorrection) && ((Start_EGMAltitudeCorrection != NOT_AVAILABLE) && (End_EGMAltitudeCorrection != NOT_AVAILABLE))) {
            egmcorr = Start_EGMAltitudeCorrection - End_EGMAltitudeCorrection;
        }
        double dresultUp = Altitude_InProgress > 0 ? Altitude_Up + Altitude_InProgress : Altitude_Up;
        dresultUp -= egmcorr < 0 ? egmcorr : 0;
        double dresultDown = Altitude_InProgress < 0 ? Altitude_Down - Altitude_InProgress : Altitude_Down;
        dresultDown -= egmcorr > 0 ? egmcorr : 0;

        if (dresultUp < 0) {
            dresultDown -= dresultUp;
            dresultUp = 0;
        }
        if (dresultDown < 0) {
            dresultUp -= dresultDown;
            dresultDown = 0;
        }
        return dresultUp;
    }


    public double getEstimatedAltitudeDown(boolean EGMCorrection){
        // Retrieve EGM Corrections if available
        if ((Start_EGMAltitudeCorrection == NOT_AVAILABLE) || (End_EGMAltitudeCorrection == NOT_AVAILABLE)) {
            EGM96 egm96 = EGM96.getInstance();
            if (egm96 != null) {
                if (egm96.isEGMGridLoaded()) {
                    if (Start_Latitude != NOT_AVAILABLE) Start_EGMAltitudeCorrection = egm96.getEGMCorrection(Start_Latitude, Start_Longitude);
                    if (End_Latitude != NOT_AVAILABLE) End_EGMAltitudeCorrection = egm96.getEGMCorrection(End_Latitude, End_Longitude);
                }
            }
        }
        double egmcorr = 0;
        if ((EGMCorrection) && ((Start_EGMAltitudeCorrection != NOT_AVAILABLE) && (End_EGMAltitudeCorrection != NOT_AVAILABLE))) {
            egmcorr = Start_EGMAltitudeCorrection - End_EGMAltitudeCorrection;
        }
        double dresultUp = Altitude_InProgress > 0 ? Altitude_Up + Altitude_InProgress : Altitude_Up;
        dresultUp -= egmcorr < 0 ? egmcorr : 0;
        double dresultDown = Altitude_InProgress < 0 ? Altitude_Down - Altitude_InProgress : Altitude_Down;
        dresultDown -= egmcorr > 0 ? egmcorr : 0;

        if (dresultUp < 0) {
            dresultDown -= dresultUp;
            dresultUp = 0;
        }
        if (dresultDown < 0) {
            dresultUp -= dresultDown;
            dresultDown = 0;
        }
        return dresultDown;
    }

    public double getEstimatedAltitudeGap(boolean EGMCorrection){
        return getEstimatedAltitudeUp(EGMCorrection) - getEstimatedAltitudeDown(EGMCorrection);
    }

    // ---------------------------------------------- Strings functions, returning formatted values

    public String getFormattedBearing() {
        if (End_Latitude != NOT_AVAILABLE) {
            if (((Start_Latitude == End_Latitude) && (Start_Longitude == End_Longitude)) || (Distance == 0)) return "";
            Location EndLoc = new Location("TEMP");
            EndLoc.setLatitude(End_Latitude);
            EndLoc.setLongitude(End_Longitude);
            Location StartLoc = new Location("TEMP");
            StartLoc.setLatitude(Start_Latitude);
            StartLoc.setLongitude(Start_Longitude);
            float BTo = StartLoc.bearingTo(EndLoc);
            if (BTo < 0) BTo += 360f;

            GPSApplication gpsApplication = GPSApplication.getInstance();
            int pDir = gpsApplication.getPrefShowDirections();

            switch (pDir) {
                case 0:         // NSWE
                    final String N = gpsApplication.getString(R.string.north);
                    final String S = gpsApplication.getString(R.string.south);
                    final String W = gpsApplication.getString(R.string.west);
                    final String E = gpsApplication.getString(R.string.east);
                    int dr = (int) Math.round(BTo / 22.5);
                    switch (dr) {
                        case 0:     return N;
                        case 1:     return N + N + E;
                        case 2:     return N + E;
                        case 3:     return E + N + E;
                        case 4:     return E;
                        case 5:     return E + S + E;
                        case 6:     return S + E;
                        case 7:     return S + S + E;
                        case 8:     return S;
                        case 9:     return S + S + W;
                        case 10:    return S + W;
                        case 11:    return W + S + W;
                        case 12:    return W;
                        case 13:    return W + N + W;
                        case 14:    return N + W;
                        case 15:    return N + N + W;
                        case 16:    return N;
                        default:    return "";
                    }
                case 1:         // Angle
                    return String.valueOf(Math.round(BTo));
                default:
                    return String.valueOf(Math.round(BTo));
            }
        }
        return "";
    }

    public String getFormattedDuration() {
        if (Duration != NOT_AVAILABLE) {
            long time = Duration / 1000;
            String seconds = Integer.toString((int) (time % 60));
            String minutes = Integer.toString((int) ((time % 3600) / 60));
            String hours = Integer.toString((int) (time / 3600));
            for (int i = 0; i < 2; i++) {
                if (seconds.length() < 2) {
                    seconds = "0" + seconds;
                }
                if (minutes.length() < 2) {
                    minutes = "0" + minutes;
                }
                if (hours.length() < 2) {
                    hours = "0" + hours;
                }
            }
            return hours.equals("00") ? minutes + ":" + seconds : hours + ":" + minutes + ":" + seconds;
        }
        return "";
    }

    public String getFormattedTimeMoving() {
        if (Duration_Moving != NOT_AVAILABLE) {
            long time = Duration_Moving / 1000;
            String seconds = Integer.toString((int) (time % 60));
            String minutes = Integer.toString((int) ((time % 3600) / 60));
            String hours = Integer.toString((int) (time / 3600));
            for (int i = 0; i < 2; i++) {
                if (seconds.length() < 2) {
                    seconds = "0" + seconds;
                }
                if (minutes.length() < 2) {
                    minutes = "0" + minutes;
                }
                if (hours.length() < 2) {
                    hours = "0" + hours;
                }
            }
            return hours.equals("00") ? minutes + ":" + seconds : hours + ":" + minutes + ":" + seconds;
        }
        return "";
    }

    // Returns the time, based on preferences (Total or Moving)
    public  String getFormattedPrefTime() {
        GPSApplication gpsApplication = GPSApplication.getInstance();
        int pTime = gpsApplication.getPrefShowTrackStatsType();
        switch (pTime) {
            case 0:         // Total based
                return this.getFormattedDuration();
            case 1:         // Moving based
                return this.getFormattedTimeMoving();
            default:
                return this.getFormattedDuration();
        }
    }

    public String getFormattedSpeedMax() {
        if ((SpeedMax != NOT_AVAILABLE) && (Duration > 0)) {
            GPSApplication gpsApplication = GPSApplication.getInstance();
            int UM = gpsApplication.getPrefUM();
            switch (UM) {
                case UM_METRIC_KMH:     return String.valueOf(Math.round(SpeedMax * 3.6f));
                case UM_METRIC_MS:      return String.valueOf(Math.round(SpeedMax));
                case UM_IMPERIAL_MPH:   return String.valueOf(Math.round(SpeedMax * MS_TO_MPH));
                case UM_IMPERIAL_FPS:   return String.valueOf(Math.round(SpeedMax * M_TO_FT));
            }
        }
        return "";
    }

    public String getFormattedSpeedAverage() {
        if ((SpeedAverage != NOT_AVAILABLE) && (Duration > 0)) {
            GPSApplication gpsApplication = GPSApplication.getInstance();
            int UM = gpsApplication.getPrefUM();
            switch (UM) {
                case UM_METRIC_KMH:     return String.format("%.1f", (SpeedAverage * 3.6f));
                case UM_METRIC_MS:      return String.format("%.1f", (SpeedAverage));
                case UM_IMPERIAL_MPH:   return String.format("%.1f", (SpeedAverage * MS_TO_MPH));
                case UM_IMPERIAL_FPS:   return String.format("%.1f", (SpeedAverage * M_TO_FT));
            }
        }
        return "";
    }

    public String getFormattedSpeedAverageMoving() {
        if ((SpeedAverageMoving != NOT_AVAILABLE) && (Duration_Moving > 0)) {
            GPSApplication gpsApplication = GPSApplication.getInstance();
            int UM = gpsApplication.getPrefUM();
            switch (UM) {
                case UM_METRIC_KMH:     return String.format("%.1f", (SpeedAverageMoving * 3.6f));
                case UM_METRIC_MS:      return String.format("%.1f", (SpeedAverageMoving));
                case UM_IMPERIAL_MPH:   return String.format("%.1f", (SpeedAverageMoving * MS_TO_MPH));
                case UM_IMPERIAL_FPS:   return String.format("%.1f", (SpeedAverageMoving * M_TO_FT));
            }
        }
        return "";
    }


    // Returns the average speed, based on preferences (Total or Moving)
    public String getFormattedPrefSpeedAverage() {
        GPSApplication gpsApplication = GPSApplication.getInstance();
        int pTime = gpsApplication.getPrefShowTrackStatsType();
        switch (pTime) {
            case 0:         // Total based
                return this.getFormattedSpeedAverage();
            case 1:         // Moving based
                return this.getFormattedSpeedAverageMoving();
            default:
                return this.getFormattedSpeedAverage();
        }
    }


    public String getFormattedDistance() {
        if (Duration > 0) {
            GPSApplication gpsApplication = GPSApplication.getInstance();
            int UM = gpsApplication.getPrefUM();
            switch (UM) {
                case UM_METRIC_KMH:
                    if (Distance < 1000) return String.format("%.0f", (Math.floor(getEstimatedDistance())));
                    else return String.format("%.1f", ((Math.floor(getEstimatedDistance() / 100.0)))/10.0);
                case UM_METRIC_MS:
                    if (Distance < 1000) return String.format("%.0f", (Math.floor(getEstimatedDistance())));
                    else return String.format("%.1f", ((Math.floor(getEstimatedDistance() / 100.0)))/10.0);
                case UM_IMPERIAL_MPH:
                    if ((Distance * M_TO_FT) < 1000) return String.format("%.0f", (Math.floor(getEstimatedDistance() * M_TO_FT)));
                    else return String.format("%.1f", ((Math.floor((getEstimatedDistance() * KM_TO_MI) / 100.0)))/10.0);
                case UM_IMPERIAL_FPS:
                    if ((Distance * M_TO_FT) < 1000) return String.format("%.0f", (Math.floor(getEstimatedDistance() * M_TO_FT)));
                    else return String.format("%.1f", ((Math.floor((getEstimatedDistance() * KM_TO_MI) / 100.0)))/10.0);
            }
        }
        return "";
    }

    public String getFormattedAltitudeGap(boolean EGMCorrection) {
        GPSApplication gpsApplication = GPSApplication.getInstance();
        int UM = gpsApplication.getPrefUM();
        switch (UM) {
            case UM_METRIC_KMH:     return Duration > 0 ? String.valueOf(Math.round(getEstimatedAltitudeGap(EGMCorrection))) : "";
            case UM_METRIC_MS:      return Duration > 0 ? String.valueOf(Math.round(getEstimatedAltitudeGap(EGMCorrection))) : "";
            case UM_IMPERIAL_MPH:   return Duration > 0 ? String.valueOf(Math.round(getEstimatedAltitudeGap(EGMCorrection) * M_TO_FT)) : "";
            case UM_IMPERIAL_FPS:   return Duration > 0 ? String.valueOf(Math.round(getEstimatedAltitudeGap(EGMCorrection) * M_TO_FT)) : "";
            default:                return "";
        }
    }

    // Functions not used in current version are commented out
    /*
    public String getFormattedAltitudeUp(boolean EGMCorrection) {
        GPSApplication gpsApplication = GPSApplication.getInstance();
        int UM = gpsApplication.getPrefUM();
        switch (UM) {
            case UM_METRIC_KMH:     return Duration > 0 ? String.valueOf(Math.round(getEstimatedAltitudeUp(EGMCorrection))) : "";
            case UM_METRIC_MS:      return Duration > 0 ? String.valueOf(Math.round(getEstimatedAltitudeUp(EGMCorrection))) : "";
            case UM_IMPERIAL_MPH:   return Duration > 0 ? String.valueOf(Math.round(getEstimatedAltitudeUp(EGMCorrection) * M_TO_FT)) : "";
            case UM_IMPERIAL_FPS:   return Duration > 0 ? String.valueOf(Math.round(getEstimatedAltitudeUp(EGMCorrection) * M_TO_FT)) : "";
            default:                return "";
        }
    }

    public String getFormattedAltitudeDown(boolean EGMCorrection) {
        GPSApplication gpsApplication = GPSApplication.getInstance();
        int UM = gpsApplication.getPrefUM();
        switch (UM) {
            case UM_METRIC_KMH:     return Duration > 0 ? String.valueOf(Math.round(getEstimatedAltitudeDown(EGMCorrection))) : "";
            case UM_METRIC_MS:      return Duration > 0 ? String.valueOf(Math.round(getEstimatedAltitudeDown(EGMCorrection))) : "";
            case UM_IMPERIAL_MPH:   return Duration > 0 ? String.valueOf(Math.round(getEstimatedAltitudeDown(EGMCorrection) * M_TO_FT)) : "";
            case UM_IMPERIAL_FPS:   return Duration > 0 ? String.valueOf(Math.round(getEstimatedAltitudeDown(EGMCorrection) * M_TO_FT)) : "";
            default:                return "";
        }
    }
    */

    public String getFormattedSpeedUM() {
        GPSApplication gpsApplication = GPSApplication.getInstance();
        int UM = gpsApplication.getPrefUM();
        switch (UM) {
            case UM_METRIC_KMH:     return "km/h";
            case UM_METRIC_MS:      return "m/s";
            case UM_IMPERIAL_MPH:   return "mph";
            case UM_IMPERIAL_FPS:   return "fps";
            default:                return "";
        }
    }

    public String getFormattedAltitudeUM() {
        GPSApplication gpsApplication = GPSApplication.getInstance();
        int UM = gpsApplication.getPrefUM();
        switch (UM) {
            case UM_METRIC_KMH:     return "m";
            case UM_METRIC_MS:      return "m";
            case UM_IMPERIAL_MPH:   return "ft";
            case UM_IMPERIAL_FPS:   return "ft";
            default:                return "";
        }
    }

    public String getFormattedDistanceUM() {
        GPSApplication gpsApplication = GPSApplication.getInstance();
        int UM = gpsApplication.getPrefUM();
        switch (UM) {
            case UM_METRIC_KMH:     return Distance < 1000 ? "m" : "km";
            case UM_METRIC_MS:      return Distance < 1000 ? "m" : "km";
            case UM_IMPERIAL_MPH:   return (Distance * M_TO_FT) < 1000 ? "ft" : "mi";
            case UM_IMPERIAL_FPS:   return (Distance * M_TO_FT) < 1000 ? "ft" : "mi";
        }
        return "";
    }

    /*
    public String getFormattedStartTime() {
        if (Start_Time != NOT_AVAILABLE) {
            SimpleDateFormat df2 = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss");
            return df2.format(Start_Time);
        }
        return "";
    }
    */

    public int getTrackType() {

        //if (Type != TRACK_TYPE_ND) return Type;

        if ((Distance == NOT_AVAILABLE) || (SpeedMax == NOT_AVAILABLE)) {
            if (NumberOfPlacemarks == 0) return TRACK_TYPE_ND;
            else return TRACK_TYPE_STEADY;
        }
        if ((Distance < 15.0f) || (SpeedMax == 0.0f) || (SpeedAverageMoving == NOT_AVAILABLE)) return TRACK_TYPE_STEADY;
        if (SpeedMax < (7.0f / 3.6f)) {
            if ((Altitude_Up != NOT_AVAILABLE) && (Altitude_Down != NOT_AVAILABLE))
                if ((Altitude_Down + Altitude_Up > (0.1f * Distance)) && (Distance > 500.0f)) return TRACK_TYPE_MOUNTAIN;
            else return TRACK_TYPE_WALK;
        }
        if (SpeedMax < (15.0f / 3.6f)) {
            if (SpeedAverageMoving > 8.0f / 3.6f) return TRACK_TYPE_RUN;
            else {
                if ((Altitude_Up != NOT_AVAILABLE) && (Altitude_Down != NOT_AVAILABLE))
                    if ((Altitude_Down + Altitude_Up > (0.1f * Distance)) && (Distance > 500.0f)) return TRACK_TYPE_MOUNTAIN;
                else return TRACK_TYPE_WALK;
            }
        }
        if (SpeedMax < (50.0f / 3.6f)) {
            if (SpeedAverageMoving > 20.0f / 3.6f) return TRACK_TYPE_CAR;
            if (SpeedAverageMoving > 12.0f / 3.6) return TRACK_TYPE_BICYCLE;
            else if (SpeedAverageMoving > 8.0f / 3.6f) return TRACK_TYPE_RUN;
            else {
                if ((Altitude_Up != NOT_AVAILABLE) && (Altitude_Down != NOT_AVAILABLE))
                    if ((Altitude_Down + Altitude_Up > (0.1f * Distance)) && (Distance > 500.0f))
                        return TRACK_TYPE_MOUNTAIN;
                else return TRACK_TYPE_RUN;
            }
        }
        if ((Altitude_Up != NOT_AVAILABLE) && (Altitude_Down != NOT_AVAILABLE))
            if ((Altitude_Down + Altitude_Up > 5000.0) && (SpeedMax > 300.0f / 3.6f)) return TRACK_TYPE_FLIGHT;

        return TRACK_TYPE_CAR;
    }
}
