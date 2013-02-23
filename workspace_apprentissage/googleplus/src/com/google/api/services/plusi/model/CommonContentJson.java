package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CommonContentJson extends EsJson<CommonContent>
{
  static final CommonContentJson INSTANCE = new CommonContentJson();

  private CommonContentJson()
  {
    super(CommonContent.class, new Object[] { ContactsJson.class, "contacts", StringFieldJson.class, "introduction", LinksJson.class, "links", NickNameJson.class, "nickname", "photoUrl", ScrapBookJson.class, "scrapbook", ScrapbookInfoJson.class, "scrapbookInfo", StringFieldJson.class, "tagLine" });
  }

  public static CommonContentJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CommonContentJson
 * JD-Core Version:    0.6.2
 */