package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.fragments.HostedStreamOneUpFragment;
import com.google.android.apps.plus.fragments.StreamOneUpCallbacks;
import com.google.android.apps.plus.service.EsService;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class StreamOneUpActivity extends HostActivity
  implements StreamOneUpCallbacks
{
  private HostedStreamOneUpFragment mFragment;
  private boolean mFullScreen;
  private boolean mKeyboardIsVisible;
  private Set<OnScreenListener> mScreenListeners = new HashSet();

  public final void addScreenListener(OnScreenListener paramOnScreenListener)
  {
    this.mScreenListeners.add(paramOnScreenListener);
  }

  protected final Fragment createDefaultFragment()
  {
    return new HostedStreamOneUpFragment();
  }

  protected final int getContentView()
  {
    return R.layout.host_frame_layout_activity;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.ACTIVITY;
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    super.onAttachFragment(paramFragment);
    if ((paramFragment instanceof HostedStreamOneUpFragment))
      this.mFragment = ((HostedStreamOneUpFragment)paramFragment);
  }

  public void onBackPressed()
  {
    if (this.mFullScreen)
      toggleFullScreen();
    while (true)
    {
      return;
      this.mFragment.onCancelRequested();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null)
    {
      String str = getIntent().getStringExtra("notif_id");
      if (str != null)
        EsService.markNotificationAsRead(this, getAccount(), str);
    }
    while (true)
    {
      final View localView = findViewById(R.id.host);
      localView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
      {
        public final void onGlobalLayout()
        {
          if (localView.getRootView().getHeight() - localView.getHeight() > 100)
          {
            StreamOneUpActivity.access$002(StreamOneUpActivity.this, true);
            Iterator localIterator2 = StreamOneUpActivity.this.mScreenListeners.iterator();
            while (localIterator2.hasNext())
              ((StreamOneUpActivity.OnScreenListener)localIterator2.next()).enableImageTransforms(false);
          }
          StreamOneUpActivity.access$002(StreamOneUpActivity.this, false);
          Iterator localIterator1 = StreamOneUpActivity.this.mScreenListeners.iterator();
          while (localIterator1.hasNext())
            ((StreamOneUpActivity.OnScreenListener)localIterator1.next()).enableImageTransforms(true);
        }
      });
      return;
      this.mFullScreen = paramBundle.getBoolean("com.google.android.apps.plus.HostedStreamOneUpFragment.FULLSCREEN", false);
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("com.google.android.apps.plus.HostedStreamOneUpFragment.FULLSCREEN", this.mFullScreen);
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

  public static abstract interface OnScreenListener
  {
    public abstract void enableImageTransforms(boolean paramBoolean);

    public abstract void onFullScreenChanged$25decb5(boolean paramBoolean);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.StreamOneUpActivity
 * JD-Core Version:    0.6.2
 */