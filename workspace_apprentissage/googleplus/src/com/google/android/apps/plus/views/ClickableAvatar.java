package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.View;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.service.ImageResourceManager;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.service.ResourceConsumer;

final class ClickableAvatar
  implements ResourceConsumer, ClickableItem
{
  private static Paint sImageSelectedPaint;
  private Resource mAvatarResource;
  private int mAvatarSizeCategory;
  private String mAvatarUrl;
  private final ClickableUserImage.UserImageClickListener mClickListener;
  private boolean mClicked;
  private CharSequence mContentDescription;
  private final Rect mContentRect;
  private final String mGaiaId;
  private final String mUserName;
  private final View mView;

  public ClickableAvatar(View paramView, String paramString1, String paramString2, String paramString3, ClickableUserImage.UserImageClickListener paramUserImageClickListener, int paramInt)
  {
    this.mView = paramView;
    Context localContext = paramView.getContext();
    this.mContentRect = new Rect();
    this.mClickListener = paramUserImageClickListener;
    this.mGaiaId = paramString1;
    this.mUserName = paramString3;
    this.mContentDescription = paramString3;
    this.mAvatarUrl = paramString2;
    this.mAvatarSizeCategory = 2;
    if (sImageSelectedPaint == null)
    {
      Paint localPaint = new Paint();
      sImageSelectedPaint = localPaint;
      localPaint.setAntiAlias(true);
      sImageSelectedPaint.setStrokeWidth(4.0F);
      sImageSelectedPaint.setColor(localContext.getApplicationContext().getResources().getColor(R.color.image_selected_stroke));
      sImageSelectedPaint.setStyle(Paint.Style.STROKE);
    }
  }

  public final void bindResources()
  {
    if (this.mAvatarUrl != null)
      this.mAvatarResource = ImageResourceManager.getInstance(this.mView.getContext()).getAvatar(this.mAvatarUrl, this.mAvatarSizeCategory, true, this);
  }

  public final void drawSelectionRect(Canvas paramCanvas)
  {
    paramCanvas.drawCircle(this.mContentRect.centerX(), this.mContentRect.centerY(), this.mContentRect.width() / 2, sImageSelectedPaint);
  }

  public final Bitmap getBitmap()
  {
    if ((this.mAvatarResource != null) && (this.mAvatarResource.getStatus() == 1));
    for (Bitmap localBitmap = (Bitmap)this.mAvatarResource.getResource(); ; localBitmap = null)
      return localBitmap;
  }

  public final CharSequence getContentDescription()
  {
    return this.mContentDescription;
  }

  public final Rect getRect()
  {
    return this.mContentRect;
  }

  public final boolean handleEvent(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 1;
    if (paramInt3 == 3)
      this.mClicked = false;
    while (true)
    {
      return i;
      if (!this.mContentRect.contains(paramInt1, paramInt2))
      {
        if (paramInt3 == i)
          this.mClicked = false;
        i = 0;
      }
      else
      {
        switch (paramInt3)
        {
        default:
          break;
        case 0:
          this.mClicked = i;
          break;
        case 1:
          if ((this.mClicked) && (this.mClickListener != null))
            this.mClickListener.onUserImageClick(this.mGaiaId, this.mUserName);
          this.mClicked = false;
        }
      }
    }
  }

  public final boolean isClicked()
  {
    return this.mClicked;
  }

  public final void onResourceStatusChange$1574fca0(Resource paramResource)
  {
    this.mView.invalidate();
  }

  public final void setRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mContentRect.set(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public final void unbindResources()
  {
    if (this.mAvatarResource != null)
    {
      this.mAvatarResource.unregister(this);
      this.mAvatarResource = null;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ClickableAvatar
 * JD-Core Version:    0.6.2
 */