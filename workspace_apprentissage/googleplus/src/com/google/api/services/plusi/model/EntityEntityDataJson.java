package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityEntityDataJson extends EsJson<EntityEntityData>
{
  static final EntityEntityDataJson INSTANCE = new EntityEntityDataJson();

  private EntityEntityDataJson()
  {
    super(EntityEntityData.class, new Object[] { EntityBirthdayDataJson.class, "birthdayData", EntityCirclesDataJson.class, "circles", EntityGadgetDataJson.class, "gadget", EntityHangoutDataJson.class, "hangout", EntityLegacyClientDataJson.class, "legacy", EntityPhotosDataJson.class, "photos", EntityEventsDataJson.class, "plusEvents", EntityRecommendedPeopleDataJson.class, "recommendedPeopleData", EntitySegmentedShareDataJson.class, "segmentedShareData", EntitySquaresDataJson.class, "squares", EntitySuggestionsDataJson.class, "suggestions", EntitySummaryDataJson.class, "summarySnippet", EntityTargetSharedDataJson.class, "targetShared", EntityUpdateDataJson.class, "update" });
  }

  public static EntityEntityDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityEntityDataJson
 * JD-Core Version:    0.6.2
 */