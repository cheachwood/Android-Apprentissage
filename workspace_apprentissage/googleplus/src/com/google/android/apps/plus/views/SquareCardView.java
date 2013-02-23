package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.DbEmbedSquare;
import com.google.android.apps.plus.service.ImageResourceManager;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.util.LinksRenderUtils;
import com.google.android.apps.plus.util.TextPaintUtils;

public class SquareCardView extends StreamCardView
{
  private static ImageResourceManager sImageResourceManager;
  protected static TextPaint sInvitationTextPaint;
  private static boolean sSquareInviteCardViewInitialized;
  protected static TextPaint sSquareNameTextPaint;
  protected DbEmbedSquare mDbEmbedSquare;
  protected Rect mDestRect;
  protected Resource mImageResource;
  protected MediaRef mMediaRef;
  protected StaticLayout mSquareInvitationLayout;
  protected StaticLayout mSquareNameLayout;
  protected Rect mSrcRect;

  public SquareCardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public SquareCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (!sSquareInviteCardViewInitialized)
    {
      sSquareInviteCardViewInitialized = true;
      sImageResourceManager = ImageResourceManager.getInstance(paramContext);
      Resources localResources = paramContext.getResources();
      TextPaint localTextPaint1 = new TextPaint();
      sInvitationTextPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sInvitationTextPaint.setColor(localResources.getColor(R.color.card_square_invite_invitation_text));
      sInvitationTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sInvitationTextPaint.setTextSize(localResources.getDimension(R.dimen.card_square_invite_invitation_text_size));
      sInvitationTextPaint.setShadowLayer(localResources.getDimension(R.dimen.card_square_invite_shadow_radius), localResources.getDimension(R.dimen.card_square_invite_shadow_x), localResources.getDimension(R.dimen.card_square_invite_shadow_y), localResources.getColor(R.color.card_square_invite_shadow_text));
      TextPaintUtils.registerTextPaint(sInvitationTextPaint, R.dimen.card_square_invite_invitation_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sSquareNameTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sSquareNameTextPaint.setColor(localResources.getColor(R.color.card_square_invite_name_text));
      sSquareNameTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sSquareNameTextPaint.setTextSize(localResources.getDimension(R.dimen.card_square_invite_name_text_size));
      sSquareNameTextPaint.setShadowLayer(localResources.getDimension(R.dimen.card_square_invite_shadow_radius), localResources.getDimension(R.dimen.card_square_invite_shadow_x), localResources.getDimension(R.dimen.card_square_invite_shadow_y), localResources.getColor(R.color.card_square_invite_shadow_text));
      TextPaintUtils.registerTextPaint(sSquareNameTextPaint, R.dimen.card_square_invite_name_text_size);
    }
    this.mSrcRect = new Rect();
    this.mDestRect = new Rect();
  }

