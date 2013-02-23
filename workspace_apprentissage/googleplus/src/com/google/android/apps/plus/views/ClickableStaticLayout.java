package com.google.android.apps.plus.views;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.text.Html;
import android.text.Html.TagHandler;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.text.style.URLSpan;
import com.google.android.apps.plus.util.TextPaintUtils;

public final class ClickableStaticLayout extends PositionedStaticLayout
  implements ClickableItem
{
  private final SpanClickListener mClickListener;
  private StateURLSpan mClickedSpan;
  private CharSequence mContentDescription;
  private final Spanned mSpannedText;

  public ClickableStaticLayout(CharSequence paramCharSequence, TextPaint paramTextPaint, int paramInt, Layout.Alignment paramAlignment, float paramFloat1, float paramFloat2, boolean paramBoolean, SpanClickListener paramSpanClickListener)
  {
    super(paramCharSequence, paramTextPaint, paramInt, paramAlignment, paramFloat1, 0.0F, false);
    this.mClickListener = paramSpanClickListener;
    this.mContentDescription = paramCharSequence;
    if ((paramCharSequence instanceof Spanned));
    for (this.mSpannedText = ((Spanned)paramCharSequence); ; this.mSpannedText = null)
      return;
  }

  private static SpannableStringBuilder buildStateSpans(Context paramContext, String paramString, Html.TagHandler paramTagHandler, int paramInt, boolean paramBoolean)
  {
    SpannableStringBuilder localSpannableStringBuilder;
    if (paramString == null)
      localSpannableStringBuilder = new SpannableStringBuilder();
    while (true)
    {
      return localSpannableStringBuilder;
      Spanned localSpanned = Html.fromHtml(paramString, null, paramTagHandler);
      localSpannableStringBuilder = new SpannableStringBuilder(localSpanned);
      URLSpan[] arrayOfURLSpan = (URLSpan[])localSpannableStringBuilder.getSpans(0, localSpanned.length(), URLSpan.class);
      for (int i = 0; i < arrayOfURLSpan.length; i++)
      {
        URLSpan localURLSpan = arrayOfURLSpan[i];
        localSpannableStringBuilder.setSpan(new StateURLSpan(localURLSpan.getURL()), localSpannableStringBuilder.getSpanStart(localURLSpan), localSpannableStringBuilder.getSpanEnd(localURLSpan), localSpannableStringBuilder.getSpanFlags(localURLSpan));
        localSpannableStringBuilder.removeSpan(localURLSpan);
      }
    }
  }

  public static SpannableStringBuilder buildStateSpans(String paramString)
  {
    return buildStateSpans(null, paramString, null, -1, false);
  }

  public static SpannableStringBuilder buildStateSpans(String paramString, Html.TagHandler paramTagHandler)
  {
    return buildStateSpans(null, paramString, paramTagHandler, -1, false);
  }

  public static ClickableStaticLayout createConstrainedLayout(TextPaint paramTextPaint, CharSequence paramCharSequence, int paramInt1, int paramInt2, SpanClickListener paramSpanClickListener)
  {
    int i = Math.max(paramInt1, 0);
    Object localObject;
    if (paramInt2 == 0)
      localObject = "";
    while (true)
    {
      ClickableStaticLayout localClickableStaticLayout = new ClickableStaticLayout((CharSequence)localObject, paramTextPaint, i, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false, paramSpanClickListener);
      do
      {
        return localClickableStaticLayout;
        if (paramInt2 == 1)
        {
          localObject = TextPaintUtils.smartEllipsize(paramCharSequence, paramTextPaint, i, TextUtils.TruncateAt.END);
          break;
        }
        localClickableStaticLayout = new ClickableStaticLayout(paramCharSequence, paramTextPaint, i, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false, paramSpanClickListener);
      }
      while (localClickableStaticLayout.getLineCount() <= paramInt2);
      int j = localClickableStaticLayout.getLineEnd(paramInt2 - 2);
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(paramCharSequence.subSequence(0, j));
      localSpannableStringBuilder.append(TextPaintUtils.smartEllipsize(paramCharSequence.subSequence(j, paramCharSequence.length()), paramTextPaint, i, TextUtils.TruncateAt.END));
      localObject = localSpannableStringBuilder;
    }
  }

  public final CharSequence getContentDescription()
  {
    return this.mContentDescription;
  }

  public final Rect getRect()
  {
    return this.mContentArea;
  }

  public final boolean handleEvent(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 1;
    if (paramInt3 == 3)
      if (this.mClickedSpan != null)
      {
        this.mClickedSpan.setClicked(false);
        this.mClickedSpan = null;
      }
    while (true)
    {
      return i;
      if (this.mSpannedText == null)
      {
        i = 0;
      }
      else if (!this.mContentArea.contains(paramInt1, paramInt2))
      {
        if ((paramInt3 == i) && (this.mClickedSpan != null))
        {
          this.mClickedSpan.setClicked(false);
          this.mClickedSpan = null;
        }
        i = 0;
      }
      else
      {
        float f1 = paramInt1 - this.mContentArea.left;
        float f2 = Math.max(0.0F, paramInt2 - this.mContentArea.top);
        int j = getLineForVertical((int)Math.min(-1 + getHeight(), f2));
        float f3 = Math.max(0.0F, f1);
        int k = getOffsetForHorizontal(j, Math.min(-1 + getWidth(), f3));
        if (k < 0)
        {
          i = 0;
        }
        else
        {
          StateURLSpan[] arrayOfStateURLSpan = (StateURLSpan[])this.mSpannedText.getSpans(k, k, StateURLSpan.class);
          if (arrayOfStateURLSpan.length == 0)
            i = 0;
          else
            switch (paramInt3)
            {
            default:
              break;
            case 0:
              this.mClickedSpan = arrayOfStateURLSpan[0];
              this.mClickedSpan.setClicked(i);
              break;
            case 1:
              if ((this.mClickedSpan == arrayOfStateURLSpan[0]) && (this.mClickListener != null))
                this.mClickListener.onSpanClick(arrayOfStateURLSpan[0]);
              if (this.mClickedSpan != null)
              {
                this.mClickedSpan.setClicked(false);
                this.mClickedSpan = null;
              }
              break;
            }
        }
      }
    }
  }

  public static abstract interface SpanClickListener
  {
    public abstract void onSpanClick(URLSpan paramURLSpan);
  }

  public static final class StateURLSpan extends URLSpan
  {
    private int mBgColor;
    private boolean mClicked;
    private boolean mFirstTime = true;

    public StateURLSpan(String paramString)
    {
      super();
    }

    public final void setClicked(boolean paramBoolean)
    {
      this.mClicked = paramBoolean;
    }

    public final void updateDrawState(TextPaint paramTextPaint)
    {
      if (this.mFirstTime)
      {
        this.mFirstTime = false;
        this.mBgColor = paramTextPaint.bgColor;
      }
      if (this.mClicked)
        if (Build.VERSION.SDK_INT >= 13)
          paramTextPaint.bgColor = -13388315;
      while (true)
      {
        paramTextPaint.setColor(paramTextPaint.linkColor);
        paramTextPaint.setUnderlineText(false);
        return;
        paramTextPaint.bgColor = -32768;
        continue;
        paramTextPaint.bgColor = this.mBgColor;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ClickableStaticLayout
 * JD-Core Version:    0.6.2
 */