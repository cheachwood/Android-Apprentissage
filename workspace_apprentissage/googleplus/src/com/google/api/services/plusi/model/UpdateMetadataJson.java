package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UpdateMetadataJson extends EsJson<UpdateMetadata>
{
  static final UpdateMetadataJson INSTANCE = new UpdateMetadataJson();

  private UpdateMetadataJson()
  {
    super(UpdateMetadata.class, new Object[] { "inlineNamespace", "namespace", NamespaceSpecificDataJson.class, "namespaceSpecificData", "type" });
  }

  public static UpdateMetadataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UpdateMetadataJson
 * JD-Core Version:    0.6.2
 */