package com.google.android.apps.plus.content;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.api.services.plusi.model.GoogleReviewProto;
import com.google.api.services.plusi.model.PriceLevelsProto;
import com.google.api.services.plusi.model.PriceProto;
import com.google.api.services.plusi.model.ZagatAspectRatingProto;
import com.google.api.services.plusi.model.ZagatAspectRatingsProto;
import java.util.Iterator;
import java.util.List;

public class GooglePlaceReview
  implements Parcelable
{
  public static final Parcelable.Creator<GooglePlaceReview> CREATOR = new Parcelable.Creator()
  {
  };
  private String priceCurrencyCode;
  private long priceLevelValueId;
  private String priceValue;
  private String reviewText;
  private Bundle zagatAspects;

  private GooglePlaceReview(Parcel paramParcel)
  {
    this.zagatAspects = paramParcel.readBundle();
    this.reviewText = paramParcel.readString();
    this.priceValue = paramParcel.readString();
    this.priceCurrencyCode = paramParcel.readString();
    this.priceLevelValueId = paramParcel.readLong();
  }

  public GooglePlaceReview(GoogleReviewProto paramGoogleReviewProto)
  {
    this.zagatAspects = new Bundle();
    Iterator localIterator = paramGoogleReviewProto.zagatAspectRatings.aspectRating.iterator();
    while (localIterator.hasNext())
    {
      ZagatAspectRatingProto localZagatAspectRatingProto = (ZagatAspectRatingProto)localIterator.next();
      String str4 = localZagatAspectRatingProto.labelId;
      String str5 = localZagatAspectRatingProto.valueDisplay;
      this.zagatAspects.putString(str4, str5);
    }
    Object localObject = paramGoogleReviewProto.fullText;
    String str1 = paramGoogleReviewProto.snippet;
    if ((localObject == null) || (((String)localObject).isEmpty()))
      localObject = str1;
    this.reviewText = ((String)localObject);
    PriceProto localPriceProto = paramGoogleReviewProto.price;
    String str2;
    String str3;
    label134: PriceLevelsProto localPriceLevelsProto;
    if (localPriceProto == null)
    {
      str2 = null;
      this.priceValue = str2;
      str3 = null;
      if (localPriceProto != null)
        break label179;
      this.priceCurrencyCode = str3;
      localPriceLevelsProto = paramGoogleReviewProto.priceLevel;
      if ((localPriceLevelsProto != null) && (localPriceLevelsProto.ratedValueId != null))
        break label189;
    }
    label179: label189: for (long l = 0L; ; l = localPriceLevelsProto.ratedValueId.longValue())
    {
      this.priceLevelValueId = l;
      return;
      str2 = localPriceProto.valueDisplay;
      break;
      str3 = localPriceProto.currencyCode;
      break label134;
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public final String getPriceCurrencyCode()
  {
    return this.priceCurrencyCode;
  }

  public final Long getPriceLevelId()
  {
    return Long.valueOf(this.priceLevelValueId);
  }

  public final String getPriceValue()
  {
    return this.priceValue;
  }

  public final String getReviewText()
  {
    return this.reviewText;
  }

  public final Bundle getZagatAspects()
  {
    return this.zagatAspects;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeBundle(this.zagatAspects);
    paramParcel.writeString(this.reviewText);
    paramParcel.writeString(this.priceValue);
    paramParcel.writeString(this.priceCurrencyCode);
    paramParcel.writeLong(this.priceLevelValueId);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.GooglePlaceReview
 * JD-Core Version:    0.6.2
 */