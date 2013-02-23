package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.DbEmbedSquare;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.DbPlusOneData;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.content.EsPostsData;
import com.google.android.apps.plus.util.AccessibilityUtils;
import com.google.android.apps.plus.util.BackgroundPatternUtils;
import com.google.android.apps.plus.util.Dates;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.PlusBarUtils;
import com.google.android.apps.plus.util.TextPaintUtils;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.PlusEventJson;

public abstract class StreamCardView extends CardView
  implements ClickableButton.ClickableButtonListener
{
  protected static TextPaint sAttributionTextPaint;
  protected static Bitmap sAuthorBitmap;
  protected static int sAuthorNameYOffset;
  protected static TextPaint sAutoTextPaint;
  protected static int sAvatarSize;
  protected static Bitmap sCommentsBitmap;
  protected static Bitmap sCommunityBitmap;
  protected static int sContentXPadding;
  protected static int sContentYPadding;
  private static final Interpolator sDampingInterpolator = new Interpolator()
  {
    public final float getInterpolation(float paramAnonymousFloat)
    {
      double d = 3.141592653589793D * (4.0F * paramAnonymousFloat - 1.0F);
      return (float)(Math.sin(d) / d);
    }
  };
  protected static Bitmap sDeepLinkHintBitmap;
  protected static Paint sGraySpamBackgroundPaint;
  protected static int sGraySpamIconPadding;
  protected static TextPaint sGraySpamTextPaint;
  protected static Bitmap sGraySpamWarningBitmap;
  protected static float sMediaCardBigHeightPercentage;
  protected static float sMediaCardHeightPercentage;
  protected static NinePatchDrawable sMediaShadowDrawable;
  protected static Paint sMediaTopAreaBackgroundPaint;
  protected static TextPaint sNameTextPaint;
  protected static TextPaint sRelativeTimeTextPaint;
  protected static int sRelativeTimeYOffset;
  protected static Bitmap sReshareBitmap;
  protected static Bitmap sSquareBitmap;
  private static boolean sStreamCardViewInitialized;
  protected static Bitmap[] sTagAlbumBitmaps;
  protected static int sTagBackgroundYPadding;
  protected static Drawable sTagDrawable;
  protected static Bitmap[] sTagHangoutBitmaps;
  protected static int sTagIconXPadding;
  protected static int sTagIconYPaddingCheckin;
  protected static int sTagIconYPaddingLocation;
  protected static int sTagIconYPaddingWithPhoto;
  protected static Bitmap[] sTagLinkBitmaps;
  protected static Bitmap[] sTagLocationBitmaps;
  protected static Bitmap[] sTagMusicBitmaps;
  protected static TextPaint sTagTextPaint;
  protected static int sTagTextXPadding;
  protected static Bitmap[] sTagVideoBitmaps;
  protected static int sTagYOffset;
  protected static BitmapDrawable sTiledStageDrawable;
  protected static Bitmap sWhatsHotBitmap;
  protected String mActivityId;
  protected CharSequence mAttribution;
  protected StaticLayout mAttributionLayout;
  protected String mAuthorAvatarUrl;
  protected String mAuthorGaiaId;
  protected ClickableAvatar mAuthorImage;
  protected String mAuthorName;
  protected StaticLayout mAuthorNameLayout;
  protected int mAutoText;
  protected StaticLayout mAutoTextLayout;
  protected boolean mCanReshare;
  protected ClickableButton mCommentsButton;
  protected CharSequence mContent;
  protected StaticLayout mContentLayout;
  protected Bitmap mCornerIcon;
  protected String mEventId;
  protected String mEventOwnerId;
  protected CharSequence mFillerContent;
  protected StaticLayout mFillerContentLayout;
  protected StaticLayout mGraySpamLayout;
  protected boolean mInvisiblePlusOneButton;
  protected boolean mIsGraySpam;
  protected boolean mIsLimited;
  protected ClickableButton mOverridePlusOnedButton;
  protected boolean mOverridePlusOnedButtonDisplay;
  protected ClickableButton mPlusOneButton;
  protected DbPlusOneData mPlusOneData;
  protected String mRelativeTime;
  protected StaticLayout mRelativeTimeLayout;
  protected ClickableButton mReshareButton;
  private Runnable mShakeAnimation;
  protected String mSquareIdForOneUp;
  protected boolean mSquareMode;
  protected StreamMediaClickListener mStreamMediaClickListener;
  protected StreamPlusBarClickListener mStreamPlusBarClickListener;
  protected CharSequence mTag;
  protected Drawable mTagDrawableInstance;
  protected Bitmap mTagIcon;
  protected StaticLayout mTagLayout;
  protected int mTotalComments;
  private ViewedListener mViewedListener;
  protected boolean mViewerIsSquareAdmin;

  public StreamCardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public StreamCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    if (!sStreamCardViewInitialized)
    {
      sStreamCardViewInitialized = true;
      sAuthorBitmap = EsAvatarData.getMediumDefaultAvatar(paramContext, true);
      Bitmap[] arrayOfBitmap1 = new Bitmap[2];
      arrayOfBitmap1[0] = ImageUtils.decodeResource(localResources, R.drawable.ic_metadata_album);
      arrayOfBitmap1[1] = ImageUtils.decodeResource(localResources, R.drawable.ic_album_blue);
      sTagAlbumBitmaps = arrayOfBitmap1;
      Bitmap[] arrayOfBitmap2 = new Bitmap[2];
      arrayOfBitmap2[0] = ImageUtils.decodeResource(localResources, R.drawable.ic_metadata_link);
      arrayOfBitmap2[1] = ImageUtils.decodeResource(localResources, R.drawable.ic_link_blue);
      sTagLinkBitmaps = arrayOfBitmap2;
      Bitmap[] arrayOfBitmap3 = new Bitmap[2];
      arrayOfBitmap3[0] = ImageUtils.decodeResource(localResources, R.drawable.ic_metadata_location);
      arrayOfBitmap3[1] = ImageUtils.decodeResource(localResources, R.drawable.icn_location_card);
      sTagLocationBitmaps = arrayOfBitmap3;
      Bitmap[] arrayOfBitmap4 = new Bitmap[2];
      arrayOfBitmap4[0] = ImageUtils.decodeResource(localResources, R.drawable.ic_metadata_music);
      arrayOfBitmap4[1] = ImageUtils.decodeResource(localResources, R.drawable.ic_music_blue);
      sTagMusicBitmaps = arrayOfBitmap4;
      Bitmap[] arrayOfBitmap5 = new Bitmap[2];
      arrayOfBitmap5[0] = ImageUtils.decodeResource(localResources, R.drawable.ic_metadata_video);
      arrayOfBitmap5[1] = ImageUtils.decodeResource(localResources, R.drawable.ic_video_blue);
      sTagVideoBitmaps = arrayOfBitmap5;
      Bitmap[] arrayOfBitmap6 = new Bitmap[2];
      arrayOfBitmap6[0] = ImageUtils.decodeResource(localResources, R.drawable.ic_metadata_hangouts);
      arrayOfBitmap6[1] = null;
      sTagHangoutBitmaps = arrayOfBitmap6;
      sCommentsBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_comments);
      sReshareBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_menu_reshare);
      sCommunityBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_community);
      sWhatsHotBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_whatshot);
      sDeepLinkHintBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_open_external_link);
      sSquareBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_community_share);
      sGraySpamWarningBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_error_white);
      sTagDrawable = localResources.getDrawable(R.drawable.card_tag);
      sMediaShadowDrawable = (NinePatchDrawable)localResources.getDrawable(R.drawable.taco_media_shadow);
      BitmapDrawable localBitmapDrawable = (BitmapDrawable)localResources.getDrawable(R.drawable.bg_taco_mediapattern);
      sTiledStageDrawable = localBitmapDrawable;
      localBitmapDrawable.setTileModeX(Shader.TileMode.REPEAT);
      sTiledStageDrawable.setTileModeY(Shader.TileMode.REPEAT);
      TextPaint localTextPaint1 = new TextPaint();
      sNameTextPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sNameTextPaint.setColor(localResources.getColor(R.color.card_author_name));
      sNameTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sNameTextPaint.setTextSize(localResources.getDimension(R.dimen.card_author_name_text_size));
      TextPaintUtils.registerTextPaint(sNameTextPaint, R.dimen.card_author_name_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sRelativeTimeTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sRelativeTimeTextPaint.setColor(localResources.getColor(R.color.card_relative_time_text));
      sRelativeTimeTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sRelativeTimeTextPaint.setTextSize(localResources.getDimension(R.dimen.card_relative_time_text_size));
      TextPaintUtils.registerTextPaint(sRelativeTimeTextPaint, R.dimen.card_relative_time_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      sTagTextPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sTagTextPaint.setColor(localResources.getColor(R.color.card_tag_text));
      sTagTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sTagTextPaint.setTextSize(localResources.getDimension(R.dimen.card_tag_text_size));
      sTagTextPaint.setShadowLayer(localResources.getDimension(R.dimen.card_tag_text_shadow_radius), localResources.getDimension(R.dimen.card_tag_text_shadow_x), localResources.getDimension(R.dimen.card_tag_text_shadow_y), localResources.getColor(R.color.card_tag_shadow_text));
      TextPaintUtils.registerTextPaint(sTagTextPaint, R.dimen.card_tag_text_size);
      TextPaint localTextPaint4 = new TextPaint();
      sAutoTextPaint = localTextPaint4;
      localTextPaint4.setAntiAlias(true);
      sAutoTextPaint.setColor(localResources.getColor(R.color.card_auto_text));
      sAutoTextPaint.setTextSize(localResources.getDimension(R.dimen.card_auto_text_size));
      TextPaintUtils.registerTextPaint(sAutoTextPaint, R.dimen.card_auto_text_size);
      TextPaint localTextPaint5 = new TextPaint();
      sAttributionTextPaint = localTextPaint5;
      localTextPaint5.setAntiAlias(true);
      sAttributionTextPaint.setColor(localResources.getColor(R.color.card_attribution_text));
      sAttributionTextPaint.setTextSize(localResources.getDimension(R.dimen.card_default_text_size));
      sAttributionTextPaint.linkColor = localResources.getColor(R.color.card_link);
      TextPaintUtils.registerTextPaint(sAttributionTextPaint, R.dimen.card_default_text_size);
      TextPaint localTextPaint6 = new TextPaint();
      sGraySpamTextPaint = localTextPaint6;
      localTextPaint6.setAntiAlias(true);
      sGraySpamTextPaint.setColor(localResources.getColor(R.color.card_gray_spam_text));
      sGraySpamTextPaint.setTextSize(localResources.getDimension(R.dimen.card_default_text_size));
      TextPaintUtils.registerTextPaint(sGraySpamTextPaint, R.dimen.card_default_text_size);
      Paint localPaint1 = new Paint();
      sMediaTopAreaBackgroundPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.solid_black));
      Paint localPaint2 = new Paint();
      sGraySpamBackgroundPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.card_gray_spam_background));
      sAvatarSize = (int)localResources.getDimension(R.dimen.card_avatar_size);
      sAuthorNameYOffset = (int)localResources.getDimension(R.dimen.card_author_name_y_padding);
      sRelativeTimeYOffset = (int)localResources.getDimension(R.dimen.card_relative_time_y_offset);
      sContentXPadding = (int)localResources.getDimension(R.dimen.card_content_x_padding);
      sContentYPadding = (int)localResources.getDimension(R.dimen.card_content_y_padding);
      sTagYOffset = (int)localResources.getDimension(R.dimen.card_tag_y_offset);
      sTagTextXPadding = (int)localResources.getDimension(R.dimen.card_tag_text_x_padding);
      sTagBackgroundYPadding = (int)localResources.getDimension(R.dimen.card_tag_background_y_padding);
      sTagIconXPadding = (int)localResources.getDimension(R.dimen.card_tag_icon_x_padding);
      sTagIconYPaddingCheckin = (int)localResources.getDimension(R.dimen.card_tag_icon_y_padding_checkin);
      sTagIconYPaddingLocation = (int)localResources.getDimension(R.dimen.card_tag_icon_y_padding_location);
      sTagIconYPaddingWithPhoto = (int)localResources.getDimension(R.dimen.card_tag_icon_y_padding_with_photo);
      sGraySpamIconPadding = (int)localResources.getDimension(R.dimen.card_gray_spam_x_padding);
      sMediaCardHeightPercentage = localResources.getDimension(R.dimen.media_card_height_percentage);
      sMediaCardBigHeightPercentage = localResources.getDimension(R.dimen.media_card_big_height_percentage);
    }
    this.mTagDrawableInstance = sTagDrawable.getConstantState().newDrawable();
  }

  private void createSourceRectForMediaImage(Rect paramRect, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt3 == 0) || (paramInt4 == 0))
    {
      paramRect.setEmpty();
      return;
    }
    int i = paramInt3 + sXDoublePadding;
    int j = (int)((paramInt4 + sYDoublePadding) * getMediaHeightPercentage());
    int k = paramInt1;
    int m = paramInt2;
    if (paramInt1 * j > paramInt2 * i)
      k = paramInt2 * i / j;
    while (true)
    {
      int n = (paramInt1 - k) / 2;
      int i1 = (paramInt2 - m) / 2;
      paramRect.set(n, i1, n + k, i1 + m);
      break;
      m = paramInt1 * j / i;
    }
  }

  protected static void drawMediaTopAreaStageWithTiledBackground(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    sTiledStageDrawable.setBounds(sLeftBorderPadding, sTopBorderPadding, paramInt1 + sXDoublePadding + sRightBorderPadding, paramInt2 + sYPadding);
    sTiledStageDrawable.draw(paramCanvas);
  }

  private void ensureOverridePlusOnedButton(int paramInt)
  {
    if (this.mOverridePlusOnedButton == null)
    {
      Rect localRect = this.mPlusOneButton.getRect();
      Resources localResources = getResources();
      int i = R.string.stream_plus_one_count_with_plus;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      String str = localResources.getString(i, arrayOfObject);
      this.mOverridePlusOnedButton = new ClickableButton(getContext(), str, PlusBarUtils.sPlusOnedTextPaint, PlusBarUtils.sPlusOnedDrawable, PlusBarUtils.sPlusOnedPressedDrawable, this, localRect.left, localRect.top);
    }
  }

  protected final int createAuthorNameAndRelativeTimeLayoutOnSameLine$4868d301(int paramInt1, int paramInt2)
  {
    CharSequence localCharSequence1 = TextUtils.ellipsize(this.mAuthorName, sNameTextPaint, paramInt2, TextUtils.TruncateAt.END);
    int i = (int)sNameTextPaint.measureText(localCharSequence1.toString());
    this.mAuthorNameLayout = new StaticLayout(localCharSequence1, sNameTextPaint, i, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
    int j = paramInt1 + this.mAuthorNameLayout.getHeight();
    CharSequence localCharSequence2 = TextUtils.ellipsize(this.mRelativeTime, sRelativeTimeTextPaint, paramInt2, TextUtils.TruncateAt.END);
    int k = (int)sRelativeTimeTextPaint.measureText(localCharSequence2.toString());
    if (k < paramInt2 - i)
      this.mRelativeTimeLayout = new StaticLayout(localCharSequence2, sRelativeTimeTextPaint, k, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
    return j;
  }

  protected final void createGraySpamBar(int paramInt)
  {
    if ((this.mIsGraySpam) && (paramInt > 0))
      if ((!this.mSquareMode) || (!this.mViewerIsSquareAdmin))
        break label74;
    label74: for (int i = R.string.card_square_gray_spam_for_moderator; ; i = R.string.card_square_gray_spam)
    {
      int j = paramInt - (sGraySpamWarningBitmap.getWidth() + 2 * sGraySpamIconPadding);
      this.mGraySpamLayout = new StaticLayout(getResources().getString(i), sGraySpamTextPaint, j, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
      return;
    }
  }

  protected final int createMediaBottomArea(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = (int)((paramInt4 + sYDoublePadding) * getMediaHeightPercentage()) + sTopBorderPadding + sAuthorNameYOffset;
    setAuthorImagePosition(paramInt1, i - sAvatarSize / 2);
    int j = paramInt3 - (sAvatarSize + sContentXPadding);
    int k = createAuthorNameAndRelativeTimeLayoutOnSameLine$4868d301(i, j) + sContentYPadding;
    boolean bool = TextUtils.isEmpty(this.mAttribution);
    int m = 0;
    if (!bool)
    {
      int i3 = (paramInt4 + paramInt2 - k) / (int)(sAttributionTextPaint.descent() - sAttributionTextPaint.ascent());
      m = 0;
      if (i3 > 0)
      {
        this.mAttributionLayout = TextPaintUtils.createConstrainedStaticLayout(sAttributionTextPaint, this.mAttribution, j, i3);
        k += this.mAttributionLayout.getHeight() + sContentYPadding;
        m = 1;
      }
    }
    if (!TextUtils.isEmpty(this.mContent))
    {
      int i2 = (paramInt4 + paramInt2 - k) / (int)(sDefaultTextPaint.descent() - sDefaultTextPaint.ascent());
      if (i2 > 0)
      {
        this.mContentLayout = TextPaintUtils.createConstrainedStaticLayout(sDefaultTextPaint, this.mContent, j, i2);
        k += this.mContentLayout.getHeight() + sContentYPadding;
        m = 1;
      }
    }
    if (!TextUtils.isEmpty(this.mFillerContent))
    {
      int i1 = (paramInt4 + paramInt2 - k) / (int)(sDefaultTextPaint.descent() - sDefaultTextPaint.ascent());
      if (i1 > 0)
      {
        this.mFillerContentLayout = TextPaintUtils.createConstrainedStaticLayout(sDefaultTextPaint, this.mFillerContent, j, i1);
        k += this.mFillerContentLayout.getHeight() + sContentYPadding;
        m = 1;
      }
    }
    if ((m == 0) && (this.mAutoText != 0))
    {
      int n = (paramInt4 + paramInt2 - k) / (int)(sAutoTextPaint.descent() - sAutoTextPaint.ascent());
      if (n > 0)
      {
        this.mAutoTextLayout = TextPaintUtils.createConstrainedStaticLayout(sAutoTextPaint, getResources().getString(this.mAutoText), j, n);
        k += this.mAutoTextLayout.getHeight() + sContentYPadding;
      }
    }
    return k;
  }

  protected final int createNameLayout$4868d301(int paramInt1, int paramInt2)
  {
    this.mAuthorNameLayout = new StaticLayout(TextUtils.ellipsize(this.mAuthorName, sNameTextPaint, paramInt2, TextUtils.TruncateAt.END), sNameTextPaint, paramInt2, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
    return paramInt1 + this.mAuthorNameLayout.getHeight();
  }

  protected final int createPlusOneBar(int paramInt1, int paramInt2, int paramInt3)
  {
    Context localContext = getContext();
    int i = paramInt1 + paramInt3;
    int j;
    int k;
    label41: TextPaint localTextPaint1;
    label100: NinePatchDrawable localNinePatchDrawable1;
    label110: NinePatchDrawable localNinePatchDrawable2;
    label120: int n;
    int i1;
    String str2;
    String str3;
    Bitmap localBitmap1;
    TextPaint localTextPaint2;
    NinePatchDrawable localNinePatchDrawable3;
    NinePatchDrawable localNinePatchDrawable4;
    if ((this.mPlusOneData != null) && (this.mPlusOneData.isPlusOnedByMe()))
    {
      j = 1;
      if (this.mPlusOneData != null)
        break label503;
      k = 1;
      Resources localResources1 = getResources();
      int m = R.string.stream_plus_one_count_with_plus;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(Math.max(k, 1));
      String str1 = localResources1.getString(m, arrayOfObject1);
      removeClickableItem(this.mPlusOneButton);
      if (j == 0)
        break label515;
      localTextPaint1 = PlusBarUtils.sPlusOnedTextPaint;
      if (j == 0)
        break label523;
      localNinePatchDrawable1 = PlusBarUtils.sPlusOnedDrawable;
      if (j == 0)
        break label531;
      localNinePatchDrawable2 = PlusBarUtils.sPlusOnedPressedDrawable;
      this.mPlusOneButton = new ClickableButton(localContext, str1, localTextPaint1, localNinePatchDrawable1, localNinePatchDrawable2, this, i, paramInt2);
      n = i - this.mPlusOneButton.getRect().width();
      i1 = paramInt2 - this.mPlusOneButton.getRect().height();
      this.mPlusOneButton.getRect().offsetTo(n, i1);
      addClickableItem(this.mPlusOneButton);
      if ((j != 0) && (this.mCanReshare))
      {
        removeClickableItem(this.mReshareButton);
        Bitmap localBitmap2 = sReshareBitmap;
        NinePatchDrawable localNinePatchDrawable5 = PlusBarUtils.sButtonDrawable;
        NinePatchDrawable localNinePatchDrawable6 = PlusBarUtils.sButtonPressedDrawable;
        String str4 = getResources().getString(R.string.reshare_button_content_description);
        this.mReshareButton = new ClickableButton(localContext, localBitmap2, localNinePatchDrawable5, localNinePatchDrawable6, this, n, i1, str4);
        n -= this.mReshareButton.getRect().width() + PlusBarUtils.sPlusBarXPadding;
        this.mReshareButton.getRect().offsetTo(n, i1);
        addClickableItem(this.mReshareButton);
      }
      if (this.mTotalComments > 0)
      {
        str2 = String.valueOf(this.mTotalComments);
        removeClickableItem(this.mCommentsButton);
        Resources localResources2 = getResources();
        int i2 = R.plurals.stream_one_up_comment_count;
        int i3 = this.mTotalComments;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(this.mTotalComments);
        str3 = localResources2.getQuantityString(i2, i3, arrayOfObject2);
        localBitmap1 = sCommentsBitmap;
        localTextPaint2 = PlusBarUtils.sNotPlusOnedTextPaint;
        localNinePatchDrawable3 = PlusBarUtils.sButtonDrawable;
        localNinePatchDrawable4 = PlusBarUtils.sButtonPressedDrawable;
        if (!(this instanceof EventStreamCardView))
          break label539;
      }
    }
    label515: label523: label531: label539: for (Object localObject = null; ; localObject = this)
    {
      this.mCommentsButton = new ClickableButton(localContext, localBitmap1, str2, localTextPaint2, localNinePatchDrawable3, localNinePatchDrawable4, (ClickableButton.ClickableButtonListener)localObject, n, i1, str3);
      int i4 = n - (this.mCommentsButton.getRect().width() + PlusBarUtils.sPlusBarXPadding);
      this.mCommentsButton.getRect().offsetTo(i4, i1);
      addClickableItem(this.mCommentsButton);
      return i1 + this.mPlusOneButton.getRect().height();
      j = 0;
      break;
      label503: k = this.mPlusOneData.getCount();
      break label41;
      localTextPaint1 = PlusBarUtils.sNotPlusOnedTextPaint;
      break label100;
      localNinePatchDrawable1 = PlusBarUtils.sButtonDrawable;
      break label110;
      localNinePatchDrawable2 = PlusBarUtils.sButtonPressedDrawable;
      break label120;
    }
  }

  protected final void createSourceRectForMediaImage(Rect paramRect, Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    if (paramBitmap == null)
      paramRect.setEmpty();
    while (true)
    {
      return;
      createSourceRectForMediaImage(paramRect, paramBitmap.getWidth(), paramBitmap.getHeight(), paramInt1, paramInt2);
    }
  }

  protected final void createSourceRectForMediaImage(Rect paramRect, Drawable paramDrawable, int paramInt1, int paramInt2)
  {
    if (paramDrawable == null)
      paramRect.setEmpty();
    while (true)
    {
      return;
      createSourceRectForMediaImage(paramRect, paramDrawable.getIntrinsicWidth(), paramDrawable.getIntrinsicHeight(), paramInt1, paramInt2);
    }
  }

  protected final int createTagBar(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.mTag != null)
    {
      int i = paramInt3 - 2 * sTagTextXPadding;
      int j = paramInt2 + sTagYOffset;
      if (this.mTagIcon != null)
      {
        this.mTagIcon.getWidth();
        i -= this.mTagIcon.getWidth() + sTagIconXPadding;
      }
      CharSequence localCharSequence = TextPaintUtils.smartEllipsize(this.mTag, sTagTextPaint, i, TextUtils.TruncateAt.END);
      int k = (int)sTagTextPaint.measureText(localCharSequence.toString());
      this.mTagLayout = new StaticLayout(localCharSequence, sTagTextPaint, k, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
      paramInt2 = j + this.mTagLayout.getHeight();
    }
    return paramInt2;
  }

  protected final void drawAuthorImage$494937f0(Canvas paramCanvas)
  {
    if (this.mAuthorImage == null)
      return;
    if (this.mAuthorImage.getBitmap() != null);
    for (Bitmap localBitmap = this.mAuthorImage.getBitmap(); ; localBitmap = sAuthorBitmap)
    {
      paramCanvas.drawBitmap(localBitmap, null, this.mAuthorImage.getRect(), sResizePaint);
      if (!this.mAuthorImage.isClicked())
        break;
      this.mAuthorImage.drawSelectionRect(paramCanvas);
      break;
    }
  }

  protected final int drawAuthorName(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    if (this.mAuthorNameLayout != null)
    {
      paramCanvas.translate(paramInt1, paramInt2);
      this.mAuthorNameLayout.draw(paramCanvas);
      paramCanvas.translate(-paramInt1, -paramInt2);
      paramInt2 += this.mAuthorNameLayout.getHeight();
    }
    return paramInt2;
  }

  protected final void drawCornerIcon(Canvas paramCanvas)
  {
    if (this.mCornerIcon != null)
    {
      int i = getHeight() - sBottomBorderPadding - this.mCornerIcon.getHeight();
      paramCanvas.drawBitmap(this.mCornerIcon, sLeftBorderPadding, i, null);
    }
  }

  protected final int drawMediaBottomArea$1be95c43(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = (int)((paramInt3 + 2 * sYPadding) * getMediaHeightPercentage()) + sTopBorderPadding + sAuthorNameYOffset;
    drawAuthorImage$494937f0(paramCanvas);
    int j = paramInt1 + (sAvatarSize + sContentXPadding);
    int k = paramInt2 - (sAvatarSize + sContentXPadding);
    int m = drawAuthorName(paramCanvas, j, i);
    if (this.mRelativeTimeLayout != null)
      drawRelativeTimeLayout(paramCanvas, j + k - this.mRelativeTimeLayout.getWidth(), m - this.mRelativeTimeLayout.getHeight() - sRelativeTimeYOffset);
    int n = m + sContentYPadding;
    if (this.mAttributionLayout != null)
    {
      paramCanvas.translate(j, n);
      this.mAttributionLayout.draw(paramCanvas);
      paramCanvas.translate(-j, -n);
      n += this.mAttributionLayout.getHeight() + sContentYPadding;
    }
    if (this.mContentLayout != null)
    {
      paramCanvas.translate(j, n);
      this.mContentLayout.draw(paramCanvas);
      paramCanvas.translate(-j, -n);
      n += this.mContentLayout.getHeight() + sContentYPadding;
    }
    if (this.mFillerContentLayout != null)
    {
      paramCanvas.translate(j, n);
      this.mFillerContentLayout.draw(paramCanvas);
      paramCanvas.translate(-j, -n);
      n += this.mFillerContentLayout.getHeight() + sContentYPadding;
    }
    if (this.mAutoTextLayout != null)
    {
      paramCanvas.translate(j, n);
      this.mAutoTextLayout.draw(paramCanvas);
      paramCanvas.translate(-j, -n);
      n += this.mAutoTextLayout.getHeight() + sContentYPadding;
    }
    return n;
  }

  protected final void drawMediaTopAreaShadow(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    int i = paramInt1 + 2 * sXPadding;
    int j = (int)((paramInt2 + 2 * sYPadding) * getMediaHeightPercentage());
    sMediaShadowDrawable.setBounds(sLeftBorderPadding, j + sTopBorderPadding - sMediaShadowDrawable.getIntrinsicHeight(), i + sLeftBorderPadding, j + sTopBorderPadding);
    sMediaShadowDrawable.draw(paramCanvas);
  }

  protected final void drawMediaTopAreaStage(Canvas paramCanvas, int paramInt1, int paramInt2, boolean paramBoolean, Rect paramRect, Paint paramPaint)
  {
    int i = paramInt1 + 2 * sXPadding;
    int j = (int)((paramInt2 + 2 * sYPadding) * getMediaHeightPercentage());
    if (paramBoolean)
      if ((paramRect == null) || (paramRect.width() < i) || (paramRect.height() < j))
        paramCanvas.drawRect(sLeftBorderPadding, sTopBorderPadding, i + sLeftBorderPadding, j + sTopBorderPadding, paramPaint);
    while (true)
    {
      return;
      BackgroundPatternUtils.getInstance(getContext());
      BitmapDrawable localBitmapDrawable = BackgroundPatternUtils.getBackgroundPattern(this.mActivityId);
      localBitmapDrawable.setBounds(sLeftBorderPadding, sTopBorderPadding, i + sLeftBorderPadding, j + sTopBorderPadding);
      localBitmapDrawable.draw(paramCanvas);
    }
  }

  protected final void drawPlusOneBar(Canvas paramCanvas)
  {
    if (!this.mInvisiblePlusOneButton)
    {
      if (this.mOverridePlusOnedButtonDisplay)
        break label53;
      this.mPlusOneButton.draw(paramCanvas);
    }
    while (true)
    {
      if (this.mReshareButton != null)
        this.mReshareButton.draw(paramCanvas);
      if (this.mCommentsButton != null)
        this.mCommentsButton.draw(paramCanvas);
      return;
      label53: if (this.mOverridePlusOnedButton != null)
        this.mOverridePlusOnedButton.draw(paramCanvas);
    }
  }

  protected final int drawRelativeTimeLayout(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    if (this.mRelativeTimeLayout != null)
    {
      paramCanvas.translate(paramInt1, paramInt2);
      this.mRelativeTimeLayout.draw(paramCanvas);
      paramCanvas.translate(-paramInt1, -paramInt2);
      paramInt2 += this.mRelativeTimeLayout.getHeight();
    }
    return paramInt2;
  }

  protected final void drawTagBarIconAndBackground(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    if (this.mTagLayout != null)
    {
      int i = paramInt2 + sTagYOffset;
      int j = this.mTagLayout.getWidth() + 2 * sTagTextXPadding;
      if (this.mTagIcon != null)
        j += this.mTagIcon.getWidth() + sTagIconXPadding;
      this.mTagDrawableInstance.setBounds(paramInt1, i - sTagBackgroundYPadding, paramInt1 + j, i + this.mTagLayout.getHeight() + sTagBackgroundYPadding);
      this.mTagDrawableInstance.draw(paramCanvas);
      int k = paramInt1 + sTagTextXPadding;
      if (this.mTagIcon != null)
      {
        int m = i + (this.mTagLayout.getHeight() - this.mTagIcon.getHeight()) / 2 + sTagIconYPaddingWithPhoto;
        paramCanvas.drawBitmap(this.mTagIcon, k, m, null);
        k += this.mTagIcon.getWidth() + sTagIconXPadding;
      }
      paramCanvas.translate(k, i);
      this.mTagLayout.draw(paramCanvas);
      paramCanvas.translate(-k, -i);
    }
  }

  protected String formatLocationName(String paramString)
  {
    return paramString.toUpperCase();
  }

  public final String getActivityId()
  {
    return this.mActivityId;
  }

  public String getAlbumId()
  {
    return null;
  }

  public String getDeepLinkLabel()
  {
    return null;
  }

  public int getDesiredHeight()
  {
    return 0;
  }

  public int getDesiredWidth()
  {
    return 0;
  }

  public final String getEventId()
  {
    return this.mEventId;
  }

  public final String getEventOwnerId()
  {
    return this.mEventOwnerId;
  }

  public String getLinkTitle()
  {
    return null;
  }

  public String getLinkUrl()
  {
    return null;
  }

  protected final float getMediaHeightPercentage()
  {
    if ((this.mDisplaySizeType == 1) || (this.mDisplaySizeType == 3));
    for (float f = sMediaCardBigHeightPercentage; ; f = sMediaCardHeightPercentage)
      return f;
  }

  public String getMediaLinkUrl()
  {
    return null;
  }

  public MediaRef getMediaRef()
  {
    return null;
  }

  public final Pair<ClickableButton, ClickableButton> getPlusOneButtonAnimationCopies()
  {
    int i = (int)getX();
    int j = (int)getY();
    ClickableButton localClickableButton = this.mPlusOneButton.createAbsoluteCoordinatesCopy(i, j);
    if (this.mPlusOneData == null);
    for (int k = 1; ; k = 1 + this.mPlusOneData.getCount())
    {
      ensureOverridePlusOnedButton(k);
      return new Pair(localClickableButton, this.mOverridePlusOnedButton.createAbsoluteCoordinatesCopy(i, j));
    }
  }

  public String getSquareId()
  {
    return null;
  }

  public final String getSquareIdForOneUp()
  {
    return this.mSquareIdForOneUp;
  }

  public void init(Cursor paramCursor, int paramInt1, int paramInt2, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, ViewedListener paramViewedListener, StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamMediaClickListener paramStreamMediaClickListener)
  {
    super.init(paramCursor, paramInt1, paramInt2, paramOnClickListener, paramItemClickListener, paramViewedListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener);
    Context localContext = getContext();
    Resources localResources = getResources();
    this.mStreamPlusBarClickListener = paramStreamPlusBarClickListener;
    this.mStreamMediaClickListener = paramStreamMediaClickListener;
    this.mActivityId = paramCursor.getString(1);
    this.mAuthorGaiaId = paramCursor.getString(2);
    this.mAuthorName = paramCursor.getString(3);
    if (this.mAuthorName == null)
      this.mAuthorName = "";
    this.mAuthorAvatarUrl = EsAvatarData.uncompressAvatarUrl(paramCursor.getString(4));
    if (this.mAuthorImage != null)
      removeClickableItem(this.mAuthorImage);
    this.mAuthorImage = new ClickableAvatar(this, this.mAuthorGaiaId, this.mAuthorAvatarUrl, this.mAuthorName, paramItemClickListener, 2);
    addClickableItem(this.mAuthorImage);
    this.mRelativeTime = Dates.getRelativeTimeSpanString(localContext, paramCursor.getLong(8)).toString().toUpperCase();
    long l = paramCursor.getLong(15);
    label239: boolean bool1;
    label389: label432: label446: boolean bool2;
    label485: boolean bool3;
    label505: StringBuilder localStringBuilder;
    if ((0x2 & l) != 0L)
    {
      this.mContent = paramCursor.getString(16);
      if ((1L & l) != 0L)
      {
        if (!TextUtils.isEmpty(this.mContent))
          break label815;
        this.mContent = paramCursor.getString(17);
      }
      String str1 = paramCursor.getString(18);
      String str2 = paramCursor.getString(19);
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)))
        this.mAttribution = localResources.getString(R.string.stream_original_author, new Object[] { str2 });
      if ((this.mTag == null) && ((0x8 & l) != 0L))
      {
        byte[] arrayOfByte4 = paramCursor.getBlob(7);
        if (arrayOfByte4 != null)
        {
          this.mTag = formatLocationName(DbLocation.deserialize(arrayOfByte4).getLocationName());
          this.mTagIcon = sTagLocationBitmaps[0];
        }
      }
      this.mTotalComments = paramCursor.getInt(6);
      byte[] arrayOfByte1 = paramCursor.getBlob(5);
      if (arrayOfByte1 == null)
        break label830;
      this.mPlusOneData = DbPlusOneData.deserialize(arrayOfByte1);
      if (paramCursor.getInt(11) != 1)
        this.mViewedListener = paramViewedListener;
      if ((this.mSquareMode) || ((0x80000 & l) == 0L))
        break label838;
      this.mCornerIcon = sCommunityBitmap;
      if (paramCursor.getInt(9) != 0)
        break label868;
      bool1 = true;
      this.mIsLimited = bool1;
      if ((!this.mViewerIsSquareAdmin) || (paramCursor.getInt(10) != 1) || ((0x80000 & l) == 0L))
        break label874;
      bool2 = true;
      this.mIsGraySpam = bool2;
      if (paramCursor.getInt(12) == 0)
        break label880;
      bool3 = true;
      this.mCanReshare = bool3;
      this.mAutoText = EsPostsData.getDefaultText(l);
      localStringBuilder = new StringBuilder();
      AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mAuthorName);
      if (this.mAutoText != 0)
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, localResources.getString(this.mAutoText));
      AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mRelativeTime);
      AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mContent);
      AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mFillerContent);
      AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mAttribution);
      AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mTag);
      if (this.mTotalComments > 0)
      {
        int k = R.plurals.comments;
        int m = this.mTotalComments;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(this.mTotalComments);
        localStringBuilder.append(localResources.getQuantityString(k, m, arrayOfObject2)).append(". ");
      }
      if (this.mPlusOneData != null)
        break label886;
    }
    label815: label830: label838: label868: label874: label880: label886: for (int i = 0; ; i = this.mPlusOneData.getCount())
    {
      if (i > 0)
      {
        int j = R.plurals.plus_one_accessibility_description;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(i);
        localStringBuilder.append(localResources.getQuantityString(j, i, arrayOfObject1));
      }
      byte[] arrayOfByte2 = paramCursor.getBlob(13);
      if (arrayOfByte2 != null)
      {
        PlusEvent localPlusEvent = (PlusEvent)PlusEventJson.getInstance().fromByteArray(arrayOfByte2);
        if (localPlusEvent != null)
        {
          this.mEventId = localPlusEvent.id;
          this.mEventOwnerId = localPlusEvent.creatorObfuscatedId;
        }
      }
      byte[] arrayOfByte3 = paramCursor.getBlob(27);
      if (arrayOfByte3 != null)
        this.mSquareIdForOneUp = DbEmbedSquare.deserialize(arrayOfByte3).getSquareId();
      setContentDescription(localStringBuilder.toString());
      setFocusable(true);
      return;
      this.mContent = null;
      break;
      this.mFillerContent = paramCursor.getString(17);
      break label239;
      this.mPlusOneData = null;
      break label389;
      if (paramCursor.getInt(14) == 1)
      {
        this.mCornerIcon = sWhatsHotBitmap;
        break label432;
      }
      this.mCornerIcon = null;
      break label432;
      bool1 = false;
      break label446;
      bool2 = false;
      break label485;
      bool3 = false;
      break label505;
    }
  }

  public boolean isAlbum()
  {
    return false;
  }

  protected void onBindResources()
  {
    super.onBindResources();
    if (this.mAuthorImage != null)
      this.mAuthorImage.bindResources();
  }

  public void onClickableButtonListenerClick(ClickableButton paramClickableButton)
  {
    if (this.mStreamPlusBarClickListener != null)
    {
      if (paramClickableButton != this.mPlusOneButton)
        break label34;
      this.mStreamPlusBarClickListener.onPlusOneClicked(this.mActivityId, this.mPlusOneData, this);
    }
    while (true)
    {
      return;
      label34: if (paramClickableButton == this.mReshareButton)
      {
        this.mStreamPlusBarClickListener.onReshareClicked(this.mActivityId, this.mIsLimited);
      }
      else if (paramClickableButton == this.mCommentsButton)
      {
        StreamPlusBarClickListener localStreamPlusBarClickListener = this.mStreamPlusBarClickListener;
        localStreamPlusBarClickListener.onCommentsClicked$1b4287ec(this);
      }
    }
  }

  protected void onDetachedFromWindow()
  {
    if (this.mShakeAnimation == null)
    {
      removeCallbacks(this.mShakeAnimation);
      this.mShakeAnimation = null;
    }
    clearAnimation();
    super.onDetachedFromWindow();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mActivityId != null)
    {
      super.onDraw(paramCanvas);
      int i = sLeftBorderPadding;
      int j = sTopBorderPadding;
      int k = getWidth() - sLeftBorderPadding - sRightBorderPadding;
      if (this.mGraySpamLayout != null)
      {
        int m = Math.max(sGraySpamWarningBitmap.getHeight(), this.mGraySpamLayout.getHeight());
        paramCanvas.drawRect(i, j, k + i, j + m, sGraySpamBackgroundPaint);
        paramCanvas.drawBitmap(sGraySpamWarningBitmap, i + sGraySpamIconPadding, j + (m - sGraySpamWarningBitmap.getHeight()) / 2, sResizePaint);
        int n = i + sGraySpamWarningBitmap.getWidth() + 2 * sGraySpamIconPadding;
        int i1 = j + (m - this.mGraySpamLayout.getHeight()) / 2;
        paramCanvas.translate(n, i1);
        this.mGraySpamLayout.draw(paramCanvas);
        paramCanvas.translate(-n, -i1);
      }
      if (this.mViewedListener != null)
      {
        this.mViewedListener.onStreamCardViewed(this.mActivityId);
        this.mViewedListener = null;
      }
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mActivityId == null)
      setMeasuredDimension(0, 0);
    while (true)
    {
      return;
      super.onMeasure(paramInt1, paramInt2);
      createGraySpamBar(getMeasuredWidth() - sLeftBorderPadding - sRightBorderPadding);
    }
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mActivityId = null;
    this.mAuthorGaiaId = null;
    this.mAuthorName = null;
    this.mAuthorAvatarUrl = null;
    this.mAuthorNameLayout = null;
    this.mRelativeTime = null;
    this.mRelativeTimeLayout = null;
    this.mTag = null;
    this.mTagIcon = null;
    this.mTagLayout = null;
    this.mContent = null;
    this.mContentLayout = null;
    this.mAttribution = null;
    this.mAttributionLayout = null;
    this.mFillerContent = null;
    this.mFillerContentLayout = null;
    this.mAutoText = 0;
    this.mAutoTextLayout = null;
    this.mGraySpamLayout = null;
    this.mCommentsButton = null;
    this.mReshareButton = null;
    this.mPlusOneButton = null;
    this.mOverridePlusOnedButton = null;
    this.mInvisiblePlusOneButton = false;
    this.mOverridePlusOnedButtonDisplay = false;
    this.mTotalComments = 0;
    this.mPlusOneData = null;
    this.mStreamPlusBarClickListener = null;
    this.mCornerIcon = null;
    this.mIsLimited = false;
    this.mIsGraySpam = false;
    this.mCanReshare = false;
    this.mSquareMode = false;
    this.mViewerIsSquareAdmin = false;
    this.mSquareIdForOneUp = null;
    this.mEventId = null;
    this.mEventOwnerId = null;
    this.mViewedListener = null;
  }

  protected void onUnbindResources()
  {
    super.onUnbindResources();
    if (this.mAuthorImage != null)
      this.mAuthorImage.unbindResources();
  }

  public final void overridePlusOnedButtonDisplay(boolean paramBoolean, int paramInt)
  {
    this.mOverridePlusOnedButtonDisplay = paramBoolean;
    if (this.mOverridePlusOnedButtonDisplay)
    {
      ensureOverridePlusOnedButton(paramInt);
      this.mPlusOneButton.setListener(null);
      this.mOverridePlusOnedButton.setListener(this);
      this.mInvisiblePlusOneButton = false;
    }
    while (true)
    {
      invalidate();
      return;
      this.mOverridePlusOnedButton = null;
    }
  }

  protected final void setAuthorImagePosition(int paramInt1, int paramInt2)
  {
    if (this.mAuthorImage != null)
      this.mAuthorImage.setRect(paramInt1, paramInt2, paramInt1 + sAvatarSize, paramInt2 + sAvatarSize);
  }

  public void setSquareMode(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mSquareMode = paramBoolean1;
    this.mViewerIsSquareAdmin = paramBoolean2;
  }

  public final void startDelayedShakeAnimation()
  {
    this.mInvisiblePlusOneButton = true;
    invalidate();
    final float f1;
    final float f2;
    switch (this.mDisplaySizeType)
    {
    default:
      f1 = -1.5F;
      f2 = 2.0F;
      if (Build.VERSION.SDK_INT >= 14)
        animate().setDuration(300L).rotationX(f1).rotationY(f2).scaleX(0.95F).scaleY(0.95F).setInterpolator(sDampingInterpolator).setStartDelay(615L);
      break;
    case 0:
    }
    while (true)
    {
      return;
      if ((this instanceof TextCardView));
      for (f1 = -2.5F; ; f1 = -2.0F)
      {
        f2 = 2.5F;
        break;
      }
      this.mShakeAnimation = new Runnable()
      {
        public final void run()
        {
          if (StreamCardView.this.getHandler() != null)
            StreamCardView.this.animate().setDuration(300L).rotationX(f1).rotationY(f2).scaleX(0.95F).scaleY(0.95F).setInterpolator(StreamCardView.sDampingInterpolator);
          StreamCardView.access$102(StreamCardView.this, null);
        }
      };
      removeCallbacks(this.mShakeAnimation);
      postDelayed(this.mShakeAnimation, 615L);
    }
  }

  public static abstract interface StreamMediaClickListener
  {
    public abstract void onMediaClicked(String paramString1, String paramString2, MediaRef paramMediaRef, boolean paramBoolean, StreamCardView paramStreamCardView);
  }

  public static abstract interface StreamPlusBarClickListener
  {
    public abstract void onCommentsClicked$1b4287ec(StreamCardView paramStreamCardView);

    public abstract void onPlusOneClicked(String paramString, DbPlusOneData paramDbPlusOneData, StreamCardView paramStreamCardView);

    public abstract void onReshareClicked(String paramString, boolean paramBoolean);
  }

  public static abstract interface ViewedListener
  {
    public abstract void onStreamCardViewed(String paramString);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.StreamCardView
 * JD-Core Version:    0.6.2
 */