package com.moon.util.image;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class PNGDecoder {
   private static final byte[] SIGNATURE = new byte[]{-119, 80, 78, 71, 13, 10, 26, 10};
   private final InputStream input;
   private final CRC32 crc;
   private final byte[] buffer;
   private int chunkLength;
   private int chunkType;
   private int chunkRemaining;
   private int width;
   private int height;
   private int bitdepth;
   private int colorType;
   private int bytesPerPixel;
   private byte[] palette;
   private byte[] paletteA;
   private byte[] transPixel;
   static final boolean assertionsDisabled = !PNGDecoder.class.desiredAssertionStatus();

   public PNGDecoder(InputStream inputStream) throws IOException {
      this.input = inputStream;
      this.crc = new CRC32();
      this.buffer = new byte[4096];
      this.readFully(this.buffer, 0, SIGNATURE.length);
      if (!checkSignature(this.buffer)) {
         throw new IOException("Not a valid PNG file");
      }

      this.openChunk(1229472850);
      this.readIHDR();
      this.closeChunk();

      while (true) {
         this.openChunk();
         switch (this.chunkType) {
            case 1229209940:
               if (this.colorType == 3 && this.palette == null) {
                  throw new IOException("Missing PLTE chunk");
               }

               return;
            case 1347179589:
               this.readPLTE();
               break;
            case 1951551059:
               this.readtRNS();
         }

         this.closeChunk();
      }
   }

   public int getHeight() {
      return this.height;
   }

   public int getWidth() {
      return this.width;
   }

   public boolean hasAlphaChannel() {
      return this.colorType == 6 || this.colorType == 4;
   }

   public boolean hasAlpha() {
      return this.hasAlphaChannel() || this.paletteA != null || this.transPixel != null;
   }

   public boolean isRGB() {
      return this.colorType == 6 || this.colorType == 2 || this.colorType == 3;
   }

   public void setTransparency(byte r, byte g, byte b) {
      if (this.hasAlphaChannel()) {
         throw new UnsupportedOperationException("image has an alpha channel");
      }

      byte[] pal = this.palette;
      if (pal == null) {
         this.transPixel = new byte[]{0, r, 0, g, 0, b};
      } else {
         this.paletteA = new byte[pal.length / 3];
         int palIndex = 0;

         for (int i = 0; palIndex < pal.length; i++) {
            if (pal[palIndex] != r || pal[palIndex + 1] != g || pal[palIndex + 2] != b) {
               this.paletteA[i] = -1;
            }

            palIndex += 3;
         }
      }
   }

   public PNGDecoder.Format decideTextureFormat(PNGDecoder.Format format) {
      switch (this.colorType) {
         case 0:
            switch (format) {
               case LUMINANCE:
               case ALPHA:
                  return format;
               default:
                  return PNGDecoder.Format.LUMINANCE;
            }
         case 1:
         case 5:
         default:
            throw new UnsupportedOperationException("Not yet implemented");
         case 2:
            switch (format) {
               case ABGR:
               case RGBA:
               case BGRA:
               case RGB:
                  return format;
               default:
                  return PNGDecoder.Format.RGB;
            }
         case 3:
            switch (format) {
               case ABGR:
               case RGBA:
               case BGRA:
                  return format;
               default:
                  return PNGDecoder.Format.RGBA;
            }
         case 4:
            return PNGDecoder.Format.LUMINANCE_ALPHA;
         case 6:
            switch (format) {
               case ABGR:
               case RGBA:
               case BGRA:
               case RGB:
                  return format;
               default:
                  return PNGDecoder.Format.RGBA;
            }
      }
   }

   public void decode(ByteBuffer buffer, int stride, PNGDecoder.Format format) throws IOException {
      int startPos = buffer.position();
      int lineSize = (this.width * this.bitdepth + 7) / 8 * this.bytesPerPixel;
      byte[] curLine = new byte[lineSize + 1];
      byte[] prevLine = new byte[lineSize + 1];
      byte[] palLine = this.bitdepth < 8 ? new byte[this.width + 1] : null;
      Inflater inflater = new Inflater();

      try {
         for (int y = 0; y < this.height; y++) {
            this.readChunkUnzip(inflater, curLine, 0, curLine.length);
            this.unfilter(curLine, prevLine);
            buffer.position(startPos + y * stride);
            label109:
            switch (this.colorType) {
               case 0:
                  switch (format) {
                     case LUMINANCE:
                     case ALPHA:
                        this.copy(buffer, curLine);
                        break label109;
                     default:
                        throw new UnsupportedOperationException("Unsupported format for this image");
                  }
               case 1:
               case 5:
               default:
                  throw new UnsupportedOperationException("Not yet implemented");
               case 2:
                  switch (format) {
                     case ABGR:
                        this.copyRGBtoABGR(buffer, curLine);
                        break label109;
                     case RGBA:
                        this.copyRGBtoRGBA(buffer, curLine);
                        break label109;
                     case BGRA:
                        this.copyRGBtoBGRA(buffer, curLine);
                        break label109;
                     case RGB:
                        this.copy(buffer, curLine);
                        break label109;
                     default:
                        throw new UnsupportedOperationException("Unsupported format for this image");
                  }
               case 3:
                  switch (this.bitdepth) {
                     case 1:
                        this.expand1(curLine, palLine);
                        break;
                     case 2:
                        this.expand2(curLine, palLine);
                        break;
                     case 3:
                     case 5:
                     case 6:
                     case 7:
                     default:
                        throw new UnsupportedOperationException("Unsupported bitdepth for this image");
                     case 4:
                        this.expand4(curLine, palLine);
                        break;
                     case 8:
                        palLine = curLine;
                  }

                  switch (format) {
                     case ABGR:
                        this.copyPALtoABGR(buffer, palLine);
                        break label109;
                     case RGBA:
                        this.copyPALtoRGBA(buffer, palLine);
                        break label109;
                     case BGRA:
                        this.copyPALtoBGRA(buffer, palLine);
                        break label109;
                     default:
                        throw new UnsupportedOperationException("Unsupported format for this image");
                  }
               case 4:
                  switch (format) {
                     case LUMINANCE_ALPHA:
                        this.copy(buffer, curLine);
                        break label109;
                     default:
                        throw new UnsupportedOperationException("Unsupported format for this image");
                  }
               case 6:
                  switch (format) {
                     case ABGR:
                        this.copyRGBAtoABGR(buffer, curLine);
                        break;
                     case RGBA:
                        this.copy(buffer, curLine);
                        break;
                     case BGRA:
                        this.copyRGBAtoBGRA(buffer, curLine);
                        break;
                     case RGB:
                        this.copyRGBAtoRGB(buffer, curLine);
                        break;
                     default:
                        throw new UnsupportedOperationException("Unsupported format for this image");
                  }
            }

            byte[] tmp = curLine;
            curLine = prevLine;
            prevLine = tmp;
         }
      } finally {
         inflater.end();
      }
   }

   public void decodeFlipped(ByteBuffer buffer, int stride, PNGDecoder.Format format) throws IOException {
      if (stride <= 0) {
         throw new IllegalArgumentException("stride");
      }

      int startPos = buffer.position();
      int offset = (this.height - 1) * stride;
      buffer.position(startPos + offset);
      this.decode(buffer, -stride, format);
      buffer.position(buffer.position() + offset);
   }

   private void copy(ByteBuffer buffer, byte[] scanline) {
      buffer.put(scanline, 1, scanline.length - 1);
   }

   private void copyRGBtoABGR(ByteBuffer buffer, byte[] scanline) {
      if (this.transPixel != null) {
         byte transR = this.transPixel[1];
         byte transG = this.transPixel[3];
         byte transB = this.transPixel[5];
         int i = 1;

         for (int len = scanline.length; i < len; i += 3) {
            byte r = scanline[i];
            byte g = scanline[i + 1];
            byte b = scanline[i + 2];
            byte a = -1;
            if (r == transR && g == transG && b == transB) {
               a = 0;
            }

            buffer.put(a).put(b).put(g).put(r);
         }
      } else {
         int i = 1;

         for (int len = scanline.length; i < len; i += 3) {
            buffer.put((byte)-1).put(scanline[i + 2]).put(scanline[i + 1]).put(scanline[i]);
         }
      }
   }

   private void copyRGBtoRGBA(ByteBuffer buffer, byte[] scanline) {
      if (this.transPixel != null) {
         byte transR = this.transPixel[1];
         byte transG = this.transPixel[3];
         byte transB = this.transPixel[5];
         int i = 1;

         for (int len = scanline.length; i < len; i += 3) {
            byte r = scanline[i];
            byte g = scanline[i + 1];
            byte b = scanline[i + 2];
            byte a = -1;
            if (r == transR && g == transG && b == transB) {
               a = 0;
            }

            buffer.put(r).put(g).put(b).put(a);
         }
      } else {
         int i = 1;

         for (int len = scanline.length; i < len; i += 3) {
            buffer.put(scanline[i]).put(scanline[i + 1]).put(scanline[i + 2]).put((byte)-1);
         }
      }
   }

   private void copyRGBtoBGRA(ByteBuffer buffer, byte[] scanline) {
      if (this.transPixel != null) {
         byte transR = this.transPixel[1];
         byte transG = this.transPixel[3];
         byte transB = this.transPixel[5];
         int i = 1;

         for (int len = scanline.length; i < len; i += 3) {
            byte r = scanline[i];
            byte g = scanline[i + 1];
            byte b = scanline[i + 2];
            byte a = -1;
            if (r == transR && g == transG && b == transB) {
               a = 0;
            }

            buffer.put(b).put(g).put(r).put(a);
         }
      } else {
         int i = 1;

         for (int len = scanline.length; i < len; i += 3) {
            buffer.put(scanline[i + 2]).put(scanline[i + 1]).put(scanline[i]).put((byte)-1);
         }
      }
   }

   private void copyRGBAtoABGR(ByteBuffer buffer, byte[] scanline) {
      int i = 1;

      for (int len = scanline.length; i < len; i += 4) {
         buffer.put(scanline[i + 3]).put(scanline[i + 2]).put(scanline[i + 1]).put(scanline[i]);
      }
   }

   private void copyRGBAtoBGRA(ByteBuffer buffer, byte[] scanline) {
      int i = 1;

      for (int len = scanline.length; i < len; i += 4) {
         buffer.put(scanline[i + 2]).put(scanline[i + 1]).put(scanline[i]).put(scanline[i + 3]);
      }
   }

   private void copyRGBAtoRGB(ByteBuffer buffer, byte[] scanline) {
      int i = 1;

      for (int len = scanline.length; i < len; i += 4) {
         buffer.put(scanline[i]).put(scanline[i + 1]).put(scanline[i + 2]);
      }
   }

   private void copyPALtoABGR(ByteBuffer buffer, byte[] scanline) {
      if (this.paletteA != null) {
         int i = 1;

         for (int len = scanline.length; i < len; i++) {
            int idx = scanline[i] & 255;
            byte r = this.palette[idx * 3 + 0];
            byte g = this.palette[idx * 3 + 1];
            byte b = this.palette[idx * 3 + 2];
            byte a = this.paletteA[idx];
            buffer.put(a).put(b).put(g).put(r);
         }
      } else {
         int i = 1;

         for (int len = scanline.length; i < len; i++) {
            int idx = scanline[i] & 255;
            byte r = this.palette[idx * 3 + 0];
            byte g = this.palette[idx * 3 + 1];
            byte b = this.palette[idx * 3 + 2];
            buffer.put((byte)-1).put(b).put(g).put(r);
         }
      }
   }

   private void copyPALtoRGBA(ByteBuffer buffer, byte[] scanline) {
      if (this.paletteA != null) {
         int i = 1;

         for (int len = scanline.length; i < len; i++) {
            int idx = scanline[i] & 255;
            byte r = this.palette[idx * 3 + 0];
            byte g = this.palette[idx * 3 + 1];
            byte b = this.palette[idx * 3 + 2];
            byte a = this.paletteA[idx];
            buffer.put(r).put(g).put(b).put(a);
         }
      } else {
         int i = 1;

         for (int len = scanline.length; i < len; i++) {
            int idx = scanline[i] & 255;
            byte r = this.palette[idx * 3 + 0];
            byte g = this.palette[idx * 3 + 1];
            byte b = this.palette[idx * 3 + 2];
            buffer.put(r).put(g).put(b).put((byte)-1);
         }
      }
   }

   private void copyPALtoBGRA(ByteBuffer buffer, byte[] scanline) {
      if (this.paletteA != null) {
         int i = 1;

         for (int len = scanline.length; i < len; i++) {
            int idx = scanline[i] & 255;
            byte r = this.palette[idx * 3 + 0];
            byte g = this.palette[idx * 3 + 1];
            byte b = this.palette[idx * 3 + 2];
            byte a = this.paletteA[idx];
            buffer.put(b).put(g).put(r).put(a);
         }
      } else {
         int i = 1;

         for (int len = scanline.length; i < len; i++) {
            int idx = scanline[i] & 255;
            byte r = this.palette[idx * 3 + 0];
            byte g = this.palette[idx * 3 + 1];
            byte b = this.palette[idx * 3 + 2];
            buffer.put(b).put(g).put(r).put((byte)-1);
         }
      }
   }

   private void expand4(byte[] src, byte[] dst) {
      int i = 1;

      for (int len = dst.length; i < len; i += 2) {
         int val = src[1 + (i >> 1)] & 255;
         switch (len - i) {
            default:
               dst[i + 1] = (byte)(val & 15);
            case 1:
               dst[i] = (byte)(val >> 4);
         }
      }
   }

   private void expand2(byte[] src, byte[] dst) {
      int i = 1;

      for (int len = dst.length; i < len; i += 4) {
         int val = src[1 + (i >> 2)] & 255;
         switch (len - i) {
            default:
               dst[i + 3] = (byte)(val & 3);
            case 3:
               dst[i + 2] = (byte)(val >> 2 & 3);
            case 2:
               dst[i + 1] = (byte)(val >> 4 & 3);
            case 1:
               dst[i] = (byte)(val >> 6);
         }
      }
   }

   private void expand1(byte[] src, byte[] dst) {
      int i = 1;

      for (int len = dst.length; i < len; i += 8) {
         int val = src[1 + (i >> 3)] & 255;
         switch (len - i) {
            default:
               dst[i + 7] = (byte)(val & 1);
            case 7:
               dst[i + 6] = (byte)(val >> 1 & 1);
            case 6:
               dst[i + 5] = (byte)(val >> 2 & 1);
            case 5:
               dst[i + 4] = (byte)(val >> 3 & 1);
            case 4:
               dst[i + 3] = (byte)(val >> 4 & 1);
            case 3:
               dst[i + 2] = (byte)(val >> 5 & 1);
            case 2:
               dst[i + 1] = (byte)(val >> 6 & 1);
            case 1:
               dst[i] = (byte)(val >> 7);
         }
      }
   }

   private void unfilter(byte[] curLine, byte[] prevLine) throws IOException {
      switch (curLine[0]) {
         case 0:
            break;
         case 1:
            this.unfilterSub(curLine);
            break;
         case 2:
            this.unfilterUp(curLine, prevLine);
            break;
         case 3:
            this.unfilterAverage(curLine, prevLine);
            break;
         case 4:
            this.unfilterPaeth(curLine, prevLine);
            break;
         default:
            throw new IOException("invalide filter type in scanline: " + curLine[0]);
      }
   }

   private void unfilterSub(byte[] curLine) {
      int bpp = this.bytesPerPixel;
      int i = bpp + 1;

      for (int len = curLine.length; i < len; i++) {
         curLine[i] += curLine[i - bpp];
      }
   }

   private void unfilterUp(byte[] curLine, byte[] prevLine) {
      int i = 1;

      for (int len = curLine.length; i < len; i++) {
         curLine[i] += prevLine[i];
      }
   }

   private void unfilterAverage(byte[] curLine, byte[] prevLine) {
      int bpp = this.bytesPerPixel;

      int i;
      for (i = 1; i <= bpp; i++) {
         curLine[i] += (byte)((prevLine[i] & 255) >>> 1);
      }

      for (int len = curLine.length; i < len; i++) {
         curLine[i] += (byte)((prevLine[i] & 255) + (curLine[i - bpp] & 255) >>> 1);
      }
   }

   private void unfilterPaeth(byte[] curLine, byte[] prevLine) {
      int bpp = this.bytesPerPixel;

      int i;
      for (i = 1; i <= bpp; i++) {
         curLine[i] += prevLine[i];
      }

      for (int len = curLine.length; i < len; i++) {
         int a = curLine[i - bpp] & 255;
         int b = prevLine[i] & 255;
         int c = prevLine[i - bpp] & 255;
         int p = a + b - c;
         int pa = p - a;
         if (pa < 0) {
            pa = -pa;
         }

         int pb = p - b;
         if (pb < 0) {
            pb = -pb;
         }

         int pc = p - c;
         if (pc < 0) {
            pc = -pc;
         }

         if (pa <= pb && pa <= pc) {
            c = a;
         } else if (pb <= pc) {
            c = b;
         }

         curLine[i] += (byte)c;
      }
   }

   private void readIHDR() throws IOException {
      this.checkChunkLength(13);
      this.readChunk(this.buffer, 0, 13);
      this.width = this.readInt(this.buffer, 0);
      this.height = this.readInt(this.buffer, 4);
      this.bitdepth = this.buffer[8] & 255;
      this.colorType = this.buffer[9] & 255;
      label43:
      switch (this.colorType) {
         case 0:
            if (this.bitdepth != 8) {
               throw new IOException("Unsupported bit depth: " + this.bitdepth);
            }

            this.bytesPerPixel = 1;
            break;
         case 1:
         case 5:
         default:
            throw new IOException("unsupported color format: " + this.colorType);
         case 2:
            if (this.bitdepth != 8) {
               throw new IOException("Unsupported bit depth: " + this.bitdepth);
            }

            this.bytesPerPixel = 3;
            break;
         case 3:
            switch (this.bitdepth) {
               case 1:
               case 2:
               case 4:
               case 8:
                  this.bytesPerPixel = 1;
                  break label43;
               case 3:
               case 5:
               case 6:
               case 7:
               default:
                  throw new IOException("Unsupported bit depth: " + this.bitdepth);
            }
         case 4:
            if (this.bitdepth != 8) {
               throw new IOException("Unsupported bit depth: " + this.bitdepth);
            }

            this.bytesPerPixel = 2;
            break;
         case 6:
            if (this.bitdepth != 8) {
               throw new IOException("Unsupported bit depth: " + this.bitdepth);
            }

            this.bytesPerPixel = 4;
      }

      if (this.buffer[10] != 0) {
         throw new IOException("unsupported compression method");
      }

      if (this.buffer[11] != 0) {
         throw new IOException("unsupported filtering method");
      }

      if (this.buffer[12] != 0) {
         throw new IOException("unsupported interlace method");
      }
   }

   private void readPLTE() throws IOException {
      int count = this.chunkLength / 3;
      if (count >= 1 && count <= 256 && this.chunkLength % 3 == 0) {
         this.palette = new byte[count * 3];
         this.readChunk(this.palette, 0, this.palette.length);
      } else {
         throw new IOException("PLTE chunk has wrong length");
      }
   }

   private void readtRNS() throws IOException {
      switch (this.colorType) {
         case 0:
            this.checkChunkLength(2);
            this.transPixel = new byte[2];
            this.readChunk(this.transPixel, 0, 2);
         case 1:
         default:
            break;
         case 2:
            this.checkChunkLength(6);
            this.transPixel = new byte[6];
            this.readChunk(this.transPixel, 0, 6);
            break;
         case 3:
            if (this.palette == null) {
               throw new IOException("tRNS chunk without PLTE chunk");
            }

            this.paletteA = new byte[this.palette.length / 3];
            Arrays.fill(this.paletteA, (byte)-1);
            this.readChunk(this.paletteA, 0, this.paletteA.length);
      }
   }

   private void closeChunk() throws IOException {
      if (this.chunkRemaining > 0) {
         this.skip(this.chunkRemaining + 4);
      } else {
         this.readFully(this.buffer, 0, 4);
         int expectedCrc = this.readInt(this.buffer, 0);
         int actualCrc = (int)this.crc.getValue();
         if (actualCrc != expectedCrc) {
            throw new IOException("Invalid CRC");
         }
      }

      this.chunkRemaining = 0;
      this.chunkLength = 0;
      this.chunkType = 0;
   }

   private void openChunk() throws IOException {
      this.readFully(this.buffer, 0, 8);
      this.chunkLength = this.readInt(this.buffer, 0);
      this.chunkType = this.readInt(this.buffer, 4);
      this.chunkRemaining = this.chunkLength;
      this.crc.reset();
      this.crc.update(this.buffer, 4, 4);
   }

   private void openChunk(int chunkType) throws IOException {
      this.openChunk();
      if (this.chunkType != chunkType) {
         throw new IOException("Expected chunk: " + Integer.toHexString(chunkType));
      }
   }

   private void checkChunkLength(int length) throws IOException {
      if (this.chunkLength != length) {
         throw new IOException("Chunk has wrong size");
      }
   }

   private int readChunk(byte[] buffer, int offset, int length) throws IOException {
      if (length > this.chunkRemaining) {
         length = this.chunkRemaining;
      }

      this.readFully(buffer, offset, length);
      this.crc.update(buffer, offset, length);
      this.chunkRemaining -= length;
      return length;
   }

   private void refillInflater(Inflater inflater) throws IOException {
      while (this.chunkRemaining == 0) {
         this.closeChunk();
         this.openChunk(1229209940);
      }

      int bytesRead = this.readChunk(this.buffer, 0, this.buffer.length);
      inflater.setInput(this.buffer, 0, bytesRead);
   }

   private void readChunkUnzip(Inflater inflater, byte[] buffer, int offset, int length) throws IOException {
      assert buffer != this.buffer;

      try {
         do {
            int inflated = inflater.inflate(buffer, offset, length);
            if (inflated <= 0) {
               if (inflater.finished()) {
                  throw new EOFException();
               }

               if (!inflater.needsInput()) {
                  throw new IOException("Can't inflate " + length + " bytes");
               }

               this.refillInflater(inflater);
            } else {
               offset += inflated;
               length -= inflated;
            }
         } while (length > 0);
      } catch (DataFormatException e) {
         throw (IOException)new IOException("inflate error").initCause(e);
      }
   }

   private void readFully(byte[] buffer, int offset, int length) throws IOException {
      do {
         int bytesRead = this.input.read(buffer, offset, length);
         if (bytesRead < 0) {
            throw new EOFException();
         }

         offset += bytesRead;
         length -= bytesRead;
      } while (length > 0);
   }

   private int readInt(byte[] buffer, int offset) {
      return buffer[offset] << 24 | (buffer[offset + 1] & 0xFF) << 16 | (buffer[offset + 2] & 0xFF) << 8 | buffer[offset + 3] & 0xFF;
   }

   private void skip(long amount) throws IOException {
      while (amount > 0L) {
         long skipped = this.input.skip(amount);
         if (skipped < 0L) {
            throw new EOFException();
         }

         amount -= skipped;
      }
   }

   private static boolean checkSignature(byte[] signature) {
      for (int i = 0; i < SIGNATURE.length; i++) {
         if (signature[i] != SIGNATURE[i]) {
            return false;
         }
      }

      return true;
   }

   public enum Format {
      ALPHA(1, true),
      LUMINANCE(1, false),
      LUMINANCE_ALPHA(2, true),
      RGB(3, false),
      RGBA(4, true),
      BGRA(4, true),
      ABGR(4, true);

      final int numComponents;
      final boolean hasAlpha;

      Format(int numComponents, boolean hasAlpha) {
         this.numComponents = numComponents;
         this.hasAlpha = hasAlpha;
      }

      public int getNumComponents() {
         return this.numComponents;
      }

      public boolean isHasAlpha() {
         return this.hasAlpha;
      }

      private static PNGDecoder.Format[] createValues() {
         return new PNGDecoder.Format[]{ALPHA, LUMINANCE, LUMINANCE_ALPHA, RGB, RGBA, BGRA, ABGR};
      }
   }
}
