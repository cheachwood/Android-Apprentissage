package com.google.android.apps.plus.analytics;

import android.content.Context;
import com.google.api.services.plusi.model.FavaDiagnosticsNamespacedType;
import com.google.api.services.plusi.model.OutputData;

public enum OzViews
{
  private final FavaDiagnosticsNamespacedType mFavaDiagnosticsNamespacedType = new FavaDiagnosticsNamespacedType();
  private final OutputData mViewData;

  static
  {
    HOME = new OzViews("HOME", 1, "str", Integer.valueOf(1));
    NOTIFICATIONS = new OzViews("NOTIFICATIONS", 2, "str", Integer.valueOf(8));
    GENERAL_SETTINGS = new OzViews("GENERAL_SETTINGS", 3, "Settings", Integer.valueOf(1));
    LOOP_EVERYONE = new OzViews("LOOP_EVERYONE", 4, "str", Integer.valueOf(1), Integer.valueOf(1));
    LOOP_CIRCLES = new OzViews("LOOP_CIRCLES", 5, "str", Integer.valueOf(1), Integer.valueOf(2));
    LOOP_NEARBY = new OzViews("LOOP_NEARBY", 6, "str", Integer.valueOf(1), Integer.valueOf(4));
    LOOP_MANAGE = new OzViews("LOOP_MANAGE", 7, "str", Integer.valueOf(10));
    LOOP_WHATS_HOT = new OzViews("LOOP_WHATS_HOT", 8, "xplr", Integer.valueOf(1));
    LOOP_USER = new OzViews("LOOP_USER", 9, "pr", null, "pr", null, Integer.valueOf(7));
    COMPOSE = new OzViews("COMPOSE", 10, "ttn", Integer.valueOf(1));
    LOCATION_PICKER = new OzViews("LOCATION_PICKER", 11, "ttn", Integer.valueOf(3));
    CIRCLE_PICKER = new OzViews("CIRCLE_PICKER", 12, "ttn", Integer.valueOf(2));
    PEOPLE_PICKER = new OzViews("PEOPLE_PICKER", 13, "ttn", Integer.valueOf(4));
    COMMENT = new OzViews("COMMENT", 14, "ttn", Integer.valueOf(5));
    SHARE = new OzViews("SHARE", 15, "ttn", Integer.valueOf(1));
    RESHARE = new OzViews("RESHARE", 16, "ttn", Integer.valueOf(1));
    ACTIVITY = new OzViews("ACTIVITY", 17, "pr", null, "plu", null);
    PROFILE = new OzViews("PROFILE", 18, "pr", null, "pr", null, Integer.valueOf(2));
    CIRCLE_SETTINGS = new OzViews("CIRCLE_SETTINGS", 19, "Settings", Integer.valueOf(11));
    PEOPLE_IN_CIRCLES = new OzViews("PEOPLE_IN_CIRCLES", 20, "sg", Integer.valueOf(2));
    ADD_CIRCLE = new OzViews("ADD_CIRCLE", 21, "sg", Integer.valueOf(8));
    ADD_TO_CIRCLE = new OzViews("ADD_TO_CIRCLE", 22, "sg", Integer.valueOf(14));
    PEOPLE_SEARCH = new OzViews("PEOPLE_SEARCH", 23, "pr", Integer.valueOf(10));
    SEARCH = new OzViews("SEARCH", 24, "se", Integer.valueOf(1));
    PLUSONE = new OzViews("PLUSONE", 25, "plusone", Integer.valueOf(1));
    REMOVE_FROM_CIRCLE = new OzViews("REMOVE_FROM_CIRCLE", 26, "sg", Integer.valueOf(11));
    ADD_PERSON_TO_CIRCLES = new OzViews("ADD_PERSON_TO_CIRCLES", 27, "sg", Integer.valueOf(6));
    PEOPLE_BLOCKED = new OzViews("PEOPLE_BLOCKED", 28, "sg", Integer.valueOf(10));
    WW_SUGGESTIONS = new OzViews("WW_SUGGESTIONS", 29, "getstarted", Integer.valueOf(2));
    PHOTO = new OzViews("PHOTO", 30, "phst", Integer.valueOf(5));
    PHOTOS_HOME = new OzViews("PHOTOS_HOME", 31, "phst", Integer.valueOf(6));
    PHOTOS_LIST = new OzViews("PHOTOS_LIST", 32, "phst", Integer.valueOf(4));
    PHOTO_PICKER = new OzViews("PHOTO_PICKER", 33, "ttn", Integer.valueOf(29));
    VIDEO = new OzViews("VIDEO", 34, "lightbox2", Integer.valueOf(27));
    ALBUMS_OF_USER = new OzViews("ALBUMS_OF_USER", 35, "pr", Integer.valueOf(3));
    INSTANT_UPLOAD_GALLERY = new OzViews("INSTANT_UPLOAD_GALLERY", 36, "phst", Integer.valueOf(30));
    CONVERSATIONS = new OzViews("CONVERSATIONS", 37, "messenger", Integer.valueOf(1));
    CONVERSATION_GROUP = new OzViews("CONVERSATION_GROUP", 38, "messenger", Integer.valueOf(2));
    CONVERSATION_ONE_ON_ONE = new OzViews("CONVERSATION_ONE_ON_ONE", 39, "messenger", Integer.valueOf(3));
    CONVERSATION_START_NEW = new OzViews("CONVERSATION_START_NEW", 40, "messenger", Integer.valueOf(4));
    CONVERSATION_PARTICIPANT_LIST = new OzViews("CONVERSATION_PARTICIPANT_LIST", 41, "messenger", Integer.valueOf(5));
    CONVERSATION_INVITE = new OzViews("CONVERSATION_INVITE", 42, "messenger", Integer.valueOf(6));
    HANGOUT = new OzViews("HANGOUT", 43, "h", Integer.valueOf(1));
    HANGOUT_START_NEW = new OzViews("HANGOUT_START_NEW", 44, "h", Integer.valueOf(2));
    HANGOUT_PARTICIPANTS = new OzViews("HANGOUT_PARTICIPANTS", 45, "h", Integer.valueOf(3));
    NOTIFICATIONS_WIDGET = new OzViews("NOTIFICATIONS_WIDGET", 46, "nots", Integer.valueOf(1));
    NOTIFICATIONS_CIRCLE = new OzViews("NOTIFICATIONS_CIRCLE", 47, "nots", Integer.valueOf(2));
    NOTIFICATIONS_SYSTEM = new OzViews("NOTIFICATIONS_SYSTEM", 48, "nots", Integer.valueOf(3));
    CONTACTS_CIRCLELIST = new OzViews("CONTACTS_CIRCLELIST", 49, "sg", Integer.valueOf(7));
    CONTACTS_SYNC_CONFIG = new OzViews("CONTACTS_SYNC_CONFIG", 50, "settings", Integer.valueOf(10));
    PLATFORM_PLUS_ONE = new OzViews("PLATFORM_PLUS_ONE", 51, "plusone", Integer.valueOf(3));
    PLATFORM_THIRD_PARTY_APP = new OzViews("PLATFORM_THIRD_PARTY_APP", 52, "plusone", Integer.valueOf(2));
    EVENT = new OzViews("EVENT", 53, "oevt", Integer.valueOf(6));
    CREATE_EVENT = new OzViews("CREATE_EVENT", 54, "oevt", Integer.valueOf(8));
    MY_EVENTS = new OzViews("MY_EVENTS", 55, "oevt", Integer.valueOf(5));
    EVENT_THEMES = new OzViews("EVENT_THEMES", 56, "oevt", Integer.valueOf(10));
    SQUARE_LANDING = new OzViews("SQUARE_LANDING", 57, "sq", Integer.valueOf(1));
    SQUARE_HOME = new OzViews("SQUARE_HOME", 58, "sq", Integer.valueOf(3));
    SQUARE_MEMBERS = new OzViews("SQUARE_MEMBERS", 59, "sq", Integer.valueOf(4));
    SQUARE_SEARCH = new OzViews("SQUARE_SEARCH", 60, "sq", Integer.valueOf(8));
    OOB_CAMERA_SYNC = new OzViews("OOB_CAMERA_SYNC", 61, "oob", Integer.valueOf(10));
    OOB_ADD_PEOPLE_VIEW = new OzViews("OOB_ADD_PEOPLE_VIEW", 62, "oob", Integer.valueOf(18));
    OOB_IMPROVE_CONTACTS_VIEW = new OzViews("OOB_IMPROVE_CONTACTS_VIEW", 63, "oob", Integer.valueOf(19));
    OzViews[] arrayOfOzViews = new OzViews[64];
    arrayOfOzViews[0] = UNKNOWN;
    arrayOfOzViews[1] = HOME;
    arrayOfOzViews[2] = NOTIFICATIONS;
    arrayOfOzViews[3] = GENERAL_SETTINGS;
    arrayOfOzViews[4] = LOOP_EVERYONE;
    arrayOfOzViews[5] = LOOP_CIRCLES;
    arrayOfOzViews[6] = LOOP_NEARBY;
    arrayOfOzViews[7] = LOOP_MANAGE;
    arrayOfOzViews[8] = LOOP_WHATS_HOT;
    arrayOfOzViews[9] = LOOP_USER;
    arrayOfOzViews[10] = COMPOSE;
    arrayOfOzViews[11] = LOCATION_PICKER;
    arrayOfOzViews[12] = CIRCLE_PICKER;
    arrayOfOzViews[13] = PEOPLE_PICKER;
    arrayOfOzViews[14] = COMMENT;
    arrayOfOzViews[15] = SHARE;
    arrayOfOzViews[16] = RESHARE;
    arrayOfOzViews[17] = ACTIVITY;
    arrayOfOzViews[18] = PROFILE;
    arrayOfOzViews[19] = CIRCLE_SETTINGS;
    arrayOfOzViews[20] = PEOPLE_IN_CIRCLES;
    arrayOfOzViews[21] = ADD_CIRCLE;
    arrayOfOzViews[22] = ADD_TO_CIRCLE;
    arrayOfOzViews[23] = PEOPLE_SEARCH;
    arrayOfOzViews[24] = SEARCH;
    arrayOfOzViews[25] = PLUSONE;
    arrayOfOzViews[26] = REMOVE_FROM_CIRCLE;
    arrayOfOzViews[27] = ADD_PERSON_TO_CIRCLES;
    arrayOfOzViews[28] = PEOPLE_BLOCKED;
    arrayOfOzViews[29] = WW_SUGGESTIONS;
    arrayOfOzViews[30] = PHOTO;
    arrayOfOzViews[31] = PHOTOS_HOME;
    arrayOfOzViews[32] = PHOTOS_LIST;
    arrayOfOzViews[33] = PHOTO_PICKER;
    arrayOfOzViews[34] = VIDEO;
    arrayOfOzViews[35] = ALBUMS_OF_USER;
    arrayOfOzViews[36] = INSTANT_UPLOAD_GALLERY;
    arrayOfOzViews[37] = CONVERSATIONS;
    arrayOfOzViews[38] = CONVERSATION_GROUP;
    arrayOfOzViews[39] = CONVERSATION_ONE_ON_ONE;
    arrayOfOzViews[40] = CONVERSATION_START_NEW;
    arrayOfOzViews[41] = CONVERSATION_PARTICIPANT_LIST;
    arrayOfOzViews[42] = CONVERSATION_INVITE;
    arrayOfOzViews[43] = HANGOUT;
    arrayOfOzViews[44] = HANGOUT_START_NEW;
    arrayOfOzViews[45] = HANGOUT_PARTICIPANTS;
    arrayOfOzViews[46] = NOTIFICATIONS_WIDGET;
    arrayOfOzViews[47] = NOTIFICATIONS_CIRCLE;
    arrayOfOzViews[48] = NOTIFICATIONS_SYSTEM;
    arrayOfOzViews[49] = CONTACTS_CIRCLELIST;
    arrayOfOzViews[50] = CONTACTS_SYNC_CONFIG;
    arrayOfOzViews[51] = PLATFORM_PLUS_ONE;
    arrayOfOzViews[52] = PLATFORM_THIRD_PARTY_APP;
    arrayOfOzViews[53] = EVENT;
    arrayOfOzViews[54] = CREATE_EVENT;
    arrayOfOzViews[55] = MY_EVENTS;
    arrayOfOzViews[56] = EVENT_THEMES;
    arrayOfOzViews[57] = SQUARE_LANDING;
    arrayOfOzViews[58] = SQUARE_HOME;
    arrayOfOzViews[59] = SQUARE_MEMBERS;
    arrayOfOzViews[60] = SQUARE_SEARCH;
    arrayOfOzViews[61] = OOB_CAMERA_SYNC;
    arrayOfOzViews[62] = OOB_ADD_PEOPLE_VIEW;
    arrayOfOzViews[63] = OOB_IMPROVE_CONTACTS_VIEW;
  }

