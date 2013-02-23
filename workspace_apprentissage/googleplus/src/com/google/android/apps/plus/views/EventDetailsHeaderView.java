package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.api.services.plusi.model.EventOptions;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.Theme;
import com.google.api.services.plusi.model.ThemeImage;
import java.util.Iterator;
import java.util.List;

public class EventDetailsHeaderView extends ViewGroup
  implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, View.OnClickListener, EsImageView.OnImageLoadedListener, Recyclable
{
  private static int sAvatarOverlap;
  private static int sAvatarSize;
  private static String sCollapseText;
  private static String sExpandText;
  private static boolean sInitialized = false;
  private static int sOnAirColor;
  private static Drawable sOnAirDrawable;
  private static String sOnAirText;
  private static int sPadding;
  private static int sPrivatePublicColor;
  private static String sPrivateText;
  private static String sPublicText;
  private static int sSecondaryPadding;
  private static float sTypeSize;
  private EventActionListener mActionListener;
  private AvatarView mAvatar;
  private int mChevronResId;
  private ImageView mExpandCollapseChevronView;
  private TextView mExpandCollapseTextView;
  private View mExpandCollapseView;
  private boolean mOnAirWrap;
  private View.OnClickListener mOnClickListener;
  private EventThemeView mThemeImageView;
  private TextView mTitleView;
  private TextView mTypeView;
  private String mVideoThemeUrl;
  private VideoView mVideoView;

  public EventDetailsHeaderView(Context paramContext)
  {
    this(paramContext, null);
  }

