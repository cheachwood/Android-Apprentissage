package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityGadgetDataGadgetMessageJson extends EsJson<EntityGadgetDataGadgetMessage>
{
  static final EntityGadgetDataGadgetMessageJson INSTANCE = new EntityGadgetDataGadgetMessageJson();

  private EntityGadgetDataGadgetMessageJson()
  {
    super(EntityGadgetDataGadgetMessage.class, new Object[] { "anchorText", "authToken", "body", EntityGadgetDataGadgetMessageGadgetJson.class, "gadget", "hangoutId", "imageUrl", "navParam", "notificationId", "title" });
  }

  public static EntityGadgetDataGadgetMessageJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityGadgetDataGadgetMessageJson
 * JD-Core Version:    0.6.2
 */