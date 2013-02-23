package com.google.android.apps.plus.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextPaint;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;

public final class PlusBarUtils
{
  public static NinePatchDrawable sButtonDrawable;
  public static NinePatchDrawable sButtonPressedDrawable;
  private static boolean sInitialized;
  public static TextPaint sInteractivePostButtonTextPaint;
  public static TextPaint sNotPlusOnedTextPaint;
  public static int sPlusBarXPadding;
  public static NinePatchDrawable sPlusOnedDrawable;
  public static NinePatchDrawable sPlusOnedPressedDrawable;
  public static TextPaint sPlusOnedTextPaint;

  public static void init(Context paramContext)
  {
    if (!sInitialized)
    {
      sInitialized = true;
      Resources localResources = paramContext.getResources();
      sButtonDrawable = (NinePatchDrawable)localResources.getDrawable(R.drawable.btn_default_gray);
      sButtonPressedDrawable = (NinePatchDrawable)localResources.getDrawable(R.drawable.btn_default_gray_pressed);
      sPlusOnedDrawable = (NinePatchDrawable)localResources.getDrawable(R.drawable.btn_plusone_red);
      sPlusOnedPressedDrawable = (NinePatchDrawable)localResources.getDrawable(R.drawable.btn_plusone_red_pressed);
      TextPaint localTextPaint1 = new TextPaint();
      sNotPlusOnedTextPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sNotPlusOnedTextPaint.setColor(localResources.getColor(R.color.card_not_plus_oned_text));
      sNotPlusOnedTextPaint.setTextSize(localResources.getDimension(R.dimen.card_plus_oned_text_size));
      sNotPlusOnedTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sNotPlusOnedTextPaint.linkColor = localResources.getColor(R.color.card_link);
      sNotPlusOnedTextPaint.setShadowLayer(localResources.getDimension(R.dimen.card_plus_oned_text_shadow_radius), localResources.getDimension(R.dimen.card_plus_oned_text_shadow_x), localResources.getDimension(R.dimen.card_plus_oned_text_shadow_y), localResources.getColor(R.color.card_not_plus_oned_shadow_text));
      TextPaintUtils.registerTextPaint(sNotPlusOnedTextPaint, R.dimen.card_plus_oned_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sPlusOnedTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sPlusOnedTextPaint.setColor(localResources.getColor(R.color.card_plus_oned_text));
      sPlusOnedTextPaint.setTextSize(localResources.getDimension(R.dimen.card_plus_oned_text_size));
      sPlusOnedTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sPlusOnedTextPaint.linkColor = localResources.getColor(R.color.card_link);
      TextPaintUtils.registerTextPaint(sPlusOnedTextPaint, R.dimen.card_plus_oned_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      sInteractivePostButtonTextPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sInteractivePostButtonTextPaint.setColor(localResources.getColor(R.color.card_interactive_post_button_text));
      sInteractivePostButtonTextPaint.setTextSize(localResources.getDimension(R.dimen.card_interactive_post_button_text_size));
      sInteractivePostButtonTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sInteractivePostButtonTextPaint.linkColor = localResources.getColor(R.color.card_link);
      TextPaintUtils.registerTextPaint(sInteractivePostButtonTextPaint, R.dimen.card_interactive_post_button_text_size);
      sPlusBarXPadding = (int)localResources.getDimension(R.dimen.card_plus_bar_x_padding);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.PlusBarUtils
 * JD-Core Version:    0.6.2
 */