package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EmailJson extends EsJson<Email>
{
  static final EmailJson INSTANCE = new EmailJson();

  private EmailJson()
  {
    super(Email.class, new Object[] { "isVerified", "value" });
  }

  public static EmailJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EmailJson
 * JD-Core Version:    0.6.2
 */