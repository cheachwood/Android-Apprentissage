package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySuggestionsDataJson extends EsJson<EntitySuggestionsData>
{
  static final EntitySuggestionsDataJson INSTANCE = new EntitySuggestionsDataJson();

  private EntitySuggestionsDataJson()
  {
    super(EntitySuggestionsData.class, new Object[] { "suggestedObfuscatedGaiaId" });
  }

  public static EntitySuggestionsDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySuggestionsDataJson
 * JD-Core Version:    0.6.2
 */