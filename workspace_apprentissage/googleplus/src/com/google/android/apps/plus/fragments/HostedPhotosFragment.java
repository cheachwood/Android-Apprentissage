package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.AlbumGridViewAdapter;
import com.google.android.apps.plus.phone.AlbumGridViewAdapter.StateFilter;
import com.google.android.apps.plus.phone.AlbumGridViewAdapter.ViewUseListener;
import com.google.android.apps.plus.phone.AlbumViewLoader;
import com.google.android.apps.plus.phone.CameraAlbumLoader;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.Intents.PhotoViewIntentBuilder;
import com.google.android.apps.plus.phone.Pageable;
import com.google.android.apps.plus.phone.Pageable.LoadingListener;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.ColumnGridView.ItemSelectionListener;
import com.google.android.apps.plus.views.ColumnGridView.OnScrollListener;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.HostActionBar.OnDoneButtonClickListener;
import com.google.android.apps.plus.views.PhotoAlbumView;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class HostedPhotosFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, View.OnLongClickListener, AlertFragmentDialog.AlertDialogListener, AlbumGridViewAdapter.ViewUseListener, Pageable.LoadingListener, HostActionBar.OnDoneButtonClickListener
{
  private ActionMode mActionMode;
  private ActionMode.Callback mActionModeCallback;
  private AlbumGridViewAdapter mAdapter;
  private int mAlbumCount = -1;
  private String mAlbumId;
  private String mAlbumName;
  private String mAlbumType;
  private PhotoAlbumView mAlbumView;
  private String mAuthkey;
  private int mCount;
  private DateFormat mDateFormat = DateFormat.getDateInstance(2);
  private Integer mDeleteReqId;
  private final EsServiceListener mEsListener = new EsServiceListener()
  {
    public final void onDeletePhotosComplete$5d3076b3(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedPhotosFragment.this.handlePhotoDelete(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onGetAlbumComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedPhotosFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onGetPhotosOfUserComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedPhotosFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onGetStreamPhotosComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedPhotosFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onLocalPhotoDelete(int paramAnonymousInt, ArrayList<MediaRef> paramAnonymousArrayList, ServiceResult paramAnonymousServiceResult)
    {
      HostedPhotosFragment.this.handlePhotoDelete(paramAnonymousInt, paramAnonymousServiceResult);
      HostedPhotosFragment.this.getLoaderManager().restartLoader(1, null, HostedPhotosFragment.this);
    }

    public final void onReadEventComplete(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedPhotosFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }
  };
  private String mEventId;
  private int mExcludedCount;
  private final ArrayList<MediaRef> mExcludedPhotoMediaRefs = new ArrayList();
  private Bundle mExtras;
  private ColumnGridView mGridView;
  private boolean mHereFromNotification;
  private long mLastNotificationTime;
  private boolean mLoaderActive;
  private String mNotificationId;
  private String mOwnerId;
  private Pageable mPageableLoader;
  private String mPhotoOfUserId;
  private int mPickerMode;
  private boolean mPickerShareWithZeroSelected;
  private int mPickerTitleResourceId;
  private Integer mRefreshReqId;
  private boolean mRefreshable;
  private final HashSet<MediaRef> mSelectedPhotoMediaRefs = new HashSet();
  private String mStreamId;
  private boolean mTakePhoto;
  private boolean mTakeVideo;

  private void handlePhotoDelete(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mDeleteReqId == null) || (this.mDeleteReqId.intValue() != paramInt));
    while (true)
    {
      return;
      this.mDeleteReqId = null;
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      {
        String str = getResources().getString(R.string.remove_photo_error);
        Toast.makeText(getActivity(), str, 0).show();
      }
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("progress_dialog");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      updatePickerMode(0);
    }
  }

  private void handleServiceCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mRefreshReqId == null) || (this.mRefreshReqId.intValue() != paramInt));
    while (true)
    {
      return;
      this.mRefreshReqId = null;
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      {
        String str = getResources().getString(R.string.refresh_photo_album_error);
        Toast.makeText(getActivity(), str, 0).show();
      }
      updateView(getView());
    }
  }

  private void invalidateContextualActionBar()
  {
    if (this.mActionMode != null)
      this.mActionMode.invalidate();
    while (true)
    {
      return;
      invalidateActionBar();
    }
  }

  private boolean isInstantUploadAlbum()
  {
    return TextUtils.equals(this.mAlbumType, "from_my_phone");
  }

  private boolean isLocalCameraAlbum()
  {
    return TextUtils.equals(this.mAlbumType, "camera_photos");
  }

  private void loadAlbumName()
  {
    int i;
    int j;
    if ((this.mOwnerId != null) && (this.mAlbumId != null))
    {
      i = 1;
      boolean bool = TextUtils.isEmpty(this.mAlbumName);
      if (this.mAlbumCount != -1)
        break label63;
      j = 1;
      label34: if ((i == 0) || ((!bool) && (j == 0)))
        break label68;
      getLoaderManager().restartLoader(2, null, this);
    }
    while (true)
    {
      return;
      i = 0;
      break;
      label63: j = 0;
      break label34;
      label68: invalidateActionBar();
    }
  }

  private void showDeleteConfirmationDialog()
  {
    Resources localResources = getResources();
    int i = this.mSelectedPhotoMediaRefs.size();
    if (isLocalCameraAlbum());
    for (int j = R.plurals.delete_local_photo_dialog_message; ; j = R.plurals.delete_remote_photo_dialog_message)
    {
      AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(localResources.getQuantityString(R.plurals.delete_photo_dialog_title, i), localResources.getQuantityString(j, i), localResources.getQuantityString(R.plurals.delete_photo, i), getString(R.string.cancel));
      localAlertFragmentDialog.setTargetFragment(this, 0);
      localAlertFragmentDialog.show(getFragmentManager(), "delete_dialog");
      return;
    }
  }

  private void updatePickerMode(int paramInt)
  {
    this.mPickerMode = paramInt;
    switch (this.mPickerMode)
    {
    case 1:
    default:
    case 0:
    case 2:
    case 3:
    }
    while (true)
    {
      invalidateContextualActionBar();
      return;
      this.mSelectedPhotoMediaRefs.clear();
      if (this.mGridView.isInSelectionMode())
      {
        this.mGridView.endSelectionMode();
        continue;
        if (!this.mGridView.isInSelectionMode())
        {
          this.mGridView.startSelectionMode();
          continue;
          if (!this.mGridView.isInSelectionMode())
            this.mGridView.startSelectionMode();
          if ((Build.VERSION.SDK_INT >= 11) && (this.mActionMode == null))
          {
            if (this.mActionModeCallback == null)
              this.mActionModeCallback = new ActionMode.Callback()
              {
                public final boolean onActionItemClicked(ActionMode paramAnonymousActionMode, MenuItem paramAnonymousMenuItem)
                {
                  int i = paramAnonymousMenuItem.getItemId();
                  if (i == R.id.reshare)
                  {
                    if (HostedPhotosFragment.this.mSelectedPhotoMediaRefs.size() > 0)
                      HostedPhotosFragment.this.shareSelectedPhotos();
                    HostedPhotosFragment.this.mActionMode.finish();
                  }
                  for (boolean bool = true; ; bool = false)
                  {
                    return bool;
                    if (i == R.id.delete_photos)
                    {
                      HostedPhotosFragment.this.showDeleteConfirmationDialog();
                      break;
                    }
                  }
                }

                public final boolean onCreateActionMode(ActionMode paramAnonymousActionMode, Menu paramAnonymousMenu)
                {
                  paramAnonymousActionMode.getMenuInflater().inflate(R.menu.photos_cab_menu, paramAnonymousMenu);
                  return true;
                }

                public final void onDestroyActionMode(ActionMode paramAnonymousActionMode)
                {
                  HostedPhotosFragment.access$802(HostedPhotosFragment.this, null);
                  HostedPhotosFragment.this.updatePickerMode(0);
                }

                public final boolean onPrepareActionMode(ActionMode paramAnonymousActionMode, Menu paramAnonymousMenu)
                {
                  if (HostedPhotosFragment.this.mPickerMode == 0)
                    HostedPhotosFragment.this.mActionMode.finish();
                  while (true)
                  {
                    return true;
                    Resources localResources = HostedPhotosFragment.this.getActivity().getResources();
                    int i = HostedPhotosFragment.this.mSelectedPhotoMediaRefs.size();
                    int j = R.plurals.from_your_phone_selected_count;
                    Object[] arrayOfObject = new Object[1];
                    arrayOfObject[0] = Integer.valueOf(i);
                    paramAnonymousActionMode.setTitle(localResources.getQuantityString(j, i, arrayOfObject));
                    MenuItem localMenuItem1 = paramAnonymousMenu.findItem(R.id.reshare);
                    MenuItem localMenuItem2 = paramAnonymousMenu.findItem(R.id.delete_photos);
                    if (i == 0)
                    {
                      localMenuItem1.setVisible(false);
                      localMenuItem2.setVisible(false);
                    }
                    else
                    {
                      localMenuItem1.setVisible(true);
                      localMenuItem2.setVisible(true);
                      localMenuItem2.setTitle(localResources.getQuantityString(R.plurals.delete_photo, i));
                    }
                  }
                }
              };
            this.mActionMode = getActivity().startActionMode(this.mActionModeCallback);
          }
        }
      }
    }
  }

  private void updateView(View paramView)
  {
    if (paramView == null)
      return;
    Cursor localCursor = this.mAdapter.getCursor();
    int i;
    label28: int j;
    if ((localCursor != null) && (localCursor.getCount() > 0))
    {
      i = 1;
      if ((this.mRefreshReqId == null) && (localCursor != null))
        break label68;
      j = 1;
      label42: if ((j == 0) || (i != 0))
        break label74;
      showEmptyViewProgress(paramView);
    }
    while (true)
    {
      updateSpinner();
      break;
      i = 0;
      break label28;
      label68: j = 0;
      break label42;
      label74: if (i == 0)
        break label86;
      showContent(paramView);
    }
    label86: int k;
    if (this.mExcludedPhotoMediaRefs.size() > 0)
    {
      k = 1;
      label99: if (k == 0)
        break label129;
    }
    label129: for (int m = R.string.no_photos_left; ; m = R.string.no_photos)
    {
      showEmptyView(paramView, getString(m));
      break;
      k = 0;
      break label99;
    }
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PHOTOS_HOME;
  }

  protected final boolean isEmpty()
  {
    if (this.mAdapter == null);
    for (boolean bool = true; ; bool = this.mAdapter.isEmpty())
      return bool;
  }

  protected final boolean isProgressIndicatorVisible()
  {
    if ((this.mRefreshReqId != null) || (this.mLoaderActive) || (super.isProgressIndicatorVisible()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onActionButtonClicked(int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return;
      if ((this.mPickerMode == 3) || (this.mHereFromNotification))
      {
        shareSelectedPhotos();
      }
      else
      {
        FragmentActivity localFragmentActivity = getActivity();
        Intent localIntent = new Intent();
        localIntent.putExtra("mediarefs", new ArrayList(this.mSelectedPhotoMediaRefs));
        localFragmentActivity.setResult(-1, localIntent);
        localFragmentActivity.finish();
        continue;
        recordUserAction(OzActions.COMPOSE_TAKE_PHOTO);
        getActivity();
        startActivityForResult(Intents.getCameraIntentPhoto$3a35108a("camera-p.jpg"), 2);
        continue;
        getActivity();
        startActivityForResult(Intents.getCameraIntentVideo$7ec49240(), 3);
      }
    }
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return;
      if (paramInt2 != 0)
      {
        getActivity().setResult(paramInt2, paramIntent);
        getActivity().finish();
        continue;
        FragmentActivity localFragmentActivity2 = getActivity();
        if (paramInt2 == -1)
        {
          Integer localInteger = EsService.insertCameraPhoto(getSafeContext(), this.mAccount, "camera-p.jpg");
          Intent localIntent2 = new Intent();
          localIntent2.putExtra("insert_photo_request_id", localInteger);
          localIntent2.putExtra("media_taken", true);
          localIntent2.putExtra("mediarefs", new ArrayList(this.mSelectedPhotoMediaRefs));
          localFragmentActivity2.setResult(-1, localIntent2);
        }
        localFragmentActivity2.finish();
        continue;
        FragmentActivity localFragmentActivity1 = getActivity();
        if ((paramIntent != null) && (paramInt2 == -1))
        {
          Intent localIntent1 = new Intent();
          MediaRef localMediaRef = new MediaRef(this.mAccount.getGaiaId(), 0L, null, paramIntent.getData(), MediaRef.MediaType.VIDEO);
          this.mSelectedPhotoMediaRefs.add(localMediaRef);
          localIntent1.putExtra("mediarefs", new ArrayList(this.mSelectedPhotoMediaRefs));
          localIntent1.putExtra("media_taken", true);
          localIntent1.removeExtra("insert_photo_request_id");
          localFragmentActivity1.setResult(-1, localIntent1);
        }
        localFragmentActivity1.finish();
      }
    }
  }

  public void onClick(View paramView)
  {
    MediaRef[] arrayOfMediaRef;
    String str1;
    label51: String str2;
    label76: String str3;
    label101: int i;
    int j;
    Cursor localCursor;
    long l;
    String str4;
    String str5;
    int m;
    label207: MediaRef.MediaType localMediaType;
    label217: MediaRef localMediaRef2;
    if (this.mExtras.containsKey("mediarefs"))
    {
      arrayOfMediaRef = (MediaRef[])this.mExtras.getParcelableArray("mediarefs");
      if (!this.mExtras.containsKey("album_id"))
        break label312;
      str1 = this.mExtras.getString("album_id");
      if (!this.mExtras.containsKey("stream_id"))
        break label317;
      str2 = this.mExtras.getString("stream_id");
      if (!this.mExtras.containsKey("photos_of_user_id"))
        break label323;
      str3 = this.mExtras.getString("photos_of_user_id");
      i = this.mExtras.getInt("photo_picker_mode");
      j = this.mExtras.getInt("photo_picker_crop_mode", 0);
      int k = ((Integer)paramView.getTag(R.id.tag_position)).intValue();
      localCursor = this.mAdapter.getCursor();
      localCursor.moveToPosition(k);
      l = localCursor.getLong(8);
      str4 = localCursor.getString(5);
      str5 = localCursor.getString(9);
      if (localCursor.getInt(12) == 0)
        break label329;
      m = 1;
      if (m == 0)
        break label335;
      localMediaType = MediaRef.MediaType.PANORAMA;
      if ((!TextUtils.equals(this.mAlbumType, "camera_photos")) || (arrayOfMediaRef != null))
        break label363;
      localMediaRef2 = new MediaRef(str4, l, null, Uri.parse(str5), localMediaType);
      arrayOfMediaRef = new MediaRef[] { localMediaRef2 };
    }
    label312: label317: label323: label329: label335: label363: for (MediaRef localMediaRef1 = localMediaRef2; ; localMediaRef1 = new MediaRef(str4, l, str5, null, localMediaType))
    {
      if (i == 0)
        break label384;
      String str6 = localCursor.getString(7);
      startActivityForResult(Intents.getPhotoPickerIntent(getActivity(), this.mAccount, str6, localMediaRef1, j), 1);
      return;
      arrayOfMediaRef = null;
      break;
      str1 = null;
      break label51;
      str2 = null;
      break label76;
      str3 = null;
      break label101;
      m = 0;
      break label207;
      if (!localCursor.isNull(11))
      {
        localMediaType = MediaRef.MediaType.VIDEO;
        break label217;
      }
      localMediaType = MediaRef.MediaType.IMAGE;
      break label217;
    }
    label384: Loader localLoader = getLoaderManager().getLoader(1);
    if ((localLoader instanceof Pageable));
    for (int n = ((Pageable)localLoader).getCurrentPage(); ; n = -1)
    {
      Intents.PhotoViewIntentBuilder localPhotoViewIntentBuilder = Intents.newPhotoViewActivityIntentBuilder(getActivity());
      localPhotoViewIntentBuilder.setAccount(this.mAccount).setGaiaId(str4).setMediaRefs(arrayOfMediaRef).setAlbumName(this.mAlbumName).setAlbumId(str1).setStreamId(str2).setPhotoOfUserId(str3).setEventId(this.mEventId).setPhotoRef(localMediaRef1).setPageHint(Integer.valueOf(n));
      if ((this.mHereFromNotification) && (!TextUtils.isEmpty(this.mNotificationId)))
        localPhotoViewIntentBuilder.setNotificationId(this.mNotificationId);
      startActivity(localPhotoViewIntentBuilder.build());
      break;
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Integer localInteger;
    if (paramBundle != null)
    {
      this.mExtras = new Bundle();
      this.mExtras.putAll(paramBundle.getBundle("INTENT"));
      if (paramBundle.containsKey("ALBUM_NAME"))
        this.mAlbumName = paramBundle.getString("ALBUM_NAME");
      boolean bool3 = paramBundle.containsKey("STATE_PICKER_MODE");
      localInteger = null;
      if (bool3)
      {
        localInteger = Integer.valueOf(paramBundle.getInt("STATE_PICKER_MODE"));
        this.mPickerTitleResourceId = paramBundle.getInt("STATE_PICKER_TITLE");
        this.mPickerShareWithZeroSelected = paramBundle.getBoolean("STATE_PICKER_SHARE_ON_ZERO");
      }
      if (paramBundle.containsKey("SELECTED_ITEMS"))
      {
        Parcelable[] arrayOfParcelable2 = paramBundle.getParcelableArray("SELECTED_ITEMS");
        for (int j = 0; j < arrayOfParcelable2.length; j++)
          this.mSelectedPhotoMediaRefs.add((MediaRef)arrayOfParcelable2[j]);
      }
      if (paramBundle.containsKey("refresh_request"))
        this.mRefreshReqId = Integer.valueOf(paramBundle.getInt("refresh_request"));
      if (paramBundle.containsKey("delete_request"))
        this.mDeleteReqId = Integer.valueOf(paramBundle.getInt("delete_request"));
      if (paramBundle.containsKey("loader_active"))
        this.mLoaderActive = paramBundle.getBoolean("loader_active");
      if (this.mExtras.containsKey("owner_id"))
        this.mOwnerId = this.mExtras.getString("owner_id");
      if ((this.mExtras.containsKey("album_name")) && (this.mAlbumName == null))
        this.mAlbumName = this.mExtras.getString("album_name");
      if (this.mExtras.containsKey("album_id"))
        this.mAlbumId = this.mExtras.getString("album_id");
      if (this.mExtras.containsKey("auth_key"))
        this.mAuthkey = this.mExtras.getString("auth_key");
      if (this.mExtras.containsKey("album_type"))
        this.mAlbumType = this.mExtras.getString("album_type");
      if (this.mExtras.containsKey("stream_id"))
        this.mStreamId = this.mExtras.getString("stream_id");
      if (this.mExtras.containsKey("event_id"))
        this.mEventId = this.mExtras.getString("event_id");
      if (this.mExtras.containsKey("photos_of_user_id"))
        this.mPhotoOfUserId = this.mExtras.getString("photos_of_user_id");
      this.mNotificationId = this.mExtras.getString("notif_id");
      if (TextUtils.isEmpty(this.mNotificationId))
        break label743;
    }
    label743: for (boolean bool2 = true; ; bool2 = false)
    {
      this.mHereFromNotification = bool2;
      if ((localInteger == null) && (this.mExtras.containsKey("photo_picker_mode")))
        localInteger = Integer.valueOf(this.mExtras.getInt("photo_picker_mode", 0));
      if (localInteger != null)
        this.mPickerMode = localInteger.intValue();
      if (this.mExtras.containsKey("photo_picker_title"))
        this.mPickerTitleResourceId = this.mExtras.getInt("photo_picker_title");
      if (this.mExtras.containsKey("photo_picker_share_on_zero"))
        this.mPickerShareWithZeroSelected = this.mExtras.getBoolean("photo_picker_share_on_zero");
      if (this.mExtras.containsKey("take_photo"))
        this.mTakePhoto = this.mExtras.getBoolean("take_photo");
      if (this.mExtras.containsKey("take_video"))
        this.mTakeVideo = this.mExtras.getBoolean("take_video");
      if (!this.mExtras.containsKey("photo_picker_selected"))
        break label749;
      Parcelable[] arrayOfParcelable1 = this.mExtras.getParcelableArray("photo_picker_selected");
      for (int i = 0; i < arrayOfParcelable1.length; i++)
        this.mExcludedPhotoMediaRefs.add((MediaRef)arrayOfParcelable1[i]);
      this.mExtras = getArguments();
      this.mLastNotificationTime = EsAccountsData.queryLastPhotoNotificationTimestamp(getActivity(), this.mAccount);
      boolean bool1 = this.mLastNotificationTime < 0L;
      localInteger = null;
      if (!bool1)
        break;
      this.mLastNotificationTime = (System.currentTimeMillis() - 14400000L);
      localInteger = null;
      break;
    }
    label749: loadAlbumName();
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 1:
    case 2:
    }
    while (true)
    {
      return localObject;
      int i = this.mExcludedPhotoMediaRefs.size();
      MediaRef[] arrayOfMediaRef = null;
      if (i > 0)
        arrayOfMediaRef = (MediaRef[])this.mExcludedPhotoMediaRefs.toArray(new MediaRef[this.mExcludedPhotoMediaRefs.size()]);
      if (isLocalCameraAlbum());
      for (localObject = new CameraAlbumLoader(getActivity(), this.mAccount, arrayOfMediaRef); ; localObject = new AlbumViewLoader(getActivity(), this.mAccount, this.mOwnerId, this.mAlbumId, this.mPhotoOfUserId, this.mStreamId, this.mEventId, this.mAuthkey, arrayOfMediaRef))
      {
        this.mPageableLoader = ((Pageable)localObject);
        this.mPageableLoader.setLoadingListener(this);
        break;
      }
      Uri localUri = EsProvider.appendAccountParameter(Uri.withAppendedPath(Uri.withAppendedPath(EsProvider.ALBUM_VIEW_BY_ALBUM_AND_OWNER_URI, this.mAlbumId), this.mOwnerId), this.mAccount);
      localObject = new EsCursorLoader(getActivity(), localUri, AlbumDetailsQuery.PROJECTION, null, null, null);
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mAlbumView = ((PhotoAlbumView)paramLayoutInflater.inflate(R.layout.hosted_album_view, paramViewGroup, false));
    this.mGridView = ((ColumnGridView)this.mAlbumView.findViewById(R.id.grid));
    ScreenMetrics localScreenMetrics = ScreenMetrics.getInstance(getActivity());
    this.mGridView.setItemMargin(localScreenMetrics.itemMargin);
    this.mGridView.setPadding(localScreenMetrics.itemMargin, localScreenMetrics.itemMargin, localScreenMetrics.itemMargin, localScreenMetrics.itemMargin);
    this.mAdapter = new AlbumGridViewAdapter(getActivity(), null, this.mAlbumType, this.mGridView, this, this, this);
    this.mAdapter.setSelectedMediaRefs(this.mSelectedPhotoMediaRefs);
    if (this.mExtras.getInt("photo_picker_crop_mode", 0) == 2)
      this.mAdapter.setStateFilter(new AlbumGridViewAdapter.StateFilter()
      {
        public final int getState(int paramAnonymousInt)
        {
          if (paramAnonymousInt < 470);
          for (int i = 1; ; i = 0)
            return i;
        }
      });
    this.mGridView.setAdapter(this.mAdapter);
    this.mGridView.setSelector(R.drawable.list_selected_holo);
    getLoaderManager().initLoader(1, null, this);
    updatePickerMode(this.mPickerMode);
    updateView(this.mAlbumView);
    setupEmptyView(this.mAlbumView, R.string.no_photos);
    this.mGridView.setOnScrollListener(new ColumnGridView.OnScrollListener()
    {
      int mCachedFirstVisibleIndex = -1;

      public final void onScroll(ColumnGridView paramAnonymousColumnGridView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5)
      {
        if ((paramAnonymousInt3 == 0) || (HostedPhotosFragment.this.mAdapter == null));
        while (true)
        {
          return;
          int i = paramAnonymousInt1 + paramAnonymousInt2;
          if (this.mCachedFirstVisibleIndex != i)
          {
            int j = Math.min(i + paramAnonymousColumnGridView.getColumnCount(), paramAnonymousInt4 - 1);
            long l = HostedPhotosFragment.this.mAdapter.getTimestampForItem(j);
            HostedPhotosFragment.this.mAlbumView.setDate(HostedPhotosFragment.this.mDateFormat.format(Long.valueOf(l)));
            this.mCachedFirstVisibleIndex = i;
          }
        }
      }

      public final void onScrollStateChanged(ColumnGridView paramAnonymousColumnGridView, int paramAnonymousInt)
      {
        int j;
        if (paramAnonymousInt != 0)
        {
          Cursor localCursor = HostedPhotosFragment.this.mAdapter.getCursor();
          if ((localCursor != null) && (localCursor.getCount() > 0))
          {
            j = 1;
            if (j == 0)
              break label59;
          }
        }
        label59: for (int i = 0; ; i = 8)
        {
          HostedPhotosFragment.this.mAlbumView.setDateVisibility(i);
          return;
          j = 0;
          break;
        }
      }
    });
    this.mGridView.registerSelectionListener(new ColumnGridView.ItemSelectionListener()
    {
      public final void onItemDeselected(View paramAnonymousView, int paramAnonymousInt)
      {
        MediaRef localMediaRef = null;
        if (paramAnonymousView != null)
          localMediaRef = (MediaRef)paramAnonymousView.getTag();
        if (localMediaRef == null)
          localMediaRef = HostedPhotosFragment.this.mAdapter.getMediaRefForItem(paramAnonymousInt);
        HostedPhotosFragment.this.mSelectedPhotoMediaRefs.remove(localMediaRef);
        HostedPhotosFragment.this.invalidateContextualActionBar();
      }

      public final void onItemSelected(View paramAnonymousView, int paramAnonymousInt)
      {
        MediaRef localMediaRef = null;
        if (paramAnonymousView != null)
          localMediaRef = (MediaRef)paramAnonymousView.getTag();
        if (localMediaRef == null)
        {
          localMediaRef = HostedPhotosFragment.this.mAdapter.getMediaRefForItem(paramAnonymousInt);
          if (paramAnonymousView != null)
            paramAnonymousView.setTag(localMediaRef);
        }
        HostedPhotosFragment.this.mSelectedPhotoMediaRefs.add(localMediaRef);
        HostedPhotosFragment.this.invalidateContextualActionBar();
      }
    });
    this.mAlbumView.enableDateDisplay(true);
    return this.mAlbumView;
  }

  public final void onDataSourceLoading(boolean paramBoolean)
  {
    this.mLoaderActive = paramBoolean;
    updateSpinner();
  }

  public final void onDestroyView()
  {
    super.onDestroyView();
    this.mGridView.unregisterSelectionListener();
    this.mGridView.setOnScrollListener(null);
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
    ArrayList localArrayList1;
    String str;
    ArrayList localArrayList2;
    if ("delete_dialog".equals(paramString))
    {
      localArrayList1 = new ArrayList();
      str = this.mAccount.getGaiaId();
      if (!isLocalCameraAlbum())
        break label94;
      localArrayList2 = new ArrayList(this.mSelectedPhotoMediaRefs);
    }
    label185: for (this.mDeleteReqId = Integer.valueOf(EsService.deleteLocalPhotos(getActivity(), localArrayList2)); ; this.mDeleteReqId = Integer.valueOf(EsService.deletePhotos(getActivity(), this.mAccount, str, localArrayList1)))
    {
      ProgressFragmentDialog.newInstance(null, getResources().getQuantityString(R.plurals.delete_photo_pending, this.mSelectedPhotoMediaRefs.size())).show(getFragmentManager(), "progress_dialog");
      return;
      label94: Iterator localIterator = this.mSelectedPhotoMediaRefs.iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          break label185;
        MediaRef localMediaRef = (MediaRef)localIterator.next();
        if (localMediaRef.hasPhotoId())
        {
          if (!str.equals(localMediaRef.getOwnerGaiaId()))
          {
            if (!EsLog.isLoggable("HostedPhotosFragment", 5))
              break;
            Log.w("HostedPhotosFragment", "Found a photo from phone which is not owned by the current user.");
            break;
          }
          localArrayList1.add(Long.valueOf(localMediaRef.getPhotoId()));
        }
      }
    }
  }

  public final void onDoneButtonClick()
  {
    updatePickerMode(0);
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public boolean onLongClick(View paramView)
  {
    if ((this.mPickerMode == 0) && ((isLocalCameraAlbum()) || (isInstantUploadAlbum())))
    {
      updatePickerMode(3);
      int i = ((Integer)paramView.getTag(R.id.tag_position)).intValue();
      this.mGridView.select(i);
    }
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    int i = paramMenuItem.getItemId();
    if (i == R.id.select_item)
      updatePickerMode(3);
    for (boolean bool = true; ; bool = false)
    {
      return bool;
      if (i == R.id.delete_photos)
      {
        showDeleteConfirmationDialog();
        break;
      }
    }
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mEsListener);
    this.mPageableLoader.setLoadingListener(null);
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    super.onPrepareActionBar(paramHostActionBar);
    paramHostActionBar.setOnDoneButtonClickListener(this);
    switch (this.mPickerMode)
    {
    default:
      paramHostActionBar.finishContextActionMode();
      paramHostActionBar.showTitle(this.mAlbumName);
      if (!isLocalCameraAlbum())
        paramHostActionBar.showRefreshButton();
      break;
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return;
      paramHostActionBar.showTitle(this.mPickerTitleResourceId);
      if (!isLocalCameraAlbum())
      {
        paramHostActionBar.showRefreshButton();
        continue;
        int k = this.mSelectedPhotoMediaRefs.size();
        if ((this.mTakeVideo) && (k == 0))
          paramHostActionBar.showActionButton(3, R.drawable.icn_add_video, R.string.post_take_video_button);
        if ((this.mTakePhoto) && (k == 0))
          paramHostActionBar.showActionButton(2, R.drawable.icn_events_add_photo, R.string.post_take_photo_button);
        if ((k > 0) || (this.mPickerShareWithZeroSelected))
        {
          Resources localResources2 = getResources();
          int m = R.plurals.from_your_phone_selected_count;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(k);
          paramHostActionBar.showTitle(localResources2.getQuantityString(m, k, arrayOfObject2));
          paramHostActionBar.showActionButton(1, R.drawable.ic_actionbar_reshare, R.string.from_your_phone_initiate_share);
        }
        else
        {
          paramHostActionBar.showTitle(this.mPickerTitleResourceId);
          continue;
          paramHostActionBar.startContextActionMode();
          int i = this.mSelectedPhotoMediaRefs.size();
          Resources localResources1 = getResources();
          int j = R.plurals.from_your_phone_selected_count;
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Integer.valueOf(i);
          paramHostActionBar.showTitle(localResources1.getQuantityString(j, i, arrayOfObject1));
          if (i > 0)
            paramHostActionBar.showActionButton(1, R.drawable.ic_actionbar_reshare, R.string.from_your_phone_initiate_share);
        }
      }
    }
  }

  public final void onPrepareOptionsMenu(Menu paramMenu)
  {
    MenuItem localMenuItem1 = paramMenu.findItem(R.id.select_item);
    MenuItem localMenuItem2 = paramMenu.findItem(R.id.delete_photos);
    switch (this.mPickerMode)
    {
    case 1:
    case 2:
    default:
    case 0:
    case 3:
    }
    while (true)
    {
      return;
      if ((isLocalCameraAlbum()) || (isInstantUploadAlbum()))
      {
        localMenuItem1.setVisible(true);
        localMenuItem2.setVisible(false);
        continue;
        localMenuItem1.setVisible(false);
        int i = this.mSelectedPhotoMediaRefs.size();
        if (i > 0)
        {
          localMenuItem2.setTitle(getActivity().getResources().getQuantityString(R.plurals.delete_photo, i));
          localMenuItem2.setVisible(true);
        }
        else
        {
          localMenuItem2.setVisible(false);
        }
      }
    }
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mEsListener);
    this.mPageableLoader = ((Pageable)getLoaderManager().getLoader(1));
    this.mPageableLoader.setLoadingListener(this);
    if ((this.mLoaderActive) && (!this.mPageableLoader.isDataSourceLoading()))
      onDataSourceLoading(false);
    if (this.mRefreshReqId != null)
    {
      if (!EsService.isRequestPending(this.mRefreshReqId.intValue()))
        break label150;
      if (isEmpty())
        showEmptyViewProgress(getView());
    }
    while (true)
    {
      if ((this.mDeleteReqId != null) && (!EsService.isRequestPending(this.mDeleteReqId.intValue())))
      {
        ServiceResult localServiceResult1 = EsService.removeResult(this.mDeleteReqId.intValue());
        handlePhotoDelete(this.mDeleteReqId.intValue(), localServiceResult1);
      }
      this.mAdapter.onResume();
      updateSpinner();
      return;
      label150: ServiceResult localServiceResult2 = EsService.removeResult(this.mRefreshReqId.intValue());
      handleServiceCallback(this.mRefreshReqId.intValue(), localServiceResult2);
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mExtras != null)
    {
      paramBundle.putParcelable("INTENT", this.mExtras);
      if (this.mAlbumName != null)
        paramBundle.putString("ALBUM_NAME", this.mAlbumName);
      if (this.mPickerMode != 0)
      {
        paramBundle.putInt("STATE_PICKER_MODE", this.mPickerMode);
        paramBundle.putInt("STATE_PICKER_TITLE", this.mPickerTitleResourceId);
        paramBundle.putBoolean("STATE_PICKER_SHARE_ON_ZERO", this.mPickerShareWithZeroSelected);
      }
      if (this.mSelectedPhotoMediaRefs.size() > 0)
      {
        MediaRef[] arrayOfMediaRef = new MediaRef[this.mSelectedPhotoMediaRefs.size()];
        this.mSelectedPhotoMediaRefs.toArray(arrayOfMediaRef);
        paramBundle.putParcelableArray("SELECTED_ITEMS", arrayOfMediaRef);
      }
      if (this.mRefreshReqId != null)
        paramBundle.putInt("refresh_request", this.mRefreshReqId.intValue());
      if (this.mDeleteReqId != null)
        paramBundle.putInt("delete_request", this.mDeleteReqId.intValue());
      if (this.mLoaderActive)
        paramBundle.putBoolean("loader_active", true);
    }
  }

  public final void onStop()
  {
    super.onStop();
    this.mAdapter.onStop();
  }

  public final void onViewUsed(int paramInt)
  {
    if (isPaused());
    int i;
    do
    {
      do
      {
        return;
        i = this.mAdapter.getCount() + this.mExcludedCount;
      }
      while (i == this.mAlbumCount);
      if (this.mCount != i)
      {
        this.mCount = i;
        this.mRefreshable = true;
      }
    }
    while (!this.mRefreshable);
    int j;
    if (i < 16)
    {
      j = 1;
      label62: if (j == 0)
        break label83;
      refresh();
    }
    while (true)
    {
      this.mRefreshable = false;
      break;
      j = 0;
      break label62;
      label83: if (paramInt < Math.max(0, -64 + (i - this.mExcludedCount)))
        break;
      Loader localLoader = getLoaderManager().getLoader(1);
      if (localLoader != null)
      {
        if ((localLoader instanceof Pageable))
        {
          Pageable localPageable = (Pageable)localLoader;
          if (localPageable.hasMore())
            localPageable.loadMore();
        }
        updateView(getView());
      }
    }
  }

  public final void refresh()
  {
    if (this.mRefreshReqId != null)
      return;
    super.refresh();
    if (this.mStreamId != null)
      this.mRefreshReqId = Integer.valueOf(EsService.getStreamPhotos(getActivity(), this.mAccount, this.mOwnerId, this.mStreamId, Integer.valueOf(0), Integer.valueOf(500), this.mAuthkey));
    while (true)
    {
      updateView(getView());
      break;
      if (this.mPhotoOfUserId != null)
        this.mRefreshReqId = Integer.valueOf(EsService.getPhotosOfUser(getActivity(), this.mAccount, this.mPhotoOfUserId));
      else if (this.mAlbumId != null)
        this.mRefreshReqId = Integer.valueOf(EsService.getAlbumPhotos(getActivity(), this.mAccount, this.mAlbumId, this.mOwnerId, this.mAuthkey));
      else if (this.mEventId != null)
        this.mRefreshReqId = Integer.valueOf(EsService.readEvent(getActivity(), this.mAccount, this.mEventId, this.mOwnerId, null, true));
    }
  }

  protected final void shareSelectedPhotos()
  {
    ArrayList localArrayList = new ArrayList(this.mSelectedPhotoMediaRefs);
    startActivity(Intents.getPostActivityIntent(getActivity(), this.mAccount, localArrayList));
    if (this.mHereFromNotification)
      EsAnalytics.recordActionEvent(getActivity(), this.mAccount, OzActions.SHARE_INSTANT_UPLOAD_FROM_NOTIFICATION, OzViews.PHOTOS_HOME);
  }

  private static abstract interface AlbumDetailsQuery
  {
    public static final String[] PROJECTION = { "title", "photo_count" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedPhotosFragment
 * JD-Core Version:    0.6.2
 */