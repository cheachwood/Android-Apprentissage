package com.google.android.apps.plus.content;

import android.text.TextUtils;
import com.google.api.services.plusi.model.AttributeProto;
import com.google.api.services.plusi.model.AttributeProtoCanonicalValue;
import com.google.api.services.plusi.model.FeaturedActivityProto;
import com.google.api.services.plusi.model.FrontendPaperProto;
import com.google.api.services.plusi.model.GoogleReviewProto;
import com.google.api.services.plusi.model.GoogleReviewsProto;
import com.google.api.services.plusi.model.LocalEntityInfo;
import com.google.api.services.plusi.model.OpeningHoursProto;
import com.google.api.services.plusi.model.OpeningHoursProtoDay;
import com.google.api.services.plusi.model.OpeningHoursProtoDayInterval;
import com.google.api.services.plusi.model.Page;
import com.google.api.services.plusi.model.PlaceActivityStreamEntryProto;
import com.google.api.services.plusi.model.PlaceInfo;
import com.google.api.services.plusi.model.PlacePageAddressProto;
import com.google.api.services.plusi.model.SimpleProfile;
import com.google.api.services.plusi.model.ZagatAspectRatingProto;
import com.google.api.services.plusi.model.ZagatAspectRatingsProto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class EsLocalPageData
{
  private static String buildOpeningHoursStringForADay(OpeningHoursProtoDay paramOpeningHoursProtoDay)
  {
    List localList = paramOpeningHoursProtoDay.interval;
    String str = null;
    if (localList != null)
    {
      boolean bool = paramOpeningHoursProtoDay.interval.isEmpty();
      str = null;
      if (!bool)
        break label29;
    }
    while (true)
    {
      return str;
      label29: StringBuilder localStringBuilder = new StringBuilder();
      int i = 1;
      Iterator localIterator = paramOpeningHoursProtoDay.interval.iterator();
      while (localIterator.hasNext())
      {
        OpeningHoursProtoDayInterval localOpeningHoursProtoDayInterval = (OpeningHoursProtoDayInterval)localIterator.next();
        if (!TextUtils.isEmpty(localOpeningHoursProtoDayInterval.value))
        {
          if (i == 0)
            localStringBuilder.append(" ");
          localStringBuilder.append(localOpeningHoursProtoDayInterval.value);
          i = 0;
        }
      }
      int j = localStringBuilder.length();
      str = null;
      if (j != 0)
      {
        if (!TextUtils.isEmpty(paramOpeningHoursProtoDay.dayName))
          localStringBuilder.insert(0, paramOpeningHoursProtoDay.dayName + " ");
        str = localStringBuilder.toString();
      }
    }
  }

  public static String getCid(SimpleProfile paramSimpleProfile)
  {
    if (paramSimpleProfile.page.localInfo.paper.placeInfo == null);
    for (String str = null; ; str = paramSimpleProfile.page.localInfo.paper.placeInfo.clusterId)
      return str;
  }

  private static FeaturedActivityProto getCircleActivityStory(SimpleProfile paramSimpleProfile)
  {
    return paramSimpleProfile.page.localInfo.paper.circleActivity;
  }

  public static List<GoogleReviewProto> getCircleReviews(SimpleProfile paramSimpleProfile)
  {
    ArrayList localArrayList = new ArrayList();
    FeaturedActivityProto localFeaturedActivityProto = getCircleActivityStory(paramSimpleProfile);
    if (hasCircleActivity(paramSimpleProfile))
    {
      Iterator localIterator = localFeaturedActivityProto.activity.iterator();
      while (localIterator.hasNext())
      {
        PlaceActivityStreamEntryProto localPlaceActivityStreamEntryProto = (PlaceActivityStreamEntryProto)localIterator.next();
        if (localPlaceActivityStreamEntryProto.review != null)
          localArrayList.add(localPlaceActivityStreamEntryProto.review);
      }
    }
    return localArrayList;
  }

  public static String getFullAddress(SimpleProfile paramSimpleProfile)
  {
    PlacePageAddressProto localPlacePageAddressProto = paramSimpleProfile.page.localInfo.paper.address;
    String str = null;
    if (localPlacePageAddressProto == null);
    while (true)
    {
      return str;
      List localList = paramSimpleProfile.page.localInfo.paper.address.addressLine;
      str = null;
      if (localList != null)
      {
        int i = localList.size();
        str = null;
        if (i != 0)
        {
          StringBuffer localStringBuffer = new StringBuffer();
          localStringBuffer.append((String)localList.get(0));
          if (localList.size() > 1)
            localStringBuffer.append("\n").append((String)localList.get(1));
          str = localStringBuffer.toString();
        }
      }
    }
  }

  public static String getOpeningHoursFull(SimpleProfile paramSimpleProfile)
  {
    OpeningHoursProto localOpeningHoursProto = paramSimpleProfile.page.localInfo.paper.openingHours;
    String str1 = null;
    if (localOpeningHoursProto != null)
    {
      List localList = localOpeningHoursProto.day;
      str1 = null;
      if (localList != null)
      {
        boolean bool = localOpeningHoursProto.day.isEmpty();
        str1 = null;
        if (!bool)
          break label51;
      }
    }
    while (true)
    {
      return str1;
      label51: StringBuilder localStringBuilder = new StringBuilder();
      int i = 1;
      Iterator localIterator = localOpeningHoursProto.day.iterator();
      while (localIterator.hasNext())
      {
        String str2 = buildOpeningHoursStringForADay((OpeningHoursProtoDay)localIterator.next());
        if (str2 != null)
        {
          if (i == 0)
            localStringBuilder.append("\n");
          localStringBuilder.append(str2);
          i = 0;
        }
      }
      int j = localStringBuilder.length();
      str1 = null;
      if (j != 0)
        str1 = localStringBuilder.toString();
    }
  }

  public static String getOpeningHoursSummary(SimpleProfile paramSimpleProfile)
  {
    OpeningHoursProto localOpeningHoursProto = paramSimpleProfile.page.localInfo.paper.openingHours;
    if ((localOpeningHoursProto == null) || (localOpeningHoursProto.today == null));
    for (String str = null; ; str = buildOpeningHoursStringForADay(localOpeningHoursProto.today))
      return str;
  }

  public static String getPriceLabel(SimpleProfile paramSimpleProfile)
  {
    AttributeProto localAttributeProto = getPriceStory(paramSimpleProfile);
    if (localAttributeProto != null);
    for (String str = localAttributeProto.labelDisplay; ; str = null)
      return str;
  }

  private static AttributeProto getPriceStory(SimpleProfile paramSimpleProfile)
  {
    if (paramSimpleProfile.page.localInfo.paper.priceContinuous != null);
    for (AttributeProto localAttributeProto = paramSimpleProfile.page.localInfo.paper.priceContinuous; ; localAttributeProto = paramSimpleProfile.page.localInfo.paper.price)
      return localAttributeProto;
  }

  public static String getPriceValue(SimpleProfile paramSimpleProfile)
  {
    AttributeProto localAttributeProto = getPriceStory(paramSimpleProfile);
    if (localAttributeProto != null);
    for (String str = localAttributeProto.value.priceLevel; ; str = null)
      return str;
  }

  public static List<GoogleReviewProto> getReviews(SimpleProfile paramSimpleProfile)
  {
    ArrayList localArrayList = new ArrayList();
    GoogleReviewsProto localGoogleReviewsProto = paramSimpleProfile.page.localInfo.paper.googleReviews;
    if ((localGoogleReviewsProto != null) && (localGoogleReviewsProto.review != null) && (localGoogleReviewsProto.review.size() > 0));
    for (int i = 1; i != 0; i = 0)
    {
      Iterator localIterator = localGoogleReviewsProto.review.iterator();
      while (localIterator.hasNext())
        localArrayList.add((GoogleReviewProto)localIterator.next());
    }
    return localArrayList;
  }

  public static FeaturedActivityProto getUserActivityStory(SimpleProfile paramSimpleProfile)
  {
    return paramSimpleProfile.page.localInfo.paper.userActivity;
  }

  public static GoogleReviewProto getYourReview(SimpleProfile paramSimpleProfile)
  {
    FeaturedActivityProto localFeaturedActivityProto = getUserActivityStory(paramSimpleProfile);
    PlaceActivityStreamEntryProto localPlaceActivityStreamEntryProto;
    if (hasYourActivity(paramSimpleProfile))
    {
      Iterator localIterator = localFeaturedActivityProto.activity.iterator();
      do
      {
        if (!localIterator.hasNext())
          break;
        localPlaceActivityStreamEntryProto = (PlaceActivityStreamEntryProto)localIterator.next();
      }
      while (localPlaceActivityStreamEntryProto.review == null);
    }
    for (GoogleReviewProto localGoogleReviewProto = localPlaceActivityStreamEntryProto.review; ; localGoogleReviewProto = null)
      return localGoogleReviewProto;
  }

  public static List<ZagatAspectRatingProto> getZagatAspects(GoogleReviewProto paramGoogleReviewProto)
  {
    if ((paramGoogleReviewProto == null) || (paramGoogleReviewProto.zagatAspectRatings == null));
    for (Object localObject = null; ; localObject = paramGoogleReviewProto.zagatAspectRatings.aspectRating)
      return localObject;
  }

  private static boolean hasActivity(FeaturedActivityProto paramFeaturedActivityProto)
  {
    if ((paramFeaturedActivityProto != null) && (paramFeaturedActivityProto.totalReviews != null) && (paramFeaturedActivityProto.totalReviews.intValue() > 0) && (paramFeaturedActivityProto.activity != null));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static boolean hasCircleActivity(SimpleProfile paramSimpleProfile)
  {
    return hasActivity(getCircleActivityStory(paramSimpleProfile));
  }

  public static boolean hasYourActivity(SimpleProfile paramSimpleProfile)
  {
    return hasActivity(getUserActivityStory(paramSimpleProfile));
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsLocalPageData
 * JD-Core Version:    0.6.2
 */