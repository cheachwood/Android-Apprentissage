package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.integer;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.DbEmbedHangout;
import com.google.android.apps.plus.hangout.Utils;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.Hangout;
import com.google.android.apps.plus.service.Hangout.SupportStatus;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.TextPaintUtils;
import java.util.ArrayList;

public class HangoutCardView extends StreamCardView
  implements ClickableButton.ClickableButtonListener
{
  private static boolean sHangoutCardViewInitialized;
  private static TextPaint sHangoutJoinButtonPaint;
  protected static NinePatchDrawable sHangoutJoinDrawable;
  protected static NinePatchDrawable sHangoutJoinPressedDrawable;
  private static TextPaint sHangoutUnsupportedTextPaint;
  protected static int sMaxHangoutAvatarsToDisplay;
  protected int mAvatarsToDisplay;
  protected DbEmbedHangout mDbEmbedHangout;
  protected final ArrayList<ClickableAvatar> mHangoutAvatars = new ArrayList();
  protected ClickableButton mJoinButton;
  protected StaticLayout mUnsupportedLayout;

  public HangoutCardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public HangoutCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (!sHangoutCardViewInitialized)
    {
      sHangoutCardViewInitialized = true;
      Resources localResources = getResources();
      TextPaint localTextPaint1 = new TextPaint();
      sHangoutJoinButtonPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sHangoutJoinButtonPaint.setColor(localResources.getColor(R.color.card_hangout_join));
      sHangoutJoinButtonPaint.setTextSize(localResources.getDimension(R.dimen.card_hangout_join_button_text_size));
      sHangoutJoinButtonPaint.setTypeface(Typeface.DEFAULT);
      TextPaintUtils.registerTextPaint(sHangoutJoinButtonPaint, R.dimen.card_hangout_join_button_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sHangoutUnsupportedTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sHangoutUnsupportedTextPaint.setColor(localResources.getColor(R.color.card_hangout_unsupported));
      sHangoutUnsupportedTextPaint.setTextSize(localResources.getDimension(R.dimen.card_hangout_unsupported_text_size));
      sHangoutUnsupportedTextPaint.setTypeface(Typeface.DEFAULT);
      TextPaintUtils.registerTextPaint(sHangoutUnsupportedTextPaint, R.dimen.card_hangout_unsupported_text_size);
      sHangoutJoinDrawable = (NinePatchDrawable)localResources.getDrawable(R.drawable.blue_button_default);
      sHangoutJoinPressedDrawable = (NinePatchDrawable)localResources.getDrawable(R.drawable.blue_button_pressed);
      sMaxHangoutAvatarsToDisplay = localResources.getInteger(R.integer.card_max_hangout_avatars);
    }
  }

  protected final int draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = sTopBorderPadding + (int)((paramInt4 + 2 * sYPadding) * getMediaHeightPercentage()) - sYPadding;
    drawMediaTopAreaStageWithTiledBackground(paramCanvas, paramInt3, i);
    if (this.mUnsupportedLayout != null)
    {
      int k = paramInt1 + (paramInt3 - this.mUnsupportedLayout.getWidth()) / 2;
      int m = paramInt2 + (i - this.mUnsupportedLayout.getHeight()) / 2;
      paramCanvas.translate(k, m);
      this.mUnsupportedLayout.draw(paramCanvas);
      paramCanvas.translate(-k, -m);
    }
    if (this.mJoinButton != null)
      this.mJoinButton.draw(paramCanvas);
    if (this.mUnsupportedLayout == null)
      for (int j = 0; j < this.mAvatarsToDisplay; j++)
      {
        ClickableAvatar localClickableAvatar = (ClickableAvatar)this.mHangoutAvatars.get(j);
        Rect localRect = localClickableAvatar.getRect();
        Bitmap localBitmap = localClickableAvatar.getBitmap();
        if (localBitmap == null)
          localBitmap = sAuthorBitmap;
        paramCanvas.drawBitmap(localBitmap, null, localRect, sResizePaint);
      }
    drawMediaTopAreaShadow(paramCanvas, paramInt3, paramInt4);
    drawTagBarIconAndBackground(paramCanvas, paramInt1, paramInt2);
    drawPlusOneBar(paramCanvas);
    drawMediaBottomArea$1be95c43(paramCanvas, paramInt1, paramInt3, paramInt4);
    drawCornerIcon(paramCanvas);
    return paramInt4;
  }

  public final void init(Cursor paramCursor, int paramInt1, int paramInt2, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamCardView.ViewedListener paramViewedListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener)
  {
    super.init(paramCursor, paramInt1, paramInt2, paramOnClickListener, paramItemClickListener, paramViewedListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener);
    byte[] arrayOfByte = paramCursor.getBlob(25);
    Resources localResources;
    if (arrayOfByte != null)
    {
      this.mDbEmbedHangout = DbEmbedHangout.deserialize(arrayOfByte);
      int i = this.mDbEmbedHangout.getNumAttendees();
      int j = Math.min(sMaxHangoutAvatarsToDisplay, i);
      ArrayList localArrayList1 = this.mDbEmbedHangout.getAttendeeGaiaIds();
      ArrayList localArrayList2 = this.mDbEmbedHangout.getAttendeeNames();
      ArrayList localArrayList3 = this.mDbEmbedHangout.getAttendeeAvatarUrls();
      for (int k = 0; k < j; k++)
      {
        ClickableAvatar localClickableAvatar = new ClickableAvatar(this, (String)localArrayList1.get(k), (String)localArrayList3.get(k), (String)localArrayList2.get(k), null, 2);
        this.mHangoutAvatars.add(localClickableAvatar);
      }
      localResources = getResources();
      if (this.mDbEmbedHangout.isInProgress())
      {
        int n = R.string.card_hangout_state_active;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.mAuthorName;
        this.mContent = localResources.getString(n, arrayOfObject2);
        if (i == 0)
          i = 1;
        int i1 = R.plurals.card_hangout_with_people;
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(i);
        this.mTag = localResources.getQuantityString(i1, i, arrayOfObject3);
        this.mTagIcon = sTagHangoutBitmaps[0];
      }
    }
    while (true)
    {
      return;
      int m = R.string.card_hangout_state_inactive;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mAuthorName;
      this.mContent = localResources.getString(m, arrayOfObject1);
      continue;
      if (EsLog.isLoggable("HangoutCardView", 5))
        Log.w("HangoutCardView", "No hangout data!");
    }
  }

  protected final int layoutElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = sTopBorderPadding + (int)((paramInt4 + 2 * sYPadding) * getMediaHeightPercentage()) - sYPadding;
    this.mBackgroundRect.set(0, i, getMeasuredWidth(), getMeasuredHeight());
    createTagBar(paramInt1, paramInt2, paramInt3);
    boolean bool = this.mDbEmbedHangout.isInProgress();
    int j = 0;
    Context localContext;
    int m;
    int n;
    if (bool)
    {
      localContext = getContext();
      Hangout.SupportStatus localSupportStatus = Hangout.getSupportedStatus(getContext(), EsService.getActiveAccount(localContext));
      if (localSupportStatus != Hangout.SupportStatus.SUPPORTED)
        this.mUnsupportedLayout = new StaticLayout(localSupportStatus.getErrorMessage(localContext), sHangoutUnsupportedTextPaint, paramInt3, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
    }
    else
    {
      if (this.mUnsupportedLayout != null)
        break label468;
      int k = this.mDbEmbedHangout.getNumAttendees();
      m = paramInt3 + sXDoublePadding;
      this.mAvatarsToDisplay = Math.min(Math.min(sMaxHangoutAvatarsToDisplay, k), (m - j) / sAvatarSize);
      n = 1 + this.mAvatarsToDisplay;
      if (this.mJoinButton == null)
        break label430;
    }
    int i4;
    label430: for (int i1 = 1; ; i1 = 0)
    {
      int i2 = n + i1;
      int i3 = (m - this.mAvatarsToDisplay * sAvatarSize - j) / i2;
      i4 = i3 + sLeftBorderPadding;
      int i5 = i3 + (sAvatarSize + sLeftBorderPadding);
      int i6 = (i - sAvatarSize) / 2;
      int i7 = i6 + sAvatarSize;
      for (int i8 = 0; i8 < this.mAvatarsToDisplay; i8++)
      {
        ((ClickableAvatar)this.mHangoutAvatars.get(i8)).setRect(i4, i6, i5, i7);
        i4 += i3 + sAvatarSize;
        i5 += i3 + sAvatarSize;
      }
      if (this.mDbEmbedHangout.isJoinable());
      for (String str = localContext.getString(R.string.hangout_enter_greenroom); ; str = localContext.getString(R.string.hangout_broadcast_view))
      {
        removeClickableItem(this.mJoinButton);
        this.mJoinButton = new ClickableButton(localContext, str, sHangoutJoinButtonPaint, sHangoutJoinDrawable, sHangoutJoinPressedDrawable, this, paramInt1, i / 2);
        Rect localRect2 = this.mJoinButton.getRect();
        j = 0 + localRect2.width();
        localRect2.offset(0, -localRect2.height() / 2);
        addClickableItem(this.mJoinButton);
        break;
      }
    }
    if (this.mJoinButton != null)
    {
      Rect localRect1 = this.mJoinButton.getRect();
      int i9 = localRect1.top;
      localRect1.offsetTo(i4, i9);
    }
    label468: createPlusOneBar(paramInt1, i, paramInt3);
    createMediaBottomArea(paramInt1, paramInt2, paramInt3, paramInt4);
    return paramInt4;
  }

  protected final void onBindResources()
  {
    super.onBindResources();
    int i = this.mHangoutAvatars.size();
    for (int j = 0; j < i; j++)
      ((ClickableAvatar)this.mHangoutAvatars.get(j)).bindResources();
  }

  public final void onClickableButtonListenerClick(ClickableButton paramClickableButton)
  {
    Context localContext;
    if (paramClickableButton == this.mJoinButton)
    {
      localContext = getContext();
      if (!this.mDbEmbedHangout.isJoinable())
      {
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/watch?v=" + this.mDbEmbedHangout.getYoutubeLiveId()));
        localIntent.addFlags(524288);
        if (Utils.isAppInstalled("com.google.android.youtube", localContext))
          localIntent.setClassName("com.google.android.youtube", "com.google.android.youtube.WatchActivity");
        localContext.startActivity(localIntent);
      }
    }
    while (true)
    {
      return;
      Hangout.enterGreenRoom(EsService.getActiveAccount(localContext), localContext, this.mAuthorGaiaId, this.mAuthorName, this.mDbEmbedHangout);
      continue;
      super.onClickableButtonListenerClick(paramClickableButton);
    }
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mHangoutAvatars.clear();
    this.mDbEmbedHangout = null;
    this.mUnsupportedLayout = null;
    this.mJoinButton = null;
    this.mAvatarsToDisplay = 0;
  }

  protected final void onUnbindResources()
  {
    super.onUnbindResources();
    int i = this.mHangoutAvatars.size();
    for (int j = 0; j < i; j++)
      ((ClickableAvatar)this.mHangoutAvatars.get(j)).unbindResources();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.HangoutCardView
 * JD-Core Version:    0.6.2
 */