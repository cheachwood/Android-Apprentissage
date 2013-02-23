package com.google.android.apps.plus.phone;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.service.Hangout.LaunchSource;
import com.google.android.apps.plus.service.Hangout.RoomType;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.Property;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public abstract class EsUrlGatewayActivity extends EsFragmentActivity
{
  private static HashMap<String, HashSet<Uri>> sKnownUnsupportedUri = new HashMap();
  protected EsAccount mAccount;
  protected String mActivityId;
  protected String mAlbumId;
  protected String mAuthKey;
  protected String mDesktopActivityId;
  private String mEventCreatorId;
  private String mEventId;
  private String mEventInvitationToken;
  protected String mGaiaId;
  protected String mHangoutDomain;
  protected String mHangoutId;
  protected String mHangoutServiceId;
  protected String mName;
  protected long mPhotoId;
  protected String mProfileId;
  protected boolean mProfileIdValidated;
  protected int mRequestType = 0;
  private String mRsvpType;
  private String mSquareId;

  private static long parseLong(String paramString)
  {
    try
    {
      long l2 = Long.parseLong(paramString);
      l1 = l2;
      return l1;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      while (true)
        long l1 = 0L;
    }
  }

  private void parseUri(Uri paramUri)
  {
    List localList1;
    while (true)
    {
      localList1 = paramUri.getPathSegments();
      this.mAuthKey = paramUri.getQueryParameter("authkey");
      if ((localList1.size() < 2) || (!"_".equals(localList1.get(0))) || (!"notifications".equals(localList1.get(1))))
        break;
      List localList2 = paramUri.getPathSegments();
      if ((localList2.size() < 3) || ((!"emlink".equals(localList2.get(2))) && (!"ngemlink".equals(localList2.get(2)))))
        break label250;
      String str21 = paramUri.getQueryParameter("path");
      if (TextUtils.isEmpty(str21))
        break label250;
      if (!str21.startsWith("/"))
        str21 = "/" + str21;
      paramUri = Uri.parse("http://plus.google.com" + str21);
    }
    int i = localList1.size();
    if ((i >= 2) && ("u".equals(localList1.get(0))))
    {
      localList1 = localList1.subList(2, i);
      i -= 2;
    }
    int k;
    if ((i > 0) && ("photos".equals(localList1.get(0))))
    {
      k = localList1.size();
      if (k == 1)
        this.mRequestType = 3;
    }
    while (true)
    {
      label250: return;
      if (k == 2)
      {
        String str20 = (String)localList1.get(1);
        if (("fromphone".equals(str20)) || ("instantupload".equals(str20)))
          this.mRequestType = 11;
      }
      else if (k == 3)
      {
        String str18 = (String)localList1.get(1);
        String str19 = (String)localList1.get(2);
        if ("of".equals(str18))
        {
          this.mRequestType = 12;
          this.mProfileId = str19;
        }
        else if ("posts".equals(str19))
        {
          this.mRequestType = 13;
          this.mProfileId = str18;
        }
        else if ("albums".equals(str19))
        {
          this.mRequestType = 7;
          this.mProfileId = str18;
        }
      }
      else if (k == 4)
      {
        String str15 = (String)localList1.get(1);
        String str16 = (String)localList1.get(2);
        String str17 = (String)localList1.get(3);
        if (("albums".equals(str16)) || ("album".equals(str16)))
        {
          this.mProfileId = str15;
          if ("profile".equals(str17))
          {
            this.mRequestType = 16;
          }
          else if ("posts".equals(str17))
          {
            this.mRequestType = 13;
          }
          else
          {
            this.mAlbumId = str17;
            if (this.mAlbumId != null)
              this.mRequestType = 14;
          }
        }
      }
      else if (k == 5)
      {
        String str11 = (String)localList1.get(1);
        String str12 = (String)localList1.get(2);
        String str13 = (String)localList1.get(3);
        String str14 = (String)localList1.get(4);
        if ("albums".equals(str12))
        {
          this.mProfileId = str11;
          if ("profile".equals(str13))
          {
            this.mPhotoId = parseLong(str14);
            if (this.mPhotoId != 0L)
              this.mRequestType = 17;
          }
          else if ("posts".equals(str13))
          {
            this.mPhotoId = parseLong(str14);
            if (this.mPhotoId != 0L)
              this.mRequestType = 18;
          }
          else
          {
            this.mAlbumId = str13;
            this.mPhotoId = parseLong(str14);
            if ((this.mAlbumId != null) && (this.mPhotoId != 0L))
            {
              this.mRequestType = 15;
              continue;
              if ((i > 0) && ("hangouts".equals(localList1.get(0))))
              {
                if (localList1.size() >= 2)
                {
                  LinkedList localLinkedList = new LinkedList(localList1);
                  if ("hangouts".equals((String)localLinkedList.get(0)))
                  {
                    localLinkedList.remove(0);
                    if ("_".equals((String)localLinkedList.get(0)))
                      localLinkedList.remove(0);
                    int j = localLinkedList.size();
                    if (j == 1)
                    {
                      this.mHangoutDomain = null;
                      this.mHangoutId = ((String)localLinkedList.get(0));
                      this.mRequestType = 20;
                    }
                    else if (j == 2)
                    {
                      String str9 = (String)localLinkedList.get(0);
                      String str10 = (String)localLinkedList.get(1);
                      if (("google.com".equals(str9)) && (EsLog.ENABLE_DOGFOOD_FEATURES))
                      {
                        this.mHangoutDomain = "google.com";
                        this.mHangoutId = Uri.decode(str10);
                        this.mRequestType = 22;
                      }
                      else if ("lite".equals(str9))
                      {
                        this.mHangoutDomain = null;
                        this.mHangoutId = str10;
                        this.mRequestType = 23;
                      }
                      else
                      {
                        this.mHangoutDomain = null;
                        this.mHangoutServiceId = str9;
                        this.mHangoutId = str10;
                        this.mRequestType = 21;
                      }
                    }
                  }
                }
              }
              else if (i == 0)
              {
                this.mRequestType = 1;
              }
              else
              {
                String str1 = (String)localList1.get(0);
                if (!"settings".equals(str1))
                  if (i == 1)
                  {
                    if ("stream".equals(str1))
                    {
                      this.mRequestType = 2;
                    }
                    else if ("me".equals(str1))
                    {
                      this.mRequestType = 4;
                    }
                    else if ("circles".equals(str1))
                    {
                      this.mRequestType = 8;
                    }
                    else if (("hot".equals(str1)) || ("explore".equals(str1)))
                    {
                      this.mRequestType = 25;
                    }
                    else if ("events".equals(str1))
                    {
                      this.mRequestType = 28;
                    }
                    else if ("communities".equals(str1))
                    {
                      if (Property.ENABLE_SQUARES.getBoolean())
                        this.mRequestType = 29;
                    }
                    else if ("+".equals(str1))
                    {
                      this.mRequestType = 1;
                    }
                    else
                    {
                      this.mProfileId = str1;
                      this.mRequestType = 19;
                    }
                  }
                  else if (i == 2)
                  {
                    String str8 = (String)localList1.get(1);
                    if (("posts".equals(str8)) || ("stream".equals(str8)))
                    {
                      this.mRequestType = 5;
                      this.mProfileId = str1;
                    }
                    else if ("about".equals(str8))
                    {
                      this.mRequestType = 6;
                      this.mProfileId = str1;
                    }
                    else if ("photos".equals(str8))
                    {
                      this.mRequestType = 7;
                      this.mProfileId = str1;
                    }
                    else if (("circles".equals(str1)) && ("find".equals(str8)))
                    {
                      this.mRequestType = 26;
                    }
                    else if ("events".equals(str1))
                    {
                      processEventUri(str8, null, null, paramUri);
                    }
                    else if (("communities".equals(str1)) && (Property.ENABLE_SQUARES.getBoolean()))
                    {
                      this.mRequestType = 30;
                      this.mSquareId = str8;
                    }
                  }
                  else if (i == 3)
                  {
                    String str5 = (String)localList1.get(1);
                    String str6 = (String)localList1.get(2);
                    if ("posts".equals(str5))
                    {
                      this.mRequestType = 9;
                      this.mProfileId = str1;
                      this.mDesktopActivityId = str6;
                    }
                    else if ("digest".equals(str5))
                    {
                      this.mRequestType = 5;
                      this.mProfileId = str1;
                    }
                    else if (("notifications".equals(str1)) && ("all".equals(str5)))
                    {
                      String str7 = paramUri.getQueryParameter("mute");
                      if (!TextUtils.isEmpty(str7))
                      {
                        this.mRequestType = 24;
                        this.mActivityId = str7;
                      }
                    }
                    else if ("events".equals(str1))
                    {
                      processEventUri(str5, str6, null, paramUri);
                    }
                  }
                  else if (i == 5)
                  {
                    String str2 = (String)localList1.get(1);
                    String str3 = (String)localList1.get(2);
                    localList1.get(3);
                    String str4 = (String)localList1.get(4);
                    if (("events".equals(str1)) && ("rsvp".equals(str3)))
                      processEventUri(str2, null, str4, paramUri);
                  }
              }
            }
          }
        }
      }
    }
  }

  private void processEventUri(String paramString1, String paramString2, String paramString3, Uri paramUri)
  {
    this.mRequestType = 27;
    this.mEventCreatorId = paramString2;
    this.mEventId = paramString1;
    this.mRsvpType = paramString3;
    this.mEventInvitationToken = paramUri.getQueryParameter("gpinv");
  }

  private void redirect(Intent paramIntent)
  {
    paramIntent.addFlags(33619968);
    paramIntent.putExtra("from_url_gateway", true);
    startActivity(paramIntent);
    finish();
    overridePendingTransition(0, 0);
  }

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.UNKNOWN;
  }

  protected final boolean isReadyToRedirect()
  {
    boolean bool = true;
    switch (this.mRequestType)
    {
    case 1:
    case 2:
    case 3:
    case 4:
    case 8:
    case 11:
    case 20:
    case 21:
    case 22:
    case 23:
    case 25:
    case 26:
    case 28:
    case 29:
    case 30:
    default:
    case 9:
    case 24:
    case 5:
    case 6:
    case 7:
    case 10:
    case 12:
    case 13:
    case 14:
    case 15:
    case 16:
    case 17:
    case 18:
    case 19:
    case 27:
    }
    while (true)
    {
      return bool;
      if (this.mActivityId == null)
      {
        bool = false;
        continue;
        if ((this.mGaiaId == null) || (!this.mProfileIdValidated))
        {
          bool = false;
          continue;
          if (this.mEventId == null)
            bool = false;
        }
      }
    }
  }

  protected final void launchBrowser(Uri paramUri)
  {
    HashSet localHashSet = (HashSet)sKnownUnsupportedUri.get(this.mAccount.getName());
    if (localHashSet == null)
    {
      localHashSet = new HashSet();
      sKnownUnsupportedUri.put(this.mAccount.getName(), localHashSet);
    }
    localHashSet.add(paramUri);
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.addFlags(524288);
    localIntent.setData(paramUri);
    List localList = getPackageManager().queryIntentActivities(localIntent, 65536);
    if (localList != null);
    for (int i = 0; ; i++)
      if (i < localList.size())
      {
        ActivityInfo localActivityInfo = ((ResolveInfo)localList.get(i)).activityInfo;
        if ((localActivityInfo != null) && (!getPackageName().equals(localActivityInfo.packageName)))
          localIntent.setComponent(new ComponentName(localActivityInfo.packageName, localActivityInfo.name));
      }
      else
      {
        redirect(localIntent);
        return;
      }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = getIntent();
    if (paramBundle != null)
    {
      this.mRequestType = paramBundle.getInt("request_type");
      this.mProfileId = paramBundle.getString("profile_id");
      this.mProfileIdValidated = paramBundle.getBoolean("profile_id_validated");
      this.mName = paramBundle.getString("name");
      this.mActivityId = paramBundle.getString("activity_id");
      this.mDesktopActivityId = paramBundle.getString("activity_id");
      this.mAlbumId = paramBundle.getString("album_id");
      this.mPhotoId = paramBundle.getLong("photo_id");
      this.mHangoutId = paramBundle.getString("hangout_id");
      this.mHangoutDomain = paramBundle.getString("hangout_domain");
      this.mHangoutServiceId = paramBundle.getString("service-id");
      this.mEventId = paramBundle.getString("event_id");
      this.mEventCreatorId = paramBundle.getString("event_creator_id");
      this.mEventInvitationToken = paramBundle.getString("event_invitation_token");
      this.mSquareId = paramBundle.getString("square_id");
    }
    while (true)
    {
      this.mAccount = EsAccountsData.getActiveAccount(this);
      if (this.mAccount == null)
      {
        localIntent.setComponent(new ComponentName(this, UrlGatewayLoaderActivity.class));
        startActivity(Intents.getAccountsActivityIntent(this, localIntent));
        finish();
      }
      return;
      if (localIntent.hasExtra("customAppUri"));
      for (Uri localUri = Uri.parse(localIntent.getStringExtra("customAppUri")); ; localUri = localIntent.getData())
      {
        if (localUri != null)
          break label264;
        finish();
        break;
      }
      label264: parseUri(localUri);
    }
  }

  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("request_type", this.mRequestType);
    paramBundle.putString("profile_id", this.mProfileId);
    paramBundle.putBoolean("profile_id_validated", this.mProfileIdValidated);
    paramBundle.putString("name", this.mName);
    paramBundle.putString("activity_id", this.mActivityId);
    paramBundle.putString("activity_id", this.mDesktopActivityId);
    paramBundle.putString("album_id", this.mAlbumId);
    paramBundle.putLong("photo_id", this.mPhotoId);
    paramBundle.putString("hangout_id", this.mHangoutId);
    paramBundle.putString("hangout_domain", this.mHangoutDomain);
    paramBundle.putString("service-id", this.mHangoutServiceId);
    paramBundle.putString("event_id", this.mEventId);
    paramBundle.putString("event_creator_id", this.mEventCreatorId);
    paramBundle.putString("event_invitation_token", this.mEventInvitationToken);
    paramBundle.putString("square_id", this.mSquareId);
  }

  protected final void redirect()
  {
    switch (this.mRequestType)
    {
    case 10:
    default:
      finish();
    case 1:
    case 8:
    case 26:
    case 2:
    case 25:
    case 4:
    case 3:
    case 9:
    case 5:
    case 6:
    case 19:
    case 7:
    case 11:
    case 12:
    case 13:
    case 14:
    case 16:
    case 15:
    case 17:
    case 18:
    case 20:
    case 23:
    case 21:
    case 22:
    case 24:
    case 28:
    case 27:
    case 29:
    case 30:
    }
    while (true)
    {
      return;
      redirect(Intents.getStreamActivityIntent(this, this.mAccount));
      continue;
      redirect(Intents.getCirclesActivityIntent(this, this.mAccount));
      continue;
      redirect(Intents.getSuggestedPeopleActivityIntent(this, this.mAccount));
      continue;
      redirect(Intents.getStreamActivityIntent(this, this.mAccount));
      continue;
      redirect(Intents.getWhatsHotCircleActivityIntent(this, this.mAccount));
      continue;
      redirect(Intents.getHostedProfileIntent(this, this.mAccount, this.mAccount.getPersonId(), null, 0));
      continue;
      redirect(Intents.getHostedProfileIntent(this, this.mAccount, this.mAccount.getPersonId(), null, 1));
      continue;
      redirect(Intents.getPostCommentsActivityIntent(this, this.mAccount, this.mActivityId));
      continue;
      redirect(Intents.getProfileActivityIntent(this, this.mAccount, "g:" + this.mGaiaId, null, 0));
      continue;
      redirect(Intents.getProfileActivityIntent(this, this.mAccount, "g:" + this.mGaiaId, null, 1));
      continue;
      redirect(Intents.newPhotosActivityIntentBuilder(this).setAccount(this.mAccount).setGaiaId(this.mAccount.getGaiaId()).setAlbumName(getString(R.string.photos_home_instant_upload_label)).setStreamId("camerasync").build());
      continue;
      Intents.PhotosIntentBuilder localPhotosIntentBuilder = Intents.newPhotosActivityIntentBuilder(this).setAccount(this.mAccount).setPhotoOfUserId(this.mGaiaId);
      int i = R.string.photos_of_user_label;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mName;
      redirect(localPhotosIntentBuilder.setAlbumName(getString(i, arrayOfObject)).setAuthkey(this.mAuthKey).build());
      continue;
      redirect(Intents.newPhotosActivityIntentBuilder(this).setAccount(this.mAccount).setGaiaId(this.mGaiaId).setAlbumName(getString(R.string.photo_view_default_title)).setStreamId("posts").setAuthkey(this.mAuthKey).build());
      continue;
      redirect(Intents.newPhotosActivityIntentBuilder(this).setAccount(this.mAccount).setGaiaId(this.mGaiaId).setAlbumId(this.mAlbumId).setAuthkey(this.mAuthKey).build());
      continue;
      redirect(Intents.newPhotosActivityIntentBuilder(this).setAccount(this.mAccount).setGaiaId(this.mGaiaId).setAlbumName(this.mName).setStreamId("profile").setAuthkey(this.mAuthKey).build());
      continue;
      MediaRef localMediaRef3 = new MediaRef(this.mGaiaId, this.mPhotoId, null, null, MediaRef.MediaType.IMAGE);
      redirect(Intents.newPhotoViewActivityIntentBuilder(this).setAccount(this.mAccount).setGaiaId(this.mGaiaId).setAlbumId(this.mAlbumId).setPhotoRef(localMediaRef3).setAuthkey(this.mAuthKey).build());
      continue;
      MediaRef localMediaRef2 = new MediaRef(this.mGaiaId, this.mPhotoId, null, null, MediaRef.MediaType.IMAGE);
      redirect(Intents.newPhotoViewActivityIntentBuilder(this).setAccount(this.mAccount).setGaiaId(this.mGaiaId).setAlbumName(this.mName).setStreamId("profile").setPhotoRef(localMediaRef2).setAuthkey(this.mAuthKey).build());
      continue;
      MediaRef localMediaRef1 = new MediaRef(this.mGaiaId, this.mPhotoId, null, null, MediaRef.MediaType.IMAGE);
      redirect(Intents.newPhotoViewActivityIntentBuilder(this).setAccount(this.mAccount).setGaiaId(this.mGaiaId).setStreamId("posts").setPhotoRef(localMediaRef1).setAuthkey(this.mAuthKey).build());
      continue;
      redirect(Intents.getHangoutActivityIntent(this, this.mAccount, Hangout.RoomType.CONSUMER, this.mHangoutDomain, null, this.mHangoutId, null, Hangout.LaunchSource.Url, false, false, null));
      continue;
      redirect(Intents.getHangoutActivityIntent(this, this.mAccount, Hangout.RoomType.CONSUMER, this.mHangoutDomain, null, this.mHangoutId, null, Hangout.LaunchSource.Url, false, true, null));
      continue;
      redirect(Intents.getHangoutActivityIntent(this, this.mAccount, Hangout.RoomType.EXTERNAL, this.mHangoutDomain, this.mHangoutServiceId, this.mHangoutId, null, Hangout.LaunchSource.Url, false, false, null));
      continue;
      redirect(Intents.getHangoutActivityIntent(this, this.mAccount, Hangout.RoomType.WITH_EXTRAS, this.mHangoutDomain, null, this.mHangoutId, null, Hangout.LaunchSource.Url, false, false, null));
      continue;
      redirect(Intents.getMuteActivityIntent(this, this.mAccount, this.mActivityId));
      continue;
      redirect(Intents.getEventsActivityIntent(this, this.mAccount));
      continue;
      redirect(Intents.getHostedEventIntent(this, this.mAccount, this.mEventId, this.mEventCreatorId, this.mEventInvitationToken, this.mRsvpType, this.mAuthKey));
      continue;
      redirect(Intents.getSquaresActivityIntent(this, this.mAccount));
      continue;
      redirect(Intents.getSquareStreamActivityIntent(this, this.mAccount, this.mSquareId, null, null));
    }
  }

  protected final void redirectToBrowser()
  {
    Uri localUri = getIntent().getData();
    if (("http".equals(localUri.getScheme())) || ("content".equals(localUri.getScheme())))
      localUri = localUri.buildUpon().scheme("https").build();
    HashSet localHashSet = (HashSet)sKnownUnsupportedUri.get(this.mAccount.getName());
    if ((localHashSet != null) && (localHashSet.contains(localUri)))
      launchBrowser(localUri);
    while (true)
    {
      return;
      UnrecognizedLinkDialog localUnrecognizedLinkDialog = new UnrecognizedLinkDialog();
      Bundle localBundle = new Bundle();
      localBundle.putParcelable("url", localUri);
      localUnrecognizedLinkDialog.setArguments(localBundle);
      localUnrecognizedLinkDialog.show(getSupportFragmentManager(), "unsupported");
    }
  }

  public static class UnrecognizedLinkDialog extends DialogFragment
    implements DialogInterface.OnClickListener
  {
    public void onCancel(DialogInterface paramDialogInterface)
    {
      FragmentActivity localFragmentActivity = getActivity();
      if (localFragmentActivity != null)
        localFragmentActivity.finish();
    }

    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      switch (paramInt)
      {
      default:
      case -1:
      case -2:
      }
      while (true)
      {
        return;
        Uri localUri = (Uri)getArguments().getParcelable("url");
        ((EsUrlGatewayActivity)getActivity()).launchBrowser(localUri);
        continue;
        paramDialogInterface.dismiss();
        getActivity().finish();
      }
    }

    public final Dialog onCreateDialog(Bundle paramBundle)
    {
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.Theme_EmeraldSea_Dark_Dialog));
      localBuilder.setTitle(R.string.unsupported_link_dialog_title);
      localBuilder.setMessage(R.string.unsupported_link_dialog_message);
      localBuilder.setPositiveButton(17039370, this);
      localBuilder.setNegativeButton(17039360, this);
      localBuilder.setCancelable(true);
      return localBuilder.create();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EsUrlGatewayActivity
 * JD-Core Version:    0.6.2
 */