package com.google.android.apps.plus.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.google.android.apps.plus.content.DbLocation;
import com.google.api.services.plusi.model.GeoCoordinates;
import com.google.api.services.plusi.model.Place;

public final class MapUtils
{
  public static Intent getPlacesActivityIntent$7ec49240()
  {
    Intent localIntent = new Intent("android.intent.action.MAIN");
    localIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.PlacesActivity");
    localIntent.addFlags(524288);
    return localIntent;
  }

  public static void launchMapsActivity(Context paramContext, Uri paramUri)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW", paramUri);
    localIntent.addFlags(524288);
    localIntent.setPackage("com.google.android.apps.maps");
    try
    {
      paramContext.startActivity(localIntent);
      return;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      while (true)
        paramContext.startActivity(Intent.createChooser(localIntent, null));
    }
  }

  private static String sanitizedLocationName(String paramString)
  {
    if (paramString == null);
    for (String str = ""; ; str = paramString.replace('<', '[').replace('>', ']').replace('(', '[').replace(')', ']'))
      return str;
  }

  public static void showActivityOnMap(Context paramContext, DbLocation paramDbLocation)
  {
    Uri.Builder localBuilder = Uri.parse("http://maps.google.com/maps").buildUpon();
    localBuilder.appendQueryParameter("lci", "com.google.latitudepublicupdates");
    if (paramDbLocation.hasCoordinates())
    {
      double d3 = paramDbLocation.getLatitudeE7() / 10000000.0D;
      double d4 = paramDbLocation.getLongitudeE7() / 10000000.0D;
      localBuilder.appendQueryParameter("ll", d3 + "," + d4);
    }
    String str1 = paramDbLocation.getClusterId();
    int i;
    String str2;
    if (!TextUtils.isEmpty(str1))
    {
      i = 1;
      if (i != 0)
        localBuilder.appendQueryParameter("cid", str1);
      str2 = paramDbLocation.getLocationName();
      if ((i != 0) || (!paramDbLocation.hasCoordinates()))
        break label233;
      double d1 = paramDbLocation.getLatitudeE7() / 10000000.0D;
      double d2 = paramDbLocation.getLongitudeE7() / 10000000.0D;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(d1).append(',').append(d2);
      if (!TextUtils.isEmpty(str2))
        localStringBuilder.append('(').append(sanitizedLocationName(str2)).append(')');
      localBuilder.appendQueryParameter("q", localStringBuilder.toString());
    }
    while (true)
    {
      launchMapsActivity(paramContext, localBuilder.build());
      return;
      i = 0;
      break;
      label233: if (!TextUtils.isEmpty(str2))
        localBuilder.appendQueryParameter("q", str2);
    }
  }

  public static void showDrivingDirections(Context paramContext, Place paramPlace)
  {
    if ((paramPlace.geo == null) && (paramPlace.name == null) && (paramPlace.clusterId != null))
    {
      Uri.Builder localBuilder2 = Uri.parse("http://maps.google.com/maps").buildUpon();
      if (paramPlace.geo != null)
        localBuilder2.appendQueryParameter("ll", paramPlace.geo.latitude + "," + paramPlace.geo.longitude);
      if (paramPlace.clusterId != null)
        localBuilder2.appendQueryParameter("cid", paramPlace.clusterId);
      if ((paramPlace.clusterId == null) && (paramPlace.geo != null))
      {
        StringBuilder localStringBuilder2 = new StringBuilder();
        localStringBuilder2.append(paramPlace.geo.latitude).append(',').append(paramPlace.geo.longitude);
        if (!TextUtils.isEmpty(paramPlace.name))
          localStringBuilder2.append('(').append(sanitizedLocationName(paramPlace.name)).append(')');
        localBuilder2.appendQueryParameter("q", localStringBuilder2.toString());
      }
      while (true)
      {
        launchMapsActivity(paramContext, localBuilder2.build());
        label206: return;
        if (TextUtils.isEmpty(paramPlace.name))
          break;
        localBuilder2.appendQueryParameter("q", paramPlace.name);
      }
    }
    Uri.Builder localBuilder1 = Uri.parse("http://maps.google.com/maps").buildUpon();
    if (paramPlace.geo != null)
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append(paramPlace.geo.latitude).append(',').append(paramPlace.geo.longitude);
      if (!TextUtils.isEmpty(paramPlace.name))
        localStringBuilder1.append('(').append(sanitizedLocationName(paramPlace.name)).append(')');
      localBuilder1.appendQueryParameter("daddr", localStringBuilder1.toString());
    }
    while (true)
    {
      launchMapsActivity(paramContext, localBuilder1.build());
      break label206;
      break;
      if (!TextUtils.isEmpty(paramPlace.name))
        localBuilder1.appendQueryParameter("daddr", paramPlace.name);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.MapUtils
 * JD-Core Version:    0.6.2
 */