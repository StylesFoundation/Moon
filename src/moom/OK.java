package moom;

import org.apache.logging.log4j.spi.Provider;

public class OK extends Provider {
   public OK() {
      super(15, "2.6.0", Oj.class, Og.class);
   }
}
