package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ItemScopeExtensionJson extends EsJson<ItemScopeExtension>
{
  static final ItemScopeExtensionJson INSTANCE = new ItemScopeExtensionJson();

  private ItemScopeExtensionJson()
  {
    super(ItemScopeExtension.class, new Object[] { ItemScopeJson.class, "itemValue", "key", "strValue" });
  }

  public static ItemScopeExtensionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ItemScopeExtensionJson
 * JD-Core Version:    0.6.2
 */