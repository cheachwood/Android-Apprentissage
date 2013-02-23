package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SpellingSuggestionJson extends EsJson<SpellingSuggestion>
{
  static final SpellingSuggestionJson INSTANCE = new SpellingSuggestionJson();

  private SpellingSuggestionJson()
  {
    super(SpellingSuggestion.class, new Object[] { "text" });
  }

  public static SpellingSuggestionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SpellingSuggestionJson
 * JD-Core Version:    0.6.2
 */