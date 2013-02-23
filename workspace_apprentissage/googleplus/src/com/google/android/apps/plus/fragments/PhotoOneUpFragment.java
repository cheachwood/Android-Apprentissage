package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.ApiUtils;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.DbPlusOneData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAnalyticsData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.FIFEUtil;
import com.google.android.apps.plus.phone.GoogleFeedback;
import com.google.android.apps.plus.phone.HostedFragment;
import com.google.android.apps.plus.phone.ImageProxyUtil;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.PhotoOneUpActivity.OnMenuItemListener;
import com.google.android.apps.plus.phone.PhotoOneUpActivity.OnScreenListener;
import com.google.android.apps.plus.phone.PhotoOneUpAnimationController;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.MediaStoreUtils;
import com.google.android.apps.plus.views.ExpandingScrollView;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.LinearLayoutWithLayoutNotifications;
import com.google.android.apps.plus.views.LinearLayoutWithLayoutNotifications.LayoutListener;
import com.google.android.apps.plus.views.MentionMultiAutoCompleteTextView;
import com.google.android.apps.plus.views.OneUpBaseView;
import com.google.android.apps.plus.views.OneUpBaseView.OnMeasuredListener;
import com.google.android.apps.plus.views.OneUpListener;
import com.google.android.apps.plus.views.OneUpTouchHandler;
import com.google.android.apps.plus.views.PhotoHeaderView;
import com.google.android.apps.plus.views.PhotoHeaderView.OnImageListener;
import com.google.android.apps.plus.views.PhotoTagScroller;
import com.google.android.apps.plus.views.PhotoTagScroller.PhotoShapeQuery;
import com.google.android.apps.plus.views.StreamOneUpCommentView;
import com.google.android.apps.plus.views.StreamOneUpListView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class PhotoOneUpFragment extends HostedFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, AdapterView.OnItemClickListener, AlertFragmentDialog.AlertDialogListener, PhotoOneUpActivity.OnMenuItemListener, PhotoOneUpActivity.OnScreenListener, LinearLayoutWithLayoutNotifications.LayoutListener, OneUpBaseView.OnMeasuredListener, OneUpListener, PhotoHeaderView.OnImageListener
{
  private static int sActionBarHeight;
  private static int sMaxWidth;
  private static boolean sResourcesLoaded;
  private EsAccount mAccount;
  private PhotoOneUpAnimationController mActionBarAnimator;
  private PhotoOneUpAdapter mAdapter;
  private String mAlbumName;
  private boolean mAllowPlusOne;
  private AudienceData mAudienceData;
  private String mAuthkey;
  private boolean mAutoPlay;
  private boolean mAutoRefreshDone;
  private int mBackgroundDesiredHeight;
  private int mBackgroundDesiredWidth;
  private MediaRef mBackgroundRef;
  private PhotoHeaderView mBackgroundView;
  private PhotoOneUpCallbacks mCallback;
  private View mCommentButton;
  private MentionMultiAutoCompleteTextView mCommentText;
  private boolean mDisableComments;
  private Boolean mDownloadable;
  private HashSet<String> mFlaggedComments;
  private LinearLayoutWithLayoutNotifications mFooter;
  private PhotoOneUpAnimationController mFooterAnimator;
  private boolean mFullScreen;
  private boolean mIsPlaceholder;
  private PhotoOneUpAnimationController mListAnimator;
  private View mListParent;
  private StreamOneUpListView mListView;
  private int mOperationType = 0;
  private byte[] mPendingBytes;
  private Integer mPendingRequestId;
  private boolean mReadProcessed;
  private Integer mRefreshRequestId;
  private final ServiceListener mServiceListener = new ServiceListener((byte)0);
  private PhotoOneUpAnimationController mTagBarAnimator;
  private View mTagLayout;
  private PhotoTagScroller mTagScroll;
  private TextWatcher mTextWatcher;
  private String mTitle;
  private OneUpTouchHandler mTouchHandler;
  private boolean mUpdateActionBar;

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

  private void doReportComment(String paramString, boolean paramBoolean1, boolean paramBoolean2)
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
      localAlertFragmentDialog.show(getFragmentManager(), "pouf_report_comment");
      return;
      str1 = getString(R.string.stream_one_up_comment_report_dialog_title);
      break;
    }
  }

  private void showProgressDialog(int paramInt)
  {
    showProgressDialog(paramInt, getString(R.string.post_operation_pending));
  }

  private void showProgressDialog(int paramInt, String paramString)
  {
    this.mOperationType = paramInt;
    ProgressFragmentDialog.newInstance(null, paramString, false).show(getFragmentManager(), "pouf_pending");
  }

  private void updateProgressIndicator(HostActionBar paramHostActionBar)
  {
    if (paramHostActionBar == null)
      return;
    label33: PhotoOneUpAdapter localPhotoOneUpAdapter;
    if ((this.mRefreshRequestId != null) || ((this.mAdapter != null) && (this.mAdapter.getCursor() == null)))
    {
      paramHostActionBar.showProgressIndicator();
      if (this.mAdapter == null)
        break label67;
      localPhotoOneUpAdapter = this.mAdapter;
      if (this.mRefreshRequestId == null)
        break label69;
    }
    label67: label69: for (boolean bool = true; ; bool = false)
    {
      localPhotoOneUpAdapter.setLoading(bool);
      break;
      paramHostActionBar.hideProgressIndicator();
      break label33;
      break;
    }
  }

  public final void doDownload(Context paramContext, boolean paramBoolean)
  {
    int i = 2048;
    if (this.mAdapter == null);
    while (true)
    {
      return;
      String str1 = this.mBackgroundRef.getUrl();
      String str2;
      if (FIFEUtil.isFifeHostedUrl(str1))
        if (paramBoolean)
          str2 = FIFEUtil.setImageUrlOptions("d", str1).toString();
      while (true)
      {
        if (str2 == null)
          break label151;
        if (EsLog.isLoggable("StreamOneUp", 3))
          Log.d("StreamOneUp", "Downloading image from: " + str2);
        this.mPendingRequestId = Integer.valueOf(EsService.savePhoto(paramContext, this.mAccount, str2, paramBoolean, this.mAlbumName));
        showProgressDialog(19, getString(R.string.download_photo_pending));
        break;
        str2 = FIFEUtil.setImageUrlSize(i, str1, false);
        continue;
        if (paramBoolean)
          i = -1;
        str2 = ImageProxyUtil.setImageUrlSize(i, str1);
      }
      label151: Toast.makeText(paramContext, getResources().getString(R.string.download_photo_error), 1).show();
    }
  }

  public final void enableImageTransforms(boolean paramBoolean)
  {
    this.mBackgroundView.enableImageTransforms(paramBoolean);
  }

  public final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PHOTO;
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
    case 1:
    }
    while (true)
    {
      return;
      if (paramInt2 == -1)
        this.mPendingBytes = paramIntent.getByteArrayExtra("data");
    }
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if ((paramActivity instanceof PhotoOneUpCallbacks))
    {
      this.mCallback = ((PhotoOneUpCallbacks)paramActivity);
      return;
    }
    throw new IllegalArgumentException("Activity must implement PhotoOneUpCallbacks");
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if (i == R.id.footer_post_button)
      if (this.mCommentText.getText().toString().trim().length() > 0)
        if (this.mPendingRequestId == null)
        {
          recordUserAction(OzActions.ONE_UP_POST_COMMENT);
          Editable localEditable = this.mCommentText.getText();
          Context localContext = getSafeContext();
          String str4 = ApiUtils.buildPostableString$6d7f0b14(localEditable);
          this.mPendingRequestId = Integer.valueOf(EsService.createPhotoComment(localContext, this.mAccount, this.mBackgroundRef.getOwnerGaiaId(), this.mBackgroundRef.getPhotoId(), str4, this.mAuthkey));
          showProgressDialog(32);
        }
    do
    {
      while (true)
      {
        return;
        this.mCommentButton.setEnabled(false);
        continue;
        if (i != R.id.background)
          break;
        if (this.mBackgroundView.isVideo())
        {
          if (this.mBackgroundView.isVideoReady())
          {
            startActivity(Intents.getVideoViewActivityIntent(getActivity(), this.mAccount, this.mBackgroundRef.getOwnerGaiaId(), this.mBackgroundRef.getPhotoId(), this.mBackgroundView.getVideoData()));
          }
          else
          {
            String str3 = getString(R.string.photo_view_video_not_ready);
            Toast.makeText(getActivity(), str3, 1).show();
          }
        }
        else if (this.mBackgroundView.isPanorama())
          startActivity(Intents.getViewPanoramaActivityIntent(getActivity(), this.mAccount, this.mBackgroundRef));
        else
          this.mCallback.toggleFullScreen();
      }
      if (i == R.id.tag_approve)
      {
        Long localLong2 = (Long)paramView.getTag(R.id.tag_shape_id);
        String str2;
        if (((Boolean)paramView.getTag(R.id.tag_is_suggestion)).booleanValue())
          str2 = (String)paramView.getTag(R.id.tag_gaiaid);
        for (this.mPendingRequestId = Integer.valueOf(EsService.suggestedTagApproval(getActivity(), this.mAccount, str2, this.mBackgroundRef.getOwnerGaiaId(), Long.toString(this.mBackgroundRef.getPhotoId()), Long.toString(localLong2.longValue()), true)); ; this.mPendingRequestId = Integer.valueOf(EsService.nameTagApproval(getActivity(), this.mAccount, this.mBackgroundRef.getOwnerGaiaId(), Long.valueOf(this.mBackgroundRef.getPhotoId()), localLong2, true)))
        {
          showProgressDialog(49);
          break;
        }
      }
    }
    while (i != R.id.tag_deny);
    Long localLong1 = (Long)paramView.getTag(R.id.tag_shape_id);
    String str1;
    if (((Boolean)paramView.getTag(R.id.tag_is_suggestion)).booleanValue())
      str1 = (String)paramView.getTag(R.id.tag_gaiaid);
    for (this.mPendingRequestId = Integer.valueOf(EsService.suggestedTagApproval(getActivity(), this.mAccount, str1, this.mBackgroundRef.getOwnerGaiaId(), Long.toString(this.mBackgroundRef.getPhotoId()), Long.toString(localLong1.longValue()), false)); ; this.mPendingRequestId = Integer.valueOf(EsService.nameTagApproval(getActivity(), this.mAccount, this.mBackgroundRef.getOwnerGaiaId(), Long.valueOf(this.mBackgroundRef.getPhotoId()), localLong1, false)))
    {
      showProgressDialog(50);
      break;
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mFlaggedComments = new HashSet();
    Bundle localBundle = getArguments();
    this.mAccount = ((EsAccount)localBundle.getParcelable("account"));
    this.mBackgroundDesiredWidth = localBundle.getInt("photo_width", -1);
    this.mBackgroundDesiredHeight = localBundle.getInt("photo_height", -1);
    this.mAllowPlusOne = localBundle.getBoolean("allow_plusone", true);
    this.mDisableComments = localBundle.getBoolean("disable_photo_comments");
    if (paramBundle != null)
    {
      if (paramBundle.containsKey("pending_request_id"))
        this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("pending_request_id"));
      if (paramBundle.containsKey("refresh_request_id"))
        this.mRefreshRequestId = Integer.valueOf(paramBundle.getInt("refresh_request_id"));
      this.mAudienceData = ((AudienceData)paramBundle.getParcelable("audience_data"));
      String[] arrayOfString = paramBundle.getStringArray("flagged_comments");
      if (arrayOfString != null)
        this.mFlaggedComments.addAll(Arrays.asList(arrayOfString));
      this.mOperationType = paramBundle.getInt("operation_type", 0);
      this.mReadProcessed = paramBundle.getBoolean("read_processed", false);
      this.mFullScreen = paramBundle.getBoolean("full_screen");
      this.mAutoPlay = false;
      this.mBackgroundRef = ((MediaRef)paramBundle.getParcelable("photo_ref"));
      this.mIsPlaceholder = paramBundle.getBoolean("is_placeholder");
    }
    while (true)
    {
      this.mUpdateActionBar = this.mFullScreen;
      return;
      this.mBackgroundRef = ((MediaRef)localBundle.getParcelable("photo_ref"));
      this.mIsPlaceholder = localBundle.getBoolean("is_placeholder");
      if ((localBundle.getBoolean("refresh", false)) || (localBundle.getLong("force_load_id", 0L) == this.mBackgroundRef.getPhotoId()))
        refresh();
      this.mAuthkey = localBundle.getString("auth_key");
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 519746381:
    case 533919674:
    }
    while (true)
    {
      return localObject;
      localObject = new PhotoOneUpLoader(getSafeContext(), this.mAccount, this.mBackgroundRef.getPhotoId(), this.mBackgroundRef.getOwnerGaiaId(), this.mBackgroundRef.getUrl(), this.mDisableComments);
      continue;
      Uri localUri = EsProvider.appendAccountParameter(ContentUris.withAppendedId(EsProvider.PHOTO_SHAPES_BY_PHOTO_ID_URI, this.mBackgroundRef.getPhotoId()), this.mAccount);
      localObject = new EsCursorLoader(getActivity(), localUri, PhotoTagScroller.PhotoShapeQuery.PROJECTION, null, null, "shape_id");
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    int i = 8;
    Context localContext = getSafeContext();
    if (!sResourcesLoaded)
    {
      Resources localResources = localContext.getResources();
      sMaxWidth = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_list_max_width);
      sActionBarHeight = localResources.getDimensionPixelOffset(R.dimen.host_action_bar_height);
      sResourcesLoaded = true;
    }
    View localView1 = paramLayoutInflater.inflate(R.layout.photo_one_up_fragment, paramViewGroup, false);
    this.mListParent = localView1.findViewById(R.id.list_parent);
    this.mListParent.findViewById(R.id.list_expander).setOnClickListener(this);
    this.mListAnimator = new PhotoOneUpAnimationController(this.mListParent, false, false);
    View localView2 = this.mListParent;
    int j;
    String str;
    label208: View localView3;
    label235: label383: View localView5;
    if (this.mFullScreen)
    {
      j = i;
      localView2.setVisibility(j);
      this.mListView = ((StreamOneUpListView)localView1.findViewById(16908298));
      this.mAdapter = new PhotoOneUpAdapter(localContext, null, this, this.mListView);
      this.mListView.setMaxWidth(sMaxWidth);
      this.mListView.setAdapter(this.mAdapter);
      this.mListView.setOnItemClickListener(this);
      this.mListView.setOnMeasureListener(this);
      if (this.mBackgroundRef != null)
        break label598;
      str = null;
      localView3 = localView1.findViewById(R.id.stage);
      if (str != null)
        break label610;
      if (localView3 != null)
        localView3.setVisibility(i);
      this.mTagLayout = localView1.findViewById(R.id.one_up_tag_layout);
      this.mTagLayout.setOnClickListener(this);
      this.mTagScroll = ((PhotoTagScroller)localView1.findViewById(R.id.one_up_tag_list));
      this.mTagScroll.setHeaderView(this.mBackgroundView);
      this.mTagScroll.setExternalOnClickListener(this);
      this.mFooter = ((LinearLayoutWithLayoutNotifications)localView1.findViewById(R.id.footer));
      this.mCommentText = ((MentionMultiAutoCompleteTextView)localView1.findViewById(R.id.footer_text));
      this.mCommentText.setEnabled(false);
      this.mCommentText.setHint(null);
      this.mFooter.setLayoutListener(this);
      this.mFooter.setMaxWidth(sMaxWidth);
      this.mFooterAnimator = new PhotoOneUpAnimationController(this.mFooter, false, true);
      LinearLayoutWithLayoutNotifications localLinearLayoutWithLayoutNotifications = this.mFooter;
      if (!this.mFullScreen)
        break label722;
      localLinearLayoutWithLayoutNotifications.setVisibility(i);
      new CircleNameResolver(localContext, getLoaderManager(), this.mAccount).initLoader();
      this.mCommentText.init(this, this.mAccount, null, null);
      this.mCommentButton = localView1.findViewById(R.id.footer_post_button);
      this.mCommentButton.setOnClickListener(this);
      localView5 = this.mCommentButton;
      if (this.mCommentText.getText().length() <= 0)
        break label728;
    }
    label598: label728: for (boolean bool = true; ; bool = false)
    {
      localView5.setEnabled(bool);
      this.mTextWatcher = new MyTextWatcher(this.mCommentButton, (byte)0);
      this.mCommentText.addTextChangedListener(this.mTextWatcher);
      this.mCommentText.setOnEditorActionListener(new TextView.OnEditorActionListener()
      {
        public final boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          if (paramAnonymousInt == 6)
            PhotoOneUpFragment.this.mCommentButton.performClick();
          for (boolean bool = true; ; bool = false)
            return bool;
        }
      });
      this.mTouchHandler = ((OneUpTouchHandler)localView1.findViewById(R.id.touch_handler));
      this.mTouchHandler.setBackground(this.mBackgroundView);
      this.mTouchHandler.setScrollView(this.mListParent);
      this.mTouchHandler.setTagLayout(this.mTagLayout);
      this.mTouchHandler.setActionBar(getActionBar());
      getLoaderManager().initLoader(519746381, null, this);
      return localView1;
      j = 0;
      break;
      str = this.mBackgroundRef.getUrl();
      break label208;
      label610: if (localView3 != null)
        break label235;
      View localView4 = ((ViewStub)localView1.findViewById(R.id.stage_media)).inflate();
      localView4.findViewById(R.id.loading).setVisibility(0);
      this.mBackgroundView = ((PhotoHeaderView)localView4.findViewById(R.id.background));
      this.mBackgroundView.init(this.mBackgroundRef, this.mIsPlaceholder);
      this.mBackgroundView.setOnClickListener(this);
      this.mBackgroundView.setOnImageListener(this);
      this.mBackgroundView.enableImageTransforms(true);
      ((ExpandingScrollView)this.mListParent.findViewById(R.id.list_expander)).setAlwaysExpanded(false);
      localView4.invalidate();
      break label235;
      i = 0;
      break label383;
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
    this.mBackgroundView.destroy();
    this.mBackgroundView = null;
    super.onDestroyView();
  }

  public final void onDetach()
  {
    this.mCallback = null;
    super.onDetach();
  }

  public final void onDialogCanceled$20f9a4b7(String paramString)
  {
  }

  public final void onDialogListClick$12e92030(int paramInt, Bundle paramBundle)
  {
    ArrayList localArrayList = paramBundle.getIntegerArrayList("comment_action");
    if (localArrayList == null)
      if (EsLog.isLoggable("StreamOneUp", 5))
        Log.w("StreamOneUp", "No actions for comment option dialog");
    while (true)
    {
      return;
      if (paramInt >= localArrayList.size())
      {
        if (EsLog.isLoggable("StreamOneUp", 5))
          Log.w("StreamOneUp", "Option selected outside the action list");
      }
      else
      {
        String str1 = paramBundle.getString("comment_id");
        String str2 = paramBundle.getString("comment_content");
        boolean bool1 = paramBundle.getBoolean("plus_one_by_me");
        long l = paramBundle.getLong("photo_id");
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
          localAlertFragmentDialog.show(getFragmentManager(), "pouf_delete_comment");
          break;
        case 38:
          Context localContext = getSafeContext();
          EsAccount localEsAccount = this.mAccount;
          if (!bool1);
          for (boolean bool2 = true; ; bool2 = false)
          {
            EsService.plusOneComment(localContext, localEsAccount, null, l, str1, null, bool2);
            break;
          }
        case 37:
          Bundle localBundle1 = EsAnalyticsData.createExtras("extra_comment_id", str1);
          recordUserAction(OzActions.ONE_UP_EDIT_COMMENT, localBundle1);
          startActivity(Intents.getEditCommentActivityIntent(getSafeContext(), this.mAccount, null, str1, str2, Long.valueOf(this.mBackgroundRef.getPhotoId()), this.mBackgroundRef.getOwnerGaiaId()));
          break;
        case 34:
          doReportComment(str1, false, false);
          break;
        case 35:
          doReportComment(str1, false, true);
          break;
        case 36:
          doReportComment(str1, true, false);
        }
      }
    }
  }

  public final void onDialogNegativeClick$20f9a4b7(String paramString)
  {
  }

  public final void onDialogPositiveClick(Bundle paramBundle, String paramString)
  {
    long l = this.mBackgroundRef.getPhotoId();
    String str = this.mBackgroundRef.getOwnerGaiaId();
    if ("pouf_delete_photo".equals(paramString))
    {
      ArrayList localArrayList = new ArrayList(1);
      localArrayList.add(Long.valueOf(l));
      FragmentActivity localFragmentActivity = getActivity();
      this.mPendingRequestId = Integer.valueOf(EsService.deletePhotos(localFragmentActivity, this.mAccount, str, localArrayList));
      showProgressDialog(16, localFragmentActivity.getResources().getQuantityString(R.plurals.delete_photo_pending, 1));
    }
    while (true)
    {
      return;
      if ("pouf_report_photo".equals(paramString))
      {
        this.mPendingRequestId = Integer.valueOf(EsService.reportPhotoAbuse(getActivity(), this.mAccount, l, str));
        showProgressDialog(17);
      }
      else if ("pouf_delete_comment".equals(paramString))
      {
        this.mPendingRequestId = Integer.valueOf(EsService.deletePhotoComment(getActivity(), this.mAccount, Long.valueOf(l), paramBundle.getString("comment_id")));
        showProgressDialog(33);
      }
      else if ("pouf_report_comment".equals(paramString))
      {
        this.mPendingRequestId = Integer.valueOf(EsService.reportPhotoComment$3486cdbb(getActivity(), this.mAccount, Long.valueOf(l), paramBundle.getString("comment_id"), paramBundle.getBoolean("delete", false), paramBundle.getBoolean("is_undo", false)));
        showProgressDialog(34);
      }
      else if ("pouf_delete_tag".equals(paramString))
      {
        Long localLong = this.mTagScroll.getMyApprovedShapeId();
        if (localLong != null)
        {
          this.mPendingRequestId = Integer.valueOf(EsService.nameTagApproval(getActivity(), this.mAccount, str, Long.valueOf(l), localLong, false));
          showProgressDialog(48);
        }
      }
    }
  }

  public final void onFullScreenChanged$25decb5(boolean paramBoolean)
  {
    if (!this.mCallback.isFragmentActive(this));
    while (true)
    {
      return;
      this.mFullScreen = paramBoolean;
      if (!this.mFullScreen)
      {
        if (this.mUpdateActionBar)
          adjustActionBarMargins(getActionBar(), false);
        this.mUpdateActionBar = false;
      }
      if (this.mActionBarAnimator != null)
        this.mActionBarAnimator.animate(this.mFullScreen);
      if (this.mListAnimator != null)
        this.mListAnimator.animate(this.mFullScreen);
      if (this.mFooterAnimator != null)
        this.mFooterAnimator.animate(this.mFullScreen);
      if (this.mTagBarAnimator != null)
        this.mTagBarAnimator.animate(this.mFullScreen);
      if (!this.mFullScreen)
        this.mBackgroundView.resetTransformations();
    }
  }

  public final void onImageLoadFinished(PhotoHeaderView paramPhotoHeaderView)
  {
    getView().findViewById(R.id.loading).setVisibility(8);
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

  public final boolean onInterceptMoveLeft$2548a39()
  {
    boolean bool1 = this.mCallback.isFragmentActive(this);
    boolean bool2 = false;
    if (!bool1);
    while (true)
    {
      return bool2;
      if (!this.mFullScreen)
      {
        View localView1 = this.mTouchHandler.getTargetView();
        View localView2 = this.mTagLayout;
        bool2 = false;
        if (localView1 != localView2);
      }
      else
      {
        bool2 = true;
      }
    }
  }

  public final boolean onInterceptMoveRight$2548a39()
  {
    boolean bool1 = this.mCallback.isFragmentActive(this);
    boolean bool2 = false;
    if (!bool1);
    while (true)
    {
      return bool2;
      if (!this.mFullScreen)
      {
        View localView1 = this.mTouchHandler.getTargetView();
        View localView2 = this.mTagLayout;
        bool2 = false;
        if (localView1 != localView2);
      }
      else
      {
        bool2 = true;
      }
    }
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Resources localResources;
    boolean bool2;
    ArrayList localArrayList1;
    ArrayList localArrayList2;
    boolean bool4;
    int i;
    if ((paramView instanceof StreamOneUpCommentView))
    {
      StreamOneUpCommentView localStreamOneUpCommentView = (StreamOneUpCommentView)paramView;
      localResources = getSafeContext().getResources();
      boolean bool1 = this.mAccount.isMyGaiaId(localStreamOneUpCommentView.getAuthorId());
      bool2 = this.mAccount.isMyGaiaId(this.mBackgroundRef.getOwnerGaiaId());
      localArrayList1 = new ArrayList(5);
      localArrayList2 = new ArrayList(5);
      boolean bool3 = localStreamOneUpCommentView.getPlusOneByMe();
      bool4 = localStreamOneUpCommentView.isFlagged();
      if (!bool4)
      {
        if (bool3)
        {
          i = R.string.stream_one_up_comment_option_plusminus;
          localArrayList1.add(localResources.getString(i));
          localArrayList2.add(Integer.valueOf(38));
        }
      }
      else
      {
        if (!bool1)
          break label331;
        localArrayList1.add(localResources.getString(R.string.stream_one_up_comment_option_edit));
        localArrayList2.add(Integer.valueOf(37));
        label155: if ((bool2) || (bool1))
        {
          localArrayList1.add(localResources.getString(R.string.stream_one_up_comment_option_delete));
          localArrayList2.add(Integer.valueOf(33));
        }
        String[] arrayOfString = new String[localArrayList1.size()];
        localArrayList1.toArray(arrayOfString);
        AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(getString(R.string.stream_one_up_comment_options_title), arrayOfString);
        localAlertFragmentDialog.setTargetFragment(this, 0);
        localAlertFragmentDialog.getArguments().putIntegerArrayList("comment_action", localArrayList2);
        localAlertFragmentDialog.getArguments().putString("comment_id", localStreamOneUpCommentView.getCommentId());
        localAlertFragmentDialog.getArguments().putString("comment_content", localStreamOneUpCommentView.getCommentContent());
        localAlertFragmentDialog.getArguments().putBoolean("plus_one_by_me", bool3);
        localAlertFragmentDialog.getArguments().putLong("photo_id", this.mBackgroundRef.getPhotoId());
        localAlertFragmentDialog.show(getFragmentManager(), "pouf_delete_comment");
        localStreamOneUpCommentView.cancelPressedState();
      }
    }
    while (true)
    {
      return;
      i = R.string.stream_one_up_comment_option_plusone;
      break;
      label331: if (bool4)
      {
        localArrayList1.add(localResources.getString(R.string.stream_one_up_comment_option_undo_report));
        localArrayList2.add(Integer.valueOf(35));
        break label155;
      }
      localArrayList1.add(localResources.getString(R.string.stream_one_up_comment_option_report));
      if (bool2)
      {
        localArrayList2.add(Integer.valueOf(36));
        break label155;
      }
      localArrayList2.add(Integer.valueOf(34));
      break label155;
      if (Log.isLoggable("StreamOneUp", 3))
        Log.d("StreamOneUp", "PhotoOneUpFragment.onItemClick: Some other view: " + paramView);
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onLocationClick$75c560e7(DbLocation paramDbLocation)
  {
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
            if (PhotoOneUpFragment.this.getView() == null);
            while (true)
            {
              return;
              ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)PhotoOneUpFragment.this.mListParent.getLayoutParams();
              localMarginLayoutParams.bottomMargin = i;
              PhotoOneUpFragment.this.mListParent.setLayoutParams(localMarginLayoutParams);
            }
          }
        });
      }
    }
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool1;
    if (!this.mCallback.isFragmentActive(this))
      bool1 = false;
    while (true)
    {
      return bool1;
      int i = paramMenuItem.getItemId();
      if (i == R.id.feedback)
      {
        recordUserAction(OzActions.SETTINGS_FEEDBACK);
        GoogleFeedback.launch(getActivity());
        bool1 = true;
      }
      else if (i == R.id.report_abuse)
      {
        AlertFragmentDialog localAlertFragmentDialog3 = AlertFragmentDialog.newInstance(getString(R.string.menu_report_photo), getString(R.string.report_photo_question), getString(R.string.ok), getString(R.string.cancel));
        localAlertFragmentDialog3.setTargetFragment(this, 0);
        localAlertFragmentDialog3.show(getFragmentManager(), "pouf_report_photo");
        bool1 = true;
      }
      else if (i == R.id.share)
      {
        ArrayList localArrayList = new ArrayList(1);
        localArrayList.add(this.mBackgroundRef);
        startActivity(Intents.getPostActivityIntent(getActivity(), this.mAccount, localArrayList));
        FragmentActivity localFragmentActivity = getActivity();
        int j = 0;
        if (localFragmentActivity != null)
        {
          Intent localIntent = localFragmentActivity.getIntent();
          j = 0;
          if (localIntent != null)
          {
            boolean bool2 = TextUtils.isEmpty(localIntent.getStringExtra("notif_id"));
            j = 0;
            if (!bool2)
              j = 1;
          }
        }
        if (j != 0)
          EsAnalytics.recordActionEvent(getSafeContext(), this.mAccount, OzActions.SHARE_INSTANT_UPLOAD_FROM_NOTIFICATION, OzViews.getViewForLogging(getSafeContext()));
        bool1 = true;
      }
      else if (i == R.id.set_profile_photo)
      {
        startActivityForResult(Intents.getPhotoPickerIntent(getActivity(), this.mAccount, null, this.mBackgroundRef, 1), 1);
        bool1 = true;
      }
      else
      {
        if (i == R.id.set_wallpaper)
        {
          PhotoHeaderView localPhotoHeaderView = this.mBackgroundView;
          Bitmap localBitmap = null;
          if (localPhotoHeaderView == null);
          while (true)
          {
            if (localBitmap != null)
            {
              Resources localResources2 = getResources();
              final String str1 = localResources2.getString(R.string.set_wallpaper_photo_success);
              final String str2 = localResources2.getString(R.string.set_wallpaper_photo_error);
              showProgressDialog(20, getString(R.string.set_wallpaper_photo_pending));
              new AsyncTask()
              {
                private Boolean doInBackground(Bitmap[] paramAnonymousArrayOfBitmap)
                {
                  try
                  {
                    WallpaperManager.getInstance(PhotoOneUpFragment.this.getActivity()).setBitmap(paramAnonymousArrayOfBitmap[0]);
                    localBoolean = Boolean.TRUE;
                    return localBoolean;
                  }
                  catch (IOException localIOException)
                  {
                    while (true)
                    {
                      if (EsLog.isLoggable("StreamOneUp", 6))
                        Log.e("StreamOneUp", "Exception setting wallpaper", localIOException);
                      Boolean localBoolean = Boolean.FALSE;
                    }
                  }
                }
              }
              .execute(new Bitmap[] { localBitmap });
            }
            bool1 = true;
            break;
            localBitmap = this.mBackgroundView.getBitmap();
          }
        }
        if (i == R.id.delete)
        {
          Resources localResources1 = getResources();
          Uri localUri;
          if (this.mBackgroundRef.getUrl() != null)
          {
            localUri = Uri.parse(this.mBackgroundRef.getUrl());
            label422: if (!MediaStoreUtils.isMediaStoreUri(localUri))
              break label509;
            label430: if (localUri != null)
              break label515;
          }
          label515: for (int k = R.plurals.delete_remote_photo_dialog_message; ; k = R.plurals.delete_local_photo_dialog_message)
          {
            AlertFragmentDialog localAlertFragmentDialog2 = AlertFragmentDialog.newInstance(localResources1.getQuantityString(R.plurals.delete_photo_dialog_title, 1), localResources1.getQuantityString(k, 1), localResources1.getQuantityString(R.plurals.delete_photo, 1), getString(R.string.cancel));
            localAlertFragmentDialog2.setTargetFragment(this, 0);
            localAlertFragmentDialog2.show(getFragmentManager(), "pouf_delete_photo");
            bool1 = true;
            break;
            localUri = null;
            break label422;
            label509: localUri = null;
            break label430;
          }
        }
        if (i == R.id.download)
        {
          doDownload(getSafeContext(), true);
          bool1 = true;
        }
        else if (i == R.id.refresh)
        {
          refresh();
          bool1 = true;
        }
        else if (i == R.id.remove_tag)
        {
          AlertFragmentDialog localAlertFragmentDialog1 = AlertFragmentDialog.newInstance(getString(R.string.menu_remove_tag), getString(R.string.remove_tag_question), getString(R.string.ok), getString(R.string.cancel));
          localAlertFragmentDialog1.setTargetFragment(this, 0);
          localAlertFragmentDialog1.show(getFragmentManager(), "pouf_delete_tag");
          bool1 = true;
        }
        else
        {
          bool1 = false;
        }
      }
    }
  }

  public final void onPause()
  {
    super.onPause();
    if (this.mBackgroundView != null)
      PhotoHeaderView.onStop();
    if (this.mListView != null)
      for (int i = -1 + this.mListView.getChildCount(); i >= 0; i--)
        if ((this.mListView.getChildAt(i) instanceof OneUpBaseView))
          OneUpBaseView.onStop();
    this.mCallback.removeScreenListener(this);
    this.mCallback.removeMenuItemListener(this);
    EsService.unregisterListener(this.mServiceListener);
  }

  public final void onPlaceClick(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      startActivity(Intents.getProfileActivityByGaiaIdIntent(getActivity(), this.mAccount, paramString, null));
  }

  public final void onPlusOne(String paramString, DbPlusOneData paramDbPlusOneData)
  {
    String str = this.mBackgroundRef.getOwnerGaiaId();
    long l = this.mBackgroundRef.getPhotoId();
    if (!EsService.isPhotoPlusOnePending(str, paramString, l))
      if ((paramDbPlusOneData != null) && (paramDbPlusOneData.isPlusOnedByMe()))
        break label60;
    label60: for (boolean bool = true; ; bool = false)
    {
      EsService.photoPlusOne(getSafeContext(), this.mAccount, str, paramString, l, bool);
      return;
    }
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    paramHostActionBar.showRefreshButton();
    paramHostActionBar.showTitle(this.mTitle);
    updateProgressIndicator(paramHostActionBar);
    if (this.mActionBarAnimator == null)
      this.mActionBarAnimator = new PhotoOneUpAnimationController(paramHostActionBar, true, true);
    if (this.mUpdateActionBar)
    {
      adjustActionBarMargins(paramHostActionBar, true);
      paramHostActionBar.setVisibility(8);
    }
    while (true)
    {
      return;
      paramHostActionBar.setVisibility(0);
    }
  }

  public final void onPrepareOptionsMenu(Menu paramMenu)
  {
    if ((this.mCallback != null) && (!this.mCallback.isFragmentActive(this)))
      return;
    super.onPrepareOptionsMenu(paramMenu);
    long l = this.mBackgroundRef.getPhotoId();
    String str = this.mBackgroundRef.getOwnerGaiaId();
    Long localLong;
    label53: boolean bool1;
    label61: Uri localUri;
    label83: int i;
    label100: int j;
    label117: int k;
    label131: boolean bool2;
    label159: int m;
    label212: boolean bool4;
    label220: boolean bool5;
    label238: boolean bool6;
    label279: MenuItem localMenuItem2;
    if (this.mTagScroll == null)
    {
      localLong = null;
      if (localLong == null)
        break label422;
      bool1 = true;
      if (this.mBackgroundRef.getUrl() == null)
        break label428;
      localUri = Uri.parse(this.mBackgroundRef.getUrl());
      if ((l != 0L) || (!MediaStoreUtils.isMediaStoreUri(localUri)))
        break label434;
      i = 1;
      if ((l == 0L) || (MediaStoreUtils.isMediaStoreUri(localUri)))
        break label440;
      j = 1;
      if ((l != 0L) || (localUri == null))
        break label446;
      k = 1;
      if ((!this.mAccount.isMyGaiaId(str)) && ((str != null) || (!MediaStoreUtils.isMediaStoreUri(localUri))))
        break label452;
      bool2 = true;
      boolean bool3 = "camerasync".equals(getArguments().getString("stream_id"));
      if (k == 0)
      {
        if (j == 0)
          break label464;
        if (!bool2)
        {
          if ((this.mDownloadable == null) || (!this.mDownloadable.booleanValue()))
            break label458;
          m = 1;
          if (m == 0)
            break label464;
        }
      }
      bool4 = true;
      if ((!bool2) || ((j == 0) && (i == 0)))
        break label470;
      bool5 = true;
      paramMenu.findItem(R.id.share).setVisible(bool3);
      MenuItem localMenuItem1 = paramMenu.findItem(R.id.set_profile_photo);
      if ((!bool2) && (!bool1))
        break label476;
      bool6 = true;
      localMenuItem1.setVisible(bool6);
      paramMenu.findItem(R.id.set_wallpaper).setVisible(bool2);
      paramMenu.findItem(R.id.delete).setVisible(bool5);
      paramMenu.findItem(R.id.download).setVisible(bool4);
      localMenuItem2 = paramMenu.findItem(R.id.report_abuse);
      if ((bool2) || (j == 0))
        break label482;
    }
    label422: label428: label434: label440: label446: label452: label458: label464: label470: label476: label482: for (boolean bool7 = true; ; bool7 = false)
    {
      localMenuItem2.setVisible(bool7);
      paramMenu.findItem(R.id.remove_tag).setVisible(bool1);
      paramMenu.findItem(R.id.feedback).setVisible(true);
      break;
      localLong = this.mTagScroll.getMyApprovedShapeId();
      break label53;
      bool1 = false;
      break label61;
      localUri = null;
      break label83;
      i = 0;
      break label100;
      j = 0;
      break label117;
      k = 0;
      break label131;
      bool2 = false;
      break label159;
      m = 0;
      break label212;
      bool4 = false;
      break label220;
      bool5 = false;
      break label238;
      bool6 = false;
      break label279;
    }
  }

  public final void onResume()
  {
    super.onResume();
    if (this.mBackgroundView != null)
      PhotoHeaderView.onStart();
    if (this.mListView != null)
      for (int i = -1 + this.mListView.getChildCount(); i >= 0; i--)
        if ((this.mListView.getChildAt(i) instanceof OneUpBaseView))
          OneUpBaseView.onStart();
    EsService.registerListener(this.mServiceListener);
    this.mCallback.addScreenListener(this);
    this.mCallback.addMenuItemListener(this);
    if ((this.mPendingRequestId != null) && (!EsService.isRequestPending(this.mPendingRequestId.intValue())))
    {
      ServiceResult localServiceResult = EsService.removeResult(this.mPendingRequestId.intValue());
      this.mServiceListener.handleServiceCallback(this.mPendingRequestId.intValue(), localServiceResult);
    }
    if ((this.mRefreshRequestId != null) && (!EsService.isRequestPending(this.mRefreshRequestId.intValue())))
    {
      EsService.removeResult(this.mRefreshRequestId.intValue());
      this.mRefreshRequestId = null;
    }
    updateProgressIndicator(getActionBar());
    invalidateActionBar();
    if (this.mPendingBytes != null)
    {
      if (this.mPendingRequestId == null)
        break label228;
      if (Log.isLoggable("StreamOneUp", 5))
        Log.w("StreamOneUp", "Both a pending profile image and an existing request");
    }
    while (true)
    {
      this.mPendingBytes = null;
      return;
      label228: byte[] arrayOfByte = this.mPendingBytes;
      this.mPendingRequestId = Integer.valueOf(EsService.uploadProfilePhoto(getActivity(), this.mAccount, arrayOfByte));
      showProgressDialog(21, getString(R.string.set_profile_photo_pending));
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mPendingRequestId != null)
      paramBundle.putInt("pending_request_id", this.mPendingRequestId.intValue());
    if (this.mRefreshRequestId != null)
      paramBundle.putInt("refresh_request_id", this.mRefreshRequestId.intValue());
    if (this.mAudienceData != null)
      paramBundle.putParcelable("audience_data", this.mAudienceData);
    if (!this.mFlaggedComments.isEmpty())
    {
      String[] arrayOfString = new String[this.mFlaggedComments.size()];
      this.mFlaggedComments.toArray(arrayOfString);
      paramBundle.putStringArray("flagged_comments", arrayOfString);
    }
    paramBundle.putParcelable("photo_ref", this.mBackgroundRef);
    paramBundle.putInt("operation_type", this.mOperationType);
    paramBundle.putBoolean("read_processed", this.mReadProcessed);
    paramBundle.putBoolean("full_screen", this.mFullScreen);
    paramBundle.putBoolean("is_placeholder", this.mIsPlaceholder);
  }

  protected final void onSetArguments(Bundle paramBundle)
  {
    super.onSetArguments(paramBundle);
    this.mAutoPlay = paramBundle.getBoolean("auto_play_music", false);
  }

  public final void onSkyjamBuyClick(String paramString)
  {
  }

  public final void onSkyjamListenClick(String paramString)
  {
  }

  public final void onSourceAppContentClick(String paramString1, List<String> paramList, String paramString2, String paramString3, String paramString4)
  {
  }

  public final void onSpanClick(URLSpan paramURLSpan)
  {
    String str1 = paramURLSpan.getURL();
    Context localContext = getSafeContext();
    if (str1.startsWith("https://plus.google.com/s/%23"))
    {
      String str2 = "#" + str1.substring(29);
      startActivity(Intents.getPostSearchActivityIntent(localContext, this.mAccount, str2));
    }
    while (true)
    {
      return;
      if (Intents.isProfileUrl(paramURLSpan.getURL()))
      {
        Bundle localBundle = EsAnalyticsData.createExtras("extra_gaia_id", Intents.getParameter(str1, "pid="));
        recordUserAction(OzActions.ONE_UP_SELECT_PERSON, localBundle);
      }
      Intents.viewContent(getActivity(), this.mAccount, str1);
    }
  }

  public final void onSquareClick(String paramString1, String paramString2)
  {
  }

  public final void onUserImageClick(String paramString1, String paramString2)
  {
    Context localContext = getSafeContext();
    Bundle localBundle = EsAnalyticsData.createExtras("extra_gaia_id", paramString1);
    recordUserAction(OzActions.ONE_UP_SELECT_AUTHOR, localBundle);
    startActivity(Intents.getProfileActivityByGaiaIdIntent(localContext, this.mAccount, paramString1, null));
  }

  public final void onViewActivated()
  {
    boolean bool = this.mCallback.isFragmentActive(this);
    if (bool)
      this.mCallback.onFragmentVisible(this);
    if (this.mBackgroundView != null)
      this.mBackgroundView.doAnimate(bool);
  }

  public final void recordNavigationAction()
  {
  }

  public final void refresh()
  {
    super.refresh();
    if (this.mRefreshRequestId == null)
      if (this.mBackgroundRef.getPhotoId() != 0L)
        break label57;
    label57: for (this.mRefreshRequestId = Integer.valueOf(EsService.getPhotoSettings(getSafeContext(), this.mAccount, this.mBackgroundRef.getOwnerGaiaId())); ; this.mRefreshRequestId = Integer.valueOf(EsService.getPhoto(getSafeContext(), this.mAccount, this.mBackgroundRef.getOwnerGaiaId(), this.mBackgroundRef.getPhotoId(), this.mAuthkey)))
    {
      updateProgressIndicator(getActionBar());
      return;
    }
  }

  public final void setTitle(String paramString)
  {
    this.mTitle = paramString;
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
      Integer localInteger = PhotoOneUpFragment.this.mPendingRequestId;
      boolean bool = false;
      if (localInteger != null)
      {
        int i = PhotoOneUpFragment.this.mPendingRequestId.intValue();
        bool = false;
        if (i == paramInt);
      }
      else
      {
        return bool;
      }
      PhotoOneUpFragment.access$1602(PhotoOneUpFragment.this, null);
      PhotoOneUpFragment.access$700(PhotoOneUpFragment.this);
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      {
        int j;
        switch (PhotoOneUpFragment.this.mOperationType)
        {
        default:
          j = R.string.operation_failed;
        case 18:
        case 16:
        case 17:
        case 32:
        case 33:
        case 34:
        case 37:
        case 49:
        case 50:
        case 48:
        case 21:
        }
        while (true)
        {
          Toast.makeText(PhotoOneUpFragment.this.getSafeContext(), j, 0).show();
          bool = false;
          break;
          j = R.string.reshare_post_error;
          continue;
          j = R.string.remove_photo_error;
          continue;
          j = R.string.report_photo_error;
          continue;
          j = R.string.comment_post_error;
          continue;
          j = R.string.comment_delete_error;
          continue;
          j = R.string.comment_moderate_error;
          continue;
          j = R.string.comment_edit_error;
          continue;
          j = R.string.photo_tag_approve_error;
          continue;
          j = R.string.photo_tag_deny_error;
          continue;
          j = R.string.photo_tag_deny_error;
          continue;
          j = R.string.set_profile_photo_error;
        }
      }
      switch (PhotoOneUpFragment.this.mOperationType)
      {
      default:
      case 16:
      case 17:
      }
      while (true)
      {
        bool = true;
        break;
        PhotoOneUpFragment.this.getActivity().finish();
      }
    }

    public final void onCreatePhotoCommentComplete$6a63df5(int paramInt, ServiceResult paramServiceResult)
    {
      if ((handleServiceCallback(paramInt, paramServiceResult)) && (PhotoOneUpFragment.this.mCommentText != null))
        PhotoOneUpFragment.this.mCommentText.setText(null);
    }

    public final void onDeletePhotoCommentsComplete$6a63df5(int paramInt, ServiceResult paramServiceResult)
    {
      handleServiceCallback(paramInt, paramServiceResult);
    }

    public final void onDeletePhotosComplete$5d3076b3(int paramInt, ServiceResult paramServiceResult)
    {
      if ((PhotoOneUpFragment.this.mPendingRequestId == null) || (PhotoOneUpFragment.this.mPendingRequestId.intValue() != paramInt));
      while (true)
      {
        return;
        PhotoOneUpFragment.access$1602(PhotoOneUpFragment.this, null);
        if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        {
          PhotoOneUpFragment.access$700(PhotoOneUpFragment.this);
          Toast.makeText(PhotoOneUpFragment.this.getSafeContext(), R.string.remove_photo_error, 1).show();
        }
        else
        {
          ArrayList localArrayList = new ArrayList(1);
          localArrayList.add(PhotoOneUpFragment.this.mBackgroundRef);
          PhotoOneUpFragment.access$1602(PhotoOneUpFragment.this, Integer.valueOf(EsService.deleteLocalPhotos(PhotoOneUpFragment.this.getSafeContext(), localArrayList)));
        }
      }
    }

    public final void onEditPhotoCommentComplete$6a63df5(int paramInt, ServiceResult paramServiceResult)
    {
      handleServiceCallback(paramInt, paramServiceResult);
    }

    public final void onGetPhoto$4894d499(int paramInt, long paramLong)
    {
      if ((PhotoOneUpFragment.this.mRefreshRequestId == null) || (PhotoOneUpFragment.this.mRefreshRequestId.intValue() != paramInt));
      while (true)
      {
        return;
        PhotoOneUpFragment.access$802(PhotoOneUpFragment.this, null);
        PhotoOneUpFragment.this.updateProgressIndicator(PhotoOneUpFragment.this.getActionBar());
        PhotoOneUpFragment.this.invalidateActionBar();
      }
    }

    public final void onGetPhotoSettings$6e3d3b8d(int paramInt, boolean paramBoolean)
    {
      if ((PhotoOneUpFragment.this.mRefreshRequestId == null) || (PhotoOneUpFragment.this.mRefreshRequestId.intValue() != paramInt));
      while (true)
      {
        return;
        PhotoOneUpFragment.access$802(PhotoOneUpFragment.this, null);
        PhotoOneUpFragment.access$1102(PhotoOneUpFragment.this, Boolean.valueOf(false));
        PhotoOneUpFragment.this.updateProgressIndicator(PhotoOneUpFragment.this.getActionBar());
        PhotoOneUpFragment.this.invalidateActionBar();
      }
    }

    public final void onLocalPhotoDelete(int paramInt, ArrayList<MediaRef> paramArrayList, ServiceResult paramServiceResult)
    {
      if (!handleServiceCallback(paramInt, paramServiceResult))
      {
        Iterator localIterator = paramArrayList.iterator();
        while (localIterator.hasNext())
        {
          MediaRef localMediaRef = (MediaRef)localIterator.next();
          PhotoOneUpCallbacks localPhotoOneUpCallbacks = PhotoOneUpFragment.this.mCallback;
          localMediaRef.getPhotoId();
          localPhotoOneUpCallbacks.onPhotoRemoved$1349ef();
        }
      }
    }

    public final void onNameTagApprovalComplete$4894d499(int paramInt, long paramLong, ServiceResult paramServiceResult)
    {
      if (!handleServiceCallback(paramInt, paramServiceResult))
        PhotoOneUpFragment.this.mCallback.onPhotoRemoved$1349ef();
    }

    public final void onPhotoPlusOneComplete$4cb07f77(int paramInt, boolean paramBoolean, ServiceResult paramServiceResult)
    {
      if ((PhotoOneUpFragment.this.mPendingRequestId == null) || (PhotoOneUpFragment.this.mPendingRequestId.intValue() != paramInt));
      do
      {
        return;
        PhotoOneUpFragment.access$1602(PhotoOneUpFragment.this, null);
      }
      while ((paramServiceResult == null) || (!paramServiceResult.hasError()));
      Context localContext = PhotoOneUpFragment.this.getSafeContext();
      if (paramBoolean);
      for (int i = R.string.plusone_error; ; i = R.string.delete_plusone_error)
      {
        Toast.makeText(localContext, i, 1).show();
        break;
      }
    }

    public final void onPlusOneComment$56b78e3(boolean paramBoolean, ServiceResult paramServiceResult)
    {
      Context localContext;
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      {
        localContext = PhotoOneUpFragment.this.getSafeContext();
        if (!paramBoolean)
          break label39;
      }
      label39: for (int i = R.string.plusone_error; ; i = R.string.delete_plusone_error)
      {
        Toast.makeText(localContext, i, 0).show();
        return;
      }
    }

    public final void onReportPhotoCommentsComplete$141714ed(int paramInt, String paramString, boolean paramBoolean, ServiceResult paramServiceResult)
    {
      if (handleServiceCallback(paramInt, paramServiceResult))
      {
        if (!paramBoolean)
          break label26;
        PhotoOneUpFragment.this.mAdapter.removeFlaggedComment(paramString);
      }
      while (true)
      {
        return;
        label26: PhotoOneUpFragment.this.mAdapter.addFlaggedComment(paramString);
      }
    }

    public final void onReportPhotoComplete$4894d499(int paramInt, ServiceResult paramServiceResult)
    {
      handleServiceCallback(paramInt, paramServiceResult);
    }

    public final void onSavePhoto(int paramInt, File paramFile, boolean paramBoolean, String paramString1, String paramString2, ServiceResult paramServiceResult)
    {
      if ((PhotoOneUpFragment.this.mPendingRequestId == null) || (PhotoOneUpFragment.this.mPendingRequestId.intValue() != paramInt));
      while (true)
      {
        return;
        PhotoOneUpFragment.access$1602(PhotoOneUpFragment.this, null);
        PhotoOneUpFragment.access$700(PhotoOneUpFragment.this);
        if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        {
          if (EsLog.isLoggable("StreamOneUp", 6))
          {
            if (paramServiceResult.getException() != null)
              Log.e("StreamOneUp", "Could not download image", paramServiceResult.getException());
          }
          else
            label86: if (!paramBoolean)
              break label167;
          label167: for (int i = R.id.photo_view_download_full_failed_dialog; ; i = R.id.photo_view_download_nonfull_failed_dialog)
          {
            Bundle localBundle = new Bundle();
            localBundle.putString("tag", PhotoOneUpFragment.this.getTag());
            PhotoOneUpFragment.this.getActivity().showDialog(i, localBundle);
            break;
            Log.e("StreamOneUp", "Could not download image: " + paramServiceResult.getErrorCode());
            break label86;
          }
        }
        FragmentActivity localFragmentActivity = PhotoOneUpFragment.this.getActivity();
        if ((paramFile != null) && (paramFile.exists()))
          PhotoOneUpFragment.access$2200(PhotoOneUpFragment.this, localFragmentActivity, paramFile, paramString1, paramString2);
        Toast.makeText(localFragmentActivity, R.string.download_photo_success, 1).show();
      }
    }

    public final void onTagSuggestionApprovalComplete$63505a2b(int paramInt, String paramString, ServiceResult paramServiceResult)
    {
      if (!handleServiceCallback(paramInt, paramServiceResult))
      {
        PhotoOneUpCallbacks localPhotoOneUpCallbacks = PhotoOneUpFragment.this.mCallback;
        Long.valueOf(paramString).longValue();
        localPhotoOneUpCallbacks.onPhotoRemoved$1349ef();
      }
    }

    public final void onUploadProfilePhotoComplete$6a63df5(int paramInt, ServiceResult paramServiceResult)
    {
      handleServiceCallback(paramInt, paramServiceResult);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PhotoOneUpFragment
 * JD-Core Version:    0.6.2
 */