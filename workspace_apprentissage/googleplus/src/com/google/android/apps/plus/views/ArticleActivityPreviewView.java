package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.network.ApiaryActivity;
import com.google.android.apps.plus.network.ApiaryArticleActivity;
import java.util.List;

public class ArticleActivityPreviewView extends ActivityPreviewView
{
  private static final int PREVIEW_ARTICLE_LAYOUT = R.layout.share_preview_article_view;

  public ArticleActivityPreviewView(Context paramContext)
  {
    super(paramContext, null, 0);
  }

  public ArticleActivityPreviewView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 0);
  }

  public ArticleActivityPreviewView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void setActivity(ApiaryActivity paramApiaryActivity)
  {
    super.setActivity(paramApiaryActivity);
    ApiaryArticleActivity localApiaryArticleActivity = (ApiaryArticleActivity)getActivity();
    removeAllViews();
    View localView = LayoutInflater.from(getContext()).inflate(PREVIEW_ARTICLE_LAYOUT, null);
    addView(localView);
    EsImageView localEsImageView1 = (EsImageView)localView.findViewById(R.id.article_icon);
    TextView localTextView1 = (TextView)localView.findViewById(R.id.article_title);
    TextView localTextView2 = (TextView)localView.findViewById(R.id.article_content);
    EsImageView localEsImageView2 = (EsImageView)localView.findViewById(R.id.article_image);
    String str1 = localApiaryArticleActivity.getDisplayName();
    String str2 = localApiaryArticleActivity.getContent();
    String str3 = localApiaryArticleActivity.getFavIconUrl();
    if (!localApiaryArticleActivity.getImages().isEmpty());
    for (String str4 = (String)localApiaryArticleActivity.getImages().get(0); ; str4 = null)
    {
      if ((str1 != null) && (!str1.equals("")))
      {
        localTextView1.setVisibility(0);
        localTextView1.setText(str1);
        if ((str2 == null) || (str2.equals("")))
          break label266;
        localTextView2.setVisibility(0);
        localTextView2.setText(str2);
        label186: if ((str3 == null) || (str3.equals("")))
          break label276;
        localEsImageView1.setVisibility(0);
        localEsImageView1.setUrl(str3);
        label214: if ((str4 == null) || (str4.equals("")))
          break label286;
        localEsImageView2.setVisibility(0);
        localEsImageView2.setUrl(str4);
      }
      while (true)
      {
        localView.setVisibility(0);
        invalidate();
        requestLayout();
        return;
        localTextView1.setVisibility(8);
        break;
        label266: localTextView2.setVisibility(8);
        break label186;
        label276: localEsImageView1.setVisibility(8);
        break label214;
        label286: localEsImageView2.setVisibility(8);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ArticleActivityPreviewView
 * JD-Core Version:    0.6.2
 */