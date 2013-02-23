package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PersonJson extends EsJson<Person>
{
  static final PersonJson INSTANCE = new PersonJson();

  private PersonJson()
  {
    super(Person.class, new Object[] { "gender", "isContactSafe", "isViewerFollowing", "obfuscatedId", "photoUrl", "profileUrl", "userName" });
  }

  public static PersonJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PersonJson
 * JD-Core Version:    0.6.2
 */