package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityUpdateDataCountDataJson extends EsJson<EntityUpdateDataCountData>
{
  static final EntityUpdateDataCountDataJson INSTANCE = new EntityUpdateDataCountDataJson();

  private EntityUpdateDataCountDataJson()
  {
    super(EntityUpdateDataCountData.class, new Object[] { "commentPlusonerOid", "commenterOid", "globalPlusoneCount", "mentionerOid", "newCommentPlusonerOid", "newCommenterOid", "newMentionerOid", "newPostPlusonerOid", "newResharerOid", "postPlusonerOid", "resharerOid" });
  }

  public static EntityUpdateDataCountDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityUpdateDataCountDataJson
 * JD-Core Version:    0.6.2
 */