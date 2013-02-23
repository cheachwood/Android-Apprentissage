package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.NinePatchDrawable;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.content.EventThemeImageRequest;
import com.google.android.apps.plus.content.MediaImageRequest;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageCache.OnAvatarChangeListener;
import com.google.android.apps.plus.service.ImageCache.OnMediaImageChangeListener;
import com.google.android.apps.plus.util.EventDateUtils;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.TextPaintUtils;
import com.google.android.apps.plus.util.TimeZoneHelper;
import com.google.api.services.plusi.model.EmbedsPostalAddress;
import com.google.api.services.plusi.model.EventTime;
import com.google.api.services.plusi.model.HangoutInfo;
import com.google.api.services.plusi.model.Place;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.ThemeImage;
import java.util.Calendar;

public final class EventCardDrawer
  implements ImageCache.OnAvatarChangeListener, ImageCache.OnMediaImageChangeListener
{
  private static Bitmap sAuthorBitmap;
  private static int sAvatarSize;
  private static Bitmap sBlueRsvpBannerBitmap;
  private static Paint sDividerPaint;
  private static Bitmap sEventAttendingBitmap;
  private static int sEventCardPadding;
  private static Paint sEventInfoBackgroundPaint;
  private static TextPaint sEventInfoTextPaint;
  private static Bitmap sEventMaybeBitmap;
  private static TextPaint sEventNameTextPaint;
  private static Bitmap sEventNotAttendingBitmap;
  private static int sEventTextLineSpacing;
  private static Bitmap sGreenRsvpBannerBitmap;
  private static Bitmap sGreyRsvpBannerBitmap;
  private static Bitmap sHangoutBitmap;
  private static String sHangoutTitle;
  private static ImageCache sImageCache;
  private static boolean sInitialized;
  private static Bitmap sLocationBitmap;
  private static NinePatchDrawable sOnAirNinePatch;
  private static TextPaint sOnAirPaint;
  private static String sOnAirTitle;
  private static Paint sResizePaint;
  private static float sRibbonHeightPercentOverlap;
  private static TextPaint sStatusGoingPaint;
  private static TextPaint sStatusInvitedPaint;
  private static TextPaint sStatusMaybePaint;
  private static TextPaint sStatusNotGoingPaint;
  EsAccount mAccount;
  private CharSequence mAttribution;
  private Point mAttributionLayoutCorner;
  ClickableUserImage mAvatar;
  private boolean mBound;
  CardView mContainingCardView;
  private StaticLayout mCreatorLayout;
  private StaticLayout mDateLayout;
  private Point mDateLayoutCorner;
  float[] mDividerLines;
  PlusEvent mEventInfo;
  private boolean mIgnoreHeight;
  private Bitmap mLocationIcon;
  private Rect mLocationIconRect;
  private StaticLayout mLocationLayout;
  private Point mLocationLayoutCorner;
  private StaticLayout mNameLayout;
  private Point mNameLayoutCorner;
  private Bitmap mRsvpBanner;
  private Rect mRsvpBannerRect;
  private Bitmap mRsvpIcon;
  private Rect mRsvpIconRect;
  private StaticLayout mRsvpLayout;
  private Point mRsvpLayoutCorner;
  RemoteImage mThemeImage;
  private Rect mThemeImageRect;
  ThemeImage mThemeInfo;
  private Point mTimeZoneCorner;
  private StaticLayout mTimeZoneLayout;
  private ClickableButton mTypeLabel;

  public EventCardDrawer(View paramView)
  {
    TimeZoneHelper.initialize(paramView.getContext());
    if (!sInitialized)
    {
      Resources localResources = paramView.getResources();
      sImageCache = ImageCache.getInstance(paramView.getContext());
      sResizePaint = new Paint(2);
      sAvatarSize = (int)localResources.getDimension(R.dimen.card_avatar_size);
      sRibbonHeightPercentOverlap = localResources.getDimension(R.dimen.event_card_ribbon_percent_height_overlap);
      sEventCardPadding = (int)localResources.getDimension(R.dimen.event_card_padding);
      sEventTextLineSpacing = (int)localResources.getDimension(R.dimen.event_card_text_line_spacing);
      sAuthorBitmap = EsAvatarData.getMediumDefaultAvatar(paramView.getContext(), true);
      Paint localPaint1 = new Paint();
      sEventInfoBackgroundPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.event_info_background_color));
      sEventInfoBackgroundPaint.setStyle(Paint.Style.FILL);
      TextPaint localTextPaint1 = new TextPaint();
      sEventNameTextPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sEventNameTextPaint.setColor(localResources.getColor(R.color.event_name_text_color));
      sEventNameTextPaint.setTextSize(localResources.getDimension(R.dimen.event_card_name_text_size));
      sEventNameTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      TextPaintUtils.registerTextPaint(sEventNameTextPaint, R.dimen.event_card_name_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sEventInfoTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sEventInfoTextPaint.setColor(localResources.getColor(R.color.card_event_info_text));
      sEventInfoTextPaint.setTextSize(localResources.getDimension(R.dimen.event_card_info_text_size));
      TextPaintUtils.registerTextPaint(sEventInfoTextPaint, R.dimen.event_card_info_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      localTextPaint3.setAntiAlias(true);
      localTextPaint3.setTextSize(localResources.getDimension(R.dimen.event_card_status_text_size));
      localTextPaint3.setTypeface(Typeface.DEFAULT_BOLD);
      TextPaint localTextPaint4 = new TextPaint(localTextPaint3);
      sStatusInvitedPaint = localTextPaint4;
      localTextPaint4.setColor(localResources.getColor(R.color.card_event_invited));
      TextPaintUtils.registerTextPaint(sStatusInvitedPaint, R.dimen.event_card_status_text_size);
      TextPaint localTextPaint5 = new TextPaint(localTextPaint3);
      sStatusGoingPaint = localTextPaint5;
      localTextPaint5.setColor(localResources.getColor(R.color.card_event_going));
      TextPaintUtils.registerTextPaint(sStatusGoingPaint, R.dimen.event_card_status_text_size);
      TextPaint localTextPaint6 = new TextPaint(localTextPaint3);
      sStatusNotGoingPaint = localTextPaint6;
      localTextPaint6.setColor(localResources.getColor(R.color.card_event_not_going));
      TextPaintUtils.registerTextPaint(sStatusNotGoingPaint, R.dimen.event_card_status_text_size);
      TextPaint localTextPaint7 = new TextPaint(localTextPaint3);
      sStatusMaybePaint = localTextPaint7;
      localTextPaint7.setColor(localResources.getColor(R.color.card_event_maybe));
      TextPaintUtils.registerTextPaint(sStatusMaybePaint, R.dimen.event_card_status_text_size);
      TextPaint localTextPaint8 = new TextPaint(sEventInfoTextPaint);
      sOnAirPaint = localTextPaint8;
      localTextPaint8.setColor(localResources.getColor(R.color.event_detail_on_air));
      sOnAirPaint.setTextSize(localResources.getDimension(R.dimen.event_card_details_on_air_size));
      TextPaintUtils.registerTextPaint(sOnAirPaint, R.dimen.event_card_details_on_air_size);
      Paint localPaint2 = new Paint();
      sDividerPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.card_event_divider));
      sDividerPaint.setStrokeWidth(localResources.getDimension(R.dimen.event_card_divider_stroke_width));
      sEventAttendingBitmap = ImageUtils.decodeResource(localResources, R.drawable.icn_events_check);
      sEventNotAttendingBitmap = ImageUtils.decodeResource(localResources, R.drawable.icn_events_not_going);
      sEventMaybeBitmap = ImageUtils.decodeResource(localResources, R.drawable.icn_events_maybe);
      sBlueRsvpBannerBitmap = ImageUtils.decodeResource(localResources, R.drawable.icn_events_ribbon_blue);
      sGreenRsvpBannerBitmap = ImageUtils.decodeResource(localResources, R.drawable.icn_events_ribbon_green);
      sGreyRsvpBannerBitmap = ImageUtils.decodeResource(localResources, R.drawable.icn_events_ribbon_grey);
      sLocationBitmap = ImageUtils.decodeResource(localResources, R.drawable.icn_location_card);
      sHangoutBitmap = ImageUtils.decodeResource(localResources, R.drawable.icn_events_hangout_taco);
      sHangoutTitle = localResources.getString(R.string.event_hangout_text);
      sOnAirTitle = localResources.getString(R.string.event_detail_on_air);
      sOnAirNinePatch = (NinePatchDrawable)localResources.getDrawable(R.drawable.btn_events_on_air);
      sInitialized = true;
    }
    this.mThemeImageRect = new Rect();
    this.mRsvpIconRect = new Rect();
    this.mRsvpLayoutCorner = new Point();
    this.mRsvpBannerRect = new Rect();
    this.mLocationIconRect = new Rect();
    this.mNameLayoutCorner = new Point();
    this.mDateLayoutCorner = new Point();
    this.mTimeZoneCorner = new Point();
    this.mLocationLayoutCorner = new Point();
    this.mAttributionLayoutCorner = new Point();
    this.mDividerLines = new float[4];
  }

  private static void drawTextLayout(StaticLayout paramStaticLayout, Point paramPoint, Canvas paramCanvas)
  {
    paramCanvas.translate(paramPoint.x, paramPoint.y);
    paramStaticLayout.draw(paramCanvas);
    paramCanvas.translate(-paramPoint.x, -paramPoint.y);
  }

  private static StaticLayout layoutTextLabel(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence, Point paramPoint, TextPaint paramTextPaint, boolean paramBoolean)
  {
    return TextPaintUtils.layoutBitmapTextLabel(paramInt1, paramInt2, paramInt3, 0, null, null, 0, paramCharSequence, paramPoint, paramTextPaint, paramBoolean);
  }

  public final void attach()
  {
    ImageCache.registerMediaImageChangeListener(this);
    ImageCache.registerAvatarChangeListener(this);
  }

  public final void bind(EsAccount paramEsAccount, CardView paramCardView, PlusEvent paramPlusEvent, ClickableUserImage.UserImageClickListener paramUserImageClickListener)
  {
    bind(paramEsAccount, paramCardView, paramPlusEvent, null, null, paramUserImageClickListener);
  }

  public final void bind(EsAccount paramEsAccount, CardView paramCardView, PlusEvent paramPlusEvent, String paramString, CharSequence paramCharSequence, ClickableUserImage.UserImageClickListener paramUserImageClickListener)
  {
    clear();
    this.mEventInfo = paramPlusEvent;
    boolean bool;
    CardView localCardView;
    if (this.mEventInfo != null)
    {
      bool = true;
      this.mBound = bool;
      if (this.mBound)
      {
        this.mAccount = paramEsAccount;
        this.mContainingCardView = paramCardView;
        this.mThemeInfo = EsEventData.getThemeImage(this.mEventInfo.theme);
        this.mContainingCardView.removeClickableItem(this.mAvatar);
        localCardView = this.mContainingCardView;
        if (paramString == null)
          break label126;
      }
    }
    label126: for (String str = paramString; ; str = this.mEventInfo.creatorObfuscatedId)
    {
      this.mAvatar = new ClickableUserImage(localCardView, str, null, null, paramUserImageClickListener, 2);
      this.mAttribution = paramCharSequence;
      this.mContainingCardView.addClickableItem(this.mAvatar);
      return;
      bool = false;
      break;
    }
  }

  public final void clear()
  {
    if (!this.mBound);
    while (true)
    {
      return;
      this.mContainingCardView.removeClickableItem(this.mAvatar);
      this.mAvatar = null;
      this.mThemeInfo = null;
      this.mEventInfo = null;
      this.mContainingCardView = null;
      this.mAccount = null;
      this.mRsvpIcon = null;
      this.mRsvpBanner = null;
      this.mThemeImage = null;
      this.mThemeImageRect.setEmpty();
      this.mRsvpIconRect.setEmpty();
      this.mRsvpLayoutCorner.set(0, 0);
      this.mRsvpBannerRect.setEmpty();
      this.mLocationIconRect.setEmpty();
      this.mNameLayout = null;
      this.mDateLayout = null;
      this.mTimeZoneLayout = null;
      this.mLocationLayout = null;
      this.mRsvpLayout = null;
      this.mCreatorLayout = null;
      this.mTypeLabel = null;
      this.mLocationIcon = null;
      this.mNameLayoutCorner.set(0, 0);
      this.mDateLayoutCorner.set(0, 0);
      this.mTimeZoneCorner.set(0, 0);
      this.mLocationLayoutCorner.set(0, 0);
      this.mAttributionLayoutCorner.set(0, 0);
      this.mAttribution = null;
      this.mBound = false;
    }
  }

  public final void detach()
  {
    ImageCache.unregisterAvatarChangeListener(this);
    ImageCache.unregisterMediaImageChangeListener(this);
  }

  public final int draw$680d9d43(int paramInt1, int paramInt2, Canvas paramCanvas)
  {
    int i = paramInt1;
    int j = paramInt1 + paramInt2;
    if (!this.mBound);
    while (true)
    {
      return paramInt1;
      if (this.mThemeImage != null)
      {
        this.mThemeImage.refreshIfInvalidated();
        Bitmap localBitmap2 = this.mThemeImage.getBitmap();
        if ((localBitmap2 != null) && ((this.mThemeImageRect.bottom <= j) || (this.mIgnoreHeight)))
          paramCanvas.drawBitmap(localBitmap2, null, this.mThemeImageRect, sResizePaint);
      }
      if ((this.mAvatar != null) && ((this.mAvatar.getRect().bottom <= j) || (this.mIgnoreHeight)))
      {
        Bitmap localBitmap1 = this.mAvatar.getBitmap();
        if (localBitmap1 == null)
          localBitmap1 = sAuthorBitmap;
        paramCanvas.drawBitmap(localBitmap1, null, this.mAvatar.getRect(), sResizePaint);
        if (this.mAvatar.isClicked())
          this.mAvatar.drawSelectionRect(paramCanvas);
      }
      if ((this.mRsvpLayout != null) && ((Math.max(this.mRsvpBannerRect.bottom, Math.max(this.mRsvpIconRect.bottom, this.mRsvpLayoutCorner.y + this.mRsvpLayout.getHeight())) <= j) || (this.mIgnoreHeight)))
      {
        paramCanvas.drawBitmap(this.mRsvpBanner, null, this.mRsvpBannerRect, null);
        drawTextLayout(this.mRsvpLayout, this.mRsvpLayoutCorner, paramCanvas);
        if (this.mRsvpIcon != null)
          paramCanvas.drawBitmap(this.mRsvpIcon, null, this.mRsvpIconRect, null);
        paramCanvas.drawLines(this.mDividerLines, sDividerPaint);
      }
      if ((this.mNameLayoutCorner.y + this.mNameLayout.getHeight() <= j) || (this.mIgnoreHeight))
        drawTextLayout(this.mNameLayout, this.mNameLayoutCorner, paramCanvas);
      int k = this.mDateLayoutCorner.y + this.mDateLayout.getHeight();
      if ((k <= j) || (this.mIgnoreHeight))
      {
        drawTextLayout(this.mDateLayout, this.mDateLayoutCorner, paramCanvas);
        i = k;
      }
      if (this.mTimeZoneLayout != null)
      {
        int i2 = this.mTimeZoneCorner.y + this.mTimeZoneLayout.getHeight();
        if ((i2 <= j) || (this.mIgnoreHeight))
        {
          drawTextLayout(this.mTimeZoneLayout, this.mTimeZoneCorner, paramCanvas);
          i = i2;
        }
      }
      if (this.mTypeLabel != null)
      {
        int i1 = this.mTypeLabel.getRect().bottom;
        if ((i1 <= j) || (this.mIgnoreHeight))
        {
          this.mTypeLabel.draw(paramCanvas);
          i = i1;
        }
      }
      if (this.mLocationLayout != null)
      {
        int n = Math.max(this.mLocationIconRect.bottom, this.mLocationLayout.getHeight() + this.mLocationLayoutCorner.y);
        if ((n <= j) || (this.mIgnoreHeight))
        {
          drawTextLayout(this.mLocationLayout, this.mLocationLayoutCorner, paramCanvas);
          paramCanvas.drawBitmap(this.mLocationIcon, null, this.mLocationIconRect, null);
          i = n;
        }
      }
      if (this.mAttribution != null)
      {
        int m = this.mAttributionLayoutCorner.y + this.mCreatorLayout.getHeight();
        if ((m <= j) || (this.mIgnoreHeight))
        {
          drawTextLayout(this.mCreatorLayout, this.mAttributionLayoutCorner, paramCanvas);
          i = m;
        }
      }
      paramInt1 = i;
    }
  }

  public final int layout(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4)
  {
    int i;
    if ((this.mContainingCardView == null) || (!this.mBound))
    {
      i = 0;
      return i;
    }
    this.mIgnoreHeight = paramBoolean;
    int j = sEventCardPadding;
    int k = j * 2;
    int m = sEventTextLineSpacing;
    int n = Math.round(paramInt3 / 3.36F);
    if (((this.mThemeImage == null) || (this.mThemeImageRect.width() != paramInt3) || (this.mThemeImageRect.height() != n) || (this.mThemeImageRect.top != paramInt2) || (this.mThemeImageRect.left != paramInt1)) && (this.mThemeInfo != null))
    {
      this.mThemeImageRect.set(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + n);
      EventThemeImageRequest localEventThemeImageRequest = new EventThemeImageRequest(ImageUtils.getCenterCroppedAndResizedUrl(paramInt3, n, this.mThemeInfo.url));
      this.mThemeImage = new RemoteImage(this.mContainingCardView, localEventThemeImageRequest);
      this.mThemeImage.load();
    }
    int i1 = sAvatarSize;
    int i2 = paramInt1 + j;
    int i3 = paramInt2 + n - k;
    this.mAvatar.setRect(i2, i3, i2 + i1, i3 + i1);
    int i4 = j + (i2 + i1);
    int i5 = paramInt2 + n;
    int i6 = paramInt1 + paramInt3 - i4 - k;
    Context localContext = this.mContainingCardView.getContext();
    int i14;
    int i18;
    Place localPlace;
    HangoutInfo localHangoutInfo;
    String str3;
    label631: String str2;
    if (!EsEventData.canRsvp(this.mEventInfo))
    {
      i14 = 0;
      int i15 = j + (i14 + i5);
      this.mNameLayout = layoutTextLabel(i4, i15, i6, this.mEventInfo.name, this.mNameLayoutCorner, sEventNameTextPaint, true);
      int i16 = i15 + this.mNameLayout.getHeight();
      if (this.mAttribution != null)
      {
        int i22 = i16 + m;
        this.mCreatorLayout = layoutTextLabel(i4, i22, i6, this.mAttribution, this.mAttributionLayoutCorner, sEventInfoTextPaint, false);
        i16 = i22 + this.mCreatorLayout.getHeight();
      }
      int i17 = i16 + m;
      this.mDateLayout = layoutTextLabel(i4, i17, i6, EventDateUtils.getSingleDisplayLine(this.mContainingCardView.getContext(), this.mEventInfo.startTime, null, false, null), this.mDateLayoutCorner, sEventInfoTextPaint, true);
      i18 = i17 + this.mDateLayout.getHeight();
      boolean bool = EsEventData.isEventHangout(this.mEventInfo);
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTimeInMillis(this.mEventInfo.startTime.timeMs.longValue());
      String str1 = TimeZoneHelper.getDisplayString(this.mEventInfo.startTime.timezone, localCalendar, bool);
      if (str1 != null)
      {
        int i21 = i18 + m;
        this.mTimeZoneLayout = layoutTextLabel(i4, i21, i6, str1, this.mTimeZoneCorner, sEventInfoTextPaint, true);
        i18 = i21 + this.mTimeZoneLayout.getHeight();
      }
      if (bool)
      {
        int i20 = i18 + m;
        this.mTypeLabel = new ClickableButton(this.mContainingCardView.getContext(), null, sOnAirTitle, sOnAirPaint, sOnAirNinePatch, sOnAirNinePatch, null, i4, i20);
        i18 = i20 + this.mTypeLabel.getRect().height();
      }
      localPlace = this.mEventInfo.location;
      localHangoutInfo = this.mEventInfo.hangoutInfo;
      if (localPlace == null)
        break label1129;
      if (localPlace.address == null)
        break label1119;
      str3 = localPlace.address.name;
      this.mLocationIcon = sLocationBitmap;
      str2 = str3;
    }
    while (true)
    {
      if (str2 != null)
      {
        int i19 = i18 + m;
        this.mLocationLayout = TextPaintUtils.layoutBitmapTextLabel(i4, i19, i6, 0, this.mLocationIcon, this.mLocationIconRect, j, str2, this.mLocationLayoutCorner, sEventInfoTextPaint, true);
        i18 = i19 + this.mLocationLayout.getHeight();
      }
      i = i5 + (i18 - i5) - paramInt2;
      break;
      int i7 = EsEventData.getRsvpStatus(this.mEventInfo);
      Object localObject = null;
      TextPaint localTextPaint = null;
      label764: int i8;
      int i10;
      int i11;
      switch (i7)
      {
      default:
        i8 = Math.round(sRibbonHeightPercentOverlap * this.mRsvpBanner.getHeight());
        int i9 = i5 - i8;
        this.mRsvpBannerRect.set(i4 + i6 - this.mRsvpBanner.getWidth(), i9, i4 + i6, i9 + this.mRsvpBanner.getHeight());
        i10 = i6 - this.mRsvpBannerRect.width() - j;
        this.mRsvpLayout = TextPaintUtils.layoutBitmapTextLabel(i4, i5 + j, i10, 0, this.mRsvpIcon, this.mRsvpIconRect, j, (CharSequence)localObject, this.mRsvpLayoutCorner, localTextPaint, true);
        i11 = this.mRsvpLayoutCorner.y + this.mRsvpLayout.getHeight() - i5;
        if (this.mRsvpIcon == null)
          break;
      case 1:
      case 3:
      case 2:
      case 0:
      }
      for (int i12 = this.mRsvpIconRect.bottom - i5; ; i12 = 0)
      {
        int i13 = j + (i5 + Math.max(i11, i12));
        this.mDividerLines[0] = i4;
        this.mDividerLines[1] = i13;
        this.mDividerLines[2] = (i10 + i4);
        this.mDividerLines[3] = i13;
        i14 = Math.max(i13 - i5, this.mRsvpBannerRect.height() - i8);
        break;
        localObject = localContext.getString(R.string.card_event_going_prompt);
        localTextPaint = sStatusGoingPaint;
        this.mRsvpIcon = sEventAttendingBitmap;
        this.mRsvpBanner = sGreenRsvpBannerBitmap;
        break label764;
        localObject = localContext.getString(R.string.card_event_declined_prompt);
        localTextPaint = sStatusNotGoingPaint;
        this.mRsvpIcon = sEventNotAttendingBitmap;
        this.mRsvpBanner = sGreyRsvpBannerBitmap;
        break label764;
        localObject = localContext.getString(R.string.card_event_maybe_prompt);
        localTextPaint = sStatusMaybePaint;
        this.mRsvpIcon = sEventMaybeBitmap;
        this.mRsvpBanner = sBlueRsvpBannerBitmap;
        break label764;
        localObject = localContext.getString(R.string.card_event_invited_prompt);
        localTextPaint = sStatusInvitedPaint;
        this.mRsvpBanner = sBlueRsvpBannerBitmap;
        break label764;
      }
      label1119: str3 = localPlace.name;
      break label631;
      label1129: str2 = null;
      if (localHangoutInfo != null)
      {
        str2 = sHangoutTitle;
        this.mLocationIcon = sHangoutBitmap;
      }
    }
  }

  public final void onAvatarChanged(String paramString)
  {
    if (this.mAvatar != null)
      this.mAvatar.onAvatarChanged(paramString);
  }

  public final void onMediaImageChanged(String paramString)
  {
    if ((this.mEventInfo != null) && (this.mThemeImage != null) && (MediaImageRequest.areCanonicallyEqual((MediaImageRequest)this.mThemeImage.getRequest(), paramString)))
      this.mThemeImage.invalidate();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventCardDrawer
 * JD-Core Version:    0.6.2
 */