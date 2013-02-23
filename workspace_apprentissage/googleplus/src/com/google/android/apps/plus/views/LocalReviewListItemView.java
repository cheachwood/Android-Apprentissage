package com.google.android.apps.plus.views;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.util.SpannableUtils;
import com.google.api.services.plusi.model.AuthorProto;
import com.google.api.services.plusi.model.GoogleReviewProto;
import com.google.api.services.plusi.model.PlacePageLink;
import com.google.api.services.plusi.model.ZagatAspectRatingProto;
import com.google.api.services.plusi.model.ZagatAspectRatingsProto;
import java.util.List;

public class LocalReviewListItemView extends RelativeLayout
{
  private AvatarView mAuthorAvatar;
  private TextView mAuthorName;
  private boolean mIsFullText;
  private TextView mPublishDate;
  private TextView mRatingAspects;
  private TextView mReviewText;
  private View mTopBorder;

  public LocalReviewListItemView(Context paramContext)
  {
    this(paramContext, null);
  }

  public LocalReviewListItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public void onFinishInflate()
  {
    this.mTopBorder = findViewById(R.id.top_border);
    this.mAuthorAvatar = ((AvatarView)findViewById(R.id.author_avatar));
    this.mAuthorName = ((TextView)findViewById(R.id.author_name));
    this.mPublishDate = ((TextView)findViewById(R.id.publish_date));
    this.mRatingAspects = ((TextView)findViewById(R.id.rating_aspects));
    this.mReviewText = ((TextView)findViewById(R.id.review_text));
  }

  public void setAuthorAvatarOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mAuthorAvatar.setOnClickListener(paramOnClickListener);
  }

  public void setIsFullText(boolean paramBoolean)
  {
    this.mIsFullText = paramBoolean;
  }

  public void setReview(GoogleReviewProto paramGoogleReviewProto)
  {
    label87: label115: List localList;
    if (paramGoogleReviewProto.author != null)
    {
      AuthorProto localAuthorProto1 = paramGoogleReviewProto.author;
      if (!TextUtils.isEmpty(localAuthorProto1.profileId))
      {
        this.mAuthorAvatar.setGaiaId(localAuthorProto1.profileId);
        AuthorProto localAuthorProto2 = paramGoogleReviewProto.author;
        if ((localAuthorProto2.profileLink == null) || (TextUtils.isEmpty(localAuthorProto2.profileLink.text)))
          break label340;
        this.mAuthorName.setVisibility(0);
        this.mAuthorName.setText(localAuthorProto2.profileLink.text);
      }
    }
    else
    {
      String str1 = paramGoogleReviewProto.publishDate;
      if (TextUtils.isEmpty(str1))
        break label352;
      this.mPublishDate.setVisibility(0);
      this.mPublishDate.setText(str1);
      if ((paramGoogleReviewProto.zagatAspectRatings == null) || (paramGoogleReviewProto.zagatAspectRatings.aspectRating == null) || (paramGoogleReviewProto.zagatAspectRatings.aspectRating.size() <= 0))
        break label364;
      localList = paramGoogleReviewProto.zagatAspectRatings.aspectRating;
    }
    while (true)
      label155: if (localList != null)
      {
        SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
        int i = 0;
        while (true)
          if (i < localList.size())
          {
            ZagatAspectRatingProto localZagatAspectRatingProto = (ZagatAspectRatingProto)localList.get(i);
            if ((!TextUtils.isEmpty(localZagatAspectRatingProto.labelDisplay)) && (!TextUtils.isEmpty(localZagatAspectRatingProto.valueDisplay)))
            {
              SpannableUtils.appendWithSpan(localSpannableStringBuilder, localZagatAspectRatingProto.labelDisplay, new TextAppearanceSpan(getContext(), R.style.ProfileLocalUserRating_AspectLabel));
              localSpannableStringBuilder.append(" ");
              SpannableUtils.appendWithSpan(localSpannableStringBuilder, localZagatAspectRatingProto.valueDisplay, new TextAppearanceSpan(getContext(), R.style.ProfileLocalUserRating_AspectValue));
              SpannableUtils.appendWithSpan(localSpannableStringBuilder, " / 3", new TextAppearanceSpan(getContext(), R.style.ProfileLocalUserRating_AspectExplanation));
              if (i != -1 + localList.size())
                localSpannableStringBuilder.append("  ");
            }
            i++;
            continue;
            this.mAuthorAvatar.setGaiaId(null);
            this.mAuthorAvatar.setOnClickListener(null);
            break;
            label340: this.mAuthorName.setVisibility(8);
            break label87;
            label352: this.mPublishDate.setVisibility(8);
            break label115;
            label364: localList = null;
            break label155;
          }
        if (localSpannableStringBuilder.length() > 0)
        {
          this.mRatingAspects.setVisibility(0);
          this.mRatingAspects.setText(localSpannableStringBuilder);
          String str2 = paramGoogleReviewProto.snippet;
          if ((this.mIsFullText) && (!TextUtils.isEmpty(paramGoogleReviewProto.fullText)))
            str2 = paramGoogleReviewProto.fullText;
          if (TextUtils.isEmpty(str2))
            break label484;
          this.mReviewText.setVisibility(0);
          String str3 = str2.replaceAll("\\<.*?>", "");
          this.mReviewText.setText(str3);
        }
      }
    while (true)
    {
      return;
      this.mRatingAspects.setVisibility(8);
      break;
      this.mRatingAspects.setVisibility(8);
      break;
      label484: this.mReviewText.setVisibility(8);
    }
  }

  public void setTopBorderVisible(boolean paramBoolean)
  {
    if (paramBoolean)
      this.mTopBorder.setVisibility(0);
    while (true)
    {
      return;
      this.mTopBorder.setVisibility(8);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.LocalReviewListItemView
 * JD-Core Version:    0.6.2
 */