package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityGadgetDataGadgetMessageGadgetJson extends EsJson<EntityGadgetDataGadgetMessageGadget>
{
  static final EntityGadgetDataGadgetMessageGadgetJson INSTANCE = new EntityGadgetDataGadgetMessageGadgetJson();

  private EntityGadgetDataGadgetMessageGadgetJson()
  {
    super(EntityGadgetDataGadgetMessageGadget.class, new Object[] { "iconUrl", "id", "marqueeIconUrl", "name" });
  }

  public static EntityGadgetDataGadgetMessageGadgetJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityGadgetDataGadgetMessageGadgetJson
 * JD-Core Version:    0.6.2
 */