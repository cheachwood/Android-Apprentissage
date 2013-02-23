package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.ColumnGridView.OnScrollListener;
import com.google.android.apps.plus.views.ComposeBarView;
import com.google.android.apps.plus.views.ComposeBarView.OnComposeBarMeasuredListener;
import com.google.android.apps.plus.views.ImageResourceView;
import java.util.ArrayList;

public final class ComposeBarController
  implements View.OnClickListener, ColumnGridView.OnScrollListener, ComposeBarView.OnComposeBarMeasuredListener
{
  private static int sActionBarHeight;
  private static Drawable sOverlayDrawable;
  private static int sRecentImagesDefaultPadding;
  private static int sRecentImagesDimension;
  private static Drawable sSelectorDrawable;
  private boolean mAlwaysHide;
  private ComposeBarListener mComposeBarListener;
  private int mCumulativeTouchSlop;
  private float mCurrentOffset;
  private int mCurrentTouchDelta;
  private View mFloatingBarView;
  private boolean mLandscape;
  private ArrayList<MediaRef> mRecentImageRefs;
  private ArrayList<ImageResourceView> mRecentImageViews = new ArrayList(10);
  private int mRecentImagesMargin;
  private int mRecentImagesThatFitOnScreen = 10;
  private final Animation.AnimationListener mSlideInListener = new Animation.AnimationListener()
  {
    public final void onAnimationEnd(Animation paramAnonymousAnimation)
    {
      switch (ComposeBarController.this.mState)
      {
      case 2:
      default:
      case 1:
        while (true)
        {
          ComposeBarController.this.updateVisibility();
          return;
          ComposeBarController.access$002(ComposeBarController.this, 2);
          ComposeBarController.access$202(ComposeBarController.this, 0.0F);
        }
      case 3:
      }
      ComposeBarController.access$002(ComposeBarController.this, 0);
      ComposeBarController localComposeBarController = ComposeBarController.this;
      if (ComposeBarController.this.mLandscape);
      for (float f = ComposeBarController.this.mFloatingBarView.getWidth(); ; f = ComposeBarController.this.mFloatingBarView.getHeight())
      {
        ComposeBarController.access$202(localComposeBarController, f);
        break;
      }
    }

    public final void onAnimationRepeat(Animation paramAnonymousAnimation)
    {
    }

    public final void onAnimationStart(Animation paramAnonymousAnimation)
    {
      switch (ComposeBarController.this.mState)
      {
      case 1:
      default:
      case 0:
      case 2:
      }
      while (true)
      {
        ComposeBarController.this.updateVisibility();
        return;
        ComposeBarController.access$002(ComposeBarController.this, 1);
        continue;
        ComposeBarController.access$002(ComposeBarController.this, 3);
      }
    }
  };
  private int mState = 0;

  public ComposeBarController(View paramView, boolean paramBoolean, ComposeBarListener paramComposeBarListener)
  {
    this.mFloatingBarView = paramView;
    this.mLandscape = paramBoolean;
    this.mComposeBarListener = paramComposeBarListener;
    ((ComposeBarView)this.mFloatingBarView).setOnComposeBarMeasuredListener(this);
    if (sRecentImagesDimension == 0)
    {
      Resources localResources = paramView.getResources();
      sOverlayDrawable = localResources.getDrawable(R.drawable.recent_images_border);
      sSelectorDrawable = localResources.getDrawable(R.drawable.list_selected_holo);
      sRecentImagesDimension = (int)localResources.getDimension(R.dimen.compose_bar_recent_images_dimension);
      sRecentImagesDefaultPadding = (int)localResources.getDimension(R.dimen.compose_bar_recent_images_default_padding);
      sActionBarHeight = (int)localResources.getDimension(R.dimen.host_action_bar_height);
    }
    Context localContext = paramView.getContext();
    this.mCumulativeTouchSlop = ViewConfiguration.get(localContext).getScaledTouchSlop();
    for (int i = 9; i >= 0; i--)
    {
      ImageResourceView localImageResourceView = new ImageResourceView(localContext);
      localImageResourceView.setLayoutParams(new LinearLayout.LayoutParams(sRecentImagesDimension, sRecentImagesDimension));
      localImageResourceView.setPadding(sRecentImagesDefaultPadding, sRecentImagesDefaultPadding, sRecentImagesDefaultPadding, sRecentImagesDefaultPadding);
      localImageResourceView.setSizeCategory(2);
      localImageResourceView.setScaleMode(1);
      localImageResourceView.setFadeIn(true);
      localImageResourceView.setClickable(true);
      localImageResourceView.setSelector(sSelectorDrawable);
      this.mRecentImageViews.add(localImageResourceView);
    }
  }

  private void dismissRecentImages(boolean paramBoolean)
  {
    setRecentImageRefs(null);
    if (this.mComposeBarListener != null)
      this.mComposeBarListener.onDismissRecentImages(paramBoolean);
  }

  private LinearLayout.LayoutParams getRecentImagesLayoutParams()
  {
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(sRecentImagesDimension, sRecentImagesDimension, 1.0F);
    if (this.mLandscape)
      localLayoutParams.setMargins(0, this.mRecentImagesMargin, 0, this.mRecentImagesMargin);
    while (true)
    {
      return localLayoutParams;
      localLayoutParams.setMargins(this.mRecentImagesMargin, 0, this.mRecentImagesMargin, 0);
    }
  }

  private boolean shouldShowRecentImages()
  {
    if ((this.mRecentImageRefs != null) && (this.mRecentImageRefs.size() > 0));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void startAnimation(float paramFloat, int paramInt)
  {
    if (this.mLandscape);
    for (TranslateAnimation localTranslateAnimation = new TranslateAnimation(this.mCurrentOffset, paramFloat, 0.0F, 0.0F); ; localTranslateAnimation = new TranslateAnimation(0.0F, 0.0F, this.mCurrentOffset, paramFloat))
    {
      localTranslateAnimation.setDuration(paramInt);
      localTranslateAnimation.setFillAfter(true);
      if (paramInt > 0)
        localTranslateAnimation.setAnimationListener(this.mSlideInListener);
      this.mFloatingBarView.startAnimation(localTranslateAnimation);
      return;
    }
  }

  private void updateBarView()
  {
    int i = 8;
    View localView1 = this.mFloatingBarView.findViewById(R.id.compose_image_bar);
    if ((this.mRecentImageRefs == null) || (this.mRecentImageViews == null))
      localView1.setVisibility(i);
    while (true)
    {
      return;
      boolean bool = shouldShowRecentImages();
      if (bool)
        i = 0;
      localView1.setVisibility(i);
      LinearLayout localLinearLayout = (LinearLayout)localView1.findViewById(R.id.compose_image_container);
      localLinearLayout.removeAllViews();
      View localView2 = this.mFloatingBarView.findViewById(R.id.compose_image_bar_close);
      if (bool);
      int j;
      for (ComposeBarController localComposeBarController = this; ; localComposeBarController = null)
      {
        localView2.setOnClickListener(localComposeBarController);
        if (!bool)
          break label331;
        j = Math.min(this.mRecentImageRefs.size(), this.mRecentImagesThatFitOnScreen);
        for (int k = 0; k < j; k++)
        {
          ImageResourceView localImageResourceView3 = (ImageResourceView)this.mRecentImageViews.get(k);
          localImageResourceView3.onRecycle();
          localImageResourceView3.setMediaRef((MediaRef)this.mRecentImageRefs.get(k));
          localImageResourceView3.setOnClickListener(this);
          localImageResourceView3.setOverlay(sOverlayDrawable);
          localLinearLayout.addView(localImageResourceView3, getRecentImagesLayoutParams());
        }
      }
      for (int m = j; m < this.mRecentImagesThatFitOnScreen; m++)
      {
        ImageResourceView localImageResourceView2 = (ImageResourceView)this.mRecentImageViews.get(m);
        localImageResourceView2.onRecycle();
        localImageResourceView2.setBackgroundResource(R.drawable.empty_recent_image);
        localImageResourceView2.setOnClickListener(null);
        localImageResourceView2.setSelected(false);
        localLinearLayout.addView(localImageResourceView2, getRecentImagesLayoutParams());
      }
      for (int n = Math.max(this.mRecentImageRefs.size(), this.mRecentImagesThatFitOnScreen); n < 10; n++)
      {
        ImageResourceView localImageResourceView1 = (ImageResourceView)this.mRecentImageViews.get(n);
        localImageResourceView1.onRecycle();
        localImageResourceView1.setBackgroundResource(0);
        localImageResourceView1.setOnClickListener(null);
        localImageResourceView1.setSelected(false);
      }
      label331: localView1.invalidate();
    }
  }

  private void updateShareButton()
  {
    if ((this.mRecentImageRefs == null) || (this.mRecentImageViews == null))
      return;
    int i = 0;
    int j = this.mRecentImageRefs.size();
    label25: boolean bool = false;
    Button localButton;
    if (i < j)
    {
      if (((ImageResourceView)this.mRecentImageViews.get(i)).isSelected())
        bool = true;
    }
    else
    {
      localButton = (Button)this.mFloatingBarView.findViewById(R.id.compose_image_bar_share);
      if (!bool)
        break label111;
    }
    label111: for (int k = R.color.compose_bar_share_button_enabled; ; k = R.color.compose_bar_share_button_disabled)
    {
      localButton.setEnabled(bool);
      localButton.setTextColor(localButton.getResources().getColor(k));
      localButton.setOnClickListener(this);
      break;
      i++;
      break label25;
    }
  }

  private void updateVisibility()
  {
    boolean bool;
    View localView;
    if (this.mState == 0)
    {
      bool = false;
      localView = this.mFloatingBarView;
      if (!bool)
        break label231;
    }
    label231: for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      this.mFloatingBarView.setClickable(bool);
      this.mFloatingBarView.findViewById(R.id.compose_post).setClickable(bool);
      this.mFloatingBarView.findViewById(R.id.compose_photos).setClickable(bool);
      this.mFloatingBarView.findViewById(R.id.compose_location).setClickable(bool);
      this.mFloatingBarView.findViewById(R.id.compose_custom).setClickable(bool);
      this.mFloatingBarView.findViewById(R.id.compose_image_bar_share).setClickable(bool);
      this.mFloatingBarView.findViewById(R.id.compose_image_bar_close).setClickable(bool);
      for (int j = -1 + this.mRecentImageViews.size(); j >= 0; j--)
        ((ImageResourceView)this.mRecentImageViews.get(j)).setClickable(bool);
      if (((this.mLandscape) && (this.mFloatingBarView.getWidth() > 0) && (this.mCurrentOffset > this.mFloatingBarView.getWidth())) || ((!this.mLandscape) && (this.mFloatingBarView.getHeight() > 0) && (this.mCurrentOffset > this.mFloatingBarView.getHeight())))
      {
        bool = false;
        break;
      }
      bool = true;
      break;
    }
    if (bool)
      this.mFloatingBarView.requestLayout();
  }

  public final void forceHide()
  {
    this.mAlwaysHide = true;
    this.mFloatingBarView.clearAnimation();
    this.mState = 0;
    if (this.mLandscape);
    for (float f = this.mFloatingBarView.getWidth(); ; f = this.mFloatingBarView.getHeight())
    {
      startAnimation(f, 0);
      updateVisibility();
      return;
    }
  }

  public final void forceShow()
  {
    this.mAlwaysHide = false;
    this.mFloatingBarView.clearAnimation();
    this.mState = 2;
    startAnimation(0.0F, 0);
    updateVisibility();
  }

  public final void onClick(View paramView)
  {
    boolean bool = true;
    int i = paramView.getId();
    if (i == R.id.compose_image_bar_close)
      dismissRecentImages(bool);
    while (true)
    {
      return;
      if (i != R.id.compose_image_bar_share)
        break;
      ArrayList localArrayList = new ArrayList();
      int j = 0;
      int k = this.mRecentImageRefs.size();
      while (j < k)
      {
        if (((ImageResourceView)this.mRecentImageViews.get(j)).isSelected())
          localArrayList.add(this.mRecentImageRefs.get(j));
        j++;
      }
      if (this.mComposeBarListener != null)
        this.mComposeBarListener.onShareRecentImages(localArrayList);
      dismissRecentImages(false);
    }
    if (!paramView.isSelected());
    while (true)
    {
      paramView.setSelected(bool);
      updateShareButton();
      break;
      bool = false;
    }
  }

  public final void onComposeBarMeasured(int paramInt1, int paramInt2)
  {
    int i;
    int j;
    if (this.mLandscape)
    {
      i = paramInt2 - sActionBarHeight;
      j = sRecentImagesDefaultPadding + sRecentImagesDimension;
      this.mRecentImagesThatFitOnScreen = Math.max(0, Math.min(10, i / j));
      if (this.mRecentImagesThatFitOnScreen <= 0)
        break label76;
    }
    label76: for (this.mRecentImagesMargin = ((i - j * this.mRecentImagesThatFitOnScreen) / (2 * this.mRecentImagesThatFitOnScreen)); ; this.mRecentImagesMargin = 0)
    {
      updateBarView();
      return;
      i = paramInt1;
      break;
    }
  }

  public final void onScroll(ColumnGridView paramColumnGridView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (this.mAlwaysHide)
      return;
    int i;
    if (paramInt2 < paramInt3)
    {
      if (((paramInt5 < 0) && (this.mCurrentTouchDelta > 0)) || ((paramInt5 > 0) && (this.mCurrentTouchDelta < 0)))
        this.mCurrentTouchDelta = 0;
      this.mCurrentTouchDelta = (paramInt5 + this.mCurrentTouchDelta);
      if (this.mCurrentTouchDelta <= 0)
        break label141;
      i = 1;
      label64: if ((this.mState == 0) && (shouldShowRecentImages()))
        dismissRecentImages(false);
      if ((this.mCurrentTouchDelta <= -this.mCumulativeTouchSlop) || (this.mCurrentTouchDelta >= this.mCumulativeTouchSlop))
      {
        if (((this.mState != 0) && (this.mState != 3)) || (i == 0))
          break label147;
        startAnimation(0.0F, 200);
      }
    }
    while (true)
    {
      updateVisibility();
      break;
      break;
      label141: i = 0;
      break label64;
      label147: if (((this.mState == 2) || (this.mState == 1)) && (i == 0))
        if (this.mLandscape)
        {
          startAnimation(this.mFloatingBarView.getWidth(), 200);
          this.mCurrentOffset = this.mFloatingBarView.getWidth();
        }
        else
        {
          startAnimation(this.mFloatingBarView.getHeight(), 200);
          this.mCurrentOffset = this.mFloatingBarView.getHeight();
        }
    }
  }

  public final void onScrollStateChanged(ColumnGridView paramColumnGridView, int paramInt)
  {
    if (this.mAlwaysHide);
    while (true)
    {
      return;
      if (paramInt != 1)
        this.mCurrentTouchDelta = 0;
    }
  }

  public final void setRecentImageRefs(ArrayList<MediaRef> paramArrayList)
  {
    this.mRecentImageRefs = paramArrayList;
    updateBarView();
    updateShareButton();
  }

  public static abstract interface ComposeBarListener
  {
    public abstract void onDismissRecentImages(boolean paramBoolean);

    public abstract void onShareRecentImages(ArrayList<MediaRef> paramArrayList);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ComposeBarController
 * JD-Core Version:    0.6.2
 */