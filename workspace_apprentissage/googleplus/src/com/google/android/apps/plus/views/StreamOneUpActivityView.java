package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.content.DbEmbedDeepLink;
import com.google.android.apps.plus.content.DbEmbedHangout;
import com.google.android.apps.plus.content.DbEmbedMedia;
import com.google.android.apps.plus.content.DbEmbedSkyjam;
import com.google.android.apps.plus.content.DbEmbedSquare;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.DbPlusOneData;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.service.ResourceConsumer;
import com.google.android.apps.plus.util.AccessibilityUtils;
import com.google.android.apps.plus.util.Dates;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.PlusBarUtils;
import com.google.android.apps.plus.util.SpannableUtils;
import com.google.android.apps.plus.util.TextPaintUtils;
import com.google.api.services.plusi.model.EmbedClientItem;
import com.google.api.services.plusi.model.Place;
import com.google.api.services.plusi.model.PlaceReview;
import com.google.api.services.plusi.model.PlaceReviewJson;
import com.google.api.services.plusi.model.Rating;
import com.google.api.services.plusi.model.RatingClientDisplayData;
import com.googlecode.eyesfree.utils.TouchExplorationHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class StreamOneUpActivityView extends OneUpBaseView
  implements ResourceConsumer, ClickableButton.ClickableButtonListener, Recyclable
{
  private static Paint sActionBarBackgroundPaint;
  private static int sAvatarMarginLeft;
  private static int sAvatarMarginRight;
  private static int sAvatarMarginTop;
  private static Bitmap sAvatarOverlayBitmap;
  private static int sAvatarSize;
  private static Paint sBackgroundPaint;
  private static Bitmap sCheckInIconBitmap;
  private static TextPaint sContentPaint;
  private static TextPaint sDatePaint;
  private static Bitmap sDefaultAvatarBitmap;
  private static float sFontSpacing;
  private static int sIsMuteColor;
  private static Paint sLinkedBackgroundPaint;
  private static TextPaint sLinkedBodyPaint;
  private static Paint sLinkedBorderPaint;
  private static float sLinkedBorderRadius;
  private static int sLinkedBorderSize;
  private static TextPaint sLinkedHeaderPaint;
  private static Bitmap sLinkedIconBitmap;
  private static int sLinkedIconMarginRight;
  private static int sLinkedInnerMargin;
  private static Bitmap sLocationIconBitmap;
  private static int sLocationIconMarginRight;
  private static int sLocationIconMarginTop;
  private static TextPaint sLocationPaint;
  private static int sMarginBottom;
  private static int sMarginLeft;
  private static int sMarginRight;
  private static int sNameMarginTop;
  private static TextPaint sNamePaint;
  private static int sPlaceReviewAspectsMarginBottom;
  private static int sPlaceReviewAspectsMarginTop;
  private static int sPlaceReviewDividerMargin;
  private static Paint sPlaceReviewDividerPaint;
  private static int sPlusOneButtonMarginLeft;
  private static int sPlusOneButtonMarginRight;
  private static Paint sReshareBackgroundPaint;
  private static TextPaint sReshareBodyPaint;
  private static Paint sReshareBorderPaint;
  private static float sReshareBorderRadius;
  private static int sReshareBorderSize;
  private static TextPaint sReshareHeaderPaint;
  private static int sReshareInnerMargin;
  private static Paint sResizePaint;
  private static Bitmap sSkyjamIconBitmap;
  private static int sTitleMarginBottom;
  private final ClickableStaticLayout.SpanClickListener mAclClickListener = new ClickableStaticLayout.SpanClickListener()
  {
    public final void onSpanClick(URLSpan paramAnonymousURLSpan)
    {
      int i;
      String str1;
      label65: String str2;
      if (StreamOneUpActivityView.this.mOneUpListener != null)
      {
        if ((!"square".equals(paramAnonymousURLSpan.getURL())) || (StreamOneUpActivityView.this.mDbEmbedSquare == null))
          break label122;
        if (TextUtils.isEmpty(StreamOneUpActivityView.this.mDbEmbedSquare.getAboutSquareId()))
          break label88;
        i = 1;
        if (i == 0)
          break label93;
        str1 = StreamOneUpActivityView.this.mDbEmbedSquare.getAboutSquareId();
        if (i == 0)
          break label107;
        str2 = null;
        label72: StreamOneUpActivityView.this.mOneUpListener.onSquareClick(str1, str2);
      }
      while (true)
      {
        return;
        label88: i = 0;
        break;
        label93: str1 = StreamOneUpActivityView.this.mDbEmbedSquare.getSquareId();
        break label65;
        label107: str2 = StreamOneUpActivityView.this.mDbEmbedSquare.getSquareStreamId();
        break label72;
        label122: StreamOneUpActivityView.this.mOneUpListener.onSpanClick(new URLSpan("acl:" + StreamOneUpActivityView.this.mActivityId));
      }
    }
  };
  private String mAclText;
  private String mActivityId;
  private Spannable mAnnotation;
  private ClickableStaticLayout mAnnotationLayout;
  private String mAuthorId;
  private ClickableAvatar mAuthorImage;
  private PositionedStaticLayout mAuthorLayout;
  private String mAuthorName;
  private int mBackgroundOffset;
  private Set<ClickableItem> mClickableItems = new HashSet();
  private String mCreationSource;
  private ClickableItem mCurrentClickableItem;
  private String mDate;
  private ClickableStaticLayout mDateSourceAclLayout;
  private DbEmbedSquare mDbEmbedSquare;
  private boolean mEdited;
  private boolean mIsCheckin;
  private Spannable mLinkedBody;
  private ClickableStaticLayout mLinkedBodyLayout;
  private RectF mLinkedContentBorder;
  private Spannable mLinkedHeader;
  private ClickableStaticLayout mLinkedHeaderLayout;
  private Rect mLinkedIconRect;
  private Spannable mLocation;
  private final ClickableStaticLayout.SpanClickListener mLocationClickListener = new ClickableStaticLayout.SpanClickListener()
  {
    public final void onSpanClick(URLSpan paramAnonymousURLSpan)
    {
      if (StreamOneUpActivityView.this.mOneUpListener != null)
      {
        if (StreamOneUpActivityView.this.mLocationData == null)
          break label50;
        OneUpListener localOneUpListener = StreamOneUpActivityView.this.mOneUpListener;
        localOneUpListener.onLocationClick$75c560e7(StreamOneUpActivityView.this.mLocationData);
      }
      while (true)
      {
        return;
        label50: if ((StreamOneUpActivityView.this.mPlaceReview != null) && (StreamOneUpActivityView.this.mPlaceReview.itemReviewed != null) && (StreamOneUpActivityView.this.mPlaceReview.itemReviewed.place != null) && (!TextUtils.isEmpty(StreamOneUpActivityView.this.mPlaceReview.itemReviewed.place.ownerObfuscatedId)))
          StreamOneUpActivityView.this.mOneUpListener.onPlaceClick(StreamOneUpActivityView.this.mPlaceReview.itemReviewed.place.ownerObfuscatedId);
      }
    }
  };
  private DbLocation mLocationData;
  private Bitmap mLocationIcon;
  private Rect mLocationIconRect;
  private ClickableStaticLayout mLocationLayout;
  private String mMuteState;
  private OneUpListener mOneUpListener;
  private PlaceReview mPlaceReview;
  private PositionedStaticLayout mPlaceReviewAspectsLayout;
  private PositionedStaticLayout mPlaceReviewBodyLayout;
  private Rect mPlaceReviewDividerRect;
  protected ClickableButton mPlusOneButton;
  private DbPlusOneData mPlusOneData;
  private Spannable mReshareBody;
  private ClickableStaticLayout mReshareBodyLayout;
  private RectF mReshareContentBorder;
  private Spannable mReshareHeader;
  private ClickableStaticLayout mReshareHeaderLayout;
  private final ClickableStaticLayout.SpanClickListener mSkyjamClickListener = new ClickableStaticLayout.SpanClickListener()
  {
    public final void onSpanClick(URLSpan paramAnonymousURLSpan)
    {
      String str1;
      if (StreamOneUpActivityView.this.mOneUpListener != null)
      {
        str1 = paramAnonymousURLSpan.getURL();
        if (!str1.startsWith("skyjam:buy:"))
          break label47;
        String str3 = str1.substring(11);
        StreamOneUpActivityView.this.mOneUpListener.onSkyjamBuyClick(str3);
      }
      while (true)
      {
        return;
        label47: if (str1.startsWith("skyjam:listen:"))
        {
          String str2 = str1.substring(14);
          StreamOneUpActivityView.this.mOneUpListener.onSkyjamListenClick(str2);
        }
      }
    }
  };
  private RectF mSkyjamContentBorder;
  private Spannable mSkyjamHeader;
  private ClickableStaticLayout mSkyjamHeaderLayout;
  private Rect mSkyjamIconRect;
  private Spannable mSkyjamSubheader1;
  private PositionedStaticLayout mSkyjamSubheader1Layout;
  private Spannable mSkyjamSubheader2;
  private ClickableStaticLayout mSkyjamSubheader2Layout;
  private String mSourceAppData;
  private final List<String> mSourceAppPackages = new ArrayList();
  private final ClickableStaticLayout.SpanClickListener mSourceClickListener = new ClickableStaticLayout.SpanClickListener()
  {
    public final void onSpanClick(URLSpan paramAnonymousURLSpan)
    {
      if (StreamOneUpActivityView.this.mOneUpListener != null)
        StreamOneUpActivityView.this.mOneUpListener.onSourceAppContentClick(StreamOneUpActivityView.this.mCreationSource, StreamOneUpActivityView.this.mSourceAppPackages, StreamOneUpActivityView.this.mSourceAppData, paramAnonymousURLSpan.getURL(), StreamOneUpActivityView.this.mAuthorId);
    }
  };
  private Spannable mTitle;
  private ClickableStaticLayout mTitleLayout;
  private OneUpActivityTouchExplorer mTouchExplorer;

  public StreamOneUpActivityView(Context paramContext)
  {
    super(paramContext);
    if (sNamePaint == null)
    {
      Resources localResources = getContext().getResources();
      sFontSpacing = localResources.getDimension(R.dimen.stream_one_up_font_spacing);
      sAvatarSize = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_size);
      sMarginBottom = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_bottom);
      sMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_left);
      sMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_right);
      sTitleMarginBottom = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_title_margin_bottom);
      sAvatarMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_top);
      sAvatarMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_left);
      sAvatarMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_right);
      sNameMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_name_margin_top);
      sLinkedInnerMargin = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_linked_inner_margin);
      sLinkedBorderSize = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_linked_border_size);
      sLinkedBorderRadius = localResources.getDimension(R.dimen.stream_one_up_linked_border_radius);
      sLinkedIconMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_linked_icon_margin_right);
      sReshareInnerMargin = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_reshare_inner_margin);
      sReshareBorderSize = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_reshare_border_size);
      sReshareBorderRadius = localResources.getDimension(R.dimen.stream_one_up_reshare_border_radius);
      sLocationIconMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_location_icon_margin_top);
      sLocationIconMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_location_icon_margin_right);
      int i = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_plus_one_button_margin_right);
      sPlusOneButtonMarginLeft = i;
      sPlusOneButtonMarginRight = i;
      sPlaceReviewDividerMargin = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_place_review_divider_margin);
      sPlaceReviewAspectsMarginBottom = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_place_review_aspects_margin_bottom);
      sPlaceReviewAspectsMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_place_review_aspects_margin_top);
      sDefaultAvatarBitmap = EsAvatarData.getMediumDefaultAvatar(getContext(), true);
      sLinkedIconBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_link_blue);
      sSkyjamIconBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_music_blue);
      sCheckInIconBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_checkin_small);
      sLocationIconBitmap = ImageUtils.decodeResource(localResources, R.drawable.icn_location_card);
      sAvatarOverlayBitmap = ImageUtils.decodeResource(localResources, R.drawable.bg_taco_avatar);
      TextPaint localTextPaint1 = new TextPaint();
      sNamePaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sNamePaint.setTypeface(Typeface.DEFAULT_BOLD);
      sNamePaint.setColor(localResources.getColor(R.color.stream_one_up_name));
      sNamePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_name_text_size));
      TextPaintUtils.registerTextPaint(sNamePaint, R.dimen.stream_one_up_name_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sDatePaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sDatePaint.setColor(localResources.getColor(R.color.stream_one_up_date));
      sDatePaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sDatePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_date_text_size));
      TextPaintUtils.registerTextPaint(sDatePaint, R.dimen.stream_one_up_date_text_size);
      sIsMuteColor = localResources.getColor(R.color.stream_one_up_muted);
      TextPaint localTextPaint3 = new TextPaint();
      sContentPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sContentPaint.setColor(localResources.getColor(R.color.stream_one_up_content));
      sContentPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sContentPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_content_text_size));
      TextPaintUtils.registerTextPaint(sContentPaint, R.dimen.stream_one_up_content_text_size);
      TextPaint localTextPaint4 = new TextPaint();
      sLinkedHeaderPaint = localTextPaint4;
      localTextPaint4.setAntiAlias(true);
      sLinkedHeaderPaint.setColor(localResources.getColor(R.color.stream_one_up_linked_header));
      sLinkedHeaderPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sLinkedHeaderPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sLinkedHeaderPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_linked_header_text_size));
      TextPaintUtils.registerTextPaint(sLinkedHeaderPaint, R.dimen.stream_one_up_linked_header_text_size);
      TextPaint localTextPaint5 = new TextPaint();
      sLinkedBodyPaint = localTextPaint5;
      localTextPaint5.setAntiAlias(true);
      sLinkedBodyPaint.setColor(localResources.getColor(R.color.stream_one_up_linked_body));
      sLinkedBodyPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sLinkedBodyPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_linked_body_text_size));
      TextPaintUtils.registerTextPaint(sLinkedBodyPaint, R.dimen.stream_one_up_linked_body_text_size);
      TextPaint localTextPaint6 = new TextPaint();
      sReshareHeaderPaint = localTextPaint6;
      localTextPaint6.setAntiAlias(true);
      sReshareHeaderPaint.setColor(localResources.getColor(R.color.stream_one_up_reshare_header));
      sReshareHeaderPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sReshareHeaderPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_reshare_header_text_size));
      TextPaintUtils.registerTextPaint(sReshareHeaderPaint, R.dimen.stream_one_up_reshare_header_text_size);
      TextPaint localTextPaint7 = new TextPaint();
      sReshareBodyPaint = localTextPaint7;
      localTextPaint7.setAntiAlias(true);
      sReshareBodyPaint.setColor(localResources.getColor(R.color.stream_one_up_reshare_body));
      sReshareBodyPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sReshareBodyPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_reshare_body_text_size));
      TextPaintUtils.registerTextPaint(sReshareBodyPaint, R.dimen.stream_one_up_reshare_body_text_size);
      TextPaint localTextPaint8 = new TextPaint();
      sLocationPaint = localTextPaint8;
      localTextPaint8.setAntiAlias(true);
      sLocationPaint.setColor(localResources.getColor(R.color.stream_one_up_location));
      sLocationPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sLocationPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_location_text_size));
      TextPaintUtils.registerTextPaint(sLocationPaint, R.dimen.stream_one_up_location_text_size);
      Paint localPaint1 = new Paint();
      sBackgroundPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.stream_one_up_list_background));
      sBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sLinkedBackgroundPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.stream_one_up_linked_background));
      sLinkedBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint3 = new Paint();
      sLinkedBorderPaint = localPaint3;
      localPaint3.setColor(localResources.getColor(R.color.stream_one_up_linked_border));
      sLinkedBorderPaint.setStrokeWidth(sLinkedBorderSize);
      sLinkedBorderPaint.setStyle(Paint.Style.STROKE);
      Paint localPaint4 = new Paint();
      sReshareBackgroundPaint = localPaint4;
      localPaint4.setColor(localResources.getColor(R.color.stream_one_up_reshare_background));
      sReshareBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint5 = new Paint();
      sReshareBorderPaint = localPaint5;
      localPaint5.setColor(localResources.getColor(R.color.stream_one_up_reshare_border));
      sReshareBorderPaint.setStrokeWidth(sReshareBorderSize);
      sReshareBorderPaint.setStyle(Paint.Style.STROKE);
      Paint localPaint6 = new Paint();
      sActionBarBackgroundPaint = localPaint6;
      localPaint6.setColor(localResources.getColor(R.color.stream_one_up_action_bar_background));
      sActionBarBackgroundPaint.setStyle(Paint.Style.FILL);
      sResizePaint = new Paint(2);
      Paint localPaint7 = new Paint();
      sPlaceReviewDividerPaint = localPaint7;
      localPaint7.setColor(localResources.getColor(R.color.stream_one_up_place_review_divider));
      sPlaceReviewDividerPaint.setStrokeWidth(localResources.getDimension(R.dimen.stream_one_up_place_review_divider_stroke_width));
    }
    setupAccessibility(getContext());
  }

  public StreamOneUpActivityView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (sNamePaint == null)
    {
      Resources localResources = getContext().getResources();
      sFontSpacing = localResources.getDimension(R.dimen.stream_one_up_font_spacing);
      sAvatarSize = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_size);
      sMarginBottom = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_bottom);
      sMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_left);
      sMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_right);
      sTitleMarginBottom = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_title_margin_bottom);
      sAvatarMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_top);
      sAvatarMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_left);
      sAvatarMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_right);
      sNameMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_name_margin_top);
      sLinkedInnerMargin = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_linked_inner_margin);
      sLinkedBorderSize = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_linked_border_size);
      sLinkedBorderRadius = localResources.getDimension(R.dimen.stream_one_up_linked_border_radius);
      sLinkedIconMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_linked_icon_margin_right);
      sReshareInnerMargin = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_reshare_inner_margin);
      sReshareBorderSize = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_reshare_border_size);
      sReshareBorderRadius = localResources.getDimension(R.dimen.stream_one_up_reshare_border_radius);
      sLocationIconMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_location_icon_margin_top);
      sLocationIconMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_location_icon_margin_right);
      int i = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_plus_one_button_margin_right);
      sPlusOneButtonMarginLeft = i;
      sPlusOneButtonMarginRight = i;
      sPlaceReviewDividerMargin = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_place_review_divider_margin);
      sPlaceReviewAspectsMarginBottom = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_place_review_aspects_margin_bottom);
      sPlaceReviewAspectsMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_place_review_aspects_margin_top);
      sDefaultAvatarBitmap = EsAvatarData.getMediumDefaultAvatar(getContext(), true);
      sLinkedIconBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_link_blue);
      sSkyjamIconBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_music_blue);
      sCheckInIconBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_checkin_small);
      sLocationIconBitmap = ImageUtils.decodeResource(localResources, R.drawable.icn_location_card);
      sAvatarOverlayBitmap = ImageUtils.decodeResource(localResources, R.drawable.bg_taco_avatar);
      TextPaint localTextPaint1 = new TextPaint();
      sNamePaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sNamePaint.setTypeface(Typeface.DEFAULT_BOLD);
      sNamePaint.setColor(localResources.getColor(R.color.stream_one_up_name));
      sNamePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_name_text_size));
      TextPaintUtils.registerTextPaint(sNamePaint, R.dimen.stream_one_up_name_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sDatePaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sDatePaint.setColor(localResources.getColor(R.color.stream_one_up_date));
      sDatePaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sDatePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_date_text_size));
      TextPaintUtils.registerTextPaint(sDatePaint, R.dimen.stream_one_up_date_text_size);
      sIsMuteColor = localResources.getColor(R.color.stream_one_up_muted);
      TextPaint localTextPaint3 = new TextPaint();
      sContentPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sContentPaint.setColor(localResources.getColor(R.color.stream_one_up_content));
      sContentPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sContentPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_content_text_size));
      TextPaintUtils.registerTextPaint(sContentPaint, R.dimen.stream_one_up_content_text_size);
      TextPaint localTextPaint4 = new TextPaint();
      sLinkedHeaderPaint = localTextPaint4;
      localTextPaint4.setAntiAlias(true);
      sLinkedHeaderPaint.setColor(localResources.getColor(R.color.stream_one_up_linked_header));
      sLinkedHeaderPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sLinkedHeaderPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sLinkedHeaderPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_linked_header_text_size));
      TextPaintUtils.registerTextPaint(sLinkedHeaderPaint, R.dimen.stream_one_up_linked_header_text_size);
      TextPaint localTextPaint5 = new TextPaint();
      sLinkedBodyPaint = localTextPaint5;
      localTextPaint5.setAntiAlias(true);
      sLinkedBodyPaint.setColor(localResources.getColor(R.color.stream_one_up_linked_body));
      sLinkedBodyPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sLinkedBodyPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_linked_body_text_size));
      TextPaintUtils.registerTextPaint(sLinkedBodyPaint, R.dimen.stream_one_up_linked_body_text_size);
      TextPaint localTextPaint6 = new TextPaint();
      sReshareHeaderPaint = localTextPaint6;
      localTextPaint6.setAntiAlias(true);
      sReshareHeaderPaint.setColor(localResources.getColor(R.color.stream_one_up_reshare_header));
      sReshareHeaderPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sReshareHeaderPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_reshare_header_text_size));
      TextPaintUtils.registerTextPaint(sReshareHeaderPaint, R.dimen.stream_one_up_reshare_header_text_size);
      TextPaint localTextPaint7 = new TextPaint();
      sReshareBodyPaint = localTextPaint7;
      localTextPaint7.setAntiAlias(true);
      sReshareBodyPaint.setColor(localResources.getColor(R.color.stream_one_up_reshare_body));
      sReshareBodyPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sReshareBodyPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_reshare_body_text_size));
      TextPaintUtils.registerTextPaint(sReshareBodyPaint, R.dimen.stream_one_up_reshare_body_text_size);
      TextPaint localTextPaint8 = new TextPaint();
      sLocationPaint = localTextPaint8;
      localTextPaint8.setAntiAlias(true);
      sLocationPaint.setColor(localResources.getColor(R.color.stream_one_up_location));
      sLocationPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sLocationPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_location_text_size));
      TextPaintUtils.registerTextPaint(sLocationPaint, R.dimen.stream_one_up_location_text_size);
      Paint localPaint1 = new Paint();
      sBackgroundPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.stream_one_up_list_background));
      sBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sLinkedBackgroundPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.stream_one_up_linked_background));
      sLinkedBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint3 = new Paint();
      sLinkedBorderPaint = localPaint3;
      localPaint3.setColor(localResources.getColor(R.color.stream_one_up_linked_border));
      sLinkedBorderPaint.setStrokeWidth(sLinkedBorderSize);
      sLinkedBorderPaint.setStyle(Paint.Style.STROKE);
      Paint localPaint4 = new Paint();
      sReshareBackgroundPaint = localPaint4;
      localPaint4.setColor(localResources.getColor(R.color.stream_one_up_reshare_background));
      sReshareBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint5 = new Paint();
      sReshareBorderPaint = localPaint5;
      localPaint5.setColor(localResources.getColor(R.color.stream_one_up_reshare_border));
      sReshareBorderPaint.setStrokeWidth(sReshareBorderSize);
      sReshareBorderPaint.setStyle(Paint.Style.STROKE);
      Paint localPaint6 = new Paint();
      sActionBarBackgroundPaint = localPaint6;
      localPaint6.setColor(localResources.getColor(R.color.stream_one_up_action_bar_background));
      sActionBarBackgroundPaint.setStyle(Paint.Style.FILL);
      sResizePaint = new Paint(2);
      Paint localPaint7 = new Paint();
      sPlaceReviewDividerPaint = localPaint7;
      localPaint7.setColor(localResources.getColor(R.color.stream_one_up_place_review_divider));
      sPlaceReviewDividerPaint.setStrokeWidth(localResources.getDimension(R.dimen.stream_one_up_place_review_divider_stroke_width));
    }
    setupAccessibility(getContext());
  }

  public StreamOneUpActivityView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (sNamePaint == null)
    {
      Resources localResources = getContext().getResources();
      sFontSpacing = localResources.getDimension(R.dimen.stream_one_up_font_spacing);
      sAvatarSize = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_size);
      sMarginBottom = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_bottom);
      sMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_left);
      sMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_right);
      sTitleMarginBottom = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_title_margin_bottom);
      sAvatarMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_top);
      sAvatarMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_left);
      sAvatarMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_right);
      sNameMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_name_margin_top);
      sLinkedInnerMargin = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_linked_inner_margin);
      sLinkedBorderSize = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_linked_border_size);
      sLinkedBorderRadius = localResources.getDimension(R.dimen.stream_one_up_linked_border_radius);
      sLinkedIconMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_linked_icon_margin_right);
      sReshareInnerMargin = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_reshare_inner_margin);
      sReshareBorderSize = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_reshare_border_size);
      sReshareBorderRadius = localResources.getDimension(R.dimen.stream_one_up_reshare_border_radius);
      sLocationIconMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_location_icon_margin_top);
      sLocationIconMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_location_icon_margin_right);
      int i = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_plus_one_button_margin_right);
      sPlusOneButtonMarginLeft = i;
      sPlusOneButtonMarginRight = i;
      sPlaceReviewDividerMargin = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_place_review_divider_margin);
      sPlaceReviewAspectsMarginBottom = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_place_review_aspects_margin_bottom);
      sPlaceReviewAspectsMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_place_review_aspects_margin_top);
      sDefaultAvatarBitmap = EsAvatarData.getMediumDefaultAvatar(getContext(), true);
      sLinkedIconBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_link_blue);
      sSkyjamIconBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_music_blue);
      sCheckInIconBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_checkin_small);
      sLocationIconBitmap = ImageUtils.decodeResource(localResources, R.drawable.icn_location_card);
      sAvatarOverlayBitmap = ImageUtils.decodeResource(localResources, R.drawable.bg_taco_avatar);
      TextPaint localTextPaint1 = new TextPaint();
      sNamePaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sNamePaint.setTypeface(Typeface.DEFAULT_BOLD);
      sNamePaint.setColor(localResources.getColor(R.color.stream_one_up_name));
      sNamePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_name_text_size));
      TextPaintUtils.registerTextPaint(sNamePaint, R.dimen.stream_one_up_name_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sDatePaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sDatePaint.setColor(localResources.getColor(R.color.stream_one_up_date));
      sDatePaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sDatePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_date_text_size));
      TextPaintUtils.registerTextPaint(sDatePaint, R.dimen.stream_one_up_date_text_size);
      sIsMuteColor = localResources.getColor(R.color.stream_one_up_muted);
      TextPaint localTextPaint3 = new TextPaint();
      sContentPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sContentPaint.setColor(localResources.getColor(R.color.stream_one_up_content));
      sContentPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sContentPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_content_text_size));
      TextPaintUtils.registerTextPaint(sContentPaint, R.dimen.stream_one_up_content_text_size);
      TextPaint localTextPaint4 = new TextPaint();
      sLinkedHeaderPaint = localTextPaint4;
      localTextPaint4.setAntiAlias(true);
      sLinkedHeaderPaint.setColor(localResources.getColor(R.color.stream_one_up_linked_header));
      sLinkedHeaderPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sLinkedHeaderPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sLinkedHeaderPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_linked_header_text_size));
      TextPaintUtils.registerTextPaint(sLinkedHeaderPaint, R.dimen.stream_one_up_linked_header_text_size);
      TextPaint localTextPaint5 = new TextPaint();
      sLinkedBodyPaint = localTextPaint5;
      localTextPaint5.setAntiAlias(true);
      sLinkedBodyPaint.setColor(localResources.getColor(R.color.stream_one_up_linked_body));
      sLinkedBodyPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sLinkedBodyPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_linked_body_text_size));
      TextPaintUtils.registerTextPaint(sLinkedBodyPaint, R.dimen.stream_one_up_linked_body_text_size);
      TextPaint localTextPaint6 = new TextPaint();
      sReshareHeaderPaint = localTextPaint6;
      localTextPaint6.setAntiAlias(true);
      sReshareHeaderPaint.setColor(localResources.getColor(R.color.stream_one_up_reshare_header));
      sReshareHeaderPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sReshareHeaderPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_reshare_header_text_size));
      TextPaintUtils.registerTextPaint(sReshareHeaderPaint, R.dimen.stream_one_up_reshare_header_text_size);
      TextPaint localTextPaint7 = new TextPaint();
      sReshareBodyPaint = localTextPaint7;
      localTextPaint7.setAntiAlias(true);
      sReshareBodyPaint.setColor(localResources.getColor(R.color.stream_one_up_reshare_body));
      sReshareBodyPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sReshareBodyPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_reshare_body_text_size));
      TextPaintUtils.registerTextPaint(sReshareBodyPaint, R.dimen.stream_one_up_reshare_body_text_size);
      TextPaint localTextPaint8 = new TextPaint();
      sLocationPaint = localTextPaint8;
      localTextPaint8.setAntiAlias(true);
      sLocationPaint.setColor(localResources.getColor(R.color.stream_one_up_location));
      sLocationPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sLocationPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_location_text_size));
      TextPaintUtils.registerTextPaint(sLocationPaint, R.dimen.stream_one_up_location_text_size);
      Paint localPaint1 = new Paint();
      sBackgroundPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.stream_one_up_list_background));
      sBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sLinkedBackgroundPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.stream_one_up_linked_background));
      sLinkedBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint3 = new Paint();
      sLinkedBorderPaint = localPaint3;
      localPaint3.setColor(localResources.getColor(R.color.stream_one_up_linked_border));
      sLinkedBorderPaint.setStrokeWidth(sLinkedBorderSize);
      sLinkedBorderPaint.setStyle(Paint.Style.STROKE);
      Paint localPaint4 = new Paint();
      sReshareBackgroundPaint = localPaint4;
      localPaint4.setColor(localResources.getColor(R.color.stream_one_up_reshare_background));
      sReshareBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint5 = new Paint();
      sReshareBorderPaint = localPaint5;
      localPaint5.setColor(localResources.getColor(R.color.stream_one_up_reshare_border));
      sReshareBorderPaint.setStrokeWidth(sReshareBorderSize);
      sReshareBorderPaint.setStyle(Paint.Style.STROKE);
      Paint localPaint6 = new Paint();
      sActionBarBackgroundPaint = localPaint6;
      localPaint6.setColor(localResources.getColor(R.color.stream_one_up_action_bar_background));
      sActionBarBackgroundPaint.setStyle(Paint.Style.FILL);
      sResizePaint = new Paint(2);
      Paint localPaint7 = new Paint();
      sPlaceReviewDividerPaint = localPaint7;
      localPaint7.setColor(localResources.getColor(R.color.stream_one_up_place_review_divider));
      sPlaceReviewDividerPaint.setStrokeWidth(localResources.getDimension(R.dimen.stream_one_up_place_review_divider_stroke_width));
    }
    setupAccessibility(getContext());
  }

  private void clearLayoutState()
  {
    unbindResources();
    this.mAuthorLayout = null;
    this.mDateSourceAclLayout = null;
    this.mAnnotationLayout = null;
    this.mTitleLayout = null;
    this.mLinkedHeaderLayout = null;
    this.mLinkedBodyLayout = null;
    this.mReshareHeaderLayout = null;
    this.mReshareBodyLayout = null;
    this.mPlaceReviewBodyLayout = null;
    this.mPlaceReviewAspectsLayout = null;
    this.mAuthorImage = null;
    this.mLocationIcon = null;
    this.mClickableItems.clear();
    this.mCurrentClickableItem = null;
    this.mLinkedContentBorder = null;
    this.mSkyjamContentBorder = null;
    this.mLinkedIconRect = null;
    this.mSkyjamIconRect = null;
    this.mLocationIconRect = null;
    this.mReshareContentBorder = null;
    this.mLocationData = null;
    this.mPlusOneData = null;
    this.mAnnotation = null;
    this.mTitle = null;
    this.mLinkedHeader = null;
    this.mLinkedBody = null;
    this.mSkyjamHeader = null;
    this.mSkyjamSubheader1 = null;
    this.mSkyjamSubheader2 = null;
    this.mReshareHeader = null;
    this.mReshareBody = null;
    this.mLocation = null;
    this.mPlaceReview = null;
    this.mPlaceReviewDividerRect = null;
  }

  private int measureAndLayoutLinkedContent(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((TextUtils.isEmpty(this.mLinkedHeader)) && (TextUtils.isEmpty(this.mLinkedBody)));
    while (this.mPlaceReview != null)
      return paramInt2;
    int i = paramInt1 + sLinkedInnerMargin;
    int j = paramInt2 + sLinkedInnerMargin;
    if (this.mSourceAppPackages.isEmpty());
    for (Object localObject = this.mOneUpListener; ; localObject = this.mSourceClickListener)
    {
      Spannable localSpannable = this.mLinkedHeader;
      int k = 0;
      int m = 0;
      int n = 0;
      int i1 = 0;
      if (localSpannable != null)
      {
        int i3 = sLinkedIconBitmap.getWidth();
        int i4 = sLinkedIconBitmap.getHeight();
        this.mLinkedIconRect = new Rect(i, j, i + i3, j + i4);
        int i5 = i + (i3 + sLinkedIconMarginRight);
        int i6 = paramInt3 - 2 * sLinkedInnerMargin - i3 - sLinkedIconMarginRight;
        this.mClickableItems.remove(this.mLinkedHeaderLayout);
        this.mLinkedHeaderLayout = new ClickableStaticLayout(this.mLinkedHeader, sLinkedHeaderPaint, i6, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false, (ClickableStaticLayout.SpanClickListener)localObject);
        this.mLinkedHeaderLayout.setPosition(i5, j);
        this.mClickableItems.add(this.mLinkedHeaderLayout);
        m = this.mLinkedIconRect.left - sLinkedInnerMargin;
        i1 = this.mLinkedIconRect.top - sLinkedInnerMargin;
        n = this.mLinkedHeaderLayout.getRight() + sLinkedInnerMargin;
        k = this.mLinkedHeaderLayout.getBottom() + sLinkedInnerMargin;
        i = i5 - (i3 + sLinkedIconMarginRight);
        j = Math.max(j + i4, k);
      }
      if (this.mLinkedBody != null)
      {
        int i2 = paramInt3 - 2 * sLinkedInnerMargin;
        this.mClickableItems.remove(this.mLinkedBodyLayout);
        this.mLinkedBodyLayout = new ClickableStaticLayout(this.mLinkedBody, sLinkedBodyPaint, i2, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false, (ClickableStaticLayout.SpanClickListener)localObject);
        this.mLinkedBodyLayout.setPosition(i, j);
        this.mClickableItems.add(this.mLinkedBodyLayout);
        if (m == 0)
        {
          m = this.mLinkedBodyLayout.getLeft() - sLinkedInnerMargin;
          i1 = this.mLinkedBodyLayout.getTop() - sLinkedInnerMargin;
        }
        n = this.mLinkedBodyLayout.getRight() + sLinkedInnerMargin;
        k = this.mLinkedBodyLayout.getBottom() + sLinkedInnerMargin;
      }
      this.mLinkedContentBorder = new RectF(m, i1, n, k);
      paramInt2 = k;
      break;
    }
  }

  private int measureAndLayoutLocation(int paramInt1, int paramInt2, int paramInt3)
  {
    if (TextUtils.isEmpty(this.mLocation))
      return paramInt2;
    int i = this.mLocationIcon.getWidth();
    int j = this.mLocationIcon.getHeight();
    int k;
    label42: int m;
    int n;
    int i1;
    if (this.mIsCheckin)
    {
      k = sLocationIconMarginTop;
      m = paramInt2 + k;
      n = paramInt1 + i;
      i1 = paramInt2 + j;
      if (!this.mIsCheckin)
        break label205;
    }
    label205: for (int i2 = sLocationIconMarginTop; ; i2 = 0)
    {
      this.mLocationIconRect = new Rect(paramInt1, m, n, i2 + i1);
      int i3 = paramInt1 + (i + sLocationIconMarginRight);
      int i4 = paramInt3 - i - sLocationIconMarginRight;
      this.mClickableItems.remove(this.mLocationLayout);
      this.mLocationLayout = new ClickableStaticLayout(this.mLocation, sLocationPaint, i4, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false, this.mLocationClickListener);
      this.mLocationLayout.setPosition(i3, paramInt2);
      this.mClickableItems.add(this.mLocationLayout);
      paramInt2 = Math.max(j, this.mLocationLayout.getBottom());
      break;
      k = 0;
      break label42;
    }
  }

  private int measureAndLayoutPlaceReviewContent(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.mPlaceReview == null);
    while (true)
    {
      return paramInt2;
      int i = paramInt2;
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
      if (this.mPlaceReview.reviewRating != null)
      {
        int k = 0;
        int m = this.mPlaceReview.reviewRating.size();
        while (k < m)
        {
          Rating localRating = (Rating)this.mPlaceReview.reviewRating.get(k);
          String str1 = localRating.name;
          String str2 = localRating.ratingValue;
          if ((localRating.clientDisplayData != null) && (!TextUtils.isEmpty(localRating.clientDisplayData.renderedRatingText)))
            str2 = localRating.clientDisplayData.renderedRatingText;
          if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)))
          {
            SpannableUtils.appendWithSpan(localSpannableStringBuilder, str1, new TextAppearanceSpan(getContext(), R.style.ProfileLocalUserRating_AspectLabel));
            localSpannableStringBuilder.append(" ");
            SpannableUtils.appendWithSpan(localSpannableStringBuilder, str2, new TextAppearanceSpan(getContext(), R.style.ProfileLocalUserRating_AspectValue));
            if (k != m - 1)
              localSpannableStringBuilder.append("  ");
          }
          k++;
        }
      }
      if (localSpannableStringBuilder.length() > 0)
      {
        int j = paramInt2 + sPlaceReviewAspectsMarginTop;
        this.mPlaceReviewAspectsLayout = new PositionedStaticLayout(localSpannableStringBuilder, sContentPaint, paramInt3, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false);
        this.mPlaceReviewAspectsLayout.setPosition(paramInt1, j);
        i = j + (this.mPlaceReviewAspectsLayout.getHeight() + sPlaceReviewAspectsMarginBottom);
      }
      if (!TextUtils.isEmpty(this.mPlaceReview.reviewBody))
      {
        this.mPlaceReviewBodyLayout = new PositionedStaticLayout(this.mPlaceReview.reviewBody, sContentPaint, paramInt3, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false);
        this.mPlaceReviewBodyLayout.setPosition(paramInt1, i);
        i += this.mPlaceReviewBodyLayout.getHeight();
      }
      paramInt2 = i;
    }
  }

  private int measureAndLayoutPlaceReviewDivider(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.mPlaceReview == null);
    while (true)
    {
      return paramInt2;
      int i = paramInt2 + sPlaceReviewDividerMargin;
      this.mPlaceReviewDividerRect = new Rect(paramInt1, i, paramInt1 + paramInt3, i);
      paramInt2 = i + sPlaceReviewDividerMargin;
    }
  }

  private int measureAndLayoutSkyjamContent(int paramInt1, int paramInt2, int paramInt3)
  {
    if (TextUtils.isEmpty(this.mSkyjamHeader));
    while (true)
    {
      return paramInt2;
      int i = paramInt1 + sLinkedInnerMargin;
      int j = paramInt2 + sLinkedInnerMargin;
      int k = sSkyjamIconBitmap.getWidth();
      int m = sSkyjamIconBitmap.getHeight();
      this.mSkyjamIconRect = new Rect(i, j, i + k, j + m);
      int n = i + (k + sLinkedIconMarginRight);
      int i1 = paramInt3 - 2 * sLinkedInnerMargin - k - sLinkedIconMarginRight;
      this.mClickableItems.remove(this.mSkyjamHeaderLayout);
      this.mSkyjamHeaderLayout = new ClickableStaticLayout(this.mSkyjamHeader, sLinkedHeaderPaint, i1, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false, this.mSkyjamClickListener);
      this.mSkyjamHeaderLayout.setPosition(n, j);
      this.mClickableItems.add(this.mSkyjamHeaderLayout);
      int i2 = this.mSkyjamIconRect.left - sLinkedInnerMargin;
      int i3 = this.mSkyjamIconRect.top - sLinkedInnerMargin;
      int i4 = this.mSkyjamHeaderLayout.getRight() + sLinkedInnerMargin;
      int i5 = this.mSkyjamHeaderLayout.getBottom() + sLinkedInnerMargin;
      int i6 = n - (k + sLinkedIconMarginRight);
      int i7 = Math.max(j + m, i5);
      int i8 = paramInt3 - 2 * sLinkedInnerMargin;
      this.mSkyjamSubheader1Layout = new PositionedStaticLayout(this.mSkyjamSubheader1, sLinkedBodyPaint, i8, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false);
      this.mSkyjamSubheader1Layout.setPosition(i6, i7);
      if (i2 == 0)
      {
        i2 = this.mSkyjamSubheader1Layout.getLeft() - sLinkedInnerMargin;
        i3 = this.mSkyjamSubheader1Layout.getTop() - sLinkedInnerMargin;
      }
      int i9 = Math.max(i4, this.mSkyjamSubheader1Layout.getRight() + sLinkedInnerMargin);
      int i10 = this.mSkyjamSubheader1Layout.getBottom() + sLinkedInnerMargin;
      this.mClickableItems.remove(this.mSkyjamSubheader2Layout);
      this.mSkyjamSubheader2Layout = new ClickableStaticLayout(this.mSkyjamSubheader2, sLinkedBodyPaint, i8, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false, this.mSkyjamClickListener);
      this.mSkyjamSubheader2Layout.setPosition(i6, i10);
      this.mClickableItems.add(this.mSkyjamSubheader2Layout);
      int i11 = Math.max(i9, this.mSkyjamSubheader2Layout.getRight() + sLinkedInnerMargin);
      int i12 = this.mSkyjamSubheader2Layout.getBottom() + sLinkedInnerMargin;
      this.mSkyjamContentBorder = new RectF(i2, i3, i11, i12);
      paramInt2 = i12;
    }
  }

  private int measureAndLayoutTitle(int paramInt1, int paramInt2, int paramInt3)
  {
    if (TextUtils.isEmpty(this.mTitle));
    while (true)
    {
      return paramInt2;
      this.mClickableItems.remove(this.mTitleLayout);
      this.mTitleLayout = new ClickableStaticLayout(this.mTitle, sContentPaint, paramInt3, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false, this.mOneUpListener);
      this.mTitleLayout.setPosition(paramInt1, paramInt2);
      this.mClickableItems.add(this.mTitleLayout);
      paramInt2 = this.mTitleLayout.getBottom();
    }
  }

  private void setupAccessibility(Context paramContext)
  {
    if ((Build.VERSION.SDK_INT >= 16) && (AccessibilityUtils.isAccessibilityEnabled(paramContext)) && (this.mTouchExplorer == null))
    {
      this.mTouchExplorer = new OneUpActivityTouchExplorer(paramContext);
      this.mTouchExplorer.install(this);
    }
  }

  private void updateAccessibility()
  {
    if (this.mTouchExplorer != null)
    {
      this.mTouchExplorer.invalidateItemCache();
      this.mTouchExplorer.invalidateParent();
    }
  }

  public final void bind(Cursor paramCursor)
  {
    unbindResources();
    Context localContext = getContext();
    Resources localResources = getResources();
    clearLayoutState();
    String str1 = paramCursor.getString(2);
    String str2 = paramCursor.getString(4);
    long l = paramCursor.getLong(21);
    boolean bool1;
    boolean bool2;
    label248: String str6;
    label276: int i;
    label513: String str14;
    label683: label758: Bitmap localBitmap;
    if ((0x10 & l) != 0L)
    {
      bool1 = true;
      this.mIsCheckin = bool1;
      this.mDbEmbedSquare = DbEmbedSquare.deserialize(paramCursor.getBlob(32));
      this.mActivityId = str1;
      this.mAclText = paramCursor.getString(3);
      this.mCreationSource = paramCursor.getString(14);
      this.mAuthorId = str2;
      this.mAuthorName = paramCursor.getString(5);
      if (this.mAuthorName == null)
      {
        this.mAuthorName = "";
        Log.w("StreamOneUp", "===> Author name was null for gaia id: " + str2);
      }
      this.mAuthorImage = new ClickableAvatar(this, str2, EsAvatarData.uncompressAvatarUrl(paramCursor.getString(6)), this.mAuthorName, this.mOneUpListener, 2);
      this.mClickableItems.add(this.mAuthorImage);
      this.mDate = Dates.getAbbreviatedRelativeTimeSpanString(localContext, paramCursor.getLong(10)).toString();
      if (1 != paramCursor.getInt(11))
        break label1120;
      bool2 = true;
      this.mEdited = bool2;
      if (paramCursor.getInt(19) == 0)
        break label1126;
      this.mMuteState = localResources.getString(R.string.stream_one_up_is_muted);
      String str3 = paramCursor.getString(24);
      String str4 = paramCursor.getString(25);
      if ((!TextUtils.isEmpty(str3)) && (!TextUtils.isEmpty(str4)))
      {
        String str15 = localResources.getString(R.string.stream_one_up_reshare_header, new Object[] { str4 });
        Locale localLocale = localResources.getConfiguration().locale;
        String str16 = str15.toUpperCase(localLocale);
        String str17 = str4.toUpperCase(localLocale);
        ClickableStaticLayout.StateURLSpan localStateURLSpan5 = new ClickableStaticLayout.StateURLSpan(Intents.makeProfileUrl(str3));
        int n = str16.indexOf(str17);
        int i1 = n + str17.length();
        this.mReshareHeader = new SpannableStringBuilder(str16);
        this.mReshareHeader.setSpan(localStateURLSpan5, n, i1, 33);
      }
      String str5 = paramCursor.getString(22);
      if (!TextUtils.isEmpty(str5))
        this.mAnnotation = ClickableStaticLayout.buildStateSpans(str5);
      if ((0x2000 & l) == 0L)
        break label1172;
      byte[] arrayOfByte7 = paramCursor.getBlob(31);
      if (arrayOfByte7 == null)
        break label1166;
      if (!DbEmbedHangout.deserialize(arrayOfByte7).isInProgress())
        break label1134;
      int m = R.string.card_hangout_state_active;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = this.mAuthorName;
      str6 = localResources.getString(m, arrayOfObject2);
      if (!TextUtils.isEmpty(str6))
        this.mTitle = ClickableStaticLayout.buildStateSpans(str6);
      this.mSourceAppPackages.clear();
      boolean bool3 = (0x8000 & l) < 0L;
      i = 0;
      if (bool3)
      {
        byte[] arrayOfByte6 = paramCursor.getBlob(26);
        i = 0;
        if (arrayOfByte6 != null)
        {
          DbEmbedDeepLink localDbEmbedDeepLink = DbEmbedDeepLink.deserialize(arrayOfByte6);
          this.mSourceAppData = localDbEmbedDeepLink.getDeepLinkId();
          this.mSourceAppPackages.addAll(localDbEmbedDeepLink.getClientPackageNames());
          boolean bool4 = this.mSourceAppPackages.isEmpty();
          i = 0;
          if (!bool4)
            i = 1;
        }
      }
      byte[] arrayOfByte1 = paramCursor.getBlob(28);
      if (arrayOfByte1 == null)
        break label1204;
      DbEmbedMedia localDbEmbedMedia = DbEmbedMedia.deserialize(arrayOfByte1);
      String str12 = localDbEmbedMedia.getTitle();
      if (!TextUtils.isEmpty(str12))
      {
        if (TextUtils.isEmpty(localDbEmbedMedia.getContentUrl()))
          break label1185;
        str14 = localDbEmbedMedia.getContentUrl();
        if (str14 != null)
          str12 = "<a href=\"" + str14 + "\">" + str12 + "</a>";
        this.mLinkedHeader = ClickableStaticLayout.buildStateSpans(str12);
      }
      String str13 = localDbEmbedMedia.getDescription();
      if (!TextUtils.isEmpty(str13))
        this.mLinkedBody = ClickableStaticLayout.buildStateSpans(str13);
      byte[] arrayOfByte3 = paramCursor.getBlob(8);
      if (arrayOfByte3 != null)
        this.mPlusOneData = DbPlusOneData.deserialize(arrayOfByte3);
      if ((0x10000 & l) != 0L)
      {
        byte[] arrayOfByte5 = paramCursor.getBlob(30);
        this.mPlaceReview = ((PlaceReview)PlaceReviewJson.getInstance().fromByteArray(arrayOfByte5));
      }
      byte[] arrayOfByte4 = paramCursor.getBlob(9);
      if (arrayOfByte4 == null)
        break label1482;
      this.mLocationData = DbLocation.deserialize(arrayOfByte4);
      if (this.mLocationIcon == null)
      {
        if (!this.mIsCheckin)
          break label1474;
        localBitmap = sCheckInIconBitmap;
        label861: this.mLocationIcon = localBitmap;
      }
      String str9 = this.mLocationData.getLocationName();
      ClickableStaticLayout.StateURLSpan localStateURLSpan3 = new ClickableStaticLayout.StateURLSpan(str9);
      this.mLocation = new SpannableStringBuilder(str9);
      this.mLocation.setSpan(localStateURLSpan3, 0, str9.length(), 33);
    }
    while (true)
    {
      if (Build.VERSION.SDK_INT < 16)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mAuthorName);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mDate);
        if (this.mEdited)
          AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, localContext.getString(R.string.stream_one_up_is_edited));
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mCreationSource);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mAclText);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mMuteState);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mAnnotation);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mTitle);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mLinkedHeader);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mLinkedBody);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mSkyjamHeader);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mSkyjamSubheader1);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mSkyjamSubheader2);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mReshareHeader);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, null);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mLocation);
        setContentDescription(localStringBuilder.toString());
        setFocusable(true);
      }
      bindResources();
      invalidate();
      requestLayout();
      return;
      bool1 = false;
      break;
      label1120: bool2 = false;
      break label248;
      label1126: this.mMuteState = null;
      break label276;
      label1134: int k = R.string.card_hangout_state_inactive;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mAuthorName;
      str6 = localResources.getString(k, arrayOfObject1);
      break label513;
      label1166: str6 = null;
      break label513;
      label1172: str6 = paramCursor.getString(23);
      break label513;
      label1185: if (i != 0)
      {
        str14 = "";
        break label683;
      }
      str14 = null;
      break label683;
      label1204: byte[] arrayOfByte2 = paramCursor.getBlob(29);
      if (arrayOfByte2 == null)
        break label758;
      DbEmbedSkyjam localDbEmbedSkyjam = DbEmbedSkyjam.deserialize(arrayOfByte2);
      String str7 = Uri.decode(localDbEmbedSkyjam.getMarketUrl());
      SpannableStringBuilder localSpannableStringBuilder1 = new SpannableStringBuilder();
      SpannableStringBuilder localSpannableStringBuilder2;
      if (localDbEmbedSkyjam.isAlbum())
      {
        localSpannableStringBuilder1.append(localDbEmbedSkyjam.getAlbum());
        String str10 = localDbEmbedSkyjam.getPreviewUrl();
        int j = str10.indexOf("https://");
        if (j < 0)
          j = str10.indexOf("https://");
        if (j >= 0)
        {
          String str11 = str10.substring(j);
          localSpannableStringBuilder2 = new SpannableStringBuilder(getResources().getString(R.string.skyjam_listen));
          ClickableStaticLayout.StateURLSpan localStateURLSpan4 = new ClickableStaticLayout.StateURLSpan("skyjam:listen:" + str11);
          localSpannableStringBuilder2.setSpan(localStateURLSpan4, 0, localSpannableStringBuilder2.length(), 33);
        }
      }
      for (this.mSkyjamSubheader2 = localSpannableStringBuilder2; ; this.mSkyjamSubheader2 = new SpannableString(localDbEmbedSkyjam.getAlbum()))
      {
        ClickableStaticLayout.StateURLSpan localStateURLSpan1 = new ClickableStaticLayout.StateURLSpan("skyjam:buy:" + str7);
        localSpannableStringBuilder1.setSpan(localStateURLSpan1, 0, localSpannableStringBuilder1.length(), 33);
        this.mSkyjamHeader = localSpannableStringBuilder1;
        this.mSkyjamSubheader1 = new SpannableString(localDbEmbedSkyjam.getArtist());
        break;
        localSpannableStringBuilder1.append(localDbEmbedSkyjam.getSong());
      }
      label1474: localBitmap = sLocationIconBitmap;
      break label861;
      label1482: if ((this.mPlaceReview != null) && (!TextUtils.isEmpty(this.mPlaceReview.name)))
      {
        this.mLocationIcon = sLocationIconBitmap;
        String str8 = this.mPlaceReview.name;
        ClickableStaticLayout.StateURLSpan localStateURLSpan2 = new ClickableStaticLayout.StateURLSpan(str8);
        this.mLocation = new SpannableStringBuilder(str8);
        this.mLocation.setSpan(localStateURLSpan2, 0, str8.length(), 33);
      }
    }
  }

  public final void bindResources()
  {
    if (this.mAuthorImage != null)
      this.mAuthorImage.bindResources();
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 1;
    int k = (int)paramMotionEvent.getX();
    int m = (int)paramMotionEvent.getY();
    switch (paramMotionEvent.getAction())
    {
    case 2:
    default:
      i = 0;
    case 0:
    case 1:
    case 3:
    }
    while (true)
    {
      return i;
      Iterator localIterator2 = this.mClickableItems.iterator();
      while (localIterator2.hasNext())
      {
        ClickableItem localClickableItem = (ClickableItem)localIterator2.next();
        if (localClickableItem.handleEvent(k, m, 0))
        {
          this.mCurrentClickableItem = localClickableItem;
          invalidate();
        }
      }
      this.mCurrentClickableItem = null;
      Iterator localIterator1 = this.mClickableItems.iterator();
      while (localIterator1.hasNext())
        ((ClickableItem)localIterator1.next()).handleEvent(k, m, i);
      invalidate();
      int j = 0;
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
      }
    }
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    bindResources();
    updateAccessibility();
  }

  public final void onClickableButtonListenerClick(ClickableButton paramClickableButton)
  {
    int i;
    if ((this.mOneUpListener != null) && (paramClickableButton == this.mPlusOneButton))
    {
      this.mOneUpListener.onPlusOne(this.mActivityId, this.mPlusOneData);
      if (AccessibilityUtils.isAccessibilityEnabled(getContext()))
      {
        if ((this.mPlusOneData == null) || (!this.mPlusOneData.isPlusOnedByMe()))
          break label122;
        i = 1;
        if (i == 0)
          break label127;
      }
    }
    label122: label127: for (int j = R.string.plus_one_removed_confirmation; ; j = R.string.plus_one_added_confirmation)
    {
      AccessibilityEvent localAccessibilityEvent = AccessibilityEvent.obtain(16384);
      localAccessibilityEvent.getText().add(getResources().getString(j));
      onInitializeAccessibilityEvent(localAccessibilityEvent);
      localAccessibilityEvent.setContentDescription(null);
      getParent().requestSendAccessibilityEvent(this, localAccessibilityEvent);
      return;
      i = 0;
      break;
    }
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    unbindResources();
    if (this.mTouchExplorer != null)
    {
      this.mTouchExplorer.uninstall();
      this.mTouchExplorer = null;
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    paramCanvas.drawRect(0.0F, this.mBackgroundOffset, getWidth(), getHeight(), sBackgroundPaint);
    if (this.mDateSourceAclLayout != null)
      if (this.mAuthorImage.getBitmap() == null)
        break label1101;
    label1101: for (Bitmap localBitmap = this.mAuthorImage.getBitmap(); ; localBitmap = sDefaultAvatarBitmap)
    {
      paramCanvas.drawBitmap(localBitmap, null, this.mAuthorImage.getRect(), sResizePaint);
      paramCanvas.drawBitmap(sAvatarOverlayBitmap, null, this.mAuthorImage.getRect(), sResizePaint);
      if (this.mAuthorImage.isClicked())
        this.mAuthorImage.drawSelectionRect(paramCanvas);
      this.mPlusOneButton.draw(paramCanvas);
      int i22 = this.mDateSourceAclLayout.getLeft();
      int i23 = this.mDateSourceAclLayout.getTop();
      paramCanvas.translate(i22, i23);
      this.mDateSourceAclLayout.draw(paramCanvas);
      paramCanvas.translate(-i22, -i23);
      int i24 = this.mAuthorLayout.getLeft();
      int i25 = this.mAuthorLayout.getTop();
      paramCanvas.translate(i24, i25);
      this.mAuthorLayout.draw(paramCanvas);
      paramCanvas.translate(-i24, -i25);
      if (this.mAnnotationLayout != null)
      {
        int i20 = this.mAnnotationLayout.getLeft();
        int i21 = this.mAnnotationLayout.getTop();
        paramCanvas.translate(i20, i21);
        this.mAnnotationLayout.draw(paramCanvas);
        paramCanvas.translate(-i20, -i21);
      }
      if ((this.mReshareHeaderLayout != null) || (this.mReshareBodyLayout != null))
      {
        paramCanvas.drawRoundRect(this.mReshareContentBorder, sReshareBorderRadius, sReshareBorderRadius, sReshareBackgroundPaint);
        paramCanvas.drawRoundRect(this.mReshareContentBorder, sReshareBorderRadius, sReshareBorderRadius, sReshareBorderPaint);
        if (this.mReshareHeaderLayout != null)
        {
          int i18 = this.mReshareHeaderLayout.getLeft();
          int i19 = this.mReshareHeaderLayout.getTop();
          paramCanvas.translate(i18, i19);
          this.mReshareHeaderLayout.draw(paramCanvas);
          paramCanvas.translate(-i18, -i19);
        }
        if (this.mReshareBodyLayout != null)
        {
          int i16 = this.mReshareBodyLayout.getLeft();
          int i17 = this.mReshareBodyLayout.getTop();
          paramCanvas.translate(i16, i17);
          this.mReshareBodyLayout.draw(paramCanvas);
          paramCanvas.translate(-i16, -i17);
        }
      }
      if (this.mTitleLayout != null)
      {
        int i14 = this.mTitleLayout.getLeft();
        int i15 = this.mTitleLayout.getTop();
        paramCanvas.translate(i14, i15);
        this.mTitleLayout.draw(paramCanvas);
        paramCanvas.translate(-i14, -i15);
      }
      if (this.mPlaceReviewDividerRect != null)
      {
        int i12 = this.mPlaceReviewDividerRect.left;
        int i13 = this.mPlaceReviewDividerRect.top;
        paramCanvas.drawLine(i12, i13, this.mPlaceReviewDividerRect.right, this.mPlaceReviewDividerRect.bottom, sPlaceReviewDividerPaint);
      }
      if (this.mLocationLayout != null)
      {
        paramCanvas.drawBitmap(this.mLocationIcon, null, this.mLocationIconRect, null);
        int i10 = this.mLocationLayout.getLeft();
        int i11 = this.mLocationLayout.getTop();
        paramCanvas.translate(i10, i11);
        this.mLocationLayout.draw(paramCanvas);
        paramCanvas.translate(-i10, -i11);
      }
      if ((this.mLinkedHeaderLayout != null) || (this.mLinkedBodyLayout != null))
      {
        paramCanvas.drawRoundRect(this.mLinkedContentBorder, sLinkedBorderRadius, sLinkedBorderRadius, sLinkedBackgroundPaint);
        paramCanvas.drawRoundRect(this.mLinkedContentBorder, sLinkedBorderRadius, sLinkedBorderRadius, sLinkedBorderPaint);
        if (this.mLinkedHeaderLayout != null)
        {
          paramCanvas.drawBitmap(sLinkedIconBitmap, null, this.mLinkedIconRect, null);
          int i8 = this.mLinkedHeaderLayout.getLeft();
          int i9 = this.mLinkedHeaderLayout.getTop();
          paramCanvas.translate(i8, i9);
          this.mLinkedHeaderLayout.draw(paramCanvas);
          paramCanvas.translate(-i8, -i9);
        }
        if (this.mLinkedBodyLayout != null)
        {
          int i6 = this.mLinkedBodyLayout.getLeft();
          int i7 = this.mLinkedBodyLayout.getTop();
          paramCanvas.translate(i6, i7);
          this.mLinkedBodyLayout.draw(paramCanvas);
          paramCanvas.translate(-i6, -i7);
        }
      }
      if (this.mSkyjamHeader != null)
      {
        paramCanvas.drawRoundRect(this.mSkyjamContentBorder, sLinkedBorderRadius, sLinkedBorderRadius, sLinkedBackgroundPaint);
        paramCanvas.drawRoundRect(this.mSkyjamContentBorder, sLinkedBorderRadius, sLinkedBorderRadius, sLinkedBorderPaint);
        if (this.mSkyjamHeaderLayout != null)
        {
          paramCanvas.drawBitmap(sSkyjamIconBitmap, null, this.mSkyjamIconRect, null);
          int i4 = this.mSkyjamHeaderLayout.getLeft();
          int i5 = this.mSkyjamHeaderLayout.getTop();
          paramCanvas.translate(i4, i5);
          this.mSkyjamHeaderLayout.draw(paramCanvas);
          paramCanvas.translate(-i4, -i5);
        }
        if (this.mSkyjamSubheader1Layout != null)
        {
          int i2 = this.mSkyjamSubheader1Layout.getLeft();
          int i3 = this.mSkyjamSubheader1Layout.getTop();
          paramCanvas.translate(i2, i3);
          this.mSkyjamSubheader1Layout.draw(paramCanvas);
          paramCanvas.translate(-i2, -i3);
        }
        if (this.mSkyjamSubheader2Layout != null)
        {
          int n = this.mSkyjamSubheader2Layout.getLeft();
          int i1 = this.mSkyjamSubheader2Layout.getTop();
          paramCanvas.translate(n, i1);
          this.mSkyjamSubheader2Layout.draw(paramCanvas);
          paramCanvas.translate(-n, -i1);
        }
      }
      if (this.mPlaceReviewAspectsLayout != null)
      {
        int k = this.mPlaceReviewAspectsLayout.getLeft();
        int m = this.mPlaceReviewAspectsLayout.getTop();
        paramCanvas.translate(k, m);
        this.mPlaceReviewAspectsLayout.draw(paramCanvas);
        paramCanvas.translate(-k, -m);
      }
      if (this.mPlaceReviewBodyLayout != null)
      {
        int i = this.mPlaceReviewBodyLayout.getLeft();
        int j = this.mPlaceReviewBodyLayout.getTop();
        paramCanvas.translate(i, j);
        this.mPlaceReviewBodyLayout.draw(paramCanvas);
        paramCanvas.translate(-i, -j);
      }
      updateAccessibility();
      return;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = getPaddingLeft() + sMarginLeft;
    int j = getPaddingTop() - sAvatarMarginTop;
    int k = getMeasuredWidth();
    int m = k - i - getPaddingRight() - sMarginRight;
    this.mBackgroundOffset = j;
    Context localContext = getContext();
    int n = i + sAvatarMarginLeft;
    int i1 = j + sAvatarMarginTop;
    this.mAuthorImage.setRect(n, i1, n + sAvatarSize, i1 + sAvatarSize);
    int i2;
    int i3;
    label126: TextPaint localTextPaint;
    label209: NinePatchDrawable localNinePatchDrawable1;
    label219: NinePatchDrawable localNinePatchDrawable2;
    label229: SpannableStringBuilder localSpannableStringBuilder;
    int i28;
    label497: String str2;
    label511: Object localObject;
    label519: label579: label607: int i14;
    label790: int i15;
    if ((this.mPlusOneData != null) && (this.mPlusOneData.isPlusOnedByMe()))
    {
      i2 = 1;
      if (this.mPlusOneData != null)
        break label877;
      i3 = 1;
      Resources localResources = getResources();
      int i4 = R.string.stream_plus_one_count_with_plus;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(Math.max(i3, 1));
      String str1 = localResources.getString(i4, arrayOfObject);
      int i5 = i + m - sPlusOneButtonMarginRight;
      int i6 = j + sNameMarginTop;
      this.mClickableItems.remove(this.mPlusOneButton);
      if (i2 == 0)
        break label889;
      localTextPaint = PlusBarUtils.sPlusOnedTextPaint;
      if (i2 == 0)
        break label897;
      localNinePatchDrawable1 = PlusBarUtils.sPlusOnedDrawable;
      if (i2 == 0)
        break label905;
      localNinePatchDrawable2 = PlusBarUtils.sPlusOnedPressedDrawable;
      this.mPlusOneButton = new ClickableButton(localContext, str1, localTextPaint, localNinePatchDrawable1, localNinePatchDrawable2, this, 0, 0);
      int i7 = i5 - this.mPlusOneButton.getRect().width();
      this.mPlusOneButton.getRect().offsetTo(i7, i6);
      this.mClickableItems.add(this.mPlusOneButton);
      int i8 = i + sAvatarMarginLeft + sAvatarSize + sAvatarMarginRight;
      int i9 = j + sNameMarginTop;
      int i10 = m - i8 - this.mPlusOneButton.getRect().width() - sPlusOneButtonMarginLeft;
      this.mAuthorLayout = new PositionedStaticLayout(TextUtils.ellipsize(this.mAuthorName, sNamePaint, i10, TextUtils.TruncateAt.END), sNamePaint, i10, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false);
      this.mAuthorLayout.setPosition(i8, i9);
      Locale localLocale = getContext().getResources().getConfiguration().locale;
      localSpannableStringBuilder = new SpannableStringBuilder(this.mDate.toUpperCase(localLocale));
      if (this.mEdited)
      {
        localSpannableStringBuilder.append("   ");
        localSpannableStringBuilder.append(localContext.getString(R.string.stream_one_up_is_edited));
      }
      localSpannableStringBuilder.append("   ");
      localSpannableStringBuilder.append(this.mCreationSource);
      if (this.mDbEmbedSquare == null)
        break label954;
      if (TextUtils.isEmpty(this.mDbEmbedSquare.getAboutSquareName()))
        break label913;
      i28 = 1;
      if (i28 == 0)
        break label919;
      str2 = this.mDbEmbedSquare.getAboutSquareName();
      if (i28 == 0)
        break label931;
      localObject = null;
      if (!TextUtils.isEmpty(str2))
      {
        localSpannableStringBuilder.append("   ");
        int i29 = localSpannableStringBuilder.length();
        if (TextUtils.isEmpty((CharSequence)localObject))
          break label943;
        localSpannableStringBuilder.append(localContext.getString(R.string.square_oneup_acl_name_and_stream, new Object[] { str2, localObject }));
        int i30 = localSpannableStringBuilder.length();
        localSpannableStringBuilder.setSpan(new ClickableStaticLayout.StateURLSpan("square"), i29, i30, 33);
      }
      if (!TextUtils.isEmpty(this.mMuteState))
      {
        localSpannableStringBuilder.append("   ");
        int i26 = localSpannableStringBuilder.length();
        localSpannableStringBuilder.append(this.mMuteState);
        int i27 = localSpannableStringBuilder.length();
        localSpannableStringBuilder.setSpan(new ForegroundColorSpan(sIsMuteColor), i26, i27, 33);
      }
      localSpannableStringBuilder.append(" ");
      int i13 = i9 + this.mAuthorLayout.getHeight();
      this.mClickableItems.remove(this.mDateSourceAclLayout);
      this.mDateSourceAclLayout = new ClickableStaticLayout(localSpannableStringBuilder, sDatePaint, i10, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false, this.mAclClickListener);
      this.mDateSourceAclLayout.setPosition(i8, i13);
      this.mClickableItems.add(this.mDateSourceAclLayout);
      i14 = Math.max(sAvatarSize, this.mDateSourceAclLayout.getBottom()) + sTitleMarginBottom;
      if (!TextUtils.isEmpty(this.mAnnotation))
        break label1022;
      if (this.mReshareHeader == null)
        break label1104;
      i15 = 1;
      label800: if (i15 == 0)
        break label1652;
      if ((!TextUtils.isEmpty(null)) || (!TextUtils.isEmpty(this.mReshareHeader)))
        break label1110;
    }
    while (true)
    {
      setMeasuredDimension(k, i14 + sMarginBottom + getPaddingBottom());
      if (this.mOnMeasuredListener != null)
        this.mOnMeasuredListener.onMeasured(this);
      if (this.mTouchExplorer != null)
        this.mTouchExplorer.invalidateItemCache();
      return;
      i2 = 0;
      break;
      label877: i3 = this.mPlusOneData.getCount();
      break label126;
      label889: localTextPaint = PlusBarUtils.sNotPlusOnedTextPaint;
      break label209;
      label897: localNinePatchDrawable1 = PlusBarUtils.sButtonDrawable;
      break label219;
      label905: localNinePatchDrawable2 = PlusBarUtils.sButtonPressedDrawable;
      break label229;
      label913: i28 = 0;
      break label497;
      label919: str2 = this.mDbEmbedSquare.getSquareName();
      break label511;
      label931: localObject = this.mDbEmbedSquare.getSquareStreamName();
      break label519;
      label943: localSpannableStringBuilder.append(str2);
      break label579;
      label954: if (TextUtils.isEmpty(this.mAclText))
        break label607;
      localSpannableStringBuilder.append("   ");
      int i11 = localSpannableStringBuilder.length();
      localSpannableStringBuilder.append(this.mAclText);
      int i12 = localSpannableStringBuilder.length();
      localSpannableStringBuilder.setSpan(new ClickableStaticLayout.StateURLSpan(this.mAclText), i11, i12, 33);
      break label607;
      label1022: this.mClickableItems.remove(this.mAnnotationLayout);
      this.mAnnotationLayout = new ClickableStaticLayout(this.mAnnotation, sContentPaint, m, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false, this.mOneUpListener);
      this.mAnnotationLayout.setPosition(i, i14);
      this.mClickableItems.add(this.mAnnotationLayout);
      i14 = this.mAnnotationLayout.getBottom();
      break label790;
      label1104: i15 = 0;
      break label800;
      label1110: int i16 = m - 2 * sReshareInnerMargin;
      int i17 = i + sReshareInnerMargin;
      int i18 = i14 + sReshareInnerMargin;
      int i21;
      int i20;
      int i22;
      int i23;
      int i19;
      if (!TextUtils.isEmpty(this.mReshareHeader))
      {
        this.mClickableItems.remove(this.mReshareHeaderLayout);
        this.mReshareHeaderLayout = new ClickableStaticLayout(this.mReshareHeader, sReshareHeaderPaint, i16, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false, this.mOneUpListener);
        this.mReshareHeaderLayout.setPosition(i17, i18);
        this.mClickableItems.add(this.mReshareHeaderLayout);
        int i24 = this.mReshareHeaderLayout.getLeft() - sReshareInnerMargin;
        int i25 = this.mReshareHeaderLayout.getTop() - sReshareInnerMargin;
        i21 = this.mReshareHeaderLayout.getRight() + sReshareInnerMargin;
        i20 = this.mReshareHeaderLayout.getBottom() + sReshareInnerMargin;
        i22 = i25;
        i23 = i24;
        i19 = i20;
      }
      while (true)
      {
        if (!TextUtils.isEmpty(null))
        {
          this.mClickableItems.remove(this.mReshareBodyLayout);
          this.mReshareBodyLayout = new ClickableStaticLayout(null, sReshareBodyPaint, i16, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false, this.mOneUpListener);
          this.mReshareBodyLayout.setPosition(i17, i19);
          this.mClickableItems.add(this.mReshareBodyLayout);
          if (i23 == 0)
          {
            i23 = this.mReshareBodyLayout.getLeft() - sReshareInnerMargin;
            i22 = this.mReshareBodyLayout.getTop() - sReshareInnerMargin;
          }
          i21 = this.mReshareBodyLayout.getRight() + sReshareInnerMargin;
          i20 = this.mReshareBodyLayout.getBottom() + sReshareInnerMargin;
          i19 = i20;
        }
        if (!TextUtils.isEmpty(this.mTitle))
        {
          i20 = measureAndLayoutTitle(i17, i19, i16);
          i19 = i20;
        }
        if (this.mPlaceReview != null)
        {
          i20 = measureAndLayoutPlaceReviewDivider(i17, i19, i16);
          i19 = i20;
        }
        if (!TextUtils.isEmpty(this.mLocation))
        {
          i20 = measureAndLayoutLocation(i17, i19, i16);
          i19 = i20;
        }
        if ((!TextUtils.isEmpty(this.mLinkedHeader)) || (!TextUtils.isEmpty(this.mLinkedBody)))
        {
          i19 = measureAndLayoutLinkedContent(i17, i19, i16);
          i20 = i19 + sReshareInnerMargin;
        }
        if (!TextUtils.isEmpty(this.mSkyjamHeader))
        {
          i19 = measureAndLayoutSkyjamContent(i17, i19, i16);
          i20 = i19 + sReshareInnerMargin;
        }
        if (this.mPlaceReview != null)
          i20 = measureAndLayoutPlaceReviewContent(i17, i19, i16) + sReshareInnerMargin;
        this.mReshareContentBorder = new RectF(i23, i22, i21, i20);
        i14 = i20;
        break;
        this.mClickableItems.remove(this.mReshareHeaderLayout);
        this.mReshareHeaderLayout = null;
        i19 = i18;
        i20 = 0;
        i21 = 0;
        i22 = 0;
        i23 = 0;
      }
      label1652: i14 = measureAndLayoutPlaceReviewContent(i, measureAndLayoutSkyjamContent(i, measureAndLayoutLinkedContent(i, measureAndLayoutLocation(i, measureAndLayoutPlaceReviewDivider(i, measureAndLayoutTitle(i, i14, m), m), m), m), m), m);
    }
  }

  public void onRecycle()
  {
    clearLayoutState();
    this.mOneUpListener = null;
    this.mIsCheckin = false;
  }

  public final void onResourceStatusChange$1574fca0(Resource paramResource)
  {
  }

  public void setOneUpClickListener(OneUpListener paramOneUpListener)
  {
    this.mOneUpListener = paramOneUpListener;
  }

  public final void unbindResources()
  {
    if (this.mAuthorImage != null)
      this.mAuthorImage.unbindResources();
  }

  private final class OneUpActivityTouchExplorer extends TouchExplorationHelper<ClickableItem>
  {
    private boolean mIsItemCacheStale = true;
    private ArrayList<ClickableItem> mItemCache = new ArrayList(StreamOneUpActivityView.this.mClickableItems.size());

    public OneUpActivityTouchExplorer(Context arg2)
    {
      super();
    }

    private void refreshItemCache()
    {
      if (this.mIsItemCacheStale)
      {
        this.mItemCache.clear();
        this.mItemCache.addAll(StreamOneUpActivityView.this.mClickableItems);
        Collections.sort(this.mItemCache, ClickableItem.sComparator);
        this.mIsItemCacheStale = false;
      }
    }

    protected final void getVisibleItems(List<ClickableItem> paramList)
    {
      refreshItemCache();
      int i = 0;
      int j = this.mItemCache.size();
      while (i < j)
      {
        paramList.add((ClickableItem)this.mItemCache.get(i));
        i++;
      }
    }

    public final void invalidateItemCache()
    {
      this.mIsItemCacheStale = true;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.StreamOneUpActivityView
 * JD-Core Version:    0.6.2
 */