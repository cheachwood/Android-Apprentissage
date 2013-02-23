package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NamespaceSpecificDataJson extends EsJson<NamespaceSpecificData>
{
  static final NamespaceSpecificDataJson INSTANCE = new NamespaceSpecificDataJson();

  private NamespaceSpecificDataJson()
  {
    super(NamespaceSpecificData.class, new Object[] { A2aDataJson.class, "a2aData", BirthdayDataJson.class, "birthdayData", CarouselFrameDataJson.class, "carouselFrameData", CircleSharingDataJson.class, "circlesharingData", EventActivityDataJson.class, "eventActivityData", EventCommentJson.class, "eventCommentData", EventFrameJson.class, "eventFrameData", EventPhotoJson.class, "eventPhotoData", InterestDataJson.class, "interestData", PhotoDataJson.class, "photoData", SharedPlusOneJson.class, "plusoneData", PlusOnePostDataJson.class, "plusonePostData", PlusPageDataJson.class, "pluspageData", SearchSharingDataJson.class, "searchSharingData" });
  }

  public static NamespaceSpecificDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NamespaceSpecificDataJson
 * JD-Core Version:    0.6.2
 */