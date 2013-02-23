package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityGadgetDataJson extends EsJson<EntityGadgetData>
{
  static final EntityGadgetDataJson INSTANCE = new EntityGadgetDataJson();

  private EntityGadgetDataJson()
  {
    super(EntityGadgetData.class, new Object[] { EntityGadgetDataGadgetMessageJson.class, "gadgetMessage" });
  }

  public static EntityGadgetDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityGadgetDataJson
 * JD-Core Version:    0.6.2
 */