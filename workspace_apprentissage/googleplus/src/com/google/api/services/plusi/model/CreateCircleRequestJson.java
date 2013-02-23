package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CreateCircleRequestJson extends EsJson<CreateCircleRequest>
{
  static final CreateCircleRequestJson INSTANCE = new CreateCircleRequestJson();

  private CreateCircleRequestJson()
  {
    super(CreateCircleRequest.class, new Object[] { DataCircleMembershipModificationParamsJson.class, "circleMembershipModificationParams", ApiaryFieldsJson.class, "commonFields", "description", "enableTracing", "justFollowingStatus", "name" });
  }

  public static CreateCircleRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CreateCircleRequestJson
 * JD-Core Version:    0.6.2
 */