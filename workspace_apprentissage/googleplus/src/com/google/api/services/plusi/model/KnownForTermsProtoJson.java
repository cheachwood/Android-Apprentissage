package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class KnownForTermsProtoJson extends EsJson<KnownForTermsProto>
{
  static final KnownForTermsProtoJson INSTANCE = new KnownForTermsProtoJson();

  private KnownForTermsProtoJson()
  {
    super(KnownForTermsProto.class, new Object[] { "term" });
  }

  public static KnownForTermsProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.KnownForTermsProtoJson
 * JD-Core Version:    0.6.2
 */