package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import java.util.ArrayList;
import java.util.List;

public class EventDetailsOptionTitleDescription extends ExactLayout
{
  private static int sDescriptionColor;
  private static float sDescriptionSize;
  private static boolean sInitialized;
  private static int sTitleColor;
  private static float sTitleSize;
  private int mActiveDescriptionCount;
  private AttributeSet mAttrs;
  private Context mContext;
  private int mDefStyle;
  private ArrayList<TextView> mDescriptionViews = new ArrayList();
  private TextView mTitle;

  public EventDetailsOptionTitleDescription(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }

  public EventDetailsOptionTitleDescription(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }

  public EventDetailsOptionTitleDescription(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this.mContext = paramContext;
    this.mAttrs = paramAttributeSet;
    this.mDefStyle = paramInt;
    Resources localResources = paramContext.getResources();
    if (!sInitialized)
    {
      sTitleColor = localResources.getColor(R.color.event_card_details_option_title_color);
      sTitleSize = localResources.getDimension(R.dimen.event_card_details_option_title_size);
      sDescriptionColor = localResources.getColor(R.color.event_card_details_option_description_color);
      sDescriptionSize = localResources.getDimension(R.dimen.event_card_details_option_description_size);
      sInitialized = true;
    }
    this.mTitle = TextViewUtils.createText(paramContext, paramAttributeSet, paramInt, sTitleSize, sTitleColor, true, true);
    addView(this.mTitle);
  }

  public final void bind(String paramString1, String paramString2)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramString1 == null)
      paramString1 = paramString2;
    while (true)
    {
      bind(paramString1, localArrayList);
      return;
      localArrayList.add(paramString2);
    }
  }

  public final void bind(String paramString, List<String> paramList)
  {
    this.mTitle.setText(paramString);
    TextView localTextView1 = this.mTitle;
    if (TextUtils.isEmpty(this.mTitle.getText()));
    for (int i = 8; ; i = 0)
    {
      localTextView1.setVisibility(i);
      for (int j = -1 + this.mDescriptionViews.size(); j >= 0; j--)
        removeView((View)this.mDescriptionViews.get(j));
    }
    this.mActiveDescriptionCount = 0;
    if (paramList != null)
    {
      int k = paramList.size();
      for (int m = 0; m < k; m++)
        if (!TextUtils.isEmpty((String)paramList.get(m)))
        {
          if (m > -1 + this.mDescriptionViews.size())
            this.mDescriptionViews.add(TextViewUtils.createText(this.mContext, this.mAttrs, this.mDefStyle, sDescriptionSize, sDescriptionColor, false, true));
          TextView localTextView2 = (TextView)this.mDescriptionViews.get(m);
          localTextView2.setText((CharSequence)paramList.get(m));
          addView(localTextView2);
          this.mActiveDescriptionCount = (1 + this.mActiveDescriptionCount);
        }
    }
  }

  public final void clear()
  {
    this.mTitle.setText(null);
    for (int i = -1 + this.mDescriptionViews.size(); i >= 0; i--)
      removeView((View)this.mDescriptionViews.get(i));
    this.mActiveDescriptionCount = 0;
  }

  protected void measureChildren(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    measure(this.mTitle, i, -2147483648, j, 0);
    setCorner(this.mTitle, 0, 0);
    if (TextUtils.isEmpty(this.mTitle.getText()));
    for (int k = 0; ; k = this.mTitle.getMeasuredHeight())
    {
      int m = k + 0;
      for (int n = 0; n < this.mActiveDescriptionCount; n++)
      {
        TextView localTextView = (TextView)this.mDescriptionViews.get(n);
        measure(localTextView, i, -2147483648, j, 0);
        setCorner(localTextView, 0, m);
        m += localTextView.getMeasuredHeight();
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventDetailsOptionTitleDescription
 * JD-Core Version:    0.6.2
 */