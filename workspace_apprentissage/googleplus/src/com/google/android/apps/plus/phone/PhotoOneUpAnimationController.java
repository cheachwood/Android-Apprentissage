package com.google.android.apps.plus.phone;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

public class PhotoOneUpAnimationController
{
  private final boolean mAdjustMargins;
  private float mCurrentOffset;
  private final boolean mSlideFromTop;
  private final Animation.AnimationListener mSlideInListener = new Animation.AnimationListener()
  {
    public final void onAnimationEnd(Animation paramAnonymousAnimation)
    {
      switch (PhotoOneUpAnimationController.this.mState)
      {
      case 2:
      default:
      case 1:
      case 3:
      }
      while (true)
      {
        PhotoOneUpAnimationController.this.updateVisibility();
        return;
        PhotoOneUpAnimationController.access$002(PhotoOneUpAnimationController.this, 2);
        PhotoOneUpAnimationController.access$402(PhotoOneUpAnimationController.this, 0.0F);
        continue;
        PhotoOneUpAnimationController.access$002(PhotoOneUpAnimationController.this, 0);
        PhotoOneUpAnimationController.access$402(PhotoOneUpAnimationController.this, PhotoOneUpAnimationController.this.getHideOffset(PhotoOneUpAnimationController.this.mSlideFromTop));
        if (PhotoOneUpAnimationController.this.mAdjustMargins)
          PhotoOneUpAnimationController.access$200(PhotoOneUpAnimationController.this, true);
      }
    }

    public final void onAnimationRepeat(Animation paramAnonymousAnimation)
    {
    }

    public final void onAnimationStart(Animation paramAnonymousAnimation)
    {
      switch (PhotoOneUpAnimationController.this.mState)
      {
      case 1:
      default:
      case 0:
      case 2:
      }
      while (true)
      {
        PhotoOneUpAnimationController.this.updateVisibility();
        return;
        PhotoOneUpAnimationController.access$002(PhotoOneUpAnimationController.this, 1);
        if (PhotoOneUpAnimationController.this.mAdjustMargins)
        {
          PhotoOneUpAnimationController.access$200(PhotoOneUpAnimationController.this, false);
          continue;
          PhotoOneUpAnimationController.access$002(PhotoOneUpAnimationController.this, 3);
        }
      }
    }
  };
  private int mState = 2;
  private final View mView;

  public PhotoOneUpAnimationController(View paramView, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mView = paramView;
    this.mSlideFromTop = paramBoolean1;
    this.mAdjustMargins = paramBoolean2;
  }

  private void startAnimation(float paramFloat, int paramInt)
  {
    Animation localAnimation = this.mView.getAnimation();
    if (localAnimation != null)
      localAnimation.cancel();
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(0.0F, 0.0F, this.mCurrentOffset, paramFloat);
    localTranslateAnimation.setDuration(100L);
    localTranslateAnimation.setFillAfter(true);
    localTranslateAnimation.setAnimationListener(this.mSlideInListener);
    this.mView.startAnimation(localTranslateAnimation);
  }

  private void updateVisibility()
  {
    int j;
    View localView1;
    if (this.mState == 0)
    {
      j = 0;
      localView1 = this.mView;
      if ((j == 0) && (!this.mAdjustMargins))
        break label142;
    }
    label142: for (int k = 0; ; k = 8)
    {
      localView1.setVisibility(k);
      View localView2 = this.mView;
      boolean bool1;
      if (j == 0)
      {
        boolean bool2 = this.mAdjustMargins;
        bool1 = false;
        if (!bool2);
      }
      else
      {
        bool1 = true;
      }
      localView2.setClickable(bool1);
      return;
      int i = getHideOffset(this.mSlideFromTop);
      if (((!this.mSlideFromTop) && (this.mView.getHeight() > 0) && (this.mCurrentOffset >= i)) || ((this.mSlideFromTop) && (this.mView.getHeight() > 0) && (this.mCurrentOffset <= i)))
      {
        j = 0;
        break;
      }
      j = 1;
      break;
    }
  }

  public final void animate(boolean paramBoolean)
  {
    if (((this.mState == 0) || (this.mState == 3)) && (!paramBoolean))
      startAnimation(0.0F, 100);
    while (true)
    {
      updateVisibility();
      return;
      if (((this.mState == 2) || (this.mState == 1)) && (paramBoolean))
      {
        int i = getHideOffset(this.mSlideFromTop);
        startAnimation(i, 100);
        this.mCurrentOffset = i;
      }
    }
  }

  protected int getHideOffset(boolean paramBoolean)
  {
    if (this.mSlideFromTop);
    for (int i = -(this.mView.getHeight() + this.mView.getPaddingTop()); ; i = this.mView.getHeight() + this.mView.getPaddingBottom())
      return i;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PhotoOneUpAnimationController
 * JD-Core Version:    0.6.2
 */