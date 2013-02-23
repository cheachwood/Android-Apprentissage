package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EmbedsPersonJson extends EsJson<EmbedsPerson>
{
  static final EmbedsPersonJson INSTANCE = new EmbedsPersonJson();

  private EmbedsPersonJson()
  {
    super(EmbedsPerson.class, new Object[] { "email", "gender", "imageUrl", "name", "ownerObfuscatedId", "url" });
  }

  public static EmbedsPersonJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EmbedsPersonJson
 * JD-Core Version:    0.6.2
 */