  protected final int draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Bitmap localBitmap;
    boolean bool;
    label18: int i;
    int j;
    if (this.mImageResource == null)
    {
      localBitmap = null;
      if (localBitmap == null)
        break label378;
      bool = true;
      drawMediaTopAreaStage(paramCanvas, paramInt3, paramInt4, bool, this.mDestRect, sMediaTopAreaBackgroundPaint);
      if (localBitmap != null)
      {
        if (this.mSrcRect.isEmpty())
          createSourceRectForMediaImage(this.mSrcRect, localBitmap, paramInt3, paramInt4);
        paramCanvas.drawBitmap(localBitmap, this.mSrcRect, this.mDestRect, sResizePaint);
      }
      paramCanvas.drawRect(this.mDestRect, LinksRenderUtils.getTransparentOverlayPaint());
      i = paramInt3 + sXDoublePadding;
      j = (int)((paramInt4 + sYDoublePadding) * getMediaHeightPercentage());
      if (this.mPlusOneButton != null)
        break label384;
    }
    label384: for (int k = j; ; k = j - this.mPlusOneButton.getRect().height())
    {
      int m = sSquareBitmap.getHeight() + sContentYPadding;
      if (this.mSquareInvitationLayout != null)
        m += this.mSquareInvitationLayout.getHeight() + sContentYPadding;
      if (this.mSquareNameLayout != null)
        m += this.mSquareNameLayout.getHeight() + sContentYPadding;
      int n = (k - m) / 2;
      paramCanvas.drawBitmap(sSquareBitmap, (i - sSquareBitmap.getWidth()) / 2, n, null);
      int i1 = n + (sSquareBitmap.getHeight() + sContentYPadding);
      if (this.mSquareInvitationLayout != null)
      {
        paramCanvas.translate(paramInt1, i1);
        this.mSquareInvitationLayout.draw(paramCanvas);
        paramCanvas.translate(-paramInt1, -i1);
        i1 += this.mSquareInvitationLayout.getHeight() + sContentYPadding;
      }
      if (this.mSquareNameLayout != null)
      {
        paramCanvas.translate(paramInt1, i1);
        this.mSquareNameLayout.draw(paramCanvas);
        paramCanvas.translate(-paramInt1, -i1);
        this.mSquareNameLayout.getHeight();
      }
      drawMediaTopAreaShadow(paramCanvas, paramInt3, paramInt4);
      drawPlusOneBar(paramCanvas);
      drawMediaBottomArea$1be95c43(paramCanvas, paramInt1, paramInt3, paramInt4);
      drawCornerIcon(paramCanvas);
      return paramInt4;
      localBitmap = (Bitmap)this.mImageResource.getResource();
      break;
      label378: bool = false;
      break label18;
    }
  }

  public final String getSquareId()
  {
    return this.mDbEmbedSquare.getAboutSquareId();
  }

  public final void init(Cursor paramCursor, int paramInt1, int paramInt2, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamCardView.ViewedListener paramViewedListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener)
  {
    super.init(paramCursor, paramInt1, paramInt2, paramOnClickListener, paramItemClickListener, paramViewedListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener);
    byte[] arrayOfByte = paramCursor.getBlob(27);
    if (arrayOfByte != null)
    {
      this.mDbEmbedSquare = DbEmbedSquare.deserialize(arrayOfByte);
      String str = this.mDbEmbedSquare.getImageUrl();
      if (!TextUtils.isEmpty(str))
        this.mMediaRef = new MediaRef(str, MediaRef.MediaType.IMAGE);
    }
  }

  protected final int layoutElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt3 + sXDoublePadding;
    int j = (int)((paramInt4 + sYDoublePadding) * getMediaHeightPercentage());
    this.mBackgroundRect.set(0, j, getMeasuredWidth(), getMeasuredHeight());
    createPlusOneBar(paramInt1, j + sTopBorderPadding - sYPadding, paramInt3);
    createMediaBottomArea(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mPlusOneButton == null);
    for (int k = j; ; k = j - this.mPlusOneButton.getRect().height())
    {
      int m = k - sSquareBitmap.getHeight() - 3 * sContentYPadding;
      if (!TextUtils.isEmpty(this.mDbEmbedSquare.getAboutSquareName()))
      {
        int i1 = (m - paramInt2) / (int)(sSquareNameTextPaint.descent() - sSquareNameTextPaint.ascent());
        if (i1 > 0)
        {
          this.mSquareNameLayout = TextPaintUtils.createConstrainedStaticLayout(sSquareNameTextPaint, this.mDbEmbedSquare.getAboutSquareName(), paramInt3, i1, Layout.Alignment.ALIGN_CENTER);
          paramInt2 += this.mSquareNameLayout.getHeight() + sContentYPadding;
        }
      }
      if (this.mDbEmbedSquare.isInvitation())
      {
        int n = (m - paramInt2) / (int)(sInvitationTextPaint.descent() - sInvitationTextPaint.ascent());
        if (n > 0)
        {
          this.mSquareInvitationLayout = TextPaintUtils.createConstrainedStaticLayout(sInvitationTextPaint, getContext().getString(R.string.card_square_invite_invitation), paramInt3, n, Layout.Alignment.ALIGN_CENTER);
          this.mSquareInvitationLayout.getHeight();
        }
      }
      this.mSrcRect.setEmpty();
      this.mDestRect.set(sLeftBorderPadding, sTopBorderPadding, i + sLeftBorderPadding, j + sTopBorderPadding);
      return paramInt4;
    }
  }

  protected final void onBindResources()
  {
    super.onBindResources();
    if (this.mMediaRef != null)
      this.mImageResource = sImageResourceManager.getMedia(this.mMediaRef, 3, this);
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mDbEmbedSquare = null;
    this.mMediaRef = null;
    this.mSrcRect.setEmpty();
    this.mDestRect.setEmpty();
    this.mSquareInvitationLayout = null;
    this.mSquareNameLayout = null;
  }

  protected final void onUnbindResources()
  {
    super.onUnbindResources();
    if (this.mImageResource != null)
    {
      this.mImageResource.unregister(this);
      this.mImageResource = null;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SquareCardView
 * JD-Core Version:    0.6.2
 */