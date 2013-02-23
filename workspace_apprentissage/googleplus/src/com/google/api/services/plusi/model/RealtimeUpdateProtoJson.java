package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RealtimeUpdateProtoJson extends EsJson<RealtimeUpdateProto>
{
  static final RealtimeUpdateProtoJson INSTANCE = new RealtimeUpdateProtoJson();

  private RealtimeUpdateProtoJson()
  {
    super(RealtimeUpdateProto.class, new Object[] { "attribution", AuthorProtoJson.class, "author", LinkFragmentJson.class, "fragment", LinkFragmentJson.class, "fullText", "localizedTime" });
  }

  public static RealtimeUpdateProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RealtimeUpdateProtoJson
 * JD-Core Version:    0.6.2
 */