  public EventDetailsHeaderView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public EventDetailsHeaderView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (!sInitialized)
    {
      sInitialized = true;
      Resources localResources2 = getResources();
      int i = localResources2.getDimensionPixelSize(R.dimen.event_card_details_avatar_size);
      sAvatarSize = i;
      sAvatarOverlap = (int)(i * localResources2.getDimension(R.dimen.event_card_details_avatar_percent_overlap));
      sPadding = localResources2.getDimensionPixelSize(R.dimen.event_card_padding);
      sSecondaryPadding = localResources2.getDimensionPixelSize(R.dimen.event_card_details_secondary_padding);
      sOnAirText = localResources2.getString(R.string.event_detail_on_air);
      sOnAirColor = localResources2.getColor(R.color.event_detail_on_air);
      sPrivateText = localResources2.getString(R.string.event_detail_private);
      sPublicText = localResources2.getString(R.string.event_detail_public);
      sPrivatePublicColor = localResources2.getColor(R.color.event_detail_private);
      sTypeSize = localResources2.getDimension(R.dimen.event_card_details_on_air_size);
      sOnAirDrawable = localResources2.getDrawable(R.drawable.btn_events_on_air);
      sExpandText = localResources2.getString(R.string.profile_show_more);
      sCollapseText = localResources2.getString(R.string.profile_show_less);
    }
    Resources localResources1 = getResources();
    if (Build.VERSION.SDK_INT >= 14)
    {
      this.mVideoView = new VideoView(paramContext);
      this.mVideoView.setOnErrorListener(this);
      addView(this.mVideoView);
    }
    this.mThemeImageView = new EventThemeView(paramContext);
    this.mThemeImageView.setFadeIn(true);
    addView(this.mThemeImageView);
    this.mAvatar = new AvatarView(paramContext);
    this.mAvatar.setRounded(true);
    addView(this.mAvatar);
    this.mTitleView = new TextView(paramContext);
    this.mTitleView.setTextColor(localResources1.getColor(R.color.event_card_details_title_color));
    this.mTitleView.setTextSize(0, localResources1.getDimension(R.dimen.event_card_details_title_size));
    this.mTitleView.setTypeface(Typeface.DEFAULT_BOLD);
    addView(this.mTitleView);
    this.mExpandCollapseChevronView = new ImageView(paramContext);
    this.mExpandCollapseChevronView.setImageResource(R.drawable.icn_events_arrow_down);
    this.mChevronResId = R.drawable.icn_events_arrow_down;
    addView(this.mExpandCollapseChevronView);
    this.mExpandCollapseView = new View(paramContext);
    addView(this.mExpandCollapseView);
    this.mTypeView = new TextView(paramContext);
    this.mTypeView.setTextSize(0, localResources1.getDimension(R.dimen.event_card_details_subtitle_size));
    this.mTypeView.setSingleLine();
    this.mTypeView.setGravity(17);
    addView(this.mTypeView);
    this.mExpandCollapseTextView = new TextView(paramContext);
    this.mExpandCollapseTextView.setTextSize(0, localResources1.getDimension(R.dimen.event_card_details_title_size));
    this.mExpandCollapseTextView.setTextColor(localResources1.getColor(R.color.event_card_details_collapse_expand_color));
    this.mExpandCollapseTextView.setText(sExpandText);
    this.mExpandCollapseTextView.setSingleLine();
    this.mExpandCollapseTextView.setEllipsize(TextUtils.TruncateAt.END);
    addView(this.mExpandCollapseTextView);
  }

  public final void bind(PlusEvent paramPlusEvent, View.OnClickListener paramOnClickListener, boolean paramBoolean, EventActionListener paramEventActionListener)
  {
    setEventTheme(paramPlusEvent.theme);
    this.mAvatar.setGaiaId(paramPlusEvent.creatorObfuscatedId);
    this.mTitleView.setText(paramPlusEvent.name);
    removeView(this.mExpandCollapseTextView);
    removeView(this.mExpandCollapseChevronView);
    removeView(this.mExpandCollapseView);
    if (paramOnClickListener != null)
    {
      addView(this.mExpandCollapseChevronView);
      addView(this.mExpandCollapseView);
      if (paramBoolean)
        addView(this.mExpandCollapseTextView);
    }
    this.mOnClickListener = paramOnClickListener;
    this.mActionListener = paramEventActionListener;
    if ((paramPlusEvent.eventOptions != null) && (paramPlusEvent.eventOptions.broadcast != null) && (paramPlusEvent.eventOptions.broadcast.booleanValue()))
    {
      this.mTypeView.setText(sOnAirText);
      this.mTypeView.setTextColor(sOnAirColor);
      this.mTypeView.setBackgroundDrawable(sOnAirDrawable);
      this.mTypeView.setVisibility(0);
    }
    while (true)
    {
      this.mTypeView.setTextSize(0, sTypeSize);
      if (this.mVideoView != null)
        this.mVideoView.setVisibility(4);
      this.mThemeImageView.setOnImageLoadedListener(this);
      this.mAvatar.setOnClickListener(this);
      this.mExpandCollapseView.setOnClickListener(this);
      return;
      if (paramPlusEvent.isPublic != null)
      {
        TextView localTextView = this.mTypeView;
        if (paramPlusEvent.isPublic.booleanValue());
        for (String str = sPublicText; ; str = sPrivateText)
        {
          localTextView.setText(str);
          this.mTypeView.setTextColor(sPrivatePublicColor);
          this.mTypeView.setBackgroundDrawable(null);
          this.mTypeView.setVisibility(0);
          break;
        }
      }
      this.mTypeView.setVisibility(8);
    }
  }

  public void onClick(View paramView)
  {
    if (((paramView instanceof AvatarView)) && (this.mActionListener != null))
      this.mActionListener.onAvatarClicked(((AvatarView)paramView).getGaiaId());
    while (true)
    {
      return;
      if (this.mOnClickListener != null)
        this.mOnClickListener.onClick(paramView);
    }
  }

  public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    return true;
  }

  public final void onImageLoaded$7ad36aad()
  {
    if ((this.mVideoView != null) && (!TextUtils.isEmpty(this.mVideoThemeUrl)))
    {
      Uri.Builder localBuilder = new Uri.Builder();
      localBuilder.path(this.mVideoThemeUrl);
      this.mVideoView.setVisibility(0);
      this.mVideoView.setVideoURI(localBuilder.build());
      this.mVideoView.setOnPreparedListener(this);
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getMeasuredWidth();
    int j = (int)(i / 3.36F);
    this.mThemeImageView.layout(0, 0, i, j);
    if (this.mVideoView != null)
      this.mVideoView.layout(0, 0, i, j);
    int k = sPadding;
    int m = j - sAvatarOverlap;
    this.mAvatar.layout(k, m, k + sAvatarSize, m + sAvatarSize);
    int n = sPadding + sAvatarSize + sPadding;
    int i1 = j + sPadding;
    this.mTitleView.layout(n, i1, n + this.mTitleView.getMeasuredWidth(), i1 + this.mTitleView.getMeasuredHeight());
    if (this.mTypeView.getVisibility() == 0)
    {
      int i6 = i1 + this.mTitleView.getMeasuredHeight();
      this.mTypeView.layout(n, i6, n + this.mTypeView.getMeasuredWidth(), i6 + this.mTypeView.getMeasuredHeight());
    }
    if (this.mOnClickListener != null)
    {
      int i2 = this.mExpandCollapseChevronView.getMeasuredHeight();
      int i3 = i1 + this.mTitleView.getBaseline() - i2;
      int i4 = i - this.mExpandCollapseChevronView.getMeasuredWidth() - sSecondaryPadding;
      this.mExpandCollapseChevronView.layout(i4, i3, i4 + this.mExpandCollapseChevronView.getMeasuredWidth(), i3 + i2);
      int i5 = i4 - this.mExpandCollapseTextView.getMeasuredWidth() - sPadding;
      this.mExpandCollapseTextView.layout(i5, i1, i5 + this.mExpandCollapseTextView.getMeasuredWidth(), i1 + this.mExpandCollapseTextView.getMeasuredHeight());
      this.mExpandCollapseView.layout(sAvatarSize, j, sAvatarSize + this.mExpandCollapseView.getMeasuredWidth(), j + this.mExpandCollapseView.getMeasuredHeight());
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getSize(paramInt1);
    if (i == 0)
      i = View.MeasureSpec.getSize(paramInt2);
    int j = (int)(i / 3.36F);
    this.mThemeImageView.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(j, 1073741824));
    if (this.mVideoView != null)
      this.mVideoView.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(j, 1073741824));
    this.mAvatar.measure(View.MeasureSpec.makeMeasureSpec(sAvatarSize, 1073741824), View.MeasureSpec.makeMeasureSpec(sAvatarSize, 1073741824));
    int k = Math.max(0, i - this.mAvatar.getMeasuredWidth() - 2 * sPadding - sSecondaryPadding);
    if (this.mOnClickListener != null)
    {
      this.mExpandCollapseChevronView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
      k = Math.max(0, k - (this.mExpandCollapseChevronView.getMeasuredWidth() + sPadding));
    }
    this.mTitleView.measure(View.MeasureSpec.makeMeasureSpec(k, -2147483648), View.MeasureSpec.makeMeasureSpec(0, 0));
    int m = Math.max(0, k - sPadding);
    this.mOnAirWrap = false;
    int n = this.mTypeView.getVisibility();
    int i1 = 0;
    int i2 = 0;
    if (n != 8)
    {
      this.mTypeView.measure(View.MeasureSpec.makeMeasureSpec(m, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
      if (this.mTypeView.getMeasuredWidth() <= m)
        break label446;
    }
    label446: for (boolean bool = true; ; bool = false)
    {
      this.mOnAirWrap = bool;
      if (this.mOnAirWrap)
        this.mTypeView.measure(View.MeasureSpec.makeMeasureSpec(k, -2147483648), View.MeasureSpec.makeMeasureSpec(0, 0));
      i2 = this.mTypeView.getMeasuredWidth() + sPadding;
      i1 = this.mTypeView.getMeasuredHeight();
      int i3 = this.mTitleView.getMeasuredWidth();
      if (this.mOnAirWrap)
        i2 = 0;
      int i4 = Math.max(0, k - Math.max(i3, i2));
      this.mExpandCollapseTextView.measure(View.MeasureSpec.makeMeasureSpec(i4, -2147483648), View.MeasureSpec.makeMeasureSpec(0, 0));
      int i5 = i1 + this.mTitleView.getMeasuredHeight() + sPadding;
      int i6 = j + Math.max(sAvatarSize - sAvatarOverlap, i5);
      this.mExpandCollapseView.measure(View.MeasureSpec.makeMeasureSpec(i - sAvatarSize, 1073741824), View.MeasureSpec.makeMeasureSpec(i6 - j, 1073741824));
      setMeasuredDimension(i, i6);
      return;
    }
  }

  public void onPrepared(MediaPlayer paramMediaPlayer)
  {
    this.mThemeImageView.startFadeOut(750);
    paramMediaPlayer.setLooping(true);
    paramMediaPlayer.start();
  }

  public void onRecycle()
  {
    this.mTitleView.setText(null);
    this.mVideoThemeUrl = null;
    this.mThemeImageView.onRecycle();
    if (this.mVideoView != null)
      this.mThemeImageView.setAlpha(1.0F);
    this.mExpandCollapseView.setOnClickListener(null);
    this.mAvatar.setOnClickListener(null);
    this.mThemeImageView.setOnImageLoadedListener(null);
    if (this.mVideoView != null)
    {
      this.mVideoView.setOnPreparedListener(null);
      if (this.mVideoView.isPlaying())
        this.mVideoView.stopPlayback();
    }
    this.mOnClickListener = null;
  }

  public final void pausePlayback()
  {
    if ((this.mVideoView != null) && (this.mVideoView.isPlaying()))
    {
      if (!this.mVideoView.canPause())
        break label57;
      this.mVideoView.pause();
    }
    while (true)
    {
      if ((this.mThemeImageView != null) && (this.mVideoView != null))
        this.mThemeImageView.setAlpha(1.0F);
      return;
      label57: this.mVideoView.stopPlayback();
    }
  }

  public void setEventTheme(Theme paramTheme)
  {
    if ((this.mVideoView != null) && (paramTheme != null) && (paramTheme.image != null))
    {
      Iterator localIterator = paramTheme.image.iterator();
      while (localIterator.hasNext())
      {
        ThemeImage localThemeImage = (ThemeImage)localIterator.next();
        if (("MOV".equals(localThemeImage.format)) && ("LARGE".equals(localThemeImage.aspectRatio)) && (localThemeImage.url.endsWith("mp4")))
          this.mVideoThemeUrl = localThemeImage.url;
      }
    }
    this.mThemeImageView.setEventTheme(paramTheme);
  }

  public void setExpandState(boolean paramBoolean)
  {
    int i;
    TextView localTextView;
    if (paramBoolean)
    {
      i = R.drawable.icn_events_arrow_up;
      this.mChevronResId = i;
      localTextView = this.mExpandCollapseTextView;
      if (!paramBoolean)
        break label69;
    }
    label69: for (String str = sCollapseText; ; str = sExpandText)
    {
      localTextView.setText(str);
      this.mExpandCollapseChevronView.setImageResource(this.mChevronResId);
      if (this.mActionListener != null)
        this.mActionListener.onExpansionToggled(paramBoolean);
      return;
      i = R.drawable.icn_events_arrow_down;
      break;
    }
  }

  public void setLayoutType(boolean paramBoolean)
  {
    TextView localTextView = this.mExpandCollapseTextView;
    if (paramBoolean);
    for (int i = 0; ; i = 8)
    {
      localTextView.setVisibility(i);
      return;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventDetailsHeaderView
 * JD-Core Version:    0.6.2
 */