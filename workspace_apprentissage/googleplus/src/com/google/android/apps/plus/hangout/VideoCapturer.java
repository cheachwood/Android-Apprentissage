package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.WindowManager;
import java.util.Iterator;
import java.util.List;

public class VideoCapturer
  implements SurfaceHolder.Callback
{
  private volatile Camera camera;
  private Cameras.CameraProperties cameraProperties;
  private Cameras.CameraType cameraType;
  private final Context context;
  private volatile int deviceRotation;
  private boolean flashLightEnabled;
  private volatile int frameRotationBeforeDisplaying;
  private volatile int frameRotationBeforeSending;
  private final SurfaceHolder holder;
  private final Host host;
  protected volatile boolean isCapturing;
  protected boolean isSurfaceReady;
  private final GCommNativeWrapper nativeWrapper;
  private volatile int previewFrameHeight;
  private volatile int previewFrameWidth;
  protected boolean startCapturingWhenSurfaceReady;
  private boolean supportsFlashLight;
  protected SurfaceTexture surfaceTexture;
  private final WindowManager windowManager;

  static
  {
    if (!VideoCapturer.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public VideoCapturer(Context paramContext, GCommNativeWrapper paramGCommNativeWrapper, SurfaceHolder paramSurfaceHolder, Host paramHost)
  {
    this.context = paramContext;
    this.nativeWrapper = paramGCommNativeWrapper;
    this.holder = paramSurfaceHolder;
    this.holder.setType(3);
    this.holder.addCallback(this);
    this.host = paramHost;
    this.windowManager = ((WindowManager)paramContext.getSystemService("window"));
    this.deviceRotation = -1;
  }

  protected VideoCapturer(Context paramContext, GCommNativeWrapper paramGCommNativeWrapper, Host paramHost)
  {
    this.context = paramContext;
    this.nativeWrapper = paramGCommNativeWrapper;
    this.holder = null;
    this.host = paramHost;
    this.windowManager = ((WindowManager)paramContext.getSystemService("window"));
    this.deviceRotation = -1;
  }

  // ERROR //
  private void configureCamera()
  {
    // Byte code:
    //   0: ldc 117
    //   2: invokestatic 123	com/google/android/apps/plus/hangout/Log:debug	(Ljava/lang/String;)V
    //   5: aload_0
    //   6: getfield 125	com/google/android/apps/plus/hangout/VideoCapturer:isSurfaceReady	Z
    //   9: ifeq +289 -> 298
    //   12: aload_0
    //   13: getfield 127	com/google/android/apps/plus/hangout/VideoCapturer:surfaceTexture	Landroid/graphics/SurfaceTexture;
    //   16: ifnull +282 -> 298
    //   19: aload_0
    //   20: getfield 111	com/google/android/apps/plus/hangout/VideoCapturer:camera	Landroid/hardware/Camera;
    //   23: aload_0
    //   24: getfield 127	com/google/android/apps/plus/hangout/VideoCapturer:surfaceTexture	Landroid/graphics/SurfaceTexture;
    //   27: invokevirtual 133	android/hardware/Camera:setPreviewTexture	(Landroid/graphics/SurfaceTexture;)V
    //   30: aload_0
    //   31: getfield 111	com/google/android/apps/plus/hangout/VideoCapturer:camera	Landroid/hardware/Camera;
    //   34: invokevirtual 137	android/hardware/Camera:getParameters	()Landroid/hardware/Camera$Parameters;
    //   37: astore_2
    //   38: aload_0
    //   39: getfield 139	com/google/android/apps/plus/hangout/VideoCapturer:cameraProperties	Lcom/google/android/apps/plus/hangout/Cameras$CameraProperties;
    //   42: aload_2
    //   43: invokestatic 143	com/google/android/apps/plus/hangout/VideoCapturer:getBestMatchPreviewSize	(Lcom/google/android/apps/plus/hangout/Cameras$CameraProperties;Landroid/hardware/Camera$Parameters;)Landroid/hardware/Camera$Size;
    //   46: astore_3
    //   47: aload_2
    //   48: bipush 17
    //   50: invokevirtual 148	android/hardware/Camera$Parameters:setPreviewFormat	(I)V
    //   53: aload_2
    //   54: aload_3
    //   55: getfield 153	android/hardware/Camera$Size:width	I
    //   58: aload_3
    //   59: getfield 156	android/hardware/Camera$Size:height	I
    //   62: invokevirtual 160	android/hardware/Camera$Parameters:setPreviewSize	(II)V
    //   65: iconst_2
    //   66: anewarray 4	java/lang/Object
    //   69: astore 4
    //   71: aload 4
    //   73: iconst_0
    //   74: aload_3
    //   75: getfield 153	android/hardware/Camera$Size:width	I
    //   78: invokestatic 166	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   81: aastore
    //   82: aload 4
    //   84: iconst_1
    //   85: aload_3
    //   86: getfield 156	android/hardware/Camera$Size:height	I
    //   89: invokestatic 166	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   92: aastore
    //   93: ldc 168
    //   95: aload 4
    //   97: invokestatic 172	com/google/android/apps/plus/hangout/Log:info	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   100: aload_0
    //   101: iconst_0
    //   102: putfield 174	com/google/android/apps/plus/hangout/VideoCapturer:supportsFlashLight	Z
    //   105: aload_2
    //   106: invokevirtual 178	android/hardware/Camera$Parameters:getSupportedFlashModes	()Ljava/util/List;
    //   109: astore 5
    //   111: aload 5
    //   113: ifnull +16 -> 129
    //   116: aload_0
    //   117: aload 5
    //   119: ldc 180
    //   121: invokeinterface 186 2 0
    //   126: putfield 174	com/google/android/apps/plus/hangout/VideoCapturer:supportsFlashLight	Z
    //   129: new 188	java/lang/StringBuilder
    //   132: dup
    //   133: ldc 190
    //   135: invokespecial 192	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   138: aload_0
    //   139: getfield 174	com/google/android/apps/plus/hangout/VideoCapturer:supportsFlashLight	Z
    //   142: invokevirtual 196	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   145: invokevirtual 200	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   148: invokestatic 202	com/google/android/apps/plus/hangout/Log:info	(Ljava/lang/String;)V
    //   151: aload_0
    //   152: getfield 174	com/google/android/apps/plus/hangout/VideoCapturer:supportsFlashLight	Z
    //   155: ifeq +21 -> 176
    //   158: aload_0
    //   159: getfield 204	com/google/android/apps/plus/hangout/VideoCapturer:flashLightEnabled	Z
    //   162: ifeq +14 -> 176
    //   165: aload_2
    //   166: ldc 180
    //   168: invokevirtual 207	android/hardware/Camera$Parameters:setFlashMode	(Ljava/lang/String;)V
    //   171: ldc 209
    //   173: invokestatic 123	com/google/android/apps/plus/hangout/Log:debug	(Ljava/lang/String;)V
    //   176: aload_0
    //   177: getfield 111	com/google/android/apps/plus/hangout/VideoCapturer:camera	Landroid/hardware/Camera;
    //   180: aload_2
    //   181: invokevirtual 213	android/hardware/Camera:setParameters	(Landroid/hardware/Camera$Parameters;)V
    //   184: bipush 17
    //   186: invokestatic 219	android/graphics/ImageFormat:getBitsPerPixel	(I)I
    //   189: i2l
    //   190: lstore 6
    //   192: lload 6
    //   194: aload_3
    //   195: getfield 153	android/hardware/Camera$Size:width	I
    //   198: aload_3
    //   199: getfield 156	android/hardware/Camera$Size:height	I
    //   202: imul
    //   203: i2l
    //   204: lmul
    //   205: lstore 8
    //   207: ldc2_w 220
    //   210: lload 8
    //   212: ladd
    //   213: ldc2_w 222
    //   216: ldiv
    //   217: l2i
    //   218: istore 10
    //   220: iconst_3
    //   221: anewarray 4	java/lang/Object
    //   224: astore 11
    //   226: aload 11
    //   228: iconst_0
    //   229: lload 6
    //   231: invokestatic 228	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   234: aastore
    //   235: aload 11
    //   237: iconst_1
    //   238: lload 8
    //   240: invokestatic 228	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   243: aastore
    //   244: aload 11
    //   246: iconst_2
    //   247: iload 10
    //   249: invokestatic 166	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   252: aastore
    //   253: ldc 230
    //   255: aload 11
    //   257: invokestatic 172	com/google/android/apps/plus/hangout/Log:info	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   260: aload_0
    //   261: getfield 111	com/google/android/apps/plus/hangout/VideoCapturer:camera	Landroid/hardware/Camera;
    //   264: aconst_null
    //   265: invokevirtual 234	android/hardware/Camera:setPreviewCallbackWithBuffer	(Landroid/hardware/Camera$PreviewCallback;)V
    //   268: iconst_0
    //   269: istore 12
    //   271: iload 12
    //   273: iconst_5
    //   274: if_icmpge +109 -> 383
    //   277: aload_0
    //   278: getfield 111	com/google/android/apps/plus/hangout/VideoCapturer:camera	Landroid/hardware/Camera;
    //   281: iload 10
    //   283: sipush 1024
    //   286: iadd
    //   287: newarray byte
    //   289: invokevirtual 238	android/hardware/Camera:addCallbackBuffer	([B)V
    //   292: iinc 12 1
    //   295: goto -24 -> 271
    //   298: aload_0
    //   299: getfield 57	com/google/android/apps/plus/hangout/VideoCapturer:holder	Landroid/view/SurfaceHolder;
    //   302: ifnull +36 -> 338
    //   305: aload_0
    //   306: getfield 111	com/google/android/apps/plus/hangout/VideoCapturer:camera	Landroid/hardware/Camera;
    //   309: aload_0
    //   310: getfield 57	com/google/android/apps/plus/hangout/VideoCapturer:holder	Landroid/view/SurfaceHolder;
    //   313: invokevirtual 242	android/hardware/Camera:setPreviewDisplay	(Landroid/view/SurfaceHolder;)V
    //   316: goto -286 -> 30
    //   319: astore_1
    //   320: ldc 244
    //   322: invokestatic 247	com/google/android/apps/plus/hangout/Log:error	(Ljava/lang/String;)V
    //   325: aload_0
    //   326: getfield 111	com/google/android/apps/plus/hangout/VideoCapturer:camera	Landroid/hardware/Camera;
    //   329: invokevirtual 250	android/hardware/Camera:release	()V
    //   332: aload_0
    //   333: aconst_null
    //   334: putfield 111	com/google/android/apps/plus/hangout/VideoCapturer:camera	Landroid/hardware/Camera;
    //   337: return
    //   338: ldc 252
    //   340: invokestatic 247	com/google/android/apps/plus/hangout/Log:error	(Ljava/lang/String;)V
    //   343: goto -313 -> 30
    //   346: astore 21
    //   348: new 188	java/lang/StringBuilder
    //   351: dup
    //   352: ldc 254
    //   354: invokespecial 192	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   357: aload 21
    //   359: invokevirtual 257	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   362: invokevirtual 200	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   365: invokestatic 247	com/google/android/apps/plus/hangout/Log:error	(Ljava/lang/String;)V
    //   368: aload_0
    //   369: getfield 111	com/google/android/apps/plus/hangout/VideoCapturer:camera	Landroid/hardware/Camera;
    //   372: invokevirtual 250	android/hardware/Camera:release	()V
    //   375: aload_0
    //   376: aconst_null
    //   377: putfield 111	com/google/android/apps/plus/hangout/VideoCapturer:camera	Landroid/hardware/Camera;
    //   380: goto -43 -> 337
    //   383: aload_0
    //   384: getfield 111	com/google/android/apps/plus/hangout/VideoCapturer:camera	Landroid/hardware/Camera;
    //   387: new 259	com/google/android/apps/plus/hangout/VideoCapturer$1
    //   390: dup
    //   391: aload_0
    //   392: invokespecial 261	com/google/android/apps/plus/hangout/VideoCapturer$1:<init>	(Lcom/google/android/apps/plus/hangout/VideoCapturer;)V
    //   395: invokevirtual 234	android/hardware/Camera:setPreviewCallbackWithBuffer	(Landroid/hardware/Camera$PreviewCallback;)V
    //   398: aload_0
    //   399: aload_0
    //   400: getfield 81	com/google/android/apps/plus/hangout/VideoCapturer:windowManager	Landroid/view/WindowManager;
    //   403: invokeinterface 90 1 0
    //   408: invokevirtual 96	android/view/Display:getRotation	()I
    //   411: putfield 83	com/google/android/apps/plus/hangout/VideoCapturer:deviceRotation	I
    //   414: aload_0
    //   415: getfield 83	com/google/android/apps/plus/hangout/VideoCapturer:deviceRotation	I
    //   418: tableswitch	default:+30 -> 448, 0:+41->459, 1:+320->738, 2:+327->745, 3:+335->753
    //   449: aconst_null
    //   450: iconst_4
    //   451: dup
    //   452: ldc_w 265
    //   455: invokespecial 266	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   458: athrow
    //   459: iconst_0
    //   460: istore 13
    //   462: aload_0
    //   463: getfield 139	com/google/android/apps/plus/hangout/VideoCapturer:cameraProperties	Lcom/google/android/apps/plus/hangout/Cameras$CameraProperties;
    //   466: invokestatic 272	com/google/android/apps/plus/hangout/Compatibility:getCameraOrientation	(Lcom/google/android/apps/plus/hangout/Cameras$CameraProperties;)I
    //   469: istore 14
    //   471: aload_0
    //   472: getfield 139	com/google/android/apps/plus/hangout/VideoCapturer:cameraProperties	Lcom/google/android/apps/plus/hangout/Cameras$CameraProperties;
    //   475: invokevirtual 277	com/google/android/apps/plus/hangout/Cameras$CameraProperties:isFrontFacing	()Z
    //   478: ifeq +305 -> 783
    //   481: aload_0
    //   482: getfield 139	com/google/android/apps/plus/hangout/VideoCapturer:cameraProperties	Lcom/google/android/apps/plus/hangout/Cameras$CameraProperties;
    //   485: pop
    //   486: getstatic 283	com/google/android/apps/plus/util/Property:HANGOUT_CAMERA_MIRRORED	Lcom/google/android/apps/plus/util/Property;
    //   489: invokevirtual 286	com/google/android/apps/plus/util/Property:get	()Ljava/lang/String;
    //   492: invokevirtual 291	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   495: ldc_w 293
    //   498: invokevirtual 296	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   501: ifne +260 -> 761
    //   504: iconst_1
    //   505: istore 20
    //   507: iload 20
    //   509: ifeq +258 -> 767
    //   512: aload_0
    //   513: sipush 360
    //   516: iload 13
    //   518: iload 14
    //   520: iadd
    //   521: sipush 360
    //   524: irem
    //   525: isub
    //   526: sipush 360
    //   529: irem
    //   530: putfield 105	com/google/android/apps/plus/hangout/VideoCapturer:frameRotationBeforeSending	I
    //   533: aload_0
    //   534: sipush 360
    //   537: iload 13
    //   539: iload 14
    //   541: iadd
    //   542: sipush 360
    //   545: irem
    //   546: isub
    //   547: sipush 360
    //   550: irem
    //   551: putfield 298	com/google/android/apps/plus/hangout/VideoCapturer:frameRotationBeforeDisplaying	I
    //   554: iconst_2
    //   555: anewarray 4	java/lang/Object
    //   558: astore 15
    //   560: aload 15
    //   562: iconst_0
    //   563: iload 13
    //   565: invokestatic 166	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   568: aastore
    //   569: aload 15
    //   571: iconst_1
    //   572: iload 14
    //   574: invokestatic 166	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   577: aastore
    //   578: ldc_w 300
    //   581: aload 15
    //   583: invokestatic 172	com/google/android/apps/plus/hangout/Log:info	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   586: iconst_2
    //   587: anewarray 4	java/lang/Object
    //   590: astore 16
    //   592: aload 16
    //   594: iconst_0
    //   595: aload_0
    //   596: getfield 298	com/google/android/apps/plus/hangout/VideoCapturer:frameRotationBeforeDisplaying	I
    //   599: invokestatic 166	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   602: aastore
    //   603: aload 16
    //   605: iconst_1
    //   606: aload_0
    //   607: getfield 105	com/google/android/apps/plus/hangout/VideoCapturer:frameRotationBeforeSending	I
    //   610: invokestatic 166	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   613: aastore
    //   614: ldc_w 302
    //   617: aload 16
    //   619: invokestatic 172	com/google/android/apps/plus/hangout/Log:info	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   622: aload_0
    //   623: getfield 111	com/google/android/apps/plus/hangout/VideoCapturer:camera	Landroid/hardware/Camera;
    //   626: aload_0
    //   627: getfield 298	com/google/android/apps/plus/hangout/VideoCapturer:frameRotationBeforeDisplaying	I
    //   630: invokevirtual 305	android/hardware/Camera:setDisplayOrientation	(I)V
    //   633: aload_0
    //   634: getfield 307	com/google/android/apps/plus/hangout/VideoCapturer:previewFrameWidth	I
    //   637: istore 17
    //   639: aload_0
    //   640: getfield 309	com/google/android/apps/plus/hangout/VideoCapturer:previewFrameHeight	I
    //   643: istore 18
    //   645: aload_0
    //   646: getfield 298	com/google/android/apps/plus/hangout/VideoCapturer:frameRotationBeforeDisplaying	I
    //   649: lookupswitch	default:+43->692, 0:+171->820, 90:+190->839, 180:+171->820, 270:+190->839
    //   693: getfield 307	com/google/android/apps/plus/hangout/VideoCapturer:previewFrameWidth	I
    //   696: iload 17
    //   698: if_icmpne +12 -> 710
    //   701: aload_0
    //   702: getfield 309	com/google/android/apps/plus/hangout/VideoCapturer:previewFrameHeight	I
    //   705: iload 18
    //   707: if_icmpeq -370 -> 337
    //   710: aload_0
    //   711: getfield 53	com/google/android/apps/plus/hangout/VideoCapturer:context	Landroid/content/Context;
    //   714: sipush 204
    //   717: new 311	com/google/android/apps/plus/hangout/RectangleDimensions
    //   720: dup
    //   721: aload_0
    //   722: getfield 307	com/google/android/apps/plus/hangout/VideoCapturer:previewFrameWidth	I
    //   725: aload_0
    //   726: getfield 309	com/google/android/apps/plus/hangout/VideoCapturer:previewFrameHeight	I
    //   729: invokespecial 313	com/google/android/apps/plus/hangout/RectangleDimensions:<init>	(II)V
    //   732: invokestatic 319	com/google/android/apps/plus/hangout/GCommApp:sendObjectMessage	(Landroid/content/Context;ILjava/lang/Object;)V
    //   735: goto -398 -> 337
    //   738: bipush 90
    //   740: istore 13
    //   742: goto -280 -> 462
    //   745: sipush 180
    //   748: istore 13
    //   750: goto -288 -> 462
    //   753: sipush 270
    //   756: istore 13
    //   758: goto -296 -> 462
    //   761: iconst_0
    //   762: istore 20
    //   764: goto -257 -> 507
    //   767: aload_0
    //   768: iload 14
    //   770: iload 13
    //   772: iadd
    //   773: sipush 360
    //   776: irem
    //   777: putfield 105	com/google/android/apps/plus/hangout/VideoCapturer:frameRotationBeforeSending	I
    //   780: goto -247 -> 533
    //   783: aload_0
    //   784: sipush 360
    //   787: iload 14
    //   789: iload 13
    //   791: isub
    //   792: iadd
    //   793: sipush 360
    //   796: irem
    //   797: putfield 105	com/google/android/apps/plus/hangout/VideoCapturer:frameRotationBeforeSending	I
    //   800: aload_0
    //   801: sipush 360
    //   804: iload 14
    //   806: iload 13
    //   808: isub
    //   809: iadd
    //   810: sipush 360
    //   813: irem
    //   814: putfield 298	com/google/android/apps/plus/hangout/VideoCapturer:frameRotationBeforeDisplaying	I
    //   817: goto -263 -> 554
    //   820: aload_0
    //   821: aload_3
    //   822: getfield 153	android/hardware/Camera$Size:width	I
    //   825: putfield 307	com/google/android/apps/plus/hangout/VideoCapturer:previewFrameWidth	I
    //   828: aload_0
    //   829: aload_3
    //   830: getfield 156	android/hardware/Camera$Size:height	I
    //   833: putfield 309	com/google/android/apps/plus/hangout/VideoCapturer:previewFrameHeight	I
    //   836: goto -144 -> 692
    //   839: aload_0
    //   840: aload_3
    //   841: getfield 156	android/hardware/Camera$Size:height	I
    //   844: putfield 307	com/google/android/apps/plus/hangout/VideoCapturer:previewFrameWidth	I
    //   847: aload_0
    //   848: aload_3
    //   849: getfield 153	android/hardware/Camera$Size:width	I
    //   852: putfield 309	com/google/android/apps/plus/hangout/VideoCapturer:previewFrameHeight	I
    //   855: goto -163 -> 692
    //
    // Exception table:
    //   from	to	target	type
    //   5	30	319	java/io/IOException
    //   298	316	319	java/io/IOException
    //   338	343	319	java/io/IOException
    //   277	292	346	java/lang/OutOfMemoryError
  }

  private static Camera.Size getBestMatchPreviewSize(Cameras.CameraProperties paramCameraProperties, Camera.Parameters paramParameters)
  {
    List localList = Compatibility.getSupportedPreviewSizes(paramParameters, paramCameraProperties);
    Object localObject = null;
    if (localList != null)
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Camera.Size localSize = (Camera.Size)localIterator.next();
        long l = getMisMatchArea(localSize);
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = Integer.valueOf(localSize.width);
        arrayOfObject[1] = Integer.valueOf(localSize.height);
        if (paramParameters != null);
        for (int i = paramParameters.getPreviewFrameRate(); ; i = 0)
        {
          arrayOfObject[2] = Integer.valueOf(i);
          arrayOfObject[3] = Long.valueOf(l);
          Log.info("Supported camera preview size: %d x %d x %d. mismatch area=%d", arrayOfObject);
          if (localObject != null)
            break label131;
          localObject = localSize;
          break;
        }
        label131: if (l < getMisMatchArea(localObject))
          localObject = localSize;
      }
    }
    return localObject;
  }

  private static long getMisMatchArea(Camera.Size paramSize)
  {
    long l;
    if ((paramSize.width <= 480) && (paramSize.height <= 360))
      l = (480 - paramSize.width) * paramSize.height + 480L * (360 - paramSize.height);
    while (true)
    {
      return l;
      if ((paramSize.width > 480) && (paramSize.height > 360))
      {
        l = 360L * (-480 + paramSize.width) + (-360 + paramSize.height) * paramSize.width;
      }
      else if (paramSize.width > 480)
      {
        assert (paramSize.height <= 360);
        l = (-480 + paramSize.width) * paramSize.height + 480L * (360 - paramSize.height);
      }
      else
      {
        assert ((paramSize.width <= 480) && (paramSize.height > 360));
        l = 360L * (480 - paramSize.width) + (-360 + paramSize.height) * paramSize.width;
      }
    }
  }

  public static Camera.Size getSizeOfCapturedFrames(Cameras.CameraType paramCameraType)
  {
    try
    {
      Cameras.CameraResult localCameraResult = Cameras.open(paramCameraType);
      Camera localCamera = localCameraResult.getCamera();
      localSize = getBestMatchPreviewSize(localCameraResult.getProperties(), localCamera.getParameters());
      if (localSize != null)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(localSize.width);
        arrayOfObject[1] = Integer.valueOf(localSize.height);
        Log.info("Size of captured frames %d x %d", arrayOfObject);
      }
      localCamera.release();
      return localSize;
    }
    catch (RuntimeException localRuntimeException)
    {
      while (true)
      {
        Log.error("Error opening camera: " + localRuntimeException);
        Camera.Size localSize = null;
      }
    }
  }

  public final boolean flashLightEnabled()
  {
    return this.flashLightEnabled;
  }

  public final boolean isCapturing()
  {
    return this.isCapturing;
  }

  public final void start(Cameras.CameraType paramCameraType)
  {
    this.cameraType = paramCameraType;
    this.startCapturingWhenSurfaceReady = true;
    if (this.isSurfaceReady)
      startCapturing();
  }

  protected final boolean startCapturing()
  {
    boolean bool = true;
    try
    {
      if (this.isCapturing);
      while (true)
      {
        return bool;
        try
        {
          Cameras.CameraResult localCameraResult = Cameras.open(this.cameraType);
          this.camera = localCameraResult.getCamera();
          this.cameraProperties = localCameraResult.getProperties();
          configureCamera();
          this.camera.startPreview();
          this.isCapturing = true;
          Host localHost2 = this.host;
          localHost2.onCapturingStateChanged$1385ff();
        }
        catch (RuntimeException localRuntimeException)
        {
          if (this.camera != null)
          {
            this.camera.release();
            this.camera = null;
          }
          this.isCapturing = false;
          Host localHost1 = this.host;
          localHost1.onCapturingStateChanged$1385ff();
          this.host.onCameraOpenError(localRuntimeException);
          bool = false;
        }
      }
    }
    finally
    {
    }
  }

  public final void stop()
  {
    stopCapturing();
    this.startCapturingWhenSurfaceReady = false;
  }

  protected final void stopCapturing()
  {
    Log.debug("*** stopCapturing");
    try
    {
      if (this.isCapturing)
      {
        this.camera.stopPreview();
        this.camera.release();
        this.camera = null;
        this.isCapturing = false;
        Host localHost = this.host;
        localHost.onCapturingStateChanged$1385ff();
        this.previewFrameWidth = 0;
        this.previewFrameHeight = 0;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final boolean supportsFlashLight()
  {
    return this.supportsFlashLight;
  }

  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
  {
    this.isSurfaceReady = true;
    if ((this.startCapturingWhenSurfaceReady) && (!this.isCapturing))
      startCapturing();
  }

  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
  }

  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    this.isSurfaceReady = false;
    stopCapturing();
  }

  public final void toggleFlashLightEnabled()
  {
    if (!this.supportsFlashLight)
      return;
    boolean bool;
    label17: Camera.Parameters localParameters;
    if (!this.flashLightEnabled)
    {
      bool = true;
      this.flashLightEnabled = bool;
      localParameters = this.camera.getParameters();
      if (!this.flashLightEnabled)
        break label64;
      localParameters.setFlashMode("torch");
      Log.debug("Turning on flash light in torch mode");
    }
    while (true)
    {
      this.camera.setParameters(localParameters);
      break;
      bool = false;
      break label17;
      label64: localParameters.setFlashMode("off");
      Log.debug("Turning off flash light in torch mode");
    }
  }

  static abstract interface Host
  {
    public abstract void onCameraOpenError(RuntimeException paramRuntimeException);

    public abstract void onCapturingStateChanged$1385ff();
  }

  public static final class TextureViewVideoCapturer extends VideoCapturer
    implements TextureView.SurfaceTextureListener
  {
    protected final TextureView textureView;

    public TextureViewVideoCapturer(Context paramContext, GCommNativeWrapper paramGCommNativeWrapper, TextureView paramTextureView, VideoCapturer.Host paramHost)
    {
      super(paramGCommNativeWrapper, paramHost);
      this.textureView = paramTextureView;
      this.textureView.setSurfaceTextureListener(this);
    }

    public final void onSurfaceTextureAvailable(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2)
    {
      this.surfaceTexture = paramSurfaceTexture;
      this.isSurfaceReady = true;
      if ((this.startCapturingWhenSurfaceReady) && (!this.isCapturing))
        startCapturing();
    }

    public final boolean onSurfaceTextureDestroyed(SurfaceTexture paramSurfaceTexture)
    {
      this.isSurfaceReady = false;
      stopCapturing();
      this.surfaceTexture = null;
      return true;
    }

    public final void onSurfaceTextureSizeChanged(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2)
    {
    }

    public final void onSurfaceTextureUpdated(SurfaceTexture paramSurfaceTexture)
    {
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.VideoCapturer
 * JD-Core Version:    0.6.2
 */