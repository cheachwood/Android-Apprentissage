package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DetailProtoJson extends EsJson<DetailProto>
{
  static final DetailProtoJson INSTANCE = new DetailProtoJson();

  private DetailProtoJson()
  {
    super(DetailProto.class, new Object[] { PlacePageLinkJson.class, "attribution", "canonicalLabelId", "displayLabel", PlacePageLinkJson.class, "hoverAttribution", DetailProtoValueJson.class, "value" });
  }

  public static DetailProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DetailProtoJson
 * JD-Core Version:    0.6.2
 */