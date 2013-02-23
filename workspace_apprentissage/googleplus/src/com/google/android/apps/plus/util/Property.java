package com.google.android.apps.plus.util;

import android.util.Log;
import com.google.android.apps.plus.content.EsAccountsData;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Properties;

public enum Property
{
  private static Properties sProperties;
  private final boolean mCanOverride;
  private final String mDefaultValue;
  private final String mExperimentId;
  private final String mKey;

  static
  {
    AUTH_URL = new Property("AUTH_URL", 1, "debug.plus.auth.url");
    AUTH_EMAIL = new Property("AUTH_EMAIL", 2, "debug.plus.auth.email");
    AUTH_PASSWORD = new Property("AUTH_PASSWORD", 3, "debug.plus.auth.password");
    SOCIAL_ADS_URL = new Property("SOCIAL_ADS_URL", 4, "debug.plus.safe.url", "https://googleads.g.doubleclick.net/pagead/drt/m");
    NATIVE_HANGOUT_LOG = new Property("NATIVE_HANGOUT_LOG", 5, "debug.plus.hangout.native", "FALSE");
    NATIVE_WRAPPER_HANGOUT_LOG_LEVEL = new Property("NATIVE_WRAPPER_HANGOUT_LOG_LEVEL", 6, "debug.plus.hangout.tag.wrapper", "WARNING");
    HANGOUT_CAMERA_ORIENTATION = new Property("HANGOUT_CAMERA_ORIENTATION", 7, "debug.plus.camera.orientation", "");
    HANGOUT_CAMERA_MIRRORED = new Property("HANGOUT_CAMERA_MIRRORED", 8, "debug.plus.camera.mirrored", "FALSE");
    HANGOUT_STRESS_MODE = new Property("HANGOUT_STRESS_MODE", 9, "debug.plus.hangout.stress", "FALSE");
    ENABLE_HANGOUT_SWITCH = new Property("ENABLE_HANGOUT_SWITCH", 10, "debug.plus.hangout.switch", "FALSE");
    ENABLE_ADVANCED_HANGOUTS = new Property("ENABLE_ADVANCED_HANGOUTS", 11, "debug.plus.hangout.enable_adv", "TRUE");
    ENABLE_HANGOUT_STAGE_STATUS = new Property("ENABLE_HANGOUT_STAGE_STATUS", 12, "debug.plus.hangout.stage_icon", "FALSE");
    ENABLE_HANGOUT_FILMSTRIP_STATUS = new Property("ENABLE_HANGOUT_FILMSTRIP_STATUS", 13, "debug.plus.hangout.strip_icon", "FALSE");
    ENABLE_HANGOUT_RECORD_ABUSE = new Property("ENABLE_HANGOUT_RECORD_ABUSE", 14, "debug.plus.enable.rec_abuse", "FALSE");
    ENABLE_HANGOUT_RECORD_ABUSE_INTERSTITIAL = new Property("ENABLE_HANGOUT_RECORD_ABUSE_INTERSTITIAL", 15, "debug.plus.rec_abuse.warning", "FALSE");
    FORCE_HANGOUT_RECORD_ABUSE = new Property("FORCE_HANGOUT_RECORD_ABUSE", 16, "debug.plus.force.rec_abuse", "FALSE");
    ACTIVE_HANGOUT_MODE = new Property("ACTIVE_HANGOUT_MODE", 17, "debug.plus.hangout.active_mode", "DISABLE");
    PLUS_TRACE = new Property("PLUS_TRACE", 18, "debug.plus.trace");
    PLUS_CLIENTID = new Property("PLUS_CLIENTID", 19, "debug.plus.clientid", "862067606707.apps.googleusercontent.com");
    PLUS_STATICMAPS_API_KEY = new Property("PLUS_STATICMAPS_API_KEY", 20, "debug.plus.staticmaps.api_key", "AIzaSyAYfoSs86LzFMXNWJhyeGtZp0ijdZb_uGU", false);
    PLUS_FRONTEND_PATH = new Property("PLUS_FRONTEND_PATH", 21, "debug.plus.frontend.path", "/plusi/v2/ozInternal/");
    PLUS_FRONTEND_URL = new Property("PLUS_FRONTEND_URL", 22, "debug.plus.frontend.url", "www.googleapis.com");
    PLUS_APIARY_AUTH_TOKEN = new Property("PLUS_APIARY_AUTH_TOKEN", 23, "debug.plus.apiary_token");
    PLUS_BACKEND_URL = new Property("PLUS_BACKEND_URL", 24, "debug.plus.backend.url");
    POS_FRONTEND_URL = new Property("POS_FRONTEND_URL", 25, "debug.pos.frontend.url", "www.googleapis.com");
    POS_FRONTEND_PATH = new Property("POS_FRONTEND_PATH", 26, "debug.pos.frontend.path", "/pos/v1/");
    POS_BACKEND_URL = new Property("POS_BACKEND_URL", 27, "debug.pos.backend.url");
    WARM_WELCOME_ON_LOGIN = new Property("WARM_WELCOME_ON_LOGIN", 28, "debug.plus.warm.welcome", "FALSE");
    LOCATION_DEBUGGING = new Property("LOCATION_DEBUGGING", 29, "debug.plus.location.toast", "FALSE");
    ADD_PHONE_ON_PROFILE = new Property("ADD_PHONE_ON_PROFILE", 30, "debug.plus.add.phone.number", "FALSE");
    ENABLE_PLUS_PAGES = new Property("ENABLE_PLUS_PAGES", 31, "debug.plus.enable_plus_pages", "FALSE");
    TRACING_TOKEN = new Property("TRACING_TOKEN", 32, "debug.plus.tracing_token");
    TRACING_TOKEN_2 = new Property("TRACING_TOKEN_2", 33, "debug.plus.tracing_token2");
    TRACING_PATH = new Property("TRACING_PATH", 34, "debug.plus.tracing_path", ".*");
    TRACING_LEVEL = new Property("TRACING_LEVEL", 35, "debug.plus.tracing_level");
    ENABLE_SHAKE_GLOBAL_ACTION = new Property("ENABLE_SHAKE_GLOBAL_ACTION", 36, "debug.plus.enable_shake_action", "FALSE");
    ENABLE_LOCAL_SWITCHER = new Property("ENABLE_LOCAL_SWITCHER", 37, "debug.plus.enable_local", "FALSE");
    ENABLE_LOCAL_PAGE = new Property("ENABLE_LOCAL_PAGE", 38, "debug.plus.enable_local_page", "FALSE");
    ENABLE_REWIEWS = new Property("ENABLE_REWIEWS", 39, "debug.plus.enable_reviews", "FALSE");
    ENABLE_HOST_ACTIVITY = new Property("ENABLE_HOST_ACTIVITY", 40, "debug.plus.enable_host_activity", "FALSE");
    ENABLE_INSTANT_SHARE = new Property("ENABLE_INSTANT_SHARE", 41, "debug.plus.enable_instant_share", "FALSE");
    ENABLE_INSTANT_SHARE_VIDEO = new Property("ENABLE_INSTANT_SHARE_VIDEO", 42, "debug.plus.enable_instant_share_video", "FALSE");
    ENABLE_EMOTISHARE = new Property("ENABLE_EMOTISHARE", 43, "debug.plus.enable.emotishare", "FALSE", "2dab999b", false);
    EMOTISHARE_GEN1_DATE = new Property("EMOTISHARE_GEN1_DATE", 44, "debug.plus.emotishare.gen1", "0", false);
    EMOTISHARE_GEN2_DATE = new Property("EMOTISHARE_GEN2_DATE", 45, "debug.plus.emotishare.gen2", Long.toString(new GregorianCalendar(2012, 11, 27).getTimeInMillis()), false);
    EMOTISHARE_GEN3_DATE = new Property("EMOTISHARE_GEN3_DATE", 46, "debug.plus.emotishare.gen3", Long.toString(new GregorianCalendar(2013, 1, 1).getTimeInMillis()), false);
    ENABLE_STREAM_GIF_ANIMATION = new Property("ENABLE_STREAM_GIF_ANIMATION", 47, "debug.plus.enable.streamanim", "TRUE");
    ENABLE_LAB1 = new Property("ENABLE_LAB1", 48, "debug.plus.enable.lab1", "FALSE");
    ENABLE_VOLLEY_IMAGE_DOWNLOAD = new Property("ENABLE_VOLLEY_IMAGE_DOWNLOAD", 49, "debug.plus.volley_images", "FALSE");
    ENABLE_SQUARES = new Property("ENABLE_SQUARES", 50, "debug.plus.enable_squares", "FALSE", "1128676a", false);
    Property[] arrayOfProperty = new Property[51];
    arrayOfProperty[0] = ENABLE_DOGFOOD_FEATURES;
    arrayOfProperty[1] = AUTH_URL;
    arrayOfProperty[2] = AUTH_EMAIL;
    arrayOfProperty[3] = AUTH_PASSWORD;
    arrayOfProperty[4] = SOCIAL_ADS_URL;
    arrayOfProperty[5] = NATIVE_HANGOUT_LOG;
    arrayOfProperty[6] = NATIVE_WRAPPER_HANGOUT_LOG_LEVEL;
    arrayOfProperty[7] = HANGOUT_CAMERA_ORIENTATION;
    arrayOfProperty[8] = HANGOUT_CAMERA_MIRRORED;
    arrayOfProperty[9] = HANGOUT_STRESS_MODE;
    arrayOfProperty[10] = ENABLE_HANGOUT_SWITCH;
    arrayOfProperty[11] = ENABLE_ADVANCED_HANGOUTS;
    arrayOfProperty[12] = ENABLE_HANGOUT_STAGE_STATUS;
    arrayOfProperty[13] = ENABLE_HANGOUT_FILMSTRIP_STATUS;
    arrayOfProperty[14] = ENABLE_HANGOUT_RECORD_ABUSE;
    arrayOfProperty[15] = ENABLE_HANGOUT_RECORD_ABUSE_INTERSTITIAL;
    arrayOfProperty[16] = FORCE_HANGOUT_RECORD_ABUSE;
    arrayOfProperty[17] = ACTIVE_HANGOUT_MODE;
    arrayOfProperty[18] = PLUS_TRACE;
    arrayOfProperty[19] = PLUS_CLIENTID;
    arrayOfProperty[20] = PLUS_STATICMAPS_API_KEY;
    arrayOfProperty[21] = PLUS_FRONTEND_PATH;
    arrayOfProperty[22] = PLUS_FRONTEND_URL;
    arrayOfProperty[23] = PLUS_APIARY_AUTH_TOKEN;
    arrayOfProperty[24] = PLUS_BACKEND_URL;
    arrayOfProperty[25] = POS_FRONTEND_URL;
    arrayOfProperty[26] = POS_FRONTEND_PATH;
    arrayOfProperty[27] = POS_BACKEND_URL;
    arrayOfProperty[28] = WARM_WELCOME_ON_LOGIN;
    arrayOfProperty[29] = LOCATION_DEBUGGING;
    arrayOfProperty[30] = ADD_PHONE_ON_PROFILE;
    arrayOfProperty[31] = ENABLE_PLUS_PAGES;
    arrayOfProperty[32] = TRACING_TOKEN;
    arrayOfProperty[33] = TRACING_TOKEN_2;
    arrayOfProperty[34] = TRACING_PATH;
    arrayOfProperty[35] = TRACING_LEVEL;
    arrayOfProperty[36] = ENABLE_SHAKE_GLOBAL_ACTION;
    arrayOfProperty[37] = ENABLE_LOCAL_SWITCHER;
    arrayOfProperty[38] = ENABLE_LOCAL_PAGE;
    arrayOfProperty[39] = ENABLE_REWIEWS;
    arrayOfProperty[40] = ENABLE_HOST_ACTIVITY;
    arrayOfProperty[41] = ENABLE_INSTANT_SHARE;
    arrayOfProperty[42] = ENABLE_INSTANT_SHARE_VIDEO;
    arrayOfProperty[43] = ENABLE_EMOTISHARE;
    arrayOfProperty[44] = EMOTISHARE_GEN1_DATE;
    arrayOfProperty[45] = EMOTISHARE_GEN2_DATE;
    arrayOfProperty[46] = EMOTISHARE_GEN3_DATE;
    arrayOfProperty[47] = ENABLE_STREAM_GIF_ANIMATION;
    arrayOfProperty[48] = ENABLE_LAB1;
    arrayOfProperty[49] = ENABLE_VOLLEY_IMAGE_DOWNLOAD;
    arrayOfProperty[50] = ENABLE_SQUARES;
  }

