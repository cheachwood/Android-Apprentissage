package com.google.android.apps.plus.content;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Binder;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import com.google.android.apps.plus.analytics.AnalyticsInfo;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.LinkPreviewOperation;
import com.google.android.apps.plus.api.PlusOnesOperation;
import com.google.android.apps.plus.api.WritePlusOneOperation;
import com.google.android.apps.plus.external.PlatformContract.AccountContent;
import com.google.android.apps.plus.external.PlatformContract.PlusOneContent;
import com.google.android.apps.plus.external.PlatformContractUtils;
import com.google.android.apps.plus.network.ApiaryActivity;
import com.google.android.apps.plus.network.ApiaryApiInfo;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.Property;
import com.google.api.services.pos.model.ListJson;
import com.google.api.services.pos.model.Plusones;
import com.google.api.services.pos.model.Plusones.Metadata;
import com.google.api.services.pos.model.Plusones.Metadata.GlobalCounts;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EsApiProvider extends ContentProvider
{
  private static UriMatcher sMatcher;
  private static final LruCache<Uri, Plusones> sPlusoneCache = new LruCache(20);
  private static final LruCache<PreviewRequestData, ApiaryActivity> sPreviewCache = new LruCache(20);

  static
  {
    UriMatcher localUriMatcher = new UriMatcher(-1);
    sMatcher = localUriMatcher;
    localUriMatcher.addURI("com.google.android.apps.plus.content.ApiProvider", "plusone", 1);
    sMatcher.addURI("com.google.android.apps.plus.content.ApiProvider", "account", 2);
    sMatcher.addURI("com.google.android.apps.plus.content.ApiProvider", "preview", 3);
  }

  private Cursor buildPlusOneCursorFromCache(String[] paramArrayOfString, UpdateResults<Uri, Plusones> paramUpdateResults)
  {
    EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(PlatformContract.PlusOneContent.COLUMNS);
    localEsMatrixCursor.getExtras().putInt("com.google.circles.platform.result.extra.ERROR_CODE", paramUpdateResults.mServiceResult.getErrorCode());
    localEsMatrixCursor.getExtras().putString("com.google.circles.platform.result.extra.ERROR_MESSAGE", paramUpdateResults.mServiceResult.getReasonPhrase());
    ArrayList localArrayList;
    LruCache localLruCache;
    if (paramUpdateResults.mServiceResult.getErrorCode() == 200)
    {
      localArrayList = new ArrayList();
      localLruCache = sPlusoneCache;
    }
    for (int i = 0; ; i++)
    {
      try
      {
        if (i < paramArrayOfString.length)
        {
          Plusones localPlusones1 = (Plusones)paramUpdateResults.mResults.get(new PreviewRequestData(paramArrayOfString[i], null));
          if (localPlusones1 == null)
            localPlusones1 = (Plusones)sPlusoneCache.get(Uri.parse(paramArrayOfString[i]));
          if (localPlusones1 == null)
          {
            Plusones localPlusones2 = new Plusones();
            localPlusones2.id = paramArrayOfString[i];
            expandPlusOneToCursor(localPlusones2, localEsMatrixCursor);
          }
          else
          {
            expandPlusOneToCursor((Plusones)sPlusoneCache.get(Uri.parse(paramArrayOfString[i])), localEsMatrixCursor);
            localArrayList.add(localPlusones1);
          }
        }
      }
      finally
      {
      }
      if (Binder.getCallingUid() == getContext().getApplicationInfo().uid);
      for (int j = 1; ; j = 0)
      {
        if (j != 0)
        {
          com.google.api.services.pos.model.List localList = new com.google.api.services.pos.model.List();
          localList.items = localArrayList;
          localEsMatrixCursor.getExtras().putString("com.google.android.apps.content.EXTRA_PLUSONES", ListJson.getInstance().toString(localList));
        }
        return localEsMatrixCursor;
      }
    }
  }

  private static Cursor buildPreviewCursorFromCache(PreviewRequestData[] paramArrayOfPreviewRequestData, UpdateResults<PreviewRequestData, ApiaryActivity> paramUpdateResults)
  {
    EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(new String[] { "uri" });
    localEsMatrixCursor.getExtras().putInt("com.google.circles.platform.result.extra.ERROR_CODE", paramUpdateResults.mServiceResult.getErrorCode());
    localEsMatrixCursor.getExtras().putString("com.google.circles.platform.result.extra.ERROR_MESSAGE", paramUpdateResults.mServiceResult.getReasonPhrase());
    ApiaryActivity[] arrayOfApiaryActivity = new ApiaryActivity[paramArrayOfPreviewRequestData.length];
    for (int i = 0; i < arrayOfApiaryActivity.length; i++)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramArrayOfPreviewRequestData[i].uri;
      localEsMatrixCursor.addRow(arrayOfObject);
      arrayOfApiaryActivity[i] = ((ApiaryActivity)paramUpdateResults.mResults.get(paramArrayOfPreviewRequestData[i]));
      if (arrayOfApiaryActivity[i] == null)
        arrayOfApiaryActivity[i] = ((ApiaryActivity)sPreviewCache.get(paramArrayOfPreviewRequestData[i]));
    }
    if (arrayOfApiaryActivity.length > 0)
      localEsMatrixCursor.getExtras().putParcelableArray("com.google.android.apps.content.EXTRA_ACTIVITY", arrayOfApiaryActivity);
    return localEsMatrixCursor;
  }

  private static void expandPlusOneToCursor(Plusones paramPlusones, EsMatrixCursor paramEsMatrixCursor)
  {
    Boolean localBoolean = paramPlusones.isSetByViewer;
    Integer localInteger;
    if (localBoolean != null)
      if (localBoolean.booleanValue())
        localInteger = PlatformContract.PlusOneContent.STATE_PLUSONED;
    while (true)
    {
      long l = 0L;
      Plusones.Metadata localMetadata = paramPlusones.metadata;
      if (localMetadata != null)
      {
        Plusones.Metadata.GlobalCounts localGlobalCounts = localMetadata.globalCounts;
        if (localGlobalCounts != null)
          l = localGlobalCounts.count.longValue();
      }
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = paramPlusones.id;
      arrayOfObject[1] = Long.valueOf(l);
      arrayOfObject[2] = localInteger;
      arrayOfObject[3] = paramPlusones.abtk;
      paramEsMatrixCursor.addRow(arrayOfObject);
      return;
      localInteger = PlatformContract.PlusOneContent.STATE_NOTPLUSONED;
      continue;
      localInteger = PlatformContract.PlusOneContent.STATE_ANONYMOUS;
    }
  }

  private ApiaryApiInfo getApiaryApiInfo(Uri paramUri, String paramString)
  {
    Context localContext = getContext();
    String str1 = paramUri.getQueryParameter("pkg");
    if ((Binder.getCallingUid() == localContext.getApplicationInfo().uid) && (!TextUtils.isEmpty(str1)));
    for (String str2 = str1; ; str2 = localContext.getPackageManager().getPackagesForUid(Binder.getCallingUid())[0])
    {
      String str3 = Property.PLUS_CLIENTID.get();
      String str4 = localContext.getPackageName();
      PackageManager localPackageManager = localContext.getPackageManager();
      String str5 = PlatformContractUtils.getCertificate(str2, localPackageManager);
      String str6 = PlatformContractUtils.getCertificate(str4, localPackageManager);
      String str7 = paramUri.getQueryParameter("apiKey");
      String str8 = paramUri.getQueryParameter("clientId");
      String str9 = paramUri.getQueryParameter("apiVersion");
      return new ApiaryApiInfo(paramString, str3, str4, str6, str9, new ApiaryApiInfo(str7, str8, str2, str5, str9));
    }
  }

  private Cursor getPreviews(Uri paramUri, String[] paramArrayOfString)
  {
    if (Binder.getCallingUid() == getContext().getApplicationInfo().uid);
    for (int i = 1; i == 0; i = 0)
      throw new SecurityException();
    PreviewRequestData[] arrayOfPreviewRequestData = new PreviewRequestData[paramArrayOfString.length];
    for (int j = 0; j < paramArrayOfString.length; j++)
      arrayOfPreviewRequestData[j] = PreviewRequestData.fromSelectionArg(paramArrayOfString[j]);
    EsAccount localEsAccount = EsAccountsData.getActiveAccount(getContext());
    ApiaryApiInfo localApiaryApiInfo = getApiaryApiInfo(paramUri, paramUri.getQueryParameter("hostKey"));
    java.util.List localList = getUncachedKeys(Boolean.parseBoolean(paramUri.getQueryParameter("skipCache")), sPreviewCache, arrayOfPreviewRequestData);
    if (localList.size() > 0);
    for (UpdateResults localUpdateResults = updatePreviewEntries(localEsAccount, localApiaryApiInfo, localList); ; localUpdateResults = new UpdateResults())
      return buildPreviewCursorFromCache(arrayOfPreviewRequestData, localUpdateResults);
  }

  private static <T> java.util.List<PreviewRequestData> getUncachedKeys(boolean paramBoolean, LruCache<PreviewRequestData, T> paramLruCache, PreviewRequestData[] paramArrayOfPreviewRequestData)
  {
    ArrayList localArrayList = new ArrayList(paramArrayOfPreviewRequestData.length);
    if (!paramBoolean);
    while (true)
    {
      int j;
      try
      {
        int i = paramArrayOfPreviewRequestData.length;
        j = 0;
        if (j < i)
        {
          PreviewRequestData localPreviewRequestData = paramArrayOfPreviewRequestData[j];
          if (paramLruCache.get(localPreviewRequestData) != null)
            break label82;
          localArrayList.add(localPreviewRequestData);
          break label82;
        }
        return localArrayList;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
      localArrayList.addAll(Arrays.asList(paramArrayOfPreviewRequestData));
      continue;
      label82: j++;
    }
  }

  private static <T> java.util.List<String> getUncachedUrls(boolean paramBoolean, LruCache<Uri, T> paramLruCache, String[] paramArrayOfString)
  {
    ArrayList localArrayList = new ArrayList(paramArrayOfString.length);
    if (!paramBoolean);
    while (true)
    {
      int j;
      try
      {
        int i = paramArrayOfString.length;
        j = 0;
        if (j < i)
        {
          String str = paramArrayOfString[j];
          if (paramLruCache.get(Uri.parse(str)) != null)
            break label85;
          localArrayList.add(str);
          break label85;
        }
        return localArrayList;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
      localArrayList.addAll(Arrays.asList(paramArrayOfString));
      continue;
      label85: j++;
    }
  }

  public static Uri makePreviewUri(ApiaryApiInfo paramApiaryApiInfo)
  {
    ApiaryApiInfo localApiaryApiInfo = paramApiaryApiInfo.getSourceInfo();
    return Uri.parse("content://com.google.android.apps.plus.content.ApiProvider/preview").buildUpon().appendQueryParameter("apiKey", localApiaryApiInfo.getApiKey()).appendQueryParameter("clientId", localApiaryApiInfo.getClientId()).appendQueryParameter("apiVersion", localApiaryApiInfo.getSdkVersion()).appendQueryParameter("pkg", localApiaryApiInfo.getPackageName()).appendQueryParameter("hostKey", paramApiaryApiInfo.getApiKey()).build();
  }

  private UpdateResults<Uri, Plusones> updatePlusoneEntries(EsAccount paramEsAccount, ApiaryApiInfo paramApiaryApiInfo, java.util.List<String> paramList)
  {
    Context localContext = getContext();
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramList.iterator();
    PlusOnesOperation localPlusOnesOperation;
    while (true)
      if (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localPlusOnesOperation = new PlusOnesOperation(localContext, paramEsAccount, null, null, paramApiaryApiInfo, str);
        localPlusOnesOperation.start();
        if (localPlusOnesOperation.getErrorCode() == 200)
        {
          Plusones localPlusones = localPlusOnesOperation.getPlusones();
          if (localPlusones == null)
            continue;
          localHashMap.put(Uri.parse(str), localPlusones);
          synchronized (sPlusoneCache)
          {
            sPlusoneCache.put(Uri.parse(str), localPlusones);
          }
        }
      }
    for (UpdateResults localUpdateResults = new UpdateResults(new ServiceResult(localPlusOnesOperation.getErrorCode(), localPlusOnesOperation.getReasonPhrase(), localPlusOnesOperation.getException())); ; localUpdateResults = new UpdateResults(new ServiceResult(), localHashMap))
      return localUpdateResults;
  }

  private UpdateResults<PreviewRequestData, ApiaryActivity> updatePreviewEntries(EsAccount paramEsAccount, ApiaryApiInfo paramApiaryApiInfo, java.util.List<PreviewRequestData> paramList)
  {
    Context localContext = getContext();
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramList.iterator();
    LinkPreviewOperation localLinkPreviewOperation;
    while (true)
      if (localIterator.hasNext())
      {
        PreviewRequestData localPreviewRequestData = (PreviewRequestData)localIterator.next();
        localLinkPreviewOperation = new LinkPreviewOperation(localContext, paramEsAccount, null, null, localPreviewRequestData.uri.toString(), localPreviewRequestData.callToAction, paramApiaryApiInfo);
        localLinkPreviewOperation.start();
        ApiaryActivity localApiaryActivity = localLinkPreviewOperation.getActivity();
        if ((localLinkPreviewOperation.getErrorCode() == 200) && (localApiaryActivity == null))
          localLinkPreviewOperation.setErrorInfo(0, "null activity", null);
        if (localLinkPreviewOperation.getErrorCode() == 200)
        {
          localHashMap.put(localPreviewRequestData, localApiaryActivity);
          synchronized (sPreviewCache)
          {
            sPreviewCache.put(localPreviewRequestData, localApiaryActivity);
          }
        }
      }
    for (UpdateResults localUpdateResults = new UpdateResults(new ServiceResult(localLinkPreviewOperation.getErrorCode(), localLinkPreviewOperation.getReasonPhrase(), localLinkPreviewOperation.getException())); ; localUpdateResults = new UpdateResults(new ServiceResult(), localHashMap))
      return localUpdateResults;
  }

  private boolean writePlusOne(Uri paramUri, ContentValues paramContentValues, String paramString)
  {
    Context localContext = getContext();
    ApiaryApiInfo localApiaryApiInfo = getApiaryApiInfo(paramUri, null);
    EsAccount localEsAccount = EsAccountsData.getActiveAccount(getContext());
    Uri localUri = Uri.parse(paramString);
    boolean bool1;
    if (paramContentValues.getAsInteger("state") == PlatformContract.PlusOneContent.STATE_PLUSONED)
      bool1 = true;
    Double localDouble1;
    Boolean localBoolean;
    int i;
    int j;
    label207: boolean bool2;
    while (true)
    {
      String str = paramContentValues.getAsString("token");
      int k;
      WritePlusOneOperation localWritePlusOneOperation;
      Plusones localPlusones3;
      synchronized (sPlusoneCache)
      {
        Plusones localPlusones1 = (Plusones)sPlusoneCache.get(localUri);
        localDouble1 = null;
        localBoolean = null;
        i = 0;
        j = 0;
        if (localPlusones1 != null)
        {
          localBoolean = localPlusones1.isSetByViewer;
          localPlusones1.isSetByViewer = Boolean.valueOf(bool1);
          Plusones.Metadata localMetadata = localPlusones1.metadata;
          localDouble1 = null;
          if (localMetadata != null)
          {
            Plusones.Metadata.GlobalCounts localGlobalCounts1 = localPlusones1.metadata.globalCounts;
            localDouble1 = null;
            if (localGlobalCounts1 != null)
            {
              Double localDouble2 = localPlusones1.metadata.globalCounts.count;
              localDouble1 = null;
              if (localDouble2 != null)
              {
                localDouble1 = localPlusones1.metadata.globalCounts.count;
                Plusones.Metadata.GlobalCounts localGlobalCounts2 = localPlusones1.metadata.globalCounts;
                double d = localDouble1.doubleValue();
                if (!Boolean.valueOf(bool1).booleanValue())
                  break label455;
                k = 1;
                localGlobalCounts2.count = Double.valueOf(d + k);
              }
            }
          }
          i = localPlusones1.hashCode();
          j = 1;
        }
        else
        {
          if (j != 0)
          {
            getContext().getContentResolver().notifyChange(Uri.parse("content://com.google.android.apps.plus.content.ApiProvider/plusone"), null);
            j = 0;
          }
          localWritePlusOneOperation = new WritePlusOneOperation(localContext, localEsAccount, null, null, localApiaryApiInfo, paramString, bool1, str);
          localWritePlusOneOperation.start();
          if (localWritePlusOneOperation.getErrorCode() != 200)
            break;
          localPlusones3 = localWritePlusOneOperation.getPlusones();
        }
      }
      synchronized (sPlusoneCache)
      {
        Plusones localPlusones4 = (Plusones)sPlusoneCache.get(localUri);
        if ((localPlusones4 != null) && (localPlusones4.isSetByViewer != localPlusones3.isSetByViewer))
        {
          localPlusones4.isSetByViewer = localPlusones3.isSetByViewer;
          if ((localDouble1 != null) && (localPlusones4.metadata != null) && (localPlusones4.metadata.globalCounts != null) && (localPlusones4.metadata.globalCounts.count != null))
            localPlusones4.metadata.globalCounts.count = localDouble1;
        }
        else
        {
          if (j != 0)
            getContext().getContentResolver().notifyChange(Uri.parse("content://com.google.android.apps.plus.content.ApiProvider/plusone"), null);
          if (localWritePlusOneOperation.getErrorCode() == 200)
          {
            bool2 = true;
            label446: return bool2;
            bool1 = false;
            continue;
            label455: k = -1;
            break label207;
            localObject1 = finally;
            throw localObject1;
          }
        }
      }
    }
    while (true)
    {
      synchronized (sPlusoneCache)
      {
        Plusones localPlusones2 = (Plusones)sPlusoneCache.get(localUri);
        if ((localPlusones2 != null) && (i == localPlusones2.hashCode()) && (localPlusones2.isSetByViewer != localBoolean))
        {
          localPlusones2.isSetByViewer = localBoolean;
          localPlusones2.metadata.globalCounts.count = localDouble1;
          if ((localDouble1 == null) || (localPlusones2.metadata == null) || (localPlusones2.metadata.globalCounts == null) || (localPlusones2.metadata.globalCounts.count == null))
            break label623;
          localPlusones2.metadata.globalCounts.count = localDouble1;
          break label623;
        }
      }
      bool2 = false;
      break label446;
      j = 1;
      break;
      label623: j = 1;
    }
  }

  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    return 0;
  }

  public String getType(Uri paramUri)
  {
    String str;
    switch (sMatcher.match(paramUri))
    {
    default:
      str = null;
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return str;
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.plusones";
      continue;
      str = "vnd.android.cursor.item/vnd.google.android.apps.plus.account";
      continue;
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.activitypreview";
    }
  }

  public Uri insert(Uri paramUri, ContentValues paramContentValues)
  {
    return ContentUris.withAppendedId(paramUri, 0L);
  }

  public boolean onCreate()
  {
    return true;
  }

  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, final String[] paramArrayOfString2, String paramString2)
  {
    Object localObject;
    label35: final EsAccount localEsAccount2;
    final ApiaryApiInfo localApiaryApiInfo;
    switch (sMatcher.match(paramUri))
    {
    default:
    case 1:
      do
      {
        localObject = null;
        return localObject;
      }
      while ((paramArrayOfString2 == null) || (paramArrayOfString2.length <= 0));
      localEsAccount2 = EsAccountsData.getActiveAccount(getContext());
      localApiaryApiInfo = getApiaryApiInfo(paramUri, null);
      if (Process.myPid() == Binder.getCallingPid())
        break;
    case 2:
    case 3:
    }
    for (AnalyticsInfo localAnalyticsInfo2 = new AnalyticsInfo(OzViews.PLATFORM_THIRD_PARTY_APP, OzViews.PLATFORM_THIRD_PARTY_APP, System.currentTimeMillis(), PlatformContractUtils.getCallingPackageAnalytics(localApiaryApiInfo)); ; localAnalyticsInfo2 = null)
    {
      final boolean bool = Boolean.parseBoolean(paramUri.getQueryParameter("skipCache"));
      final java.util.List localList = getUncachedUrls(bool, sPlusoneCache, paramArrayOfString2);
      UpdateResults localUpdateResults;
      label144: Cursor localCursor;
      if (localList.size() > 0)
      {
        localUpdateResults = updatePlusoneEntries(localEsAccount2, localApiaryApiInfo, localList);
        if ((localEsAccount2 != null) && (!Boolean.parseBoolean(paramUri.getQueryParameter("no_preview"))))
          new Thread(new Runnable()
          {
            public final void run()
            {
              PreviewRequestData[] arrayOfPreviewRequestData = new PreviewRequestData[paramArrayOfString2.length];
              for (int i = 0; i < paramArrayOfString2.length; i++)
                arrayOfPreviewRequestData[i] = new PreviewRequestData(paramArrayOfString2[i], null);
              java.util.List localList = EsApiProvider.getUncachedKeys(EsApiProvider.this, bool, EsApiProvider.sPreviewCache, arrayOfPreviewRequestData);
              if (localList.size() > 0)
                EsApiProvider.this.updatePreviewEntries(localEsAccount2, localApiaryApiInfo, localList);
            }
          }).start();
        localCursor = buildPlusOneCursorFromCache(paramArrayOfString2, localUpdateResults);
        if (localAnalyticsInfo2 != null)
          if (localUpdateResults.mServiceResult.hasError())
            break label253;
      }
      label253: for (OzActions localOzActions = OzActions.PLATFORM_READ_PLUSONES; ; localOzActions = OzActions.PLATFORM_READ_PLUSONES_ERROR)
      {
        EsAnalytics.postRecordEvent(getContext(), localEsAccount2, localAnalyticsInfo2, localOzActions);
        localObject = localCursor;
        break;
        localUpdateResults = new UpdateResults();
        break label144;
      }
      EsAccount localEsAccount1 = EsAccountsData.getActiveAccount(getContext());
      EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(PlatformContract.AccountContent.COLUMNS);
      if (localEsAccount1 != null)
      {
        Map localMap = Collections.emptyMap();
        PackageManager localPackageManager = getContext().getPackageManager();
        String[] arrayOfString = localPackageManager.getPackagesForUid(Binder.getCallingUid());
        if ((arrayOfString != null) && (arrayOfString.length > 0))
          localMap = PlatformContractUtils.getCallingPackageAnalytics(new ApiaryApiInfo(null, null, null, null, null, new ApiaryApiInfo(null, null, arrayOfString[0], PlatformContractUtils.getCertificate(arrayOfString[0], localPackageManager), null)));
        AnalyticsInfo localAnalyticsInfo1 = new AnalyticsInfo(OzViews.PLATFORM_THIRD_PARTY_APP, OzViews.PLATFORM_THIRD_PARTY_APP, System.currentTimeMillis(), localMap);
        EsAnalytics.postRecordEvent(getContext(), localEsAccount1, localAnalyticsInfo1, OzActions.PLATFORM_GET_ACCOUNT);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localEsAccount1.getName();
        localEsMatrixCursor.addRow(arrayOfObject);
      }
      localObject = localEsMatrixCursor;
      break label35;
      if ((paramArrayOfString2 == null) || (paramArrayOfString2.length <= 0))
        break;
      localObject = getPreviews(paramUri, paramArrayOfString2);
      break label35;
    }
  }

  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    int i = 1;
    if (Binder.getCallingUid() == getContext().getApplicationInfo().uid);
    for (int j = i; j == 0; j = 0)
      throw new SecurityException();
    switch (sMatcher.match(paramUri))
    {
    default:
    case 1:
    case 2:
    }
    while (true)
    {
      i = 0;
      while (true)
      {
        return i;
        if ((paramArrayOfString == null) || (paramArrayOfString.length != i))
          break;
        if (!writePlusOne(paramUri, paramContentValues, paramArrayOfString[0]))
          i = 0;
      }
      synchronized (sPlusoneCache)
      {
        sPlusoneCache.evictAll();
      }
      synchronized (sPreviewCache)
      {
        sPreviewCache.evictAll();
        getContext().getContentResolver().notifyChange(Uri.parse("content://com.google.android.apps.plus.content.ApiProvider/account"), null);
        getContext().getContentResolver().notifyChange(Uri.parse("content://com.google.android.apps.plus.content.ApiProvider/plusone"), null);
        continue;
        localObject1 = finally;
        throw localObject1;
      }
    }
  }

  private static final class UpdateResults<K, V>
  {
    public final Map<K, V> mResults;
    public final ServiceResult mServiceResult;

    public UpdateResults()
    {
      this(new ServiceResult());
    }

    public UpdateResults(ServiceResult paramServiceResult)
    {
      this(paramServiceResult, Collections.emptyMap());
    }

    public UpdateResults(ServiceResult paramServiceResult, Map<K, V> paramMap)
    {
      this.mServiceResult = paramServiceResult;
      this.mResults = paramMap;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsApiProvider
 * JD-Core Version:    0.6.2
 */