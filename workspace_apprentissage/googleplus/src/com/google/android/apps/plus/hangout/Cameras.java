package com.google.android.apps.plus.hangout;

import android.hardware.Camera;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class Cameras
{
  private static Method cameraGetCameraInfo;
  private static Method cameraGetNumberOfCameras;
  private static Class<?> cameraInfoClass;
  private static Field cameraInfoFacing;
  private static int cameraInfoFrontFacingConstant;
  private static Field cameraInfoOrientation;
  private static Method cameraOpen;
  private static boolean gingerbreadCameraApiSupported;
  private static SelectedCameras selectedCameras;

  static
  {
    try
    {
      cameraInfoClass = Class.forName("android.hardware.Camera$CameraInfo");
      Class[] arrayOfClass1 = new Class[1];
      arrayOfClass1[0] = Integer.TYPE;
      cameraOpen = Camera.class.getMethod("open", arrayOfClass1);
      cameraGetNumberOfCameras = Camera.class.getMethod("getNumberOfCameras", new Class[0]);
      Class[] arrayOfClass2 = new Class[2];
      arrayOfClass2[0] = Integer.TYPE;
      arrayOfClass2[1] = cameraInfoClass;
      cameraGetCameraInfo = Camera.class.getMethod("getCameraInfo", arrayOfClass2);
      cameraInfoFacing = cameraInfoClass.getField("facing");
      cameraInfoOrientation = cameraInfoClass.getField("orientation");
      cameraInfoFrontFacingConstant = cameraInfoClass.getField("CAMERA_FACING_FRONT").getInt(null);
      gingerbreadCameraApiSupported = true;
      label120: if (gingerbreadCameraApiSupported)
        selectedCameras = gingerbreadSelectCameras();
      return;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new IllegalStateException(localIllegalAccessException);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      break label120;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      break label120;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      break label120;
    }
  }

  private static CameraProperties gingerbreadGetCameraProperties(int paramInt)
  {
    boolean bool = true;
    if (!gingerbreadCameraApiSupported)
      throw new IllegalStateException("Gingerbread camera API not supported");
    try
    {
      Object localObject = cameraInfoClass.newInstance();
      Method localMethod = cameraGetCameraInfo;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      arrayOfObject[1] = localObject;
      localMethod.invoke(null, arrayOfObject);
      if (cameraInfoFacing.getInt(localObject) == cameraInfoFrontFacingConstant);
      while (true)
      {
        CameraProperties localCameraProperties = new CameraProperties(bool, cameraInfoOrientation.getInt(localObject));
        return localCameraProperties;
        bool = false;
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new IllegalStateException(localIllegalAccessException);
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new IllegalStateException(localInstantiationException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new IllegalStateException(localInvocationTargetException);
    }
  }

  private static int gingerbreadGetNumberOfCameras()
  {
    if (!gingerbreadCameraApiSupported)
      throw new IllegalStateException("Gingerbread camera API not supported");
    try
    {
      int i = ((Integer)cameraGetNumberOfCameras.invoke(null, new Object[0])).intValue();
      return i;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new IllegalStateException(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new IllegalStateException(localInvocationTargetException);
    }
  }

  private static Camera gingerbreadOpenCamera(int paramInt)
  {
    if (!gingerbreadCameraApiSupported)
      throw new IllegalStateException("Gingerbread camera API not supported");
    try
    {
      Method localMethod = cameraOpen;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      Camera localCamera = (Camera)localMethod.invoke(null, arrayOfObject);
      return localCamera;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new IllegalStateException(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new IllegalStateException(localInvocationTargetException);
    }
  }

  private static SelectedCameras gingerbreadSelectCameras()
  {
    if (!gingerbreadCameraApiSupported)
      throw new IllegalStateException("Gingerbread camera API not supported");
    int i = -1;
    Object localObject1 = null;
    int j = -1;
    Object localObject2 = null;
    int k = 0;
    if (k < gingerbreadGetNumberOfCameras())
    {
      CameraProperties localCameraProperties = gingerbreadGetCameraProperties(k);
      if (localCameraProperties.isFrontFacing())
        if (i == -1)
        {
          i = k;
          localObject1 = localCameraProperties;
        }
      while (true)
      {
        k++;
        break;
        if (j == -1)
        {
          j = k;
          localObject2 = localCameraProperties;
        }
      }
    }
    return new SelectedCameras(j, localObject2, i, localObject1);
  }

  public static boolean isAnyCameraAvailable()
  {
    if ((isFrontFacingCameraAvailable()) || (isRearFacingCameraAvailable()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static boolean isFrontFacingCameraAvailable()
  {
    boolean bool1 = gingerbreadCameraApiSupported;
    boolean bool2 = false;
    if (bool1)
    {
      int i = selectedCameras.frontFacingCameraId;
      bool2 = false;
      if (i != -1)
        bool2 = true;
    }
    return bool2;
  }

  public static boolean isRearFacingCameraAvailable()
  {
    boolean bool = true;
    if ((!gingerbreadCameraApiSupported) || (selectedCameras.rearFacingCameraId != -1));
    while (true)
    {
      return bool;
      bool = false;
    }
  }

  public static CameraResult open(CameraType paramCameraType)
  {
    int i;
    CameraProperties localCameraProperties;
    if (gingerbreadCameraApiSupported)
    {
      if (((paramCameraType == CameraType.FrontFacing) && (!isFrontFacingCameraAvailable())) || ((paramCameraType == CameraType.RearFacing) && (!isRearFacingCameraAvailable())))
        throw new IllegalArgumentException("Requested camera type not available");
      if (paramCameraType == CameraType.FrontFacing)
      {
        i = selectedCameras.frontFacingCameraId;
        localCameraProperties = selectedCameras.frontFacingCameraProperties;
      }
    }
    for (CameraResult localCameraResult = new CameraResult(gingerbreadOpenCamera(i), localCameraProperties); ; localCameraResult = new CameraResult(Camera.open(), CameraProperties.FROYO_CAMERA_PROPERTIES))
    {
      return localCameraResult;
      if (paramCameraType == CameraType.RearFacing)
      {
        i = selectedCameras.rearFacingCameraId;
        localCameraProperties = selectedCameras.rearFacingCameraProperties;
        break;
      }
      throw new IllegalArgumentException("Unknown camera type");
      if (paramCameraType == CameraType.FrontFacing)
        throw new IllegalArgumentException("Requested camera type not available");
    }
  }

  public static final class CameraProperties
  {
    public static final CameraProperties FROYO_CAMERA_PROPERTIES = new CameraProperties(false, 90);
    private final boolean frontFacing;
    private final int orientation;

    public CameraProperties(boolean paramBoolean, int paramInt)
    {
      this.frontFacing = paramBoolean;
      this.orientation = paramInt;
    }

    public final int getOrientation()
    {
      return this.orientation;
    }

    public final boolean isFrontFacing()
    {
      return this.frontFacing;
    }
  }

  public static final class CameraResult
  {
    private final Camera camera;
    private final Cameras.CameraProperties properties;

    public CameraResult(Camera paramCamera, Cameras.CameraProperties paramCameraProperties)
    {
      this.camera = paramCamera;
      this.properties = paramCameraProperties;
    }

    public final Camera getCamera()
    {
      return this.camera;
    }

    public final Cameras.CameraProperties getProperties()
    {
      return this.properties;
    }
  }

  public static enum CameraType
  {
    static
    {
      CameraType[] arrayOfCameraType = new CameraType[2];
      arrayOfCameraType[0] = FrontFacing;
      arrayOfCameraType[1] = RearFacing;
    }
  }

  private static final class SelectedCameras
  {
    public final int frontFacingCameraId;
    public final Cameras.CameraProperties frontFacingCameraProperties;
    public final int rearFacingCameraId;
    public final Cameras.CameraProperties rearFacingCameraProperties;

    public SelectedCameras(int paramInt1, Cameras.CameraProperties paramCameraProperties1, int paramInt2, Cameras.CameraProperties paramCameraProperties2)
    {
      this.rearFacingCameraId = paramInt1;
      this.rearFacingCameraProperties = paramCameraProperties1;
      this.frontFacingCameraId = paramInt2;
      this.frontFacingCameraProperties = paramCameraProperties2;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.Cameras
 * JD-Core Version:    0.6.2
 */