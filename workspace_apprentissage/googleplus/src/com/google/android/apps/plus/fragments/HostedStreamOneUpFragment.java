package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.ApiUtils;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.api.OzServerException;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.DbEmbedDeepLink;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.DbPlusOneData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsAnalyticsData;
import com.google.android.apps.plus.content.EsDeepLinkInstallsData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.phone.GoogleFeedback;
import com.google.android.apps.plus.phone.HostedFragment;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.Intents.PhotoViewIntentBuilder;
import com.google.android.apps.plus.phone.PhotoOneUpAnimationController;
import com.google.android.apps.plus.phone.StreamOneUpActivity.OnScreenListener;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.MapUtils;
import com.google.android.apps.plus.util.PlayStoreInstaller;
import com.google.android.apps.plus.util.SoftInput;
import com.google.android.apps.plus.views.ClickableButton;
import com.google.android.apps.plus.views.ClickableButton.ClickableButtonListener;
import com.google.android.apps.plus.views.EsImageView;
import com.google.android.apps.plus.views.ExpandingScrollView;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.LinearLayoutWithLayoutNotifications;
import com.google.android.apps.plus.views.LinearLayoutWithLayoutNotifications.LayoutListener;
import com.google.android.apps.plus.views.MentionMultiAutoCompleteTextView;
import com.google.android.apps.plus.views.OneUpBaseView;
import com.google.android.apps.plus.views.OneUpBaseView.OnMeasuredListener;
import com.google.android.apps.plus.views.OneUpLinkView;
import com.google.android.apps.plus.views.OneUpLinkView.BackgroundViewLoadedListener;
import com.google.android.apps.plus.views.OneUpListener;
import com.google.android.apps.plus.views.OneUpTouchHandler;
import com.google.android.apps.plus.views.PhotoHeaderView;
import com.google.android.apps.plus.views.PhotoHeaderView.OnImageListener;
import com.google.android.apps.plus.views.StreamOneUpCommentView;
import com.google.android.apps.plus.views.StreamOneUpHangoutView;
import com.google.android.apps.plus.views.StreamOneUpListView;
import com.google.android.apps.plus.views.StreamOneUpSkyjamView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class HostedStreamOneUpFragment extends HostedFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, AdapterView.OnItemClickListener, AlertFragmentDialog.AlertDialogListener, StreamOneUpActivity.OnScreenListener, ClickableButton.ClickableButtonListener, LinearLayoutWithLayoutNotifications.LayoutListener, OneUpBaseView.OnMeasuredListener, OneUpLinkView.BackgroundViewLoadedListener, OneUpListener, PhotoHeaderView.OnImageListener
{
  private static int sAvatarMarginTop;
  private static int sMaxWidth;
  private static int sMinExposureLand;
  private static int sMinExposurePort;
  private static boolean sResourcesLoaded;
  private EsAccount mAccount;
  private PhotoOneUpAnimationController mActionBarAnimator;
  private boolean mActivityDataNotFound;
  private String mActivityId;
  private Integer mActivityRequestId;
  private StreamOneUpAdapter mAdapter;
  private String mAlbumId;
  private DbEmbedDeepLink mAppInviteData;
  private AudienceData mAudienceData;
  private String mAuthorId;
  private boolean mAutoPlay;
  private String mBackgroundLinkUrl;
  private MediaRef mBackgroundRef;
  private StreamOneUpCallbacks mCallback;
  private View mCommentButton;
  private MentionMultiAutoCompleteTextView mCommentText;
  private String mCreationSource;
  private String mCreationSourceId;
  private DbEmbedDeepLink mDeepLinkData;
  private String mDeepLinkLabel;
  private String mEditableText;
  private HashSet<String> mFlaggedComments;
  private LinearLayoutWithLayoutNotifications mFooter;
  private PhotoOneUpAnimationController mFooterAnimator;
  private boolean mFullScreen;
  private boolean mGetActivityComplete;
  private PhotoHeaderView mImageView;
  private boolean mIsActivityMuted;
  private boolean mIsActivityPublic;
  private boolean mIsActivityResharable;
  private boolean mIsAlbum;
  private boolean mIsGraySpam;
  private Boolean mIsMyActivity;
  private boolean mIsSquarePost;
  private String mLinkTitle;
  private String mLinkUrl;
  private OneUpLinkView mLinkView;
  private PhotoOneUpAnimationController mListAnimator;
  private View mListParent;
  private StreamOneUpListView mListView;
  private DbLocation mLocationData;
  private boolean mMuteProcessed;
  private int mOperationType = 0;
  private Integer mPendingRequestId;
  private Pair<String, Integer> mPlusOnedByData;
  private boolean mReadProcessed;
  private boolean mReshare;
  private final ServiceListener mServiceListener = new ServiceListener((byte)0);
  private String mSourceAuthorId;
  private String mSourcePackageName;
  private String mSquareId;
  private boolean mStageMediaLoaded;
  private TextWatcher mTextWatcher;
  private OneUpTouchHandler mTouchHandler;
  private boolean mUpdateActionBar;
  private boolean mViewerIsSquareAdmin;

  private static void adjustActionBarMargins(HostActionBar paramHostActionBar, boolean paramBoolean)
  {
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)paramHostActionBar.getLayoutParams();
    int i = localLayoutParams.leftMargin;
    int j = localLayoutParams.rightMargin;
    if (paramBoolean);
    for (int k = -paramHostActionBar.getHeight(); ; k = 0)
    {
      localLayoutParams.setMargins(i, k, j, localLayoutParams.bottomMargin);
      paramHostActionBar.setLayoutParams(localLayoutParams);
      return;
    }
  }

  private boolean bindStageLink(View paramView)
  {
    if ((this.mBackgroundRef == null) || (TextUtils.isEmpty(this.mBackgroundRef.getUrl())));
    View localView1;
    boolean bool;
    for (MediaRef localMediaRef = null; ; localMediaRef = this.mBackgroundRef)
    {
      localView1 = paramView.findViewById(R.id.stage);
      if ((localMediaRef != null) || (this.mDeepLinkLabel != null))
        break;
      bool = false;
      if (localView1 != null)
        localView1.setVisibility(8);
      return bool;
    }
    View localView2;
    int i;
    if (localView1 == null)
    {
      localView2 = ((ViewStub)paramView.findViewById(R.id.stage_link)).inflate();
      View localView3 = localView2.findViewById(R.id.loading);
      if (this.mGetActivityComplete)
        break label226;
      i = 0;
      label104: localView3.setVisibility(i);
      this.mLinkView = ((OneUpLinkView)localView2.findViewById(R.id.background));
      if ((localMediaRef != null) && (localMediaRef.getType() != MediaRef.MediaType.IMAGE))
        break label233;
    }
    label226: label233: for (int j = 3; ; j = 2)
    {
      this.mLinkView.init(localMediaRef, j, this, this.mLinkTitle, this.mDeepLinkLabel, this, this.mLinkUrl);
      this.mLinkView.setOnClickListener(this);
      if (localMediaRef == null)
        onBackgroundViewLoaded(this.mLinkView);
      this.mTouchHandler.setBackground(this.mLinkView);
      ((ExpandingScrollView)this.mListParent.findViewById(R.id.list_expander)).setAlwaysExpanded(false);
      localView2.invalidate();
      bool = true;
      break;
      i = 8;
      break label104;
    }
  }

  private boolean bindStageMedia(View paramView)
  {
    if (this.mBackgroundRef == null);
    View localView1;
    boolean bool;
    for (String str = null; ; str = this.mBackgroundRef.getUrl())
    {
      localView1 = paramView.findViewById(R.id.stage);
      if (str != null)
        break;
      bool = false;
      if (localView1 != null)
        localView1.setVisibility(8);
      return bool;
    }
    View localView2;
    View localView3;
    if (localView1 == null)
    {
      localView2 = ((ViewStub)paramView.findViewById(R.id.stage_media)).inflate();
      localView3 = localView2.findViewById(R.id.loading);
      if (this.mGetActivityComplete)
        break label254;
    }
    label254: for (int i = 0; ; i = 8)
    {
      localView3.setVisibility(i);
      this.mImageView = ((PhotoHeaderView)localView2.findViewById(R.id.background));
      this.mImageView.setOnImageListener(this);
      this.mImageView.init(new MediaRef(this.mBackgroundRef.getOwnerGaiaId(), this.mBackgroundRef.getPhotoId(), this.mBackgroundRef.getUrl(), null, this.mBackgroundRef.getType()), getResources().getColor(R.color.stream_one_up_background));
      this.mImageView.doAnimate(true);
      this.mImageView.setOnClickListener(this);
      this.mImageView.enableImageTransforms(true);
      this.mTouchHandler.setBackground(this.mImageView);
      ExpandingScrollView localExpandingScrollView = (ExpandingScrollView)this.mListParent.findViewById(R.id.list_expander);
      localExpandingScrollView.setAlwaysExpanded(false);
      localExpandingScrollView.setMinimumExposure(sMinExposureLand, sMinExposurePort);
      localExpandingScrollView.setBigBounce(true);
      localView2.invalidate();
      bool = true;
      break;
    }
  }

  private void doReportComment(boolean paramBoolean1, String paramString, boolean paramBoolean2)
  {
    Bundle localBundle = EsAnalyticsData.createExtras("extra_comment_id", paramString);
    recordUserAction(OzActions.ONE_UP_REPORT_ABUSE_COMMENT, localBundle);
    String str1;
    if (paramBoolean2)
    {
      str1 = getString(R.string.stream_one_up_comment_undo_report_dialog_title);
      if (!paramBoolean2)
        break label135;
    }
    label135: for (String str2 = getString(R.string.stream_one_up_comment_undo_report_dialog_question); ; str2 = getString(R.string.stream_one_up_comment_report_dialog_question))
    {
      AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(str1, str2, getString(R.string.ok), getString(R.string.cancel));
      localAlertFragmentDialog.setTargetFragment(this, 0);
      localAlertFragmentDialog.getArguments().putString("comment_id", paramString);
      localAlertFragmentDialog.getArguments().putBoolean("delete", paramBoolean1);
      localAlertFragmentDialog.getArguments().putBoolean("is_undo", paramBoolean2);
      localAlertFragmentDialog.show(getFragmentManager(), "hsouf_report_comment");
      return;
      str1 = getString(R.string.stream_one_up_comment_report_dialog_title);
      break;
    }
  }

  private void launchDeepLink$654e823a(List<String> paramList, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    Bundle localBundle = EsAnalyticsData.createExtras("extra_creation_source_id", this.mCreationSourceId);
    int i = 0;
    PackageManager localPackageManager = getSafeContext().getPackageManager();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      String str5 = (String)localIterator.next();
      if (PlayStoreInstaller.isPackageInstalled(localPackageManager, str5))
      {
        i = 1;
        String str6;
        if (paramBoolean)
          str6 = "stream_interactive_post";
        Intent localIntent2;
        while (true)
        {
          localIntent2 = PlayStoreInstaller.getContinueIntent(localPackageManager, str5, paramString1, str6);
          if (localIntent2 == null)
            break;
          try
          {
            startActivity(localIntent2);
            Context localContext5 = getSafeContext();
            EsAccount localEsAccount5 = this.mAccount;
            if (paramBoolean);
            for (OzActions localOzActions4 = OzActions.CALL_TO_ACTION_CONSUMED; ; localOzActions4 = OzActions.DEEP_LINK_CONSUMED)
            {
              EsAnalytics.recordActionEvent(localContext5, localEsAccount5, localOzActions4, OzViews.ACTIVITY, localBundle);
              return;
              str6 = "stream";
              break;
            }
          }
          catch (ActivityNotFoundException localActivityNotFoundException)
          {
          }
        }
        if (EsLog.isLoggable("StreamOneUp", 5))
          Log.w("StreamOneUp", "Failed to start deep linked Activity with " + localIntent2);
      }
    }
    if (!TextUtils.isEmpty(paramString2))
    {
      if (i != 0)
      {
        onUrlClick(paramString2);
        Context localContext4 = getSafeContext();
        EsAccount localEsAccount4 = this.mAccount;
        if (paramBoolean);
        for (OzActions localOzActions3 = OzActions.CALL_TO_ACTION_UNRESOLVED; ; localOzActions3 = OzActions.DEEP_LINK_UNRESOLVED)
        {
          EsAnalytics.recordActionEvent(localContext4, localEsAccount4, localOzActions3, OzViews.ACTIVITY, localBundle);
          break;
        }
      }
      if (!PlayStoreInstaller.isPlayStoreInstalled(localPackageManager))
      {
        onUrlClick(paramString2);
        Context localContext3 = getSafeContext();
        EsAccount localEsAccount3 = this.mAccount;
        if (paramBoolean);
        for (OzActions localOzActions2 = OzActions.CALL_TO_ACTION_CLICKED_BUT_PLAY_STORE_NOT_INSTALLED; ; localOzActions2 = OzActions.DEEP_LINK_CLICKED_BUT_PLAY_STORE_NOT_INSTALLED)
        {
          EsAnalytics.recordActionEvent(localContext3, localEsAccount3, localOzActions2, OzViews.ACTIVITY, localBundle);
          break;
        }
      }
    }
    this.mSourcePackageName = ((String)paramList.get(0));
    this.mSourceAuthorId = paramString3;
    Intent localIntent1 = PlayStoreInstaller.getInstallIntent(this.mSourcePackageName);
    int j;
    label354: String str4;
    label406: Context localContext2;
    EsAccount localEsAccount2;
    if (paramBoolean)
    {
      j = 2;
      startActivityForResult(localIntent1, j);
      Context localContext1 = getActivity().getApplicationContext();
      EsAccount localEsAccount1 = EsAccountsData.getActiveAccount(localContext1);
      String str1 = this.mSourcePackageName;
      String str2 = this.mActivityId;
      String str3 = this.mSourceAuthorId;
      if (!paramBoolean)
        break label466;
      str4 = "stream_install_interactive_post";
      EsDeepLinkInstallsData.insert(localContext1, localEsAccount1, str1, str2, str3, str4);
      localContext2 = getSafeContext();
      localEsAccount2 = this.mAccount;
      if (!paramBoolean)
        break label474;
    }
    label466: label474: for (OzActions localOzActions1 = OzActions.CALL_TO_ACTION_INSTALL_ACCEPTED; ; localOzActions1 = OzActions.DEEP_LINK_INSTALL_ACCEPTED)
    {
      EsAnalytics.recordActionEvent(localContext2, localEsAccount2, localOzActions1, OzViews.ACTIVITY, localBundle);
      break;
      j = 1;
      break label354;
      str4 = "stream_install";
      break label406;
    }
  }

  private void onUrlClick(String paramString)
  {
    Context localContext = getSafeContext();
    String str2;
    String str3;
    if (paramString.startsWith("acl:"))
      if (this.mAudienceData == null)
        if (this.mPendingRequestId == null)
        {
          str2 = this.mAdapter.getAclText();
          if (!TextUtils.equals(str2, localContext.getString(R.string.acl_public)))
            break label93;
          str3 = getString(R.string.acl_description_public);
          if (str3 != null)
            AlertFragmentDialog.newInstance(str2, str3, getString(R.string.ok), null).show(getFragmentManager(), "hsouf_audience");
        }
    while (true)
    {
      return;
      label93: if (TextUtils.equals(str2, localContext.getString(R.string.acl_private_contacts)))
      {
        str3 = getString(R.string.acl_description_private_contacts);
        break;
      }
      if (TextUtils.equals(str2, localContext.getString(R.string.acl_extended_network)))
      {
        str3 = getString(R.string.acl_description_extended_network);
        break;
      }
      if (!TextUtils.equals(str2, localContext.getString(R.string.acl_limited)))
      {
        str3 = getString(R.string.acl_description_domain, new Object[] { str2 });
        break;
      }
      this.mPendingRequestId = Integer.valueOf(EsService.getActivityAudience(localContext, this.mAccount, this.mActivityId));
      showProgressDialog(48);
      str3 = null;
      break;
      showAudience(this.mAudienceData);
      continue;
      if (paramString.startsWith("+1:"))
      {
        String[] arrayOfString = paramString.split(":");
        if ((arrayOfString != null) && (arrayOfString.length == 3))
          showPlusOnePeople(arrayOfString[1], Integer.valueOf(arrayOfString[2]).intValue());
      }
      else if (paramString.startsWith("https://plus.google.com/s/%23"))
      {
        String str1 = "#" + paramString.substring(29);
        startActivity(Intents.getPostSearchActivityIntent(localContext, this.mAccount, str1));
      }
      else
      {
        if (Intents.isProfileUrl(paramString))
        {
          Bundle localBundle = EsAnalyticsData.createExtras("extra_gaia_id", Intents.getPersonIdFromProfileUrl(paramString));
          recordUserAction(OzActions.ONE_UP_SELECT_PERSON, localBundle);
        }
        Intents.viewContent(getActivity(), this.mAccount, paramString, this.mCreationSourceId);
      }
    }
  }

  private void showAudience(AudienceData paramAudienceData)
  {
    if (EsLog.isLoggable("StreamOneUp", 3))
    {
      Log.d("StreamOneUp", "Hidden count: " + paramAudienceData.getHiddenUserCount());
      Log.d("StreamOneUp", "Audience users: " + paramAudienceData.getUserCount());
      for (PersonData localPersonData : paramAudienceData.getUsers())
        Log.d("StreamOneUp", "Users: " + localPersonData.getName());
    }
    String str = this.mAdapter.getAclText();
    PeopleListDialogFragment localPeopleListDialogFragment = new PeopleListDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("account", this.mAccount);
    localBundle.putParcelable("audience", paramAudienceData);
    localBundle.putString("people_list_title", str);
    localPeopleListDialogFragment.setArguments(localBundle);
    localPeopleListDialogFragment.show(getFragmentManager(), "hsouf_audience");
  }

  private void showPlusOnePeople(String paramString, int paramInt)
  {
    PlusOnePeopleFragment localPlusOnePeopleFragment = new PlusOnePeopleFragment();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("account", this.mAccount);
    localBundle.putString("plus_one_id", paramString);
    localBundle.putInt("total_plus_ones", paramInt);
    localPlusOnePeopleFragment.setArguments(localBundle);
    localPlusOnePeopleFragment.show(getFragmentManager(), "hsouf_plus_ones");
  }

  private void showProgressDialog(int paramInt)
  {
    this.mOperationType = paramInt;
    if (paramInt == 48);
    for (int i = R.string.loading; ; i = R.string.post_operation_pending)
    {
      ProgressFragmentDialog.newInstance(null, getString(i), false).show(getFragmentManager(), "hsouf_pending");
      return;
    }
  }

  private void updateLoadingSpinner(View paramView)
  {
    int i = 8;
    View localView;
    if (paramView != null)
    {
      localView = paramView.findViewById(R.id.loading);
      if (localView != null)
      {
        if (paramView.findViewById(R.id.stage) == null)
          break label47;
        if (!this.mStageMediaLoaded)
          break label42;
      }
    }
    while (true)
    {
      localView.setVisibility(i);
      return;
      label42: i = 0;
    }
    label47: if (this.mGetActivityComplete);
    while (true)
    {
      localView.setVisibility(i);
      break;
      i = 0;
    }
  }

  public final void enableImageTransforms(boolean paramBoolean)
  {
    if (this.mImageView != null)
      this.mImageView.enableImageTransforms(paramBoolean);
  }

  public final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.ACTIVITY;
  }

  public final void onActionButtonClicked(int paramInt)
  {
    Intent localIntent;
    if (paramInt == 0)
    {
      localIntent = Intents.getReshareActivityIntent(getActivity(), this.mAccount, this.mActivityId, this.mIsActivityPublic);
      if (this.mIsActivityPublic)
      {
        Bundle localBundle = EsAnalyticsData.createExtras("extra_activity_id", this.mActivityId);
        recordUserAction(OzActions.OPEN_RESHARE_SHAREBOX, localBundle);
        ConfirmIntentDialog.newInstance(getString(R.string.reshare_dialog_title), getString(R.string.reshare_dialog_message), getString(R.string.reshare_dialog_positive_button), localIntent).show(getFragmentManager(), "reshare_activity");
      }
    }
    while (true)
    {
      return;
      startActivity(localIntent);
      continue;
      super.onActionButtonClicked(paramInt);
    }
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    int i = 1;
    Bundle localBundle = EsAnalyticsData.createExtras("extra_creation_source_id", this.mCreationSourceId);
    Context localContext2;
    EsAccount localEsAccount2;
    if ((paramInt1 == i) || (paramInt1 == 2))
    {
      if (paramInt1 != 2)
        break label80;
      if (paramInt2 != -1)
        break label94;
      if (getActivity() != null)
      {
        localContext2 = getSafeContext();
        localEsAccount2 = this.mAccount;
        if (i == 0)
          break label86;
      }
    }
    label80: label86: for (OzActions localOzActions2 = OzActions.CALL_TO_ACTION_INSTALL_STARTED_ON_PLAY_STORE; ; localOzActions2 = OzActions.DEEP_LINK_INSTALL_STARTED_ON_PLAY_STORE)
    {
      EsAnalytics.recordActionEvent(localContext2, localEsAccount2, localOzActions2, OzViews.ACTIVITY, localBundle);
      return;
      i = 0;
      break;
    }
    label94: Context localContext1 = getSafeContext();
    EsAccount localEsAccount1 = this.mAccount;
    if (i != 0);
    for (OzActions localOzActions1 = OzActions.CALL_TO_ACTION_INSTALL_NOT_STARTED_ON_PLAY_STORE; ; localOzActions1 = OzActions.DEEP_LINK_INSTALL_NOT_STARTED_ON_PLAY_STORE)
    {
      EsAnalytics.recordActionEvent(localContext1, localEsAccount1, localOzActions1, OzViews.ACTIVITY, localBundle);
      break;
    }
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if ((paramActivity instanceof StreamOneUpCallbacks))
    {
      this.mCallback = ((StreamOneUpCallbacks)paramActivity);
      return;
    }
    throw new IllegalArgumentException("Activity must implement PhotoOneUpCallbacks");
  }

  public final void onBackgroundViewLoaded(OneUpLinkView paramOneUpLinkView)
  {
    if (paramOneUpLinkView.getId() == R.id.background)
    {
      this.mStageMediaLoaded = true;
      updateLoadingSpinner(getView());
    }
  }

  public final void onCancelRequested()
  {
    if (this.mCommentText == null)
      getActivity().finish();
    while (true)
    {
      return;
      if (this.mCommentText.getText().toString().length() == 0)
      {
        getActivity().finish();
      }
      else
      {
        AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(getString(R.string.comment_title), getString(R.string.comment_quit_question), getString(R.string.yes), getString(R.string.no));
        localAlertFragmentDialog.setTargetFragment(this, 0);
        localAlertFragmentDialog.show(getFragmentManager(), "hsouf_cancel_edits");
      }
    }
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if (i == R.id.footer_post_button)
      if (this.mCommentText.getText().toString().trim().length() > 0)
        if (this.mPendingRequestId == null)
        {
          Bundle localBundle2 = EsAnalyticsData.createExtras("extra_activity_id", this.mActivityId);
          recordUserAction(OzActions.ONE_UP_POST_COMMENT, localBundle2);
          Editable localEditable = this.mCommentText.getText();
          Context localContext2 = getSafeContext();
          String str3 = ApiUtils.buildPostableString$6d7f0b14(localEditable);
          this.mPendingRequestId = Integer.valueOf(EsService.createComment(localContext2, this.mAccount, this.mActivityId, str3));
          showProgressDialog(32);
        }
    while (true)
    {
      return;
      this.mCommentButton.setEnabled(false);
      continue;
      if ((i == R.id.list_expander) || (i == R.id.background))
        if (this.mBackgroundRef == null)
        {
          if ((paramView instanceof ExpandingScrollView))
          {
            MotionEvent localMotionEvent = ((ExpandingScrollView)paramView).getLastTouchEvent();
            if (localMotionEvent != null)
            {
              float f1 = localMotionEvent.getRawX();
              float f2 = localMotionEvent.getRawY();
              View localView = getView().findViewById(R.id.stage);
              if (localView != null)
              {
                StreamOneUpHangoutView localStreamOneUpHangoutView = (StreamOneUpHangoutView)localView.findViewById(R.id.hangout);
                if (localStreamOneUpHangoutView != null)
                  localStreamOneUpHangoutView.processClick(f1, f2);
                StreamOneUpSkyjamView localStreamOneUpSkyjamView = (StreamOneUpSkyjamView)localView.findViewById(R.id.skyjam);
                if (localStreamOneUpSkyjamView != null)
                  localStreamOneUpSkyjamView.processClick(f1, f2);
              }
            }
          }
        }
        else
        {
          MediaRef.MediaType localMediaType = this.mBackgroundRef.getType();
          if ((localMediaType == MediaRef.MediaType.IMAGE) || (localMediaType == MediaRef.MediaType.PANORAMA))
          {
            if (this.mBackgroundRef.getPhotoId() != 0L)
            {
              if (!this.mIsAlbum)
              {
                if (this.mImageView.isPanorama())
                  startActivity(Intents.getViewPanoramaActivityIntent(getActivity(), this.mAccount, this.mBackgroundRef));
                else
                  this.mCallback.toggleFullScreen();
              }
              else
              {
                Context localContext1 = getSafeContext();
                if (localContext1 != null)
                {
                  Intents.PhotoViewIntentBuilder localPhotoViewIntentBuilder = Intents.newPhotoViewActivityIntentBuilder(localContext1);
                  String str1 = this.mBackgroundRef.getOwnerGaiaId();
                  localPhotoViewIntentBuilder.setAccount(this.mAccount).setPhotoRef(this.mBackgroundRef).setGaiaId(str1).setAlbumId(this.mAlbumId).setRefreshAlbumId(this.mAlbumId).setDisableComments(Boolean.valueOf(this.mIsSquarePost));
                  Bundle localBundle1 = EsAnalyticsData.createExtras("extra_gaia_id", str1);
                  recordUserAction(OzActions.ONE_UP_SELECT_PHOTO, localBundle1);
                  startActivity(localPhotoViewIntentBuilder.build());
                }
              }
            }
            else if ((this.mDeepLinkData != null) && (!this.mDeepLinkData.getClientPackageNames().isEmpty()))
              launchDeepLink$654e823a(this.mDeepLinkData.getClientPackageNames(), this.mDeepLinkData.getDeepLinkId(), this.mBackgroundLinkUrl, this.mAuthorId, false);
            else if (!TextUtils.isEmpty(this.mBackgroundLinkUrl))
              Intents.viewContent(getActivity(), this.mAccount, this.mBackgroundLinkUrl, this.mCreationSourceId);
          }
          else
          {
            String str2 = this.mBackgroundRef.getLocalUri().toString();
            Intents.viewContent(getActivity(), this.mAccount, str2, this.mCreationSourceId);
          }
        }
    }
  }

  public final void onClickableButtonListenerClick(ClickableButton paramClickableButton)
  {
    if (this.mAppInviteData != null)
    {
      if ((this.mAppInviteData.getClientPackageNames().isEmpty()) || (TextUtils.isEmpty(this.mAppInviteData.getDeepLinkId())))
        break label69;
      launchDeepLink$654e823a(this.mAppInviteData.getClientPackageNames(), this.mAppInviteData.getDeepLinkId(), this.mAppInviteData.getUrl(), this.mAuthorId, true);
    }
    while (true)
    {
      return;
      label69: if (!TextUtils.isEmpty(this.mAppInviteData.getUrl()))
        Intents.viewContent(getActivity(), this.mAccount, this.mAppInviteData.getUrl(), this.mCreationSourceId);
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mFlaggedComments = new HashSet();
    Intent localIntent = getActivity().getIntent();
    this.mAccount = ((EsAccount)localIntent.getParcelableExtra("account"));
    this.mActivityId = localIntent.getStringExtra("activity_id");
    this.mAlbumId = localIntent.getStringExtra("album_id");
    this.mBackgroundRef = ((MediaRef)localIntent.getParcelableExtra("photo_ref"));
    this.mBackgroundLinkUrl = localIntent.getStringExtra("photo_link_url");
    this.mLinkTitle = localIntent.getStringExtra("link_title");
    this.mDeepLinkLabel = localIntent.getStringExtra("deep_link_label");
    this.mLinkUrl = localIntent.getStringExtra("link_url");
    this.mIsAlbum = localIntent.getBooleanExtra("is_album", false);
    this.mViewerIsSquareAdmin = localIntent.getBooleanExtra("square_admin", false);
    this.mSquareId = localIntent.getStringExtra("square_id");
    this.mFullScreen = false;
    if (paramBundle != null)
    {
      if (paramBundle.containsKey("pending_request_id"))
        this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("pending_request_id"));
      if (paramBundle.containsKey("activity_request_id"))
        this.mActivityRequestId = Integer.valueOf(paramBundle.getInt("activity_request_id"));
      this.mAudienceData = ((AudienceData)paramBundle.getParcelable("audience_data"));
      String[] arrayOfString = paramBundle.getStringArray("flagged_comments");
      if (arrayOfString != null)
        this.mFlaggedComments.addAll(Arrays.asList(arrayOfString));
      this.mOperationType = paramBundle.getInt("operation_type", 0);
      this.mMuteProcessed = paramBundle.getBoolean("mute_processed", false);
      this.mReadProcessed = paramBundle.getBoolean("read_processed", false);
      this.mSourcePackageName = paramBundle.getString("source_package_name");
      this.mSourceAuthorId = paramBundle.getString("source_author_id");
      this.mGetActivityComplete = paramBundle.getBoolean("get_activity_complete");
      this.mStageMediaLoaded = paramBundle.getBoolean("stage_media_loaded");
      this.mFullScreen = paramBundle.getBoolean("full_screen");
      this.mAutoPlay = false;
    }
    while (true)
    {
      this.mUpdateActionBar = this.mFullScreen;
      return;
      if (localIntent.getBooleanExtra("refresh", false))
        refresh();
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default:
    case 519746381:
    }
    for (Object localObject = null; ; localObject = new StreamOneUpLoader(getSafeContext(), this.mAccount, this.mActivityId))
      return localObject;
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    int i = 8;
    Context localContext = getSafeContext();
    if (!sResourcesLoaded)
    {
      Resources localResources = localContext.getResources();
      sMaxWidth = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_list_max_width);
      sMinExposureLand = localResources.getDimensionPixelOffset(R.dimen.one_up_photo_min_exposure_land);
      sMinExposurePort = localResources.getDimensionPixelOffset(R.dimen.one_up_photo_min_exposure_port);
      sAvatarMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_top);
      sResourcesLoaded = true;
    }
    View localView1 = paramLayoutInflater.inflate(R.layout.stream_one_up_fragment, paramViewGroup, false);
    this.mListParent = localView1.findViewById(R.id.list_parent);
    this.mListParent.findViewById(R.id.list_expander).setOnClickListener(this);
    View localView2 = this.mListParent;
    int j;
    label302: View localView3;
    if (this.mFullScreen)
    {
      j = i;
      localView2.setVisibility(j);
      this.mListAnimator = new PhotoOneUpAnimationController(this.mListParent, false, false);
      this.mListView = ((StreamOneUpListView)localView1.findViewById(16908298));
      this.mAdapter = new StreamOneUpAdapter(localContext, null, this, this.mListView);
      this.mAdapter.setLoading(true);
      this.mListView.setMaxWidth(sMaxWidth);
      this.mListView.setAdapter(this.mAdapter);
      this.mListView.setOnItemClickListener(this);
      this.mListView.setOnMeasureListener(this);
      this.mTouchHandler = ((OneUpTouchHandler)localView1.findViewById(R.id.touch_handler));
      this.mTouchHandler.setScrollView(this.mListParent);
      this.mTouchHandler.setActionBar(getActionBar());
      if ((this.mBackgroundRef != null) && (!this.mBackgroundRef.hasPhotoId()) && (this.mBackgroundRef.getType() != MediaRef.MediaType.VIDEO))
        break label591;
      bindStageMedia(localView1);
      this.mFooter = ((LinearLayoutWithLayoutNotifications)localView1.findViewById(R.id.footer));
      this.mCommentText = ((MentionMultiAutoCompleteTextView)localView1.findViewById(R.id.footer_text));
      this.mCommentText.setEnabled(false);
      this.mCommentText.setHint(null);
      this.mFooter.setLayoutListener(this);
      this.mFooter.setMaxWidth(sMaxWidth);
      this.mFooterAnimator = new PhotoOneUpAnimationController(this.mFooter, false, true);
      LinearLayoutWithLayoutNotifications localLinearLayoutWithLayoutNotifications = this.mFooter;
      if (!this.mFullScreen)
        break label601;
      label396: localLinearLayoutWithLayoutNotifications.setVisibility(i);
      new CircleNameResolver(localContext, getLoaderManager(), this.mAccount).initLoader();
      this.mCommentText.init(this, this.mAccount, this.mActivityId, null);
      this.mCommentButton = localView1.findViewById(R.id.footer_post_button);
      this.mCommentButton.setOnClickListener(this);
      localView3 = this.mCommentButton;
      if (this.mCommentText.getText().length() <= 0)
        break label607;
    }
    label591: label601: label607: for (boolean bool = true; ; bool = false)
    {
      localView3.setEnabled(bool);
      this.mTextWatcher = new MyTextWatcher(this.mCommentButton, (byte)0);
      this.mCommentText.addTextChangedListener(this.mTextWatcher);
      this.mCommentText.setOnEditorActionListener(new TextView.OnEditorActionListener()
      {
        public final boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          if (paramAnonymousInt == 6)
            HostedStreamOneUpFragment.this.mCommentButton.performClick();
          for (boolean bool = true; ; bool = false)
            return bool;
        }
      });
      getLoaderManager().initLoader(519746381, null, this);
      if (getActivity().getIntent().getBooleanExtra("show_keyboard", false))
        this.mCommentText.postDelayed(new Runnable()
        {
          public final void run()
          {
            if ((HostedStreamOneUpFragment.this.mCommentButton != null) && (HostedStreamOneUpFragment.this.mCommentText != null) && (HostedStreamOneUpFragment.this.mCommentText.isEnabled()))
            {
              HostedStreamOneUpFragment.this.mCommentText.requestFocus();
              SoftInput.show(HostedStreamOneUpFragment.this.mCommentText);
            }
          }
        }
        , 250L);
      return localView1;
      j = 0;
      break;
      bindStageLink(localView1);
      break label302;
      i = 0;
      break label396;
    }
  }

  public final void onDestroyView()
  {
    this.mCommentText.removeTextChangedListener(this.mTextWatcher);
    this.mCommentText.setOnEditorActionListener(null);
    this.mCommentText.destroy();
    this.mCommentText = null;
    this.mCommentButton.setOnClickListener(null);
    this.mCommentButton = null;
    super.onDestroyView();
  }

  public final void onDialogCanceled$20f9a4b7(String paramString)
  {
  }

  public final void onDialogListClick$12e92030(int paramInt, Bundle paramBundle)
  {
    ArrayList localArrayList = paramBundle.getIntegerArrayList("comment_action");
    if (localArrayList == null)
      Log.w("StreamOneUp", "No actions for comment option dialog");
    while (true)
    {
      return;
      if (paramInt >= localArrayList.size())
      {
        Log.w("StreamOneUp", "Option selected outside the action list");
      }
      else
      {
        String str1 = paramBundle.getString("comment_id");
        String str2 = paramBundle.getString("comment_content");
        String str3 = paramBundle.getString("plus_one_id");
        boolean bool = paramBundle.getBoolean("plus_one_by_me");
        int i = paramBundle.getInt("plus_one_count");
        switch (((Integer)localArrayList.get(paramInt)).intValue())
        {
        default:
          break;
        case 33:
          Bundle localBundle2 = EsAnalyticsData.createExtras("extra_comment_id", str1);
          recordUserAction(OzActions.ONE_UP_DELETE_COMMENT, localBundle2);
          AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(getString(R.string.menu_delete_comment), getString(R.string.comment_delete_question), getString(R.string.ok), getString(R.string.cancel));
          localAlertFragmentDialog.setTargetFragment(this, 0);
          localAlertFragmentDialog.getArguments().putString("comment_id", str1);
          localAlertFragmentDialog.show(getFragmentManager(), "hsouf_delete_comment");
          break;
        case 37:
          if ((str3 == null) || (!bool))
            EsService.plusOneComment(getSafeContext(), this.mAccount, this.mActivityId, 0L, str1, null, true);
          else
            EsService.plusOneComment(getSafeContext(), this.mAccount, this.mActivityId, 0L, str1, str3, false);
          break;
        case 38:
          Bundle localBundle1 = EsAnalyticsData.createExtras("extra_comment_id", str1);
          recordUserAction(OzActions.ONE_UP_EDIT_COMMENT, localBundle1);
          startActivity(Intents.getEditCommentActivityIntent(getSafeContext(), this.mAccount, this.mActivityId, str1, str2, null, null));
          break;
        case 34:
          doReportComment(false, str1, false);
          break;
        case 35:
          doReportComment(false, str1, true);
          break;
        case 36:
          doReportComment(true, str1, false);
          break;
        case 64:
          showPlusOnePeople(str3, i);
        }
      }
    }
  }

  public final void onDialogNegativeClick$20f9a4b7(String paramString)
  {
  }

  public final void onDialogPositiveClick(Bundle paramBundle, String paramString)
  {
    boolean bool = true;
    if ("hsouf_delete_activity".equals(paramString))
    {
      this.mPendingRequestId = Integer.valueOf(EsService.deleteActivity(getSafeContext(), this.mAccount, this.mActivityId));
      showProgressDialog(16);
    }
    while (true)
    {
      return;
      if ("hsouf_delete_comment".equals(paramString))
      {
        this.mPendingRequestId = Integer.valueOf(EsService.deleteComment(getSafeContext(), this.mAccount, this.mActivityId, paramBundle.getString("comment_id")));
        showProgressDialog(33);
      }
      else if ("hsouf_report_comment".equals(paramString))
      {
        this.mPendingRequestId = Integer.valueOf(EsService.moderateComment(getSafeContext(), this.mAccount, this.mActivityId, paramBundle.getString("comment_id"), paramBundle.getBoolean("delete", false), bool, paramBundle.getBoolean("is_undo", false)));
        showProgressDialog(34);
      }
      else
      {
        if ("hsouf_mute_activity".equals(paramString))
        {
          Context localContext = getSafeContext();
          EsAccount localEsAccount = this.mAccount;
          String str2 = this.mActivityId;
          if (!this.mIsActivityMuted);
          while (true)
          {
            this.mPendingRequestId = Integer.valueOf(EsService.muteActivity(localContext, localEsAccount, str2, bool));
            showProgressDialog(17);
            break;
            bool = false;
          }
        }
        if ("hsouf_report_activity".equals(paramString))
        {
          if (this.mViewerIsSquareAdmin);
          for (String str1 = this.mCreationSourceId; ; str1 = null)
          {
            this.mPendingRequestId = Integer.valueOf(EsService.reportActivity(getSafeContext(), this.mAccount, this.mActivityId, str1));
            showProgressDialog(18);
            break;
          }
        }
        if ("hsouf_cancel_edits".equals(paramString))
          getActivity().finish();
      }
    }
  }

  public final void onFullScreenChanged$25decb5(boolean paramBoolean)
  {
    this.mFullScreen = paramBoolean;
    if (!this.mFullScreen)
    {
      if (this.mUpdateActionBar)
        adjustActionBarMargins(getActionBar(), false);
      this.mUpdateActionBar = false;
    }
    if (this.mFooterAnimator != null)
      this.mFooterAnimator.animate(this.mFullScreen);
    if (this.mListAnimator != null)
      this.mListAnimator.animate(this.mFullScreen);
    if (this.mActionBarAnimator != null)
      this.mActionBarAnimator.animate(this.mFullScreen);
    if ((!this.mFullScreen) && (this.mImageView != null))
      this.mImageView.resetTransformations();
  }

  public final void onImageLoadFinished(PhotoHeaderView paramPhotoHeaderView)
  {
    this.mStageMediaLoaded = true;
    View localView1 = getView();
    updateLoadingSpinner(localView1);
    int i;
    int j;
    label60: int k;
    label81: EsImageView localEsImageView;
    int m;
    if (paramPhotoHeaderView.isPhotoBound())
    {
      if ((this.mBackgroundRef == null) || (this.mBackgroundRef.getType() != MediaRef.MediaType.VIDEO))
        break label122;
      i = 1;
      View localView2 = localView1.findViewById(R.id.video_overlay);
      if (i == 0)
        break label127;
      j = 0;
      localView2.setVisibility(j);
      if ((i != 0) || (!this.mIsAlbum))
        break label134;
      k = 1;
      localEsImageView = (EsImageView)localView1.findViewById(R.id.album_overlay);
      m = 0;
      if (k == 0)
        break label140;
    }
    while (true)
    {
      localEsImageView.setVisibility(m);
      if (k != 0)
        localEsImageView.startFadeOut(2000);
      return;
      label122: i = 0;
      break;
      label127: j = 8;
      break label60;
      label134: k = 0;
      break label81;
      label140: m = 8;
    }
  }

  public final void onImageScaled(float paramFloat)
  {
    if (paramFloat > 1.0F);
    for (int i = 1; ; i = 0)
    {
      if (this.mFullScreen != i)
        this.mCallback.toggleFullScreen();
      return;
    }
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Resources localResources;
    boolean bool2;
    ArrayList localArrayList1;
    ArrayList localArrayList2;
    boolean bool4;
    int j;
    if ((paramView instanceof StreamOneUpCommentView))
    {
      StreamOneUpCommentView localStreamOneUpCommentView = (StreamOneUpCommentView)paramView;
      localResources = getSafeContext().getResources();
      boolean bool1 = this.mAccount.isMyGaiaId(localStreamOneUpCommentView.getAuthorId());
      bool2 = this.mAccount.isMyGaiaId(this.mAdapter.getActivityAuthorId());
      localArrayList1 = new ArrayList(5);
      localArrayList2 = new ArrayList(5);
      boolean bool3 = localStreamOneUpCommentView.getPlusOneByMe();
      String str = localStreamOneUpCommentView.getPlusOneId();
      int i = localStreamOneUpCommentView.getPlusOneCount();
      bool4 = localStreamOneUpCommentView.isFlagged();
      if (!bool4)
      {
        if (bool3)
        {
          j = R.string.stream_one_up_comment_option_plusminus;
          localArrayList1.add(localResources.getString(j));
          localArrayList2.add(Integer.valueOf(37));
        }
      }
      else
      {
        if (!bool1)
          break label391;
        localArrayList1.add(localResources.getString(R.string.stream_one_up_comment_option_edit));
        localArrayList2.add(Integer.valueOf(38));
        label169: if ((bool2) || (bool1))
        {
          localArrayList1.add(localResources.getString(R.string.stream_one_up_comment_option_delete));
          localArrayList2.add(Integer.valueOf(33));
        }
        if ((str != null) && (i > 0))
        {
          localArrayList1.add(localResources.getString(R.string.stream_one_up_comment_option_plus_ones));
          localArrayList2.add(Integer.valueOf(64));
        }
        String[] arrayOfString = new String[localArrayList1.size()];
        localArrayList1.toArray(arrayOfString);
        AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(getString(R.string.stream_one_up_comment_options_title), arrayOfString);
        localAlertFragmentDialog.setTargetFragment(this, 0);
        localAlertFragmentDialog.getArguments().putIntegerArrayList("comment_action", localArrayList2);
        localAlertFragmentDialog.getArguments().putString("comment_id", localStreamOneUpCommentView.getCommentId());
        localAlertFragmentDialog.getArguments().putString("comment_content", localStreamOneUpCommentView.getCommentContent());
        localAlertFragmentDialog.getArguments().putString("plus_one_id", str);
        localAlertFragmentDialog.getArguments().putBoolean("plus_one_by_me", bool3);
        localAlertFragmentDialog.getArguments().putInt("plus_one_count", localStreamOneUpCommentView.getPlusOneCount());
        localAlertFragmentDialog.show(getFragmentManager(), "hsouf_delete_comment");
        localStreamOneUpCommentView.cancelPressedState();
      }
    }
    while (true)
    {
      return;
      j = R.string.stream_one_up_comment_option_plusone;
      break;
      label391: if (bool4)
      {
        localArrayList1.add(localResources.getString(R.string.stream_one_up_comment_option_undo_report));
        localArrayList2.add(Integer.valueOf(35));
        break label169;
      }
      localArrayList1.add(localResources.getString(R.string.stream_one_up_comment_option_report));
      if (bool2)
      {
        localArrayList2.add(Integer.valueOf(36));
        break label169;
      }
      localArrayList2.add(Integer.valueOf(34));
      break label169;
      if (Log.isLoggable("StreamOneUp", 3))
        Log.e("StreamOneUp", "HostedStreamOneUpFragment.onItemClick: Some other view: " + paramView);
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onLocationClick$75c560e7(DbLocation paramDbLocation)
  {
    MapUtils.showActivityOnMap(getActivity(), paramDbLocation);
  }

  public final void onMeasured(View paramView)
  {
    if (paramView == this.mListView)
      this.mAdapter.setContainerHeight(this.mListView.getMeasuredHeight());
    while (true)
    {
      return;
      if (paramView == this.mFooter)
      {
        final int i = this.mFooter.getMeasuredHeight();
        new Handler(Looper.getMainLooper()).post(new Runnable()
        {
          public final void run()
          {
            View localView1 = HostedStreamOneUpFragment.this.getView();
            if (localView1 == null);
            View localView2;
            do
            {
              do
              {
                return;
                ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)HostedStreamOneUpFragment.this.mListParent.getLayoutParams();
                localMarginLayoutParams1.bottomMargin = i;
                HostedStreamOneUpFragment.this.mListParent.setLayoutParams(localMarginLayoutParams1);
              }
              while (localView1.getMeasuredWidth() > HostedStreamOneUpFragment.sMaxWidth);
              localView2 = localView1.findViewById(R.id.stage);
            }
            while ((localView2 == null) || (HostedStreamOneUpFragment.this.mLinkView == null));
            int i;
            label98: ViewGroup.MarginLayoutParams localMarginLayoutParams2;
            int j;
            if (HostedStreamOneUpFragment.this.getResources().getConfiguration().orientation == 2)
            {
              i = 1;
              localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)localView2.getLayoutParams();
              j = i + HostedStreamOneUpFragment.sAvatarMarginTop;
              if (i == 0)
                break label152;
            }
            label152: for (int k = HostedStreamOneUpFragment.sMinExposureLand; ; k = HostedStreamOneUpFragment.sMinExposurePort)
            {
              localMarginLayoutParams2.bottomMargin = (k + j);
              localView2.setLayoutParams(localMarginLayoutParams2);
              break;
              i = 0;
              break label98;
            }
          }
        });
      }
    }
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    Context localContext = getSafeContext();
    int i = paramMenuItem.getItemId();
    boolean bool;
    if (i == R.id.feedback)
    {
      recordUserAction(OzActions.SETTINGS_FEEDBACK);
      GoogleFeedback.launch(getActivity());
      bool = true;
    }
    while (true)
    {
      return bool;
      if (i == R.id.show_location)
      {
        MapUtils.showActivityOnMap(getActivity(), this.mLocationData);
        bool = true;
      }
      else if (i == R.id.edit)
      {
        startActivity(Intents.getEditPostActivityIntent(localContext, this.mAccount, this.mActivityId, this.mEditableText, this.mReshare));
        bool = true;
      }
      else if (i == R.id.delete_post)
      {
        Bundle localBundle4 = EsAnalyticsData.createExtras("extra_activity_id", this.mActivityId);
        recordUserAction(OzActions.ONE_UP_REMOVE_ACTIVITY, localBundle4);
        AlertFragmentDialog localAlertFragmentDialog3 = AlertFragmentDialog.newInstance(getString(R.string.menu_remove_post), getString(R.string.post_delete_question), getString(R.string.ok), getString(R.string.cancel));
        localAlertFragmentDialog3.setTargetFragment(this, 0);
        localAlertFragmentDialog3.show(getFragmentManager(), "hsouf_delete_activity");
        bool = true;
      }
      else if (i == R.id.plus_oned_by)
      {
        if (this.mPlusOnedByData != null)
          showPlusOnePeople((String)this.mPlusOnedByData.first, ((Integer)this.mPlusOnedByData.second).intValue());
        bool = true;
      }
      else if (i == R.id.report_abuse)
      {
        Bundle localBundle3 = EsAnalyticsData.createExtras("extra_activity_id", this.mActivityId);
        recordUserAction(OzActions.ONE_UP_REPORT_ABUSE_ACTIVITY, localBundle3);
        AlertFragmentDialog localAlertFragmentDialog2 = AlertFragmentDialog.newInstance(getString(R.string.menu_report_abuse), getString(R.string.post_report_question), getString(R.string.ok), getString(R.string.cancel));
        localAlertFragmentDialog2.setTargetFragment(this, 0);
        localAlertFragmentDialog2.getArguments().putString("activity_id", this.mActivityId);
        localAlertFragmentDialog2.show(getFragmentManager(), "hsouf_report_activity");
        bool = true;
      }
      else
      {
        if (i == R.id.mute_post)
        {
          Bundle localBundle2 = EsAnalyticsData.createExtras("extra_activity_id", this.mActivityId);
          recordUserAction(OzActions.ONE_UP_MUTE_ACTIVITY, localBundle2);
          int j;
          label374: String str2;
          if (this.mIsActivityMuted)
          {
            j = R.string.menu_unmute_post;
            str2 = getString(j);
            if (!this.mIsActivityMuted)
              break label469;
          }
          label469: for (int k = R.string.post_unmute_question; ; k = R.string.post_mute_question)
          {
            AlertFragmentDialog localAlertFragmentDialog1 = AlertFragmentDialog.newInstance(str2, getString(k), getString(R.string.ok), getString(R.string.cancel));
            localAlertFragmentDialog1.setTargetFragment(this, 0);
            localAlertFragmentDialog1.getArguments().putString("activity_id", this.mActivityId);
            localAlertFragmentDialog1.show(getFragmentManager(), "hsouf_mute_activity");
            bool = true;
            break;
            j = R.string.menu_mute_post;
            break label374;
          }
        }
        if (i == R.id.photo_details)
        {
          if ((this.mBackgroundRef != null) && (this.mBackgroundRef.getPhotoId() != 0L))
          {
            Intents.PhotoViewIntentBuilder localPhotoViewIntentBuilder = Intents.newPhotoViewActivityIntentBuilder(getSafeContext());
            String str1 = this.mBackgroundRef.getOwnerGaiaId();
            localPhotoViewIntentBuilder.setAccount(this.mAccount).setPhotoRef(this.mBackgroundRef).setGaiaId(str1).setAlbumId(this.mAlbumId).setRefreshAlbumId(this.mAlbumId).setDisableComments(Boolean.valueOf(this.mIsSquarePost));
            Bundle localBundle1 = EsAnalyticsData.createExtras("extra_gaia_id", str1);
            recordUserAction(OzActions.ONE_UP_SELECT_PHOTO, localBundle1);
            startActivity(localPhotoViewIntentBuilder.build());
          }
          bool = true;
        }
        else
        {
          bool = false;
        }
      }
    }
  }

  public final void onPause()
  {
    super.onPause();
    if (this.mImageView != null)
      PhotoHeaderView.onStop();
    if (this.mLinkView != null)
      OneUpLinkView.onStop();
    if (this.mListView != null)
      for (int i = -1 + this.mListView.getChildCount(); i >= 0; i--)
        if ((this.mListView.getChildAt(i) instanceof OneUpBaseView))
          OneUpBaseView.onStop();
    EsService.unregisterListener(this.mServiceListener);
  }

  public final void onPlaceClick(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      startActivity(Intents.getProfileActivityByGaiaIdIntent(getActivity(), this.mAccount, paramString, null));
  }

  public final void onPlusOne(String paramString, DbPlusOneData paramDbPlusOneData)
  {
    FragmentActivity localFragmentActivity;
    if (!EsService.isPostPlusOnePending(paramString))
    {
      localFragmentActivity = getActivity();
      if ((paramDbPlusOneData == null) || (!paramDbPlusOneData.isPlusOnedByMe()))
        break label34;
      EsService.deletePostPlusOne(localFragmentActivity, this.mAccount, paramString);
    }
    while (true)
    {
      return;
      label34: EsService.createPostPlusOne(localFragmentActivity, this.mAccount, paramString);
    }
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    paramHostActionBar.showRefreshButton();
    paramHostActionBar.showProgressIndicator();
    if (this.mIsActivityResharable)
      paramHostActionBar.showActionButton(0, R.drawable.ic_actionbar_reshare, R.string.menu_reshare_post);
    if (this.mActionBarAnimator == null)
      this.mActionBarAnimator = new PhotoOneUpAnimationController(paramHostActionBar, true, true);
    if (this.mUpdateActionBar)
    {
      adjustActionBarMargins(paramHostActionBar, true);
      paramHostActionBar.setVisibility(8);
    }
    while (true)
    {
      updateProgressIndicator();
      return;
      paramHostActionBar.setVisibility(0);
    }
  }

  public final void onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    if (this.mLocationData != null)
      paramMenu.findItem(R.id.show_location).setVisible(true);
    if (this.mIsMyActivity != null)
    {
      if (!this.mIsMyActivity.booleanValue())
        break label152;
      paramMenu.findItem(R.id.edit).setVisible(true);
      paramMenu.findItem(R.id.delete_post).setVisible(true);
    }
    while (true)
    {
      if (this.mPlusOnedByData != null)
        paramMenu.findItem(R.id.plus_oned_by).setVisible(true);
      paramMenu.findItem(R.id.feedback).setVisible(true);
      if ((this.mBackgroundRef != null) && (this.mBackgroundRef.getPhotoId() != 0L))
        paramMenu.findItem(R.id.photo_details).setVisible(true);
      return;
      label152: paramMenu.findItem(R.id.report_abuse).setVisible(true);
      MenuItem localMenuItem = paramMenu.findItem(R.id.mute_post);
      localMenuItem.setVisible(true);
      if (this.mIsActivityMuted)
        localMenuItem.setTitle(R.string.menu_unmute_post);
      else
        localMenuItem.setTitle(R.string.menu_mute_post);
    }
  }

  public final void onResume()
  {
    super.onResume();
    if (this.mImageView != null)
      PhotoHeaderView.onStart();
    if (this.mLinkView != null)
      OneUpLinkView.onStart();
    if (this.mListView != null)
      for (int i = -1 + this.mListView.getChildCount(); i >= 0; i--)
        if ((this.mListView.getChildAt(i) instanceof OneUpBaseView))
          OneUpBaseView.onStart();
    EsService.registerListener(this.mServiceListener);
    this.mCallback.addScreenListener(this);
    if ((this.mPendingRequestId != null) && (!EsService.isRequestPending(this.mPendingRequestId.intValue())))
    {
      ServiceResult localServiceResult = EsService.removeResult(this.mPendingRequestId.intValue());
      this.mServiceListener.handleServiceCallback(this.mPendingRequestId.intValue(), localServiceResult);
    }
    if ((this.mActivityRequestId != null) && (!EsService.isRequestPending(this.mActivityRequestId.intValue())))
    {
      EsService.removeResult(this.mActivityRequestId.intValue());
      this.mActivityRequestId = null;
    }
    updateProgressIndicator();
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mPendingRequestId != null)
      paramBundle.putInt("pending_request_id", this.mPendingRequestId.intValue());
    if (this.mActivityRequestId != null)
      paramBundle.putInt("activity_request_id", this.mActivityRequestId.intValue());
    if (this.mAudienceData != null)
      paramBundle.putParcelable("audience_data", this.mAudienceData);
    if (!this.mFlaggedComments.isEmpty())
    {
      String[] arrayOfString = new String[this.mFlaggedComments.size()];
      this.mFlaggedComments.toArray(arrayOfString);
      paramBundle.putStringArray("flagged_comments", arrayOfString);
    }
    paramBundle.putInt("operation_type", this.mOperationType);
    paramBundle.putBoolean("mute_processed", this.mMuteProcessed);
    paramBundle.putBoolean("read_processed", this.mReadProcessed);
    paramBundle.putString("source_package_name", this.mSourcePackageName);
    paramBundle.putString("source_author_id", this.mSourceAuthorId);
    paramBundle.putBoolean("get_activity_complete", this.mGetActivityComplete);
    paramBundle.putBoolean("stage_media_loaded", this.mStageMediaLoaded);
    paramBundle.putBoolean("full_screen", this.mFullScreen);
  }

  protected final void onSetArguments(Bundle paramBundle)
  {
    super.onSetArguments(paramBundle);
    this.mAutoPlay = paramBundle.getBoolean("auto_play_music", false);
  }

  public final void onSkyjamBuyClick(String paramString)
  {
    FragmentActivity localFragmentActivity = getActivity();
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.addFlags(524288);
    localIntent.setData(Uri.parse(paramString));
    localIntent.setPackage("com.android.vending");
    if (localFragmentActivity.getPackageManager().resolveActivity(localIntent, 0) == null)
      localIntent.setPackage(null);
    localFragmentActivity.startActivity(localIntent);
  }

  public final void onSkyjamListenClick(String paramString)
  {
    FragmentActivity localFragmentActivity = getActivity();
    Intent localIntent = new Intent("com.google.android.music.SHARED_PLAY");
    localIntent.putExtra("url", paramString);
    localIntent.putExtra("authAccount", EsService.getActiveAccount(localFragmentActivity).getName());
    localIntent.putExtra("accountType", "com.google");
    localIntent.setPackage("com.google.android.music");
    if (localFragmentActivity.getPackageManager().resolveActivity(localIntent, 0) == null)
    {
      localIntent = new Intent("android.intent.action.VIEW");
      localIntent.addFlags(524288);
      localIntent.setData(Uri.parse("market://details?id=com.google.android.music"));
    }
    localFragmentActivity.startActivity(localIntent);
  }

  public final void onSourceAppContentClick(String paramString1, List<String> paramList, String paramString2, String paramString3, String paramString4)
  {
    launchDeepLink$654e823a(paramList, paramString2, paramString3, paramString4, false);
  }

  public final void onSpanClick(URLSpan paramURLSpan)
  {
    onUrlClick(paramURLSpan.getURL());
  }

  public final void onSquareClick(String paramString1, String paramString2)
  {
    startActivity(Intents.getSquareStreamActivityIntent(getActivity(), this.mAccount, paramString1, paramString2, null));
  }

  public final void onUserImageClick(String paramString1, String paramString2)
  {
    Context localContext = getSafeContext();
    Bundle localBundle = EsAnalyticsData.createExtras("extra_gaia_id", paramString1);
    recordUserAction(OzActions.ONE_UP_SELECT_AUTHOR, localBundle);
    startActivity(Intents.getProfileActivityByGaiaIdIntent(localContext, this.mAccount, paramString1, null));
  }

  public final void refresh()
  {
    super.refresh();
    this.mGetActivityComplete = false;
    if (this.mActivityRequestId == null)
      this.mActivityRequestId = Integer.valueOf(EsService.getActivity(getSafeContext(), this.mAccount, this.mActivityId, this.mSquareId));
    updateProgressIndicator();
  }

  protected final void updateProgressIndicator()
  {
    HostActionBar localHostActionBar = getActionBar();
    StreamOneUpAdapter localStreamOneUpAdapter;
    if (localHostActionBar != null)
    {
      if ((this.mActivityRequestId != null) || ((this.mAdapter != null) && (this.mAdapter.getCursor() == null)))
        localHostActionBar.showProgressIndicator();
    }
    else if (this.mAdapter != null)
    {
      localStreamOneUpAdapter = this.mAdapter;
      if (this.mActivityRequestId == null)
        break label71;
    }
    label71: for (boolean bool = true; ; bool = false)
    {
      localStreamOneUpAdapter.setLoading(bool);
      return;
      localHostActionBar.hideProgressIndicator();
      break;
    }
  }

  private static final class MyTextWatcher
    implements TextWatcher
  {
    private final View mView;

    private MyTextWatcher(View paramView)
    {
      this.mView = paramView;
    }

    public final void afterTextChanged(Editable paramEditable)
    {
      View localView = this.mView;
      if (TextUtils.getTrimmedLength(paramEditable) > 0);
      for (boolean bool = true; ; bool = false)
      {
        localView.setEnabled(bool);
        return;
      }
    }

    public final void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public final void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }
  }

  private final class ServiceListener extends EsServiceListener
  {
    private ServiceListener()
    {
    }

    private boolean handleServiceCallback(int paramInt, ServiceResult paramServiceResult)
    {
      Integer localInteger = HostedStreamOneUpFragment.this.mPendingRequestId;
      boolean bool = false;
      if (localInteger != null)
      {
        int i = HostedStreamOneUpFragment.this.mPendingRequestId.intValue();
        bool = false;
        if (i == paramInt);
      }
      else
      {
        return bool;
      }
      HostedStreamOneUpFragment.access$702(HostedStreamOneUpFragment.this, null);
      HostedStreamOneUpFragment.access$2500(HostedStreamOneUpFragment.this);
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      {
        int j;
        switch (HostedStreamOneUpFragment.this.mOperationType)
        {
        default:
          j = R.string.operation_failed;
        case 19:
        case 16:
        case 17:
        case 18:
        case 32:
        case 33:
        case 34:
        case 48:
        }
        while (true)
        {
          Toast.makeText(HostedStreamOneUpFragment.this.getSafeContext(), j, 0).show();
          bool = false;
          break;
          j = R.string.reshare_post_error;
          continue;
          j = R.string.remove_post_error;
          continue;
          j = R.string.mute_activity_error;
          continue;
          j = R.string.report_activity_error;
          continue;
          j = R.string.comment_post_error;
          continue;
          j = R.string.comment_delete_error;
          continue;
          j = R.string.comment_moderate_error;
          continue;
          j = R.string.get_acl_error;
        }
      }
      switch (HostedStreamOneUpFragment.this.mOperationType)
      {
      default:
      case 17:
      case 16:
      case 18:
      }
      while (true)
      {
        bool = true;
        break;
        if (!HostedStreamOneUpFragment.this.mIsActivityMuted)
          HostedStreamOneUpFragment.this.getActivity().finish();
      }
    }

    public final void onCreateComment$63505a2b(int paramInt, ServiceResult paramServiceResult)
    {
      if (!paramServiceResult.hasError())
      {
        if (HostedStreamOneUpFragment.this.mCommentText != null)
          HostedStreamOneUpFragment.this.mCommentText.setText(null);
        handleServiceCallback(paramInt, paramServiceResult);
      }
      while (true)
      {
        return;
        Exception localException = paramServiceResult.getException();
        if ((!(localException instanceof OzServerException)) || (((OzServerException)localException).getErrorCode() != 14))
          break;
        HostedStreamOneUpFragment.access$2500(HostedStreamOneUpFragment.this);
        AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(HostedStreamOneUpFragment.this.getString(R.string.post_not_sent_title), HostedStreamOneUpFragment.this.getString(R.string.post_restricted_mention_error), HostedStreamOneUpFragment.this.getString(R.string.ok), null);
        localAlertFragmentDialog.setTargetFragment(HostedStreamOneUpFragment.this.getTargetFragment(), 0);
        localAlertFragmentDialog.show(HostedStreamOneUpFragment.this.getFragmentManager(), "StreamPostRestrictionsNotSupported");
      }
    }

    public final void onCreatePostPlusOne$63505a2b(ServiceResult paramServiceResult)
    {
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(HostedStreamOneUpFragment.this.getSafeContext(), R.string.plusone_error, 0).show();
    }

    public final void onDeleteActivity$63505a2b(int paramInt, ServiceResult paramServiceResult)
    {
      handleServiceCallback(paramInt, paramServiceResult);
    }

    public final void onDeleteComment$51e3eb1f(int paramInt, ServiceResult paramServiceResult)
    {
      handleServiceCallback(paramInt, paramServiceResult);
    }

    public final void onDeletePostPlusOne$63505a2b(ServiceResult paramServiceResult)
    {
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(HostedStreamOneUpFragment.this.getSafeContext(), R.string.delete_plusone_error, 0).show();
    }

    public final void onEditActivity$63505a2b(int paramInt, ServiceResult paramServiceResult)
    {
      handleServiceCallback(paramInt, paramServiceResult);
    }

    public final void onEditComment$51e3eb1f(int paramInt, ServiceResult paramServiceResult)
    {
      handleServiceCallback(paramInt, paramServiceResult);
    }

    public final void onGetActivity$51e3eb1f(int paramInt, String paramString, ServiceResult paramServiceResult)
    {
      if ((paramString == null) || (!paramString.equals(HostedStreamOneUpFragment.this.mActivityId)));
      while (true)
      {
        return;
        if ((HostedStreamOneUpFragment.this.mActivityRequestId != null) && (paramInt == HostedStreamOneUpFragment.this.mActivityRequestId.intValue()))
        {
          HostedStreamOneUpFragment.access$2002(HostedStreamOneUpFragment.this, null);
          HostedStreamOneUpFragment.this.updateProgressIndicator();
        }
        HostedStreamOneUpFragment.access$2102(HostedStreamOneUpFragment.this, true);
        if ((paramServiceResult.hasError()) && (HostedStreamOneUpFragment.this.mActivityDataNotFound))
        {
          Toast.makeText(HostedStreamOneUpFragment.this.getSafeContext(), HostedStreamOneUpFragment.this.getText(R.string.comments_activity_not_found), 0).show();
        }
        else if ((HostedStreamOneUpFragment.this.mAdapter != null) && (!HostedStreamOneUpFragment.this.mAdapter.isEmpty()))
        {
          View localView = HostedStreamOneUpFragment.this.getView();
          HostedStreamOneUpFragment.this.updateLoadingSpinner(localView);
        }
      }
    }

    public final void onGetActivityAudience$6db92636(int paramInt, AudienceData paramAudienceData, ServiceResult paramServiceResult)
    {
      if ((!paramServiceResult.hasError()) && (paramAudienceData != null))
      {
        HostedStreamOneUpFragment.access$2902(HostedStreamOneUpFragment.this, paramAudienceData);
        HostedStreamOneUpFragment.this.showAudience(paramAudienceData);
      }
      handleServiceCallback(paramInt, paramServiceResult);
    }

    public final void onModerateComment$56b78e3(int paramInt, String paramString, boolean paramBoolean, ServiceResult paramServiceResult)
    {
      if (handleServiceCallback(paramInt, paramServiceResult))
      {
        if (!paramBoolean)
          break label26;
        HostedStreamOneUpFragment.this.mAdapter.removeFlaggedComment(paramString);
      }
      while (true)
      {
        return;
        label26: HostedStreamOneUpFragment.this.mAdapter.addFlaggedComment(paramString);
      }
    }

    public final void onMuteActivity$63505a2b(int paramInt, ServiceResult paramServiceResult)
    {
      handleServiceCallback(paramInt, paramServiceResult);
    }

    public final void onPlusOneComment$56b78e3(boolean paramBoolean, ServiceResult paramServiceResult)
    {
      Context localContext;
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      {
        localContext = HostedStreamOneUpFragment.this.getSafeContext();
        if (!paramBoolean)
          break label39;
      }
      label39: for (int i = R.string.plusone_error; ; i = R.string.delete_plusone_error)
      {
        Toast.makeText(localContext, i, 0).show();
        return;
      }
    }

    public final void onReportActivity$63505a2b(int paramInt, ServiceResult paramServiceResult)
    {
      handleServiceCallback(paramInt, paramServiceResult);
    }

    public final void onReshareActivity$63505a2b(int paramInt, ServiceResult paramServiceResult)
    {
      handleServiceCallback(paramInt, paramServiceResult);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedStreamOneUpFragment
 * JD-Core Version:    0.6.2
 */