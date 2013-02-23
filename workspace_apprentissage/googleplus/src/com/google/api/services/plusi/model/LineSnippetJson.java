package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LineSnippetJson extends EsJson<LineSnippet>
{
  static final LineSnippetJson INSTANCE = new LineSnippetJson();

  private LineSnippetJson()
  {
    super(LineSnippet.class, new Object[] { "backgroundColor", "iconAlt", "iconSrc", "longName", "name", "nameInColorBox", "nameNonBold", "textColor", "tooltip", "vehicleTypeName" });
  }

  public static LineSnippetJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LineSnippetJson
 * JD-Core Version:    0.6.2
 */