package com.google.android.apps.plus.phone;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.InstrumentedActivity;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.fragments.PhotoOneUpCallbacks;
import com.google.android.apps.plus.fragments.PhotoOneUpFragment;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.HostActionBar.HostActionBarListener;
import com.google.android.apps.plus.views.HostActionBar.OnUpButtonClickListener;
import com.google.android.apps.plus.views.PhotoViewPager;
import com.google.android.apps.plus.views.PhotoViewPager.InterceptType;
import com.google.android.apps.plus.views.PhotoViewPager.OnInterceptTouchListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PhotoOneUpActivity extends InstrumentedActivity
  implements LoaderManager.LoaderCallbacks<Cursor>, ViewPager.OnPageChangeListener, PhotoOneUpCallbacks, EsFragmentPagerAdapter.OnFragmentPagerListener, HostActionBar.HostActionBarListener, HostActionBar.OnUpButtonClickListener, PhotoViewPager.OnInterceptTouchListener
{
  private static final String[] EVENT_NAME_PROJECTION = { "name" };
  private EsAccount mAccount;
  private HostActionBar mActionBar;
  private PhotoPagerAdapter mAdapter;
  private int mAlbumCount = -1;
  private String mAlbumId;
  private String mAlbumName;
  private String mAuthkey;
  private int mCurrentIndex;
  private MediaRef mCurrentRef;
  private String mEventId;
  private DialogInterface.OnClickListener mFailedListener = new DialogInterface.OnClickListener()
  {
    public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
    {
      paramAnonymousDialogInterface.dismiss();
    }
  };
  private boolean mFragmentIsLoading;
  private boolean mFullScreen;
  private HostedFragment mHostedFragment;
  private boolean mIsEmpty;
  private boolean mIsPaused = true;
  private boolean mKeyboardIsVisible;
  private MediaRef[] mMediaRefs;
  private Set<OnMenuItemListener> mMenuItemListeners = new HashSet();
  private String mOwnerGaiaId;
  private int mPageHint = -1;
  private String mPhotoOfUserGaiaId;
  private MediaRef mPhotoRef;
  private String mPhotoUrl;
  private boolean mRestartLoader;
  private View mRootView;
  private Set<OnScreenListener> mScreenListeners = new HashSet();
  private String mStreamId;
  private PhotoViewPager mViewPager;

  private void updateTitleAndSubtitle()
  {
    int i = 1 + this.mViewPager.getCurrentItem();
    int j;
    String str;
    if (this.mAlbumCount >= 0)
    {
      j = 1;
      if ((!this.mIsEmpty) && (j != 0) && (i > 0))
        break label94;
      if (this.mAlbumName == null)
        break label80;
      str = this.mAlbumName;
    }
    while (true)
    {
      if ((this.mHostedFragment instanceof PhotoOneUpFragment))
        ((PhotoOneUpFragment)this.mHostedFragment).setTitle(str);
      this.mActionBar.invalidateActionBar();
      return;
      j = 0;
      break;
      label80: str = getResources().getString(R.string.photo_view_default_title);
      continue;
      label94: Resources localResources = getResources();
      int k = R.string.photo_view_count;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(i);
      arrayOfObject[1] = Integer.valueOf(this.mAlbumCount);
      str = localResources.getString(k, arrayOfObject);
    }
  }

  private void updateView(View paramView)
  {
    if (paramView == null);
    while (true)
    {
      return;
      if ((this.mFragmentIsLoading) || ((this.mAdapter.getCursor() == null) && (!this.mIsEmpty)))
      {
        paramView.findViewById(R.id.photo_activity_empty_text).setVisibility(8);
        paramView.findViewById(R.id.photo_activity_empty_progress).setVisibility(0);
        paramView.findViewById(R.id.photo_activity_empty).setVisibility(0);
      }
      else if (!this.mIsEmpty)
      {
        paramView.findViewById(R.id.photo_activity_empty).setVisibility(8);
      }
      else
      {
        String str = getResources().getString(R.string.camera_photo_error);
        paramView.findViewById(R.id.photo_activity_empty_progress).setVisibility(8);
        TextView localTextView = (TextView)paramView.findViewById(R.id.photo_activity_empty_text);
        localTextView.setText(str);
        localTextView.setVisibility(0);
        paramView.findViewById(R.id.photo_activity_empty).setVisibility(0);
      }
    }
  }

  public final void addMenuItemListener(OnMenuItemListener paramOnMenuItemListener)
  {
    this.mMenuItemListeners.add(paramOnMenuItemListener);
  }

  public final void addScreenListener(OnScreenListener paramOnScreenListener)
  {
    this.mScreenListeners.add(paramOnScreenListener);
  }

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PHOTO;
  }

  public final boolean isFragmentActive(Fragment paramFragment)
  {
    PhotoViewPager localPhotoViewPager = this.mViewPager;
    boolean bool = false;
    if (localPhotoViewPager != null)
    {
      PhotoPagerAdapter localPhotoPagerAdapter = this.mAdapter;
      bool = false;
      if (localPhotoPagerAdapter != null)
        break label26;
    }
    while (true)
    {
      return bool;
      label26: int i = this.mViewPager.getCurrentItem();
      int j = this.mAdapter.getItemPosition(paramFragment);
      bool = false;
      if (i == j)
        bool = true;
    }
  }

  public final void onActionBarInvalidated()
  {
    if ((this.mActionBar != null) && (this.mHostedFragment != null))
    {
      this.mActionBar.reset();
      this.mHostedFragment.attachActionBar(this.mActionBar);
      this.mActionBar.commit();
    }
  }

  public final void onActionButtonClicked(int paramInt)
  {
    if (this.mHostedFragment != null)
      this.mHostedFragment.onActionButtonClicked(paramInt);
  }

  public void onBackPressed()
  {
    if (this.mFullScreen)
      toggleFullScreen();
    while (true)
    {
      return;
      super.onBackPressed();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = getIntent();
    this.mAccount = ((EsAccount)localIntent.getParcelableExtra("account"));
    String str1 = null;
    int i = -1;
    if (paramBundle != null)
    {
      i = paramBundle.getInt("com.google.android.apps.plus.PhotoViewFragment.ITEM", -1);
      this.mFullScreen = paramBundle.getBoolean("com.google.android.apps.plus.PhotoViewFragment.FULLSCREEN", false);
      this.mCurrentRef = ((MediaRef)paramBundle.getParcelable("com.google.android.apps.plus.PhotoViewFragment.CURRENT_REF"));
      if (localIntent.hasExtra("event_id"))
        this.mEventId = localIntent.getStringExtra("event_id");
      if (!localIntent.hasExtra("album_name"))
        break label245;
      this.mAlbumName = localIntent.getStringExtra("album_name");
    }
    while (true)
    {
      if (localIntent.hasExtra("owner_id"))
        this.mOwnerGaiaId = localIntent.getStringExtra("owner_id");
      if (!localIntent.hasExtra("mediarefs"))
        break label269;
      Parcelable[] arrayOfParcelable = localIntent.getParcelableArrayExtra("mediarefs");
      this.mMediaRefs = new MediaRef[arrayOfParcelable.length];
      for (int j = 0; j < arrayOfParcelable.length; j++)
        this.mMediaRefs[j] = ((MediaRef)arrayOfParcelable[j]);
      String str2 = localIntent.getStringExtra("notif_id");
      if (str2 != null)
        EsService.markNotificationAsRead(this, this.mAccount, str2);
      boolean bool1 = localIntent.hasExtra("refresh_album_id");
      str1 = null;
      if (!bool1)
        break;
      str1 = localIntent.getStringExtra("refresh_album_id");
      break;
      label245: if (this.mEventId == null)
        this.mAlbumName = getResources().getString(R.string.photo_view_default_title);
    }
    label269: if (localIntent.hasExtra("album_id"))
      this.mAlbumId = localIntent.getStringExtra("album_id");
    if (localIntent.hasExtra("stream_id"))
      this.mStreamId = localIntent.getStringExtra("stream_id");
    if (localIntent.hasExtra("photos_of_user_id"))
      this.mPhotoOfUserGaiaId = localIntent.getStringExtra("photos_of_user_id");
    if (localIntent.hasExtra("photo_url"))
      this.mPhotoUrl = localIntent.getStringExtra("photo_url");
    if ((localIntent.hasExtra("photo_ref")) && (i < 0))
      this.mPhotoRef = ((MediaRef)localIntent.getParcelableExtra("photo_ref"));
    if ((localIntent.hasExtra("page_hint")) && (i < 0))
      this.mPageHint = localIntent.getIntExtra("page_hint", -1);
    if ((localIntent.hasExtra("photo_index")) && (i < 0))
      i = localIntent.getIntExtra("photo_index", -1);
    if (localIntent.hasExtra("auth_key"))
      this.mAuthkey = localIntent.getStringExtra("auth_key");
    this.mCurrentIndex = i;
    if ((str1 != null) && (this.mOwnerGaiaId != null))
    {
      EsAccount localEsAccount = this.mAccount;
      String str3 = this.mOwnerGaiaId;
      String str4 = this.mAuthkey;
      EsService.getAlbumPhotos(this, localEsAccount, str1, str3, str4);
    }
    setContentView(R.layout.photo_one_up_activity);
    this.mActionBar = ((HostActionBar)findViewById(R.id.title_bar));
    this.mActionBar.setOnUpButtonClickListener(this);
    this.mActionBar.setHostActionBarListener(this);
    this.mActionBar.setUpButtonContentDescription(getString(R.string.nav_up_content_description));
    this.mRootView = findViewById(R.id.photo_activity_root_view);
    if (localIntent.hasExtra("force_load_id"));
    for (Long localLong = Long.valueOf(localIntent.getLongExtra("force_load_id", 0L)); ; localLong = null)
    {
      boolean bool2 = localIntent.getBooleanExtra("allow_plusone", true);
      boolean bool3 = localIntent.getBooleanExtra("disable_photo_comments", false);
      this.mAdapter = new PhotoPagerAdapter(this, getSupportFragmentManager(), null, this.mAccount, localLong, this.mStreamId, this.mEventId, this.mAlbumName, bool2, bool3);
      this.mAdapter.setFragmentPagerListener(this);
      this.mViewPager = ((PhotoViewPager)findViewById(R.id.photo_view_pager));
      this.mViewPager.setAdapter(this.mAdapter);
      this.mViewPager.setOnPageChangeListener(this);
      this.mViewPager.setOnInterceptTouchListener(this);
      getSupportLoaderManager().initLoader(2131361833, null, this);
      updateView(this.mRootView);
      this.mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
      {
        public final void onGlobalLayout()
        {
          if (PhotoOneUpActivity.this.mRootView.getRootView().getHeight() - PhotoOneUpActivity.this.mRootView.getHeight() > 100)
          {
            PhotoOneUpActivity.access$102(PhotoOneUpActivity.this, true);
            Iterator localIterator2 = PhotoOneUpActivity.this.mScreenListeners.iterator();
            while (localIterator2.hasNext())
              ((PhotoOneUpActivity.OnScreenListener)localIterator2.next()).enableImageTransforms(false);
          }
          PhotoOneUpActivity.access$102(PhotoOneUpActivity.this, false);
          Iterator localIterator1 = PhotoOneUpActivity.this.mScreenListeners.iterator();
          while (localIterator1.hasNext())
            ((PhotoOneUpActivity.OnScreenListener)localIterator1.next()).enableImageTransforms(true);
        }
      });
      return;
    }
  }

  protected Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    String str = paramBundle.getString("tag");
    Object localObject;
    if (paramInt == R.id.photo_view_pending_dialog)
    {
      localObject = new ProgressDialog(this);
      ((ProgressDialog)localObject).setMessage(paramBundle.getString("dialog_message"));
      ((ProgressDialog)localObject).setProgressStyle(0);
      ((ProgressDialog)localObject).setCancelable(false);
    }
    while (true)
    {
      return localObject;
      if (paramInt == R.id.photo_view_download_full_failed_dialog)
      {
        RetryDialogListener localRetryDialogListener = new RetryDialogListener(str);
        AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(this);
        localBuilder1.setMessage(R.string.download_photo_retry).setPositiveButton(R.string.yes, localRetryDialogListener).setNegativeButton(R.string.no, localRetryDialogListener);
        localObject = localBuilder1.create();
      }
      else if (paramInt == R.id.photo_view_download_nonfull_failed_dialog)
      {
        AlertDialog.Builder localBuilder2 = new AlertDialog.Builder(this);
        localBuilder2.setMessage(R.string.download_photo_error).setNeutralButton(R.string.ok, this.mFailedListener);
        localObject = localBuilder2.create();
      }
      else
      {
        localObject = null;
      }
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 2131361833:
    case 2131361834:
    case 1360862707:
    }
    while (true)
    {
      return localObject;
      this.mFragmentIsLoading = true;
      MediaRef[] arrayOfMediaRef = this.mMediaRefs;
      int i;
      if ((arrayOfMediaRef != null) && (arrayOfMediaRef.length == 1))
      {
        String str = arrayOfMediaRef[0].getUrl();
        if (str == null)
        {
          Uri localUri = arrayOfMediaRef[0].getLocalUri();
          if (localUri != null)
            str = localUri.toString();
        }
        if ((!TextUtils.isEmpty(str)) && (str.startsWith("content:")))
          i = 1;
      }
      while (true)
      {
        if (i == 0)
          break label154;
        localObject = new CameraPhotoLoader(this);
        break;
        i = 0;
        continue;
        i = 0;
      }
      label154: localObject = new PhotoPagerLoader(this, this.mAccount, this.mOwnerGaiaId, this.mMediaRefs, this.mAlbumId, this.mPhotoOfUserGaiaId, this.mStreamId, this.mEventId, this.mPhotoUrl, this.mPageHint, this.mAuthkey);
      continue;
      localObject = new EsCursorLoader(this, EsProvider.appendAccountParameter(Uri.withAppendedPath(Uri.withAppendedPath(EsProvider.ALBUM_VIEW_BY_ALBUM_AND_OWNER_URI, this.mAlbumId), this.mOwnerGaiaId), this.mAccount), AlbumDetailsQuery.PROJECTION, null, null, null);
      continue;
      localObject = new CursorLoader(this)
      {
        public final Cursor loadInBackground()
        {
          EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(PhotoOneUpActivity.EVENT_NAME_PROJECTION);
          String str = EsEventData.getEventName(PhotoOneUpActivity.this, PhotoOneUpActivity.this.getAccount(), PhotoOneUpActivity.this.mEventId);
          localEsMatrixCursor.newRow().add(str);
          return localEsMatrixCursor;
        }
      };
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.photo_view_menu, paramMenu);
    return true;
  }

  public final void onFragmentVisible(Fragment paramFragment)
  {
    if ((this.mViewPager == null) || (this.mAdapter == null));
    while (true)
    {
      return;
      if (this.mViewPager.getCurrentItem() == this.mAdapter.getItemPosition(paramFragment))
        this.mFragmentIsLoading = false;
      updateView(this.mRootView);
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    Iterator localIterator = this.mMenuItemListeners.iterator();
    do
      if (!localIterator.hasNext())
        break;
    while (!((OnMenuItemListener)localIterator.next()).onOptionsItemSelected(paramMenuItem));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onPageActivated(Fragment paramFragment)
  {
    if ((paramFragment instanceof HostedFragment))
    {
      this.mHostedFragment = ((HostedFragment)paramFragment);
      Iterator localIterator = this.mScreenListeners.iterator();
      while (localIterator.hasNext())
        ((OnScreenListener)localIterator.next()).onViewActivated();
      updateTitleAndSubtitle();
    }
    while (true)
    {
      return;
      this.mHostedFragment = null;
    }
  }

  public final void onPageScrollStateChanged(int paramInt)
  {
  }

  public final void onPageScrolled$486775f1(int paramInt, float paramFloat)
  {
  }

  public final void onPageSelected(int paramInt)
  {
    this.mCurrentIndex = paramInt;
    PhotoPagerAdapter localPhotoPagerAdapter = this.mAdapter;
    if (localPhotoPagerAdapter.isDataValid());
    MediaRef localMediaRef;
    for (Cursor localCursor = localPhotoPagerAdapter.getCursor(); (localCursor == null) || (localCursor.isClosed()) || (!localCursor.moveToPosition(paramInt)); localCursor = null)
    {
      localMediaRef = null;
      this.mCurrentRef = localMediaRef;
      return;
    }
    long l = localCursor.getLong(1);
    String str1 = localCursor.getString(2);
    String str2 = localCursor.getString(3);
    int i;
    if (localCursor.getInt(6) != 0)
    {
      i = 1;
      label101: if (i == 0)
        break label138;
    }
    label138: for (MediaRef.MediaType localMediaType = MediaRef.MediaType.PANORAMA; ; localMediaType = MediaRef.MediaType.IMAGE)
    {
      localMediaRef = new MediaRef(str2, l, str1, null, localMediaType);
      break;
      i = 0;
      break label101;
    }
  }

  protected void onPause()
  {
    this.mIsPaused = true;
    super.onPause();
  }

  public final void onPhotoRemoved$1349ef()
  {
    Cursor localCursor = this.mAdapter.getCursor();
    if (localCursor == null);
    while (true)
    {
      return;
      if (localCursor.getCount() <= 1)
      {
        Intent localIntent = Intents.getHostNavigationActivityIntent(this, this.mAccount);
        localIntent.addFlags(67108864);
        startActivity(localIntent);
        finish();
      }
      else
      {
        getSupportLoaderManager().restartLoader(2131361833, null, this);
      }
    }
  }

  protected void onPrepareDialog(int paramInt, Dialog paramDialog, Bundle paramBundle)
  {
    super.onPrepareDialog(paramInt, paramDialog, paramBundle);
    if ((paramInt == R.id.photo_view_pending_dialog) && ((paramDialog instanceof ProgressDialog)))
      ((ProgressDialog)paramDialog).setMessage(paramBundle.getString("dialog_message"));
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    int i = paramMenu.size();
    for (int j = 0; j < i; j++)
      paramMenu.getItem(j).setVisible(false);
    Iterator localIterator = this.mMenuItemListeners.iterator();
    while (localIterator.hasNext())
      ((OnMenuItemListener)localIterator.next()).onPrepareOptionsMenu(paramMenu);
    return true;
  }

  public final void onPrimarySpinnerSelectionChange(int paramInt)
  {
  }

  public final void onRefreshButtonClicked()
  {
    if (this.mHostedFragment != null)
      this.mHostedFragment.refresh();
  }

  protected void onResume()
  {
    super.onResume();
    EsAccount localEsAccount = (EsAccount)getIntent().getParcelableExtra("account");
    int i;
    if (localEsAccount != null)
      if (!localEsAccount.equals(EsService.getActiveAccount(this)))
      {
        if (EsLog.isLoggable("PhotoOneUp", 6))
          Log.e("PhotoOneUp", "Activity finished because it is associated with a signed-out account: " + getClass().getName());
        i = 0;
        if (i == 0)
          break label121;
        this.mIsPaused = false;
        if (this.mRestartLoader)
        {
          this.mRestartLoader = false;
          getSupportLoaderManager().restartLoader(2131361833, null, this);
        }
      }
    while (true)
    {
      return;
      i = 1;
      break;
      i = 0;
      break;
      label121: finish();
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("com.google.android.apps.plus.PhotoViewFragment.ITEM", this.mViewPager.getCurrentItem());
    paramBundle.putBoolean("com.google.android.apps.plus.PhotoViewFragment.FULLSCREEN", this.mFullScreen);
    paramBundle.putParcelable("com.google.android.apps.plus.PhotoViewFragment.CURRENT_REF", this.mCurrentRef);
  }

  public final PhotoViewPager.InterceptType onTouchIntercept(float paramFloat1, float paramFloat2)
  {
    boolean bool1 = false;
    boolean bool2 = false;
    Iterator localIterator = this.mScreenListeners.iterator();
    while (localIterator.hasNext())
    {
      OnScreenListener localOnScreenListener = (OnScreenListener)localIterator.next();
      if (!bool1)
        bool1 = localOnScreenListener.onInterceptMoveLeft$2548a39();
      if (!bool2)
        bool2 = localOnScreenListener.onInterceptMoveRight$2548a39();
      localOnScreenListener.onViewActivated();
    }
    PhotoViewPager.InterceptType localInterceptType;
    if (bool1)
      if (bool2)
        localInterceptType = PhotoViewPager.InterceptType.BOTH;
    while (true)
    {
      return localInterceptType;
      localInterceptType = PhotoViewPager.InterceptType.LEFT;
      continue;
      if (bool2)
        localInterceptType = PhotoViewPager.InterceptType.RIGHT;
      else
        localInterceptType = PhotoViewPager.InterceptType.NONE;
    }
  }

  public final void onUpButtonClick()
  {
    Intent localIntent;
    if ((getIntent().getBooleanExtra("from_url_gateway", false)) || (getIntent().getBooleanExtra("com.google.plus.analytics.intent.extra.FROM_NOTIFICATION", false)))
    {
      TaskStackBuilder localTaskStackBuilder = TaskStackBuilder.create(this);
      if (!this.mAccount.isMyGaiaId(this.mOwnerGaiaId))
        localTaskStackBuilder.addNextIntent(Intents.getStreamActivityIntent(this, this.mAccount));
      localTaskStackBuilder.addNextIntent(Intents.getProfilePhotosActivityIntent(this, this.mAccount, "g:" + this.mOwnerGaiaId));
      if (this.mEventId == null)
        if (this.mStreamId != null)
        {
          localIntent = Intents.newPhotosActivityIntentBuilder(this).setAccount(this.mAccount).setStreamId(this.mStreamId).setAlbumName(this.mAlbumName).setGaiaId(this.mOwnerGaiaId).build();
          if (localIntent != null)
            localTaskStackBuilder.addNextIntent(localIntent);
          localTaskStackBuilder.startActivities();
          finish();
        }
    }
    while (true)
    {
      return;
      if ((this.mPhotoOfUserGaiaId == null) && (this.mAlbumId != null))
      {
        localIntent = Intents.newPhotosActivityIntentBuilder(this).setAccount(this.mAccount).setAlbumId(this.mAlbumId).setAlbumName(this.mAlbumName).setGaiaId(this.mOwnerGaiaId).build();
        break;
      }
      localIntent = null;
      break;
      onBackPressed();
    }
  }

  public final void removeMenuItemListener(OnMenuItemListener paramOnMenuItemListener)
  {
    this.mMenuItemListeners.remove(paramOnMenuItemListener);
  }

  public final void removeScreenListener(OnScreenListener paramOnScreenListener)
  {
    this.mScreenListeners.remove(paramOnScreenListener);
  }

  public final void toggleFullScreen()
  {
    if (this.mKeyboardIsVisible)
      return;
    if (!this.mFullScreen);
    for (boolean bool = true; ; bool = false)
    {
      this.mFullScreen = bool;
      Iterator localIterator = this.mScreenListeners.iterator();
      while (localIterator.hasNext())
        ((OnScreenListener)localIterator.next()).onFullScreenChanged$25decb5(this.mFullScreen);
      break;
    }
  }

  private static abstract interface AlbumDetailsQuery
  {
    public static final String[] PROJECTION = { "title", "photo_count" };
  }

  public static abstract interface OnMenuItemListener
  {
    public abstract boolean onOptionsItemSelected(MenuItem paramMenuItem);

    public abstract void onPrepareOptionsMenu(Menu paramMenu);
  }

  public static abstract interface OnScreenListener
  {
    public abstract void enableImageTransforms(boolean paramBoolean);

    public abstract void onFullScreenChanged$25decb5(boolean paramBoolean);

    public abstract boolean onInterceptMoveLeft$2548a39();

    public abstract boolean onInterceptMoveRight$2548a39();

    public abstract void onViewActivated();
  }

  final class RetryDialogListener
    implements DialogInterface.OnClickListener
  {
    final String mTag;

    public RetryDialogListener(String arg2)
    {
      Object localObject;
      this.mTag = localObject;
    }

    public final void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      switch (paramInt)
      {
      default:
      case -1:
      }
      while (true)
      {
        paramDialogInterface.dismiss();
        return;
        Fragment localFragment = PhotoOneUpActivity.this.getSupportFragmentManager().findFragmentByTag(this.mTag);
        if (localFragment != null)
          ((PhotoOneUpFragment)localFragment).doDownload(PhotoOneUpActivity.this, false);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PhotoOneUpActivity
 * JD-Core Version:    0.6.2
 */