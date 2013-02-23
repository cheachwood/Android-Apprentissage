package com.google.android.apps.plus.service;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

public abstract class ResourceManager
  implements Handler.Callback
{
  private final Context mContext;
  private LoaderThread mLoaderThread;
  private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper(), this);

  protected ResourceManager(Context paramContext)
  {
    this.mContext = paramContext;
  }

  protected final void deliverHttpError(Resource paramResource, int paramInt1, int paramInt2)
  {
    this.mMainThreadHandler.sendMessage(this.mMainThreadHandler.obtainMessage(2, 6, paramInt2, paramResource));
  }

  protected final void deliverResourceContent(Resource paramResource, int paramInt, Object paramObject)
  {
    this.mMainThreadHandler.sendMessage(this.mMainThreadHandler.obtainMessage(1, paramInt, 0, new ResourceData(paramResource, paramObject)));
  }

  protected final void deliverResourceStatus(Resource paramResource, int paramInt)
  {
    this.mMainThreadHandler.sendMessage(this.mMainThreadHandler.obtainMessage(0, paramInt, 0, paramResource));
  }

  protected final void deliverResourceType(Resource paramResource, int paramInt)
  {
    this.mMainThreadHandler.sendMessage(this.mMainThreadHandler.obtainMessage(3, paramInt, 0, paramResource));
  }

  public final Context getContext()
  {
    return this.mContext;
  }

  public boolean handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
    case 0:
    case 2:
    case 1:
    case 3:
    }
    while (true)
    {
      return true;
      Resource localResource4 = (Resource)paramMessage.obj;
      localResource4.mStatus = paramMessage.arg1;
      localResource4.notifyConsumers();
      continue;
      Resource localResource3 = (Resource)paramMessage.obj;
      localResource3.mStatus = paramMessage.arg1;
      localResource3.mHttpStatusCode = paramMessage.arg2;
      localResource3.notifyConsumers();
      continue;
      ResourceData localResourceData = (ResourceData)paramMessage.obj;
      Resource localResource2 = localResourceData.resource;
      localResource2.mResource = localResourceData.data;
      localResource2.mStatus = paramMessage.arg1;
      localResource2.notifyConsumers();
      continue;
      Resource localResource1 = (Resource)paramMessage.obj;
      localResource1.mResourceType = paramMessage.arg1;
      localResource1.notifyConsumers();
    }
  }

  protected final void loadResource(Resource paramResource)
  {
    if (this.mLoaderThread == null)
    {
      this.mLoaderThread = new LoaderThread();
      this.mLoaderThread.start();
    }
    this.mLoaderThread.loadResource(paramResource);
  }

  public void onFirstConsumerRegistered(Resource paramResource)
  {
  }

  public void onLastConsumerUnregistered(Resource paramResource)
  {
  }

  private static final class LoaderThread extends HandlerThread
    implements Handler.Callback
  {
    private Handler mLoaderThreadHandler;

    public LoaderThread()
    {
      super();
    }

    public final boolean handleMessage(Message paramMessage)
    {
      ((Resource)paramMessage.obj).load();
      return true;
    }

    public final void loadResource(Resource paramResource)
    {
      if (this.mLoaderThreadHandler == null)
        this.mLoaderThreadHandler = new Handler(getLooper(), this);
      this.mLoaderThreadHandler.sendMessage(this.mLoaderThreadHandler.obtainMessage(0, paramResource));
    }

    public final void run()
    {
      Process.setThreadPriority(10);
      super.run();
    }
  }

  private static final class ResourceData
  {
    Object data;
    Resource resource;

    public ResourceData(Resource paramResource, Object paramObject)
    {
      this.resource = paramResource;
      this.data = paramObject;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.ResourceManager
 * JD-Core Version:    0.6.2
 */