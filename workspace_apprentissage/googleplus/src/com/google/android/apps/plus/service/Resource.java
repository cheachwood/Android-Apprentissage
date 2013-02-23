package com.google.android.apps.plus.service;

import android.util.Log;
import android.view.View;
import com.google.android.apps.plus.util.EsLog;
import java.io.File;
import java.util.ArrayList;

public abstract class Resource
{
  private ResourceConsumer mConsumer;
  private ArrayList<ResourceConsumerHolder> mConsumerList;
  protected boolean mDebugLogEnabled;
  protected volatile int mHttpStatusCode;
  protected final ResourceIdentifier mId;
  protected final ResourceManager mManager;
  protected volatile Object mResource;
  protected volatile int mResourceType;
  protected volatile int mStatus;
  private Object mTag;

  public Resource(ResourceManager paramResourceManager, ResourceIdentifier paramResourceIdentifier)
  {
    this.mManager = paramResourceManager;
    this.mId = paramResourceIdentifier;
    this.mDebugLogEnabled = EsLog.isLoggable("EsResource", 3);
  }

  private static void appendConsumer(StringBuilder paramStringBuilder, ResourceConsumer paramResourceConsumer, Object paramObject)
  {
    paramStringBuilder.append(paramResourceConsumer);
    if ((paramResourceConsumer instanceof View))
      paramStringBuilder.append(" context: ").append(((View)paramResourceConsumer).getContext());
    if (paramObject != null)
      paramStringBuilder.append(" tag: ").append(paramObject);
  }

  private static String statusToString(int paramInt)
  {
    String str;
    switch (paramInt)
    {
    default:
      str = String.valueOf(paramInt);
    case 8:
    case 3:
    case 2:
    case 4:
    case 7:
    case 9:
    case 6:
    case 1:
    case 5:
    case 0:
    }
    while (true)
    {
      return str;
      str = "canceled";
      continue;
      str = "downloading";
      continue;
      str = "loading";
      continue;
      str = "not found";
      continue;
      str = "out of memory";
      continue;
      str = "packed";
      continue;
      str = "permanent error";
      continue;
      str = "ready";
      continue;
      str = "transient error";
      continue;
      str = "undefined";
    }
  }

  public abstract void deliverData(byte[] paramArrayOfByte, boolean paramBoolean);

  public final void deliverDownloadError(int paramInt)
  {
    if ((this.mStatus != 2) && (this.mStatus != 3))
      if (this.mDebugLogEnabled)
        logDebug("Request no longer needed, not delivering status change: " + this.mId + ", current status: " + statusToString(this.mStatus) + ", ignored new status: " + statusToString(paramInt));
    while (true)
    {
      return;
      if (this.mDebugLogEnabled)
        logDebug("Delivering error code to consumers: " + this.mId + " status: " + statusToString(paramInt));
      this.mManager.deliverResourceStatus(this, paramInt);
    }
  }

  public final void deliverHttpError$4f708078(int paramInt)
  {
    if (paramInt == 404)
      this.mManager.deliverResourceStatus(this, 4);
    while (true)
    {
      return;
      this.mManager.deliverHttpError(this, 6, paramInt);
    }
  }

  public final void deliverResource(Object paramObject)
  {
    this.mManager.deliverResourceContent(this, 1, paramObject);
  }

  public final void deliverResourceType(int paramInt)
  {
    if (this.mDebugLogEnabled)
      logDebug("Delivering resource type to consumers: " + this.mId + " resource type: 2");
    this.mManager.deliverResourceType(this, 2);
  }

  public File getCacheFileName()
  {
    return null;
  }

  public final ResourceIdentifier getIdentifier()
  {
    return this.mId;
  }

  public final Object getResource()
  {
    return this.mResource;
  }

  public final int getResourceType()
  {
    return this.mResourceType;
  }

  public final int getStatus()
  {
    return this.mStatus;
  }

  public final String getStatusAsString()
  {
    return statusToString(this.mStatus);
  }

  public final boolean isDebugLogEnabled()
  {
    return this.mDebugLogEnabled;
  }

  public abstract void load();

  public final void logDebug(String paramString)
  {
    if (this.mDebugLogEnabled)
    {
      if (!EsLog.isLoggable("EsResource", 3))
        break label24;
      Log.d("EsResource", paramString);
    }
    while (true)
    {
      return;
      label24: Log.i("EsResource", paramString);
    }
  }

