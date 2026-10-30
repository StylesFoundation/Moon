package com.moon.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.moon.Moon;
import com.moon.account.Account;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Iterator;
import org.lwjgl.opengl.Display;

public class AccountStorage {
   private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final Path accountsFile;

   public AccountStorage(Path directory) {
      try {
         if (Files.notExists(directory)) {
            Files.createDirectory(directory);
         }
      } catch (IOException e) {
         Moon.getInstance().getLogger().error("Could not create storage file, do you have enough permissions on the host pc?", new Object[]{e});
         e.printStackTrace();
      }

      this.accountsFile = directory.resolve("accounts.json");
   }

   public void save(Collection<Account> accounts) {
      JsonElement[] elements = accounts.stream().map(Account::toJson).toArray(JsonElement[]::new);
      String json = this.GSON.toJson(elements);

      try {
         Files.writeString(this.accountsFile, json, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
      } catch (IOException e) {
         Moon.getInstance().getLogger().error("Failed to save the account storage to the disk, saved alts purged", new Object[]{e});
         e.printStackTrace();
      }
   }

   public void load() {
      if (!Files.exists(this.accountsFile)) {
         Moon.getInstance().getLogger().info("No account storage found on disk, skipping load of saved alts");
      } else {
         JsonElement jsonElement;
         try {
            jsonElement = JsonParser.parseString(Files.readString(this.accountsFile));
         } catch (IOException e) {
            Moon.getInstance().getLogger().error("Unable to parse module storage file", new Object[]{e});
            e.printStackTrace();
            return;
         }

         if (jsonElement.isJsonArray()) {
            Iterator iterator = jsonElement.getAsJsonArray().iterator();

            while (iterator.hasNext()) {
               Account account = Account.fromJson((JsonElement)iterator.next());
               if (account != null) {
                  Moon.getInstance().getAccountManager().addAccount(account);
               }
            }
         }
      }
   }
}
