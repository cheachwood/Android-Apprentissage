package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PersonalCircleJson extends EsJson<PersonalCircle>
{
  static final PersonalCircleJson INSTANCE = new PersonalCircleJson();

  private PersonalCircleJson()
  {
    super(PersonalCircle.class, new Object[] { "focusGroupId", "memberCount", "name", "synthetic", "visible" });
  }

  public static PersonalCircleJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PersonalCircleJson
 * JD-Core Version:    0.6.2
 */