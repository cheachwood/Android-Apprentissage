package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PackagingServiceClientJson extends EsJson<PackagingServiceClient>
{
  static final PackagingServiceClientJson INSTANCE = new PackagingServiceClientJson();

  private PackagingServiceClientJson()
  {
    super(PackagingServiceClient.class, new Object[] { "androidPackageName", "iosAppStoreId", "iosBundleId", "type" });
  }

  public static PackagingServiceClientJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PackagingServiceClientJson
 * JD-Core Version:    0.6.2
 */