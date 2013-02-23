package com.google.android.apps.plus.network;

import com.google.android.apps.plus.json.EsJson;
import com.google.android.apps.plus.json.GenericJson;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.util.EncodingUtils;

public final class JsonHttpEntity<Request extends GenericJson> extends AbstractHttpEntity
{
  private static final byte[] BOUNDARY_HYPHEN_BYTES = EncodingUtils.getAsciiBytes("--");
  private static final byte[] CONTENT_TYPE_BYTES = EncodingUtils.getAsciiBytes("Content-Type: ");
  private static final byte[] CRLF_BYTES = EncodingUtils.getAsciiBytes("\r\n");
  private static final byte[] DEFAULT_BOUNDARY_BYTES = "onetwothreefourfivesixseven".getBytes();
  private final EsJson<Request> mJsonGenerator;
  private final byte[] mPayloadBytes;
  private final Request mRequest;

  public JsonHttpEntity(EsJson<Request> paramEsJson, Request paramRequest)
  {
    this(paramEsJson, paramRequest, null);
  }

  public JsonHttpEntity(EsJson<Request> paramEsJson, Request paramRequest, byte[] paramArrayOfByte)
  {
    this.mJsonGenerator = paramEsJson;
    this.mRequest = paramRequest;
    this.mPayloadBytes = paramArrayOfByte;
    if (this.mPayloadBytes == null)
    {
      setContentType("application/octet-stream");
      setContentEncoding("gzip");
    }
    while (true)
    {
      return;
      setContentType("multipart/related");
    }
  }

  private static void writeBoundary(OutputStream paramOutputStream, boolean paramBoolean)
    throws IOException
  {
    paramOutputStream.write(BOUNDARY_HYPHEN_BYTES);
    paramOutputStream.write(DEFAULT_BOUNDARY_BYTES);
    if (paramBoolean)
      paramOutputStream.write(BOUNDARY_HYPHEN_BYTES);
    paramOutputStream.write(CRLF_BYTES);
  }

  private static void writeMetaData(OutputStream paramOutputStream, byte[] paramArrayOfByte)
    throws IOException
  {
    paramOutputStream.write(CONTENT_TYPE_BYTES);
    paramOutputStream.write(EncodingUtils.getAsciiBytes("application/json; charset=UTF-8"));
    paramOutputStream.write(CRLF_BYTES);
    paramOutputStream.write(CRLF_BYTES);
    paramOutputStream.write(paramArrayOfByte);
    paramOutputStream.write(CRLF_BYTES);
  }

  private static void writePayload(OutputStream paramOutputStream, byte[] paramArrayOfByte)
    throws IOException
  {
    paramOutputStream.write(CONTENT_TYPE_BYTES);
    paramOutputStream.write(EncodingUtils.getAsciiBytes("image/jpeg"));
    paramOutputStream.write(CRLF_BYTES);
    paramOutputStream.write("Content-Transfer-Encoding: binary".getBytes());
    paramOutputStream.write(CRLF_BYTES);
    paramOutputStream.write(CRLF_BYTES);
    paramOutputStream.write(paramArrayOfByte);
    paramOutputStream.write(CRLF_BYTES);
  }

  public final InputStream getContent()
    throws IOException
  {
    ByteArrayInputStream localByteArrayInputStream;
    if (this.mJsonGenerator == null)
      localByteArrayInputStream = new ByteArrayInputStream(new byte[0]);
    while (true)
    {
      return localByteArrayInputStream;
      if (this.mPayloadBytes == null)
      {
        localByteArrayInputStream = new ByteArrayInputStream(this.mJsonGenerator.toByteArray(this.mRequest));
      }
      else
      {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        writeBoundary(localByteArrayOutputStream, false);
        writeMetaData(localByteArrayOutputStream, this.mJsonGenerator.toByteArray(this.mRequest));
        writeBoundary(localByteArrayOutputStream, false);
        writePayload(localByteArrayOutputStream, this.mPayloadBytes);
        writeBoundary(localByteArrayOutputStream, true);
        localByteArrayInputStream = new ByteArrayInputStream(localByteArrayOutputStream.toByteArray());
      }
    }
  }

  public final long getContentLength()
  {
    return -1L;
  }

  public final boolean isRepeatable()
  {
    return true;
  }

  public final boolean isStreaming()
  {
    return false;
  }

  public final void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    if ((this.mJsonGenerator != null) && (this.mPayloadBytes != null))
    {
      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(paramOutputStream);
      writeBoundary(localBufferedOutputStream, false);
      writeMetaData(localBufferedOutputStream, this.mJsonGenerator.toByteArray(this.mRequest));
      writeBoundary(localBufferedOutputStream, false);
      writePayload(localBufferedOutputStream, this.mPayloadBytes);
      writeBoundary(localBufferedOutputStream, true);
      localBufferedOutputStream.flush();
      localBufferedOutputStream.close();
    }
    while (true)
    {
      return;
      if (this.mJsonGenerator == null)
        break;
      GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(new BufferedOutputStream(paramOutputStream));
      this.mJsonGenerator.writeToStream(localGZIPOutputStream, this.mRequest);
      localGZIPOutputStream.close();
    }
    throw new IllegalArgumentException("A jsonGenerator was not found!");
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.JsonHttpEntity
 * JD-Core Version:    0.6.2
 */