  public final void notifyConsumers()
  {
    if (this.mConsumerList != null)
    {
      int i = this.mConsumerList.size();
      for (int j = 0; j < i; j++)
      {
        ResourceConsumerHolder localResourceConsumerHolder = (ResourceConsumerHolder)this.mConsumerList.get(j);
        ResourceConsumer localResourceConsumer2 = localResourceConsumerHolder.consumer;
        localResourceConsumer2.onResourceStatusChange$1574fca0(this);
      }
    }
    if (this.mConsumer != null)
    {
      ResourceConsumer localResourceConsumer1 = this.mConsumer;
      localResourceConsumer1.onResourceStatusChange$1574fca0(this);
    }
  }

  public void recycle()
  {
    this.mStatus = 0;
    this.mResource = null;
  }

  public final void register(ResourceConsumer paramResourceConsumer)
  {
    int i;
    boolean bool;
    if ((this.mConsumer == paramResourceConsumer) && (((this.mTag == null) && (0 == 0)) || ((this.mTag != null) && (this.mTag.equals(null)))))
    {
      i = 1;
      if (i == 0)
      {
        if (this.mConsumerList == null)
          break label157;
        bool = this.mConsumerList.isEmpty();
        this.mConsumerList.add(new ResourceConsumerHolder(paramResourceConsumer, null));
      }
    }
    while (true)
    {
      if (bool)
        this.mManager.onFirstConsumerRegistered(this);
      paramResourceConsumer.onResourceStatusChange$1574fca0(this);
      return;
      if (this.mConsumerList != null)
      {
        int j = this.mConsumerList.size();
        for (int k = 0; ; k++)
        {
          if (k >= j)
            break label152;
          if (((ResourceConsumerHolder)this.mConsumerList.get(k)).matches(paramResourceConsumer, null))
          {
            i = 1;
            break;
          }
        }
      }
      label152: i = 0;
      break;
      label157: if (this.mConsumer != null)
      {
        this.mConsumerList = new ArrayList();
        this.mConsumerList.add(new ResourceConsumerHolder(this.mConsumer, this.mTag));
        this.mConsumer = null;
        this.mTag = null;
        this.mConsumerList.add(new ResourceConsumerHolder(paramResourceConsumer, null));
        bool = false;
      }
      else
      {
        this.mConsumer = paramResourceConsumer;
        this.mTag = null;
        bool = true;
      }
    }
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName()).append('@').append(System.identityHashCode(this)).append("\n  ID: ").append(this.mId).append("\n  Status: ").append(statusToString(this.mStatus));
    localStringBuilder.append("\n  Consumers:");
    if (this.mConsumerList != null)
    {
      int i = this.mConsumerList.size();
      for (int j = 0; j < i; j++)
      {
        ResourceConsumerHolder localResourceConsumerHolder = (ResourceConsumerHolder)this.mConsumerList.get(j);
        localStringBuilder.append("\n   ");
        appendConsumer(localStringBuilder, localResourceConsumerHolder.consumer, localResourceConsumerHolder.tag);
      }
    }
    if (this.mConsumer != null)
    {
      localStringBuilder.append("\n   ");
      appendConsumer(localStringBuilder, this.mConsumer, this.mTag);
    }
    while (true)
    {
      return localStringBuilder.toString();
      localStringBuilder.append("\n   none");
    }
  }

  public final void unregister(ResourceConsumer paramResourceConsumer)
  {
    if ((this.mConsumer == paramResourceConsumer) && ((this.mTag == null) || ((this.mTag != null) && (this.mTag.equals(null)))))
    {
      this.mConsumer = null;
      this.mTag = null;
      this.mManager.onLastConsumerUnregistered(this);
      return;
    }
    int i;
    if (this.mConsumerList != null)
      i = this.mConsumerList.size();
    for (int j = 0; ; j++)
      if (j < i)
      {
        if (((ResourceConsumerHolder)this.mConsumerList.get(j)).matches(paramResourceConsumer, null))
          this.mConsumerList.remove(j);
      }
      else
      {
        if (!this.mConsumerList.isEmpty())
          break;
        this.mManager.onLastConsumerUnregistered(this);
        break;
        break;
      }
  }

  private static final class ResourceConsumerHolder
  {
    ResourceConsumer consumer;
    Object tag;

    public ResourceConsumerHolder(ResourceConsumer paramResourceConsumer, Object paramObject)
    {
      this.consumer = paramResourceConsumer;
      this.tag = paramObject;
    }

    public final boolean matches(ResourceConsumer paramResourceConsumer, Object paramObject)
    {
      ResourceConsumer localResourceConsumer = this.consumer;
      boolean bool = false;
      if (localResourceConsumer != paramResourceConsumer);
      while (true)
      {
        return bool;
        if (this.tag == null)
        {
          bool = false;
          if (paramObject == null)
            bool = true;
        }
        else
        {
          bool = this.tag.equals(paramObject);
        }
      }
    }
  }

  public static abstract class ResourceIdentifier
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.Resource
 * JD-Core Version:    0.6.2
 */