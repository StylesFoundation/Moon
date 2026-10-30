package net.minecraft.util;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class EnumTypeAdapterFactory implements TypeAdapterFactory {
   @Override
   public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
      Class var6 = typeToken.getRawType();
      if (!var6.isEnum()) {
         return null;
      }

      final HashMap var7 = Maps.newHashMap();

      for (Object var11 : var6.getEnumConstants()) {
         var7.put(this.func_151232_a(var11), var11);
      }

      return new TypeAdapter<T>() {
         @Override
         public void write(JsonWriter jsonWriter, T object) throws IOException {
            if (object == null) {
               jsonWriter.nullValue();
            } else {
               jsonWriter.value(EnumTypeAdapterFactory.this.func_151232_a(object));
            }
         }

         @Override
         public T read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
               jsonReader.nextNull();
               return null;
            } else {
               return (T)var7.get(jsonReader.nextString());
            }
         }
      };
   }

   private String func_151232_a(Object object) {
      return object instanceof Enum ? ((Enum)object).name().toLowerCase(Locale.US) : object.toString().toLowerCase(Locale.US);
   }
}
