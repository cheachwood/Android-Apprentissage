package com.google.android.apps.plus.hangout;

import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import com.google.android.apps.plus.util.Property;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Compatibility
{
  public static int getCameraOrientation(Cameras.CameraProperties paramCameraProperties)
  {
    int i;
    int j;
    if ((Build.MANUFACTURER.equals("HTC")) && ((Build.MODEL.equals("PC36100")) || (Build.MODEL.equals("myTouch_4G")) || (Build.MODEL.equals("HTC Glacier")) || (Build.MODEL.equals("ADR6400L")) || (Build.MODEL.equals("HTC Incredible S S710e")) || (Build.MODEL.equals("A9191"))))
    {
      i = 1;
      if ((i == 0) || (!paramCameraProperties.isFrontFacing()))
        break label101;
      j = 270;
    }
    while (true)
    {
      return j;
      i = 0;
      break;
      try
      {
        label101: j = Integer.parseInt(Property.HANGOUT_CAMERA_ORIENTATION.get());
        Log.info("Using camera orientation of: " + j);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        j = paramCameraProperties.getOrientation();
      }
    }
  }

  public static List<Camera.Size> getSupportedPreviewSizes(Camera.Parameters paramParameters, Cameras.CameraProperties paramCameraProperties)
  {
    List localList = paramParameters.getSupportedPreviewSizes();
    if ((Build.MANUFACTURER.equals("motorola")) && (Build.MODEL.equals("DROID3")));
    for (int i = 1; (i != 0) && (paramCameraProperties.isFrontFacing()); i = 0)
    {
      localObject = new ArrayList(-1 + localList.size());
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Camera.Size localSize = (Camera.Size)localIterator.next();
        if ((localSize.width != 240) || (localSize.height != 160))
          ((ArrayList)localObject).add(localSize);
      }
    }
    Object localObject = localList;
    return localObject;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.Compatibility
 * JD-Core Version:    0.6.2
 */