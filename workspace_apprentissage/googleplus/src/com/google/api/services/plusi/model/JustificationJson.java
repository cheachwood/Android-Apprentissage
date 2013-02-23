package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class JustificationJson extends EsJson<Justification>
{
  static final JustificationJson INSTANCE = new JustificationJson();

  private JustificationJson()
  {
    super(Justification.class, new Object[] { LinkFragmentJson.class, "fragment", LinkFragmentJson.class, "fullText", PlacePageLinkJson.class, "photoLink", "photoUrl" });
  }

  public static JustificationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.JustificationJson
 * JD-Core Version:    0.6.2
 */