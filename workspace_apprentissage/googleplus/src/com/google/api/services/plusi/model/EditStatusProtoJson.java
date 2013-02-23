package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EditStatusProtoJson extends EsJson<EditStatusProto>
{
  static final EditStatusProtoJson INSTANCE = new EditStatusProtoJson();

  private EditStatusProtoJson()
  {
    super(EditStatusProto.class, new Object[] { "editState", "important", "message" });
  }

  public static EditStatusProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EditStatusProtoJson
 * JD-Core Version:    0.6.2
 */