  private OzViews(String paramString, Integer paramInteger)
  {
    this(paramString, paramInteger, null, null);
  }

  private OzViews(String paramString, Integer paramInteger1, Integer paramInteger2)
  {
    this(paramString, paramInteger1, null, paramInteger2);
  }

  private OzViews(String paramString1, Integer paramInteger1, String paramString2, Integer paramInteger2)
  {
    this(paramString1, paramInteger1, paramString2, paramInteger2, null);
  }

  private OzViews(String paramString1, Integer paramInteger1, String paramString2, Integer paramInteger2, Integer paramInteger3)
  {
    this.mFavaDiagnosticsNamespacedType.namespace = paramString1;
    this.mFavaDiagnosticsNamespacedType.typeNum = paramInteger1;
    this.mFavaDiagnosticsNamespacedType.typeStr = paramString2;
    if ((paramInteger2 != null) || (paramInteger3 != null))
    {
      this.mViewData = new OutputData();
      if (paramInteger2 != null)
        this.mViewData.filterType = paramInteger2;
      if (paramInteger3 != null)
        this.mViewData.tab = paramInteger3;
    }
    while (true)
    {
      return;
      this.mViewData = null;
    }
  }

  public static String getName(OzViews paramOzViews)
  {
    if (paramOzViews == null);
    for (String str = null; ; str = paramOzViews.name())
      return str;
  }

  public static OzViews getViewForLogging(Context paramContext)
  {
    if ((paramContext != null) && ((paramContext instanceof InstrumentedActivity)));
    for (OzViews localOzViews = ((InstrumentedActivity)paramContext).getViewForLogging(); ; localOzViews = null)
      return localOzViews;
  }

  public static OzViews valueOf(int paramInt)
  {
    OzViews[] arrayOfOzViews = values();
    if ((arrayOfOzViews != null) && (paramInt >= 0) && (paramInt < arrayOfOzViews.length));
    for (OzViews localOzViews = arrayOfOzViews[paramInt]; ; localOzViews = UNKNOWN)
      return localOzViews;
  }

  public final FavaDiagnosticsNamespacedType getFavaDiagnosticsNamespacedType()
  {
    return this.mFavaDiagnosticsNamespacedType;
  }

  public final OutputData getViewData()
  {
    return this.mViewData;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.analytics.OzViews
 * JD-Core Version:    0.6.2
 */