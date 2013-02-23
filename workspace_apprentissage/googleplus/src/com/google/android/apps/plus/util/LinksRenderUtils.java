package com.google.android.apps.plus.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.views.ClickableButton;
import com.google.android.apps.plus.views.ClickableButton.ClickableButtonListener;

public final class LinksRenderUtils
{
  protected static Paint sAppInviteTopAreaBackgroundPaint;
  protected static Bitmap sDeepLinkIcon;
  protected static TextPaint sDeepLinkTextPaint;
  protected static int sHorizontalSpacing;
  protected static int sIconHorizontalSpacing;
  protected static Paint sImageBorderPaint;
  protected static int sImageHorizontalSpacing;
  protected static float sImageMaxWidthPercentage;
  protected static TextPaint sLinkTitleTextPaint;
  protected static TextPaint sLinkUrlTextPaint;
  private static boolean sLinksCardViewInitialized;
  protected static Paint sLinksTopAreaBackgroundPaint;
  protected static int sMaxImageDimension;
  protected static final Paint sResizePaint = new Paint(2);
  protected static Paint sTransparentOverlayPaint;

  public static void createBackgroundDestRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Rect paramRect)
  {
    paramRect.set(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public static void createBackgroundSourceRect(Bitmap paramBitmap, Rect paramRect1, Rect paramRect2)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    float f1 = i / j;
    float f2 = paramRect1.width() / paramRect1.height();
    if (f1 > f2)
    {
      int m = (i - (int)(f2 * j)) / 2;
      paramRect2.set(m, 0, i - m, j);
    }
    while (true)
    {
      return;
      int k = (j - (int)(i / f2)) / 2;
      paramRect2.set(0, k, i, j - k);
    }
  }

  public static ClickableButton createDeepLinkButton(Context paramContext, String paramString, int paramInt1, int paramInt2, int paramInt3, ClickableButton.ClickableButtonListener paramClickableButtonListener)
  {
    CharSequence localCharSequence;
    if (!TextUtils.isEmpty(paramString))
    {
      int i = Math.max(0, paramInt3 - sDeepLinkIcon.getWidth() - ClickableButton.getTotalPadding(paramContext, true, true) - sHorizontalSpacing - sImageHorizontalSpacing);
      localCharSequence = TextUtils.ellipsize(paramString, PlusBarUtils.sNotPlusOnedTextPaint, i, TextUtils.TruncateAt.END);
    }
    for (ClickableButton localClickableButton = new ClickableButton(paramContext, sDeepLinkIcon, localCharSequence, PlusBarUtils.sInteractivePostButtonTextPaint, PlusBarUtils.sButtonDrawable, PlusBarUtils.sButtonPressedDrawable, paramClickableButtonListener, paramInt1, paramInt2, localCharSequence, true); ; localClickableButton = null)
      return localClickableButton;
  }

  public static void createImageRects(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Rect paramRect1, Rect paramRect2)
  {
    int i = paramInt3 + sHorizontalSpacing;
    int j = paramInt4 + (paramInt1 - paramInt2) / 2;
    paramRect1.set(i, j, i + paramInt2, j + paramInt2);
    int k = (int)sImageBorderPaint.getStrokeWidth();
    paramRect2.set(i + k, j + k, i + paramInt2 - k, j + paramInt2 - k);
  }

  public static void createImageSourceRect(Bitmap paramBitmap, Rect paramRect)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    int k = Math.min(i, j);
    if (i > k)
      paramRect.set((i - k) / 2, 0, (i + k) / 2, j);
    while (true)
    {
      return;
      paramRect.set(0, (j - k) / 2, i, (j + k) / 2);
    }
  }

  public static StaticLayout createTitle(String paramString, int paramInt1, int paramInt2)
  {
    int i;
    if (!TextUtils.isEmpty(paramString))
    {
      i = Math.min(3, paramInt1 / (int)(sLinkTitleTextPaint.descent() - sLinkTitleTextPaint.ascent()));
      if (i <= 0);
    }
    for (StaticLayout localStaticLayout = TextPaintUtils.createConstrainedStaticLayout(sLinkTitleTextPaint, paramString, paramInt2 - 2 * sHorizontalSpacing - sImageHorizontalSpacing, i); ; localStaticLayout = null)
      return localStaticLayout;
  }

  public static StaticLayout createUrl(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    if (!TextUtils.isEmpty(paramString))
    {
      i = Math.min(1, (paramInt1 - paramInt3) / (int)(sLinkTitleTextPaint.descent() - sLinkTitleTextPaint.ascent()));
      if (i <= 0);
    }
    for (StaticLayout localStaticLayout = TextPaintUtils.createConstrainedStaticLayout(sLinkUrlTextPaint, paramString, paramInt2 - 2 * sHorizontalSpacing - sImageHorizontalSpacing - sIconHorizontalSpacing, i); ; localStaticLayout = null)
      return localStaticLayout;
  }

  public static void drawBitmap(Canvas paramCanvas, Bitmap paramBitmap, Rect paramRect1, Rect paramRect2, Rect paramRect3, Rect paramRect4, Rect paramRect5)
  {
    paramCanvas.drawBitmap(paramBitmap, paramRect2, paramRect3, sResizePaint);
    paramCanvas.drawRect(paramRect3, sTransparentOverlayPaint);
    paramCanvas.drawBitmap(paramBitmap, paramRect1, paramRect4, sResizePaint);
    paramCanvas.drawRect(paramRect5, sImageBorderPaint);
  }

  public static void drawTitleDeepLinkAndUrl(Canvas paramCanvas, int paramInt1, int paramInt2, StaticLayout paramStaticLayout1, ClickableButton paramClickableButton, StaticLayout paramStaticLayout2, Bitmap paramBitmap)
  {
    int i = paramInt1 + (sHorizontalSpacing + sImageHorizontalSpacing);
    if (paramStaticLayout1 != null)
    {
      paramCanvas.translate(i, paramInt2);
      paramStaticLayout1.draw(paramCanvas);
      paramCanvas.translate(-i, -paramInt2);
      paramInt2 += paramStaticLayout1.getHeight();
    }
    if (paramClickableButton != null)
    {
      Rect localRect = paramClickableButton.getRect();
      localRect.offset(i - localRect.left, paramInt2 - localRect.top);
      paramClickableButton.draw(paramCanvas);
      paramInt2 += localRect.height();
    }
    if (paramStaticLayout2 != null)
    {
      paramCanvas.drawBitmap(paramBitmap, i, paramInt2 + (paramStaticLayout2.getHeight() - paramBitmap.getHeight()) / 2, null);
      int j = i + (paramBitmap.getWidth() + sIconHorizontalSpacing);
      paramCanvas.translate(j, paramInt2);
      paramStaticLayout2.draw(paramCanvas);
      paramCanvas.translate(-j, -paramInt2);
      paramStaticLayout2.getHeight();
    }
  }

  public static Paint getAppInviteTopAreaBackgroundPaint()
  {
    return sAppInviteTopAreaBackgroundPaint;
  }

  public static float getImageMaxWidthPercentage()
  {
    return sImageMaxWidthPercentage;
  }

  public static String getLinkTitle(Resources paramResources, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramBoolean2) && (!TextUtils.isEmpty(paramString1)) && (!paramBoolean1))
      if (!TextUtils.isEmpty(paramString2))
        paramString1 = paramResources.getString(R.string.stream_app_invite_title, new Object[] { paramString1, paramString2 });
    while (true)
    {
      return paramString1;
      if (!TextUtils.isEmpty(paramString3))
      {
        paramString1 = paramResources.getString(R.string.stream_app_invite_title, new Object[] { paramString1, paramString3 });
        continue;
        paramString1 = paramString2;
      }
    }
  }

  public static Paint getLinksTopAreaBackgroundPaint()
  {
    return sLinksTopAreaBackgroundPaint;
  }

  public static int getMaxImageDimension()
  {
    return sMaxImageDimension;
  }

  public static Paint getTransparentOverlayPaint()
  {
    return sTransparentOverlayPaint;
  }

  public static void init(Context paramContext)
  {
    if (!sLinksCardViewInitialized)
    {
      sLinksCardViewInitialized = true;
      Resources localResources = paramContext.getResources();
      sDeepLinkIcon = ImageUtils.decodeResource(localResources, R.drawable.ic_app_invite);
      Paint localPaint1 = new Paint();
      sTransparentOverlayPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.card_links_background_tint));
      Paint localPaint2 = new Paint();
      sLinksTopAreaBackgroundPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.solid_black));
      Paint localPaint3 = new Paint();
      sAppInviteTopAreaBackgroundPaint = localPaint3;
      localPaint3.setColor(localResources.getColor(R.color.card_app_invite_background));
      Paint localPaint4 = new Paint();
      sImageBorderPaint = localPaint4;
      localPaint4.setColor(localResources.getColor(R.color.card_links_image_border));
      sImageBorderPaint.setStyle(Paint.Style.STROKE);
      sImageBorderPaint.setStrokeWidth(localResources.getDimension(R.dimen.card_links_image_stroke_dimension));
      TextPaint localTextPaint1 = new TextPaint();
      sDeepLinkTextPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sDeepLinkTextPaint.setColor(localResources.getColor(R.color.card_not_plus_oned_text));
      sDeepLinkTextPaint.setTextSize(localResources.getDimension(R.dimen.card_plus_oned_text_size));
      sDeepLinkTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sDeepLinkTextPaint.linkColor = localResources.getColor(R.color.card_link);
      sDeepLinkTextPaint.setShadowLayer(localResources.getDimension(R.dimen.card_plus_oned_text_shadow_radius), localResources.getDimension(R.dimen.card_plus_oned_text_shadow_x), localResources.getDimension(R.dimen.card_plus_oned_text_shadow_y), localResources.getColor(R.color.card_not_plus_oned_shadow_text));
      TextPaintUtils.registerTextPaint(sDeepLinkTextPaint, R.dimen.card_plus_oned_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sLinkTitleTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sLinkTitleTextPaint.setColor(localResources.getColor(R.color.card_links_title_text));
      sLinkTitleTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sLinkTitleTextPaint.setTextSize(localResources.getDimension(R.dimen.card_links_title_text_size));
      sLinkTitleTextPaint.setShadowLayer(localResources.getDimension(R.dimen.card_links_title_text_shadow_radius), localResources.getDimension(R.dimen.card_links_title_text_shadow_x), localResources.getDimension(R.dimen.card_links_title_text_shadow_y), localResources.getColor(R.color.card_links_title_text_shadow));
      TextPaintUtils.registerTextPaint(sLinkTitleTextPaint, R.dimen.card_links_title_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      sLinkUrlTextPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sLinkUrlTextPaint.setColor(localResources.getColor(R.color.card_links_url_text));
      sLinkUrlTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sLinkUrlTextPaint.setTextSize(localResources.getDimension(R.dimen.card_links_url_text_size));
      sLinkUrlTextPaint.setShadowLayer(localResources.getDimension(R.dimen.card_links_url_text_shadow_radius), localResources.getDimension(R.dimen.card_links_url_text_shadow_x), localResources.getDimension(R.dimen.card_links_url_text_shadow_y), localResources.getColor(R.color.card_links_url_text_shadow));
      TextPaintUtils.registerTextPaint(sLinkUrlTextPaint, R.dimen.card_links_url_text_size);
      sImageMaxWidthPercentage = localResources.getDimension(R.dimen.card_links_image_max_width_percent);
      sMaxImageDimension = (int)localResources.getDimension(R.dimen.card_links_image_dimension);
      sHorizontalSpacing = (int)localResources.getDimension(R.dimen.card_links_x_padding);
      sImageHorizontalSpacing = (int)localResources.getDimension(R.dimen.card_links_image_x_padding);
      sIconHorizontalSpacing = (int)localResources.getDimension(R.dimen.card_links_icon_x_padding);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.LinksRenderUtils
 * JD-Core Version:    0.6.2
 */