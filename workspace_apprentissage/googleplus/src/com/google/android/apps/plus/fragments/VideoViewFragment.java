package com.google.android.apps.plus.fragments;

import android.content.ContentUris;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.MediaController;
import android.widget.VideoView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.api.services.plusi.model.DataVideo;
import com.google.api.services.plusi.model.DataVideoJson;
import com.google.api.services.plusi.model.DataVideoStream;
import java.util.Iterator;
import java.util.List;

public class VideoViewFragment extends EsFragment
  implements MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener
{
  private static final String[] PROJECTION = { "video_data" };
  private static final SparseBooleanArray sPlayableTypes;
  private EsAccount mAccount;
  private String mAuthkey;
  private boolean mError;
  private final EsServiceListener mEsListener = new EsServiceListener()
  {
    public final void onGetPhoto$4894d499(int paramAnonymousInt, long paramAnonymousLong)
    {
      if (paramAnonymousLong == VideoViewFragment.this.mPhotoId)
        VideoViewFragment.this.getLoaderManager().restartLoader(0, null, VideoViewFragment.this);
    }
  };
  private Intent mIntent;
  private boolean mIsWiFiConnection;
  private boolean mLoading;
  private String mOwnerId;
  private boolean mPerformedRefetch;
  private long mPhotoId;
  private boolean mPlayOnResume;
  private VideoView mPlayerView;
  private int mPreviousOrientation = -1;
  private DataVideo mVideoData;
  private int mVideoPosition;

  static
  {
    SparseBooleanArray localSparseBooleanArray = new SparseBooleanArray();
    sPlayableTypes = localSparseBooleanArray;
    localSparseBooleanArray.put(18, true);
    sPlayableTypes.put(22, true);
    sPlayableTypes.put(36, true);
  }

  public VideoViewFragment()
  {
  }

  public VideoViewFragment(Intent paramIntent)
  {
    this();
    this.mIntent = paramIntent;
  }

  private void startPlayback()
  {
    String str;
    label79: Object localObject1;
    label95: Object localObject2;
    int j;
    if ((TextUtils.equals("READY", this.mVideoData.status)) || (TextUtils.equals("FINAL", this.mVideoData.status)))
      if (this.mPhotoId == 0L)
      {
        str = ((DataVideoStream)this.mVideoData.stream.get(0)).url;
        if (str != null)
        {
          this.mLoading = true;
          this.mPlayerView.setVideoURI(Uri.parse(str));
        }
      }
      else
      {
        Iterator localIterator = this.mVideoData.stream.iterator();
        localObject1 = null;
        if (localIterator.hasNext())
        {
          localObject2 = (DataVideoStream)localIterator.next();
          int i = ((DataVideoStream)localObject2).height.intValue();
          if ((!sPlayableTypes.get(((DataVideoStream)localObject2).formatId.intValue())) || (TextUtils.isEmpty(((DataVideoStream)localObject2).url)))
            break label297;
          if (i <= 640)
          {
            j = 1;
            label164: if ((localObject1 != null) && ((!this.mIsWiFiConnection) || (i <= 0)) && ((this.mIsWiFiConnection) || (j == 0) || (i <= 0)) && ((this.mIsWiFiConnection) || (i >= 0)))
              break label297;
          }
        }
      }
    while (true)
    {
      localObject1 = localObject2;
      break label95;
      j = 0;
      break label164;
      if (localObject1 == null)
      {
        str = null;
        break;
      }
      str = localObject1.url;
      break;
      this.mError = true;
      setupEmptyView(getView(), R.string.video_no_stream);
      break label79;
      if (TextUtils.equals("PENDING", this.mVideoData.status))
      {
        setupEmptyView(getView(), R.string.video_not_ready);
        break label79;
      }
      setupEmptyView(getView(), R.string.no_video);
      break label79;
      label297: localObject2 = localObject1;
    }
  }

  private void updateView(View paramView)
  {
    int i;
    if ((this.mVideoData != null) && ((TextUtils.equals("READY", this.mVideoData.status)) || (TextUtils.equals("FINAL", this.mVideoData.status))))
    {
      i = 1;
      if (!this.mLoading)
        break label57;
      showEmptyViewProgress(paramView);
    }
    while (true)
    {
      return;
      i = 0;
      break;
      label57: if ((i != 0) && (!this.mError))
        showContent(paramView);
      else
        showEmptyView(paramView);
    }
  }

  protected final boolean isEmpty()
  {
    if ((this.mVideoData != null) && ((TextUtils.equals("READY", this.mVideoData.status)) || (TextUtils.equals("FINAL", this.mVideoData.status))));
    for (int i = 1; ; i = 0)
    {
      boolean bool1;
      if ((i != 0) && (!this.mError))
      {
        boolean bool2 = this.mLoading;
        bool1 = false;
        if (!bool2);
      }
      else
      {
        bool1 = true;
      }
      return bool1;
    }
  }

  public void onClick(View paramView)
  {
    Object localObject = paramView.getTag();
    MediaController localMediaController;
    if ((localObject instanceof MediaController))
    {
      localMediaController = (MediaController)localObject;
      if (localMediaController.isShowing());
    }
    try
    {
      localMediaController.show();
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
      while (true)
      {
        this.mLoading = false;
        this.mError = true;
        View localView = getView();
        if (localView != null)
        {
          setupEmptyView(localView, R.string.video_no_stream);
          updateView(localView);
        }
      }
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mIntent = new Intent().putExtras(paramBundle.getBundle("com.google.android.apps.plus.VideoViewFragment.INTENT"));
      this.mVideoPosition = paramBundle.getInt("com.google.android.apps.plus.VideoViewFragment.POSITION", 0);
      this.mPlayOnResume = paramBundle.getBoolean("com.google.android.apps.plus.VideoViewFragment.PLAY_ON_RESUME");
      this.mPreviousOrientation = paramBundle.getInt("com.google.android.apps.plus.VideoViewFragment.PREVIOUS_ORIENTATION");
    }
    int i = getResources().getConfiguration().orientation;
    if (this.mPreviousOrientation != i)
    {
      this.mPreviousOrientation = i;
      this.mPlayOnResume = true;
    }
    this.mAccount = ((EsAccount)this.mIntent.getParcelableExtra("account"));
    this.mPhotoId = this.mIntent.getLongExtra("photo_id", 0L);
    this.mOwnerId = this.mIntent.getStringExtra("owner_id");
    if (this.mIntent.hasExtra("data"))
    {
      byte[] arrayOfByte = this.mIntent.getByteArrayExtra("data");
      this.mVideoData = null;
      if (arrayOfByte != null)
        this.mVideoData = ((DataVideo)DataVideoJson.getInstance().fromByteArray(arrayOfByte));
    }
    if (this.mIntent.hasExtra("auth_key"))
      this.mAuthkey = this.mIntent.getStringExtra("auth_key");
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Uri localUri = EsProvider.appendAccountParameter(ContentUris.withAppendedId(EsProvider.PHOTO_BY_PHOTO_ID_URI, this.mPhotoId), this.mAccount);
    return new EsCursorLoader(getActivity(), localUri, PROJECTION, null, null, null);
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView1 = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle, R.layout.video_view_fragment);
    MediaController localMediaController = new MediaController(getActivity());
    View localView2 = localView1.findViewById(R.id.videolayout);
    localView2.setOnClickListener(this);
    localView2.setTag(localMediaController);
    localMediaController.setAnchorView(localView2);
    localMediaController.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    this.mPlayerView = ((VideoView)localView1.findViewById(R.id.videoplayer));
    this.mPlayerView.setMediaController(localMediaController);
    this.mPlayerView.setOnPreparedListener(this);
    this.mPlayerView.setOnErrorListener(this);
    if (this.mVideoData == null)
    {
      this.mLoading = true;
      getLoaderManager().initLoader(0, null, this);
    }
    setupEmptyView(localView1, R.string.no_video);
    updateView(localView1);
    return localView1;
  }

  public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    if ((!this.mPerformedRefetch) && (paramInt1 == 1))
    {
      this.mPerformedRefetch = true;
      EsService.getPhoto(getActivity(), this.mAccount, this.mOwnerId, this.mPhotoId, this.mAuthkey);
    }
    while (true)
    {
      return true;
      this.mError = true;
      this.mLoading = false;
      View localView = getView();
      if (localView != null)
      {
        setupEmptyView(localView, R.string.video_no_stream);
        updateView(localView);
      }
    }
  }

  public boolean onInfo(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default:
    case 701:
    case 702:
    case 1:
    case 100:
    case 200:
    }
    while (true)
    {
      View localView = getView();
      if (localView != null)
        updateView(localView);
      return true;
      this.mLoading = true;
      this.mError = false;
      continue;
      this.mLoading = false;
      this.mError = false;
      continue;
      this.mLoading = false;
      this.mError = true;
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onPause()
  {
    super.onPause();
    if ((this.mPlayerView != null) && (this.mPlayerView.isPlaying()))
    {
      if (!this.mPlayerView.canPause())
        break label46;
      this.mPlayerView.pause();
    }
    while (true)
    {
      EsService.unregisterListener(this.mEsListener);
      return;
      label46: this.mPlayerView.stopPlayback();
    }
  }

  public void onPrepared(MediaPlayer paramMediaPlayer)
  {
    this.mLoading = false;
    paramMediaPlayer.setOnInfoListener(this);
    if (this.mVideoPosition == 0)
      this.mPlayerView.start();
    while (true)
    {
      View localView = getView();
      if (localView != null)
        updateView(localView);
      return;
      this.mPlayerView.seekTo(this.mVideoPosition);
      this.mPlayerView.start();
    }
  }

  public final void onResume()
  {
    int i = 1;
    super.onResume();
    EsService.registerListener(this.mEsListener);
    NetworkInfo localNetworkInfo = ((ConnectivityManager)getActivity().getSystemService("connectivity")).getActiveNetworkInfo();
    if ((localNetworkInfo != null) && (localNetworkInfo.getType() == i));
    while (true)
    {
      this.mIsWiFiConnection = i;
      if ((this.mVideoData != null) && (this.mPlayOnResume))
        startPlayback();
      this.mPlayOnResume = false;
      updateView(getView());
      return;
      i = 0;
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mIntent != null)
    {
      paramBundle.putParcelable("com.google.android.apps.plus.VideoViewFragment.INTENT", this.mIntent.getExtras());
      paramBundle.putInt("com.google.android.apps.plus.VideoViewFragment.POSITION", this.mPlayerView.getCurrentPosition());
      paramBundle.putBoolean("com.google.android.apps.plus.VideoViewFragment.PLAY_ON_RESUME", this.mPlayOnResume);
      paramBundle.putInt("com.google.android.apps.plus.VideoViewFragment.PREVIOUS_ORIENTATION", this.mPreviousOrientation);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.VideoViewFragment
 * JD-Core Version:    0.6.2
 */