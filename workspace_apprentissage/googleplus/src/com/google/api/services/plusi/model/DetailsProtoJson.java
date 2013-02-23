package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DetailsProtoJson extends EsJson<DetailsProto>
{
  static final DetailsProtoJson INSTANCE = new DetailsProtoJson();

  private DetailsProtoJson()
  {
    super(DetailsProto.class, new Object[] { MoreDetailsProtoJson.class, "moreDetails", SummaryDetailsProtoJson.class, "summaryDetails" });
  }

  public static DetailsProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DetailsProtoJson
 * JD-Core Version:    0.6.2
 */