package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class VerificationJson extends EsJson<Verification>
{
  static final VerificationJson INSTANCE = new VerificationJson();

  private VerificationJson()
  {
    super(Verification.class, new Object[] { "status", "type" });
  }

  public static VerificationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.VerificationJson
 * JD-Core Version:    0.6.2
 */