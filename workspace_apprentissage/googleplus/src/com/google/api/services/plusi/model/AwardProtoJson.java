package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AwardProtoJson extends EsJson<AwardProto>
{
  static final AwardProtoJson INSTANCE = new AwardProtoJson();

  private AwardProtoJson()
  {
    super(AwardProto.class, new Object[] { PlacePageLinkJson.class, "awardLink" });
  }

  public static AwardProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AwardProtoJson
 * JD-Core Version:    0.6.2
 */