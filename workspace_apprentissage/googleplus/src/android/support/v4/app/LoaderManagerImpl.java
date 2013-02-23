package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.OnLoadCompleteListener;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

final class LoaderManagerImpl extends LoaderManager
{
  static boolean DEBUG = false;
  FragmentActivity mActivity;
  boolean mCreatingLoader;
  final SparseArrayCompat<LoaderInfo> mInactiveLoaders = new SparseArrayCompat();
  final SparseArrayCompat<LoaderInfo> mLoaders = new SparseArrayCompat();
  boolean mRetaining;
  boolean mStarted;

  LoaderManagerImpl(FragmentActivity paramFragmentActivity, boolean paramBoolean)
  {
    this.mActivity = paramFragmentActivity;
    this.mStarted = paramBoolean;
  }

  private LoaderInfo createAndInstallLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<Object> paramLoaderCallbacks)
  {
    try
    {
      this.mCreatingLoader = true;
      LoaderInfo localLoaderInfo = createLoader(paramInt, paramBundle, paramLoaderCallbacks);
      installLoader(localLoaderInfo);
      return localLoaderInfo;
    }
    finally
    {
      this.mCreatingLoader = false;
    }
  }

  private LoaderInfo createLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<Object> paramLoaderCallbacks)
  {
    LoaderInfo localLoaderInfo = new LoaderInfo(paramInt, paramBundle, paramLoaderCallbacks);
    localLoaderInfo.mLoader = paramLoaderCallbacks.onCreateLoader(paramInt, paramBundle);
    return localLoaderInfo;
  }

  public final void destroyLoader(int paramInt)
  {
    if (this.mCreatingLoader)
      throw new IllegalStateException("Called while creating a loader");
    int i = this.mLoaders.indexOfKey(paramInt);
    if (i >= 0)
    {
      LoaderInfo localLoaderInfo2 = (LoaderInfo)this.mLoaders.valueAt(i);
      this.mLoaders.removeAt(i);
      localLoaderInfo2.destroy();
    }
    int j = this.mInactiveLoaders.indexOfKey(paramInt);
    if (j >= 0)
    {
      LoaderInfo localLoaderInfo1 = (LoaderInfo)this.mInactiveLoaders.valueAt(j);
      this.mInactiveLoaders.removeAt(j);
      localLoaderInfo1.destroy();
    }
    if ((this.mActivity != null) && (!hasRunningLoaders()))
      this.mActivity.mFragments.startPendingDeferredFragments();
  }

  final void doDestroy()
  {
    if (!this.mRetaining)
      for (int j = -1 + this.mLoaders.size(); j >= 0; j--)
        ((LoaderInfo)this.mLoaders.valueAt(j)).destroy();
    for (int i = -1 + this.mInactiveLoaders.size(); i >= 0; i--)
      ((LoaderInfo)this.mInactiveLoaders.valueAt(i)).destroy();
    this.mInactiveLoaders.clear();
  }

  final void doReportNextStart()
  {
    for (int i = -1 + this.mLoaders.size(); i >= 0; i--)
      ((LoaderInfo)this.mLoaders.valueAt(i)).mReportNextStart = true;
  }

  final void doReportStart()
  {
    for (int i = -1 + this.mLoaders.size(); i >= 0; i--)
    {
      LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.valueAt(i);
      if ((localLoaderInfo.mStarted) && (localLoaderInfo.mReportNextStart))
      {
        localLoaderInfo.mReportNextStart = false;
        if (localLoaderInfo.mHaveData)
          localLoaderInfo.callOnLoadFinished(localLoaderInfo.mLoader, localLoaderInfo.mData);
      }
    }
  }

  final void doRetain()
  {
    if (!this.mStarted)
    {
      RuntimeException localRuntimeException = new RuntimeException("here");
      localRuntimeException.fillInStackTrace();
      Log.w("LoaderManager", "Called doRetain when not started: " + this, localRuntimeException);
    }
    while (true)
    {
      return;
      this.mRetaining = true;
      this.mStarted = false;
      for (int i = -1 + this.mLoaders.size(); i >= 0; i--)
      {
        LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.valueAt(i);
        localLoaderInfo.mRetaining = true;
        localLoaderInfo.mRetainingStarted = localLoaderInfo.mStarted;
        localLoaderInfo.mStarted = false;
        localLoaderInfo.mCallbacks = null;
      }
    }
  }

  final void doStart()
  {
    if (this.mStarted)
    {
      RuntimeException localRuntimeException = new RuntimeException("here");
      localRuntimeException.fillInStackTrace();
      Log.w("LoaderManager", "Called doStart when already started: " + this, localRuntimeException);
    }
    while (true)
    {
      return;
      this.mStarted = true;
      for (int i = -1 + this.mLoaders.size(); i >= 0; i--)
        ((LoaderInfo)this.mLoaders.valueAt(i)).start();
    }
  }

  final void doStop()
  {
    if (!this.mStarted)
    {
      RuntimeException localRuntimeException = new RuntimeException("here");
      localRuntimeException.fillInStackTrace();
      Log.w("LoaderManager", "Called doStop when not started: " + this, localRuntimeException);
    }
    while (true)
    {
      return;
      for (int i = -1 + this.mLoaders.size(); i >= 0; i--)
        ((LoaderInfo)this.mLoaders.valueAt(i)).stop();
      this.mStarted = false;
    }
  }

  public final void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    if (this.mLoaders.size() > 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Active Loaders:");
      String str2 = paramString + "    ";
      for (int j = 0; j < this.mLoaders.size(); j++)
      {
        LoaderInfo localLoaderInfo2 = (LoaderInfo)this.mLoaders.valueAt(j);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  #");
        paramPrintWriter.print(this.mLoaders.keyAt(j));
        paramPrintWriter.print(": ");
        paramPrintWriter.println(localLoaderInfo2.toString());
        localLoaderInfo2.dump(str2, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      }
    }
    if (this.mInactiveLoaders.size() > 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Inactive Loaders:");
      String str1 = paramString + "    ";
      for (int i = 0; i < this.mInactiveLoaders.size(); i++)
      {
        LoaderInfo localLoaderInfo1 = (LoaderInfo)this.mInactiveLoaders.valueAt(i);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  #");
        paramPrintWriter.print(this.mInactiveLoaders.keyAt(i));
        paramPrintWriter.print(": ");
        paramPrintWriter.println(localLoaderInfo1.toString());
        localLoaderInfo1.dump(str1, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      }
    }
  }

  final void finishRetain()
  {
    if (this.mRetaining)
    {
      this.mRetaining = false;
      for (int i = -1 + this.mLoaders.size(); i >= 0; i--)
      {
        LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.valueAt(i);
        if (localLoaderInfo.mRetaining)
        {
          localLoaderInfo.mRetaining = false;
          if ((localLoaderInfo.mStarted != localLoaderInfo.mRetainingStarted) && (!localLoaderInfo.mStarted))
            localLoaderInfo.stop();
        }
        if ((localLoaderInfo.mStarted) && (localLoaderInfo.mHaveData) && (!localLoaderInfo.mReportNextStart))
          localLoaderInfo.callOnLoadFinished(localLoaderInfo.mLoader, localLoaderInfo.mData);
      }
    }
  }

  public final <D> Loader<D> getLoader(int paramInt)
  {
    if (this.mCreatingLoader)
      throw new IllegalStateException("Called while creating a loader");
    LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.get(paramInt);
    Loader localLoader;
    if (localLoaderInfo != null)
      if (localLoaderInfo.mPendingLoader != null)
        localLoader = localLoaderInfo.mPendingLoader.mLoader;
    while (true)
    {
      return localLoader;
      localLoader = localLoaderInfo.mLoader;
      continue;
      localLoader = null;
    }
  }

  public final boolean hasRunningLoaders()
  {
    boolean bool1 = false;
    int i = this.mLoaders.size();
    int j = 0;
    if (j < i)
    {
      LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.valueAt(j);
      if ((localLoaderInfo.mStarted) && (!localLoaderInfo.mDeliveredData));
      for (boolean bool2 = true; ; bool2 = false)
      {
        bool1 |= bool2;
        j++;
        break;
      }
    }
    return bool1;
  }

  public final <D> Loader<D> initLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<D> paramLoaderCallbacks)
  {
    if (this.mCreatingLoader)
      throw new IllegalStateException("Called while creating a loader");
    LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.get(paramInt);
    if (localLoaderInfo == null)
      localLoaderInfo = createAndInstallLoader(paramInt, paramBundle, paramLoaderCallbacks);
    while (true)
    {
      if ((localLoaderInfo.mHaveData) && (this.mStarted))
        localLoaderInfo.callOnLoadFinished(localLoaderInfo.mLoader, localLoaderInfo.mData);
      return localLoaderInfo.mLoader;
      localLoaderInfo.mCallbacks = paramLoaderCallbacks;
    }
  }

  final void installLoader(LoaderInfo paramLoaderInfo)
  {
    this.mLoaders.put(paramLoaderInfo.mId, paramLoaderInfo);
    if (this.mStarted)
      paramLoaderInfo.start();
  }

  public final <D> Loader<D> restartLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<D> paramLoaderCallbacks)
  {
    if (this.mCreatingLoader)
      throw new IllegalStateException("Called while creating a loader");
    LoaderInfo localLoaderInfo1 = (LoaderInfo)this.mLoaders.get(paramInt);
    if (localLoaderInfo1 != null)
    {
      LoaderInfo localLoaderInfo2 = (LoaderInfo)this.mInactiveLoaders.get(paramInt);
      if (localLoaderInfo2 != null)
      {
        if (!localLoaderInfo1.mHaveData)
          break label105;
        localLoaderInfo2.mDeliveredData = false;
        localLoaderInfo2.destroy();
      }
      localLoaderInfo1.mLoader.abandon();
      this.mInactiveLoaders.put(paramInt, localLoaderInfo1);
    }
    for (Loader localLoader = createAndInstallLoader(paramInt, paramBundle, paramLoaderCallbacks).mLoader; ; localLoader = localLoaderInfo1.mPendingLoader.mLoader)
    {
      return localLoader;
      label105: if (!localLoaderInfo1.mStarted)
      {
        this.mLoaders.put(paramInt, null);
        localLoaderInfo1.destroy();
        break;
      }
      if (localLoaderInfo1.mPendingLoader != null)
      {
        localLoaderInfo1.mPendingLoader.destroy();
        localLoaderInfo1.mPendingLoader = null;
      }
      localLoaderInfo1.mPendingLoader = createLoader(paramInt, paramBundle, paramLoaderCallbacks);
    }
  }

  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("LoaderManager{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    localStringBuilder.append(" in ");
    DebugUtils.buildShortClassTag(this.mActivity, localStringBuilder);
    localStringBuilder.append("}}");
    return localStringBuilder.toString();
  }

  final void updateActivity(FragmentActivity paramFragmentActivity)
  {
    this.mActivity = paramFragmentActivity;
  }

  final class LoaderInfo
    implements Loader.OnLoadCompleteListener<Object>
  {
    final Bundle mArgs;
    LoaderManager.LoaderCallbacks<Object> mCallbacks;
    Object mData;
    boolean mDeliveredData;
    boolean mDestroyed;
    boolean mHaveData;
    final int mId;
    boolean mListenerRegistered;
    Loader<Object> mLoader;
    LoaderInfo mPendingLoader;
    boolean mReportNextStart;
    boolean mRetaining;
    boolean mRetainingStarted;
    boolean mStarted;

    public LoaderInfo(Bundle paramLoaderCallbacks, LoaderManager.LoaderCallbacks<Object> arg3)
    {
      this.mId = paramLoaderCallbacks;
      Object localObject1;
      this.mArgs = localObject1;
      Object localObject2;
      this.mCallbacks = localObject2;
    }

    final void callOnLoadFinished(Loader<Object> paramLoader, Object paramObject)
    {
      String str;
      if (this.mCallbacks != null)
      {
        FragmentActivity localFragmentActivity = LoaderManagerImpl.this.mActivity;
        str = null;
        if (localFragmentActivity != null)
        {
          str = LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause;
          LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = "onLoadFinished";
        }
      }
      try
      {
        this.mCallbacks.onLoadFinished(paramLoader, paramObject);
        if (LoaderManagerImpl.this.mActivity != null)
          LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = str;
        this.mDeliveredData = true;
        return;
      }
      finally
      {
        if (LoaderManagerImpl.this.mActivity != null)
          LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = str;
      }
    }

    final void destroy()
    {
      while (true)
      {
        this.mDestroyed = true;
        boolean bool = this.mDeliveredData;
        this.mDeliveredData = false;
        String str;
        if ((this.mCallbacks != null) && (this.mLoader != null) && (this.mHaveData) && (bool))
        {
          FragmentActivity localFragmentActivity = LoaderManagerImpl.this.mActivity;
          str = null;
          if (localFragmentActivity != null)
          {
            str = LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause;
            LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = "onLoaderReset";
          }
        }
        try
        {
          this.mCallbacks.onLoaderReset(this.mLoader);
          if (LoaderManagerImpl.this.mActivity != null)
            LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = str;
          this.mCallbacks = null;
          this.mData = null;
          this.mHaveData = false;
          if (this.mLoader != null)
          {
            if (this.mListenerRegistered)
            {
              this.mListenerRegistered = false;
              this.mLoader.unregisterListener(this);
            }
            this.mLoader.reset();
          }
          if (this.mPendingLoader != null)
            this = this.mPendingLoader;
        }
        finally
        {
          if (LoaderManagerImpl.this.mActivity != null)
            LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = str;
        }
      }
    }

    public final void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
    {
      while (true)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mId=");
        paramPrintWriter.print(this.mId);
        paramPrintWriter.print(" mArgs=");
        paramPrintWriter.println(this.mArgs);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mCallbacks=");
        paramPrintWriter.println(this.mCallbacks);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mLoader=");
        paramPrintWriter.println(this.mLoader);
        if (this.mLoader != null)
          this.mLoader.dump(paramString + "  ", paramFileDescriptor, paramPrintWriter, paramArrayOfString);
        if ((this.mHaveData) || (this.mDeliveredData))
        {
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("mHaveData=");
          paramPrintWriter.print(this.mHaveData);
          paramPrintWriter.print("  mDeliveredData=");
          paramPrintWriter.println(this.mDeliveredData);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("mData=");
          paramPrintWriter.println(this.mData);
        }
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mStarted=");
        paramPrintWriter.print(this.mStarted);
        paramPrintWriter.print(" mReportNextStart=");
        paramPrintWriter.print(this.mReportNextStart);
        paramPrintWriter.print(" mDestroyed=");
        paramPrintWriter.println(this.mDestroyed);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mRetaining=");
        paramPrintWriter.print(this.mRetaining);
        paramPrintWriter.print(" mRetainingStarted=");
        paramPrintWriter.print(this.mRetainingStarted);
        paramPrintWriter.print(" mListenerRegistered=");
        paramPrintWriter.println(this.mListenerRegistered);
        if (this.mPendingLoader == null)
          break;
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Pending Loader ");
        paramPrintWriter.print(this.mPendingLoader);
        paramPrintWriter.println(":");
        this = this.mPendingLoader;
        paramString = paramString + "  ";
      }
    }

    public final void onLoadComplete(Loader<Object> paramLoader, Object paramObject)
    {
      if (this.mDestroyed);
      while (true)
      {
        return;
        if (LoaderManagerImpl.this.mLoaders.get(this.mId) == this)
        {
          LoaderInfo localLoaderInfo1 = this.mPendingLoader;
          if (localLoaderInfo1 != null)
          {
            this.mPendingLoader = null;
            LoaderManagerImpl.this.mLoaders.put(this.mId, null);
            destroy();
            LoaderManagerImpl.this.installLoader(localLoaderInfo1);
          }
          else
          {
            if ((this.mData != paramObject) || (!this.mHaveData))
            {
              this.mData = paramObject;
              this.mHaveData = true;
              if (this.mStarted)
                callOnLoadFinished(paramLoader, paramObject);
            }
            LoaderInfo localLoaderInfo2 = (LoaderInfo)LoaderManagerImpl.this.mInactiveLoaders.get(this.mId);
            if ((localLoaderInfo2 != null) && (localLoaderInfo2 != this))
            {
              localLoaderInfo2.mDeliveredData = false;
              localLoaderInfo2.destroy();
              LoaderManagerImpl.this.mInactiveLoaders.remove(this.mId);
            }
            if ((LoaderManagerImpl.this.mActivity != null) && (!LoaderManagerImpl.this.hasRunningLoaders()))
              LoaderManagerImpl.this.mActivity.mFragments.startPendingDeferredFragments();
          }
        }
      }
    }

    final void start()
    {
      if ((this.mRetaining) && (this.mRetainingStarted))
        this.mStarted = true;
      while (true)
      {
        return;
        if (!this.mStarted)
        {
          this.mStarted = true;
          if ((this.mLoader == null) && (this.mCallbacks != null))
            this.mLoader = this.mCallbacks.onCreateLoader(this.mId, this.mArgs);
          if (this.mLoader != null)
          {
            if ((this.mLoader.getClass().isMemberClass()) && (!Modifier.isStatic(this.mLoader.getClass().getModifiers())))
              throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + this.mLoader);
            if (!this.mListenerRegistered)
            {
              this.mLoader.registerListener(this.mId, this);
              this.mListenerRegistered = true;
            }
            this.mLoader.startLoading();
          }
        }
      }
    }

    final void stop()
    {
      this.mStarted = false;
      if ((!this.mRetaining) && (this.mLoader != null) && (this.mListenerRegistered))
      {
        this.mListenerRegistered = false;
        this.mLoader.unregisterListener(this);
        this.mLoader.stopLoading();
      }
    }

    public final String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder(64);
      localStringBuilder.append("LoaderInfo{");
      localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      localStringBuilder.append(" #");
      localStringBuilder.append(this.mId);
      localStringBuilder.append(" : ");
      DebugUtils.buildShortClassTag(this.mLoader, localStringBuilder);
      localStringBuilder.append("}}");
      return localStringBuilder.toString();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.app.LoaderManagerImpl
 * JD-Core Version:    0.6.2
 */