  private Property(String paramString)
  {
    this(paramString, null, true);
  }

  private Property(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, true);
  }

  private Property(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    this.mKey = paramString1;
    this.mDefaultValue = getDefaultProperty(paramString1, paramString2);
    this.mExperimentId = paramString3;
    this.mCanOverride = paramBoolean;
  }

  private Property(String paramString1, String paramString2, boolean paramBoolean)
  {
    this(paramString1, paramString2, null, paramBoolean);
  }

  private static String getDefaultProperty(String paramString1, String paramString2)
  {
    InputStream localInputStream;
    if (sProperties == null)
    {
      sProperties = new Properties();
      localInputStream = Property.class.getClassLoader().getResourceAsStream("com/google/android/apps/plusone/debug.prop");
      if (localInputStream == null);
    }
    try
    {
      sProperties.load(localInputStream);
      if (sProperties.containsKey(paramString1))
        paramString2 = sProperties.getProperty(paramString1);
      return paramString2;
    }
    catch (IOException localIOException)
    {
      while (true)
        Log.e("EsProperty", "Cannot load debug.prop");
    }
  }

  public static ArrayList<String> getExperimentIds()
  {
    ArrayList localArrayList = new ArrayList();
    for (Property localProperty : values())
      if (localProperty.mExperimentId != null)
        localArrayList.add(localProperty.mExperimentId);
    return localArrayList;
  }

  public final String get()
  {
    if (this.mExperimentId != null);
    for (String str = EsAccountsData.getExperiment(this.mExperimentId, this.mDefaultValue); ; str = this.mDefaultValue)
    {
      if (this.mCanOverride)
        str = SystemProperties.get(this.mKey, str);
      return str;
    }
  }

  public final boolean getBoolean()
  {
    return "TRUE".equalsIgnoreCase(get());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.Property
 * JD-Core Version:    0.6.2
 */