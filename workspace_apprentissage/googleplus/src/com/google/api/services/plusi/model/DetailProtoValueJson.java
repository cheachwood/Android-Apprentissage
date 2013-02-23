package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DetailProtoValueJson extends EsJson<DetailProtoValue>
{
  static final DetailProtoValueJson INSTANCE = new DetailProtoValueJson();

  private DetailProtoValueJson()
  {
    super(DetailProtoValue.class, new Object[] { PlacePageLinkJson.class, "showLink", PlacePageLinkJson.class, "value" });
  }

  public static DetailProtoValueJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DetailProtoValueJson
 * JD-Core Version:    0.6.2
 */