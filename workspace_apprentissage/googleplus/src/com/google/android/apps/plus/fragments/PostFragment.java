package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.google.android.apps.plus.R.anim;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.integer;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.AnalyticsInfo;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.InstrumentedActivity;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.ApiUtils;
import com.google.android.apps.plus.api.BirthdayData;
import com.google.android.apps.plus.api.CallToActionData;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.DbEmbedDeepLink;
import com.google.android.apps.plus.content.DbEmbedEmotishare;
import com.google.android.apps.plus.content.DbEmbedMedia;
import com.google.android.apps.plus.content.DbEmbedSkyjam;
import com.google.android.apps.plus.content.DbEmotishareMetadata;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsApiProvider;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.PreviewRequestData;
import com.google.android.apps.plus.content.SquareTargetData;
import com.google.android.apps.plus.external.PlatformContractUtils;
import com.google.android.apps.plus.network.ApiaryActivity;
import com.google.android.apps.plus.network.ApiaryActivityFactory;
import com.google.android.apps.plus.network.ApiaryApiInfo;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.Intents.PhotoViewIntentBuilder;
import com.google.android.apps.plus.phone.LocationController;
import com.google.android.apps.plus.phone.PostActivity;
import com.google.android.apps.plus.phone.ShareActivity;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.ImageUtils.InsertCameraPhotoDialogDisplayer;
import com.google.android.apps.plus.util.MentionTokenizer;
import com.google.android.apps.plus.util.PeopleUtils;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.android.apps.plus.util.Property;
import com.google.android.apps.plus.util.ResourceRedirector;
import com.google.android.apps.plus.util.SoftInput;
import com.google.android.apps.plus.util.StringUtils;
import com.google.android.apps.plus.views.AlbumColumnGridItemView;
import com.google.android.apps.plus.views.AudienceView;
import com.google.android.apps.plus.views.EmotiShareView;
import com.google.android.apps.plus.views.ImageResourceView;
import com.google.android.apps.plus.views.LinksCardView;
import com.google.android.apps.plus.views.MentionMultiAutoCompleteTextView;
import com.google.android.apps.plus.views.OneUpLinkView;
import com.google.android.apps.plus.views.OneUpLinkView.BackgroundViewLoadedListener;
import com.google.android.apps.plus.views.PostAclButtonView;
import com.google.android.apps.plus.views.StreamOneUpSkyjamView;
import com.google.android.apps.plus.views.TextOnlyAudienceView;
import com.google.android.apps.plus.views.TextOnlyAudienceView.ChevronDirection;
import com.google.api.services.plusi.model.AppInvite;
import com.google.api.services.plusi.model.DeepLink;
import com.google.api.services.plusi.model.DeepLinkData;
import com.google.api.services.plusi.model.EmbedClientItem;
import com.google.api.services.plusi.model.PlayMusicAlbum;
import com.google.api.services.plusi.model.PlayMusicTrack;
import com.google.api.services.plusi.model.SharingRoster;
import com.google.api.services.plusi.model.SharingTargetId;
import com.google.api.services.plusi.model.SharingTargetIdJson;
import com.google.api.services.plusi.model.Thing;
import com.google.api.services.plusi.model.VideoObject;
import com.google.api.services.plusi.model.WebPage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class PostFragment extends Fragment
  implements AlertFragmentDialog.AlertDialogListener
{
  private EsAccount mAccount;
  private View mAclDropDown;
  private String mActivityId;
  private ApiaryApiInfo mApiaryApiInfo;
  private ArrayList<MediaRef> mAttachmentRefs;
  private ArrayList<MediaRef> mAttachments;
  private AudienceView mAudienceView;
  private CallToActionData mCallToAction;
  private MentionMultiAutoCompleteTextView mCommentsView;
  private String mContentDeepLinkId;
  private Bundle mContentDeepLinkMetadata;
  private PostAclButtonView mCreateAclButton;
  private PostAclButtonView mDefaultAclButton;
  private AudienceData mDefaultAudience;
  private PostAclButtonView mDomainAclButton;
  private CircleData mDomainCircle;
  private DbEmotishareMetadata mEmotiShare;
  private DbEmotishareMetadata mEmotiShareResult;
  private View mEmptyMediaView;
  private View mFocusOverrideView;
  private String mFooterMessage;
  private PostAclButtonView[] mHistoryAclButtonArray;
  private ArrayList<AudienceData> mHistoryAudienceArray;
  private Integer mInsertCameraPhotoRequestId;
  private boolean mIsFromPlusOne;
  private boolean mLoadingMediaAttachments;
  private boolean mLoadingUrlPreview;
  private View mLoadingView;
  private DbLocation mLocation;
  private boolean mLocationChecked;
  private LocationController mLocationController;
  private View mMediaContainer;
  private TextView mMediaCount;
  private MediaGallery mMediaGallery;
  private ViewGroup mMediaGalleryView;
  private final LoaderManager.LoaderCallbacks<ArrayList<MediaRef>> mMediaRefLoaderCallbacks = new LoaderManager.LoaderCallbacks()
  {
    public final Loader<ArrayList<MediaRef>> onCreateLoader(int paramAnonymousInt, Bundle paramAnonymousBundle)
    {
      return new PostFragment.MediaRefLoader(PostFragment.this.getActivity(), PostFragment.this.mAccount, PostFragment.this.mAttachments);
    }

    public final void onLoaderReset(Loader<ArrayList<MediaRef>> paramAnonymousLoader)
    {
    }
  };
  private final MentionTokenizer mMentionTokenizer = new MentionTokenizer();
  private String mOriginalText;
  private Integer mPendingPostId;
  private ViewGroup mPreviewContainerView;
  private ApiaryActivity mPreviewResult = null;
  private ViewGroup mPreviewWrapperView;
  private Location mProviderLocation;
  private PostAclButtonView mPublicAclButton;
  private CircleData mPublicCircle;
  private boolean mRemoveEmotiShare;
  private boolean mRemoveLocation;
  private View mRemoveLocationView;
  private View mRemovePreviewButton;
  private AudienceData mResultAudience;
  private DbLocation mResultLocation;
  private ArrayList<MediaRef> mResultMediaItems;
  private AudienceData mSavedDefaultAudience;
  private ScrollView mScrollView;
  private final EsServiceListener mServiceListener = new ServiceListener((byte)0);
  private Animation mSlideInDown;
  private Animation mSlideOutUp;
  private PostAclButtonView mSquaresAclButton;
  private final TextWatcher mTextWatcher = new TextWatcher()
  {
    public final void afterTextChanged(Editable paramAnonymousEditable)
    {
      PostFragment.this.updatePostUI();
      PostFragment.updateText(PostFragment.this, PostFragment.this.getView());
    }

    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
    }

    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      if (PostFragment.this.mCommentsView == null);
      while (true)
      {
        return;
        int i = PostFragment.this.mCommentsView.getSelectionEnd();
        if (PostFragment.this.mMentionTokenizer.findTokenStart(paramAnonymousCharSequence, i) + PostFragment.this.mCommentsView.getThreshold() <= i)
        {
          int j = (int)PostFragment.this.getActivity().getResources().getDimension(R.dimen.plus_mention_suggestion_min_space);
          int[] arrayOfInt = new int[2];
          PostFragment.this.mCommentsView.getLocationOnScreen(arrayOfInt);
          Rect localRect = new Rect();
          PostFragment.this.getView().getWindowVisibleDisplayFrame(localRect);
          int k = arrayOfInt[1] + PostFragment.this.mCommentsView.getCursorYPosition();
          if (localRect.height() - k < j)
            PostFragment.this.mScrollView.smoothScrollTo(0, PostFragment.this.mCommentsView.getCursorYTop());
        }
      }
    }
  };
  private String mUrl;
  private CircleData mYourCircles;
  private PostAclButtonView mYourCirclesAclButton;
  private View.OnClickListener onClickListener = new View.OnClickListener()
  {
    private void toggleAclOverlay()
    {
      if (PostFragment.this.mAclDropDown.getVisibility() == 0)
        PostFragment.this.hideAclOverlay();
      while (true)
      {
        return;
        PostFragment.access$1800(PostFragment.this);
      }
    }

    public final void onClick(View paramAnonymousView)
    {
      FragmentActivity localFragmentActivity = PostFragment.this.getActivity();
      Bundle localBundle = PostFragment.this.getExtrasForLogging();
      OzViews localOzViews = OzViews.getViewForLogging(localFragmentActivity);
      int i = paramAnonymousView.getId();
      if (i == R.id.audience_button)
        if (PostFragment.this.mAudienceView.getAudience().isEmpty())
          toggleAclOverlay();
      while (true)
      {
        return;
        if (PostFragment.this.mAudienceView.getAudience().getSquareTargetCount() > 0)
        {
          PostFragment.access$600(PostFragment.this);
        }
        else
        {
          PostFragment.this.launchAclPicker();
          continue;
          if (i == R.id.chevron_icon)
          {
            toggleAclOverlay();
          }
          else if ((i == R.id.empty_media) || (i == R.id.choose_media))
          {
            PostFragment.this.hideAclOverlay();
            PostFragment.access$900(PostFragment.this);
          }
          else if (i == R.id.empty_emotishare)
          {
            ResourceRedirector.getInstance();
            if (Property.ENABLE_EMOTISHARE.getBoolean())
            {
              EsAnalytics.recordActionEvent(localFragmentActivity, PostFragment.this.mAccount, OzActions.EMOTISHARE_INSERT_CLICKED, localOzViews, localBundle);
              Intent localIntent2 = Intents.getChooseEmotiShareObjectIntent(localFragmentActivity, PostFragment.this.mAccount, PostFragment.this.mEmotiShare);
              PostFragment.this.launchActivity(localIntent2, 5);
            }
          }
          else
          {
            if (i == R.id.location_view)
            {
              EsAnalytics.recordActionEvent(localFragmentActivity, PostFragment.this.mAccount, OzActions.PLATFORM_SHARE_CLICKED_LOCATION, localOzViews, localBundle);
              boolean bool = PostFragment.access$1300(PostFragment.this);
              if ((PostFragment.this.mLocation != null) && (PostFragment.this.mLocation.hasCoordinates()));
              for (DbLocation localDbLocation = PostFragment.this.mLocation; ; localDbLocation = null)
              {
                Intent localIntent1 = Intents.getChooseLocationIntent(localFragmentActivity, PostFragment.this.mAccount, bool, localDbLocation);
                PostFragment.this.launchActivity(localIntent1, 3);
                break;
              }
            }
            if (i == R.id.remove_location)
            {
              PostFragment.this.hideAclOverlay();
              EsAnalytics.recordActionEvent(localFragmentActivity, PostFragment.this.mAccount, OzActions.PLATFORM_SHARE_CLICKED_LOCATION, localOzViews, localBundle);
              PostFragment.access$1502(PostFragment.this, true);
              PostFragment.this.setLocationChecked(false);
            }
            else if ((i == R.id.mention_scroll_view) || (i == R.id.compose_text))
            {
              PostFragment.this.hideAclOverlay();
            }
            else
            {
              Intents.PhotoViewIntentBuilder localPhotoViewIntentBuilder = Intents.newPhotoComposeActivityIntentBuilder(localFragmentActivity);
              MediaRef[] arrayOfMediaRef1 = new MediaRef[PostFragment.this.mAttachmentRefs.size()];
              MediaRef[] arrayOfMediaRef2 = (MediaRef[])PostFragment.this.mAttachmentRefs.toArray(arrayOfMediaRef1);
              MediaRef localMediaRef = (MediaRef)paramAnonymousView.getTag();
              if (localMediaRef != null)
              {
                int j = 0;
                for (int k = 0; k < arrayOfMediaRef2.length; k++)
                  if (localMediaRef.equals(arrayOfMediaRef2[k]))
                    j = k;
                localPhotoViewIntentBuilder.setAccount(PostFragment.this.mAccount).setPhotoIndex(Integer.valueOf(j)).setMediaRefs(arrayOfMediaRef2);
                PostFragment.this.launchActivity(localPhotoViewIntentBuilder.build(), 4);
              }
            }
          }
        }
      }
    }
  };
  private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener()
  {
    public final boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
    {
      if (paramAnonymousTextView == PostFragment.this.mCommentsView)
        switch (paramAnonymousInt)
        {
        default:
        case 6:
        }
      for (boolean bool = false; ; bool = true)
      {
        return bool;
        SoftInput.hide(paramAnonymousTextView);
      }
    }
  };

  private void addLocationListener()
  {
    if ((this.mLocationController == null) && (LocationController.isProviderEnabled(getActivity())))
      this.mLocationController = new LocationController(getActivity(), this.mAccount, true, 3000L, this.mProviderLocation, new PostLocationListener((byte)0));
  }

  private void addToMediaGallery(MediaRef paramMediaRef)
  {
    this.mAttachmentRefs.add(paramMediaRef);
    this.mMediaGallery.add(paramMediaRef);
    getView();
    updatePreviewContainer$3c7ec8c3();
    updatePostUI();
  }

  private static boolean compareAudiences(AudienceData paramAudienceData1, AudienceData paramAudienceData2)
  {
    boolean bool = false;
    if (paramAudienceData1 != null)
    {
      bool = false;
      if (paramAudienceData2 != null)
        break label14;
    }
    while (true)
    {
      return bool;
      label14: SharingRoster localSharingRoster = EsPeopleData.convertAudienceToSharingRoster(paramAudienceData1);
      HashSet localHashSet = new HashSet(localSharingRoster.sharingTargetId.size());
      Iterator localIterator1 = localSharingRoster.sharingTargetId.iterator();
      while (localIterator1.hasNext())
      {
        SharingTargetId localSharingTargetId2 = (SharingTargetId)localIterator1.next();
        localHashSet.add(SharingTargetIdJson.getInstance().toString(localSharingTargetId2));
      }
      Iterator localIterator2 = EsPeopleData.convertAudienceToSharingRoster(paramAudienceData2).sharingTargetId.iterator();
      while (true)
        if (localIterator2.hasNext())
        {
          SharingTargetId localSharingTargetId1 = (SharingTargetId)localIterator2.next();
          if (!localHashSet.remove(SharingTargetIdJson.getInstance().toString(localSharingTargetId1)))
          {
            bool = false;
            break;
          }
        }
      bool = localHashSet.isEmpty();
    }
  }

  private void createDefaultAclButton(View paramView, AudienceData paramAudienceData)
  {
    this.mSavedDefaultAudience = paramAudienceData;
    PostAclButtonView localPostAclButtonView1 = (PostAclButtonView)paramView.findViewById(R.id.default_acl_button);
    if (!isValidCustomAudience(paramAudienceData))
    {
      this.mDefaultAclButton = null;
      this.mDefaultAudience = null;
      localPostAclButtonView1.setVisibility(8);
      return;
    }
    int i;
    label50: PostAclButtonView localPostAclButtonView2;
    String str;
    int j;
    if (paramAudienceData.getSquareTargetCount() > 0)
    {
      i = 1;
      this.mDefaultAclButton = localPostAclButtonView1;
      this.mDefaultAudience = paramAudienceData;
      localPostAclButtonView2 = this.mDefaultAclButton;
      str = paramAudienceData.toNameList(getActivity());
      if (i == 0)
        break label142;
      j = R.drawable.ic_nav_communities;
      label86: if (i == 0)
        break label150;
    }
    label142: label150: for (int k = R.drawable.ic_communities_grey; ; k = R.drawable.ic_acl_custom_inactive)
    {
      localPostAclButtonView2.initialize(str, j, k, R.drawable.ic_done_save_ok_blue);
      this.mDefaultAclButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          PostFragment.this.updateAudienceUI$353d2340(PostFragment.this.getView(), null);
          PostFragment.this.updateAudienceUI$353d2340(PostFragment.this.getView(), PostFragment.this.mSavedDefaultAudience);
          PostFragment.this.updatePostUI();
          PostFragment.this.hideAclOverlay();
        }
      });
      this.mDefaultAclButton.setVisibility(0);
      break;
      i = 0;
      break label50;
      j = R.drawable.ic_person_active;
      break label86;
    }
  }

  private void createDomainAclButton(View paramView, CircleData paramCircleData)
  {
    this.mDomainCircle = paramCircleData;
    PostAclButtonView localPostAclButtonView = (PostAclButtonView)paramView.findViewById(R.id.domain_acl_button);
    if (paramCircleData == null)
    {
      this.mDomainAclButton = null;
      localPostAclButtonView.setVisibility(8);
    }
    while (true)
    {
      return;
      this.mDomainAclButton = localPostAclButtonView;
      this.mDomainAclButton.initialize(paramCircleData.getName(), R.drawable.ic_acl_domain_active, R.drawable.ic_acl_domain_inactive, R.drawable.ic_done_save_ok_green);
      this.mDomainAclButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          PostFragment.this.updateAudienceUI$353d2340(PostFragment.this.getView(), new AudienceData(PostFragment.this.mDomainCircle));
          PostFragment.this.updatePostUI();
          PostFragment.this.hideAclOverlay();
        }
      });
      this.mDomainAclButton.setVisibility(0);
    }
  }

  private void createPublicAclButton(View paramView, CircleData paramCircleData)
  {
    this.mPublicCircle = paramCircleData;
    PostAclButtonView localPostAclButtonView = (PostAclButtonView)paramView.findViewById(R.id.public_acl_button);
    if (paramCircleData == null)
    {
      this.mPublicAclButton = null;
      localPostAclButtonView.setVisibility(8);
    }
    while (true)
    {
      return;
      this.mPublicAclButton = localPostAclButtonView;
      this.mPublicAclButton.initialize(paramCircleData.getName(), R.drawable.ic_public_active, R.drawable.ic_public, R.drawable.ic_done_save_ok_green);
      this.mPublicAclButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          PostFragment.this.updateAudienceUI$353d2340(PostFragment.this.getView(), new AudienceData(PostFragment.this.mPublicCircle));
          PostFragment.this.updatePostUI();
          PostFragment.this.hideAclOverlay();
        }
      });
      this.mPublicAclButton.setVisibility(0);
    }
  }

  private void createYourCirclesAclButton(View paramView, CircleData paramCircleData)
  {
    this.mYourCircles = paramCircleData;
    PostAclButtonView localPostAclButtonView = (PostAclButtonView)paramView.findViewById(R.id.your_circles_acl_button);
    if (paramCircleData == null)
    {
      this.mYourCirclesAclButton = null;
      localPostAclButtonView.setVisibility(8);
    }
    while (true)
    {
      return;
      this.mYourCirclesAclButton = localPostAclButtonView;
      this.mYourCirclesAclButton.initialize(paramCircleData.getName(), R.drawable.ic_circles_active, R.drawable.ic_circles, R.drawable.ic_done_save_ok_blue);
      this.mYourCirclesAclButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          PostFragment.this.updateAudienceUI$353d2340(PostFragment.this.getView(), new AudienceData(PostFragment.this.mYourCircles));
          PostFragment.this.updatePostUI();
          PostFragment.this.hideAclOverlay();
        }
      });
      this.mYourCirclesAclButton.setVisibility(0);
    }
  }

  private boolean getCityLevelLocationPreference()
  {
    SharedPreferences localSharedPreferences = getActivity().getSharedPreferences("streams", 0);
    if (localSharedPreferences.contains("city_level_sharebox_location"));
    for (boolean bool = localSharedPreferences.getBoolean("city_level_sharebox_location", false); ; bool = localSharedPreferences.getBoolean("city_level_location", false))
      return bool;
  }

  private Bundle getExtrasForLogging()
  {
    InstrumentedActivity localInstrumentedActivity = (InstrumentedActivity)getActivity();
    Bundle localBundle2;
    if (localInstrumentedActivity == null)
      localBundle2 = null;
    while (true)
    {
      return localBundle2;
      Bundle localBundle1 = new Bundle();
      if (InstrumentedActivity.isFromThirdPartyApp(localInstrumentedActivity.getIntent()))
        localBundle1.putBoolean("extra_platform_event", true);
      if ((this.mAudienceView != null) && (this.mAudienceView.getAudience().getSquareTargetCount() > 0))
        localBundle1.putString("extra_square_id", this.mAudienceView.getAudience().getSquareTarget(0).getSquareId());
      localBundle2 = EsAnalytics.addExtrasForLogging(localBundle1, this.mEmotiShare);
      if (localBundle2.isEmpty())
        localBundle2 = null;
    }
  }

  private static DbLocation getLocationFromExtras(Bundle paramBundle)
  {
    DbLocation localDbLocation = (DbLocation)paramBundle.getParcelable("location");
    if (localDbLocation != null);
    while (true)
    {
      return localDbLocation;
      if ((paramBundle.containsKey("location_name")) || (paramBundle.containsKey("cid")))
      {
        boolean bool1 = paramBundle.containsKey("latitude");
        Integer localInteger1 = null;
        Object localObject = null;
        int i = 0;
        if (bool1)
        {
          boolean bool5 = paramBundle.containsKey("longitude");
          localInteger1 = null;
          localObject = null;
          i = 0;
          if (!bool5);
        }
        try
        {
          double d1 = Double.parseDouble(paramBundle.getString("latitude"));
          double d2 = Double.parseDouble(paramBundle.getString("longitude"));
          boolean bool7 = d1 < -90.0D;
          localInteger1 = null;
          if (!bool7)
          {
            boolean bool9 = d1 < 90.0D;
            localInteger1 = null;
            if (!bool9)
            {
              boolean bool10 = d2 < -180.0D;
              localInteger1 = null;
              if (!bool10)
              {
                boolean bool11 = d2 < 180.0D;
                localInteger1 = null;
                if (!bool11)
                {
                  localInteger1 = Integer.valueOf((int)(10000000.0D * d1));
                  Integer localInteger2 = Integer.valueOf((int)(10000000.0D * d2));
                  localObject = localInteger2;
                  i = 1;
                }
              }
            }
          }
          while (true)
          {
            boolean bool2 = paramBundle.containsKey("cid");
            String str1 = null;
            if (bool2)
            {
              str1 = paramBundle.getString("cid");
              i = 1;
            }
            boolean bool3 = paramBundle.containsKey("location_name");
            String str2 = null;
            if (bool3)
            {
              str2 = paramBundle.getString("location_name");
              i = 1;
            }
            boolean bool4 = paramBundle.containsKey("address");
            String str3 = null;
            if (bool4)
            {
              str3 = paramBundle.getString("address");
              i = 1;
            }
            if (i == 0)
              break label462;
            localDbLocation = new DbLocation(3, localInteger1, localObject, str2, str3, str1, 0.0D);
            break;
            boolean bool8 = EsLog.isLoggable("PostFragment", 5);
            localObject = null;
            i = 0;
            localInteger1 = null;
            if (bool8)
            {
              Log.w("PostFragment", "Provided latitude/longitude are out of range. latitude: " + paramBundle.getString("latitude") + ", longitude: " + paramBundle.getString("longitude"));
              localInteger1 = null;
              localObject = null;
              i = 0;
            }
          }
        }
        catch (NumberFormatException localNumberFormatException)
        {
          while (true)
          {
            boolean bool6 = EsLog.isLoggable("PostFragment", 5);
            localObject = null;
            i = 0;
            if (bool6)
            {
              Log.w("PostFragment", "Can't parse latitude/longitude extras. latitude: " + paramBundle.getString("latitude") + ", longitude: " + paramBundle.getString("longitude"));
              localObject = null;
              i = 0;
            }
          }
        }
      }
      else
      {
        label462: localDbLocation = null;
      }
    }
  }

  private void handlePostResult(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mPendingPostId == null) || (this.mPendingPostId.intValue() != paramInt));
    while (true)
    {
      return;
      this.mPendingPostId = null;
      FragmentActivity localFragmentActivity = getActivity();
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      {
        Toast.makeText(getActivity(), R.string.post_create_activity_error, 0).show();
      }
      else
      {
        Toast.makeText(localFragmentActivity, R.string.share_post_success, 0).show();
        SharedPreferences.Editor localEditor = getActivity().getSharedPreferences("streams", 0).edit();
        localEditor.putBoolean("want_sharebox_locations", this.mLocationChecked);
        localEditor.putBoolean("city_level_sharebox_location", getCityLevelLocationPreference());
        localEditor.commit();
        localFragmentActivity.setResult(-1);
        localFragmentActivity.finish();
      }
    }
  }

  private void handlePreviewResult(ServiceResult paramServiceResult, ApiaryActivity paramApiaryActivity)
  {
    if (paramServiceResult.hasError())
    {
      if (EsLog.isLoggable("PostFragment", 3))
        Log.d("PostFragment", "Could not retrieve preview: errorCode: " + paramServiceResult.getErrorCode());
      getActivity().showDialog(28199);
    }
    this.mPreviewResult = paramApiaryActivity;
    new Handler(Looper.getMainLooper()).post(new Runnable()
    {
      public final void run()
      {
        PostFragment.this.updateViews(PostFragment.this.getView());
      }
    });
  }

  private boolean hasContentDeepLinkMetadata()
  {
    if ((this.mContentDeepLinkMetadata != null) && (!TextUtils.isEmpty(this.mContentDeepLinkMetadata.getString("title"))) && (!TextUtils.isEmpty(this.mContentDeepLinkMetadata.getString("description"))));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void hideAclOverlay()
  {
    if ((this.mAclDropDown != null) && (this.mAclDropDown.getVisibility() == 0))
      this.mAclDropDown.startAnimation(this.mSlideOutUp);
  }

  private void insertCameraPhoto(String paramString)
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (paramString != null)
    {
      Uri localUri = Uri.parse(paramString);
      MediaRef localMediaRef = new MediaRef(this.mAccount.getGaiaId(), 0L, null, localUri, MediaRef.MediaType.IMAGE);
      this.mResultMediaItems = new ArrayList();
      this.mResultMediaItems.add(localMediaRef);
      updateResultMediaItems();
    }
    while (true)
    {
      if ((localFragmentActivity instanceof ImageUtils.InsertCameraPhotoDialogDisplayer))
        ((ImageUtils.InsertCameraPhotoDialogDisplayer)localFragmentActivity).hideInsertCameraPhotoDialog();
      return;
      Toast.makeText(localFragmentActivity, getString(R.string.camera_photo_error), 1).show();
    }
  }

  private static boolean isAudienceCircle(AudienceData paramAudienceData, int paramInt)
  {
    int i = 1;
    if ((paramAudienceData.getUserCount() == 0) && (paramAudienceData.getCircleCount() == i) && (paramAudienceData.getCircle(0).getType() == paramInt));
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  private static boolean isValidCustomAudience(AudienceData paramAudienceData)
  {
    boolean bool1 = false;
    if (paramAudienceData != null)
    {
      boolean bool2 = paramAudienceData.isEmpty();
      bool1 = false;
      if (!bool2)
        break label19;
    }
    while (true)
    {
      return bool1;
      label19: int i = paramAudienceData.getCircleCount();
      int j = paramAudienceData.getUserCount();
      int k = paramAudienceData.getSquareTargetCount();
      if ((j == 0) && (k == 0) && (i == 1))
      {
        int m = paramAudienceData.getCircle(0).getType();
        bool1 = false;
        if (m != 5)
        {
          bool1 = false;
          if (m != 8)
          {
            bool1 = false;
            if (m == 9);
          }
        }
      }
      else if (k > 0)
      {
        boolean bool3 = Property.ENABLE_SQUARES.getBoolean();
        bool1 = false;
        if (!bool3);
      }
      else
      {
        bool1 = true;
      }
    }
  }

  private void launchAclPicker()
  {
    FragmentActivity localFragmentActivity = getActivity();
    Bundle localBundle = getExtrasForLogging();
    OzViews localOzViews = OzViews.getViewForLogging(localFragmentActivity);
    EsAnalytics.recordActionEvent(localFragmentActivity, this.mAccount, OzActions.PLATFORM_SHARE_CLICKED_ACL, localOzViews, localBundle);
    launchActivity(Intents.getEditAudienceActivityIntent(localFragmentActivity, this.mAccount, getString(R.string.post_edit_audience_activity_title), this.mAudienceView.getAudience(), 5, false, true, true, false), 2);
  }

  private void launchActivity(Intent paramIntent, int paramInt)
  {
    if (this.mFocusOverrideView != null)
      this.mFocusOverrideView.requestFocus();
    hideAclOverlay();
    SoftInput.hide(getView());
    if (paramInt == 0)
      startActivity(paramIntent);
    while (true)
    {
      return;
      startActivityForResult(paramIntent, paramInt);
    }
  }

  private String makeLinkTitle(String paramString, boolean paramBoolean)
  {
    String str1;
    PackageManager localPackageManager;
    if ((paramBoolean) && (!TextUtils.isEmpty(paramString)))
    {
      str1 = this.mApiaryApiInfo.getSourceInfo().getPackageName();
      if (!TextUtils.isEmpty(str1))
        localPackageManager = getActivity().getPackageManager();
    }
    try
    {
      CharSequence localCharSequence = localPackageManager.getApplicationLabel(localPackageManager.getApplicationInfo(str1, 0));
      if (!TextUtils.isEmpty(localCharSequence))
      {
        String str2 = getResources().getString(R.string.stream_app_invite_title, new Object[] { localCharSequence, paramString });
        paramString = str2;
      }
      label88: return paramString;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label88;
    }
  }

  private void maybeExtractUrlFromString(String paramString)
  {
    if ((this.mLoadingMediaAttachments) || (this.mUrl != null) || (paramString == null));
    while (true)
    {
      return;
      SpannableString localSpannableString = new SpannableString(paramString);
      Linkify.addLinks(localSpannableString, 1);
      URLSpan[] arrayOfURLSpan = (URLSpan[])localSpannableString.getSpans(0, localSpannableString.length(), URLSpan.class);
      if (arrayOfURLSpan.length > 0)
        this.mUrl = arrayOfURLSpan[0].getURL();
    }
  }

  private void removeFromMediaGallery(MediaRef paramMediaRef)
  {
    this.mAttachmentRefs.remove(paramMediaRef);
    MediaGallery.access$5000(this.mMediaGallery, paramMediaRef);
    getView();
    updatePreviewContainer$3c7ec8c3();
    updatePostUI();
  }

  private void removeFromMediaGallery(List<MediaRef> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
      removeFromMediaGallery((MediaRef)localIterator.next());
  }

  private void removeLocationListener()
  {
    if (this.mLocationController != null)
    {
      this.mLocationController.release();
      this.mLocationController = null;
    }
  }

  private static void setLocationText$681f095b(View paramView, String paramString1, String paramString2)
  {
    TextView localTextView1 = (TextView)paramView.findViewById(16908310);
    TextView localTextView2;
    if (paramString1 == null)
    {
      localTextView1.setVisibility(4);
      localTextView2 = (TextView)paramView.findViewById(R.id.centered_text);
      if (paramString2 != null)
        break label57;
      localTextView2.setVisibility(8);
    }
    while (true)
    {
      return;
      localTextView1.setVisibility(0);
      localTextView1.setText(paramString1);
      break;
      label57: localTextView2.setVisibility(0);
      localTextView2.setText(paramString2);
    }
  }

  private void updateAudienceUI$353d2340(AudienceData paramAudienceData)
  {
    this.mAudienceView.replaceAudience(paramAudienceData);
  }

  private void updateLocation(View paramView)
  {
    View localView1 = paramView.findViewById(R.id.location_progress);
    ImageView localImageView = (ImageView)paramView.findViewById(R.id.location_marker);
    int i = 4;
    int j = 4;
    View localView2;
    if (this.mLocationChecked)
      if (this.mLocation != null)
      {
        localImageView.setVisibility(0);
        localImageView.setImageResource(R.drawable.ic_location_active);
        i = 0;
        String str1 = this.mLocation.getName();
        String str2 = this.mLocation.getBestAddress();
        if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)))
        {
          setLocationText$681f095b(paramView, str1, null);
          localView1.setVisibility(j);
          if (this.mRemoveLocationView != null)
            this.mRemoveLocationView.setVisibility(i);
          localView2 = paramView.findViewById(R.id.location_marker_progress_container);
          if (localView2 != null)
            if ((j != 0) && (i != 0))
              break label220;
        }
      }
    label220: for (int k = 0; ; k = 8)
    {
      localView2.setVisibility(k);
      return;
      setLocationText$681f095b(paramView, this.mLocation.getLocationName(), null);
      i = 0;
      break;
      localImageView.setVisibility(4);
      setLocationText$681f095b(paramView, null, getString(R.string.finding_your_location));
      j = 0;
      break;
      localImageView.setVisibility(0);
      localImageView.setImageResource(R.drawable.ic_location_grey);
      setLocationText$681f095b(paramView, null, getString(R.string.no_location_attached));
      break;
    }
  }

  private void updatePostUI()
  {
    FragmentActivity localFragmentActivity = getActivity();
    if ((localFragmentActivity instanceof PostActivity))
      ((PostActivity)localFragmentActivity).invalidateMenu();
    while (true)
    {
      return;
      if ((localFragmentActivity instanceof ShareActivity))
        ((ShareActivity)localFragmentActivity).invalidateMenu();
    }
  }

  private void updatePreviewContainer$3c7ec8c3()
  {
    this.mPreviewContainerView.removeAllViews();
    final FragmentActivity localFragmentActivity = getActivity();
    int i = 0;
    EmbedClientItem localEmbedClientItem1;
    DbEmbedDeepLink localDbEmbedDeepLink;
    EmbedClientItem localEmbedClientItem2;
    DbEmbedMedia localDbEmbedMedia;
    DbEmbedSkyjam localDbEmbedSkyjam;
    label165: Object localObject1;
    label228: int j;
    label242: int m;
    int n;
    if (this.mPreviewResult != null)
    {
      localEmbedClientItem1 = this.mPreviewResult.getEmbed(null);
      this.mPreviewContainerView.setBackgroundResource(R.drawable.compose_item_background);
      if ((localEmbedClientItem1 != null) && (localEmbedClientItem1.appInvite != null) && (localEmbedClientItem1.appInvite.callToAction != null) && (localEmbedClientItem1.appInvite.callToAction.deepLink != null))
      {
        DeepLinkData localDeepLinkData = localEmbedClientItem1.appInvite.callToAction.deepLink;
        String str9 = localEmbedClientItem1.appInvite.callToAction.renderedLabel;
        localDbEmbedDeepLink = new DbEmbedDeepLink(localDeepLinkData, str9);
        localEmbedClientItem2 = localEmbedClientItem1.appInvite.about;
        localDbEmbedMedia = null;
        localDbEmbedSkyjam = null;
        if (localEmbedClientItem2 != null)
        {
          if (localEmbedClientItem2.webPage == null)
            break label397;
          WebPage localWebPage = localEmbedClientItem2.webPage;
          localDbEmbedMedia = new DbEmbedMedia(localWebPage);
        }
        if (localDbEmbedSkyjam == null)
          break label545;
        StreamOneUpSkyjamView localStreamOneUpSkyjamView = new StreamOneUpSkyjamView(localFragmentActivity);
        localStreamOneUpSkyjamView.bind(localDbEmbedSkyjam.getAlbum(), localDbEmbedSkyjam.getSong(), localDbEmbedSkyjam.getImageUrl(), localDbEmbedSkyjam.getPreviewUrl(), localDbEmbedSkyjam.getMarketUrl(), this.mActivityId);
        localObject1 = localStreamOneUpSkyjamView;
        this.mPreviewContainerView.setBackgroundResource(R.drawable.bg_taco_mediapattern);
        if ((!this.mLoadingUrlPreview) && (i == 0))
          break label1022;
        j = 0;
        int k = j;
        if (localObject1 != null)
        {
          this.mPreviewContainerView.addView((View)localObject1);
          k = 0;
        }
        this.mPreviewContainerView.setVisibility(k);
        this.mLoadingView.setVisibility(j);
        this.mPreviewWrapperView.setVisibility(k);
        m = 8;
        n = 8;
        if (k != 0)
        {
          int i1 = this.mMediaGalleryView.getChildCount();
          TextView localTextView = this.mMediaCount;
          Resources localResources = getResources();
          int i2 = R.plurals.share_photo_count;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(i1);
          localTextView.setText(localResources.getQuantityString(i2, i1, arrayOfObject));
          if (i1 <= 0)
            break label1029;
          m = 0;
        }
      }
    }
    while (true)
    {
      this.mMediaContainer.setVisibility(m);
      this.mEmptyMediaView.setVisibility(n);
      return;
      localEmbedClientItem2 = localEmbedClientItem1;
      localDbEmbedDeepLink = null;
      break;
      label397: if (localEmbedClientItem2.videoObject != null)
      {
        VideoObject localVideoObject = localEmbedClientItem2.videoObject;
        localDbEmbedMedia = new DbEmbedMedia(localVideoObject);
        localDbEmbedSkyjam = null;
        break label165;
      }
      if (localEmbedClientItem2.playMusicAlbum != null)
      {
        PlayMusicAlbum localPlayMusicAlbum = localEmbedClientItem2.playMusicAlbum;
        localDbEmbedSkyjam = new DbEmbedSkyjam(localPlayMusicAlbum);
        localDbEmbedMedia = null;
        break label165;
      }
      if (localEmbedClientItem2.playMusicTrack != null)
      {
        PlayMusicTrack localPlayMusicTrack = localEmbedClientItem2.playMusicTrack;
        localDbEmbedSkyjam = new DbEmbedSkyjam(localPlayMusicTrack);
        localDbEmbedMedia = null;
        break label165;
      }
      if (localEmbedClientItem2.thing != null)
      {
        Thing localThing = localEmbedClientItem2.thing;
        localDbEmbedMedia = new DbEmbedMedia(localThing);
        localDbEmbedSkyjam = null;
        break label165;
      }
      EsLog.writeToLog(6, "PostFragment", "Found an embed we don't understand without a THING!");
      localDbEmbedMedia = null;
      localDbEmbedSkyjam = null;
      break label165;
      label545: if (localDbEmbedMedia != null)
      {
        String str1 = localDbEmbedMedia.getTitle();
        boolean bool;
        label565: String str2;
        String str3;
        label590: String str4;
        Object localObject2;
        String str5;
        MediaRef localMediaRef;
        int i3;
        if (localDbEmbedDeepLink != null)
        {
          bool = true;
          str2 = makeLinkTitle(str1, bool);
          if (!localDbEmbedMedia.isVideo())
            break label730;
          str3 = localDbEmbedMedia.getVideoUrl();
          str4 = LinksCardView.makeLinkUrl(str3);
          localObject2 = localDbEmbedMedia.getImageUrl();
          str5 = localDbEmbedMedia.getVideoUrl();
          if (localDbEmbedMedia.isVideo())
          {
            String str8 = ImageUtils.rewriteYoutubeMediaUrl(str5);
            if (!TextUtils.equals(str5, str8))
              localObject2 = str8;
          }
          if (!TextUtils.isEmpty((CharSequence)localObject2))
            break label740;
          localMediaRef = null;
          i3 = 3;
          if (localDbEmbedDeepLink == null)
            break label826;
        }
        label820: label826: for (String str7 = localDbEmbedDeepLink.getLabelOrDefault(localFragmentActivity); ; str7 = null)
        {
          LinkPreviewView localLinkPreviewView = new LinkPreviewView(localFragmentActivity);
          OneUpLinkView.BackgroundViewLoadedListener local17 = null;
          i = 0;
          if (localMediaRef != null)
          {
            i = 1;
            local17 = new OneUpLinkView.BackgroundViewLoadedListener()
            {
              public final void onBackgroundViewLoaded(OneUpLinkView paramAnonymousOneUpLinkView)
              {
                if (!PostFragment.this.mLoadingUrlPreview)
                  PostFragment.this.mLoadingView.setVisibility(8);
              }
            };
          }
          localLinkPreviewView.init(localMediaRef, i3, local17, str2, str7, null, str4);
          localObject1 = localLinkPreviewView;
          break;
          bool = false;
          break label565;
          label730: str3 = localDbEmbedMedia.getContentUrl();
          break label590;
          label740: String str6 = localDbEmbedMedia.getOwnerId();
          long l = PrimitiveUtils.safeLong(Long.valueOf(localDbEmbedMedia.getPhotoId()));
          if (localDbEmbedMedia.isVideo());
          for (Uri localUri = Uri.parse(str5); ; localUri = null)
          {
            localMediaRef = new MediaRef(str6, l, (String)localObject2, localUri, localDbEmbedMedia.getMediaType());
            if (localMediaRef.getType() != MediaRef.MediaType.IMAGE)
              break label820;
            i3 = 3;
            break;
          }
          i3 = 2;
          break label654;
        }
      }
      label654: localFragmentActivity.showDialog(28199);
      i = 0;
      localObject1 = null;
      break label228;
      if (this.mEmotiShare != null)
      {
        EmotiShareView localEmotiShareView = new EmotiShareView(localFragmentActivity);
        localEmotiShareView.setMediaRef(this.mEmotiShare.getImageRef());
        localEmotiShareView.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            Intent localIntent = Intents.getChooseEmotiShareObjectIntent(localFragmentActivity, PostFragment.this.mAccount, PostFragment.this.mEmotiShare);
            EsAnalytics.recordActionEvent(localFragmentActivity, PostFragment.this.mAccount, OzActions.EMOTISHARE_INSERT_CLICKED, OzViews.getViewForLogging(localFragmentActivity), PostFragment.this.getExtrasForLogging());
            PostFragment.this.launchActivity(localIntent, 5);
          }
        });
        localEmotiShareView.getMissingImageView().setImageResource(R.drawable.ic_error_gold_40);
        ImageResourceView localImageResourceView = localEmotiShareView.getImageView();
        localEmotiShareView.getView().setBackgroundResource(R.drawable.bg_taco_mediapattern);
        ViewGroup.LayoutParams localLayoutParams = this.mPreviewWrapperView.getLayoutParams();
        localLayoutParams.width = -1;
        localLayoutParams.height = getResources().getDimensionPixelOffset(R.dimen.emotishare_preview_height);
        this.mPreviewWrapperView.setLayoutParams(localLayoutParams);
        if (getResources().getConfiguration().orientation != 2)
          localImageResourceView.setScaleMode(1);
        if (this.mRemovePreviewButton != null)
        {
          this.mRemovePreviewButton.setVisibility(0);
          this.mRemovePreviewButton.setOnClickListener(new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
              paramAnonymousView.setVisibility(8);
              paramAnonymousView.setOnClickListener(null);
              PostFragment.access$1102(PostFragment.this, null);
              if (PostFragment.this.mCommentsView != null)
                PostFragment.this.mCommentsView.setText(null);
              EsAnalytics.recordActionEvent(localFragmentActivity, PostFragment.this.mAccount, OzActions.EMOTISHARE_REMOVED, OzViews.getViewForLogging(localFragmentActivity), PostFragment.this.getExtrasForLogging());
              PostFragment.this.updatePreviewContainer$3c7ec8c3(PostFragment.this.getView());
              PostFragment.this.updatePostUI();
            }
          });
        }
        localObject1 = localEmotiShareView.getView();
        i = 0;
        break label228;
      }
      i = 0;
      localObject1 = null;
      break label228;
      label1022: j = 8;
      break label242;
      label1029: n = 0;
    }
  }

  private void updateResultMediaItems()
  {
    if ((this.mResultMediaItems != null) && (this.mResultMediaItems.size() > 0))
    {
      if (this.mResultMediaItems.size() + this.mAttachmentRefs.size() <= 250)
        break label95;
      FragmentActivity localFragmentActivity = getActivity();
      int i = R.string.post_max_photos;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(250);
      Toast.makeText(localFragmentActivity, getString(i, arrayOfObject), 1).show();
    }
    while (true)
    {
      this.mResultMediaItems.clear();
      this.mResultMediaItems = null;
      updatePostUI();
      return;
      label95: Iterator localIterator = this.mResultMediaItems.iterator();
      while (localIterator.hasNext())
        addToMediaGallery((MediaRef)localIterator.next());
    }
  }

  private static void updateText(View paramView)
  {
    if (paramView == null);
    ImageView localImageView;
    MentionMultiAutoCompleteTextView localMentionMultiAutoCompleteTextView;
    do
    {
      return;
      localImageView = (ImageView)paramView.findViewById(R.id.text_marker);
      localMentionMultiAutoCompleteTextView = (MentionMultiAutoCompleteTextView)paramView.findViewById(R.id.compose_text);
    }
    while ((localImageView == null) || (localMentionMultiAutoCompleteTextView == null));
    if (TextUtils.isEmpty(localMentionMultiAutoCompleteTextView.getText().toString()));
    for (int i = R.drawable.ic_text_grey; ; i = R.drawable.ic_text_active)
    {
      localImageView.setImageResource(i);
      break;
    }
  }

  private void updateViews(View paramView)
  {
    if (paramView != null)
    {
      View localView = paramView.findViewById(R.id.footer_separator);
      TextView localTextView = (TextView)paramView.findViewById(R.id.footer_message);
      int i = 8;
      if (this.mFooterMessage != null)
      {
        i = 0;
        localTextView.setText(this.mFooterMessage);
      }
      localView.setVisibility(i);
      localTextView.setVisibility(i);
      updatePreviewContainer$3c7ec8c3();
      updateText(paramView);
    }
  }

  public final boolean canPost()
  {
    Integer localInteger = this.mPendingPostId;
    boolean bool1 = false;
    if (localInteger == null)
    {
      boolean bool2 = this.mLoadingUrlPreview;
      bool1 = false;
      if (!bool2)
        break label24;
    }
    label24: boolean bool3;
    do
    {
      return bool1;
      bool3 = PeopleUtils.isEmpty(this.mAudienceView.getAudience());
      bool1 = false;
    }
    while (bool3);
    int i;
    label53: int j;
    label66: int k;
    label79: int m;
    label97: int n;
    if (this.mEmotiShare != null)
    {
      i = 1;
      if (TextUtils.isEmpty(this.mUrl))
        break label163;
      j = 1;
      if (TextUtils.isEmpty(this.mContentDeepLinkId))
        break label169;
      k = 1;
      if (this.mCommentsView.getText().length() <= 0)
        break label175;
      m = 1;
      if (this.mLocation == null)
        break label181;
      n = 1;
      label107: if (this.mAttachmentRefs.isEmpty())
        break label187;
    }
    label163: label169: label175: label181: label187: for (int i1 = 1; ; i1 = 0)
    {
      if ((i == 0) && (j == 0) && (k == 0) && (m == 0) && (n == 0))
      {
        bool1 = false;
        if (i1 == 0)
          break;
      }
      bool1 = true;
      break;
      i = 0;
      break label53;
      j = 0;
      break label66;
      k = 0;
      break label79;
      m = 0;
      break label97;
      n = 0;
      break label107;
    }
  }

  public final void onActivityCreated(Bundle paramBundle)
  {
    boolean bool1 = true;
    super.onActivityCreated(paramBundle);
    boolean bool2;
    boolean bool3;
    if (paramBundle == null)
    {
      if (this.mLocation == null)
        break label58;
      bool2 = bool1;
      if (!bool2)
      {
        if (!this.mAccount.isChild())
          break label63;
        bool3 = false;
        label37: if (!bool3)
          break label121;
      }
      if (!LocationController.isProviderEnabled(getActivity()))
        break label121;
    }
    while (true)
    {
      setLocationChecked(bool1);
      return;
      label58: bool2 = false;
      break;
      label63: SharedPreferences localSharedPreferences = getActivity().getSharedPreferences("streams", 0);
      if (localSharedPreferences.contains("want_sharebox_locations"))
      {
        bool3 = localSharedPreferences.getBoolean("want_sharebox_locations", false);
        break label37;
      }
      bool3 = localSharedPreferences.getBoolean("want_locations", false);
      break label37;
      label121: bool1 = false;
    }
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 != -1);
    while (true)
    {
      return;
      switch (paramInt1)
      {
      default:
        break;
      case 1:
        if ((paramInt2 == -1) && (paramIntent != null))
          if (paramIntent.hasExtra("insert_photo_request_id"))
          {
            FragmentActivity localFragmentActivity = getActivity();
            this.mInsertCameraPhotoRequestId = Integer.valueOf(paramIntent.getIntExtra("insert_photo_request_id", 0));
            if (!(localFragmentActivity instanceof ImageUtils.InsertCameraPhotoDialogDisplayer))
              continue;
            ((ImageUtils.InsertCameraPhotoDialogDisplayer)localFragmentActivity).showInsertCameraPhotoDialog();
          }
        break;
      case 2:
        if (paramIntent != null)
        {
          this.mResultAudience = ((AudienceData)paramIntent.getParcelableExtra("audience"));
          if ((this.mResultAudience != null) && (EsLog.isLoggable("PostFragment", 3)))
            for (CircleData localCircleData : this.mResultAudience.getCircles())
              Log.d("PostFragment", "Out circle id: " + localCircleData.getId());
        }
        break;
      case 3:
        if (paramIntent != null)
        {
          this.mResultLocation = ((DbLocation)paramIntent.getParcelableExtra("location"));
          if (this.mResultLocation == null);
          for (boolean bool2 = true; ; bool2 = false)
          {
            this.mRemoveLocation = bool2;
            break;
          }
        }
        break;
      case 5:
        this.mEmotiShareResult = ((DbEmotishareMetadata)paramIntent.getParcelableExtra("typed_image_embed"));
        if (this.mEmotiShareResult == null);
        for (boolean bool1 = true; ; bool1 = false)
        {
          this.mRemoveEmotiShare = bool1;
          break;
        }
        this.mResultMediaItems = new ArrayList();
        ArrayList localArrayList = paramIntent.getParcelableArrayListExtra("mediarefs");
        int m = localArrayList.size();
        for (int n = 0; n < m; n++)
          this.mResultMediaItems.add(localArrayList.get(n));
      case 4:
        if ((paramInt2 == -1) && (paramIntent.hasExtra("photo_remove_from_compose")))
        {
          Parcelable[] arrayOfParcelable = paramIntent.getParcelableArrayExtra("photo_remove_from_compose");
          MediaRef[] arrayOfMediaRef = new MediaRef[arrayOfParcelable.length];
          for (int i = 0; i < arrayOfParcelable.length; i++)
            arrayOfMediaRef[i] = ((MediaRef)arrayOfParcelable[i]);
          removeFromMediaGallery(Arrays.asList(arrayOfMediaRef));
        }
        break;
      }
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    this.mAccount = ((EsAccount)localBundle.getParcelable("account"));
    if (paramBundle == null)
    {
      if (localBundle.containsKey("external_id"))
        this.mActivityId = localBundle.getString("external_id");
      if (this.mActivityId == null)
        this.mActivityId = (System.currentTimeMillis() + "." + StringUtils.randomString(32));
      this.mLocation = getLocationFromExtras(localBundle);
      this.mAttachmentRefs = new ArrayList();
      if (localBundle.containsKey("android.intent.extra.STREAM"))
      {
        this.mAttachments = localBundle.getParcelableArrayList("android.intent.extra.STREAM");
        getLoaderManager().initLoader(R.id.post_fragment_media_ref_loader_id, null, this.mMediaRefLoaderCallbacks);
        this.mLoadingMediaAttachments = true;
      }
      if (localBundle.containsKey("url"))
        this.mUrl = localBundle.getString("url");
      if (localBundle.containsKey("content_deep_link_id"))
      {
        this.mContentDeepLinkId = localBundle.getString("content_deep_link_id");
        maybeExtractUrlFromString(this.mContentDeepLinkId);
      }
      if (localBundle.containsKey("content_deep_link_metadata"))
        this.mContentDeepLinkMetadata = localBundle.getBundle("content_deep_link_metadata");
      if (localBundle.containsKey("call_to_action"))
        this.mCallToAction = ((CallToActionData)localBundle.getParcelable("call_to_action"));
      if (localBundle.containsKey("footer"))
        this.mFooterMessage = localBundle.getString("footer");
      if (localBundle.containsKey("api_info"));
      PackageManager localPackageManager;
      String str1;
      ApiaryApiInfo localApiaryApiInfo;
      String str2;
      for (this.mApiaryApiInfo = ((ApiaryApiInfo)localBundle.getSerializable("api_info")); ; this.mApiaryApiInfo = new ApiaryApiInfo(null, str1, str2, PlatformContractUtils.getCertificate(str2, localPackageManager), "", localApiaryApiInfo))
      {
        if (localBundle.containsKey("typed_image_embed"))
          this.mEmotiShareResult = ((DbEmotishareMetadata)localBundle.getParcelable("typed_image_embed"));
        this.mResultAudience = ((AudienceData)localBundle.getParcelable("audience"));
        if (localBundle.containsKey("android.intent.extra.TEXT"))
        {
          this.mOriginalText = localBundle.getString("android.intent.extra.TEXT");
          maybeExtractUrlFromString(this.mOriginalText);
          if ((this.mUrl != null) && (this.mOriginalText != null) && (this.mOriginalText.trim().equals(this.mUrl)))
            this.mOriginalText = null;
        }
        if (localBundle.containsKey("insert_photo_request_id"))
        {
          this.mInsertCameraPhotoRequestId = Integer.valueOf(localBundle.getInt("insert_photo_request_id"));
          FragmentActivity localFragmentActivity = getActivity();
          if ((localFragmentActivity instanceof ImageUtils.InsertCameraPhotoDialogDisplayer))
            ((ImageUtils.InsertCameraPhotoDialogDisplayer)localFragmentActivity).showInsertCameraPhotoDialog();
        }
        if ((this.mContentDeepLinkId == null) || (this.mUrl != null) || (hasContentDeepLinkMetadata()))
          break;
        if (EsLog.isLoggable("PostFragment", 5))
          Log.w("PostFragment", "Mobile deep-link IDs must specify metadata.");
        getActivity().setResult(0);
        getActivity().finish();
        return;
        localPackageManager = getActivity().getPackageManager();
        str1 = Property.PLUS_CLIENTID.get();
        localApiaryApiInfo = new ApiaryApiInfo(null, str1, "com.google.android.apps.social", PlatformContractUtils.getCertificate("com.google.android.apps.social", localPackageManager), null);
        str2 = getActivity().getPackageName();
      }
      this.mIsFromPlusOne = localBundle.getBoolean("is_from_plusone", false);
    }
    while (true)
    {
      if (this.mSavedDefaultAudience == null)
        getLoaderManager().restartLoader(2, null, new CursorLoaderCallbacks((byte)0));
      if ((this.mPublicCircle != null) || (this.mDomainCircle != null) || (this.mYourCircles != null))
        break;
      getLoaderManager().initLoader(1, null, new CursorLoaderCallbacks((byte)0));
      break;
      this.mActivityId = paramBundle.getString("activity_id");
      if (paramBundle.containsKey("location"))
      {
        this.mLocation = ((DbLocation)paramBundle.getParcelable("location"));
        this.mLocationChecked = true;
      }
      if (paramBundle.containsKey("prov_location"))
        this.mProviderLocation = ((Location)paramBundle.getParcelable("prov_location"));
      if (paramBundle.containsKey("pending_request_id"))
        this.mPendingPostId = Integer.valueOf(paramBundle.getInt("pending_request_id"));
      if (paramBundle.containsKey("insert_camera_photo_req_id"))
        this.mInsertCameraPhotoRequestId = Integer.valueOf(paramBundle.getInt("insert_camera_photo_req_id"));
      if (paramBundle.containsKey("preview_result"))
        this.mPreviewResult = ((ApiaryActivity)paramBundle.getParcelable("preview_result"));
      if (paramBundle.containsKey("emotishare_result"))
        this.mEmotiShareResult = ((DbEmotishareMetadata)paramBundle.getParcelable("emotishare_result"));
      if (paramBundle.containsKey("emotishare"))
        this.mEmotiShare = ((DbEmotishareMetadata)paramBundle.getParcelable("emotishare"));
      if (paramBundle.containsKey("api_info"))
        this.mApiaryApiInfo = ((ApiaryApiInfo)paramBundle.getSerializable("api_info"));
      if (paramBundle.containsKey("footer"))
        this.mFooterMessage = paramBundle.getString("footer");
      this.mAttachmentRefs = paramBundle.getParcelableArrayList("l_attachments");
      this.mLoadingMediaAttachments = paramBundle.getBoolean("loading_attachments", false);
      if (paramBundle.containsKey("url"))
        this.mUrl = paramBundle.getString("url");
      if (paramBundle.containsKey("content_deep_link_id"))
        this.mContentDeepLinkId = paramBundle.getString("content_deep_link_id");
      if (paramBundle.containsKey("content_deep_link_metadata"))
        this.mContentDeepLinkMetadata = paramBundle.getBundle("content_deep_link_metadata");
      if (paramBundle.containsKey("call_to_action"))
        this.mCallToAction = ((CallToActionData)paramBundle.getParcelable("call_to_action"));
      if (paramBundle.containsKey("text"))
        this.mOriginalText = paramBundle.getString("text");
      this.mIsFromPlusOne = paramBundle.getBoolean("is_from_plusone", false);
      if (paramBundle.containsKey("public_circle"))
        this.mPublicCircle = ((CircleData)paramBundle.getParcelable("public_circle"));
      if (paramBundle.containsKey("domain_circle"))
        this.mDomainCircle = ((CircleData)paramBundle.getParcelable("domain_circle"));
      if (paramBundle.containsKey("your_circles"))
        this.mYourCircles = ((CircleData)paramBundle.getParcelable("your_circles"));
      if (paramBundle.containsKey("saved_default_audience"))
        this.mSavedDefaultAudience = ((AudienceData)paramBundle.getParcelable("saved_default_audience"));
      if (paramBundle.containsKey("default_audience"))
        this.mDefaultAudience = ((AudienceData)paramBundle.getParcelable("default_audience"));
      if (paramBundle.containsKey("audience_history"))
        this.mHistoryAudienceArray = paramBundle.getParcelableArrayList("audience_history");
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView1 = paramLayoutInflater.inflate(R.layout.post_fragment, paramViewGroup, false);
    FragmentActivity localFragmentActivity = getActivity();
    this.mLoadingView = localView1.findViewById(R.id.list_empty_progress);
    this.mMediaGalleryView = ((ViewGroup)localView1.findViewById(R.id.photos_gallery));
    this.mMediaCount = ((TextView)localView1.findViewById(R.id.media_count));
    this.mAudienceView = ((AudienceView)localView1.findViewById(R.id.audience_view));
    this.mScrollView = ((ScrollView)localView1.findViewById(R.id.mention_scroll_view));
    this.mCommentsView = ((MentionMultiAutoCompleteTextView)localView1.findViewById(R.id.compose_text));
    this.mPreviewContainerView = ((ViewGroup)localView1.findViewById(R.id.share_preview_container));
    this.mEmptyMediaView = localView1.findViewById(R.id.empty_media_container);
    this.mMediaContainer = localView1.findViewById(R.id.photos_container);
    this.mRemoveLocationView = localView1.findViewById(R.id.remove_location);
    this.mFocusOverrideView = localView1.findViewById(R.id.focus_override);
    this.mPreviewWrapperView = ((ViewGroup)localView1.findViewById(R.id.share_preview_wrapper));
    this.mAclDropDown = localView1.findViewById(R.id.acl_overlay);
    this.mRemovePreviewButton = localView1.findViewById(R.id.remove_preview_button);
    createPublicAclButton(localView1, this.mPublicCircle);
    createYourCirclesAclButton(localView1, this.mYourCircles);
    createDomainAclButton(localView1, this.mDomainCircle);
    createDefaultAclButton(localView1, this.mSavedDefaultAudience);
    this.mCreateAclButton = ((PostAclButtonView)localView1.findViewById(R.id.create_acl_button));
    this.mCreateAclButton.initialize(getString(R.string.post_create_custom_acl), R.drawable.ic_right);
    this.mCreateAclButton.setActive();
    this.mCreateAclButton.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        PostFragment.this.hideAclOverlay();
        PostFragment.this.launchAclPicker();
      }
    });
    this.mCreateAclButton.setVisibility(0);
    if (Property.ENABLE_SQUARES.getBoolean())
    {
      this.mSquaresAclButton = ((PostAclButtonView)localView1.findViewById(R.id.squares_acl_button));
      this.mSquaresAclButton.initialize(getString(R.string.square_member_item_text), R.drawable.ic_communities_grey, R.drawable.ic_communities_grey, R.drawable.ic_right);
      this.mSquaresAclButton.setActive();
      this.mSquaresAclButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          PostFragment.this.hideAclOverlay();
          PostFragment.access$600(PostFragment.this);
        }
      });
      this.mSquaresAclButton.setVisibility(0);
    }
    this.mAclDropDown.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        PostFragment.this.hideAclOverlay();
      }
    });
    this.mSlideInDown = AnimationUtils.loadAnimation(localFragmentActivity, R.anim.slide_in_down_self);
    this.mSlideInDown.setInterpolator(localFragmentActivity, R.anim.decelerate_interpolator);
    this.mSlideInDown.setDuration(250L);
    this.mSlideOutUp = AnimationUtils.loadAnimation(localFragmentActivity, R.anim.slide_out_up_self);
    this.mSlideOutUp.setInterpolator(localFragmentActivity, R.anim.accelerate_interpolator);
    this.mSlideOutUp.setDuration(250L);
    this.mSlideInDown.setAnimationListener(new Animation.AnimationListener()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
      }

      public final void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public final void onAnimationStart(Animation paramAnonymousAnimation)
      {
        if ((PostFragment.this.mAudienceView instanceof TextOnlyAudienceView))
          ((TextOnlyAudienceView)PostFragment.this.mAudienceView).setChevronDirection(TextOnlyAudienceView.ChevronDirection.POINT_UP);
        if (PostFragment.this.mAclDropDown != null)
          PostFragment.this.mAclDropDown.setVisibility(0);
      }
    });
    this.mSlideOutUp.setAnimationListener(new Animation.AnimationListener()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        if (PostFragment.this.mAclDropDown != null)
          PostFragment.this.mAclDropDown.setVisibility(8);
      }

      public final void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public final void onAnimationStart(Animation paramAnonymousAnimation)
      {
        if ((PostFragment.this.mAudienceView instanceof TextOnlyAudienceView))
          ((TextOnlyAudienceView)PostFragment.this.mAudienceView).setChevronDirection(TextOnlyAudienceView.ChevronDirection.POINT_DOWN);
      }
    });
    this.mCommentsView.setOnClickListener(this.onClickListener);
    this.mScrollView.setOnClickListener(this.onClickListener);
    if (Build.VERSION.SDK_INT < 11)
      this.mMediaGalleryView.setOnCreateContextMenuListener(this);
    this.mMediaGallery = new MediaGallery(localFragmentActivity, this.mAttachmentRefs, this.mMediaGalleryView);
    if (!(getActivity() instanceof ShareActivity))
      this.mCommentsView.setMinLines(localFragmentActivity.getResources().getInteger(R.integer.compose_text_min_lines_big));
    this.mCommentsView.init(this, this.mAccount, null, this.mAudienceView);
    this.mCommentsView.addTextChangedListener(this.mTextWatcher);
    this.mCommentsView.setOnEditorActionListener(this.onEditorActionListener);
    if (paramBundle == null);
    try
    {
      this.mCommentsView.setText(this.mOriginalText);
      this.mCommentsView.addTextChangedListener(new TextWatcher()
      {
        public final void afterTextChanged(Editable paramAnonymousEditable)
        {
          PostFragment.this.updatePostUI();
          PostFragment.updateText(PostFragment.this, PostFragment.this.getView());
        }

        public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
        }

        public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
        }
      });
      this.mAudienceView.setAudienceChangedCallback(new Runnable()
      {
        public final void run()
        {
          PostFragment.this.updatePostUI();
        }
      });
      this.mAudienceView.setAccount(this.mAccount);
      this.mAudienceView.findViewById(R.id.audience_button).setOnClickListener(this.onClickListener);
      this.mAudienceView.findViewById(R.id.chevron_icon).setOnClickListener(this.onClickListener);
      if ((this.mAudienceView instanceof TextOnlyAudienceView))
      {
        TextOnlyAudienceView localTextOnlyAudienceView = (TextOnlyAudienceView)this.mAudienceView;
        localTextOnlyAudienceView.setChevronDirection(TextOnlyAudienceView.ChevronDirection.POINT_DOWN);
        localTextOnlyAudienceView.setChevronVisibility(0);
      }
      localView1.findViewById(R.id.location_view).setOnClickListener(this.onClickListener);
      localView1.findViewById(R.id.choose_media).setOnClickListener(this.onClickListener);
      localView1.findViewById(R.id.empty_media).setOnClickListener(this.onClickListener);
      ResourceRedirector.getInstance();
      if (Property.ENABLE_EMOTISHARE.getBoolean())
      {
        View localView2 = localView1.findViewById(R.id.empty_emotishare);
        localView2.setOnClickListener(this.onClickListener);
        localView2.setVisibility(0);
        localView1.findViewById(R.id.vertical_separator).setVisibility(0);
      }
      this.mMediaContainer.setOnClickListener(this.onClickListener);
      this.mRemoveLocationView.setOnClickListener(this.onClickListener);
      this.mPreviewWrapperView.setVisibility(8);
      if ((this.mAttachmentRefs.isEmpty()) && (!this.mLoadingMediaAttachments) && (this.mUrl != null))
      {
        getLoaderManager().initLoader(3, Bundle.EMPTY, new CursorLoaderCallbacks((byte)0));
        this.mLoadingUrlPreview = true;
      }
      if ((this.mContentDeepLinkId != null) && (this.mUrl == null) && (hasContentDeepLinkMetadata()))
        handlePreviewResult(new ServiceResult(), ApiaryActivityFactory.getApiaryActivity(this.mContentDeepLinkMetadata, this.mCallToAction));
      if ((this.mUrl != null) || (this.mContentDeepLinkId != null))
        this.mEmptyMediaView.setVisibility(8);
      updateLocation(localView1);
      updatePostUI();
      updateViews(localView1);
      if (paramBundle == null)
      {
        if (getActivity().getIntent().getBooleanExtra("start_editing", false))
          this.mCommentsView.requestFocus();
      }
      else
        return localView1;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        continue;
        this.mFocusOverrideView.requestFocus();
      }
    }
  }

  public final void onDestroyView()
  {
    this.mCommentsView.destroy();
    this.mCommentsView = null;
    super.onDestroyView();
  }

  public final void onDialogCanceled$20f9a4b7(String paramString)
  {
  }

  public final void onDialogListClick$12e92030(int paramInt, Bundle paramBundle)
  {
  }

  public final void onDialogNegativeClick$20f9a4b7(String paramString)
  {
  }

  public final void onDialogPositiveClick(Bundle paramBundle, String paramString)
  {
    if ("quit".equals(paramString))
      if (!this.mIsFromPlusOne)
        break label54;
    label54: for (OzActions localOzActions = OzActions.PLATFORM_CANCEL_SHARE_FROM_PLUSONE; ; localOzActions = OzActions.PLATFORM_CANCEL_SHARE)
    {
      FragmentActivity localFragmentActivity = getActivity();
      EsAnalytics.recordActionEvent(localFragmentActivity, this.mAccount, localOzActions, OzViews.getViewForLogging(localFragmentActivity), getExtrasForLogging());
      getActivity().finish();
      return;
    }
  }

  public final void onDiscard(boolean paramBoolean)
  {
    SoftInput.hide(this.mCommentsView);
    if ((!paramBoolean) && (this.mAclDropDown != null) && (this.mAclDropDown.getVisibility() == 0))
    {
      this.mAclDropDown.startAnimation(this.mSlideOutUp);
      return;
    }
    String str1 = this.mCommentsView.getText().toString();
    String str2;
    label63: int i;
    if (this.mOriginalText != null)
    {
      str2 = this.mOriginalText;
      if (str1.equals(str2))
        break label141;
      i = 1;
    }
    while (true)
    {
      if (i == 0)
        break label163;
      AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(getString(R.string.app_name), getString(R.string.post_quit_question), getString(R.string.yes), getString(R.string.no));
      localAlertFragmentDialog.setTargetFragment(this, 0);
      localAlertFragmentDialog.show(getFragmentManager(), "quit");
      break;
      str2 = "";
      break label63;
      label141: if (this.mAttachmentRefs.size() > 0)
        i = 1;
      else
        i = 0;
    }
    label163: if (this.mIsFromPlusOne);
    for (OzActions localOzActions = OzActions.PLATFORM_CANCEL_SHARE_FROM_PLUSONE; ; localOzActions = OzActions.PLATFORM_CANCEL_SHARE)
    {
      FragmentActivity localFragmentActivity = getActivity();
      EsAnalytics.recordActionEvent(localFragmentActivity, this.mAccount, localOzActions, OzViews.getViewForLogging(localFragmentActivity), getExtrasForLogging());
      getActivity().finish();
      break;
    }
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
    removeLocationListener();
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    if (this.mResultAudience != null)
    {
      getView();
      updateAudienceUI$353d2340(this.mResultAudience);
      updatePostUI();
      this.mResultAudience = null;
    }
    if ((this.mPendingPostId != null) && (!EsService.isRequestPending(this.mPendingPostId.intValue())))
    {
      ServiceResult localServiceResult = EsService.removeResult(this.mPendingPostId.intValue());
      handlePostResult(this.mPendingPostId.intValue(), localServiceResult);
    }
    if ((this.mInsertCameraPhotoRequestId != null) && (!EsService.isRequestPending(this.mInsertCameraPhotoRequestId.intValue())))
    {
      EsService.removeResult(this.mInsertCameraPhotoRequestId.intValue());
      insertCameraPhoto(EsService.getLastCameraMediaLocation());
      this.mInsertCameraPhotoRequestId = null;
    }
    boolean bool;
    if (LocationController.isProviderEnabled(getActivity()))
    {
      if ((this.mLocationChecked) && (!EsAccountsData.hasSeenLocationDialog(getActivity(), this.mAccount)))
        getActivity().showDialog(30875012);
      if ((this.mLocationChecked) && (this.mLocation == null))
        addLocationListener();
      if ((this.mResultLocation != null) || (this.mRemoveLocation))
      {
        this.mLocation = this.mResultLocation;
        if (!this.mRemoveLocation)
        {
          bool = true;
          setLocationChecked(bool);
          this.mResultLocation = null;
          this.mRemoveLocation = false;
        }
      }
      else
      {
        label233: if ((this.mEmotiShareResult != null) || (this.mRemoveEmotiShare))
        {
          this.mEmotiShare = this.mEmotiShareResult;
          this.mRemoveEmotiShare = false;
          this.mEmotiShareResult = null;
          if (!this.mRemoveEmotiShare)
            break label348;
          FragmentActivity localFragmentActivity = getActivity();
          EsAnalytics.recordActionEvent(localFragmentActivity, this.mAccount, OzActions.EMOTISHARE_REMOVED, OzViews.getViewForLogging(localFragmentActivity), getExtrasForLogging());
          this.mCommentsView.setText(null);
        }
      }
    }
    while (true)
    {
      updateLocation(getView());
      updatePreviewContainer$3c7ec8c3();
      updatePostUI();
      updateResultMediaItems();
      return;
      bool = false;
      break;
      this.mResultLocation = null;
      this.mRemoveLocation = false;
      setLocationChecked(false);
      break label233;
      label348: this.mCommentsView.setText(this.mEmotiShare.getShareText());
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putString("activity_id", this.mActivityId);
    if (this.mLocation != null)
      paramBundle.putParcelable("location", this.mLocation);
    if (this.mProviderLocation != null)
      paramBundle.putParcelable("prov_location", this.mProviderLocation);
    if (this.mPendingPostId != null)
      paramBundle.putInt("pending_request_id", this.mPendingPostId.intValue());
    if (this.mPreviewResult != null)
      paramBundle.putParcelable("preview_result", this.mPreviewResult);
    if (this.mEmotiShareResult != null)
      paramBundle.putParcelable("emotishare_result", this.mEmotiShareResult);
    if (this.mEmotiShare != null)
      paramBundle.putParcelable("emotishare", this.mEmotiShare);
    if (this.mApiaryApiInfo != null)
      paramBundle.putSerializable("api_info", this.mApiaryApiInfo);
    if (this.mFooterMessage != null)
      paramBundle.putSerializable("footer", this.mFooterMessage);
    if (this.mAttachmentRefs != null)
      paramBundle.putParcelableArrayList("l_attachments", this.mAttachmentRefs);
    paramBundle.putBoolean("loading_attachments", this.mLoadingMediaAttachments);
    if (this.mUrl != null)
      paramBundle.putString("url", this.mUrl);
    if (this.mContentDeepLinkId != null)
      paramBundle.putString("content_deep_link_id", this.mContentDeepLinkId);
    if (this.mContentDeepLinkMetadata != null)
      paramBundle.putBundle("content_deep_link_metadata", this.mContentDeepLinkMetadata);
    if (this.mCallToAction != null)
      paramBundle.putParcelable("call_to_action", this.mCallToAction);
    if (this.mOriginalText != null)
      paramBundle.putString("text", this.mOriginalText);
    if (this.mInsertCameraPhotoRequestId != null)
      paramBundle.putInt("insert_camera_photo_req_id", this.mInsertCameraPhotoRequestId.intValue());
    if (this.mIsFromPlusOne)
      paramBundle.putBoolean("is_from_plusone", true);
    if (this.mPublicCircle != null)
      paramBundle.putParcelable("public_circle", this.mPublicCircle);
    if (this.mDomainCircle != null)
      paramBundle.putParcelable("domain_circle", this.mDomainCircle);
    if (this.mYourCircles != null)
      paramBundle.putParcelable("your_circles", this.mYourCircles);
    if (this.mSavedDefaultAudience != null)
      paramBundle.putParcelable("saved_default_audience", this.mSavedDefaultAudience);
    if (this.mDefaultAudience != null)
      paramBundle.putParcelable("default_audience", this.mDefaultAudience);
    if (this.mHistoryAudienceArray != null)
      paramBundle.putParcelableArrayList("audience_history", this.mHistoryAudienceArray);
  }

  public final boolean post()
  {
    boolean bool1;
    if ((this.mPendingPostId != null) || (this.mLoadingUrlPreview))
    {
      bool1 = false;
      return bool1;
    }
    FragmentActivity localFragmentActivity1 = getActivity();
    Bundle localBundle1 = getExtrasForLogging();
    if (this.mIsFromPlusOne);
    OzViews localOzViews;
    AudienceData localAudienceData;
    Editable localEditable;
    for (OzActions localOzActions1 = OzActions.PLATFORM_CONFIRM_SHARE_FROM_PLUSONE; ; localOzActions1 = OzActions.PLATFORM_CONFIRM_SHARE)
    {
      localOzViews = OzViews.getViewForLogging(localFragmentActivity1);
      EsAnalytics.recordActionEvent(localFragmentActivity1, this.mAccount, localOzActions1, localOzViews, localBundle1);
      SoftInput.hide(this.mCommentsView);
      localAudienceData = this.mAudienceView.getAudience();
      localEditable = this.mCommentsView.getText();
      if (!PeopleUtils.isEmpty(localAudienceData))
        break label109;
      launchAclPicker();
      bool1 = false;
      break;
    }
    label109: if ((localAudienceData.getSquareTargetCount() != 0) && (localAudienceData.getSquareTarget(0).getSquareStreamId() == null));
    for (int i = 1; ; i = 0)
    {
      if (i == 0)
        break label187;
      SquareTargetData localSquareTargetData = localAudienceData.getSquareTarget(0);
      launchActivity(Intents.getSelectSquareCategoryActivityIntent(getActivity(), this.mAccount, localSquareTargetData.getSquareName(), localSquareTargetData.getSquareId(), localSquareTargetData.getSquareName()), 2);
      bool1 = false;
      break;
    }
    label187: int j;
    label197: int k;
    label207: int m;
    label217: int n;
    label230: int i1;
    if (this.mEmotiShare != null)
    {
      j = 1;
      if (this.mUrl == null)
        break label312;
      k = 1;
      if (this.mContentDeepLinkId == null)
        break label318;
      m = 1;
      if (localEditable.length() <= 0)
        break label324;
      n = 1;
      if (this.mLocation == null)
        break label330;
      i1 = 1;
      label240: if (this.mAttachmentRefs.isEmpty())
        break label336;
    }
    label312: label318: label324: label330: label336: for (int i2 = 1; ; i2 = 0)
    {
      if ((j != 0) || (k != 0) || (m != 0) || (n != 0) || (i1 != 0) || (i2 != 0))
        break label342;
      Toast.makeText(localFragmentActivity1, getResources().getString(R.string.share_body_empty), 0).show();
      bool1 = false;
      break;
      j = 0;
      break label197;
      k = 0;
      break label207;
      m = 0;
      break label217;
      n = 0;
      break label230;
      i1 = 0;
      break label240;
    }
    label342: ProgressFragmentDialog.newInstance(null, getString(R.string.post_operation_pending), false).show(getFragmentManager(), "req_pending");
    if (i2 != 0)
      EsAnalytics.recordActionEvent(localFragmentActivity1, this.mAccount, OzActions.PLATFORM_SHARE_POST_WITH_ATTACHMENT, localOzViews, localBundle1);
    if (n != 0)
      EsAnalytics.recordActionEvent(localFragmentActivity1, this.mAccount, OzActions.PLATFORM_SHARE_POST_WITH_COMMENT, localOzViews, localBundle1);
    if (i1 != 0)
      EsAnalytics.recordActionEvent(localFragmentActivity1, this.mAccount, OzActions.PLATFORM_SHARE_POST_WITH_LOCATION, localOzViews, localBundle1);
    if (k != 0)
      EsAnalytics.recordActionEvent(localFragmentActivity1, this.mAccount, OzActions.PLATFORM_SHARE_POST_WITH_URL, localOzViews, localBundle1);
    if (m != 0)
      EsAnalytics.recordActionEvent(localFragmentActivity1, this.mAccount, OzActions.PLATFORM_SHARE_POST_WITH_DEEP_LINK, localOzViews, localBundle1);
    String str1 = ApiUtils.buildPostableString$6d7f0b14(localEditable);
    OzActions localOzActions2;
    label501: AnalyticsInfo localAnalyticsInfo;
    BirthdayData localBirthdayData;
    label686: FragmentActivity localFragmentActivity3;
    EsAccount localEsAccount;
    ApiaryApiInfo localApiaryApiInfo;
    ApiaryActivity localApiaryActivity;
    String str2;
    ArrayList localArrayList;
    DbLocation localDbLocation;
    String str3;
    boolean bool2;
    if (j != 0)
    {
      if ((n != 0) && (TextUtils.equals(str1, this.mEmotiShare.getShareText())))
      {
        localOzActions2 = OzActions.EMOTISHARE_TEXT_UNMODIFIED;
        EsAnalytics.recordActionEvent(localFragmentActivity1, this.mAccount, localOzActions2, localOzViews, localBundle1);
        EsAnalytics.recordActionEvent(localFragmentActivity1, this.mAccount, OzActions.PLATFORM_CONFIRM_SHARE, localOzViews, localBundle1);
      }
    }
    else
    {
      localAnalyticsInfo = new AnalyticsInfo(OzViews.SHARE, OzViews.PLATFORM_THIRD_PARTY_APP, System.currentTimeMillis(), PlatformContractUtils.getCallingPackageAnalytics(this.mApiaryApiInfo));
      Bundle localBundle2 = getExtrasForLogging();
      FragmentActivity localFragmentActivity2 = getActivity();
      if (!TextUtils.isEmpty(str1))
        EsAnalytics.recordActionEvent(localFragmentActivity2, this.mAccount, OzActions.PLATFORM_SHARE_COMMENT_ADDED, OzViews.getViewForLogging(localFragmentActivity2), localBundle2);
      if (localAudienceData.getCircleCount() > 0)
        EsAnalytics.recordActionEvent(localFragmentActivity2, this.mAccount, OzActions.PLATFORM_CIRCLES_SHARE_ACL_ADDED, OzViews.getViewForLogging(localFragmentActivity2), localBundle2);
      if (localAudienceData.getUserCount() > 0)
        EsAnalytics.recordActionEvent(localFragmentActivity2, this.mAccount, OzActions.PLATFORM_PEOPLE_SHARE_ACL_ADDED, OzViews.getViewForLogging(localFragmentActivity2), localBundle2);
      if (!"com.google.android.apps.plus.GOOGLE_BIRTHDAY_POST".equals(localFragmentActivity1.getIntent().getAction()))
        break label823;
      Bundle localBundle3 = getArguments();
      if (localBundle3 == null)
        break label817;
      localBirthdayData = (BirthdayData)localBundle3.getParcelable("birthday_data");
      localFragmentActivity3 = getActivity();
      localEsAccount = this.mAccount;
      localApiaryApiInfo = this.mApiaryApiInfo;
      localApiaryActivity = this.mPreviewResult;
      str2 = this.mActivityId;
      localArrayList = this.mAttachmentRefs;
      localDbLocation = this.mLocation;
      str3 = this.mContentDeepLinkId;
      if (!"com.google.android.apps.plus.GOOGLE_BIRTHDAY_POST".equals(getActivity().getIntent().getAction()))
        break label829;
      bool2 = false;
      label756: if (this.mEmotiShare != null)
        break label835;
    }
    label817: label823: label829: label835: for (DbEmbedEmotishare localDbEmbedEmotishare = null; ; localDbEmbedEmotishare = this.mEmotiShare.getEmbed())
    {
      this.mPendingPostId = Integer.valueOf(EsService.postActivity(localFragmentActivity3, localEsAccount, localAnalyticsInfo, localApiaryApiInfo, localApiaryActivity, localAudienceData, str2, str1, localArrayList, localDbLocation, str3, bool2, localBirthdayData, localDbEmbedEmotishare));
      bool1 = true;
      break;
      localOzActions2 = OzActions.EMOTISHARE_TEXT_MODIFIED;
      break label501;
      localBirthdayData = null;
      break label686;
      localBirthdayData = null;
      break label686;
      bool2 = true;
      break label756;
    }
  }

  public final void setLocationChecked(boolean paramBoolean)
  {
    this.mLocationChecked = paramBoolean;
    boolean bool = this.mLocationChecked;
    FragmentActivity localFragmentActivity = getActivity();
    if (bool)
      if (!LocationController.isProviderEnabled(localFragmentActivity))
        localFragmentActivity.showDialog(29341608);
    while (true)
    {
      updateLocation(getView());
      updatePostUI();
      return;
      if (isResumed())
      {
        addLocationListener();
        if (!EsAccountsData.hasSeenLocationDialog(localFragmentActivity, this.mAccount))
        {
          localFragmentActivity.showDialog(30875012);
          continue;
          removeLocationListener();
          this.mLocation = null;
          this.mProviderLocation = null;
        }
      }
    }
  }

  private static abstract interface AccountStatusQuery
  {
    public static final String[] PROJECTION = { "audience_data", "audience_history" };
  }

  private static abstract interface CirclesQuery
  {
    public static final String[] PROJECTION = { "_id", "circle_name", "circle_id", "type", "contact_count" };
  }

  private final class CursorLoaderCallbacks
    implements LoaderManager.LoaderCallbacks<Cursor>
  {
    private CursorLoaderCallbacks()
    {
    }

    public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
    {
      Object localObject1;
      switch (paramInt)
      {
      default:
        localObject1 = null;
      case 1:
      case 2:
        while (true)
        {
          return localObject1;
          localObject1 = new CircleListLoader(PostFragment.this.getActivity(), PostFragment.this.mAccount, 13, PostFragment.CirclesQuery.PROJECTION);
          continue;
          localObject1 = new EsCursorLoader(PostFragment.this.getActivity(), EsProvider.appendAccountParameter(EsProvider.ACCOUNT_STATUS_URI, PostFragment.this.mAccount), PostFragment.AccountStatusQuery.PROJECTION, null, null, null);
        }
      case 3:
      }
      PostFragment.PreviewCursorLoader localPreviewCursorLoader = new PostFragment.PreviewCursorLoader(PostFragment.this.getActivity());
      localPreviewCursorLoader.setUri(EsApiProvider.makePreviewUri(PostFragment.this.mApiaryApiInfo));
      PreviewRequestData localPreviewRequestData = new PreviewRequestData(PostFragment.this.mUrl, PostFragment.this.mCallToAction);
      String[] arrayOfString = new String[1];
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.put(localPreviewRequestData.uri.toString());
      Object localObject2;
      label213: Object localObject3;
      if (localPreviewRequestData.callToAction != null)
      {
        if (localPreviewRequestData.callToAction.mLabel == null)
          break label300;
        localObject2 = localPreviewRequestData.callToAction.mLabel;
        localJSONArray.put(localObject2);
        if (localPreviewRequestData.callToAction.mUrl == null)
          break label308;
        localObject3 = localPreviewRequestData.callToAction.mUrl;
        label242: localJSONArray.put(localObject3);
        if (localPreviewRequestData.callToAction.mDeepLinkId == null)
          break label316;
      }
      label300: label308: label316: for (Object localObject4 = localPreviewRequestData.callToAction.mDeepLinkId; ; localObject4 = JSONObject.NULL)
      {
        localJSONArray.put(localObject4);
        arrayOfString[0] = localJSONArray.toString();
        localPreviewCursorLoader.setSelectionArgs(arrayOfString);
        localObject1 = localPreviewCursorLoader;
        break;
        localObject2 = JSONObject.NULL;
        break label213;
        localObject3 = JSONObject.NULL;
        break label242;
      }
    }

    public final void onLoaderReset(Loader<Cursor> paramLoader)
    {
      switch (paramLoader.getId())
      {
      default:
      case 3:
      }
      while (true)
      {
        return;
        PostFragment.this.mLoadingView.setVisibility(8);
      }
    }
  }

  private static class LinkPreviewView extends OneUpLinkView
  {
    private static boolean sLinkPreviewViewInitialized;
    private static int sMinExposureLand;
    private static int sMinExposurePort;

    public LinkPreviewView(Context paramContext)
    {
      super();
      if (!sLinkPreviewViewInitialized)
      {
        sLinkPreviewViewInitialized = true;
        Resources localResources = paramContext.getResources();
        sMinExposureLand = localResources.getDimensionPixelOffset(R.dimen.share_preview_margin_top_landscape);
        sMinExposurePort = localResources.getDimensionPixelOffset(R.dimen.share_preview_margin_top_portrait);
      }
    }

    protected final int getMinExposureLand()
    {
      return sMinExposureLand;
    }

    protected final int getMinExposurePort()
    {
      return sMinExposurePort;
    }
  }

  private final class MediaGallery
  {
    private ViewGroup mGalleryView;
    private ArrayList<MediaRef> mImages = new ArrayList();
    private final LayoutInflater mLayoutInflater;

    public MediaGallery(ArrayList<MediaRef> paramViewGroup, ViewGroup arg3)
    {
      Object localObject2;
      this.mGalleryView = localObject2;
      this.mLayoutInflater = ((LayoutInflater)paramViewGroup.getSystemService("layout_inflater"));
      Object localObject1;
      if (localObject1 != null)
      {
        int i = localObject1.size();
        for (int j = 0; j < i; j++)
          add((MediaRef)localObject1.get(j));
      }
    }

    public final void add(final MediaRef paramMediaRef)
    {
      this.mImages.add(paramMediaRef);
      View localView = this.mLayoutInflater.inflate(R.layout.compose_gallery_image_container, null);
      AlbumColumnGridItemView localAlbumColumnGridItemView = (AlbumColumnGridItemView)localView.findViewById(R.id.image);
      localAlbumColumnGridItemView.setTag(paramMediaRef);
      localAlbumColumnGridItemView.setMediaRef(paramMediaRef);
      localAlbumColumnGridItemView.setOnClickListener(PostFragment.this.onClickListener);
      ((ImageButton)localView.findViewById(R.id.remove_image_button)).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          PostFragment.this.hideAclOverlay();
          paramAnonymousView.setOnClickListener(null);
          PostFragment.this.removeFromMediaGallery(paramMediaRef);
          PostFragment.this.updatePostUI();
        }
      });
      this.mGalleryView.addView(localView);
    }
  }

  private static final class MediaRefLoader extends AsyncTaskLoader<ArrayList<MediaRef>>
  {
    private final EsAccount mAccount;
    private final ArrayList<MediaRef> mLoadedList = new ArrayList();
    private final ArrayList<MediaRef> mMediaRefList;

    public MediaRefLoader(Context paramContext, EsAccount paramEsAccount, ArrayList<MediaRef> paramArrayList)
    {
      super();
      this.mMediaRefList = paramArrayList;
      this.mAccount = paramEsAccount;
    }

    protected final void onStartLoading()
    {
      if (this.mLoadedList.size() == 0)
        forceLoad();
    }
  }

  private final class PostLocationListener
    implements LocationListener
  {
    private PostLocationListener()
    {
    }

    public final void onLocationChanged(Location paramLocation)
    {
      PostFragment.this.removeLocationListener();
      if (PostFragment.this.mLocation != null)
        return;
      PostFragment.access$2502(PostFragment.this, paramLocation);
      PostFragment localPostFragment = PostFragment.this;
      if (PostFragment.this.getCityLevelLocationPreference());
      for (DbLocation localDbLocation = LocationController.getCityLevelLocation(paramLocation); ; localDbLocation = LocationController.getStreetLevelLocation(paramLocation))
      {
        PostFragment.access$1402(localPostFragment, localDbLocation);
        PostFragment.this.updatePostUI();
        PostFragment.this.updateLocation(PostFragment.this.getView());
        break;
      }
    }

    public final void onProviderDisabled(String paramString)
    {
    }

    public final void onProviderEnabled(String paramString)
    {
    }

    public final void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
    {
    }
  }

  private static final class PreviewCursorLoader extends EsCursorLoader
  {
    private boolean mCachedData;

    public PreviewCursorLoader(Context paramContext)
    {
      super();
    }

    public final Cursor esLoadInBackground()
    {
      this.mCachedData = false;
      return super.esLoadInBackground();
    }

    public final boolean isCachedData()
    {
      return this.mCachedData;
    }

    public final void setCachedData(boolean paramBoolean)
    {
      this.mCachedData = true;
    }
  }

  private final class ServiceListener extends EsServiceListener
  {
    private ServiceListener()
    {
    }

    public final void onInsertCameraPhotoComplete$6a63df5(int paramInt, ServiceResult paramServiceResult)
    {
      if ((PostFragment.this.mInsertCameraPhotoRequestId != null) && (PostFragment.this.mInsertCameraPhotoRequestId.intValue() == paramInt))
      {
        PostFragment.this.insertCameraPhoto(EsService.getLastCameraMediaLocation());
        PostFragment.access$102(PostFragment.this, null);
      }
    }

    public final void onPostActivityResult(int paramInt, ServiceResult paramServiceResult)
    {
      PostFragment.this.handlePostResult(paramInt, paramServiceResult);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PostFragment
 * JD-Core Version:    0.6.2
 */