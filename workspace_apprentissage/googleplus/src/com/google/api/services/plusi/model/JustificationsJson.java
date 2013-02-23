package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class JustificationsJson extends EsJson<Justifications>
{
  static final JustificationsJson INSTANCE = new JustificationsJson();

  private JustificationsJson()
  {
    super(Justifications.class, new Object[] { LinkFragmentJson.class, "collapseLink", JustificationListJson.class, "negativeJustifications", JustificationListJson.class, "positiveJustifications", LinkFragmentJson.class, "ratePlacesLink", JustificationJson.class, "summaryJustification" });
  }

  public static JustificationsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.JustificationsJson
 * JD-Core Version:    0.6.2
 */