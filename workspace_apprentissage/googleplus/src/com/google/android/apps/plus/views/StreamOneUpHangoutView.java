package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.text.Layout.Alignment;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.integer;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.DbEmbedHangout;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.hangout.Utils;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.Hangout;
import com.google.android.apps.plus.service.Hangout.SupportStatus;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageCache.OnAvatarChangeListener;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.TextPaintUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class StreamOneUpHangoutView extends View
  implements ImageCache.OnAvatarChangeListener, ClickableButton.ClickableButtonListener
{
  protected static TextPaint sActiveTextPaint;
  protected static int sAvatarSize;
  protected static int sAvatarSpacing;
  protected static int sButtonMarginBottom;
  protected static Bitmap sDefaultAvatarBitmap;
  protected static TextPaint sDefaultTextPaint;
  protected static Bitmap sHangoutActiveBitmap;
  private static boolean sHangoutCardViewInitialized;
  protected static NinePatchDrawable sHangoutJoinDrawable;
  protected static NinePatchDrawable sHangoutJoinPressedDrawable;
  protected static Bitmap sHangoutOverBitmap;
  private static ImageCache sImageCache;
  private static TextPaint sJoinButtonPaint;
  protected static int sMaxHangoutAvatarsToDisplay;
  protected static int sMaxWidth;
  protected static int sNameMargin;
  private static Paint sResizePaint;
  private static TextPaint sUnsupportedTextPaint;
  private String mAuthorId;
  private String mAuthorName;
  private int mAvatarsToDisplay;
  private final Set<ClickableItem> mClickableItems = new HashSet();
  private ClickableItem mCurrentClickableItem;
  private DbEmbedHangout mDbEmbedHangout;
  private PositionedStaticLayout mExtraParticpantsLayout;
  private final ArrayList<ClickableUserImage> mHangoutAvatars = new ArrayList();
  private Bitmap mHangoutIcon;
  private Rect mHangoutIconRect;
  private PositionedStaticLayout mHangoutLayout;
  private ClickableButton mJoinButton;
  private String mParticipantNames;

  public StreamOneUpHangoutView(Context paramContext)
  {
    super(paramContext);
    Context localContext = getContext();
    if (!sHangoutCardViewInitialized)
    {
      sHangoutCardViewInitialized = true;
      sImageCache = ImageCache.getInstance(localContext);
      Resources localResources = getResources();
      sHangoutJoinDrawable = (NinePatchDrawable)localResources.getDrawable(R.drawable.blue_button_default);
      sHangoutJoinPressedDrawable = (NinePatchDrawable)localResources.getDrawable(R.drawable.blue_button_pressed);
      sDefaultAvatarBitmap = EsAvatarData.getMediumDefaultAvatar(localContext, true);
      sHangoutActiveBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_nav_hangouts);
      sHangoutOverBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_hangouts_over);
      sAvatarSize = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_avatar_size);
      sAvatarSpacing = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_avatar_spacing);
      sMaxWidth = (int)localResources.getDimension(R.dimen.stream_one_up_list_max_width);
      sButtonMarginBottom = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_button_margin_bottom);
      sNameMargin = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_name_margin);
      sMaxHangoutAvatarsToDisplay = localResources.getInteger(R.integer.card_max_hangout_avatars);
      sResizePaint = new Paint(2);
      TextPaint localTextPaint1 = new TextPaint();
      sDefaultTextPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sDefaultTextPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_default));
      sDefaultTextPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_stage_default_text_size));
      TextPaintUtils.registerTextPaint(sDefaultTextPaint, R.dimen.stream_one_up_stage_default_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sActiveTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sActiveTextPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_hangout_active_name));
      sActiveTextPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_stage_default_text_size));
      TextPaintUtils.registerTextPaint(sActiveTextPaint, R.dimen.stream_one_up_stage_default_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      sJoinButtonPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sJoinButtonPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_default));
      sJoinButtonPaint.setTextSize(localResources.getDimension(R.dimen.card_hangout_join_button_text_size));
      sJoinButtonPaint.setTypeface(Typeface.DEFAULT);
      TextPaintUtils.registerTextPaint(sJoinButtonPaint, R.dimen.card_hangout_join_button_text_size);
      TextPaint localTextPaint4 = new TextPaint();
      sUnsupportedTextPaint = localTextPaint4;
      localTextPaint4.setAntiAlias(true);
      sUnsupportedTextPaint.setColor(localResources.getColor(R.color.card_hangout_unsupported));
      sUnsupportedTextPaint.setTextSize(localResources.getDimension(R.dimen.card_hangout_unsupported_text_size));
      sUnsupportedTextPaint.setTypeface(Typeface.DEFAULT);
      TextPaintUtils.registerTextPaint(sUnsupportedTextPaint, R.dimen.card_hangout_unsupported_text_size);
    }
  }

  public StreamOneUpHangoutView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Context localContext = getContext();
    if (!sHangoutCardViewInitialized)
    {
      sHangoutCardViewInitialized = true;
      sImageCache = ImageCache.getInstance(localContext);
      Resources localResources = getResources();
      sHangoutJoinDrawable = (NinePatchDrawable)localResources.getDrawable(R.drawable.blue_button_default);
      sHangoutJoinPressedDrawable = (NinePatchDrawable)localResources.getDrawable(R.drawable.blue_button_pressed);
      sDefaultAvatarBitmap = EsAvatarData.getMediumDefaultAvatar(localContext, true);
      sHangoutActiveBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_nav_hangouts);
      sHangoutOverBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_hangouts_over);
      sAvatarSize = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_avatar_size);
      sAvatarSpacing = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_avatar_spacing);
      sMaxWidth = (int)localResources.getDimension(R.dimen.stream_one_up_list_max_width);
      sButtonMarginBottom = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_button_margin_bottom);
      sNameMargin = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_name_margin);
      sMaxHangoutAvatarsToDisplay = localResources.getInteger(R.integer.card_max_hangout_avatars);
      sResizePaint = new Paint(2);
      TextPaint localTextPaint1 = new TextPaint();
      sDefaultTextPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sDefaultTextPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_default));
      sDefaultTextPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_stage_default_text_size));
      TextPaintUtils.registerTextPaint(sDefaultTextPaint, R.dimen.stream_one_up_stage_default_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sActiveTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sActiveTextPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_hangout_active_name));
      sActiveTextPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_stage_default_text_size));
      TextPaintUtils.registerTextPaint(sActiveTextPaint, R.dimen.stream_one_up_stage_default_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      sJoinButtonPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sJoinButtonPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_default));
      sJoinButtonPaint.setTextSize(localResources.getDimension(R.dimen.card_hangout_join_button_text_size));
      sJoinButtonPaint.setTypeface(Typeface.DEFAULT);
      TextPaintUtils.registerTextPaint(sJoinButtonPaint, R.dimen.card_hangout_join_button_text_size);
      TextPaint localTextPaint4 = new TextPaint();
      sUnsupportedTextPaint = localTextPaint4;
      localTextPaint4.setAntiAlias(true);
      sUnsupportedTextPaint.setColor(localResources.getColor(R.color.card_hangout_unsupported));
      sUnsupportedTextPaint.setTextSize(localResources.getDimension(R.dimen.card_hangout_unsupported_text_size));
      sUnsupportedTextPaint.setTypeface(Typeface.DEFAULT);
      TextPaintUtils.registerTextPaint(sUnsupportedTextPaint, R.dimen.card_hangout_unsupported_text_size);
    }
  }

  public StreamOneUpHangoutView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Context localContext = getContext();
    if (!sHangoutCardViewInitialized)
    {
      sHangoutCardViewInitialized = true;
      sImageCache = ImageCache.getInstance(localContext);
      Resources localResources = getResources();
      sHangoutJoinDrawable = (NinePatchDrawable)localResources.getDrawable(R.drawable.blue_button_default);
      sHangoutJoinPressedDrawable = (NinePatchDrawable)localResources.getDrawable(R.drawable.blue_button_pressed);
      sDefaultAvatarBitmap = EsAvatarData.getMediumDefaultAvatar(localContext, true);
      sHangoutActiveBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_nav_hangouts);
      sHangoutOverBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_hangouts_over);
      sAvatarSize = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_avatar_size);
      sAvatarSpacing = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_avatar_spacing);
      sMaxWidth = (int)localResources.getDimension(R.dimen.stream_one_up_list_max_width);
      sButtonMarginBottom = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_button_margin_bottom);
      sNameMargin = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_name_margin);
      sMaxHangoutAvatarsToDisplay = localResources.getInteger(R.integer.card_max_hangout_avatars);
      sResizePaint = new Paint(2);
      TextPaint localTextPaint1 = new TextPaint();
      sDefaultTextPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sDefaultTextPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_default));
      sDefaultTextPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_stage_default_text_size));
      TextPaintUtils.registerTextPaint(sDefaultTextPaint, R.dimen.stream_one_up_stage_default_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sActiveTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sActiveTextPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_hangout_active_name));
      sActiveTextPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_stage_default_text_size));
      TextPaintUtils.registerTextPaint(sActiveTextPaint, R.dimen.stream_one_up_stage_default_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      sJoinButtonPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sJoinButtonPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_default));
      sJoinButtonPaint.setTextSize(localResources.getDimension(R.dimen.card_hangout_join_button_text_size));
      sJoinButtonPaint.setTypeface(Typeface.DEFAULT);
      TextPaintUtils.registerTextPaint(sJoinButtonPaint, R.dimen.card_hangout_join_button_text_size);
      TextPaint localTextPaint4 = new TextPaint();
      sUnsupportedTextPaint = localTextPaint4;
      localTextPaint4.setAntiAlias(true);
      sUnsupportedTextPaint.setColor(localResources.getColor(R.color.card_hangout_unsupported));
      sUnsupportedTextPaint.setTextSize(localResources.getDimension(R.dimen.card_hangout_unsupported_text_size));
      sUnsupportedTextPaint.setTypeface(Typeface.DEFAULT);
      TextPaintUtils.registerTextPaint(sUnsupportedTextPaint, R.dimen.card_hangout_unsupported_text_size);
    }
  }

  public final void bind(DbEmbedHangout paramDbEmbedHangout, String paramString1, String paramString2, OneUpListener paramOneUpListener)
  {
    this.mHangoutAvatars.clear();
    this.mClickableItems.clear();
    this.mCurrentClickableItem = null;
    this.mDbEmbedHangout = null;
    this.mHangoutLayout = null;
    this.mExtraParticpantsLayout = null;
    this.mJoinButton = null;
    this.mHangoutIcon = null;
    this.mHangoutIconRect = null;
    this.mDbEmbedHangout = paramDbEmbedHangout;
    this.mAuthorName = paramString1;
    this.mAuthorId = paramString2;
    StringBuilder localStringBuilder = new StringBuilder();
    ArrayList localArrayList1 = this.mDbEmbedHangout.getAttendeeGaiaIds();
    ArrayList localArrayList2 = this.mDbEmbedHangout.getAttendeeNames();
    ArrayList localArrayList3 = this.mDbEmbedHangout.getAttendeeAvatarUrls();
    int i = Math.min(sMaxHangoutAvatarsToDisplay, this.mDbEmbedHangout.getNumAttendees());
    for (int j = 0; j < i; j++)
    {
      String str = (String)localArrayList2.get(j);
      ClickableUserImage localClickableUserImage = new ClickableUserImage(this, (String)localArrayList1.get(j), (String)localArrayList3.get(j), str, paramOneUpListener, 2);
      this.mClickableItems.add(localClickableUserImage);
      this.mHangoutAvatars.add(localClickableUserImage);
      localStringBuilder.append('\n').append(str);
    }
    this.mParticipantNames = localStringBuilder.toString();
    invalidate();
    requestLayout();
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 1;
    int k = (int)paramMotionEvent.getX();
    int m = (int)paramMotionEvent.getY();
    switch (paramMotionEvent.getAction())
    {
    default:
      i = 0;
    case 0:
    case 1:
    case 3:
    case 2:
    }
    while (true)
    {
      return i;
      Iterator localIterator2 = this.mClickableItems.iterator();
      while (true)
      {
        Iterator localIterator1;
        int j;
        if (localIterator2.hasNext())
        {
          ClickableItem localClickableItem = (ClickableItem)localIterator2.next();
          if (localClickableItem.handleEvent(k, m, 0))
          {
            this.mCurrentClickableItem = localClickableItem;
            invalidate();
            break;
          }
        }
      }
      i = 0;
      continue;
      this.mCurrentClickableItem = null;
      localIterator1 = this.mClickableItems.iterator();
      while (localIterator1.hasNext())
        ((ClickableItem)localIterator1.next()).handleEvent(k, m, i);
      invalidate();
      j = 0;
      continue;
      if (this.mCurrentClickableItem != null)
      {
        this.mCurrentClickableItem.handleEvent(k, m, 3);
        this.mCurrentClickableItem = null;
        invalidate();
      }
      else
      {
        j = 0;
        continue;
        j = 0;
      }
    }
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ImageCache.registerAvatarChangeListener(this);
  }

  public void onAvatarChanged(String paramString)
  {
    Iterator localIterator = this.mHangoutAvatars.iterator();
    while (localIterator.hasNext())
      ((ClickableUserImage)localIterator.next()).onAvatarChanged(paramString);
  }

  public final void onClickableButtonListenerClick(ClickableButton paramClickableButton)
  {
    Context localContext;
    if (paramClickableButton == this.mJoinButton)
    {
      localContext = getContext();
      if (this.mDbEmbedHangout.isJoinable())
        break label95;
      Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/watch?v=" + this.mDbEmbedHangout.getYoutubeLiveId()));
      localIntent.addFlags(524288);
      if (Utils.isAppInstalled("com.google.android.youtube", localContext))
        localIntent.setClassName("com.google.android.youtube", "com.google.android.youtube.WatchActivity");
      localContext.startActivity(localIntent);
    }
    while (true)
    {
      return;
      label95: Hangout.enterGreenRoom(EsService.getActiveAccount(localContext), localContext, this.mAuthorId, this.mAuthorName, this.mDbEmbedHangout);
    }
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    ImageCache.unregisterAvatarChangeListener(this);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mHangoutIcon != null)
      paramCanvas.drawBitmap(this.mHangoutIcon, null, this.mHangoutIconRect, null);
    if (this.mHangoutLayout == null)
    {
      int m = this.mHangoutLayout.getLeft();
      int n = this.mHangoutLayout.getTop();
      paramCanvas.translate(m, n);
      this.mHangoutLayout.draw(paramCanvas);
      paramCanvas.translate(-m, -n);
    }
    if (this.mJoinButton != null)
      this.mJoinButton.draw(paramCanvas);
    for (int i = -1 + Math.min(this.mAvatarsToDisplay, this.mHangoutAvatars.size()); i >= 0; i--)
    {
      ClickableUserImage localClickableUserImage = (ClickableUserImage)this.mHangoutAvatars.get(i);
      Bitmap localBitmap = localClickableUserImage.getBitmap();
      if (localBitmap == null)
        localBitmap = sDefaultAvatarBitmap;
      paramCanvas.drawBitmap(localBitmap, null, localClickableUserImage.getRect(), sResizePaint);
    }
    if (this.mExtraParticpantsLayout != null)
    {
      int j = this.mExtraParticpantsLayout.getLeft();
      int k = this.mExtraParticpantsLayout.getTop();
      paramCanvas.translate(j, k);
      this.mExtraParticpantsLayout.draw(paramCanvas);
      paramCanvas.translate(-j, -k);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j;
    int k;
    int n;
    int i1;
    Context localContext;
    int i2;
    Hangout.SupportStatus localSupportStatus2;
    Bitmap localBitmap2;
    TextPaint localTextPaint;
    String str1;
    Hangout.SupportStatus localSupportStatus1;
    Object localObject;
    label156: int i9;
    String str4;
    switch (View.MeasureSpec.getMode(paramInt1))
    {
    default:
      j = sMaxWidth;
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), paramInt2);
      k = getPaddingLeft();
      int m = getPaddingTop();
      n = getMeasuredWidth();
      i1 = n - k - getPaddingRight();
      localContext = getContext();
      i2 = this.mDbEmbedHangout.getNumAttendees();
      if (this.mDbEmbedHangout.isInProgress())
      {
        localSupportStatus2 = Hangout.getSupportedStatus(localContext, EsService.getActiveAccount(localContext));
        localBitmap2 = sHangoutActiveBitmap;
        if (localSupportStatus2 != Hangout.SupportStatus.SUPPORTED)
        {
          localTextPaint = sUnsupportedTextPaint;
          str1 = localSupportStatus2.getErrorMessage(localContext);
          localSupportStatus1 = localSupportStatus2;
          localObject = localBitmap2;
          int i4 = ((Bitmap)localObject).getWidth();
          int i5 = ((Bitmap)localObject).getHeight();
          int i6 = k + (i1 - i4) / 2;
          this.mHangoutIcon = ((Bitmap)localObject);
          this.mHangoutIconRect = new Rect(i6, m, i4 + i6, m + i5);
          int i7 = m + (i5 + sNameMargin);
          this.mHangoutLayout = new PositionedStaticLayout(str1, localTextPaint, (int)localTextPaint.measureText(str1), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
          int i8 = k + (i1 - this.mHangoutLayout.getWidth()) / 2;
          this.mHangoutLayout.setPosition(i8, i7);
          i9 = i7 + (this.mHangoutLayout.getHeight() + sNameMargin);
          if (localSupportStatus1 != Hangout.SupportStatus.SUPPORTED)
            break label1062;
          if (!this.mDbEmbedHangout.isJoinable())
            break label939;
          str4 = localContext.getString(R.string.stream_one_up_stage_hangout_join);
          label325: this.mClickableItems.remove(this.mJoinButton);
          this.mJoinButton = new ClickableButton(localContext, str4, sJoinButtonPaint, sHangoutJoinDrawable, sHangoutJoinPressedDrawable, this, 0, 0);
          Rect localRect = this.mJoinButton.getRect();
          localRect.offset(k + (i1 - localRect.width()) / 2, i9);
          this.mClickableItems.add(this.mJoinButton);
        }
      }
      break;
    case -2147483648:
    case 1073741824:
    }
    label670: label1062: for (int i10 = i9 + (this.mJoinButton.getRect().height() + sButtonMarginBottom); ; i10 = i9)
    {
      Resources localResources2 = localContext.getResources();
      int i11 = R.plurals.hangout_plus_others;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(i2);
      String str2 = localResources2.getQuantityString(i11, i2, arrayOfObject2);
      int i12 = (int)sDefaultTextPaint.measureText(str2);
      int i13 = Math.min(this.mHangoutAvatars.size(), (i1 - i12) / (sAvatarSize + sAvatarSpacing));
      int i14 = i2 - i13;
      int i15 = sAvatarSpacing + i13 * (sAvatarSize + sAvatarSpacing);
      int i16;
      if (i14 > 0)
      {
        int i23 = R.plurals.hangout_plus_others;
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(i14);
        String str3 = localResources2.getQuantityString(i23, i14, arrayOfObject3);
        int i24 = (int)sDefaultTextPaint.measureText(str3);
        if (i15 + i24 > i1)
        {
          int i25 = i14 - 1;
          int i26 = i13 - 1;
          i15 = sAvatarSpacing + i26 * (sAvatarSize + sAvatarSpacing);
          int i27 = R.plurals.hangout_plus_others;
          int i28 = i25 - 1;
          Object[] arrayOfObject4 = new Object[1];
          arrayOfObject4[0] = Integer.valueOf(i25 - 1);
          str3 = localResources2.getQuantityString(i27, i28, arrayOfObject4);
          i24 = (int)sDefaultTextPaint.measureText(str3);
          i16 = i26;
          this.mExtraParticpantsLayout = new PositionedStaticLayout(str3, sDefaultTextPaint, i24, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
          i1 -= this.mExtraParticpantsLayout.getWidth();
        }
      }
      while (true)
      {
        int i17 = i1 - i15;
        int i18 = k + sAvatarSpacing + i17 / 2;
        this.mAvatarsToDisplay = i16;
        int i19 = this.mHangoutAvatars.size();
        int i20 = 0;
        label744: if (i20 < i19)
        {
          ClickableUserImage localClickableUserImage = (ClickableUserImage)this.mHangoutAvatars.get(i20);
          if (i20 < i16)
            localClickableUserImage.setRect(i18, i10, i18 + sAvatarSize, i10 + sAvatarSize);
          for (int i22 = i18 + (sAvatarSize + sAvatarSpacing); ; i22 = i18)
          {
            i20++;
            i18 = i22;
            break label744;
            j = Math.min(i, sMaxWidth);
            break;
            j = i;
            break;
            localTextPaint = sActiveTextPaint;
            int i29 = R.string.stream_one_up_stage_hangout_active;
            Object[] arrayOfObject5 = new Object[1];
            arrayOfObject5[0] = this.mAuthorName;
            str1 = localContext.getString(i29, arrayOfObject5);
            localSupportStatus1 = localSupportStatus2;
            localObject = localBitmap2;
            break label156;
            Bitmap localBitmap1 = sHangoutOverBitmap;
            localTextPaint = sDefaultTextPaint;
            Resources localResources1 = localContext.getResources();
            int i3 = R.plurals.stream_one_up_stage_hangout_over;
            Object[] arrayOfObject1 = new Object[1];
            arrayOfObject1[0] = Integer.valueOf(i2);
            str1 = localResources1.getQuantityString(i3, i2, arrayOfObject1);
            localObject = localBitmap1;
            localSupportStatus1 = null;
            break label156;
            str4 = localContext.getString(R.string.hangout_broadcast_view);
            break label325;
            localClickableUserImage.setRect(0, 0, 0, 0);
          }
        }
        label939: if (this.mExtraParticpantsLayout != null)
          this.mExtraParticpantsLayout.setPosition(i18, i10 + (sAvatarSize - this.mExtraParticpantsLayout.getHeight()) / 2);
        int i21 = i10 + sAvatarSize;
        setContentDescription(str1 + this.mParticipantNames);
        setMeasuredDimension(n, i21 + getPaddingBottom());
        return;
        i16 = i13;
        break label670;
        i16 = i13;
      }
    }
  }

  public final void processClick(float paramFloat1, float paramFloat2)
  {
    if (this.mJoinButton != null)
    {
      Rect localRect = this.mJoinButton.getRect();
      int[] arrayOfInt = new int[2];
      getLocationOnScreen(arrayOfInt);
      if (localRect.contains((int)(paramFloat1 - arrayOfInt[0]), (int)(paramFloat2 - arrayOfInt[1])))
        onClickableButtonListenerClick(this.mJoinButton);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.StreamOneUpHangoutView
 * JD-Core Version:    0.6.2
 */