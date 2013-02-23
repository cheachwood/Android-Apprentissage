package com.google.android.apps.plus.views;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.SuperscriptSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;

public class SectionHeaderView extends RelativeLayout
{
  private static final SuperscriptSpan SUPERSCRIPT_SPAN = new SuperscriptSpan()
  {
    public final void updateDrawState(TextPaint paramAnonymousTextPaint)
    {
      paramAnonymousTextPaint.baselineShift += (int)(paramAnonymousTextPaint.ascent() / 4.0F);
    }

    public final void updateMeasureState(TextPaint paramAnonymousTextPaint)
    {
      paramAnonymousTextPaint.baselineShift += (int)(paramAnonymousTextPaint.ascent() / 4.0F);
    }
  };

  public SectionHeaderView(Context paramContext)
  {
    super(paramContext);
  }

  public SectionHeaderView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public SectionHeaderView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private static void adjustSuperscriptSpans(SpannableStringBuilder paramSpannableStringBuilder)
  {
    SuperscriptSpan[] arrayOfSuperscriptSpan = (SuperscriptSpan[])paramSpannableStringBuilder.getSpans(0, paramSpannableStringBuilder.length(), SuperscriptSpan.class);
    if (arrayOfSuperscriptSpan != null)
      for (int i = 0; i < arrayOfSuperscriptSpan.length; i++)
      {
        SuperscriptSpan localSuperscriptSpan = arrayOfSuperscriptSpan[i];
        int j = paramSpannableStringBuilder.getSpanStart(localSuperscriptSpan);
        int k = paramSpannableStringBuilder.getSpanEnd(localSuperscriptSpan);
        int m = paramSpannableStringBuilder.getSpanFlags(localSuperscriptSpan);
        paramSpannableStringBuilder.removeSpan(localSuperscriptSpan);
        paramSpannableStringBuilder.setSpan(SUPERSCRIPT_SPAN, j, k, m);
      }
  }

  public final void enableEditIcon(boolean paramBoolean)
  {
    View localView = findViewById(R.id.edit);
    if (paramBoolean);
    for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }

  public void setText(int paramInt)
  {
    setText(getContext().getText(paramInt));
  }

  public void setText(CharSequence paramCharSequence)
  {
    TextView localTextView = (TextView)findViewById(16908308);
    String str = paramCharSequence.toString().toUpperCase();
    if ((paramCharSequence instanceof SpannedString))
    {
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(str);
      TextUtils.copySpansFrom((SpannedString)paramCharSequence, 0, paramCharSequence.length(), Object.class, localSpannableStringBuilder, 0);
      if (Build.VERSION.SDK_INT < 14)
        adjustSuperscriptSpans(localSpannableStringBuilder);
      localTextView.setText(localSpannableStringBuilder);
    }
    while (true)
    {
      return;
      localTextView.setText(str);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SectionHeaderView
 * JD-Core Version:    0.6.2
 */