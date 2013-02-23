package com.google.android.apps.plus.phone;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.AccountSettingsData;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.DbEmotishareMetadata;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.PhotoTaggeeData.PhotoTaggee;
import com.google.android.apps.plus.fragments.PhotoOneUpFragment;
import com.google.android.apps.plus.hangout.HangoutActivity;
import com.google.android.apps.plus.hangout.HangoutParticipantListActivity;
import com.google.android.apps.plus.hangout.HangoutRingingActivity;
import com.google.android.apps.plus.hangout.HangoutTile;
import com.google.android.apps.plus.oob.OutOfBoxResponseParcelable;
import com.google.android.apps.plus.service.EventFinishedReceiver;
import com.google.android.apps.plus.service.Hangout.Info;
import com.google.android.apps.plus.service.Hangout.LaunchSource;
import com.google.android.apps.plus.service.Hangout.RoomType;
import com.google.android.apps.plus.settings.InstantUploadSettingsActivity;
import com.google.android.apps.plus.settings.SettingsActivity;
import com.google.android.apps.plus.util.MediaStoreUtils;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.android.apps.plus.widget.EsWidgetCameraLauncherActivity;
import com.google.api.services.plusi.model.MobileOutOfBoxResponse;
import com.google.api.services.plusi.model.Place;
import com.google.api.services.plusi.model.PlaceJson;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Intents
{
  private static final List<String> INTERNAL_URLS = Arrays.asList(new String[] { "youtube.com", "google.com" });

  public static Intent getAccountsActivityIntent(Context paramContext, Intent paramIntent)
  {
    Intent localIntent = new Intent(paramContext, AccountSelectionActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    if (paramIntent != null)
      localIntent.putExtra("intent", paramIntent);
    return localIntent;
  }

  public static Intent getAddedToCircleActivityIntent(Context paramContext, EsAccount paramEsAccount, byte[] paramArrayOfByte, String paramString)
  {
    Intent localIntent = new Intent(paramContext, AddedToCircleActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("circle_actor_data", paramArrayOfByte);
    localIntent.putExtra("notif_id", paramString);
    return localIntent;
  }

  public static Intent getCameraIntentPhoto$3a35108a(String paramString)
  {
    Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
    localIntent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory(), paramString)));
    return localIntent;
  }

  public static Intent getCameraIntentVideo$7ec49240()
  {
    return new Intent("android.media.action.VIDEO_CAPTURE");
  }

  public static Intent getCheckinActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, LocationPickerActivity.class);
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("places_only", true);
    return localIntent;
  }

  public static Intent getChooseEmotiShareObjectIntent(Context paramContext, EsAccount paramEsAccount, DbEmotishareMetadata paramDbEmotishareMetadata)
  {
    Intent localIntent = new Intent(paramContext, HostEmotiShareChooserActivity.class);
    localIntent.putExtra("account", paramEsAccount);
    if (paramDbEmotishareMetadata != null)
      localIntent.putExtra("typed_image_embed", paramDbEmotishareMetadata);
    localIntent.setAction("android.intent.action.PICK");
    return localIntent;
  }

  public static Intent getChooseLocationIntent(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean, DbLocation paramDbLocation)
  {
    Intent localIntent = new Intent(paramContext, LocationPickerActivity.class);
    localIntent.setAction("android.intent.action.PICK");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("places_only", paramBoolean);
    if (paramDbLocation != null)
      localIntent.putExtra("location", paramDbLocation);
    return localIntent;
  }

  public static Intent getCircleMembershipActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean)
  {
    Intent localIntent = new Intent(paramContext, CirclesMembershipActivity.class);
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("person_id", paramString1);
    localIntent.putExtra("display_name", paramString2);
    localIntent.putExtra("empty_selection_allowed", paramBoolean);
    return localIntent;
  }

  public static Intent getCirclePostsActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("destination", 0);
    localIntent.putExtra("circle_id", paramString);
    return localIntent;
  }

  public static Intent getCirclesActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("destination", 5);
    return localIntent;
  }

  public static Intent getClearRealTimeChatIntent(EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent("com.google.android.apps.plus.realtimechat.reset");
    localIntent.putExtra("account", paramEsAccount);
    return localIntent;
  }

  public static Intent getContactsSyncConfigActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, ContactsSyncConfigActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    return localIntent;
  }

  public static Intent getConversationActivityHangoutTileIntent(Context paramContext, EsAccount paramEsAccount, long paramLong, boolean paramBoolean)
  {
    Intent localIntent = getConversationActivityIntent(paramContext, paramEsAccount, paramLong, paramBoolean);
    localIntent.putExtra("tile", HangoutTile.class.getName());
    return localIntent;
  }

  public static Intent getConversationActivityIntent(Context paramContext, EsAccount paramEsAccount, long paramLong, boolean paramBoolean)
  {
    Intent localIntent = new Intent(paramContext, ConversationActivity.class);
    localIntent.setAction("unique" + System.currentTimeMillis());
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("conversation_row_id", paramLong);
    localIntent.putExtra("is_group", paramBoolean);
    return localIntent;
  }

  public static Intent getConversationInvititationActivityIntent(Context paramContext, EsAccount paramEsAccount, long paramLong, String paramString, boolean paramBoolean)
  {
    Intent localIntent = new Intent(paramContext, InvitationActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("conversation_row_id", paramLong);
    localIntent.putExtra("inviter_id", paramString);
    localIntent.putExtra("is_group", paramBoolean);
    return localIntent;
  }

  public static Intent getCreateEventActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, NewEventActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    return localIntent;
  }

  public static Intent getEditAudienceActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString, AudienceData paramAudienceData, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    return getEditAudienceActivityIntent(paramContext, paramEsAccount, paramString, paramAudienceData, paramInt, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, false);
  }

  public static Intent getEditAudienceActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString, AudienceData paramAudienceData, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5)
  {
    Intent localIntent = new Intent(paramContext, EditAudienceActivity.class);
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("title", paramString);
    localIntent.putExtra("audience", paramAudienceData);
    localIntent.putExtra("circle_usage_type", paramInt);
    localIntent.putExtra("search_phones_enabled", paramBoolean1);
    localIntent.putExtra("search_plus_pages_enabled", paramBoolean2);
    localIntent.putExtra("search_pub_profiles_enabled", paramBoolean3);
    localIntent.putExtra("filter_null_gaia_ids", paramBoolean4);
    localIntent.putExtra("audience_is_read_only", paramBoolean5);
    return localIntent;
  }

  public static Intent getEditCommentActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3, Long paramLong, String paramString4)
  {
    Intent localIntent = new Intent(paramContext, EditCommentActivity.class);
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("activity_id", paramString1);
    localIntent.putExtra("comment_id", paramString2);
    localIntent.putExtra("comment", paramString3);
    if (paramLong != null)
      localIntent.putExtra("photo_id", paramLong);
    if (paramString4 != null)
      localIntent.putExtra("gaia_id", paramString4);
    return localIntent;
  }

  public static Intent getEditEventActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(paramContext, EditEventActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("event_id", paramString1);
    localIntent.putExtra("auth_key", paramString2);
    return localIntent;
  }

  public static Intent getEditPostActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean)
  {
    Intent localIntent = new Intent(paramContext, EditPostActivity.class);
    localIntent.setAction("android.intent.action.EDIT");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("activity_id", paramString1);
    localIntent.putExtra("content", paramString2);
    localIntent.putExtra("reshare", paramBoolean);
    return localIntent;
  }

  public static Intent getEditSquareAudienceActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString, AudienceData paramAudienceData)
  {
    Intent localIntent = new Intent(paramContext, EditSquareAudienceActivity.class);
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("title", paramString);
    localIntent.putExtra("audience", paramAudienceData);
    return localIntent;
  }

  public static Intent getEmotiShareActivityIntent(Context paramContext, EsAccount paramEsAccount, DbEmotishareMetadata paramDbEmotishareMetadata)
  {
    Intent localIntent = new Intent(paramContext, HostEmotiShareChooserActivity.class);
    localIntent.putExtra("account", paramEsAccount);
    localIntent.setAction("android.intent.action.SEND");
    return localIntent;
  }

  public static PendingIntent getEventFinishedIntent(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent(EventFinishedReceiver.sIntent);
    if (paramString != null)
      localIntent.putExtra("event_id", paramString);
    return PendingIntent.getBroadcast(paramContext, 0, localIntent, 0);
  }

  public static Intent getEventHangoutActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    return getHangoutActivityIntent(paramContext, paramEsAccount, Hangout.RoomType.EXTERNAL, null, "event", paramString, null, Hangout.LaunchSource.Event, false, false, null);
  }

  public static Intent getEventInviteeListActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent(paramContext, HostEventInviteeListActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("event_id", paramString1);
    localIntent.putExtra("owner_id", paramString3);
    localIntent.putExtra("auth_key", paramString2);
    return localIntent;
  }

  public static Intent getEventLocationActivityIntent(Context paramContext, EsAccount paramEsAccount, Place paramPlace)
  {
    Intent localIntent = new Intent(paramContext, EventLocationActivity.class);
    localIntent.setAction("android.intent.action.PICK");
    localIntent.putExtra("account", paramEsAccount);
    if (paramPlace != null)
      localIntent.putExtra("location", PlaceJson.getInstance().toByteArray(paramPlace));
    return localIntent;
  }

  public static Intent getEventThemePickerIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, EventThemePickerActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    return localIntent;
  }

  public static Intent getEventsActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("destination", 2);
    return localIntent;
  }

  public static Intent getFakeConversationActivityIntent(Context paramContext, EsAccount paramEsAccount, Data.Participant paramParticipant, boolean paramBoolean)
  {
    Intent localIntent = new Intent(paramContext, ConversationActivity.class);
    localIntent.setAction("unique" + System.currentTimeMillis());
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("participant", paramParticipant);
    localIntent.putExtra("is_group", false);
    return localIntent;
  }

  public static Intent getGoogleFeedbackIntent$7ec49240()
  {
    return new Intent("android.intent.action.BUG_REPORT");
  }

  public static Intent getHangoutActivityAudienceIntent(Context paramContext, EsAccount paramEsAccount, Hangout.Info paramInfo, boolean paramBoolean, AudienceData paramAudienceData)
  {
    Intent localIntent = new Intent(paramContext, HangoutActivity.class);
    localIntent.setAction("unique" + System.currentTimeMillis());
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("hangout_info", paramInfo);
    localIntent.putExtra("hangout_skip_green_room", paramBoolean);
    if (paramAudienceData != null)
      localIntent.putExtra("audience", paramAudienceData);
    return localIntent;
  }

  public static Intent getHangoutActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("destination", 3);
    return localIntent;
  }

  public static Intent getHangoutActivityIntent(Context paramContext, EsAccount paramEsAccount, Hangout.Info paramInfo, boolean paramBoolean, ArrayList<Data.Participant> paramArrayList)
  {
    Intent localIntent = new Intent(paramContext, HangoutActivity.class);
    localIntent.setAction("unique" + System.currentTimeMillis());
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("hangout_info", paramInfo);
    localIntent.putExtra("hangout_skip_green_room", paramBoolean);
    localIntent.putExtra("hangout_participants", paramArrayList);
    return localIntent;
  }

  public static Intent getHangoutActivityIntent(Context paramContext, EsAccount paramEsAccount, Hangout.RoomType paramRoomType, String paramString1, String paramString2, String paramString3, String paramString4, Hangout.LaunchSource paramLaunchSource, boolean paramBoolean1, boolean paramBoolean2, ArrayList<Data.Participant> paramArrayList)
  {
    Intent localIntent = new Intent(paramContext, HangoutActivity.class);
    localIntent.setAction("unique" + System.currentTimeMillis());
    localIntent.putExtra("account", paramEsAccount);
    return getHangoutActivityIntent(paramContext, paramEsAccount, new Hangout.Info(paramRoomType, paramString1, paramString2, paramString3, null, paramLaunchSource, false), paramBoolean2, paramArrayList);
  }

  public static Intent getHangoutParticipantListActivityIntent(Context paramContext, EsAccount paramEsAccount, ArrayList<Data.Participant> paramArrayList)
  {
    Intent localIntent = new Intent(paramContext, HangoutParticipantListActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("conversation_row_id", -1L);
    localIntent.putExtra("hangout_participants", paramArrayList);
    return localIntent;
  }

  public static Intent getHangoutRingingActivityIntent$55105fd9(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, Hangout.Info paramInfo, boolean paramBoolean)
  {
    Intent localIntent = new Intent(paramContext, HangoutRingingActivity.class);
    localIntent.setAction("unique" + System.currentTimeMillis());
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("hangout_inviter_id", paramString1);
    localIntent.putExtra("hangout_inviter_name", paramString2);
    localIntent.putExtra("hangout_info", paramInfo);
    localIntent.putExtra("hangout_is_lite", paramBoolean);
    return localIntent;
  }

  public static Intent getHomeOobActivityIntent(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, MobileOutOfBoxResponse paramMobileOutOfBoxResponse, AccountSettingsData paramAccountSettingsData)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("run_oob", true);
    if (paramMobileOutOfBoxResponse != null)
      localIntent.putExtra("network_oob", new OutOfBoxResponseParcelable(paramMobileOutOfBoxResponse));
    if (paramAccountSettingsData != null)
      localIntent.putExtra("plus_pages", paramAccountSettingsData);
    if (paramIntent != null)
      localIntent.putExtra("intent", paramIntent);
    return localIntent;
  }

  public static Intent getHostNavigationActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    return localIntent;
  }

  public static Intent getHostedEventIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    return getHostedEventIntent(paramContext, paramEsAccount, paramString1, paramInt, paramString2, null, paramString4, null, null);
  }

  private static Intent getHostedEventIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    Intent localIntent = new Intent(paramContext, EventActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("event_id", paramString1);
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("owner_id", paramString2);
    localIntent.putExtra("invitation_token", paramString3);
    localIntent.putExtra("auth_key", paramString6);
    localIntent.putExtra("notif_type", paramInt);
    localIntent.putExtra("notif_id", paramString4);
    localIntent.putExtra("rsvp", paramString5);
    return localIntent;
  }

  public static Intent getHostedEventIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3)
  {
    return getHostedEventIntent(paramContext, paramEsAccount, paramString1, paramString2, null, null, null);
  }

  public static Intent getHostedEventIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    Intent localIntent = getHostedEventIntent(paramContext, paramEsAccount, paramString1, 0, paramString2, paramString3, null, paramString4, paramString5);
    localIntent.addFlags(67108864);
    return localIntent;
  }

  public static Intent getHostedProfileAlbumsIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("destination", 7);
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("person_id", paramString1);
    localIntent.putExtra("notif_id", null);
    localIntent.putExtra("photos_home", paramEsAccount.isMyGaiaId(EsPeopleData.extractGaiaId(paramString1)));
    return localIntent;
  }

  public static Intent getHostedProfileIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, int paramInt)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      localIntent.putExtra("account", paramEsAccount);
      localIntent.putExtra("person_id", paramString1);
      localIntent.putExtra("notif_id", null);
      return localIntent;
      localIntent.putExtra("destination", 1);
      continue;
      localIntent.putExtra("destination", 7);
    }
  }

  public static Intent getInstantUploadSettingsActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, InstantUploadSettingsActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    return localIntent;
  }

  public static Intent getLicenseActivityIntent(Context paramContext)
  {
    return new Intent(paramContext, LicenseActivity.class);
  }

  public static Intent getLocalReviewActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString, int paramInt1, int paramInt2)
  {
    Intent localIntent = new Intent(paramContext, LocalReviewActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("person_id", paramString);
    localIntent.putExtra("local_review_type", paramInt1);
    localIntent.putExtra("local_review_index", paramInt2);
    return localIntent;
  }

  public static Intent getLocationSettingActivityIntent()
  {
    int i = 1;
    if (Build.VERSION.SDK_INT < 16)
      if (i == 0)
        break label81;
    label81: for (String str1 = "android.settings.LOCATION_SOURCE_SETTINGS"; ; str1 = "com.google.android.gsf.GOOGLE_LOCATION_SETTINGS")
    {
      return new Intent(str1);
      if (Build.VERSION.SDK_INT <= 16)
      {
        String str2 = Build.VERSION.RELEASE;
        if ((TextUtils.isEmpty(str2)) || (str2.equals("4.1")) || (str2.startsWith("4.1.0")) || (str2.startsWith("4.1.1")))
          break;
      }
      i = 0;
      break;
    }
  }

  public static Intent getMessengerActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("destination", 4);
    localIntent.putExtra("account", paramEsAccount);
    return localIntent;
  }

  public static Intent getMissedHangoutCallbackIntent(Context paramContext, EsAccount paramEsAccount, Hangout.Info paramInfo, AudienceData paramAudienceData)
  {
    Intent localIntent = new Intent(paramContext, HangoutActivity.class);
    localIntent.setAction("unique" + System.currentTimeMillis());
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("hangout_info", new Hangout.Info(paramInfo.getRoomType(), paramInfo.getDomain(), null, paramInfo.getId(), paramInfo.getNick(), Hangout.LaunchSource.MissedCall, true));
    localIntent.putExtra("hangout_skip_green_room", true);
    localIntent.putExtra("audience", paramAudienceData);
    return localIntent;
  }

  public static Intent getMuteActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    Intent localIntent = new Intent(paramContext, StreamOneUpActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("activity_id", paramString);
    localIntent.putExtra("mute", true);
    return localIntent;
  }

  public static Intent getNetworkRequestsIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, NetworkTransactionsActivity.class);
    localIntent.putExtra("account", paramEsAccount);
    return localIntent;
  }

  public static Intent getNetworkStatisticsIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, NetworkStatisticsActivity.class);
    localIntent.putExtra("account", paramEsAccount);
    return localIntent;
  }

  public static Intent getNewConversationActivityIntent(Context paramContext, EsAccount paramEsAccount, AudienceData paramAudienceData)
  {
    Intent localIntent = new Intent(paramContext, NewConversationActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("audience", paramAudienceData);
    return localIntent;
  }

  public static Intent getNewHangoutActivityIntent(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean, AudienceData paramAudienceData)
  {
    Intent localIntent = new Intent(paramContext, HangoutActivity.class);
    localIntent.setAction("unique" + System.currentTimeMillis());
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("audience", paramAudienceData);
    localIntent.putExtra("hangout_ring_invitees", paramBoolean);
    localIntent.putExtra("hangout_skip_green_room", true);
    return localIntent;
  }

  public static Intent getNextOobIntent(Context paramContext, EsAccount paramEsAccount, AccountSettingsData paramAccountSettingsData, Intent paramIntent)
  {
    OobIntents localOobIntents = (OobIntents)paramIntent.getParcelableExtra("oob_intents");
    if (localOobIntents == null);
    for (Intent localIntent = null; ; localIntent = localOobIntents.getNextIntent(paramContext, paramEsAccount, paramAccountSettingsData))
      return localIntent;
  }

  public static Intent getNotificationsIntent(Context paramContext, EsAccount paramEsAccount, Cursor paramCursor)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("show_notifications", true);
    if ((paramCursor != null) && (paramCursor.moveToFirst()))
    {
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      do
      {
        localArrayList1.add(Integer.valueOf(paramCursor.getInt(15)));
        localArrayList2.add(paramCursor.getString(2));
      }
      while (paramCursor.moveToNext());
      if (!localArrayList1.isEmpty())
      {
        localIntent.putIntegerArrayListExtra("notif_types", localArrayList1);
        localIntent.putStringArrayListExtra("coalescing_codes", localArrayList2);
      }
    }
    return localIntent;
  }

  public static Intent getOobContactsSyncIntent(Context paramContext, EsAccount paramEsAccount, OobIntents paramOobIntents)
  {
    Intent localIntent = new Intent(paramContext, OobContactsSyncActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("oob_intents", paramOobIntents);
    return localIntent;
  }

  public static Intent getOobInstantUploadIntent(Context paramContext, EsAccount paramEsAccount, OobIntents paramOobIntents)
  {
    Intent localIntent = new Intent(paramContext, OobInstantUploadActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("oob_intents", paramOobIntents);
    return localIntent;
  }

  public static Intent getOobIntent(Context paramContext, EsAccount paramEsAccount, MobileOutOfBoxResponse paramMobileOutOfBoxResponse, AccountSettingsData paramAccountSettingsData, String paramString)
  {
    return OobIntents.getInitialIntent(paramContext, paramEsAccount, paramMobileOutOfBoxResponse, paramAccountSettingsData, paramString);
  }

  public static Intent getOobSelectPlusPageActivityIntent(Context paramContext, EsAccount paramEsAccount, AccountSettingsData paramAccountSettingsData, OobIntents paramOobIntents)
  {
    Intent localIntent = new Intent(paramContext, OobSelectPlusPageActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("plus_pages", paramAccountSettingsData);
    localIntent.putExtra("oob_intents", paramOobIntents);
    return localIntent;
  }

  public static Intent getOobSuggestedPeopleActivityIntent(Context paramContext, EsAccount paramEsAccount, OobIntents paramOobIntents)
  {
    Intent localIntent = new Intent(paramContext, OobSuggestedPeopleActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("oob_intents", paramOobIntents);
    return localIntent;
  }

  public static Intent getOutOfBoxActivityIntent(Context paramContext, EsAccount paramEsAccount, OobIntents paramOobIntents, MobileOutOfBoxResponse paramMobileOutOfBoxResponse, String paramString)
  {
    Intent localIntent = new Intent(paramContext, OutOfBoxActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("oob_intents", paramOobIntents);
    localIntent.putExtra("network_oob", new OutOfBoxResponseParcelable(paramMobileOutOfBoxResponse));
    localIntent.putExtra("oob_origin", paramString);
    return localIntent;
  }

  public static String getParameter(String paramString1, String paramString2)
  {
    String str1;
    int j;
    int k;
    String str2;
    if (paramString2.endsWith("="))
    {
      str1 = paramString2;
      int i = paramString1.indexOf(str1);
      if (i == -1)
        break label95;
      j = i + str1.length();
      k = paramString1.indexOf('&', j);
      if (k != -1)
        break label82;
      str2 = paramString1.substring(j);
    }
    while (true)
    {
      return str2;
      str1 = paramString2 + "=";
      break;
      label82: str2 = paramString1.substring(j, k);
      continue;
      label95: str2 = null;
    }
  }

  public static Intent getParticipantListActivityIntent(Context paramContext, EsAccount paramEsAccount, long paramLong, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    Intent localIntent = new Intent(paramContext, ParticipantListActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("conversation_row_id", paramLong);
    localIntent.putExtra("conversation_name", paramString);
    localIntent.putExtra("is_group", paramBoolean1);
    if (paramBoolean2)
      localIntent.putExtra("tile", HangoutTile.class.getName());
    return localIntent;
  }

  public static Intent getPeopleSearchActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString, boolean paramBoolean1, int paramInt, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
  {
    Intent localIntent = new Intent(paramContext, PeopleSearchActivity.class);
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("picker_mode", paramBoolean1);
    localIntent.putExtra("search_circles_usage", paramInt);
    localIntent.putExtra("search_pub_profiles_enabled", paramBoolean2);
    localIntent.putExtra("search_phones_enabled", paramBoolean3);
    localIntent.putExtra("search_plus_pages_enabled", paramBoolean4);
    localIntent.putExtra("search_in_circles_enabled", paramBoolean5);
    localIntent.putExtra("query", paramString);
    localIntent.putExtra("filter_null_gaia_ids", paramBoolean6);
    return localIntent;
  }

  public static Intent getPeopleSearchActivityIntent(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean1, int paramInt, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
  {
    return getPeopleSearchActivityIntent(paramContext, paramEsAccount, null, paramBoolean1, paramInt, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, paramBoolean6);
  }

  public static String getPersonIdFromProfileUrl(String paramString)
  {
    String str = getParameter(paramString, "pid=");
    if (str == null)
      str = paramString.substring(1 + paramString.lastIndexOf('/'));
    return str;
  }

  public static Intent getPhotoPickerIntent(Context paramContext, EsAccount paramEsAccount, String paramString, MediaRef paramMediaRef, int paramInt)
  {
    Intent localIntent = new Intent(paramContext, PhotoPickerActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("photo_picker_mode", 1);
    localIntent.putExtra("display_name", paramString);
    localIntent.putExtra("mediarefs", paramMediaRef);
    localIntent.putExtra("photo_picker_crop_mode", paramInt);
    return localIntent;
  }

  public static Intent getPostActivityIntent(Context paramContext, EsAccount paramEsAccount, MediaRef paramMediaRef)
  {
    Intent localIntent = new Intent(paramContext, PostActivity.class);
    localIntent.setAction("android.intent.action.SEND");
    if (paramMediaRef != null)
      localIntent.putExtra("android.intent.extra.STREAM", paramMediaRef);
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("is_internal", true);
    return localIntent;
  }

  public static Intent getPostActivityIntent(Context paramContext, EsAccount paramEsAccount, DbEmotishareMetadata paramDbEmotishareMetadata)
  {
    Intent localIntent = new Intent(paramContext, PostActivity.class);
    localIntent.setAction("android.intent.action.SEND");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("is_internal", true);
    if (paramDbEmotishareMetadata != null)
      localIntent.putExtra("typed_image_embed", paramDbEmotishareMetadata);
    return localIntent;
  }

  public static Intent getPostActivityIntent(Context paramContext, EsAccount paramEsAccount, DbLocation paramDbLocation)
  {
    Intent localIntent = new Intent(paramContext, PostActivity.class);
    localIntent.setAction("android.intent.action.SEND");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("is_internal", true);
    if (paramDbLocation != null)
      localIntent.putExtra("location", paramDbLocation);
    return localIntent;
  }

  public static Intent getPostActivityIntent(Context paramContext, EsAccount paramEsAccount, ArrayList<MediaRef> paramArrayList)
  {
    return getPostActivityIntent(paramContext, paramEsAccount, paramArrayList, null);
  }

  public static Intent getPostActivityIntent(Context paramContext, EsAccount paramEsAccount, ArrayList<MediaRef> paramArrayList, AudienceData paramAudienceData)
  {
    Intent localIntent = new Intent(paramContext, PostActivity.class);
    localIntent.setAction("android.intent.action.SEND_MULTIPLE");
    if ((paramArrayList != null) && (!paramArrayList.isEmpty()))
      localIntent.putExtra("android.intent.extra.STREAM", paramArrayList);
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("is_internal", true);
    if (paramAudienceData != null)
      localIntent.putExtra("audience", paramAudienceData);
    return localIntent;
  }

  public static Intent getPostCommentsActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    Intent localIntent = new Intent(paramContext, StreamOneUpActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("activity_id", paramString);
    localIntent.putExtra("refresh", false);
    return localIntent;
  }

  public static Intent getPostCommentsActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    Intent localIntent = new Intent(paramContext, StreamOneUpActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("activity_id", paramString1);
    localIntent.putExtra("com.google.plus.analytics.intent.extra.FROM_NOTIFICATION", true);
    if (paramString2 != null)
      localIntent.putExtra("notif_id", paramString2);
    localIntent.putExtra("notif_category", paramInt);
    localIntent.putExtra("refresh", true);
    localIntent.putExtra("enable_comment_action", paramBoolean2);
    return localIntent;
  }

  public static Intent getPostSearchActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    Intent localIntent = new Intent(paramContext, PostSearchActivity.class);
    localIntent.putExtra("account", paramEsAccount);
    if (paramString != null)
      localIntent.putExtra("query", paramString);
    return localIntent;
  }

  public static Intent getPostTextActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, PostTextActivity.class);
    localIntent.setAction("android.intent.action.SEND");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("is_internal", true);
    localIntent.putExtra("start_editing", true);
    return localIntent;
  }

  public static Intent getProfileActivityByGaiaIdIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    return getProfileActivityIntent(paramContext, paramEsAccount, "g:" + paramString1, paramString2, 0);
  }

  public static Intent getProfileActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(paramContext, ProfileActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("person_id", paramString1);
    localIntent.putExtra("notif_id", null);
    return localIntent;
  }

  public static Intent getProfileActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, int paramInt)
  {
    Intent localIntent = new Intent(paramContext, ProfileActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("person_id", paramString1);
    localIntent.putExtra("notif_id", paramString2);
    localIntent.putExtra("profile_view_type", paramInt);
    return localIntent;
  }

  public static Intent getProfileEditActivityIntent(Context paramContext, EsAccount paramEsAccount, int paramInt, String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(paramContext, ProfileEditActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("profile_edit_mode", paramInt);
    localIntent.putExtra("profile_edit_items_json", paramString1);
    localIntent.putExtra("profile_edit_roster_json", paramString2);
    return localIntent;
  }

  public static Intent getProfilePhotosActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    if (paramEsAccount.isMyGaiaId(EsPeopleData.extractGaiaId(paramString)));
    for (Intent localIntent = getHostedProfileAlbumsIntent(paramContext, paramEsAccount, paramString, null); ; localIntent = getProfileActivityIntent(paramContext, paramEsAccount, paramString, null, 1))
      return localIntent;
  }

  public static Intent getReshareActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString, boolean paramBoolean)
  {
    Intent localIntent = new Intent(paramContext, ReshareActivity.class);
    localIntent.setAction("android.intent.action.SEND");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("activity_id", paramString);
    localIntent.putExtra("limited", paramBoolean);
    return localIntent;
  }

  public static Intent getSelectSquareCategoryActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent(paramContext, SelectSquareCategoryActivity.class);
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("title", paramString1);
    localIntent.putExtra("square_id", paramString2);
    localIntent.putExtra("square_name", paramString3);
    return localIntent;
  }

  public static Intent getSettingsActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, SettingsActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    return localIntent;
  }

  public static Intent getSquareSearchActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, HostSquareSearchActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    return localIntent;
  }

  public static Intent getSquareStreamActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent(paramContext, HostSquareStreamActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("square_id", paramString1);
    localIntent.putExtra("stream_id", paramString2);
    localIntent.putExtra("notif_id", paramString3);
    return localIntent;
  }

  public static Intent getSquaresActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("destination", 8);
    return localIntent;
  }

  public static Intent getStreamActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("destination", 0);
    return localIntent;
  }

  public static Intent getStreamOneUpActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    Intent localIntent = new Intent(paramContext, StreamOneUpActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("activity_id", paramString);
    localIntent.putExtra("refresh", false);
    return localIntent;
  }

  public static Intent getSuggestedPeopleActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("destination", 5);
    localIntent.putExtra("people_view_type", 0);
    return localIntent;
  }

  public static Intent getTargetIntent(Context paramContext, Intent paramIntent, String paramString)
  {
    Intent localIntent = new Intent(paramIntent);
    localIntent.putExtra("calling_package", paramString);
    localIntent.putExtra("intent", paramIntent);
    String str = localIntent.getAction();
    if (TextUtils.isEmpty(str))
      localIntent = null;
    while (true)
    {
      return localIntent;
      if (str.equals("com.google.android.apps.plus.action.PLUSONE"))
        localIntent.setComponent(new ComponentName(paramContext, PlusOneActivity.class));
      else if ((str.equals("com.google.android.apps.plus.SHARE_GOOGLE")) || (str.equals("android.intent.action.SEND")) || (str.equals("android.intent.action.SEND_MULTIPLE")) || (str.equals("com.google.android.apps.plus.GOOGLE_BIRTHDAY_POST")) || (str.equals("com.google.android.apps.plus.GOOGLE_PLUS_SHARE")))
        localIntent.setComponent(new ComponentName(paramContext, ShareActivity.class));
      else
        localIntent = null;
    }
  }

  public static Intent getVideoViewActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString, long paramLong, byte[] paramArrayOfByte)
  {
    Intent localIntent = new Intent(paramContext, VideoViewActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("owner_id", paramString);
    localIntent.putExtra("photo_id", paramLong);
    localIntent.putExtra("data", paramArrayOfByte);
    return localIntent;
  }

  public static PendingIntent getViewEventActivityNotificationIntent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    return PendingIntent.getActivity(paramContext, 0, getHostedEventIntent(paramContext, paramEsAccount, paramString1, paramString2, null), 134217728);
  }

  public static Intent getViewPanoramaActivityIntent(Context paramContext, EsAccount paramEsAccount, MediaRef paramMediaRef)
  {
    Intent localIntent = new Intent(paramContext, PanoramaViewerActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("mediaref", paramMediaRef);
    return localIntent;
  }

  public static Intent getWhatsHotCircleActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, HomeActivity.class);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.putExtra("account", paramEsAccount);
    localIntent.putExtra("destination", 0);
    localIntent.putExtra("circle_id", "v.whatshot");
    return localIntent;
  }

  public static Intent getWidgetCameraLauncherActivityIntent(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = new Intent(paramContext, EsWidgetCameraLauncherActivity.class);
    localIntent.putExtra("account", null);
    return localIntent;
  }

  public static boolean isCameraIntentRegistered(Context paramContext)
  {
    Intent localIntent = getCameraIntentPhoto$3a35108a("camera-photo.jpg");
    if (paramContext.getPackageManager().queryIntentActivities(localIntent, 65536).size() > 0);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private static boolean isExternalUrl(String paramString)
  {
    boolean bool1 = TextUtils.isEmpty(paramString);
    boolean bool2 = false;
    if (bool1);
    while (true)
    {
      return bool2;
      String str = paramString.toLowerCase();
      Iterator localIterator = INTERNAL_URLS.iterator();
      while (true)
        if (localIterator.hasNext())
          if (str.contains((String)localIterator.next()))
          {
            bool2 = false;
            break;
          }
      bool2 = true;
    }
  }

  public static boolean isHashtagUrl(String paramString)
  {
    return paramString.startsWith("https://plus.google.com/s/%23");
  }

  public static boolean isInitialOobIntent$755b117a(Intent paramIntent)
  {
    OobIntents localOobIntents = (OobIntents)paramIntent.getParcelableExtra("oob_intents");
    if (localOobIntents == null);
    for (boolean bool = true; ; bool = localOobIntents.isInitialIntent())
      return bool;
  }

  public static boolean isLastOobIntent(Context paramContext, EsAccount paramEsAccount, AccountSettingsData paramAccountSettingsData, Intent paramIntent)
  {
    OobIntents localOobIntents = (OobIntents)paramIntent.getParcelableExtra("oob_intents");
    if (localOobIntents == null);
    for (boolean bool = true; ; bool = localOobIntents.isLastIntent(paramContext, paramEsAccount, paramAccountSettingsData))
      return bool;
  }

  public static boolean isProfileUrl(String paramString)
  {
    if ((paramString.startsWith("#~loop:svt=person&")) || (paramString.matches("^https://plus\\.google\\.com/[0-9]*$")));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static String makeProfileUrl(String paramString)
  {
    return "#~loop:svt=person&view=stream&pid=" + paramString;
  }

  public static PhotosIntentBuilder newAlbumsActivityIntentBuilder(Context paramContext)
  {
    return new PhotosIntentBuilder(paramContext, HostAlbumsActivity.class, (byte)0);
  }

  public static PhotoViewIntentBuilder newPhotoComposeActivityIntentBuilder(Context paramContext)
  {
    return new PhotoViewIntentBuilder(paramContext, PhotoComposeActivity.class, (byte)0);
  }

  public static PhotoViewIntentBuilder newPhotoComposeFragmentIntentBuilder(Context paramContext)
  {
    return new PhotoViewIntentBuilder(paramContext, PhotoComposeFragment.class, (byte)0);
  }

  public static PhotoViewIntentBuilder newPhotoViewActivityIntentBuilder(Context paramContext)
  {
    return new PhotoViewIntentBuilder(paramContext, PhotoOneUpActivity.class, (byte)0);
  }

  public static PhotoViewIntentBuilder newPhotoViewFragmentIntentBuilder(Context paramContext)
  {
    return new PhotoViewIntentBuilder(paramContext, PhotoOneUpFragment.class, (byte)0);
  }

  public static PhotosIntentBuilder newPhotosActivityIntentBuilder(Context paramContext)
  {
    return new PhotosIntentBuilder(paramContext, HostPhotosActivity.class, (byte)0);
  }

  public static PhotosIntentBuilder newPhotosSelectionActivityIntentBuilder(Context paramContext)
  {
    return new PhotosIntentBuilder(paramContext, PhotosSelectionActivity.class, (byte)0);
  }

  public static void viewContent(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    viewContent(paramContext, paramEsAccount, paramString, null);
  }

  public static void viewContent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    String str11;
    if (isProfileUrl(paramString1))
    {
      str11 = getPersonIdFromProfileUrl(paramString1);
      if (str11 == null);
    }
    try
    {
      paramContext.startActivity(getProfileActivityByGaiaIdIntent(paramContext, paramEsAccount, str11, null));
      while (true)
      {
        label30: return;
        if (paramString1.startsWith("#~loop:svt=album&"))
        {
          String str3 = getParameter(paramString1, "aid=");
          String str4 = getParameter(paramString1, "eid=");
          String str5 = getParameter(paramString1, "sid=");
          String str6 = getParameter(paramString1, "oid=");
          String str7 = getParameter(paramString1, "aname=");
          if (str4 != null)
            paramContext.startActivity(getHostedEventIntent(paramContext, paramEsAccount, Uri.decode(str4), str6, null));
          else if ((str5 != null) && (str6 != null))
            try
            {
              String str9 = Uri.decode(str5);
              String str10 = Uri.decode(str7);
              paramContext.startActivity(newPhotosActivityIntentBuilder(paramContext).setAccount(paramEsAccount).setAlbumName(str10).setStreamId(str9).setGaiaId(str6).build());
            }
            catch (NumberFormatException localNumberFormatException3)
            {
            }
          else if ((str3 != null) && (str6 != null))
            try
            {
              String str8 = Uri.decode(str7);
              paramContext.startActivity(newPhotosActivityIntentBuilder(paramContext).setAccount(paramEsAccount).setAlbumName(str8).setAlbumId(str3).setGaiaId(str6).build());
            }
            catch (NumberFormatException localNumberFormatException2)
            {
            }
        }
        else
        {
          List localList = Uri.parse(paramString1).getPathSegments();
          if ((localList.size() == 4) && ("photos".equals(localList.get(0))) && ("albums".equals(localList.get(2))))
            try
            {
              String str1 = (String)localList.get(1);
              String str2 = (String)localList.get(3);
              paramContext.startActivity(newPhotosActivityIntentBuilder(paramContext).setAccount(paramEsAccount).setAlbumId(str2).setGaiaId(str1).build());
            }
            catch (NumberFormatException localNumberFormatException1)
            {
            }
          else
            viewUrl(paramContext, paramEsAccount, paramString1, paramString2);
        }
      }
    }
    catch (NumberFormatException localNumberFormatException4)
    {
      break label30;
    }
  }

  public static void viewUrl(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    viewUrl(paramContext, paramEsAccount, paramString, null);
  }

  private static void viewUrl(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    try
    {
      Intent localIntent = new Intent("android.intent.action.VIEW");
      localIntent.setData(Uri.parse(paramString1));
      localIntent.putExtra("com.android.browser.application_id", paramContext.getPackageName());
      localIntent.addFlags(524288);
      Bundle localBundle1 = new Bundle();
      localBundle1.putString("Referer", "http://plus.url.google.com/mobileapp");
      localIntent.putExtra("com.android.browser.headers", localBundle1);
      paramContext.startActivity(localIntent);
      Bundle localBundle2;
      if (paramEsAccount != null)
      {
        if (!isExternalUrl(paramString1))
          break label140;
        localBundle2 = new Bundle();
        localBundle2.putString("extra_external_url", paramString1);
        if (!TextUtils.isEmpty(paramString2))
          localBundle2.putString("extra_creation_source_id", paramString2);
      }
      while (true)
      {
        EsAnalytics.recordActionEvent(paramContext, paramEsAccount, OzActions.OPEN_URL, OzViews.getViewForLogging(paramContext), localBundle2);
        return;
        label140: localBundle2 = null;
      }
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      while (true)
        Log.w("Intents", "Unable to start activity for URL: " + paramString1);
    }
  }

  public static final class PhotoViewIntentBuilder
  {
    private EsAccount mAccount;
    private String mAlbumId;
    private String mAlbumName;
    private Boolean mAllowPlusOne;
    private String mAuthkey;
    private Boolean mDisableComments;
    private String mDisplayName;
    private String mEventId;
    private Long mForceLoadId;
    private String mGaiaId;
    private final Intent mIntent;
    private Boolean mIsPlaceholder;
    private MediaRef[] mMediaRefs;
    private MediaRef.MediaType mMediaType;
    private String mNotificationId;
    private Integer mPageHint;
    private Long mPhotoId;
    private Integer mPhotoIndex;
    private String mPhotoOfUserId;
    private Boolean mPhotoOnly;
    private MediaRef mPhotoRef;
    private String mPhotoUrl;
    private String mRefreshAlbumId;
    private String mStreamId;

    private PhotoViewIntentBuilder(Context paramContext, Class<?> paramClass)
    {
      this.mIntent = new Intent(paramContext, paramClass);
    }

    public final Intent build()
    {
      if (this.mAccount == null)
        throw new IllegalArgumentException("Account must be set");
      this.mIntent.setAction("android.intent.action.VIEW");
      this.mIntent.putExtra("account", this.mAccount);
      if (this.mAlbumId != null)
        this.mIntent.putExtra("album_id", this.mAlbumId);
      if (this.mAlbumName != null)
        this.mIntent.putExtra("album_name", this.mAlbumName);
      if (this.mAllowPlusOne != null)
        this.mIntent.putExtra("allow_plusone", this.mAllowPlusOne.booleanValue());
      if (this.mDisplayName != null)
        this.mIntent.putExtra("display_name", this.mDisplayName);
      if (this.mEventId != null)
        this.mIntent.putExtra("event_id", this.mEventId);
      if (this.mForceLoadId != null)
        this.mIntent.putExtra("force_load_id", this.mForceLoadId.longValue());
      if (this.mRefreshAlbumId != null)
        this.mIntent.putExtra("refresh_album_id", this.mRefreshAlbumId);
      if (this.mMediaRefs != null)
        this.mIntent.putExtra("mediarefs", this.mMediaRefs);
      if (this.mNotificationId != null)
        this.mIntent.putExtra("notif_id", this.mNotificationId);
      if (this.mGaiaId != null)
        this.mIntent.putExtra("owner_id", this.mGaiaId);
      if (this.mPageHint != null)
      {
        this.mIntent.putExtra("page_hint", this.mPageHint.intValue());
        if (this.mPhotoId != null)
          this.mIntent.putExtra("photo_id", this.mPhotoId.longValue());
        if (this.mPhotoIndex != null)
          this.mIntent.putExtra("photo_index", this.mPhotoIndex.intValue());
        if (this.mPhotoOfUserId != null)
          this.mIntent.putExtra("photos_of_user_id", this.mPhotoOfUserId);
        if (((this.mPhotoOnly != null) && (this.mPhotoOnly.booleanValue())) || (this.mMediaRefs != null))
          this.mIntent.putExtra("show_photo_only", true);
        if (this.mPhotoRef == null)
          break label536;
        this.mIntent.putExtra("photo_ref", this.mPhotoRef);
      }
      label536: 
      while (this.mPhotoId == null)
      {
        if (this.mPhotoUrl != null)
          this.mIntent.putExtra("photo_url", this.mPhotoUrl);
        if (this.mStreamId != null)
          this.mIntent.putExtra("stream_id", this.mStreamId);
        if (this.mAuthkey != null)
          this.mIntent.putExtra("auth_key", this.mAuthkey);
        if (this.mDisableComments != null)
          this.mIntent.putExtra("disable_photo_comments", this.mDisableComments.booleanValue());
        if (this.mIsPlaceholder != null)
          this.mIntent.putExtra("is_placeholder", this.mIsPlaceholder.booleanValue());
        return this.mIntent;
        this.mIntent.putExtra("page_hint", -1);
        break;
      }
      Uri localUri1;
      label559: Uri localUri2;
      label571: String str1;
      long l;
      String str2;
      if (this.mPhotoUrl != null)
      {
        localUri1 = Uri.parse(this.mPhotoUrl);
        if (!MediaStoreUtils.isMediaStoreUri(localUri1))
          break label645;
        localUri2 = localUri1;
        str1 = this.mGaiaId;
        l = this.mPhotoId.longValue();
        str2 = this.mPhotoUrl;
        if (this.mMediaType == null)
          break label651;
      }
      label645: label651: for (MediaRef.MediaType localMediaType = this.mMediaType; ; localMediaType = MediaRef.MediaType.IMAGE)
      {
        MediaRef localMediaRef = new MediaRef(str1, l, str2, localUri2, localMediaType);
        this.mIntent.putExtra("photo_ref", localMediaRef);
        break;
        localUri1 = null;
        break label559;
        localUri2 = null;
        break label571;
      }
    }

    public final PhotoViewIntentBuilder setAccount(EsAccount paramEsAccount)
    {
      this.mAccount = paramEsAccount;
      return this;
    }

    public final PhotoViewIntentBuilder setAlbumId(String paramString)
    {
      this.mAlbumId = paramString;
      return this;
    }

    public final PhotoViewIntentBuilder setAlbumName(String paramString)
    {
      this.mAlbumName = paramString;
      return this;
    }

    public final PhotoViewIntentBuilder setAllowPlusOne(Boolean paramBoolean)
    {
      this.mAllowPlusOne = paramBoolean;
      return this;
    }

    public final PhotoViewIntentBuilder setAuthkey(String paramString)
    {
      this.mAuthkey = paramString;
      return this;
    }

    public final PhotoViewIntentBuilder setDisableComments(Boolean paramBoolean)
    {
      this.mDisableComments = paramBoolean;
      return this;
    }

    public final PhotoViewIntentBuilder setDisplayName(String paramString)
    {
      this.mDisplayName = paramString;
      return this;
    }

    public final PhotoViewIntentBuilder setEventId(String paramString)
    {
      this.mEventId = paramString;
      return this;
    }

    public final PhotoViewIntentBuilder setForceLoadId(Long paramLong)
    {
      this.mForceLoadId = paramLong;
      return this;
    }

    public final PhotoViewIntentBuilder setGaiaId(String paramString)
    {
      this.mGaiaId = paramString;
      return this;
    }

    public final PhotoViewIntentBuilder setIsPlaceholder(Boolean paramBoolean)
    {
      this.mIsPlaceholder = paramBoolean;
      return this;
    }

    public final PhotoViewIntentBuilder setMediaRefs(MediaRef[] paramArrayOfMediaRef)
    {
      this.mMediaRefs = paramArrayOfMediaRef;
      return this;
    }

    public final PhotoViewIntentBuilder setMediaType(MediaRef.MediaType paramMediaType)
    {
      this.mMediaType = paramMediaType;
      return this;
    }

    public final PhotoViewIntentBuilder setNotificationId(String paramString)
    {
      this.mNotificationId = paramString;
      return this;
    }

    public final PhotoViewIntentBuilder setPageHint(Integer paramInteger)
    {
      this.mPageHint = paramInteger;
      return this;
    }

    public final PhotoViewIntentBuilder setPhotoId(Long paramLong)
    {
      this.mPhotoId = paramLong;
      return this;
    }

    public final PhotoViewIntentBuilder setPhotoIndex(Integer paramInteger)
    {
      this.mPhotoIndex = paramInteger;
      return this;
    }

    public final PhotoViewIntentBuilder setPhotoOfUserId(String paramString)
    {
      this.mPhotoOfUserId = paramString;
      return this;
    }

    public final PhotoViewIntentBuilder setPhotoOnly(Boolean paramBoolean)
    {
      this.mPhotoOnly = paramBoolean;
      return this;
    }

    public final PhotoViewIntentBuilder setPhotoRef(MediaRef paramMediaRef)
    {
      this.mPhotoRef = paramMediaRef;
      return this;
    }

    public final PhotoViewIntentBuilder setPhotoUrl(String paramString)
    {
      this.mPhotoUrl = paramString;
      return this;
    }

    public final PhotoViewIntentBuilder setRefreshAlbumId(String paramString)
    {
      this.mRefreshAlbumId = paramString;
      return this;
    }

    public final PhotoViewIntentBuilder setStreamId(String paramString)
    {
      this.mStreamId = paramString;
      return this;
    }
  }

  public static final class PhotosIntentBuilder
  {
    private EsAccount mAccount;
    private String mAlbumId;
    private String mAlbumName;
    private String mAlbumType;
    private AudienceData mAudience;
    private String mAuthkey;
    private Integer mCropMode;
    private String mGaiaId;
    private final Intent mIntent;
    private Map<MediaRef, List<PhotoTaggeeData.PhotoTaggee>> mMediaRefUserMap;
    private MediaRef[] mMediaRefs;
    private String mNotificationId;
    private String mPersonId;
    private String mPhotoOfUserId;
    private ArrayList<MediaRef> mPhotoPickerMediaRefs;
    private Integer mPhotoPickerMode;
    private Integer mPhotoPickerTitleResourceId;
    private Boolean mPhotosHome;
    private Boolean mShowCameraAlbum;
    private String mStreamId;
    private Boolean mTakePhoto;
    private Boolean mTakeVideo;

    private PhotosIntentBuilder(Context paramContext, Class<?> paramClass)
    {
      this.mIntent = new Intent(paramContext, paramClass);
    }

    public final Intent build()
    {
      if (this.mAccount == null)
        throw new IllegalArgumentException("Account must be set");
      this.mIntent.setAction("android.intent.action.VIEW");
      Bundle localBundle = new Bundle();
      localBundle.putParcelable("account", this.mAccount);
      if (this.mAlbumId != null)
        localBundle.putString("album_id", this.mAlbumId);
      if (this.mAlbumName != null)
        localBundle.putString("album_name", this.mAlbumName);
      if (this.mAlbumType != null)
        localBundle.putString("album_type", this.mAlbumType);
      if (this.mMediaRefs != null)
        localBundle.putParcelableArray("mediarefs", this.mMediaRefs);
      if (this.mNotificationId != null)
        localBundle.putString("notif_id", this.mNotificationId);
      if (this.mGaiaId != null)
        localBundle.putString("owner_id", this.mGaiaId);
      localBundle.putInt("page_hint", -1);
      if (this.mPersonId != null)
        localBundle.putString("person_id", this.mPersonId);
      if (this.mPhotoOfUserId != null)
        localBundle.putString("photos_of_user_id", this.mPhotoOfUserId);
      if (this.mMediaRefs != null)
        localBundle.putBoolean("show_photo_only", true);
      if (this.mPhotosHome != null)
        localBundle.putBoolean("photos_home", this.mPhotosHome.booleanValue());
      if (this.mShowCameraAlbum != null)
        localBundle.putBoolean("photos_show_camera_album", this.mShowCameraAlbum.booleanValue());
      if (this.mStreamId != null)
        localBundle.putString("stream_id", this.mStreamId);
      if (this.mPhotoPickerMode != null)
        localBundle.putInt("photo_picker_mode", this.mPhotoPickerMode.intValue());
      if (this.mPhotoPickerTitleResourceId != null)
        localBundle.putInt("photo_picker_title", this.mPhotoPickerTitleResourceId.intValue());
      if (this.mPhotoPickerMediaRefs != null)
      {
        MediaRef[] arrayOfMediaRef = new MediaRef[this.mPhotoPickerMediaRefs.size()];
        this.mPhotoPickerMediaRefs.toArray(arrayOfMediaRef);
        localBundle.putParcelableArray("photo_picker_selected", arrayOfMediaRef);
      }
      if (this.mCropMode != null)
        localBundle.putInt("photo_picker_crop_mode", this.mCropMode.intValue());
      if (this.mAuthkey != null)
        localBundle.putString("auth_key", this.mAuthkey);
      if (PrimitiveUtils.safeBoolean(this.mTakePhoto))
        localBundle.putBoolean("take_photo", this.mTakePhoto.booleanValue());
      if (PrimitiveUtils.safeBoolean(this.mTakeVideo))
        localBundle.putBoolean("take_video", this.mTakeVideo.booleanValue());
      if (this.mMediaRefUserMap != null)
        localBundle.putSerializable("mediaref_user_map", (Serializable)this.mMediaRefUserMap);
      if (this.mAudience != null)
        localBundle.putParcelable("audience", this.mAudience);
      this.mIntent.putExtras(localBundle);
      return this.mIntent;
    }

    public final PhotosIntentBuilder setAccount(EsAccount paramEsAccount)
    {
      this.mAccount = paramEsAccount;
      return this;
    }

    public final PhotosIntentBuilder setAlbumId(String paramString)
    {
      this.mAlbumId = paramString;
      return this;
    }

    public final PhotosIntentBuilder setAlbumName(String paramString)
    {
      this.mAlbumName = paramString;
      return this;
    }

    public final PhotosIntentBuilder setAlbumType(String paramString)
    {
      this.mAlbumType = paramString;
      return this;
    }

    public final PhotosIntentBuilder setAudience(AudienceData paramAudienceData)
    {
      this.mAudience = paramAudienceData;
      return this;
    }

    public final PhotosIntentBuilder setAuthkey(String paramString)
    {
      this.mAuthkey = paramString;
      return this;
    }

    public final PhotosIntentBuilder setCropMode(Integer paramInteger)
    {
      this.mCropMode = paramInteger;
      return this;
    }

    public final PhotosIntentBuilder setGaiaId(String paramString)
    {
      this.mGaiaId = paramString;
      return this;
    }

    public final PhotosIntentBuilder setMediaRefUserMap(Map<MediaRef, List<PhotoTaggeeData.PhotoTaggee>> paramMap)
    {
      if ((paramMap instanceof Serializable))
      {
        this.mMediaRefUserMap = paramMap;
        return this;
      }
      throw new IllegalArgumentException("mediaRefUserMap must be serializable!");
    }

    public final PhotosIntentBuilder setMediaRefs(MediaRef[] paramArrayOfMediaRef)
    {
      this.mMediaRefs = paramArrayOfMediaRef;
      return this;
    }

    public final PhotosIntentBuilder setNotificationId(String paramString)
    {
      this.mNotificationId = paramString;
      return this;
    }

    public final PhotosIntentBuilder setPersonId(String paramString)
    {
      this.mPersonId = paramString;
      return this;
    }

    public final PhotosIntentBuilder setPhotoOfUserId(String paramString)
    {
      this.mPhotoOfUserId = paramString;
      return this;
    }

    public final PhotosIntentBuilder setPhotoPickerInitiallySelected(ArrayList<MediaRef> paramArrayList)
    {
      this.mPhotoPickerMediaRefs = paramArrayList;
      return this;
    }

    public final PhotosIntentBuilder setPhotoPickerMode(Integer paramInteger)
    {
      this.mPhotoPickerMode = paramInteger;
      return this;
    }

    public final PhotosIntentBuilder setPhotoPickerTitleResourceId(Integer paramInteger)
    {
      this.mPhotoPickerTitleResourceId = paramInteger;
      return this;
    }

    public final PhotosIntentBuilder setPhotosHome(Boolean paramBoolean)
    {
      this.mPhotosHome = paramBoolean;
      return this;
    }

    public final PhotosIntentBuilder setShowCameraAlbum(Boolean paramBoolean)
    {
      this.mShowCameraAlbum = paramBoolean;
      return this;
    }

    public final PhotosIntentBuilder setStreamId(String paramString)
    {
      this.mStreamId = paramString;
      return this;
    }

    public final PhotosIntentBuilder setTakePhoto(boolean paramBoolean)
    {
      this.mTakePhoto = Boolean.valueOf(true);
      return this;
    }

    public final PhotosIntentBuilder setTakeVideo(boolean paramBoolean)
    {
      this.mTakeVideo = Boolean.valueOf(true);
      return this;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.Intents
 * JD-Core Version:    0.6.2
 */