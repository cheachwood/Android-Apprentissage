package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SpellingSuggestionsJson extends EsJson<SpellingSuggestions>
{
  static final SpellingSuggestionsJson INSTANCE = new SpellingSuggestionsJson();

  private SpellingSuggestionsJson()
  {
    super(SpellingSuggestions.class, new Object[] { SpellingSuggestionJson.class, "suggestion" });
  }

  public static SpellingSuggestionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SpellingSuggestionsJson
 * JD-Core Version:    0.6.2
 */