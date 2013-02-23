package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EditActivityRequestJson extends EsJson<EditActivityRequest>
{
  static final EditActivityRequestJson INSTANCE = new EditActivityRequestJson();

  private EditActivityRequestJson()
  {
    super(EditActivityRequest.class, new Object[] { "annotation", ApiaryFieldsJson.class, "commonFields", "contentFormat", "deleteLocation", EditSegmentsJson.class, "editSegments", EmbedClientItemJson.class, "embed", "enableTracing", "externalId", "isReshare", "mediaJson", "preserveExistingAttachment", "renderContextLocation", "updateText" });
  }

  public static EditActivityRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EditActivityRequestJson
 * JD-Core Version:    0.6.2
 */