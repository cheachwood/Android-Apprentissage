package com.google.android.apps.plus.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.panorama.PanoramaClient;
import com.google.android.gms.panorama.PanoramaClient.OnPanoramaInfoLoadedListener;
import java.util.ArrayList;
import java.util.HashMap;

public final class PanoramaDetector extends HandlerThread
  implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener
{
  private static PanoramaDetector sDetector;
  private static final HashMap<Uri, Boolean> sPanoramaMap = new HashMap();
  private PanoramaClient mClient;
  private Context mContext;
  private Handler mHandler;
  private ArrayList<DetectionRequest> mQueue = new ArrayList();
  private boolean mWaitingForConnection = true;

  private PanoramaDetector(Context paramContext)
  {
    super("PanoramaDetector", 10);
    this.mContext = paramContext.getApplicationContext();
  }

  public static void clearCache()
  {
    sPanoramaMap.clear();
  }

  private void connect()
  {
    try
    {
      this.mWaitingForConnection = true;
      new Handler(Looper.getMainLooper()).post(new Runnable()
      {
        public final void run()
        {
          PanoramaDetector.access$202(PanoramaDetector.this, new PanoramaClient(PanoramaDetector.this.mContext, PanoramaDetector.this, PanoramaDetector.this));
          PanoramaDetector.this.mClient.connect();
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static void detectPanorama(Context paramContext, Resource paramResource, Uri paramUri)
  {
    try
    {
      Boolean localBoolean = (Boolean)sPanoramaMap.get(paramUri);
      if (localBoolean != null)
        if (localBoolean.booleanValue())
          paramResource.deliverResourceType(2);
      while (true)
      {
        return;
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext) == 0)
        {
          if (sDetector == null)
          {
            PanoramaDetector localPanoramaDetector = new PanoramaDetector(paramContext);
            sDetector = localPanoramaDetector;
            localPanoramaDetector.start();
          }
          sDetector.detectPanorama(paramResource, paramUri);
        }
      }
    }
    finally
    {
    }
  }

  private void detectPanorama(Resource paramResource, Uri paramUri)
  {
    try
    {
      if (this.mHandler != null)
        this.mHandler.removeMessages(1);
      DetectionRequest localDetectionRequest = new DetectionRequest(paramResource, paramUri);
      if (((this.mClient == null) || (!this.mClient.isConnected())) && (!this.mWaitingForConnection))
        connect();
      if (this.mWaitingForConnection)
        this.mQueue.add(localDetectionRequest);
      while (true)
      {
        return;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(0, localDetectionRequest));
      }
    }
    finally
    {
    }
  }

  protected final void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return;
      DetectionRequest localDetectionRequest = (DetectionRequest)paramMessage.obj;
      if (this.mClient != null)
      {
        if (EsLog.isLoggable("PanoramaDetector", 3))
          Log.d("PanoramaDetector", "Detecting if the image is a panorama: " + localDetectionRequest.mUri);
        this.mClient.loadPanoramaInfo(localDetectionRequest, localDetectionRequest.mUri);
        continue;
        if (this.mClient != null)
        {
          if (EsLog.isLoggable("PanoramaDetector", 3))
            Log.d("PanoramaDetector", "Disconnecting from GooglePlayServices");
          this.mClient.disconnect();
        }
      }
    }
  }

  public final void onConnected()
  {
    while (true)
    {
      try
      {
        if (EsLog.isLoggable("PanoramaDetector", 3))
        {
          Log.d("PanoramaDetector", "Connected to GooglePlayServices");
          break label83;
          if (i < this.mQueue.size())
          {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(0, this.mQueue.get(i)));
            i++;
            continue;
          }
          this.mQueue.clear();
          this.mWaitingForConnection = false;
          return;
        }
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
      label83: int i = 0;
    }
  }

  public final void onConnectionFailed$5d4cef71()
  {
    try
    {
      this.mClient = null;
      this.mQueue.clear();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final void onDisconnected()
  {
    this.mClient = null;
    this.mQueue.clear();
  }

  public final void start()
  {
    try
    {
      super.start();
      this.mHandler = new Handler(getLooper())
      {
        public final void handleMessage(Message paramAnonymousMessage)
        {
          PanoramaDetector.this.handleMessage(paramAnonymousMessage);
        }
      };
      connect();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private final class DetectionRequest
    implements PanoramaClient.OnPanoramaInfoLoadedListener
  {
    private Resource mResource;
    private Uri mUri;

    public DetectionRequest(Resource paramUri, Uri arg3)
    {
      this.mResource = paramUri;
      Object localObject;
      this.mUri = localObject;
    }

    public final void onPanoramaInfoLoaded$680664b4(Intent paramIntent)
    {
      PanoramaDetector localPanoramaDetector = PanoramaDetector.this;
      Resource localResource = this.mResource;
      Uri localUri = this.mUri;
      if (paramIntent != null);
      for (boolean bool = true; ; bool = false)
      {
        PanoramaDetector.access$000(localPanoramaDetector, localResource, localUri, bool);
        return;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.PanoramaDetector
 * JD-Core Version:    0.6.2
 */